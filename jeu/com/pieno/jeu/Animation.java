package com.pieno.jeu;
import java.awt.Image;
import java.util.*;

public class Animation {

	private ArrayList<OneScene> scenes;
	private int sceneIndex;
	private long movieTime, totalTime;
	

	public Animation() {
		scenes = new ArrayList<OneScene>();
		totalTime = 0;
		start();
	}
	
	public Animation(Animation a){
		scenes = a.scenes;
		totalTime = a.totalTime;
		start();
	}
	
	//ajoute une image � l'animation
	public synchronized void addScene(Image i, int t){
		totalTime += t;
		scenes.add(new OneScene(i, totalTime));
	}
	
	public long getTime(){
		return movieTime;
	}
	public long getTotalTime(){
		return totalTime;
	}
	public void setTime(long time){
		movieTime = time;
		update();
	}
	
	//recommence l'animation
	public synchronized void start(){
		movieTime = 0;
		sceneIndex = 0;
	}
	
	//change l'image selon le temps �coul�
	public synchronized void update(long timePassed){
		if(scenes.size() > 1){
			movieTime += timePassed;
			if(movieTime >= totalTime) start();
			
			while(movieTime > getScene(sceneIndex).endTime) sceneIndex++;
		}
	}
	
	public synchronized void update(){
		if(scenes.size() > 1){
		if(movieTime >= totalTime) start();
		while(movieTime > getScene(sceneIndex).endTime) sceneIndex++;
	}
	}
	
	//retourne l'image actuelle
	public synchronized Image getImage() {
		if(scenes.size()==0) return null;
		else return getScene(sceneIndex).pic;
	}
	
	
	//retourne une sc�ne (une image accompagn�e d'un temps)
	private OneScene getScene(int x){
		return(OneScene)scenes.get(x);
	}
	
	//////////////classe interne, une sc�ne est un objet compos� d'une image et de son temps durant l'animation ////////////
	private class OneScene{
		Image pic;
		long endTime;
		
		public OneScene(Image pic, long endTime){
			this.pic = pic;
			this.endTime = endTime;
		}
	}
	
}
