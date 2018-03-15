package net.minecraft.server;

import java.util.List;

public class FileIOThread implements Runnable {
  public static final FileIOThread a = new FileIOThread();
  
  private List b = java.util.Collections.synchronizedList(new java.util.ArrayList());
  
  private volatile long c = 0L;
  private volatile long d = 0L;
  private volatile boolean e = false;
  
  private FileIOThread() {
    Thread localThread = new Thread(this, "File IO Thread");
    localThread.setPriority(1);
    localThread.start();
  }
  
  public void run() {
    for (;;) {
      b();
    }
  }
  
  private void b() {
    for (int i = 0; i < this.b.size(); i++) {
      IAsyncChunkSaver localIAsyncChunkSaver = (IAsyncChunkSaver)this.b.get(i);
      boolean bool = localIAsyncChunkSaver.c();
      if (!bool) {
        this.b.remove(i--);
        this.d += 1L;
      }
      try
      {
        if (!this.e) {
          Thread.sleep(10L);
        } else {
          Thread.sleep(0L);
        }
      } catch (InterruptedException localInterruptedException2) {
        localInterruptedException2.printStackTrace();
      }
    }
    if (this.b.isEmpty()) {
      try {
        Thread.sleep(25L);
      } catch (InterruptedException localInterruptedException1) {
        localInterruptedException1.printStackTrace();
      }
    }
  }
  
  public void a(IAsyncChunkSaver paramIAsyncChunkSaver) {
    if (this.b.contains(paramIAsyncChunkSaver)) return;
    this.c += 1L;
    this.b.add(paramIAsyncChunkSaver);
  }
  
  public void a() {
    this.e = true;
    while (this.c != this.d) {
      try {
		Thread.sleep(10L);
	} catch (InterruptedException e1) {
		System.out.println("BTCS: Exception X2 happened in FileIOThread");
		e1.printStackTrace();
	}
    }
    this.e = false;
  }
}
