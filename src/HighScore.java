import java.io.IOException;

import javax.swing.JOptionPane;
/**
 * This class reads and sets highscores for Easy, Medium, and Hard mode of Minesweeper.
 * 
 * @author Wanda Song
 */
public class HighScore {

	/**
	 * This program reads the highscore of Easy mode.
	 * @return String formatted like a clock (00:00)
	 */
	public static String getEasy()
	{
		String highscore="HighScores\\easyScore.txt";
		int minute, second,marker;
		String time="", line;
		try
		{
			IO.openInputFile(highscore);
			line=IO.readLine();
			
			marker = line.indexOf('%');
			
			minute= Integer.parseInt(line.substring(0,marker));
			second = Integer.parseInt(line.substring(marker+1,line.length()));
			time=Useful.clock(minute, second);
		    IO.closeInputFile();
	    }
	    catch (IOException e)
	    {
	    }
		return time;
	}
	
	/**
	 * This program reads the highscore of Medium mode.
	 * @return String formatted like a clock (00:00)
	 */
	public static String getMedium()
	{
		String highscore="HighScores\\mediumScore.txt";
		int minute, second,marker;
		String time="", line;
		try
		{
			IO.openInputFile(highscore);
			line=IO.readLine();
			
			marker = line.indexOf('%');
			
			minute= Integer.parseInt(line.substring(0,marker));
			second = Integer.parseInt(line.substring(marker+1,line.length()));
			time=Useful.clock(minute, second);
		    IO.closeInputFile();
	    }
	    catch (IOException e)
	    {
	    }
		return time;
	}
	
	/**
	 * This program reads the highscore of Hard mode.
	 * @return String formatted like a clock (00:00)
	 */
	public static String getHard()
	{
		String highscore="HighScores\\hardScore.txt";
		int minute, second,marker;
		String time="", line;
		try
		{
			IO.openInputFile(highscore);
			line=IO.readLine();
			
			marker = line.indexOf('%');
			
			minute= Integer.parseInt(line.substring(0,marker));
			second = Integer.parseInt(line.substring(marker+1,line.length()));
			time=Useful.clock(minute, second);
		    IO.closeInputFile();
	    }
	    catch (IOException e)
	    {
	    }
		return time;
	}

	/**
	 * This method sets the highscore of Easy mode.
	 * @param minute
	 * @param second
	 */
	public static void setEasy(int minute, int second)
	{
		String highscore="HighScores\\easyScore.txt";
		int mins=0, secs=0,marker;
		String line;
		try
		{
			IO.openInputFile(highscore);
			line=IO.readLine();
			
			marker = line.indexOf('%');
			
			mins= Integer.parseInt(line.substring(0,marker));
			secs = Integer.parseInt(line.substring(marker+1,line.length()));
		    IO.closeInputFile();
	    }
	    catch (IOException e)
	    {
	    }
		
		//checks if new score is lower than old highscore
		//only changes file if score is lower
		if(mins>minute)
		{
			JOptionPane.showMessageDialog(null,"You have set a new highscore: "+Useful.clock(minute, second));
			IO.createOutputFile(highscore);;
			IO.print(minute+"%"+second);
		    IO.closeOutputFile();
		}else if(mins==minute&&secs>second)
		{
			JOptionPane.showMessageDialog(null,"You have set a new highscore: "+Useful.clock(minute, second));
			IO.createOutputFile(highscore);;
			IO.print(minute+"%"+second);
		    IO.closeOutputFile();
		}
	}
	
	/**
	 * This method sets the highscore of Medium mode.
	 * @param minute
	 * @param second
	 */
	public static void setMedium(int minute, int second)
	{

		String highscore="HighScores\\mediumScore.txt";
		int mins=0, secs=0,marker;
		String line;
		try
		{
			IO.openInputFile(highscore);
			line=IO.readLine();
			
			marker = line.indexOf('%');
			
			mins= Integer.parseInt(line.substring(0,marker));
			secs = Integer.parseInt(line.substring(marker+1,line.length()));
		    IO.closeInputFile();
	    }
	    catch (IOException e)
	    {
	    }

		//checks if new score is lower than old highscore
		//only changes file if score is lower
		if(mins>minute)
		{
			JOptionPane.showMessageDialog(null,"You have set a new highscore: "+Useful.clock(minute, second));
			IO.createOutputFile(highscore);;
			IO.print(minute+"%"+second);
		    IO.closeOutputFile();
		}else if(mins==minute&&secs>second)
		{
			JOptionPane.showMessageDialog(null,"You have set a new highscore: "+Useful.clock(minute, second));
			IO.createOutputFile(highscore);;
			IO.print(minute+"%"+second);
		    IO.closeOutputFile();
		}
	}
	
	/**
	 * This method sets the highscore of Hard mode.
	 * @param minute
	 * @param second
	 */
	public static void setHard(int minute, int second)
	{

		String highscore="HighScores\\hardScore.txt";
		int mins=0, secs=0,marker;
		String line;
		try
		{
			IO.openInputFile(highscore);
			line=IO.readLine();
			
			marker = line.indexOf('%');
			
			mins= Integer.parseInt(line.substring(0,marker));
			secs = Integer.parseInt(line.substring(marker+1,line.length()));
		    IO.closeInputFile();
	    }
	    catch (IOException e)
	    {
	    }

		//checks if new score is lower than old highscore
		//only changes file if score is lower
		if(mins>minute)
		{
			JOptionPane.showMessageDialog(null,"You have set a new highscore: "+Useful.clock(minute, second));
			IO.createOutputFile(highscore);;
			IO.print(minute+"%"+second);
		    IO.closeOutputFile();
		}else if(mins==minute&&secs>second)
		{
			JOptionPane.showMessageDialog(null,"You have set a new highscore: "+Useful.clock(minute, second));
			IO.createOutputFile(highscore);;
			IO.print(minute+"%"+second);
		    IO.closeOutputFile();
		}
	}
}
