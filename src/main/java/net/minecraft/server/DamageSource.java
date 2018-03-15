package net.minecraft.server;






public class DamageSource
{
  public static DamageSource FIRE = new DamageSource("inFire").j();
  public static DamageSource BURN = new DamageSource("onFire").h().j();
  public static DamageSource LAVA = new DamageSource("lava").j();
  public static DamageSource STUCK = new DamageSource("inWall").h();
  public static DamageSource DROWN = new DamageSource("drown").h();
  public static DamageSource STARVE = new DamageSource("starve").h();
  public static DamageSource CACTUS = new DamageSource("cactus");
  public static DamageSource FALL = new DamageSource("fall").h();
  public static DamageSource OUT_OF_WORLD = new DamageSource("outOfWorld").h().i();
  public static DamageSource GENERIC = new DamageSource("generic").h();
  public static DamageSource EXPLOSION = new DamageSource("explosion");
  public static DamageSource MAGIC = new DamageSource("magic").h();
  
  public static DamageSource mobAttack(EntityLiving paramEntityLiving) {
    return new EntityDamageSource("mob", paramEntityLiving);
  }
  
  public static DamageSource playerAttack(EntityHuman paramEntityHuman) {
    return new EntityDamageSource("player", paramEntityHuman);
  }
  
  public static DamageSource arrow(EntityArrow paramEntityArrow, Entity paramEntity) {
    return new EntityDamageSourceIndirect("arrow", paramEntityArrow, paramEntity).d();
  }
  
  public static DamageSource fireball(EntityFireball paramEntityFireball, Entity paramEntity) {
    return new EntityDamageSourceIndirect("fireball", paramEntityFireball, paramEntity).j().d();
  }
  
  public static DamageSource projectile(Entity paramEntity1, Entity paramEntity2) {
    return new EntityDamageSourceIndirect("thrown", paramEntity1, paramEntity2).d();
  }
  
  public static DamageSource b(Entity paramEntity1, Entity paramEntity2) {
    return new EntityDamageSourceIndirect("indirectMagic", paramEntity1, paramEntity2).h();
  }
  
  private boolean a = false;
  private boolean o = false;
  
  private float p = 0.3F;
  private boolean q;
  private boolean r;
  public String translationIndex;
  
  public boolean c() { return this.r; }
  
  public DamageSource d()
  {
    this.r = true;
    return this;
  }
  
  public boolean ignoresArmor() {
    return this.a;
  }
  
  public float f() {
    return this.p;
  }
  
  public boolean ignoresInvulnerability() {
    return this.o;
  }
  

  protected DamageSource(String paramString)
  {
    this.translationIndex = paramString;
  }
  
  public Entity b()
  {
    return getEntity();
  }
  
  public Entity getEntity() {
    return null;
  }
  
  protected DamageSource h() {
    this.a = true;
    
    this.p = 0.0F;
    return this;
  }
  
  protected DamageSource i() {
    this.o = true;
    return this;
  }
  
  protected DamageSource j() {
    this.q = true;
    return this;
  }
  
  public String getLocalizedDeathMessage(EntityHuman paramEntityHuman) {
    return LocaleI18n.get("death." + this.translationIndex, new Object[] { paramEntityHuman.name });
  }
  
  public boolean k() {
    return this.q;
  }
  
  public String l() {
    return this.translationIndex;
  }
}
