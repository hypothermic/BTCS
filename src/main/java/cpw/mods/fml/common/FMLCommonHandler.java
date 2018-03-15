package cpw.mods.fml.common;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;




































public class FMLCommonHandler
{
  private static final FMLCommonHandler INSTANCE = new FMLCommonHandler();
  private static final Pattern metadataFile = Pattern.compile("$/modinfo.json$");
  private Map<ModContainer, Set<String>> channelList;
  
  public FMLCommonHandler() {
    this.channelList = new HashMap();
    


    this.modChannels = new HashMap();
    


    this.activeChannels = new HashMap();
    




    this.uniqueEntityListId = 220;
    
    this.auxilliaryContainers = new ArrayList();
    
    this.modLanguageData = new HashMap();
    
    this.tickHandlers = new PriorityQueue();
    
    this.scheduledTicks = new ArrayList();
    
    this.worldGenerators = new HashSet();
  }
  


  private static class TickQueueElement
    implements Comparable<TickQueueElement>
  {
    static long tickCounter = 0L;
    private long next;
    
    public TickQueueElement(IScheduledTickHandler ticker) { this.ticker = ticker;
      update();
    }
    
    public int compareTo(TickQueueElement o)
    {
      return (int)(this.next - o.next);
    }
    
    public void update()
    {
      this.next = (tickCounter + Math.max(this.ticker.nextTickSpacing(), 1));
    }
    

    private IScheduledTickHandler ticker;
    
    public boolean scheduledNow()
    {
      return tickCounter >= this.next;
    }
  }
  
  public void beginLoading(IFMLSidedHandler handler)
  {
    this.sidedDelegate = handler;
    getFMLLogger().info("Attempting early MinecraftForge initialization");
    callForgeMethod("initialize");
    getFMLLogger().info("Completed early MinecraftForge initialization");
  }
  
  public void rescheduleTicks()
  {
    this.sidedDelegate.profileStart("modTickScheduling");
    TickQueueElement.tickCounter += 1L;
    this.scheduledTicks.clear();
    

    while ((this.tickHandlers.size() != 0) && (((TickQueueElement)this.tickHandlers.peek()).scheduledNow()))
    {


      TickQueueElement tickQueueElement = (TickQueueElement)this.tickHandlers.poll();
      tickQueueElement.update();
      this.tickHandlers.offer(tickQueueElement);
      this.scheduledTicks.add(tickQueueElement.ticker);
    }
    this.sidedDelegate.profileEnd();
  }
  
  public void tickStart(EnumSet<TickType> ticks, Object... data) {
    if (this.scheduledTicks.size() == 0)
    {
      return;
    }
    this.sidedDelegate.profileStart("modTickStart$" + ticks);
    for (IScheduledTickHandler ticker : this.scheduledTicks)
    {
      EnumSet<TickType> ticksToRun = EnumSet.copyOf(ticker.ticks());
      ticksToRun.removeAll(EnumSet.complementOf(ticks));
      if (!ticksToRun.isEmpty())
      {
        this.sidedDelegate.profileStart(ticker.getLabel());
        ticker.tickStart(ticksToRun, data);
        this.sidedDelegate.profileEnd();
      }
    }
    this.sidedDelegate.profileEnd();
  }
  
  public void tickEnd(EnumSet<TickType> ticks, Object... data)
  {
    if (this.scheduledTicks.size() == 0)
    {
      return;
    }
    this.sidedDelegate.profileStart("modTickEnd$" + ticks);
    for (IScheduledTickHandler ticker : this.scheduledTicks)
    {
      EnumSet<TickType> ticksToRun = EnumSet.copyOf(ticker.ticks());
      ticksToRun.removeAll(EnumSet.complementOf(ticks));
      if (!ticksToRun.isEmpty())
      {
        this.sidedDelegate.profileStart(ticker.getLabel());
        ticker.tickEnd(ticksToRun, data);
        this.sidedDelegate.profileEnd();
      }
    }
    this.sidedDelegate.profileEnd();
  }
  
  public List<IKeyHandler> gatherKeyBindings() {
    List<IKeyHandler> allKeys = new ArrayList();
    for (ModContainer mod : Loader.getModList())
    {
      allKeys.addAll(mod.getKeys());
    }
    for (ModContainer mod : this.auxilliaryContainers)
    {
      allKeys.addAll(mod.getKeys());
    }
    return allKeys;
  }
  


  public static FMLCommonHandler instance()
  {
    return INSTANCE;
  }
  




  public ModContainer findContainerFor(Object mod)
  {
    for (ModContainer mc : Loader.getModList())
    {
      if (mc.matches(mod))
      {
        return mc;
      }
    }
    return null;
  }
  




  public ModContainer getModForChannel(String channel)
  {
    return (ModContainer)this.modChannels.get(channel);
  }
  




  public Set<String> getChannelListFor(ModContainer container)
  {
    return (Set)this.channelList.get(container);
  }
  

  private Map<String, ModContainer> modChannels;
  
  private Map<Object, Set<String>> activeChannels;
  
  public void registerChannel(ModContainer container, String channelName)
  {
    if (this.modChannels.containsKey(channelName)) {}
    



    Set<String> list = (Set)this.channelList.get(container);
    
    if (list == null)
    {
      list = new HashSet();
      this.channelList.put(container, list);
    }
    
    list.add(channelName);
    this.modChannels.put(channelName, container);
  }
  




  public void activateChannel(Object player, String channel)
  {
    Set<String> active = (Set)this.activeChannels.get(player);
    
    if (active == null)
    {
      active = new HashSet();
      this.activeChannels.put(player, active);
    }
    
    active.add(channel);
  }
  





  public void deactivateChannel(Object player, String channel)
  {
    Set<String> active = (Set)this.activeChannels.get(player);
    
    if (active == null)
    {
      active = new HashSet();
      this.activeChannels.put(player, active);
    }
    
    active.remove(channel);
  }
  




  public byte[] getPacketRegistry()
  {
    StringBuffer sb = new StringBuffer();
    
    for (String chan : this.modChannels.keySet())
    {
      sb.append(chan).append("\000");
    }
    
    try
    {
      return sb.toString().getBytes("UTF8");
    }
    catch (UnsupportedEncodingException e)
    {
      Loader.log.warning("Error building registration list");
      Loader.log.throwing("FMLHooks", "getPacketRegistry", e); }
    return new byte[0];
  }
  

  private IFMLSidedHandler sidedDelegate;
  
  private int uniqueEntityListId;
  
  private List<ModContainer> auxilliaryContainers;
  
  public boolean isChannelActive(String channel, Object player)
  {
    return ((Set)this.activeChannels.get(player)).contains(channel);
  }
  




  public Logger getFMLLogger()
  {
    return Loader.log;
  }
  




  public Logger getMinecraftLogger()
  {
    if (this.sidedDelegate == null)
    {
      throw new RuntimeException("sidedDelegate null when attempting to getMinecraftLogger, this is generally caused by you not installing FML properly, or installing some other mod that edits Minecraft.class on top of FML such as ModLoader, do not do this. Reinstall FML properly and try again.");
    }
    
    return this.sidedDelegate.getMinecraftLogger();
  }
  





  public boolean isModLoaderMod(Class<?> clazz)
  {
    return this.sidedDelegate.isModLoaderMod(clazz);
  }
  






  public ModContainer loadBaseModMod(Class<?> clazz, File canonicalFile)
  {
    return this.sidedDelegate.loadBaseModMod(clazz, canonicalFile);
  }
  
  public File getMinecraftRootDirectory() {
    return this.sidedDelegate.getMinecraftRootDirectory();
  }
  



  public Object getMinecraftInstance()
  {
    return this.sidedDelegate.getMinecraftInstance();
  }
  



  public int nextUniqueEntityListId()
  {
    return this.uniqueEntityListId++;
  }
  





  public void addStringLocalization(String key, String lang, String value)
  {
    Properties langPack = (Properties)this.modLanguageData.get(lang);
    if (langPack == null) {
      langPack = new Properties();
      this.modLanguageData.put(lang, langPack);
    }
    langPack.put(key, value);
    
    handleLanguageLoad(this.sidedDelegate.getCurrentLanguageTable(), lang);
  }
  




  public void handleLanguageLoad(Properties languagePack, String lang)
  {
    Properties usPack = (Properties)this.modLanguageData.get("en_US");
    if (usPack != null) {
      languagePack.putAll(usPack);
    }
    Properties langPack = (Properties)this.modLanguageData.get(lang);
    if (langPack == null) {
      return;
    }
    languagePack.putAll(langPack);
  }
  
  public Side getSide()
  {
    return this.sidedDelegate.getSide();
  }
  
  public void addAuxilliaryModContainer(ModContainer ticker)
  {
    this.auxilliaryContainers.add(ticker);
  }
  







  public int fuelLookup(int itemId, int itemDamage)
  {
    int fv = 0;
    
    for (ModContainer mod : Loader.getModList())
    {
      fv = Math.max(fv, mod.lookupFuelValue(itemId, itemDamage));
    }
    
    return fv;
  }
  
  public void addNameForObject(Object minecraftObject, String lang, String name) {
    String label = this.sidedDelegate.getObjectName(minecraftObject);
    addStringLocalization(label, lang, name);
  }
  

  private Map<String, Properties> modLanguageData;
  
  private PriorityQueue<TickQueueElement> tickHandlers;
  private List<IScheduledTickHandler> scheduledTicks;
  private Set<IWorldGenerator> worldGenerators;
  private Class<?> forge;
  private boolean noForge;
  public void raiseException(Throwable exception, String message, boolean stopGame)
  {
    instance().getFMLLogger().throwing("FMLHandler", "raiseException", exception);
    throw new RuntimeException(exception);
  }
  




  private Class<?> findMinecraftForge()
  {
    if ((this.forge == null) && (!this.noForge)) {
      try
      {
        this.forge = Class.forName("forge.MinecraftForge");
      } catch (Exception ex) {
        try {
          this.forge = Class.forName("net.minecraft.src.forge.MinecraftForge");
        }
        catch (Exception ex2) {
          this.noForge = true;
        }
      }
    }
    return this.forge;
  }
  
  private Object callForgeMethod(String method)
  {
    if (this.noForge) {
      return null;
    }
    try {
      return findMinecraftForge().getMethod(method, new Class[0]).invoke(null, new Object[0]);
    }
    catch (Exception e) {}
    

    return null;
  }
  




  public String[] getBrandingStrings(String mcVersion)
  {
    ArrayList<String> brandings = new ArrayList();
    brandings.add(mcVersion);
    brandings.add(Loader.instance().getFMLVersionString());
    String forgeVersion = (String)callForgeMethod("getVersionString");
    if (forgeVersion != null)
    {
      brandings.add(forgeVersion);
    }
    brandings.addAll(this.sidedDelegate.getAdditionalBrandingInformation());
    try {
      Properties props = new Properties();
      props.load(FMLCommonHandler.class.getClassLoader().getResourceAsStream("fmlbranding.properties"));
      brandings.add(props.getProperty("fmlbranding"));
    }
    catch (Exception ex) {}
    
    brandings.add(String.format("%d mod%s loaded", new Object[] { Integer.valueOf(Loader.getModList().size()), Loader.getModList().size() != 1 ? "s" : "" }));
    Collections.reverse(brandings);
    return (String[])brandings.toArray(new String[brandings.size()]);
  }
  



  public void loadMetadataFor(ModContainer mod)
  {
    if (mod.getSourceType() == ModContainer.SourceType.JAR) {
      ZipFile jar = null;
      try
      {
        jar = new ZipFile(mod.getSource());
        ZipEntry infoFile = jar.getEntry("mcmod.info");
        if (infoFile != null) {
          InputStream input = jar.getInputStream(infoFile);
          ModMetadata data = this.sidedDelegate.readMetadataFrom(input, mod);
          mod.setMetadata(data);
        } else {
          getFMLLogger().fine(String.format("Failed to find mcmod.info file in %s for %s", new Object[] { mod.getSource().getName(), mod.getName() }));
        }
        
      }
      catch (Exception e)
      {
        getFMLLogger().fine(String.format("Failed to find mcmod.info file in %s for %s", new Object[] { mod.getSource().getName(), mod.getName() }));
        getFMLLogger().throwing("FMLCommonHandler", "loadMetadataFor", e);
      }
      finally
      {
        if (jar != null)
        {
          try
          {
            jar.close();
          }
          catch (IOException e) {}
        }
      }
    }
    


    try
    {
      InputStream input = Loader.instance().getModClassLoader().getResourceAsStream(mod.getName() + ".info");
      if (input == null) {
        input = Loader.instance().getModClassLoader().getResourceAsStream("net/minecraft/src/" + mod.getName() + ".info");
      }
      if (input != null) {
        ModMetadata data = this.sidedDelegate.readMetadataFrom(input, mod);
        mod.setMetadata(data);
      }
      
    }
    catch (Exception e)
    {
      getFMLLogger().fine(String.format("Failed to find %s.info file in %s for %s", new Object[] { mod.getName(), mod.getSource().getName(), mod.getName() }));
      getFMLLogger().throwing("FMLCommonHandler", "loadMetadataFor", e);
    }
  }
  




  public IFMLSidedHandler getSidedDelegate()
  {
    return this.sidedDelegate;
  }
  



  public void injectSidedProxyDelegate(ModContainer mod)
  {
    ProxyInjector injector = mod.findSidedProxy();
    if (injector != null)
    {
      injector.inject(mod, this.sidedDelegate.getSide());
    }
  }
  
  public void handleWorldGeneration(int chunkX, int chunkZ, long worldSeed, Object... data)
  {
    Random fmlRandom = new Random(worldSeed);
    long xSeed = fmlRandom.nextLong() >> 3;
    long zSeed = fmlRandom.nextLong() >> 3;
    fmlRandom.setSeed(xSeed * chunkX + zSeed * chunkZ ^ worldSeed);
    
    for (IWorldGenerator generator : this.worldGenerators)
    {
      generator.generate(fmlRandom, chunkX, chunkZ, data);
    }
  }
  
  public void registerTickHandler(ITickHandler handler)
  {
    registerScheduledTickHandler(new SingleIntervalHandler(handler));
  }
  
  public void registerScheduledTickHandler(IScheduledTickHandler handler)
  {
    this.tickHandlers.add(new TickQueueElement(handler));
  }
  
  public void registerWorldGenerator(IWorldGenerator generator)
  {
    this.worldGenerators.add(generator);
  }
}
