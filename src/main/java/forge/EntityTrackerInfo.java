package forge;

public class EntityTrackerInfo
{
  public final NetworkMod Mod;
  public final int ID;
  public final int Range;
  public final int UpdateFrequency;
  public final boolean SendVelocityInfo;
  
  public EntityTrackerInfo(NetworkMod mod, int ID, int range, int updateFrequency, boolean sendVelocityInfo)
  {
    this.Mod = mod;
    this.ID = ID;
    this.Range = range;
    this.UpdateFrequency = updateFrequency;
    this.SendVelocityInfo = sendVelocityInfo;
  }
}
