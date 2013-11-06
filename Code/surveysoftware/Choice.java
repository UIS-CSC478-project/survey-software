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
	private JButton btnGiveSurvey;
	private JLabel lblChoose;
	
	
	public Choice() {
		//initChoice();
	}
		
	public void initChoice() //Program initiates menu asking user what to do
	{
		setResizable(false); //user not allowed to resize
		setSize(506,284);
		getContentPane().setLayout(null);
		
		lblChoose = new JLabel("Where to Begin"); //Title of window
		lblChoose.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblChoose.setBounds(182, 37, 104, 23);
		getContentPane().add(lblChoose);
		
		btnCreateSurvey = new JButton("Create Survey"); //Button moves program to create a survey
		btnCreateSurvey.setBounds(37, 95, 138, 23);
		getContentPane().add(btnCreateSurvey);
		
		btnLookUpSurvey = new JButton("Look up Survey"); //Button for user to look up survey to review
		btnLookUpSurvey.setBounds(182, 95, 138, 23);
		getContentPane().add(btnLookUpSurvey);
		
		btnGiveSurvey = new JButton("Give Survey"); //Button to give survey to public
		btnGiveSurvey.setBounds(326, 95, 132, 23);
		getContentPane().add(btnGiveSurvey);
		
		//register listeners with buttons
		btnCreateSurvey.addActionListener(new ButtonListener());
		btnLookUpSurvey.addActionListener(new ButtonListener());
		btnGiveSurvey.addActionListener(new ButtonListener());
		
		
	}
	
	
	private class ButtonListener implements ActionListener //responds to button events
	{
		
		public void actionPerformed(ActionEvent e)  //ActionListener Interface method
		{
			
			if(e.getSource()==btnCreateSurvey)  //Create Survey
			{	
				setVisible(false);
				SurveyGUI sgi = new SurveyGUI();
				sgi.initGUI();
				sgi.setVisible(true);
			}
			else if(e.getSource()==btnLookUpSurvey) //look up survey
			{
				setVisible(false);
				SurvRef svr = new SurvRef();
				svr.initGUI();
				svr.setVisible(true);
			}
			else if(e.getSource()==btnGiveSurvey) //Give survey
			{
				setVisible(false);
				SurvGive sgv = new SurvGive();
				sgv.initGUI();
				sgv.setVisible(true);
			}
			else
			{
				JOptionPane.showMessageDialog(null, "error"); //code to tell programmer somethings wrong
			}
				
		}	
	}
}
