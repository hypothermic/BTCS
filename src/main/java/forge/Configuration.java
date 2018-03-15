package forge;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import net.minecraft.server.Block;











public class Configuration
{
  private boolean[] configBlocks = null;
  
  public static final String CATEGORY_GENERAL = "general";
  
  public static final String CATEGORY_BLOCK = "block";
  
  public static final String CATEGORY_ITEM = "item";
  File file;
  public Map<String, Map<String, Property>> categories = new TreeMap();
  
  public TreeMap<String, Property> blockProperties = new TreeMap();
  public TreeMap<String, Property> itemProperties = new TreeMap();
  public TreeMap<String, Property> generalProperties = new TreeMap();
  



  public Configuration(File file)
  {
    this.file = file;
    this.categories.put("general", this.generalProperties);
    this.categories.put("block", this.blockProperties);
    this.categories.put("item", this.itemProperties);
  }
  






  public Property getOrCreateBlockIdProperty(String key, int defaultId)
  {
    if (this.configBlocks == null)
    {
      this.configBlocks = new boolean[Block.byId.length];
      
      for (int i = 0; i < this.configBlocks.length; i++)
      {
        this.configBlocks[i] = false;
      }
    }
    
    Map<String, Property> properties = (Map)this.categories.get("block");
    if (properties.containsKey(key))
    {
      Property property = getOrCreateIntProperty(key, "block", defaultId);
      this.configBlocks[Integer.parseInt(property.value)] = true;
      return property;
    }
    

    Property property = new Property();
    properties.put(key, property);
    property.name = key;
    
    // BTCS start
    /*if ((Block.byId[defaultId] == null) && (this.configBlocks[defaultId] == 0))*/
    if ((Block.byId[defaultId] == null) && (this.configBlocks[defaultId]))
    // BTCS end
    {
      property.value = Integer.toString(defaultId);
      this.configBlocks[defaultId] = true;
      return property;
    }
    

    for (int j = this.configBlocks.length - 1; j >= 0; j--)
    {
      // BTCS start
      /*if ((Block.byId[j] == null) && (this.configBlocks[j] == 0))*/
      if ((Block.byId[j] == null) && (this.configBlocks[j]))
      // BTCS end
      {
        property.value = Integer.toString(j);
        this.configBlocks[j] = true;
        return property;
      }
    }
    
    throw new RuntimeException("No more block ids available for " + key);
  }
  


  public Property getOrCreateIntProperty(String key, String category, int defaultValue)
  {
    Property prop = getOrCreateProperty(key, category, Integer.toString(defaultValue));
    try
    {
      Integer.parseInt(prop.value);
      return prop;
    }
    catch (NumberFormatException e)
    {
      prop.value = Integer.toString(defaultValue); }
    return prop;
  }
  

  public Property getOrCreateBooleanProperty(String key, String category, boolean defaultValue)
  {
    Property prop = getOrCreateProperty(key, category, Boolean.toString(defaultValue));
    if (("true".equals(prop.value.toLowerCase())) || ("false".equals(prop.value.toLowerCase())))
    {
      return prop;
    }
    

    prop.value = Boolean.toString(defaultValue);
    return prop;
  }
  

  public Property getOrCreateProperty(String key, String category, String defaultValue)
  {
    category = category.toLowerCase();
    Map<String, Property> source = (Map)this.categories.get(category);
    
    if (source == null)
    {
      source = new TreeMap();
      this.categories.put(category, source);
    }
    
    if (source.containsKey(key))
    {
      return (Property)source.get(key);
    }
    if (defaultValue != null)
    {
      Property property = new Property();
      
      source.put(key, property);
      property.name = key;
      
      property.value = defaultValue;
      return property;
    }
    

    return null;
  }
  

  public void load()
  {
    BufferedReader buffer = null;
    try
    {
      if (this.file.getParentFile() != null)
      {
        this.file.getParentFile().mkdirs();
      }
      
      if ((!this.file.exists()) && (!this.file.createNewFile())) {
        return;
      }
      

      if (this.file.canRead())
      {
        FileInputStream fileinputstream = new FileInputStream(this.file);
        buffer = new BufferedReader(new InputStreamReader(fileinputstream, "8859_1"));
        

        Map<String, Property> currentMap = null;
        
        for (;;)
        {
          String line = buffer.readLine();
          
          if (line == null) {
            break;
          }
          

          int nameStart = -1;int nameEnd = -1;
          boolean skip = false;
          
          for (int i = 0; (i < line.length()) && (!skip); i++)
          {
            if ((Character.isLetterOrDigit(line.charAt(i))) || (line.charAt(i) == '.'))
            {
              if (nameStart == -1)
              {
                nameStart = i;
              }
              
              nameEnd = i;
            }
            else if (!Character.isWhitespace(line.charAt(i)))
            {




              switch (line.charAt(i))
              {
              case '#': 
                skip = true;
                break;
              case '{': 
                String scopeName = line.substring(nameStart, nameEnd + 1);
                
                currentMap = (Map)this.categories.get(scopeName);
                if (currentMap == null)
                {
                  currentMap = new TreeMap();
                  this.categories.put(scopeName, currentMap);
                }
                
                break;
              case '}': 
                currentMap = null;
                break;
              case '=': 
                String propertyName = line.substring(nameStart, nameEnd + 1);
                
                if (currentMap == null)
                {
                  throw new RuntimeException("property " + propertyName + " has no scope");
                }
                
                Property prop = new Property();
                prop.name = propertyName;
                prop.value = line.substring(i + 1);
                i = line.length();
                
                currentMap.put(propertyName, prop);
                
                break;
              default: 
                throw new RuntimeException("unknown character " + line.charAt(i));
              }
            }
          }
        }
      }
      return;
    }
    catch (IOException e) {
      e.printStackTrace();
    }
    finally
    {
      if (buffer != null)
      {
        try
        {
          buffer.close();
        }
        catch (IOException ioe) {}
      }
    }
  }
  
  public void save()
  {
    try {
      if (this.file.getParentFile() != null)
      {
        this.file.getParentFile().mkdirs();
      }
      
      if ((!this.file.exists()) && (!this.file.createNewFile()))
      {
        return;
      }
      
      if (this.file.canWrite())
      {
        FileOutputStream fos = new FileOutputStream(this.file);
        BufferedWriter buffer = new BufferedWriter(new OutputStreamWriter(fos, "8859_1"));
        
        buffer.write("# Configuration file\r\n");
        buffer.write("# Generated on " + DateFormat.getInstance().format(new Date()) + "\r\n");
        buffer.write("\r\n");
        
        for (Map.Entry<String, Map<String, Property>> category : this.categories.entrySet())
        {
          buffer.write("####################\r\n");
          buffer.write("# " + (String)category.getKey() + " \r\n");
          buffer.write("####################\r\n\r\n");
          
          buffer.write((String)category.getKey() + " {\r\n");
          writeProperties(buffer, ((Map)category.getValue()).values());
          buffer.write("}\r\n\r\n");
        }
        
        buffer.close();
        fos.close();
      }
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }
  
  private void writeProperties(BufferedWriter buffer, Collection<Property> props) throws IOException
  {
    for (Property property : props)
    {
      if (property.comment != null)
      {
        buffer.write("   # " + property.comment + "\r\n");
      }
      
      buffer.write("   " + property.name + "=" + property.value);
      buffer.write("\r\n");
    }
  }
}
