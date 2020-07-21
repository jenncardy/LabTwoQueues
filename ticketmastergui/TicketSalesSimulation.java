package ticketmastergui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JTextField;
import java.awt.Insets;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.SystemColor;
import java.awt.Color;
import java.awt.Font;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.Choice;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

import ch04.queues.*;
import support.LLNode;

public class TicketSalesSimulation 
{

	private JFrame frmTicketMaster;
	private JTextField name;
	private JLabel lblConcertName;
	private JLabel lblQuantity;
	private JTextField quantity;
	private JLabel lblInstructions;
	private JLabel lblError;
	private JComboBox eventName;
   private boolean inputIsValid = false;
   

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) 
   {
      LinkedQueue orderQueue = new LinkedQueue();
      TicketQueue tq = new TicketQueue(orderQueue);
      Thread t = new Thread(tq);
      t.setName("Ticket Queue");
      t.start();
     
      
      EventQueue.invokeLater(new Runnable() 
      {
			public void run() 
         {
				try 
            {
					TicketSalesSimulation window = new TicketSalesSimulation(orderQueue, tq);
					window.frmTicketMaster.setVisible(true);
				} 
            catch (Exception e) 
            {
					e.printStackTrace();
				}
			}
		});     
   }
   

    /**
	 * Create the application.
	 */
	public TicketSalesSimulation(LinkedQueue orderQueue, TicketQueue tq) 
   {
		initialize(orderQueue, tq);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(LinkedQueue orderQueue, TicketQueue tq) {
		frmTicketMaster = new JFrame();
		frmTicketMaster.setFont(new Font("Arial", Font.BOLD, 12));
		frmTicketMaster.getContentPane().setBackground(new Color(191, 205, 219));
		frmTicketMaster.setTitle("Ticket Master");
		frmTicketMaster.setBounds(100, 100, 450, 300);
		frmTicketMaster.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{46, 133, 72, 161, 0};
		gridBagLayout.rowHeights = new int[]{20, 20, 20, 0, 41, 33, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		frmTicketMaster.getContentPane().setLayout(gridBagLayout);
		
		JLabel lblName = new JLabel("Name:");
		GridBagConstraints gbc_lblName = new GridBagConstraints();
		gbc_lblName.anchor = GridBagConstraints.WEST;
		gbc_lblName.insets = new Insets(0, 0, 5, 5);
		gbc_lblName.gridx = 0;
		gbc_lblName.gridy = 1;
		frmTicketMaster.getContentPane().add(lblName, gbc_lblName);
		
		name = new JTextField();
		name.setColumns(10);
		GridBagConstraints gbc_name = new GridBagConstraints();
		gbc_name.anchor = GridBagConstraints.NORTH;
		gbc_name.fill = GridBagConstraints.HORIZONTAL;
		gbc_name.insets = new Insets(0, 0, 5, 0);
		gbc_name.gridwidth = 3;
		gbc_name.gridx = 1;
		gbc_name.gridy = 1;
		frmTicketMaster.getContentPane().add(name, gbc_name);
		
		lblConcertName = new JLabel("Event:");
		GridBagConstraints gbc_lblConcertName = new GridBagConstraints();
		gbc_lblConcertName.anchor = GridBagConstraints.WEST;
		gbc_lblConcertName.insets = new Insets(0, 0, 5, 5);
		gbc_lblConcertName.gridx = 0;
		gbc_lblConcertName.gridy = 2;
		frmTicketMaster.getContentPane().add(lblConcertName, gbc_lblConcertName);
		
		eventName = new JComboBox();
		eventName.setModel(new DefaultComboBoxModel(new String[] {"Broadway - Hamilton"}));
		GridBagConstraints gbc_eventName = new GridBagConstraints();
		gbc_eventName.gridwidth = 3;
		gbc_eventName.insets = new Insets(0, 0, 5, 5);
		gbc_eventName.fill = GridBagConstraints.HORIZONTAL;
		gbc_eventName.gridx = 1;
		gbc_eventName.gridy = 2;
		frmTicketMaster.getContentPane().add(eventName, gbc_eventName);
		
		lblQuantity = new JLabel("Quantity:");
		GridBagConstraints gbc_lblQuantity = new GridBagConstraints();
		gbc_lblQuantity.anchor = GridBagConstraints.WEST;
		gbc_lblQuantity.insets = new Insets(0, 0, 5, 5);
		gbc_lblQuantity.gridx = 0;
		gbc_lblQuantity.gridy = 3;
		frmTicketMaster.getContentPane().add(lblQuantity, gbc_lblQuantity);
		
		quantity = new JTextField();
		quantity.setColumns(10);
		GridBagConstraints gbc_quantity = new GridBagConstraints();
		gbc_quantity.anchor = GridBagConstraints.NORTHWEST;
		gbc_quantity.insets = new Insets(0, 0, 5, 0);
		gbc_quantity.gridwidth = 3;
		gbc_quantity.gridx = 1;
		gbc_quantity.gridy = 3;
		frmTicketMaster.getContentPane().add(quantity, gbc_quantity);
		
		JButton btnOK = new JButton("OK");
      btnOK.addActionListener(new ActionListener() 
      {
			public void actionPerformed(ActionEvent e) 
         {
           if (name.getText().equalsIgnoreCase("STOP"))
           {
              tq.doStop();
           }   
           else
           {
               inputIsValid = getFields(name, quantity);
               if (inputIsValid)
               {
                  TicketOrder order = new TicketOrder(name.getText(), Integer.parseInt(quantity.getText()));
                  orderQueue.enqueue(order);
                  name.setText(" ");
                  quantity.setText(" ");
                  
                  frmTicketMaster.setVisible(false);
                                   
                  frmTicketMaster.dispose();
                                  
                  EventQueue.invokeLater(new Runnable() 
                  {
			            public void run() 
                  {
				         try 
                     {
					          TicketSalesSimulation window = new TicketSalesSimulation(orderQueue, tq);
					          window.frmTicketMaster.setVisible(true);
				         } 
                     catch (Exception e) 
                     {
					         e.printStackTrace();                
				         }
			         }
		          });
                                
                initialize(orderQueue, tq);
               }
               else
               {               
                  lblError.setEnabled(true);
                  lblError.setText("Please enter a name and a valid quantity");
               }   				
			   }
         }   
		   });
         
     	lblInstructions = new JLabel("Please Enter Your First or Last Name and Ticket Quantity or STOP to quit");
		lblInstructions.setFont(new Font("Arial", Font.PLAIN, 11));
		GridBagConstraints gbc_lblInstructions = new GridBagConstraints();
		gbc_lblInstructions.insets = new Insets(0, 0, 5, 0);
		gbc_lblInstructions.gridwidth = 4;
		gbc_lblInstructions.gridx = 0;
		gbc_lblInstructions.gridy = 4;
		frmTicketMaster.getContentPane().add(lblInstructions, gbc_lblInstructions);
		GridBagConstraints gbc_btnOK = new GridBagConstraints();
		gbc_btnOK.anchor = GridBagConstraints.EAST;
		gbc_btnOK.fill = GridBagConstraints.VERTICAL;
		gbc_btnOK.insets = new Insets(0, 0, 5, 5);
		gbc_btnOK.gridx = 1;
		gbc_btnOK.gridy = 5;
		frmTicketMaster.getContentPane().add(btnOK, gbc_btnOK);
		
		JButton btnCancel = new JButton("Cancel");
		GridBagConstraints gbc_btnCancel = new GridBagConstraints();
		gbc_btnCancel.insets = new Insets(0, 0, 5, 0);
		gbc_btnCancel.anchor = GridBagConstraints.WEST;
		gbc_btnCancel.fill = GridBagConstraints.VERTICAL;
		gbc_btnCancel.gridx = 3;
		gbc_btnCancel.gridy = 5;
		frmTicketMaster.getContentPane().add(btnCancel, gbc_btnCancel);
      btnCancel.addActionListener(new ActionListener() 
      {
            public void actionPerformed(ActionEvent e) 
            {
               tq.doStop();
               frmTicketMaster.setVisible(false);
               frmTicketMaster.dispose();
               System.exit(0);
            }
		 });
		
		lblError = new JLabel("");
		lblError.setEnabled(false);
		lblError.setFont(new Font("Arial", Font.BOLD, 12));
		GridBagConstraints gbc_lblError = new GridBagConstraints();
		gbc_lblError.anchor = GridBagConstraints.WEST;
		gbc_lblError.gridwidth = 3;
		gbc_lblError.insets = new Insets(0, 0, 5, 0);
		gbc_lblError.gridx = 1;
		gbc_lblError.gridy = 7;
		frmTicketMaster.getContentPane().add(lblError, gbc_lblError);
	}   
   
   /**
      This method ensures that the user input is valid
      @param String   The orderer's name
      @param int      The quantity of tickets
      @return boolean True if the input is valid, otherwise false
   */
   
   public static boolean getFields(JTextField name, JTextField quantity)
   {
      String nameStr = name.getText();
      int intQty;

      if (nameStr == null  || nameStr == " ")
      {
         return false;
      }
               
      //String quantityStr = quantity.getText();
      
      try 
		{
        intQty = Integer.parseInt(quantity.getText());
        if (intQty > 0)
        {
	       return true;
        }
		}
		catch (NumberFormatException exe)
		{
				   return false;
		}
      return false;      
   }
}
