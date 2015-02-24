package ryan.nhg.sevenseas;

/**
 * Created by ryan on 2/23/15.
 */
public abstract class Actor
{
    int x, y, dir;

    public void setX(int x)
    {
        this.x = x;
    }

    public int getX()
    {
        return x;
    }

    public void setY(int y)
    {
        this.y = y;
    }

    public int getY()
    {
        return y;
    }

    public int getDir()
    {
        return dir;
    }

    public void setDir(int dir)
    {
        this.dir = dir;
    }

    public abstract int act(SeaTile tile);

    protected int getDirection(int oldX, int oldY, int newX, int newY)
    {
        int direction = 0;

        if(newX > oldX)
        {
            if(newY > oldY)
                direction = Global.DIR_DOWN_RIGHT;
            else if(newY < oldY)
                direction = Global.DIR_UP_RIGHT;
            else direction = Global.DIR_RIGHT;
        }
        else if(newX < oldX)
        {
            if(newY > oldY)
                direction = Global.DIR_DOWN_LEFT;
            else if(newY < oldY)
                direction = Global.DIR_UP_LEFT;
            else direction = Global.DIR_LEFT;
        }
        else // newX == oldX
        {
            if(newY > oldY)
                direction = Global.DIR_DOWN;
            else if(newY < oldY)
                direction = Global.DIR_UP;
        }

        return direction;
    }

    public abstract int getImage();
}
