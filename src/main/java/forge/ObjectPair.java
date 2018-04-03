package forge;




public class ObjectPair<T1, T2>
{
  private T1 object1;
  

  private T2 object2;
  


  public ObjectPair(T1 obj1, T2 obj2)
  {
    this.object1 = obj1;
    this.object2 = obj2;
  }
  
  public T1 getValue1()
  {
    return (T1)this.object1;
  }
  
  public T2 getValue2()
  {
    return (T2)this.object2;
  }
  
  public void setValue1(T1 value)
  {
    this.object1 = value;
  }
  
  public void setValue2(T2 value)
  {
    this.object2 = value;
  }
}
