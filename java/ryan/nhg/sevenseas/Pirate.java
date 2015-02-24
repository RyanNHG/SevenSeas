package ryan.nhg.sevenseas;

/**
 * Created by ryan on 10/30/14.
 */
public class Pirate extends Actor
{
    private int level;

    public Pirate(int x, int y, int dir)
    {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.level = 1;
    }


    public void moveToward(int x, int y)
    {
        int newX, newY;
        int bestX = 0, bestY=0;
        int dist = Integer.MAX_VALUE;

        for(int i = -1; i < 2; i++)
        {
            for(int j = -1; j < 2; j++)
            {
                if(i == 0 && j == 0) continue;

                newX = getX()+i;
                newY = getY()+j;

                if(MainActivity.outOfBounds(newX,newY)) continue;

                if(dist > (newX-x)*(newX-x)+(newY-y)*(newY-y))
                {
                    dist = (newX-x)*(newX-x)+(newY-y)*(newY-y);
                    bestX = newX;
                    bestY = newY;
                }
            }
        }

        setX(bestX);
        setY(bestY);
    }

    @Override
    public int act(SeaTile tile) {
        return 0;
    }

    public int getImage()
    {
        return Global.TYPE_PIRATE*10 + (level-1)*10 + dir;
    }
}
