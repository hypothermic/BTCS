package org.bukkit.command;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.permissions.Permissible;
import org.bukkit.plugin.PluginManager;



public abstract class Command
{
  private final String name;
  private String nextLabel;
  private String label;
  private List<String> aliases;
  private List<String> activeAliases;
  private CommandMap commandMap = null;
  protected String description = "";
  protected String usageMessage;
  private String permission;
  private String permissionMessage;
  
  protected Command(String name) {
    this(name, "", "/" + name, new ArrayList());
  }
  
  protected Command(String name, String description, String usageMessage, List<String> aliases) {
    this.name = name;
    this.nextLabel = name;
    this.label = name;
    this.description = description;
    this.usageMessage = usageMessage;
    this.aliases = aliases;
    this.activeAliases = new ArrayList(aliases);
  }
  






  public abstract boolean execute(CommandSender paramCommandSender, String paramString, String[] paramArrayOfString);
  






  public String getName()
  {
    return this.name;
  }
  




  public String getPermission()
  {
    return this.permission;
  }
  




  public void setPermission(String permission)
  {
    this.permission = permission;
  }
  







  public boolean testPermission(CommandSender target)
  {
    if (testPermissionSilent(target)) {
      return true;
    }
    
    if (this.permissionMessage == null) {
      target.sendMessage(ChatColor.RED + "I'm sorry, but you do not have permission to perform this command. Please contact the server administrators if you believe that this is in error.");
    } else if (this.permissionMessage.length() != 0) {
      for (String line : this.permissionMessage.replace("<permission>", this.permission).split("\n")) {
        target.sendMessage(line);
      }
    }
    
    return false;
  }
  







  public boolean testPermissionSilent(CommandSender target)
  {
    if ((this.permission == null) || (this.permission.length() == 0)) {
      return true;
    }
    
    for (String p : this.permission.split(";")) {
      if (target.hasPermission(p)) {
        return true;
      }
    }
    
    return false;
  }
  




  public String getLabel()
  {
    return this.label;
  }
  







  public boolean setLabel(String name)
  {
    this.nextLabel = name;
    if (!isRegistered()) {
      this.label = name;
      return true;
    }
    return false;
  }
  






  public boolean register(CommandMap commandMap)
  {
    if (allowChangesFrom(commandMap)) {
      this.commandMap = commandMap;
      return true;
    }
    
    return false;
  }
  





  public boolean unregister(CommandMap commandMap)
  {
    if (allowChangesFrom(commandMap)) {
      this.commandMap = null;
      this.activeAliases = new ArrayList(this.aliases);
      this.label = this.nextLabel;
      return true;
    }
    
    return false;
  }
  
  private boolean allowChangesFrom(CommandMap commandMap) {
    return (null == this.commandMap) || (this.commandMap == commandMap);
  }
  




  public boolean isRegistered()
  {
    return null != this.commandMap;
  }
  




  public List<String> getAliases()
  {
    return this.activeAliases;
  }
  




  public String getPermissionMessage()
  {
    return this.permissionMessage;
  }
  




  public String getDescription()
  {
    return this.description;
  }
  




  public String getUsage()
  {
    return this.usageMessage;
  }
  





  public Command setAliases(List<String> aliases)
  {
    this.aliases = aliases;
    if (!isRegistered()) {
      this.activeAliases = new ArrayList(aliases);
    }
    return this;
  }
  





  public Command setDescription(String description)
  {
    this.description = description;
    return this;
  }
  





  public Command setPermissionMessage(String permissionMessage)
  {
    this.permissionMessage = permissionMessage;
    return this;
  }
  





  public Command setUsage(String usage)
  {
    this.usageMessage = usage;
    return this;
  }
  
  public static void broadcastCommandMessage(CommandSender source, String message) {
    Set<Permissible> users = Bukkit.getPluginManager().getPermissionSubscriptions("bukkit.broadcast.admin");
    String result = source.getName() + ": " + message;
    String colored = ChatColor.GRAY + "(" + result + ")";
    
    if (!(source instanceof ConsoleCommandSender)) {
      source.sendMessage(message);
    }
    
    for (Permissible user : users) {
      if ((user instanceof CommandSender)) {
        CommandSender target = (CommandSender)user;
        
        if ((target instanceof ConsoleCommandSender)) {
          target.sendMessage(result);
        } else if (target != source) {
          target.sendMessage(colored);
        }
      }
    }
  }
}
