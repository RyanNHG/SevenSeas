package ryan.nhg.sevenseas;

/**
 * Created by ryan on 2/23/15.
 */
public class Player extends Actor
{

    public Player()
    {
        x = Global.INIT_PLAYER_X;
        y = Global.INIT_PLAYER_X;
        dir = Global.INIT_PLAYER_DIR;
    }

    /**
     * ACT
     * @param tile - tile pressed by user.
     * @return - returns result of action (NO_MOVE, MOVE, DEATH)
     */
    public int act(SeaTile tile)
    {
        int tileX = tile.getTileX();
        int tileY = tile.getTileY();
        
        if(tileX == this.getX() && tileY == this.getY()) return fireCannons();
        else if(canMove(tile)) return move(tile);
        else return Global.RESULT_NO_MOVE;

    }

    public int getImage()
    {
        return Global.TYPE_PLAYER*10 + this.getDir();
    }

    private boolean canMove(SeaTile tile)
    {
        int tileX = tile.getTileX();
        int tileY = tile.getTileY();

        return (((this.getX() - tileX) * (this.getX() - tileX) <= 1 && (this.getY() - tileY) * (this.getY() - tileY) <= 1) &&
            (!tile.isType(Global.TYPE_OBSTACLE) && !tile.isType(Global.TYPE_PIRATE)));
    }

    private int move(SeaTile tile)
    {
        int tileX = tile.getTileX();
        int tileY = tile.getTileY();

        int direction = getDirection(this.getX(), this.getY(), tileX, tileY);

        //TODO: moveAnimation();
        this.setDir(getDirection(this.getX(),this.getY(),tileX,tileY));

        this.setDir(getDirection(this.getX(),this.getY(),tileX,tileY));
        this.setX(tileX);
        this.setY(tileY);

        if (tile.isType(Global.TYPE_PIRATE))
            return Global.RESULT_DEATH;
        else if (tile.isType(Global.TYPE_WHIRLPOOL))
            return Global.RESULT_WHIRLPOOL;

        return Global.RESULT_MOVE;
    }

    private int fireCannons()
    {
        return Global.RESULT_FIRE;
    }


}

/*

                //  IF PLAYER IS THERE
                if (this.getX() == tileX && this.getY() == tileY) {
                    fireCannons();
                    //  TODO: cannonAnimation();
                    pirateMoveTrigger = true;
                }
                
                //  IF BUTTON IS IN MOVE RANGE
                else if ((this.getX() - tileX) * (this.getX() - tileX) <= 1 && (this.getY() - tileY) * (this.getY() - tileY) <= 1)
                {
                    //MOVE PLAYER
                    int direction = findDirection(Global.TYPE_PLAYER, this.getX(), this.getY(), tileX, tileY);


                    if (!clickedTile.isType(Global.TYPE_OBSTACLE))
                    {
                        //TODO: moveAnimation();
                        this.setDir(findDirection(0,this.getX(),this.getY(),tileX,tileY));
                        pirateMoveTrigger = true;
                    }
                    if (clickedTile.isType(Global.TYPE_PIRATE))
                    {
                        gameOver();
                        return;
                    }
                    else if (clickedTile.isType(Global.TYPE_WHIRLPOOL))
                    {
                        //  TODO: whirlpoolAnimation();

                        //  relocate this
                        SeaTile random = getRandomEmptyTile();

                        tiles[this.getX()][this.getY()].setImage(Global.TILE_EMPTY);


                        this.setX(random.getTileX());
                        this.setY(random.getTileY());
                        this.setDir((int)(Math.random() * Global.NUM_DIRECTIONS));

                        random.setImage(Global.TYPE_PLAYER * 10 + this.getDir());

                        //  TODO: whirlpoolAnimation();
                    }
                    else if (clickedTile.isType(Global.TYPE_EMPTY))
                    {
                        tiles[this.getX()][this.getY()].setImage(Global.TILE_EMPTY);

                        tiles[tileX][tileY].setImage(direction);

                        this.setX(tileX);
                        this.setY(tileY);
                    }
                }

 */