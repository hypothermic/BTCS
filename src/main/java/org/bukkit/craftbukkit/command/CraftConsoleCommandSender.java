package org.bukkit.craftbukkit.command;

import java.io.PrintStream;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.conversations.Conversation;
import org.bukkit.conversations.ConversationAbandonedEvent;
import org.bukkit.conversations.ManuallyAbandonedConversationCanceller;
import org.bukkit.craftbukkit.conversations.ConversationTracker;

public class CraftConsoleCommandSender
  extends ServerCommandSender
  implements ConsoleCommandSender
{
  protected ConversationTracker conversationTracker = new ConversationTracker();
  



  public void sendMessage(String message)
  {
    sendRawMessage(message);
  }
  
  public void sendRawMessage(String message) {
    System.out.println(ChatColor.stripColor(message));
  }
  
  public void sendMessage(String[] messages) {
    for (String message : messages) {
      sendMessage(message);
    }
  }
  
  public String getName() {
    return "CONSOLE";
  }
  
  public boolean isOp() {
    return true;
  }
  
  public void setOp(boolean value) {
    throw new UnsupportedOperationException("Cannot change operator status of server console");
  }
  
  public boolean beginConversation(Conversation conversation) {
    return this.conversationTracker.beginConversation(conversation);
  }
  
  public void abandonConversation(Conversation conversation) {
    this.conversationTracker.abandonConversation(conversation, new ConversationAbandonedEvent(conversation, new ManuallyAbandonedConversationCanceller()));
  }
  
  public void abandonConversation(Conversation conversation, ConversationAbandonedEvent details) {
    this.conversationTracker.abandonConversation(conversation, details);
  }
  
  public void acceptConversationInput(String input) {
    this.conversationTracker.acceptConversationInput(input);
  }
  
  public boolean isConversing() {
    return this.conversationTracker.isConversing();
  }
}
