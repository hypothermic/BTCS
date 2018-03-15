package javax.persistence;




















public class RollbackException
  extends PersistenceException
{
  public RollbackException() {}
  



















  public RollbackException(String message)
  {
    super(message);
  }
  





  public RollbackException(String message, Throwable cause)
  {
    super(message, cause);
  }
  




  public RollbackException(Throwable cause)
  {
    super(cause);
  }
}
