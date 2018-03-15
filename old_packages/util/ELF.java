package org.ibex.nestedvm.util;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;










public class ELF
{
  private static final int ELF_MAGIC = 2135247942;
  public static final int ELFCLASSNONE = 0;
  public static final int ELFCLASS32 = 1;
  public static final int ELFCLASS64 = 2;
  public static final int ELFDATANONE = 0;
  public static final int ELFDATA2LSB = 1;
  public static final int ELFDATA2MSB = 2;
  public static final int SHT_SYMTAB = 2;
  public static final int SHT_STRTAB = 3;
  public static final int SHT_NOBITS = 8;
  public static final int SHF_WRITE = 1;
  public static final int SHF_ALLOC = 2;
  public static final int SHF_EXECINSTR = 4;
  public static final int PF_X = 1;
  public static final int PF_W = 2;
  public static final int PF_R = 4;
  public static final int PT_LOAD = 1;
  public static final short ET_EXEC = 2;
  public static final short EM_MIPS = 8;
  private Seekable data;
  public ELFIdent ident;
  public ELFHeader header;
  public PHeader[] pheaders;
  public SHeader[] sheaders;
  private byte[] stringTable;
  private boolean sectionReaderActive;
  private Symtab _symtab;
  
  private void readFully(byte[] paramArrayOfByte)
    throws IOException
  {
    int i = paramArrayOfByte.length;
    int j = 0;
    while (i > 0) {
      int k = this.data.read(paramArrayOfByte, j, i);
      if (k == -1) throw new IOException("EOF");
      j += k;
      i -= k;
    }
  }
  
  private int readIntBE() throws IOException {
    byte[] arrayOfByte = new byte[4];
    readFully(arrayOfByte);
    return (arrayOfByte[0] & 0xFF) << 24 | (arrayOfByte[1] & 0xFF) << 16 | (arrayOfByte[2] & 0xFF) << 8 | (arrayOfByte[3] & 0xFF) << 0;
  }
  
  private int readInt() throws IOException { int i = readIntBE();
    if ((this.ident != null) && (this.ident.data == 1))
      i = i << 24 & 0xFF000000 | i << 8 & 0xFF0000 | i >>> 8 & 0xFF00 | i >> 24 & 0xFF;
    return i;
  }
  
  private short readShortBE() throws IOException {
    byte[] arrayOfByte = new byte[2];
    readFully(arrayOfByte);
    return (short)((arrayOfByte[0] & 0xFF) << 8 | (arrayOfByte[1] & 0xFF) << 0);
  }
  
  private short readShort() throws IOException { short s = readShortBE();
    if ((this.ident != null) && (this.ident.data == 1))
      s = (short)((s << 8 & 0xFF00 | s >> 8 & 0xFF) & 0xFFFF);
    return s;
  }
  
  private byte readByte() throws IOException {
    byte[] arrayOfByte = new byte[1];
    readFully(arrayOfByte);
    return arrayOfByte[0];
  }
  
  public class ELFIdent {
    public byte klass;
    public byte data;
    public byte osabi;
    public byte abiversion;
    
    ELFIdent() throws IOException {
      if (ELF.this.readIntBE() != 2135247942) { throw new ELF.ELFException(ELF.this, "Bad Magic");
      }
      this.klass = ELF.this.readByte();
      if (this.klass != 1) { throw new ELF.ELFException(ELF.this, "org.ibex.nestedvm.util.ELF does not suport 64-bit binaries");
      }
      this.data = ELF.this.readByte();
      if ((this.data != 1) && (this.data != 2)) { throw new ELF.ELFException(ELF.this, "Unknown byte order");
      }
      ELF.this.readByte();
      this.osabi = ELF.this.readByte();
      this.abiversion = ELF.this.readByte();
      for (int i = 0; i < 7; i++) ELF.this.readByte();
    }
  }
  
  public class ELFHeader {
    public short type;
    public short machine;
    public int version;
    public int entry;
    public int phoff;
    public int shoff;
    public int flags;
    public short ehsize;
    public short phentsize;
    public short phnum;
    public short shentsize;
    public short shnum;
    public short shstrndx;
    
    ELFHeader() throws IOException {
      this.type = ELF.this.readShort();
      this.machine = ELF.this.readShort();
      this.version = ELF.this.readInt();
      if (this.version != 1) throw new ELF.ELFException(ELF.this, "version != 1");
      this.entry = ELF.this.readInt();
      this.phoff = ELF.this.readInt();
      this.shoff = ELF.this.readInt();
      this.flags = ELF.this.readInt();
      this.ehsize = ELF.this.readShort();
      this.phentsize = ELF.this.readShort();
      this.phnum = ELF.this.readShort();
      this.shentsize = ELF.this.readShort();
      this.shnum = ELF.this.readShort();
      this.shstrndx = ELF.this.readShort();
    }
  }
  
  public class PHeader {
    public int type;
    public int offset;
    public int vaddr;
    public int paddr;
    public int filesz;
    public int memsz;
    public int flags;
    public int align;
    
    PHeader() throws IOException {
      this.type = ELF.this.readInt();
      this.offset = ELF.this.readInt();
      this.vaddr = ELF.this.readInt();
      this.paddr = ELF.this.readInt();
      this.filesz = ELF.this.readInt();
      this.memsz = ELF.this.readInt();
      this.flags = ELF.this.readInt();
      this.align = ELF.this.readInt();
      if (this.filesz > this.memsz) throw new ELF.ELFException(ELF.this, "ELF inconsistency: filesz > memsz (" + ELF.toHex(this.filesz) + " > " + ELF.toHex(this.memsz) + ")");
    }
    
    public boolean writable() { return (this.flags & 0x2) != 0; }
    
    public InputStream getInputStream() throws IOException {
      return new BufferedInputStream(new ELF.SectionInputStream(ELF.this, this.offset, this.offset + this.filesz));
    }
  }
  
  public class SHeader
  {
    int nameidx;
    public String name;
    public int type;
    public int flags;
    public int addr;
    public int offset;
    public int size;
    public int link;
    public int info;
    public int addralign;
    public int entsize;
    
    SHeader() throws IOException {
      this.nameidx = ELF.this.readInt();
      this.type = ELF.this.readInt();
      this.flags = ELF.this.readInt();
      this.addr = ELF.this.readInt();
      this.offset = ELF.this.readInt();
      this.size = ELF.this.readInt();
      this.link = ELF.this.readInt();
      this.info = ELF.this.readInt();
      this.addralign = ELF.this.readInt();
      this.entsize = ELF.this.readInt();
    }
    
    public InputStream getInputStream() throws IOException {
      return new BufferedInputStream(new ELF.SectionInputStream(ELF.this, this.offset, this.type == 8 ? 0 : this.offset + this.size));
    }
    

    public boolean isText() { return this.name.equals(".text"); }
    public boolean isData() { return (this.name.equals(".data")) || (this.name.equals(".sdata")) || (this.name.equals(".rodata")) || (this.name.equals(".ctors")) || (this.name.equals(".dtors")); }
    public boolean isBSS() { return (this.name.equals(".bss")) || (this.name.equals(".sbss")); }
  }
  
  public ELF(String paramString) throws IOException, ELF.ELFException { this(new Seekable.File(paramString, false)); }
  
  public ELF(Seekable paramSeekable) throws IOException, ELF.ELFException { this.data = paramSeekable;
    this.ident = new ELFIdent();
    this.header = new ELFHeader();
    this.pheaders = new PHeader[this.header.phnum];
    for (int i = 0; i < this.header.phnum; i++) {
      paramSeekable.seek(this.header.phoff + i * this.header.phentsize);
      this.pheaders[i] = new PHeader();
    }
    this.sheaders = new SHeader[this.header.shnum];
    for (i = 0; i < this.header.shnum; i++) {
      paramSeekable.seek(this.header.shoff + i * this.header.shentsize);
      this.sheaders[i] = new SHeader();
    }
    if ((this.header.shstrndx < 0) || (this.header.shstrndx >= this.header.shnum)) throw new ELFException("Bad shstrndx");
    paramSeekable.seek(this.sheaders[this.header.shstrndx].offset);
    this.stringTable = new byte[this.sheaders[this.header.shstrndx].size];
    readFully(this.stringTable);
    
    for (i = 0; i < this.header.shnum; i++) {
      SHeader localSHeader = this.sheaders[i];
      localSHeader.name = getString(localSHeader.nameidx);
    }
  }
  
  private String getString(int paramInt) { return getString(paramInt, this.stringTable); }
  
  private String getString(int paramInt, byte[] paramArrayOfByte) { StringBuffer localStringBuffer = new StringBuffer();
    if ((paramInt < 0) || (paramInt >= paramArrayOfByte.length)) return "<invalid strtab entry>";
    while ((paramInt >= 0) && (paramInt < paramArrayOfByte.length) && (paramArrayOfByte[paramInt] != 0)) localStringBuffer.append((char)paramArrayOfByte[(paramInt++)]);
    return localStringBuffer.toString();
  }
  
  public SHeader sectionWithName(String paramString) {
    for (int i = 0; i < this.sheaders.length; i++)
      if (this.sheaders[i].name.equals(paramString))
        return this.sheaders[i];
    return null;
  }
  
  public class ELFException extends IOException { ELFException(String paramString) { super(); }
  }
  
  private class SectionInputStream extends InputStream { private int pos;
    private int maxpos;
    
    SectionInputStream(int paramInt1, int paramInt2) throws IOException { if (ELF.this.sectionReaderActive)
        throw new IOException("Section reader already active");
      ELF.this.sectionReaderActive = true;
      this.pos = paramInt1;
      ELF.this.data.seek(this.pos);
      this.maxpos = paramInt2;
    }
    
    private int bytesLeft() { return this.maxpos - this.pos; }
    
    public int read() throws IOException { byte[] arrayOfByte = new byte[1];
      return read(arrayOfByte, 0, 1) == -1 ? -1 : arrayOfByte[0] & 0xFF;
    }
    
    public int read(byte[] paramArrayOfByte, int paramInt1, int paramInt2) throws IOException { int i = ELF.this.data.read(paramArrayOfByte, paramInt1, Math.min(paramInt2, bytesLeft())); if (i > 0) this.pos += i; return i; }
    
    public void close() { ELF.this.sectionReaderActive = false; }
  }
  
  public Symtab getSymtab() throws IOException
  {
    if (this._symtab != null) { return this._symtab;
    }
    if (this.sectionReaderActive) { throw new ELFException("Can't read the symtab while a section reader is active");
    }
    SHeader localSHeader1 = sectionWithName(".symtab");
    if ((localSHeader1 == null) || (localSHeader1.type != 2)) { return null;
    }
    SHeader localSHeader2 = sectionWithName(".strtab");
    if ((localSHeader2 == null) || (localSHeader2.type != 3)) { return null;
    }
    byte[] arrayOfByte = new byte[localSHeader2.size];
    DataInputStream localDataInputStream = new DataInputStream(localSHeader2.getInputStream());
    localDataInputStream.readFully(arrayOfByte);
    localDataInputStream.close();
    
    return this._symtab = new Symtab(localSHeader1.offset, localSHeader1.size, arrayOfByte);
  }
  
  public class Symtab {
    public ELF.Symbol[] symbols;
    
    Symtab(int paramInt1, int paramInt2, byte[] paramArrayOfByte) throws IOException {
      ELF.this.data.seek(paramInt1);
      int i = paramInt2 / 16;
      this.symbols = new ELF.Symbol[i];
      for (int j = 0; j < i; j++) this.symbols[j] = new ELF.Symbol(ELF.this, paramArrayOfByte);
    }
    
    public ELF.Symbol getSymbol(String paramString) {
      ELF.Symbol localSymbol = null;
      for (int i = 0; i < this.symbols.length; i++) {
        if (this.symbols[i].name.equals(paramString)) {
          if (localSymbol == null) {
            localSymbol = this.symbols[i];
          } else
            System.err.println("WARNING: Multiple symbol matches for " + paramString);
        }
      }
      return localSymbol;
    }
    
    public ELF.Symbol getGlobalSymbol(String paramString) {
      for (int i = 0; i < this.symbols.length; i++)
        if ((this.symbols[i].binding == 1) && (this.symbols[i].name.equals(paramString)))
          return this.symbols[i];
      return null;
    }
  }
  
  public class Symbol
  {
    public String name;
    public int addr;
    public int size;
    public byte info;
    public byte type;
    public byte binding;
    public byte other;
    public short shndx;
    public ELF.SHeader sheader;
    public static final int STT_FUNC = 2;
    public static final int STB_GLOBAL = 1;
    
    Symbol(byte[] paramArrayOfByte) throws IOException {
      this.name = ELF.this.getString(ELF.access$300(ELF.this), paramArrayOfByte);
      this.addr = ELF.this.readInt();
      this.size = ELF.this.readInt();
      this.info = ELF.this.readByte();
      this.type = ((byte)(this.info & 0xF));
      this.binding = ((byte)(this.info >> 4));
      this.other = ELF.this.readByte();
      this.shndx = ELF.this.readShort();
    }
  }
  
  private static String toHex(int paramInt) { return "0x" + Long.toString(paramInt & 0xFFFFFFFF, 16); }
}
