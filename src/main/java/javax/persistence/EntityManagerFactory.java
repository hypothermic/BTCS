package javax.persistence;

import java.util.Map;

public abstract interface EntityManagerFactory
{
  public abstract EntityManager createEntityManager();
  
  public abstract EntityManager createEntityManager(Map paramMap);
  
  public abstract void close();
  
  public abstract boolean isOpen();
}
