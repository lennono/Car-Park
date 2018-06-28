import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

class CarPark
{
  public static void main(String[] args)
  {
    AtomicInteger display = new AtomicInteger(1000); // Display spaces
    AtomicInteger spaces = new AtomicInteger(1000); // Actually spaces included for bad drivers
    
    Semaphore availableExit = new Semaphore(3, true); // To restrict access to the outgates
    ExecutorService entrances = Executors.newFixedThreadPool(3); // In gates
    ExecutorService observatory = Executors.newSingleThreadExecutor(); // Monitor
    for (int i = 0; i < 3; i++) {
      entrances.execute(new InGate(display, spaces, availableExit));
    }
    observatory.execute(new Monitor(display));
  }
}