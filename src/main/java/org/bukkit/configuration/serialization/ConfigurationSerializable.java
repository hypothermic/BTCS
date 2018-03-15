package org.bukkit.configuration.serialization;

import java.util.Map;

public abstract interface ConfigurationSerializable
{
  public abstract Map<String, Object> serialize();
}
