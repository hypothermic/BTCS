package net.minecraft.server;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JTextField;
class ServerGuiCommandListener implements ActionListener
{
	// BTCS start
	final JTextField a;
	final ServerGUI b;
	
  ServerGuiCommandListener(ServerGUI sg, JTextField tf) {
	  this.b = sg;
	  this.a = tf;
  }
  // BTCS end
  
  public void actionPerformed(ActionEvent paramActionEvent)
  {
    String str = this.a.getText().trim();
    if (str.length() > 0) {
      ServerGUI.a(this.b).issueCommand(str, this.b);
    }
    this.a.setText("");
  }
}
