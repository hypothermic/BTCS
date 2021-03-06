package net.minecraft.server;

import org.bukkit.event.entity.EntityTargetEvent; // CraftBukkit

public abstract class PathfinderGoalTarget extends PathfinderGoal {

    protected EntityLiving c;
    protected float d;
    protected boolean e;
    private boolean a;
    private int b;
    private int f;
    private int g;

    public PathfinderGoalTarget(EntityLiving entityliving, float f, boolean flag) {
        this(entityliving, f, flag, false);
    }

    public PathfinderGoalTarget(EntityLiving entityliving, float f, boolean flag, boolean flag1) {
        this.b = 0;
        this.f = 0;
        this.g = 0;
        this.c = entityliving;
        this.d = f;
        this.e = flag;
        this.a = flag1;
    }

    public boolean b() {
        EntityLiving entityliving = this.c.at();

        if (entityliving == null) {
            return false;
        } else if (!entityliving.isAlive()) {
            return false;
        } else if (this.c.j(entityliving) > (double) (this.d * this.d)) {
            return false;
        } else {
            if (this.e) {
                if (!this.c.am().canSee(entityliving)) {
                    if (++this.g > 60) {
                        return false;
                    }
                } else {
                    this.g = 0;
                }
            }

            return true;
        }
    }

    public void c() {
        this.b = 0;
        this.f = 0;
        this.g = 0;
    }

    public void d() {
        this.c.b((EntityLiving) null);
    }

    protected boolean a(EntityLiving entityliving, boolean flag) {
        if (entityliving == null) {
            return false;
        } else if (entityliving == this.c) {
            return false;
        } else if (!entityliving.isAlive()) {
            return false;
        } else if (entityliving.boundingBox.e > this.c.boundingBox.b && entityliving.boundingBox.b < this.c.boundingBox.e) {
            if (!this.c.a(entityliving.getClass())) {
                return false;
            } else {
                if (this.c instanceof EntityTameableAnimal && ((EntityTameableAnimal) this.c).isTamed()) {
                    if (entityliving instanceof EntityTameableAnimal && ((EntityTameableAnimal) entityliving).isTamed()) {
                        return false;
                    }

                    if (entityliving == ((EntityTameableAnimal) this.c).getOwner()) {
                        return false;
                    }
                } else if (entityliving instanceof EntityHuman && !flag && ((EntityHuman) entityliving).abilities.isInvulnerable) {
                    return false;
                }

                if (!this.c.e(MathHelper.floor(entityliving.locX), MathHelper.floor(entityliving.locY), MathHelper.floor(entityliving.locZ))) {
                    return false;
                } else if (this.e && !this.c.am().canSee(entityliving)) {
                    return false;
                } else {
                    if (this.a) {
                        if (--this.f <= 0) {
                            this.b = 0;
                        }

                        if (this.b == 0) {
                            this.b = this.a(entityliving) ? 1 : 2;
                        }

                        if (this.b == 2) {
                            return false;
                        }
                    }

                    // CraftBukkit start - Check all the different target goals for the reason, default to RANDOM_TARGET
                    EntityTargetEvent.TargetReason reason = EntityTargetEvent.TargetReason.RANDOM_TARGET;

                    if (this instanceof PathfinderGoalDefendVillage) {
                        reason = EntityTargetEvent.TargetReason.DEFEND_VILLAGE;
                    } else if (this instanceof PathfinderGoalHurtByTarget) {
                        reason = EntityTargetEvent.TargetReason.TARGET_ATTACKED_ENTITY;
                    } else if (this instanceof PathfinderGoalNearestAttackableTarget) {
                        if (entityliving instanceof EntityHuman) {
                            reason = EntityTargetEvent.TargetReason.CLOSEST_PLAYER;
                        }
                    } else if (this instanceof PathfinderGoalOwnerHurtByTarget) {
                        reason = EntityTargetEvent.TargetReason.TARGET_ATTACKED_OWNER;
                    } else if (this instanceof PathfinderGoalOwnerHurtTarget) {
                        reason = EntityTargetEvent.TargetReason.OWNER_ATTACKED_TARGET;
                    }

                    org.bukkit.event.entity.EntityTargetLivingEntityEvent event = org.bukkit.craftbukkit.event.CraftEventFactory.callEntityTargetLivingEvent(c, entityliving, reason);
                    if (event.isCancelled() || event.getTarget() == null) {
                        return false;
                    } else if (entityliving.getBukkitEntity() != event.getTarget()) {
                        this.c.b((EntityLiving) ((org.bukkit.craftbukkit.entity.CraftEntity) event.getTarget()).getHandle());
                    }
                    // CraftBukkit end

                    return true;
                }
            }
        } else {
            return false;
        }
    }

    private boolean a(EntityLiving entityliving) {
        this.f = 10 + this.c.an().nextInt(5);
        PathEntity pathentity = this.c.al().a(entityliving);

        if (pathentity == null) {
            return false;
        } else {
            PathPoint pathpoint = pathentity.c();

            if (pathpoint == null) {
                return false;
            } else {
                int i = pathpoint.a - MathHelper.floor(entityliving.locX);
                int j = pathpoint.c - MathHelper.floor(entityliving.locZ);

                return (double) (i * i + j * j) <= 2.25D;
            }
        }
    }
}
