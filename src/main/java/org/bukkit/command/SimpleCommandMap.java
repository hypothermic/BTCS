package org.bukkit.command;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.bukkit.Server;

public class SimpleCommandMap implements CommandMap
{
  protected final Map<String, Command> knownCommands = new java.util.HashMap();
  protected final Set<String> aliases = new java.util.HashSet();
  private final Server server;
  protected static final Set<org.bukkit.command.defaults.VanillaCommand> fallbackCommands = new java.util.HashSet();
  
  static {
    fallbackCommands.add(new org.bukkit.command.defaults.ListCommand());
    fallbackCommands.add(new org.bukkit.command.defaults.StopCommand());
    fallbackCommands.add(new org.bukkit.command.defaults.SaveCommand());
    fallbackCommands.add(new org.bukkit.command.defaults.SaveOnCommand());
    fallbackCommands.add(new org.bukkit.command.defaults.SaveOffCommand());
    fallbackCommands.add(new org.bukkit.command.defaults.OpCommand());
    fallbackCommands.add(new org.bukkit.command.defaults.DeopCommand());
    fallbackCommands.add(new org.bukkit.command.defaults.BanIpCommand());
    fallbackCommands.add(new org.bukkit.command.defaults.PardonIpCommand());
    fallbackCommands.add(new org.bukkit.command.defaults.BanCommand());
    fallbackCommands.add(new org.bukkit.command.defaults.PardonCommand());
    fallbackCommands.add(new org.bukkit.command.defaults.KickCommand());
    fallbackCommands.add(new org.bukkit.command.defaults.TeleportCommand());
    fallbackCommands.add(new org.bukkit.command.defaults.GiveCommand());
    fallbackCommands.add(new org.bukkit.command.defaults.TimeCommand());
    fallbackCommands.add(new org.bukkit.command.defaults.SayCommand());
    fallbackCommands.add(new org.bukkit.command.defaults.WhitelistCommand());
    fallbackCommands.add(new org.bukkit.command.defaults.TellCommand());
    fallbackCommands.add(new org.bukkit.command.defaults.MeCommand());
    fallbackCommands.add(new org.bukkit.command.defaults.KillCommand());
    fallbackCommands.add(new org.bukkit.command.defaults.GameModeCommand());
    fallbackCommands.add(new org.bukkit.command.defaults.HelpCommand());
    fallbackCommands.add(new org.bukkit.command.defaults.ExpCommand());
    fallbackCommands.add(new org.bukkit.command.defaults.ToggleDownfallCommand());
    fallbackCommands.add(new org.bukkit.command.defaults.BanListCommand());
  }
  
  public SimpleCommandMap(Server server) {
    this.server = server;
    setDefaultCommands(server);
  }
  
  private void setDefaultCommands(Server server) {
    register("bukkit", new org.bukkit.command.defaults.VersionCommand("version"));
    register("bukkit", new org.bukkit.command.defaults.ReloadCommand("reload"));
    register("bukkit", new org.bukkit.command.defaults.PluginsCommand("plugins"));
    register("bukkit", new org.bukkit.command.defaults.TimingsCommand("timings"));
  }
  


  public void registerAll(String fallbackPrefix, List<Command> commands)
  {
    if (commands != null) {
      for (Command c : commands) {
        register(fallbackPrefix, c);
      }
    }
  }
  


  public boolean register(String fallbackPrefix, Command command)
  {
    return register(command.getName(), fallbackPrefix, command);
  }
  


  public boolean register(String label, String fallbackPrefix, Command command)
  {
    boolean registeredPassedLabel = register(label, fallbackPrefix, command, false);
    
    Iterator<String> iterator = command.getAliases().iterator();
    while (iterator.hasNext()) {
      if (!register((String)iterator.next(), fallbackPrefix, command, true)) {
        iterator.remove();
      }
    }
    

    command.register(this);
    
    return registeredPassedLabel;
  }
  









  private synchronized boolean register(String label, String fallbackPrefix, Command command, boolean isAlias)
  {
    String lowerLabel = label.trim().toLowerCase();
    
    if ((isAlias) && (this.knownCommands.containsKey(lowerLabel)))
    {

      return false;
    }
    
    String lowerPrefix = fallbackPrefix.trim().toLowerCase();
    boolean registerdPassedLabel = true;
    

    while ((this.knownCommands.containsKey(lowerLabel)) && (!this.aliases.contains(lowerLabel))) {
      lowerLabel = lowerPrefix + ":" + lowerLabel;
      registerdPassedLabel = false;
    }
    
    if (isAlias) {
      this.aliases.add(lowerLabel);
    }
    else {
      this.aliases.remove(lowerLabel);
      command.setLabel(lowerLabel);
    }
    this.knownCommands.put(lowerLabel, command);
    
    return registerdPassedLabel;
  }
  
  protected Command getFallback(String label) {
    for (org.bukkit.command.defaults.VanillaCommand cmd : fallbackCommands) {
      if (cmd.matches(label)) {
        return cmd;
      }
    }
    
    return null;
  }
  
  public Set<org.bukkit.command.defaults.VanillaCommand> getFallbackCommands() {
    return java.util.Collections.unmodifiableSet(fallbackCommands);
  }
  

  public boolean dispatch(CommandSender sender, String commandLine)
    throws CommandException
  {
    String[] args = commandLine.split(" ");
    
    if (args.length == 0) {
      return false;
    }
    
    String sentCommandLabel = args[0].toLowerCase();
    Command target = getCommand(sentCommandLabel);
    
    if (target == null) {
      return false;
    }
    
    try
    {
      target.execute(sender, sentCommandLabel, (String[])org.bukkit.util.Java15Compat.Arrays_copyOfRange(args, 1, args.length));
    } catch (CommandException ex) {
      throw ex;
    } catch (Throwable ex) {
      throw new CommandException("Unhandled exception executing '" + commandLine + "' in " + target, ex);
    }
    

    return true;
  }
  
  public synchronized void clearCommands() {
    for (java.util.Map.Entry<String, Command> entry : this.knownCommands.entrySet()) {
      ((Command)entry.getValue()).unregister(this);
    }
    this.knownCommands.clear();
    this.aliases.clear();
    setDefaultCommands(this.server);
  }
  
  public Command getCommand(String name) {
    Command target = (Command)this.knownCommands.get(name.toLowerCase());
    if (target == null) {
      target = getFallback(name);
    }
    return target;
  }
  
  public java.util.Collection<Command> getCommands() {
    return this.knownCommands.values();
  }
  
  public void registerServerAliases() {
    Map<String, String[]> values = this.server.getCommandAliases();
    
    for (String alias : values.keySet()) {
      String[] targetNames = (String[])values.get(alias);
      List<Command> targets = new java.util.ArrayList();
      StringBuilder bad = new StringBuilder();
      
      for (String name : targetNames) {
        Command command = getCommand(name);
        
        if (command == null) {
          if (bad.length() > 0) {
            bad.append(", ");
          }
          bad.append(name);
        } else {
          targets.add(command);
        }
      }
      


      if (targets.size() > 0) {
        this.knownCommands.put(alias.toLowerCase(), new MultipleCommandAlias(alias.toLowerCase(), (Command[])targets.toArray(new Command[0])));
      } else {
        this.knownCommands.remove(alias.toLowerCase());
      }
      
      if (bad.length() > 0) {
        this.server.getLogger().warning("The following command(s) could not be aliased under '" + alias + "' because they do not exist: " + bad);
      }
    }
  }
}
