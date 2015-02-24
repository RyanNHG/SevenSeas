package ryan.nhg.sevenseas;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;

/**
 * Created by ryan on 10/29/14.
 */
public class SeaTile extends ImageButton
{

    private int bg, tileX, tileY;

    public SeaTile(Context context, int x, int y)
    {
        super(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        this.setLayoutParams(params);
        this.setOnClickListener(MainActivity.tileListener);
        tileX = x;
        tileY = y;
        bg = Global.TILE_EMPTY;
        this.setImage(bg);
        this.setVisibility(View.VISIBLE);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int min = Math.min(widthMeasureSpec, heightMeasureSpec);
        super.onMeasure(min, min);
    }

    public void setImage(int i)
    {
        switch (i)
        {
            case Global.TILE_EMPTY:
                this.setBackground(null);
                break;
            case Global.TILE_PIRATE_UP:
                this.setBackground(getResources().getDrawable(R.drawable.pirate_up));
                break;
            case Global.TILE_PIRATE_LEFT:
                this.setBackground(getResources().getDrawable(R.drawable.pirate_left));
                break;
            case Global.TILE_PIRATE_DOWN:
                this.setBackground(getResources().getDrawable(R.drawable.pirate_down));
                break;
            case Global.TILE_PIRATE_RIGHT:
                this.setBackground(getResources().getDrawable(R.drawable.pirate_right));
                break;
            case Global.TILE_PIRATE_UP_LEFT:
                this.setBackground(getResources().getDrawable(R.drawable.pirate_up_left));
                break;
            case Global.TILE_PIRATE_DOWN_LEFT:
                this.setBackground(getResources().getDrawable(R.drawable.pirate_down_left));
                break;
            case Global.TILE_PIRATE_DOWN_RIGHT:
                this.setBackground(getResources().getDrawable(R.drawable.pirate_down_right));
                break;
            case Global.TILE_PIRATE_UP_RIGHT:
                this.setBackground(getResources().getDrawable(R.drawable.pirate_up_right));
                break;
            case Global.TILE_PLAYER_UP:
                this.setBackground(getResources().getDrawable(R.drawable.player_up));
                break;
            case Global.TILE_PLAYER_LEFT:
                this.setBackground(getResources().getDrawable(R.drawable.player_left));
                break;
            case Global.TILE_PLAYER_DOWN:
                this.setBackground(getResources().getDrawable(R.drawable.player_down));
                break;
            case Global.TILE_PLAYER_RIGHT:
                this.setBackground(getResources().getDrawable(R.drawable.player_right));
                break;
            case Global.TILE_PLAYER_UP_LEFT:
                this.setBackground(getResources().getDrawable(R.drawable.player_up_left));
                break;
            case Global.TILE_PLAYER_DOWN_LEFT:
                this.setBackground(getResources().getDrawable(R.drawable.player_down_left));
                break;
            case Global.TILE_PLAYER_DOWN_RIGHT:
                this.setBackground(getResources().getDrawable(R.drawable.player_down_right));
                break;
            case Global.TILE_PLAYER_UP_RIGHT:
                this.setBackground(getResources().getDrawable(R.drawable.player_up_right));
                break;
            case Global.TILE_WHIRLPOOL:
                this.setBackground(getResources().getDrawable(R.drawable.whirlpool));
                break;
            case Global.TILE_ISLAND:
                this.setBackground(getResources().getDrawable(R.drawable.island));
                break;
            case Global.TILE_WRECKAGE:
                this.setBackground(getResources().getDrawable(R.drawable.wreckage));
                break;
            case Global.TILE_CANNONBALL:
                this.setBackground(getResources().getDrawable(R.drawable.cannonball));
                break;
            default:
                i = bg;
                break;
        }

        bg = i;
    }

    public boolean isType(int type)
    {
        switch (type)
        {
            case Global.TYPE_PLAYER:
                return (bg >= Global.TILE_PLAYER_UP && bg <= Global.TILE_PLAYER_DOWN_RIGHT);
            case Global.TYPE_PIRATE:
                return (bg >= Global.TILE_PIRATE_UP);
            case Global.TYPE_WHIRLPOOL:
                return (bg == Global.TILE_WHIRLPOOL);
            case Global.TYPE_OBSTACLE:
                return (bg == Global.TILE_ISLAND || bg == Global.TILE_WRECKAGE);
            case Global.TYPE_EMPTY:
                return bg == Global.TILE_EMPTY;
        }
        return false;
    }

    public int getImage()
    {
        return bg;
    }

    public void setTileX(int x)
    {
        tileX = x;
    }

    public int getTileX()
    {
        return tileX;
    }

    public void setTileY(int y)
    {
        tileY = y;
    }

    public int getTileY()
    {
        return tileY;
    }
}
