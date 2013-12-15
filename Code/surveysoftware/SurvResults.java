/*
 * SurvResults
 * version 2.0.0
 * 7/28/2013
 * Copyright (c) 2013 Lizard League Software
 */
/**
 *  
   Shows window after click of the button Survey Results from the Choice class.
   
   Shows a list of all survey questions and the percentage of survey takers 
   who chose each possible answer.
   
   Holds 2 Done buttons, one at the top of the survey and one at the bottom.
         
    Requirements: 1.6.0, 1.6.1, 1.6.2,
    			1.6.3, 1.6.4, 5.4.0
 * */
package surveysoftware;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;

import surveysoftware.SurvGive.ButtonListener;


public class SurvResults extends JFrame {
	String surveyName;
	Survey_Actions mySurvey;
	private JScrollPane scroller;
	private JPanel panel, headerPanel, bodyPanel, footerPanel;
	private JTextArea[] text;
	private JButton done, done2;
	
	public SurvResults(String s) {
	    surveyName = s;
		mySurvey = new Survey_Actions();
	}
	
	public void initGui(){
//		System.out.println(" in initGui");
//	    setLayout(new BorderLayout());
//	    
//		setSize(600,700);
//		panel = new JPanel();
//		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
//
//		setVisible(true);
//		
//		headerPanel = new JPanel();
//		headerPanel.setLayout(null);
//		setVisible(true);
//
//		bodyPanel = new JPanel();
//		bodyPanel.setLayout(new GridLayout(32, 1,10,10));
//		setVisible(true);
//		scroller = new JScrollPane(bodyPanel);
//		
//		panel.add(headerPanel);
//		panel.add(bodyPanel);
////		headerPanel.setVisible(true);
////		bodyPanel.setVisible(true);
//		
//		done = new JButton("Done");
//		done.setBounds(254, 25, 138, 23);
//		done.addActionListener(new ButtonListener());
//		headerPanel.add(done);
//		headerPanel.add(new JLabel(surveyName));
//
////		getContentPane().add(headerPanel);
////		getContentPane().add(scroller);
//
//	    add(headerPanel,BorderLayout.NORTH);
//	 //   add(bodyPanel,BorderLayout.CENTER);
//	//  headerPanel.setBounds(0,0,600,100);
//	 //   bodyPanel.setBounds(0,100, 600, 500);
//	    setVisible(true);
		
	    headerPanel = new JPanel();
	    bodyPanel = new JPanel();
	    footerPanel = new JPanel();
	    
	    done = new JButton("Done");
	    done2 = new JButton("Done");
	    headerPanel.add(done);
	    footerPanel.add(done2);
	    done.addActionListener(new ButtonListener());
	    done2.addActionListener(new ButtonListener());



	    setLayout(new BorderLayout());
	    setSize(600,450);
	    scroller = new JScrollPane(bodyPanel);
	    
	    add(headerPanel,BorderLayout.NORTH);
	    add(scroller,BorderLayout.CENTER);
	    add(footerPanel,BorderLayout.SOUTH);
	    bodyPanel.setBounds(0,100, 600, 500);
	    bodyPanel.setLayout(new GridLayout(32, 1,10,10));
	    setVisible(true);
		
		
		
		
		
		int numQuestions = mySurvey.getNumberOfQuestions(mySurvey.getSurveyId(surveyName));
		String postText = "";
		text = new JTextArea[numQuestions];
		
		int i;
		//Get questions
		ArrayList allQuestions = mySurvey.getSurveyQuestionsAnswers(surveyName);
		
		for(i = 0; i < numQuestions; i++){
			ArrayList <String> temp = (ArrayList) allQuestions.get(i);
			postText = temp.get(0) + "\n";
			for(int j = 1; j < temp.size()-1; j++){
				if (j%2 == 0)
					postText = postText + temp.get(j) + "\n";
				else
					postText = postText + "     " + temp.get(j) + "   ";
			}
			
			
			text[i] = new JTextArea(postText);
			
			text[i].setEditable(false);
			text[i].setColumns(1);
			bodyPanel.add(text[i]);
		}



	}//END INITGUI

	public class ButtonListener implements ActionListener
	{		
		@Override
		public void actionPerformed(ActionEvent e) 
		{					
			setVisible(false);  //back to main menu
			Choice chc = new Choice();
			chc.initChoice();
		}
	}

}
