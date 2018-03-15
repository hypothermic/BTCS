package org.bukkit.permissions;

import java.util.Set;
import org.bukkit.plugin.Plugin;

public abstract interface Permissible
  extends ServerOperator
{
  public abstract boolean isPermissionSet(String paramString);
  
  public abstract boolean isPermissionSet(Permission paramPermission);
  
  public abstract boolean hasPermission(String paramString);
  
  public abstract boolean hasPermission(Permission paramPermission);
  
  public abstract PermissionAttachment addAttachment(Plugin paramPlugin, String paramString, boolean paramBoolean);
  
  public abstract PermissionAttachment addAttachment(Plugin paramPlugin);
  
  public abstract PermissionAttachment addAttachment(Plugin paramPlugin, String paramString, boolean paramBoolean, int paramInt);
  
  public abstract PermissionAttachment addAttachment(Plugin paramPlugin, int paramInt);
  
  public abstract void removeAttachment(PermissionAttachment paramPermissionAttachment);
  
  public abstract void recalculatePermissions();
  
  public abstract Set<PermissionAttachmentInfo> getEffectivePermissions();
}
