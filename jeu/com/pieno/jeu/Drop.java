package com.pieno.jeu;

import java.awt.Point;
import java.awt.Rectangle;

public class Drop
{
	Item item;
	float x, y;
	private boolean up = true;
	int moveTimer = 0, ownerID = -1, id;
	long ownerTimer = 3000;
	transient public static final int width = 50, height = 50;

	public Drop(Item item, Point p)
	{
		this.item = item;
		x = p.x;
		y = p.y;
	}

	public Drop(Item item, Point p, int owner)
	{
		this(item, p);
		ownerID = owner;
	}

	public void update(long timePassed)
	{
		moveTimer += timePassed;
		if (moveTimer >= 800)
		{
			moveTimer = 0;
			up = !up;
		}
		if (ownerID != -1)
		{
			ownerTimer -= timePassed;
			if (ownerTimer <= 0) ownerID = -1;
		}

		if (up) y -= timePassed * 0.02f;
		else y += timePassed * 0.02f;
	}

	public Rectangle getArea()
	{
		return new Rectangle((int) x, (int) y, width, height);
	}
}
