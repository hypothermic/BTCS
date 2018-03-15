package net.minecraft.server;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class RemoteControlSession
  extends RemoteConnectionThread
{
  private boolean g = false;
  private Socket h;
  private byte[] i = new byte['ִ'];
  private String j;
  
  RemoteControlSession(IMinecraftServer iminecraftserver, Socket socket) {
    super(iminecraftserver);
    this.h = socket;
    try
    {
      this.h.setSoTimeout(0);
    } catch (Exception ex) {
      this.running = false;
    }
    
    this.j = iminecraftserver.a("rcon.password", "");
    info("Rcon connection from: " + socket.getInetAddress());
  }
  
  /* Error */
  public void run()
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 7	net/minecraft/server/RemoteControlSession:running	Z
    //   4: ifne +8 -> 12
    //   7: aload_0
    //   8: invokespecial 20	net/minecraft/server/RemoteControlSession:f	()V
    //   11: return
    //   12: new 21	java/io/BufferedInputStream
    //   15: dup
    //   16: aload_0
    //   17: getfield 4	net/minecraft/server/RemoteControlSession:h	Ljava/net/Socket;
    //   20: invokevirtual 22	java/net/Socket:getInputStream	()Ljava/io/InputStream;
    //   23: invokespecial 23	java/io/BufferedInputStream:<init>	(Ljava/io/InputStream;)V
    //   26: astore_1
    //   27: aload_1
    //   28: aload_0
    //   29: getfield 3	net/minecraft/server/RemoteControlSession:i	[B
    //   32: iconst_0
    //   33: sipush 1460
    //   36: invokevirtual 24	java/io/BufferedInputStream:read	([BII)I
    //   39: istore_2
    //   40: bipush 10
    //   42: iload_2
    //   43: if_icmpgt +286 -> 329
    //   46: iconst_0
    //   47: istore_3
    //   48: aload_0
    //   49: getfield 3	net/minecraft/server/RemoteControlSession:i	[B
    //   52: iconst_0
    //   53: iload_2
    //   54: invokestatic 25	net/minecraft/server/StatusChallengeUtils:b	([BII)I
    //   57: istore 4
    //   59: iload 4
    //   61: iload_2
    //   62: iconst_4
    //   63: isub
    //   64: if_icmpeq +8 -> 72
    //   67: aload_0
    //   68: invokespecial 20	net/minecraft/server/RemoteControlSession:f	()V
    //   71: return
    //   72: iload_3
    //   73: iconst_4
    //   74: iadd
    //   75: istore 5
    //   77: aload_0
    //   78: getfield 3	net/minecraft/server/RemoteControlSession:i	[B
    //   81: iload 5
    //   83: iload_2
    //   84: invokestatic 25	net/minecraft/server/StatusChallengeUtils:b	([BII)I
    //   87: istore 6
    //   89: iinc 5 4
    //   92: aload_0
    //   93: getfield 3	net/minecraft/server/RemoteControlSession:i	[B
    //   96: iload 5
    //   98: invokestatic 26	net/minecraft/server/StatusChallengeUtils:a	([BI)I
    //   101: istore 7
    //   103: iinc 5 4
    //   106: iload 7
    //   108: lookupswitch	default:+195->303, 2:+28->136, 3:+123->231
    //   136: aload_0
    //   137: getfield 2	net/minecraft/server/RemoteControlSession:g	Z
    //   140: ifeq +84 -> 224
    //   143: aload_0
    //   144: getfield 3	net/minecraft/server/RemoteControlSession:i	[B
    //   147: iload 5
    //   149: iload_2
    //   150: invokestatic 27	net/minecraft/server/StatusChallengeUtils:a	([BII)Ljava/lang/String;
    //   153: astore 8
    //   155: aload_0
    //   156: iload 6
    //   158: aload_0
    //   159: getfield 28	net/minecraft/server/RemoteControlSession:server	Lnet/minecraft/server/IMinecraftServer;
    //   162: aload 8
    //   164: invokeinterface 29 2 0
    //   169: invokespecial 30	net/minecraft/server/RemoteControlSession:a	(ILjava/lang/String;)V
    //   172: goto +49 -> 221
    //   175: astore 9
    //   177: aload_0
    //   178: iload 6
    //   180: new 12	java/lang/StringBuilder
    //   183: dup
    //   184: invokespecial 13	java/lang/StringBuilder:<init>	()V
    //   187: ldc 31
    //   189: invokevirtual 15	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   192: aload 8
    //   194: invokevirtual 15	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   197: ldc 32
    //   199: invokevirtual 15	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   202: aload 9
    //   204: invokevirtual 33	java/lang/Exception:getMessage	()Ljava/lang/String;
    //   207: invokevirtual 15	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   210: ldc 34
    //   212: invokevirtual 15	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   215: invokevirtual 18	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   218: invokespecial 30	net/minecraft/server/RemoteControlSession:a	(ILjava/lang/String;)V
    //   221: goto -221 -> 0
    //   224: aload_0
    //   225: invokespecial 35	net/minecraft/server/RemoteControlSession:e	()V
    //   228: goto -228 -> 0
    //   231: aload_0
    //   232: getfield 3	net/minecraft/server/RemoteControlSession:i	[B
    //   235: iload 5
    //   237: iload_2
    //   238: invokestatic 27	net/minecraft/server/StatusChallengeUtils:a	([BII)Ljava/lang/String;
    //   241: astore 8
    //   243: iload 5
    //   245: aload 8
    //   247: invokevirtual 36	java/lang/String:length	()I
    //   250: iadd
    //   251: istore 9
    //   253: iconst_0
    //   254: aload 8
    //   256: invokevirtual 36	java/lang/String:length	()I
    //   259: if_icmpeq +32 -> 291
    //   262: aload 8
    //   264: aload_0
    //   265: getfield 11	net/minecraft/server/RemoteControlSession:j	Ljava/lang/String;
    //   268: invokevirtual 37	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   271: ifeq +20 -> 291
    //   274: aload_0
    //   275: iconst_1
    //   276: putfield 2	net/minecraft/server/RemoteControlSession:g	Z
    //   279: aload_0
    //   280: iload 6
    //   282: iconst_2
    //   283: ldc 9
    //   285: invokespecial 38	net/minecraft/server/RemoteControlSession:a	(IILjava/lang/String;)V
    //   288: goto -288 -> 0
    //   291: aload_0
    //   292: iconst_0
    //   293: putfield 2	net/minecraft/server/RemoteControlSession:g	Z
    //   296: aload_0
    //   297: invokespecial 35	net/minecraft/server/RemoteControlSession:e	()V
    //   300: goto -300 -> 0
    //   303: aload_0
    //   304: iload 6
    //   306: ldc 39
    //   308: iconst_1
    //   309: anewarray 40	java/lang/Object
    //   312: dup
    //   313: iconst_0
    //   314: iload 7
    //   316: invokestatic 41	java/lang/Integer:toHexString	(I)Ljava/lang/String;
    //   319: aastore
    //   320: invokestatic 42	java/lang/String:format	(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
    //   323: invokespecial 30	net/minecraft/server/RemoteControlSession:a	(ILjava/lang/String;)V
    //   326: goto -326 -> 0
    //   329: aload_0
    //   330: invokespecial 20	net/minecraft/server/RemoteControlSession:f	()V
    //   333: return
    //   334: astore_1
    //   335: goto -335 -> 0
    //   338: astore_1
    //   339: aload_0
    //   340: getfield 7	net/minecraft/server/RemoteControlSession:running	Z
    //   343: ifeq +29 -> 372
    //   346: aload_0
    //   347: new 12	java/lang/StringBuilder
    //   350: dup
    //   351: invokespecial 13	java/lang/StringBuilder:<init>	()V
    //   354: ldc 45
    //   356: invokevirtual 15	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   359: aload_1
    //   360: invokevirtual 46	java/io/IOException:getMessage	()Ljava/lang/String;
    //   363: invokevirtual 15	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   366: invokevirtual 18	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   369: invokevirtual 19	net/minecraft/server/RemoteControlSession:info	(Ljava/lang/String;)V
    //   372: aload_0
    //   373: invokespecial 20	net/minecraft/server/RemoteControlSession:f	()V
    //   376: return
    //   377: astore_1
    //   378: getstatic 47	java/lang/System:out	Ljava/io/PrintStream;
    //   381: aload_1
    //   382: invokevirtual 48	java/io/PrintStream:println	(Ljava/lang/Object;)V
    //   385: aload_0
    //   386: invokespecial 20	net/minecraft/server/RemoteControlSession:f	()V
    //   389: return
    //   390: astore 10
    //   392: aload_0
    //   393: invokespecial 20	net/minecraft/server/RemoteControlSession:f	()V
    //   396: aload 10
    //   398: athrow
    // Line number table:
    //   Java source line #35	-> byte code offset #0
    //   Java source line #107	-> byte code offset #7
    //   Java source line #40	-> byte code offset #12
    //   Java source line #41	-> byte code offset #27
    //   Java source line #43	-> byte code offset #40
    //   Java source line #44	-> byte code offset #46
    //   Java source line #45	-> byte code offset #48
    //   Java source line #47	-> byte code offset #59
    //   Java source line #107	-> byte code offset #67
    //   Java source line #51	-> byte code offset #72
    //   Java source line #52	-> byte code offset #77
    //   Java source line #54	-> byte code offset #89
    //   Java source line #55	-> byte code offset #92
    //   Java source line #57	-> byte code offset #103
    //   Java source line #58	-> byte code offset #106
    //   Java source line #60	-> byte code offset #136
    //   Java source line #61	-> byte code offset #143
    //   Java source line #64	-> byte code offset #155
    //   Java source line #67	-> byte code offset #172
    //   Java source line #65	-> byte code offset #175
    //   Java source line #66	-> byte code offset #177
    //   Java source line #68	-> byte code offset #221
    //   Java source line #71	-> byte code offset #224
    //   Java source line #72	-> byte code offset #228
    //   Java source line #75	-> byte code offset #231
    //   Java source line #76	-> byte code offset #243
    //   Java source line #78	-> byte code offset #253
    //   Java source line #79	-> byte code offset #274
    //   Java source line #80	-> byte code offset #279
    //   Java source line #81	-> byte code offset #288
    //   Java source line #84	-> byte code offset #291
    //   Java source line #85	-> byte code offset #296
    //   Java source line #86	-> byte code offset #300
    //   Java source line #89	-> byte code offset #303
    //   Java source line #90	-> byte code offset #326
    //   Java source line #107	-> byte code offset #329
    //   Java source line #94	-> byte code offset #334
    //   Java source line #95	-> byte code offset #335
    //   Java source line #96	-> byte code offset #338
    //   Java source line #97	-> byte code offset #339
    //   Java source line #98	-> byte code offset #346
    //   Java source line #107	-> byte code offset #372
    //   Java source line #103	-> byte code offset #377
    //   Java source line #104	-> byte code offset #378
    //   Java source line #107	-> byte code offset #385
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	399	0	this	RemoteControlSession
    //   26	2	1	bufferedinputstream	java.io.BufferedInputStream
    //   334	2	1	sockettimeoutexception	java.net.SocketTimeoutException
    //   338	22	1	ioexception	IOException
    //   377	5	1	exception1	Exception
    //   39	199	2	i	int
    //   47	26	3	b0	byte
    //   57	3	4	j	int
    //   75	169	5	k	int
    //   87	218	6	l	int
    //   101	214	7	i1	int
    //   153	40	8	s	String
    //   241	22	8	s1	String
    //   175	28	9	exception	Exception
    //   251	3	9	j1	int
    //   390	7	10	localObject	Object
    // Exception table:
    //   from	to	target	type
    //   155	172	175	java/lang/Exception
    //   12	67	334	java/net/SocketTimeoutException
    //   72	221	334	java/net/SocketTimeoutException
    //   224	228	334	java/net/SocketTimeoutException
    //   231	288	334	java/net/SocketTimeoutException
    //   291	300	334	java/net/SocketTimeoutException
    //   303	326	334	java/net/SocketTimeoutException
    //   12	67	338	java/io/IOException
    //   72	221	338	java/io/IOException
    //   224	228	338	java/io/IOException
    //   231	288	338	java/io/IOException
    //   291	300	338	java/io/IOException
    //   303	326	338	java/io/IOException
    //   0	7	377	java/lang/Exception
    //   12	67	377	java/lang/Exception
    //   72	329	377	java/lang/Exception
    //   334	372	377	java/lang/Exception
    //   0	7	390	finally
    //   12	67	390	finally
    //   72	329	390	finally
    //   334	372	390	finally
    //   377	385	390	finally
    //   390	392	390	finally
  }
  
  private void a(int i, int j, String s)
    throws IOException
  {
    ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream(1248);
    DataOutputStream dataoutputstream = new DataOutputStream(bytearrayoutputstream);
    
    dataoutputstream.writeInt(Integer.reverseBytes(s.length() + 10));
    dataoutputstream.writeInt(Integer.reverseBytes(i));
    dataoutputstream.writeInt(Integer.reverseBytes(j));
    dataoutputstream.writeBytes(s);
    dataoutputstream.write(0);
    dataoutputstream.write(0);
    this.h.getOutputStream().write(bytearrayoutputstream.toByteArray());
  }
  
  private void e() throws IOException {
    a(-1, 2, "");
  }
  
  private void a(int i, String s) throws IOException {
    int j = s.length();
    do
    {
      int k = 4096 <= j ? 4096 : j;
      
      a(i, 0, s.substring(0, k));
      s = s.substring(k);
      j = s.length();
    } while (0 != j);
  }
  
  private void f()
  {
    if (null != this.h) {
      try {
        this.h.close();
        info("Rcon connection closed.");
      } catch (IOException ioexception) {
        warning("IO: " + ioexception.getMessage());
      }
      
      this.h = null;
    }
  }
}
