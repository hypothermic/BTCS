package javax.persistence.spi;

import java.util.Map;
import javax.persistence.EntityManagerFactory;

public abstract interface PersistenceProvider
{
  public abstract EntityManagerFactory createEntityManagerFactory(String paramString, Map paramMap);
  
  public abstract EntityManagerFactory createContainerEntityManagerFactory(PersistenceUnitInfo paramPersistenceUnitInfo, Map paramMap);
}
