package forge;

import net.minecraft.server.EntityMinecart;





public class MinecartKey
{
  public final Class<? extends EntityMinecart> minecart;
  public final int type;
  
  public MinecartKey(Class<? extends EntityMinecart> cls, int typtID)
  {
    this.minecart = cls;
    this.type = typtID;
  }
  

  public boolean equals(Object obj)
  {
    if (obj == null)
    {
      return false;
    }
    
    if (getClass() != obj.getClass())
    {
      return false;
    }
    
    MinecartKey other = (MinecartKey)obj;
    if ((this.minecart != other.minecart) && ((this.minecart == null) || (!this.minecart.equals(other.minecart))))
    {
      return false;
    }
    
    return this.type == other.type;
  }
  

  public int hashCode()
  {
    int hash = 7;
    hash = 59 * hash + (this.minecart != null ? this.minecart.hashCode() : 0);
    hash = 59 * hash + this.type;
    return hash;
  }
}
