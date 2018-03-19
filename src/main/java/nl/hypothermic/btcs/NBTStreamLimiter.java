package nl.hypothermic.btcs;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public class NBTStreamLimiter extends FilterInputStream {

    private final NBTReadLimiter limit;

    public NBTStreamLimiter(InputStream is, NBTReadLimiter limit)
    {
        super( is );
        this.limit = limit;
    }

    @Override
    public int read() throws IOException {
        limit.a( 8 );
        return super.read();
    }

    @Override
    public int read(byte[] b) throws IOException
    {
        limit.a( b.length * 8 );
        return super.read( b );
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException
    {
        limit.a( len * 8 );
        return super.read( b, off, len );
    }
}