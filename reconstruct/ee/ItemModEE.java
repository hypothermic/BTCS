package ee;

import forge.ITextureProvider;
import net.minecraft.server.Item;
import net.minecraft.server.ItemStack;
import net.minecraft.server.NBTTagCompound;

public class ItemModEE extends Item implements ITextureProvider
{
  protected ItemModEE(int var1)
  {
    super(var1);
    setNoRepair();
  }
  
  public String getTextureFile()
  {
    return "/eqex/eqexsheet.png";
  }
  
  public String getString(ItemStack var1, String var2)
  {
    if (var1.tag == null)
    {
      var1.setTag(new NBTTagCompound());
    }
    
    if (!var1.tag.hasKey(var2))
    {
      setString(var1, var2, "");
    }
    
    return var1.tag.getString(var2);
  }
  
  public void setString(ItemStack var1, String var2, String var3)
  {
    if (var1.tag == null)
    {
      var1.setTag(new NBTTagCompound());
    }
    
    var1.tag.setString(var2, var3);
  }
  
  public boolean getBoolean(ItemStack var1, String var2)
  {
    if (var1.tag == null)
    {
      var1.setTag(new NBTTagCompound());
    }
    
    if (!var1.tag.hasKey(var2))
    {
      setBoolean(var1, var2, false);
    }
    
    return var1.tag.getBoolean(var2);
  }
  
  public void setBoolean(ItemStack var1, String var2, boolean var3)
  {
    if (var1.tag == null)
    {
      var1.setTag(new NBTTagCompound());
    }
    
    var1.tag.setBoolean(var2, var3);
  }
  
  public short getShort(ItemStack var1, String var2)
  {
    if (var1.tag == null)
    {
      var1.setTag(new NBTTagCompound());
    }
    
    if (!var1.tag.hasKey(var2))
    {
      setShort(var1, var2, 0);
    }
    
    return var1.tag.getShort(var2);
  }
  
  public void setShort(ItemStack var1, String var2, int var3)
  {
    if (var1.tag == null)
    {
      var1.setTag(new NBTTagCompound());
    }
    
    var1.tag.setShort(var2, (short)var3);
  }
  
  public int getInteger(ItemStack var1, String var2)
  {
    if (var1.tag == null)
    {
      var1.setTag(new NBTTagCompound());
    }
    
    if (!var1.tag.hasKey(var2))
    {
      setInteger(var1, var2, 0);
    }
    
    return var1.tag.getInt(var2);
  }
  
  public void setInteger(ItemStack var1, String var2, int var3)
  {
    if (var1.tag == null)
    {
      var1.setTag(new NBTTagCompound());
    }
    
    var1.tag.setInt(var2, var3);
  }
  
  public byte getByte(ItemStack var1, String var2)
  {
    if (var1.tag == null)
    {
      var1.setTag(new NBTTagCompound());
    }
    
    if (!var1.tag.hasKey(var2))
    {
      setByte(var1, var2, (byte)0);
    }
    
    return var1.tag.getByte(var2);
  }
  
  public void setByte(ItemStack var1, String var2, byte var3)
  {
    if (var1.tag == null)
    {
      var1.setTag(new NBTTagCompound());
    }
    
    var1.tag.setByte(var2, var3);
  }
  
  public long getLong(ItemStack var1, String var2)
  {
    if (var1.tag == null)
    {
      var1.setTag(new NBTTagCompound());
    }
    
    if (!var1.tag.hasKey(var2))
    {
      setLong(var1, var2, 0L);
    }
    
    return var1.tag.getLong(var2);
  }
  
  public void setLong(ItemStack var1, String var2, long var3)
  {
    if (var1.tag == null)
    {
      var1.setTag(new NBTTagCompound());
    }
    
    var1.tag.setLong(var2, var3);
  }
}
