package ticketmastergui;

import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime;
import ch04.queues.*;
import support.LLNode;

public class TicketQueue implements Runnable
{
   private int numTickets = 25;
   private final int SLEEP_TIME = 10*1000;
   private int ticketsRequested;
   private LinkedQueue orderQueue;
   private TicketOrder order;
   private boolean doStop = false;
   
   // Resource:  http://tutorials.jenkov.com/java-concurrency/creating-and-starting-threads.html

   public TicketQueue(LinkedQueue orderQueue)
    {
      this.orderQueue = orderQueue;      
    }
    
    @Override
    public void run()
    {
        System.out.println("Initializing Tix Queue ");
        System.out.println(getAvailableTickets() + " tickets available for order!");
        
        while(keepRunning()) 
        {
          System.out.println(Thread.currentThread().getName() + " running"); 
        
          try 
          {
            Thread.sleep(SLEEP_TIME);
             checkQueue();
          } catch (InterruptedException e)
          { 
            e.printStackTrace();
          }
        } 
        
        System.out.println("Checking queue for remaining orders . . . "); 
        
        while (!orderQueue.isEmpty())
        {
            processOrder();
        }
        
        System.out.println(Thread.currentThread().getName() + " ended ");
        
    }
    
    public synchronized void doStop() 
    {
        this.doStop = true;
    }

    private synchronized boolean keepRunning() 
    {
        return this.doStop == false;
    }
    
    public void checkQueue()
    {
      if (orderQueue.isEmpty())
      {
         System.out.println("No orders to process");
      }   
      else
         {
            while (!orderQueue.isEmpty())
            {
               processOrder();
            }               
         }    
    }
    
    public int getAvailableTickets()
    {
      return numTickets;
    }
    
    public void processOrder()
    {
      String customerName;
      int    requestedQty;
      String orderID;
      order = (TicketOrder)orderQueue.dequeue();
      customerName = order.getCustomerName();
      requestedQty = order.getNumRequested();
      orderID = order.getOrderID();  
      
      if (requestedQty <= numTickets)
      {
         System.out.println("\nORDER PROCESSED");
         System.out.println("ORDER ID: " + orderID);
         System.out.println("Name: " + customerName);
         System.out.println("Quantity: " + requestedQty);
         LocalDateTime now = LocalDateTime.now();
         System.out.println("Processed: " + now.format(DateTimeFormatter.ofPattern("yyyy:MM:dd.HHmmss")));
         
         System.out.println();
         numTickets = numTickets - requestedQty;
         System.out.println(numTickets + " tickets remaining");      
      }  
      else
      {
         System.out.println("\n                 *** ORDER WAS NOT PROCESSED ***");
         System.out.println("THE NUMBER OF TICKETS REQUESTED EXCEEDS THE NUMBER OF TICKETS AVAILABLE.");
         System.out.println("ORDER ID: " + orderID);
         System.out.println("Name: " + customerName);
         System.out.println("Quantity: " + requestedQty);
         System.out.println();
         System.out.println(numTickets + " tickets remaining"); 
      }    
    }     
  }  

   
   
 