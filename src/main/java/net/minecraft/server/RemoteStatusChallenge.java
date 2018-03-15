package net.minecraft.server;

import java.net.DatagramPacket;
import java.util.Date;
import java.util.Random;





































































































































































































































































































































class RemoteStatusChallenge
{
  private long time;
  private int token;
  private byte[] identity;
  private byte[] response;
  private String f;
  
  public RemoteStatusChallenge(RemoteStatusListener paramRemoteStatusListener, DatagramPacket paramDatagramPacket)
  {
    this.time = new Date().getTime();
    byte[] arrayOfByte = paramDatagramPacket.getData();
    this.identity = new byte[4];
    this.identity[0] = arrayOfByte[3];
    this.identity[1] = arrayOfByte[4];
    this.identity[2] = arrayOfByte[5];
    this.identity[3] = arrayOfByte[6];
    this.f = new String(this.identity);
    this.token = new Random().nextInt(16777216);
    this.response = String.format("\t%s%d\000", new Object[] { this.f, Integer.valueOf(this.token) }).getBytes();
  }
  
  public Boolean isExpired(long paramLong) {
    return Boolean.valueOf(this.time < paramLong);
  }
  
  public int getToken() {
    return this.token;
  }
  
  public byte[] getChallengeResponse() {
    return this.response;
  }
  
  public byte[] getIdentityToken() {
    return this.identity;
  }
}
