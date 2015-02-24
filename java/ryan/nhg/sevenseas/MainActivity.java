package ryan.nhg.sevenseas;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableRow;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.TimerTask;

import ryan.nhg.sevenseas.SeaTile;


public class MainActivity extends Activity {

    public static ButtonListener tileListener;
    private SquareLayout squareLayout;

    SeaTile[][] tiles;
    private Player player;
    private Pirate[] pirates;
    private int numPirates;

    private int level;
    private boolean canClick, tellMeStuff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //  TODO: Main menu

        initSquareLayoutView();
        initPlayer();
        initWorld();

        //  TODO: Save and load game on resume

    }

    private void initSquareLayoutView()
    {
        squareLayout = new SquareLayout(this);
        ViewGroup.LayoutParams params = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        squareLayout.setBackgroundColor(Color.rgb(0,0x66,0xff));//getResources().getDrawable(R.drawable.ic_launcher));
        squareLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        squareLayout.setLayoutParams(params);
        squareLayout.setOrientation(LinearLayout.VERTICAL);

        ((RelativeLayout) findViewById(R.id.mainLayout)).addView(squareLayout);
    }


    private void initPlayer()
    {
        player = new Player();
    }

    public class ButtonListener implements View.OnClickListener
    {
        public void onClick(View view)
        {
            if(canClick) {

                //  Disable clicking
                canClick = false;

                //  Get clicked tile information
                SeaTile clickedTile = (SeaTile) view;
                int playerOldX = player.getX();
                int playerOldY = player.getY();

                switch(player.act(clickedTile))
                {
                    case Global.RESULT_NO_MOVE:
                        canClick = true;
                        return;
                    case Global.RESULT_FIRE:
                        fireCannons();
                        break;
                    case Global.RESULT_MOVE:
                        tiles[playerOldX][playerOldY].setImage(Global.TILE_EMPTY);
                        tiles[player.getX()][player.getY()].setImage(player.getImage());
                        break;
                    case Global.RESULT_WHIRLPOOL:
                        tiles[playerOldX][playerOldY].setImage(Global.TILE_EMPTY);
                        whirlpool();
                        tiles[player.getX()][player.getY()].setImage(player.getImage());
                        break;
                    case Global.RESULT_DEATH:
                        tiles[playerOldX][playerOldY].setImage(Global.TILE_EMPTY);
                        gameOver();
                        return;
                    default:break;
                }

                movePirates();

                if(tellMeStuff) tellMeStuff();
                canClick = true;
            }
        }
    }

    private void whirlpool()
    {
        SeaTile random = getRandomEmptyTile();
        //  TODO: whirlpoolAnimation();

        player.setX(random.getTileX());
        player.setY(random.getTileY());
        player.setDir((int)(Math.random() * Global.NUM_DIRECTIONS));
    }

    private void cannonAnimation(int x, int y)
    {

    }

    private void gameOver()
    {
        canClick=false;
        tiles[player.getX()][player.getY()].setImage(Global.TILE_WRECKAGE);
        Toast.makeText(this, "Game Over!", Toast.LENGTH_LONG).show();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                loadLevel(1);
            }
        }, 2000);
    }

    private void movePirates()
    {
        for(int p = 0; p < pirates.length; p++)
        {
            Pirate pirate = pirates[p];

            if(pirate != null)
            {
                int oldX = pirate.getX();
                int oldY = pirate.getY();

                tiles[pirate.getX()][pirate.getY()].setImage(Global.TILE_EMPTY);
                pirate.moveToward(player.getX(),player.getY());
                //pirate.setDir(findDirection(0, oldX, oldY, pirate.getX(), pirate.getY()));

                //  TODO: moveAnimation();

                SeaTile t = tiles[pirate.getX()][pirate.getY()];

                if(t.isType(Global.TYPE_OBSTACLE))
                {
                    killPirate(p);
                }
                else if(t.isType(Global.TYPE_PIRATE))
                {
                    killPirate(p);
                    for(int p2 = 0; p2 < pirates.length; p2++)
                    {
                        Pirate pirate2 = pirates[p2];
                        if(pirate2 != null && pirate2.getX()==t.getTileX() && pirate2.getY()==t.getTileY())
                            killPirate(p2);
                    }
                    t.setImage(Global.TILE_WRECKAGE);

                }
                else if(t.isType(Global.TYPE_PLAYER))
                {
                    gameOver();
                    canClick = false;
                    return;
                }
                else {
                    tiles[pirate.getX()][pirate.getY()].setImage(pirate.getImage());
                }
            }
        }
        canClick = true;
    }

    public static boolean outOfBounds(int x, int y)
    {
        return (x < 0 || x > Global.NUM_COLS-1 || y < 0 || y > Global.NUM_ROWS - 1);
    }



    private void fireCannons()
    {
        SeaTile[] cannonTiles = getTilesInRange(player.getX(),player.getY(),player.getDir());

        for(int i = 0; i < cannonTiles.length; i++)
        {
            if(cannonTiles[i]!=null) {

                if (cannonTiles[i].isType(Global.TYPE_PIRATE)) {

                    cannonTiles[i].setImage(Global.TILE_WRECKAGE);
                    for (int p = 0; p < pirates.length; p++) {
                        if (pirates[p] != null &&
                                pirates[p].getX() == cannonTiles[i].getTileX() &&
                                pirates[p].getY() == cannonTiles[i].getTileY()) {
                            killPirate(p);
                        }
                    }
                }
            }
        }

    }
    private void killPirate(int p) {

        pirates[p] = null;
        numPirates--;
        tellMeStuff = true;
    }

    private void tellMeStuff()
    {
        if (numPirates == 0) {
            Toast.makeText(this, "Level " + level + " complete!", Toast.LENGTH_SHORT).show();
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    loadLevel(level + 1);
                }
            }, 1000);
        } else if (numPirates == 1) {
            Toast.makeText(this, "" + numPirates + " pirate remaining!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "" + numPirates + " pirates remaining!", Toast.LENGTH_SHORT).show();
        }
        tellMeStuff = false;
    }

    private void loadLevel(int level)
    {
        this.level = level;

        for (int y = 0; y < Global.NUM_ROWS; y++)
            for(int x = 0; x < Global.NUM_COLS; x++)
            {
                tiles[x][y].setImage(Global.TILE_EMPTY);

                if((x==0 || x==Global.NUM_COLS-1)&&(y==0 || y==Global.NUM_ROWS-1))
                    tiles[x][y].setImage(Global.TILE_WHIRLPOOL);
            }

        //  Load player
        initPlayer();
        tiles[player.getX()][player.getY()].setImage(Global.TILE_PLAYER_LEFT);

        //  Generate Islands

        int numIslands = (int)(Math.random()*Global.MAX_ISLANDS);

        for(int i = 0; i < numIslands; i++)
        {
            SeaTile t = getRandomEmptyTile();

            t.setImage(Global.TILE_ISLAND);
        }


        //  Generate pirates
        numPirates = (int)(Math.random()*(level+1)) + Global.BASE_PIRATES + (level/Global.LEVELS_PER_BASE_INCREASE);
        pirates = new Pirate[numPirates];

        for(int i = 0; i < numPirates; i++)
        {
            SeaTile t = getRandomEmptyTile(Global.INITIAL_DISTANCE_FROM_PLAYER, player.getX(), player.getY());

            t.setImage(Global.TYPE_PIRATE * 10 + (int) (Math.random() * Global.NUM_DIRECTIONS));

            Pirate p = new Pirate(t.getTileX(),t.getTileY(),t.getImage()-20);
            pirates[i] = p;
        }

        canClick = true;
    }

    private SeaTile[] getTilesInRange(int x, int y, int dir)
    {
        SeaTile[] cannonTiles = new SeaTile[Global.CANNON_RANGE*2];
        int[] xs = new int[Global.CANNON_RANGE*2];
        int[] ys = new int[Global.CANNON_RANGE*2];

        if(dir == Global.DIR_DOWN || dir == Global.DIR_UP)
        {
            for(int i = 0; i < Global.CANNON_RANGE*2; i+=2)
            {
                xs[i]   = x - (i/2+1);      ys[i] = y;
                xs[i+1] = x + (i/2+1);    ys[i+1] = y;
            }
        }
        else if(dir == Global.DIR_LEFT || dir == Global.DIR_RIGHT)
        {
            for(int i = 0; i < Global.CANNON_RANGE*2; i+=2)
            {
                xs[i]   = x;      ys[i] = y - (i/2+1);
                xs[i+1] = x;    ys[i+1] = y + (i/2+1);
            }
        }
        else if(dir == Global.DIR_UP_LEFT || dir == Global.DIR_DOWN_RIGHT)
        {
            for(int i = 0; i < Global.CANNON_RANGE*2; i+=2)
            {
                xs[i]   = x - (i/2+1);      ys[i] = y + (i/2+1);
                xs[i+1] = x + (i/2+1);    ys[i+1] = y - (i/2+1);
            }
        }
        else // if(dir == Global.DIR_DOWN_LEFT || dir == Global.DIR_UP_RIGHT)
        {
            for(int i = 0; i < Global.CANNON_RANGE*2; i+=2)
            {
                xs[i]   = x - (i/2+1);      ys[i] = y - (i/2+1);
                xs[i+1] = x + (i/2+1);    ys[i+1] = y + (i/2+1);
            }
        }

        for(int i = 0; i < Global.CANNON_RANGE*2; i++)
        {
            if(outOfBounds(xs[i],ys[i]))
                cannonTiles[i] = null;
            else
                cannonTiles[i] = tiles[xs[i]][ys[i]];
        }

        return cannonTiles;
    }

    private void initWorld()
    {
        //  TODO: Fix weird layout issue starting at random lines.

        tileListener = new ButtonListener();
        level = 1;

        tiles = new SeaTile[Global.NUM_ROWS][Global.NUM_COLS];

        for (int y = 0; y < Global.NUM_ROWS; y++)
        {

            LinearLayout row = new LinearLayout(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            params.weight = 1;
            row.setLayoutParams(params);
            row.setGravity(Gravity.CENTER);

            for(int x = 0; x < Global.NUM_COLS; x++)
            {
                SeaTile tile = new SeaTile(this,x,y);

                if((x==0 || x==Global.NUM_COLS-1)&&(y==0 || y==Global.NUM_ROWS-1))
                    tile.setImage(Global.TILE_WHIRLPOOL);

                tiles[x][y] = tile;

                row.addView(tiles[x][y]);
                row.addView(new Divider(this,true));
            }

            squareLayout.addView(row);
            squareLayout.addView(new Divider(this,false));
        }

        //  Load player
        tiles[player.getX()][player.getY()].setImage(Global.TILE_PLAYER_LEFT);

        //  Generate Islands

        int numIslands = (int)(Math.random()*Global.MAX_ISLANDS);

        for(int i = 0; i < numIslands; i++)
        {
            SeaTile t = getRandomEmptyTile();

            t.setImage(Global.TILE_ISLAND);
        }


        //  Generate pirates
        numPirates = (int)(Math.random()*(level+1)) + Global.BASE_PIRATES + (level/Global.LEVELS_PER_BASE_INCREASE);
        pirates = new Pirate[numPirates];

        for(int i = 0; i < numPirates; i++)
        {
            SeaTile t = getRandomEmptyTile(Global.INITIAL_DISTANCE_FROM_PLAYER, player.getX(), player.getY());

            t.setImage(Global.TYPE_PIRATE * 10 + (int) (Math.random() * Global.NUM_DIRECTIONS));

            Pirate p = new Pirate(t.getTileX(),t.getTileY(),t.getImage()-20);
            pirates[i] = p;
        }

        canClick = true;
    }

    private SeaTile getRandomEmptyTile()
    {
        boolean searching = true;
        int num = 0;
        SeaTile randomTile = tiles[0][0];

        while(searching) {
            num++;
            int randX = (int) (Math.random() * Global.NUM_COLS);
            int randY = (int) (Math.random() * Global.NUM_ROWS);

            randomTile = tiles[randX][randY];

            if (randomTile.isType(Global.TYPE_EMPTY))
            {
                searching = false;
            }

            if(num > Global.NUM_COLS*Global.NUM_ROWS*2) {
                System.out.println("Couldn't find empty tiles! Breaking the game.");
                break;
            }
        }

        return randomTile;
    }

    private SeaTile getRandomEmptyTile(int distance, int x, int y)
    {
        SeaTile t;

        do {
            t=getRandomEmptyTile();
        }while((t.getTileX()-x)*(t.getTileX()-x) + (t.getTileY()-y)*(t.getTileY()-y) < distance*distance && t != tiles[0][0]);

        return t;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            //  TODO: Settings
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private class Divider extends View {
        public Divider(Context context, boolean upDown)
        {
            super(context);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            if(!upDown)params.height=1;
            else params.width=1;
            this.setBackgroundColor(Color.rgb(0,0x44,0xdd));
            this.setLayoutParams(params);
        }
    }
}
