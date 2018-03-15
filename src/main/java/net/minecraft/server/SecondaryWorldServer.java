package net.minecraft.server;

import org.bukkit.World.Environment;

public class SecondaryWorldServer extends WorldServer {
  public SecondaryWorldServer(MinecraftServer minecraftserver, IDataManager idatamanager, String s, int i, WorldSettings worldsettings, WorldServer worldserver, org.bukkit.World.Environment env, org.bukkit.generator.ChunkGenerator gen) { super(minecraftserver, idatamanager, s, i, worldsettings, env, gen); // BTCS: 'World' --> 'org.bukkit.World'
    
    this.worldMaps = worldserver.worldMaps;
  }
}
