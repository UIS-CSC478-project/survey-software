import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SurvRef extends JFrame {
	private JButton btnReviewSurvey, btnGiveSurvey, btnMainMenu;
	private JLabel lblChooseSurveyFrom;
	private JComboBox comboBox;
	
	public SurvRef()   //This GUI allows user to choose a survey
			   //and then choose to review or give the survey
	{
		//initGUI();
	}


public void initGUI()
{
	setSize(428, 264);
	setResizable(false);
	getContentPane().setLayout(null);
	
	comboBox = new JComboBox();
	comboBox.setBounds(121, 68, 152, 20);
	getContentPane().add(comboBox);
	
	lblChooseSurveyFrom = new JLabel("Choose survey from drop down");
	lblChooseSurveyFrom.setBounds(121, 27, 158, 42);
	getContentPane().add(lblChooseSurveyFrom);
	
	btnReviewSurvey = new JButton("Review Survey");
	btnReviewSurvey.setBounds(70, 119, 118, 23);
	getContentPane().add(btnReviewSurvey);
	
	btnGiveSurvey = new JButton("Give Survey");
	btnGiveSurvey.setBounds(214, 119, 113, 23);
	getContentPane().add(btnGiveSurvey);
	
	btnMainMenu = new JButton("Main Menu");  //button directs program back to main menu
	btnMainMenu.setBounds(156, 171, 106, 23);
	getContentPane().add(btnMainMenu);
	
	btnReviewSurvey.addActionListener(new ButtonListener());
	btnGiveSurvey.addActionListener(new ButtonListener());
	btnMainMenu.addActionListener(new ButtonListener()); 
}
	public class ButtonListener implements ActionListener
	{
		
		public void actionPerformed(ActionEvent e) 
		{
			
			if(e.getSource()==btnReviewSurvey)  //Review selected survey
			{
				setVisible(false);
				SurvRev svr = new SurvRev();
				svr.initGUI();
				
			}
			else if(e.getSource()==btnGiveSurvey) //Give selected survey
			{
				setVisible(false);
				SurvGive svg = new SurvGive();	
				svg.initGUI();
			}
			else
			{
				setVisible(false);  //back to main menu
				Choice chc = new Choice();
				chc.initChoice();
			}
		}		
     }
}    
	


    


	
	

