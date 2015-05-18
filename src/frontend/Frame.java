package frontend;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.Timer;

import backend.Display;
import backend.Global;

public class Frame extends JFrame implements Display
{
	//	SCREEN SIZE
	private static final int		SCREEN_WIDTH = Toolkit.getDefaultToolkit().getScreenSize().width,
									SCREEN_HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().height;

	private static final Color[] 	colors = {	new Color(0x00,0x00,0x00),
												new Color(0x66,0x99,0xFF)};
	
	//	INPUT LISTENER
	private Keyboard				keyboard;
	
	//	TILE GRID
	private JLayeredPane 			grid;
	private JButton[][]				tiles;
	private static final int		GRID_WIDTH = 800,
									GRID_HEIGHT = 450,
									GRID_X = 0,
									GRID_Y = 0,
									GRID_MARGIN = 4;
	public static final int			TILE_SIZE = GRID_HEIGHT/Global.NUM_ROWS;
	
	private JButton					player;
	

	//	FRAME SETTINGS
	private static final String 	FRAME_TITLE = "SevenSeas";
	private static final boolean 	FRAME_RESIZABLE = false,
									FRAME_UNDECORATED = false;
	private static final int		FRAME_WIDTH = GRID_WIDTH,
									FRAME_HEIGHT = GRID_HEIGHT,
									FRAME_X = (SCREEN_WIDTH-FRAME_WIDTH)/2,
									FRAME_Y = (SCREEN_HEIGHT-FRAME_HEIGHT)*0;

	//	IMAGE LOADING
	private static final String[]	images = {	"img/player.png",
												"img/island.png" };

	private static final int 		TIMER_DELAY = 500;
	
	
	
	public Frame(Keyboard kb)
	{
		this.keyboard = kb;
		this.initFrame();
		this.initGrid();
		this.initPlayer();
	}

	/**
	 * initFrame()
	 */
	private void initFrame() 
	{
		this.setTitle(Frame.FRAME_TITLE);
		this.setBounds(FRAME_X, FRAME_Y, FRAME_WIDTH, FRAME_HEIGHT);
		this.setResizable(FRAME_RESIZABLE);
		this.setUndecorated(FRAME_UNDECORATED);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.addKeyListener(keyboard);
		this.setLayout(null);
		this.setIconImage(getImage(Global.IMG_PLAYER));
		
		this.setVisible(true);

		final Insets 	insets = this.getInsets();
		final int 		horizontalInsets = insets.left + insets.right,
						verticalInsets = insets.top + insets.bottom;
		
		this.setSize(this.getWidth() + horizontalInsets,
						this.getHeight() + verticalInsets);
	}
	
	private void initGrid()
	{
		this.grid = new JLayeredPane();
		this.tiles = new JButton[Global.NUM_COLS][Global.NUM_ROWS];
		this.grid.setBounds(GRID_X, GRID_Y, GRID_WIDTH, GRID_HEIGHT);
		this.grid.setBackground(colors[Global.BG_COLOR]);
		
		for(int x = 0; x < Global.NUM_COLS; x++)
		{
			for(int y = 0; y < Global.NUM_ROWS; y++)
			{
				tiles[x][y] = new JButton();
				tiles[x][y].setBounds(x*Frame.TILE_SIZE+Frame.GRID_MARGIN, y*Frame.TILE_SIZE+Frame.GRID_MARGIN, Frame.TILE_SIZE, Frame.TILE_SIZE);
				tiles[x][y].setBackground(Frame.colors[Global.SEA_BLUE]);
				tiles[x][y].setMargin(new Insets(0,0,0,0));
				tiles[x][y].setBorder(null);
				tiles[x][y].setBorderPainted(false);
				tiles[x][y].setFocusable(false);
				tiles[x][y].setFocusPainted(false);
				tiles[x][y].addActionListener(keyboard);
				tiles[x][y].addKeyListener(keyboard);
				
				this.grid.add(tiles[x][y], 0);
			}
		}
		
		this.add(grid);
		this.repaint();
	}
	
	private void initPlayer()
	{
		player = new JButton();
		
		player.setBounds(Global.PLAYER_INIT_X*TILE_SIZE+this.GRID_MARGIN, Global.PLAYER_INIT_Y*TILE_SIZE+this.GRID_MARGIN, TILE_SIZE, TILE_SIZE);
		player.setBackground(Frame.colors[Global.SEA_BLUE]);
		player.setIcon(new ImageIcon(getImage(Global.IMG_PLAYER)));
		player.setMargin(new Insets(0,0,0,0));
		player.setBorder(null);
		player.setBorderPainted(false);
		
		this.grid.add(player, 1);
		this.repaint();
	}
	
	
	public void drawGrid(int[][] values)
	{
		for(int x = 0; x < values.length; x++)
		{
			for(int y = 0; y < values[0].length; y++)
			{
				ImageIcon icon = new ImageIcon(getImage(values[x][y]));
				tiles[x][y].setIcon(icon);
			}
		}
	}
	
	public void movePlayer(final int x, final int y, int dir)
	{
		final int initX = player.getX(),
					initY = player.getY();
		Timer timer;
		timer = new Timer(TIMER_DELAY, new ActionListener(){

			public void actionPerformed(ActionEvent event) {
				player.setLocation(player.getX() + (x*TILE_SIZE - initX)/10,
									player.getY() + (y*TILE_SIZE - initY)/10);
			}
			
		});
		
		player.setLocation(x*TILE_SIZE, y*TILE_SIZE);
	}
	
	private BufferedImage getImage(int i) 
	{
		if(i < 0) return null;
		
		BufferedImage image = null;
		
		try{
			image = ImageIO.read(new File(images[i]));
			return getScaledImage(image, TILE_SIZE, TILE_SIZE);
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	private BufferedImage getScaledImage(BufferedImage src, int w, int h)
	{
	    int finalw = w;
	    int finalh = h;
	    double factor = 1.0d;
	    if(src.getWidth() > src.getHeight()){
	        factor = ((double)src.getHeight()/(double)src.getWidth());
	        finalh = (int)(finalw * factor);                
	    }else{
	        factor = ((double)src.getWidth()/(double)src.getHeight());
	        finalw = (int)(finalh * factor);
	    }   

	    BufferedImage resizedImg = new BufferedImage(finalw, finalh, BufferedImage.TRANSLUCENT);
	    Graphics2D g2 = resizedImg.createGraphics();
	    g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
	    g2.drawImage(src, 0, 0, finalw, finalh, null);
	    g2.dispose();
	    return resizedImg;
	}
	
	
	//	DEBUGGING
	public void print(String message)
	{
		System.out.println(message);
	}
}
