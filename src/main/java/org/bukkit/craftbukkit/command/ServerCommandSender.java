package org.bukkit.craftbukkit.command;

import java.util.Set;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.PermissibleBase;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.plugin.Plugin;

public abstract class ServerCommandSender implements CommandSender
{
  private final PermissibleBase perm = new PermissibleBase(this);
  


  public boolean isPermissionSet(String name)
  {
    return this.perm.isPermissionSet(name);
  }
  
  public boolean isPermissionSet(Permission perm) {
    return this.perm.isPermissionSet(perm);
  }
  
  public boolean hasPermission(String name) {
    return this.perm.hasPermission(name);
  }
  
  public boolean hasPermission(Permission perm) {
    return this.perm.hasPermission(perm);
  }
  
  public PermissionAttachment addAttachment(Plugin plugin, String name, boolean value) {
    return this.perm.addAttachment(plugin, name, value);
  }
  
  public PermissionAttachment addAttachment(Plugin plugin) {
    return this.perm.addAttachment(plugin);
  }
  
  public PermissionAttachment addAttachment(Plugin plugin, String name, boolean value, int ticks) {
    return this.perm.addAttachment(plugin, name, value, ticks);
  }
  
  public PermissionAttachment addAttachment(Plugin plugin, int ticks) {
    return this.perm.addAttachment(plugin, ticks);
  }
  
  public void removeAttachment(PermissionAttachment attachment) {
    this.perm.removeAttachment(attachment);
  }
  
  public void recalculatePermissions() {
    this.perm.recalculatePermissions();
  }
  
  public Set<PermissionAttachmentInfo> getEffectivePermissions() {
    return this.perm.getEffectivePermissions();
  }
  
  public boolean isPlayer() {
    return false;
  }
  
  public Server getServer() {
    return Bukkit.getServer();
  }
}
