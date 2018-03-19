package forge.oredict;

import java.util.ArrayList;
import java.util.Iterator;
import net.minecraft.server.Block;
import net.minecraft.server.CraftingRecipe;
import net.minecraft.server.InventoryCrafting;
import net.minecraft.server.Item;
import net.minecraft.server.ItemStack;
import org.bukkit.inventory.Recipe;




public class ShapelessOreRecipe
  implements CraftingRecipe
{
  private ItemStack output = null;
  private ArrayList input = new ArrayList();
  
  public ShapelessOreRecipe(Block result, Object... recipe) { this(new ItemStack(result), recipe); }
  public ShapelessOreRecipe(Item result, Object... recipe) { this(new ItemStack(result), recipe); }
  
  public ShapelessOreRecipe(ItemStack result, Object... recipe)
  {
    this.output = result.cloneItemStack();
    for (Object in : recipe)
    {
      if ((in instanceof ItemStack))
      {
        this.input.add(((ItemStack)in).cloneItemStack());
      }
      else if ((in instanceof Item))
      {
        this.input.add(new ItemStack((Item)in));
      }
      else if ((in instanceof Block))
      {
        this.input.add(new ItemStack((Block)in));
      }
      else if ((in instanceof String))
      {
        this.input.add(OreDictionary.getOres((String)in));
      }
      else
      {
        String ret = "Invalid shapeless ore recipe: ";
        for (Object tmp : recipe)
        {
          ret = ret + tmp + ", ";
        }
        ret = ret + this.output;
        throw new RuntimeException(ret);
      }
    }
  }
  
  public int a() {
    return this.input.size();
  }
  
  public ItemStack b() { return this.output; }
  
  public ItemStack b(InventoryCrafting var1) {
    return this.output.cloneItemStack();
  }
  
  public boolean a(InventoryCrafting var1)
  {
    ArrayList required = new ArrayList(this.input);
    
    for (int x = 0; x < var1.getSize(); x++)
    {
      ItemStack slot = var1.getItem(x);
      
      if (slot != null)
      {
        boolean inRecipe = false;
        Iterator req = required.iterator();
        
        while (req.hasNext())
        {
          boolean match = false;
          
          Object next = req.next();
          
          if ((next instanceof ItemStack))
          {
            match = checkItemEquals((ItemStack)next, slot);
          }
          else if (next instanceof ArrayList)
          {
        	// BTCS start
            /*for (ItemStack item : (ArrayList)next)*/
        	for (ItemStack item : (ArrayList<ItemStack>) next)
            // BTCS end
            {
              match = (match) || (checkItemEquals(item, slot));
            }
          }
          
          if (match)
          {
            inRecipe = true;
            required.remove(next);
            break;
          }
        }
        
        if (!inRecipe)
        {
          return false;
        }
      }
    }
    
    return required.isEmpty();
  }
  
  private boolean checkItemEquals(ItemStack target, ItemStack input)
  {
    return (target.id == input.id) && ((target.getData() == -1) || (target.getData() == input.getData()));
  }
  

  public Recipe toBukkitRecipe()
  {
    return null;
  }
}
