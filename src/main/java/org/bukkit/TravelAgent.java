package org.bukkit;

public abstract interface TravelAgent
{
  public abstract TravelAgent setSearchRadius(int paramInt);
  
  public abstract int getSearchRadius();
  
  public abstract TravelAgent setCreationRadius(int paramInt);
  
  public abstract int getCreationRadius();
  
  public abstract boolean getCanCreatePortal();
  
  public abstract void setCanCreatePortal(boolean paramBoolean);
  
  public abstract Location findOrCreate(Location paramLocation);
  
  public abstract Location findPortal(Location paramLocation);
  
  public abstract boolean createPortal(Location paramLocation);
}
