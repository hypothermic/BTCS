package net.minecraft.server;

import java.io.DataInputStream;
import java.io.IOException;

import nl.hypothermic.btcs.NBTReadLimiter;

public class NBTCompressedStreamTools
{
  public static NBTTagCompound a(java.io.InputStream paramInputStream) throws IOException
  {
    DataInputStream localDataInputStream = new DataInputStream(new java.io.BufferedInputStream(new java.util.zip.GZIPInputStream(paramInputStream)));
    try {
      return a(localDataInputStream, false);
    } finally {
      localDataInputStream.close();
    }
  }
  
  public static void a(NBTTagCompound paramNBTTagCompound, java.io.OutputStream paramOutputStream, boolean x) throws IOException {
    java.io.DataOutputStream localDataOutputStream = new java.io.DataOutputStream(new java.util.zip.GZIPOutputStream(paramOutputStream));
    try {
      a(paramNBTTagCompound, localDataOutputStream);
    } finally {
      localDataOutputStream.close();
    }
  }
  
  public static NBTTagCompound a(byte[] paramArrayOfByte, boolean x) throws IOException {
    DataInputStream localDataInputStream = new DataInputStream(new java.io.BufferedInputStream(new java.util.zip.GZIPInputStream(new java.io.ByteArrayInputStream(paramArrayOfByte))));
    try {
      return a(localDataInputStream);
    } finally {
      localDataInputStream.close();
    }
  }
  
  public static byte[] a(NBTTagCompound paramNBTTagCompound, boolean x) throws IOException {
    java.io.ByteArrayOutputStream localByteArrayOutputStream = new java.io.ByteArrayOutputStream();
    java.io.DataOutputStream localDataOutputStream = new java.io.DataOutputStream(new java.util.zip.GZIPOutputStream(localByteArrayOutputStream));
    try {
      a(paramNBTTagCompound, localDataOutputStream);
    } finally {
      localDataOutputStream.close();
    }
    return localByteArrayOutputStream.toByteArray();
  }

  public static NBTTagCompound a(java.io.DataInput paramDataInput, boolean x) throws IOException { // BTCS
    NBTBase localNBTBase = NBTBase.b(paramDataInput, 0);
    if ((localNBTBase instanceof NBTTagCompound)) return (NBTTagCompound)localNBTBase;
    throw new java.io.IOException("Root tag must be a named compound tag");
  }
  
  public static void a(NBTTagCompound paramNBTTagCompound, java.io.DataOutput paramDataOutput) {
    NBTBase.a(paramNBTTagCompound, paramDataOutput);
  }
}
