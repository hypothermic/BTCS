package net.minecraft.server;

import java.util.HashMap;
import java.util.Map;

public class WM_WarhammerCharge
{
  private int chargeDelay;
  private long lastAttack;
  public EntityHuman entityPlayer;
  private static Map chargeMap = new HashMap();
  public static final int DEFAULT_CHARGE_DELAY = 30000;
  
  private WM_WarhammerCharge(EntityHuman paramEntityHuman)
  {
    this.entityPlayer = paramEntityHuman;
    resetCharge(30000);
  }
  
  public void resetCharge(int paramInt)
  {
    this.chargeDelay = paramInt;
    this.lastAttack = 0L;
  }
  
  public boolean isCharged()
  {
    return System.currentTimeMillis() - this.lastAttack > this.chargeDelay;
  }
  
  public void onAttack()
  {
    this.lastAttack = System.currentTimeMillis();
  }
  
  public static WM_WarhammerCharge getWarhammerCharge(EntityHuman paramEntityHuman)
  {
    WM_WarhammerCharge localWM_WarhammerCharge = (WM_WarhammerCharge)chargeMap.get(paramEntityHuman);
    
    if (localWM_WarhammerCharge == null)
    {
      localWM_WarhammerCharge = registerPlayerToCharge(paramEntityHuman);
    }
    
    return localWM_WarhammerCharge;
  }
  
  public static WM_WarhammerCharge registerPlayerToCharge(EntityHuman paramEntityHuman)
  {
    WM_WarhammerCharge localWM_WarhammerCharge = new WM_WarhammerCharge(paramEntityHuman);
    chargeMap.put(paramEntityHuman, localWM_WarhammerCharge);
    return localWM_WarhammerCharge;
  }
}
