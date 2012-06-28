package com.pieno.jeu;
import java.awt.Rectangle;


public class Wall extends Platform{
	
	private Rectangle area;
	private int y;
	private Rectangle side;
	private int x;
	private int width;
	private int height;
	private Rectangle bottom;
	
	public Wall(int x, int y, int width, int height){
		super(x, y, width, height);
		this.side = new Rectangle(x-5,y + 2,width+10,height - 4);
		this.area = new Rectangle(x,y,width,height);
		this.width = width;
		this.x = x;
		bottom = new Rectangle(x,y+height-7,width,12);
		this.y = y;
		this.height = height;
	}
	//retourne les cot�s du mur
	public Rectangle getSide(){ return side; }
	//retourne la coordonn�e horizontale du mur
	public int getX(){return x;}
	//retourne la largeur du mur
	public int getWidth(){return width;}
	//retourne la coordonn�e veticale en haut mur / en bas du mur
	public int getTopY(){return y;}
	public int getBotY(){return (y + height);}
	//retourne le dessous du mur
	public Rectangle getBot(){ return bottom; }
	//retourne le mur au complet
	public Rectangle getArea() { return area; }
	
}
