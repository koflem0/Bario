package com.pieno.jeu;

import com.pieno.jeu.Animation;

import java.awt.Image;

import javax.swing.ImageIcon;

public class MonsterAnimation{
	int x, y, type, eliteType, lvl;
	int lastUpdate = 0, frames = 0;
	int lifePercentage;
	private Image[] monstreD,monstreG;
	private Image monstreHitD, monstreHitL;
	Animation hitLeft, hitRight, left, right;
	boolean summoned = false;
	boolean canMove, facingLeft, alive = false;
	
	public MonsterAnimation(int type){
		this.type = type;
		getAnimations(type);
		
	}
	
	// load les images du monstre
	public void loadpics(int y) {
		String name = "";
		switch (y) {
		case Monster.COBRA:
			name = "cobra";
			frames = 2;
			break;
		case Monster.BIGCOBRA:
			name = "bigcobra";
			frames = 2;
			break;
		case Monster.VERYBIGCOBRA:
			name = "verybigcobra";
			frames = 2;
			break;
		case Monster.COC:
			name = "coc";
			frames = 4;
			break;
		case Monster.MUSH:
			name = "mushjump";
			frames = 10;
			break;
		case Monster.MUSHETTE:
			name = "MUSHETTE";
			frames = 3;
			break;
		case Monster.BABYSPIDER:
			name = "babyspider";
			frames = 2;
			break;
		case Monster.MUSHY:
			name = "MUSHY";
			frames = 3;
			break;
		case Monster.TREANT:
			name = "TREANT";
			frames = 4;
		}
		
		monstreD = new Image[frames+1];
		monstreG = new Image[frames+1];
		for(int i = 1; i <= frames; i++){
			monstreD[i] = newImage("/"+name+i+"D.png");
			monstreG[i] = newImage("/"+name+i+"G.png");
		}
	}
	
	// load les animations du monstre
	private void getAnimations(int i) {
		hitLeft = new Animation();
		hitRight = new Animation();
		right = new Animation();
		left = new Animation();
		loadpics(i);
		switch (i) {
		case Monster.COBRA:
		case Monster.BIGCOBRA:
		case Monster.VERYBIGCOBRA:
			right.addScene(monstreD[1], 220);
			right.addScene(monstreD[2], 220);
			left.addScene(monstreG[1], 220);
			left.addScene(monstreG[2], 220);
			hitLeft.addScene(monstreG[1], 200);
			hitRight.addScene(monstreD[1],200);
			break;
		case Monster.COC:
			right.addScene(monstreD[1],110);
			right.addScene(monstreD[2],110);
			right.addScene(monstreD[3],110);
			right.addScene(monstreD[4],110);
			left.addScene(monstreG[1],110);
			left.addScene(monstreG[2],110);
			left.addScene(monstreG[3],110);
			left.addScene(monstreG[4],110);
			hitLeft.addScene(monstreG[1], 200);
			hitRight.addScene(monstreD[1],200);
			break;
		case Monster.MUSH:
			right.addScene(monstreD[1], 50);
			right.addScene(monstreD[2], 50);
			right.addScene(monstreD[3], 50);
			right.addScene(monstreD[4], 50);
			right.addScene(monstreD[5], 50);
			right.addScene(monstreD[6], 50);
			right.addScene(monstreD[7], 50);
			right.addScene(monstreD[8], 50);
			right.addScene(monstreD[9], 50);
			right.addScene(monstreD[10], 50);
			left.addScene(monstreG[1], 50);
			left.addScene(monstreG[2], 50);
			left.addScene(monstreG[3], 50);
			left.addScene(monstreG[4], 50);
			left.addScene(monstreG[5], 50);
			left.addScene(monstreG[6], 50);
			left.addScene(monstreG[7], 50);
			left.addScene(monstreG[8], 50);
			left.addScene(monstreG[9], 50);
			left.addScene(monstreG[10], 50);
			hitLeft.addScene(monstreG[5], 200);
			hitRight.addScene(monstreD[5], 200);
			break;
		case Monster.BABYSPIDER:
			right.addScene(monstreD[1], 200);
			right.addScene(monstreD[2], 200);
			left.addScene(monstreG[1], 200);
			left.addScene(monstreG[2], 200);
			hitLeft.addScene(monstreG[1], 200);
			hitRight.addScene(monstreD[1], 200);
			break;
		case Monster.MUSHY:
		case Monster.MUSHETTE:
			right.addScene(monstreD[1], 190);
			right.addScene(monstreD[2], 90);
			right.addScene(monstreD[3], 190);
			right.addScene(monstreD[2], 90);
			left.addScene(monstreG[1], 190);
			left.addScene(monstreG[2], 90);
			left.addScene(monstreG[3], 190);
			left.addScene(monstreG[2], 90);
			hitLeft.addScene(monstreG[1], 200);
			hitRight.addScene(monstreD[1], 200);
			break;
		case Monster.TREANT:
			right.addScene(monstreD[1], 120);
			right.addScene(monstreD[2], 120);
			right.addScene(monstreD[3], 120);
			right.addScene(monstreD[4], 120);
			left.addScene(monstreG[1], 120);
			left.addScene(monstreG[2], 120);
			left.addScene(monstreG[3], 120);
			left.addScene(monstreG[4], 120);
			break;
		}
	}
	
	public void update(long timePassed){
		getAnimation().update(timePassed);
		lastUpdate+= timePassed;
		if(lastUpdate>=800) alive = false;
	}
	
	public String getName(){
		switch(type){
		case Monster.COBRA: return "Cobra";
		case Monster.BIGCOBRA: return "BigCobra";
		case Monster.VERYBIGCOBRA: return "VBigCobra";
		case Monster.COC: return "Cocksinel";
		case Monster.BABYSPIDER: return "BabySpider";
		case Monster.MUSHY: return "Mushy";
		case Monster.MUSH: return "Big Mush";
		case Monster.MUSHETTE: return"Mushette";
		case Monster.TREANT: return "Treant";
		}
		return"";
	}
	
	public String getEliteType(){
		switch(eliteType){
		case Monster.DMG: return "DMG";
		case Monster.SPD: return "SPD";
		case Monster.DEF: return "DEF";
		case Monster.SLOW: return "SLOW";
		default: return "";
		}
	}
	
	// retourne l'animation du monstre
	public Animation getAnimation() {
		if (facingLeft){
			if(!canMove) return hitLeft;
			return this.left;
		}else{
			if(!canMove) return hitRight;
			return right;
		}
	}
	
	public Image newImage(String source) {
		return new ImageIcon(getClass().getResource("/monster"+source)).getImage();
	}

}
