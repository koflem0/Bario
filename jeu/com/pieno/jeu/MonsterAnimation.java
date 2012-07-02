package com.pieno.jeu;

import com.pieno.jeu.Animation;

import java.awt.Image;

import javax.swing.ImageIcon;

public class MonsterAnimation{
	int x, y, type, eliteType, lvl;
	int lastUpdate = 0;
	int lifePercentage;
	private Image[] monstreD = new Image[12],monstreG = new Image[12];
	private Image monstreHitD, monstreHitL;
	Animation hitLeft, hitRight, left, right;
	boolean canMove, facingLeft, alive = false;
	
	public MonsterAnimation(int type){
		this.type = type;
		getAnimations(type);
		
	}
	
	// load les images du monstre
	public void loadpics(int i) {
		switch (i) {
		case Monster.COBRA:
			monstreD[1] = newImage("/cobra1D.png");
			monstreD[2] = newImage("/cobra2D.png");
			monstreG[1] = newImage("/cobra1G.png");
			monstreG[2] = newImage("/cobra2G.png");
			break;
		case Monster.BIGCOBRA:
			monstreD[1] = newImage("/bigcobra1D.png");
			monstreD[2] = newImage("/bigcobra2D.png");
			monstreG[1] = newImage("/bigcobra1G.png");
			monstreG[2] = newImage("/bigcobra2G.png");
			break;
		case Monster.VERYBIGCOBRA:
			monstreD[1] = newImage("/verybigcobra1D.png");
			monstreD[2] = newImage("/verybigcobra2D.png");
			monstreG[1] = newImage("/verybigcobra1G.png");
			monstreG[2] = newImage("/verybigcobra2G.png");
			break;
		case Monster.COC:
			monstreD[1] = newImage("/coc1D.png");
			monstreD[2] = newImage("/coc2D.png");
			monstreD[3] = newImage("/coc3D.png");
			monstreD[4] = newImage("/coc4D.png");
			monstreG[1] = newImage("/coc1G.png");
			monstreG[2] = newImage("/coc2G.png");
			monstreG[3] = newImage("/coc3G.png");
			monstreG[4] = newImage("/coc4G.png");
			break;
		case Monster.MUSH:
			monstreD[0] = newImage("/mushjumpR.png");
			monstreD[1] = newImage("/mushjumpR1.png");
			monstreD[2] = newImage("/mushjumpR2.png");
			monstreD[3] = newImage("/mushjumpR3.png");
			monstreD[4] = newImage("/mushjumpR4.png");
			monstreD[5] = newImage("/mushjumpR5.png");
			monstreD[6] = newImage("/mushjumpR6.png");
			monstreD[7] = newImage("/mushjumpR7.png");
			monstreD[8] = newImage("/mushjumpR8.png");
			monstreD[9] = newImage("/mushjumpR9.png");
			monstreG[0] = newImage("/mushjumpL.png");
			monstreG[1] = newImage("/mushjumpL1.png");
			monstreG[2] = newImage("/mushjumpL2.png");
			monstreG[3] = newImage("/mushjumpL3.png");
			monstreG[4] = newImage("/mushjumpL4.png");
			monstreG[5] = newImage("/mushjumpL5.png");
			monstreG[6] = newImage("/mushjumpL6.png");
			monstreG[7] = newImage("/mushjumpL7.png");
			monstreG[8] = newImage("/mushjumpL8.png");
			monstreG[9] = newImage("/mushjumpL9.png");
			break;
		case Monster.MUSHETTE:
			monstreD[1] = newImage("/MUSHETTE1D.png");
			monstreD[2] = newImage("/MUSHETTE2D.png");
			monstreD[3] = newImage("/MUSHETTE3D.png");
			monstreG[1] = newImage("/MUSHETTE1G.png");
			monstreG[2] = newImage("/MUSHETTE2G.png");
			monstreG[3] = newImage("/MUSHETTE3G.png");
			break;
		case Monster.BABYSPIDER:
			monstreG[0] = newImage("/babyspider1G.png");
			monstreG[1] = newImage("/babyspider2G.png");
			monstreD[0] = newImage("/babyspider1D.png");
			monstreD[1] = newImage("/babyspider2D.png");
			break;
		case Monster.MUSHY:
			monstreD[1] = newImage("/MUSHY1D.png");
			monstreD[2] = newImage("/MUSHY2D.png");
			monstreD[3] = newImage("/MUSHY3D.png");
			monstreG[1] = newImage("/MUSHY1G.png");
			monstreG[2] = newImage("/MUSHY2G.png");
			monstreG[3] = newImage("/MUSHY3G.png");
			break;
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
			right.addScene(monstreD[0], 50);
			right.addScene(monstreD[1], 50);
			right.addScene(monstreD[2], 50);
			right.addScene(monstreD[3], 50);
			right.addScene(monstreD[4], 50);
			right.addScene(monstreD[5], 50);
			right.addScene(monstreD[6], 50);
			right.addScene(monstreD[7], 50);
			right.addScene(monstreD[8], 50);
			right.addScene(monstreD[9], 50);
			left.addScene(monstreG[0], 50);
			left.addScene(monstreG[1], 50);
			left.addScene(monstreG[2], 50);
			left.addScene(monstreG[3], 50);
			left.addScene(monstreG[4], 50);
			left.addScene(monstreG[5], 50);
			left.addScene(monstreG[6], 50);
			left.addScene(monstreG[7], 50);
			left.addScene(monstreG[8], 50);
			left.addScene(monstreG[9], 50);
			hitLeft.addScene(monstreG[5], 200);
			hitRight.addScene(monstreD[5], 200);
			break;
		case Monster.BABYSPIDER:
			right.addScene(monstreD[0], 200);
			right.addScene(monstreD[1], 200);
			left.addScene(monstreG[0], 200);
			left.addScene(monstreG[1], 200);
			hitLeft.addScene(monstreG[0], 200);
			hitRight.addScene(monstreD[0], 200);
			break;
		case Monster.MUSHY:
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
		}
	}
	
	public void update(long timePassed){
		getAnimation().update(timePassed);
		lastUpdate+= timePassed;
		if(lastUpdate>=700) alive = false;
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
