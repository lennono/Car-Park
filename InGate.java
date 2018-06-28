import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

public class InGate
  extends Gate
{
  public InGate(AtomicInteger display, AtomicInteger spaces, Semaphore availableExit)
  {
    setDisplay(display);
    setSpaces(spaces);
    setAvailableGate(availableExit);
  }
  
  public void run()
  {
    try
    {
      for (;;) // Run forever, carpark never closes
      {
        processDriver();
        
        getDisplay().decrementAndGet();
        new Thread(new Occupant(getDisplay(), getSpaces(), getAvailableGate())).start(); // Create new occupant
        
        Thread.sleep(ThreadLocalRandom.current().nextInt(100, 1100)); //  wait for new driver to use gate
      }
    }
    catch (InterruptedException localInterruptedException) {}
  }
}