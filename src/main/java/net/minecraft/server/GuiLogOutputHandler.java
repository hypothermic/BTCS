package net.minecraft.server;

import java.util.logging.Formatter;
import java.util.logging.LogRecord;
import javax.swing.JTextArea;
import javax.swing.text.Document;

public class GuiLogOutputHandler extends java.util.logging.Handler
{
  private int[] b = new int['Ѐ'];
  private int c = 0;
  
  Formatter a = new GuiLogFormatter(this);
  












  private JTextArea d;
  












  public GuiLogOutputHandler(JTextArea paramJTextArea)
  {
    setFormatter(this.a);
    this.d = paramJTextArea;
  }
  

  public void close() {}
  
  public void flush() {}
  
  public void publish(LogRecord paramLogRecord)
  {
    int i = this.d.getDocument().getLength();
    this.d.append(this.a.format(paramLogRecord));
    this.d.setCaretPosition(this.d.getDocument().getLength());
    int j = this.d.getDocument().getLength() - i;
    
    if (this.b[this.c] != 0) {
      this.d.replaceRange("", 0, this.b[this.c]);
    }
    this.b[this.c] = j;
    this.c = ((this.c + 1) % 1024);
  }
}
