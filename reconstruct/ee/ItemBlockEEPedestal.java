package ee;

import java.util.HashMap;
import net.minecraft.server.ItemBlock;
import net.minecraft.server.ItemStack;

public class ItemBlockEEPedestal extends ItemBlock
{
  HashMap names = new HashMap();
  
  public ItemBlockEEPedestal(int var1)
  {
    super(var1);
    setMaxDurability(0);
    a(true);
  }
  



  public int filterData(int var1)
  {
    return var1;
  }
  
  public void setMetaName(int var1, String var2)
  {
    this.names.put(Integer.valueOf(var1), var2);
  }
  
  public String a(ItemStack var1)
  {
    String var2 = (String)this.names.get(Integer.valueOf(var1.getData()));
    
    if (var2 == null)
    {
      throw new IndexOutOfBoundsException();
    }
    

    return var2;
  }
}
