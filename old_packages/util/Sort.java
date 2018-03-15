package org.ibex.nestedvm.util;





public final class Sort
{
  private static final CompareFunc comparableCompareFunc = new CompareFunc() {
    public int compare(Object paramAnonymousObject1, Object paramAnonymousObject2) { return ((Sort.Comparable)paramAnonymousObject1).compareTo(paramAnonymousObject2); }
  };
  
  public static void sort(Comparable[] paramArrayOfComparable) { sort(paramArrayOfComparable, comparableCompareFunc); }
  public static void sort(Object[] paramArrayOfObject, CompareFunc paramCompareFunc) { sort(paramArrayOfObject, paramCompareFunc, 0, paramArrayOfObject.length - 1); }
  
  private static void sort(Object[] paramArrayOfObject, CompareFunc paramCompareFunc, int paramInt1, int paramInt2)
  {
    if (paramInt1 >= paramInt2) return;
    if (paramInt2 - paramInt1 <= 6) {
      for (int i = paramInt1 + 1; i <= paramInt2; i++) {
        localObject1 = paramArrayOfObject[i];
        
        for (j = i - 1; j >= paramInt1; j--) {
          if (paramCompareFunc.compare(paramArrayOfObject[j], localObject1) <= 0) break;
          paramArrayOfObject[(j + 1)] = paramArrayOfObject[j];
        }
        paramArrayOfObject[(j + 1)] = localObject1;
      }
      return;
    }
    
    Object localObject2 = paramArrayOfObject[paramInt2];
    int j = paramInt1 - 1;
    break label105; int k = paramInt2;
    label105:
    do {
      while ((j < k) && (paramCompareFunc.compare(paramArrayOfObject[(++j)], localObject2) < 0)) {}
      while ((k > j) && (paramCompareFunc.compare(paramArrayOfObject[(--k)], localObject2) > 0)) {}
      localObject1 = paramArrayOfObject[j];paramArrayOfObject[j] = paramArrayOfObject[k];paramArrayOfObject[k] = localObject1;
    } while (j < k);
    
    Object localObject1 = paramArrayOfObject[j];paramArrayOfObject[j] = paramArrayOfObject[paramInt2];paramArrayOfObject[paramInt2] = localObject1;
    
    sort(paramArrayOfObject, paramCompareFunc, paramInt1, j - 1);
    sort(paramArrayOfObject, paramCompareFunc, j + 1, paramInt2);
  }
  
  public static abstract interface CompareFunc
  {
    public abstract int compare(Object paramObject1, Object paramObject2);
  }
  
  public static abstract interface Comparable
  {
    public abstract int compareTo(Object paramObject);
  }
}
