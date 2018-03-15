package javax.persistence;

public abstract interface EntityManager
{
  public abstract void persist(Object paramObject);
  
  public abstract <T> T merge(T paramT);
  
  public abstract void remove(Object paramObject);
  
  public abstract <T> T find(Class<T> paramClass, Object paramObject);
  
  public abstract <T> T getReference(Class<T> paramClass, Object paramObject);
  
  public abstract void flush();
  
  public abstract void setFlushMode(FlushModeType paramFlushModeType);
  
  public abstract FlushModeType getFlushMode();
  
  public abstract void lock(Object paramObject, LockModeType paramLockModeType);
  
  public abstract void refresh(Object paramObject);
  
  public abstract void clear();
  
  public abstract boolean contains(Object paramObject);
  
  public abstract Query createQuery(String paramString);
  
  public abstract Query createNamedQuery(String paramString);
  
  public abstract Query createNativeQuery(String paramString);
  
  public abstract Query createNativeQuery(String paramString, Class paramClass);
  
  public abstract Query createNativeQuery(String paramString1, String paramString2);
  
  public abstract void joinTransaction();
  
  public abstract Object getDelegate();
  
  public abstract void close();
  
  public abstract boolean isOpen();
  
  public abstract EntityTransaction getTransaction();
}
