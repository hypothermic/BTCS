package net.minecraft.server;

import java.io.DataOutputStream;
import java.io.IOException;

public class RemoteStatusReply {
  private java.io.ByteArrayOutputStream buffer;
  private DataOutputStream stream;
  
  public RemoteStatusReply(int paramInt) {
    this.buffer = new java.io.ByteArrayOutputStream(paramInt);
    this.stream = new DataOutputStream(this.buffer);
  }
  
  public void write(byte[] paramArrayOfByte) {
    try {
		this.stream.write(paramArrayOfByte, 0, paramArrayOfByte.length);
    } catch (IOException x) {
    	System.out.println("BTCS: Exception X240 happened in RemoteStatusReply");
    	x.printStackTrace();
    }
  }
  
  public void write(String paramString) {
	  try {
    this.stream.writeBytes(paramString);
    this.stream.write(0);
	  } catch (IOException x) {
	    	System.out.println("BTCS: Exception X241 happened in RemoteStatusReply");
	    	x.printStackTrace();
	    }
  }
  
  public void write(int paramInt) {
    try {
		this.stream.write(paramInt);
    } catch (IOException x) {
    	System.out.println("BTCS: Exception X242 happened in RemoteStatusReply");
    	x.printStackTrace();
    }
  }
  
  public void write(short paramShort)
  {
    try {
		this.stream.writeShort(Short.reverseBytes(paramShort));
    } catch (IOException x) {
    	System.out.println("BTCS: Exception X243 happened in RemoteStatusReply");
    	x.printStackTrace();
    }
  }
  
  public byte[] getBytes()
  {
    return this.buffer.toByteArray();
  }
  
  public void reset() {
    this.buffer.reset();
  }
}
