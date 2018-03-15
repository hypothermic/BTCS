package net.minecraft.server;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class GuiStatsListener implements ActionListener
{
  // BTCS start
  final GuiStatsComponent a;
	
  GuiStatsListener(GuiStatsComponent gsc) {
	  this.a = gsc;
  }
  // BTCS end
  
  public void actionPerformed(ActionEvent paramActionEvent)
  {
    GuiStatsComponent.a(this.a);
  }
}
