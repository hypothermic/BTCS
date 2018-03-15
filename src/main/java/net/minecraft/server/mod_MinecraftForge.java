package net.minecraft.server;

import forge.ForgeHooks;
import forge.MinecraftForge;
import forge.NetworkMod;
import java.util.Hashtable;
import java.util.Set;



public class mod_MinecraftForge
  extends NetworkMod
{
  @MLProp(info="Set to true to disable darkrooms, it adds a extra check when planting the grass and plants to check that they won't instantly die.")
  public static boolean DISABLE_DARK_ROOMS = false;
  
  @MLProp(info="Set to false to reproduce a vanilla bug that prevents mobs from spawning on inverted half-slabs and inverted stairs.")
  public static boolean SPAWNER_ALLOW_ON_INVERTED = true;
  
  @MLProp(info="The kick message used when a client tries to connect but does not have Minecraft Forge installed.")
  public static String NO_FORGE_KICK_MESSAGE = "This server requires you to have Minecraft Forge installed. http://MinecraftForge.net/";
  
  @MLProp(info="Set to true to randomly shuffle the potential chunks for spawning, this is useful in FTB challange maps where you don't want one side hogging the spawns")
  public static boolean SPAWNER_MAKE_MORE_RANDOM = false;
  

  public String getVersion()
  {
    return String.format("%d.%d.%d.%d", new Object[] { Integer.valueOf(3), Integer.valueOf(3), Integer.valueOf(8), Integer.valueOf(152) });
  }
  



  public void load()
  {
    MinecraftForge.getDungeonLootTries();
    int x = 0;
    for (BaseMod mod : ModLoader.getLoadedMods())
    {
      if ((mod instanceof NetworkMod))
      {
        if (x == Item.MAP.id) {
          x++;
        }
        ForgeHooks.networkMods.put(Integer.valueOf(x++), (NetworkMod)mod);
      }
    }
    
    ((Set)ModLoader.getPrivateValue(Packet.class, null, 3)).add(Integer.valueOf(131));
    ((Set)ModLoader.getPrivateValue(Packet.class, null, 3)).add(Integer.valueOf(132));
  }
}
