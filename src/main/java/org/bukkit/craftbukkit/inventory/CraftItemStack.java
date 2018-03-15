package org.bukkit.craftbukkit.inventory;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.server.NBTTagCompound;
import net.minecraft.server.NBTTagList;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;

@org.bukkit.configuration.serialization.DelegateDeserialization(org.bukkit.inventory.ItemStack.class)
public class CraftItemStack extends org.bukkit.inventory.ItemStack
{
  protected net.minecraft.server.ItemStack item;
  
  public CraftItemStack(net.minecraft.server.ItemStack item)
  {
    super(item != null ? item.id : 0, item != null ? item.count : 0, (short)(item != null ? item.getData() : 0));

    this.item = item;
  }
  
  // BTCS start
  /*
  public CraftItemStack(CraftItemStack item) {
    this(item);
    this.item = (item.item != null ? item.item.cloneItemStack() : null);
  }*/
  // BTCS end
  
  public CraftItemStack(org.bukkit.inventory.ItemStack item) {
    this(item.getTypeId(), item.getAmount(), item.getDurability());
    addUnsafeEnchantments(item.getEnchantments());
  }
  
  public CraftItemStack(int type)
  {
    this(type, 1);
  }
  
  public CraftItemStack(Material type) {
    this(type, 1);
  }
  
  public CraftItemStack(int type, int amount) {
    this(type, amount, (byte)0);
  }
  
  public CraftItemStack(Material type, int amount) {
    this(type.getId(), amount);
  }
  
  public CraftItemStack(int type, int amount, short damage) {
    this(type, amount, damage, null);
  }
  
  public CraftItemStack(Material type, int amount, short damage) {
    this(type.getId(), amount, damage);
  }
  
  public CraftItemStack(Material type, int amount, short damage, Byte data) {
    this(type.getId(), amount, damage, data);
  }
  
  public CraftItemStack(int type, int amount, short damage, Byte data) {
    this(new net.minecraft.server.ItemStack(type, amount, data != null ? data.byteValue() : damage));
  }
  





  public Material getType()
  {
    super.setTypeId(this.item != null ? this.item.id : 0);
    return super.getType();
  }
  
  public int getTypeId()
  {
    super.setTypeId(this.item != null ? this.item.id : 0);
    return this.item != null ? this.item.id : 0;
  }
  
  public void setTypeId(int type)
  {
    if (type == 0) {
      super.setTypeId(0);
      super.setAmount(0);
      this.item = null;
    }
    else if (this.item == null) {
      this.item = new net.minecraft.server.ItemStack(type, 1, 0);
      super.setTypeId(type);
      super.setAmount(1);
      super.setDurability((short)0);
    } else {
      this.item.id = type;
      super.setTypeId(this.item.id);
    }
  }
  

  public int getAmount()
  {
    super.setAmount(this.item != null ? this.item.count : 0);
    return this.item != null ? this.item.count : 0;
  }
  
  public void setAmount(int amount)
  {
    if (amount == 0) {
      super.setTypeId(0);
      super.setAmount(0);
      this.item = null;
    } else {
      super.setAmount(amount);
      this.item.count = amount;
    }
  }
  

  public void setDurability(short durability)
  {
    if (this.item != null) {
      super.setDurability(durability);
      this.item.setData(durability);
    }
  }
  
  public short getDurability()
  {
    if (this.item != null) {
      super.setDurability((short)this.item.getData());
      return (short)this.item.getData();
    }
    return -1;
  }
  

  public int getMaxStackSize()
  {
    return (this.item == null) ? 0 : this.item.getItem().getMaxStackSize();
  }
  
  public void addUnsafeEnchantment(Enchantment ench, int level)
  {
    Map<Enchantment, Integer> enchantments = getEnchantments();
    enchantments.put(ench, Integer.valueOf(level));
    rebuildEnchantments(enchantments);
  }
  
  public boolean containsEnchantment(Enchantment ench)
  {
    return getEnchantmentLevel(ench) > 0;
  }
  
  public int getEnchantmentLevel(Enchantment ench)
  {
    if (this.item == null) return 0;
    return net.minecraft.server.EnchantmentManager.getEnchantmentLevel(ench.getId(), this.item);
  }
  
  public int removeEnchantment(Enchantment ench)
  {
    Map<Enchantment, Integer> enchantments = getEnchantments();
    Integer previous = (Integer)enchantments.remove(ench);
    
    rebuildEnchantments(enchantments);
    
    return previous == null ? 0 : previous.intValue();
  }
  
  public Map<Enchantment, Integer> getEnchantments()
  {
    Map<Enchantment, Integer> result = new HashMap();
    NBTTagList list = this.item == null ? null : this.item.getEnchantments();
    
    if (list == null) {
      return result;
    }
    
    for (int i = 0; i < list.size(); i++) {
      short id = ((NBTTagCompound)list.get(i)).getShort("id");
      short level = ((NBTTagCompound)list.get(i)).getShort("lvl");
      
      result.put(Enchantment.getById(id), Integer.valueOf(level));
    }
    
    return result;
  }
  
  private void rebuildEnchantments(Map<Enchantment, Integer> enchantments) {
    if (this.item == null) { return;
    }
    NBTTagCompound tag = this.item.tag;
    NBTTagList list = new NBTTagList("ench");
    
    if (tag == null) {
      tag = this.item.tag = new NBTTagCompound();
    }
    
    for (Map.Entry<Enchantment, Integer> entry : enchantments.entrySet()) {
      NBTTagCompound subtag = new NBTTagCompound();
      
      subtag.setShort("id", (short)((Enchantment)entry.getKey()).getId());
      subtag.setShort("lvl", (short)((Integer)entry.getValue()).intValue());
      
      list.add(subtag);
    }
    
    if (enchantments.isEmpty()) {
      tag.remove("ench");
    } else {
      tag.set("ench", list);
    }
  }
  
  public net.minecraft.server.ItemStack getHandle() {
    return this.item;
  }
  
  public CraftItemStack clone()
  {
    CraftItemStack itemStack = (CraftItemStack)super.clone();
    if (this.item != null) {
      itemStack.item = this.item.cloneItemStack();
    }
    return itemStack;
  }
  
  public static net.minecraft.server.ItemStack createNMSItemStack(org.bukkit.inventory.ItemStack original) {
    if ((original == null) || (original.getTypeId() <= 0)) {
      return null;
    }
    if ((original instanceof CraftItemStack)) {
      return ((CraftItemStack)original).getHandle();
    }
    return new CraftItemStack(original).getHandle();
  }
}
