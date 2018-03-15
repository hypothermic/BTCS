package org.bukkit.craftbukkit;

import java.util.ArrayList;

public class TextWrapper
{
  public static java.util.List<String> wrapText(String text)
  {
    ArrayList<String> output = new ArrayList();
    String[] lines = text.split("\n");
    String lastColor = null;
    
    for (int i = 0; i < lines.length; i++) {
      String line = lines[i];
      if (lastColor != null) {
        line = lastColor + line;
      }
      
      output.add(line);
      lastColor = org.bukkit.ChatColor.getLastColors(line);
    }
    
    return output;
  }
}
