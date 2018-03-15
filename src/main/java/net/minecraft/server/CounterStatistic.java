package net.minecraft.server;

import java.util.List;

public class CounterStatistic extends Statistic {
  public CounterStatistic(int paramInt, String paramString, Counter paramCounter) { super(paramInt, paramString, paramCounter); }
  
  public CounterStatistic(int paramInt, String paramString)
  {
    super(paramInt, paramString);
  }
  
  public Statistic d()
  {
    super.d();
    
    StatisticList.c.add(this);
    
    return this;
  }
}
