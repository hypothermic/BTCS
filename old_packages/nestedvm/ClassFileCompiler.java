package org.ibex.nestedvm;

import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Hashtable;
import org.ibex.classgen.CGConst;
import org.ibex.classgen.ClassFile;
import org.ibex.classgen.ClassFile.Exn;
import org.ibex.classgen.MethodGen;
import org.ibex.classgen.MethodGen.Pair;
import org.ibex.classgen.MethodGen.PhantomTarget;
import org.ibex.classgen.MethodGen.Switch.Lookup;
import org.ibex.classgen.MethodGen.Switch.Table;
import org.ibex.classgen.Type;
import org.ibex.classgen.Type.Class;
import org.ibex.nestedvm.util.ELF;
import org.ibex.nestedvm.util.ELF.ELFHeader;
import org.ibex.nestedvm.util.ELF.SHeader;
import org.ibex.nestedvm.util.ELF.Symbol;
import org.ibex.nestedvm.util.ELF.Symtab;
import org.ibex.nestedvm.util.Seekable;
import org.ibex.nestedvm.util.Seekable.File;












public class ClassFileCompiler
  extends Compiler
  implements CGConst
{
  private static final boolean OPTIMIZE_CP = true;
  private OutputStream os;
  private File outDir;
  private PrintStream warn = System.err;
  private final Type.Class me;
  private ClassFile cg;
  private MethodGen clinit;
  private MethodGen init;
  private static int initDataCount;
  
  public ClassFileCompiler(String paramString1, String paramString2, OutputStream paramOutputStream) throws IOException { this(new Seekable.File(paramString1), paramString2, paramOutputStream); }
  
  public ClassFileCompiler(Seekable paramSeekable, String paramString, OutputStream paramOutputStream) throws IOException { this(paramSeekable, paramString);
    if (paramOutputStream == null) throw new NullPointerException();
    this.os = paramOutputStream;
  }
  
  public ClassFileCompiler(Seekable paramSeekable, String paramString, File paramFile) throws IOException { this(paramSeekable, paramString);
    if (paramFile == null) throw new NullPointerException();
    this.outDir = paramFile;
  }
  
  private ClassFileCompiler(Seekable paramSeekable, String paramString) throws IOException { super(paramSeekable, paramString);
    this.me = Type.Class.instance(this.fullClassName);
  }
  
  public void setWarnWriter(PrintStream paramPrintStream) { this.warn = paramPrintStream; }
  
  protected void _go() throws Compiler.Exn, IOException {
    try {
      __go();
    } catch (ClassFile.Exn localExn) {
      localExn.printStackTrace(this.warn);
      throw new Compiler.Exn("Class generation exception: " + localExn.toString());
    }
  }
  
  private void __go() throws Compiler.Exn, IOException {
    if (!this.pruneCases) { throw new Compiler.Exn("-o prunecases MUST be enabled for ClassFileCompiler");
    }
    
    Type.Class localClass1 = Type.Class.instance(this.runtimeClass);
    this.cg = new ClassFile(this.me, localClass1, 49);
    if (this.source != null) { this.cg.setSourceFile(this.source);
    }
    
    this.cg.addField("pc", Type.INT, 2);
    this.cg.addField("hi", Type.INT, 2);
    this.cg.addField("lo", Type.INT, 2);
    this.cg.addField("fcsr", Type.INT, 2);
    for (int i = 1; i < 32; i++) this.cg.addField("r" + i, Type.INT, 2);
    for (i = 0; i < 32; i++) { this.cg.addField("f" + i, this.singleFloat ? Type.FLOAT : Type.INT, 2);
    }
    
    this.clinit = this.cg.addMethod("<clinit>", Type.VOID, Type.NO_ARGS, 10);
    



    this.init = this.cg.addMethod("<init>", Type.VOID, Type.NO_ARGS, 1);
    this.init.add((byte)42);
    this.init.add((byte)18, this.pageSize);
    this.init.add((byte)18, this.totalPages);
    this.init.add((byte)-73, this.me.method("<init>", Type.VOID, new Type[] { Type.INT, Type.INT }));
    this.init.add((byte)-79);
    

    this.init = this.cg.addMethod("<init>", Type.VOID, new Type[] { Type.BOOLEAN }, 1);
    this.init.add((byte)42);
    this.init.add((byte)18, this.pageSize);
    this.init.add((byte)18, this.totalPages);
    this.init.add((byte)27);
    this.init.add((byte)-73, this.me.method("<init>", Type.VOID, new Type[] { Type.INT, Type.INT, Type.BOOLEAN }));
    this.init.add((byte)-79);
    

    this.init = this.cg.addMethod("<init>", Type.VOID, new Type[] { Type.INT, Type.INT }, 1);
    this.init.add((byte)42);
    this.init.add((byte)27);
    this.init.add((byte)28);
    this.init.add((byte)3);
    this.init.add((byte)-73, this.me.method("<init>", Type.VOID, new Type[] { Type.INT, Type.INT, Type.BOOLEAN }));
    this.init.add((byte)-79);
    

    this.init = this.cg.addMethod("<init>", Type.VOID, new Type[] { Type.INT, Type.INT, Type.BOOLEAN }, 1);
    this.init.add((byte)42);
    this.init.add((byte)27);
    this.init.add((byte)28);
    this.init.add((byte)29);
    this.init.add((byte)-73, localClass1.method("<init>", Type.VOID, new Type[] { Type.INT, Type.INT, Type.BOOLEAN }));
    
    if (this.onePage) {
      this.cg.addField("page", Type.INT.makeArray(), 18);
      this.init.add((byte)42);
      this.init.add((byte)89);
      this.init.add((byte)-76, this.me.field("readPages", Type.INT.makeArray(2)));
      this.init.add((byte)18, 0);
      this.init.add((byte)50);
      this.init.add((byte)-75, this.me.field("page", Type.INT.makeArray()));
    }
    
    if (this.supportCall) {
      this.cg.addField("symbols", Type.Class.instance(this.hashClass), 26);
    }
    i = 0;
    
    for (int j = 0; j < this.elf.sheaders.length; j++) {
      localObject2 = this.elf.sheaders[j];
      String str = ((ELF.SHeader)localObject2).name;
      
      if (((ELF.SHeader)localObject2).addr != 0)
      {
        i = Math.max(i, ((ELF.SHeader)localObject2).addr + ((ELF.SHeader)localObject2).size);
        
        if (str.equals(".text")) {
          emitText(((ELF.SHeader)localObject2).addr, new DataInputStream(((ELF.SHeader)localObject2).getInputStream()), ((ELF.SHeader)localObject2).size);
        } else if ((str.equals(".data")) || (str.equals(".sdata")) || (str.equals(".rodata")) || (str.equals(".ctors")) || (str.equals(".dtors"))) {
          emitData(((ELF.SHeader)localObject2).addr, new DataInputStream(((ELF.SHeader)localObject2).getInputStream()), ((ELF.SHeader)localObject2).size, str.equals(".rodata"));
        } else if ((str.equals(".bss")) || (str.equals(".sbss"))) {
          emitBSS(((ELF.SHeader)localObject2).addr, ((ELF.SHeader)localObject2).size);
        } else {
          throw new Compiler.Exn("Unknown segment: " + str);
        }
      }
    }
    this.init.add((byte)-79);
    

    if (this.supportCall) {
      localObject1 = Type.Class.instance(this.hashClass);
      this.clinit.add((byte)-69, localObject1);
      this.clinit.add((byte)89);
      this.clinit.add((byte)89);
      this.clinit.add((byte)-73, ((Type.Class)localObject1).method("<init>", Type.VOID, Type.NO_ARGS));
      this.clinit.add((byte)-77, this.me.field("symbols", (Type)localObject1));
      localObject2 = this.elf.getSymtab().symbols;
      for (k = 0; k < localObject2.length; k++) {
        Object localObject3 = localObject2[k];
        if ((((ELF.Symbol)localObject3).type == 2) && (((ELF.Symbol)localObject3).binding == 1) && ((((ELF.Symbol)localObject3).name.equals("_call_helper")) || (!((ELF.Symbol)localObject3).name.startsWith("_")))) {
          this.clinit.add((byte)89);
          this.clinit.add((byte)18, ((ELF.Symbol)localObject3).name);
          this.clinit.add((byte)-69, Type.INTEGER_OBJECT);
          this.clinit.add((byte)89);
          this.clinit.add((byte)18, ((ELF.Symbol)localObject3).addr);
          this.clinit.add((byte)-73, Type.INTEGER_OBJECT.method("<init>", Type.VOID, new Type[] { Type.INT }));
          this.clinit.add((byte)-74, ((Type.Class)localObject1).method("put", Type.OBJECT, new Type[] { Type.OBJECT, Type.OBJECT }));
          this.clinit.add((byte)87);
        }
      }
      this.clinit.add((byte)87);
    }
    
    this.clinit.add((byte)-79);
    
    Object localObject1 = this.elf.sectionWithName(".text");
    

    Object localObject2 = this.cg.addMethod("trampoline", Type.VOID, Type.NO_ARGS, 2);
    
    int k = ((MethodGen)localObject2).size();
    ((MethodGen)localObject2).add((byte)42);
    ((MethodGen)localObject2).add((byte)-76, this.me.field("state", Type.INT));
    ((MethodGen)localObject2).add((byte)-103, ((MethodGen)localObject2).size() + 2);
    ((MethodGen)localObject2).add((byte)-79);
    
    ((MethodGen)localObject2).add((byte)42);
    ((MethodGen)localObject2).add((byte)42);
    ((MethodGen)localObject2).add((byte)-76, this.me.field("pc", Type.INT));
    ((MethodGen)localObject2).add((byte)18, this.methodShift);
    ((MethodGen)localObject2).add((byte)124);
    
    int m = ((ELF.SHeader)localObject1).addr >>> this.methodShift;
    int n = ((ELF.SHeader)localObject1).addr + ((ELF.SHeader)localObject1).size + this.maxBytesPerMethod - 1 >>> this.methodShift;
    
    MethodGen.Switch.Table localTable = new MethodGen.Switch.Table(m, n - 1);
    ((MethodGen)localObject2).add((byte)-86, localTable);
    for (int i1 = m; i1 < n; i1++) {
      localTable.setTargetForVal(i1, ((MethodGen)localObject2).size());
      ((MethodGen)localObject2).add((byte)-73, this.me.method("run_" + toHex(i1 << this.methodShift), Type.VOID, Type.NO_ARGS));
      ((MethodGen)localObject2).add((byte)-89, k);
    }
    localTable.setDefaultTarget(((MethodGen)localObject2).size());
    
    ((MethodGen)localObject2).add((byte)87);
    ((MethodGen)localObject2).add((byte)-69, Type.Class.instance("org.ibex.nestedvm.Runtime$ExecutionException"));
    ((MethodGen)localObject2).add((byte)89);
    ((MethodGen)localObject2).add((byte)-69, Type.STRINGBUFFER);
    ((MethodGen)localObject2).add((byte)89);
    ((MethodGen)localObject2).add((byte)18, "Jumped to invalid address in trampoline (r2: ");
    ((MethodGen)localObject2).add((byte)-73, Type.STRINGBUFFER.method("<init>", Type.VOID, new Type[] { Type.STRING }));
    ((MethodGen)localObject2).add((byte)42);
    ((MethodGen)localObject2).add((byte)-76, this.me.field("r2", Type.INT));
    ((MethodGen)localObject2).add((byte)-74, Type.STRINGBUFFER.method("append", Type.STRINGBUFFER, new Type[] { Type.INT }));
    ((MethodGen)localObject2).add((byte)18, " pc: ");
    ((MethodGen)localObject2).add((byte)-74, Type.STRINGBUFFER.method("append", Type.STRINGBUFFER, new Type[] { Type.STRING }));
    ((MethodGen)localObject2).add((byte)42);
    ((MethodGen)localObject2).add((byte)-76, this.me.field("pc", Type.INT));
    ((MethodGen)localObject2).add((byte)-74, Type.STRINGBUFFER.method("append", Type.STRINGBUFFER, new Type[] { Type.INT }));
    ((MethodGen)localObject2).add((byte)18, ")");
    ((MethodGen)localObject2).add((byte)-74, Type.STRINGBUFFER.method("append", Type.STRINGBUFFER, new Type[] { Type.STRING }));
    ((MethodGen)localObject2).add((byte)-74, Type.STRINGBUFFER.method("toString", Type.STRING, Type.NO_ARGS));
    
    ((MethodGen)localObject2).add((byte)-73, Type.Class.instance("org.ibex.nestedvm.Runtime$ExecutionException").method("<init>", Type.VOID, new Type[] { Type.STRING }));
    ((MethodGen)localObject2).add((byte)-65);
    
    addConstReturnMethod("gp", this.gp.addr);
    addConstReturnMethod("entryPoint", this.elf.header.entry);
    addConstReturnMethod("heapStart", i);
    
    if (this.userInfo != null) {
      addConstReturnMethod("userInfoBase", this.userInfo.addr);
      addConstReturnMethod("userInfoSize", this.userInfo.size);
    }
    
    if (this.supportCall) {
      localClass2 = Type.Class.instance(this.hashClass);
      localMethodGen1 = this.cg.addMethod("lookupSymbol", Type.INT, new Type[] { Type.STRING }, 4);
      localMethodGen1.add((byte)-78, this.me.field("symbols", localClass2));
      localMethodGen1.add((byte)43);
      localMethodGen1.add((byte)-74, localClass2.method("get", Type.OBJECT, new Type[] { Type.OBJECT }));
      localMethodGen1.add((byte)89);
      int i2 = localMethodGen1.add((byte)-58);
      localMethodGen1.add((byte)-64, Type.INTEGER_OBJECT);
      localMethodGen1.add((byte)-74, Type.INTEGER_OBJECT.method("intValue", Type.INT, Type.NO_ARGS));
      localMethodGen1.add((byte)-84);
      localMethodGen1.setArg(i2, localMethodGen1.size());
      localMethodGen1.add((byte)87);
      localMethodGen1.add((byte)2);
      localMethodGen1.add((byte)-84);
    }
    


    Type.Class localClass2 = Type.Class.instance("org.ibex.nestedvm.Runtime$CPUState");
    MethodGen localMethodGen1 = this.cg.addMethod("setCPUState", Type.VOID, new Type[] { localClass2 }, 4);
    MethodGen localMethodGen2 = this.cg.addMethod("getCPUState", Type.VOID, new Type[] { localClass2 }, 4);
    
    localMethodGen1.add((byte)43);
    localMethodGen2.add((byte)43);
    localMethodGen1.add((byte)-76, localClass2.field("r", Type.INT.makeArray()));
    localMethodGen2.add((byte)-76, localClass2.field("r", Type.INT.makeArray()));
    localMethodGen1.add((byte)77);
    localMethodGen2.add((byte)77);
    
    for (int i3 = 1; i3 < 32; i3++) {
      localMethodGen1.add((byte)42);
      localMethodGen1.add((byte)44);
      localMethodGen1.add((byte)18, i3);
      localMethodGen1.add((byte)46);
      localMethodGen1.add((byte)-75, this.me.field("r" + i3, Type.INT));
      
      localMethodGen2.add((byte)44);
      localMethodGen2.add((byte)18, i3);
      localMethodGen2.add((byte)42);
      localMethodGen2.add((byte)-76, this.me.field("r" + i3, Type.INT));
      localMethodGen2.add((byte)79);
    }
    
    localMethodGen1.add((byte)43);
    localMethodGen2.add((byte)43);
    localMethodGen1.add((byte)-76, localClass2.field("f", Type.INT.makeArray()));
    localMethodGen2.add((byte)-76, localClass2.field("f", Type.INT.makeArray()));
    localMethodGen1.add((byte)77);
    localMethodGen2.add((byte)77);
    
    for (i3 = 0; i3 < 32; i3++) {
      localMethodGen1.add((byte)42);
      localMethodGen1.add((byte)44);
      localMethodGen1.add((byte)18, i3);
      localMethodGen1.add((byte)46);
      if (this.singleFloat) localMethodGen1.add((byte)-72, Type.FLOAT_OBJECT.method("intBitsToFloat", Type.FLOAT, new Type[] { Type.INT }));
      localMethodGen1.add((byte)-75, this.me.field("f" + i3, this.singleFloat ? Type.FLOAT : Type.INT));
      
      localMethodGen2.add((byte)44);
      localMethodGen2.add((byte)18, i3);
      localMethodGen2.add((byte)42);
      localMethodGen2.add((byte)-76, this.me.field("f" + i3, this.singleFloat ? Type.FLOAT : Type.INT));
      if (this.singleFloat) localMethodGen2.add((byte)-72, Type.FLOAT_OBJECT.method("floatToIntBits", Type.INT, new Type[] { Type.FLOAT }));
      localMethodGen2.add((byte)79);
    }
    
    String[] arrayOfString = { "hi", "lo", "fcsr", "pc" };
    for (int i4 = 0; i4 < arrayOfString.length; i4++) {
      localMethodGen1.add((byte)42);
      localMethodGen1.add((byte)43);
      localMethodGen1.add((byte)-76, localClass2.field(arrayOfString[i4], Type.INT));
      localMethodGen1.add((byte)-75, this.me.field(arrayOfString[i4], Type.INT));
      
      localMethodGen2.add((byte)43);
      localMethodGen2.add((byte)42);
      localMethodGen2.add((byte)-76, this.me.field(arrayOfString[i4], Type.INT));
      localMethodGen2.add((byte)-75, localClass2.field(arrayOfString[i4], Type.INT));
    }
    localMethodGen1.add((byte)-79);
    localMethodGen2.add((byte)-79);
    

    MethodGen localMethodGen3 = this.cg.addMethod("_execute", Type.VOID, Type.NO_ARGS, 4);
    int i5 = localMethodGen3.size();
    localMethodGen3.add((byte)42);
    localMethodGen3.add((byte)-73, this.me.method("trampoline", Type.VOID, Type.NO_ARGS));
    int i6 = localMethodGen3.size();
    localMethodGen3.add((byte)-79);
    
    int i7 = localMethodGen3.size();
    localMethodGen3.add((byte)76);
    localMethodGen3.add((byte)-69, Type.Class.instance("org.ibex.nestedvm.Runtime$FaultException"));
    localMethodGen3.add((byte)89);
    localMethodGen3.add((byte)43);
    
    localMethodGen3.add((byte)-73, Type.Class.instance("org.ibex.nestedvm.Runtime$FaultException").method("<init>", Type.VOID, new Type[] { Type.Class.instance("java.lang.RuntimeException") }));
    localMethodGen3.add((byte)-65);
    
    localMethodGen3.addExceptionHandler(i5, i6, i7, Type.Class.instance("java.lang.RuntimeException"));
    localMethodGen3.addThrow(Type.Class.instance("org.ibex.nestedvm.Runtime$ExecutionException"));
    
    MethodGen localMethodGen4 = this.cg.addMethod("main", Type.VOID, new Type[] { Type.STRING.makeArray() }, 9);
    localMethodGen4.add((byte)-69, this.me);
    localMethodGen4.add((byte)89);
    localMethodGen4.add((byte)-73, this.me.method("<init>", Type.VOID, Type.NO_ARGS));
    localMethodGen4.add((byte)18, this.fullClassName);
    localMethodGen4.add((byte)42);
    if (this.unixRuntime) {
      Type.Class localClass3 = Type.Class.instance("org.ibex.nestedvm.UnixRuntime");
      
      localMethodGen4.add((byte)-72, localClass3.method("runAndExec", Type.INT, new Type[] { localClass3, Type.STRING, Type.STRING.makeArray() }));
    }
    else {
      localMethodGen4.add((byte)-74, this.me.method("run", Type.INT, new Type[] { Type.STRING, Type.STRING.makeArray() }));
    }
    localMethodGen4.add((byte)-72, Type.Class.instance("java.lang.System").method("exit", Type.VOID, new Type[] { Type.INT }));
    localMethodGen4.add((byte)-79);
    
    if (this.outDir != null) {
      if (!this.outDir.isDirectory()) throw new IOException("" + this.outDir + " isn't a directory");
      this.cg.dump(this.outDir);
    } else {
      this.cg.dump(this.os);
    }
  }
  
  private void addConstReturnMethod(String paramString, int paramInt) {
    MethodGen localMethodGen = this.cg.addMethod(paramString, Type.INT, Type.NO_ARGS, 4);
    localMethodGen.add((byte)18, paramInt);
    localMethodGen.add((byte)-84);
  }
  
  private void emitData(int paramInt1, DataInputStream paramDataInputStream, int paramInt2, boolean paramBoolean) throws Compiler.Exn, IOException
  {
    if (((paramInt1 & 0x3) != 0) || ((paramInt2 & 0x3) != 0)) throw new Compiler.Exn("Data section on weird boundaries");
    int i = paramInt1 + paramInt2;
    while (paramInt1 < i) {
      int j = Math.min(paramInt2, 28000);
      StringBuffer localStringBuffer = new StringBuffer();
      for (int k = 0; k < j; k += 7) {
        long l = 0L;
        for (int m = 0; m < 7; m++) {
          l <<= 8;
          int n = k + m < paramInt2 ? paramDataInputStream.readByte() : 1;
          l |= n & 0xFF;
        }
        for (m = 0; m < 8; m++)
          localStringBuffer.append((char)(int)(l >>> 7 * (7 - m) & 0x7F));
      }
      String str = "_data" + ++initDataCount;
      this.cg.addField(str, Type.INT.makeArray(), 26);
      
      this.clinit.add((byte)18, localStringBuffer.toString());
      this.clinit.add((byte)18, j / 4);
      
      this.clinit.add((byte)-72, Type.Class.instance("org.ibex.nestedvm.Runtime").method("decodeData", Type.INT.makeArray(), new Type[] { Type.STRING, Type.INT }));
      this.clinit.add((byte)-77, this.me.field(str, Type.INT.makeArray()));
      this.init.add((byte)42);
      this.init.add((byte)-78, this.me.field(str, Type.INT.makeArray()));
      this.init.add((byte)18, paramInt1);
      this.init.add((byte)18, paramBoolean ? 1 : 0);
      
      this.init.add((byte)-74, this.me.method("initPages", Type.VOID, new Type[] { Type.INT.makeArray(), Type.INT, Type.BOOLEAN }));
      
      paramInt1 += j;
      paramInt2 -= j;
    }
    paramDataInputStream.close();
  }
  
  private void emitBSS(int paramInt1, int paramInt2) throws Compiler.Exn {
    if ((paramInt1 & 0x3) != 0) throw new Compiler.Exn("BSS section on weird boundaries");
    paramInt2 = paramInt2 + 3 & 0xFFFFFFFC;
    int i = paramInt2 / 4;
    
    this.init.add((byte)42);
    this.init.add((byte)18, paramInt1);
    this.init.add((byte)18, i);
    
    this.init.add((byte)-74, this.me.method("clearPages", Type.VOID, new Type[] { Type.INT, Type.INT }));
  }
  

  private int startOfMethod = 0;
  private int endOfMethod = 0;
  
  private MethodGen.PhantomTarget returnTarget;
  
  private MethodGen.PhantomTarget defaultTarget;
  private MethodGen.PhantomTarget[] insnTargets;
  
  private boolean jumpable(int paramInt) { return this.jumpableAddresses.get(new Integer(paramInt)) != null; }
  
  private MethodGen mg;
  private static final int UNREACHABLE = 1;
  private static final int SKIP_NEXT = 2;
  private boolean textDone;
  private void emitText(int paramInt1, DataInputStream paramDataInputStream, int paramInt2) throws Compiler.Exn, IOException {
    if (this.textDone) throw new Compiler.Exn("Multiple text segments");
    this.textDone = true;
    
    if (((paramInt1 & 0x3) != 0) || ((paramInt2 & 0x3) != 0)) throw new Compiler.Exn("Section on weird boundaries");
    int i = paramInt2 / 4;
    int k = -1;
    
    int m = 1;
    boolean bool = false;
    
    for (int n = 0; n < i; paramInt1 += 4) {
      int j = m != 0 ? paramDataInputStream.readInt() : k;
      k = n == i - 1 ? -1 : paramDataInputStream.readInt();
      if (paramInt1 >= this.endOfMethod) { endMethod(paramInt1, bool);startMethod(paramInt1); }
      if (this.insnTargets[(n % this.maxInsnPerMethod)] != null) {
        this.insnTargets[(n % this.maxInsnPerMethod)].setTarget(this.mg.size());
        bool = false;
      } else { if (bool)
          break label304;
      }
      try {
        int i1 = emitInstruction(paramInt1, j, k);
        bool = (i1 & 0x1) != 0;
        m = (i1 & 0x2) != 0 ? 1 : 0;
      } catch (Compiler.Exn localExn) {
        localExn.printStackTrace(this.warn);
        this.warn.println("Exception at " + toHex(paramInt1));
        throw localExn;
      } catch (RuntimeException localRuntimeException) {
        this.warn.println("Exception at " + toHex(paramInt1));
        throw localRuntimeException;
      }
      if (m != 0) { paramInt1 += 4;n++;
      }
      label304:
      n++;
    }
    





















    endMethod(0, bool);
    paramDataInputStream.close();
  }
  
  private void startMethod(int paramInt) {
    this.startOfMethod = (paramInt & this.methodMask);
    this.endOfMethod = (this.startOfMethod + this.maxBytesPerMethod);
    
    this.mg = this.cg.addMethod("run_" + toHex(this.startOfMethod), Type.VOID, Type.NO_ARGS, 18);
    if (this.onePage) {
      this.mg.add((byte)42);
      this.mg.add((byte)-76, this.me.field("page", Type.INT.makeArray()));
      this.mg.add((byte)77);
    } else {
      this.mg.add((byte)42);
      this.mg.add((byte)-76, this.me.field("readPages", Type.INT.makeArray(2)));
      this.mg.add((byte)77);
      this.mg.add((byte)42);
      this.mg.add((byte)-76, this.me.field("writePages", Type.INT.makeArray(2)));
      this.mg.add((byte)78);
    }
    
    this.returnTarget = new MethodGen.PhantomTarget();
    this.insnTargets = new MethodGen.PhantomTarget[this.maxBytesPerMethod / 4];
    
    int[] arrayOfInt = new int[this.maxBytesPerMethod / 4];
    Object[] arrayOfObject = new Object[this.maxBytesPerMethod / 4];
    int i = 0;
    for (int j = paramInt; j < this.endOfMethod; j += 4) {
      if (jumpable(j)) {
        arrayOfObject[i] = (this.insnTargets[((j - this.startOfMethod) / 4)] = new MethodGen.PhantomTarget());
        arrayOfInt[i] = j;
        i++;
      }
    }
    
    MethodGen.Switch.Lookup localLookup = new MethodGen.Switch.Lookup(i);
    System.arraycopy(arrayOfInt, 0, localLookup.vals, 0, i);
    System.arraycopy(arrayOfObject, 0, localLookup.targets, 0, i);
    localLookup.setDefaultTarget(this.defaultTarget = new MethodGen.PhantomTarget());
    
    fixupRegsStart();
    
    this.mg.add((byte)42);
    this.mg.add((byte)-76, this.me.field("pc", Type.INT));
    this.mg.add((byte)-85, localLookup);
  }
  
  private void endMethod(int paramInt, boolean paramBoolean) {
    if (this.startOfMethod == 0) { return;
    }
    if (!paramBoolean) {
      preSetPC();
      this.mg.add((byte)18, paramInt);
      setPC();
      
      this.jumpableAddresses.put(new Integer(paramInt), Boolean.TRUE);
    }
    
    this.returnTarget.setTarget(this.mg.size());
    
    fixupRegsEnd();
    
    this.mg.add((byte)-79);
    
    this.defaultTarget.setTarget(this.mg.size());
    
    if (this.debugCompiler) {
      this.mg.add((byte)-69, Type.Class.instance("org.ibex.nestedvm.Runtime$ExecutionException"));
      this.mg.add((byte)89);
      this.mg.add((byte)-69, Type.STRINGBUFFER);
      this.mg.add((byte)89);
      this.mg.add((byte)18, "Jumped to invalid address: ");
      this.mg.add((byte)-73, Type.STRINGBUFFER.method("<init>", Type.VOID, new Type[] { Type.STRING }));
      this.mg.add((byte)42);
      this.mg.add((byte)-76, this.me.field("pc", Type.INT));
      this.mg.add((byte)-74, Type.STRINGBUFFER.method("append", Type.STRINGBUFFER, new Type[] { Type.INT }));
      this.mg.add((byte)-74, Type.STRINGBUFFER.method("toString", Type.STRING, Type.NO_ARGS));
      this.mg.add((byte)-73, Type.Class.instance("org.ibex.nestedvm.Runtime$ExecutionException").method("<init>", Type.VOID, new Type[] { Type.STRING }));
      this.mg.add((byte)-65);
    } else {
      this.mg.add((byte)-69, Type.Class.instance("org.ibex.nestedvm.Runtime$ExecutionException"));
      this.mg.add((byte)89);
      this.mg.add((byte)18, "Jumped to invalid address");
      this.mg.add((byte)-73, Type.Class.instance("org.ibex.nestedvm.Runtime$ExecutionException").method("<init>", Type.VOID, new Type[] { Type.STRING }));
      this.mg.add((byte)-65);
    }
    
    this.endOfMethod = (this.startOfMethod = 0);
  }
  
  private void leaveMethod()
  {
    this.mg.add((byte)-89, this.returnTarget);
  }
  
  private void link(int paramInt) {
    preSetReg(31);
    if (this.lessConstants) {
      int i = paramInt + 8 + 32768 & 0xFFFF0000;
      int j = paramInt + 8 - i;
      if ((j < 32768) || (j > 32767)) throw new Error("should never happen " + j);
      this.mg.add((byte)18, i);
      this.mg.add((byte)18, j);
      this.mg.add((byte)96);
    } else {
      this.mg.add((byte)18, paramInt + 8);
    }
    setReg();
  }
  
  private void branch(int paramInt1, int paramInt2) {
    if ((paramInt1 & this.methodMask) == (paramInt2 & this.methodMask)) {
      this.mg.add((byte)-89, this.insnTargets[((paramInt2 - this.startOfMethod) / 4)]);
    } else {
      preSetPC();
      this.mg.add((byte)18, paramInt2);
      setPC();
      leaveMethod();
    }
  }
  
  private int doIfInstruction(byte paramByte, int paramInt1, int paramInt2, int paramInt3) throws Compiler.Exn
  {
    emitInstruction(-1, paramInt3, -1);
    if ((paramInt2 & this.methodMask) == (paramInt1 & this.methodMask)) {
      this.mg.add(paramByte, this.insnTargets[((paramInt2 - this.startOfMethod) / 4)]);
    } else {
      i = this.mg.add(MethodGen.negate(paramByte));
      branch(paramInt1, paramInt2);
      this.mg.setArg(i, this.mg.size());
    }
    if (!jumpable(paramInt1 + 4)) { return 2;
    }
    
    if (paramInt1 + 4 == this.endOfMethod)
    {
      this.jumpableAddresses.put(new Integer(paramInt1 + 8), Boolean.TRUE);
      branch(paramInt1, paramInt1 + 8);
      


      return 1;
    }
    


    int i = this.mg.add((byte)-89);
    this.insnTargets[((paramInt1 + 4 - this.startOfMethod) / 4)].setTarget(this.mg.size());
    emitInstruction(-1, paramInt3, 1);
    this.mg.setArg(i, this.mg.size());
    
    return 2;
  }
  

  private static final Float POINT_5_F = new Float(0.5F);
  private static final Double POINT_5_D = new Double(0.5D);
  private static final Long FFFFFFFF = new Long(4294967295L);
  private static final int R = 0;
  
  private int emitInstruction(int paramInt1, int paramInt2, int paramInt3) throws Compiler.Exn { MethodGen localMethodGen = this.mg;
    if (paramInt2 == -1) { throw new Compiler.Exn("insn is -1");
    }
    int i = 0;
    
    int j = paramInt2 >>> 26 & 0xFF;
    int k = paramInt2 >>> 21 & 0x1F;
    int m = paramInt2 >>> 16 & 0x1F;
    int n = paramInt2 >>> 16 & 0x1F;
    int i1 = paramInt2 >>> 11 & 0x1F;
    int i2 = paramInt2 >>> 11 & 0x1F;
    int i3 = paramInt2 >>> 6 & 0x1F;
    int i4 = paramInt2 >>> 6 & 0x1F;
    int i5 = paramInt2 & 0x3F;
    int i6 = paramInt2 >>> 6 & 0xFFFFF;
    
    int i7 = paramInt2 & 0x3FFFFFF;
    int i8 = paramInt2 & 0xFFFF;
    int i9 = paramInt2 << 16 >> 16;
    int i10 = i9;
    
    int i11;
    int i12;
    int i13;
    switch (j) {
    case 0: 
      switch (i5) {
      case 0: 
        if (paramInt2 != 0) {
          preSetReg(0 + i1);
          pushRegWZ(0 + m);
          localMethodGen.add((byte)18, i3);
          localMethodGen.add((byte)120);
          setReg(); }
        break;
      case 2: 
        preSetReg(0 + i1);
        pushRegWZ(0 + m);
        localMethodGen.add((byte)18, i3);
        localMethodGen.add((byte)124);
        setReg();
        break;
      case 3: 
        preSetReg(0 + i1);
        pushRegWZ(0 + m);
        localMethodGen.add((byte)18, i3);
        localMethodGen.add((byte)122);
        setReg();
        break;
      case 4: 
        preSetReg(0 + i1);
        pushRegWZ(0 + m);
        pushRegWZ(0 + k);
        localMethodGen.add((byte)120);
        setReg();
        break;
      case 6: 
        preSetReg(0 + i1);
        pushRegWZ(0 + m);
        pushRegWZ(0 + k);
        localMethodGen.add((byte)124);
        setReg();
        break;
      case 7: 
        preSetReg(0 + i1);
        pushRegWZ(0 + m);
        pushRegWZ(0 + k);
        localMethodGen.add((byte)122);
        setReg();
        break;
      case 8: 
        if (paramInt1 == -1) throw new Compiler.Exn("pc modifying insn in delay slot");
        emitInstruction(-1, paramInt3, -1);
        preSetPC();
        pushRegWZ(0 + k);
        setPC();
        leaveMethod();
        i |= 0x1;
        break;
      case 9: 
        if (paramInt1 == -1) throw new Compiler.Exn("pc modifying insn in delay slot");
        emitInstruction(-1, paramInt3, -1);
        link(paramInt1);
        preSetPC();
        pushRegWZ(0 + k);
        setPC();
        leaveMethod();
        i |= 0x1;
        break;
      case 12: 
        preSetPC();
        localMethodGen.add((byte)18, paramInt1);
        setPC();
        


        restoreChangedRegs();
        
        preSetReg(2);
        localMethodGen.add((byte)42);
        pushRegZ(2);
        pushRegZ(4);
        pushRegZ(5);
        pushRegZ(6);
        pushRegZ(7);
        pushRegZ(8);
        pushRegZ(9);
        
        localMethodGen.add((byte)-74, this.me.method("syscall", Type.INT, new Type[] { Type.INT, Type.INT, Type.INT, Type.INT, Type.INT, Type.INT, Type.INT }));
        setReg();
        
        localMethodGen.add((byte)42);
        localMethodGen.add((byte)-76, this.me.field("state", Type.INT));
        i11 = localMethodGen.add((byte)-103);
        preSetPC();
        localMethodGen.add((byte)18, paramInt1 + 4);
        setPC();
        leaveMethod();
        localMethodGen.setArg(i11, localMethodGen.size());
        break;
      case 13: 
        localMethodGen.add((byte)-69, Type.Class.instance("org.ibex.nestedvm.Runtime$ExecutionException"));
        localMethodGen.add((byte)89);
        localMethodGen.add((byte)18, "BREAK Code " + toHex(i6));
        localMethodGen.add((byte)-73, Type.Class.instance("org.ibex.nestedvm.Runtime$ExecutionException").method("<init>", Type.VOID, new Type[] { Type.STRING }));
        localMethodGen.add((byte)-65);
        i |= 0x1;
        break;
      case 16: 
        preSetReg(0 + i1);
        pushReg(64);
        setReg();
        break;
      case 17: 
        preSetReg(64);
        pushRegZ(0 + k);
        setReg();
        break;
      case 18: 
        preSetReg(0 + i1);
        pushReg(65);
        setReg();
        break;
      case 19: 
        preSetReg(65);
        pushRegZ(0 + k);
        setReg();
        break;
      case 24: 
        pushRegWZ(0 + k);
        localMethodGen.add((byte)-123);
        pushRegWZ(0 + m);
        localMethodGen.add((byte)-123);
        localMethodGen.add((byte)105);
        localMethodGen.add((byte)92);
        
        localMethodGen.add((byte)-120);
        if (preSetReg(65))
          localMethodGen.add((byte)95);
        setReg();
        
        localMethodGen.add((byte)18, 32);
        localMethodGen.add((byte)125);
        localMethodGen.add((byte)-120);
        if (preSetReg(64))
          localMethodGen.add((byte)95);
        setReg();
        
        break;
      case 25: 
        pushRegWZ(0 + k);
        localMethodGen.add((byte)-123);
        localMethodGen.add((byte)18, FFFFFFFF);
        localMethodGen.add((byte)Byte.MAX_VALUE);
        pushRegWZ(0 + m);
        localMethodGen.add((byte)-123);
        localMethodGen.add((byte)18, FFFFFFFF);
        localMethodGen.add((byte)Byte.MAX_VALUE);
        localMethodGen.add((byte)105);
        localMethodGen.add((byte)92);
        
        localMethodGen.add((byte)-120);
        if (preSetReg(65))
          localMethodGen.add((byte)95);
        setReg();
        
        localMethodGen.add((byte)18, 32);
        localMethodGen.add((byte)125);
        localMethodGen.add((byte)-120);
        if (preSetReg(64))
          localMethodGen.add((byte)95);
        setReg();
        
        break;
      case 26: 
        pushRegWZ(0 + k);
        pushRegWZ(0 + m);
        localMethodGen.add((byte)92);
        
        localMethodGen.add((byte)108);
        if (preSetReg(65))
          localMethodGen.add((byte)95);
        setReg();
        
        localMethodGen.add((byte)112);
        if (preSetReg(64))
          localMethodGen.add((byte)95);
        setReg();
        
        break;
      case 27: 
        pushRegWZ(0 + m);
        localMethodGen.add((byte)89);
        setTmp();
        i11 = localMethodGen.add((byte)-103);
        
        pushRegWZ(0 + k);
        localMethodGen.add((byte)-123);
        localMethodGen.add((byte)18, FFFFFFFF);
        localMethodGen.add((byte)Byte.MAX_VALUE);
        localMethodGen.add((byte)92);
        pushTmp();
        localMethodGen.add((byte)-123);
        localMethodGen.add((byte)18, FFFFFFFF);
        
        localMethodGen.add((byte)Byte.MAX_VALUE);
        localMethodGen.add((byte)94);
        localMethodGen.add((byte)109);
        
        localMethodGen.add((byte)-120);
        if (preSetReg(65))
          localMethodGen.add((byte)95);
        setReg();
        
        localMethodGen.add((byte)113);
        localMethodGen.add((byte)-120);
        if (preSetReg(64))
          localMethodGen.add((byte)95);
        setReg();
        
        localMethodGen.setArg(i11, localMethodGen.size());
        
        break;
      
      case 32: 
        throw new Compiler.Exn("ADD (add with oveflow trap) not suported");
      case 33: 
        preSetReg(0 + i1);
        if ((m != 0) && (k != 0)) {
          pushReg(0 + k);
          pushReg(0 + m);
          localMethodGen.add((byte)96);
        } else if (k != 0) {
          pushReg(0 + k);
        } else {
          pushRegZ(0 + m);
        }
        setReg();
        break;
      case 34: 
        throw new Compiler.Exn("SUB (add with oveflow trap) not suported");
      case 35: 
        preSetReg(0 + i1);
        if ((m != 0) && (k != 0)) {
          pushReg(0 + k);
          pushReg(0 + m);
          localMethodGen.add((byte)100);
        } else if (m != 0) {
          pushReg(0 + m);
          localMethodGen.add((byte)116);
        } else {
          pushRegZ(0 + k);
        }
        setReg();
        break;
      case 36: 
        preSetReg(0 + i1);
        pushRegWZ(0 + k);
        pushRegWZ(0 + m);
        localMethodGen.add((byte)126);
        setReg();
        break;
      case 37: 
        preSetReg(0 + i1);
        pushRegWZ(0 + k);
        pushRegWZ(0 + m);
        localMethodGen.add((byte)Byte.MIN_VALUE);
        setReg();
        break;
      case 38: 
        preSetReg(0 + i1);
        pushRegWZ(0 + k);
        pushRegWZ(0 + m);
        localMethodGen.add((byte)-126);
        setReg();
        break;
      case 39: 
        preSetReg(0 + i1);
        if ((k != 0) || (m != 0)) {
          if ((k != 0) && (m != 0)) {
            pushReg(0 + k);
            pushReg(0 + m);
            localMethodGen.add((byte)Byte.MIN_VALUE);
          } else if (k != 0) {
            pushReg(0 + k);
          } else {
            pushReg(0 + m);
          }
          localMethodGen.add((byte)2);
          localMethodGen.add((byte)-126);
        } else {
          localMethodGen.add((byte)18, -1);
        }
        setReg();
        break;
      case 42: 
        preSetReg(0 + i1);
        if (k != m) {
          pushRegZ(0 + k);
          pushRegZ(0 + m);
          i11 = localMethodGen.add((byte)-95);
          localMethodGen.add((byte)3);
          i12 = localMethodGen.add((byte)-89);
          localMethodGen.setArg(i11, localMethodGen.add((byte)4));
          localMethodGen.setArg(i12, localMethodGen.size());
        } else {
          localMethodGen.add((byte)18, 0);
        }
        setReg();
        break;
      case 43: 
        preSetReg(0 + i1);
        if (k != m) {
          if (k != 0) {
            pushReg(0 + k);
            localMethodGen.add((byte)-123);
            localMethodGen.add((byte)18, FFFFFFFF);
            localMethodGen.add((byte)Byte.MAX_VALUE);
            pushReg(0 + m);
            localMethodGen.add((byte)-123);
            localMethodGen.add((byte)18, FFFFFFFF);
            localMethodGen.add((byte)Byte.MAX_VALUE);
            localMethodGen.add((byte)-108);
            i11 = localMethodGen.add((byte)-101);
          } else {
            pushReg(0 + m);
            i11 = localMethodGen.add((byte)-102);
          }
          localMethodGen.add((byte)3);
          i12 = localMethodGen.add((byte)-89);
          localMethodGen.setArg(i11, localMethodGen.add((byte)4));
          localMethodGen.setArg(i12, localMethodGen.size());
        } else {
          localMethodGen.add((byte)18, 0);
        }
        setReg();
        break;
      case 1: case 5: case 10: case 11: case 14: case 15: case 20: case 21: case 22: case 23: case 28: case 29: case 30: case 31: case 40: case 41: default: 
        throw new Compiler.Exn("Illegal instruction 0/" + i5);
      }
      
      break;
    case 1: 
      switch (m) {
      case 0: 
        if (paramInt1 == -1) throw new Compiler.Exn("pc modifying insn in delay slot");
        pushRegWZ(0 + k);
        return doIfInstruction((byte)-101, paramInt1, paramInt1 + i10 * 4 + 4, paramInt3);
      case 1: 
        if (paramInt1 == -1) throw new Compiler.Exn("pc modifying insn in delay slot");
        pushRegWZ(0 + k);
        return doIfInstruction((byte)-100, paramInt1, paramInt1 + i10 * 4 + 4, paramInt3);
      case 16: 
        if (paramInt1 == -1) throw new Compiler.Exn("pc modifying insn in delay slot");
        pushRegWZ(0 + k);
        i11 = localMethodGen.add((byte)-100);
        emitInstruction(-1, paramInt3, -1);
        link(paramInt1);
        branch(paramInt1, paramInt1 + i10 * 4 + 4);
        localMethodGen.setArg(i11, localMethodGen.size());
        break;
      case 17: 
        if (paramInt1 == -1) throw new Compiler.Exn("pc modifying insn in delay slot");
        i11 = -1;
        if (k != 0) {
          pushRegWZ(0 + k);
          i11 = localMethodGen.add((byte)-101);
        }
        emitInstruction(-1, paramInt3, -1);
        link(paramInt1);
        branch(paramInt1, paramInt1 + i10 * 4 + 4);
        if (i11 != -1) localMethodGen.setArg(i11, localMethodGen.size());
        if (i11 == -1) i |= 0x1;
        break;
      default: 
        throw new Compiler.Exn("Illegal Instruction 1/" + m);
      }
      
      break;
    case 2: 
      if (paramInt1 == -1) throw new Compiler.Exn("pc modifying insn in delay slot");
      emitInstruction(-1, paramInt3, -1);
      branch(paramInt1, paramInt1 & 0xF0000000 | i7 << 2);
      i |= 0x1;
      break;
    
    case 3: 
      if (paramInt1 == -1) throw new Compiler.Exn("pc modifying insn in delay slot");
      i13 = paramInt1 & 0xF0000000 | i7 << 2;
      emitInstruction(-1, paramInt3, -1);
      link(paramInt1);
      branch(paramInt1, i13);
      i |= 0x1;
      break;
    
    case 4: 
      if (paramInt1 == -1) throw new Compiler.Exn("pc modifying insn in delay slot");
      if (k == m) {
        emitInstruction(-1, paramInt3, -1);
        branch(paramInt1, paramInt1 + i10 * 4 + 4);
        i |= 0x1;
      } else { if ((k == 0) || (m == 0)) {
          pushReg(m == 0 ? 0 + k : 0 + m);
          return doIfInstruction((byte)-103, paramInt1, paramInt1 + i10 * 4 + 4, paramInt3);
        }
        pushReg(0 + k);
        pushReg(0 + m);
        return doIfInstruction((byte)-97, paramInt1, paramInt1 + i10 * 4 + 4, paramInt3);
      }
      break;
    case 5: 
      if (paramInt1 == -1) throw new Compiler.Exn("pc modifying insn in delay slot");
      pushRegWZ(0 + k);
      if (m == 0) {
        return doIfInstruction((byte)-102, paramInt1, paramInt1 + i10 * 4 + 4, paramInt3);
      }
      pushReg(0 + m);
      return doIfInstruction((byte)-96, paramInt1, paramInt1 + i10 * 4 + 4, paramInt3);
    
    case 6: 
      if (paramInt1 == -1) throw new Compiler.Exn("pc modifying insn in delay slot");
      pushRegWZ(0 + k);
      return doIfInstruction((byte)-98, paramInt1, paramInt1 + i10 * 4 + 4, paramInt3);
    case 7: 
      if (paramInt1 == -1) throw new Compiler.Exn("pc modifying insn in delay slot");
      pushRegWZ(0 + k);
      return doIfInstruction((byte)-99, paramInt1, paramInt1 + i10 * 4 + 4, paramInt3);
    case 8: 
      throw new Compiler.Exn("ADDI (add immediate with oveflow trap) not suported");
    case 9: 
      if ((k != 0) && (i9 != 0) && (k == m) && (doLocal(m)) && (i9 >= 32768) && (i9 <= 32767))
      {
        this.regLocalWritten[m] = true;
        localMethodGen.add((byte)-124, new MethodGen.Pair(getLocalForReg(m), i9));
      } else {
        preSetReg(0 + m);
        addiu(k, i9);
        setReg();
      }
      break;
    case 10: 
      preSetReg(0 + m);
      pushRegWZ(0 + k);
      localMethodGen.add((byte)18, i9);
      i11 = localMethodGen.add((byte)-95);
      localMethodGen.add((byte)3);
      i12 = localMethodGen.add((byte)-89);
      localMethodGen.setArg(i11, localMethodGen.add((byte)4));
      localMethodGen.setArg(i12, localMethodGen.size());
      setReg();
      break;
    case 11: 
      preSetReg(0 + m);
      pushRegWZ(0 + k);
      localMethodGen.add((byte)-123);
      localMethodGen.add((byte)18, FFFFFFFF);
      localMethodGen.add((byte)Byte.MAX_VALUE);
      
      localMethodGen.add((byte)18, new Long(i9 & 0xFFFFFFFF));
      localMethodGen.add((byte)-108);
      
      i11 = localMethodGen.add((byte)-101);
      localMethodGen.add((byte)3);
      i12 = localMethodGen.add((byte)-89);
      localMethodGen.setArg(i11, localMethodGen.add((byte)4));
      localMethodGen.setArg(i12, localMethodGen.size());
      setReg();
      break;
    case 12: 
      preSetReg(0 + m);
      pushRegWZ(0 + k);
      localMethodGen.add((byte)18, i8);
      localMethodGen.add((byte)126);
      setReg();
      break;
    case 13: 
      preSetReg(0 + m);
      if ((k != 0) && (i8 != 0)) {
        pushReg(0 + k);
        localMethodGen.add((byte)18, i8);
        localMethodGen.add((byte)Byte.MIN_VALUE);
      } else if (k != 0) {
        pushReg(0 + k);
      } else {
        localMethodGen.add((byte)18, i8);
      }
      setReg();
      break;
    case 14: 
      preSetReg(0 + m);
      pushRegWZ(0 + k);
      localMethodGen.add((byte)18, i8);
      localMethodGen.add((byte)-126);
      setReg();
      break;
    case 15: 
      preSetReg(0 + m);
      localMethodGen.add((byte)18, i8 << 16);
      setReg();
      break;
    case 16: 
      throw new Compiler.Exn("TLB/Exception support not implemented");
    case 17: 
      switch (k) {
      case 0: 
        preSetReg(0 + m);
        pushReg(32 + i1);
        setReg();
        break;
      case 2: 
        if (i2 != 31) throw new Compiler.Exn("FCR " + i2 + " unavailable");
        preSetReg(0 + m);
        pushReg(66);
        setReg();
        break;
      case 4: 
        preSetReg(32 + i1);
        if (m != 0) pushReg(0 + m); else
          localMethodGen.add((byte)3);
        setReg();
        break;
      case 6: 
        if (i2 != 31) throw new Compiler.Exn("FCR " + i2 + " unavailable");
        preSetReg(66);
        pushReg(0 + m);
        setReg();
        break;
      case 8: 
        pushReg(66);
        localMethodGen.add((byte)18, 8388608);
        localMethodGen.add((byte)126);
        return doIfInstruction((byte)((paramInt2 >>> 16 & 0x1) == 0 ? -103 : -102), paramInt1, paramInt1 + i10 * 4 + 4, paramInt3);
      

      case 16: 
      case 17: 
        i13 = k == 17 ? 1 : 0;
        switch (i5) {
        case 0: 
          preSetDouble(32 + i4, i13);
          pushDouble(32 + i2, i13);
          pushDouble(32 + n, i13);
          localMethodGen.add((byte)(i13 != 0 ? 99 : 98));
          setDouble(i13);
          break;
        case 1: 
          preSetDouble(32 + i4, i13);
          pushDouble(32 + i2, i13);
          pushDouble(32 + n, i13);
          localMethodGen.add((byte)(i13 != 0 ? 103 : 102));
          setDouble(i13);
          break;
        case 2: 
          preSetDouble(32 + i4, i13);
          pushDouble(32 + i2, i13);
          pushDouble(32 + n, i13);
          localMethodGen.add((byte)(i13 != 0 ? 107 : 106));
          setDouble(i13);
          break;
        case 3: 
          preSetDouble(32 + i4, i13);
          pushDouble(32 + i2, i13);
          pushDouble(32 + n, i13);
          localMethodGen.add((byte)(i13 != 0 ? 111 : 110));
          setDouble(i13);
          break;
        case 5: 
          preSetDouble(32 + i4, i13);
          

          pushDouble(32 + i2, i13);
          localMethodGen.add((byte)(i13 != 0 ? 92 : 89));
          localMethodGen.add((byte)(i13 != 0 ? 14 : 11));
          localMethodGen.add((byte)(i13 != 0 ? -104 : -106));
          
          i11 = localMethodGen.add((byte)-99);
          localMethodGen.add((byte)(i13 != 0 ? 14 : 11));
          if (i13 != 0) {
            localMethodGen.add((byte)94);
            localMethodGen.add((byte)88);
          } else {
            localMethodGen.add((byte)95);
          }
          localMethodGen.add((byte)(i13 != 0 ? 103 : 102));
          
          localMethodGen.setArg(i11, localMethodGen.size());
          setDouble(i13);
          
          break;
        case 6: 
          preSetReg(32 + i4);
          pushReg(32 + i2);
          setReg();
          
          if (i13 != 0) {
            preSetReg(32 + i4 + 1);
            pushReg(32 + i2 + 1);
            setReg();
          }
          break;
        case 7: 
          preSetDouble(32 + i4, i13);
          pushDouble(32 + i2, i13);
          localMethodGen.add((byte)(i13 != 0 ? 119 : 118));
          setDouble(i13);
          break;
        case 32: 
          preSetFloat(32 + i4);
          pushDouble(32 + i2, i13);
          if (i13 != 0) localMethodGen.add((byte)-112);
          setFloat();
          break;
        case 33: 
          preSetDouble(32 + i4);
          pushDouble(32 + i2, i13);
          if (i13 == 0) localMethodGen.add((byte)-115);
          setDouble();
          break;
        case 36: 
          MethodGen.Switch.Table localTable = new MethodGen.Switch.Table(0, 3);
          preSetReg(32 + i4);
          pushDouble(32 + i2, i13);
          pushReg(66);
          localMethodGen.add((byte)6);
          localMethodGen.add((byte)126);
          localMethodGen.add((byte)-86, localTable);
          

          localTable.setTarget(2, localMethodGen.size());
          if (i13 == 0) localMethodGen.add((byte)-115);
          localMethodGen.add((byte)-72, Type.Class.instance("java.lang.Math").method("ceil", Type.DOUBLE, new Type[] { Type.DOUBLE }));
          if (i13 == 0) localMethodGen.add((byte)-112);
          i11 = localMethodGen.add((byte)-89);
          

          localTable.setTarget(0, localMethodGen.size());
          localMethodGen.add((byte)18, i13 != 0 ? POINT_5_D : POINT_5_F);
          localMethodGen.add((byte)(i13 != 0 ? 99 : 98));
          


          localTable.setTarget(3, localMethodGen.size());
          if (i13 == 0) localMethodGen.add((byte)-115);
          localMethodGen.add((byte)-72, Type.Class.instance("java.lang.Math").method("floor", Type.DOUBLE, new Type[] { Type.DOUBLE }));
          if (i13 == 0) { localMethodGen.add((byte)-112);
          }
          localTable.setTarget(1, localMethodGen.size());
          localTable.setDefaultTarget(localMethodGen.size());
          localMethodGen.setArg(i11, localMethodGen.size());
          
          localMethodGen.add((byte)(i13 != 0 ? -114 : -117));
          setReg();
          
          break;
        
        case 50: 
        case 60: 
        case 62: 
          preSetReg(66);
          pushReg(66);
          localMethodGen.add((byte)18, -8388609);
          localMethodGen.add((byte)126);
          pushDouble(32 + i2, i13);
          pushDouble(32 + n, i13);
          localMethodGen.add((byte)(i13 != 0 ? -104 : -106));
          switch (i5) {
          case 50:  i11 = localMethodGen.add((byte)-102); break;
          case 60:  i11 = localMethodGen.add((byte)-100); break;
          case 62:  i11 = localMethodGen.add((byte)-99); break;
          default:  i11 = -1;
          }
          localMethodGen.add((byte)18, 8388608);
          localMethodGen.add((byte)Byte.MIN_VALUE);
          localMethodGen.setArg(i11, localMethodGen.size());
          setReg();
          break;
        default:  throw new Compiler.Exn("Invalid Instruction 17/" + k + "/" + i5);
        }
        
        break;
      case 20: 
        switch (i5) {
        case 32: 
          preSetFloat(32 + i4);
          pushReg(32 + i2);
          localMethodGen.add((byte)-122);
          setFloat();
          break;
        case 33: 
          preSetDouble(32 + i4);
          pushReg(32 + i2);
          localMethodGen.add((byte)-121);
          setDouble();
          break;
        default:  throw new Compiler.Exn("Invalid Instruction 17/" + k + "/" + i5);
        }
        break;
      case 1: case 3: case 5: case 7: case 9: case 10: case 11: 
      case 12: case 13: case 14: case 15: case 18: case 19: default: 
        throw new Compiler.Exn("Invalid Instruction 17/" + k);
      }
      break;
    case 18: 
    case 19: 
      throw new Compiler.Exn("coprocessor 2 and 3 instructions not available");
    case 32: 
      preSetReg(0 + m);
      addiu(0 + k, i9);
      setTmp();
      preMemRead();
      pushTmp();
      memRead(true);
      pushTmp();
      
      localMethodGen.add((byte)2);
      localMethodGen.add((byte)-126);
      localMethodGen.add((byte)6);
      localMethodGen.add((byte)126);
      localMethodGen.add((byte)6);
      localMethodGen.add((byte)120);
      localMethodGen.add((byte)124);
      localMethodGen.add((byte)-111);
      setReg();
      break;
    
    case 33: 
      preSetReg(0 + m);
      addiu(0 + k, i9);
      setTmp();
      preMemRead();
      pushTmp();
      memRead(true);
      pushTmp();
      
      localMethodGen.add((byte)2);
      localMethodGen.add((byte)-126);
      localMethodGen.add((byte)5);
      localMethodGen.add((byte)126);
      localMethodGen.add((byte)6);
      localMethodGen.add((byte)120);
      localMethodGen.add((byte)124);
      localMethodGen.add((byte)-109);
      setReg();
      break;
    
    case 34: 
      preSetReg(0 + m);
      addiu(0 + k, i9);
      setTmp();
      
      pushRegWZ(0 + m);
      localMethodGen.add((byte)18, 16777215);
      pushTmp();
      
      localMethodGen.add((byte)2);
      localMethodGen.add((byte)-126);
      localMethodGen.add((byte)6);
      localMethodGen.add((byte)126);
      localMethodGen.add((byte)6);
      localMethodGen.add((byte)120);
      localMethodGen.add((byte)124);
      localMethodGen.add((byte)126);
      
      preMemRead();
      pushTmp();
      memRead(true);
      pushTmp();
      
      localMethodGen.add((byte)6);
      localMethodGen.add((byte)126);
      localMethodGen.add((byte)6);
      localMethodGen.add((byte)120);
      localMethodGen.add((byte)120);
      localMethodGen.add((byte)Byte.MIN_VALUE);
      
      setReg();
      
      break;
    

    case 35: 
      preSetReg(0 + m);
      memRead(0 + k, i9);
      setReg();
      break;
    case 36: 
      preSetReg(0 + m);
      addiu(0 + k, i9);
      setTmp();
      preMemRead();
      pushTmp();
      memRead(true);
      pushTmp();
      
      localMethodGen.add((byte)2);
      localMethodGen.add((byte)-126);
      localMethodGen.add((byte)6);
      localMethodGen.add((byte)126);
      localMethodGen.add((byte)6);
      localMethodGen.add((byte)120);
      localMethodGen.add((byte)124);
      localMethodGen.add((byte)18, 255);
      localMethodGen.add((byte)126);
      setReg();
      break;
    
    case 37: 
      preSetReg(0 + m);
      addiu(0 + k, i9);
      setTmp();
      preMemRead();
      pushTmp();
      memRead(true);
      pushTmp();
      
      localMethodGen.add((byte)2);
      localMethodGen.add((byte)-126);
      localMethodGen.add((byte)5);
      localMethodGen.add((byte)126);
      localMethodGen.add((byte)6);
      localMethodGen.add((byte)120);
      localMethodGen.add((byte)124);
      

      localMethodGen.add((byte)-110);
      setReg();
      break;
    
    case 38: 
      preSetReg(0 + m);
      addiu(0 + k, i9);
      setTmp();
      
      pushRegWZ(0 + m);
      localMethodGen.add((byte)18, 65280);
      pushTmp();
      
      localMethodGen.add((byte)6);
      localMethodGen.add((byte)126);
      localMethodGen.add((byte)6);
      localMethodGen.add((byte)120);
      localMethodGen.add((byte)120);
      localMethodGen.add((byte)126);
      
      preMemRead();
      pushTmp();
      memRead(true);
      pushTmp();
      
      localMethodGen.add((byte)2);
      localMethodGen.add((byte)-126);
      localMethodGen.add((byte)6);
      localMethodGen.add((byte)126);
      localMethodGen.add((byte)6);
      localMethodGen.add((byte)120);
      localMethodGen.add((byte)124);
      localMethodGen.add((byte)Byte.MIN_VALUE);
      

      setReg();
      break;
    
    case 40: 
      addiu(0 + k, i9);
      setTmp();
      
      preMemRead(true);
      pushTmp();
      memRead(true);
      
      localMethodGen.add((byte)18, -16777216);
      pushTmp();
      
      localMethodGen.add((byte)6);
      localMethodGen.add((byte)126);
      localMethodGen.add((byte)6);
      localMethodGen.add((byte)120);
      localMethodGen.add((byte)124);
      localMethodGen.add((byte)2);
      localMethodGen.add((byte)-126);
      localMethodGen.add((byte)126);
      
      if (m != 0) {
        pushReg(0 + m);
        localMethodGen.add((byte)18, 255);
        localMethodGen.add((byte)126);
      } else {
        localMethodGen.add((byte)18, 0);
      }
      pushTmp();
      
      localMethodGen.add((byte)2);
      localMethodGen.add((byte)-126);
      localMethodGen.add((byte)6);
      localMethodGen.add((byte)126);
      localMethodGen.add((byte)6);
      localMethodGen.add((byte)120);
      localMethodGen.add((byte)120);
      localMethodGen.add((byte)Byte.MIN_VALUE);
      
      memWrite();
      
      break;
    
    case 41: 
      addiu(0 + k, i9);
      setTmp();
      
      preMemRead(true);
      pushTmp();
      memRead(true);
      
      localMethodGen.add((byte)18, 65535);
      pushTmp();
      
      localMethodGen.add((byte)5);
      localMethodGen.add((byte)126);
      localMethodGen.add((byte)6);
      localMethodGen.add((byte)120);
      localMethodGen.add((byte)120);
      localMethodGen.add((byte)126);
      
      if (m != 0) {
        pushReg(0 + m);
        localMethodGen.add((byte)18, 65535);
        localMethodGen.add((byte)126);
      } else {
        localMethodGen.add((byte)18, 0);
      }
      pushTmp();
      
      localMethodGen.add((byte)2);
      localMethodGen.add((byte)-126);
      localMethodGen.add((byte)5);
      localMethodGen.add((byte)126);
      localMethodGen.add((byte)6);
      localMethodGen.add((byte)120);
      localMethodGen.add((byte)120);
      localMethodGen.add((byte)Byte.MIN_VALUE);
      
      memWrite();
      
      break;
    
    case 42: 
      addiu(0 + k, i9);
      setTmp();
      
      preMemRead(true);
      pushTmp();
      memRead(true);
      
      localMethodGen.add((byte)18, 65280);
      pushTmp();
      
      localMethodGen.add((byte)2);
      localMethodGen.add((byte)-126);
      localMethodGen.add((byte)6);
      localMethodGen.add((byte)126);
      localMethodGen.add((byte)6);
      localMethodGen.add((byte)120);
      localMethodGen.add((byte)120);
      localMethodGen.add((byte)126);
      
      pushRegWZ(0 + m);
      pushTmp();
      
      localMethodGen.add((byte)6);
      localMethodGen.add((byte)126);
      localMethodGen.add((byte)6);
      localMethodGen.add((byte)120);
      localMethodGen.add((byte)124);
      localMethodGen.add((byte)Byte.MIN_VALUE);
      
      memWrite();
      break;
    

    case 43: 
      preMemWrite1();
      preMemWrite2(0 + k, i9);
      pushRegZ(0 + m);
      memWrite();
      break;
    case 46: 
      addiu(0 + k, i9);
      setTmp();
      
      preMemRead(true);
      pushTmp();
      memRead(true);
      
      localMethodGen.add((byte)18, 16777215);
      pushTmp();
      
      localMethodGen.add((byte)6);
      localMethodGen.add((byte)126);
      localMethodGen.add((byte)6);
      localMethodGen.add((byte)120);
      localMethodGen.add((byte)124);
      localMethodGen.add((byte)126);
      
      pushRegWZ(0 + m);
      pushTmp();
      
      localMethodGen.add((byte)2);
      localMethodGen.add((byte)-126);
      localMethodGen.add((byte)6);
      localMethodGen.add((byte)126);
      localMethodGen.add((byte)6);
      localMethodGen.add((byte)120);
      localMethodGen.add((byte)120);
      localMethodGen.add((byte)Byte.MIN_VALUE);
      
      memWrite();
      break;
    

    case 48: 
      preSetReg(0 + m);
      memRead(0 + k, i9);
      setReg();
      break;
    
    case 49: 
      preSetReg(32 + m);
      memRead(0 + k, i9);
      setReg();
      break;
    



    case 56: 
      preSetReg(0 + m);
      preMemWrite1();
      preMemWrite2(0 + k, i9);
      pushReg(0 + m);
      memWrite();
      localMethodGen.add((byte)18, 1);
      setReg();
      break;
    
    case 57: 
      preMemWrite1();
      preMemWrite2(0 + k, i9);
      pushReg(32 + m);
      memWrite();
      break;
    case 20: case 21: case 22: case 23: case 24: case 25: case 26: case 27: case 28: case 29: case 30: case 31: case 39: case 44: case 45: case 47: case 50: case 51: case 52: case 53: case 54: case 55: default: 
      throw new Compiler.Exn("Invalid Instruction: " + j + " at " + toHex(paramInt1));
    }
    return i;
  }
  

  private static final int F = 32;
  
  private static final int HI = 64;
  
  private static final int LO = 65;
  private static final int FCSR = 66;
  private static final int REG_COUNT = 67;
  private static final String[] regField = { "r0", "r1", "r2", "r3", "r4", "r5", "r6", "r7", "r8", "r9", "r10", "r11", "r12", "r13", "r14", "r15", "r16", "r17", "r18", "r19", "r20", "r21", "r22", "r23", "r24", "r25", "r26", "r27", "r28", "r29", "r30", "r31", "f0", "f1", "f2", "f3", "f4", "f5", "f6", "f7", "f8", "f9", "f10", "f11", "f12", "f13", "f14", "f15", "f16", "f17", "f18", "f19", "f20", "f21", "f22", "f23", "f24", "f25", "f26", "f27", "f28", "f29", "f30", "f31", "hi", "lo", "fcsr" };
  



  private static final int MAX_LOCALS = 4;
  



  private static final int LOAD_LENGTH = 3;
  



  private int[] regLocalMapping = new int[67];
  private boolean[] regLocalWritten = new boolean[67];
  private int nextAvailLocal;
  private int loadsStart;
  private int preSetRegStackPos;
  
  private boolean doLocal(int paramInt) { return (paramInt == 2) || (paramInt == 3) || (paramInt == 4) || (paramInt == 29); }
  
  private int getLocalForReg(int paramInt)
  {
    if (this.regLocalMapping[paramInt] != 0) return this.regLocalMapping[paramInt];
    this.regLocalMapping[paramInt] = (this.nextAvailLocal++);
    return this.regLocalMapping[paramInt];
  }
  
  private void fixupRegsStart() {
    for (int i = 0; i < 67; i++) {
      this.regLocalMapping[i] = 0;
      this.regLocalWritten[i] = false;
    }
    this.nextAvailLocal = (this.onePage ? 4 : 5);
    this.loadsStart = this.mg.size();
    for (i = 0; i < 12; i++)
      this.mg.add((byte)0);
  }
  
  private void fixupRegsEnd() {
    int i = this.loadsStart;
    for (int j = 0; j < 67; j++)
      if (this.regLocalMapping[j] != 0) {
        this.mg.set(i++, (byte)42);
        this.mg.set(i++, (byte)-76, this.me.field(regField[j], Type.INT));
        this.mg.set(i++, (byte)54, this.regLocalMapping[j]);
        
        if (this.regLocalWritten[j] != 0) {
          this.mg.add((byte)42);
          this.mg.add((byte)21, this.regLocalMapping[j]);
          this.mg.add((byte)-75, this.me.field(regField[j], Type.INT));
        }
      }
  }
  
  private void restoreChangedRegs() {
    for (int i = 0; i < 67; i++) {
      if (this.regLocalWritten[i] != 0) {
        this.mg.add((byte)42);
        this.mg.add((byte)21, this.regLocalMapping[i]);
        this.mg.add((byte)-75, this.me.field(regField[i], Type.INT));
      }
    }
  }
  
  private int pushRegWZ(int paramInt) {
    if (paramInt == 0) {
      this.warn.println("Warning: Pushing r0!");
      new Exception().printStackTrace(this.warn);
    }
    return pushRegZ(paramInt);
  }
  
  private int pushRegZ(int paramInt) {
    if (paramInt == 0) return this.mg.add((byte)3);
    return pushReg(paramInt);
  }
  
  private int pushReg(int paramInt)
  {
    int i = this.mg.size();
    if (doLocal(paramInt)) {
      this.mg.add((byte)21, getLocalForReg(paramInt));
    } else if ((paramInt >= 32) && (paramInt <= 63) && (this.singleFloat)) {
      this.mg.add((byte)42);
      this.mg.add((byte)-76, this.me.field(regField[paramInt], Type.FLOAT));
      this.mg.add((byte)-72, Type.FLOAT_OBJECT.method("floatToIntBits", Type.INT, new Type[] { Type.FLOAT }));
    } else {
      this.mg.add((byte)42);
      this.mg.add((byte)-76, this.me.field(regField[paramInt], Type.INT));
    }
    return i;
  }
  

  private int[] preSetRegStack = new int[8];
  private int memWriteStage;
  
  private boolean preSetReg(int paramInt) {
    this.preSetRegStack[this.preSetRegStackPos] = paramInt;
    this.preSetRegStackPos += 1;
    if (doLocal(paramInt)) {
      return false;
    }
    this.mg.add((byte)42);
    return true;
  }
  
  private int setReg()
  {
    if (this.preSetRegStackPos == 0) throw new RuntimeException("didn't do preSetReg");
    this.preSetRegStackPos -= 1;
    int i = this.preSetRegStack[this.preSetRegStackPos];
    int j = this.mg.size();
    if (doLocal(i)) {
      this.mg.add((byte)54, getLocalForReg(i));
      this.regLocalWritten[i] = true;
    } else if ((i >= 32) && (i <= 63) && (this.singleFloat)) {
      this.mg.add((byte)-72, Type.FLOAT_OBJECT.method("intBitsToFloat", Type.FLOAT, new Type[] { Type.INT }));
      this.mg.add((byte)-75, this.me.field(regField[i], Type.FLOAT));
    } else {
      this.mg.add((byte)-75, this.me.field(regField[i], Type.INT));
    }
    return j;
  }
  
  private int preSetPC() { return this.mg.add((byte)42); }
  
  private int setPC() { return this.mg.add((byte)-75, this.me.field("pc", Type.INT)); }
  


  private int pushFloat(int paramInt) throws Compiler.Exn { return pushDouble(paramInt, false); }
  
  private int pushDouble(int paramInt, boolean paramBoolean) throws Compiler.Exn { if ((paramInt < 32) || (paramInt >= 64)) throw new IllegalArgumentException("" + paramInt);
    int i = this.mg.size();
    if (paramBoolean) {
      if (this.singleFloat) throw new Compiler.Exn("Double operations not supported when singleFloat is enabled");
      if (paramInt == 63) throw new Compiler.Exn("Tried to use a double in f31");
      pushReg(paramInt + 1);
      this.mg.add((byte)-123);
      this.mg.add((byte)18, 32);
      this.mg.add((byte)121);
      pushReg(paramInt);
      this.mg.add((byte)-123);
      this.mg.add((byte)18, FFFFFFFF);
      this.mg.add((byte)Byte.MAX_VALUE);
      this.mg.add((byte)-127);
      this.mg.add((byte)-72, Type.DOUBLE_OBJECT.method("longBitsToDouble", Type.DOUBLE, new Type[] { Type.LONG }));
    } else if (this.singleFloat) {
      this.mg.add((byte)42);
      this.mg.add((byte)-76, this.me.field(regField[paramInt], Type.FLOAT));
    } else {
      pushReg(paramInt);
      this.mg.add((byte)-72, Type.Class.instance("java.lang.Float").method("intBitsToFloat", Type.FLOAT, new Type[] { Type.INT }));
    }
    return i;
  }
  
  private void preSetFloat(int paramInt) { preSetDouble(paramInt, false); }
  private void preSetDouble(int paramInt) { preSetDouble(paramInt, true); }
  private void preSetDouble(int paramInt, boolean paramBoolean) { preSetReg(paramInt); }
  
  private int setFloat() throws Compiler.Exn { return setDouble(false); }
  private int setDouble() throws Compiler.Exn { return setDouble(true); }
  
  private int setDouble(boolean paramBoolean) throws Compiler.Exn { int i = this.preSetRegStack[(this.preSetRegStackPos - 1)];
    if ((i < 32) || (i >= 64)) throw new IllegalArgumentException("" + i);
    int j = this.mg.size();
    if (paramBoolean) {
      if (this.singleFloat) throw new Compiler.Exn("Double operations not supported when singleFloat is enabled");
      if (i == 63) throw new Compiler.Exn("Tried to use a double in f31");
      this.mg.add((byte)-72, Type.DOUBLE_OBJECT.method("doubleToLongBits", Type.LONG, new Type[] { Type.DOUBLE }));
      this.mg.add((byte)92);
      this.mg.add((byte)18, 32);
      this.mg.add((byte)125);
      this.mg.add((byte)-120);
      if (preSetReg(i + 1))
        this.mg.add((byte)95);
      setReg();
      this.mg.add((byte)-120);
      setReg();
    } else if (this.singleFloat)
    {
      this.preSetRegStackPos -= 1;
      this.mg.add((byte)-75, this.me.field(regField[i], Type.FLOAT));
    }
    else {
      this.mg.add((byte)-72, Type.FLOAT_OBJECT.method("floatToRawIntBits", Type.INT, new Type[] { Type.FLOAT }));
      setReg();
    }
    return j;
  }
  
  private void pushTmp() { this.mg.add((byte)27); }
  private void setTmp() { this.mg.add((byte)60); }
  
  private void addiu(int paramInt1, int paramInt2) {
    if ((paramInt1 != 0) && (paramInt2 != 0)) {
      pushReg(paramInt1);
      this.mg.add((byte)18, paramInt2);
      this.mg.add((byte)96);
    } else if (paramInt1 != 0) {
      pushReg(paramInt1);
    } else {
      this.mg.add((byte)18, paramInt2);
    }
  }
  
  private void preMemWrite1() {
    if (this.memWriteStage != 0) throw new Error("pending preMemWrite1/2");
    this.memWriteStage = 1;
    if (this.onePage) {
      this.mg.add((byte)44);
    } else if (this.fastMem) {
      this.mg.add((byte)25, 3);
    } else
      this.mg.add((byte)42);
  }
  
  private void preMemWrite2(int paramInt1, int paramInt2) {
    addiu(paramInt1, paramInt2);
    preMemWrite2();
  }
  
  private void preMemWrite2() { preMemWrite2(false); }
  
  private void preMemWrite2(boolean paramBoolean) { if (this.memWriteStage != 1) throw new Error("pending preMemWrite2 or no preMemWrite1");
    this.memWriteStage = 2;
    
    if (this.nullPointerCheck) {
      this.mg.add((byte)89);
      this.mg.add((byte)42);
      this.mg.add((byte)95);
      
      this.mg.add((byte)-74, this.me.method("nullPointerCheck", Type.VOID, new Type[] { Type.INT }));
    }
    
    if (this.onePage) {
      this.mg.add((byte)5);
      this.mg.add((byte)124);
    } else if (this.fastMem) {
      if (!paramBoolean)
        this.mg.add((byte)90);
      this.mg.add((byte)18, this.pageShift);
      this.mg.add((byte)124);
      this.mg.add((byte)50);
      if (paramBoolean) {
        pushTmp();
      } else
        this.mg.add((byte)95);
      this.mg.add((byte)5);
      this.mg.add((byte)124);
      this.mg.add((byte)18, (this.pageSize >> 2) - 1);
      this.mg.add((byte)126);
    }
  }
  
  private void memWrite()
  {
    if (this.memWriteStage != 2) throw new Error("didn't do preMemWrite1 or preMemWrite2");
    this.memWriteStage = 0;
    
    if (this.onePage) {
      this.mg.add((byte)79);
    } else if (this.fastMem) {
      this.mg.add((byte)79);
    }
    else {
      this.mg.add((byte)-74, this.me.method("unsafeMemWrite", Type.VOID, new Type[] { Type.INT, Type.INT }));
    }
  }
  

  private void memRead(int paramInt1, int paramInt2)
  {
    preMemRead();
    addiu(paramInt1, paramInt2);
    memRead();
  }
  
  private boolean didPreMemRead;
  private boolean preMemReadDoPreWrite;
  private void preMemRead() {
    preMemRead(false); }
  
  private void preMemRead(boolean paramBoolean) { if (this.didPreMemRead) throw new Error("pending preMemRead");
    this.didPreMemRead = true;
    this.preMemReadDoPreWrite = paramBoolean;
    if (this.onePage) {
      this.mg.add((byte)44);
    } else if (this.fastMem) {
      this.mg.add((byte)25, paramBoolean ? 3 : 2);
    } else {
      this.mg.add((byte)42);
    }
  }
  
  private void memRead() { memRead(false); }
  
  private void memRead(boolean paramBoolean) {
    if (!this.didPreMemRead) throw new Error("didn't do preMemRead");
    this.didPreMemRead = false;
    if (this.preMemReadDoPreWrite) {
      this.memWriteStage = 2;
    }
    if (this.nullPointerCheck) {
      this.mg.add((byte)89);
      this.mg.add((byte)42);
      this.mg.add((byte)95);
      this.mg.add((byte)-74, this.me.method("nullPointerCheck", Type.VOID, new Type[] { Type.INT }));
    }
    
    if (this.onePage) {
      this.mg.add((byte)5);
      this.mg.add((byte)124);
      if (this.preMemReadDoPreWrite)
        this.mg.add((byte)92);
      this.mg.add((byte)46);
    } else if (this.fastMem) {
      if (!paramBoolean)
        this.mg.add((byte)90);
      this.mg.add((byte)18, this.pageShift);
      this.mg.add((byte)124);
      this.mg.add((byte)50);
      if (paramBoolean) {
        pushTmp();
      } else
        this.mg.add((byte)95);
      this.mg.add((byte)5);
      this.mg.add((byte)124);
      this.mg.add((byte)18, (this.pageSize >> 2) - 1);
      this.mg.add((byte)126);
      if (this.preMemReadDoPreWrite)
        this.mg.add((byte)92);
      this.mg.add((byte)46);
    }
    else {
      if (this.preMemReadDoPreWrite) {
        this.mg.add((byte)92);
      }
      this.mg.add((byte)-74, this.me.method("unsafeMemRead", Type.INT, new Type[] { Type.INT }));
    }
  }
}
