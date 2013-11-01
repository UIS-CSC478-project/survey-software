package surveysoftware;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JComboBox;


public class SurvRef extends JFrame {
	public SurvRef() {
		initGUI();
	}


public void initGUI()
{
	survey_actions mysurvey = new survey_actions();
	String[] surveyList = mysurvey.getSurveyNames();
	setSize(436, 422);
	setResizable(false);
	getContentPane().setLayout(null);
	
	JLabel lblChooseSurveyFrom = new JLabel("Choose survey from drop down");
	lblChooseSurveyFrom.setBounds(127, 11, 183, 14);
	getContentPane().add(lblChooseSurveyFrom);
	
	JComboBox comboBox = new JComboBox(surveyList);
	comboBox.setBounds(127, 36, 152, 20);
	getContentPane().add(comboBox);
}
}