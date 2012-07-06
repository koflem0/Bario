package com.pieno.jeu;

import java.awt.Image;

import javax.swing.ImageIcon;

public class EffectAnimation {
	int x, y, totalTime, previousCX, previousCY;
	Animation a;
	CharacterAnimation c;
	boolean active = true;
	
	public EffectAnimation(int type, CharacterAnimation c){
		this(type);
		this.c = c;
		previousCX = c.x;
		previousCY = c.y;
	}
	
	public EffectAnimation(int type){
		a = loadAnimation(type);
		totalTime = (int) a.getTotalTime();
	}
	
	public Animation loadAnimation(int type){
		Animation b = new Animation();
		switch(type){
		case Network.EXPLOSION:
			for(int i = 1; i <= 10; i++){
				b.addScene(newImage("/explos"+i+".png"), 60);
			}
			break;
		case Network.EXPLOARROW:
			for(int i = 1; i <= 5; i++){
				b.addScene(newImage("/explosion"+i+".png"), 40);
			}
			break;
		case Network.LVLUP:
			for(int i = 1; i <= 5; i++)
				b.addScene(newImage("/bob_lvlup"+i+".png"), 125);
			b.addScene(newImage("/bob_lvlup"+5+".png"), 300);
			for(int i = 5; i >= 1; i--)
				b.addScene(newImage("/bob_lvlup"+i+".png"), 115);
			break;
		}
		return b;
	}
	
	public void update(long timePassed){
		a.update(timePassed);
		totalTime -= timePassed;
		if(totalTime <= 0) active = false;
		
		if (c!=null)
		{
			x += c.getRealX() - previousCX;
			previousCX = c.getRealX();
			y+= c.getRealY() - previousCY;
			previousCY = c.getRealY();
		}
	}
	
	public Image newImage(String source){
		return new ImageIcon(getClass().getResource("/skill"+source)).getImage();
	}
}
