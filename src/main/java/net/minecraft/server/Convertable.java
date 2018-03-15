package net.minecraft.server;

public abstract interface Convertable
{
  public abstract boolean isConvertable(String paramString);
  
  public abstract boolean convert(String paramString, IProgressUpdate paramIProgressUpdate);
}
