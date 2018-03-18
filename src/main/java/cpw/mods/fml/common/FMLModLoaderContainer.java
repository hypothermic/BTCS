package cpw.mods.fml.common;

import java.util.Arrays;

public class FMLModLoaderContainer
  extends FMLModContainer
{
  public FMLModLoaderContainer()
  {
    super("Forge Mod Loader");
  }

  public ModMetadata getMetadata()
  {
    if (super.getMetadata() == null) {
      ModMetadata md = new ModMetadata(this);
      setMetadata(md);
      md.name = "Forge Mod Loader";
      md.version = Loader.instance().getFMLVersionString();
      md.credits = "Made possible with help from many people";
      md.authorList = Arrays.asList(new String[] { "cpw, LexManos" });
      md.description = "The Forge Mod Loader provides the ability for systems to load mods from the file system. It also provides key capabilities for mods to be able to cooperate and provide a good modding environment. The mod loading system is compatible with ModLoader, all your ModLoader mods should work.";
      md.url = "https://github.com/cpw/FML/wiki";
      md.updateUrl = "https://github.com/cpw/FML/wiki";
      md.screenshots = new String[0];
      md.logoFile = "";
    }
    return super.getMetadata();
  }

  public String getName()
  {
    return "Forge Mod Loader";
  }

  public String getVersion()
  {
    return Loader.instance().getFMLVersionString();
  }
}
