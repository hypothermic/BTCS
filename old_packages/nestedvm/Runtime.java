package org.ibex.nestedvm;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import org.ibex.nestedvm.util.Platform;
import org.ibex.nestedvm.util.Seekable;
import org.ibex.nestedvm.util.Seekable.File;












public abstract class Runtime
  implements UsermodeConstants, Registers, Cloneable
{
  public static final String VERSION = "1.0";
  static final boolean STDERR_DIAG = true;
  protected final int pageShift;
  private final int stackBottom;
  protected int[][] readPages;
  protected int[][] writePages;
  private int heapEnd;
  private static final int STACK_GUARD_PAGES = 4;
  private long startTime;
  public static final int RUNNING = 0;
  public static final int STOPPED = 1;
  public static final int PAUSED = 2;
  public static final int CALLJAVA = 3;
  public static final int EXITED = 4;
  public static final int EXECED = 5;
  
  protected int userInfoBase() { return 0; }
  protected int userInfoSize() { return 0; }
  




















  protected int state = 1;
  
  public final int getState() { return this.state; }
  
  private int exitStatus;
  public ExecutionException exitException;
  FD[] fds;
  boolean[] closeOnExec;
  SecurityManager sm;
  private CallJavaCB callJavaCB;
  private byte[] _byteBuf;
  static final int MAX_CHUNK = 16776192;
  static final boolean win32Hacks;
  public void setSecurityManager(SecurityManager paramSecurityManager)
  {
    this.sm = paramSecurityManager;
  }
  
  public void setCallJavaCB(CallJavaCB paramCallJavaCB) {
    this.callJavaCB = paramCallJavaCB;
  }
  

  public static final int RD_ONLY = 0;
  public static final int WR_ONLY = 1;
  public static final int RDWR = 2;
  public static final int O_CREAT = 512;
  public static final int O_EXCL = 2048;
  public static final int O_APPEND = 8;
  public static final int O_TRUNC = 1024;
  public static final int O_NONBLOCK = 16384;
  public static final int O_NOCTTY = 32768;
  public int lookupSymbol(String paramString)
  {
    return -1;
  }
  







  static
  {
    String str1 = Platform.getProperty("os.name");
    String str2 = Platform.getProperty("nestedvm.win32hacks");
    if (str2 != null) win32Hacks = Boolean.valueOf(str2).booleanValue(); else
      win32Hacks = (str1 != null) && (str1.toLowerCase().indexOf("windows") != -1);
  }
  
  protected Object clone() throws CloneNotSupportedException {
    Runtime localRuntime = (Runtime)super.clone();
    localRuntime._byteBuf = null;
    localRuntime.startTime = 0L;
    localRuntime.fds = new FD[64];
    for (int i = 0; i < 64; i++) if (this.fds[i] != null) localRuntime.fds[i] = this.fds[i].dup();
    i = this.writePages.length;
    localRuntime.readPages = new int[i][];
    localRuntime.writePages = new int[i][];
    for (int j = 0; j < i; j++) {
      if (this.readPages[j] != null)
        if (this.writePages[j] == null) localRuntime.readPages[j] = this.readPages[j]; else
          localRuntime.readPages[j] = (localRuntime.writePages[j] = (int[])(int[])this.writePages[j].clone());
    }
    return localRuntime;
  }
  
  protected Runtime(int paramInt1, int paramInt2) { this(paramInt1, paramInt2, false); }
  
  protected Runtime(int paramInt1, int paramInt2, boolean paramBoolean) { if (paramInt1 <= 0) throw new IllegalArgumentException("pageSize <= 0");
    if (paramInt2 <= 0) throw new IllegalArgumentException("totalPages <= 0");
    if ((paramInt1 & paramInt1 - 1) != 0) { throw new IllegalArgumentException("pageSize not a power of two");
    }
    int i = 0;
    while (paramInt1 >>> i != 1) i++;
    this.pageShift = i;
    
    int j = heapStart();
    int k = paramInt2 * paramInt1;
    int m = max(k / 512, 131072);
    int n = 0;
    if (paramInt2 > 1) {
      m = max(m, paramInt1);
      m = m + paramInt1 - 1 & (paramInt1 - 1 ^ 0xFFFFFFFF);
      n = m >>> this.pageShift;
      j = j + paramInt1 - 1 & (paramInt1 - 1 ^ 0xFFFFFFFF);
      if (n + 4 + (j >>> this.pageShift) >= paramInt2)
        throw new IllegalArgumentException("total pages too small");
    } else {
      if (paramInt1 < j + m) throw new IllegalArgumentException("total memory too small");
      j = j + 4095 & 0xEFFF;
    }
    
    this.stackBottom = (k - m);
    this.heapEnd = j;
    
    this.readPages = new int[paramInt2][];
    this.writePages = new int[paramInt2][];
    
    if (paramInt2 == 1) {
      this.readPages[0] = (this.writePages[0] = new int[paramInt1 >> 2]);
    } else {
      for (int i1 = this.stackBottom >>> this.pageShift; i1 < this.writePages.length; i1++) {
        this.readPages[i1] = (this.writePages[i1] = new int[paramInt1 >> 2]);
      }
    }
    
    if (!paramBoolean) {
      this.fds = new FD[64];
      this.closeOnExec = new boolean[64];
      
      InputStream localInputStream = win32Hacks ? new Win32ConsoleIS(System.in) : System.in;
      addFD(new TerminalFD(localInputStream));
      addFD(new TerminalFD(System.out));
      addFD(new TerminalFD(System.err));
    }
  }
  

  protected final void initPages(int[] paramArrayOfInt, int paramInt, boolean paramBoolean)
  {
    int i = 1 << this.pageShift >>> 2;
    int j = (1 << this.pageShift) - 1;
    
    for (int k = 0; k < paramArrayOfInt.length;) {
      int m = paramInt >>> this.pageShift;
      int n = (paramInt & j) >> 2;
      int i1 = min(i - n, paramArrayOfInt.length - k);
      if (this.readPages[m] == null) {
        initPage(m, paramBoolean);
      } else if ((!paramBoolean) && 
        (this.writePages[m] == null)) { this.writePages[m] = this.readPages[m];
      }
      System.arraycopy(paramArrayOfInt, k, this.readPages[m], n, i1);
      k += i1;
      paramInt += i1 * 4;
    }
  }
  
  protected final void clearPages(int paramInt1, int paramInt2)
  {
    int i = 1 << this.pageShift >>> 2;
    int j = (1 << this.pageShift) - 1;
    
    for (int k = 0; k < paramInt2;) {
      int m = paramInt1 >>> this.pageShift;
      int n = (paramInt1 & j) >> 2;
      int i1 = min(i - n, paramInt2 - k);
      if (this.readPages[m] == null) {
        this.readPages[m] = (this.writePages[m] = new int[i]);
      } else {
        if (this.writePages[m] == null) this.writePages[m] = this.readPages[m];
        for (int i2 = n; i2 < n + i1; i2++) this.writePages[m][i2] = 0;
      }
      k += i1;
      paramInt1 += i1 * 4;
    }
  }
  
  public final void copyin(int paramInt1, byte[] paramArrayOfByte, int paramInt2)
    throws Runtime.ReadFaultException
  {
    int i = 1 << this.pageShift >>> 2;
    int j = i - 1;
    
    int k = 0;
    if (paramInt2 == 0) return;
    int m; if ((paramInt1 & 0x3) != 0) {
      m = memRead(paramInt1 & 0xFFFFFFFC);
      switch (paramInt1 & 0x3) {
      case 1:  paramArrayOfByte[(k++)] = ((byte)(m >>> 16 & 0xFF));paramInt2--; if (paramInt2 == 0)
          break;  case 2:  paramArrayOfByte[(k++)] = ((byte)(m >>> 8 & 0xFF));paramInt2--; if (paramInt2 == 0)
          break;  case 3:  paramArrayOfByte[(k++)] = ((byte)(m >>> 0 & 0xFF));paramInt2--; if (paramInt2 != 0)
          break; }
      paramInt1 = (paramInt1 & 0xFFFFFFFC) + 4;
    }
    if ((paramInt2 & 0xFFFFFFFC) != 0) {
      m = paramInt2 >>> 2;
      int n = paramInt1 >>> 2;
      int i2; for (; m != 0; 
          








          m -= i2)
      {
        int[] arrayOfInt = this.readPages[(n >>> this.pageShift - 2)];
        if (arrayOfInt == null) throw new ReadFaultException(n << 2);
        int i1 = n & j;
        i2 = min(m, i - i1);
        for (int i3 = 0; i3 < i2; k += 4) {
          int i4 = arrayOfInt[(i1 + i3)];
          paramArrayOfByte[(k + 0)] = ((byte)(i4 >>> 24 & 0xFF));paramArrayOfByte[(k + 1)] = ((byte)(i4 >>> 16 & 0xFF));
          paramArrayOfByte[(k + 2)] = ((byte)(i4 >>> 8 & 0xFF));paramArrayOfByte[(k + 3)] = ((byte)(i4 >>> 0 & 0xFF));i3++;
        }
        

        n += i2;
      }
      paramInt1 = n << 2;paramInt2 &= 0x3;
    }
    if (paramInt2 != 0) {
      m = memRead(paramInt1);
      switch (paramInt2) {
      case 3:  paramArrayOfByte[(k + 2)] = ((byte)(m >>> 8 & 0xFF));
      case 2:  paramArrayOfByte[(k + 1)] = ((byte)(m >>> 16 & 0xFF));
      case 1:  paramArrayOfByte[(k + 0)] = ((byte)(m >>> 24 & 0xFF));
      }
    }
  }
  
  public final void copyout(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws Runtime.FaultException
  {
    int i = 1 << this.pageShift >>> 2;
    int j = i - 1;
    
    int k = 0;
    if (paramInt2 == 0) return;
    int m; if ((paramInt1 & 0x3) != 0) {
      m = memRead(paramInt1 & 0xFFFFFFFC);
      switch (paramInt1 & 0x3) {
      case 1:  m = m & 0xFF00FFFF | (paramArrayOfByte[(k++)] & 0xFF) << 16;paramInt2--; if (paramInt2 == 0)
          break;  case 2:  m = m & 0xFFFF00FF | (paramArrayOfByte[(k++)] & 0xFF) << 8;paramInt2--; if (paramInt2 == 0)
          break;  case 3:  m = m & 0xFF00 | (paramArrayOfByte[(k++)] & 0xFF) << 0;paramInt2--; if (paramInt2 != 0)
          break; }
      memWrite(paramInt1 & 0xFFFFFFFC, m);
      paramInt1 += k;
    }
    
    if ((paramInt2 & 0xFFFFFFFC) != 0) {
      m = paramInt2 >>> 2;
      int n = paramInt1 >>> 2;
      int i2; for (; m != 0; 
          





          m -= i2)
      {
        int[] arrayOfInt = this.writePages[(n >>> this.pageShift - 2)];
        if (arrayOfInt == null) throw new WriteFaultException(n << 2);
        int i1 = n & j;
        i2 = min(m, i - i1);
        for (int i3 = 0; i3 < i2; k += 4) {
          arrayOfInt[(i1 + i3)] = ((paramArrayOfByte[(k + 0)] & 0xFF) << 24 | (paramArrayOfByte[(k + 1)] & 0xFF) << 16 | (paramArrayOfByte[(k + 2)] & 0xFF) << 8 | (paramArrayOfByte[(k + 3)] & 0xFF) << 0);i3++; }
        n += i2;
      }
      paramInt1 = n << 2;paramInt2 &= 0x3;
    }
    
    if (paramInt2 != 0) {
      m = memRead(paramInt1);
      switch (paramInt2) {
      case 1:  m = m & 0xFFFFFF | (paramArrayOfByte[(k + 0)] & 0xFF) << 24; break;
      case 2:  m = m & 0xFFFF | (paramArrayOfByte[(k + 0)] & 0xFF) << 24 | (paramArrayOfByte[(k + 1)] & 0xFF) << 16; break;
      case 3:  m = m & 0xFF | (paramArrayOfByte[(k + 0)] & 0xFF) << 24 | (paramArrayOfByte[(k + 1)] & 0xFF) << 16 | (paramArrayOfByte[(k + 2)] & 0xFF) << 8;
      }
      memWrite(paramInt1, m);
    }
  }
  
  public final void memcpy(int paramInt1, int paramInt2, int paramInt3) throws Runtime.FaultException {
    int i = 1 << this.pageShift >>> 2;
    int j = i - 1;
    int k; if (((paramInt1 & 0x3) == 0) && ((paramInt2 & 0x3) == 0)) { int m;
      if ((paramInt3 & 0xFFFFFFFC) != 0) {
        k = paramInt3 >> 2;
        m = paramInt2 >>> 2;
        int n = paramInt1 >>> 2;
        int i3; for (; k != 0; 
            







            k -= i3)
        {
          int[] arrayOfInt1 = this.readPages[(m >>> this.pageShift - 2)];
          if (arrayOfInt1 == null) throw new ReadFaultException(m << 2);
          int[] arrayOfInt2 = this.writePages[(n >>> this.pageShift - 2)];
          if (arrayOfInt2 == null) throw new WriteFaultException(n << 2);
          int i1 = m & j;
          int i2 = n & j;
          i3 = min(k, i - max(i1, i2));
          System.arraycopy(arrayOfInt1, i1, arrayOfInt2, i2, i3);
          m += i3;n += i3;
        }
        paramInt2 = m << 2;paramInt1 = n << 2;paramInt3 &= 0x3;
      }
      if (paramInt3 != 0) {
        k = memRead(paramInt2);
        m = memRead(paramInt1);
        switch (paramInt3) {
        case 1:  memWrite(paramInt1, k & 0xFF000000 | m & 0xFFFFFF); break;
        case 2:  memWrite(paramInt1, k & 0xFFFF0000 | m & 0xFFFF); break;
        case 3:  memWrite(paramInt1, k & 0xFF00 | m & 0xFF);
        }
      }
    } else {
      for (; paramInt3 > 0; 
          



          paramInt1 += k)
      {
        k = min(paramInt3, 16776192);
        byte[] arrayOfByte = byteBuf(k);
        copyin(paramInt2, arrayOfByte, k);
        copyout(arrayOfByte, paramInt1, k);
        paramInt3 -= k;paramInt2 += k;
      }
    }
  }
  
  public final void memset(int paramInt1, int paramInt2, int paramInt3) throws Runtime.FaultException {
    int i = 1 << this.pageShift >>> 2;
    int j = i - 1;
    
    int k = (paramInt2 & 0xFF) << 24 | (paramInt2 & 0xFF) << 16 | (paramInt2 & 0xFF) << 8 | (paramInt2 & 0xFF) << 0;
    int m; if ((paramInt1 & 0x3) != 0) {
      m = memRead(paramInt1 & 0xFFFFFFFC);
      switch (paramInt1 & 0x3) {
      case 1:  m = m & 0xFF00FFFF | (paramInt2 & 0xFF) << 16;paramInt3--; if (paramInt3 == 0)
          break;  case 2:  m = m & 0xFFFF00FF | (paramInt2 & 0xFF) << 8;paramInt3--; if (paramInt3 == 0)
          break;  case 3:  m = m & 0xFF00 | (paramInt2 & 0xFF) << 0;paramInt3--; if (paramInt3 != 0)
          break; }
      memWrite(paramInt1 & 0xFFFFFFFC, m);
      paramInt1 = (paramInt1 & 0xFFFFFFFC) + 4;
    }
    if ((paramInt3 & 0xFFFFFFFC) != 0) {
      m = paramInt3 >> 2;
      int n = paramInt1 >>> 2;
      int i2; for (; m != 0; 
          





          m -= i2)
      {
        int[] arrayOfInt = this.readPages[(n >>> this.pageShift - 2)];
        if (arrayOfInt == null) throw new WriteFaultException(n << 2);
        int i1 = n & j;
        i2 = min(m, i - i1);
        
        for (int i3 = i1; i3 < i1 + i2; i3++) arrayOfInt[i3] = k;
        n += i2;
      }
      paramInt1 = n << 2;paramInt3 &= 0x3;
    }
    if (paramInt3 != 0) {
      m = memRead(paramInt1);
      switch (paramInt3) {
      case 1:  m = m & 0xFFFFFF | k & 0xFF000000; break;
      case 2:  m = m & 0xFFFF | k & 0xFFFF0000; break;
      case 3:  m = m & 0xFF | k & 0xFF00;
      }
      memWrite(paramInt1, m);
    }
  }
  
  public final int memRead(int paramInt) throws Runtime.ReadFaultException
  {
    if ((paramInt & 0x3) != 0) throw new ReadFaultException(paramInt);
    return unsafeMemRead(paramInt);
  }
  
  protected final int unsafeMemRead(int paramInt) throws Runtime.ReadFaultException {
    int i = paramInt >>> this.pageShift;
    int j = (paramInt & (1 << this.pageShift) - 1) >> 2;
    try {
      return this.readPages[i][j];
    } catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException) {
      if ((i < 0) || (i >= this.readPages.length)) throw new ReadFaultException(paramInt);
      throw localArrayIndexOutOfBoundsException;
    } catch (NullPointerException localNullPointerException) {
      throw new ReadFaultException(paramInt);
    }
  }
  
  public final void memWrite(int paramInt1, int paramInt2) throws Runtime.WriteFaultException
  {
    if ((paramInt1 & 0x3) != 0) throw new WriteFaultException(paramInt1);
    unsafeMemWrite(paramInt1, paramInt2);
  }
  
  protected final void unsafeMemWrite(int paramInt1, int paramInt2) throws Runtime.WriteFaultException {
    int i = paramInt1 >>> this.pageShift;
    int j = (paramInt1 & (1 << this.pageShift) - 1) >> 2;
    try {
      this.writePages[i][j] = paramInt2;
    } catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException) {
      if ((i < 0) || (i >= this.writePages.length)) throw new WriteFaultException(paramInt1);
      throw localArrayIndexOutOfBoundsException;
    } catch (NullPointerException localNullPointerException) {
      throw new WriteFaultException(paramInt1);
    }
  }
  

  private final int[] initPage(int paramInt) { return initPage(paramInt, false); }
  
  private final int[] initPage(int paramInt, boolean paramBoolean) {
    int[] arrayOfInt = new int[1 << this.pageShift >>> 2];
    this.writePages[paramInt] = (paramBoolean ? null : arrayOfInt);
    this.readPages[paramInt] = arrayOfInt;
    return arrayOfInt;
  }
  

  public final int exitStatus()
  {
    if (this.state != 4) throw new IllegalStateException("exitStatus() called in an inappropriate state");
    return this.exitStatus;
  }
  
  private int addStringArray(String[] paramArrayOfString, int paramInt) throws Runtime.FaultException {
    int i = paramArrayOfString.length;
    int j = 0;
    for (int k = 0; k < i; k++) j += paramArrayOfString[k].length() + 1;
    j += (i + 1) * 4;
    k = paramInt - j & 0xFFFFFFFC;
    int m = k + (i + 1) * 4;
    int[] arrayOfInt = new int[i + 1];
    try {
      for (int n = 0; n < i; n++) {
        byte[] arrayOfByte = getBytes(paramArrayOfString[n]);
        arrayOfInt[n] = m;
        copyout(arrayOfByte, m, arrayOfByte.length);
        memset(m + arrayOfByte.length, 0, 1);
        m += arrayOfByte.length + 1;
      }
      m = k;
      for (n = 0; n < i + 1; n++) {
        memWrite(m, arrayOfInt[n]);
        m += 4;
      }
    } catch (FaultException localFaultException) {
      throw new RuntimeException(localFaultException.toString());
    }
    return k;
  }
  
  String[] createEnv(String[] paramArrayOfString) { if (paramArrayOfString == null) paramArrayOfString = new String[0]; return paramArrayOfString;
  }
  



  public void setUserInfo(int paramInt1, int paramInt2)
  {
    if ((paramInt1 < 0) || (paramInt1 >= userInfoSize() / 4)) throw new IndexOutOfBoundsException("setUserInfo called with index >= " + userInfoSize() / 4);
    try {
      memWrite(userInfoBase() + paramInt1 * 4, paramInt2);
    } catch (FaultException localFaultException) { throw new RuntimeException(localFaultException.toString());
    }
  }
  
  public int getUserInfo(int paramInt)
  {
    if ((paramInt < 0) || (paramInt >= userInfoSize() / 4)) throw new IndexOutOfBoundsException("setUserInfo called with index >= " + userInfoSize() / 4);
    try {
      return memRead(userInfoBase() + paramInt * 4);
    } catch (FaultException localFaultException) { throw new RuntimeException(localFaultException.toString());
    }
  }
  
  private void __execute() {
    try {
      _execute();
    } catch (FaultException localFaultException) {
      localFaultException.printStackTrace();
      exit(139, true);
      this.exitException = localFaultException;
    } catch (ExecutionException localExecutionException) {
      localExecutionException.printStackTrace();
      exit(132, true);
      this.exitException = localExecutionException;
    }
  }
  
  public final boolean execute()
  {
    if (this.state != 2) throw new IllegalStateException("execute() called in inappropriate state");
    if (this.startTime == 0L) this.startTime = System.currentTimeMillis();
    this.state = 0;
    __execute();
    if ((this.state != 2) && (this.state != 4) && (this.state != 5))
      throw new IllegalStateException("execute() ended up in an inappropriate state (" + this.state + ")");
    return this.state != 2;
  }
  
  static String[] concatArgv(String paramString, String[] paramArrayOfString) {
    String[] arrayOfString = new String[paramArrayOfString.length + 1];
    System.arraycopy(paramArrayOfString, 0, arrayOfString, 1, paramArrayOfString.length);
    arrayOfString[0] = paramString;
    return arrayOfString;
  }
  
  public final int run() { return run(null); }
  public final int run(String paramString, String[] paramArrayOfString) { return run(concatArgv(paramString, paramArrayOfString)); }
  public final int run(String[] paramArrayOfString) { return run(paramArrayOfString, null); }
  

  public final int run(String[] paramArrayOfString1, String[] paramArrayOfString2)
  {
    start(paramArrayOfString1, paramArrayOfString2);
    
    while (!execute()) {
      System.err.println("WARNING: Pause requested while executing run()");
    }
    if (this.state == 5) System.err.println("WARNING: Process exec()ed while being run under run()");
    return this.state == 4 ? exitStatus() : 0;
  }
  
  public final void start() { start(null); }
  public final void start(String[] paramArrayOfString) { start(paramArrayOfString, null); }
  

  public final void start(String[] paramArrayOfString1, String[] paramArrayOfString2)
  {
    if (this.state != 1) throw new IllegalStateException("start() called in inappropriate state");
    if (paramArrayOfString1 == null) paramArrayOfString1 = new String[] { getClass().getName() };
    int i;
    int j = i = this.writePages.length * (1 << this.pageShift);
    int k;
    int m; try { j = k = addStringArray(paramArrayOfString1, j);
      j = m = addStringArray(createEnv(paramArrayOfString2), j);
    } catch (FaultException localFaultException) {
      throw new IllegalArgumentException("args/environ too big");
    }
    j &= 0xFFFFFFF0;
    if (i - j > 65536) { throw new IllegalArgumentException("args/environ too big");
    }
    

    if (this.heapEnd == 0) {
      this.heapEnd = heapStart();
      if (this.heapEnd == 0) throw new Error("heapEnd == 0");
      int n = this.writePages.length == 1 ? 4096 : 1 << this.pageShift;
      this.heapEnd = (this.heapEnd + n - 1 & (n - 1 ^ 0xFFFFFFFF));
    }
    
    CPUState localCPUState = new CPUState();
    localCPUState.r[4] = k;
    localCPUState.r[5] = m;
    localCPUState.r[29] = j;
    localCPUState.r[31] = -559038737;
    localCPUState.r[28] = gp();
    localCPUState.pc = entryPoint();
    setCPUState(localCPUState);
    
    this.state = 2;
    
    _started();
  }
  
  public final void stop() {
    if ((this.state != 0) && (this.state != 2)) throw new IllegalStateException("stop() called in inappropriate state");
    exit(0, false);
  }
  

  public final int call(String paramString, Object[] paramArrayOfObject)
    throws Runtime.CallException, Runtime.FaultException
  {
    if ((this.state != 2) && (this.state != 3)) throw new IllegalStateException("call() called in inappropriate state");
    if (paramArrayOfObject.length > 7) throw new IllegalArgumentException("args.length > 7");
    CPUState localCPUState = new CPUState();
    getCPUState(localCPUState);
    
    int i = localCPUState.r[29];
    int[] arrayOfInt = new int[paramArrayOfObject.length];
    for (int j = 0; j < paramArrayOfObject.length; j++) {
      Object localObject = paramArrayOfObject[j];
      byte[] arrayOfByte = null;
      if ((localObject instanceof String)) {
        arrayOfByte = getBytes((String)localObject);
      } else if ((localObject instanceof byte[])) {
        arrayOfByte = (byte[])localObject;
      } else if ((localObject instanceof Number)) {
        arrayOfInt[j] = ((Number)localObject).intValue();
      }
      if (arrayOfByte != null) {
        i -= arrayOfByte.length;
        copyout(arrayOfByte, i, arrayOfByte.length);
        arrayOfInt[j] = i;
      }
    }
    j = localCPUState.r[29];
    if (j == i) { return call(paramString, arrayOfInt);
    }
    localCPUState.r[29] = i;
    setCPUState(localCPUState);
    int k = call(paramString, arrayOfInt);
    localCPUState.r[29] = j;
    setCPUState(localCPUState);
    return k;
  }
  
  public final int call(String paramString) throws Runtime.CallException { return call(paramString, new int[0]); }
  public final int call(String paramString, int paramInt) throws Runtime.CallException { return call(paramString, new int[] { paramInt }); }
  public final int call(String paramString, int paramInt1, int paramInt2) throws Runtime.CallException { return call(paramString, new int[] { paramInt1, paramInt2 }); }
  
  public final int call(String paramString, int[] paramArrayOfInt) throws Runtime.CallException
  {
    int i = lookupSymbol(paramString);
    if (i == -1) throw new CallException(paramString + " not found");
    int j = lookupSymbol("_call_helper");
    if (j == -1) throw new CallException("_call_helper not found");
    return call(j, i, paramArrayOfInt);
  }
  

  public final int call(int paramInt1, int paramInt2, int[] paramArrayOfInt)
    throws Runtime.CallException
  {
    if (paramArrayOfInt.length > 7) throw new IllegalArgumentException("rest.length > 7");
    if ((this.state != 2) && (this.state != 3)) throw new IllegalStateException("call() called in inappropriate state");
    int i = this.state;
    CPUState localCPUState1 = new CPUState();
    getCPUState(localCPUState1);
    CPUState localCPUState2 = localCPUState1.dup();
    
    localCPUState2.r[29] &= 0xFFFFFFF0;
    localCPUState2.r[31] = -559038737;
    localCPUState2.r[4] = paramInt2;
    switch (paramArrayOfInt.length) {
    case 7:  localCPUState2.r[19] = paramArrayOfInt[6];
    case 6:  localCPUState2.r[18] = paramArrayOfInt[5];
    case 5:  localCPUState2.r[17] = paramArrayOfInt[4];
    case 4:  localCPUState2.r[16] = paramArrayOfInt[3];
    case 3:  localCPUState2.r[7] = paramArrayOfInt[2];
    case 2:  localCPUState2.r[6] = paramArrayOfInt[1];
    case 1:  localCPUState2.r[5] = paramArrayOfInt[0];
    }
    localCPUState2.pc = paramInt1;
    
    this.state = 0;
    
    setCPUState(localCPUState2);
    __execute();
    getCPUState(localCPUState2);
    setCPUState(localCPUState1);
    
    if (this.state != 2) throw new CallException("Process exit()ed while servicing a call() request");
    this.state = i;
    
    return localCPUState2.r[3];
  }
  


  public final int addFD(FD paramFD)
  {
    if ((this.state == 4) || (this.state == 5)) { throw new IllegalStateException("addFD called in inappropriate state");
    }
    for (int i = 0; (i < 64) && (this.fds[i] != null); i++) {}
    if (i == 64) return -1;
    this.fds[i] = paramFD;
    this.closeOnExec[i] = false;
    return i;
  }
  




  public final boolean closeFD(int paramInt)
  {
    if ((this.state == 4) || (this.state == 5)) throw new IllegalStateException("closeFD called in inappropriate state");
    if ((paramInt < 0) || (paramInt >= 64)) return false;
    if (this.fds[paramInt] == null) return false;
    _preCloseFD(this.fds[paramInt]);
    this.fds[paramInt].close();
    _postCloseFD(this.fds[paramInt]);
    this.fds[paramInt] = null;
    return true;
  }
  

  public final int dupFD(int paramInt)
  {
    if ((paramInt < 0) || (paramInt >= 64)) return -1;
    if (this.fds[paramInt] == null) return -1;
    for (int i = 0; (i < 64) && (this.fds[i] != null); i++) {}
    if (i == 64) return -1;
    this.fds[i] = this.fds[paramInt].dup();
    return i;
  }
  










  FD hostFSOpen(File paramFile, int paramInt1, int paramInt2, Object paramObject)
    throws Runtime.ErrnoException
  {
    if ((paramInt1 & 0xF1F4) != 0)
    {
      System.err.println("WARNING: Unsupported flags passed to open(\"" + paramFile + "\"): " + toHex(paramInt1 & 0xF1F4));
      throw new ErrnoException(134);
    }
    boolean bool = (paramInt1 & 0x3) != 0;
    
    if ((this.sm != null) && (bool ? !this.sm.allowWrite(paramFile) : !this.sm.allowRead(paramFile))) { throw new ErrnoException(13);
    }
    if ((paramInt1 & 0xA00) == 2560) {
      try {
        if (!Platform.atomicCreateFile(paramFile)) throw new ErrnoException(17);
      } catch (IOException localIOException1) {
        throw new ErrnoException(5);
      }
    } else if (!paramFile.exists()) {
      if ((paramInt1 & 0x200) == 0) return null;
    } else if (paramFile.isDirectory()) {
      return hostFSDirFD(paramFile, paramObject);
    }
    Seekable.File localFile;
    try
    {
      localFile = new Seekable.File(paramFile, bool, (paramInt1 & 0x400) != 0);
    } catch (FileNotFoundException localFileNotFoundException) {
      if ((localFileNotFoundException.getMessage() != null) && (localFileNotFoundException.getMessage().indexOf("Permission denied") >= 0)) throw new ErrnoException(13);
      return null;
    } catch (IOException localIOException2) { throw new ErrnoException(5);
    }
    new SeekableFD(localFile, paramInt1) { protected Runtime.FStat _fstat() { return Runtime.this.hostFStat(this.val$f, this.val$sf, this.val$data); }
    }; }
  
  FStat hostFStat(File paramFile, Seekable.File paramFile1, Object paramObject) { return new HostFStat(paramFile, paramFile1); }
  
  FD hostFSDirFD(File paramFile, Object paramObject) { return null; }
  
  FD _open(String paramString, int paramInt1, int paramInt2) throws Runtime.ErrnoException {
    return hostFSOpen(new File(paramString), paramInt1, paramInt2, null);
  }
  
  private int sys_open(int paramInt1, int paramInt2, int paramInt3) throws Runtime.ErrnoException, Runtime.FaultException
  {
    String str = cstring(paramInt1);
    

    if ((str.length() == 1024) && (getClass().getName().equals("tests.TeX"))) { str = str.trim();
    }
    paramInt2 &= 0xFFFF7FFF;
    FD localFD = _open(str, paramInt2, paramInt3);
    if (localFD == null) return -2;
    int i = addFD(localFD);
    if (i == -1) { localFD.close();return -23; }
    return i;
  }
  
  private int sys_write(int paramInt1, int paramInt2, int paramInt3)
    throws Runtime.FaultException, Runtime.ErrnoException
  {
    paramInt3 = Math.min(paramInt3, 16776192);
    if ((paramInt1 < 0) || (paramInt1 >= 64)) return -81;
    if (this.fds[paramInt1] == null) return -81;
    byte[] arrayOfByte = byteBuf(paramInt3);
    copyin(paramInt2, arrayOfByte, paramInt3);
    try {
      return this.fds[paramInt1].write(arrayOfByte, 0, paramInt3);
    } catch (ErrnoException localErrnoException) {
      if (localErrnoException.errno == 32) sys_exit(141);
      throw localErrnoException;
    }
  }
  
  private int sys_read(int paramInt1, int paramInt2, int paramInt3) throws Runtime.FaultException, Runtime.ErrnoException
  {
    paramInt3 = Math.min(paramInt3, 16776192);
    if ((paramInt1 < 0) || (paramInt1 >= 64)) return -81;
    if (this.fds[paramInt1] == null) return -81;
    byte[] arrayOfByte = byteBuf(paramInt3);
    int i = this.fds[paramInt1].read(arrayOfByte, 0, paramInt3);
    copyout(arrayOfByte, paramInt2, i);
    return i;
  }
  
  private int sys_ftruncate(int paramInt, long paramLong)
  {
    if ((paramInt < 0) || (paramInt >= 64)) return -81;
    if (this.fds[paramInt] == null) { return -81;
    }
    Seekable localSeekable = this.fds[paramInt].seekable();
    if ((paramLong < 0L) || (localSeekable == null)) return -22;
    try { localSeekable.resize(paramLong); } catch (IOException localIOException) { return -5; }
    return 0;
  }
  
  private int sys_close(int paramInt)
  {
    return closeFD(paramInt) ? 0 : -81;
  }
  
  private int sys_lseek(int paramInt1, int paramInt2, int paramInt3)
    throws Runtime.ErrnoException
  {
    if ((paramInt1 < 0) || (paramInt1 >= 64)) return -81;
    if (this.fds[paramInt1] == null) return -81;
    if ((paramInt3 != 0) && (paramInt3 != 1) && (paramInt3 != 2)) return -22;
    int i = this.fds[paramInt1].seek(paramInt2, paramInt3);
    return i < 0 ? -29 : i;
  }
  
  int stat(FStat paramFStat, int paramInt) throws Runtime.FaultException
  {
    memWrite(paramInt + 0, paramFStat.dev() << 16 | paramFStat.inode() & 0xFFFF);
    memWrite(paramInt + 4, paramFStat.type() & 0xF000 | paramFStat.mode() & 0xFFF);
    memWrite(paramInt + 8, paramFStat.nlink() << 16 | paramFStat.uid() & 0xFFFF);
    memWrite(paramInt + 12, paramFStat.gid() << 16 | 0x0);
    memWrite(paramInt + 16, paramFStat.size());
    memWrite(paramInt + 20, paramFStat.atime());
    
    memWrite(paramInt + 28, paramFStat.mtime());
    
    memWrite(paramInt + 36, paramFStat.ctime());
    
    memWrite(paramInt + 44, paramFStat.blksize());
    memWrite(paramInt + 48, paramFStat.blocks());
    

    return 0;
  }
  
  private int sys_fstat(int paramInt1, int paramInt2) throws Runtime.FaultException
  {
    if ((paramInt1 < 0) || (paramInt1 >= 64)) return -81;
    if (this.fds[paramInt1] == null) return -81;
    return stat(this.fds[paramInt1].fstat(), paramInt2);
  }
  




  private int sys_gettimeofday(int paramInt1, int paramInt2)
    throws Runtime.FaultException
  {
    long l = System.currentTimeMillis();
    int i = (int)(l / 1000L);
    int j = (int)(l % 1000L * 1000L);
    memWrite(paramInt1 + 0, i);
    memWrite(paramInt1 + 4, j);
    return 0;
  }
  
  private int sys_sleep(int paramInt) {
    if (paramInt < 0) paramInt = Integer.MAX_VALUE;
    try {
      Thread.sleep(paramInt * 1000L);
      return 0;
    } catch (InterruptedException localInterruptedException) {}
    return -1;
  }
  


  private final File val$f;
  

  private final Seekable.File val$sf;
  

  private final Object val$data;
  
  private int sys_times(int paramInt)
  {
    long l = System.currentTimeMillis();
    int i = (int)((l - this.startTime) / 16L);
    int j = (int)((l - this.startTime) / 16L);
    try
    {
      if (paramInt != 0) {
        memWrite(paramInt + 0, i);
        memWrite(paramInt + 4, j);
        memWrite(paramInt + 8, i);
        memWrite(paramInt + 12, j);
      }
    } catch (FaultException localFaultException) {
      return -14;
    }
    return (int)l;
  }
  
  private int sys_sysconf(int paramInt) {
    switch (paramInt) {
    case 2:  return 1000;
    case 8:  return this.writePages.length == 1 ? 4096 : 1 << this.pageShift;
    case 11:  return this.writePages.length == 1 ? (1 << this.pageShift) / 4096 : this.writePages.length;
    }
    System.err.println("WARNING: Attempted to use unknown sysconf key: " + paramInt);
    return -22;
  }
  


  public final int sbrk(int paramInt)
  {
    if (paramInt < 0) return -12;
    if (paramInt == 0) return this.heapEnd;
    paramInt = paramInt + 3 & 0xFFFFFFFC;
    int i = this.heapEnd;
    int j = i + paramInt;
    if (j >= this.stackBottom) { return -12;
    }
    if (this.writePages.length > 1) {
      int k = (1 << this.pageShift) - 1;
      int m = 1 << this.pageShift >>> 2;
      int n = i + k >>> this.pageShift;
      int i1 = j + k >>> this.pageShift;
      try {
        for (int i2 = n; i2 < i1; i2++) this.readPages[i2] = (this.writePages[i2] = new int[m]);
      } catch (OutOfMemoryError localOutOfMemoryError) {
        System.err.println("WARNING: Caught OOM Exception in sbrk: " + localOutOfMemoryError);
        return -12;
      }
    }
    this.heapEnd = j;
    return i;
  }
  

  private int sys_getpid() { return getPid(); }
  int getPid() { return 1; }
  

  private int sys_calljava(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if (this.state != 0) throw new IllegalStateException("wound up calling sys_calljava while not in RUNNING");
    if (this.callJavaCB != null) {
      this.state = 3;
      int i;
      try {
        i = this.callJavaCB.call(paramInt1, paramInt2, paramInt3, paramInt4);
      } catch (RuntimeException localRuntimeException) {
        System.err.println("Error while executing callJavaCB");
        localRuntimeException.printStackTrace();
        i = 0;
      }
      this.state = 0;
      return i;
    }
    System.err.println("WARNING: calljava syscall invoked without a calljava callback set");
    return 0;
  }
  
  private int sys_pause()
  {
    this.state = 2;
    return 0;
  }
  
  private int sys_getpagesize() { return this.writePages.length == 1 ? 4096 : 1 << this.pageShift; }
  


  void exit(int paramInt, boolean paramBoolean)
  {
    if ((paramBoolean) && (this.fds[2] != null)) {
      try {
        byte[] arrayOfByte = getBytes("Process exited on signal " + (paramInt - 128) + "\n");
        this.fds[2].write(arrayOfByte, 0, arrayOfByte.length);
      } catch (ErrnoException localErrnoException) {}
    }
    this.exitStatus = paramInt;
    for (int i = 0; i < this.fds.length; i++) if (this.fds[i] != null) closeFD(i);
    this.state = 4;
    _exited();
  }
  
  private int sys_exit(int paramInt) {
    exit(paramInt, false);
    return 0;
  }
  
  final int sys_fcntl(int paramInt1, int paramInt2, int paramInt3)
    throws Runtime.FaultException
  {
    if ((paramInt1 < 0) || (paramInt1 >= 64)) return -81;
    if (this.fds[paramInt1] == null) return -81;
    FD localFD = this.fds[paramInt1];
    
    switch (paramInt2) {
    case 0: 
      if ((paramInt3 < 0) || (paramInt3 >= 64)) return -22;
      for (int i = paramInt3; (i < 64) && (this.fds[i] != null); i++) {}
      if (i == 64) return -24;
      this.fds[i] = localFD.dup();
      return i;
    case 3: 
      return localFD.flags();
    case 2: 
      this.closeOnExec[paramInt1] = (paramInt3 != 0 ? 1 : false);
      return 0;
    case 1: 
      return this.closeOnExec[paramInt1] != 0 ? 1 : 0;
    case 7: 
    case 8: 
      System.err.println("WARNING: file locking requires UnixRuntime");
      return -88;
    }
    System.err.println("WARNING: Unknown fcntl command: " + paramInt2);
    return -88;
  }
  
  final int fsync(int paramInt)
  {
    if ((paramInt < 0) || (paramInt >= 64)) return -81;
    if (this.fds[paramInt] == null) return -81;
    FD localFD = this.fds[paramInt];
    
    Seekable localSeekable = localFD.seekable();
    if (localSeekable == null) return -22;
    try
    {
      localSeekable.sync();
      return 0;
    } catch (IOException localIOException) {}
    return -5;
  }
  




  protected final int syscall(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7)
  {
    try
    {
      return _syscall(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7);


    }
    catch (ErrnoException localErrnoException)
    {

      return -localErrnoException.errno;
    } catch (FaultException localFaultException) {
      return -14;
    } catch (RuntimeException localRuntimeException) {
      localRuntimeException.printStackTrace();
      throw new Error("Internal Error in _syscall()");
    }
  }
  
  int _syscall(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7) throws Runtime.ErrnoException, Runtime.FaultException {
    switch (paramInt1) {
    case 0:  return 0;
    case 1:  return sys_exit(paramInt2);
    case 2:  return sys_pause();
    case 6:  return sys_write(paramInt2, paramInt3, paramInt4);
    case 8:  return sys_fstat(paramInt2, paramInt3);
    case 7:  return sbrk(paramInt2);
    case 3:  return sys_open(paramInt2, paramInt3, paramInt4);
    case 4:  return sys_close(paramInt2);
    case 5:  return sys_read(paramInt2, paramInt3, paramInt4);
    case 10:  return sys_lseek(paramInt2, paramInt3, paramInt4);
    case 44:  return sys_ftruncate(paramInt2, paramInt3);
    case 12:  return sys_getpid();
    case 13:  return sys_calljava(paramInt2, paramInt3, paramInt4, paramInt5);
    case 15:  return sys_gettimeofday(paramInt2, paramInt3);
    case 16:  return sys_sleep(paramInt2);
    case 17:  return sys_times(paramInt2);
    case 19:  return sys_getpagesize();
    case 29:  return sys_fcntl(paramInt2, paramInt3, paramInt4);
    case 31:  return sys_sysconf(paramInt2);
    case 68:  return sys_getuid();
    case 70:  return sys_geteuid();
    case 69:  return sys_getgid();
    case 71:  return sys_getegid();
    case 91: 
      return fsync(paramInt2);
    case 37:  memcpy(paramInt2, paramInt3, paramInt4);return paramInt2;
    case 38:  memset(paramInt2, paramInt3, paramInt4);return paramInt2;
    
    case 11: 
    case 14: 
    case 18: 
    case 22: 
    case 23: 
    case 24: 
    case 25: 
    case 26: 
    case 27: 
      System.err.println("Attempted to use a UnixRuntime syscall in Runtime (" + paramInt1 + ")");
      return -88;
    }
    System.err.println("Attempted to use unknown syscall: " + paramInt1);
    return -88;
  }
  

  private int sys_getuid() { return 0; }
  private int sys_geteuid() { return 0; }
  private int sys_getgid() { return 0; }
  private int sys_getegid() { return 0; }
  
  public int xmalloc(int paramInt) { int i = malloc(paramInt); if (i == 0) throw new RuntimeException("malloc() failed"); return i; }
  public int xrealloc(int paramInt1, int paramInt2) { int i = realloc(paramInt1, paramInt2); if (i == 0) throw new RuntimeException("realloc() failed"); return i; }
  public int realloc(int paramInt1, int paramInt2) { try { return call("realloc", paramInt1, paramInt2); } catch (CallException localCallException) {} return 0; }
  public int malloc(int paramInt) { try { return call("malloc", paramInt); } catch (CallException localCallException) {} return 0; }
  public void free(int paramInt) { try { if (paramInt != 0) call("free", paramInt);
    } catch (CallException localCallException) {}
  }
  
  public int strdup(String paramString) {
    if (paramString == null) paramString = "(null)";
    byte[] arrayOfByte2 = getBytes(paramString);
    byte[] arrayOfByte1 = new byte[arrayOfByte2.length + 1];
    System.arraycopy(arrayOfByte2, 0, arrayOfByte1, 0, arrayOfByte2.length);
    int i = malloc(arrayOfByte1.length);
    if (i == 0) return 0;
    try {
      copyout(arrayOfByte1, i, arrayOfByte1.length);
    } catch (FaultException localFaultException) {
      free(i);
      return 0;
    }
    return i;
  }
  
  public final String utfstring(int paramInt)
    throws Runtime.ReadFaultException
  {
    if (paramInt == 0) { return null;
    }
    
    int i = paramInt;
    for (int j = 1; j != 0; i++) {
      j = memRead(i & 0xFFFFFFFC);
      switch (i & 0x3) {
      case 0:  j = j >>> 24 & 0xFF; break;
      case 1:  j = j >>> 16 & 0xFF; break;
      case 2:  j = j >>> 8 & 0xFF; break;
      case 3:  j = j >>> 0 & 0xFF;
      }
    }
    if (i > paramInt) { i--;
    }
    byte[] arrayOfByte = new byte[i - paramInt];
    copyin(paramInt, arrayOfByte, arrayOfByte.length);
    try
    {
      return new String(arrayOfByte, "UTF-8");
    } catch (UnsupportedEncodingException localUnsupportedEncodingException) {
      throw new RuntimeException(localUnsupportedEncodingException);
    }
  }
  
  public final String cstring(int paramInt) throws Runtime.ReadFaultException
  {
    if (paramInt == 0) return null;
    StringBuffer localStringBuffer = new StringBuffer();
    for (;;) {
      int i = memRead(paramInt & 0xFFFFFFFC);
      switch (paramInt & 0x3) {
      case 0:  if ((i >>> 24 & 0xFF) == 0) return localStringBuffer.toString(); localStringBuffer.append((char)(i >>> 24 & 0xFF));paramInt++;
      case 1:  if ((i >>> 16 & 0xFF) == 0) return localStringBuffer.toString(); localStringBuffer.append((char)(i >>> 16 & 0xFF));paramInt++;
      case 2:  if ((i >>> 8 & 0xFF) == 0) return localStringBuffer.toString(); localStringBuffer.append((char)(i >>> 8 & 0xFF));paramInt++;
      case 3:  if ((i >>> 0 & 0xFF) == 0) return localStringBuffer.toString(); localStringBuffer.append((char)(i >>> 0 & 0xFF));paramInt++; }
    }
  }
  
  public static abstract interface CallJavaCB { public abstract int call(int paramInt1, int paramInt2, int paramInt3, int paramInt4);
  }
  
  public static abstract class FD { private int refCount = 1;
    private String normalizedPath = null;
    private boolean deleteOnClose = false;
    
    public void setNormalizedPath(String paramString) { this.normalizedPath = paramString; }
    public String getNormalizedPath() { return this.normalizedPath; }
    
    public void markDeleteOnClose() { this.deleteOnClose = true; }
    public boolean isMarkedForDeleteOnClose() { return this.deleteOnClose; }
    

    public int read(byte[] paramArrayOfByte, int paramInt1, int paramInt2) throws Runtime.ErrnoException { throw new Runtime.ErrnoException(81); }
    
    public int write(byte[] paramArrayOfByte, int paramInt1, int paramInt2) throws Runtime.ErrnoException { throw new Runtime.ErrnoException(81); }
    

    public int seek(int paramInt1, int paramInt2) throws Runtime.ErrnoException { return -1; }
    
    public int getdents(byte[] paramArrayOfByte, int paramInt1, int paramInt2) throws Runtime.ErrnoException { throw new Runtime.ErrnoException(81); }
    


    Seekable seekable() { return null; }
    
    private Runtime.FStat cachedFStat = null;
    
    public final Runtime.FStat fstat() { if (this.cachedFStat == null) this.cachedFStat = _fstat();
      return this.cachedFStat;
    }
    
    protected abstract Runtime.FStat _fstat();
    
    public abstract int flags();
    
    public final void close() { if (--this.refCount == 0) _close(); }
    
    protected void _close() {}
    FD dup() { this.refCount += 1;return this;
    }
  }
  
  public static abstract class SeekableFD extends Runtime.FD {
    private final int flags;
    private final Seekable data;
    
    SeekableFD(Seekable paramSeekable, int paramInt) { this.data = paramSeekable;this.flags = paramInt; }
    
    protected abstract Runtime.FStat _fstat();
    public int flags() { return this.flags; }
    
    Seekable seekable() { return this.data; }
    
    public int seek(int paramInt1, int paramInt2) throws Runtime.ErrnoException {
      try {
        switch (paramInt2) {
        case 0: 
          break; case 1:  paramInt1 += this.data.pos(); break;
        case 2:  paramInt1 += this.data.length(); break;
        default:  return -1;
        }
        this.data.seek(paramInt1);
        return paramInt1;
      } catch (IOException localIOException) {
        throw new Runtime.ErrnoException(29);
      }
    }
    
    public int write(byte[] paramArrayOfByte, int paramInt1, int paramInt2) throws Runtime.ErrnoException {
      if ((this.flags & 0x3) == 0) { throw new Runtime.ErrnoException(81);
      }
      if ((this.flags & 0x8) != 0) seek(0, 2);
      try {
        return this.data.write(paramArrayOfByte, paramInt1, paramInt2);
      } catch (IOException localIOException) {
        throw new Runtime.ErrnoException(5);
      }
    }
    
    public int read(byte[] paramArrayOfByte, int paramInt1, int paramInt2) throws Runtime.ErrnoException {
      if ((this.flags & 0x3) == 1) throw new Runtime.ErrnoException(81);
      try {
        int i = this.data.read(paramArrayOfByte, paramInt1, paramInt2);
        return i < 0 ? 0 : i;
      } catch (IOException localIOException) {
        throw new Runtime.ErrnoException(5);
      }
    }
    
    protected void _close() { try { this.data.close();
      } catch (IOException localIOException) {}
    } }
  
  public static class InputOutputStreamFD extends Runtime.FD { private final InputStream is;
    private final OutputStream os;
    
    public InputOutputStreamFD(InputStream paramInputStream) { this(paramInputStream, null); }
    public InputOutputStreamFD(OutputStream paramOutputStream) { this(null, paramOutputStream); }
    
    public InputOutputStreamFD(InputStream paramInputStream, OutputStream paramOutputStream) { this.is = paramInputStream;
      this.os = paramOutputStream;
      if ((paramInputStream == null) && (paramOutputStream == null)) throw new IllegalArgumentException("at least one stream must be supplied");
    }
    
    public int flags() {
      if ((this.is != null) && (this.os != null)) return 2;
      if (this.is != null) return 0;
      if (this.os != null) return 1;
      throw new Error("should never happen");
    }
    
    public void _close() {
      if (this.is != null) try { this.is.close(); } catch (IOException localIOException1) {}
      if (this.os != null) try { this.os.close();
        } catch (IOException localIOException2) {}
    }
    
    public int read(byte[] paramArrayOfByte, int paramInt1, int paramInt2) throws Runtime.ErrnoException { if (this.is == null) return super.read(paramArrayOfByte, paramInt1, paramInt2);
      try {
        int i = this.is.read(paramArrayOfByte, paramInt1, paramInt2);
        return i < 0 ? 0 : i;
      } catch (IOException localIOException) {
        throw new Runtime.ErrnoException(5);
      }
    }
    
    public int write(byte[] paramArrayOfByte, int paramInt1, int paramInt2) throws Runtime.ErrnoException {
      if (this.os == null) return super.write(paramArrayOfByte, paramInt1, paramInt2);
      try {
        this.os.write(paramArrayOfByte, paramInt1, paramInt2);
        return paramInt2;
      } catch (IOException localIOException) {
        throw new Runtime.ErrnoException(5);
      }
    }
    
    public Runtime.FStat _fstat() { return new Runtime.SocketFStat(); }
  }
  
  static class TerminalFD extends Runtime.InputOutputStreamFD {
    public TerminalFD(InputStream paramInputStream) { this(paramInputStream, null); }
    public TerminalFD(OutputStream paramOutputStream) { this(null, paramOutputStream); }
    public TerminalFD(InputStream paramInputStream, OutputStream paramOutputStream) { super(paramOutputStream); }
    public void _close() {}
    public Runtime.FStat _fstat() { new Runtime.SocketFStat() { public int mode() { return 384; } public int type() { return 8192; }
      }; }
  }
  
  static class Win32ConsoleIS extends InputStream {
    private int pushedBack = -1;
    
    public Win32ConsoleIS(InputStream paramInputStream) { this.parent = paramInputStream; }
    
    public int read() throws IOException { if (this.pushedBack != -1) { i = this.pushedBack;this.pushedBack = -1;return i; }
      int i = this.parent.read();
      if ((i == 13) && ((i = this.parent.read()) != 10)) { this.pushedBack = i;return 13; }
      return i; }
    
    private final InputStream parent;
    public int read(byte[] paramArrayOfByte, int paramInt1, int paramInt2) throws IOException { int i = 0;
      if ((this.pushedBack != -1) && (paramInt2 > 0)) {
        paramArrayOfByte[0] = ((byte)this.pushedBack);
        this.pushedBack = -1;
        paramInt1++;paramInt2--;i = 1;
      }
      int j = this.parent.read(paramArrayOfByte, paramInt1, paramInt2);
      if (j == -1) return i != 0 ? 1 : -1;
      for (int k = 0; k < j; k++) {
        if (paramArrayOfByte[(paramInt1 + k)] == 13) {
          if (k == j - 1) {
            int m = this.parent.read();
            if (m == 10) paramArrayOfByte[(paramInt1 + k)] = 10; else
              this.pushedBack = m;
          } else if (paramArrayOfByte[(paramInt1 + k + 1)] == 10) {
            System.arraycopy(paramArrayOfByte, paramInt1 + k + 1, paramArrayOfByte, paramInt1 + k, paramInt2 - k - 1);
            j--;
          }
        }
      }
      return j + (i != 0 ? 1 : 0);
    }
  }
  
  public static abstract class FStat {
    public static final int S_IFIFO = 4096;
    public static final int S_IFCHR = 8192;
    public static final int S_IFDIR = 16384;
    public static final int S_IFREG = 32768;
    public static final int S_IFSOCK = 49152;
    
    public int mode() { return 0; }
    public int nlink() { return 0; }
    public int uid() { return 0; }
    public int gid() { return 0; }
    public int size() { return 0; }
    public int atime() { return 0; }
    public int mtime() { return 0; }
    public int ctime() { return 0; }
    public int blksize() { return 512; }
    public int blocks() { return (size() + blksize() - 1) / blksize(); }
    
    public abstract int dev();
    
    public abstract int type();
    
    public abstract int inode(); }
  
  public static class SocketFStat extends Runtime.FStat { public int dev() { return -1; }
    public int type() { return 49152; }
    public int inode() { return hashCode() & 0x7FFF; }
  }
  
  static class HostFStat extends Runtime.FStat { private final File f;
    private final Seekable.File sf;
    private final boolean executable;
    
    public HostFStat(File paramFile, Seekable.File paramFile1) { this(paramFile, paramFile1, false); }
    public HostFStat(File paramFile, boolean paramBoolean) { this(paramFile, null, paramBoolean); }
    
    public HostFStat(File paramFile, Seekable.File paramFile1, boolean paramBoolean) { this.f = paramFile;
      this.sf = paramFile1;
      this.executable = paramBoolean; }
    
    public int dev() { return 1; }
    public int inode() { return this.f.getAbsolutePath().hashCode() & 0x7FFF; }
    public int type() { return this.f.isDirectory() ? 16384 : 32768; }
    public int nlink() { return 1; }
    
    public int mode() { int i = 0;
      boolean bool = this.f.canRead();
      if ((bool) && ((this.executable) || (this.f.isDirectory()))) i |= 0x49;
      if (bool) i |= 0x124;
      if (this.f.canWrite()) i |= 0x92;
      return i;
    }
    
    public int size() {
      try { return this.sf != null ? this.sf.length() : (int)this.f.length();
      } catch (Exception localException) {}
      return (int)this.f.length();
    }
    
    public int mtime() { return (int)(this.f.lastModified() / 1000L); }
  }
  
  public static class ReadFaultException
    extends Runtime.FaultException {
    public ReadFaultException(int paramInt) { super(); }
  }
  
  public static class WriteFaultException extends Runtime.FaultException { public WriteFaultException(int paramInt) { super(); } }
  
  public static class FaultException extends Runtime.ExecutionException { public final int addr;
    public final RuntimeException cause;
    
    public FaultException(int paramInt) { super();this.addr = paramInt;this.cause = null; }
    public FaultException(RuntimeException paramRuntimeException) { super();this.addr = -1;this.cause = paramRuntimeException; } }
  
  public static class ExecutionException extends Exception { public ExecutionException() {}
    private String message = "(null)";
    private String location = "(unknown)";
    
    public ExecutionException(String paramString) { if (paramString != null) this.message = paramString; }
    void setLocation(String paramString) { this.location = (paramString == null ? "(unknown)" : paramString); }
    public final String getMessage() { return this.message + " at " + this.location; }
  }
  
  public static class CallException extends Exception { public CallException(String paramString) { super(); }
  }
  
  protected static class ErrnoException extends Exception { public int errno;
    
    public ErrnoException(int paramInt) { super();this.errno = paramInt;
    }
  }
  

  protected static class CPUState
  {
    public int[] r = new int[32];
    
    public int[] f = new int[32];
    public int hi;
    public int lo;
    public int fcsr;
    public int pc;
    
    public CPUState dup() { CPUState localCPUState = new CPUState();
      localCPUState.hi = this.hi;
      localCPUState.lo = this.lo;
      localCPUState.fcsr = this.fcsr;
      localCPUState.pc = this.pc;
      for (int i = 0; i < 32; i++) {
        localCPUState.r[i] = this.r[i];
        localCPUState.f[i] = this.f[i];
      }
      return localCPUState;
    }
  }
  
  public static class SecurityManager {
    public boolean allowRead(File paramFile) { return true; }
    public boolean allowWrite(File paramFile) { return true; }
    public boolean allowStat(File paramFile) { return true; }
    public boolean allowUnlink(File paramFile) { return true; }
  }
  
  protected final void nullPointerCheck(int paramInt) throws Runtime.ExecutionException
  {
    if (paramInt < 65536) {
      throw new ExecutionException("Attempted to dereference a null pointer " + toHex(paramInt));
    }
  }
  
  byte[] byteBuf(int paramInt) {
    if (this._byteBuf == null) { this._byteBuf = new byte[paramInt];
    } else if (this._byteBuf.length < paramInt)
      this._byteBuf = new byte[min(max(this._byteBuf.length * 2, paramInt), 16776192)];
    return this._byteBuf;
  }
  
  protected static final int[] decodeData(String paramString, int paramInt)
  {
    if (paramString.length() % 8 != 0) throw new IllegalArgumentException("string length must be a multiple of 8");
    if (paramString.length() / 8 * 7 < paramInt * 4) throw new IllegalArgumentException("string isn't big enough");
    int[] arrayOfInt = new int[paramInt];
    int i = 0;int j = 0;
    int k = 0; for (int m = 0; m < paramInt; k += 8) {
      long l = 0L;
      for (int n = 0; n < 8; n++) { l <<= 7;l |= paramString.charAt(k + n) & 0x7F; }
      if (j > 0) arrayOfInt[(m++)] = (i | (int)(l >>> 56 - j));
      if (m < paramInt) arrayOfInt[(m++)] = ((int)(l >>> 24 - j));
      j = j + 8 & 0x1F;
      i = (int)(l << j);
    }
    return arrayOfInt;
  }
  
  static byte[] getBytes(String paramString) {
    try {
      return paramString.getBytes("UTF-8");
    } catch (UnsupportedEncodingException localUnsupportedEncodingException) {}
    return null;
  }
  
  static byte[] getNullTerminatedBytes(String paramString)
  {
    byte[] arrayOfByte1 = getBytes(paramString);
    byte[] arrayOfByte2 = new byte[arrayOfByte1.length + 1];
    System.arraycopy(arrayOfByte1, 0, arrayOfByte2, 0, arrayOfByte1.length);
    return arrayOfByte2;
  }
  
  static final String toHex(int paramInt) { return "0x" + Long.toString(paramInt & 0xFFFFFFFF, 16); }
  static final int min(int paramInt1, int paramInt2) { return paramInt1 < paramInt2 ? paramInt1 : paramInt2; }
  static final int max(int paramInt1, int paramInt2) { return paramInt1 > paramInt2 ? paramInt1 : paramInt2; }
  
  protected abstract int heapStart();
  
  protected abstract int entryPoint();
  
  protected abstract int gp();
  
  protected abstract void _execute()
    throws Runtime.ExecutionException;
  
  protected abstract void getCPUState(CPUState paramCPUState);
  
  protected abstract void setCPUState(CPUState paramCPUState);
  
  void _started() {}
  
  void _preCloseFD(FD paramFD) {}
  
  void _postCloseFD(FD paramFD) {}
  
  void _exited() {}
}
