package net.minecraft.server;

import java.io.PrintStream;
import org.bukkit.craftbukkit.util.UnsafeList;




public class PathfinderGoalSelector
{
  private UnsafeList a = new UnsafeList(16);
  private UnsafeList b = new UnsafeList(16);
  


  public void a(int i, PathfinderGoal pathfindergoal)
  {
    this.a.add(new PathfinderGoalSelectorItem(this, i, pathfindergoal));
  }
  


  public void a()
  {
    for (int i = 0; i < this.a.size(); i++) {
      PathfinderGoalSelectorItem pathfindergoalselectoritem = (PathfinderGoalSelectorItem)this.a.unsafeGet(i);
      
      boolean flag = this.b.contains(pathfindergoalselectoritem);
      
      if (flag) {
        if ((!a(pathfindergoalselectoritem)) || (!pathfindergoalselectoritem.a.b()))
        {


          pathfindergoalselectoritem.a.d();
          this.b.remove(pathfindergoalselectoritem);
        }
      }
      else if ((a(pathfindergoalselectoritem)) && (pathfindergoalselectoritem.a.a()))
      {

        pathfindergoalselectoritem.a.c();
        
        this.b.add(pathfindergoalselectoritem);
      }
    }
    
    boolean flag1 = false;
    


















    if ((flag1) && (this.b.size() > 0)) {
      System.out.println("Running: ");
    }
    

    for (int i = 0; i < this.b.size(); i++) {
      PathfinderGoalSelectorItem pathfindergoalselectoritem1 = (PathfinderGoalSelectorItem)this.b.unsafeGet(i);
      pathfindergoalselectoritem1.a.e();
      if (flag1) {
        System.out.println(pathfindergoalselectoritem1.a.toString());
      }
    }
  }
  

  private boolean a(PathfinderGoalSelectorItem pathfindergoalselectoritem)
  {
    for (int i = 0; i < this.a.size(); i++) {
      PathfinderGoalSelectorItem pathfindergoalselectoritem1 = (PathfinderGoalSelectorItem)this.a.unsafeGet(i);
      
      if (pathfindergoalselectoritem1 != pathfindergoalselectoritem) {
        if (pathfindergoalselectoritem.b >= pathfindergoalselectoritem1.b) {
          if ((!a(pathfindergoalselectoritem, pathfindergoalselectoritem1)) && (this.b.contains(pathfindergoalselectoritem1))) {
            return false;
          }
        } else if ((!pathfindergoalselectoritem1.a.g()) && (this.b.contains(pathfindergoalselectoritem1))) {
          return false;
        }
      }
    }
    

    return true;
  }
  
  private boolean a(PathfinderGoalSelectorItem pathfindergoalselectoritem, PathfinderGoalSelectorItem pathfindergoalselectoritem1) {
    return (pathfindergoalselectoritem.a.h() & pathfindergoalselectoritem1.a.h()) == 0;
  }
}
