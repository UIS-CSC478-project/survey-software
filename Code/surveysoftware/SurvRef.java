/*
 * SurvRef
 * version 1.0.1
 * 11/28/2013
 * Copyright (c) 2013 Lizard League Software
 */
/**
 *  
   Shows window after click of the button Look Up Survey from the Choice Class..
   
   Holds the drop-down box for choosing a survey, and the buttons Review Survey and Give Survey. 
         
    Requirements: 1.3.0, 1.3.1, 1.3.2,
    			1.3.3, 1.3.4, 5.4.0
 * */

package surveysoftware;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SurvRef extends JFrame {
	private JButton btnReviewSurvey, btnGiveSurvey, btnMainMenu, btnSurveyResults;
	private JLabel lblChooseSurveyFrom;
	private JComboBox comboBox;
	Survey_Actions mySurvey;
	String surveyName;
	
	public SurvRef()   //This GUI allows user to choose a survey
			   //and then choose to review or give the survey
	{
		//initGUI();
		mySurvey = new Survey_Actions();
	}


public void initGUI()
{
	setSize(412, 264);
	setResizable(false);
	getContentPane().setLayout(null);
	
	comboBox = new JComboBox();
	comboBox.setBounds(121, 68, 152, 20);
	getContentPane().add(comboBox);
	
	lblChooseSurveyFrom = new JLabel("Choose survey from drop down"); //drop down menu populated by curent survey titles
	lblChooseSurveyFrom.setBounds(121, 27, 158, 42);
	getContentPane().add(lblChooseSurveyFrom);
	
	btnReviewSurvey = new JButton("Review Survey"); //After choosing survey button opens up survey for review
	btnReviewSurvey.setBounds(10, 119, 118, 23);
	getContentPane().add(btnReviewSurvey);
	
	btnGiveSurvey = new JButton("Give Survey"); //After choosing survey button opens up survey to give
	btnGiveSurvey.setBounds(271, 119, 113, 23);
	getContentPane().add(btnGiveSurvey);
	
	btnMainMenu = new JButton("Main Menu");  //button directs program back to main menu
	btnMainMenu.setBounds(138, 169, 123, 23);
	getContentPane().add(btnMainMenu);
	
	btnSurveyResults = new JButton("Survey Results");
	btnSurveyResults.setBounds(138, 119, 123, 23);
	getContentPane().add(btnSurveyResults);
	
	//comboBox.addActionListener(new ButtonListener());
	btnReviewSurvey.addActionListener(new ButtonListener());
	btnGiveSurvey.addActionListener(new ButtonListener());
	btnMainMenu.addActionListener(new ButtonListener()); 
	btnSurveyResults.addActionListener(new ButtonListener());
	
	String[] allSurveys = mySurvey.getSurveyNames();
	for(int i = 0; i < allSurveys.length; i++){
          comboBox.addItem(allSurveys[i]);
	}
}
	public class ButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e) 
		{		
			if(e.getSource()==btnReviewSurvey)  //Review selected survey
			{
				setVisible(false);
				SurvRev svr = new SurvRev((String)comboBox.getSelectedItem());
				//svr.initGUI();		
			}
			else if(e.getSource()==btnGiveSurvey) //Give selected survey
			{
				setVisible(false);
				SurvGive svg = new SurvGive((String)comboBox.getSelectedItem());	
				//svg.initGUI();
			}
			else if(e.getSource()==btnMainMenu){
				setVisible(false);  //back to main menu
				Choice chc = new Choice();
				chc.initChoice();
			}
			else
			{
				setVisible(false);  //Display survey results
				SurvResults srvRes = new SurvResults();
				srvRes.initGui();
			}
		}		
     }
}    
	


    


	
	

