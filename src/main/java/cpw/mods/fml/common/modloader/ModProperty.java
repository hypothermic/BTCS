package cpw.mods.fml.common.modloader;





public class ModProperty
{
  private String info;
  



  private double min;
  



  private double max;
  



  private String name;
  




  public ModProperty(String info, double min, double max, String name)
  {
    this.info = info;
    this.min = min;
    this.max = max;
    this.name = name;
  }
  



  public String name()
  {
    return this.name;
  }
  



  public double min()
  {
    return this.min;
  }
  



  public double max()
  {
    return this.max;
  }
  



  public String info()
  {
    return this.info;
  }
}
