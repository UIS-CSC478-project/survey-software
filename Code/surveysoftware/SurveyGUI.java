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


public class SurveyGUI extends JFrame {
	private JButton btnNextQues;
	private JLabel lblNewSurvey, lblSurveyName, lblQuestion;
	private JLabel lblA, lblB, lblC, lblD, lblAns;
	private JTextArea surveyName, Question, quesA, quesB, quesC, quesD, corAns;
	survey_actions mysurvey = new survey_actions();
	
	
	public SurveyGUI() {
		//initGUI();
	}
	
	public void initGUI()
	{
		
		
		multiCh();
		header();
		
	}	
	public void header()
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
		
		lblQuestion = new JLabel("First Question"); //Question number
		lblQuestion.setBounds(54, 155, 83, 14);
		getContentPane().add(lblQuestion);
	}
		
	public void multiCh()
		{
		
		setResizable(false); //user not allowed to resize
		setSize(750,787);
		getContentPane().setLayout(null);
		
		Question = new JTextArea();
		Question.setBounds(54, 180, 465, 48);
		getContentPane().add(Question);
		//Question.setText(null);
			
		lblA = new JLabel("a.");  //possible answer a
		lblA.setBounds(54, 239, 46, 14);
		getContentPane().add(lblA);
		
		quesA = new JTextArea();
		quesA.setBounds(54, 255, 465, 57);
		getContentPane().add(quesA);
		
		lblB = new JLabel("b.");  //possible answer b
		lblB.setBounds(54, 323, 46, 14);
		getContentPane().add(lblB);
		
		quesB = new JTextArea();
		quesB.setBounds(54, 337, 465, 57);
		getContentPane().add(quesB);
		
		lblC = new JLabel("c.");  //possible answer c
		lblC.setBounds(54, 408, 46, 14);
		getContentPane().add(lblC);
		
		quesC = new JTextArea();
		quesC.setBounds(54, 422, 465, 57);
		getContentPane().add(quesC);
		
		lblD = new JLabel("d.");  //possible answer d
		lblD.setBounds(54, 489, 46, 14);
		getContentPane().add(lblD);
		
		quesD = new JTextArea();
		quesD.setBounds(54, 508, 465, 57);
		getContentPane().add(quesD);
		
		lblAns = new JLabel("correct answer");  //input correct answer by letter a,b,c,d
		lblAns.setBounds(54, 586, 160, 14);
		getContentPane().add(lblAns);
		
		corAns = new JTextArea(); //Enter the correct answer
		corAns.setBounds(54, 611, 46, 22);
		getContentPane().add(corAns);
		
		btnNextQues = new JButton("Next Question");  //Move to next question
		btnNextQues.setBounds(302, 673, 139, 23);
		getContentPane().add(btnNextQues);
		btnNextQues.addActionListener(new ButtonListener()); 
		
		}
	
	private class ButtonListener implements ActionListener
	{		
		@Override
		public void actionPerformed(ActionEvent e) 
		{		
			
			//setVisible(false);
			ArrayList QA = new ArrayList();
			
			mysurvey.addNewSurvey(surveyName.getText());
			
			QA.add(mysurvey.getSurveyID(surveyName.getText()));
			
			int numAnswers = 2;
			QA.add(Question.getText());
			QA.add(corAns.getText());
			
			QA.add("a");
			QA.add(quesA.getText());
			QA.add("b");
			QA.add(quesB.getText());
			
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
			
			mysurvey.addNewQuestionWithAnswer(QA, numAnswers);
			
			
			
			
			Question.setText(null);
			corAns.setText(null);
			quesA.setText(null);
			quesB.setText(null);
			quesC.setText(null);
			quesD.setText(null);
			corAns.setText(null);
			JOptionPane.showMessageDialog(null, "This worked");
		}
		
	}
		
		
	
}
