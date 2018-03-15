package net.minecraft.server;

import java.util.ArrayList;
import java.util.List;

public class PlayerManager
{
  public List managedPlayers = new ArrayList();
  private LongHashMap b = new LongHashMap();
  private List c = new ArrayList();
  private MinecraftServer server;
  private int e;
  private int f;
  private final int[][] g = { { 1, 0 }, { 0, 1 }, { -1, 0 }, { 0, -1 } };
  private boolean wasNotEmpty;
  
  public PlayerManager(MinecraftServer minecraftserver, int i, int j) {
    if (j > 15)
      throw new IllegalArgumentException("Too big view radius!");
    if (j < 3) {
      throw new IllegalArgumentException("Too small view radius!");
    }
    this.f = j;
    this.server = minecraftserver;
    this.e = i;
  }
  
  public WorldServer a()
  {
    return this.server.getWorldServer(this.e);
  }
  
  public void flush() {
    for (int i = 0; i < this.c.size(); i++) {
      ((PlayerInstance)this.c.get(i)).a();
    }
    
    this.c.clear();
    if (this.managedPlayers.isEmpty()) {
      if (!this.wasNotEmpty) return;
      WorldServer worldserver = this.server.getWorldServer(this.e);
      WorldProvider worldprovider = worldserver.worldProvider;
      
      if (!worldprovider.c()) {
        worldserver.chunkProviderServer.c();
      }
      
      this.wasNotEmpty = false;
    } else {
      this.wasNotEmpty = true;
    }
  }
  
  private PlayerInstance a(int i, int j, boolean flag)
  {
    long k = i + 2147483647L | j + 2147483647L << 32;
    PlayerInstance playerinstance = (PlayerInstance)this.b.getEntry(k);
    
    if ((playerinstance == null) && (flag)) {
      playerinstance = new PlayerInstance(this, i, j);
      this.b.put(k, playerinstance);
    }
    
    return playerinstance;
  }
  
  public void flagDirty(int i, int j, int k) {
    int l = i >> 4;
    int i1 = k >> 4;
    PlayerInstance playerinstance = a(l, i1, false);
    
    if (playerinstance != null) {
      playerinstance.a(i & 0xF, j, k & 0xF);
    }
  }
  
  public void addPlayer(EntityPlayer entityplayer) {
    int i = (int)entityplayer.locX >> 4;
    int j = (int)entityplayer.locZ >> 4;
    
    entityplayer.d = entityplayer.locX;
    entityplayer.e = entityplayer.locZ;
    int k = 0;
    int l = this.f;
    int i1 = 0;
    int j1 = 0;
    
    a(i, j, true).a(entityplayer);
    


    for (int k1 = 1; k1 <= l * 2; k1++) {
      for (int l1 = 0; l1 < 2; l1++) {
        int[] aint = this.g[(k++ % 4)];
        
        for (int i2 = 0; i2 < k1; i2++) {
          i1 += aint[0];
          j1 += aint[1];
          a(i + i1, j + j1, true).a(entityplayer);
        }
      }
    }
    
    k %= 4;
    
    for (int k1 = 0; k1 < l * 2; k1++) { // BTCS: added decl 'int '
      i1 += this.g[k][0];
      j1 += this.g[k][1];
      a(i + i1, j + j1, true).a(entityplayer);
    }
    
    this.managedPlayers.add(entityplayer);
  }
  
  public void removePlayer(EntityPlayer entityplayer) {
    int i = (int)entityplayer.d >> 4;
    int j = (int)entityplayer.e >> 4;
    
    for (int k = i - this.f; k <= i + this.f; k++) {
      for (int l = j - this.f; l <= j + this.f; l++) {
        PlayerInstance playerinstance = a(k, l, false);
        
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
    
    return (j1 >= -this.f) && (j1 <= this.f);
  }
  
  public void movePlayer(EntityPlayer entityplayer) {
    int i = (int)entityplayer.locX >> 4;
    int j = (int)entityplayer.locZ >> 4;
    double d0 = entityplayer.d - entityplayer.locX;
    double d1 = entityplayer.e - entityplayer.locZ;
    double d2 = d0 * d0 + d1 * d1;
    
    if (d2 >= 64.0D) {
      int k = (int)entityplayer.d >> 4;
      int l = (int)entityplayer.e >> 4;
      int i1 = i - k;
      int j1 = j - l;
      
      if ((i1 != 0) || (j1 != 0)) {
        for (int k1 = i - this.f; k1 <= i + this.f; k1++) {
          for (int l1 = j - this.f; l1 <= j + this.f; l1++) {
            if (!a(k1, l1, k, l)) {
              a(k1, l1, true).a(entityplayer);
            }
            
            if (!a(k1 - i1, l1 - j1, i, j)) {
              PlayerInstance playerinstance = a(k1 - i1, l1 - j1, false);
              
              if (playerinstance != null) {
                playerinstance.b(entityplayer);
              }
            }
          }
        }
        
        entityplayer.d = entityplayer.locX;
        entityplayer.e = entityplayer.locZ;
        

        if ((i1 > 1) || (i1 < -1) || (j1 > 1) || (j1 < -1)) {
          final int x = i;
          final int z = j;
          List<ChunkCoordIntPair> chunksToSend = entityplayer.chunkCoordIntPairQueue;
          
          java.util.Collections.sort(chunksToSend, new java.util.Comparator() {
            public int compare(ChunkCoordIntPair a, ChunkCoordIntPair b) {
              return Math.max(Math.abs(a.x - x), Math.abs(a.z - z)) - Math.max(Math.abs(b.x - x), Math.abs(b.z - z));
            }
            
            // BTCS start: reason = unimplemented method
			public int compare(Object arg0, Object arg1) {
				// TODO Auto-generated method stub
				return 0;
			}
			// BTCS end
          });
        }
      }
    }
  }
  
  public int getFurthestViewableBlock()
  {
    return this.f * 16 - 16;
  }
  
  static LongHashMap a(PlayerManager playermanager) {
    return playermanager.b;
  }
  
  static List b(PlayerManager playermanager) {
    return playermanager.c;
  }
}
