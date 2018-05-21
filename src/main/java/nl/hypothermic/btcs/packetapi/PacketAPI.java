package nl.hypothermic.btcs.packetapi;

import java.util.ArrayList;

import nl.hypothermic.btcs.packetapi.ServerInfoEventListener.ServerInfoData;

public class PacketAPI {
	
	/**
	 * The main PacketAPI instance.
	 */
	public static final PacketAPI instance = new PacketAPI();
	
	/*
	 * All of the active listeners
	 */
	private static ArrayList<EventListener> listeners = new ArrayList<EventListener>();
	
	/**
	 * WARNING: do NOT use this constructor, use PacketAPI.instance.*
	 */
	public PacketAPI() {
		;
	}
	
	/**
	 * Add an event listener
	 */
	public void addListener(EventListener listener) {
		listeners.add(listener);
	}
	
	public static class Announcer {
		
		public Announcer() {
			
		}
		
		public static ServerInfoData onServerInfoEvent(String motd, int playerCount, int maxPlayers) {
			ServerInfoData tmp = new ServerInfoData(motd, playerCount, maxPlayers);
			for (EventListener listener : listeners) {
				if (listener instanceof ServerInfoEventListener) {
					tmp = (ServerInfoData) listener.modify(tmp);
				}
			}
			return tmp;
		}
	}
}
