import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

public class OutGate
  extends Gate
{
  private int spacesTaken;
  
  public OutGate(AtomicInteger display, AtomicInteger spaces, Semaphore availableExit, int spacesTaken)
  {
    setDisplay(display);
    setSpaces(spaces);
    setAvailableGate(availableExit);
    this.spacesTaken = spacesTaken;
  }
  
  public void run()
  {
    try
    {
      processDriver();
      
      getDisplay().addAndGet(this.spacesTaken);
      getSpaces().addAndGet(this.spacesTaken);
      
      getAvailableGate().release(); // User has passed through the gate and another user can proceed
    }
    catch (InterruptedException localInterruptedException) {}
  }
}