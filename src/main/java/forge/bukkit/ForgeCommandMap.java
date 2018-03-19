package forge.bukkit;

import cpw.mods.fml.server.FMLBukkitHandler;
import forge.ForgeHooks;
import java.util.logging.Logger;
import net.minecraft.server.EntityPlayer;
import net.minecraft.server.ICommandListener;
import net.minecraft.server.MinecraftServer;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.entity.CraftPlayer;

public class ForgeCommandMap extends SimpleCommandMap
{
  private MinecraftServer mcServer;
  
  public ForgeCommandMap(Server server, MinecraftServer mcServer)
  {
    super(server);
    this.mcServer = mcServer;
    register("forge", new ModTimingCommand("modtiming"));
  }
  
  public boolean dispatch(CommandSender sender, String commandLine) throws CommandException {
    ICommandListener listener = null;
    EntityPlayer player = null;
    if (sender.equals(this.mcServer.server.getConsoleSender())) {
      listener = this.mcServer;
    } else {
      try {
        player = ((CraftPlayer)sender).getHandle();
        listener = player.netServerHandler;
      }
      catch (Exception ex) {}
    }
    
    if ((player != null) && (ForgeHooks.onChatCommand(player, sender.hasPermission("forge.chatCommands"), commandLine)))
    {
      MinecraftServer.log.info("Forge: " + sender.getName() + " issues command: " + commandLine);
      return true;
    }
    
    if ((commandLine.startsWith("say ")) && (commandLine.length() > 4)) {
      String msg = ForgeHooks.onServerCommandSay(listener, sender.getName(), commandLine.substring(4));
      if (msg == null) {
        return true;
      }
      commandLine = "say " + msg;
    }
    
    if (super.dispatch(sender, commandLine)) {
      return true;
    }
    
    if ((sender.hasPermission("fml.serverCommands")) && (FMLBukkitHandler.instance().handleServerCommand(commandLine, sender.getName(), listener)))
      return true;
    if ((sender.hasPermission("forge.serverCommands")) && (ForgeHooks.onServerCommand(listener, sender.getName(), commandLine))) {
      return true;
    }
    return false;
  }
  

  public Command getCommand(String name)
  {
    return super.getCommand(name);
  }
}
