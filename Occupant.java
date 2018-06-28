import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

public class Occupant
  implements Runnable
{
  private Semaphore availableExit;
  private AtomicInteger display;
  private AtomicInteger spaces;
  private Random ran = new Random();
  private boolean isParked;
  private int spacesTaken;
  
  public Occupant(AtomicInteger display, AtomicInteger spaces, Semaphore availableExit)
  {
    this.availableExit = availableExit;
    this.display = display;
    this.spaces = spaces;
    this.isParked = false;
    this.spacesTaken = 0;
  }
  
  public void run()
  {
    try
    {
      if (this.ran.nextInt(10) == 1) // 1 in ten are bad drivers and take 2 spaces
      {
        this.spacesTaken = 2;
      } else {
        this.spacesTaken = 1;
      }
      for (int i = this.ran.nextInt(3) + 1; i <= 4; i++) // Users will retry from 1 to 4 times to find a space 
      {
        Thread.sleep(ThreadLocalRandom.current().nextInt(10, 200));
        if (this.spaces.get() >= this.spacesTaken)
        {
          if (this.spacesTaken >= 2)
          {
            this.spaces.addAndGet(Math.negateExact(this.spacesTaken)); // two spaces taken by bad parking
            this.display.decrementAndGet(); // No way for the display to know that
          }
          else
          {
            this.spaces.decrementAndGet();
          }
          this.isParked = true; // Space found
          break; // Exit as space found
        }
      }
      if (this.isParked) // if parked
      {
        Thread.sleep(ThreadLocalRandom.current().nextInt(100000, 1100000)); // excessive stay time to show that cars can come and go
      }
      this.availableExit.acquire();
      new Thread(new OutGate(this.display, this.spaces, this.availableExit, this.spacesTaken)).start();
    }
    catch (InterruptedException localInterruptedException) {}
  }
}