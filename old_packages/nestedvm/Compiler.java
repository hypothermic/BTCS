package org.ibex.nestedvm;

import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.util.Hashtable;
import java.util.StringTokenizer;
import org.ibex.nestedvm.util.ELF;
import org.ibex.nestedvm.util.ELF.ELFHeader;
import org.ibex.nestedvm.util.ELF.SHeader;
import org.ibex.nestedvm.util.ELF.Symbol;
import org.ibex.nestedvm.util.ELF.Symtab;

public abstract class Compiler implements Registers
{
  ELF elf;
  final String fullClassName;
  String source = "unknown.mips.binary";
  public void setSource(String paramString) { this.source = paramString; }
  
  static class Exn extends Exception {
    public Exn(String paramString) { super(); }
  }
  


  boolean fastMem = true;
  




  int maxInsnPerMethod = 128;
  int maxBytesPerMethod;
  int methodMask;
  int methodShift;
  
  void maxInsnPerMethodInit() throws Compiler.Exn
  {
    if ((this.maxInsnPerMethod & this.maxInsnPerMethod - 1) != 0) throw new Exn("maxBytesPerMethod is not a power of two");
    this.maxBytesPerMethod = (this.maxInsnPerMethod * 4);
    this.methodMask = (this.maxBytesPerMethod - 1 ^ 0xFFFFFFFF);
    while (this.maxBytesPerMethod >>> this.methodShift != 1) { this.methodShift += 1;
    }
  }
  
  boolean pruneCases = true;
  
  boolean assumeTailCalls = true;
  

  boolean debugCompiler = false;
  

  boolean printStats = false;
  

  boolean runtimeStats = false;
  
  boolean supportCall = true;
  
  boolean nullPointerCheck = false;
  
  String runtimeClass = "org.ibex.nestedvm.Runtime";
  
  String hashClass = "java.util.Hashtable";
  
  boolean unixRuntime;
  
  boolean lessConstants;
  
  boolean singleFloat;
  
  int pageSize = 4096;
  int totalPages = 65536;
  int pageShift;
  boolean onePage;
  Hashtable jumpableAddresses;
  
  void pageSizeInit() throws Compiler.Exn { if ((this.pageSize & this.pageSize - 1) != 0) throw new Exn("pageSize not a multiple of two");
    if ((this.totalPages & this.totalPages - 1) != 0) throw new Exn("totalPages not a multiple of two");
    while (this.pageSize >>> this.pageShift != 1) { this.pageShift += 1;
    }
  }
  

  ELF.Symbol userInfo;
  ELF.Symbol gp;
  private boolean used;
  private static void usage()
  {
    System.err.println("Usage: java Compiler [-outfile output.java] [-o options] [-dumpoptions] <classname> <binary.mips>");
    System.err.println("-o takes mount(8) like options and can be specified multiple times");
    System.err.println("Available options:");
    for (int i = 0; i < options.length; i += 2)
      System.err.print(options[i] + ": " + wrapAndIndent(options[(i + 1)], 16 - options[i].length(), 18, 62));
    System.exit(1);
  }
  
  public static void main(String[] paramArrayOfString) throws IOException {
    String str1 = null;
    String str2 = null;
    String str3 = null;
    String str4 = null;
    String str5 = null;
    String str6 = null;
    int i = 0;
    int j = 0;
    while (paramArrayOfString.length - j > 0) {
      if (paramArrayOfString[j].equals("-outfile")) {
        j++;
        if (j == paramArrayOfString.length) usage();
        str1 = paramArrayOfString[j];
      } else if (paramArrayOfString[j].equals("-d")) {
        j++;
        if (j == paramArrayOfString.length) usage();
        str2 = paramArrayOfString[j];
      } else if (paramArrayOfString[j].equals("-outformat")) {
        j++;
        if (j == paramArrayOfString.length) usage();
        str6 = paramArrayOfString[j];
      } else if (paramArrayOfString[j].equals("-o")) {
        j++;
        if (j == paramArrayOfString.length) usage();
        if ((str3 == null) || (str3.length() == 0)) {
          str3 = paramArrayOfString[j];
        } else if (paramArrayOfString[j].length() != 0)
          str3 = str3 + "," + paramArrayOfString[j];
      } else if (paramArrayOfString[j].equals("-dumpoptions")) {
        i = 1;
      } else if (str4 == null) {
        str4 = paramArrayOfString[j];
      } else if (str5 == null) {
        str5 = paramArrayOfString[j];
      } else {
        usage();
      }
      j++;
    }
    if ((str4 == null) || (str5 == null)) { usage();
    }
    org.ibex.nestedvm.util.Seekable.File localFile = new org.ibex.nestedvm.util.Seekable.File(str5);
    
    java.io.FileWriter localFileWriter = null;
    java.io.FileOutputStream localFileOutputStream = null;
    Object localObject1 = null;
    if ((str6 == null) || (str6.equals("class"))) {
      if (str1 != null) {
        localFileOutputStream = new java.io.FileOutputStream(str1);
        localObject1 = new ClassFileCompiler(localFile, str4, localFileOutputStream);
      } else if (str2 != null) {
        File localFile1 = new File(str2);
        if (!localFile1.isDirectory()) {
          System.err.println(str2 + " doesn't exist or is not a directory");
          System.exit(1);
        }
        localObject1 = new ClassFileCompiler(localFile, str4, localFile1);
      } else {
        System.err.println("Refusing to write a classfile to stdout - use -outfile foo.class");
        System.exit(1);
      }
    } else if ((str6.equals("javasource")) || (str6.equals("java"))) {
      localFileWriter = str1 == null ? new java.io.OutputStreamWriter(System.out) : new java.io.FileWriter(str1);
      localObject1 = new JavaSourceCompiler(localFile, str4, localFileWriter);
    } else {
      System.err.println("Unknown output format: " + str6);
      System.exit(1);
    }
    
    ((Compiler)localObject1).parseOptions(str3);
    ((Compiler)localObject1).setSource(str5);
    
    if (i != 0) {
      System.err.println("== Options ==");
      for (int k = 0; k < options.length; k += 2)
        System.err.println(options[k] + ": " + ((Compiler)localObject1).getOption(options[k]).get());
      System.err.println("== End Options ==");
    }
    try
    {
      ((Compiler)localObject1).go();
    } catch (Exn localExn) {
      System.err.println("Compiler Error: " + localExn.getMessage());
      System.exit(1);
    } finally {
      if (localFileWriter != null) localFileWriter.close();
      if (localFileOutputStream != null) localFileOutputStream.close();
    }
  }
  
  public Compiler(org.ibex.nestedvm.util.Seekable paramSeekable, String paramString) throws IOException {
    this.fullClassName = paramString;
    this.elf = new ELF(paramSeekable);
    
    if (this.elf.header.type != 2) throw new IOException("Binary is not an executable");
    if (this.elf.header.machine != 8) throw new IOException("Binary is not for the MIPS I Architecture");
    if (this.elf.ident.data != 2) throw new IOException("Binary is not big endian");
  }
  
  abstract void _go() throws Compiler.Exn, IOException;
  
  public void go() throws Compiler.Exn, IOException
  {
    if (this.used) throw new RuntimeException("Compiler instances are good for one shot only");
    this.used = true;
    
    if ((this.onePage) && (this.pageSize <= 4096)) this.pageSize = 4194304;
    if ((this.nullPointerCheck) && (!this.fastMem)) throw new Exn("fastMem must be enabled for nullPointerCheck to be of any use");
    if ((this.onePage) && (!this.fastMem)) throw new Exn("fastMem must be enabled for onePage to be of any use");
    if ((this.totalPages == 1) && (!this.onePage)) throw new Exn("totalPages == 1 and onePage is not set");
    if (this.onePage) { this.totalPages = 1;
    }
    maxInsnPerMethodInit();
    pageSizeInit();
    

    ELF.Symtab localSymtab = this.elf.getSymtab();
    if (localSymtab == null) { throw new Exn("Binary has no symtab (did you strip it?)");
    }
    
    this.userInfo = localSymtab.getGlobalSymbol("user_info");
    this.gp = localSymtab.getGlobalSymbol("_gp");
    if (this.gp == null) { throw new Exn("no _gp symbol (did you strip the binary?)");
    }
    if (this.pruneCases)
    {
      this.jumpableAddresses = new Hashtable();
      
      this.jumpableAddresses.put(new Integer(this.elf.header.entry), Boolean.TRUE);
      
      ELF.SHeader localSHeader1 = this.elf.sectionWithName(".text");
      if (localSHeader1 == null) { throw new Exn("No .text segment");
      }
      findBranchesInSymtab(localSymtab, this.jumpableAddresses);
      
      for (int j = 0; j < this.elf.sheaders.length; j++) {
        ELF.SHeader localSHeader2 = this.elf.sheaders[j];
        String str2 = localSHeader2.name;
        
        if ((localSHeader2.addr != 0) && (
          (str2.equals(".data")) || (str2.equals(".sdata")) || (str2.equals(".rodata")) || (str2.equals(".ctors")) || (str2.equals(".dtors")))) {
          findBranchesInData(new DataInputStream(localSHeader2.getInputStream()), localSHeader2.size, this.jumpableAddresses, localSHeader1.addr, localSHeader1.addr + localSHeader1.size);
        }
      }
      findBranchesInText(localSHeader1.addr, new DataInputStream(localSHeader1.getInputStream()), localSHeader1.size, this.jumpableAddresses);
    }
    
    if ((this.unixRuntime) && (this.runtimeClass.startsWith("org.ibex.nestedvm."))) { this.runtimeClass = "org.ibex.nestedvm.UnixRuntime";
    }
    for (int i = 0; i < this.elf.sheaders.length; i++) {
      String str1 = this.elf.sheaders[i].name;
      if (((this.elf.sheaders[i].flags & 0x2) != 0) && (!str1.equals(".text")) && (!str1.equals(".data")) && (!str1.equals(".sdata")) && (!str1.equals(".rodata")) && (!str1.equals(".ctors")) && (!str1.equals(".dtors")) && (!str1.equals(".bss")) && (!str1.equals(".sbss")))
      {

        throw new Exn("Unknown section: " + str1); }
    }
    _go();
  }
  
  private void findBranchesInSymtab(ELF.Symtab paramSymtab, Hashtable paramHashtable) {
    ELF.Symbol[] arrayOfSymbol = paramSymtab.symbols;
    int i = 0;
    for (int j = 0; j < arrayOfSymbol.length; j++) {
      ELF.Symbol localSymbol = arrayOfSymbol[j];
      if ((localSymbol.type == 2) && 
        (paramHashtable.put(new Integer(localSymbol.addr), Boolean.TRUE) == null))
      {
        i++;
      }
    }
    
    if (this.printStats) System.err.println("Found " + i + " additional possible branch targets in Symtab");
  }
  
  private void findBranchesInText(int paramInt1, DataInputStream paramDataInputStream, int paramInt2, Hashtable paramHashtable) throws IOException {
    int i = paramInt2 / 4;
    int j = paramInt1;
    int k = 0;
    int[] arrayOfInt1 = new int[32];
    int[] arrayOfInt2 = new int[32];
    

    for (int m = 0; m < i; j += 4) {
      int n = paramDataInputStream.readInt();
      int i1 = n >>> 26 & 0xFF;
      int i2 = n >>> 21 & 0x1F;
      int i3 = n >>> 16 & 0x1F;
      int i4 = n << 16 >> 16;
      int i5 = n & 0xFFFF;
      int i6 = i4;
      int i7 = n & 0x3FFFFFF;
      int i8 = n & 0x3F;
      
      switch (i1) {
      case 0: 
        switch (i8) {
        case 9: 
          if (paramHashtable.put(new Integer(j + 8), Boolean.TRUE) == null) k++;
          break;
        case 12: 
          if (paramHashtable.put(new Integer(j + 4), Boolean.TRUE) == null) k++;
          break;
        }
        break;
      case 1: 
        switch (i3) {
        case 16: 
        case 17: 
          if (paramHashtable.put(new Integer(j + 8), Boolean.TRUE) == null) { k++;
          }
        case 0: 
        case 1: 
          if (paramHashtable.put(new Integer(j + i6 * 4 + 4), Boolean.TRUE) == null) k++;
          break;
        }
        break;
      case 3: 
        if (paramHashtable.put(new Integer(j + 8), Boolean.TRUE) == null) { k++;
        }
      case 2: 
        if (paramHashtable.put(new Integer(j & 0xF0000000 | i7 << 2), Boolean.TRUE) == null) k++;
        break;
      case 4: 
      case 5: 
      case 6: 
      case 7: 
        if (paramHashtable.put(new Integer(j + i6 * 4 + 4), Boolean.TRUE) == null) k++;
        break;
      case 9: 
        if (j - arrayOfInt2[i2] <= 128) {
          int i9 = (arrayOfInt1[i2] << 16) + i4;
          if (((i9 & 0x3) == 0) && (i9 >= paramInt1) && (i9 < paramInt1 + paramInt2) && 
            (paramHashtable.put(new Integer(i9), Boolean.TRUE) == null))
          {
            k++;
          }
          

          if (i3 == i2) arrayOfInt2[i2] = 0; }
        break;
      

      case 15: 
        arrayOfInt1[i3] = i5;
        arrayOfInt2[i3] = j;
        break;
      

      case 17: 
        switch (i2) {
        case 8: 
          if (paramHashtable.put(new Integer(j + i6 * 4 + 4), Boolean.TRUE) == null) { k++;
          }
          break;
        }
        break;
      }
      m++;
    }
    








































































    paramDataInputStream.close();
    if (this.printStats) System.err.println("Found " + k + " additional possible branch targets in Text segment");
  }
  
  private void findBranchesInData(DataInputStream paramDataInputStream, int paramInt1, Hashtable paramHashtable, int paramInt2, int paramInt3) throws IOException {
    int i = paramInt1 / 4;
    int j = 0;
    for (int k = 0; k < i; k++) {
      int m = paramDataInputStream.readInt();
      if (((m & 0x3) == 0) && (m >= paramInt2) && (m < paramInt3) && 
        (paramHashtable.put(new Integer(m), Boolean.TRUE) == null))
      {
        j++;
      }
    }
    
    paramDataInputStream.close();
    if ((j > 0) && (this.printStats)) { System.err.println("Found " + j + " additional possible branch targets in Data segment");
    }
  }
  
  static final String toHex(int paramInt) { return "0x" + Long.toString(paramInt & 0xFFFFFFFF, 16); }
  
  static final String toHex8(int paramInt) { String str = Long.toString(paramInt & 0xFFFFFFFF, 16);
    StringBuffer localStringBuffer = new StringBuffer("0x");
    for (int i = 8 - str.length(); i > 0; i--) localStringBuffer.append('0');
    localStringBuffer.append(str);
    return localStringBuffer.toString();
  }
  
  static final String toOctal3(int paramInt) {
    char[] arrayOfChar = new char[3];
    for (int i = 2; i >= 0; i--) {
      arrayOfChar[i] = ((char)(48 + (paramInt & 0x7)));
      paramInt >>= 3;
    }
    return new String(arrayOfChar);
  }
  
  private class Option {
    private Field field;
    
    public Option(String paramString) throws NoSuchFieldException { this.field = (paramString == null ? null : Compiler.class.getDeclaredField(paramString)); }
    
    public void set(Object paramObject) { if (this.field == null) return;
      try
      {
        this.field.set(Compiler.this, paramObject);
      } catch (IllegalAccessException localIllegalAccessException) {
        System.err.println(localIllegalAccessException);
      }
    }
    
    public Object get() { if (this.field == null) return null;
      try
      {
        return this.field.get(Compiler.this);
      } catch (IllegalAccessException localIllegalAccessException) {
        System.err.println(localIllegalAccessException); } return null;
    }
    
    public Class getType() { return this.field == null ? null : this.field.getType(); }
  }
  
  private static String[] options = { "fastMem", "Enable fast memory access - RuntimeExceptions will be thrown on faults", "nullPointerCheck", "Enables checking at runtime for null pointer accessses (slows things down a bit, only applicable with fastMem)", "maxInsnPerMethod", "Maximum number of MIPS instructions per java method (128 is optimal with Hotspot)", "pruneCases", "Remove unnecessary case 0xAABCCDD blocks from methods - may break some weird code", "assumeTailCalls", "Assume the JIT optimizes tail calls", "optimizedMemcpy", "Use an optimized java version of memcpy where possible", "debugCompiler", "Output information in the generated code for debugging the compiler - will slow down generated code significantly", "printStats", "Output some useful statistics about the compilation", "runtimeStats", "Keep track of some statistics at runtime in the generated code - will slow down generated code significantly", "supportCall", "Keep a stripped down version of the symbol table in the generated code to support the call() method", "runtimeClass", "Full classname of the Runtime class (default: Runtime) - use this is you put Runtime in a package", "hashClass", "Full classname of a Hashtable class (default: java.util.HashMap) - this must support get() and put()", "unixRuntime", "Use the UnixRuntime (has support for fork, wai, du, pipe, etc)", "pageSize", "The page size (must be a power of two)", "totalPages", "Total number of pages (total mem = pageSize*totalPages, must be a power of two)", "onePage", "One page hack (FIXME: document this better)", "lessConstants", "Use less constants at the cost of speed (FIXME: document this better)", "singleFloat", "Support single precision (32-bit) FP ops only" };
  


















  private Option getOption(String paramString)
  {
    paramString = paramString.toLowerCase();
    try {
      for (int i = 0; i < options.length; i += 2)
        if (options[i].toLowerCase().equals(paramString))
          return new Option(options[i]);
      return null;
    } catch (NoSuchFieldException localNoSuchFieldException) {}
    return null;
  }
  
  public void parseOptions(String paramString)
  {
    if ((paramString == null) || (paramString.length() == 0)) return;
    StringTokenizer localStringTokenizer = new StringTokenizer(paramString, ",");
    while (localStringTokenizer.hasMoreElements()) {
      String str1 = localStringTokenizer.nextToken();
      String str2;
      String str3;
      if (str1.indexOf("=") != -1) {
        str2 = str1.substring(0, str1.indexOf("="));
        str3 = str1.substring(str1.indexOf("=") + 1);
      } else if (str1.startsWith("no")) {
        str2 = str1.substring(2);
        str3 = "false";
      } else {
        str2 = str1;
        str3 = "true";
      }
      Option localOption = getOption(str2);
      if (localOption == null) {
        System.err.println("WARNING: No such option: " + str2);


      }
      else if (localOption.getType() == String.class) {
        localOption.set(str3);
      } else if (localOption.getType() == Integer.TYPE) {
        try {
          localOption.set(parseInt(str3));
        } catch (NumberFormatException localNumberFormatException) {
          System.err.println("WARNING: " + str3 + " is not an integer");
        }
      } else if (localOption.getType() == Boolean.TYPE) {
        localOption.set(new Boolean((str3.toLowerCase().equals("true")) || (str3.toLowerCase().equals("yes"))));
      } else
        throw new Error("Unknown type: " + localOption.getType());
    }
  }
  
  private static Integer parseInt(String paramString) {
    int i = 1;
    paramString = paramString.toLowerCase();
    if ((!paramString.startsWith("0x")) && (paramString.endsWith("m"))) { paramString = paramString.substring(0, paramString.length() - 1);i = 1048576;
    } else if ((!paramString.startsWith("0x")) && (paramString.endsWith("k"))) { paramString = paramString.substring(0, paramString.length() - 1);i = 1024; }
    int j;
    if ((paramString.length() > 2) && (paramString.startsWith("0x"))) j = Integer.parseInt(paramString.substring(2), 16); else
      j = Integer.parseInt(paramString);
    return new Integer(j * i);
  }
  
  private static String wrapAndIndent(String paramString, int paramInt1, int paramInt2, int paramInt3) {
    StringTokenizer localStringTokenizer = new StringTokenizer(paramString, " ");
    StringBuffer localStringBuffer = new StringBuffer();
    for (int i = 0; i < paramInt1; i++)
      localStringBuffer.append(' ');
    i = 0;
    while (localStringTokenizer.hasMoreTokens()) {
      String str = localStringTokenizer.nextToken();
      if ((str.length() + i + 1 > paramInt3) && (i > 0)) {
        localStringBuffer.append('\n');
        for (int j = 0; j < paramInt2; j++) localStringBuffer.append(' ');
        i = 0;
      } else if (i > 0) {
        localStringBuffer.append(' ');
        i++;
      }
      localStringBuffer.append(str);
      i += str.length();
    }
    localStringBuffer.append('\n');
    return localStringBuffer.toString();
  }
  
  static String dateTime()
  {
    try
    {
      return new java.util.Date().toString();
    } catch (RuntimeException localRuntimeException) {}
    return "<unknown>";
  }
}
