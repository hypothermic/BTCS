package net.minecraft.server;

import java.util.ArrayList;
import java.util.List;

import nl.hypothermic.btcs.Launcher;

public class PlayerManager {

    public List managedPlayers = new ArrayList();
    private LongHashMap b = new LongHashMap();
    private List c = new ArrayList();
    private MinecraftServer server;
    private int e;
    private int f; // raw view distance
    private final int[][] g = new int[][] { { 1, 0}, { 0, 1}, { -1, 0}, { 0, -1}};
    private boolean wasNotEmpty; // CraftBukkit

    public PlayerManager(MinecraftServer minecraftserver, int i, int j) {
        if (j > 15) {
            throw new IllegalArgumentException("Too big view radius!");
        } else if (j < 3) {
            throw new IllegalArgumentException("Too small view radius!");
        } else {
            this.f = j;
            this.server = minecraftserver;
            this.e = i;
        }
    }

    public WorldServer a() {
        return this.server.getWorldServer(this.e);
    }

    public void flush() {
        for (int i = 0; i < this.c.size(); ++i) {
            ((PlayerInstance) this.c.get(i)).a();
        }

        this.c.clear();
        if (this.managedPlayers.isEmpty()) {
            if (!wasNotEmpty) return; // CraftBukkit - only do unload when we go from non-empty to empty
            WorldServer worldserver = this.server.getWorldServer(this.e);
            WorldProvider worldprovider = worldserver.worldProvider;

            if (!worldprovider.c()) {
                worldserver.chunkProviderServer.c();
            }
            // CraftBukkit start
            wasNotEmpty = false;
        } else {
            wasNotEmpty = true;
        }
        // CraftBukkit end
    }

    private PlayerInstance a(int i, int j, boolean flag) {
        long k = (long) i + 2147483647L | (long) j + 2147483647L << 32;
        PlayerInstance playerinstance = (PlayerInstance) this.b.getEntry(k);

        if (playerinstance == null && flag) {
            playerinstance = new PlayerInstance(this, i, j);
            this.b.put(k, playerinstance);
        }

        return playerinstance;
    }

    public void flagDirty(int i, int j, int k) {
        int l = i >> 4;
        int i1 = k >> 4;
        PlayerInstance playerinstance = this.a(l, i1, false);

        if (playerinstance != null) {
            playerinstance.a(i & 15, j, k & 15);
        }
    }

    public void addPlayer(EntityPlayer entityplayer) {
        int i = (int) entityplayer.locX >> 4;
        int j = (int) entityplayer.locZ >> 4;

        entityplayer.d = entityplayer.locX;
        entityplayer.e = entityplayer.locZ;
        int k = 0;
        int l = this.f;
        // BTCS start
        if (Launcher.cc.viewdist.containsKey(entityplayer.getLocalizedName())) {
        	l = Launcher.cc.viewdist.get(entityplayer.getLocalizedName());
        }
        // BTCS end
        int i1 = 0;
        int j1 = 0;

        this.a(i, j, true).a(entityplayer);

        int k1;

        for (k1 = 1; k1 <= l * 2; ++k1) {
            for (int l1 = 0; l1 < 2; ++l1) {
                int[] aint = this.g[k++ % 4];

                for (int i2 = 0; i2 < k1; ++i2) {
                    i1 += aint[0];
                    j1 += aint[1];
                    this.a(i + i1, j + j1, true).a(entityplayer);
                }
            }
        }

        k %= 4;

        for (k1 = 0; k1 < l * 2; ++k1) {
            i1 += this.g[k][0];
            j1 += this.g[k][1];
            this.a(i + i1, j + j1, true).a(entityplayer);
        }

        this.managedPlayers.add(entityplayer);
    }

    public void removePlayer(EntityPlayer entityplayer) {
        int i = (int) entityplayer.d >> 4;
        int j = (int) entityplayer.e >> 4;

        // BTCS start
        int vd = this.f;
        if (Launcher.cc.viewdist.containsKey(entityplayer.getLocalizedName())) {
        	vd = Launcher.cc.viewdist.get(entityplayer.getLocalizedName());
        }
            
        // BTCS: this.f --> vd
        for (int k = i - vd; k <= i + vd; ++k) {
            for (int l = j - vd; l <= j + vd; ++l) {
        // BTCS end
                PlayerInstance playerinstance = this.a(k, l, false);

                if (playerinstance != null) {
                    playerinstance.b(entityplayer);
                }
            }
        }

        this.managedPlayers.remove(entityplayer);
    }

    private boolean a(int i, int j, int k, int l) {
        int i1 = i - k;
        int j1 = j - l;

        return i1 >= -this.f && i1 <= this.f ? j1 >= -this.f && j1 <= this.f : false;
    }

    public void movePlayer(EntityPlayer entityplayer) {
        int i = (int) entityplayer.locX >> 4;
        int j = (int) entityplayer.locZ >> 4;
        double d0 = entityplayer.d - entityplayer.locX;
        double d1 = entityplayer.e - entityplayer.locZ;
        double d2 = d0 * d0 + d1 * d1;
        
        // BTCS start
        int vd = this.f;
        if (Launcher.cc.viewdist.containsKey(entityplayer.getLocalizedName())) {
        	vd = Launcher.cc.viewdist.get(entityplayer.getLocalizedName());
        }
        // BTCS end

        if (d2 >= 64.0D) {
            int k = (int) entityplayer.d >> 4;
            int l = (int) entityplayer.e >> 4;
            int i1 = i - k;
            int j1 = j - l;

            if (i1 != 0 || j1 != 0) {
            	// BTCS start: this.f --> vd
                for (int k1 = i - vd; k1 <= i + vd; ++k1) {
                    for (int l1 = j - vd; l1 <= j + vd; ++l1) {
                // BTCS end
                        if (!this.a(k1, l1, k, l)) {
                            this.a(k1, l1, true).a(entityplayer);
                        }

                        if (!this.a(k1 - i1, l1 - j1, i, j)) {
                            PlayerInstance playerinstance = this.a(k1 - i1, l1 - j1, false);

                            if (playerinstance != null) {
                                playerinstance.b(entityplayer);
                            }
                        }
                    }
                }

                entityplayer.d = entityplayer.locX;
                entityplayer.e = entityplayer.locZ;

                // CraftBukkit start - send nearest chunks first
                if (i1 > 1 || i1 < -1 || j1 > 1 || j1 < -1) {
                    final int x = i;
                    final int z = j;
                    List<ChunkCoordIntPair> chunksToSend = entityplayer.chunkCoordIntPairQueue;

                    java.util.Collections.sort(chunksToSend, new java.util.Comparator<ChunkCoordIntPair>() {
                        public int compare(ChunkCoordIntPair a, ChunkCoordIntPair b) {
                            return Math.max(Math.abs(a.x - x), Math.abs(a.z - z)) - Math.max(Math.abs(b.x - x), Math.abs(b.z - z));
                        }
                    });
                }
                // CraftBukkit end
            }
        }
    }

    public int getFurthestViewableBlock() {
        return this.f * 16 - 16;
    }

    static LongHashMap a(PlayerManager playermanager) {
        return playermanager.b;
    }

    static List b(PlayerManager playermanager) {
        return playermanager.c;
    }
}
