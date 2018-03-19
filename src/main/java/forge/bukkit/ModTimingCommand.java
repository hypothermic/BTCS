package forge.bukkit;

import cpw.mods.fml.server.FMLBukkitProfiler;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;


public class ModTimingCommand
  extends Command
{
  protected ModTimingCommand(String name)
  {
    super(name);
    this.description = "Controls the forge mod timing tracker";
    this.usageMessage = "/modtiming <start [seconds] | stop | reset | show [ count ]>";
    setPermission("bukkit.command.timings");
  }
  
  public boolean execute(CommandSender sender, String commandLabel, String[] args)
  {
    if (!testPermission(sender))
    {
      return true;
    }
    if (args.length == 0) {
      sender.sendMessage(ChatColor.RED + "Usage: " + this.usageMessage);
      return false;
    }
    if ("start".equals(args[0]))
    {
      int seconds = 300;
      if (args.length > 1) {
        try
        {
          seconds = Integer.parseInt(args[1]);
        }
        catch (Exception e) {}
      }
      


      long secondsToRun = FMLBukkitProfiler.beginProfiling(seconds);
      if (secondsToRun > 0L)
      {
        sender.sendMessage(ChatColor.YELLOW + String.format("Timing run in progress. Timings will be gathered for another %d seconds.", new Object[] { Long.valueOf(secondsToRun) }));
      }
      else
      {
        sender.sendMessage(ChatColor.YELLOW + String.format("Timing run is complete. Use show to view or reset to reset for another run", new Object[0]));
      }
      return true;
    }
    if ("stop".equals(args[0]))
    {
      long seconds = FMLBukkitProfiler.endProfiling();
      sender.sendMessage(ChatColor.YELLOW + String.format("Timing run stopped after %d seconds.", new Object[] { Long.valueOf(seconds) }));
      return true;
    }
    if ("reset".equals(args[0]))
    {
      FMLBukkitProfiler.resetProfiling();
      sender.sendMessage(ChatColor.YELLOW + String.format("Timing data has been reset", new Object[0]));
      return true;
    }
    if ("show".equals(args[0]))
    {
      int count = -1;
      if (args.length > 1) {
        try
        {
          count = Integer.parseInt(args[1]);
        }
        catch (Exception e) {}
      }
      


      String[] dump = FMLBukkitProfiler.dumpProfileData(count);
      sender.sendMessage(ChatColor.YELLOW + String.format("Timing data for %s timings", new Object[] { count == -1 ? "all" : String.valueOf(count) }));
      for (String str : dump)
      {
        sender.sendMessage(ChatColor.YELLOW + str);
      }
      return true;
    }
    sender.sendMessage(ChatColor.RED + "Usage: " + this.usageMessage);
    return false;
  }
}
