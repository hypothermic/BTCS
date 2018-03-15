package net.minecraft.server;







public class StepSound
{
  public final String a;
  





  public final float b;
  





  public final float c;
  





  public StepSound(String paramString, float paramFloat1, float paramFloat2)
  {
    this.a = paramString;
    this.b = paramFloat1;
    this.c = paramFloat2;
  }
  
  public float getVolume1() {
    return this.b;
  }
  
  public float getVolume2() {
    return this.c;
  }
  



  public String getName()
  {
    return "step." + this.a;
  }
}
