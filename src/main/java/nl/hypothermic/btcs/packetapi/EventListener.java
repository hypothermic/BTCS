package nl.hypothermic.btcs.packetapi;

import nl.hypothermic.btcs.packetapi.EventListener.Data;

public interface EventListener<T extends Data> {
	
	T modify(T data);
	
	public static class Data {
		
	}
}
