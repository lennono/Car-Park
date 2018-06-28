import java.awt.Container;
import java.awt.Font;
import java.util.concurrent.atomic.AtomicInteger;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Monitor
  implements Runnable
{
  AtomicInteger spaces;
  
  public Monitor(AtomicInteger spaces)
  {
    this.spaces = spaces;
  }
  
  public void run()
  {
    JFrame myFrame = new JFrame("Monitor");
    myFrame.setVisible(true);
    myFrame.setBounds(300, 200, 700, 400);
    
    JLabel spacesLeft = new JLabel(" ", 0);
    spacesLeft.setFont(new Font("TimesRoman", 0, 20));
    
    myFrame.getContentPane().add(spacesLeft, "Center");
    for (;;)
    {
      if (this.spaces.get() <= 0) {
        spacesLeft.setText("No Lots available"); // change when full to signal to drivers there are no spaces
      } else {
        spacesLeft.setText("Lots available: " + String.valueOf(this.spaces));
      }
    }
  }
}