package ee;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public final class EEProps
{
  private String field_26620_fileName;
  private List field_26618_lines = new ArrayList();
  private Map field_26619_props = new HashMap();
  
  public EEProps(String var1)
  {
    this.field_26620_fileName = var1;
    File var2 = new File(this.field_26620_fileName);
    
    if (var2.exists())
    {
      try
      {
        func_26600_load();
      }
      catch (IOException var4)
      {
        System.out.println("[Props] Unable to load " + this.field_26620_fileName + "!");
      }
      
    }
    else {
      func_26596_save();
    }
  }
  
  public void func_26600_load() throws IOException
  {
    BufferedReader var1 = new BufferedReader(new InputStreamReader(new FileInputStream(this.field_26620_fileName), "UTF8"));
    this.field_26618_lines.clear();
    this.field_26619_props.clear();
    
    String var2;
    while ((var2 = var1.readLine()) != null)
    {
      var2 = new String(var2.getBytes(), "UTF-8");
      boolean var3 = false;
      
      char var14 = '\000';
      
      int var4;
      for (var4 = 0; (var4 < var2.length()) && (Character.isWhitespace(var14 = var2.charAt(var4))); var4++) {}

      if ((var2.length() - var4 != 0) && (var2.charAt(var4) != '#') && (var2.charAt(var4) != '!'))
      {
        boolean var6 = var2.indexOf('\\', var4) != -1;
        StringBuffer var7 = var6 ? new StringBuffer() : null;
        
        if (var7 != null)
        {
          while ((var4 < var2.length()) && (!Character.isWhitespace(var14 = var2.charAt(var4++))) && (var14 != '=') && (var14 != ':'))
          {
            if ((var6) && (var14 == '\\'))
            {
              if (var4 == var2.length())
              {
                var2 = var1.readLine();
                
                if (var2 == null)
                {
                  var2 = "";
                }
                
                var4 = 0;
                
                do
                {
                  var4++;
                  
                  if (var4 >= var2.length()) break; } while (Character.isWhitespace(var14 = var2.charAt(var4)));


              }
              else
              {


                var14 = var2.charAt(var4++);
              }
              
            }
            else {
              switch (var14)
              {
              case 'n': 
                var7.append('\n');
                break;
              case 'o': 
              case 'p': 
              case 'q': 
              case 's': 
              default: 
                var7.append('\000');
                break;
              case 'r': 
                var7.append('\r');
                break;
              case 't': 
                var7.append('\t');
                break;
              
              case 'u': 
                if (var4 + 4 <= var2.length())
                {
                  char var8 = (char)Integer.parseInt(var2.substring(var4, var4 + 4), 16);
                  var7.append(var8);
                  var4 += 4;
                }
                break;
              }
            }
          }
        }
        boolean var15 = (var14 == ':') || (var14 == '=');
        String var9;
        if (var6)
        {
          var9 = var7.toString();
        } else {
          if ((!var15) && (!Character.isWhitespace(var14)))
          {
            var9 = var2.substring(var4, var4);
          }
          else
          {
            var9 = var2.substring(var4, var4 - 1);
          }
        }
        while ((var4 < var2.length()) && (Character.isWhitespace(var14 = var2.charAt(var4))))
        {
          var4++;
        }
        
        if ((!var15) && ((var14 == ':') || (var14 == '=')))
        {
          var4++;
          
          while ((var4 < var2.length()) && (Character.isWhitespace(var2.charAt(var4))))
          {
            var4++;
          }
        }
        
        if (!var6)
        {
          this.field_26618_lines.add(var2);
        }
        else
        {
          StringBuilder var10 = new StringBuilder(var2.length() - var4);
          
          while (var4 < var2.length())
          {
            char var11 = var2.charAt(var4++);
            
            if (var11 == '\\')
            {
              if (var4 == var2.length())
              {
                var2 = var1.readLine();
                
                if (var2 == null) {
                  break;
                }
                

                for (var4 = 0; (var4 < var2.length()) && (Character.isWhitespace(var2.charAt(var4))); var4++) {}
                



                var10.ensureCapacity(var2.length() - var4 + var10.length());
                continue;
              }
              
              char var12 = var2.charAt(var4++);
              
              switch (var12)
              {
              case 'n': 
                var10.append('\n');
                break;
              case 'o': 
              case 'p': 
              case 'q': 
              case 's': 
              default: 
                var10.append('\000');
                break;
              case 'r': 
                var10.append('\r');
                break;
              case 't': 
                var10.append('\t');
                break;
              
              case 'u': 
                if (var4 + 4 > var2.length()) {
                  continue;
                }
                

                char var13 = (char)Integer.parseInt(var2.substring(var4, var4 + 4), 16);
                var10.append(var13);
                var4 += 4;
              }
              
            }
            var10.append('\000');
          }
          
          this.field_26618_lines.add(var9 + "=" + var10.toString());
        }
      }
      else
      {
        this.field_26618_lines.add(var2);
      }
    }
    
    var1.close();
  }
  
  public void func_26596_save()
  {
    FileOutputStream var1 = null;
    
    try
    {
      var1 = new FileOutputStream(this.field_26620_fileName);
    }
    catch (FileNotFoundException var11)
    {
      System.out.println("[Props] Unable to open " + this.field_26620_fileName + "!");
    }
    
    PrintStream var2 = null;
    
    try
    {
      var2 = new PrintStream(var1, true, "UTF-8");
    }
    catch (UnsupportedEncodingException var10)
    {
      System.out.println("[Props] Unable to write to " + this.field_26620_fileName + "!");
    }
    
    ArrayList var3 = new ArrayList();
    Iterator var4 = this.field_26618_lines.iterator();
    
    while (var4.hasNext())
    {
      String var5 = (String)var4.next();
      
      if (var5.trim().length() == 0)
      {
        var2.println(var5);
      }
      else if (var5.charAt(0) == '#')
      {
        var2.println(var5);
      }
      else if (var5.contains("="))
      {
        int var6 = var5.indexOf('=');
        String var7 = var5.substring(0, var6).trim();
        
        if (this.field_26619_props.containsKey(var7))
        {
          String var8 = (String)this.field_26619_props.get(var7);
          var2.println(var7 + "=" + var8);
          var3.add(var7);
        }
        else
        {
          var2.println(var5);
        }
      }
      else
      {
        var2.println(var5);
      }
    }
    
    var4 = this.field_26619_props.entrySet().iterator();
    
    while (var4.hasNext())
    {
      Map.Entry var12 = (Map.Entry)var4.next();
      
      if (!var3.contains(var12.getKey()))
      {
        var2.println((String)var12.getKey() + "=" + (String)var12.getValue());
      }
    }
    
    var2.close();
    
    try
    {
      this.field_26619_props.clear();
      this.field_26618_lines.clear();
      func_26600_load();
    }
    catch (IOException var9)
    {
      System.out.println("[Props] Unable to load " + this.field_26620_fileName + "!");
    }
  }
  
  public Map func_26604_returnMap() throws Exception
  {
    HashMap var1 = new HashMap();
    BufferedReader var2 = new BufferedReader(new java.io.FileReader(this.field_26620_fileName));
    
    String var3;
    while ((var3 = var2.readLine()) != null) {
      if ((var3.trim().length() != 0) && (var3.charAt(0) != '#') && (var3.contains("=")))
      {
        int var4 = var3.indexOf('=');
        String var5 = var3.substring(0, var4).trim();
        String var6 = var3.substring(var4 + 1).trim();
        var1.put(var5, var6);
      }
    }
    
    var2.close();
    return var1;
  }
  
  public boolean func_26610_containsKey(String var1)
  {
    Iterator var2 = this.field_26618_lines.iterator();
    
    while (var2.hasNext())
    {
      String var3 = (String)var2.next();
      
      if ((var3.trim().length() != 0) && (var3.charAt(0) != '#') && (var3.contains("=")))
      {
        int var4 = var3.indexOf('=');
        String var5 = var3.substring(0, var4);
        
        if (var5.equals(var1))
        {
          return true;
        }
      }
    }
    
    return false;
  }
  
  public String func_26615_getProperty(String var1)
  {
    Iterator var2 = this.field_26618_lines.iterator();
    
    while (var2.hasNext())
    {
      String var3 = (String)var2.next();
      
      if ((var3.trim().length() != 0) && (var3.charAt(0) != '#') && (var3.contains("=")))
      {
        int var4 = var3.indexOf('=');
        String var5 = var3.substring(0, var4).trim();
        String var6 = var3.substring(var4 + 1);
        
        if (var5.equals(var1))
        {
          return var6;
        }
      }
    }
    
    return "";
  }
  
  public void func_26606_removeKey(String var1)
  {
    Boolean var2 = Boolean.valueOf(false);
    
    if (this.field_26619_props.containsKey(var1))
    {
      this.field_26619_props.remove(var1);
      var2 = Boolean.valueOf(true);
    }
    
    try
    {
      for (int var3 = 0; var3 < this.field_26618_lines.size(); var3++)
      {
        String var4 = (String)this.field_26618_lines.get(var3);
        
        if ((var4.trim().length() != 0) && (var4.charAt(0) != '#') && (var4.contains("=")))
        {
          int var5 = var4.indexOf('=');
          String var6 = var4.substring(0, var5).trim();
          
          if (var6.equals(var1))
          {
            this.field_26618_lines.remove(var3);
            var2 = Boolean.valueOf(true);
          }
        }
      }
    }
    catch (ConcurrentModificationException var7)
    {
      func_26606_removeKey(var1);
      return;
    }
    
    if (var2.booleanValue())
    {
      func_26596_save();
    }
  }
  
  public boolean func_26611_keyExists(String var1)
  {
    try
    {
      return func_26610_containsKey(var1);
    }
    catch (Exception var3) {}
    
    return false;
  }
  

  public String func_26614_getString(String var1)
  {
    return func_26610_containsKey(var1) ? func_26615_getProperty(var1) : "";
  }
  
  public String func_26599_getString(String var1, String var2)
  {
    if (func_26610_containsKey(var1))
    {
      return func_26615_getProperty(var1);
    }
    

    func_26616_setString(var1, var2);
    return var2;
  }
  

  public void func_26616_setString(String var1, String var2)
  {
    this.field_26619_props.put(var1, var2);
    func_26596_save();
  }
  
  public int getInt(String var1)
  {
    return func_26602_getInt(var1);
  }
  
  public int func_26602_getInt(String var1)
  {
    return func_26610_containsKey(var1) ? Integer.parseInt(func_26615_getProperty(var1)) : 0;
  }
  
  public int getInt(String var1, int var2)
  {
    return func_26608_getInt(var1, var2);
  }
  
  public int func_26608_getInt(String var1, int var2)
  {
    if (func_26610_containsKey(var1))
    {
      return Integer.parseInt(func_26615_getProperty(var1));
    }
    

    func_26603_setInt(var1, var2);
    return var2;
  }
  

  public void func_26603_setInt(String var1, int var2)
  {
    this.field_26619_props.put(var1, String.valueOf(var2));
    func_26596_save();
  }
  
  public double func_26605_getDouble(String var1)
  {
    return func_26610_containsKey(var1) ? Double.parseDouble(func_26615_getProperty(var1)) : 0.0D;
  }
  
  public double func_26607_getDouble(String var1, double var2)
  {
    if (func_26610_containsKey(var1))
    {
      return Double.parseDouble(func_26615_getProperty(var1));
    }
    

    func_26609_setDouble(var1, var2);
    return var2;
  }
  

  public void func_26609_setDouble(String var1, double var2)
  {
    this.field_26619_props.put(var1, String.valueOf(var2));
    func_26596_save();
  }
  
  public long func_26598_getLong(String var1)
  {
    return func_26610_containsKey(var1) ? Long.parseLong(func_26615_getProperty(var1)) : 0L;
  }
  
  public long func_26613_getLong(String var1, long var2)
  {
    if (func_26610_containsKey(var1))
    {
      return Long.parseLong(func_26615_getProperty(var1));
    }
    

    func_26617_setLong(var1, var2);
    return var2;
  }
  

  public void func_26617_setLong(String var1, long var2)
  {
    this.field_26619_props.put(var1, String.valueOf(var2));
    func_26596_save();
  }
  
  public boolean func_26612_getBoolean(String var1)
  {
    return func_26610_containsKey(var1) ? Boolean.parseBoolean(func_26615_getProperty(var1)) : false;
  }
  
  public boolean func_26597_getBoolean(String var1, boolean var2)
  {
    if (func_26610_containsKey(var1))
    {
      return Boolean.parseBoolean(func_26615_getProperty(var1));
    }
    

    func_26601_setBoolean(var1, var2);
    return var2;
  }
  

  public void func_26601_setBoolean(String var1, boolean var2)
  {
    this.field_26619_props.put(var1, String.valueOf(var2));
    func_26596_save();
  }
}
