package net.minecraft.server;





public class TileEntityRecordPlayer
  extends TileEntity
{
  public int record;
  




  public void a(NBTTagCompound paramNBTTagCompound)
  {
    super.a(paramNBTTagCompound);
    this.record = paramNBTTagCompound.getInt("Record");
  }
  
  public void b(NBTTagCompound paramNBTTagCompound)
  {
    super.b(paramNBTTagCompound);
    if (this.record > 0) paramNBTTagCompound.setInt("Record", this.record);
  }
}
