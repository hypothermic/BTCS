package org.ibex.nestedvm;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import org.ibex.nestedvm.util.Seekable;
import org.ibex.nestedvm.util.Seekable.File;



public class RuntimeCompiler
{
  public static Class compile(Seekable paramSeekable) throws IOException, Compiler.Exn { return compile(paramSeekable, null); }
  public static Class compile(Seekable paramSeekable, String paramString) throws IOException, Compiler.Exn { return compile(paramSeekable, paramString, null); }
  
  public static Class compile(Seekable paramSeekable, String paramString1, String paramString2) throws IOException, Compiler.Exn {
    String str = "nestedvm.runtimecompiled";
    byte[] arrayOfByte;
    try {
      arrayOfByte = runCompiler(paramSeekable, str, paramString1, paramString2, null);
    } catch (Compiler.Exn localExn) {
      if ((localExn.getMessage() != null) || (localExn.getMessage().indexOf("constant pool full") != -1)) {
        arrayOfByte = runCompiler(paramSeekable, str, paramString1, paramString2, "lessconstants");
      } else
        throw localExn;
    }
    return new SingleClassLoader(null).fromBytes(str, arrayOfByte);
  }
  
  private static byte[] runCompiler(Seekable paramSeekable, String paramString1, String paramString2, String paramString3, String paramString4) throws IOException, Compiler.Exn {
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
    try
    {
      ClassFileCompiler localClassFileCompiler = new ClassFileCompiler(paramSeekable, paramString1, localByteArrayOutputStream);
      localClassFileCompiler.parseOptions("nosupportcall,maxinsnpermethod=256");
      localClassFileCompiler.setSource(paramString3);
      if (paramString2 != null) localClassFileCompiler.parseOptions(paramString2);
      if (paramString4 != null) localClassFileCompiler.parseOptions(paramString4);
      localClassFileCompiler.go();
    } finally {
      paramSeekable.seek(0);
    }
    
    localByteArrayOutputStream.close();
    return localByteArrayOutputStream.toByteArray();
  }
  
  private static class SingleClassLoader extends ClassLoader { SingleClassLoader(RuntimeCompiler.1 param1) { this(); }
    

    public Class loadClass(String paramString, boolean paramBoolean) throws ClassNotFoundException { return super.loadClass(paramString, paramBoolean); }
    
    public Class fromBytes(String paramString, byte[] paramArrayOfByte) { return fromBytes(paramString, paramArrayOfByte, 0, paramArrayOfByte.length); }
    
    public Class fromBytes(String paramString, byte[] paramArrayOfByte, int paramInt1, int paramInt2) { Class localClass = super.defineClass(paramString, paramArrayOfByte, paramInt1, paramInt2);
      resolveClass(localClass);
      return localClass;
    }
    
    private SingleClassLoader() {} }
  
  public static void main(String[] paramArrayOfString) throws Exception { if (paramArrayOfString.length == 0) {
      System.err.println("Usage: RuntimeCompiler mipsbinary");
      System.exit(1);
    }
    UnixRuntime localUnixRuntime = (UnixRuntime)compile(new Seekable.File(paramArrayOfString[0]), "unixruntime").newInstance();
    System.err.println("Instansiated: " + localUnixRuntime);
    System.exit(UnixRuntime.runAndExec(localUnixRuntime, paramArrayOfString));
  }
}
