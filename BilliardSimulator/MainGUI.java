/**
 * @(#)MainGUI.java
 *
 *
 * @author 
 * @version 1.00 2020/5/23
 */

import javax.swing.*;
import javax.swing.border.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.*;

public class MainGUI extends JFrame
{

	private TableGUI table;
	private JSlider slider;
	private JPanel sliderHolder;
	
    public MainGUI(TableGUI t) 	
    {
    	setSize(1000,500);
    	setTitle("Billiard Simulator");
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	setResizable(false);
    	setLayout(null);

    	table = t;
    	table.setBounds(250,10,800,500);   
    	add(table);
    	
    	sliderHolder = new JPanel();
    	sliderHolder.setBounds(10,10,50,235);
    	Border blackline = BorderFactory.createLineBorder(Color.black);  	
    	sliderHolder.setBorder(BorderFactory.createTitledBorder(blackline, "RAILS"));
    	sliderHolder.setVisible(true);
    	add(sliderHolder);
    	
    	slider = new JSlider(JSlider.VERTICAL, 1, 5, 2);
    	slider.setPaintLabels(true);
    	slider.setSnapToTicks(true);
    	slider.setMajorTickSpacing(1);
    	sliderHolder.add(slider);
    
    	
    	setVisible(true);
    	repaint();
    	
    }
    
    public int getSliderValue()
    {
    	return slider.getValue();
    }
    
}