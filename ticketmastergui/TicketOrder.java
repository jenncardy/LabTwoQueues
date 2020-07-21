package ticketmastergui;

import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime; 
import java.util.Random; 
import ch04.queues.*;
import support.LLNode;

public class TicketOrder
{
   private String customerName;
   private int numRequested;
   private String orderID;
   
   private final int RANDOM_MAX99 = 100;
   private final int RANDOM_MAX9999 = 10000;
   
   private final int RANDOM_MIN1 = 10;
   private final int RANDOM_MAX1 = 99;
   
   private final int RANDOM_MIN2 = 1000;
   //private final int RANDOM_MAX2 = 9999;
   private final int ID_SUFFIX_MAX = 9999;
   private final int ID_SUFFIX_MIN = 1000;
   private static int sequentialNumber = 0;
   
   /**
   */

   public TicketOrder(String customerName, int numRequested)
   {
      String name;
      int idSuffix1;
      int idSuffix2;
   
      this.customerName = customerName;
      this.numRequested = numRequested;
      
      // provides Date/Time Stamp to the Order ID for uniqueness
      LocalDateTime now = LocalDateTime.now();

      // provides a random suffix to the Order ID
      Random randomNo = new Random();
      
      idSuffix1 = randomNo.nextInt((RANDOM_MAX1 - RANDOM_MIN1) + 1)+RANDOM_MIN1;
      
      //int idSuffix2 = randomNo.nextInt((RANDOM_MAX2 - RANDOM_MIN2) + 1)+RANDOM_MIN2;
      if (sequentialNumber >= ID_SUFFIX_MAX || sequentialNumber == 0)
      {
         sequentialNumber = ID_SUFFIX_MIN;
      }
      else
         {
            sequentialNumber = sequentialNumber + 1;
         }
      
      idSuffix2 = sequentialNumber;
      
      if (customerName.length() < 2)
      {
         name = customerName.substring(0,1) + "X";
      }   
      else
      {
         name = customerName.substring(0, 2);
      }
      
      // Order Id consists of the date, time, name, quantity, a random number and a sequential number
      orderID = now.format(DateTimeFormatter.ofPattern("yyyy-MMdd-HHmm-")) + name.toUpperCase()+idSuffix1+"-"+ idSuffix2;   
                           
   }  
   
   public String getCustomerName()
   {
      return customerName;
   }
   
   public int getNumRequested()
   {
      return numRequested;
   }

   public String getOrderID()
   {
      return orderID;
   }            
}