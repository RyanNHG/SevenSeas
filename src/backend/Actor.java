package backend;

public class Actor 
{
	protected int x, y, dir;
	
	public Actor()
	{
		this.x = 0;
		this.y = 0;
		this.dir = Global.DIR_S;
	}
	
	public boolean move(int newX, int newY)
	{
		//	If can move
		if( (newX-x)*(newX-x) < 2 && (newY-y)*(newY-y) < 2 ) 
		{
			//	Find new direction
			int newDir = dir;
			
			if(newX > x)		//	EAST
			{
				if(newY < y)		//	NORTH
					newDir = Global.DIR_NE;
				else if(newY > y)	//	SOUTH
					newDir = Global.DIR_SE;
				else newDir = Global.DIR_E;
			}
			else if(newX < x)	//	WEST
			{
				if(newY < y)		//	NORTH
					newDir = Global.DIR_NW;
				else if(newY > y)	//	SOUTH
					newDir = Global.DIR_SW;
				else newDir = Global.DIR_W;	
			}
			else				//	NEITHER
			{
				if(newY < y)		//	NORTH
					newDir = Global.DIR_N;
				else if(newY > y)	//	SOUTH
					newDir = Global.DIR_S;
				else return false;
			}
			
			this.x = newX;
			this.y = newY;
			this.dir = newDir;
			return true;
		}
		
		return false;
		
	}
	
	public int getDir()
	{
		return dir;
	}
}
