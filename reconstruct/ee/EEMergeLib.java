package ee;

import java.util.HashSet;
import net.minecraft.server.Item;

public class EEMergeLib
{
  public static HashSet mergeOnCraft = new HashSet();
  public static HashSet destroyOnCraft = new HashSet();
  
  public static void addMergeOnCraft(Item var0)
  {
    mergeOnCraft.add(Integer.valueOf(var0.id));
  }
  
  public static void addDestroyOnCraft(Item var0)
  {
    destroyOnCraft.add(Integer.valueOf(var0.id));
  }
}
