import javax.swing.JOptionPane; 
import ch04.queues.*;
import support.LLNode;

/**
*  TicketSalesSimulation simulates a ticket-sales program.  Users are
*  prompted to input their name and the number of tickets they wish to
*  purchase and creates an order queue.
*/

public class TicketSalesSimulation
{
   public static void main(String[] args)
   {
      String[] splitted = new String[2];
      
      LinkedQueue orderQueue = new LinkedQueue();
      
      TicketQueue tq = new TicketQueue(orderQueue);
      Thread t = new Thread(tq);
      t.setName("Ticket Queue");
      t.start();
      
      do 
      {
         String input = JOptionPane.showInputDialog("Enter your name and number of tickets you'd like to purchase." +
                                                    "you'd like to purchase.\n" +
                                                    "Separate your name from the ticket number with a " +
                                                      "colon like this:  James:4\n" +
                                                      "Type STOP to stop program.");
         System.out.println(input);
         if (input.equals("STOP"))
         {
            splitted[0] = "STOP";
            splitted[1] = "0";
         }
         else
         {
            splitted = input.split(":", 2);
         
            int numRequested = Integer.parseInt(splitted[1]);
            
            TicketOrder tixOrder = new TicketOrder(splitted[0], numRequested);
            
            orderQueue.enqueue(tixOrder);
         
            System.out.println(input);
            System.out.println(numRequested);
          }
      }
      while(splitted[0] != "STOP");
      tq.doStop();     
   }
   
}
