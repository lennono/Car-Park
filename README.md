# Car-Park

## Description 
The imagined car park is much like the DCU multi-story car park, with the in gates beside one another and the out gates beside one another. No idea of a users before the ingates. There is only 1 queue for the gates with user at the front of the queue going through the free gate. Users may have issues at the gates holding up that gate. 
 
In gates create occupants, which are occupants of the car park. In gates after incrementing the count for the monitor and after creating an occupant, sleep for a period of time, imitating a gate waiting for a new user to activate the gate. Occupants who have passed the gate look for a space in the car park. If a spot is unavailable they the user tries to look for a spot in the car park again, up to 4 times. If a spot was available the user parks and goes about their business. 
 
Once the occupant wants to leave they queue with other occupants(if any) to leave the car park through the out gates, there is one queue like the in gates.  
 
* We didn’t see any differences between Lectures and student in a car park other than stay time, so we left it at occupant. 
 
 
## Technical walkthrough 
Ingate threads decrement the AtomicIntegers passed which are for the display on the monitor and for the actual spaces left in the car park. In gate also creates an occupant thread after a wait period which imitates the time taken to drive through the gate. After which the in gate waits. 
 
The occupant thread has a for loop which imitates a driver looking for a space a number of times. Once a user finds a space the thread waits for a period. If a driver can not find a space they exit the car park. The semaphores are used to control access to the outgates for the occupants, with occupants needing to acquire a semaphore before proceeding out of the car park. The semaphore is released in the outgate thread, Semaphores in Java don’t have to call release in the same thread as acquire. 
 
The outgate is very straightforward, increase the AtomicIntegers for the spaces and wait for a small amount of time, sometimes longer for a mishap at the gate. Release the Semaphore once the driver is clear of the gate. 
 
The monitor thread is running continuously(busy-wait), updating the monitor with the AtomicInteger for spaces. Any changes are picked up quickly. Only interacts with the monitor, would be the front end. 
 
The Gate thread abstracts out the things that were common to both InGate and OutGate, like processing a driver at the gates and incrementing and decrementing the AtomicIntegers. 
 
 
## Fairness 
The program mostly relies on AtomicIntegers, which means the threads need not wait on one another until contention for the outgates Using semaphores to restrict access to the out gates, ​the Semaphore is set to be fair meaning FIFO processing for the outgates.   
 
## Starvation 
No issues with starvation, as every thread will eventually have a turn at the outgates and the other threads need not wait on one another. 
 
## Prevention 
I tried as close as we could to implement a non blocking algorithm using AtomicIntegers, there are no issues with blocking until the outgates, where a deadlock can’t happen. 
 
 
**How to Run** 
1. javac *.java 
2. java CarPark 
