package net.minecraft.server;

public class ServerCommand {
  public final String command;
  public final ICommandListener source;
  
  public ServerCommand(String paramString, ICommandListener paramICommandListener) {
    this.command = paramString;
    this.source = paramICommandListener;
  }
}
