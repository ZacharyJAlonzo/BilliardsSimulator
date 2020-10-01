/**
 * @(#)Table.java
 *
 *
 * @author 
 * @version 1.00 2020/5/22
 */


import java.util.Map;
public class Table
{
	
	private Ball[] balls;
	
    public Table() 
    {
    	balls = new Ball[10];
    }
    
    void addBall(Ball toAdd)
    {
    	balls[toAdd.getNumber()] = toAdd;
    }
   
    Ball[] GetBalls()
    {
    	return balls;
    }
    
}