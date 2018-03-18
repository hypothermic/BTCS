package forge;

import cpw.mods.fml.common.FMLCommonHandler;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Map; // BTCS: ln 67
import java.util.Map.Entry;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.minecraft.server.World;
import net.minecraft.server.WorldProvider;
import net.minecraft.server.WorldProviderNormal;
import net.minecraft.server.WorldProviderTheEnd;
import org.bukkit.World.Environment;

public class DimensionManager
{
  private static Hashtable<Integer, WorldProvider> providers = new Hashtable();
  private static Hashtable<Integer, Boolean> spawnSettings = new Hashtable();
  private static Hashtable<Integer, World> worlds = new Hashtable();
  private static boolean hasInit = false;
  
  @SuppressWarnings("unlikely-arg-type") // BTCS
public static boolean registerDimension(int id, WorldProvider provider, boolean keepLoaded)
  {
    if (providers.containsValue(Integer.valueOf(id)))
    {
      return false;
    }
    providers.put(Integer.valueOf(id), provider);
    spawnSettings.put(Integer.valueOf(id), Boolean.valueOf(keepLoaded));
    // BTCS start: changed all 'World' to 'org.bukkit.World'
    if (org.bukkit.World.Environment.getEnvironment(id) == null)
    {
    	org.bukkit.World.Environment env = EnumHelper.addBukkitEnvironment(id, provider.getSaveFolder());
    	org.bukkit.World.Environment.registerEnvironment(env);
    }
    // BTCS end
    return true;
  }
  
  public static void init()
  {
    if (hasInit)
    {
      return;
    }
    registerDimension(0, new net.minecraft.server.WorldProviderNormal(), true);
    registerDimension(-1, new net.minecraft.server.WorldProviderHell(), true);
    registerDimension(1, new net.minecraft.server.WorldProviderTheEnd(), false);
  }
  
  public static WorldProvider getProvider(int id)
  {
    return (WorldProvider)providers.get(Integer.valueOf(id));
  }
  
  public static Integer[] getIDs()
  {
    return (Integer[])providers.keySet().toArray(new Integer[0]);
  }
  
  public static void setWorld(int id, World world)
  {
    WorldProvider wp = world.worldProvider;
    int wpid = 0;
    // BTCS: imported Map (ln 6) to fix issue below on line 68.
    for (Map.Entry<Integer, WorldProvider> wpe : providers.entrySet())
    {
      if (wpe.getValue() == wp)
      {
        wpid = ((Integer)wpe.getKey()).intValue();
        break;
      }
    }
    FMLCommonHandler.instance().getFMLLogger().info(String.format("Registering world %s, dimension %d, of providertype %s(%d) with Minecraft Forge", new Object[] { world.worldData.name, Integer.valueOf(id), wp, Integer.valueOf(wpid) }));
    worlds.put(Integer.valueOf(id), world);
  }
  
  public static World getWorld(int id)
  {
    return (World)worlds.get(Integer.valueOf(id));
  }
  
  public static World[] getWorlds()
  {
    return (World[])worlds.values().toArray(new World[0]);
  }
  
  public static boolean shouldLoadSpawn(int id)
  {
    return (spawnSettings.contains(Integer.valueOf(id))) && (((Boolean)spawnSettings.get(Integer.valueOf(id))).booleanValue());
  }
  
  static
  {
    init();
  }
  
  public static WorldProvider createProviderFor(int dimensionId) {
    try {
      if (providers.containsKey(Integer.valueOf(dimensionId)))
      {
        return (WorldProvider)getProvider(dimensionId).getClass().newInstance();
      }
      

      return null;
    } catch (Exception e) {
      FMLCommonHandler.instance().getFMLLogger().log(Level.SEVERE, String.format("An error occured trying to create an instance of WorldProvider %d (%s)", new Object[] { Integer.valueOf(dimensionId), getProvider(dimensionId).getClass().getSimpleName() }), e);
      throw new RuntimeException(e);
    }
  }
}
