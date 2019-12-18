import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;
import javax.swing.*;
import java.util.Timer;
import java.util.TimerTask;
/**
 * @author Wanda Song
 * January 22th, 2018
 *
 *This is a imitation of the classic Minesweeper game from Microsoft. This is the main class
 *where everything will be instantiated and run. The program uses classic minesweeper icons,
 *and is coded according to the minesweeper mechanics.
 */
public class Mines extends MouseAdapter{ //MouseAdapter is used instead of Mouselistener because mouselistener is
										 //an abstract class that requires the implementation of all methods
										 //whether you use them or not. MouseAdapter implements all those methods
										 //for you, and you only need to override the method you need.

	private Button[][] mines;  //2D Button array, for details on the Button class, see Button class
	private Queue<Button> zeros = new LinkedList<Button>();//A Queue for the cascade function in minesweeper, 
														   //where a click on a blank square will reveal all	
														   //surrounding squares
	private JButton timer, remainingMines,difficulty; //JButton instantiation for three buttons on the timerPanel
	private JPanel buttonPanel,timerPanel,contentPanel; //instantiation of JPanels. contentPanel is the base JPanel
	private int numMines,returnValue=-1,n,remainMine,seconds=0,minutes=0,noMinex,noMiney; 
	//numMines records the number of mines.
	//returnValue stores the choice the user made on the difficulty JOptionPane
	//n is the length of the grid
	//remainMine stores the remaining mines left
	//seconds and minutes are variables for the timer
	//noMinex and noMiney stores the coordinates of the first square clicked by the user
	private String[] buttons = { "Easy", "Medium", "Hard" }; //String array that contains the three difficulty options
	private JFrame frame=new JFrame();//instantiate new JFrame
	private Font font =new Font("arial", Font.BOLD, 14);//set font for JButtons
	private Timer clock;//timer class to keep track of time
	private boolean firstPress=false;//boolean variable that stores if it is the user's first click or not 
									 //for more info see: mouseReleased left mouse button methods
	private static int numGames=0; //static variable to keep track of the number of games,
								   //program displays instructions on the first game


	public Mines()
	{	 
		//asks user to choose difficulty
		 returnValue = JOptionPane.showOptionDialog(null, "Choose your difficulty:", "Level Option",
				 JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, buttons, buttons[1]);

		 //instantiate different sized grids for each choice
		switch(returnValue)
		{
			case 0: mines= new Button[11][11];
					n=11;
					numMines=10;
					//JOptionPane.showMessageDialog(null,"The current highscore is: "+HighScore.getEasy());
					break;
			case 1: mines= new Button[18][18];
					n=18;
					numMines=40;
					//JOptionPane.showMessageDialog(null,"The current highscore is: "+HighScore.getMedium());
					break;
			case 2: mines= new Button[26][26];
					n=26;
					numMines=99;
					//JOptionPane.showMessageDialog(null,"The current highscore is: "+HighScore.getHard());
					break;
			case -1:System.exit(0);
					break;
		}
		remainMine=numMines;
		buttonPanel=new JPanel();
		buttonPanel.setSize(n*25,n*25);  //each square is 25 pixels
		buttonPanel.setLayout(new GridLayout(n,n,0,0)); //nxn grid with no space
		buttonPanel.setBackground(Color.BLACK); //Allows empty space to be black
		
		timerPanel=new JPanel();
		timerPanel.setSize(n*25,90);
		timerPanel.setLayout(new BoxLayout(timerPanel, BoxLayout.LINE_AXIS));//box layout filling from left to right
		timerPanel.setBackground(Color.black); 
		
		//button instantiation...
		timer = new JButton("Time");
		timer.setAlignmentX(Component.LEFT_ALIGNMENT);
		timer.setFont(font);
		timer.setOpaque(true);
		
		remainingMines = new JButton(Integer.toString(remainMine));
		remainingMines.setAlignmentX(Component.RIGHT_ALIGNMENT);
		remainingMines.setFont(font);
		remainingMines.setOpaque(true);
		
		difficulty = new JButton("Change Difficulty");
		difficulty.setAlignmentX(Component.CENTER_ALIGNMENT);
		difficulty.setFont(font);
		difficulty.addMouseListener(this);
		difficulty.setOpaque(true);

		//add the three buttons to the timerPanel
		timerPanel.add(timer);
		timerPanel.add(Box.createHorizontalGlue());//glue for even spacing
		timerPanel.add(difficulty);
		timerPanel.add(Box.createHorizontalGlue());
		timerPanel.add(remainingMines);
		
		//instantiation of mines
		for (int k = 0; k <mines.length; k++) 
		{
			for(int j = 0; j <mines.length; j++)
			{
				//a outer layer of non-visible buttons prevents array out of bound errors,
				//and makes the cascade method easier to code
				if(k==0||k==mines.length-1||j==0||j==mines.length-1)
				{
					mines[k][j]=new Button();
					mines[k][j].setXY(j, k);
					mines[k][j].setVisible(false);
					buttonPanel.add(mines[k][j]);
				}else{ //instantiation of normal buttons
					ImageIcon icon= new ImageIcon("Images/blank.png");
					Image img = icon.getImage() ;  
					Image newimg = img.getScaledInstance( 25, 25,  java.awt.Image.SCALE_SMOOTH ) ;  
					icon = new ImageIcon( newimg );
					mines[k][j]=new Button(icon);
					mines[k][j].setXY(j, k);
					mines[k][j].setFont(font);
					//many variables need to be set to null to make the graphics prettier
					mines[k][j].setMargin(new Insets(0, 0, 0, 0));
					mines[k][j].setBorder(null);
					mines[k][j].setBackground(null);
					mines[k][j].addMouseListener(this);
					buttonPanel.add(mines[k][j]);    //Add buttons to the grid layout of 
													   //buttonPanel
				}
			}
		}
		
		contentPanel=new JPanel();
		contentPanel.setSize(n*25,n*25+90);
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.PAGE_AXIS));//box layout filling from top to bottom
		contentPanel.add(timerPanel);
		contentPanel.add(buttonPanel);
		
		frame.setSize(n*25,n*25+90);
		frame.add(contentPanel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);//resizable is false because the images on the buttons are not scalable on the fly,
								  //so resizing will mess up grapics
		frame.setVisible(true);
		if(numGames==0)//if it is first game, display instructions
		{
			JOptionPane.showMessageDialog(null,"Left click to reveal a square. Right click to flag a mine.\n"
					+ "If you think you flagged all the mines around a square,\n"
					+ "left-right click that square, and all the non-mines will be revealed."
					+ "\nBeware that if you flagged the wrong mines,the game will end."
					+ "\nThe top right button shows the remaining number of mines."
					+ "\nGood luck!");
		}
		timer();//starts timer after user closes instructions
	}
	
	public static void main(String[] args) {
		
		new Mines();               //Run constructor for class
	}

	
	//mouselistener method, detects when mouse is released
	public void mouseReleased(MouseEvent e) 
	{
		//if both right and left mousebuttons are clicked at once
		//the method checks if one of the squares are clicked, 
		//and if true, calls the rightLeftClick method with the coordinates of the square clicked
		
		//note: arrays display the y-coordinate before the x (array[y][x]),
		//so you need to reverse the variabels when sending them through a method
		if(SwingUtilities.isLeftMouseButton(e)&&SwingUtilities.isRightMouseButton(e))
		{
			//double for loop to check all visible squares
			for (int k = 1; k <mines.length-1; k++) 
			{
				for(int j = 1; j <mines.length-1; j++)
				{
					if(e.getSource() == mines[k][j])
					{
						rightLeftClick(j,k);//notice the reversed variables
						win();//check if user won 
					}
				}
			}
		//if left mousebutton is released
		}else if(SwingUtilities.isLeftMouseButton(e))
		{
			//this changes the difficulty, but it basically instantiates a new game
			//the difficulty button is checked first for faster performance
			if(e.getSource() == difficulty)
			{
				numGames++;
				frame.dispose();
		    	new Mines();
			}
			
			//double for-loop for all the squares
			for (int k = 1; k <mines.length-1; k++) 
			{
				for(int j = 1; j <mines.length-1; j++)
				{
					if(e.getSource() == mines[k][j])
					{
						//If it's the first square pressed, the coordinates of the square is recorded
						//and then the mines are added. The addMines method ensures that the square clicked 
						//will be a zero, so the first click will almost always invoke a cascade.
						//This prevents the irritation of clicking on a mine first try
						if(firstPress==false)
						{
							noMinex=j;
							noMiney=k;
							addMines();
							firstPress=true;
						//if the square is not flagged, the reveal method is called
						//and the win method checks if the user won or not
						}if(mines[k][j].isFlagged()==false)
						{
							reveal(j,k); 
							win();
						}
					}
				}
			}
		}else if(SwingUtilities.isRightMouseButton(e))
		{
			//checks all visible squares
			for (int k = 1; k <mines.length-1; k++) 
			{
				for(int j = 1; j <mines.length-1; j++)
				{
					if(e.getSource() == mines[k][j])
					{
						//boolean variables to see if the square is already flagged, and if it is revealed or not
						boolean check=mines[k][j].isFlagged();
						boolean press=mines[k][j].isPressed();
						if(check==false&&press==false)//if square is not revealed and not flagged, set flag
						{
							remainMine--;
							mines[k][j].setFlag();
							//rescales and sets image
							mines[k][j].setIcon(null);
							ImageIcon icon= new ImageIcon("Images/flag.png");
							Image img = icon.getImage() ;  
							Image newimg = img.getScaledInstance( 25, 25,  java.awt.Image.SCALE_SMOOTH ) ;  
							icon = new ImageIcon( newimg );
							mines[k][j].setIcon(icon);

						}
						if(check==true&&press==false)//if square is not revealed but flagged, remove flag
						{
							remainMine++;
							mines[k][j].setFlag();
							//rescales and sets image
							mines[k][j].setIcon(null);
							ImageIcon icon= new ImageIcon("Images/blank.png");
							Image img = icon.getImage() ;  
							Image newimg = img.getScaledInstance( 25, 25,  java.awt.Image.SCALE_SMOOTH ) ;  
							icon = new ImageIcon( newimg );
							mines[k][j].setIcon(icon);
						}
						//updates the remaining mines button
						remainingMines.setText(Integer.toString(remainMine));
					}
				}
			}
		}//end is right mouse button
	}//end mouseReleased method
	
	/**
	 * This method gets the coordinates of a square.
	 * It checks if the 8 squares around it are correctly flagged with mines.
	 * 
	 * If all mines are correctly flagged, it reveals the remaining non-mines in the area
	 * 
	 * If the number of squares flagged is less than the number of Mines, i.e. not all mines are flagged,
	 * it does nothing.
	 * 
	 * If more buttons are flagged than there are number of mines, 
	 * or the right number of mines are flagged, but the flags are in the wrong places
	 * the method treats it as clicking on a mine, and the program ends.
	 * 
	 * @param x - x coordinates of button pressed
	 * @param y - y coordinates of button pressed
	 */
	private void rightLeftClick(int x, int y)
	{
		//check if clicked square is revealed, else won't run
		if(mines[y][x].isPressed()==true)
		{
			//int variables, numMines - mines around the square
			//numFlagged - total number of squares flagged
			//mineFlagged - number of flagged squares that are correct
			int numBomb=calculateNumMines(x,y),numFlagged=0,mineFlagged=0;
			//double for-loop to check the 8 squares around the clicked square
			for(int i=y-1;i<=y+1;i++)
			{
				for(int j=x-1;j<=x+1;j++)
				{
					//if the square is flagged, increment numFlagged; 
					//the "!(i==y&&j==x)" prevents the center square from being counted
					if(mines[i][j].isFlagged()==true&&!(i==y&&j==x))
					{
						numFlagged++;
						if(mines[i][j].isBomb()==true)//if the flagged square is a bomb, increment mineFlagged 
							mineFlagged++;
					}
						
				}
			}
			//if flagged squares is exactly the number of mines, and they are all in the correct places
			//reveal remaining squares
			if(numBomb==mineFlagged&&numBomb==numFlagged)
			{
				for(int i=y-1;i<=y+1;i++)
				{
					for(int j=x-1;j<=x+1;j++)
					{
						if(!(i==y&&j==x)&&i!=0&&i!=mines.length-1&&j!=0&&j!=mines.length-1)
							reveal(j,i);
					}
				}
			}else if(numFlagged>mineFlagged)
			{
				//if total flagged squares is more than correctly flagged squares
				//and total flagged squares is more than or equal to the number of mines
				//reveal mines and run lose() method 
				if(numBomb<=numFlagged)
				{
					for(int i=y-1;i<=y+1;i++)
					{
						for(int j=x-1;j<=x+1;j++)
						{
							if(!(i==y&&j==x)&&mines[i][j].isBomb()==true)
							{
								mines[i][j].setIcon(null);
								mines[i][j].setBackground(Color.RED);
								ImageIcon icon= new ImageIcon("Images/mine.png");
								Image img = icon.getImage() ;  
								Image newimg = img.getScaledInstance( 25, 25,  java.awt.Image.SCALE_SMOOTH ) ;  
								icon = new ImageIcon( newimg );
								mines[i][j].setIcon(icon);
							}
						}
					}
					lose();
				}
			}
		}		
	}//end method rightLeftClick
	
	/**
	 * This method adds mines to the grid randomly based on two random integers generated 
	 * from the Useful class. It avoids placing any mines in and around the first clicked square.
	 */
	private void addMines()
	{
		int count=0,random,random2;
		while(count<numMines)
		{
			//two random ints
			random=Useful.getRandomInt(1, n-2);
			random2=Useful.getRandomInt(1, n-2);
			//if square at random coordinates is bomb-free, add bomb and increment counter
			if(mines[random][random2].isBomb()==false)
			{
				mines[random][random2].makeBomb();
				count++;
				//check if squares around first-click square has bombs, 
				//remove them if there are any, decrease counter
				for(int i=noMinex-1;i<=noMinex+1;i++)
				{
					for(int j=noMiney-1;j<=noMiney+1;j++)
					{
						if(j==random&&i==random2)
						{
							count--;
							mines[random][random2].removeBomb();
						}
					}
				}
			}
		}
		//revealAll(); //this was used for testing
	}//end method addMines
	
	
	/**
	 * This was used as a test method to reveal all the squares and mines
	 * for bug testing. It has now retired.
	 */
	private void revealAll()
	{
		for (int k = 1; k <mines.length-1; k++) 
		{
			for(int j = 1; j <mines.length-1; j++)
			{
				if(mines[j][k].isBomb()==true)
				{
					mines[j][k].setIcon(null);
					mines[j][k].setBackground(Color.LIGHT_GRAY);
					ImageIcon icon= new ImageIcon("Images/mine.png");
					Image img = icon.getImage() ;  
					Image newimg = img.getScaledInstance( 25, 25,  java.awt.Image.SCALE_SMOOTH ) ;  
					icon = new ImageIcon( newimg );
					mines[j][k].setIcon(icon);
				}else if(calculateNumMines(k,j)!=0){
					int numberMines=calculateNumMines(k,j);
					setNumber(k,j,numberMines);
				}else{
					mines[j][k].setIcon(null);
					ImageIcon icon= new ImageIcon("Images/gray.png");
					Image img = icon.getImage() ;  
					Image newimg = img.getScaledInstance( 25, 25,  java.awt.Image.SCALE_SMOOTH ) ;  
					icon = new ImageIcon( newimg );
					mines[j][k].setIcon(icon);
				}
			}
		}
	}//end method revealAll
	
	/**
	 * This method checks if the user has won.
	 * If the unpressed squares equal the number of mines, 
	 * the user wins. This method prevents cheaty winning
	 * like randomly placing flags until the user
	 * hits the right combination.
	 */
	private void win()
	{
		int count=0;
		//count number of unpressed squares
		for (int k = 1; k <mines.length-1; k++) 
		{
			for(int j = 1; j <mines.length-1; j++)
			{
				if(mines[k][j].isPressed()==false)
				{
					count++;
				}
			}
		}
		
		//if user wins
		if(count==numMines){
			clock.cancel();//cancel timer
			clock.purge();
			//record highscore
			/*if(n==11)
				HighScore.setEasy(minutes, seconds);
			if(n==18)
				HighScore.setMedium(minutes, seconds);
			if(n==26)
				HighScore.setHard(minutes, seconds);*/
			JOptionPane.showMessageDialog(null,"You won! Your score is "+Useful.clock(minutes, seconds)+".");
			
			//ask user to play again
			int reply = JOptionPane.showConfirmDialog(null, "Do you want to play again?", "Replay", JOptionPane.YES_NO_OPTION);
		    if (reply == JOptionPane.YES_OPTION) {
		    	numGames++; //increment number of games int
		    	frame.dispose();//remove old JFrame
		    	new Mines();//make new game
		    }else {
		    	System.exit(0);
		    }
		}
	}//end method win
	
	/**
	 * This method is related to the cascade method.
	 * 
	 * It removes a Button from the Queue 'zeros', 
	 * and checks if there are zero mines around it.
	 * If the Button is a zero, reveal the eight squares around it
	 * and add them to the Queue. The cycle repeats until 
	 * there are no more elements in the Queue.
	 */
	private void checkQueue()
	{
		boolean queueNull=false;
		//while there are still objects in the Queue...
		while(queueNull==false)
		{
			try{
				Button mine=zeros.peek();//instantiate a Button to the first element in the Queue
				zeros.remove();//remove the first element in the Queue
				int x=mine.getHori();
				int y=mine.getVerti();
				int numberMines=calculateNumMines(x,y);
				
				//if the Button has zero mines around it
				//reveal the eight squares around it 
				//add revealed squares to the Queue
				if(numberMines==0)
				{
					for(int i=x-1;i<=x+1;i++)
					{
						for(int j=y-1;j<=y+1;j++)
						{
							if(i!=0&&i!=mines.length-1&&j!=0&&j!=mines.length-1)//check if button is out of bounds
							{
								if(mines[j][i].isPressed()==false)
								{
									reveal(i,j);
									zeros.add(mines[j][i]);					
								}
							}
						}
					}		
				}
			}catch(NoSuchElementException e){ //when the Queue is empty, this catches the error
				queueNull=true;//sets queueNull to true, which breaks the while-loop, and the program ends
			}
		}	
	}//end method checkQueue
	
	/**
	 * This method reveals a square.
	 * If the square is a bomb, the lose() method is called
	 * If the square is not a bomb and not flagged, the right picture is set to the Button.
	 * 
	 * @param x -x coordinates of button
	 * @param y -y coordinates of button
	 */
	private void reveal(int x,int y)
	{
		if(mines[y][x].isBomb()==true&&mines[y][x].isFlagged()==false)
		{
			mines[y][x].pressed();
			//set icon to mine
			mines[y][x].setIcon(null);
			mines[y][x].setBackground(Color.RED);
			ImageIcon icon= new ImageIcon("Images/mine.png");
			Image img = icon.getImage() ;  
			Image newimg = img.getScaledInstance( 25, 25,  java.awt.Image.SCALE_SMOOTH ) ;  
			icon = new ImageIcon( newimg );
			mines[y][x].setIcon(icon);
			lose();
		}else if(mines[y][x].isBomb()==false&&mines[y][x].isFlagged()==false){
				mines[y][x].setIcon(null);
				mines[y][x].pressed();
				int numberMines=calculateNumMines(x,y);
				setNumber(x,y,numberMines);
				if(numberMines==0)
				{
					zeros.add(mines[y][x]); //add square to Queue
					checkQueue();//calls checkQueue method to see if a cascade is availiable
				}	
		}
	}
	/**
	 * This method sets the icon of a Button to a number based on how many mines are surrounding it.
	 * 
	 * @param x -x coordinates of button
	 * @param y -y coordinates of button
	 * @param numMines -number of mines surrounding that Button
	 */
	private void setNumber(int x, int y,int numMines)
	{
		String image="";
		switch(numMines)
		{
			case 0: image="Images/gray.png";
					break;
			case 1: image="Images/one.png";
					break;
			case 2: image="Images/two.png";
					break;
			case 3: image="Images/three.png";
					break;
			case 4: image="Images/four.png";
					break;
			case 5: image="Images/five.png";
					break;
			case 6: image="Images/six.png";
					break;
			case 7: image="Images/seven.png";
					break;
			case 8: image="Images/eight.png";
					break;
		}
		ImageIcon icon= new ImageIcon(image);
		Image img = icon.getImage() ;  
		Image newimg = img.getScaledInstance( 25, 25,  java.awt.Image.SCALE_SMOOTH ) ;  
		icon = new ImageIcon( newimg );
		mines[y][x].setIcon(icon);
	}//end method setNumber
	
	/**
	 * This method counts how many mines surround a square.
	 * 
	 * @param x -x coordinates of button
	 * @param y -y coordinates of button
	 * @return integer, number of mines
	 */
	private int calculateNumMines(int x,int y)
	{
		int count=0;	
		for(int i=x-1;i<=x+1;i++)
		{
			for(int j=y-1;j<=y+1;j++)
			{
				if(mines[j][i].isBomb()==true)
					count++;
			}
		}
		return count;
	}
	
	/**
	 * This method is called when the user loses.
	 * It reveals all mines, and asks user if they want to play again.
	 */
	private void lose()
	{
		clock.cancel();//cancel timer
		clock.purge();
		
		//reveal mines
		for (int k = 1; k <mines.length-1; k++) 
		{
			for(int j = 1; j <mines.length-1; j++)
			{
				if(mines[k][j].isBomb()==true&&!(mines[k][j].getBackground()==Color.RED))
				{
					mines[k][j].setIcon(null);
					mines[k][j].setBackground(Color.LIGHT_GRAY);
					ImageIcon icon= new ImageIcon("Images/mine.png");
					Image img = icon.getImage() ;  
					Image newimg = img.getScaledInstance( 25, 25,  java.awt.Image.SCALE_SMOOTH ) ;  
					icon = new ImageIcon( newimg );
					mines[k][j].setIcon(icon);
				}
			}
		}
		JOptionPane.showMessageDialog(null,"You lost!");
		//asks user if they want to play again
		int reply = JOptionPane.showConfirmDialog(null, "Do you want to play again?", "Replay", JOptionPane.YES_NO_OPTION);
	    if (reply == JOptionPane.YES_OPTION) {
	    	numGames++;
	    	frame.dispose();
	    	new Mines();
	    }else {
	    	System.exit(0);
	    }
	}//end method lose
	
	/**
	 * This method instantiates a timer,
	 * and increments a int variable every second.
	 * Acts as a clock.
	 */
	private void timer() {
	    int delay = 1000;
	    int period = 1000;
	    clock = new Timer();
	    clock.scheduleAtFixedRate(new TimerTask() {
	        public void run() {
	        	seconds++;
	        	if(seconds>=60)
	        	{
	        		minutes++;
	        		seconds-=60;
	        	}
	            timer.setText(Useful.clock(minutes, seconds));//the Useful class method formats this into 00:00 form
	        }
	    }, delay, period);
	}//end method timer
}//end class
