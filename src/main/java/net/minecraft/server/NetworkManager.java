package net.minecraft.server;

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import forge.ForgeHooks;
import forge.MessageManager;

public class NetworkManager {

	/** threadSyncObject - Synchronization object used for read and write threads. */
    public static final Object a = new Object();
    /** numReadThreads - The number of read threads spawned. Not really used on client side. */
    public static int b;
    /** numWriteThreads - The number of write threads spawned. Not really used on client side. */
    public static int c;
    /** The object used for synchronization on the send queue. */
    private Object g = new Object();
    /** The socket used by this network manager. */
    public Socket socket; // CraftBukkit - private -> public
    /** The address of the socket used by this network manager. */
    private final SocketAddress i;
    /** The input stream connected to the socket. */
    private DataInputStream input;
    /** The output stream connected to the socket. */
    private DataOutputStream output;
    /** isRunning - Whether the network is currently operational. */
    private boolean l = true;
    /** readPackets - Linked list of packets that have been read and are awaiting processing. */
    private java.util.Queue m = new java.util.concurrent.ConcurrentLinkedQueue(); // CraftBukkit - Concurrent linked queue
    /** dataPackets - Linked list of packets awaiting sending. */
    private List highPriorityQueue = Collections.synchronizedList(new ArrayList());
    /** chunkDataPackets - Linked list of packets with chunk data that are awaiting sending. */
    private List lowPriorityQueue = Collections.synchronizedList(new ArrayList());
    /** A reference to the NetHandler object. */
    private NetHandler packetListener;
    /** Whether this server is currently terminating. If this is a client, this is always false. */
    private boolean q = false;
    /** writeThread - The thread used for writing. */
    private Thread r;
    /** readThread - The thread used for reading. */
    private Thread s;
    /** isTerminating - Whether this network manager is currently terminating (and should ignore further errors).*/
    private boolean t = false;
    /** terminationReason - A String indicating why the network has shutdown. */
    private String u = "";
    private Object[] v;
    /** timeSinceLastRead - Counter used to detect read timeouts after 1200 failed attempts to read a packet. */
    private int w = 0;
    /** sendQueueByteLength - The length in bytes of the packets in both send queues (data and chunkData). */
    private int x = 0;
    public static int[] d = new int[256];
    public static int[] e = new int[256];
    /** chunkDataSendCounter - Counter used to prevent us from sending too many chunk data packets one after another. The delay appears to be set to 50.*/
    public int f = 0;
    private int lowPriorityQueueDelay = 50;

    public NetworkManager(Socket socket, String s, NetHandler nethandler) {
        this.socket = socket;
        this.i = socket.getRemoteSocketAddress();
        this.packetListener = nethandler;

        // CraftBukkit start - IPv6 stack in Java on BSD/OSX doesn't support setTrafficClass
        try {
            socket.setTrafficClass(24);
        } catch (SocketException e) {}
        // CraftBukkit end

        try {
            // CraftBukkit start - cant compile these outside the try
            socket.setSoTimeout(30000);
            this.input = new DataInputStream(new java.io.BufferedInputStream(socket.getInputStream(), 2)); // Remove buffered input after 1.3
            this.output = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream(), 5120));
        } catch (java.io.IOException socketexception) {
            // CraftBukkit end
            System.err.println(socketexception.getMessage());
        }

        /* CraftBukkit start - moved up
        this.input = new DataInputStream(socket.getInputStream());
        this.output = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream(), 5120));
        // CraftBukkit end */
        this.s = new NetworkReaderThread(this, s + " read thread");
        this.r = new NetworkWriterThread(this, s + " write thread");
        this.s.start();
        this.r.start();
    }

    /** setNetHandler(...) - Sets the NetHandler for this NetworkManager. Server-only. */
    public void a(NetHandler nethandler) {
        this.packetListener = nethandler;
    }

    /** addToSendQueue(...) - Adds the packet to the correct send queue (chunk data packets go to a separate queue). */
    public void queue(Packet packet) {
        if (!this.q) {
            Object object = this.g;

            synchronized (this.g) {
                this.x += packet.a() + 1;
                if (packet.lowPriority) {
                    this.lowPriorityQueue.add(packet);
                } else {
                    this.highPriorityQueue.add(packet);
                }
            }
        }
    }

    /** sendPacket() - Sends a data packet if there is one to send, or sends a chunk data packet if there is one and the counter is up,
     * or does nothing. If it sends a packet, it sleeps for 10ms. */
    private boolean g() {
        boolean flag = false;

        try {
            Object object;
            Packet packet;
            int i;
            int[] aint;

            if (!this.highPriorityQueue.isEmpty() && (this.f == 0 || System.currentTimeMillis() - ((Packet) this.highPriorityQueue.get(0)).timestamp >= (long) this.f)) {
                object = this.g;
                synchronized (this.g) {
                    packet = (Packet) this.highPriorityQueue.remove(0);
                    this.x -= packet.a() + 1;
                }

                Packet.a(packet, this.output);
                aint = e;
                i = packet.b();
                aint[i] += packet.a() + 1;
                flag = true;
            }

            // CraftBukkit - don't allow low priority packet to be sent unless it was placed in the queue before the first packet on the high priority queue
            if ((flag || this.lowPriorityQueueDelay-- <= 0) && !this.lowPriorityQueue.isEmpty() && (this.highPriorityQueue.isEmpty() || ((Packet) this.highPriorityQueue.get(0)).timestamp > ((Packet) this.lowPriorityQueue.get(0)).timestamp)) {
                object = this.g;
                synchronized (this.g) {
                    packet = (Packet) this.lowPriorityQueue.remove(0);
                    this.x -= packet.a() + 1;
                }

                Packet.a(packet, this.output);
                aint = e;
                i = packet.b();
                aint[i] += packet.a() + 1;
                this.lowPriorityQueueDelay = 0;
                flag = true;
            }

            return flag;
        } catch (Exception exception) {
            if (!this.t) {
                this.a(exception);
            }

            return false;
        }
    }

    /** wakeThreads() - Wakes reader and writer threads */
    public void a() {
        this.s.interrupt();
        this.r.interrupt();
    }

    /** readPacket() - Reads a single packet from the input stream and adds it to the read queue.
     *  If no packet is read, it shuts down the network. */
    // BTCS: needs optimization. Please send pull requests with optimizations.
    private boolean h() {
        boolean flag = false;

        try {
            // CraftBukkit start - 1.3 detection
            this.input.mark(2);
            if (this.input.read() == 2 && this.input.read() != 0) {
                Packet.a(this.input, 16);
                Packet.a(this.input, 255);
                this.input.readInt();

                if (this.q) {
                    return true;
                }

                this.m.clear();
                this.m.add(new Packet2Handshake(null));
                return true;
            }
            this.input.reset();
            // CraftBukkit end
            Packet packet = Packet.a(this.input, this.packetListener.c());

            if (packet != null) {
                int[] aint = d;
                int i = packet.b();

                aint[i] += packet.a() + 1;
                if (!this.q) {
                    this.m.add(packet);
                }

                flag = true;
            } else {
                this.a("disconnect.endOfStream", new Object[0]);
            }

            return flag;
        } catch (Exception exception) {
            if (!this.t) {
                this.a(exception);
            }

            return false;
        }
    }

    /** onNetworkError(...) - Used to report network errors and causes a network shutdown */
    private void a(Exception exception) {
        // exception.printStackTrace(); // CraftBukkit - Remove console spam
        this.a("disconnect.genericReason", new Object[] { "Internal exception: " + exception.toString() + " - Please notify administrator"});
    }

    /** networkShutdown(...) - Shuts down the network with the specified reason. 
     * Closes all streams and sockets, spawns NetworkMasterThread to stop reading and writing threads.*/
    public void a(String s, Object... aobject) {
        if (this.l) {
            this.t = true;
            this.u = s;
            this.v = aobject;
            (new NetworkMasterThread(this)).start();
            this.l = false;

            try {
                this.input.close();
                this.input = null;
            } catch (Throwable throwable) {
                ;
            }

            try {
                this.output.close();
                this.output = null;
            } catch (Throwable throwable1) {
                ;
            }

            try {
                this.socket.close();
                this.socket = null;
            } catch (Throwable throwable2) {
                ;
            }
            
            // BTCS start
            ForgeHooks.onDisconnect(this, s, aobject);
            MessageManager.getInstance().removeConnection(this);
            // BTCS end
        }
    }

    /** processReadPackets() - Checks timeouts and processes all pending read packets */
    public void b() {
        if (this.x > 1048576) {
            this.a("disconnect.overflow", new Object[0]);
        }

        if (this.m.isEmpty()) {
            if (this.w++ == 1200) {
                this.a("disconnect.timeout", new Object[0]);
            }
        } else {
            this.w = 0;
        }

        int i = 1000;

        while (!this.m.isEmpty() && i-- >= 0) {
            Packet packet = (Packet) this.m.poll(); // CraftBukkit - remove -> poll

            if (!this.q) packet.handle(this.packetListener); // CraftBukkit
        }

        this.a();
        if (this.t && this.m.isEmpty()) {
            this.packetListener.a(this.u, this.v);
        }
    }

    /** getRemoteAddress() - Returns the socket address of the remote side. Server-only.*/
    public SocketAddress getSocketAddress() {
        return this.i;
    }

    /** serverShutdown() - Server-only method to shut down the network. */
    public void d() {
        if (!this.q) {
            this.a();
            this.q = true;
            this.s.interrupt();
            (new NetworkMonitorThread(this)).start();
        }
    }

    /** getNumChunkDataPackets() - Returns the number of chunk data packets waiting to be sent.*/
    public int e() {
        return this.lowPriorityQueue.size();
    }

    public Socket getSocket() {
        return this.socket;
    }

    /** isRunning(...) - Whether the network is operational.*/
    static boolean a(NetworkManager networkmanager) {
        return networkmanager.l;
    }

    /** isServerTerminating(...) - Is the server terminating? Client side aways returns false. */
    static boolean b(NetworkManager networkmanager) {
        return networkmanager.q;
    }

    /** readNetworkPacket(...) - Static accessor to readPacket. */
    static boolean c(NetworkManager networkmanager) {
        return networkmanager.h();
    }

    /** sendNetworkPacket(...) - Static accessor to sendPacket. */
    static boolean d(NetworkManager networkmanager) {
        return networkmanager.g();
    }

    static DataOutputStream e(NetworkManager networkmanager) {
        return networkmanager.output;
    }

    /** isTerminating(...) - Gets whether the Network manager is terminating. */
    static boolean f(NetworkManager networkmanager) {
        return networkmanager.t;
    }

    /** sendError(...) - Sends the network manager an error. */
    static void a(NetworkManager networkmanager, Exception exception) {
        networkmanager.a(exception);
    }

    /** getReadThread(...) - Returns the read thread. */
    static Thread g(NetworkManager networkmanager) {
        return networkmanager.s;
    }

    /** getWriteThread(...) - Returns the write thread.*/
    static Thread h(NetworkManager networkmanager) {
        return networkmanager.r;
    }

    // BTCS start
    public NetHandler getNetHandler() {
      return this.packetListener;
    }
    // BTCS end
}
