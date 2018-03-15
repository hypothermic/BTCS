package net.minecraft.server;

import java.awt.BorderLayout;
import java.util.logging.Logger;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;

public class ServerGUI extends JComponent implements ICommandListener
{
  public static Logger a = Logger.getLogger("Minecraft");
  private MinecraftServer b;
  
  public static void a(MinecraftServer paramMinecraftServer)
  {
    try
    {
      javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
    }
    catch (Exception localException) {}
    
    ServerGUI localServerGUI = new ServerGUI(paramMinecraftServer);
    JFrame localJFrame = new JFrame("Minecraft server");
    localJFrame.add(localServerGUI);
    localJFrame.pack();
    localJFrame.setLocationRelativeTo(null);
    localJFrame.setVisible(true);
    localJFrame.addWindowListener(new ServerWindowAdapter(paramMinecraftServer));
  }

  public ServerGUI(MinecraftServer paramMinecraftServer)
  {
    this.b = paramMinecraftServer;
    setPreferredSize(new java.awt.Dimension(854, 480));
    
    setLayout(new BorderLayout());
    try {
      add(c(), "Center");
      add(a(), "West");
    } catch (Exception localException) {
      localException.printStackTrace();
    }
  }
  
  private JComponent a() {
    JPanel localJPanel = new JPanel(new BorderLayout());
    localJPanel.add(new GuiStatsComponent(this.b), "North");
    localJPanel.add(b(), "Center");
    localJPanel.setBorder(new javax.swing.border.TitledBorder(new EtchedBorder(), "Stats"));
    return localJPanel;
  }
  
  private JComponent b() {
    PlayerListBox localPlayerListBox = new PlayerListBox(this.b);
    JScrollPane localJScrollPane = new JScrollPane(localPlayerListBox, 22, 30);
    localJScrollPane.setBorder(new javax.swing.border.TitledBorder(new EtchedBorder(), "Players"));
    
    return localJScrollPane;
  }
  
  private JComponent c() {
    JPanel localJPanel = new JPanel(new BorderLayout());
    JTextArea localJTextArea = new JTextArea();
    a.addHandler(new GuiLogOutputHandler(localJTextArea));
    JScrollPane localJScrollPane = new JScrollPane(localJTextArea, 22, 30);
    localJTextArea.setEditable(false);
    
    JTextField localJTextField = new JTextField();
    localJTextField.addActionListener(new ServerGuiCommandListener(this, localJTextField));
    localJTextArea.addFocusListener(new ServerGuiFocusAdapter(this));
    localJPanel.add(localJScrollPane, "Center");
    localJPanel.add(localJTextField, "South");
    localJPanel.setBorder(new javax.swing.border.TitledBorder(new EtchedBorder(), "Log and chat"));
    
    return localJPanel;
  }
  
  public void sendMessage(String paramString) {
    a.info(paramString);
  }
  public String getName()
  {
    return "CONSOLE";
  }
  static MinecraftServer a(ServerGUI servergui) {
      return servergui.b;
  }
}
