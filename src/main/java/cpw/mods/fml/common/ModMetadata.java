package cpw.mods.fml.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

















public class ModMetadata
{
  public ModContainer mod;
  public ModType type;
  public String name;
  public String description;
  
  public static enum ModType
  {
    MODLOADER,  FML;
    

    private ModType() {}
  }
  

  public String url = "";
  public String updateUrl = "";
  
  public String logoFile = "";
  public String version = "";
  public List<String> authorList = new ArrayList(1);
  public String credits = "";
  public String parent = "";
  
  public String[] screenshots;
  public ModContainer parentMod;
  public List<ModContainer> childMods = new ArrayList(1);
  




  public ModMetadata(ModContainer mod)
  {
    this.mod = mod;
    this.type = ((mod instanceof FMLModContainer) ? ModType.FML : ModType.MODLOADER);
  }
  
  public void associate(Map<String, ModContainer> mods) {
    if ((this.parent != null) && (this.parent.length() > 0)) {
      ModContainer mc = (ModContainer)mods.get(this.parent);
      if ((mc != null) && (mc.getMetadata() != null)) {
        mc.getMetadata().childMods.add(this.mod);
        this.parentMod = mc;
      }
    }
  }
  



  public String getChildModCountString()
  {
    return String.format("%d child mod%s", new Object[] { Integer.valueOf(this.childMods.size()), this.childMods.size() != 1 ? "s" : "" });
  }
  
  public String getAuthorList() {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < this.authorList.size(); i++) {
      sb.append((String)this.authorList.get(i));
      if (i < this.authorList.size() - 1) {
        sb.append(", ");
      }
    }
    return sb.toString();
  }
  


  public String getChildModList()
  {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < this.childMods.size(); i++) {
      sb.append(((ModContainer)this.childMods.get(i)).getMetadata().name);
      if (i < this.childMods.size() - 1) {
        sb.append(", ");
      }
    }
    return sb.toString();
  }
}
