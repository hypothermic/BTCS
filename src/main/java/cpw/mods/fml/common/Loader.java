package cpw.mods.fml.common;

import cpw.mods.fml.common.toposort.ModSorter;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import nl.hypothermic.btcs.ForgeExchanger; // BTCS

public class Loader {
	private static ForgeExchanger.Reporter fer = new ForgeExchanger.Reporter(); // BTCS
	
  private static Pattern zipJar = Pattern.compile("(.+).(zip|jar)$");
  private static Pattern modClass = Pattern.compile("(.+/|)(mod\\_[^\\s$]+).class$");
  

  private static Loader instance;
  


  private static enum State {
    NOINIT,  LOADING,  PREINIT,  INIT,  POSTINIT,  UP,  ERRORED;

    private State() {}
  }
  


  public static Logger log = Logger.getLogger("ForgeModLoader");
  

  private static String major;
  

  private static String minor;
  

  private static String rev;
  

  private static String build;
  

  private static String mcversion;
  

  private State state;
  

  private ModClassLoader modClassLoader;
  

  private List<ModContainer> mods;
  

  private Map<String, ModContainer> namedMods;
  

  private File canonicalConfigDir;
  

  private File canonicalMinecraftDir;
  

  private Exception capturedError;
  

  public static Loader instance()
  {
    if (instance == null)
    {
      instance = new Loader();
    }
    
    return instance;
  }
  
  private Loader()
  {
    FMLLogFormatter formatter = new FMLLogFormatter();
    if (FMLCommonHandler.instance().getMinecraftLogger() != null) {
      log.setParent(FMLCommonHandler.instance().getMinecraftLogger());
    } else {
      ConsoleHandler ch = new ConsoleHandler();
      log.setUseParentHandlers(false);
      log.addHandler(ch);
      ch.setFormatter(formatter);
    }
    
    log.setLevel(Level.ALL);
    try
    {
      File logPath = new File(FMLCommonHandler.instance().getMinecraftRootDirectory().getCanonicalPath(), "ForgeModLoader-%g.log");
      FileHandler fileHandler = new FileHandler(logPath.getPath(), 0, 3);
      
      fileHandler.setFormatter(new FMLLogFormatter());
      fileHandler.setLevel(Level.ALL);
      log.addHandler(fileHandler);
    }
    catch (Exception e) {}
    


    InputStream stream = Loader.class.getClassLoader().getResourceAsStream("fmlversion.properties");
    Properties properties = new Properties();
    
    if (stream != null) {
      try {
        properties.load(stream);
        major = properties.getProperty("fmlbuild.major.number", "none");
        minor = properties.getProperty("fmlbuild.minor.number", "none");
        rev = properties.getProperty("fmlbuild.revision.number", "none");
        build = properties.getProperty("fmlbuild.build.number", "none");
        mcversion = properties.getProperty("fmlbuild.mcversion", "none");
      } catch (IOException ex) {
        log.log(Level.SEVERE, "Could not get FML version information - corrupted installation detected!", ex);
        throw new LoaderException(ex);
      }
    }
    
    log.info(String.format("Forge Mod Loader version %s.%s.%s.%s for Minecraft %s loading", new Object[] { major, minor, rev, build, mcversion }));
    this.modClassLoader = new ModClassLoader();
  }
  




  private void sortModList()
  {
    log.fine("Verifying mod dependencies are satisfied");
    
    for (ModContainer mod : this.mods)
    {
      if (!this.namedMods.keySet().containsAll(mod.getDependencies()))
      {
        log.severe(String.format("The mod %s requires mods %s to be available, one or more are not", new Object[] { mod.getName(), mod.getDependencies() }));
        LoaderException le = new LoaderException();
        log.throwing("Loader", "sortModList", le);
        throw new LoaderException();
      }
    }
    
    log.fine("All dependencies are satisfied");
    ModSorter sorter = new ModSorter(this.mods, this.namedMods);
    
    try
    {
      log.fine("Sorting mods into an ordered list");
      this.mods = sorter.sort();
      log.fine("Sorted mod list:");
      for (ModContainer mod : this.mods)
      {
        log.fine(String.format("\t%s: %s (%s)", new Object[] { mod.getName(), mod.getSource().getName(), mod.getSortingRules() }));
      }
    }
    catch (IllegalArgumentException iae)
    {
      log.severe("A dependency cycle was detected in the input mod set so they cannot be loaded in order");
      log.throwing("Loader", "sortModList", iae);
      throw new LoaderException(iae);
    }
  }
  




  private void preModInit()
  {
    this.state = State.PREINIT;
    log.fine("Beginning mod pre-initialization");
    
    for (ModContainer mod : this.mods)
    {
      if (mod.wantsPreInit())
      {
        log.finer(String.format("Pre-initializing %s", new Object[] { mod.getSource() }));
        mod.preInit();
        this.namedMods.put(mod.getName(), mod);
      }
      mod.nextState();
    }
    

    for (ModContainer mod : this.mods) {
      if (mod.getMetadata() != null) {
        mod.getMetadata().associate(this.namedMods);
      }
      
      FMLCommonHandler.instance().injectSidedProxyDelegate(mod);
    }
    log.fine("Mod pre-initialization complete");
  }
  



  private void modInit()
  {
    this.state = State.INIT;
    log.fine("Beginning mod initialization");
    
    for (ModContainer mod : this.mods)
    {
      log.finer(String.format("Initializing %s", new Object[] { mod.getName() }));
      mod.init();
      mod.nextState();
    }
    
    log.fine("Mod initialization complete");
  }
  
  private void postModInit()
  {
    this.state = State.POSTINIT;
    log.fine("Beginning mod post-initialization");
    
    for (ModContainer mod : this.mods)
    {
      if (mod.wantsPostInit())
      {
        log.finer(String.format("Post-initializing %s", new Object[] { mod.getName() }));
        mod.postInit();
        mod.nextState();
      }
    }
    
    log.fine("Mod post-initialization complete");
  }

  private void load()
  {
    File minecraftDir = FMLCommonHandler.instance().getMinecraftRootDirectory();
    File modsDir = new File(minecraftDir, "mods");
    File configDir = new File(minecraftDir, "config");
    
    String canonicalModsPath;
    String canonicalConfigPath;
    try
    {
      this.canonicalMinecraftDir = minecraftDir.getCanonicalFile();
      canonicalModsPath = modsDir.getCanonicalPath();
      canonicalConfigPath = configDir.getCanonicalPath();
      this.canonicalConfigDir = configDir.getCanonicalFile();
    }
    catch (IOException ioe)
    {
      log.severe(String.format("Failed to resolve mods directory mods %s", new Object[] { modsDir.getAbsolutePath() }));
      log.throwing("fml.server.Loader", "initialize", ioe);
      throw new LoaderException(ioe);
    }
    
    if (!modsDir.exists())
    {
      log.fine(String.format("No mod directory found, creating one: %s", new Object[] { canonicalModsPath }));
      
      try
      {
        modsDir.mkdir();
      }
      catch (Exception e)
      {
        log.throwing("fml.server.Loader", "initialize", e);
        throw new LoaderException(e);
      }
    }
    
    if (!configDir.exists())
    {
      log.fine(String.format("No config directory found, creating one: %s", new Object[] { canonicalConfigPath }));
      
      try
      {
        configDir.mkdir();
      }
      catch (Exception e)
      {
        log.throwing("fml.server.Loader", "initialize", e);
        throw new LoaderException(e);
      }
    }
    
    if (!modsDir.isDirectory())
    {
      log.severe(String.format("Attempting to load mods from %s, which is not a directory", new Object[] { canonicalModsPath }));
      LoaderException loaderException = new LoaderException();
      log.throwing("fml.server.Loader", "initialize", loaderException);
      throw loaderException;
    }
    
    if (!configDir.isDirectory())
    {
      log.severe(String.format("Attempting to load configuration from %s, which is not a directory", new Object[] { canonicalConfigPath }));
      LoaderException loaderException = new LoaderException();
      log.throwing("fml.server.Loader", "initialize", loaderException);
      throw loaderException;
    }
    
    this.state = State.LOADING;
    log.fine("Attempting to load mods contained in the minecraft jar file and associated classes");
    File[] minecraftSources = this.modClassLoader.getParentSources();
    if ((minecraftSources.length == 1) && (minecraftSources[0].isFile())) {
      log.fine(String.format("Minecraft is a file at %s, loading", new Object[] { minecraftSources[0].getAbsolutePath() }));
      attemptFileLoad(minecraftSources[0], ModContainer.SourceType.CLASSPATH);
    } else {
      for (int i = 0; i < minecraftSources.length; i++) {
        if (minecraftSources[i].isFile()) {
          log.fine(String.format("Found a minecraft related file at %s, loading", new Object[] { minecraftSources[i].getAbsolutePath() }));
          attemptFileLoad(minecraftSources[i], ModContainer.SourceType.CLASSPATH);
        } else if (minecraftSources[i].isDirectory()) {
          log.fine(String.format("Found a minecraft related directory at %s, loading", new Object[] { minecraftSources[i].getAbsolutePath() }));
          attemptDirLoad(minecraftSources[i], "", ModContainer.SourceType.CLASSPATH);
        }
      }
    }
    log.fine("Minecraft jar mods loaded successfully");
    
    log.info(String.format("Loading mods from %s", new Object[] { canonicalModsPath }));
    File[] modList = modsDir.listFiles();
    
    Arrays.sort(modList);
    
    for (File modFile : modList)
    {
      if (modFile.isDirectory())
      {
        log.fine(String.format("Found a directory %s, attempting to load it", new Object[] { modFile.getName() }));
        boolean modFound = attemptDirLoad(modFile, "", ModContainer.SourceType.DIR);
        
        if (modFound)
        {
          log.fine(String.format("Directory %s loaded successfully", new Object[] { modFile.getName() }));
        }
        else
        {
          log.info(String.format("Directory %s contained no mods", new Object[] { modFile.getName() }));
        }
      }
      else
      {
        Matcher matcher = zipJar.matcher(modFile.getName());
        
        if (matcher.matches())
        {
          log.fine(String.format("Found a zip or jar file %s, attempting to load it", new Object[] { matcher.group(0) }));
          boolean modFound = attemptFileLoad(modFile, ModContainer.SourceType.JAR);
          
          if (modFound)
          {
            log.fine(String.format("File %s loaded successfully", new Object[] { matcher.group(0) }));
            fer.reportModLoaded(modFile.getName()); // BTCS
          }
          else
          {
            log.info(String.format("File %s contained no mods", new Object[] { matcher.group(0) }));
          }
        }
      }
    }
    
    if (this.state == State.ERRORED)
    {
      log.severe("A problem has occured during mod loading. Likely a corrupt jar is located in your mods directory");
      throw new LoaderException(this.capturedError);
    }
    
    log.info(String.format("Forge Mod Loader has loaded %d mods", new Object[] { Integer.valueOf(this.mods.size()) }));
  }
  
  private boolean attemptDirLoad(File modDir, String path, ModContainer.SourceType sourceType)
  {
    if (path.length() == 0) {
      extendClassLoader(modDir);
    }
    boolean foundAModClass = false;
    File[] content = modDir.listFiles(new FileFilter()
    {

      public boolean accept(File file)
      {
        return ((file.isFile()) && (Loader.modClass.matcher(file.getName()).find())) || (file.isDirectory());
      }
      

    });
    Arrays.sort(content);
    for (File file : content)
    {
      if (file.isDirectory()) {
        log.finest(String.format("Recursing into package %s", new Object[] { path + file.getName() }));
        foundAModClass |= attemptDirLoad(file, path + file.getName() + ".", sourceType);
      }
      else {
        Matcher fname = modClass.matcher(file.getName());
        if (fname.find())
        {

          String clazzName = path + fname.group(2);
          try
          {
            log.fine(String.format("Found a mod class %s in directory %s, attempting to load it", new Object[] { clazzName, modDir.getName() }));
            loadModClass(modDir, file.getName(), clazzName, sourceType);
            log.fine(String.format("Successfully loaded mod class %s", new Object[] { file.getName() }));
            foundAModClass = true;
          }
          catch (Exception e)
          {
            log.severe(String.format("File %s failed to read properly", new Object[] { file.getName() }));
            log.throwing("fml.server.Loader", "attemptDirLoad", e);
            this.state = State.ERRORED;
            this.capturedError = e;
          }
        }
      } }
    return foundAModClass;
  }
  
  private void loadModClass(File classSource, String classFileName, String clazzName, ModContainer.SourceType sourceType)
  {
    try
    {
      Class<?> clazz = Class.forName(clazzName, false, this.modClassLoader);
      
      ModContainer mod = null;
      if (clazz.isAnnotationPresent(Mod.class))
      {

        log.severe("Currently, the FML mod type is disabled");
        throw new LoaderException();
      }
      


      if (FMLCommonHandler.instance().isModLoaderMod(clazz))
      {
        log.fine(String.format("ModLoader BaseMod class %s found, loading", new Object[] { clazzName }));
        mod = FMLCommonHandler.instance().loadBaseModMod(clazz, classSource.getCanonicalFile());
        log.fine(String.format("ModLoader BaseMod class %s loaded", new Object[] { clazzName }));
      }
      



      if (mod != null) {
        mod.setSourceType(sourceType);
        FMLCommonHandler.instance().loadMetadataFor(mod);
        this.mods.add(mod);
        mod.nextState();
      }
    }
    catch (Throwable e)
    {
      log.warning(String.format("Failed to load mod class %s in %s", new Object[] { classFileName, classSource.getAbsoluteFile() }));
      log.throwing("fml.server.Loader", "attemptLoad", e);
      throw new LoaderException(e);
    }
  }
  
  private void extendClassLoader(File file)
  {
    try
    {
      this.modClassLoader.addFile(file);
    }
    catch (MalformedURLException e)
    {
      throw new LoaderException(e);
    }
  }
  
  private boolean attemptFileLoad(File modFile, ModContainer.SourceType sourceType)
  {
    extendClassLoader(modFile);
    boolean foundAModClass = false;
    
    ZipFile jar = null;
    try
    {
      jar = new ZipFile(modFile);
      
      for (ZipEntry ze : Collections.list(jar.entries()))
      {
        Matcher match = modClass.matcher(ze.getName());
        
        if (match.matches())
        {
          String pkg = match.group(1).replace('/', '.');
          String clazzName = pkg + match.group(2);
          log.fine(String.format("Found a mod class %s in file %s, attempting to load it", new Object[] { clazzName, modFile.getName() }));
          loadModClass(modFile, ze.getName(), clazzName, sourceType);
          log.fine(String.format("Mod class %s loaded successfully", new Object[] { clazzName, modFile.getName() }));
          foundAModClass = true;
        }
      }
      return foundAModClass;
    }
    catch (Exception e)
    {
      log.severe(String.format("Zip file %s failed to read properly", new Object[] { modFile.getName() }));
      log.throwing("fml.server.Loader", "attemptFileLoad", e);
      this.state = State.ERRORED;
      this.capturedError = e;
    }
    finally
    {
      if (jar != null)
      {
        try
        {
          jar.close();
        }
        catch (Exception e) {}
      }
    }
    return foundAModClass;
  }

  public static List<ModContainer> getModList()
  {
    return instance().mods;
  }
  
  public void loadMods()
  {
    this.state = State.NOINIT;
    this.mods = new ArrayList();
    this.namedMods = new HashMap();
    load();
    preModInit();
    sortModList();
    
    this.mods = Collections.unmodifiableList(this.mods);
  }

  public void initializeMods()
  {
    modInit();
    postModInit();
    for (ModContainer mod : getModList()) {
      mod.nextState();
    }
    this.state = State.UP;
    log.info(String.format("Forge Mod Loader load complete, %d mods loaded", new Object[] { Integer.valueOf(this.mods.size()) }));
  }
  
  public static boolean isModLoaded(String modname)
  {
    return instance().namedMods.containsKey(modname);
  }

  public File getConfigDir()
  {
    return this.canonicalConfigDir;
  }
  
  public String getCrashInformation()
  {
    StringBuffer ret = new StringBuffer();
    for (String brand : FMLCommonHandler.instance().getBrandingStrings(String.format("Forge Mod Loader version %s.%s.%s.%s for Minecraft %s", new Object[] { major, minor, rev, build, mcversion }))) {
      ret.append(brand).append("\n");
    }
    for (ModContainer mod : this.mods)
    {
      ret.append(String.format("\t%s : %s (%s)\n", new Object[] { mod.getName(), mod.getModState(), mod.getSource().getName() }));
    }
    return ret.toString();
  }

  public String getFMLVersionString()
  {
    return String.format("FML v%s.%s.%s.%s", new Object[] { major, minor, rev, build });
  }

  public ClassLoader getModClassLoader()
  {
    return this.modClassLoader;
  }
}
