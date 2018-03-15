package javax.persistence;




















public class TransactionRequiredException
  extends PersistenceException
{
  public TransactionRequiredException() {}
  



















  public TransactionRequiredException(String message)
  {
    super(message);
  }
}
