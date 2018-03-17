package net.minecraft.server;

import forge.MinecraftForge;
import forge.NetworkMod;
import java.io.File;
import java.util.Random;

public class mod_WeaponMod extends NetworkMod
{
  private int overrideAmount;
  private Random rand;
  public WM_Properties properties;
  public static mod_WeaponMod instance;
  public static final int CANNON_PACKET_ID = 0;
  public static Item javelin;
  public static Item spearWood;
  public static Item spearStone;
  public static Item spearSteel;
  public static Item spearDiamond;
  public static Item spearGold;
  public static Item halberdWood;
  public static Item halberdStone;
  public static Item halberdSteel;
  public static Item halberdDiamond;
  public static Item halberdGold;
  public static Item knifeWood;
  public static Item knifeStone;
  public static Item knifeSteel;
  public static Item knifeDiamond;
  public static Item knifeGold;
  public static Item bayonet;
  public static Item musketBullet;
  public static Item musket;
  public static Item gunStock;
  public static Item musket_iron_part;
  public static Item battleaxeWood;
  public static Item battleaxeStone;
  public static Item battleaxeSteel;
  public static Item battleaxeDiamond;
  public static Item battleaxeGold;
  public static Item warhammerWood;
  public static Item warhammerStone;
  public static Item warhammerSteel;
  public static Item warhammerDiamond;
  public static Item warhammerGold;
  public static Item crossbow;
  public static Item bolt;
  public static Item blowgun;
  public static Item dart;
  public static Item dynamite;
  public static Item flailWood;
  public static Item flailStone;
  public static Item flailSteel;
  public static Item flailDiamond;
  public static Item flailGold;
  public static Item fireRod;
  public static Item cannon;
  public static Item cannonBall;
  public static Item blunderShot;
  public static Item blunderbuss;
  public static Item blunder_iron_part;
  public static Item dummy;
  public static final String texturePath = "/gui/weaponmod/";
  
  public mod_WeaponMod()
  {
    this.rand = new Random();
    this.overrideAmount = 0;
    createWeaponData();
    this.properties = new WM_Properties(this);
    this.properties.loadAllProperties();
    addWeapons();
    registerWeapons();
    ModLoader.registerPacketChannel(this, "wpnmodCannon");
    ModLoader.registerPacketChannel(this, "wpnmodFlail");
    WM_WeaponStorage.clear();
    instance = this;
  }
  

  public void load() {}
  

  public String getVersion()
  {
    return "1.2.5 v8.6.0";
  }
  
  public String getName()
  {
    return "Balkon's WeaponMod";
  }
  
  private void createWeaponData()
  {
    WM_WeaponStorage.addWeapon("javelin", false, true, true);
    WM_WeaponStorage.addWeapon("spear", true, true, true);
    WM_WeaponStorage.addWeapon("halberd", true, false, true);
    WM_WeaponStorage.addWeapon("battleaxe", true, false, true);
    WM_WeaponStorage.addWeapon("warhammer", true, false, true);
    WM_WeaponStorage.addWeapon("knife", true, true, true);
    WM_WeaponStorage.addWeapon("flail", true, true, true);
    WM_WeaponStorage.addWeapon("musket", false, false, true);
    WM_WeaponStorage.addWeapon("musketbayonet", false, false, false);
    WM_WeaponStorage.addWeapon("musket-ironpart", false, false, false);
    WM_WeaponStorage.addWeapon("bullet", false, true, false);
    WM_WeaponStorage.addWeapon("crossbow", false, false, true);
    WM_WeaponStorage.addWeapon("bolt", false, true, false);
    WM_WeaponStorage.addWeapon("blowgun", false, false, true);
    WM_WeaponStorage.addWeapon("dart", false, true, false);
    WM_WeaponStorage.addWeapon("dynamite", false, true, true);
    WM_WeaponStorage.addWeapon("firerod", false, false, false);
    WM_WeaponStorage.addWeapon("cannon", false, true, true);
    WM_WeaponStorage.addWeapon("cannonball", false, true, false);
    WM_WeaponStorage.addWeapon("blunderbuss", false, false, true);
    WM_WeaponStorage.addWeapon("shot", false, true, false);
    WM_WeaponStorage.addWeapon("blunder-ironpart", false, false, false);
    WM_WeaponStorage.addWeapon("gun-stock", false, false, false);
    WM_WeaponStorage.addWeapon("dummy", false, true, true);
  }
  
  private void addWeapons()
  {
    WM_WeaponStorage localWM_WeaponStorage = WM_WeaponStorage.getWeapon("spear");
    int[] arrayOfInt1;
    if (localWM_WeaponStorage.isEnabled)
    {
      arrayOfInt1 = localWM_WeaponStorage.getItemIDs();
      spearWood = new WM_ItemSpear(arrayOfInt1[0], EnumToolMaterial.WOOD, WM_EnumWeapon.SPEAR).d(0).a("spearWood");
      spearStone = new WM_ItemSpear(arrayOfInt1[1], EnumToolMaterial.STONE, WM_EnumWeapon.SPEAR).d(1).a("spearStone");
      spearSteel = new WM_ItemSpear(arrayOfInt1[2], EnumToolMaterial.IRON, WM_EnumWeapon.SPEAR).d(2).a("spearSteel");
      spearDiamond = new WM_ItemSpear(arrayOfInt1[3], EnumToolMaterial.DIAMOND, WM_EnumWeapon.SPEAR).d(3).a("spearDiamond");
      spearGold = new WM_ItemSpear(arrayOfInt1[4], EnumToolMaterial.GOLD, WM_EnumWeapon.SPEAR).d(4).a("spearGold");
    }
    
    localWM_WeaponStorage = WM_WeaponStorage.getWeapon("halberd");
    
    if (localWM_WeaponStorage.isEnabled)
    {
      arrayOfInt1 = localWM_WeaponStorage.getItemIDs();
      halberdWood = new WM_ItemHalberd(arrayOfInt1[0], EnumToolMaterial.WOOD, WM_EnumWeapon.HALBERD).d(16).a("halberdWood");
      halberdStone = new WM_ItemHalberd(arrayOfInt1[1], EnumToolMaterial.STONE, WM_EnumWeapon.HALBERD).d(17).a("halberdStone");
      halberdSteel = new WM_ItemHalberd(arrayOfInt1[2], EnumToolMaterial.IRON, WM_EnumWeapon.HALBERD).d(18).a("halberdSteel");
      halberdDiamond = new WM_ItemHalberd(arrayOfInt1[3], EnumToolMaterial.DIAMOND, WM_EnumWeapon.HALBERD).d(19).a("halberdDiamond");
      halberdGold = new WM_ItemHalberd(arrayOfInt1[4], EnumToolMaterial.GOLD, WM_EnumWeapon.HALBERD).d(20).a("halberdGold");
    }
    
    localWM_WeaponStorage = WM_WeaponStorage.getWeapon("battleaxe");
    
    if (localWM_WeaponStorage.isEnabled)
    {
      arrayOfInt1 = localWM_WeaponStorage.getItemIDs();
      battleaxeWood = new WM_ItemBattleAxe(arrayOfInt1[0], EnumToolMaterial.WOOD, WM_EnumWeapon.BATTLEAXE).d(32).a("battleaxeWood");
      battleaxeStone = new WM_ItemBattleAxe(arrayOfInt1[1], EnumToolMaterial.STONE, WM_EnumWeapon.BATTLEAXE).d(33).a("battleaxeStone");
      battleaxeSteel = new WM_ItemBattleAxe(arrayOfInt1[2], EnumToolMaterial.IRON, WM_EnumWeapon.BATTLEAXE).d(34).a("battleaxeIron");
      battleaxeDiamond = new WM_ItemBattleAxe(arrayOfInt1[3], EnumToolMaterial.DIAMOND, WM_EnumWeapon.BATTLEAXE).d(35).a("battleaxeDiamond");
      battleaxeGold = new WM_ItemBattleAxe(arrayOfInt1[4], EnumToolMaterial.GOLD, WM_EnumWeapon.BATTLEAXE).d(36).a("battleaxeGold");
    }
    
    localWM_WeaponStorage = WM_WeaponStorage.getWeapon("warhammer");
    
    if (localWM_WeaponStorage.isEnabled)
    {
      arrayOfInt1 = localWM_WeaponStorage.getItemIDs();
      warhammerWood = new WM_ItemWarhammer(arrayOfInt1[0], EnumToolMaterial.WOOD, WM_EnumWeapon.WARHAMMER).d(48).a("warhammerWood");
      warhammerStone = new WM_ItemWarhammer(arrayOfInt1[1], EnumToolMaterial.STONE, WM_EnumWeapon.WARHAMMER).d(49).a("warhammerStone");
      warhammerSteel = new WM_ItemWarhammer(arrayOfInt1[2], EnumToolMaterial.IRON, WM_EnumWeapon.WARHAMMER).d(50).a("warhammerIron");
      warhammerDiamond = new WM_ItemWarhammer(arrayOfInt1[3], EnumToolMaterial.DIAMOND, WM_EnumWeapon.WARHAMMER).d(51).a("warhammerDiamond");
      warhammerGold = new WM_ItemWarhammer(arrayOfInt1[4], EnumToolMaterial.GOLD, WM_EnumWeapon.WARHAMMER).d(52).a("warhammerGold");
    }
    
    localWM_WeaponStorage = WM_WeaponStorage.getWeapon("knife");
    
    if (localWM_WeaponStorage.isEnabled)
    {
      arrayOfInt1 = localWM_WeaponStorage.getItemIDs();
      knifeWood = new WM_ItemKnife(arrayOfInt1[0], EnumToolMaterial.WOOD, WM_EnumWeapon.KNIFE).d(64).a("knifeWood");
      knifeStone = new WM_ItemKnife(arrayOfInt1[1], EnumToolMaterial.STONE, WM_EnumWeapon.KNIFE).d(65).a("knifeStone");
      knifeSteel = new WM_ItemKnife(arrayOfInt1[2], EnumToolMaterial.IRON, WM_EnumWeapon.KNIFE).d(66).a("knifeSteel");
      knifeDiamond = new WM_ItemKnife(arrayOfInt1[3], EnumToolMaterial.DIAMOND, WM_EnumWeapon.KNIFE).d(67).a("knifeDiamond");
      knifeGold = new WM_ItemKnife(arrayOfInt1[4], EnumToolMaterial.GOLD, WM_EnumWeapon.KNIFE).d(68).a("knifeGold");
    }
    
    localWM_WeaponStorage = WM_WeaponStorage.getWeapon("javelin");
    int i;
    if (localWM_WeaponStorage.isEnabled)
    {
      i = localWM_WeaponStorage.getItemID();
      javelin = new WM_ItemJavelin(i, null, WM_EnumWeapon.NONE).d(5).a("javelin");
    }
    
    localWM_WeaponStorage = WM_WeaponStorage.getWeapon("musket");
    
    if (localWM_WeaponStorage.isEnabled)
    {
      i = localWM_WeaponStorage.getItemID();
      musket = new WM_ItemMusket(i, EnumToolMaterial.IRON, WM_EnumWeapon.MUSKET).d(21).a("musket");
      
      if (WM_WeaponStorage.isEnabled("knife"))
      {
        i = WM_WeaponStorage.getWeapon("musketbayonet").getItemID();
        bayonet = new WM_ItemMusket(i, EnumToolMaterial.IRON, WM_EnumWeapon.KNIFE).d(22).a("bayonet");
      }
      
      i = WM_WeaponStorage.getWeapon("musket-ironpart").getItemID();
      musket_iron_part = new WM_Item(i).d(23).a("musket_iron_part");
      i = WM_WeaponStorage.getWeapon("bullet").getItemID();
      musketBullet = new WM_Item(i).d(24).a("bullet");
    }
    
    localWM_WeaponStorage = WM_WeaponStorage.getWeapon("crossbow");
    
    if (localWM_WeaponStorage.isEnabled)
    {
      i = localWM_WeaponStorage.getItemID();
      crossbow = new WM_ItemCrossbow(i, null, WM_EnumWeapon.NONE, 54, 53).d(53).a("crossbow");
      i = WM_WeaponStorage.getWeapon("bolt").getItemID();
      bolt = new WM_Item(i).d(55).a("bolt");
    }
    
    localWM_WeaponStorage = WM_WeaponStorage.getWeapon("blowgun");
    
    if (localWM_WeaponStorage.isEnabled)
    {
      i = localWM_WeaponStorage.getItemID();
      blowgun = new WM_ItemBlowgun(i, null, WM_EnumWeapon.NONE).d(69).a("blowgun");
      i = WM_WeaponStorage.getWeapon("dart").getItemID();
      dart = new WM_Item(i).d(70).a("dart");
    }
    
    localWM_WeaponStorage = WM_WeaponStorage.getWeapon("dynamite");
    
    if (localWM_WeaponStorage.isEnabled)
    {
      i = localWM_WeaponStorage.getItemID();
      dynamite = new WM_ItemDynamite(i).d(7).a("dynamite");
    }
    
    localWM_WeaponStorage = WM_WeaponStorage.getWeapon("flail");
    
    if (localWM_WeaponStorage.isEnabled)
    {
      int[] arrayOfInt2 = localWM_WeaponStorage.getItemIDs();
      int k = 96;
      flailWood = new WM_ItemFlail(arrayOfInt2[0], EnumToolMaterial.WOOD, WM_EnumWeapon.FLAIL, 80, k).d(80).a("flailWood");
      flailStone = new WM_ItemFlail(arrayOfInt2[1], EnumToolMaterial.STONE, WM_EnumWeapon.FLAIL, 81, k).d(81).a("flailStone");
      flailSteel = new WM_ItemFlail(arrayOfInt2[2], EnumToolMaterial.IRON, WM_EnumWeapon.FLAIL, 82, k).d(82).a("flailSteel");
      flailDiamond = new WM_ItemFlail(arrayOfInt2[3], EnumToolMaterial.DIAMOND, WM_EnumWeapon.FLAIL, 83, k).d(83).a("flailDiamond");
      flailGold = new WM_ItemFlail(arrayOfInt2[4], EnumToolMaterial.GOLD, WM_EnumWeapon.FLAIL, 84, k).d(84).a("flailGold");
    }
    
    localWM_WeaponStorage = WM_WeaponStorage.getWeapon("firerod");
    int j;
    if (localWM_WeaponStorage.isEnabled)
    {
      j = localWM_WeaponStorage.getItemID();
      fireRod = new WM_ItemFireRod(j, EnumToolMaterial.WOOD, WM_EnumWeapon.FIREROD).d(8).a("fireRod");
    }
    
    localWM_WeaponStorage = WM_WeaponStorage.getWeapon("cannon");
    
    if (localWM_WeaponStorage.isEnabled)
    {
      j = localWM_WeaponStorage.getItemID();
      cannon = new WM_ItemCannon(j).d(85).a("cannon");
      j = WM_WeaponStorage.getWeapon("cannonball").getItemID();
      cannonBall = new WM_Item(j).d(86).a("cannonBall");
    }
    
    localWM_WeaponStorage = WM_WeaponStorage.getWeapon("blunderbuss");
    
    if (localWM_WeaponStorage.isEnabled)
    {
      j = localWM_WeaponStorage.getItemID();
      blunderbuss = new WM_ItemBlunderbuss(j, EnumToolMaterial.IRON, WM_EnumWeapon.MUSKET).d(37).a("blunderbuss");
      j = WM_WeaponStorage.getWeapon("blunder-ironpart").getItemID();
      blunder_iron_part = new WM_Item(j).d(38).a("blunder_iron_part");
      j = WM_WeaponStorage.getWeapon("shot").getItemID();
      blunderShot = new WM_Item(j).d(39).a("blunderShot");
    }
    
    if ((WM_WeaponStorage.isEnabled("musket")) || (WM_WeaponStorage.isEnabled("blunderbuss")))
    {
      j = WM_WeaponStorage.getWeapon("gun-stock").getItemID();
      gunStock = new WM_Item(j).d(6).a("gunStock");
    }
    
    localWM_WeaponStorage = WM_WeaponStorage.getWeapon("dummy");
    
    if (localWM_WeaponStorage.isEnabled)
    {
      j = localWM_WeaponStorage.getItemID();
      dummy = new WM_ItemDummy(j).d(9).a("dummy");
    }
  }
  
  private void registerWeapons()
  {
    WM_WeaponStorage localWM_WeaponStorage = WM_WeaponStorage.getWeapon("spear");
    
    if (localWM_WeaponStorage.isEnabled)
    {
      ModLoader.addRecipe(new ItemStack(spearWood, 1), new Object[] { "  #", " X ", "X  ", Character.valueOf('X'), Item.STICK, Character.valueOf('#'), Block.WOOD });
      


      ModLoader.addRecipe(new ItemStack(spearStone, 1), new Object[] { "  #", " X ", "X  ", Character.valueOf('X'), Item.STICK, Character.valueOf('#'), Block.COBBLESTONE });
      


      ModLoader.addRecipe(new ItemStack(spearSteel, 1), new Object[] { "  #", " X ", "X  ", Character.valueOf('X'), Item.STICK, Character.valueOf('#'), Item.IRON_INGOT });
      


      ModLoader.addRecipe(new ItemStack(spearDiamond, 1), new Object[] { "  #", " X ", "X  ", Character.valueOf('X'), Item.STICK, Character.valueOf('#'), Item.DIAMOND });
      


      ModLoader.addRecipe(new ItemStack(spearGold, 1), new Object[] { "  #", " X ", "X  ", Character.valueOf('X'), Item.STICK, Character.valueOf('#'), Item.GOLD_INGOT });
      


      ModLoader.registerEntityID(WM_EntitySpear.class, "Spear", localWM_WeaponStorage.entityID);
      MinecraftForge.registerEntity(WM_EntitySpear.class, this, localWM_WeaponStorage.networkID, 16, 5, true);
    }
    
    if (WM_WeaponStorage.isEnabled("halberd"))
    {
      ModLoader.addRecipe(new ItemStack(halberdWood, 1), new Object[] { " ##", " X#", "X  ", Character.valueOf('X'), Item.STICK, Character.valueOf('#'), Block.WOOD });
      


      ModLoader.addRecipe(new ItemStack(halberdStone, 1), new Object[] { " ##", " X#", "X  ", Character.valueOf('X'), Item.STICK, Character.valueOf('#'), Block.COBBLESTONE });
      


      ModLoader.addRecipe(new ItemStack(halberdSteel, 1), new Object[] { " ##", " X#", "X  ", Character.valueOf('X'), Item.STICK, Character.valueOf('#'), Item.IRON_INGOT });
      


      ModLoader.addRecipe(new ItemStack(halberdDiamond, 1), new Object[] { " ##", " X#", "X  ", Character.valueOf('X'), Item.STICK, Character.valueOf('#'), Item.DIAMOND });
      


      ModLoader.addRecipe(new ItemStack(halberdGold, 1), new Object[] { " ##", " X#", "X  ", Character.valueOf('X'), Item.STICK, Character.valueOf('#'), Item.GOLD_INGOT });
    }
    



    localWM_WeaponStorage = WM_WeaponStorage.getWeapon("knife");
    
    if (localWM_WeaponStorage.isEnabled)
    {
      ModLoader.addRecipe(new ItemStack(knifeWood, 1), new Object[] { "#X", Character.valueOf('X'), Item.STICK, Character.valueOf('#'), Block.WOOD });
      


      ModLoader.addRecipe(new ItemStack(knifeStone, 1), new Object[] { "#X", Character.valueOf('X'), Item.STICK, Character.valueOf('#'), Block.COBBLESTONE });
      


      ModLoader.addRecipe(new ItemStack(knifeSteel, 1), new Object[] { "#X", Character.valueOf('X'), Item.STICK, Character.valueOf('#'), Item.IRON_INGOT });
      


      ModLoader.addRecipe(new ItemStack(knifeDiamond, 1), new Object[] { "#X", Character.valueOf('X'), Item.STICK, Character.valueOf('#'), Item.DIAMOND });
      


      ModLoader.addRecipe(new ItemStack(knifeGold, 1), new Object[] { "#X", Character.valueOf('X'), Item.STICK, Character.valueOf('#'), Item.GOLD_INGOT });
      


      ModLoader.registerEntityID(WM_EntityKnife.class, "Knife", localWM_WeaponStorage.entityID);
      MinecraftForge.registerEntity(WM_EntityKnife.class, this, localWM_WeaponStorage.networkID, 16, 5, true);
    }
    
    localWM_WeaponStorage = WM_WeaponStorage.getWeapon("javelin");
    
    if (localWM_WeaponStorage.isEnabled)
    {
      ModLoader.addRecipe(new ItemStack(javelin, 2), new Object[] { "  #", " X ", "X  ", Character.valueOf('X'), Item.STICK, Character.valueOf('#'), Item.FLINT });
      


      ModLoader.registerEntityID(WM_EntityJavelin.class, "Javelin", localWM_WeaponStorage.entityID);
      MinecraftForge.registerEntity(WM_EntityJavelin.class, this, localWM_WeaponStorage.networkID, 16, 5, true);
    }
    
    if (WM_WeaponStorage.isEnabled("musket"))
    {
      if (WM_WeaponStorage.isEnabled("knife"))
      {
        ModLoader.addRecipe(new ItemStack(bayonet, 1), new Object[] { "#X", Character.valueOf('X'), knifeSteel, Character.valueOf('#'), musket });
      }
      



      ModLoader.addRecipe(new ItemStack(musketBullet, 8), new Object[] { "X", "#", "O", Character.valueOf('X'), Item.IRON_INGOT, Character.valueOf('#'), Item.SULPHUR, Character.valueOf('O'), Item.PAPER });
      


      ModLoader.addRecipe(new ItemStack(musket, 1), new Object[] { "#", "X", Character.valueOf('X'), gunStock, Character.valueOf('#'), musket_iron_part });
      


      ModLoader.addRecipe(new ItemStack(musket_iron_part, 1), new Object[] { "XX#", "  X", Character.valueOf('X'), Item.IRON_INGOT, Character.valueOf('#'), Item.FLINT_AND_STEEL });
      


      ModLoader.registerEntityID(WM_EntityMusketBullet.class, "Bullet", WM_WeaponStorage.getWeapon("bullet").entityID);
      MinecraftForge.registerEntity(WM_EntityMusketBullet.class, this, WM_WeaponStorage.getWeapon("bullet").networkID, 16, 5, true);
    }
    
    if (WM_WeaponStorage.isEnabled("battleaxe"))
    {
      ModLoader.addRecipe(new ItemStack(battleaxeWood, 1), new Object[] { "###", "#X#", " X ", Character.valueOf('X'), Item.STICK, Character.valueOf('#'), Block.WOOD });
      


      ModLoader.addRecipe(new ItemStack(battleaxeStone, 1), new Object[] { "###", "#X#", " X ", Character.valueOf('X'), Item.STICK, Character.valueOf('#'), Block.COBBLESTONE });
      


      ModLoader.addRecipe(new ItemStack(battleaxeSteel, 1), new Object[] { "###", "#X#", " X ", Character.valueOf('X'), Item.STICK, Character.valueOf('#'), Item.IRON_INGOT });
      


      ModLoader.addRecipe(new ItemStack(battleaxeDiamond, 1), new Object[] { "###", "#X#", " X ", Character.valueOf('X'), Item.STICK, Character.valueOf('#'), Item.DIAMOND });
      


      ModLoader.addRecipe(new ItemStack(battleaxeGold, 1), new Object[] { "###", "#X#", " X ", Character.valueOf('X'), Item.STICK, Character.valueOf('#'), Item.GOLD_INGOT });
    }
    



    if (WM_WeaponStorage.isEnabled("warhammer"))
    {
      ModLoader.addRecipe(new ItemStack(warhammerWood, 1), new Object[] { "#X#", "#X#", " X ", Character.valueOf('X'), Item.STICK, Character.valueOf('#'), Block.WOOD });
      


      ModLoader.addRecipe(new ItemStack(warhammerStone, 1), new Object[] { "#X#", "#X#", " X ", Character.valueOf('X'), Item.STICK, Character.valueOf('#'), Block.COBBLESTONE });
      


      ModLoader.addRecipe(new ItemStack(warhammerSteel, 1), new Object[] { "#X#", "#X#", " X ", Character.valueOf('X'), Item.STICK, Character.valueOf('#'), Item.IRON_INGOT });
      


      ModLoader.addRecipe(new ItemStack(warhammerDiamond, 1), new Object[] { "#X#", "#X#", " X ", Character.valueOf('X'), Item.STICK, Character.valueOf('#'), Item.DIAMOND });
      


      ModLoader.addRecipe(new ItemStack(warhammerGold, 1), new Object[] { "#X#", "#X#", " X ", Character.valueOf('X'), Item.STICK, Character.valueOf('#'), Item.GOLD_INGOT });
    }
    



    if (WM_WeaponStorage.isEnabled("crossbow"))
    {
      ModLoader.addRecipe(new ItemStack(crossbow, 1), new Object[] { "O##", "#X ", "# X", Character.valueOf('X'), Block.WOOD, Character.valueOf('#'), Item.IRON_INGOT, Character.valueOf('O'), Item.BOW });
      


      ModLoader.addRecipe(new ItemStack(bolt, 4), new Object[] { "#", "X", Character.valueOf('X'), Item.FEATHER, Character.valueOf('#'), Item.IRON_INGOT });
      


      ModLoader.registerEntityID(WM_EntityCrossbowBolt.class, "Bolt", WM_WeaponStorage.getWeapon("bolt").entityID);
      MinecraftForge.registerEntity(WM_EntityCrossbowBolt.class, this, WM_WeaponStorage.getWeapon("bolt").networkID, 8, 5, true);
    }
    
    if (WM_WeaponStorage.isEnabled("blowgun"))
    {
      ModLoader.addRecipe(new ItemStack(blowgun, 1), new Object[] { "X  ", " X ", "  X", Character.valueOf('X'), Item.SUGAR_CANE });
      


      ModLoader.addRecipe(new ItemStack(dart, 4), new Object[] { "#", "X", "O", Character.valueOf('X'), Block.CACTUS, Character.valueOf('#'), Item.STICK, Character.valueOf('O'), Item.FEATHER });
      


      ModLoader.registerEntityID(WM_EntityBlowgunDart.class, "Dart", WM_WeaponStorage.getWeapon("dart").entityID);
      MinecraftForge.registerEntity(WM_EntityBlowgunDart.class, this, WM_WeaponStorage.getWeapon("dart").networkID, 8, 5, true);
    }
    
    localWM_WeaponStorage = WM_WeaponStorage.getWeapon("dynamite");
    
    if (localWM_WeaponStorage.isEnabled)
    {
      ModLoader.addRecipe(new ItemStack(dynamite, 2), new Object[] { "#", "X", "X", Character.valueOf('X'), Item.SULPHUR, Character.valueOf('#'), Item.STRING });
      


      ModLoader.registerEntityID(WM_EntityDynamite.class, "Dynamite", localWM_WeaponStorage.entityID);
      MinecraftForge.registerEntity(WM_EntityDynamite.class, this, localWM_WeaponStorage.networkID, 16, 5, true);
    }
    
    localWM_WeaponStorage = WM_WeaponStorage.getWeapon("flail");
    
    if (localWM_WeaponStorage.isEnabled)
    {
      ModLoader.addRecipe(new ItemStack(flailWood, 1), new Object[] { "  O", " XO", "X #", Character.valueOf('X'), Item.STICK, Character.valueOf('O'), Item.STRING, Character.valueOf('#'), Block.WOOD });
      


      ModLoader.addRecipe(new ItemStack(flailStone, 1), new Object[] { "  O", " XO", "X #", Character.valueOf('X'), Item.STICK, Character.valueOf('O'), Item.STRING, Character.valueOf('#'), Block.COBBLESTONE });
      


      ModLoader.addRecipe(new ItemStack(flailSteel, 1), new Object[] { "  O", " XO", "X #", Character.valueOf('X'), Item.STICK, Character.valueOf('O'), Item.STRING, Character.valueOf('#'), Item.IRON_INGOT });
      


      ModLoader.addRecipe(new ItemStack(flailDiamond, 1), new Object[] { "  O", " XO", "X #", Character.valueOf('X'), Item.STICK, Character.valueOf('O'), Item.STRING, Character.valueOf('#'), Item.DIAMOND });
      


      ModLoader.addRecipe(new ItemStack(flailGold, 1), new Object[] { "  O", " XO", "X #", Character.valueOf('X'), Item.STICK, Character.valueOf('O'), Item.STRING, Character.valueOf('#'), Item.GOLD_INGOT });
      


      ModLoader.registerEntityID(WM_EntityFlail.class, "Flail", localWM_WeaponStorage.entityID);
      MinecraftForge.registerEntity(WM_EntityFlail.class, this, localWM_WeaponStorage.networkID, 16, 2, true);
    }
    
    if (WM_WeaponStorage.isEnabled("firerod"))
    {
      ModLoader.addRecipe(new ItemStack(fireRod, 1), new Object[] { "#  ", " X ", "  X", Character.valueOf('X'), Item.STICK, Character.valueOf('#'), Block.TORCH });
    }
    



    localWM_WeaponStorage = WM_WeaponStorage.getWeapon("cannon");
    
    if (localWM_WeaponStorage.isEnabled)
    {
      ModLoader.addRecipe(new ItemStack(cannon, 1), new Object[] { "XX#", "  X", "XXO", Character.valueOf('X'), Item.IRON_INGOT, Character.valueOf('#'), Item.FLINT_AND_STEEL, Character.valueOf('O'), Block.LOG });
      


      ModLoader.registerEntityID(WM_EntityCannon.class, "Cannon", localWM_WeaponStorage.entityID);
      MinecraftForge.registerEntity(WM_EntityCannon.class, this, localWM_WeaponStorage.networkID, 32, 16, false);
      ModLoader.addRecipe(new ItemStack(cannonBall, 4), new Object[] { " X ", "XXX", " X ", Character.valueOf('X'), Block.STONE });
      


      ModLoader.registerEntityID(WM_EntityCannonBall.class, "CannonBall", WM_WeaponStorage.getWeapon("cannonball").entityID);
      MinecraftForge.registerEntity(WM_EntityCannonBall.class, this, WM_WeaponStorage.getWeapon("cannonball").networkID, 32, 5, true);
    }
    
    localWM_WeaponStorage = WM_WeaponStorage.getWeapon("blunderbuss");
    
    if (localWM_WeaponStorage.isEnabled)
    {
      ModLoader.addRecipe(new ItemStack(blunderShot, 8), new Object[] { "X", "#", "O", Character.valueOf('X'), Block.GRAVEL, Character.valueOf('#'), Item.SULPHUR, Character.valueOf('O'), Item.PAPER });
      


      ModLoader.addRecipe(new ItemStack(blunderbuss, 1), new Object[] { "#", "X", Character.valueOf('X'), gunStock, Character.valueOf('#'), blunder_iron_part });
      


      ModLoader.addRecipe(new ItemStack(blunder_iron_part, 1), new Object[] { "X  ", " X#", "X X", Character.valueOf('X'), Item.IRON_INGOT, Character.valueOf('#'), Item.FLINT_AND_STEEL });
      


      ModLoader.registerEntityID(WM_EntityBlunderShot.class, "Shot", WM_WeaponStorage.getWeapon("shot").entityID);
      MinecraftForge.registerEntity(WM_EntityBlunderShot.class, this, WM_WeaponStorage.getWeapon("shot").networkID, 8, 5, true);
    }
    
    if ((WM_WeaponStorage.isEnabled("musket")) || (WM_WeaponStorage.isEnabled("blunderbuss")))
    {
      ModLoader.addRecipe(new ItemStack(gunStock, 1), new Object[] { "XX#", Character.valueOf('X'), Item.STICK, Character.valueOf('#'), Block.WOOD });
    }
    



    localWM_WeaponStorage = WM_WeaponStorage.getWeapon("dummy");
    
    if (localWM_WeaponStorage.isEnabled)
    {
      ModLoader.addRecipe(new ItemStack(dummy, 1), new Object[] { " U ", "XOX", " # ", Character.valueOf('#'), Item.STICK, Character.valueOf('X'), Item.WHEAT, Character.valueOf('O'), Item.LEATHER_CHESTPLATE, Character.valueOf('U'), Block.WOOL });
      



      ModLoader.registerEntityID(WM_EntityDummy.class, "Dummy", localWM_WeaponStorage.entityID);
      MinecraftForge.registerEntity(WM_EntityDummy.class, this, localWM_WeaponStorage.networkID, 32, 16, false);
    }
  }
  
  public void onPacket250Received(EntityHuman paramEntityHuman, Packet250CustomPayload paramPacket250CustomPayload)
  {
    if ((paramPacket250CustomPayload.tag.equals("wpnmodCannon")) && ((paramEntityHuman.vehicle instanceof WM_EntityCannon)))
    {
      float f1 = Float.intBitsToFloat(paramPacket250CustomPayload.data[0] & 0xFF | (paramPacket250CustomPayload.data[1] & 0xFF) << 8 | (paramPacket250CustomPayload.data[2] & 0xFF) << 16 | (paramPacket250CustomPayload.data[3] & 0xFF) << 24);
      float f2 = Float.intBitsToFloat(paramPacket250CustomPayload.data[4] & 0xFF | (paramPacket250CustomPayload.data[5] & 0xFF) << 8 | (paramPacket250CustomPayload.data[6] & 0xFF) << 16 | (paramPacket250CustomPayload.data[7] & 0xFF) << 24);
      paramEntityHuman.c(f1, f2);
      WM_EntityCannon localWM_EntityCannon = (WM_EntityCannon)paramEntityHuman.vehicle;
      localWM_EntityCannon.shootCannon();
    }
  }
  
  public boolean dispenseEntity(World paramWorld, double paramDouble1, double paramDouble2, double paramDouble3, int paramInt1, int paramInt2, ItemStack paramItemStack) {
    Object localObject1;
    if (paramItemStack.id == musketBullet.id)
    {
      localObject1 = new WM_EntityMusketBullet(paramWorld, paramDouble1, paramDouble2, paramDouble3);
      ((WM_EntityMusketBullet)localObject1).setArrowHeading(paramInt1, 0.0D, paramInt2, 5.0F, 3.0F);
      paramWorld.addEntity((Entity)localObject1);
      paramWorld.makeSound(paramDouble1, paramDouble2, paramDouble3, "random.explode", 3.0F, 1.0F / (this.rand.nextFloat() * 0.4F + 0.7F));
      paramWorld.makeSound(paramDouble1, paramDouble2, paramDouble3, "ambient.weather.thunder", 3.0F, 1.0F / (this.rand.nextFloat() * 0.4F + 0.4F));
    }
    else if (paramItemStack.id == javelin.id)
    {
      localObject1 = new WM_EntityJavelin(paramWorld, paramDouble1, paramDouble2, paramDouble3);
      ((WM_EntityJavelin)localObject1).setArrowHeading(paramInt1, 0.1D, paramInt2, 1.1F, 4.0F);
      paramWorld.addEntity((Entity)localObject1);
      paramWorld.makeSound(paramDouble1, paramDouble2, paramDouble3, "random.bow", 1.0F, 1.2F);
    }
    else if (paramItemStack.id == bolt.id)
    {
      localObject1 = new WM_EntityCrossbowBolt(paramWorld, paramDouble1, paramDouble2, paramDouble3);
      ((WM_EntityCrossbowBolt)localObject1).setArrowHeading(paramInt1, 0.0D, paramInt2, 5.0F, 4.0F);
      paramWorld.addEntity((Entity)localObject1);
      paramWorld.makeSound(paramDouble1, paramDouble2, paramDouble3, "random.bow", 1.0F, 1.2F);
    }
    else if (paramItemStack.id == dart.id)
    {
      localObject1 = new WM_EntityBlowgunDart(paramWorld, paramDouble1, paramDouble2, paramDouble3);
      ((WM_EntityBlowgunDart)localObject1).setArrowHeading(paramInt1, 0.0D, paramInt2, 5.0F, 4.0F);
      paramWorld.addEntity((Entity)localObject1);
      paramWorld.makeSound(paramDouble1, paramDouble2, paramDouble3, "random.bow", 1.0F, 1.2F);
    }
    else if (paramItemStack.id == dynamite.id)
    {
      localObject1 = new WM_EntityDynamite(paramWorld, paramDouble1, paramDouble2, paramDouble3);
      ((WM_EntityDynamite)localObject1).setArrowHeading(paramInt1, 0.1D, paramInt2, 1.0F, 4.0F);
      paramWorld.addEntity((Entity)localObject1);
      paramWorld.makeSound(paramDouble1, paramDouble2, paramDouble3, "random.fuse", 1.0F, 1.2F);
    }
    else if (paramItemStack.id == blunderShot.id)
    {
      WM_EntityBlunderShot.fireFromDispenser(paramWorld, paramDouble1, paramDouble2, paramDouble3, paramInt1, paramInt2);
      paramWorld.makeSound(paramDouble1, paramDouble2, paramDouble3, "random.explode", 3.0F, 1.0F / (this.rand.nextFloat() * 0.4F + 1.0F));
    }
    else if ((paramItemStack.id == cannonBall.id) || (paramItemStack.id == Item.SULPHUR.id))
    {
      int i = 0;
      TileEntity localTileEntity = paramWorld.getTileEntity(MathHelper.floor(paramDouble1 - paramInt1), MathHelper.floor(paramDouble2), MathHelper.floor(paramDouble3 - paramInt2));
      
      if ((localTileEntity instanceof TileEntityDispenser))
      {
        Object localObject2 = (TileEntityDispenser)localTileEntity;
        int j = 0;
        
        if (paramItemStack.id == Item.SULPHUR.id)
        {
          j = cannonBall.id;
        }
        else if (paramItemStack.id == cannonBall.id)
        {
          j = Item.SULPHUR.id;
        }
        
        int k = 0;
        


        while (k < ((TileEntityDispenser)localObject2).getSize())
        {



          ItemStack localItemStack = ((TileEntityDispenser)localObject2).getItem(k);
          
          if ((localItemStack != null) && (localItemStack.id == j))
          {
            ((TileEntityDispenser)localObject2).splitStack(k, 1);
            i = 1;
            break;
          }
          
          k++;
        }
      }
      

      if (i == 0)
      {
        return false;
      }
      
      Object localObject2 = new WM_EntityCannonBall(paramWorld, paramDouble1, paramDouble2, paramDouble3);
      ((WM_EntityCannonBall)localObject2).setArrowHeading(paramInt1, 0.1D, paramInt2, 2.0F, 2.0F);
      paramWorld.addEntity((Entity)localObject2);
      paramWorld.makeSound(paramDouble1, paramDouble2, paramDouble3, "random.explode", 8.0F, 1.0F / (this.rand.nextFloat() * 0.8F + 0.9F));
      paramWorld.makeSound(paramDouble1, paramDouble2, paramDouble3, "ambient.weather.thunder", 8.0F, 1.0F / (this.rand.nextFloat() * 0.4F + 0.6F));
      paramWorld.a("flame", paramDouble1 + paramInt1, paramDouble2, paramDouble3 + paramInt2, 0.0D, 0.0D, 0.0D);
    }
    else
    {
      return false;
    }
    
    return true;
  }
  
  public static File getServerLocation()
  {
    return new File(System.getProperty("user.dir"));
  }
  
  public boolean clientSideRequired()
  {
    return false;
  }
  
  public boolean serverSideRequired()
  {
    return false;
  }
}
