package cpw.mods.fml.common;














public enum Side
{
  CLIENT,  SERVER,  BUKKIT;
  

  private Side() {}
  
  public boolean isServer()
  {
    return !isClient();
  }
  



  public boolean isClient()
  {
    return this == CLIENT;
  }
}
