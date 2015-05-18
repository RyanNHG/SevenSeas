package backend;

public class Global 
{
	//	TILE GRID
	public static final int		NUM_COLS = 11,
								NUM_ROWS = NUM_COLS;
	
	//	PLAYER
	public static final int		PLAYER_INIT_X = NUM_COLS/2,
								PLAYER_INIT_Y = NUM_ROWS/2;
	
	public static final int 	BG_COLOR = 0,
								SEA_BLUE = 1;
	
	public static final int		IMG_PLAYER = 0,
								IMG_ISLAND = 1;
	
	public static final int		KEY_FIRE = 0,
								KEY_UP = 1,
								KEY_DOWN = 2,
								KEY_LEFT = 3,
								KEY_RIGHT = 4;
	
	public static final int		DIR_N = 0,
								DIR_NE = 1,
								DIR_E = 2,
								DIR_SE = 3,
								DIR_S = 4,
								DIR_SW = 5,
								DIR_W = 6,
								DIR_NW = 7;
}
