package net.minecraft.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.craftbukkit.entity.CraftHumanEntity;
import org.bukkit.entity.HumanEntity;
// CraftBukkit start
import org.bukkit.inventory.InventoryHolder;
// CraftBukkit end

public class TileEntity implements net.minecraft.src.TileEntity {

    private static Map a = new HashMap();
    private static Map b = new HashMap();
    public World world;
    public int x;
    public int y;
    public int z;
    protected boolean o;
    /** Block metadata */
    public int p = -1;
    public Block q;

    public TileEntity() {} // BTCS

    private static void a(Class oclass, String s) {
        if (b.containsKey(s)) {
            throw new IllegalArgumentException("Duplicate id: " + s);
        } else {
            a.put(s, oclass);
            b.put(oclass, s);
        }
    }

    public void a(NBTTagCompound nbttagcompound) {
        this.x = nbttagcompound.getInt("x");
        this.y = nbttagcompound.getInt("y");
        this.z = nbttagcompound.getInt("z");
    }

    public void b(NBTTagCompound nbttagcompound) {
        String s = (String) b.get(this.getClass());

        if (s == null) {
            throw new RuntimeException(this.getClass() + " is missing a mapping! This is a bug!");
        } else {
            nbttagcompound.setString("id", s);
            nbttagcompound.setInt("x", this.x);
            nbttagcompound.setInt("y", this.y);
            nbttagcompound.setInt("z", this.z);
        }
    }

    public void q_() {}

    public static TileEntity c(NBTTagCompound nbttagcompound) {
        TileEntity tileentity = null;

        try {
            Class oclass = (Class) a.get(nbttagcompound.getString("id"));

            if (oclass != null) {
                tileentity = (TileEntity) oclass.newInstance();
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        if (tileentity != null) {
            tileentity.a(nbttagcompound);
        } else {
            System.out.println("Skipping TileEntity with id " + nbttagcompound.getString("id"));
        }

        return tileentity;
    }

    public int k() {
        if (this.p == -1) {
            this.p = this.world.getData(this.x, this.y, this.z);
        }

        return this.p;
    }

    public void update() {
        if (this.world != null) {
            this.p = this.world.getData(this.x, this.y, this.z);
            this.world.b(this.x, this.y, this.z, this);
        }
    }

    public Packet d() {
        return null;
    }

    /** isInvalid() */
    public boolean l() {
        return this.o;
    }

    /** invalidate() */
    public void j() {
        this.o = true;
    }

    /** validate() */
    public void m() {
        this.o = false;
    }

    public void b(int i, int j) {}

    public void h() {
        this.q = null;
        this.p = -1;
    }

    static {
        a(TileEntityFurnace.class, "Furnace");
        a(TileEntityChest.class, "Chest");
        a(TileEntityRecordPlayer.class, "RecordPlayer");
        a(TileEntityDispenser.class, "Trap");
        a(TileEntitySign.class, "Sign");
        a(TileEntityMobSpawner.class, "MobSpawner");
        a(TileEntityNote.class, "Music");
        a(TileEntityPiston.class, "Piston");
        a(TileEntityBrewingStand.class, "Cauldron");
        a(TileEntityEnchantTable.class, "EnchantTable");
        a(TileEntityEnderPortal.class, "Airportal");
    }

    // CraftBukkit start
    public InventoryHolder getOwner() {
        org.bukkit.block.BlockState state = world.getWorld().getBlockAt(x, y, z).getState();
        if(state instanceof InventoryHolder) return (InventoryHolder) state;
        return null;
    }
    // CraftBukkit end
    
    // BTCS start
    private List<HumanEntity> transaction = new ArrayList(2);
    
    public void onOpen(CraftHumanEntity who)
    {
      this.transaction.add(who);
    }
    
    public void onClose(CraftHumanEntity who) {
      this.transaction.remove(who);
    }
    
    public List<HumanEntity> getViewers() {
      return this.transaction;
    }
    
    public static void addNewTileEntityMapping(Class<? extends TileEntity> tileEntityClass, String id) {
      a(tileEntityClass, id);
    }

    public boolean canUpdate()
    {
      return true;
    }
    
    public void onDataPacket(NetworkManager net, Packet132TileEntityData pkt) {}
    
    public void onChunkUnload() {}
    // BTCS end
}
