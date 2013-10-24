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
		//survey_db mydb = new survey_db();
		int numanswers = 4;
				

//		
//		ArrayList questionanswer = new ArrayList();
//		questionanswer.add(mydb.getSurveyID(newsurvey));
//		questionanswer.add("why");
//		questionanswer.add(null);
//		questionanswer.add('a');
//		questionanswer.add("answer a");
//
//		questionanswer.add('b');
//		questionanswer.add("answer b");
//		questionanswer.add('c');
//		questionanswer.add("answer c");
//		questionanswer.add('d');
//		questionanswer.add("answer d");
//		
//		mydb.addNewQuestionWithAnswer(questionanswer, numanswers);
		
		//creating thread 
		SwingUtilities.invokeLater(new Runnable()
		{ 
			public void run(){ 
				JavaSwing1 obj=new JavaSwing1(); 
				obj.display(); 
				} 
			}); 
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
			
			field = new JTextField("Enter Survey Name", 20);
			Container content = jfrm.getContentPane();
			JButton button1 = new JButton("SubmitSurveyName");
			content.add(button1);
			button1.addActionListener(this);
			jfrm.add(field);
			
			numanswers = new JTextField("How many answers to the question", 1);
			jfrm.add(numanswers);
			
			qfield = new JTextField("Enter Survey question", 70);
			jfrm.add(qfield);
			afield = new JTextField("Enter Letter or Number", 1);
			jfrm.add(afield);
			afieldtext = new JTextField("Enter Text Answer", 20);
			jfrm.add(afieldtext);
			bfield = new JTextField("Enter Letter or Number", 1);
			jfrm.add(bfield);
			bfieldtext = new JTextField("Enter Text Answer", 20);
			jfrm.add(bfieldtext);
			cfield = new JTextField("Enter Letter or Number", 1);
			jfrm.add(cfield);
			cfieldtext = new JTextField("Enter Text Answer", 20);
			jfrm.add(cfieldtext);
			
			dfield = new JTextField("Enter Letter or Number", 1);
			jfrm.add(dfield);
			dfieldtext = new JTextField("Enter Text Answer", 20);
			jfrm.add(dfieldtext);
			
			//content = jfrm.getContentPane();
			JButton button2 = new JButton("Submit Survey Question");
			content.add(button2);
			button2.addActionListener(this);
			
		
		
		

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
				System.out.println("action is " + e.toString());
				String name = field.getText();	
				mysurvey.addNewSurvey(name);
				questionanswer.add(name);
			}
			
			public void button2ActionPerformed(ActionEvent e) {
				/* questionanswer[1] = survey id
		 * questionanswer[2] = question text
		 * questionanswer [3] = question number (used to sort questions later)
		 * questionanswer [4][6][8][10] = answer letter
		 * questionansswer [5][7][9][11] = answer text
		 */
				e.getActionCommand();
				System.out.println("action is " + e.toString());
				ArrayList questionanswer = new ArrayList();
				String name = qfield.getText();
				questionanswer.add(qfield);
				int num = Integer.parseInt(numanswers.getText());
				questionanswer.add(num);
				String a = afield.getText();
				questionanswer.add(a);
				String a_text = afieldtext.getName()
				
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
	}
	

	

