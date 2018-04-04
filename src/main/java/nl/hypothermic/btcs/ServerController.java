package nl.hypothermic.btcs;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.registry.IMinecraftRegistry;
import cpw.mods.fml.server.FMLBukkitHandler;
import net.minecraft.server.MinecraftServer;

/** 
 * ServerController.java <br>
 * API for interacting with the BTCS server
 */

public class ServerController {
	
	//-------------------------------------------------------------------------------------------------------
	
	/** The main instance of the ServerController is stored here. */
	private static final ServerController INSTANCE = new ServerController();
	private static Boolean enabled;
	
	/** Returns the main ServerController instance if the ServerController has been enabled by the server administrator. */
	public static ServerController instance() {
		if (enabled != null && enabled == false) {
			// Access restricted
			throw new RuntimeException("ServerController is disabled by server administrator.");
		}
	    return INSTANCE;
	}
	
	/** Enable or disable the usage of ServerController */
	public void setEnabled(boolean value) {
		if (this.enabled != null) {
			throw new RuntimeException("ServerController: Illegal attempt to replace enabled status.");
		}
		this.enabled = value;
	}
	
	//-------------------------------------------------------------------------------------------------------
	
	private static MinecraftServer mcserver;
	private static IMinecraftRegistry fmlregistry;
	private static FMLCommonHandler fmlch;
	private static FMLBukkitHandler fmlbh;
	
	/** Set the MinecraftServer instance. */
	public void setMinecraftInstance(MinecraftServer mcserver){
		if (this.mcserver != null) {
			throw new RuntimeException("ServerController: Illegal attempt to replace the MinecraftServer instance");
		}
	    this.mcserver = mcserver;
    }

	/** Get the MinecraftServer instance. */
	public MinecraftServer getMinecraftInstance() {
	    return this.mcserver;
    }
	
	/** Set the Bukkit Registry used by the server. */
	public void setFMLRegistry(IMinecraftRegistry fmlregistry){
		if (this.fmlregistry != null) {
			throw new RuntimeException("ServerController: Illegal attempt to replace the Bukkit Registry");
		}
	    this.fmlregistry = fmlregistry;
    }

	/** Get the Bukkit Registry used by the server. */
	public IMinecraftRegistry getFMLRegistry() {
	    return this.fmlregistry;
    }
	
	/** Set the FMLCommonHandler used by the server. */
	public void setFMLCommonHandler(FMLCommonHandler fmlch){
		if (this.fmlch != null) {
			throw new RuntimeException("ServerController: Illegal attempt to replace the FMLCommonHandler");
		}
	    this.fmlch = fmlch;
    }

	/** Get the FMLCommonHandler used by the server. */
	public FMLCommonHandler getFMLCommonHandler() {
	    return this.fmlch;
    }
	
	/** Set the FMLBukkitHandler used by the server. */
	public void setFMLBukkitHandler(FMLBukkitHandler fmlbh){
		if (this.fmlbh != null) {
			throw new RuntimeException("ServerController: Illegal attempt to replace the FMLBukkitHandler");
		}
	    this.fmlbh = fmlbh;
    }

	/** Get the FMLBukkitHandler used by the server. */
	public FMLBukkitHandler getFMLBukkitHandler() {
	    return this.fmlbh;
    }
	
	//-------------------------------------------------------------------------------------------------------
}
