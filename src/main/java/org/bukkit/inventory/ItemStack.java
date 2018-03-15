package org.bukkit.inventory;

import com.google.common.collect.ImmutableMap;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.material.MaterialData;

public class ItemStack
  implements Cloneable, ConfigurationSerializable
{
  private int type;
  private int amount = 0;
  private MaterialData data = null;
  private short durability = 0;
  private Map<Enchantment, Integer> enchantments = new HashMap();
  
  public ItemStack(int type) {
    this(type, 1);
  }
  
  public ItemStack(Material type) {
    this(type, 1);
  }
  
  public ItemStack(int type, int amount) {
    this(type, amount, (short)0);
  }
  
  public ItemStack(Material type, int amount) {
    this(type.getId(), amount);
  }
  
  public ItemStack(int type, int amount, short damage) {
    this(type, amount, damage, null);
  }
  
  public ItemStack(Material type, int amount, short damage) {
    this(type.getId(), amount, damage);
  }
  
  public ItemStack(int type, int amount, short damage, Byte data) {
    this.type = type;
    this.amount = amount;
    this.durability = damage;
    if (data != null) {
      createData(data.byteValue());
      this.durability = ((short)data.byteValue());
    }
  }
  
  public ItemStack(Material type, int amount, short damage, Byte data) {
    this(type.getId(), amount, damage, data);
  }
  
  public ItemStack(ItemStack stack) {
    this.type = stack.type;
    this.amount = stack.amount;
    this.durability = stack.durability;
    if (stack.data != null) {
      this.data = stack.data.clone();
    }
    addUnsafeEnchantments(stack.getEnchantments());
  }
  




  public Material getType()
  {
    return Material.getMaterial(this.type);
  }
  






  public void setType(Material type)
  {
    setTypeId(type.getId());
  }
  




  public int getTypeId()
  {
    return this.type;
  }
  






  public void setTypeId(int type)
  {
    this.type = type;
    createData((byte)0);
  }
  




  public int getAmount()
  {
    return this.amount;
  }
  




  public void setAmount(int amount)
  {
    this.amount = amount;
  }
  




  public MaterialData getData()
  {
    Material mat = Material.getMaterial(getTypeId());
    if ((mat != null) && (mat.getData() != null)) {
      this.data = mat.getNewData((byte)this.durability);
    }
    
    return this.data;
  }
  




  public void setData(MaterialData data)
  {
    Material mat = getType();
    
    if ((mat == null) || (mat.getData() == null)) {
      this.data = data;
    }
    else if ((data.getClass() == mat.getData()) || (data.getClass() == MaterialData.class)) {
      this.data = data;
    } else {
      throw new IllegalArgumentException("Provided data is not of type " + mat.getData().getName() + ", found " + data.getClass().getName());
    }
  }
  





  public void setDurability(short durability)
  {
    this.durability = durability;
  }
  




  public short getDurability()
  {
    return this.durability;
  }
  





  public int getMaxStackSize()
  {
    Material material = getType();
    if (material != null) {
      return material.getMaxStackSize();
    }
    
    return -1;
  }
  
  private void createData(byte data) {
    Material mat = Material.getMaterial(this.type);
    
    if (mat == null) {
      this.data = new MaterialData(this.type, data);
    } else {
      this.data = mat.getNewData(data);
    }
  }
  
  public String toString()
  {
    return "ItemStack{" + getType().name() + " x " + getAmount() + "}";
  }
  
  public boolean equals(Object obj)
  {
    if (!(obj instanceof ItemStack)) {
      return false;
    }
    
    ItemStack item = (ItemStack)obj;
    
    return (item.getAmount() == getAmount()) && (item.getTypeId() == getTypeId()) && (getDurability() == item.getDurability()) && (getEnchantments().equals(item.getEnchantments()));
  }
  
  public ItemStack clone()
  {
    try {
      ItemStack itemStack = (ItemStack)super.clone();
      
      itemStack.enchantments = new HashMap(this.enchantments);
      if (this.data != null) {
        itemStack.data = this.data.clone();
      }
      
      return itemStack;
    } catch (CloneNotSupportedException e) {
      throw new Error(e);
    }
  }
  
  public int hashCode()
  {
    int hash = 11;
    
    hash = hash * 19 + 7 * getTypeId();
    hash = hash * 7 + 23 * getAmount();
    return hash;
  }
  





  public boolean containsEnchantment(Enchantment ench)
  {
    return this.enchantments.containsKey(ench);
  }
  





  public int getEnchantmentLevel(Enchantment ench)
  {
    return ((Integer)this.enchantments.get(ench)).intValue();
  }
  




  public Map<Enchantment, Integer> getEnchantments()
  {
    return ImmutableMap.copyOf(this.enchantments);
  }
  







  public void addEnchantments(Map<Enchantment, Integer> enchantments)
  {
    for (Map.Entry<Enchantment, Integer> entry : enchantments.entrySet()) {
      addEnchantment((Enchantment)entry.getKey(), ((Integer)entry.getValue()).intValue());
    }
  }
  







  public void addEnchantment(Enchantment ench, int level)
  {
    if ((level < ench.getStartLevel()) || (level > ench.getMaxLevel()))
      throw new IllegalArgumentException("Enchantment level is either too low or too high (given " + level + ", bounds are " + ench.getStartLevel() + " to " + ench.getMaxLevel());
    if (!ench.canEnchantItem(this)) {
      throw new IllegalArgumentException("Specified enchantment cannot be applied to this itemstack");
    }
    
    addUnsafeEnchantment(ench, level);
  }
  







  public void addUnsafeEnchantments(Map<Enchantment, Integer> enchantments)
  {
    for (Map.Entry<Enchantment, Integer> entry : enchantments.entrySet()) {
      addUnsafeEnchantment((Enchantment)entry.getKey(), ((Integer)entry.getValue()).intValue());
    }
  }
  










  public void addUnsafeEnchantment(Enchantment ench, int level)
  {
    this.enchantments.put(ench, Integer.valueOf(level));
  }
  





  public int removeEnchantment(Enchantment ench)
  {
    Integer previous = (Integer)this.enchantments.remove(ench);
    return previous == null ? 0 : previous.intValue();
  }
  
  public Map<String, Object> serialize() {
    Map<String, Object> result = new LinkedHashMap();
    
    result.put("type", getType().name());
    
    if (this.durability != 0) {
      result.put("damage", Short.valueOf(this.durability));
    }
    
    if (this.amount != 1) {
      result.put("amount", Integer.valueOf(this.amount));
    }
    
    Map<Enchantment, Integer> enchants = getEnchantments();
    
    if (enchants.size() > 0) {
      Map<String, Integer> safeEnchants = new HashMap();
      
      for (Map.Entry<Enchantment, Integer> entry : enchants.entrySet()) {
        safeEnchants.put(((Enchantment)entry.getKey()).getName(), entry.getValue());
      }
      
      result.put("enchantments", safeEnchants);
    }
    
    return result;
  }
  
  public static ItemStack deserialize(Map<String, Object> args) {
    Material type = Material.getMaterial((String)args.get("type"));
    short damage = 0;
    int amount = 1;
    
    if (args.containsKey("damage")) {
      damage = ((Number)args.get("damage")).shortValue();
    }
    
    if (args.containsKey("amount")) {
      amount = ((Integer)args.get("amount")).intValue();
    }
    
    ItemStack result = new ItemStack(type, amount, damage);
    
    if (args.containsKey("enchantments")) {
      Object raw = args.get("enchantments");
      
      if ((raw instanceof Map)) {
        Map<?, ?> map = (Map)raw;
        
        for (Map.Entry<?, ?> entry : map.entrySet()) {
          Enchantment enchantment = Enchantment.getByName(entry.getKey().toString());
          
          if ((enchantment != null) && ((entry.getValue() instanceof Integer))) {
            result.addUnsafeEnchantment(enchantment, ((Integer)entry.getValue()).intValue());
          }
        }
      }
    }
    
    return result;
  }
}
