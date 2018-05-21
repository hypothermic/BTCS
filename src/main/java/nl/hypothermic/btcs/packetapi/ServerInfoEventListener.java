package nl.hypothermic.btcs.packetapi;

import nl.hypothermic.btcs.packetapi.EventListener.Data;
import nl.hypothermic.btcs.packetapi.ServerInfoEventListener.ServerInfoData;

public interface ServerInfoEventListener extends EventListener<ServerInfoData> {
	
	@Override ServerInfoData modify(ServerInfoData data);
	
	public static class ServerInfoData extends Data {
		public ServerInfoData(String motd, int playerCount, int maxPlayers) {
			this.motd = motd;
			this.playerCount = playerCount;
			this.maxPlayers = maxPlayers;
		}
		public String motd;
		public int playerCount;
		public int maxPlayers;
	}
}
