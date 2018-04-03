package cpw.mods.fml.server;

import com.google.common.collect.Multiset;
import com.google.common.collect.Multiset.Entry;
import com.google.common.collect.TreeMultiset;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class FMLBukkitProfiler
{
  private static FMLBukkitProfiler lastInstance;
  private static long endTime;
  private LinkedList<String> profiles;
  private TreeMultiset<String> hitCounter;
  private TreeMultiset<String> timings;
  private long timestamp;
  
  public FMLBukkitProfiler()
  {
    this.profiles = new LinkedList();
    this.hitCounter = TreeMultiset.create();
    this.timings = TreeMultiset.create();
  }
  
  public static long beginProfiling(int seconds)
  {
    long now = System.currentTimeMillis();
    if (lastInstance == null)
    {
      lastInstance = new FMLBukkitProfiler();
      FMLBukkitHandler.instance().profiler = lastInstance;
      endTime = now + seconds * 1000;
    }
    if (endTime > now)
    {
      return (endTime - now) / 1000L;
    }
    

    return 0L;
  }
  

  public static long endProfiling()
  {
    FMLBukkitHandler.instance().profiler = null;
    return endTime - System.currentTimeMillis();
  }
  
  public static void resetProfiling()
  {
    endProfiling();
    lastInstance = null;
  }
  
  public void start(String profileLabel)
  {
    this.profiles.push(profileLabel);
    this.timestamp = System.nanoTime();
  }
  
  public void end()
  {
    int timing = (int)((System.nanoTime() - this.timestamp) / 1000L);
    // BTCS start
    /*String label = com.google.common.base.Joiner.on('.').join(this.profiles);*/
    String label = com.google.common.base.Joiner.on(".").join(this.profiles);
    // BTCS end
    this.profiles.pop();
    this.hitCounter.add(label);
    this.timings.add(label, timing);
    if (System.currentTimeMillis() > endTime)
    {
      endProfiling();
    }
  }
  
  public static String[] dumpProfileData(int count)
  {
    if (lastInstance == null)
    {
      return new String[] { "No profile data available" };
    }
    
    if (endTime > System.currentTimeMillis())
    {
      return new String[] { String.format("Timing is being gathered for another %d seconds", new Object[] { Long.valueOf(endTime - System.currentTimeMillis()) }) };
    }
    return lastInstance.profileData(count);
  }
  
  private String[] profileData(int count) {
    List<Multiset.Entry<String>> sortedTiming = getEntriesSortedByFrequency(this.timings, false);
    ArrayList<String> timeLine = new ArrayList();
    
    double totalTime = this.timings.size();
    
    for (Multiset.Entry<String> hit : sortedTiming)
    {
      count--; if (count == 0) {
        break;
      }
      
      timeLine.add(String.format("%s : %d microseconds, %d invocations. %.2f %% of overall time", new Object[] { hit.getElement(), Integer.valueOf(hit.getCount()), Integer.valueOf(this.hitCounter.count(hit.getElement())), Double.valueOf(100.0D * hit.getCount() / totalTime) }));
    }
    return (String[])timeLine.toArray(new String[0]);
  }
  

  private static enum EntryComp implements java.util.Comparator<Multiset.Entry<?>>
  {
    DESCENDING, 
    ASCENDING;

    private EntryComp() {}

    // BTCS start
	public int compare(Entry<?> arg0, Entry<?> arg1) {
		return 0;
	}
	// BTCS end
  }
  

  private static <E> List<Multiset.Entry<E>> getEntriesSortedByFrequency(Multiset<E> ms, boolean ascending)
  {
    List<Multiset.Entry<E>> entryList = com.google.common.collect.Lists.newArrayList(ms.entrySet());
    java.util.Collections.sort(entryList, ascending ? EntryComp.ASCENDING : EntryComp.DESCENDING);
    return entryList;
  }
}
