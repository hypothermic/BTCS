package org.bukkit.command;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;

public final class PluginCommand extends Command implements PluginIdentifiableCommand
{
  private final Plugin owningPlugin;
  private CommandExecutor executor;
  
  protected PluginCommand(String name, Plugin owner)
  {
    super(name);
    this.executor = owner;
    this.owningPlugin = owner;
    this.usageMessage = "";
  }
  








  public boolean execute(CommandSender sender, String commandLabel, String[] args)
  {
    boolean success = false;
    
    if (!this.owningPlugin.isEnabled()) {
      return false;
    }
    
    if (!testPermission(sender)) {
      return true;
    }
    try
    {
      success = this.executor.onCommand(sender, this, commandLabel, args);
    } catch (Throwable ex) {
      throw new CommandException("Unhandled exception executing command '" + commandLabel + "' in plugin " + this.owningPlugin.getDescription().getFullName(), ex);
    }
    
    if ((!success) && (this.usageMessage.length() > 0)) {
      for (String line : this.usageMessage.replace("<command>", commandLabel).split("\n")) {
        sender.sendMessage(line);
      }
    }
    
    return success;
  }
  




  public void setExecutor(CommandExecutor executor)
  {
    this.executor = executor;
  }
  




  public CommandExecutor getExecutor()
  {
    return this.executor;
  }
  




  public Plugin getPlugin()
  {
    return this.owningPlugin;
  }
}
