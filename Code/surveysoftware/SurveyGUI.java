/*
 * SurveyGUI
 * version 1.0.1
 * 11/28/2013
 * Copyright (c) 2013 Lizard League Software
 */
/**
 *  
   Shows window after click of the button Give Survey from the Choice Class.  
   
   Holds the text areas for survey name, the survey question, the possible answers for 
   answers a, b, c, or d, and the correct answer.
   
   Holds the buttons for next question and done.   
         
    Requirements: 1.2.0, 1.2.1, 1.2.2,
    			1.2.3, 1.2.4, 1.2.5, 1.2.6,
    			1.2.7, 1.2.8, 1.2.9, 5.4.0
 * */



package surveysoftware;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

import java.awt.Font;

import javax.swing.JTextArea;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;


public class SurveyGUI extends JFrame {  //GUI used to create survey
	private JButton btnNextQues, btnDone;
	private JLabel lblNewSurvey, lblSurveyName, lblQuestion;
	private JLabel lblA, lblB, lblC, lblD, lblAns;
	private JTextArea surveyName, Question, quesA, quesB, quesC, quesD, corAns;
	Survey_Actions mysurvey = new Survey_Actions();
	
	
	public SurveyGUI() {
		//initGUI();
	}
	
	public void initGUI() //initialize sections of GUI 
	{
		
		multiCh();
		header();
		
	}	
	public void header() //This information is initially entered when creating GUI
			     //When entering questions for rest of survey, header isn't needed
	{
		lblNewSurvey = new JLabel("New Survey"); //New survey initiated
		lblNewSurvey.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewSurvey.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewSurvey.setBounds(302, 21, 139, 25);
		getContentPane().add(lblNewSurvey);
		
		lblSurveyName = new JLabel("Survey Name"); //Enter name for survey
		lblSurveyName.setBounds(54, 85, 96, 25);
		getContentPane().add(lblSurveyName);
		
		surveyName = new JTextArea();
		surveyName.setBounds(54, 108, 237, 22);
		getContentPane().add(surveyName);
		
		lblQuestion = new JLabel("Question"); //Enter question in box below lable
		lblQuestion.setBounds(54, 155, 83, 14);
		getContentPane().add(lblQuestion);
	}
		
	public void multiCh() //This is body of window where questions are entered
			      //These boxes stay active until user is finished creating survey
		{
		
		setResizable(false); //user not allowed to resize
		setSize(610,787);
		getContentPane().setLayout(null);
		
		Question = new JTextArea();
		Question.setBounds(54, 180, 465, 48);
		Question.setLineWrap(true);
		getContentPane().add(Question);
		//Question.setText(null);
			
		lblA = new JLabel("a.");  //possible answer a
		lblA.setBounds(54, 239, 46, 14);
		getContentPane().add(lblA);
		
		quesA = new JTextArea();
		quesA.setBounds(54, 255, 465, 57);
		quesA.setLineWrap(true);
		getContentPane().add(quesA);
		
		lblB = new JLabel("b.");  //possible answer b
		lblB.setBounds(54, 323, 46, 14);
		getContentPane().add(lblB);
		
		quesB = new JTextArea();
		quesB.setBounds(54, 337, 465, 57);
		quesB.setLineWrap(true);
		getContentPane().add(quesB);
		
		lblC = new JLabel("c.");  //possible answer c
		lblC.setBounds(54, 408, 46, 14);
		getContentPane().add(lblC);
		
		quesC = new JTextArea();
		quesC.setBounds(54, 422, 465, 57);
		quesC.setLineWrap(true);
		getContentPane().add(quesC);
		
		lblD = new JLabel("d.");  //possible answer d
		lblD.setBounds(54, 489, 46, 14);
		getContentPane().add(lblD);
		
		quesD = new JTextArea();
		quesD.setBounds(54, 508, 465, 57);
		quesD.setLineWrap(true);
		getContentPane().add(quesD);
		
		lblAns = new JLabel("correct answer (optional)");  //input correct answer by letter a,b,c,d
		lblAns.setBounds(54, 586, 160, 14);
		getContentPane().add(lblAns);
		
		corAns = new JTextArea(); //Enter the correct answer
		corAns.setBounds(54, 611, 46, 22);
		getContentPane().add(corAns);
		
		btnNextQues = new JButton("Next Question");  //Move to next question
		btnNextQues.setBounds(114, 653, 139, 23);
		getContentPane().add(btnNextQues);
		btnNextQues.addActionListener(new ButtonListener()); 
		
		btnDone = new JButton("Done");
		btnDone.setBounds(292, 653, 139, 23);
		getContentPane().add(btnDone);
		btnDone.addActionListener(new ButtonListener()); 
		
		}
	
	public class ButtonListener implements ActionListener
	{		
		@Override
		public void actionPerformed(ActionEvent e) 
		{		
			ArrayList QA = new ArrayList(); //holds the question and answers from the form.
			
			/*check if the survey name exists */
			if(!mysurvey.survey_Exists(surveyName.getText())){
				mysurvey.addNewSurvey(surveyName.getText()); //if survey name doesn't already exist, add
			}											     // the survey name to the db.
			
			//Add the data relating to the question to the QA arraylist.
			
			QA.add(mysurvey.getSurveyId(surveyName.getText()));
			QA.add(Question.getText());
			QA.add(corAns.getText());
			QA.add("a");
			QA.add(quesA.getText());
			QA.add("b");
			QA.add(quesB.getText());
			
			int numAnswers = 2; //a minimum of 2 possible answers are required.
			if (quesC.getText().trim().length() != 0 ){
				QA.add("c");
				QA.add(quesC.getText());
				numAnswers++;
			}
			if (quesD.getText().trim().length() != 0){
				QA.add("d");
				QA.add(quesD.getText());
				numAnswers++;
			}
			
			//Add the data collected from the form to the database.
			mysurvey.addNewQuestionWithAnswer(QA, numAnswers);
			
			if(e.getSource()==btnNextQues)
			{			
			Question.setText(null);
			//surveyName.setText(null);
			quesA.setText(null);
			quesB.setText(null);
			quesC.setText(null);
			quesD.setText(null);
			corAns.setText(null);
		        }
			else if(e.getSource()==btnDone)
			{
				setVisible(false);
				Choice chc = new Choice();
				chc.initChoice();
			}
			else
				setVisible(false);        
		}	
	}
}
