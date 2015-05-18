package frontend;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;

import backend.Game;
import backend.Global;
import backend.Input;

public class Keyboard implements KeyListener, ActionListener, Input
{
	private Game game;
	
	public Keyboard(Game game)
	{
		this.game = game;
	}
	
	public void actionPerformed(ActionEvent event) 
	{
		JButton button = (JButton)event.getSource();
		game.tileClicked(button.getX()/Frame.TILE_SIZE, button.getY()/Frame.TILE_SIZE);
	}
			
	@Override
	public void keyPressed(KeyEvent event) 
	{
		int key = getGameKey(event.getKeyCode());
		
		if(key != -1)
			game.keyDown(key);
	}

	@Override
	public void keyReleased(KeyEvent event) 
	{
		int key = getGameKey(event.getKeyCode());
		
		if(key != -1)
			game.keyUp(key);	
	}
	
	private  int getGameKey(int realKey)
	{
		int key = -1;
		
		switch(realKey)
		{
		case KeyEvent.VK_SPACE: key = Global.KEY_FIRE; break;
		case KeyEvent.VK_UP: 	key = Global.KEY_UP; break;
		case KeyEvent.VK_DOWN: 	key = Global.KEY_DOWN; break;
		case KeyEvent.VK_LEFT: 	key = Global.KEY_LEFT; break;
		case KeyEvent.VK_RIGHT: key = Global.KEY_RIGHT; break;
		}
		
		return key;
	}

	@Override
	public void keyTyped(KeyEvent event) 
	{
		
	}

}
