package net.minecraft.server;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;





public class ConsoleCommandHandler
{
  private static Logger a = Logger.getLogger("Minecraft");
  private MinecraftServer server;
  
  public ConsoleCommandHandler(MinecraftServer paramMinecraftServer)
  {
    this.server = paramMinecraftServer;
  }
  
  public synchronized void handle(ServerCommand paramServerCommand) {
    String str1 = paramServerCommand.command;
    String[] arrayOfString = str1.split(" ");
    String str2 = arrayOfString[0];
    String str3 = str1.substring(str2.length()).trim();
    ICommandListener localICommandListener = paramServerCommand.source;
    String str4 = localICommandListener.getName();
    

    ServerConfigurationManager localServerConfigurationManager = this.server.serverConfigurationManager;
    
    if ((str2.equalsIgnoreCase("help")) || (str2.equalsIgnoreCase("?"))) {
      a(localICommandListener);
    } else if (str2.equalsIgnoreCase("list")) {
      localICommandListener.sendMessage("Connected players: " + localServerConfigurationManager.c());
    } else if (str2.equalsIgnoreCase("stop")) {
      print(str4, "Stopping the server..");
      this.server.safeShutdown(); } else { int i;
      Object localObject2; 
      int k; 
      // BTCS start: temp commented until I figure this worldServer out.
      /*if (str2.equalsIgnoreCase("save-all")) {
        print(str4, "Forcing save..");
        if (localServerConfigurationManager != null) {
          localServerConfigurationManager.savePlayers();
        }
        for (i = 0; i < this.server.worldServer.length; i++) {
          localObject2 = this.server.worldServer[i];
          k = ((WorldServer)localObject2).savingDisabled;
          ((WorldServer)localObject2).savingDisabled = false;
          ((WorldServer)localObject2).save(true, null);
          ((WorldServer)localObject2).savingDisabled = k;
        }
        print(str4, "Save complete.");
      } else if (str2.equalsIgnoreCase("save-off")) {
        print(str4, "Disabling level saving..");
        for (i = 0; i < this.server.worldServer.length; i++) {
          localObject2 = this.server.worldServer[i];
          ((WorldServer)localObject2).savingDisabled = true;
        }
      }
      else if (str2.equalsIgnoreCase("save-on")) {
        print(str4, "Enabling level saving..");
        for (i = 0; i < this.server.worldServer.length; i++) {
          localObject2 = this.server.worldServer[i];
          ((WorldServer)localObject2).savingDisabled = false;
        } else */
      // BTCS end
      if (str2.equalsIgnoreCase("op")) {
        localServerConfigurationManager.addOp(str3);
        print(str4, "Opping " + str3);
        localServerConfigurationManager.a(str3, "§eYou are now op!"); } else { Object localObject1;
        if (str2.equalsIgnoreCase("deop")) {
          localObject1 = str3;
          localServerConfigurationManager.removeOp((String)localObject1);
          localServerConfigurationManager.a((String)localObject1, "§eYou are no longer op!");
          print(str4, "De-opping " + (String)localObject1);
        } else if (str2.equalsIgnoreCase("ban-ip")) {
          localObject1 = str3;
          localServerConfigurationManager.addIpBan((String)localObject1);
          print(str4, "Banning ip " + (String)localObject1);
        } else if (str2.equalsIgnoreCase("pardon-ip")) {
          localObject1 = str3;
          localServerConfigurationManager.removeIpBan((String)localObject1);
          print(str4, "Pardoning ip " + (String)localObject1);
        } else if (str2.equalsIgnoreCase("ban")) {
          localObject1 = str3;
          localServerConfigurationManager.addUserBan((String)localObject1);
          print(str4, "Banning " + (String)localObject1);
          
          localObject2 = localServerConfigurationManager.i((String)localObject1);
          
          if (localObject2 != null) {
            ((EntityPlayer)localObject2).netServerHandler.disconnect("Banned by admin");
          }
        } else if (str2.equalsIgnoreCase("pardon")) {
          localObject1 = str3;
          localServerConfigurationManager.removeUserBan((String)localObject1);
          print(str4, "Pardoning " + (String)localObject1);
        } else if (str2.equalsIgnoreCase("kick")) {
          localObject1 = str3;
          localObject2 = null;
          for (k = 0; k < localServerConfigurationManager.players.size(); k++) {
            EntityPlayer localEntityPlayer = (EntityPlayer)localServerConfigurationManager.players.get(k);
            if (localEntityPlayer.name.equalsIgnoreCase((String)localObject1)) {
              localObject2 = localEntityPlayer;
            }
          }
          
          if (localObject2 != null) {
            ((EntityPlayer)localObject2).netServerHandler.disconnect("Kicked by admin");
            print(str4, "Kicking " + ((EntityPlayer)localObject2).name);
          } else {
            localICommandListener.sendMessage("Can't find user " + (String)localObject1 + ". No kick.");
          }
          
        }
        else if (str2.equalsIgnoreCase("tp")) {
          if (arrayOfString.length == 3) {
            localObject1 = localServerConfigurationManager.i(arrayOfString[1]);
            localObject2 = localServerConfigurationManager.i(arrayOfString[2]);
            
            if (localObject1 == null) {
              localICommandListener.sendMessage("Can't find user " + arrayOfString[1] + ". No tp.");
            } else if (localObject2 == null) {
              localICommandListener.sendMessage("Can't find user " + arrayOfString[2] + ". No tp.");
            } else if (((EntityPlayer)localObject1).dimension != ((EntityPlayer)localObject2).dimension) {
              localICommandListener.sendMessage("User " + arrayOfString[1] + " and " + arrayOfString[2] + " are in different dimensions. No tp.");
            } else {
              ((EntityPlayer)localObject1).netServerHandler.a(((EntityPlayer)localObject2).locX, ((EntityPlayer)localObject2).locY, ((EntityPlayer)localObject2).locZ, ((EntityPlayer)localObject2).yaw, ((EntityPlayer)localObject2).pitch);
              print(str4, "Teleporting " + arrayOfString[1] + " to " + arrayOfString[2] + ".");
            }
          } else {
            localICommandListener.sendMessage("Syntax error, please provide a source and a target.");
          }
        } else if (str2.equalsIgnoreCase("give")) {
          if ((arrayOfString.length != 3) && (arrayOfString.length != 4) && (arrayOfString.length != 5)) {
            return;
          }
          
          localObject1 = arrayOfString[1];
          localObject2 = localServerConfigurationManager.i((String)localObject1);
          
          if (localObject2 != null) {
            try {
              int m = Integer.parseInt(arrayOfString[2]);
              if (Item.byId[m] != null) {
                print(str4, "Giving " + ((EntityPlayer)localObject2).name + " some " + m);
                int i3 = 1;
                int i4 = 0;
                if (arrayOfString.length > 3) i3 = a(arrayOfString[3], 1);
                if (arrayOfString.length > 4) i4 = a(arrayOfString[4], 1);
                if (i3 < 1) i3 = 1;
                if (i3 > 64) i3 = 64;
                ((EntityPlayer)localObject2).drop(new ItemStack(m, i3, i4));
              } else {
                localICommandListener.sendMessage("There's no item with id " + m);
              }
            } catch (NumberFormatException localNumberFormatException2) {
              localICommandListener.sendMessage("There's no item with id " + arrayOfString[2]);
            }
          } else {
            localICommandListener.sendMessage("Can't find user " + (String)localObject1);
          }
        } else if (str2.equalsIgnoreCase("xp")) {
          if (arrayOfString.length != 3) {
            return;
          }
          
          localObject1 = arrayOfString[1];
          localObject2 = localServerConfigurationManager.i((String)localObject1);
          
          if (localObject2 != null) {
            try {
              int n = Integer.parseInt(arrayOfString[2]);
              n = n > 5000 ? 5000 : n;
              print(str4, "Giving " + n + " orbs to " + ((EntityPlayer)localObject2).name);
              ((EntityPlayer)localObject2).giveExp(n);
            } catch (NumberFormatException localNumberFormatException3) {
              localICommandListener.sendMessage("Invalid orb count: " + arrayOfString[2]);
            }
          } else {
            localICommandListener.sendMessage("Can't find user " + (String)localObject1);
          }
        } else if (str2.equalsIgnoreCase("gamemode")) {
          if (arrayOfString.length != 3) {
            return;
          }
          
          localObject1 = arrayOfString[1];
          localObject2 = localServerConfigurationManager.i((String)localObject1);
          
          if (localObject2 != null) {
            try {
              int i1 = Integer.parseInt(arrayOfString[2]);
              i1 = WorldSettings.a(i1);
              if (((EntityPlayer)localObject2).itemInWorldManager.getGameMode() != i1) {
                print(str4, "Setting " + ((EntityPlayer)localObject2).name + " to game mode " + i1);
                ((EntityPlayer)localObject2).itemInWorldManager.setGameMode(i1);
                ((EntityPlayer)localObject2).netServerHandler.sendPacket(new Packet70Bed(3, i1));
              } else {
                print(str4, ((EntityPlayer)localObject2).name + " already has game mode " + i1);
              }
            } catch (NumberFormatException localNumberFormatException4) {
              localICommandListener.sendMessage("There's no game mode with id " + arrayOfString[2]);
            }
          } else {
            localICommandListener.sendMessage("Can't find user " + (String)localObject1);
          }
        } else if (str2.equalsIgnoreCase("time")) {
          if (arrayOfString.length != 3) {
            return;
          }
          
          localObject1 = arrayOfString[1];
          try
          {
            int j = Integer.parseInt(arrayOfString[2]);
            int i2; WorldServer localWorldServer; if ("add".equalsIgnoreCase((String)localObject1)) {
              for (i2 = 0; i2 < this.server.worlds.size(); i2++) { // BTCS: this.server.worldServer.length --> this.server.world.size()
                localWorldServer = this.server.worlds.get(i2); // BTCS: modified this a bit
                localWorldServer.setTimeAndFixTicklists(localWorldServer.getTime() + j);
              }
              print(str4, "Added " + j + " to time");
            } else if ("set".equalsIgnoreCase((String)localObject1)) {
              for (i2 = 0; i2 < this.server.worlds.size(); i2++) { // BTCS: same thing as above
                localWorldServer = this.server.worlds.get(i2); // BTCS
                localWorldServer.setTimeAndFixTicklists(j);
              }
              print(str4, "Set time to " + j);
            } else {
              localICommandListener.sendMessage("Unknown method, use either \"add\" or \"set\"");
            }
          } catch (NumberFormatException localNumberFormatException1) {
            localICommandListener.sendMessage("Unable to convert time value, " + arrayOfString[2]);
          }
        } else if ((str2.equalsIgnoreCase("say")) && (str3.length() > 0)) {
          a.info("[" + str4 + "] " + str3);
          localServerConfigurationManager.sendAll(new Packet3Chat("§d[Server] " + str3));
        }
        else if (str2.equalsIgnoreCase("tell")) {
          if (arrayOfString.length >= 3)
          {
            str1 = str1.substring(str1.indexOf(" ")).trim();
            str1 = str1.substring(str1.indexOf(" ")).trim();
            
            a.info("[" + str4 + "->" + arrayOfString[1] + "] " + str1);
            str1 = "§7" + str4 + " whispers " + str1;
            a.info(str1);
            if (!localServerConfigurationManager.a(arrayOfString[1], new Packet3Chat(str1)))
            {
              localICommandListener.sendMessage("There's no player by that name online.");
            }
          }
        } else if (str2.equalsIgnoreCase("whitelist")) {
          a(str4, str1, localICommandListener);
        } else if (str2.equalsIgnoreCase("toggledownfall")) {
          this.server.worlds.get(0).j();
          localICommandListener.sendMessage("Toggling rain and snow, hold on...");
        } else if (str2.equalsIgnoreCase("banlist")) {
          if (arrayOfString.length == 2) {
            if (arrayOfString[1].equals("ips")) {
              localICommandListener.sendMessage("IP Ban list:" + a(this.server.q(), ", "));
            }
          } else {
            localICommandListener.sendMessage("Ban list:" + a(this.server.r(), ", "));
          }
        } else {
          a.info("Unknown console command. Type \"help\" for help.");
        }
      }
    }
  }
  
  private void a(String paramString1, String paramString2, ICommandListener paramICommandListener) { String[] arrayOfString = paramString2.split(" ");
    
    if (arrayOfString.length < 2) {
      return;
    }
    
    String str1 = arrayOfString[1].toLowerCase();
    
    if ("on".equals(str1)) {
      print(paramString1, "Turned on white-listing");
      this.server.propertyManager.setBoolean("white-list", true);
    } else if ("off".equals(str1)) {
      print(paramString1, "Turned off white-listing");
      print(paramString1, "BTCS Warning: whitelisting may not work yet!");
      this.server.propertyManager.setBoolean("white-list", false); } else { Object localObject;
      if ("list".equals(str1)) {
        Set clocalObject = this.server.serverConfigurationManager.getWhitelisted();
        String str2 = "";
        // BTCS start: TODO!
        /*for (String str3 : clocalObject) {
          str2 = str2 + str3 + " ";
        }*/
        String str4;
        for (Iterator iterator = clocalObject.iterator(); iterator.hasNext(); str2 = str2 + str4 + " ") {
            str4 = (String) iterator.next();
        }
        // BTCS end
        paramICommandListener.sendMessage("White-listed players: " + str2);
      } else if (("add".equals(str1)) && (arrayOfString.length == 3)) {
        localObject = arrayOfString[2].toLowerCase();
        this.server.serverConfigurationManager.addWhitelist((String)localObject);
        print(paramString1, "Added " + (String)localObject + " to white-list");
      } else if (("remove".equals(str1)) && (arrayOfString.length == 3)) {
        localObject = arrayOfString[2].toLowerCase();
        this.server.serverConfigurationManager.removeWhitelist((String)localObject);
        print(paramString1, "Removed " + (String)localObject + " from white-list");
      } else if ("reload".equals(str1)) {
        this.server.serverConfigurationManager.reloadWhitelist();
        print(paramString1, "Reloaded white-list from file");
      }
    }
  }
  
  private void a(ICommandListener paramICommandListener) { paramICommandListener.sendMessage("To run the server without a gui, start it like this:");
    paramICommandListener.sendMessage("   java -Xmx1024M -Xms1024M -jar minecraft_server.jar nogui");
    paramICommandListener.sendMessage("Console commands:");
    paramICommandListener.sendMessage("   help  or  ?               shows this message");
    paramICommandListener.sendMessage("   kick <player>             removes a player from the server");
    paramICommandListener.sendMessage("   ban <player>              bans a player from the server");
    paramICommandListener.sendMessage("   pardon <player>           pardons a banned player so that they can connect again");
    paramICommandListener.sendMessage("   ban-ip <ip>               bans an IP address from the server");
    paramICommandListener.sendMessage("   pardon-ip <ip>            pardons a banned IP address so that they can connect again");
    paramICommandListener.sendMessage("   op <player>               turns a player into an op");
    paramICommandListener.sendMessage("   deop <player>             removes op status from a player");
    paramICommandListener.sendMessage("   tp <player1> <player2>    moves one player to the same location as another player");
    paramICommandListener.sendMessage("   give <player> <id> [num]  gives a player a resource");
    paramICommandListener.sendMessage("   tell <player> <message>   sends a private message to a player");
    paramICommandListener.sendMessage("   stop                      gracefully stops the server");
    paramICommandListener.sendMessage("   save-all                  forces a server-wide level save");
    paramICommandListener.sendMessage("   save-off                  disables terrain saving (useful for backup scripts)");
    paramICommandListener.sendMessage("   save-on                   re-enables terrain saving");
    paramICommandListener.sendMessage("   list                      lists all currently connected players");
    paramICommandListener.sendMessage("   say <message>             broadcasts a message to all players");
    paramICommandListener.sendMessage("   time <add|set> <amount>   adds to or sets the world time (0-24000)");
    paramICommandListener.sendMessage("   gamemode <player> <mode>  sets player's game mode (0 or 1)");
    paramICommandListener.sendMessage("   toggledownfall            toggles rain on or off");
    paramICommandListener.sendMessage("   xp <player> <amount>      gives the player the amount of xp (0-5000)");
  }
  
  private void print(String paramString1, String paramString2) {
    String str = paramString1 + ": " + paramString2;
    this.server.serverConfigurationManager.j("§7(" + str + ")");
    a.info(str);
  }
  
  private int a(String paramString, int paramInt) {
    try {
      return Integer.parseInt(paramString);
    } catch (NumberFormatException localNumberFormatException) {}
    return paramInt;
  }
  
  private String a(String[] paramArrayOfString, String paramString)
  {
    int i = paramArrayOfString.length;
    if (0 == i) {
      return "";
    }
    
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(paramArrayOfString[0]);
    for (int j = 1; j < i; j++) {
      localStringBuilder.append(paramString).append(paramArrayOfString[j]);
    }
    return localStringBuilder.toString();
  }
}
