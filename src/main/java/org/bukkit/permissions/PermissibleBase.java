package org.bukkit.permissions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;

public class PermissibleBase implements Permissible
{
  private ServerOperator opable = null;
  private Permissible parent = this;
  private final List<PermissionAttachment> attachments = new java.util.LinkedList();
  private final Map<String, PermissionAttachmentInfo> permissions = new HashMap();
  
  public PermissibleBase(ServerOperator opable) {
    this.opable = opable;
    
    if ((opable instanceof Permissible)) {
      this.parent = ((Permissible)opable);
    }
    
    recalculatePermissions();
  }
  
  public boolean isOp() {
    if (this.opable == null) {
      return false;
    }
    return this.opable.isOp();
  }
  
  public void setOp(boolean value)
  {
    if (this.opable == null) {
      throw new UnsupportedOperationException("Cannot change op value as no ServerOperator is set");
    }
    this.opable.setOp(value);
  }
  
  public boolean isPermissionSet(String name)
  {
    if (name == null) {
      throw new IllegalArgumentException("Permission name cannot be null");
    }
    
    return this.permissions.containsKey(name.toLowerCase());
  }
  
  public boolean isPermissionSet(Permission perm) {
    if (perm == null) {
      throw new IllegalArgumentException("Permission cannot be null");
    }
    
    return isPermissionSet(perm.getName());
  }
  
  public boolean hasPermission(String inName) {
    if (inName == null) {
      throw new IllegalArgumentException("Permission name cannot be null");
    }
    
    String name = inName.toLowerCase();
    
    if (isPermissionSet(name)) {
      return ((PermissionAttachmentInfo)this.permissions.get(name)).getValue();
    }
    Permission perm = Bukkit.getServer().getPluginManager().getPermission(name);
    
    if (perm != null) {
      return perm.getDefault().getValue(isOp());
    }
    return Permission.DEFAULT_PERMISSION.getValue(isOp());
  }
  

  public boolean hasPermission(Permission perm)
  {
    if (perm == null) {
      throw new IllegalArgumentException("Permission cannot be null");
    }
    
    String name = perm.getName().toLowerCase();
    
    if (isPermissionSet(name)) {
      return ((PermissionAttachmentInfo)this.permissions.get(name)).getValue();
    }
    return perm.getDefault().getValue(isOp());
  }
  
  public PermissionAttachment addAttachment(Plugin plugin, String name, boolean value) {
    if (name == null)
      throw new IllegalArgumentException("Permission name cannot be null");
    if (plugin == null)
      throw new IllegalArgumentException("Plugin cannot be null");
    if (!plugin.isEnabled()) {
      throw new IllegalArgumentException("Plugin " + plugin.getDescription().getFullName() + " is disabled");
    }
    
    PermissionAttachment result = addAttachment(plugin);
    result.setPermission(name, value);
    
    recalculatePermissions();
    
    return result;
  }
  
  public PermissionAttachment addAttachment(Plugin plugin) {
    if (plugin == null)
      throw new IllegalArgumentException("Plugin cannot be null");
    if (!plugin.isEnabled()) {
      throw new IllegalArgumentException("Plugin " + plugin.getDescription().getFullName() + " is disabled");
    }
    
    PermissionAttachment result = new PermissionAttachment(plugin, this.parent);
    
    this.attachments.add(result);
    recalculatePermissions();
    
    return result;
  }
  
  public void removeAttachment(PermissionAttachment attachment) {
    if (attachment == null) {
      throw new IllegalArgumentException("Attachment cannot be null");
    }
    
    if (this.attachments.contains(attachment)) {
      this.attachments.remove(attachment);
      PermissionRemovedExecutor ex = attachment.getRemovalCallback();
      
      if (ex != null) {
        ex.attachmentRemoved(attachment);
      }
      
      recalculatePermissions();
    } else {
      throw new IllegalArgumentException("Given attachment is not part of Permissible object " + this.parent);
    }
  }
  
  public void recalculatePermissions() {
    clearPermissions();
    Set<Permission> defaults = Bukkit.getServer().getPluginManager().getDefaultPermissions(isOp());
    Bukkit.getServer().getPluginManager().subscribeToDefaultPerms(isOp(), this.parent);
    
    for (Permission perm : defaults) {
      String name = perm.getName().toLowerCase();
      this.permissions.put(name, new PermissionAttachmentInfo(this.parent, name, null, true));
      Bukkit.getServer().getPluginManager().subscribeToPermission(name, this.parent);
      calculateChildPermissions(perm.getChildren(), false, null);
    }
    
    for (PermissionAttachment attachment : this.attachments) {
      calculateChildPermissions(attachment.getPermissions(), false, attachment);
    }
  }
  
  public synchronized void clearPermissions() {
    Set<String> perms = this.permissions.keySet();
    
    for (String name : perms) {
      Bukkit.getServer().getPluginManager().unsubscribeFromPermission(name, this.parent);
    }
    
    Bukkit.getServer().getPluginManager().unsubscribeFromDefaultPerms(false, this.parent);
    Bukkit.getServer().getPluginManager().unsubscribeFromDefaultPerms(true, this.parent);
    
    this.permissions.clear();
  }
  
  private void calculateChildPermissions(Map<String, Boolean> children, boolean invert, PermissionAttachment attachment) {
    Set<String> keys = children.keySet();
    
    for (String name : keys) {
      Permission perm = Bukkit.getServer().getPluginManager().getPermission(name);
      boolean value = ((Boolean)children.get(name)).booleanValue() ^ invert;
      String lname = name.toLowerCase();
      
      this.permissions.put(lname, new PermissionAttachmentInfo(this.parent, lname, attachment, value));
      Bukkit.getServer().getPluginManager().subscribeToPermission(name, this.parent);
      
      if (perm != null) {
        calculateChildPermissions(perm.getChildren(), !value, attachment);
      }
    }
  }
  
  public PermissionAttachment addAttachment(Plugin plugin, String name, boolean value, int ticks) {
    if (name == null)
      throw new IllegalArgumentException("Permission name cannot be null");
    if (plugin == null)
      throw new IllegalArgumentException("Plugin cannot be null");
    if (!plugin.isEnabled()) {
      throw new IllegalArgumentException("Plugin " + plugin.getDescription().getFullName() + " is disabled");
    }
    
    PermissionAttachment result = addAttachment(plugin, ticks);
    
    if (result != null) {
      result.setPermission(name, value);
    }
    
    return result;
  }
  
  public PermissionAttachment addAttachment(Plugin plugin, int ticks) {
    if (plugin == null)
      throw new IllegalArgumentException("Plugin cannot be null");
    if (!plugin.isEnabled()) {
      throw new IllegalArgumentException("Plugin " + plugin.getDescription().getFullName() + " is disabled");
    }
    
    PermissionAttachment result = addAttachment(plugin);
    
    if (Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new RemoveAttachmentRunnable(result), ticks) == -1) {
      Bukkit.getServer().getLogger().log(Level.WARNING, "Could not add PermissionAttachment to " + this.parent + " for plugin " + plugin.getDescription().getFullName() + ": Scheduler returned -1");
      result.remove();
      return null;
    }
    return result;
  }
  
  public Set<PermissionAttachmentInfo> getEffectivePermissions()
  {
    return new java.util.HashSet(this.permissions.values());
  }
  
  private class RemoveAttachmentRunnable implements Runnable {
    private PermissionAttachment attachment;
    
    public RemoveAttachmentRunnable(PermissionAttachment attachment) {
      this.attachment = attachment;
    }
    
    public void run() {
      this.attachment.remove();
    }
  }
}
