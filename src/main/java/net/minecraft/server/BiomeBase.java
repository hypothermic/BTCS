package net.minecraft.server;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class BiomeBase implements net.minecraft.src.BiomeGenBase
{
  public static final BiomeBase[] biomes = new BiomeBase['Ā'];
  public static final BiomeBase OCEAN = new BiomeOcean(0).b(112).a("Ocean").b(-1.0F, 0.4F);
  public static final BiomeBase PLAINS = new BiomePlains(1).b(9286496).a("Plains").a(0.8F, 0.4F);
  public static final BiomeBase DESERT = new BiomeDesert(2).b(16421912).a("Desert").j().a(2.0F, 0.0F).b(0.1F, 0.2F);
  public static final BiomeBase EXTREME_HILLS = new BiomeBigHills(3).b(6316128).a("Extreme Hills").b(0.2F, 1.3F).a(0.2F, 0.3F);
  public static final BiomeBase FOREST = new BiomeForest(4).b(353825).a("Forest").a(5159473).a(0.7F, 0.8F);
  public static final BiomeBase TAIGA = new BiomeTaiga(5).b(747097).a("Taiga").a(5159473).b().a(0.05F, 0.8F).b(0.1F, 0.4F);
  public static final BiomeBase SWAMPLAND = new BiomeSwamp(6).b(522674).a("Swampland").a(9154376).b(-0.2F, 0.1F).a(0.8F, 0.9F);
  public static final BiomeBase RIVER = new BiomeRiver(7).b(255).a("River").b(-0.5F, 0.0F);
  public static final BiomeBase HELL = new BiomeHell(8).b(16711680).a("Hell").j().a(2.0F, 0.0F);
  public static final BiomeBase SKY = new BiomeTheEnd(9).b(8421631).a("Sky").j();
  public static final BiomeBase FROZEN_OCEAN = new BiomeOcean(10).b(9474208).a("FrozenOcean").b().b(-1.0F, 0.5F).a(0.0F, 0.5F);
  public static final BiomeBase FROZEN_RIVER = new BiomeRiver(11).b(10526975).a("FrozenRiver").b().b(-0.5F, 0.0F).a(0.0F, 0.5F);
  public static final BiomeBase ICE_PLAINS = new BiomeIcePlains(12).b(16777215).a("Ice Plains").b().a(0.0F, 0.5F);
  public static final BiomeBase ICE_MOUNTAINS = new BiomeIcePlains(13).b(10526880).a("Ice Mountains").b().b(0.2F, 1.2F).a(0.0F, 0.5F);
  public static final BiomeBase MUSHROOM_ISLAND = new BiomeMushrooms(14).b(16711935).a("MushroomIsland").a(0.9F, 1.0F).b(0.2F, 1.0F);
  public static final BiomeBase MUSHROOM_SHORE = new BiomeMushrooms(15).b(10486015).a("MushroomIslandShore").a(0.9F, 1.0F).b(-1.0F, 0.1F);
  public static final BiomeBase BEACH = new BiomeBeach(16).b(16440917).a("Beach").a(0.8F, 0.4F).b(0.0F, 0.1F);
  public static final BiomeBase DESERT_HILLS = new BiomeDesert(17).b(13786898).a("DesertHills").j().a(2.0F, 0.0F).b(0.2F, 0.7F);
  public static final BiomeBase FOREST_HILLS = new BiomeForest(18).b(2250012).a("ForestHills").a(5159473).a(0.7F, 0.8F).b(0.2F, 0.6F);
  public static final BiomeBase TAIGA_HILLS = new BiomeTaiga(19).b(1456435).a("TaigaHills").b().a(5159473).a(0.05F, 0.8F).b(0.2F, 0.7F);
  public static final BiomeBase SMALL_MOUNTAINS = new BiomeBigHills(20).b(7501978).a("Extreme Hills Edge").b(0.2F, 0.8F).a(0.2F, 0.3F);
  public static final BiomeBase JUNGLE = new BiomeJungle(21).b(5470985).a("Jungle").a(5470985).a(1.2F, 0.9F).b(0.2F, 0.4F);
  public static final BiomeBase JUNGLE_HILLS = new BiomeJungle(22).b(2900485).a("JungleHills").a(5470985).a(1.2F, 0.9F).b(1.8F, 0.2F);
  public String y;
  public int z;
  public byte A;
  public byte B;
  public int C;
  public float D;
  public float E;
  public float F;
  public float G;
  public int H;
  public BiomeDecorator I;
  protected List J;
  protected List K;
  protected List L;
  private boolean R;
  private boolean S;
  public final int id;
  protected WorldGenTrees N;
  protected WorldGenBigTree O;
  protected WorldGenForest P;
  protected WorldGenSwampTree Q;
  
  protected BiomeBase(int i) {
    this.A = ((byte)Block.GRASS.id);
    this.B = ((byte)Block.DIRT.id);
    this.C = 5169201;
    this.D = 0.1F;
    this.E = 0.3F;
    this.F = 0.5F;
    this.G = 0.5F;
    this.H = 16777215;
    this.J = new ArrayList();
    this.K = new ArrayList();
    this.L = new ArrayList();
    this.S = true;
    this.N = new WorldGenTrees(false);
    this.O = new WorldGenBigTree(false);
    this.P = new WorldGenForest(false);
    this.Q = new WorldGenSwampTree();
    this.id = i;
    biomes[i] = this;
    this.I = a();
    this.K.add(new BiomeMeta(EntitySheep.class, 12, 4, 4));
    this.K.add(new BiomeMeta(EntityPig.class, 10, 4, 4));
    this.K.add(new BiomeMeta(EntityChicken.class, 10, 4, 4));
    this.K.add(new BiomeMeta(EntityCow.class, 8, 4, 4));
    this.J.add(new BiomeMeta(EntitySpider.class, 10, 4, 4));
    this.J.add(new BiomeMeta(EntityZombie.class, 10, 4, 4));
    this.J.add(new BiomeMeta(EntitySkeleton.class, 10, 4, 4));
    this.J.add(new BiomeMeta(EntityCreeper.class, 10, 4, 4));
    this.J.add(new BiomeMeta(EntitySlime.class, 10, 4, 4));
    this.J.add(new BiomeMeta(EntityEnderman.class, 1, 1, 4));
    this.L.add(new BiomeMeta(EntitySquid.class, 10, 4, 4));
  }
  
  protected BiomeDecorator a() {
    return new BiomeDecorator(this);
  }
  
  private BiomeBase a(float f, float f1) {
    if ((f > 0.1F) && (f < 0.2F)) {
      throw new IllegalArgumentException("Please avoid temperatures in the range 0.1 - 0.2 because of snow");
    }
    this.F = f;
    this.G = f1;
    return this;
  }
  
  private BiomeBase b(float f, float f1)
  {
    this.D = f;
    this.E = f1;
    return this;
  }
  
  private BiomeBase j() {
    this.S = false;
    return this;
  }
  
  public WorldGenerator a(Random random) {
    return random.nextInt(10) == 0 ? this.O : this.N;
  }
  
  public WorldGenerator b(Random random) {
    return new WorldGenGrass(Block.LONG_GRASS.id, 1);
  }
  
  protected BiomeBase b() {
    this.R = true;
    return this;
  }
  
  protected BiomeBase a(String s) {
    this.y = s;
    return this;
  }
  
  protected BiomeBase a(int i) {
    this.C = i;
    return this;
  }
  
  protected BiomeBase b(int i) {
    this.z = i;
    return this;
  }
  
  public List getMobs(EnumCreatureType enumcreaturetype) {
    return enumcreaturetype == EnumCreatureType.WATER_CREATURE ? this.L : enumcreaturetype == EnumCreatureType.CREATURE ? this.K : enumcreaturetype == EnumCreatureType.MONSTER ? this.J : null;
  }
  
  public boolean c() {
    return this.R;
  }
  
  public boolean d() {
    return this.R ? false : this.S;
  }
  
  public boolean e() {
    return this.G > 0.85F;
  }
  
  public float f() {
    return 0.1F;
  }
  
  public final int g() {
    return (int)(this.G * 65536.0F);
  }
  
  public final int h() {
    return (int)(this.F * 65536.0F);
  }
  
  public final float i() {
    return this.F;
  }
  
  public void a(World world, Random random, int i, int j) {
    this.I.a(world, random, i, j);
  }
}
