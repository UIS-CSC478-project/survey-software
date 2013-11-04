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
	
	public SurvRef() 
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
	
	btnMainMenu = new JButton("Main Menu");
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
			
			if(e.getSource()==btnReviewSurvey)
			{
				setVisible(false);
				SurvRev svr = new SurvRev();
				svr.initGUI();
				
			}
			else if(e.getSource()==btnGiveSurvey)
			{
				setVisible(false);
				SurvGive svg = new SurvGive();	
				svg.initGUI();
			}
			else
			{
				setVisible(false);
				Choice chc = new Choice();
				chc.initChoice();
			}
		}		
     }
}    
	


    


	
	

