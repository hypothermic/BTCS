package ee;

import net.minecraft.server.Block;
import net.minecraft.server.EEProxy;
import net.minecraft.server.ItemStack;
import net.minecraft.server.ModLoader;

public class EEBlock
{
  public static BlockEEStone eeStone;
  public static BlockEEPedestal eePedestal;
  public static BlockEETorch eeTorch;
  public static BlockEEChest eeChest;
  public static BlockEEDevice eeDevice;
  public static ItemStack alchChest;
  public static ItemStack collector;
  public static ItemStack condenser;
  public static ItemStack dmFurnace;
  public static ItemStack relay;
  public static ItemStack dmBlock;
  public static ItemStack rmBlock;
  public static ItemStack collector2;
  public static ItemStack collector3;
  public static ItemStack relay2;
  public static ItemStack relay3;
  public static ItemStack rmFurnace;
  public static ItemStack pedestal;
  public static ItemStack iTorch;
  public static ItemStack novaCatalyst;
  public static ItemStack novaCataclysm;
  public static ItemStack transTablet;
  private static boolean initialized;
  
  public static void init()
  {
    if (!initialized)
    {
      initialized = true;
      eeStone = new BlockEEStone(EEBase.props.getInt("BlockEEStone"));
      eePedestal = new BlockEEPedestal(EEBase.props.getInt("BlockEEPedestal"));
      eeTorch = new BlockEETorch(EEBase.props.getInt("BlockEETorch"));
      eeChest = new BlockEEChest(EEBase.props.getInt("BlockEEChest"));
      eeDevice = new BlockEEDevice(EEBase.props.getInt("BlockEEDevice"));
      eeStone.a("eeStone");
      eePedestal.a("eePedestal");
      eeTorch.a("eeTorch");
      eeChest.a("eeChest");
      eeDevice.a("eeDevice");
      eeStone.addTileEntityMapping(0, TileCollector.class);
      eeStone.addTileEntityMapping(1, TileCollector2.class);
      eeStone.addTileEntityMapping(2, TileCollector3.class);
      eeStone.addTileEntityMapping(3, TileDMFurnace.class);
      eeStone.addTileEntityMapping(4, TileRMFurnace.class);
      eeStone.addTileEntityMapping(5, TileRelay.class);
      eeStone.addTileEntityMapping(6, TileRelay2.class);
      eeStone.addTileEntityMapping(7, TileRelay3.class);
      eeStone.addTileEntityMapping(8, TileDMBlock.class);
      eeStone.addTileEntityMapping(9, TileRMBlock.class);
      eeStone.addTileEntityMapping(10, TileNovaCatalyst.class);
      eeStone.addTileEntityMapping(11, TileNovaCataclysm.class);
      eeChest.addTileEntityMapping(0, TileAlchChest.class);
      eeChest.addTileEntityMapping(1, TileCondenser.class);
      eeDevice.addTileEntityMapping(0, TileTransTablet.class);
      ModLoader.registerBlock(eeStone, ItemBlockEEStone.class);
      ModLoader.registerBlock(eeTorch, ItemBlockEETorch.class);
      ModLoader.registerBlock(eePedestal, ItemBlockEEPedestal.class);
      ModLoader.registerBlock(eeChest, ItemBlockEEChest.class);
      ModLoader.registerBlock(eeDevice, ItemBlockEEDevice.class);
      ModLoader.registerTileEntity(TileAlchChest.class, "Alchemical Chest");
      ModLoader.registerTileEntity(TileCollector.class, "Energy Collector");
      ModLoader.registerTileEntity(TileCollector2.class, "Energy Collector MK2");
      ModLoader.registerTileEntity(TileCollector3.class, "Energy Collector MK3");
      ModLoader.registerTileEntity(TileRelay.class, "Antimatter Array");
      ModLoader.registerTileEntity(TileRelay2.class, "Antimatter Array MK2");
      ModLoader.registerTileEntity(TileRelay3.class, "Antimatter Array MK3");
      ModLoader.registerTileEntity(TileCondenser.class, "Energy Condenser");
      ModLoader.registerTileEntity(TileDMBlock.class, "DM Block");
      ModLoader.registerTileEntity(TileRMBlock.class, "RM Block");
      ModLoader.registerTileEntity(TilePedestal.class, "Pedestal");
      ModLoader.registerTileEntity(TileDMFurnace.class, "Dark Matter Furnace");
      ModLoader.registerTileEntity(TileRMFurnace.class, "Red Matter Furnace");
      ModLoader.registerTileEntity(TileNovaCatalyst.class, "Nova Catalyst");
      ModLoader.registerTileEntity(TileNovaCataclysm.class, "Nova Cataclysm");
      ModLoader.registerTileEntity(TileTransTablet.class, "Transmutation Tablet");
      eeStone.setItemName(0, "collector");
      eeStone.setItemName(1, "collector2");
      eeStone.setItemName(2, "collector3");
      eeStone.setItemName(3, "dmFurnace");
      eeStone.setItemName(4, "rmFurnace");
      eeStone.setItemName(5, "relay");
      eeStone.setItemName(6, "relay2");
      eeStone.setItemName(7, "relay3");
      eeStone.setItemName(8, "dmBlock");
      eeStone.setItemName(9, "rmBlock");
      eeStone.setItemName(10, "novaCatalyst");
      eeStone.setItemName(11, "novaCataclysm");
      eeTorch.setItemName(0, "iTorch");
      eePedestal.setItemName(0, "pedestal");
      eeChest.setItemName(0, "alchChest");
      eeChest.setItemName(1, "condenser");
      eeDevice.setItemName(0, "transTablet");
      collector = new ItemStack(eeStone, 1, 0);
      collector2 = new ItemStack(eeStone, 1, 1);
      collector3 = new ItemStack(eeStone, 1, 2);
      dmFurnace = new ItemStack(eeStone, 1, 3);
      rmFurnace = new ItemStack(eeStone, 1, 4);
      relay = new ItemStack(eeStone, 1, 5);
      relay2 = new ItemStack(eeStone, 1, 6);
      relay3 = new ItemStack(eeStone, 1, 7);
      dmBlock = new ItemStack(eeStone, 1, 8);
      rmBlock = new ItemStack(eeStone, 1, 9);
      novaCatalyst = new ItemStack(eeStone, 1, 10);
      novaCataclysm = new ItemStack(eeStone, 1, 11);
      pedestal = new ItemStack(eePedestal, 1, 0);
      iTorch = new ItemStack(eeTorch, 1, 0);
      alchChest = new ItemStack(eeChest, 1, 0);
      condenser = new ItemStack(eeChest, 1, 1);
      transTablet = new ItemStack(eeDevice, 1, 0);
    }
  }
  
  public static int blockDamageDropped(Block var0, int var1)
  {
    return EEProxy.blockDamageDropped(var0, var1);
  }
}
