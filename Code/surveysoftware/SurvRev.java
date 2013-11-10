package surveysoftware;
import javax.swing.JFrame;


public class SurvRev extends JFrame {
	
	String surveyName;
	
	public SurvRev(String s) {
		surveyName = s;
		//initGUI();
	}
	public void initGUI()
	{
		setSize(684, 704);
		setResizable(false);
		getContentPane().setLayout(null);
		setVisible(true);
	}

}
