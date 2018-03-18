package net.minecraft.server;

import java.util.Random;

public class WeightedRandom
{
  public static int a(java.util.Collection<WeightedRandomChoice> paramCollection) { // BTCS: decl '<WeightedRandomChoice>'
    int i = 0;
    for (WeightedRandomChoice localWeightedRandomChoice : paramCollection.toArray(new WeightedRandomChoice[paramCollection.size()])) { // BTCS: added .toArray(...)
      i += localWeightedRandomChoice.d;
    }
    return i;
  }
  
  public static WeightedRandomChoice a(Random paramRandom, java.util.Collection<WeightedRandomChoice> paramCollection, int paramInt)
  {
    if (paramInt <= 0) {
      throw new IllegalArgumentException();
    }
    
    int i = paramRandom.nextInt(paramInt);
    for (WeightedRandomChoice localWeightedRandomChoice : paramCollection.toArray(new WeightedRandomChoice[paramCollection.size()])) { // BTCS: added .toArray()
      i -= localWeightedRandomChoice.d;
      if (i < 0) {
        return localWeightedRandomChoice;
      }
    }
    return null;
  }
  
  public static WeightedRandomChoice a(Random paramRandom, java.util.Collection paramCollection) {
    return a(paramRandom, paramCollection, a(paramCollection));
  }
  
  public static int a(WeightedRandomChoice[] paramArrayOfWeightedRandomChoice) {
    int i = 0;
    for (WeightedRandomChoice localWeightedRandomChoice : paramArrayOfWeightedRandomChoice) {
      i += localWeightedRandomChoice.d;
    }
    return i;
  }
  
  public static WeightedRandomChoice a(Random paramRandom, WeightedRandomChoice[] paramArrayOfWeightedRandomChoice, int paramInt)
  {
    if (paramInt <= 0) {
      throw new IllegalArgumentException();
    }
    
    int i = paramRandom.nextInt(paramInt);
    for (WeightedRandomChoice localWeightedRandomChoice : paramArrayOfWeightedRandomChoice) {
      i -= localWeightedRandomChoice.d;
      if (i < 0) {
        return localWeightedRandomChoice;
      }
    }
    return null;
  }
  
  public static WeightedRandomChoice a(Random paramRandom, WeightedRandomChoice[] paramArrayOfWeightedRandomChoice) {
    return a(paramRandom, paramArrayOfWeightedRandomChoice, a(paramArrayOfWeightedRandomChoice));
  }
}
