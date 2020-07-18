import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime;
import ch04.queues.*;
import support.LLNode;

public class TicketQueue implements Runnable
{
   private int numTickets = 10;
   private final int SLEEP_TIME = 10*1000;
   private int ticketsRequested;
   private LinkedQueue orderQueue;
   private TicketOrder order;
   
   public TicketQueue(LinkedQueue orderQueue)
    {
      this.orderQueue = orderQueue;
      run();
    }
    
    @Override
    public void run()
    {
        Thread t = Thread.currentThread();
        //TicketOrder order = new TicketOrder();
        
        System.out.println("Initializing Tix Queue ");
        System.out.println(getAvailableTickets() + " tickets available for order!");
        try {
            Thread.sleep(SLEEP_TIME);
        } catch (InterruptedException e) { 
            e.printStackTrace();
        }
        //LinkedQueue orderList = order.getOrderQueue();
        checkQueue();
        System.out.println("Tix Thread ended ");
        
    }
    
    public void checkQueue()
    {
      if (orderQueue.isEmpty())
         System.out.println("No orders to process");
      else
      {
          processOrder();            
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
         System.out.println("ORDER PROCESSED");
         System.out.println("ORDER ID: " + orderID);
         System.out.println("Name: " + customerName);
         System.out.println("Quantity: " + requestedQty);
         LocalDateTime now = LocalDateTime.now();
         //now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))
         System.out.println("Processed: " + now.format(DateTimeFormatter.ofPattern("yyyy:MM:dd.HHmmss")));
         
         System.out.println();
         numTickets = numTickets - requestedQty;
         System.out.println(numTickets + " tickets remaining");         
         
      }      
    }

    
  }  

   
   //public TicketQueue (LinkedQueueExtension<TicketOrder> tix)
   //{
    //  this.tix = tix;
   //}
   
   //public void run()
   //{
   
   /**
   Methods:
   
      public void orderDequeue()
   // include checkQuantity in this method (similar to remove() method)
   //write unique error message "You requested more tickets than are available"
   // Throws QueueUnderflowException if this queue is empty; throws
   // otherwise, removes front element from this queue and returns it.
  {
    if (isEmpty())
      throw new QueueUnderflowException("Dequeue attempted on empty queue.");
    else if (numTickets < ticketsRequested)
    {
      
    }
    else
    {
      T element;
      element = front.getInfo();
      front = front.getLink();
      if (front == null)
        rear = null;
      numElements--;
      return element;
    }
  }


   
   public boolean checkQuantity()
   //checks if enough tickets remain
   
   public int ticketsRemaining()
   //returns # tickets remaining
   
   toString()
   //
   

   }
 */  
 