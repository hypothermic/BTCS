package forge.oredict;

import forge.IOreHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map; // BTCS
import java.util.Map.Entry;
import java.util.Set;
import net.minecraft.server.Block;
import net.minecraft.server.Item;
import net.minecraft.server.ItemStack;



public class OreDictionary
{
  private static int maxID = 0;
  private static HashMap<String, Integer> oreIDs = new HashMap();
  private static HashMap<Integer, ArrayList<ItemStack>> oreStacks = new HashMap();
  private static ArrayList<IOreHandler> oreHandlers = new ArrayList();
  







  public static int getOreID(String name)
  {
    Integer val = (Integer)oreIDs.get(name);
    if (val == null)
    {
      val = Integer.valueOf(maxID++);
      oreIDs.put(name, val);
      oreStacks.put(val, new ArrayList());
    }
    return val.intValue();
  }
  






  public static String getOreName(int id)
  {
	// BTCS: imported java.util.Map to resolve issue
    for (Map.Entry<String, Integer> entry : oreIDs.entrySet())
    {
      if (id == ((Integer)entry.getValue()).intValue())
      {
        return (String)entry.getKey();
      }
    }
    return "Unknown";
  }

  public static ArrayList<ItemStack> getOres(String name)
  {
    return getOres(Integer.valueOf(getOreID(name)));
  }

  public static ArrayList<ItemStack> getOres(Integer id)
  {
    ArrayList<ItemStack> val = (ArrayList)oreStacks.get(id);
    if (val == null)
    {
      val = new ArrayList();
      oreStacks.put(id, val);
    }
    return val;
  }
  
  public static void registerOreHandler(IOreHandler handler)
  {
    oreHandlers.add(handler);
    
    HashMap<String, Integer> tmp = (HashMap)oreIDs.clone();
    
    // BTCS start
    /*for (Iterator i$ = tmp.entrySet().iterator(); i$.hasNext();) { entry = (Map.Entry)i$.next();
      
      for (ItemStack stack : getOres((Integer)entry.getValue()))
      {
        handler.registerOre((String)entry.getKey(), stack);
      }
    }*/
    for(Map.Entry<String, Integer> entry : tmp.entrySet())
    {
        for(ItemStack stack : getOres(entry.getValue()))
        {
            handler.registerOre(entry.getKey(), stack);
        }
    }
    // BTCS end
    Map.Entry<String, Integer> entry;
  }
  
  public static void registerOre(String name, Item ore) { registerOre(name, new ItemStack(ore)); }
  public static void registerOre(String name, Block ore) { registerOre(name, new ItemStack(ore)); }
  public static void registerOre(String name, ItemStack ore) { registerOre(name, getOreID(name), ore); }
  public static void registerOre(int id, Item ore) { registerOre(id, new ItemStack(ore)); }
  public static void registerOre(int id, Block ore) { registerOre(id, new ItemStack(ore)); }
  public static void registerOre(int id, ItemStack ore) { registerOre(getOreName(id), id, ore); }
  








  private static void registerOre(String name, int id, ItemStack ore)
  {
    ArrayList<ItemStack> ores = getOres(Integer.valueOf(id));
    ore = ore.cloneItemStack();
    ores.add(ore);
    
    for (IOreHandler handler : oreHandlers)
    {
      handler.registerOre(name, ore);
    }
  }
}
