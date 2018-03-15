package javax.persistence;

public abstract interface EntityTransaction
{
  public abstract void begin();
  
  public abstract void commit();
  
  public abstract void rollback();
  
  public abstract void setRollbackOnly();
  
  public abstract boolean getRollbackOnly();
  
  public abstract boolean isActive();
}
