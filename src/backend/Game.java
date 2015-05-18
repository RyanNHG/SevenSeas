package backend;

public class Game 
{
	//	INTERFACES
	private Display display;
	private Input input;
	
	//	pressedKeys
	private boolean[] pressedKeys = {false, false, false, false, false};

	//	PLAYER
	private Player player;
	
	public Game()
	{
		player = new Player();
	}
	
	public void setDisplay(Display display)
	{
		this.display = display;
	}
	
	public void setInput(Input input)
	{
		this.input = input;
	}
	
	public void start()
	{
		if(display == null) return;
		else if(input == null)
		{
			display.print("Can't start game without input!");
		}
		else
		{
			display.print("Game started");
		}
	}

	public void tileClicked(int x, int y) 
	{
		display.print("tile [" + x + ", " + y + "] clicked.");
		
		if (player.move(x,y))
			display.movePlayer(x,y,player.getDir());
	}
	
	public void keyDown(int key)
	{
		display.print("keydown: " + key);
		pressedKeys[key] = true;
	}
	
	public void keyUp(int key)
	{
		pressedKeys[key] = false;
		display.print("keyup: " + key);
	}
}
