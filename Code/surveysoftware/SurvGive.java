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
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import surveysoftware.SurveyGUI.ButtonListener;

public class SurvGive extends JFrame {

	String surveyName;
	Survey_Actions mySurvey;
	ArrayList allQuestions;
	private JScrollPane scroller;
	private JPanel panel, headerPanel, bodyPanel, footerPanel;
	private JPanel[] questionPanel;
	private JPanel[] answerPanel;
	private JLabel[] lblAnswer;
	private JTextArea[] text;
	private JTextField[] answer;
	private JLabel survey;
	private JButton done, done2;
	int numQuestions;
	ArrayList<String> oneQuesAns;

	public SurvGive(String s) {
		surveyName = s;
		mySurvey = new Survey_Actions();
		initGUI();
	}
	
	public void initGUI()
	{	    
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
	    setTitle(surveyName);  
	    setSize(600,450);
	    scroller = new JScrollPane(bodyPanel);
	  //  scroller.setAutoscrolls(false);
	    
	    add(headerPanel,BorderLayout.NORTH);
	    add(scroller,BorderLayout.CENTER);
	    add(footerPanel,BorderLayout.SOUTH);
	 //   bodyPanel.setBounds(0,0, 600, 500);
//	    bodyPanel.setLayout(new GridLayout(32, 1,10,10));
	    bodyPanel.setLayout(new GridLayout(0,1));
	    setVisible(true);
		
		numQuestions = mySurvey.getNumberOfQuestions(mySurvey.getSurveyId(surveyName));
		String postText = "";
		//Get questions
		allQuestions = mySurvey.getSurveyQuestionsAnswers(surveyName);
		text = new JTextArea[numQuestions];
		answer = new JTextField[numQuestions];
	    questionPanel = new JPanel[numQuestions];
	    answerPanel = new JPanel[numQuestions];
	    lblAnswer = new JLabel[numQuestions];
		
		for(int i = 0; i < numQuestions; i++){
			questionPanel[i] = new JPanel();
			answerPanel[i] = new JPanel();
			lblAnswer[i] = new JLabel("Answer");

			/*Get the Text to post */
			oneQuesAns = (ArrayList) allQuestions.get(i);
			
			postText = oneQuesAns.get(0) + "\n";
			for (int j = 1; j < oneQuesAns.size() - 1; j++) {
				if (j % 2 == 0)
					postText = postText + oneQuesAns.get(j) + "\n";
				else
					postText = postText + "     " + oneQuesAns.get(j) + "   ";
			}
			
			bodyPanel.add(questionPanel[i]);
			bodyPanel.add(answerPanel[i]);
			text[i] = new JTextArea(postText);
			text[i].setLineWrap(true);
			text[i].setVisible(true);
			text[i].setBounds(1, 1, 500, 100);
			answer[i] = new JTextField();
			text[i].setEditable(false);
			answer[i].setEditable(true);
			text[i].setColumns(1);
			answer[i].setColumns(1);
			questionPanel[i].add(text[i]);
			answerPanel[i].add(lblAnswer[i]);
			answerPanel[i].add(answer[i]);
		}
		
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
