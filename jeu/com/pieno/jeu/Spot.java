package com.pieno.jeu;
import java.awt.Point;
import java.awt.Rectangle;


public class Spot {
	
	private Point spawn;
	private Rectangle area;
	private String nextMap;
	public boolean invisible = false;
	
	public Spot(Point spot, Point spawn, String nextMap){
		this.area = new Rectangle(spot.x,spot.y,100,200);
		this.spawn = spawn;
		this.nextMap = nextMap;
	}
	public Spot(Point spot, Point spawn, String nextMap, boolean invisible){
		this(spot,spawn,nextMap);
		this.invisible = invisible;
	}
	
	//retourne le spawn du personnage s'il entre dans le téléporteur
	public Point getSpawn(){
		return spawn;
	}
	//retourne la zone du téléporteur
	public Rectangle getArea(){
		return area;
	}
	//retourne la prochaine map
	public String getNextMap(){
		return nextMap;
	}

}
