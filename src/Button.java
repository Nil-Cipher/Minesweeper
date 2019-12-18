import javax.swing.*;
/**
 * This is a subclass of JButton with added methods related to Minesweeper.
 * 
 * @author Wanda Song
 */
public class Button extends JButton {
	
	//boolean variables that determine the characteristics of a Button
	private boolean isBomb=false,pressed=false,flagged=false;
	private int x,y;//coordinates of Button
	
	//constructors
	public Button()
	{
		super();
	}
	public Button(Icon pic)
	{
		super(pic);
	}
	
	public void makeBomb(){
		isBomb=true;
	} 
	
	public void removeBomb()
	{
		isBomb=false;
	}
	
	public boolean isBomb()
	{
		return isBomb;
	}
	public void pressed()
	{
		pressed=true;
	}
	
	public boolean isPressed()
	{
		return pressed;
	}
	
	public boolean isFlagged()
	{
		return flagged;
	}
	
	//reverses boolean flagged
	public boolean setFlag()
	{
		if(flagged==false)
			return flagged=true;
		else
			return flagged=false;
	}
	
	public void setXY(int hori, int verti)
	{
		x=hori;	
		y=verti;
	}
	
	public int getHori()
	{
		return x;
	}
	
	public int getVerti()
	{
		return y;
	}
}
