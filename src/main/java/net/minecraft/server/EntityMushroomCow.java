package net.minecraft.server;

import java.util.ArrayList; // BTCS

import org.bukkit.event.player.PlayerShearEntityEvent; // CraftBukkit

import forge.IShearable; // BTCS

public class EntityMushroomCow extends EntityCow implements IShearable { // BTCS: implemets IShearable

    public EntityMushroomCow(World world) {
        super(world);
        this.texture = "/mob/redcow.png";
        this.b(0.9F, 1.3F);
    }

    public boolean b(EntityHuman entityhuman) {
        ItemStack itemstack = entityhuman.inventory.getItemInHand();

        if (itemstack != null && itemstack.id == Item.BOWL.id && this.getAge() >= 0) {
            if (itemstack.count == 1) {
                entityhuman.inventory.setItem(entityhuman.inventory.itemInHandIndex, new ItemStack(Item.MUSHROOM_SOUP));
                return true;
            }

            if (entityhuman.inventory.pickup(new ItemStack(Item.MUSHROOM_SOUP)) && !entityhuman.abilities.canInstantlyBuild) {
                entityhuman.inventory.splitStack(entityhuman.inventory.itemInHandIndex, 1);
                return true;
            }
        }

        // BTCS start
        /*if (itemstack != null && itemstack.id == Item.SHEARS.id && this.getAge() >= 0) {
            // CraftBukkit start
            PlayerShearEntityEvent event = new PlayerShearEntityEvent((org.bukkit.entity.Player) entityhuman.getBukkitEntity(), this.getBukkitEntity());
            this.world.getServer().getPluginManager().callEvent(event);

            if (event.isCancelled()) {
                return false;
            }
            // CraftBukkit end

            this.die();
            this.world.a("largeexplode", this.locX, this.locY + (double) (this.length / 2.0F), this.locZ, 0.0D, 0.0D, 0.0D);
            if (!this.world.isStatic) {
                EntityCow entitycow = new EntityCow(this.world);

                entitycow.setPositionRotation(this.locX, this.locY, this.locZ, this.yaw, this.pitch);
                entitycow.setHealth(this.getHealth());
                entitycow.V = this.V;
                this.world.addEntity(entitycow);

                for (int i = 0; i < 5; ++i) {
                    this.world.addEntity(new EntityItem(this.world, this.locX, this.locY + (double) this.length, this.locZ, new ItemStack(Block.RED_MUSHROOM)));
                }
            }

            return true;
        } else {
            return super.b(entityhuman);
        }*/
        return super.b(entityhuman);
        // BTCS end
    }

    public EntityAnimal createChild(EntityAnimal entityanimal) {
        return new EntityMushroomCow(this.world);
    }
    
    @Override
    public boolean isShearable(ItemStack item, World world, int X, int Y, int Z) 
    {
        return getAge() >= 0;
    }
    
    @Override
    public ArrayList<ItemStack> onSheared(ItemStack item, World world, int X, int Y, int Z, int fortune) 
    {
        die();
        EntityCow entitycow = new EntityCow(world);
        entitycow.setPositionRotation(this.locX, this.locY, this.locZ, this.yaw, this.pitch);
        entitycow.setHealth(getHealth());
        entitycow.V = this.V;
        world.addEntity(entitycow);
        this.world.a("largeexplode", this.locX, this.locY + (double)(height / 2.0F), this.locZ, 0.0D, 0.0D, 0.0D);
        
        ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
        for (int x = 0; x < 5; x++) {
        	ret.add(new ItemStack(Block.RED_MUSHROOM));
        }
        return ret;
    }
}
