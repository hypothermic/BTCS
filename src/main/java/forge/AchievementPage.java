package forge;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import net.minecraft.server.Achievement;







public class AchievementPage
{
  private String name;
  private LinkedList<Achievement> achievements;
  
  public AchievementPage(String name, Achievement... achievements)
  {
    this.name = name;
    this.achievements = new LinkedList(Arrays.asList(achievements));
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public List<Achievement> getAchievements()
  {
    return this.achievements;
  }
}
