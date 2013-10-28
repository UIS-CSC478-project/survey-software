/*
 * survey_main
 * version 0.0.1
 * 10/8/2013
 * Copyright (c) 2013 Lizard League Software
 */

package surveysoftware;
import java.awt.Container;
import java.awt.event.*;
import java.awt.FlowLayout;
import java.sql.*;
//import ____________;
//import ____________;
import java.util.ArrayList;

import javax.swing.*;

/**
 *  
         Class description goes here.
 *
 * @version      
         0.0.1 Oct 8 2013  
 * @author          
         Beth Kieler  */

public class survey_main {


	
	public static void main(String[] args) {
		boolean dbExists;
		
		survey_actions mysurvey = new survey_actions();
		mysurvey.addNewSurvey("survey1");
		
		ArrayList questionanswer = new ArrayList();
		questionanswer.add(mysurvey.getSurveyID("survey1"));
		questionanswer.add("why");
		questionanswer.add(null);
		questionanswer.add('a');
		questionanswer.add("answer a");

		questionanswer.add('b');
		questionanswer.add("answer b");
		questionanswer.add('c');
		questionanswer.add("answer c");
		questionanswer.add('d');
		questionanswer.add("answer d");
		
		mysurvey.addNewQuestionWithAnswer(questionanswer, 4);
		ArrayList surveynames;
		surveynames = mysurvey.getSurveyNames();
		mysurvey.getSurveyQuestions("survey1");
		
		//creating thread 
//		SwingUtilities.invokeLater(new Runnable()
//		{ 
//			public void run(){ 
//				JavaSwing1 obj=new JavaSwing1(); 
//				obj.display(); 
//				} 
//			}); 
	} // END MAIN
} //END SURVEY_MAIN
	
	class JavaSwing1 implements ActionListener{
		survey_actions mysurvey = new survey_actions();
		ArrayList questionanswer = new ArrayList();
		
		public void display(){ 
			//create frame 
			JFrame jfrm=new JFrame("Swing Application"); 
			//set size 
			jfrm.setSize(600,600); 
			//when closed? 
			jfrm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
			//set the layout 
			jfrm.setLayout(new FlowLayout());
			jfrm.setVisible(true);
			
			field = new JTextField("Name", 20);
			Container content = jfrm.getContentPane();
			JButton button1 = new JButton("SubmitSurveyName");
			content.add(button1);
			button1.addActionListener(this);
			jfrm.add(field);
			
			// The question number for that question
			questionnumber = new JTextField(1);
			jfrm.add(questionnumber);
			
			// Number of possible answers 
			numanswers = new JTextField(1);
			jfrm.add(numanswers);
			
			//Question text field
			qfield = new JTextField(70);
			jfrm.add(qfield);
			
			// First letter text field
			afield = new JTextField(1);
			jfrm.add(afield);
			// First text for the letter text field
			afieldtext = new JTextField(20);
			jfrm.add(afieldtext);
			
			// Second letter text field
			bfield = new JTextField(1);
			jfrm.add(bfield);
			// Secod text for the letter text field
			bfieldtext = new JTextField(20);
			jfrm.add(bfieldtext);
			
			// Third letter text field
			cfield = new JTextField(1);
			jfrm.add(cfield);
			// Third text for the letter text field
			cfieldtext = new JTextField(20);
			jfrm.add(cfieldtext);
			
			// Fourth letter text field
			dfield = new JTextField(1);
			jfrm.add(dfield);
			// Fourth text for the letter text field
			dfieldtext = new JTextField(20);
			jfrm.add(dfieldtext);
			
			//content = jfrm.getContentPane();
			JButton button2 = new JButton("Submit Survey Question");
			content.add(button2);
			button2.addActionListener(this);
			
			//question field w/ answers
			
		
		
		

			button1.addActionListener(new java.awt.event.ActionListener() {
	            public void actionPerformed(java.awt.event.ActionEvent evt) {
	                button1ActionPerformed(evt);
	            }
	        });
		
			button2.addActionListener(new java.awt.event.ActionListener() {
	            public void actionPerformed(java.awt.event.ActionEvent evt) {
	                button2ActionPerformed(evt);
	            }
	        });}

		
			public void button1ActionPerformed(ActionEvent e) {
				e.getActionCommand();				
				String name = field.getText();	
				if(name == " " || name == null){
					JOptionPane.showMessageDialog(null, "Enter a survey name");
				}

				try{
					mysurvey.addNewSurvey(name);
					questionanswer.add(name);
				}
				catch ( Exception exc ) {
				//	System.err.println( e.getClass().getName() + ": " + exc.getMessage() );
					JOptionPane.showMessageDialog(null, exc.getMessage());
			    }
				//}
			}
			
			public void button2ActionPerformed(ActionEvent e) {
				/* questionanswer[1] = survey id
				 * questionanswer[2] = question text
				 * questionanswer [3] = question number (used to sort questions later)
				 * questionanswer [4][6][8][10] = answer letter
				 * questionansswer [5][7][9][11] = answer text
				 */
				
				e.getActionCommand();
				ArrayList questionanswer = new ArrayList();
				
				questionanswer.add(1);//mysurvey.getSurveyID("survey1"));
				
				String questiontext = qfield.getText();
				questionanswer.add(questiontext);
				
				int qnum = Integer.parseInt(questionnumber.getText());
				questionanswer.add(qnum);

				int num = Integer.parseInt(numanswers.getText());
				
				String a = afield.getText();
				questionanswer.add(a);
				String a_text = afieldtext.getText();
				questionanswer.add(a_text);

				a = bfield.getText();
				questionanswer.add(a);
				String b_text = bfieldtext.getText();
				questionanswer.add(b_text);
				
				a = cfield.getText();
				questionanswer.add(a);
				String c_text = cfieldtext.getText();
				questionanswer.add(c_text);
				
				a = dfield.getText();
				questionanswer.add(a);
				String d_text = dfieldtext.getText();
				questionanswer.add(d_text);
				
				mysurvey.addNewQuestionWithAnswer(questionanswer, num);
				
			}
	

		
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			private javax.swing.JTextField field;
			private javax.swing.JTextField qfield;
			private javax.swing.JTextField afield;
			private javax.swing.JTextField bfield;
			private javax.swing.JTextField cfield;
			private javax.swing.JTextField dfield;
			private javax.swing.JTextField afieldtext;
			private javax.swing.JTextField bfieldtext;
			private javax.swing.JTextField cfieldtext;
			private javax.swing.JTextField dfieldtext;
			private javax.swing.JTextField numanswers;
			private javax.swing.JTextField questionnumber;
	}
	

	

