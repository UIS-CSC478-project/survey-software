/*
 * SurvGive
 * version 1.0.1
 * 11/28/2013
 * Copyright (c) 2013 Lizard League Software
 */

/**
 *  
   Shows window after click of the button Give Survey from the Choice class..
   
   Shows a list of all survey questions and their possible answers.
   
   Holds blank text fields that users can enter answers into.
   
   Holds 2 Done buttons, one at the top of the survey and one at the bottom.
         
    Requirements: 1.5.0, 1.5.1, 1.5.2,
    			1.5.3, 1.5.5, 1.5.6, 5.4.0
 * */

package surveysoftware;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import surveysoftware.SurveyGUI.ButtonListener;

public class SurvGive extends JFrame {

	String surveyName;
	Survey_Actions mySurvey;
	ArrayList allQuestions;
	private JScrollPane scroller;
	private JPanel panel;
	private JTextArea[] text;
	private JTextField[] answer;
	private JLabel survey;
	private JButton done, done2;
	int numQuestions;
	int y = 20;
	ArrayList<String> oneQuesAns;

	public SurvGive(String s) {
		surveyName = s;
		mySurvey = new Survey_Actions();
		initGUI();
	}
	
	public void initGUI()
	{	    
		numQuestions = mySurvey.getNumberOfQuestions(mySurvey.getSurveyId(surveyName));
		String postText = "";
		//Get questions
		allQuestions = mySurvey.getSurveyQuestionsAnswers(surveyName);
		
		
		
		setBounds(0,0, 600, 700);
		panel = new JPanel();
		panel.setLayout(null);
		panel.setPreferredSize(new Dimension(600, 750));
		setVisible(true);
		scroller = new JScrollPane(panel);
		text = new JTextArea[numQuestions];
		answer = new JTextField[numQuestions];
		survey = new JLabel(surveyName);
		survey.setBounds(10, 25, 138, 23);
		panel.add(survey);
		done = new JButton("Done");
		done.setBounds(254, 25, 138, 23);
		done.addActionListener(new ButtonListener());
		panel.add(done);
		
		for(int i = 0; i < numQuestions; i++){
			
			/*Get the Text to post */
			oneQuesAns = (ArrayList) allQuestions.get(i);
			//System.out.println(oneQuesAns);
			postText = oneQuesAns.get(0) + "\n";
			for (int j = 1; j < oneQuesAns.size() - 1; j++) {
				if (j % 2 == 0)
					postText = postText + oneQuesAns.get(j) + "\n";
				else
					postText = postText + "     " + oneQuesAns.get(j) + "   ";
			}

			y += 40;
			text[i] = new JTextArea(postText);
			text[i].setLineWrap(true);
			text[i].setVisible(true);
			text[i].setBounds(10, y, 500, 100);
			answer[i] = new JTextField();

			y += 120;
			answer[i].setBounds(10, y, 30, 30);
			text[i].setLayout(new FlowLayout());
			answer[i].setLayout(new FlowLayout());
			text[i].setEditable(false);
			answer[i].setEditable(true);
			text[i].setColumns(1);
			panel.add(text[i]);
			panel.add(answer[i]);
		}
		done2 = new JButton("Done");
		done2.setBounds(254, y + 30, 138, 23);
		done2.addActionListener(new ButtonListener());
		panel.add(done2);
		scroller = new JScrollPane(panel);
		scroller.setPreferredSize(new Dimension(600, 700));
		getContentPane().add(scroller, BorderLayout.CENTER);

	}// END INITGUI

	public class ButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// first check if all the answers have been filled
			for (int i = 0; i < numQuestions; i++) {
				// check if the answer has one character
				if (answer[i].getText().trim().length() != 1) {
					JOptionPane
							.showMessageDialog(
									null,
									"You haven't filled in all the answers or the answers are not valid. Please correct the errors and try again");
					return;
				}

				// check if the answer is in possible answers list
				ArrayList question = (ArrayList) allQuestions.get(i);
				boolean isValid = false;
				for (int j = 1; j < question.size() - 1; j++) {

					if (j % 2 != 0) {
						if (answer[i].getText().trim()
								.equalsIgnoreCase((String) question.get(j))) {
							isValid = true;
							break;

						}
					}
				}
				if (isValid == false) {
					JOptionPane
							.showMessageDialog(
									null,
									"Your answers should be in the list shown above. Please correct the errors and try again");
					return;
				}

			}
			
			//At this point we have valid answers and we are good to go
			for (int i = 0; i < numQuestions; i++) {
				oneQuesAns = (ArrayList) allQuestions.get(i);
				Integer num = Integer.valueOf(oneQuesAns.get(oneQuesAns.size()-1));
				mySurvey.addResults(num, answer[i].getText());
			}

			setVisible(false); // back to main menu
			Choice chc = new Choice();
			chc.initChoice();
		}

	}

}
