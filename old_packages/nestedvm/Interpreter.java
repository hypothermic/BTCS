package org.ibex.nestedvm;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;
import org.ibex.nestedvm.util.ELF;
import org.ibex.nestedvm.util.ELF.ELFHeader;
import org.ibex.nestedvm.util.ELF.PHeader;
import org.ibex.nestedvm.util.ELF.Symbol;
import org.ibex.nestedvm.util.ELF.Symtab;
import org.ibex.nestedvm.util.Seekable;

public class Interpreter extends UnixRuntime implements Cloneable
{
  private int[] registers = new int[32];
  
  private int hi;
  private int lo;
  private int[] fpregs = new int[32];
  

  private int fcsr;
  

  private int pc;
  
  public String image;
  
  private ELF.Symtab symtab;
  private int gp;
  private ELF.Symbol userInfo;
  private int entryPoint;
  private int heapStart;
  private HashMap sourceLineCache;
  
  private final void setFC(boolean paramBoolean) { this.fcsr = (this.fcsr & 0xFF7FFFFF | (paramBoolean ? 8388608 : 0)); }
  private final int roundingMode() { return this.fcsr & 0x3; }
  
  private final double getDouble(int paramInt) { return Double.longBitsToDouble((this.fpregs[(paramInt + 1)] & 0xFFFFFFFF) << 32 | this.fpregs[paramInt] & 0xFFFFFFFF); }
  
  private final void setDouble(int paramInt, double paramDouble) {
    long l = Double.doubleToLongBits(paramDouble);
    this.fpregs[(paramInt + 1)] = ((int)(l >>> 32));this.fpregs[paramInt] = ((int)l); }
  
  private final float getFloat(int paramInt) { return Float.intBitsToFloat(this.fpregs[paramInt]); }
  private final void setFloat(int paramInt, float paramFloat) { this.fpregs[paramInt] = Float.floatToRawIntBits(paramFloat); }
  
  protected void _execute() throws Runtime.ExecutionException {
    try {
      runSome();
    } catch (Runtime.ExecutionException localExecutionException) {
      localExecutionException.setLocation(toHex(this.pc) + ": " + sourceLine(this.pc));
      throw localExecutionException;
    }
  }
  
  protected Object clone() throws CloneNotSupportedException {
    Interpreter localInterpreter = (Interpreter)super.clone();
    localInterpreter.registers = ((int[])this.registers.clone());
    localInterpreter.fpregs = ((int[])this.fpregs.clone());
    return localInterpreter;
  }
  
  private final int runSome()
    throws Runtime.FaultException, Runtime.ExecutionException
  {
    int i = 1 << this.pageShift >> 2;
    int[] arrayOfInt1 = this.registers;
    int[] arrayOfInt2 = this.fpregs;
    int j = this.pc;
    int k = j + 4;
    try {
      for (;;) {
        int m;
        try {
          m = this.readPages[(j >>> this.pageShift)][(j >>> 2 & i - 1)];
        } catch (RuntimeException localRuntimeException1) {
          if (j == -559038737) throw new Error("fell off cpu: r2: " + arrayOfInt1[2]);
          m = memRead(j);
        }
        
        int n = m >>> 26 & 0xFF;
        int i1 = m >>> 21 & 0x1F;
        int i2 = m >>> 16 & 0x1F;
        int i3 = m >>> 16 & 0x1F;
        int i4 = m >>> 11 & 0x1F;
        int i5 = m >>> 11 & 0x1F;
        int i6 = m >>> 6 & 0x1F;
        int i7 = m >>> 6 & 0x1F;
        int i8 = m & 0x3F;
        
        int i9 = m & 0x3FFFFFF;
        int i10 = m & 0xFFFF;
        int i11 = m << 16 >> 16;
        int i12 = i11;
        


        arrayOfInt1[0] = 0;
        int i13;
        int i14; switch (n) {
        case 0:  long l;
          switch (i8) {
          case 0: 
            if (m != 0)
              arrayOfInt1[i2] <<= i6;
            break;
          case 2: 
            arrayOfInt1[i2] >>>= i6;
            break;
          case 3: 
            arrayOfInt1[i2] >>= i6;
            break;
          case 4: 
            arrayOfInt1[i2] <<= (arrayOfInt1[i1] & 0x1F);
            break;
          case 6: 
            arrayOfInt1[i2] >>>= (arrayOfInt1[i1] & 0x1F);
            break;
          case 7: 
            arrayOfInt1[i2] >>= (arrayOfInt1[i1] & 0x1F);
            break;
          case 8: 
            i13 = arrayOfInt1[i1];j += 4;k = i13;
            break;
          case 9: 
            i13 = arrayOfInt1[i1];j += 4;arrayOfInt1[i4] = (j + 4);k = i13;
            break;
          case 12: 
            this.pc = j;
            arrayOfInt1[2] = syscall(arrayOfInt1[2], arrayOfInt1[4], arrayOfInt1[5], arrayOfInt1[6], arrayOfInt1[7], arrayOfInt1[8], arrayOfInt1[9]);
            if (this.state != 0) this.pc = k;
            break;
          case 13: 
            throw new Runtime.ExecutionException("Break");
          case 16: 
            arrayOfInt1[i4] = this.hi;
            break;
          case 17: 
            this.hi = arrayOfInt1[i1];
            break;
          case 18: 
            arrayOfInt1[i4] = this.lo;
            break;
          case 19: 
            this.lo = arrayOfInt1[i1];
            break;
          case 24: 
            l = arrayOfInt1[i1] * arrayOfInt1[i2];
            this.hi = ((int)(l >>> 32));
            this.lo = ((int)l);
            break;
          
          case 25: 
            l = (arrayOfInt1[i1] & 0xFFFFFFFF) * (arrayOfInt1[i2] & 0xFFFFFFFF);
            this.hi = ((int)(l >>> 32));
            this.lo = ((int)l);
            break;
          
          case 26: 
            this.hi = (arrayOfInt1[i1] % arrayOfInt1[i2]);
            this.lo = (arrayOfInt1[i1] / arrayOfInt1[i2]);
            break;
          case 27: 
            if (i2 != 0) {
              this.hi = ((int)((arrayOfInt1[i1] & 0xFFFFFFFF) % (arrayOfInt1[i2] & 0xFFFFFFFF)));
              this.lo = ((int)((arrayOfInt1[i1] & 0xFFFFFFFF) / (arrayOfInt1[i2] & 0xFFFFFFFF)));
            }
            break;
          case 32: 
            throw new Runtime.ExecutionException("ADD (add with oveflow trap) not suported");
          


          case 33: 
            arrayOfInt1[i1] += arrayOfInt1[i2];
            break;
          case 34: 
            throw new Runtime.ExecutionException("SUB (sub with oveflow trap) not suported");
          


          case 35: 
            arrayOfInt1[i1] -= arrayOfInt1[i2];
            break;
          case 36: 
            arrayOfInt1[i1] &= arrayOfInt1[i2];
            break;
          case 37: 
            arrayOfInt1[i1] |= arrayOfInt1[i2];
            break;
          case 38: 
            arrayOfInt1[i1] ^= arrayOfInt1[i2];
            break;
          case 39: 
            arrayOfInt1[i4] = ((arrayOfInt1[i1] | arrayOfInt1[i2]) ^ 0xFFFFFFFF);
            break;
          case 42: 
            arrayOfInt1[i4] = (arrayOfInt1[i1] < arrayOfInt1[i2] ? 1 : 0);
            break;
          case 43: 
            arrayOfInt1[i4] = ((arrayOfInt1[i1] & 0xFFFFFFFF) < (arrayOfInt1[i2] & 0xFFFFFFFF) ? 1 : 0);
            break;
          case 1: case 5: case 10: case 11: case 14: case 15: case 20: case 21: case 22: case 23: case 28: case 29: case 30: case 31: case 40: case 41: default: 
            throw new Runtime.ExecutionException("Illegal instruction 0/" + i8);
          }
          
          break;
        case 1: 
          switch (i2) {
          case 0: 
            if (arrayOfInt1[i1] < 0) {
              j += 4;i13 = j + i12 * 4;k = i13; }
            break;
          

          case 1: 
            if (arrayOfInt1[i1] >= 0) {
              j += 4;i13 = j + i12 * 4;k = i13; }
            break;
          

          case 16: 
            if (arrayOfInt1[i1] < 0) {
              j += 4;arrayOfInt1[31] = (j + 4);i13 = j + i12 * 4;k = i13; }
            break;
          

          case 17: 
            if (arrayOfInt1[i1] >= 0) {
              j += 4;arrayOfInt1[31] = (j + 4);i13 = j + i12 * 4;k = i13; }
            break;
          

          default: 
            throw new Runtime.ExecutionException("Illegal Instruction");
          }
          
          break;
        case 2: 
          i13 = j & 0xF0000000 | i9 << 2;
          j += 4;k = i13;
          break;
        
        case 3: 
          i13 = j & 0xF0000000 | i9 << 2;
          j += 4;arrayOfInt1[31] = (j + 4);k = i13;
          break;
        
        case 4: 
          if (arrayOfInt1[i1] == arrayOfInt1[i2]) {
            j += 4;i13 = j + i12 * 4;k = i13; }
          break;
        

        case 5: 
          if (arrayOfInt1[i1] != arrayOfInt1[i2]) {
            j += 4;i13 = j + i12 * 4;k = i13; }
          break;
        

        case 6: 
          if (arrayOfInt1[i1] <= 0) {
            j += 4;i13 = j + i12 * 4;k = i13; }
          break;
        

        case 7: 
          if (arrayOfInt1[i1] > 0) {
            j += 4;i13 = j + i12 * 4;k = i13; }
          break;
        

        case 8: 
          arrayOfInt1[i1] += i11;
          break;
        case 9: 
          arrayOfInt1[i1] += i11;
          break;
        case 10: 
          arrayOfInt1[i2] = (arrayOfInt1[i1] < i11 ? 1 : 0);
          break;
        case 11: 
          arrayOfInt1[i2] = ((arrayOfInt1[i1] & 0xFFFFFFFF) < (i11 & 0xFFFFFFFF) ? 1 : 0);
          break;
        case 12: 
          arrayOfInt1[i1] &= i10;
          break;
        case 13: 
          arrayOfInt1[i1] |= i10;
          break;
        case 14: 
          arrayOfInt1[i1] ^= i10;
          break;
        case 15: 
          arrayOfInt1[i2] = (i10 << 16);
          break;
        case 16: 
          throw new Runtime.ExecutionException("TLB/Exception support not implemented");
        case 17: 
          int i15 = 0;
          String str = i15 != 0 ? sourceLine(j) : "";
          int i16 = (i15 != 0) && ((str.indexOf("dtoa.c:51") >= 0) || (str.indexOf("dtoa.c:52") >= 0) || (str.indexOf("test.c") >= 0)) ? 1 : 0;
          if ((i1 > 8) && (i16 != 0))
            System.out.println("               FP Op: " + n + "/" + i1 + "/" + i8 + " " + str);
          if ((roundingMode() != 0) && (i1 != 6) && (((i1 != 16) && (i1 != 17)) || (i8 != 36)))
            throw new Runtime.ExecutionException("Non-cvt.w.z operation attempted with roundingMode != round to nearest");
          switch (i1) {
          case 0: 
            arrayOfInt1[i2] = arrayOfInt2[i4];
            break;
          case 2: 
            if (i5 != 31) throw new Runtime.ExecutionException("FCR " + i5 + " unavailable");
            arrayOfInt1[i2] = this.fcsr;
            break;
          case 4: 
            arrayOfInt2[i4] = arrayOfInt1[i2];
            break;
          case 6: 
            if (i5 != 31) throw new Runtime.ExecutionException("FCR " + i5 + " unavailable");
            this.fcsr = arrayOfInt1[i2];
            break;
          case 8: 
            if (((this.fcsr & 0x800000) != 0 ? 1 : 0) == ((m >>> 16 & 0x1) != 0 ? 1 : 0)) {
              j += 4;i13 = j + i12 * 4;k = i13; }
            break;
          

          case 16: 
            switch (i8) {
            case 0: 
              setFloat(i7, getFloat(i5) + getFloat(i3));
              break;
            case 1: 
              setFloat(i7, getFloat(i5) - getFloat(i3));
              break;
            case 2: 
              setFloat(i7, getFloat(i5) * getFloat(i3));
              break;
            case 3: 
              setFloat(i7, getFloat(i5) / getFloat(i3));
              break;
            case 5: 
              setFloat(i7, Math.abs(getFloat(i5)));
              break;
            case 6: 
              arrayOfInt2[i7] = arrayOfInt2[i5];
              break;
            case 7: 
              setFloat(i7, -getFloat(i5));
              break;
            case 33: 
              setDouble(i7, getFloat(i5));
              break;
            case 36: 
              switch (roundingMode()) {
              case 0:  arrayOfInt2[i7] = ((int)Math.floor(getFloat(i5) + 0.5F)); break;
              case 1:  arrayOfInt2[i7] = ((int)getFloat(i5)); break;
              case 2:  arrayOfInt2[i7] = ((int)Math.ceil(getFloat(i5))); break;
              case 3:  arrayOfInt2[i7] = ((int)Math.floor(getFloat(i5)));
              }
              break;
            case 50: 
              setFC(getFloat(i5) == getFloat(i3));
              break;
            case 60: 
              setFC(getFloat(i5) < getFloat(i3));
              break;
            case 62: 
              setFC(getFloat(i5) <= getFloat(i3));
              break;
            default:  throw new Runtime.ExecutionException("Invalid Instruction 17/" + i1 + "/" + i8 + " at " + sourceLine(j));
            }
            
            break;
          case 17: 
            switch (i8) {
            case 0: 
              setDouble(i7, getDouble(i5) + getDouble(i3));
              break;
            case 1: 
              if (i16 != 0) System.out.println("f" + i7 + " = f" + i5 + " (" + getDouble(i5) + ") - f" + i3 + " (" + getDouble(i3) + ")");
              setDouble(i7, getDouble(i5) - getDouble(i3));
              break;
            case 2: 
              if (i16 != 0) System.out.println("f" + i7 + " = f" + i5 + " (" + getDouble(i5) + ") * f" + i3 + " (" + getDouble(i3) + ")");
              setDouble(i7, getDouble(i5) * getDouble(i3));
              if (i16 != 0) System.out.println("f" + i7 + " = " + getDouble(i7));
              break;
            case 3: 
              setDouble(i7, getDouble(i5) / getDouble(i3));
              break;
            case 5: 
              setDouble(i7, Math.abs(getDouble(i5)));
              break;
            case 6: 
              arrayOfInt2[i7] = arrayOfInt2[i5];
              arrayOfInt2[(i7 + 1)] = arrayOfInt2[(i5 + 1)];
              break;
            case 7: 
              setDouble(i7, -getDouble(i5));
              break;
            case 32: 
              setFloat(i7, (float)getDouble(i5));
              break;
            case 36: 
              if (i16 != 0) System.out.println("CVT.W.D rm: " + roundingMode() + " f" + i5 + ":" + getDouble(i5));
              switch (roundingMode()) {
              case 0:  arrayOfInt2[i7] = ((int)Math.floor(getDouble(i5) + 0.5D)); break;
              case 1:  arrayOfInt2[i7] = ((int)getDouble(i5)); break;
              case 2:  arrayOfInt2[i7] = ((int)Math.ceil(getDouble(i5))); break;
              case 3:  arrayOfInt2[i7] = ((int)Math.floor(getDouble(i5)));
              }
              if (i16 != 0) System.out.println("CVT.W.D: f" + i7 + ":" + arrayOfInt2[i7]);
              break;
            case 50: 
              setFC(getDouble(i5) == getDouble(i3));
              break;
            case 60: 
              setFC(getDouble(i5) < getDouble(i3));
              break;
            case 62: 
              setFC(getDouble(i5) <= getDouble(i3));
              break;
            default:  throw new Runtime.ExecutionException("Invalid Instruction 17/" + i1 + "/" + i8 + " at " + sourceLine(j));
            }
            
            break;
          case 20: 
            switch (i8) {
            case 32: 
              setFloat(i7, arrayOfInt2[i5]);
              break;
            case 33: 
              setDouble(i7, arrayOfInt2[i5]);
              break;
            default:  throw new Runtime.ExecutionException("Invalid Instruction 17/" + i1 + "/" + i8 + " at " + sourceLine(j));
            }
            break;
          case 1: case 3: case 5: case 7: case 9: case 10: case 11: 
          case 12: case 13: case 14: case 15: case 18: case 19: default: 
            throw new Runtime.ExecutionException("Invalid Instruction 17/" + i1);
          }
          break;
        case 18: 
        case 19: 
          throw new Runtime.ExecutionException("No coprocessor installed");
        case 32: 
          i14 = arrayOfInt1[i1] + i11;
          try {
            i13 = this.readPages[(i14 >>> this.pageShift)][(i14 >>> 2 & i - 1)];
          } catch (RuntimeException localRuntimeException2) {
            i13 = memRead(i14 & 0xFFFFFFFC);
          }
          switch (i14 & 0x3) {
          case 0:  i13 = i13 >>> 24 & 0xFF; break;
          case 1:  i13 = i13 >>> 16 & 0xFF; break;
          case 2:  i13 = i13 >>> 8 & 0xFF; break;
          case 3:  i13 = i13 >>> 0 & 0xFF;
          }
          if ((i13 & 0x80) != 0) i13 |= 0xFF00;
          arrayOfInt1[i2] = i13;
          break;
        
        case 33: 
          i14 = arrayOfInt1[i1] + i11;
          try {
            i13 = this.readPages[(i14 >>> this.pageShift)][(i14 >>> 2 & i - 1)];
          } catch (RuntimeException localRuntimeException3) {
            i13 = memRead(i14 & 0xFFFFFFFC);
          }
          switch (i14 & 0x3) {
          case 0:  i13 = i13 >>> 16 & 0xFFFF; break;
          case 2:  i13 = i13 >>> 0 & 0xFFFF; break;
          default:  throw new Runtime.ReadFaultException(i14);
          }
          if ((i13 & 0x8000) != 0) i13 |= 0xFFFF0000;
          arrayOfInt1[i2] = i13;
          break;
        
        case 34: 
          i14 = arrayOfInt1[i1] + i11;
          try {
            i13 = this.readPages[(i14 >>> this.pageShift)][(i14 >>> 2 & i - 1)];
          } catch (RuntimeException localRuntimeException4) {
            i13 = memRead(i14 & 0xFFFFFFFC);
          }
          switch (i14 & 0x3) {
          case 0:  arrayOfInt1[i2] = (arrayOfInt1[i2] & 0x0 | i13 << 0); break;
          case 1:  arrayOfInt1[i2] = (arrayOfInt1[i2] & 0xFF | i13 << 8); break;
          case 2:  arrayOfInt1[i2] = (arrayOfInt1[i2] & 0xFFFF | i13 << 16); break;
          case 3:  arrayOfInt1[i2] = (arrayOfInt1[i2] & 0xFFFFFF | i13 << 24);
          }
          break;
        
        case 35: 
          i14 = arrayOfInt1[i1] + i11;
          try {
            arrayOfInt1[i2] = this.readPages[(i14 >>> this.pageShift)][(i14 >>> 2 & i - 1)];
          } catch (RuntimeException localRuntimeException5) {
            arrayOfInt1[i2] = memRead(i14);
          }
        
        case 36: 
          i14 = arrayOfInt1[i1] + i11;
          try {
            i13 = this.readPages[(i14 >>> this.pageShift)][(i14 >>> 2 & i - 1)];
          } catch (RuntimeException localRuntimeException6) {
            i13 = memRead(i14);
          }
          switch (i14 & 0x3) {
          case 0:  arrayOfInt1[i2] = (i13 >>> 24 & 0xFF); break;
          case 1:  arrayOfInt1[i2] = (i13 >>> 16 & 0xFF); break;
          case 2:  arrayOfInt1[i2] = (i13 >>> 8 & 0xFF); break;
          case 3:  arrayOfInt1[i2] = (i13 >>> 0 & 0xFF);
          }
          break;
        
        case 37: 
          i14 = arrayOfInt1[i1] + i11;
          try {
            i13 = this.readPages[(i14 >>> this.pageShift)][(i14 >>> 2 & i - 1)];
          } catch (RuntimeException localRuntimeException7) {
            i13 = memRead(i14 & 0xFFFFFFFC);
          }
          switch (i14 & 0x3) {
          case 0:  arrayOfInt1[i2] = (i13 >>> 16 & 0xFFFF); break;
          case 2:  arrayOfInt1[i2] = (i13 >>> 0 & 0xFFFF); break;
          default:  throw new Runtime.ReadFaultException(i14);
          }
          
        
        case 38: 
          i14 = arrayOfInt1[i1] + i11;
          try {
            i13 = this.readPages[(i14 >>> this.pageShift)][(i14 >>> 2 & i - 1)];
          } catch (RuntimeException localRuntimeException8) {
            i13 = memRead(i14 & 0xFFFFFFFC);
          }
          switch (i14 & 0x3) {
          case 0:  arrayOfInt1[i2] = (arrayOfInt1[i2] & 0xFF00 | i13 >>> 24); break;
          case 1:  arrayOfInt1[i2] = (arrayOfInt1[i2] & 0xFFFF0000 | i13 >>> 16); break;
          case 2:  arrayOfInt1[i2] = (arrayOfInt1[i2] & 0xFF000000 | i13 >>> 8); break;
          case 3:  arrayOfInt1[i2] = (arrayOfInt1[i2] & 0x0 | i13 >>> 0);
          }
          break;
        
        case 40: 
          i14 = arrayOfInt1[i1] + i11;
          try {
            i13 = this.readPages[(i14 >>> this.pageShift)][(i14 >>> 2 & i - 1)];
          } catch (RuntimeException localRuntimeException9) {
            i13 = memRead(i14 & 0xFFFFFFFC);
          }
          switch (i14 & 0x3) {
          case 0:  i13 = i13 & 0xFFFFFF | (arrayOfInt1[i2] & 0xFF) << 24; break;
          case 1:  i13 = i13 & 0xFF00FFFF | (arrayOfInt1[i2] & 0xFF) << 16; break;
          case 2:  i13 = i13 & 0xFFFF00FF | (arrayOfInt1[i2] & 0xFF) << 8; break;
          case 3:  i13 = i13 & 0xFF00 | (arrayOfInt1[i2] & 0xFF) << 0;
          }
          try {
            this.writePages[(i14 >>> this.pageShift)][(i14 >>> 2 & i - 1)] = i13;
          } catch (RuntimeException localRuntimeException10) {
            memWrite(i14 & 0xFFFFFFFC, i13);
          }
        

        case 41: 
          i14 = arrayOfInt1[i1] + i11;
          try {
            i13 = this.readPages[(i14 >>> this.pageShift)][(i14 >>> 2 & i - 1)];
          } catch (RuntimeException localRuntimeException11) {
            i13 = memRead(i14 & 0xFFFFFFFC);
          }
          switch (i14 & 0x3) {
          case 0:  i13 = i13 & 0xFFFF | (arrayOfInt1[i2] & 0xFFFF) << 16; break;
          case 2:  i13 = i13 & 0xFFFF0000 | (arrayOfInt1[i2] & 0xFFFF) << 0; break;
          default:  throw new Runtime.WriteFaultException(i14);
          }
          try {
            this.writePages[(i14 >>> this.pageShift)][(i14 >>> 2 & i - 1)] = i13;
          } catch (RuntimeException localRuntimeException12) {
            memWrite(i14 & 0xFFFFFFFC, i13);
          }
        

        case 42: 
          i14 = arrayOfInt1[i1] + i11;
          i13 = memRead(i14 & 0xFFFFFFFC);
          switch (i14 & 0x3) {
          case 0:  i13 = i13 & 0x0 | arrayOfInt1[i2] >>> 0; break;
          case 1:  i13 = i13 & 0xFF000000 | arrayOfInt1[i2] >>> 8; break;
          case 2:  i13 = i13 & 0xFFFF0000 | arrayOfInt1[i2] >>> 16; break;
          case 3:  i13 = i13 & 0xFF00 | arrayOfInt1[i2] >>> 24;
          }
          try {
            this.writePages[(i14 >>> this.pageShift)][(i14 >>> 2 & i - 1)] = i13;
          } catch (RuntimeException localRuntimeException13) {
            memWrite(i14 & 0xFFFFFFFC, i13);
          }
        

        case 43: 
          i14 = arrayOfInt1[i1] + i11;
          try {
            this.writePages[(i14 >>> this.pageShift)][(i14 >>> 2 & i - 1)] = arrayOfInt1[i2];
          } catch (RuntimeException localRuntimeException14) {
            memWrite(i14 & 0xFFFFFFFC, arrayOfInt1[i2]);
          }
        
        case 46: 
          i14 = arrayOfInt1[i1] + i11;
          i13 = memRead(i14 & 0xFFFFFFFC);
          switch (i14 & 0x3) {
          case 0:  i13 = i13 & 0xFFFFFF | arrayOfInt1[i2] << 24; break;
          case 1:  i13 = i13 & 0xFFFF | arrayOfInt1[i2] << 16; break;
          case 2:  i13 = i13 & 0xFF | arrayOfInt1[i2] << 8; break;
          case 3:  i13 = i13 & 0x0 | arrayOfInt1[i2] << 0;
          }
          memWrite(i14 & 0xFFFFFFFC, i13);
          break;
        

        case 48: 
          arrayOfInt1[i2] = memRead(arrayOfInt1[i1] + i11);
          break;
        case 49: 
          arrayOfInt2[i2] = memRead(arrayOfInt1[i1] + i11);
          break;
        
        case 56: 
          memWrite(arrayOfInt1[i1] + i11, arrayOfInt1[i2]);
          arrayOfInt1[i2] = 1;
          break;
        case 57: 
          memWrite(arrayOfInt1[i1] + i11, arrayOfInt2[i2]);
          break;
        case 20: case 21: case 22: case 23: case 24: case 25: case 26: case 27: case 28: case 29: case 30: case 31: case 39: case 44: case 45: case 47: case 50: case 51: case 52: case 53: case 54: case 55: default: 
          throw new Runtime.ExecutionException("Invalid Instruction: " + n);
          
          j = k;
          k = j + 4; }
      }
    } catch (Runtime.ExecutionException localExecutionException) {
      this.pc = j;
      throw localExecutionException;
    }
    return 0;
  }
  
  public int lookupSymbol(String paramString) {
    ELF.Symbol localSymbol = this.symtab.getGlobalSymbol(paramString);
    return localSymbol == null ? -1 : localSymbol.addr;
  }
  
  protected int gp() {
    return this.gp;
  }
  
  protected int userInfoBae() { return this.userInfo == null ? 0 : this.userInfo.addr; }
  protected int userInfoSize() { return this.userInfo == null ? 0 : this.userInfo.size; }
  
  protected int entryPoint() {
    return this.entryPoint;
  }
  
  protected int heapStart() { return this.heapStart; }
  
  private void loadImage(Seekable paramSeekable) throws IOException
  {
    ELF localELF = new ELF(paramSeekable);
    this.symtab = localELF.getSymtab();
    
    if (localELF.header.type != 2) throw new IOException("Binary is not an executable");
    if (localELF.header.machine != 8) throw new IOException("Binary is not for the MIPS I Architecture");
    if (localELF.ident.data != 2) { throw new IOException("Binary is not big endian");
    }
    this.entryPoint = localELF.header.entry;
    
    ELF.Symtab localSymtab = localELF.getSymtab();
    if (localSymtab == null) throw new IOException("No symtab in binary (did you strip it?)");
    this.userInfo = localSymtab.getGlobalSymbol("user_info");
    ELF.Symbol localSymbol = localSymtab.getGlobalSymbol("_gp");
    
    if (localSymbol == null) throw new IOException("NO _gp symbol!");
    this.gp = localSymbol.addr;
    
    this.entryPoint = localELF.header.entry;
    
    ELF.PHeader[] arrayOfPHeader = localELF.pheaders;
    int i = 0;
    int j = 1 << this.pageShift;
    int k = 1 << this.pageShift >> 2;
    for (int m = 0; m < arrayOfPHeader.length; m++) {
      ELF.PHeader localPHeader = arrayOfPHeader[m];
      if (localPHeader.type == 1) {
        int n = localPHeader.memsz;
        int i1 = localPHeader.filesz;
        if (n != 0) {
          if (n < 0) throw new IOException("pheader size too large");
          int i2 = localPHeader.vaddr;
          if (i2 == 0) throw new IOException("pheader vaddr == 0x0");
          i = max(i2 + n, i);
          
          for (int i3 = 0; i3 < n + j - 1; i3 += j) {
            int i4 = i3 + i2 >>> this.pageShift;
            if (this.readPages[i4] == null)
              this.readPages[i4] = new int[k];
            if (localPHeader.writable()) this.writePages[i4] = this.readPages[i4];
          }
          if (i1 != 0) {
            i1 &= 0xFFFFFFFC;
            DataInputStream localDataInputStream = new DataInputStream(localPHeader.getInputStream());
            do {
              this.readPages[(i2 >>> this.pageShift)][(i2 >>> 2 & k - 1)] = localDataInputStream.readInt();
              i2 += 4;
              i1 -= 4;
            } while (i1 > 0);
            localDataInputStream.close();
          }
        } } }
    this.heapStart = (i + j - 1 & (j - 1 ^ 0xFFFFFFFF));
  }
  
  protected void setCPUState(Runtime.CPUState paramCPUState) {
    for (int i = 1; i < 32; i++) this.registers[i] = paramCPUState.r[i];
    for (i = 0; i < 32; i++) this.fpregs[i] = paramCPUState.f[i];
    this.hi = paramCPUState.hi;this.lo = paramCPUState.lo;this.fcsr = paramCPUState.fcsr;
    this.pc = paramCPUState.pc;
  }
  
  protected void getCPUState(Runtime.CPUState paramCPUState) {
    for (int i = 1; i < 32; i++) paramCPUState.r[i] = this.registers[i];
    for (i = 0; i < 32; i++) paramCPUState.f[i] = this.fpregs[i];
    paramCPUState.hi = this.hi;paramCPUState.lo = this.lo;paramCPUState.fcsr = this.fcsr;
    paramCPUState.pc = this.pc;
  }
  
  public Interpreter(Seekable paramSeekable) throws IOException {
    super(4096, 65536);
    loadImage(paramSeekable);
  }
  
  public Interpreter(String paramString) throws IOException { this(new org.ibex.nestedvm.util.Seekable.File(paramString, false));
    this.image = paramString; }
  
  public Interpreter(java.io.InputStream paramInputStream) throws IOException { this(new org.ibex.nestedvm.util.Seekable.InputStream(paramInputStream)); }
  



  public String sourceLine(int paramInt)
  {
    String str = (String)(this.sourceLineCache == null ? null : this.sourceLineCache.get(new Integer(paramInt)));
    if (str != null) return str;
    if (this.image == null) return null;
    try {
      Process localProcess = java.lang.Runtime.getRuntime().exec(new String[] { "mips-unknown-elf-addr2line", "-e", this.image, toHex(paramInt) });
      str = new java.io.BufferedReader(new java.io.InputStreamReader(localProcess.getInputStream())).readLine();
      if (str == null) return null;
      while (str.startsWith("../")) str = str.substring(3);
      if (this.sourceLineCache == null) this.sourceLineCache = new HashMap();
      this.sourceLineCache.put(new Integer(paramInt), str);
      return str;
    } catch (IOException localIOException) {}
    return null;
  }
  
  public class DebugShutdownHook implements Runnable {
    public DebugShutdownHook() {}
    
    public void run() { int i = Interpreter.this.pc;
      if (Interpreter.this.getState() == 0)
        System.err.print("\nCPU Executing " + Runtime.toHex(i) + ": " + Interpreter.this.sourceLine(i) + "\n");
    }
  }
  
  public static void main(String[] paramArrayOfString) throws Exception {
    String str = paramArrayOfString[0];
    Interpreter localInterpreter = new Interpreter(str); Interpreter 
      tmp25_24 = localInterpreter;tmp25_24.getClass();java.lang.Runtime.getRuntime().addShutdownHook(new Thread(new DebugShutdownHook(tmp25_24)));
    int i = localInterpreter.run(paramArrayOfString);
    System.err.println("Exit status: " + i);
    System.exit(i);
  }
}
