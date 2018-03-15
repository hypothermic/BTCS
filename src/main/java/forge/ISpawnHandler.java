package forge;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public abstract interface ISpawnHandler
{
  public abstract void writeSpawnData(DataOutputStream paramDataOutputStream)
    throws IOException;
  
  public abstract void readSpawnData(DataInputStream paramDataInputStream)
    throws IOException;
}
