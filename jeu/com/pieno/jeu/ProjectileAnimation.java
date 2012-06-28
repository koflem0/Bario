package com.pieno.jeu;

import java.awt.Image;
import com.pieno.jeu.Animation;
import javax.swing.ImageIcon;

public class ProjectileAnimation {
	float x,y;
	int type;
	float vx, vy;
	Animation a;
	
	public ProjectileAnimation(int type){
		this.type = type;
		a = loadAnimation(type);
	}
	
	//TODO generate animation
	public Animation loadAnimation(int type){
		Animation b = new Animation();
		switch(type){
		case Network.ARROW:
			Image flecheG = newImage("/flecheG.png");
			b.addScene(flecheG,200);
			break;
		case Network.ARROW+1:
			Image flecheD = newImage("/flecheD.png");
			b.addScene(flecheD,200);
			break;
		case Network.ENERGY:
			Image energy = newImage("/energyball.png");
			b.addScene(energy,200);
			break;
		case Network.FIRE:
			Image fire = newImage("/fireball.png");
			Image fire2 = newImage("/fireball2.png");
			b.addScene(fire,70);
			b.addScene(fire2,70);
			break;
		}
		return b;
	}
	
	public void update(long timePassed){
		a.update(timePassed);
		x+=vx*timePassed;
		y+=vy*timePassed;
	}
	
	public Image newImage(String source){
		return new ImageIcon(getClass().getResource("/skill"+source)).getImage();
	}
}
