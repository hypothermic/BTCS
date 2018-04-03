package cpw.mods.fml.common.modloader;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.IConsoleHandler;
import cpw.mods.fml.common.ICraftingHandler;
import cpw.mods.fml.common.IDispenseHandler;
import cpw.mods.fml.common.IFMLSidedHandler;
import cpw.mods.fml.common.IKeyHandler;
import cpw.mods.fml.common.INetworkHandler;
import cpw.mods.fml.common.IPickupNotifier;
import cpw.mods.fml.common.IPlayerTracker;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.LoaderException;
import cpw.mods.fml.common.ModContainer;
import cpw.mods.fml.common.ModContainer.ModState;
import cpw.mods.fml.common.ModContainer.SourceType;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.common.ProxyInjector;
import cpw.mods.fml.common.TickType;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.logging.Logger;






















public class ModLoaderModContainer
  implements ModContainer
{
  private static final ProxyInjector NULLPROXY = new ProxyInjector("", "", "", null);
  private Class<? extends BaseMod> modClazz;
  private BaseMod mod;
  private File modSource;
  private ArrayList<String> dependencies;
  private ArrayList<String> preDependencies;
  private ArrayList<String> postDependencies;
  private ArrayList<IKeyHandler> keyHandlers;
  private ModContainer.ModState state;
  private ModContainer.SourceType sourceType;
  private ModMetadata metadata;
  private ProxyInjector sidedProxy;
  private BaseModTicker tickHandler;
  
  public ModLoaderModContainer(Class<? extends BaseMod> modClazz, File modSource)
  {
    this.modClazz = modClazz;
    this.modSource = modSource;
    
    nextState();
  }
  



  ModLoaderModContainer(BaseMod instance)
  {
    FMLCommonHandler.instance().addAuxilliaryModContainer(this);
    this.mod = instance;
    this.tickHandler = new BaseModTicker(instance);
  }
  

  public boolean wantsPreInit()
  {
    return true;
  }
  

  public boolean wantsPostInit()
  {
    return true;
  }
  

  public void preInit()
  {
    try
    {
      EnumSet<TickType> ticks = EnumSet.noneOf(TickType.class);
      this.tickHandler = new BaseModTicker(ticks);
      configureMod();
      this.mod = ((BaseMod)this.modClazz.newInstance());
      this.tickHandler.setMod(this.mod);
      FMLCommonHandler.instance().registerTickHandler(this.tickHandler);
      FMLCommonHandler.instance().registerWorldGenerator(this.mod);
    }
    catch (Exception e)
    {
      throw new LoaderException(e);
    }
  }
  

  public ModContainer.ModState getModState()
  {
    return this.state;
  }
  

  public void nextState()
  {
    if (this.state == null) {
      this.state = ModContainer.ModState.UNLOADED;
      return;
    }
    if (this.state.ordinal() + 1 < ModContainer.ModState.values().length) {
      this.state = ModContainer.ModState.values()[(this.state.ordinal() + 1)];
    }
  }
  
  /* Error */
  private void configureMod()
  {
    // Byte code:
    //   0: invokestatic 5	cpw/mods/fml/common/FMLCommonHandler:instance	()Lcpw/mods/fml/common/FMLCommonHandler;
    //   3: invokevirtual 27	cpw/mods/fml/common/FMLCommonHandler:getSidedDelegate	()Lcpw/mods/fml/common/IFMLSidedHandler;
    //   6: astore_1
    //   7: invokestatic 28	cpw/mods/fml/common/Loader:instance	()Lcpw/mods/fml/common/Loader;
    //   10: invokevirtual 29	cpw/mods/fml/common/Loader:getConfigDir	()Ljava/io/File;
    //   13: astore_2
    //   14: aload_0
    //   15: getfield 2	cpw/mods/fml/common/modloader/ModLoaderModContainer:modClazz	Ljava/lang/Class;
    //   18: invokevirtual 30	java/lang/Class:getSimpleName	()Ljava/lang/String;
    //   21: astore_3
    //   22: new 31	java/io/File
    //   25: dup
    //   26: aload_2
    //   27: ldc 32
    //   29: iconst_1
    //   30: anewarray 33	java/lang/Object
    //   33: dup
    //   34: iconst_0
    //   35: aload_3
    //   36: aastore
    //   37: invokestatic 34	java/lang/String:format	(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
    //   40: invokespecial 35	java/io/File:<init>	(Ljava/io/File;Ljava/lang/String;)V
    //   43: astore 4
    //   45: new 36	java/util/Properties
    //   48: dup
    //   49: invokespecial 37	java/util/Properties:<init>	()V
    //   52: astore 5
    //   54: iconst_0
    //   55: istore 6
    //   57: iconst_0
    //   58: istore 7
    //   60: aload 4
    //   62: invokevirtual 38	java/io/File:exists	()Z
    //   65: ifeq +106 -> 171
    //   68: getstatic 39	cpw/mods/fml/common/Loader:log	Ljava/util/logging/Logger;
    //   71: ldc 40
    //   73: iconst_2
    //   74: anewarray 33	java/lang/Object
    //   77: dup
    //   78: iconst_0
    //   79: aload_3
    //   80: aastore
    //   81: dup
    //   82: iconst_1
    //   83: aload 4
    //   85: invokevirtual 41	java/io/File:getName	()Ljava/lang/String;
    //   88: aastore
    //   89: invokestatic 34	java/lang/String:format	(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
    //   92: invokevirtual 42	java/util/logging/Logger:fine	(Ljava/lang/String;)V
    //   95: new 43	java/io/FileReader
    //   98: dup
    //   99: aload 4
    //   101: invokespecial 44	java/io/FileReader:<init>	(Ljava/io/File;)V
    //   104: astore 8
    //   106: aload 5
    //   108: aload 8
    //   110: invokevirtual 45	java/util/Properties:load	(Ljava/io/Reader;)V
    //   113: aload 8
    //   115: invokevirtual 46	java/io/FileReader:close	()V
    //   118: goto +50 -> 168
    //   121: astore 8
    //   123: getstatic 39	cpw/mods/fml/common/Loader:log	Ljava/util/logging/Logger;
    //   126: ldc 47
    //   128: iconst_1
    //   129: anewarray 33	java/lang/Object
    //   132: dup
    //   133: iconst_0
    //   134: aload 4
    //   136: invokevirtual 41	java/io/File:getName	()Ljava/lang/String;
    //   139: aastore
    //   140: invokestatic 34	java/lang/String:format	(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
    //   143: invokevirtual 48	java/util/logging/Logger:severe	(Ljava/lang/String;)V
    //   146: getstatic 39	cpw/mods/fml/common/Loader:log	Ljava/util/logging/Logger;
    //   149: ldc 49
    //   151: ldc 50
    //   153: aload 8
    //   155: invokevirtual 51	java/util/logging/Logger:throwing	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
    //   158: new 21	cpw/mods/fml/common/LoaderException
    //   161: dup
    //   162: aload 8
    //   164: invokespecial 22	cpw/mods/fml/common/LoaderException:<init>	(Ljava/lang/Throwable;)V
    //   167: athrow
    //   168: iconst_1
    //   169: istore 6
    //   171: new 52	java/lang/StringBuffer
    //   174: dup
    //   175: invokespecial 53	java/lang/StringBuffer:<init>	()V
    //   178: astore 8
    //   180: aload 8
    //   182: ldc 54
    //   184: invokevirtual 55	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   187: pop
    //   188: aload_0
    //   189: getfield 2	cpw/mods/fml/common/modloader/ModLoaderModContainer:modClazz	Ljava/lang/Class;
    //   192: invokevirtual 56	java/lang/Class:getDeclaredFields	()[Ljava/lang/reflect/Field;
    //   195: astore 9
    //   197: aload 9
    //   199: arraylength
    //   200: istore 10
    //   202: iconst_0
    //   203: istore 11
    //   205: iload 11
    //   207: iload 10
    //   209: if_icmpge +643 -> 852
    //   212: aload 9
    //   214: iload 11
    //   216: aaload
    //   217: astore 12
    //   219: aload 12
    //   221: invokevirtual 57	java/lang/reflect/Field:getModifiers	()I
    //   224: invokestatic 58	java/lang/reflect/Modifier:isStatic	(I)Z
    //   227: ifne +6 -> 233
    //   230: goto +616 -> 846
    //   233: aload_1
    //   234: aload 12
    //   236: invokeinterface 59 2 0
    //   241: astore 13
    //   243: aload 13
    //   245: ifnonnull +6 -> 251
    //   248: goto +598 -> 846
    //   251: aload 13
    //   253: invokevirtual 60	cpw/mods/fml/common/modloader/ModProperty:name	()Ljava/lang/String;
    //   256: invokevirtual 61	java/lang/String:length	()I
    //   259: ifle +11 -> 270
    //   262: aload 13
    //   264: invokevirtual 60	cpw/mods/fml/common/modloader/ModProperty:name	()Ljava/lang/String;
    //   267: goto +8 -> 275
    //   270: aload 12
    //   272: invokevirtual 62	java/lang/reflect/Field:getName	()Ljava/lang/String;
    //   275: astore 14
    //   277: aconst_null
    //   278: astore 15
    //   280: aconst_null
    //   281: astore 16
    //   283: aload 12
    //   285: aconst_null
    //   286: invokevirtual 63	java/lang/reflect/Field:get	(Ljava/lang/Object;)Ljava/lang/Object;
    //   289: astore 16
    //   291: aload 5
    //   293: aload 14
    //   295: aload_0
    //   296: aload 16
    //   298: invokespecial 64	cpw/mods/fml/common/modloader/ModLoaderModContainer:extractValue	(Ljava/lang/Object;)Ljava/lang/String;
    //   301: invokevirtual 65	java/util/Properties:getProperty	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   304: astore 15
    //   306: aload_0
    //   307: aload 15
    //   309: aload 13
    //   311: aload 12
    //   313: invokevirtual 66	java/lang/reflect/Field:getType	()Ljava/lang/Class;
    //   316: aload 14
    //   318: aload_3
    //   319: invokespecial 67	cpw/mods/fml/common/modloader/ModLoaderModContainer:parseValue	(Ljava/lang/String;Lcpw/mods/fml/common/modloader/ModProperty;Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/Object;
    //   322: astore 17
    //   324: getstatic 39	cpw/mods/fml/common/Loader:log	Ljava/util/logging/Logger;
    //   327: ldc 68
    //   329: iconst_5
    //   330: anewarray 33	java/lang/Object
    //   333: dup
    //   334: iconst_0
    //   335: aload_3
    //   336: aastore
    //   337: dup
    //   338: iconst_1
    //   339: aload 14
    //   341: aastore
    //   342: dup
    //   343: iconst_2
    //   344: aload 16
    //   346: aastore
    //   347: dup
    //   348: iconst_3
    //   349: aload 15
    //   351: aastore
    //   352: dup
    //   353: iconst_4
    //   354: aload 17
    //   356: aastore
    //   357: invokestatic 34	java/lang/String:format	(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
    //   360: invokevirtual 69	java/util/logging/Logger:finest	(Ljava/lang/String;)V
    //   363: aload 17
    //   365: ifnull +50 -> 415
    //   368: aload 17
    //   370: aload 16
    //   372: invokevirtual 70	java/lang/Object:equals	(Ljava/lang/Object;)Z
    //   375: ifne +40 -> 415
    //   378: getstatic 39	cpw/mods/fml/common/Loader:log	Ljava/util/logging/Logger;
    //   381: ldc 71
    //   383: iconst_3
    //   384: anewarray 33	java/lang/Object
    //   387: dup
    //   388: iconst_0
    //   389: aload_3
    //   390: aastore
    //   391: dup
    //   392: iconst_1
    //   393: aload 14
    //   395: aastore
    //   396: dup
    //   397: iconst_2
    //   398: aload 17
    //   400: aastore
    //   401: invokestatic 34	java/lang/String:format	(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
    //   404: invokevirtual 69	java/util/logging/Logger:finest	(Ljava/lang/String;)V
    //   407: aload 12
    //   409: aconst_null
    //   410: aload 17
    //   412: invokevirtual 72	java/lang/reflect/Field:set	(Ljava/lang/Object;Ljava/lang/Object;)V
    //   415: aload 8
    //   417: ldc 73
    //   419: iconst_3
    //   420: anewarray 33	java/lang/Object
    //   423: dup
    //   424: iconst_0
    //   425: aload 14
    //   427: aastore
    //   428: dup
    //   429: iconst_1
    //   430: aload 12
    //   432: invokevirtual 66	java/lang/reflect/Field:getType	()Ljava/lang/Class;
    //   435: invokevirtual 74	java/lang/Class:getName	()Ljava/lang/String;
    //   438: aastore
    //   439: dup
    //   440: iconst_2
    //   441: aload 16
    //   443: aastore
    //   444: invokestatic 34	java/lang/String:format	(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
    //   447: invokevirtual 55	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   450: pop
    //   451: aload 13
    //   453: invokevirtual 75	cpw/mods/fml/common/modloader/ModProperty:min	()D
    //   456: ldc2_w 76
    //   459: dcmpl
    //   460: ifeq +34 -> 494
    //   463: aload 8
    //   465: ldc 78
    //   467: invokevirtual 55	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   470: ldc 79
    //   472: iconst_1
    //   473: anewarray 33	java/lang/Object
    //   476: dup
    //   477: iconst_0
    //   478: aload 13
    //   480: invokevirtual 75	cpw/mods/fml/common/modloader/ModProperty:min	()D
    //   483: invokestatic 80	java/lang/Double:valueOf	(D)Ljava/lang/Double;
    //   486: aastore
    //   487: invokestatic 34	java/lang/String:format	(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
    //   490: invokevirtual 55	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   493: pop
    //   494: aload 13
    //   496: invokevirtual 81	cpw/mods/fml/common/modloader/ModProperty:max	()D
    //   499: ldc2_w 82
    //   502: dcmpl
    //   503: ifeq +34 -> 537
    //   506: aload 8
    //   508: ldc 84
    //   510: invokevirtual 55	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   513: ldc 79
    //   515: iconst_1
    //   516: anewarray 33	java/lang/Object
    //   519: dup
    //   520: iconst_0
    //   521: aload 13
    //   523: invokevirtual 81	cpw/mods/fml/common/modloader/ModProperty:max	()D
    //   526: invokestatic 80	java/lang/Double:valueOf	(D)Ljava/lang/Double;
    //   529: aastore
    //   530: invokestatic 34	java/lang/String:format	(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
    //   533: invokevirtual 55	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   536: pop
    //   537: aload 8
    //   539: ldc 85
    //   541: invokevirtual 55	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   544: pop
    //   545: aload 13
    //   547: invokevirtual 86	cpw/mods/fml/common/modloader/ModProperty:info	()Ljava/lang/String;
    //   550: invokevirtual 61	java/lang/String:length	()I
    //   553: ifle +19 -> 572
    //   556: aload 8
    //   558: ldc 87
    //   560: invokevirtual 55	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   563: aload 13
    //   565: invokevirtual 86	cpw/mods/fml/common/modloader/ModProperty:info	()Ljava/lang/String;
    //   568: invokevirtual 55	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   571: pop
    //   572: aload 15
    //   574: ifnull +17 -> 591
    //   577: aload 5
    //   579: aload 14
    //   581: aload_0
    //   582: aload 15
    //   584: invokespecial 64	cpw/mods/fml/common/modloader/ModLoaderModContainer:extractValue	(Ljava/lang/Object;)Ljava/lang/String;
    //   587: invokevirtual 88	java/util/Properties:setProperty	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/Object;
    //   590: pop
    //   591: aload 8
    //   593: ldc 89
    //   595: invokevirtual 55	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   598: pop
    //   599: goto +244 -> 843
    //   602: astore 17
    //   604: getstatic 39	cpw/mods/fml/common/Loader:log	Ljava/util/logging/Logger;
    //   607: ldc 90
    //   609: iconst_2
    //   610: anewarray 33	java/lang/Object
    //   613: dup
    //   614: iconst_0
    //   615: aload 14
    //   617: aastore
    //   618: dup
    //   619: iconst_1
    //   620: aload 4
    //   622: invokevirtual 41	java/io/File:getName	()Ljava/lang/String;
    //   625: aastore
    //   626: invokestatic 34	java/lang/String:format	(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
    //   629: invokevirtual 48	java/util/logging/Logger:severe	(Ljava/lang/String;)V
    //   632: getstatic 39	cpw/mods/fml/common/Loader:log	Ljava/util/logging/Logger;
    //   635: ldc 49
    //   637: ldc 50
    //   639: aload 17
    //   641: invokevirtual 51	java/util/logging/Logger:throwing	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
    //   644: new 21	cpw/mods/fml/common/LoaderException
    //   647: dup
    //   648: aload 17
    //   650: invokespecial 22	cpw/mods/fml/common/LoaderException:<init>	(Ljava/lang/Throwable;)V
    //   653: athrow
    //   654: astore 18
    //   656: aload 8
    //   658: ldc 73
    //   660: iconst_3
    //   661: anewarray 33	java/lang/Object
    //   664: dup
    //   665: iconst_0
    //   666: aload 14
    //   668: aastore
    //   669: dup
    //   670: iconst_1
    //   671: aload 12
    //   673: invokevirtual 66	java/lang/reflect/Field:getType	()Ljava/lang/Class;
    //   676: invokevirtual 74	java/lang/Class:getName	()Ljava/lang/String;
    //   679: aastore
    //   680: dup
    //   681: iconst_2
    //   682: aload 16
    //   684: aastore
    //   685: invokestatic 34	java/lang/String:format	(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
    //   688: invokevirtual 55	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   691: pop
    //   692: aload 13
    //   694: invokevirtual 75	cpw/mods/fml/common/modloader/ModProperty:min	()D
    //   697: ldc2_w 76
    //   700: dcmpl
    //   701: ifeq +34 -> 735
    //   704: aload 8
    //   706: ldc 78
    //   708: invokevirtual 55	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   711: ldc 79
    //   713: iconst_1
    //   714: anewarray 33	java/lang/Object
    //   717: dup
    //   718: iconst_0
    //   719: aload 13
    //   721: invokevirtual 75	cpw/mods/fml/common/modloader/ModProperty:min	()D
    //   724: invokestatic 80	java/lang/Double:valueOf	(D)Ljava/lang/Double;
    //   727: aastore
    //   728: invokestatic 34	java/lang/String:format	(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
    //   731: invokevirtual 55	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   734: pop
    //   735: aload 13
    //   737: invokevirtual 81	cpw/mods/fml/common/modloader/ModProperty:max	()D
    //   740: ldc2_w 82
    //   743: dcmpl
    //   744: ifeq +34 -> 778
    //   747: aload 8
    //   749: ldc 84
    //   751: invokevirtual 55	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   754: ldc 79
    //   756: iconst_1
    //   757: anewarray 33	java/lang/Object
    //   760: dup
    //   761: iconst_0
    //   762: aload 13
    //   764: invokevirtual 81	cpw/mods/fml/common/modloader/ModProperty:max	()D
    //   767: invokestatic 80	java/lang/Double:valueOf	(D)Ljava/lang/Double;
    //   770: aastore
    //   771: invokestatic 34	java/lang/String:format	(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
    //   774: invokevirtual 55	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   777: pop
    //   778: aload 8
    //   780: ldc 85
    //   782: invokevirtual 55	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   785: pop
    //   786: aload 13
    //   788: invokevirtual 86	cpw/mods/fml/common/modloader/ModProperty:info	()Ljava/lang/String;
    //   791: invokevirtual 61	java/lang/String:length	()I
    //   794: ifle +19 -> 813
    //   797: aload 8
    //   799: ldc 87
    //   801: invokevirtual 55	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   804: aload 13
    //   806: invokevirtual 86	cpw/mods/fml/common/modloader/ModProperty:info	()Ljava/lang/String;
    //   809: invokevirtual 55	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   812: pop
    //   813: aload 15
    //   815: ifnull +17 -> 832
    //   818: aload 5
    //   820: aload 14
    //   822: aload_0
    //   823: aload 15
    //   825: invokespecial 64	cpw/mods/fml/common/modloader/ModLoaderModContainer:extractValue	(Ljava/lang/Object;)Ljava/lang/String;
    //   828: invokevirtual 88	java/util/Properties:setProperty	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/Object;
    //   831: pop
    //   832: aload 8
    //   834: ldc 89
    //   836: invokevirtual 55	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   839: pop
    //   840: aload 18
    //   842: athrow
    //   843: iconst_1
    //   844: istore 7
    //   846: iinc 11 1
    //   849: goto -644 -> 205
    //   852: iload 7
    //   854: ifne +28 -> 882
    //   857: iload 6
    //   859: ifne +23 -> 882
    //   862: getstatic 39	cpw/mods/fml/common/Loader:log	Ljava/util/logging/Logger;
    //   865: ldc 91
    //   867: iconst_1
    //   868: anewarray 33	java/lang/Object
    //   871: dup
    //   872: iconst_0
    //   873: aload_3
    //   874: aastore
    //   875: invokestatic 34	java/lang/String:format	(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
    //   878: invokevirtual 42	java/util/logging/Logger:fine	(Ljava/lang/String;)V
    //   881: return
    //   882: iload 7
    //   884: ifne +144 -> 1028
    //   887: iload 6
    //   889: ifeq +139 -> 1028
    //   892: new 31	java/io/File
    //   895: dup
    //   896: aload 4
    //   898: invokevirtual 92	java/io/File:getParent	()Ljava/lang/String;
    //   901: new 93	java/lang/StringBuilder
    //   904: dup
    //   905: invokespecial 94	java/lang/StringBuilder:<init>	()V
    //   908: aload 4
    //   910: invokevirtual 41	java/io/File:getName	()Ljava/lang/String;
    //   913: invokevirtual 95	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   916: ldc 96
    //   918: invokevirtual 95	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   921: invokevirtual 97	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   924: invokespecial 98	java/io/File:<init>	(Ljava/lang/String;Ljava/lang/String;)V
    //   927: astore 9
    //   929: getstatic 39	cpw/mods/fml/common/Loader:log	Ljava/util/logging/Logger;
    //   932: ldc 99
    //   934: iconst_2
    //   935: anewarray 33	java/lang/Object
    //   938: dup
    //   939: iconst_0
    //   940: aload_3
    //   941: aastore
    //   942: dup
    //   943: iconst_1
    //   944: aload 9
    //   946: invokevirtual 41	java/io/File:getName	()Ljava/lang/String;
    //   949: aastore
    //   950: invokestatic 34	java/lang/String:format	(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
    //   953: invokevirtual 42	java/util/logging/Logger:fine	(Ljava/lang/String;)V
    //   956: aload 4
    //   958: aload 9
    //   960: invokevirtual 100	java/io/File:renameTo	(Ljava/io/File;)Z
    //   963: istore 10
    //   965: iload 10
    //   967: ifeq +33 -> 1000
    //   970: getstatic 39	cpw/mods/fml/common/Loader:log	Ljava/util/logging/Logger;
    //   973: ldc 101
    //   975: iconst_2
    //   976: anewarray 33	java/lang/Object
    //   979: dup
    //   980: iconst_0
    //   981: aload_3
    //   982: aastore
    //   983: dup
    //   984: iconst_1
    //   985: aload 9
    //   987: invokevirtual 41	java/io/File:getName	()Ljava/lang/String;
    //   990: aastore
    //   991: invokestatic 34	java/lang/String:format	(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
    //   994: invokevirtual 42	java/util/logging/Logger:fine	(Ljava/lang/String;)V
    //   997: goto +30 -> 1027
    //   1000: getstatic 39	cpw/mods/fml/common/Loader:log	Ljava/util/logging/Logger;
    //   1003: ldc 102
    //   1005: iconst_2
    //   1006: anewarray 33	java/lang/Object
    //   1009: dup
    //   1010: iconst_0
    //   1011: aload_3
    //   1012: aastore
    //   1013: dup
    //   1014: iconst_1
    //   1015: aload 9
    //   1017: invokevirtual 41	java/io/File:getName	()Ljava/lang/String;
    //   1020: aastore
    //   1021: invokestatic 34	java/lang/String:format	(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
    //   1024: invokevirtual 42	java/util/logging/Logger:fine	(Ljava/lang/String;)V
    //   1027: return
    //   1028: new 103	java/io/FileWriter
    //   1031: dup
    //   1032: aload 4
    //   1034: invokespecial 104	java/io/FileWriter:<init>	(Ljava/io/File;)V
    //   1037: astore 9
    //   1039: aload 5
    //   1041: aload 9
    //   1043: aload 8
    //   1045: invokevirtual 105	java/lang/StringBuffer:toString	()Ljava/lang/String;
    //   1048: invokevirtual 106	java/util/Properties:store	(Ljava/io/Writer;Ljava/lang/String;)V
    //   1051: aload 9
    //   1053: invokevirtual 107	java/io/FileWriter:close	()V
    //   1056: getstatic 39	cpw/mods/fml/common/Loader:log	Ljava/util/logging/Logger;
    //   1059: ldc 108
    //   1061: iconst_2
    //   1062: anewarray 33	java/lang/Object
    //   1065: dup
    //   1066: iconst_0
    //   1067: aload_3
    //   1068: aastore
    //   1069: dup
    //   1070: iconst_1
    //   1071: aload 4
    //   1073: invokevirtual 41	java/io/File:getName	()Ljava/lang/String;
    //   1076: aastore
    //   1077: invokestatic 34	java/lang/String:format	(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
    //   1080: invokevirtual 42	java/util/logging/Logger:fine	(Ljava/lang/String;)V
    //   1083: goto +336 -> 1419
    //   1086: astore 9
    //   1088: getstatic 39	cpw/mods/fml/common/Loader:log	Ljava/util/logging/Logger;
    //   1091: ldc 110
    //   1093: iconst_1
    //   1094: anewarray 33	java/lang/Object
    //   1097: dup
    //   1098: iconst_0
    //   1099: aload 4
    //   1101: invokevirtual 41	java/io/File:getName	()Ljava/lang/String;
    //   1104: aastore
    //   1105: invokestatic 34	java/lang/String:format	(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
    //   1108: invokevirtual 111	java/util/logging/Logger:warning	(Ljava/lang/String;)V
    //   1111: getstatic 39	cpw/mods/fml/common/Loader:log	Ljava/util/logging/Logger;
    //   1114: ldc 49
    //   1116: ldc 50
    //   1118: aload 9
    //   1120: invokevirtual 51	java/util/logging/Logger:throwing	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
    //   1123: new 21	cpw/mods/fml/common/LoaderException
    //   1126: dup
    //   1127: aload 9
    //   1129: invokespecial 22	cpw/mods/fml/common/LoaderException:<init>	(Ljava/lang/Throwable;)V
    //   1132: athrow
    //   1133: astore 19
    //   1135: iload 7
    //   1137: ifne +28 -> 1165
    //   1140: iload 6
    //   1142: ifne +23 -> 1165
    //   1145: getstatic 39	cpw/mods/fml/common/Loader:log	Ljava/util/logging/Logger;
    //   1148: ldc 91
    //   1150: iconst_1
    //   1151: anewarray 33	java/lang/Object
    //   1154: dup
    //   1155: iconst_0
    //   1156: aload_3
    //   1157: aastore
    //   1158: invokestatic 34	java/lang/String:format	(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
    //   1161: invokevirtual 42	java/util/logging/Logger:fine	(Ljava/lang/String;)V
    //   1164: return
    //   1165: iload 7
    //   1167: ifne +144 -> 1311
    //   1170: iload 6
    //   1172: ifeq +139 -> 1311
    //   1175: new 31	java/io/File
    //   1178: dup
    //   1179: aload 4
    //   1181: invokevirtual 92	java/io/File:getParent	()Ljava/lang/String;
    //   1184: new 93	java/lang/StringBuilder
    //   1187: dup
    //   1188: invokespecial 94	java/lang/StringBuilder:<init>	()V
    //   1191: aload 4
    //   1193: invokevirtual 41	java/io/File:getName	()Ljava/lang/String;
    //   1196: invokevirtual 95	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1199: ldc 96
    //   1201: invokevirtual 95	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1204: invokevirtual 97	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   1207: invokespecial 98	java/io/File:<init>	(Ljava/lang/String;Ljava/lang/String;)V
    //   1210: astore 20
    //   1212: getstatic 39	cpw/mods/fml/common/Loader:log	Ljava/util/logging/Logger;
    //   1215: ldc 99
    //   1217: iconst_2
    //   1218: anewarray 33	java/lang/Object
    //   1221: dup
    //   1222: iconst_0
    //   1223: aload_3
    //   1224: aastore
    //   1225: dup
    //   1226: iconst_1
    //   1227: aload 20
    //   1229: invokevirtual 41	java/io/File:getName	()Ljava/lang/String;
    //   1232: aastore
    //   1233: invokestatic 34	java/lang/String:format	(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
    //   1236: invokevirtual 42	java/util/logging/Logger:fine	(Ljava/lang/String;)V
    //   1239: aload 4
    //   1241: aload 20
    //   1243: invokevirtual 100	java/io/File:renameTo	(Ljava/io/File;)Z
    //   1246: istore 21
    //   1248: iload 21
    //   1250: ifeq +33 -> 1283
    //   1253: getstatic 39	cpw/mods/fml/common/Loader:log	Ljava/util/logging/Logger;
    //   1256: ldc 101
    //   1258: iconst_2
    //   1259: anewarray 33	java/lang/Object
    //   1262: dup
    //   1263: iconst_0
    //   1264: aload_3
    //   1265: aastore
    //   1266: dup
    //   1267: iconst_1
    //   1268: aload 20
    //   1270: invokevirtual 41	java/io/File:getName	()Ljava/lang/String;
    //   1273: aastore
    //   1274: invokestatic 34	java/lang/String:format	(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
    //   1277: invokevirtual 42	java/util/logging/Logger:fine	(Ljava/lang/String;)V
    //   1280: goto +30 -> 1310
    //   1283: getstatic 39	cpw/mods/fml/common/Loader:log	Ljava/util/logging/Logger;
    //   1286: ldc 102
    //   1288: iconst_2
    //   1289: anewarray 33	java/lang/Object
    //   1292: dup
    //   1293: iconst_0
    //   1294: aload_3
    //   1295: aastore
    //   1296: dup
    //   1297: iconst_1
    //   1298: aload 20
    //   1300: invokevirtual 41	java/io/File:getName	()Ljava/lang/String;
    //   1303: aastore
    //   1304: invokestatic 34	java/lang/String:format	(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
    //   1307: invokevirtual 42	java/util/logging/Logger:fine	(Ljava/lang/String;)V
    //   1310: return
    //   1311: new 103	java/io/FileWriter
    //   1314: dup
    //   1315: aload 4
    //   1317: invokespecial 104	java/io/FileWriter:<init>	(Ljava/io/File;)V
    //   1320: astore 20
    //   1322: aload 5
    //   1324: aload 20
    //   1326: aload 8
    //   1328: invokevirtual 105	java/lang/StringBuffer:toString	()Ljava/lang/String;
    //   1331: invokevirtual 106	java/util/Properties:store	(Ljava/io/Writer;Ljava/lang/String;)V
    //   1334: aload 20
    //   1336: invokevirtual 107	java/io/FileWriter:close	()V
    //   1339: getstatic 39	cpw/mods/fml/common/Loader:log	Ljava/util/logging/Logger;
    //   1342: ldc 108
    //   1344: iconst_2
    //   1345: anewarray 33	java/lang/Object
    //   1348: dup
    //   1349: iconst_0
    //   1350: aload_3
    //   1351: aastore
    //   1352: dup
    //   1353: iconst_1
    //   1354: aload 4
    //   1356: invokevirtual 41	java/io/File:getName	()Ljava/lang/String;
    //   1359: aastore
    //   1360: invokestatic 34	java/lang/String:format	(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
    //   1363: invokevirtual 42	java/util/logging/Logger:fine	(Ljava/lang/String;)V
    //   1366: goto +50 -> 1416
    //   1369: astore 20
    //   1371: getstatic 39	cpw/mods/fml/common/Loader:log	Ljava/util/logging/Logger;
    //   1374: ldc 110
    //   1376: iconst_1
    //   1377: anewarray 33	java/lang/Object
    //   1380: dup
    //   1381: iconst_0
    //   1382: aload 4
    //   1384: invokevirtual 41	java/io/File:getName	()Ljava/lang/String;
    //   1387: aastore
    //   1388: invokestatic 34	java/lang/String:format	(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
    //   1391: invokevirtual 111	java/util/logging/Logger:warning	(Ljava/lang/String;)V
    //   1394: getstatic 39	cpw/mods/fml/common/Loader:log	Ljava/util/logging/Logger;
    //   1397: ldc 49
    //   1399: ldc 50
    //   1401: aload 20
    //   1403: invokevirtual 51	java/util/logging/Logger:throwing	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
    //   1406: new 21	cpw/mods/fml/common/LoaderException
    //   1409: dup
    //   1410: aload 20
    //   1412: invokespecial 22	cpw/mods/fml/common/LoaderException:<init>	(Ljava/lang/Throwable;)V
    //   1415: athrow
    //   1416: aload 19
    //   1418: athrow
    //   1419: return
    // Line number table:
    //   Java source line #140	-> byte code offset #0
    //   Java source line #141	-> byte code offset #7
    //   Java source line #142	-> byte code offset #14
    //   Java source line #143	-> byte code offset #22
    //   Java source line #144	-> byte code offset #45
    //   Java source line #146	-> byte code offset #54
    //   Java source line #147	-> byte code offset #57
    //   Java source line #149	-> byte code offset #60
    //   Java source line #153	-> byte code offset #68
    //   Java source line #154	-> byte code offset #95
    //   Java source line #155	-> byte code offset #106
    //   Java source line #156	-> byte code offset #113
    //   Java source line #163	-> byte code offset #118
    //   Java source line #158	-> byte code offset #121
    //   Java source line #160	-> byte code offset #123
    //   Java source line #161	-> byte code offset #146
    //   Java source line #162	-> byte code offset #158
    //   Java source line #164	-> byte code offset #168
    //   Java source line #167	-> byte code offset #171
    //   Java source line #168	-> byte code offset #180
    //   Java source line #172	-> byte code offset #188
    //   Java source line #174	-> byte code offset #219
    //   Java source line #176	-> byte code offset #230
    //   Java source line #179	-> byte code offset #233
    //   Java source line #180	-> byte code offset #243
    //   Java source line #182	-> byte code offset #248
    //   Java source line #184	-> byte code offset #251
    //   Java source line #185	-> byte code offset #277
    //   Java source line #186	-> byte code offset #280
    //   Java source line #190	-> byte code offset #283
    //   Java source line #191	-> byte code offset #291
    //   Java source line #192	-> byte code offset #306
    //   Java source line #193	-> byte code offset #324
    //   Java source line #195	-> byte code offset #363
    //   Java source line #197	-> byte code offset #378
    //   Java source line #198	-> byte code offset #407
    //   Java source line #209	-> byte code offset #415
    //   Java source line #211	-> byte code offset #451
    //   Java source line #213	-> byte code offset #463
    //   Java source line #216	-> byte code offset #494
    //   Java source line #218	-> byte code offset #506
    //   Java source line #221	-> byte code offset #537
    //   Java source line #223	-> byte code offset #545
    //   Java source line #225	-> byte code offset #556
    //   Java source line #228	-> byte code offset #572
    //   Java source line #230	-> byte code offset #577
    //   Java source line #232	-> byte code offset #591
    //   Java source line #233	-> byte code offset #599
    //   Java source line #201	-> byte code offset #602
    //   Java source line #203	-> byte code offset #604
    //   Java source line #204	-> byte code offset #632
    //   Java source line #205	-> byte code offset #644
    //   Java source line #209	-> byte code offset #654
    //   Java source line #211	-> byte code offset #692
    //   Java source line #213	-> byte code offset #704
    //   Java source line #216	-> byte code offset #735
    //   Java source line #218	-> byte code offset #747
    //   Java source line #221	-> byte code offset #778
    //   Java source line #223	-> byte code offset #786
    //   Java source line #225	-> byte code offset #797
    //   Java source line #228	-> byte code offset #813
    //   Java source line #230	-> byte code offset #818
    //   Java source line #232	-> byte code offset #832
    //   Java source line #234	-> byte code offset #843
    //   Java source line #172	-> byte code offset #846
    //   Java source line #239	-> byte code offset #852
    //   Java source line #241	-> byte code offset #862
    //   Java source line #242	-> byte code offset #881
    //   Java source line #245	-> byte code offset #882
    //   Java source line #247	-> byte code offset #892
    //   Java source line #248	-> byte code offset #929
    //   Java source line #249	-> byte code offset #956
    //   Java source line #250	-> byte code offset #965
    //   Java source line #252	-> byte code offset #970
    //   Java source line #256	-> byte code offset #1000
    //   Java source line #259	-> byte code offset #1027
    //   Java source line #263	-> byte code offset #1028
    //   Java source line #264	-> byte code offset #1039
    //   Java source line #265	-> byte code offset #1051
    //   Java source line #266	-> byte code offset #1056
    //   Java source line #273	-> byte code offset #1083
    //   Java source line #268	-> byte code offset #1086
    //   Java source line #270	-> byte code offset #1088
    //   Java source line #271	-> byte code offset #1111
    //   Java source line #272	-> byte code offset #1123
    //   Java source line #239	-> byte code offset #1133
    //   Java source line #241	-> byte code offset #1145
    //   Java source line #242	-> byte code offset #1164
    //   Java source line #245	-> byte code offset #1165
    //   Java source line #247	-> byte code offset #1175
    //   Java source line #248	-> byte code offset #1212
    //   Java source line #249	-> byte code offset #1239
    //   Java source line #250	-> byte code offset #1248
    //   Java source line #252	-> byte code offset #1253
    //   Java source line #256	-> byte code offset #1283
    //   Java source line #259	-> byte code offset #1310
    //   Java source line #263	-> byte code offset #1311
    //   Java source line #264	-> byte code offset #1322
    //   Java source line #265	-> byte code offset #1334
    //   Java source line #266	-> byte code offset #1339
    //   Java source line #273	-> byte code offset #1366
    //   Java source line #268	-> byte code offset #1369
    //   Java source line #270	-> byte code offset #1371
    //   Java source line #271	-> byte code offset #1394
    //   Java source line #272	-> byte code offset #1406
    //   Java source line #275	-> byte code offset #1419
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	1420	0	this	ModLoaderModContainer
    //   6	228	1	sideHandler	IFMLSidedHandler
    //   13	14	2	configDir	File
    //   21	1330	3	modConfigName	String
    //   43	1340	4	modConfig	File
    //   52	1271	5	props	java.util.Properties
    //   55	1116	6	existingConfigFound	boolean
    //   58	1108	7	mlPropFound	boolean
    //   104	10	8	configReader	java.io.FileReader
    //   121	42	8	e	Exception
    //   178	1149	8	comments	StringBuffer
    //   195	18	9	arr$	java.lang.reflect.Field[]
    //   927	89	9	mlPropBackup	File
    //   1037	15	9	configWriter	java.io.FileWriter
    //   1086	42	9	e	java.io.IOException
    //   200	8	10	len$	int
    //   963	3	10	renamed	boolean
    //   203	644	11	i$	int
    //   217	455	12	f	java.lang.reflect.Field
    //   241	564	13	property	ModProperty
    //   275	546	14	propertyName	String
    //   278	546	15	propertyValue	String
    //   281	402	16	defaultValue	Object
    //   322	89	17	currentValue	Object
    //   602	47	17	e	Exception
    //   654	187	18	localObject1	Object
    //   1133	284	19	localObject2	Object
    //   1210	89	20	mlPropBackup	File
    //   1320	15	20	configWriter	java.io.FileWriter
    //   1369	42	20	e	java.io.IOException
    //   1246	3	21	renamed	boolean
    // Exception table:
    //   from	to	target	type
    //   68	118	121	java/lang/Exception
    //   283	415	602	java/lang/Exception
    //   283	415	654	finally
    //   602	656	654	finally
    //   1028	1083	1086	java/io/IOException
    //   188	852	1133	finally
    //   1133	1135	1133	finally
    //   1311	1366	1369	java/io/IOException
  }
  
  private Object parseValue(String val, ModProperty property, Class<?> type, String propertyName, String modConfigName)
  {
    if (type.isAssignableFrom(String.class))
    {
      return val;
    }
    if ((type.isAssignableFrom(Boolean.TYPE)) || (type.isAssignableFrom(Boolean.class)))
    {
      return Boolean.valueOf(Boolean.parseBoolean(val));
    }
    if ((Number.class.isAssignableFrom(type)) || (type.isPrimitive()))
    {
      Number n = null;
      
      if ((type.isAssignableFrom(Double.TYPE)) || (Double.class.isAssignableFrom(type)))
      {
        n = Double.valueOf(Double.parseDouble(val));
      }
      else if ((type.isAssignableFrom(Float.TYPE)) || (Float.class.isAssignableFrom(type)))
      {
        n = Float.valueOf(Float.parseFloat(val));
      }
      else if ((type.isAssignableFrom(Long.TYPE)) || (Long.class.isAssignableFrom(type)))
      {
        n = Long.valueOf(Long.parseLong(val));
      }
      else if ((type.isAssignableFrom(Integer.TYPE)) || (Integer.class.isAssignableFrom(type)))
      {
        n = Integer.valueOf(Integer.parseInt(val));
      }
      else if ((type.isAssignableFrom(Short.TYPE)) || (Short.class.isAssignableFrom(type)))
      {
        n = Short.valueOf(Short.parseShort(val));
      }
      else if ((type.isAssignableFrom(Byte.TYPE)) || (Byte.class.isAssignableFrom(type)))
      {
        n = Byte.valueOf(Byte.parseByte(val));
      }
      else
      {
        throw new IllegalArgumentException(String.format("MLProp declared on %s of type %s, an unsupported type", new Object[] { propertyName, type.getName() }));
      }
      
      if ((n.doubleValue() < property.min()) || (n.doubleValue() > property.max()))
      {
        Loader.log.warning(String.format("Configuration for %s.%s found value %s outside acceptable range %s,%s", new Object[] { modConfigName, propertyName, n, Double.valueOf(property.min()), Double.valueOf(property.max()) }));
        return null;
      }
      

      return n;
    }
    

    throw new IllegalArgumentException(String.format("MLProp declared on %s of type %s, an unsupported type", new Object[] { propertyName, type.getName() }));
  }
  
  private String extractValue(Object value) {
    if (String.class.isInstance(value))
    {
      return (String)value;
    }
    if ((Number.class.isInstance(value)) || (Boolean.class.isInstance(value)))
    {
      return String.valueOf(value);
    }
    

    throw new IllegalArgumentException("MLProp declared on non-standard type");
  }
  

  public void init()
  {
    this.mod.load();
  }
  

  public void postInit()
  {
    this.mod.modsLoaded();
  }
  

  public String getName()
  {
    return this.mod != null ? this.mod.getName() : this.modClazz.getSimpleName();
  }
  
  @Deprecated
  public static ModContainer findContainerFor(BaseMod mod)
  {
    return FMLCommonHandler.instance().findContainerFor(mod);
  }
  

  public String getSortingRules()
  {
    if (this.mod != null) {
      return this.mod.getPriorities();
    }
    return "";
  }
  

  public boolean matches(Object mod)
  {
    return this.modClazz.isInstance(mod);
  }
  





  public static <A extends BaseMod> List<A> findAll(Class<A> clazz)
  {
    ArrayList<A> modList = new ArrayList();
    
    for (ModContainer mc : Loader.getModList())
    {
      if (((mc instanceof ModLoaderModContainer)) && (mc.getMod() != null))
      {
        modList.add((A)((ModLoaderModContainer)mc).mod);
      }
    }
    
    return modList;
  }
  

  public File getSource()
  {
    return this.modSource;
  }
  

  public Object getMod()
  {
    return this.mod;
  }
  

  public int lookupFuelValue(int itemId, int itemDamage)
  {
    return this.mod.addFuel(itemId, itemDamage);
  }
  

  public boolean wantsPickupNotification()
  {
    return true;
  }
  

  public IPickupNotifier getPickupNotifier()
  {
    return this.mod;
  }
  

  public boolean wantsToDispense()
  {
    return true;
  }
  

  public IDispenseHandler getDispenseHandler()
  {
    return this.mod;
  }
  

  public boolean wantsCraftingNotification()
  {
    return true;
  }
  

  public ICraftingHandler getCraftingHandler()
  {
    return this.mod;
  }
  
  private void computeDependencies()
  {
    this.dependencies = new ArrayList();
    this.preDependencies = new ArrayList();
    this.postDependencies = new ArrayList();
    
    if ((this.mod.getPriorities() == null) || (this.mod.getPriorities().length() == 0))
    {
      return;
    }
    
    boolean parseFailure = false;
    StringTokenizer st = new StringTokenizer(this.mod.getPriorities(), ";");
    
    while (st.hasMoreTokens())
    {
      String dep = st.nextToken();
      String[] depparts = dep.split(":");
      
      if (depparts.length < 2)
      {
        parseFailure = true;
      }
      else {
        if (("required-before".equals(depparts[0])) || ("required-after".equals(depparts[0])))
        {
          if (!depparts[1].trim().equals("*")) {
            this.dependencies.add(depparts[1]);
          } else {
            parseFailure = true;
            continue;
          }
        }
        
        if (("required-before".equals(depparts[0])) || ("before".equals(depparts[0])))
        {
          this.postDependencies.add(depparts[1]);
        } else if (("required-after".equals(depparts[0])) || ("after".equals(depparts[0])))
        {
          this.preDependencies.add(depparts[1]);
        } else {
          parseFailure = true;
        }
      }
    }
    if (parseFailure) {
      FMLCommonHandler.instance().getFMLLogger().warning(String.format("The mod %s has an incorrect dependency string {%s}", new Object[] { this.mod.getName(), this.mod.getPriorities() }));
    }
  }
  

  public List<String> getDependencies()
  {
    if (this.dependencies == null)
    {
      computeDependencies();
    }
    
    return this.dependencies;
  }
  

  public List<String> getPostDepends()
  {
    if (this.dependencies == null)
    {
      computeDependencies();
    }
    
    return this.postDependencies;
  }
  

  public List<String> getPreDepends()
  {
    if (this.dependencies == null)
    {
      computeDependencies();
    }
    return this.preDependencies;
  }
  

  public String toString()
  {
    return this.modClazz.getSimpleName();
  }
  

  public boolean wantsNetworkPackets()
  {
    return true;
  }
  

  public INetworkHandler getNetworkHandler()
  {
    return this.mod;
  }
  

  public boolean ownsNetworkChannel(String channel)
  {
    return FMLCommonHandler.instance().getChannelListFor(this).contains(channel);
  }
  

  public boolean wantsConsoleCommands()
  {
    return true;
  }
  

  public IConsoleHandler getConsoleHandler()
  {
    return this.mod;
  }
  

  public boolean wantsPlayerTracking()
  {
    return true;
  }
  

  public IPlayerTracker getPlayerTracker()
  {
    return this.mod;
  }
  




  public void addKeyHandler(IKeyHandler handler)
  {
    if (this.keyHandlers == null) {
      this.keyHandlers = new ArrayList();
    }
    
    Iterator<IKeyHandler> itr = this.keyHandlers.iterator();
    while (itr.hasNext())
    {
      IKeyHandler old = (IKeyHandler)itr.next();
      if (old.getKeyBinding() == handler.getKeyBinding())
      {
        itr.remove();
      }
    }
    
    this.keyHandlers.add(handler);
  }
  

  public List<IKeyHandler> getKeys()
  {
    if (this.keyHandlers == null) {
      return Collections.emptyList();
    }
    return this.keyHandlers;
  }
  
  public void setSourceType(ModContainer.SourceType type)
  {
    this.sourceType = type;
  }
  
  public ModContainer.SourceType getSourceType()
  {
    return this.sourceType;
  }
  




  public ModMetadata getMetadata()
  {
    return this.metadata;
  }
  




  public void setMetadata(ModMetadata meta)
  {
    this.metadata = meta;
  }
  




  public void gatherRenderers(Map renderers)
  {
    this.mod.onRenderHarvest(renderers);
  }
  




  public void requestAnimations()
  {
    this.mod.onRegisterAnimations();
  }
  




  public String getVersion()
  {
    if ((this.mod == null) || (this.mod.getVersion() == null))
    {
      return "Not available";
    }
    return this.mod.getVersion();
  }
  




  public ProxyInjector findSidedProxy()
  {
    if (this.sidedProxy == null) {
      this.sidedProxy = FMLCommonHandler.instance().getSidedDelegate().findSidedProxyOn(this.mod);
      if (this.sidedProxy == null)
      {
        this.sidedProxy = NULLPROXY;
      }
    }
    return this.sidedProxy == NULLPROXY ? null : this.sidedProxy;
  }
  




  public void keyBindEvent(Object keybinding)
  {
    this.mod.keyBindingEvent(keybinding);
  }
  



  public BaseModTicker getTickHandler()
  {
    return this.tickHandler;
  }
}
