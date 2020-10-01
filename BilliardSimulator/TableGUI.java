/**
 * @(#)TableGUI.java
 *
 *
 * @author 
 * @version 1.00 2020/5/22
 */
 
 //all of the line calculations are done here. was going to do them in the ball class but decided not to because I was too lazy to connect them effectively.
import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;
import java.awt.Point;
import java.awt.PointerInfo;

public class TableGUI extends JPanel implements MouseListener, MouseMotionListener
{
	
	//the table instance
	private Table table;
	//the user clicked inside of a ball and is moving it
		private boolean userMovingBall;
	//whether or not we should draw a line.
		private boolean cueSelected;	
	//the selected ball
	private Ball selectedBall;
	
	private MainGUI owner;
	
	//the mouse location as it moves from the cueball. used to draw to direction line.
	private Point lineEndPoint;
	
	private int tableTRail, tableBRail, tableRRail, tableLRail;
		
    public TableGUI(Table t) 
    {   	  	
    	setVisible(true);
    	table = t; 	
    		
    	addMouseListener(this);
    	addMouseMotionListener(this);
    	repaint();
    }
    
    void setOwner(MainGUI own)
    {
    	owner = own;
    }

 	public void paintComponent(Graphics g) 
  	{
   	 	super.paintComponent(g);
   	 	Graphics2D g2d = (Graphics2D) g;
    	g2d.setColor(new Color(0, 0, 0));
    	setSize(800,500);

		drawTable(g2d);
    	//draw balls
    	drawBalls(g2d);
    	
  			
  		if(cueSelected)
  		{	
  			drawGuideLine(g2d); 			
  		}
  }
  void drawTable(Graphics2D g2d)
  {
  		//outer edge
    	g2d.drawRoundRect(0,20,700,400, 25, 25);
    	    	
    	//top  	
    	g2d.drawLine(35, 40, 340, 40);
    	g2d.drawLine(360, 40, 665, 40);   	
  		//bottom
    	g2d.drawLine(360,400,665,400);
    	g2d.drawLine(35,400, 340, 400);
    	
    	tableTRail = 40;
    	tableBRail = 400;
    	
    	//left
    	g2d.drawLine(20,55,20,385);
    	//right
    	g2d.drawLine(680,55,680,385);
    	
    	tableRRail = 680;
    	tableLRail = 20;
    	
    	//diamonds
    	//top row
    	g2d.drawOval(85,30,5,5);
    		g2d.drawOval(185,30,5,5);
    			g2d.drawOval(285,30,5,5);
    			
    	g2d.drawOval(415,30,5,5);
    		g2d.drawOval(510,30,5,5);
    			g2d.drawOval(615,30,5,5);
    			
    	//bottom row
    	g2d.drawOval(85,410,5,5);
    		g2d.drawOval(185,410,5,5);
    			g2d.drawOval(285,410,5,5);
    			
    	g2d.drawOval(415,410,5,5);
    		g2d.drawOval(510,410,5,5);
    			g2d.drawOval(615,410,5,5);
    	
    	//right col
    	g2d.drawOval(7,100,5,5);
    		g2d.drawOval(7,215,5,5);
    			g2d.drawOval(7,330,5,5);
    			
    	//left col
    	g2d.drawOval(688,100,5,5);
    		g2d.drawOval(688,215,5,5);
    			g2d.drawOval(688,330,5,5);
    	
    	
    	
  }
  
  void drawBalls(Graphics2D g2d)
  {
  		Ball[] arr = table.GetBalls(); 	
  		for(int i = 0; i < arr.length; i++)
  		{
  			if(arr[i] != null)
  			g2d.drawOval(arr[i].ax, arr[i].ay, Ball.RADIUS*2, Ball.RADIUS*2);	
  		}
  	
  		
  }
  
  void drawRebounds(Graphics2D g2d, Point p1, Point p2)
  {
  			Point newEnd = new Point();
  			
  			for(int i = 0; i < (owner.getSliderValue()-1); i++)
  			{ 			
  				double slope = (double)(p2.y - p1.y)/(double)(p2.x - p1.x);  			
  				//p2 is the new line's start point
  				//y = M(x - p2.x) + p2.y
  				//x/y will be determined by a strength slider. not yet implemented
  				if(p2.x == tableRRail)
  				{
  					if(slope >= 0)
  					{
  						newEnd.y = tableBRail; //the lowest the guideline will go
  						newEnd.x = (int)(((double)(-p2.y + newEnd.y + (-slope * (double)p2.x)))/-slope); 
  						
  						if(newEnd.x < tableLRail)
  						{
  							newEnd.x = tableLRail;
  						}
  						
  						newEnd.y = (int)(slope * (double)(p2.x - newEnd.x)) + p2.y;
  						if(newEnd.y > tableBRail)
  						{
  							newEnd.y = tableBRail;
  						} 					
  					}
  					else
  					{  		
  						newEnd.y = tableTRail; //the highest the guideline will go
  						newEnd.x = (int)(((double)(-p2.y + newEnd.y + (-slope * (double)p2.x)))/-slope); 
  							
  						if(newEnd.x < tableLRail)
  						{
  							newEnd.x = tableLRail;
  						}
  						
  						newEnd.y = (int)(slope * (double)(p2.x - newEnd.x)) + p2.y;
  						if(newEnd.y < tableTRail)
  						{
  							newEnd.y = tableTRail;
  						}
  					
  					}
  	
  				}
  				if(p2.x <= tableLRail)
  				{				
  					if(slope >= 0)
  					{
  						newEnd.y = tableTRail; //the lowest the guideline will go
  						newEnd.x = (int)(((double)(-p2.y + newEnd.y + (-slope * (double)p2.x)))/-slope);
  						
  						if(newEnd.x > tableRRail)
  						{
  							newEnd.x = tableRRail;
  						}
  						
  						newEnd.y = (int)(slope * (double)(p2.x - newEnd.x)) + p2.y;
  						if(newEnd.y-10 < tableTRail)
  						{
  							newEnd.y = tableTRail;
  						}
  						
  					}
  					else
  					{  		
  						newEnd.y = tableBRail; //the lowest the guideline will go
  						newEnd.x = (int)(((double)(-p2.y + newEnd.y + (-slope * (double)p2.x)))/-slope); 
  							
  						if(newEnd.x > tableRRail)
  						{
  							newEnd.x = tableRRail;
  						}
  						
  						newEnd.y = (int)(slope * (double)(p2.x - newEnd.x)) + p2.y;
  						if(newEnd.y+10 > tableBRail)
  						{
  							newEnd.y = tableBRail;
  						}
  						
  					}
  				}
  				if(p2.y <= tableTRail)
  				{
			
  					if(slope <= 0)
  					{ 						
  						newEnd.x = tableRRail;
  						newEnd.y = (int)(slope * (double)(p2.x - newEnd.x)) + p2.y;
  						//newEnd.y = tableBRail; //the highest the guideline will go
  						//newEnd.x = (int)(((double)(-p2.y + newEnd.y + (-slope * (double)p2.x)))/-slope);
  						
  						if(newEnd.y > tableBRail)
  						{
  							newEnd.y = tableBRail;
  						}
  						newEnd.x = (int)(((double)(-p2.y + newEnd.y + (-slope * (double)p2.x)))/-slope);
  						if(newEnd.x+10 > tableRRail)
  						{
  							newEnd.x = tableRRail;
  						} 
  							
  						
  					}
  					else if(slope > 0)
  					{  		
  						newEnd.x = tableLRail;
  						newEnd.y = (int)(slope * (double)(p2.x - newEnd.x)) + p2.y;
  						//newEnd.y = tableBRail; //the highest the guideline will go
  						//newEnd.x = (int)(((double)(-p2.y + newEnd.y + (-slope * (double)p2.x)))/-slope);
  						
  						if(newEnd.y > tableBRail)
  						{
  							newEnd.y = tableBRail;
  						}
  						
  						newEnd.x = (int)(((double)(-p2.y + newEnd.y + (-slope * (double)p2.x)))/-slope);
  						if(newEnd.x-10 < tableLRail)
  						{
  							newEnd.x = tableLRail;
  						} 
  					}
  			

  				}
  				if(p2.y == tableBRail)
  				{
  					if(slope >= 0)
  					{
  					 //the lowest the guideline will go
  						newEnd.x = tableRRail;
  						newEnd.y = (int)(slope * (double)(p2.x - newEnd.x)) + p2.y;
  						
  						//newEnd.y = tableTRail; //the highest the guideline will go
  						//newEnd.x = (int)(((double)(-p2.y + newEnd.y + (-slope * (double)p2.x)))/-slope);
  						
  						if(newEnd.y < tableTRail)
  						{
  							newEnd.y = tableTRail;
  						}
  						newEnd.x = (int)(((double)(-p2.y + newEnd.y + (-slope * (double)p2.x)))/-slope);
  						if(newEnd.x+10 > tableRRail)
  						{
  							newEnd.x = tableRRail;
  						} 
  						
  					}
  					else if(slope < 0)
  					{  		
  						newEnd.x = tableLRail;
  						newEnd.y = (int)(slope * (double)(p2.x - newEnd.x)) + p2.y;
  						//newEnd.y = tableTRail; //the highest the guideline will go
  						//newEnd.x = (int)(((double)(-p2.y + newEnd.y + (-slope * (double)p2.x)))/-slope);
  						
  						if(newEnd.y < tableTRail)
  						{
  							newEnd.y = tableTRail;
  						}
  						
  						newEnd.x = (int)(((double)(-p2.y + newEnd.y + (-slope * (double)p2.x)))/-slope);
  						if(newEnd.x-10 < tableLRail)
  						{
  							newEnd.x = tableLRail;
  						} 
  					}
  					
  				}
  				
				if(!coordsInBounds(newEnd.x, newEnd.y))
				{
					break;
				}
  				
  				g2d.drawLine(p2.x, p2.y, newEnd.x, newEnd.y);
  					
  				p1.x = p2.x;
  				p1.y = p2.y;
  				
  				p2.x = newEnd.x;
  				p2.y = newEnd.y;
  			}	
  				
  }
  
 
  void drawGuideLine(Graphics2D g2d)
  { 			 			
  		//beyond to the right
  		if(lineEndPoint.x >= tableRRail)
  		{
  				//initial line to rail
  				g2d.drawLine(selectedBall.ax + Ball.RADIUS,selectedBall.ay + Ball.RADIUS, tableRRail, lineEndPoint.y);
  				//reflect across the rail
  				//we know the line's end point, and start point. we can find the equation of the line from this.
  				//negate the slope of the line to reflect
  				Point p1 = new Point();
  				Point p2 = new Point();
  				p1.x = selectedBall.ax + Ball.RADIUS;
  				p1.y = selectedBall.ay + Ball.RADIUS;
  				
  				p2.x = tableRRail;
  				p2.y = lineEndPoint.y;

  				drawRebounds(g2d, p1, p2);
  		}
  		else if(lineEndPoint.x <= tableLRail)
  		{
  			//initial line to rail
  				g2d.drawLine(selectedBall.ax + Ball.RADIUS,selectedBall.ay + Ball.RADIUS, tableLRail, lineEndPoint.y);
  				//reflect across the rail
  				//we know the line's end point, and start point. we can find the equation of the line from this.
  				//negate the slope of the line to reflect
  				Point p1 = new Point();
  				Point p2 = new Point();
  				p1.x = selectedBall.ax + Ball.RADIUS;
  				p1.y = selectedBall.ay + Ball.RADIUS;
  				
  				p2.x = tableLRail;
  				p2.y = lineEndPoint.y;
  				
  				double slope = (double)(p2.y - p1.y)/(double)(p2.x - p1.x);
  			
  				drawRebounds(g2d, p1,p2);
  				
  		} 	
  		else if(lineEndPoint.y <= tableTRail)
  		{			
  				//initial line to rail
  				g2d.drawLine(selectedBall.ax + Ball.RADIUS,selectedBall.ay + Ball.RADIUS, lineEndPoint.x, tableTRail);
  				//reflect across the rail
  				//we know the line's end point, and start point. we can find the equation of the line from this.
  				//negate the slope of the line to reflect
  				Point p1 = new Point();
  				Point p2 = new Point();
  				p1.x = selectedBall.ax + Ball.RADIUS;
  				p1.y = selectedBall.ay + Ball.RADIUS;
  				
  				p2.x = lineEndPoint.x;
  				p2.y = tableTRail;
  				
  				double slope = (double)(p2.y - p1.y)/(double)(p2.x - p1.x); 			
  				
  				drawRebounds(g2d, p1, p2);
  		}
  		else if(lineEndPoint.y >= tableBRail)
  		{			
  				//initial line to rail
  				g2d.drawLine(selectedBall.ax + Ball.RADIUS,selectedBall.ay + Ball.RADIUS, lineEndPoint.x, tableBRail);
  				//reflect across the rail
  				//we know the line's end point, and start point. we can find the equation of the line from this.
  				//negate the slope of the line to reflect
  				Point p1 = new Point();
  				Point p2 = new Point();
  				p1.x = selectedBall.ax + Ball.RADIUS;
  				p1.y = selectedBall.ay + Ball.RADIUS;
  				
  				p2.x = lineEndPoint.x;
  				p2.y = tableBRail;
  				
  				double slope = (double)(p2.y - p1.y)/(double)(p2.x - p1.x);
  				 			
  				drawRebounds(g2d, p1, p2);
  		} 	
  		else
  		{
  			int coordX = 0;
  			int coordY = 0;
  			boolean isTouchingBall = false;
  			//check if guideline will hit a ball
  			Point iter = new Point();
  			Ball[] arr = table.GetBalls();
  			iter.x = arr[1].ax; 
  			iter.y = arr[1].ay;
  			//radius = 7
  			//(x)^2 + (y)^2 = 49 = every point on sphere
  			//get all unique points on the sphere
  			ArrayList<Point> list = new ArrayList<Point>();
  			int count = 0;
  			for(double i = -7.0; i < 7.0; i+=0.1)//70 points 
  			{
  				double Px = i;
  				double Py = Math.sqrt(Ball.RADIUS*Ball.RADIUS - (i*i));
  				
  				//calculated coords
  				int Ax = (int)(Px + (double)arr[1].ax)+Ball.RADIUS;
  				int Ay = (int)(Py + (double)arr[1].ay) + Ball.RADIUS;
    				
    			int AX  = Ax;
  				int AY = (int)(-Py + (double)(arr[1].ay) + Ball.RADIUS);
    				
    			
    			boolean add1 = true;
    			boolean add2 = true;
    			for(int k = 0; k < list.size(); k++)
    			{
    				if(list.get(k).x == Ax && list.get(k).y == Ay)
    				{
    					add1 = false;
    				}
    				if(list.get(k).x == AX && list.get(k).y == AY)
    				{
    					add2 = false;
	  				}    				
    			}	
    				
    			if(add1)
    			{
    				list.add(count, new Point(Ax, Ay));
    				count++;
    			}	
    				
    			if(add2)
    			{
    				list.add(count, new Point(AX, AY));
    				count++;
    			}    				
    						
  			}

			//calculate the slope 
  			Point p1 = new Point();
  			Point p2 = new Point();
  				p1.x = arr[0].ax + Ball.RADIUS;
  				p1.y = arr[0].ay + Ball.RADIUS;
  				
  				p2.x = lineEndPoint.x;
  				p2.y = lineEndPoint.y;
  				
  			double slope = (double)(p2.y - p1.y)/(double)(p2.x - p1.x);
  			
  			
  			
  			
  		if(Math.abs(slope) < 7.0) // not severe enough to fail
  		{
  				
  				//check the slope at every point on the sphere
  			ArrayList<Point> close = new ArrayList<Point>();
  			 			
  			for(int i = 0; i < list.size(); i++)
  			{
  				double slope2 = (double)(p2.y - list.get(i).y)/(double)(p2.x - list.get(i).x);
   				
  				if(Math.abs(slope2-slope) < 0.05)
  				{
  					close.add(list.get(i));	
  				}
  					
  			}
  				
  			
  			if(p1.x > iter.x+Ball.RADIUS && lineEndPoint.x < iter.x+Ball.RADIUS)
  			{
  					
  					//the closest point will be farthest to the right
  					if(close.size() >= 1)
  					{
  						isTouchingBall = true;
  						Point closest = close.get(0);
  						
  						for(int k = 0; k < close.size(); k++)
  						{
  							if(close.get(k).x > closest.x)
  							{
  								closest = close.get(k);
  							}
  						}
  						
  						g2d.drawLine(p1.x, p1.y, closest.x, closest.y);
  					}
  							
  			}
  			else if(p1.x < iter.x+Ball.RADIUS && lineEndPoint.x > iter.x+Ball.RADIUS)
  			{
  					//the closest point will be farthest to the right
  					if(close.size() >= 1)
  					{
  						isTouchingBall = true;
  						Point closest = close.get(0);
  						
  						for(int k = 0; k < close.size(); k++)
  						{
  							if(close.get(k).x < closest.x)
  							{
  								closest = close.get(k);
  							}
  						}
  						
  						g2d.drawLine(p1.x, p1.y, closest.x, closest.y);
  					}
  			}
  			
  		}
  		else if(Math.abs(slope) < 26)//severe slope
  		{
  			//check the slope at every point on the sphere
  			ArrayList<Point> close = new ArrayList<Point>();
  			 			
  			for(int i = 0; i < list.size(); i++)
  			{
  				double slope2 = (double)(p2.y - list.get(i).y)/(double)(p2.x - list.get(i).x);
   				
  				if(Math.abs(slope2 - slope) < 3)
  				{
  					close.add(list.get(i));	
  				}
  					
  			}
  			
  			
  			if(p1.y > iter.y+Ball.RADIUS && lineEndPoint.y < iter.y+Ball.RADIUS)
  			{
  					
  					//the closest point will be lowest
  					if(close.size() >= 1)
  					{
  						isTouchingBall = true;
  						Point closest = close.get(0);
  						
  						for(int k = 0; k < close.size(); k++)
  						{
  							if(close.get(k).y > closest.y)
  							{
  								closest = close.get(k);
  							}
  						}
  						
  						g2d.drawLine(p1.x, p1.y, closest.x, closest.y);
  					}
  							
  			}
  			else if(p1.y < iter.y+Ball.RADIUS && lineEndPoint.y > iter.y+Ball.RADIUS)
  			{
  					//the closest point will be highest
  					if(close.size() >= 1)
  					{
  						isTouchingBall = true;
  						Point closest = close.get(0);
  						
  						for(int k = 0; k < close.size(); k++)
  						{
  							if(close.get(k).y < closest.y)
  							{
  								closest = close.get(k);
  							}
  						}
  						
  						g2d.drawLine(p1.x, p1.y, closest.x, closest.y);
  					}
  			}	
  		}
  		else
  		{
  			//check the slope at every point on the sphere
  			ArrayList<Point> close = new ArrayList<Point>();
  			 			
  			for(int i = 0; i < list.size(); i++)
  			{
  				double slope2 = (double)(p2.y - list.get(i).y)/(double)(p2.x - list.get(i).x);
   				
  				if(Math.abs(slope2 - slope) < 15)
  				{
  					close.add(list.get(i));	
  				}
  					
  			}
  			
  			
  			if(p1.y > iter.y+Ball.RADIUS && lineEndPoint.y < iter.y+Ball.RADIUS)
  			{
  					
  					//the closest point will be lowest
  					if(close.size() >= 1)
  					{
  						isTouchingBall = true;
  						Point closest = close.get(0);
  						
  						for(int k = 0; k < close.size(); k++)
  						{
  							if(close.get(k).y > closest.y)
  							{
  								closest = close.get(k);
  							}
  						}
  						
  						g2d.drawLine(p1.x, p1.y, closest.x, closest.y);
  					}
  							
  			}
  			else if(p1.y < iter.y+Ball.RADIUS && lineEndPoint.y > iter.y+Ball.RADIUS)
  			{
  					//the closest point will be highest
  					if(close.size() >= 1)
  					{
  						isTouchingBall = true;
  						Point closest = close.get(0);
  						
  						for(int k = 0; k < close.size(); k++)
  						{
  							if(close.get(k).y < closest.y)
  							{
  								closest = close.get(k);
  							}
  						}
  						
  						g2d.drawLine(p1.x, p1.y, closest.x, closest.y);
  					}
  			}	
  		}
  	
  	
  		
		if(!isTouchingBall)
		{
			g2d.drawLine(p1.x, p1.y, lineEndPoint.x, lineEndPoint.y);
		}

			
  			
  
  			
  		}
  		
  }
  
  

   public void mousePressed(MouseEvent e)
   {
   			Point loc = e.getPoint();
    		Ball[] arr = table.GetBalls();
    		
    		//if LMB, check if we clicked inside the ball
    		if(e.getButton() == e.BUTTON1)
    		{   			
    			//did the mouse click occur within the bounds of our circle?
    			for(int i = 0; i < arr.length; i++)
    			{
    				if(arr[i] != null)
    				//loc is subtracted because the mouse cursor does not register at the tip, it registers at the right edge of the triangle
    				if( (loc.x-5 <= arr[i].ax+Ball.RADIUS && loc.x-5 >= arr[i].ax-Ball.RADIUS) && (loc.y-5 <= arr[i].ay+Ball.RADIUS && loc.y-5 >= arr[i].ay-Ball.RADIUS) )
    					{
    						userMovingBall = true;
    						selectedBall = arr[i];
    					}
    			}  
	
    		}
    		//RMB
    		if(e.getButton() == e.BUTTON3)
    		{
    			//check if there is a cueball
    			if(arr[0] != null)
    			{
    				//did we click inside the cueball?
    				if( (loc.x-5 <= arr[0].ax+Ball.RADIUS && loc.x-5 >= arr[0].ax-Ball.RADIUS) && (loc.y-5 <= arr[0].ay+Ball.RADIUS && loc.y-5 >= arr[0].ay-Ball.RADIUS) )
    					{
    						cueSelected = true;
    						//so we know cue location
    						selectedBall = arr[0];
    					}
    			}
    			
    			
    		}
   }
    
    //move the ball to location if we are grabbing one
    public void mouseReleased(MouseEvent e)
    {
    	if(e.getButton() == e.BUTTON1)
    	{
    			
    		if(userMovingBall)
    		{
    			Point loc = e.getPoint();  			
    			moveBallToLocation(loc.x-5, loc.y-5);
    		}   		
    	}
    	
    	selectedBall = null;
    	cueSelected = false;
    	userMovingBall = false;
    	repaint();
    }
  
    public void mouseDragged(MouseEvent e)
    {
    	//if cueball selected:
    	if(cueSelected)
    	{
    		Point loc = e.getPoint();  		
    		lineEndPoint = loc;
    		
    		if(!coordsInBounds(lineEndPoint.x, lineEndPoint.y))
    		{
    			if(lineEndPoint.x > tableRRail)
    			{
    				lineEndPoint.x = tableRRail;
    			}
    			if(lineEndPoint.x < tableLRail)
    			{
    				lineEndPoint.x = tableLRail;
    			}
    			if(lineEndPoint.y > tableBRail)
    			{
    				lineEndPoint.y = tableBRail;
    			}
    			if(lineEndPoint.y < tableTRail)
    			{
    				lineEndPoint.y = tableTRail;
    			}
    		}
    		
    		   			  	
    		repaint();
    	}
    	//user is moving the ball across the table
    	else if(userMovingBall)
    	{
    		//show ball on drag
    		Point loc = e.getPoint();    		
    		//offset is the same as the end of the guideline. not sure why it is needed.		
    		moveBallToLocation(loc.x-5, loc.y-5);
    		repaint();
    	}
    }
    
    
    //not implemented
    public void mouseMoved(MouseEvent e){}
    public void mouseEntered(MouseEvent e){}  
    public void mouseExited(MouseEvent e){}
    public void mouseClicked(MouseEvent e){}
  
  
  	public boolean coordsInBounds(int x, int y)
  	{
  		if((x <= tableRRail && x >= tableLRail) && (y >= tableTRail && y <= tableBRail))
  		{
  			return true;
  		}
  		else return false;
  	}
  
  	public void moveBallToLocation(int x, int y)
  	{
  		if( (x < tableRRail-Ball.RADIUS*2 && x > tableLRail) && (y > tableTRail && y < tableBRail-Ball.RADIUS*2) )
  		{
  			//is in bounds
  			selectedBall.ax = x;
  			selectedBall.ay = y;
  		}
  		else
  		{
  			boolean xValueChanged = false;
  			boolean yValueChanged = false;
  			
  			//not in bounds. snap to rail
  			//subtracted values are used to snap the ball to the inside of the rail instead of the outside.
  			if(x > tableRRail-Ball.RADIUS*2) { selectedBall.ax = tableRRail-Ball.RADIUS*2; xValueChanged = true;}
  			
  			
  			if(x < tableLRail) { selectedBall.ax = tableLRail; xValueChanged = true;}
  		
  			
  			if(y > tableBRail-Ball.RADIUS*2) { selectedBall.ay = tableBRail-Ball.RADIUS*2; yValueChanged = true;}
  		
  			
  			if(y < tableTRail){ selectedBall.ay = tableTRail; yValueChanged = true; }
  			
  			//if no change in value, they are in bounds.
  			if(!xValueChanged)
  			{
  				selectedBall.ax = x;
  			}
  			if(!yValueChanged)
  			{
  				selectedBall.ay = y;
  			}
  		

  		}
  	}
  
  
}