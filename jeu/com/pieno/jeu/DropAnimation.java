package com.pieno.jeu;

public class DropAnimation
{
	long moveTimer = 0;
	float x, y;
	int type, rarity;
	boolean up = true;

	public void update(long timePassed)
	{
		moveTimer += timePassed;
		if (moveTimer >= 800)
		{
			moveTimer = 0;
			up = !up;
		}

		if (up) y -= timePassed * 0.02f;
		else y += timePassed * 0.02f;
	}
}
