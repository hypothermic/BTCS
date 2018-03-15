package org.bukkit.craftbukkit;

import java.util.HashMap;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.zip.Deflater;
import net.minecraft.server.EntityPlayer;
import net.minecraft.server.NetServerHandler;
import net.minecraft.server.Packet;
import net.minecraft.server.Packet51MapChunk;

public final class ChunkCompressionThread implements Runnable
{
  private static final ChunkCompressionThread instance = new ChunkCompressionThread();
  private static boolean isRunning = false;
  
  public ChunkCompressionThread() { this.QUEUE_CAPACITY = 10240;
    this.queueSizePerPlayer = new HashMap();
    this.packetQueue = new LinkedBlockingQueue(10240);
    
    this.CHUNK_SIZE = 163840;
    this.REDUCED_DEFLATE_THRESHOLD = 40960;
    this.DEFLATE_LEVEL_CHUNKS = 6;
    this.DEFLATE_LEVEL_PARTS = 1;
    
    this.deflater = new Deflater();
    this.deflateBuffer = new byte[163940]; }
  
  private int QUEUE_CAPACITY = 10240; // BTCS: rm final
  public static void startThread() { if (!isRunning) {
      isRunning = true;
      new Thread(instance).start(); } }
  
  private final HashMap<EntityPlayer, Integer> queueSizePerPlayer;
  private final BlockingQueue<QueuedPacket> packetQueue;
  private int CHUNK_SIZE = 163840; // BTCS: rm final
  private int REDUCED_DEFLATE_THRESHOLD = 40960; // BTCS: rm final
  
  public void run() { for (;;) { try { handleQueuedPacket((QueuedPacket)this.packetQueue.take());
      }
      catch (InterruptedException ie) {}catch (Exception e) {
        e.printStackTrace(); } } }
  
  private int DEFLATE_LEVEL_CHUNKS = 6; // BTCS: rm final
  private int DEFLATE_LEVEL_PARTS = 1; // BTCS: rm final
  private final Deflater deflater;
  private byte[] deflateBuffer;
  private void handleQueuedPacket(QueuedPacket queuedPacket) { addToPlayerQueueSize(queuedPacket.player, -1);
    
    if (queuedPacket.compress) {
      handleMapChunk(queuedPacket);
    }
    sendToNetworkQueue(queuedPacket);
  }
  
  private void handleMapChunk(QueuedPacket queuedPacket) {
    Packet51MapChunk packet = (Packet51MapChunk)queuedPacket.packet;
    

    if (packet.buffer != null) {
      return;
    }
    
    int dataSize = packet.rawData.length;
    if (this.deflateBuffer.length < dataSize + 100) {
      this.deflateBuffer = new byte[dataSize + 100];
    }
    
    this.deflater.reset();
    this.deflater.setLevel(dataSize < 40960 ? 1 : 6);
    this.deflater.setInput(packet.rawData);
    this.deflater.finish();
    int size = this.deflater.deflate(this.deflateBuffer);
    if (size == 0) {
      size = this.deflater.deflate(this.deflateBuffer);
    }
    

    packet.buffer = new byte[size];
    packet.size = size;
    System.arraycopy(this.deflateBuffer, 0, packet.buffer, 0, size);
  }
  
  private void sendToNetworkQueue(QueuedPacket queuedPacket) {
    queuedPacket.player.netServerHandler.networkManager.queue(queuedPacket.packet);
  }
  
  public static void sendPacket(EntityPlayer player, Packet packet) {
    if ((packet instanceof Packet51MapChunk))
    {
      instance.addQueuedPacket(new QueuedPacket(player, packet, true));
    }
    else {
      instance.addQueuedPacket(new QueuedPacket(player, packet, false));
    }
  }
  
  private void addToPlayerQueueSize(EntityPlayer player, int amount) {
    synchronized (this.queueSizePerPlayer) {
      Integer count = (Integer)this.queueSizePerPlayer.get(player);
      amount += (count == null ? 0 : count.intValue());
      if (amount == 0) {
        this.queueSizePerPlayer.remove(player);
      } else {
        this.queueSizePerPlayer.put(player, Integer.valueOf(amount));
      }
    }
  }
  
  public static int getPlayerQueueSize(EntityPlayer player) {
    synchronized (instance.queueSizePerPlayer) {
      Integer count = (Integer)instance.queueSizePerPlayer.get(player);
      return count == null ? 0 : count.intValue();
    }
  }
  
  private void addQueuedPacket(QueuedPacket task) {
    addToPlayerQueueSize(task.player, 1);
    for (;;)
    {
      try {
        this.packetQueue.put(task);
        return;
      }
      catch (InterruptedException e) {}
    }
  }
  
  private static class QueuedPacket {
    final EntityPlayer player;
    final Packet packet;
    final boolean compress;
    
    QueuedPacket(EntityPlayer player, Packet packet, boolean compress) {
      this.player = player;
      this.packet = packet;
      this.compress = compress;
    }
  }
}
