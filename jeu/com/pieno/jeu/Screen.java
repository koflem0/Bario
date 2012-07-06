package com.pieno.jeu;
import java.awt.*;
import java.awt.image.*;
import javax.swing.*;


public class Screen {
	
	private GraphicsDevice vc;
	
	public Screen(){
		GraphicsEnvironment e = GraphicsEnvironment.getLocalGraphicsEnvironment();
		vc = e.getDefaultScreenDevice();
	}
	
	//retourne les modes compatibles de l'�cran
	public DisplayMode[] getCompatibleModes() {
		return vc.getDisplayModes();
	}
	
	//trouve quelle mode d'�cran du jeu est compatible avec la carte graphique/l'�cran utilis�
	public DisplayMode findGoodMode(DisplayMode modes[]){
		DisplayMode[] goodModes = getCompatibleModes();
		for(int x=0; x < modes.length; x++){
			for(int y = 0; y <goodModes.length; y++){
				if(modes[x].equals(goodModes[y])) return modes[x];
			}
		}
		return null;
	}
	
	//retourne le Display Mode utilis�
	public DisplayMode getCurrentMode(){
		return vc.getDisplayMode();
	}
	
	//mets le jeu Full Screen
	public void setFullScreen(DisplayMode dm){
		
		JFrame f = new JFrame();
		f.setUndecorated(true);
		f.setResizable(false);
		f.setIgnoreRepaint(true);
		vc.setFullScreenWindow(f);
		setDisplayMode(dm);
		f.createBufferStrategy(2);
	}

	//set la mode de l'�cran
	public void setDisplayMode(DisplayMode dm){
		if(dm != null && vc.isDisplayChangeSupported()){
			try{
				vc.setDisplayMode(dm);
			}catch(Exception e) {} }
	}
	
	//retourne les graphique
	public Graphics2D getGraphics(){
		Window w = vc.getFullScreenWindow();
		if(w != null){
			BufferStrategy s = w.getBufferStrategy();
			return(Graphics2D) s.getDrawGraphics();
		}
		return null;
	}
	
	//update l'�cran
	public void update(){
		Window w = vc.getFullScreenWindow();
		if(w != null){
			BufferStrategy s = w.getBufferStrategy();
			if(!s.contentsLost()) s.show();
		}
	}
	
	//retourne l'�cran
	public Window getFSWindow(){
		return vc.getFullScreenWindow();
	}
	
	//retourne la taille de l'�cran
	public int getWidth(){
		Window w = getFSWindow();
		if(w != null) return w.getWidth();
		return 0;
	}
	public int getHeight(){
		Window w = getFSWindow();
		if(w != null) return w.getHeight();
		return 0;
	}
	
	//Exit full screen
	public void restoreScreen(){
		Window w = getFSWindow();
		if(w != null) w.dispose();
		vc.setFullScreenWindow(null);
	}
	
	//cr�e une image compatible � l'�cran
	public BufferedImage createImage(int W, int H, int T){
		Window w = getFSWindow();
		if(w != null){
			GraphicsConfiguration gc = w.getGraphicsConfiguration();
			return gc.createCompatibleImage(W,H,T);
		}
		return null;
	}
	
}
