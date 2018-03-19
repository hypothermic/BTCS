package cpw.mods.fml.common;

import java.io.File;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.logging.Logger;



















public class ModClassLoader
  extends URLClassLoader
{
  public ModClassLoader()
  {
    super(new URL[0], ModClassLoader.class.getClassLoader());
  }
  
  public ModClassLoader(ClassLoader parent) {
    super(new URL[0], null);
  }
  
  public void addFile(File modFile) throws MalformedURLException {
    ClassLoader cl = getParent();
    if ((cl instanceof URLClassLoader)) {
      URLClassLoader ucl = (URLClassLoader)cl;
      URL url = modFile.toURI().toURL();
      try {
        Method addUrl = URLClassLoader.class.getDeclaredMethod("addURL", new Class[] { URL.class });
        addUrl.setAccessible(true);
        addUrl.invoke(ucl, new Object[] { url });
      } catch (Exception e) {
        Loader.log.severe("A fatal error occured attempting to load a file into the classloader");
        throw new LoaderException(e);
      }
    }
  }
  
  public File[] getParentSources() {
    ClassLoader cl = getParent();
    if ((cl instanceof URLClassLoader)) {
      URLClassLoader ucl = (URLClassLoader)cl;
      URL[] pUrl = ucl.getURLs();
      File[] sources = new File[pUrl.length];
      try
      {
        for (int i = 0; i < pUrl.length; i++) {
          sources[i] = new File(pUrl[i].toURI());
        }
        return sources;
      }
      catch (URISyntaxException e)
      {
        Loader.log.throwing("ModClassLoader", "getParentSources", e);
      }
    }
    Loader.log.severe("Unable to process our input to locate the minecraft code");
    throw new LoaderException();
  }
}
