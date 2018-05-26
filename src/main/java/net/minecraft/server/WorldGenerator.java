package net.minecraft.server;

import java.util.Random;

import org.bukkit.BlockChangeDelegate; // CraftBukkit

public abstract class WorldGenerator {

    private final boolean a;

    public WorldGenerator() {
        this.a = false;
    }

    public WorldGenerator(boolean flag) {
        this.a = flag;
    }

    public abstract boolean a(World world, Random random, int i, int j, int k);

    public void a(double d0, double d1, double d2) {}

    // CraftBukkit - change signature
    protected void setType(BlockChangeDelegate world, int i, int j, int k, int l) {
        this.setTypeAndData(world, i, j, k, l, 0);
        world.setMetadata(i, j, k, "nat", null); // BTCS
    }

    // CraftBukkit - change signature
    protected void setTypeAndData(BlockChangeDelegate world, int i, int j, int k, int l, int i1) {
        if (this.a) {
            world.setTypeIdAndData(i, j, k, l, i1);
        } else if (world instanceof World && ((World)world).getChunkAtWorldCoords(i, k).seenByPlayer) { // CraftBukkit
            if (world.setRawTypeIdAndData(i, j, k, l, i1)) {
                ((World)world).notify(i, j, k); // CraftBukkit - casty
            }
        } else {
            world.setRawTypeIdAndData(i, j, k, l, i1);
        }
        world.setMetadata(i, j, k, "nat", null); // BTCS
    }
}
