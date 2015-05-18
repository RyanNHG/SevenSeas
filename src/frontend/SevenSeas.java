package frontend;

import backend.Game;

public class SevenSeas 
{
	public static void main(String args[])
	{
		Game game = new Game();
		Keyboard keyboard = new Keyboard(game);
		Frame frame = new Frame(keyboard);
		
		game.setDisplay(frame);
		game.setInput(keyboard);
		game.start();
	}
}
