package org.ibex.nestedvm;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import org.ibex.nestedvm.util.ELF;
import org.ibex.nestedvm.util.ELF.SHeader;
import org.ibex.nestedvm.util.ELF.Symbol;

public class JavaSourceCompiler extends Compiler
{
  private StringBuffer runs = new StringBuffer();
  
  private StringBuffer inits = new StringBuffer();
  
  private StringBuffer classLevel = new StringBuffer();
  
  private PrintWriter out;
  
  private int indent;
  
  private void p() { this.out.println(); }
  
  private void p(String paramString) { this.out.println(indents[this.indent] + paramString); }
  private void pblock(StringBuffer paramStringBuffer) { this.out.print(paramStringBuffer.toString()); }
  



  private static String[] indents = new String[16];
  static { String str = ""; for (int i = 0; i < indents.length; str = str + "    ") { indents[i] = str;i++;
    } }
  
  public JavaSourceCompiler(org.ibex.nestedvm.util.Seekable paramSeekable, String paramString, java.io.Writer paramWriter) throws IOException { super(paramSeekable, paramString);
    this.out = new PrintWriter(paramWriter);
  }
  
  protected void _go() throws Compiler.Exn, IOException {
    if (this.singleFloat) throw new Compiler.Exn("JavaSourceCompiler doesn't support singleFloat");
    String str1;
    String str2;
    if (this.fullClassName.indexOf('.') != -1) {
      str1 = this.fullClassName.substring(0, this.fullClassName.lastIndexOf('.'));
      str2 = this.fullClassName.substring(this.fullClassName.lastIndexOf('.') + 1);
    } else {
      str2 = this.fullClassName;
      str1 = null;
    }
    
    p("/* This file was generated from " + this.source + " by Mips2Java on " + dateTime() + " */");
    if (str1 != null) p("package " + str1 + ";");
    if (this.runtimeStats) p("import java.util.*;");
    p();
    p("public final class " + str2 + " extends " + this.runtimeClass + " {");
    this.indent += 1;
    
    p("/* program counter */");
    p("private int pc = 0;");
    if (this.debugCompiler)
      p("private int lastPC = 0;");
    p();
    p("/* General Purpose registers */");
    p("private final static int r0 = 0;");
    p("private int      r1,  r2,  r3,  r4,  r5,  r6,  r7,");
    p("            r8,  r9,  r10, r11, r12, r13, r14, r15,");
    p("            r16, r17, r18, r19, r20, r21, r22, r23,");
    p("            r24, r25, r26, r27, r28, r29, r30, r31,");
    p("            hi = 0, lo = 0;");
    p("/* FP registers */");
    p("private int f0,  f1,  f2,  f3,  f4,  f5,  f6,  f7,");
    p("            f8,  f9,  f10, f11, f12, f13, f14, f15,");
    p("            f16, f17, f18, f19, f20, f21, f22, f23,");
    p("            f24, f25, f26, f27, f28, f29, f30, f31;");
    p("/* FP Control Register */");
    p("private int fcsr = 0;");
    p();
    
    if (this.onePage) { p("private final int[] page = readPages[0];");
    }
    
    int i = 0;
    Object localObject;
    for (int j = 0; j < this.elf.sheaders.length; j++) {
      ELF.SHeader localSHeader = this.elf.sheaders[j];
      localObject = localSHeader.name;
      
      if (localSHeader.addr != 0)
      {
        i = Math.max(i, localSHeader.addr + localSHeader.size);
        
        if (((String)localObject).equals(".text")) {
          emitText(localSHeader.addr, new DataInputStream(localSHeader.getInputStream()), localSHeader.size);
        } else if ((((String)localObject).equals(".data")) || (((String)localObject).equals(".sdata")) || (((String)localObject).equals(".rodata")) || (((String)localObject).equals(".ctors")) || (((String)localObject).equals(".dtors"))) {
          emitData(localSHeader.addr, new DataInputStream(localSHeader.getInputStream()), localSHeader.size, ((String)localObject).equals(".rodata"));
        } else if ((((String)localObject).equals(".bss")) || (((String)localObject).equals(".sbss"))) {
          emitBSS(localSHeader.addr, localSHeader.size);
        } else
          throw new Compiler.Exn("Unknown segment: " + (String)localObject);
      } }
    p();
    
    pblock(this.classLevel);
    p();
    

    p("private final void trampoline() throws ExecutionException {");
    this.indent += 1;
    p("while(state == RUNNING) {");
    this.indent += 1;
    p("switch(pc>>>" + this.methodShift + ") {");
    
    this.indent += 1;
    pblock(this.runs);
    p("default: throw new ExecutionException(\"invalid address 0x\" + Long.toString(this.pc&0xffffffffL,16) + \": r2: \" + r2);");
    this.indent -= 1;p("}");
    this.indent -= 1;p("}");
    this.indent -= 1;p("}");
    p();
    

    p("public " + str2 + "() {");
    this.indent += 1;
    p("super(" + this.pageSize + "," + this.totalPages + ");");
    pblock(this.inits);
    this.indent -= 1;
    p("}");
    p();
    
    p("protected int entryPoint() { return " + toHex(this.elf.header.entry) + "; }");
    p("protected int heapStart() { return " + toHex(i) + "; }");
    p("protected int gp() { return " + toHex(this.gp.addr) + "; }");
    if (this.userInfo != null) {
      p("protected int userInfoBase() { return " + toHex(this.userInfo.addr) + "; }");
      p("protected int userInfoSize() { return " + toHex(this.userInfo.size) + "; }");
    }
    

    p("public static void main(String[] args) throws Exception {");
    this.indent += 1;
    p("" + str2 + " me = new " + str2 + "();");
    p("int status = me.run(\"" + this.fullClassName + "\",args);");
    if (this.runtimeStats) p("me.printStats();");
    p("System.exit(status);");
    this.indent -= 1;
    p("}");
    p();
    

    p("protected void _execute() throws ExecutionException { trampoline(); }");
    p();
    
    p("protected void setCPUState(CPUState state) {");
    this.indent += 1;
    for (j = 1; j < 32; j++) p("r" + j + "=state.r[" + j + "];");
    for (j = 0; j < 32; j++) p("f" + j + "=state.f[" + j + "];");
    p("hi=state.hi; lo=state.lo; fcsr=state.fcsr;");
    p("pc=state.pc;");
    this.indent -= 1;
    p("}");
    p("protected void getCPUState(CPUState state) {");
    this.indent += 1;
    for (j = 1; j < 32; j++) p("state.r[" + j + "]=r" + j + ";");
    for (j = 0; j < 32; j++) p("state.f[" + j + "]=f" + j + ";");
    p("state.hi=hi; state.lo=lo; state.fcsr=fcsr;");
    p("state.pc=pc;");
    this.indent -= 1;
    p("}");
    p();
    
    if (this.supportCall) {
      p("private static final " + this.hashClass + " symbols = new " + this.hashClass + "();");
      p("static {");
      this.indent += 1;
      ELF.Symbol[] arrayOfSymbol = this.elf.getSymtab().symbols;
      for (int k = 0; k < arrayOfSymbol.length; k++) {
        localObject = arrayOfSymbol[k];
        if ((((ELF.Symbol)localObject).type == 2) && (((ELF.Symbol)localObject).binding == 1) && ((((ELF.Symbol)localObject).name.equals("_call_helper")) || (!((ELF.Symbol)localObject).name.startsWith("_"))))
          p("symbols.put(\"" + ((ELF.Symbol)localObject).name + "\",new Integer(" + toHex(((ELF.Symbol)localObject).addr) + "));");
      }
      this.indent -= 1;
      p("}");
      p("public int lookupSymbol(String symbol) { Integer i = (Integer) symbols.get(symbol); return i==null ? -1 : i.intValue(); }");
      p();
    }
    

    if (this.runtimeStats) {
      p("private HashMap counters = new HashMap();");
      p("private void inc(String k) { Long i = (Long)counters.get(k); counters.put(k,new Long(i==null ? 1 : i.longValue() + 1)); }");
      p("private void printStats() {");
      p(" Iterator i = new TreeSet(counters.keySet()).iterator();");
      p(" while(i.hasNext()) { Object o = i.next(); System.err.println(\"\" + o + \": \" + counters.get(o)); }");
      p("}");
      p();
    }
    
    this.indent -= 1;
    p("}");
  }
  
  private int startOfMethod = 0;
  private int endOfMethod = 0;
  
  private void startMethod(int paramInt) {
    paramInt &= (this.maxBytesPerMethod - 1 ^ 0xFFFFFFFF);
    this.startOfMethod = paramInt;
    this.endOfMethod = (paramInt + this.maxBytesPerMethod);
    String str = "run_" + Long.toString(paramInt & 0xFFFFFFFF, 16);
    this.runs.append(indents[4] + "case " + toHex(paramInt >>> this.methodShift) + ": " + str + "(); break; \n");
    

    p("private final void " + str + "() throws ExecutionException { /" + "* " + toHex(paramInt) + " - " + toHex(this.endOfMethod) + " *" + "/");
    this.indent += 1;
    p("int addr, tmp;");
    p("for(;;) {");
    this.indent += 1;
    p("switch(pc) {");
    this.indent += 1;
  }
  
  private void endMethod() { endMethod(this.endOfMethod); }
  
  private void endMethod(int paramInt) { if (this.startOfMethod == 0) { return;
    }
    
    p("case " + toHex(paramInt) + ":");
    this.indent += 1;
    p("pc=" + constant(paramInt) + ";");
    leaveMethod();
    this.indent -= 1;
    if (this.debugCompiler) {
      p("default: throw new ExecutionException(\"invalid address 0x\" + Long.toString(pc&0xffffffffL,16)  + \" (got here from 0x\" + Long.toString(lastPC&0xffffffffL,16)+\")\");");
    } else
      p("default: throw new ExecutionException(\"invalid address 0x\" + Long.toString(pc&0xffffffffL,16));");
    this.indent -= 1;
    p("}");
    p("/* NOT REACHED */");
    this.indent -= 1;
    p("}");
    this.indent -= 1;
    p("}");
    this.endOfMethod = (this.startOfMethod = 0);
  }
  
  private HashMap relativeAddrs = new HashMap();
  
  private String constant(int paramInt) { if ((paramInt >= 4096) && (this.lessConstants)) {
      int i = paramInt & 0xFC00;
      String str = "N_" + toHex8(i);
      if (this.relativeAddrs.get(new Integer(i)) == null) {
        this.relativeAddrs.put(new Integer(i), Boolean.TRUE);
        this.classLevel.append(indents[1] + "private static int " + str + " = " + toHex(i) + ";\n");
      }
      return "(" + str + " + " + toHex(paramInt - i) + ")";
    }
    return toHex(paramInt);
  }
  
  private boolean textDone;
  private void branch(int paramInt1, int paramInt2) {
    if (this.debugCompiler) p("lastPC = " + toHex(paramInt1) + ";");
    p("pc=" + constant(paramInt2) + ";");
    if (paramInt2 == 0) {
      p("throw new ExecutionException(\"Branch to addr 0x0\");");
    } else if ((paramInt1 & this.methodMask) == (paramInt2 & this.methodMask)) {
      p("continue;");
    } else if (this.assumeTailCalls) {
      p("run_" + Long.toString(paramInt2 & this.methodMask & 0xFFFFFFFF, 16) + "(); return;");
    } else
      leaveMethod();
  }
  
  private void leaveMethod() {
    p("return;");
  }
  
  private void emitText(int paramInt1, DataInputStream paramDataInputStream, int paramInt2) throws Compiler.Exn, IOException
  {
    if (this.textDone) throw new Compiler.Exn("Multiple text segments");
    this.textDone = true;
    
    if (((paramInt1 & 0x3) != 0) || ((paramInt2 & 0x3) != 0)) throw new Compiler.Exn("Section on weird boundaries");
    int i = paramInt2 / 4;
    int j = paramDataInputStream.readInt();
    if (j == -1) { throw new Error("Actually read -1 at " + toHex(paramInt1));
    }
    
    for (int m = 0; m < i; paramInt1 += 4) {
      int k = j;
      j = m == i - 1 ? -1 : paramDataInputStream.readInt();
      if (paramInt1 >= this.endOfMethod) { endMethod();startMethod(paramInt1); }
      if ((this.jumpableAddresses == null) || (paramInt1 == this.startOfMethod) || (this.jumpableAddresses.get(new Integer(paramInt1)) != null)) {
        p("case " + toHex(paramInt1) + ":");
        this.unreachable = false;
      } else { if (this.unreachable)
          break label295;
        if (this.debugCompiler)
          p("/* pc = " + toHex(paramInt1) + "*" + "/");
      }
      this.indent += 1;
      emitInstruction(paramInt1, k, j);
      this.indent -= 1;
      label295:
      m++;
    }
    













    endMethod(paramInt1);
    p();
    paramDataInputStream.close();
  }
  
  private int initDataCount = 0;
  
  private void emitData(int paramInt1, DataInputStream paramDataInputStream, int paramInt2, boolean paramBoolean) throws Compiler.Exn, IOException { if (((paramInt1 & 0x3) != 0) || ((paramInt2 & 0x3) != 0)) throw new Compiler.Exn("Data section on weird boundaries");
    int i = paramInt1 + paramInt2;
    while (paramInt1 < i) {
      int j = Math.min(paramInt2, 28000);
      StringBuffer localStringBuffer = new StringBuffer();
      for (int k = 0; k < j; k += 7) {
        long l = 0L;
        char c; for (int m = 0; m < 7; m++) {
          l <<= 8;
          c = k + m < paramInt2 ? paramDataInputStream.readByte() : '\001';
          l |= c & 0xFF;
        }
        for (m = 0; m < 8; m++) {
          c = (char)(int)(l >>> 7 * (7 - m) & 0x7F);
          if (c == '\n') { localStringBuffer.append("\\n");
          } else if (c == '\r') { localStringBuffer.append("\\r");
          } else if (c == '\\') { localStringBuffer.append("\\\\");
          } else if (c == '"') { localStringBuffer.append("\\\"");
          } else if ((c >= ' ') && (c <= '~')) localStringBuffer.append(c); else
            localStringBuffer.append("\\" + toOctal3(c));
        }
      }
      String str = "_data" + ++this.initDataCount;
      p("private static final int[] " + str + " = decodeData(\"" + localStringBuffer.toString() + "\"," + toHex(j / 4) + ");");
      this.inits.append(indents[2] + "initPages(" + str + "," + toHex(paramInt1) + "," + (paramBoolean ? "true" : "false") + ");\n");
      paramInt1 += j;
      paramInt2 -= j;
    }
    paramDataInputStream.close();
  }
  
  private void emitBSS(int paramInt1, int paramInt2) throws Compiler.Exn {
    if ((paramInt1 & 0x3) != 0) throw new Compiler.Exn("BSS section on weird boundaries");
    paramInt2 = paramInt2 + 3 & 0xFFFFFFFC;
    int i = paramInt2 / 4;
    this.inits.append(indents[2] + "clearPages(" + toHex(paramInt1) + "," + toHex(i) + ");\n");
  }
  

  private boolean unreachable = false;
  
  private void emitInstruction(int paramInt1, int paramInt2, int paramInt3) throws IOException, Compiler.Exn {
    if (paramInt2 == -1) { throw new Error("insn is -1");
    }
    int i = paramInt2 >>> 26 & 0xFF;
    int j = paramInt2 >>> 21 & 0x1F;
    int k = paramInt2 >>> 16 & 0x1F;
    int m = paramInt2 >>> 16 & 0x1F;
    int n = paramInt2 >>> 11 & 0x1F;
    int i1 = paramInt2 >>> 11 & 0x1F;
    int i2 = paramInt2 >>> 6 & 0x1F;
    int i3 = paramInt2 >>> 6 & 0x1F;
    int i4 = paramInt2 & 0x3F;
    
    int i5 = paramInt2 & 0x3FFFFFF;
    int i6 = paramInt2 & 0xFFFF;
    int i7 = paramInt2 << 16 >> 16;
    int i8 = i7;
    




    if (paramInt1 == -1) { p("/* Next insn is delay slot */ ");
    }
    if ((this.runtimeStats) && (i != 0)) p("inc(\"opcode: " + i + "\");");
    switch (i) {
    case 0: 
      if ((this.runtimeStats) && (paramInt2 != 0)) p("inc(\"opcode: 0/" + i4 + "\");");
      switch (i4) {
      case 0: 
        if (paramInt2 != 0)
          p("r" + n + " = r" + k + " << " + i2 + ";");
        break;
      case 2: 
        p("r" + n + " = r" + k + " >>> " + i2 + ";");
        break;
      case 3: 
        p("r" + n + " = r" + k + " >> " + i2 + ";");
        break;
      case 4: 
        p("r" + n + " = r" + k + " << (r" + j + "&0x1f);");
        break;
      case 6: 
        p("r" + n + " = r" + k + " >>> (r" + j + "&0x1f);");
        break;
      case 7: 
        p("r" + n + " = r" + k + " >> (r" + j + "&0x1f);");
        break;
      case 8: 
        if (paramInt1 == -1) throw new Error("pc modifying insn in delay slot");
        emitInstruction(-1, paramInt3, -1);
        if (this.debugCompiler) p("lastPC = " + toHex(paramInt1) + ";");
        p("pc=r" + j + ";");
        leaveMethod();
        this.unreachable = true;
        break;
      case 9: 
        if (paramInt1 == -1) throw new Error("pc modifying insn in delay slot");
        emitInstruction(-1, paramInt3, -1);
        if (this.debugCompiler) p("lastPC = " + toHex(paramInt1) + ";");
        p("pc=r" + j + ";");
        p("r31=" + constant(paramInt1 + 8) + ";");
        leaveMethod();
        this.unreachable = true;
        break;
      case 12: 
        p("pc = " + toHex(paramInt1) + ";");
        p("r2 = syscall(r2,r4,r5,r6,r7,r8,r9);");
        p("if (state != RUNNING) {");
        this.indent += 1;
        p("pc = " + toHex(paramInt1 + 4) + ";");
        leaveMethod();
        this.indent -= 1;
        p("}");
        break;
      case 13: 
        p("throw new ExecutionException(\"Break\");");
        this.unreachable = true;
        break;
      case 16: 
        p("r" + n + " = hi;");
        break;
      case 17: 
        p("hi = r" + j + ";");
        break;
      case 18: 
        p("r" + n + " = lo;");
        break;
      case 19: 
        p("lo = r" + j + ";");
        break;
      case 24: 
        p("{ long hilo = (long)(r" + j + ") * ((long)r" + k + "); " + "hi = (int) (hilo >>> 32); " + "lo = (int) hilo; }");
        

        break;
      case 25: 
        p("{ long hilo = (r" + j + " & 0xffffffffL) * (r" + k + " & 0xffffffffL); " + "hi = (int) (hilo >>> 32); " + "lo = (int) hilo; } ");
        

        break;
      case 26: 
        p("hi = r" + j + "%r" + k + "; lo = r" + j + "/r" + k + ";");
        break;
      case 27: 
        p("if(r" + k + "!=0) {");
        p("hi = (int)((r" + j + " & 0xffffffffL) % (r" + k + " & 0xffffffffL)); " + "lo = (int)((r" + j + " & 0xffffffffL) / (r" + k + " & 0xffffffffL));");
        
        p("}");
        break;
      case 32: 
        throw new Compiler.Exn("ADD (add with oveflow trap) not suported");
      


      case 33: 
        p("r" + n + " = r" + j + " + r" + k + ";");
        break;
      case 34: 
        throw new Compiler.Exn("SUB (add with oveflow trap) not suported");
      


      case 35: 
        p("r" + n + " = r" + j + " - r" + k + ";");
        break;
      case 36: 
        p("r" + n + " = r" + j + " & r" + k + ";");
        break;
      case 37: 
        p("r" + n + " = r" + j + " | r" + k + ";");
        break;
      case 38: 
        p("r" + n + " = r" + j + " ^ r" + k + ";");
        break;
      case 39: 
        p("r" + n + " = ~(r" + j + " | r" + k + ");");
        break;
      case 42: 
        p("r" + n + " = r" + j + " < r" + k + " ? 1 : 0;");
        break;
      case 43: 
        p("r" + n + " = ((r" + j + " & 0xffffffffL) < (r" + k + " & 0xffffffffL)) ? 1 : 0;");
        break;
      case 1: case 5: case 10: case 11: case 14: case 15: case 20: case 21: case 22: case 23: case 28: case 29: case 30: case 31: case 40: case 41: default: 
        throw new RuntimeException("Illegal instruction 0/" + i4);
      }
      
      break;
    case 1: 
      switch (k) {
      case 0: 
        if (paramInt1 == -1) throw new Error("pc modifying insn in delay slot");
        p("if(r" + j + " < 0) {");
        this.indent += 1;
        emitInstruction(-1, paramInt3, -1);
        branch(paramInt1, paramInt1 + i8 * 4 + 4);
        this.indent -= 1;
        p("}");
        break;
      case 1: 
        if (paramInt1 == -1) throw new Error("pc modifying insn in delay slot");
        p("if(r" + j + " >= 0) {");
        this.indent += 1;
        emitInstruction(-1, paramInt3, -1);
        branch(paramInt1, paramInt1 + i8 * 4 + 4);
        this.indent -= 1;
        p("}");
        break;
      case 16: 
        if (paramInt1 == -1) throw new Error("pc modifying insn in delay slot");
        p("if(r" + j + " < 0) {");
        this.indent += 1;
        emitInstruction(-1, paramInt3, -1);
        p("r31=" + constant(paramInt1 + 8) + ";");
        branch(paramInt1, paramInt1 + i8 * 4 + 4);
        this.indent -= 1;
        p("}");
        break;
      case 17: 
        if (paramInt1 == -1) throw new Error("pc modifying insn in delay slot");
        p("if(r" + j + " >= 0) {");
        this.indent += 1;
        emitInstruction(-1, paramInt3, -1);
        p("r31=" + constant(paramInt1 + 8) + ";");
        branch(paramInt1, paramInt1 + i8 * 4 + 4);
        this.indent -= 1;
        p("}");
        break;
      default: 
        throw new RuntimeException("Illegal Instruction 1/" + k);
      }
      
      break;
    case 2: 
      if (paramInt1 == -1) throw new Error("pc modifying insn in delay slot");
      emitInstruction(-1, paramInt3, -1);
      branch(paramInt1, paramInt1 & 0xF0000000 | i5 << 2);
      this.unreachable = true;
      break;
    
    case 3: 
      if (paramInt1 == -1) throw new Error("pc modifying insn in delay slot");
      int i10 = paramInt1 & 0xF0000000 | i5 << 2;
      emitInstruction(-1, paramInt3, -1);
      p("r31=" + constant(paramInt1 + 8) + ";");
      branch(paramInt1, i10);
      this.unreachable = true;
      break;
    
    case 4: 
      if (paramInt1 == -1) throw new Error("pc modifying insn in delay slot");
      p("if(r" + j + " == r" + k + ") {");
      this.indent += 1;
      emitInstruction(-1, paramInt3, -1);
      branch(paramInt1, paramInt1 + i8 * 4 + 4);
      this.indent -= 1;
      p("}");
      break;
    case 5: 
      if (paramInt1 == -1) throw new Error("pc modifying insn in delay slot");
      p("if(r" + j + " != r" + k + ") {");
      this.indent += 1;
      emitInstruction(-1, paramInt3, -1);
      branch(paramInt1, paramInt1 + i8 * 4 + 4);
      this.indent -= 1;
      p("}");
      break;
    case 6: 
      if (paramInt1 == -1) throw new Error("pc modifying insn in delay slot");
      p("if(r" + j + " <= 0) {");
      this.indent += 1;
      emitInstruction(-1, paramInt3, -1);
      branch(paramInt1, paramInt1 + i8 * 4 + 4);
      this.indent -= 1;
      p("}");
      break;
    case 7: 
      if (paramInt1 == -1) throw new Error("pc modifying insn in delay slot");
      p("if(r" + j + " > 0) {");
      this.indent += 1;
      emitInstruction(-1, paramInt3, -1);
      branch(paramInt1, paramInt1 + i8 * 4 + 4);
      this.indent -= 1;
      p("}");
      break;
    case 8: 
      p("r" + k + " = r" + j + " + " + i7 + ";");
      break;
    case 9: 
      p("r" + k + " = r" + j + " + " + i7 + ";");
      break;
    case 10: 
      p("r" + k + " = r" + j + " < " + i7 + " ? 1 : 0;");
      break;
    case 11: 
      p("r" + k + " = (r" + j + "&0xffffffffL) < (" + i7 + "&0xffffffffL) ? 1 : 0;");
      break;
    case 12: 
      p("r" + k + " = r" + j + " & " + i6 + ";");
      break;
    case 13: 
      p("r" + k + " = r" + j + " | " + i6 + ";");
      break;
    case 14: 
      p("r" + k + " = r" + j + " ^ " + i6 + ";");
      break;
    case 15: 
      p("r" + k + " = " + i6 + " << 16;");
      break;
    case 16: 
      throw new Compiler.Exn("TLB/Exception support not implemented");
    case 17: 
      switch (j) {
      case 0: 
        p("r" + k + " = f" + n + ";");
        break;
      case 2: 
        if (i1 != 31) throw new Compiler.Exn("FCR " + i1 + " unavailable");
        p("r" + k + " = fcsr;");
        break;
      case 4: 
        p("f" + n + " = r" + k + ";");
        break;
      case 6: 
        if (i1 != 31) throw new Compiler.Exn("FCR " + i1 + " unavailable");
        p("fcsr = r" + k + ";");
        break;
      case 8: 
        int i9 = paramInt2 >>> 16 & 0x1;
        p("if(((fcsr&0x800000)!=0) == (" + i9 + "!=0)) {");
        this.indent += 1;
        emitInstruction(-1, paramInt3, -1);
        branch(paramInt1, paramInt1 + i8 * 4 + 4);
        this.indent -= 1;
        p("}");
        break;
      
      case 16: 
        switch (i4) {
        case 0: 
          p(setFloat(i3, getFloat(i1) + "+" + getFloat(m)));
          break;
        case 1: 
          p(setFloat(i3, getFloat(i1) + "-" + getFloat(m)));
          break;
        case 2: 
          p(setFloat(i3, getFloat(i1) + "*" + getFloat(m)));
          break;
        case 3: 
          p(setFloat(i3, getFloat(i1) + "/" + getFloat(m)));
          break;
        case 5: 
          p(setFloat(i3, "Math.abs(" + getFloat(i1) + ")"));
          break;
        case 6: 
          p("f" + i3 + " = f" + i1 + "; // MOV.S");
          break;
        case 7: 
          p(setFloat(i3, "-" + getFloat(i1)));
          break;
        case 33: 
          p(setDouble(i3, "(float)" + getFloat(i1)));
          break;
        case 36: 
          p("switch(fcsr & 3) {");
          this.indent += 1;
          p("case 0: f" + i3 + " = (int)Math.floor(" + getFloat(i1) + "+0.5); break; // Round to nearest");
          p("case 1: f" + i3 + " = (int)" + getFloat(i1) + "; break; // Round towards zero");
          p("case 2: f" + i3 + " = (int)Math.ceil(" + getFloat(i1) + "); break; // Round towards plus infinity");
          p("case 3: f" + i3 + " = (int)Math.floor(" + getFloat(i1) + "); break; // Round towards minus infinity");
          this.indent -= 1;
          p("}");
          break;
        case 50: 
          p("fcsr = (fcsr&~0x800000) | ((" + getFloat(i1) + "==" + getFloat(m) + ") ? 0x800000 : 0x000000);");
          break;
        case 60: 
          p("fcsr = (fcsr&~0x800000) | ((" + getFloat(i1) + "<" + getFloat(m) + ") ? 0x800000 : 0x000000);");
          break;
        case 62: 
          p("fcsr = (fcsr&~0x800000) | ((" + getFloat(i1) + "<=" + getFloat(m) + ") ? 0x800000 : 0x000000);");
          break;
        default:  throw new Compiler.Exn("Invalid Instruction 17/" + j + "/" + i4);
        }
        
        break;
      case 17: 
        switch (i4) {
        case 0: 
          p(setDouble(i3, getDouble(i1) + "+" + getDouble(m)));
          break;
        case 1: 
          p(setDouble(i3, getDouble(i1) + "-" + getDouble(m)));
          break;
        case 2: 
          p(setDouble(i3, getDouble(i1) + "*" + getDouble(m)));
          break;
        case 3: 
          p(setDouble(i3, getDouble(i1) + "/" + getDouble(m)));
          break;
        case 5: 
          p(setDouble(i3, "Math.abs(" + getDouble(i1) + ")"));
          break;
        case 6: 
          p("f" + i3 + " = f" + i1 + ";");
          p("f" + (i3 + 1) + " = f" + (i1 + 1) + ";");
          break;
        case 7: 
          p(setDouble(i3, "-" + getDouble(i1)));
          break;
        case 32: 
          p(setFloat(i3, "(float)" + getDouble(i1)));
          break;
        case 36: 
          p("switch(fcsr & 3) {");
          this.indent += 1;
          p("case 0: f" + i3 + " = (int)Math.floor(" + getDouble(i1) + "+0.5); break; // Round to nearest");
          p("case 1: f" + i3 + " = (int)" + getDouble(i1) + "; break; // Round towards zero");
          p("case 2: f" + i3 + " = (int)Math.ceil(" + getDouble(i1) + "); break; // Round towards plus infinity");
          p("case 3: f" + i3 + " = (int)Math.floor(" + getDouble(i1) + "); break; // Round towards minus infinity");
          this.indent -= 1;
          p("}");
          break;
        case 50: 
          p("fcsr = (fcsr&~0x800000) | ((" + getDouble(i1) + "==" + getDouble(m) + ") ? 0x800000 : 0x000000);");
          break;
        case 60: 
          p("fcsr = (fcsr&~0x800000) | ((" + getDouble(i1) + "<" + getDouble(m) + ") ? 0x800000 : 0x000000);");
          break;
        case 62: 
          p("fcsr = (fcsr&~0x800000) | ((" + getDouble(i1) + "<=" + getDouble(m) + ") ? 0x800000 : 0x000000);");
          break;
        default:  throw new Compiler.Exn("Invalid Instruction 17/" + j + "/" + i4);
        }
        
        break;
      case 20: 
        switch (i4) {
        case 32: 
          p(" // CVS.S.W");
          p(setFloat(i3, "((float)f" + i1 + ")"));
          break;
        case 33: 
          p(setDouble(i3, "((double)f" + i1 + ")"));
          break;
        default:  throw new Compiler.Exn("Invalid Instruction 17/" + j + "/" + i4);
        }
        break;
      case 1: case 3: case 5: case 7: case 9: case 10: case 11: 
      case 12: case 13: case 14: case 15: case 18: case 19: default: 
        throw new Compiler.Exn("Invalid Instruction 17/" + j);
      }
      break;
    case 18: 
    case 19: 
      throw new Compiler.Exn("coprocessor 2 and 3 instructions not available");
    case 32: 
      if (this.runtimeStats) p("inc(\"LB\");");
      p("addr=r" + j + "+" + i7 + ";");
      memRead("addr", "tmp");
      p("tmp = (tmp>>>(((~addr)&3)<<3)) & 0xff;");
      p("if((tmp&0x80)!=0) tmp |= 0xffffff00; /* sign extend */");
      p("r" + k + " = tmp;");
      break;
    
    case 33: 
      if (this.runtimeStats) p("inc(\"LH\");");
      p("addr=r" + j + "+" + i7 + ";");
      memRead("addr", "tmp");
      p("tmp = (tmp>>>(((~addr)&2)<<3)) & 0xffff;");
      p("if((tmp&0x8000)!=0) tmp |= 0xffff0000; /* sign extend */");
      p("r" + k + " = tmp;");
      break;
    
    case 34: 
      p("addr=r" + j + "+" + i7 + ";");
      memRead("addr", "tmp");
      p("r" + k + " = (r" + k + "&(0x00ffffff>>>(((~addr)&3)<<3)))|(tmp<<((addr&3)<<3));");
      break;
    











    case 35: 
      if (this.runtimeStats) p("inc(\"LW\");");
      memRead("r" + j + "+" + i7, "r" + k);
      break;
    case 36: 
      p("addr=r" + j + "+" + i7 + ";");
      memRead("addr", "tmp");
      p("tmp = (tmp>>>(((~addr)&3)<<3)) & 0xff;");
      p("r" + k + " = tmp;");
      break;
    
    case 37: 
      p("addr=r" + j + "+" + i7 + ";");
      memRead("addr", "tmp");
      p("tmp = (tmp>>>(((~addr)&2)<<3)) & 0xffff;");
      p("r" + k + " = tmp;");
      break;
    
    case 38: 
      p("addr=r" + j + "+" + i7 + ";");
      memRead("addr", "tmp");
      p("r" + k + " = (r" + k + "&(0xffffff00<<((addr&3)<<3)))|(tmp>>>(((~addr)&3)<<3));");
      break;
    













    case 40: 
      if (this.runtimeStats) p("inc(\"SB\");");
      p("addr=r" + j + "+" + i7 + ";");
      memRead("addr", "tmp");
      p("tmp = (tmp&~(0xff000000>>>((addr&3)<<3)))|((r" + k + "&0xff)<<(((~addr)&3)<<3));");
      memWrite("addr", "tmp");
      break;
    
    case 41: 
      if (this.runtimeStats) p("inc(\"SH\");");
      p("addr=r" + j + "+" + i7 + ";");
      memRead("addr", "tmp");
      p("tmp = (tmp&(0xffff<<((addr&2)<<3)))|((r" + k + "&0xffff)<<(((~addr)&2)<<3));");
      memWrite("addr", "tmp");
      break;
    
    case 42: 
      p(" // SWL");
      p("addr=r" + j + "+" + i7 + ";");
      memRead("addr", "tmp");
      p("tmp = (tmp&(0xffffff00<<(((~addr)&3)<<3)))|(r" + k + ">>>((addr&3)<<3));");
      memWrite("addr", "tmp");
      break;
    
    case 43: 
      if (this.runtimeStats) p("inc(\"SW\");");
      memWrite("r" + j + "+" + i7, "r" + k);
      break;
    case 46: 
      p(" // SWR");
      p("addr=r" + j + "+" + i7 + ";");
      memRead("addr", "tmp");
      p("tmp = (tmp&(0x00ffffff>>>((addr&3)<<3)))|(r" + k + "<<(((~addr)&3)<<3));");
      memWrite("addr", "tmp");
      break;
    

    case 48: 
      memRead("r" + j + "+" + i7, "r" + k);
      break;
    case 49: 
      memRead("r" + j + "+" + i7, "f" + k);
      break;
    
    case 56: 
      memWrite("r" + j + "+" + i7, "r" + k);
      p("r" + k + "=1;");
      break;
    case 57: 
      memWrite("r" + j + "+" + i7, "f" + k);
      break;
    case 20: case 21: case 22: case 23: case 24: case 25: case 26: case 27: case 28: case 29: case 30: case 31: case 39: case 44: case 45: case 47: case 50: case 51: case 52: case 53: case 54: case 55: default: 
      throw new Compiler.Exn("Invalid Instruction: " + i + " at " + toHex(paramInt1));
    }
    
  }
  
  private void memWrite(String paramString1, String paramString2)
  {
    if (this.nullPointerCheck) p("nullPointerCheck(" + paramString1 + ");");
    if (this.onePage) {
      p("page[(" + paramString1 + ")>>>2] = " + paramString2 + ";");
    } else if (this.fastMem) {
      p("writePages[(" + paramString1 + ")>>>" + this.pageShift + "][((" + paramString1 + ")>>>2)&" + toHex((this.pageSize >> 2) - 1) + "] = " + paramString2 + ";");
    } else
      p("unsafeMemWrite(" + paramString1 + "," + paramString2 + ");");
  }
  
  private void memRead(String paramString1, String paramString2) { if (this.nullPointerCheck) p("nullPointerCheck(" + paramString1 + ");");
    if (this.onePage) {
      p(paramString2 + "= page[(" + paramString1 + ")>>>2];");
    } else if (this.fastMem) {
      p(paramString2 + " = readPages[(" + paramString1 + ")>>>" + this.pageShift + "][((" + paramString1 + ")>>>2)&" + toHex((this.pageSize >> 2) - 1) + "];");
    } else
      p(paramString2 + " = unsafeMemRead(" + paramString1 + ");"); }
  
  private static String getFloat(int paramInt) { return "(Float.intBitsToFloat(f" + paramInt + "))"; }
  
  private static String getDouble(int paramInt) { return "(Double.longBitsToDouble(((f" + (paramInt + 1) + "&0xffffffffL) << 32) | (f" + paramInt + "&0xffffffffL)))"; }
  
  private static String setFloat(int paramInt, String paramString) { return "f" + paramInt + "=Float.floatToRawIntBits(" + paramString + ");"; }
  
  private static String setDouble(int paramInt, String paramString) { return "{ long l = Double.doubleToLongBits(" + paramString + "); " + "f" + (paramInt + 1) + " = (int)(l >>> 32); f" + paramInt + " = (int)l; }"; }
}
