package surveysoftware;
import javax.swing.JFrame;


public class SurvGive extends JFrame {
	
	String surveyName;
	survey_actions mySurvey;
	
	public SurvGive(String s) 
	{
		surveyName = s;
		mySurvey = new survey_actions();
		//initGUI();
	}
	public void initGUI()
	{
		setResizable(false); //user not allowed to resize
		setSize(667,660);
		getContentPane().setLayout(null);
		setVisible(true);
	}
}

