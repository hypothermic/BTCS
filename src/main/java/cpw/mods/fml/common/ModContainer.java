package cpw.mods.fml.common;

import java.io.File;
import java.util.List;
import java.util.Map;







public abstract interface ModContainer
{
  public abstract boolean wantsPreInit();
  
  public abstract boolean wantsPostInit();
  
  public abstract void preInit();
  
  public abstract void init();
  
  public abstract void postInit();
  
  public abstract String getName();
  
  public abstract ModState getModState();
  
  public abstract void nextState();
  
  public abstract boolean matches(Object paramObject);
  
  public static enum ModState
  {
    UNLOADED("Unloaded"),  LOADED("Loaded"),  PREINITIALIZED("Pre-initialized"),  INITIALIZED("Initialized"),  POSTINITIALIZED("Post-initialized"),  AVAILABLE("Available");
    
    private String label;
    
    private ModState(String label) { this.label = label; }
    

    public String toString() { return this.label; }
  }
  
  public abstract File getSource();
  
  public static enum SourceType { JAR,  CLASSPATH,  DIR;
    
    private SourceType() {}
  }
  
  public abstract String getSortingRules();
  
  public abstract Object getMod();
  
  public abstract int lookupFuelValue(int paramInt1, int paramInt2);
  
  public abstract boolean wantsPickupNotification();
  
  public abstract IPickupNotifier getPickupNotifier();
  
  public abstract boolean wantsToDispense();
  
  public abstract IDispenseHandler getDispenseHandler();
  
  public abstract boolean wantsCraftingNotification();
  
  public abstract ICraftingHandler getCraftingHandler();
  
  public abstract List<String> getDependencies();
  
  public abstract List<String> getPreDepends();
  
  public abstract List<String> getPostDepends();
  
  public abstract boolean wantsNetworkPackets();
  
  public abstract INetworkHandler getNetworkHandler();
  
  public abstract boolean ownsNetworkChannel(String paramString);
  
  public abstract boolean wantsConsoleCommands();
  
  public abstract IConsoleHandler getConsoleHandler();
  
  public abstract boolean wantsPlayerTracking();
  
  public abstract IPlayerTracker getPlayerTracker();
  
  public abstract List<IKeyHandler> getKeys();
  
  public abstract SourceType getSourceType();
  
  public abstract void setSourceType(SourceType paramSourceType);
  
  public abstract ModMetadata getMetadata();
  
  public abstract void setMetadata(ModMetadata paramModMetadata);
  
  public abstract void gatherRenderers(Map paramMap);
  
  public abstract void requestAnimations();
  
  public abstract String getVersion();
  
  public abstract ProxyInjector findSidedProxy();
  
  public abstract void keyBindEvent(Object paramObject);
}
