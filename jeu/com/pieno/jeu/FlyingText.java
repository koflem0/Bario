package com.pieno.jeu;

import java.awt.Color;
import java.awt.Point;

public class FlyingText
{

	public int x, y;
	private String text;
	private float moveUp = 0;
	private boolean active;
	private Color color = Color.YELLOW;

	public FlyingText(int t, Point p, boolean crit)
	{
		active = true;
		if (crit) color = Color.RED;
		if (t == 0)
		{
			text = "miss";
			color = Color.WHITE;
		} else text = "" + t;
		x = p.x;
		y = p.y;
	}

	// fais bouger le texte
	public void update(long timePassed)
	{
		moveUp += timePassed;
		if (moveUp >= 1000)
		{
			active = false;
		}
	}

	public int getX()
	{
		return x;
	}

	public int getY()
	{
		return (int) (y - moveUp * 0.1f);
	}

	public Color getColor()
	{
		return color;
	}

	public String getText()
	{
		return text;
	}

	public boolean isActive()
	{
		return active;
	}

}
