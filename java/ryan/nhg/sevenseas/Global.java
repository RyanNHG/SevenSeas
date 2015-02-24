package ryan.nhg.sevenseas;

/**
 * Created by ryan on 2/23/15.
 */
public class Global
{
    public static final int TYPE_EMPTY = 0, TYPE_PLAYER = 1, TYPE_PIRATE = 2, TYPE_WHIRLPOOL = 3, TYPE_OBSTACLE = 4;

    public static final int DIR_UP = 0,         DIR_LEFT = 1,       DIR_DOWN =2,        DIR_RIGHT = 3,      DIR_UP_LEFT = 4,
                            DIR_UP_RIGHT = 5,   DIR_DOWN_LEFT = 6,  DIR_DOWN_RIGHT = 7, NUM_DIRECTIONS = 8;

    public static final int TILE_EMPTY = 0,             TILE_ISLAND = 1,            TILE_WHIRLPOOL = 2,         TILE_WRECKAGE = 3,          TILE_CANNONBALL = 4,
                            TILE_PLAYER_UP = 10,        TILE_PLAYER_LEFT = 11,      TILE_PLAYER_DOWN = 12,      TILE_PLAYER_RIGHT = 13,
                            TILE_PLAYER_UP_LEFT= 14,    TILE_PLAYER_UP_RIGHT= 15,   TILE_PLAYER_DOWN_LEFT= 16,  TILE_PLAYER_DOWN_RIGHT= 17,
                            TILE_PIRATE_UP = 20,        TILE_PIRATE_LEFT = 21,      TILE_PIRATE_DOWN = 22,      TILE_PIRATE_RIGHT = 23,
                            TILE_PIRATE_UP_LEFT= 24,    TILE_PIRATE_UP_RIGHT= 25,   TILE_PIRATE_DOWN_LEFT= 26,  TILE_PIRATE_DOWN_RIGHT= 27;


    public static final int NUM_ROWS = 11, NUM_COLS = 11;

    public static final int INIT_PLAYER_X = NUM_COLS/2;
    public static final int INIT_PLAYER_Y = NUM_ROWS/2;
    public static final int INIT_PLAYER_DIR = DIR_DOWN;
    public static final int CANNON_RANGE = 3;

    //  ISLANDS
    public static final int MAX_ISLANDS = 10;

    //  PIRATES
    public static final int BASE_PIRATES = 2, LEVELS_PER_BASE_INCREASE = 3, INITIAL_DISTANCE_FROM_PLAYER = 3;

    //  PLAYER
    public static final int RESULT_NO_MOVE = 1, RESULT_MOVE = 2, RESULT_DEATH = 3, RESULT_FIRE = 4, RESULT_WHIRLPOOL = 5;

    public static void setImage(SeaTile tile, int image)
    {
        tile.setImage(image);
    }


}
