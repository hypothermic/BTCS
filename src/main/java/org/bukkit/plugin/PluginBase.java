package org.bukkit.plugin;




public abstract class PluginBase
  implements Plugin
{
  public final int hashCode()
  {
    return getName().hashCode();
  }
  
  public final boolean equals(Object obj)
  {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (!(obj instanceof Plugin)) {
      return false;
    }
    return getName().equals(((Plugin)obj).getName());
  }
  
  public final String getName() {
    return getDescription().getName();
  }
}
