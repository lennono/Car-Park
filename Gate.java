import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class Gate
  implements Runnable
{
  private AtomicInteger display;
  private AtomicInteger spaces;
  private Semaphore availableGate;
  private Random ran;
  
  public Gate()
  {
    setRan(new Random()); // random number generator
  }
  
  public abstract void run();
  
  protected final void processDriver()
    throws InterruptedException
  {
    if (this.ran.nextInt(5) == 1) // 1 in 5 have complications at gate
    {
      if (this.ran.nextInt(3) == 1) // 1 in 3 have major complications at gate
      {
        Thread.sleep(ThreadLocalRandom.current().nextInt(100, 1100));
      } else {
        Thread.sleep(ThreadLocalRandom.current().nextInt(10, 110));
      }
    }
    else {
      Thread.sleep(ThreadLocalRandom.current().nextInt(1, 11));
    }
  }
  
  protected final AtomicInteger getDisplay()
  {
    return this.display;
  }
  
  protected final void setDisplay(AtomicInteger display)
  {
    this.display = display;
  }
  
  protected final AtomicInteger getSpaces()
  {
    return this.spaces;
  }
  
  protected final void setSpaces(AtomicInteger spaces)
  {
    this.spaces = spaces;
  }
  
  protected final Semaphore getAvailableGate()
  {
    return this.availableGate;
  }
  
  protected final void setAvailableGate(Semaphore availableGate)
  {
    this.availableGate = availableGate;
  }
  
  protected final Random getRan()
  {
    return this.ran;
  }
  
  private final void setRan(Random ran)
  {
    this.ran = ran;
  }
}