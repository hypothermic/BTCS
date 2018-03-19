package forge;

import java.util.ArrayList;
import java.util.Arrays;
import net.minecraft.server.DamageSource;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.EntityLiving;
import net.minecraft.server.ItemArmor;
import net.minecraft.server.ItemStack;






public class ArmorProperties
  implements Comparable<ArmorProperties>
{
  public int Priority = 0;
  public int AbsorbMax = Integer.MAX_VALUE;
  public double AbsorbRatio = 0.0D;
  public int Slot = 0;
  
  private static final boolean DEBUG = false;
  
  public ArmorProperties(int priority, double ratio, int max)
  {
    this.Priority = priority;
    this.AbsorbRatio = ratio;
    this.AbsorbMax = max;
  }
  













  public static int ApplyArmor(EntityLiving entity, ItemStack[] inventory, DamageSource source, double damage)
  {
    damage *= 25.0D;
    ArrayList<ArmorProperties> dmgVals = new ArrayList();
    for (int x = 0; x < inventory.length; x++)
    {
      ItemStack stack = inventory[x];
      if (stack != null)
      {


        ArmorProperties prop = null;
        if ((stack.getItem() instanceof ISpecialArmor))
        {
          ISpecialArmor armor = (ISpecialArmor)stack.getItem();
          prop = armor.getProperties(entity, stack, source, damage / 25.0D, x).copy();
        }
        else if (((stack.getItem() instanceof ItemArmor)) && (!source.ignoresArmor()))
        {
          ItemArmor armor = (ItemArmor)stack.getItem();
          prop = new ArmorProperties(0, armor.b / 25.0D, armor.getMaxDurability() + 1 - stack.getData());
        }
        if (prop != null)
        {
          prop.Slot = x;
          dmgVals.add(prop);
        }
      } }
    if (dmgVals.size() > 0)
    {
      ArmorProperties[] props = (ArmorProperties[])dmgVals.toArray(new ArmorProperties[0]);
      StandardizeList(props, damage);
      int level = props[0].Priority;
      double ratio = 0.0D;
      for (ArmorProperties prop : props)
      {
        if (level != prop.Priority)
        {
          damage -= damage * ratio;
          ratio = 0.0D;
          level = prop.Priority;
        }
        ratio += prop.AbsorbRatio;
        
        double absorb = damage * prop.AbsorbRatio;
        if (absorb > 0.0D)
        {
          ItemStack stack = inventory[prop.Slot];
          int itemDamage = (int)(absorb / 25.0D < 1.0D ? 1.0D : absorb / 25.0D);
          if ((stack.getItem() instanceof ISpecialArmor))
          {
            ((ISpecialArmor)stack.getItem()).damageArmor(entity, stack, source, itemDamage, prop.Slot);


          }
          else
          {


            stack.damage(itemDamage, entity);
          }
          if (stack.count <= 0)
          {
            if ((entity instanceof EntityHuman))
            {
              stack.a((EntityHuman)entity);
            }
            inventory[prop.Slot] = null;
          }
        }
      }
      damage -= damage * ratio;
    }
    damage += entity.aq;
    



    entity.aq = ((int)damage % 25);
    return (int)(damage / 25.0D);
  }
  






  private static void StandardizeList(ArmorProperties[] armor, double damage)
  {
    Arrays.sort(armor);
    
    int start = 0;
    double total = 0.0D;
    int priority = armor[0].Priority;
    int pStart = 0;
    boolean pChange = false;
    boolean pFinished = false;
    









    for (int x = 0; x < armor.length; x++)
    {
      total += armor[x].AbsorbRatio;
      if ((x == armor.length - 1) || (armor[x].Priority != priority))
      {
        if (armor[x].Priority != priority)
        {
          total -= armor[x].AbsorbRatio;
          x--;
          pChange = true;
        }
        if (total > 1.0D)
        {
          for (int y = start; y <= x; y++)
          {
            double newRatio = armor[y].AbsorbRatio / total;
            if (newRatio * damage > armor[y].AbsorbMax)
            {
              armor[y].AbsorbRatio = (armor[y].AbsorbMax / damage);
              total = 0.0D;
              for (int z = pStart; z <= y; z++)
              {
                total += armor[z].AbsorbRatio;
              }
              start = y + 1;
              x = y;
              break;
            }
            

            armor[y].AbsorbRatio = newRatio;
            pFinished = true;
          }
          
          if ((pChange) && (pFinished))
          {
            damage -= damage * total;
            total = 0.0D;
            start = x + 1;
            priority = armor[start].Priority;
            pStart = start;
            pChange = false;
            pFinished = false;
            if (damage <= 0.0D)
            {
              for (int y = x + 1; y < armor.length; y++)
              {
                armor[y].AbsorbRatio = 0.0D;
              }
              break;
            }
          }
        }
        else
        {
          for (int y = start; y <= x; y++)
          {
            total -= armor[y].AbsorbRatio;
            if (damage * armor[y].AbsorbRatio > armor[y].AbsorbMax)
            {
              armor[y].AbsorbRatio = (armor[y].AbsorbMax / damage);
            }
            total += armor[y].AbsorbRatio;
          }
          damage -= damage * total;
          total = 0.0D;
          if (x != armor.length - 1)
          {
            start = x + 1;
            priority = armor[start].Priority;
            pStart = start;
            pChange = false;
            if (damage <= 0.0D)
            {
              for (int y = x + 1; y < armor.length; y++)
              {
                armor[y].AbsorbRatio = 0.0D;
              }
              break;
            }
          }
        }
      }
    }
  }
  







  public int compareTo(ArmorProperties o)
  {
    if (o.Priority != this.Priority)
    {
      return o.Priority - this.Priority;
    }
    double left = this.AbsorbRatio == 0.0D ? 0.0D : this.AbsorbMax * 100.0D / this.AbsorbRatio;
    double right = o.AbsorbRatio == 0.0D ? 0.0D : o.AbsorbMax * 100.0D / o.AbsorbRatio;
    return (int)(left - right);
  }
  
  public String toString()
  {
    return String.format("%d, %d, %f, %d", new Object[] { Integer.valueOf(this.Priority), Integer.valueOf(this.AbsorbMax), Double.valueOf(this.AbsorbRatio), Integer.valueOf(this.AbsorbRatio == 0.0D ? 0 : (int)(this.AbsorbMax * 100.0D / this.AbsorbRatio)) });
  }
  
  public ArmorProperties copy()
  {
    return new ArmorProperties(this.Priority, this.AbsorbRatio, this.AbsorbMax);
  }
}
