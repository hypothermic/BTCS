package net.minecraft.server;

public class ItemColoredBlock
  extends ItemBlock
{
  private final Block a;
  private String[] b;
  
  public ItemColoredBlock(int paramInt, boolean paramBoolean)
  {
    super(paramInt);
    this.a = Block.byId[a()];
    
    if (paramBoolean) {
      setMaxDurability(0);
      a(true);
    }
  }
  










  public int filterData(int paramInt)
  {
    return paramInt;
  }
  
  public ItemColoredBlock a(String[] paramArrayOfString) {
    this.b = paramArrayOfString;
    return this;
  }
  
  public String a(ItemStack paramItemStack)
  {
    if (this.b == null) {
      return super.a(paramItemStack);
    }
    int i = paramItemStack.getData();
    if ((i >= 0) && (i < this.b.length)) {
      return super.a(paramItemStack) + "." + this.b[i];
    }
    return super.a(paramItemStack);
  }
}
