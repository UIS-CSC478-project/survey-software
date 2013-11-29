/*
 * Choice
 * version 1.0.1
 * 11/28/2013
 * Copyright (c) 2013 Lizard League Software
 */

/**
 *  
   First window that is shown after program starts.
   
   Holds the buttons Create Survey, add user, Lookup Survey, and Quit. 
         
    Requirements: 1.0.0, 1.1.0, 1.1.1, 1.1.2,
    			1.1.3, 5.4.0
 * */

package surveysoftware;
import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.*;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Choice extends JFrame {
	private JButton btnCreateSurvey;
	private JButton btnLookUpSurvey;
	private JButton btnQuit;
	
	
	public Choice() {
		//initChoice();
	}
		
	public void initChoice() //Program initiates menu asking user what to do
	{
		setVisible(true);
		setResizable(false); //user not allowed to resize
		setSize(510,168);
		getContentPane().setLayout(null);
		
		btnCreateSurvey = new JButton("Create Survey"); //Button to create survey
		btnCreateSurvey.setBounds(102, 45, 138, 23);
		getContentPane().add(btnCreateSurvey);
		
		btnLookUpSurvey = new JButton("Look up Survey"); //Button to look up survey
		btnLookUpSurvey.setBounds(254, 45, 138, 23);
		getContentPane().add(btnLookUpSurvey);
		
		btnQuit = new JButton("Quit"); //Button to quit program
		btnQuit.setBounds(203, 92, 89, 23);
		getContentPane().add(btnQuit);
		
		//register listeners with buttons
		btnCreateSurvey.addActionListener(new ButtonListener());
		btnLookUpSurvey.addActionListener(new ButtonListener());
		btnQuit.addActionListener(new ButtonListener());
		
		
	}
	
	
	private class ButtonListener implements ActionListener //responds to button events
	{
		@Override
		public void actionPerformed(ActionEvent e)  //ActionListener Interface method
		{
			
			if(e.getSource()==btnCreateSurvey)  //Create Survey
			{	
				setVisible(false);
				SurveyGUI sgi = new SurveyGUI();
				sgi.initGUI();
				sgi.setVisible(true);
			}
			else if(e.getSource()==btnLookUpSurvey) //Look up survey
			{
				setVisible(false);
				SurvRef svr = new SurvRef();
				svr.initGUI();
				svr.setVisible(true);
			}
			else if(e.getSource()==btnQuit) //Quit
			{
				
				System.exit(0);
				
			}
			else
			{
				JOptionPane.showMessageDialog(null, "error"); //programmer code to indicate if something went wrong
			}
				
		}	
	}
}
