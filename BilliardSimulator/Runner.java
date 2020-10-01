/**
 * @(#)Runner.java
 *
 *
 * @author 
 * @version 1.00 2020/5/22
 */


public class Runner 
{

    public static void main(String[] args)
    {
    	Table t = new Table();
    	Ball cue = new Ball(0,t);
    	Ball one = new Ball(1,t);
    	cue.ax = 300;
    	cue.ay = 200;
    	
    	one.ax = 400;
    	one.ay = 200;
    	t.addBall(one);
    	t.addBall(cue);
    	
    	
    	TableGUI x = new TableGUI(t);
    	MainGUI y = new MainGUI(x);
    	x.setOwner(y);
    	
    
    }
    
}