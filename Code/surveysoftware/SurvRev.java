/*
 * SurvRev
 * version 1.0.1
 * 11/28/2013
 * Copyright (c) 2013 Lizard League Software
 */
/**
 *  
   Shows window after click of the button Review Survey from the Choice class..
   
   Shows a list of all survey questions and their possible answers.
   
   Holds text fields that users can enter answers into.
   
   Holds 2 Done buttons, one at the top of the survey and one at the bottom.
         
    Requirements: 1.4.0, 1.4.1, 1.4.2,
    			1.4.3, 1.4.4, 5.4.0
 * */
package surveysoftware;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;

import surveysoftware.SurvGive.ButtonListener;

public class SurvRev extends JFrame {
	
	String surveyName;
	Survey_Actions mySurvey;
	private JScrollPane scroller;
	private JPanel panel;
	private JTextArea[] text;
	private JButton done, done2;
	
	public SurvRev(String s) {
	    surveyName = s;
		mySurvey = new Survey_Actions();
		initGUI();
	}
	
	public void initGUI()
	{
		setSize(600,700);
		panel = new JPanel();
		panel.setLayout(new GridLayout(32, 1,10,10));
		setVisible(true);
		scroller = new JScrollPane(panel);
		
		done = new JButton("Done");
		done.setBounds(254, 25, 138, 23);
		done.addActionListener(new ButtonListener());
		getContentPane().add(scroller, BorderLayout.CENTER);
		panel.add(done);
		panel.add(new JLabel(surveyName));


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
			panel.add(text[i]);
		}
		done2 = new JButton("Done");
		done2.setBounds(254, text[i].getY() + 30, 138, 23);
		done2.addActionListener(new ButtonListener());
		panel.add(done2);
	    scroller = new JScrollPane(panel);
	    getContentPane().add(scroller, BorderLayout.CENTER);

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
