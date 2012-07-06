package com.pieno.jeu;

import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Random;
import java.util.Vector;

import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;

public class Monster {
	public static final int COBRA = 0, BIGCOBRA = 1, COC = 2, VERYBIGCOBRA = 3, MUSH = 4, BABYSPIDER = 5, MUSHY = 6, MUSHETTE = 7, TREANT = 8;
	public static final int DMG = 0, SPD = 1, DEF = 2, SLOW = 3; 
	
	long cantMoveTime;
	private int atk, def, mastery;
	
	Vector<Monster> summons;
	
	float life, maxLife;

	private int exp, lvl, dropchance = 13, rarechance = 9, dropamount = 1;

	public int avoid, width = 100, height = 50, yCorrection = 0;
	float vx = 0, vy = 0;
	public float x = 0, y = 0;
	private float spd, allStatsMultiplier = 1, lifeMultiplier = 1, summonMultiplier = 0.5f;
	private float[] statMultipliers = {1,1,1,1,1,1,1,1,1,1};
	private boolean facingLeft = true;

	boolean canMove = true;

	private boolean alive = false, special = false;

	Character isAggro = null;
	int type = 0, eliteT = -1, summonType = -1, maxSummons = 1, summonTimer = 0, summonTime = 6000;
	private Point spawnPoint;
	private int timer, deathTimer = 200, regen = 0;

	long aggroTimer = 5000;
	
	public Monster(int i, Point spawn, float statMultiplier, int eliteType){
		this(i, spawn);
		allStatsMultiplier = statMultiplier;
		special = true;
		summonType = -1;
		summons = null;
		timer = 60000;
		lvl-=1;
		generateElite(eliteType);
		statMultipliers[SPD] = 0.9f/statMultiplier * statMultipliers[SPD];
		init();
	}
	
	public Monster(int i, Point spawn) {
		type = i;
		switch (i) {
		case COBRA:
			atk = 12;
			def = 2;
			mastery = 50;
			spd = -0.240f;
			maxLife = 13;
			timer = 12000;
			exp = 5;
			lvl = 1;
			avoid = 7;
			break;
		case BIGCOBRA:
			atk = 22;
			def = 5;
			mastery = 65;
			spd = -0.35f;
			maxLife = 25;
			timer = 30000;
			exp = 12;
			lvl = 3;
			dropchance = 20;
			dropamount = 1;
			avoid = 12;
			break;
		case VERYBIGCOBRA:
			atk = 28;
			def = 8;
			mastery = 50;
			spd = -0.40f;
			maxLife = 41;
			timer = 30000;
			exp = 20;
			lvl = 5;
			dropamount = 1;
			avoid = 20;
			break;
		case COC:
			atk = 28;
			def = 15;
			mastery = 70;
			spd = -0.37f;
			maxLife = 62;
			timer = 24000;
			exp = 22;
			lvl = 7;
			dropchance = 24;
			dropamount = 1;
			rarechance = 14;
			avoid = 12;
			break;
		case BABYSPIDER:
			atk = 32;
			def = 7;
			mastery = 70;
			spd = -0.47f;
			maxLife = 42;
			timer = 20000;
			exp = 17;
			lvl = 8;
			avoid = 18;
			dropchance = 7;
			dropamount = 1;
			width = 61;
			height = 34;
			break;
		case MUSHY:
			atk = 48;
			def = 24;
			mastery = 50;
			spd = -0.27f;
			maxLife = 131;
			timer = 30000;
			exp = 43;
			lvl = 11;
			avoid = 15;
			rarechance = 11;
			dropchance = 14;
			dropamount = 1;
			width = 63;
			height = 82;
			break;
		case MUSHETTE:
			atk = 60;
			def = 10;
			mastery = 50;
			spd = -0.37f;
			maxLife = 240;
			timer = 20000;
			exp = 60;
			lvl = 13;
			avoid = 25;
			rarechance = 11;
			dropchance = 19;
			dropamount = 1;
			width = 63;
			height = 82;
			break;
		case MUSH:
			width = 300;
			height = 300;
			special = true;
			//TODO
			break;
		case TREANT:
			atk = 100;
			def = 20;
			mastery = 50;
			spd = -0.17f;
			maxLife = 300;
			timer = 45000;
			exp = 120;
			avoid = 5;
			rarechance = 13;
			dropchance = 20;
			dropamount = 1;
			width = 59;
			height = 73;
			summonType = BABYSPIDER;
			lvl = 15;
			summonMultiplier = 1;
			maxSummons = 4;
			yCorrection = -30;
			break;
		}
		this.spawnPoint = spawn;
		if(summonType != -1) summons = new Vector<Monster>();
	}
	
	public int getHeight(){
		return height;
	}
	public int getWidth(){
		return width;
	}
	
	// initialise le monstre
	public void init() {
		if(!special)
		randomElite();
		life = getMaxLife();
		canMove = true;
		facingLeft = true;
		isAggro = null;
		vx = getSpeed();
		x = spawnPoint.x;
		y = spawnPoint.y;
		summonTimer = summonTime/5;
		alive = true;
	}
	
	public void generateElite(int type){
		eliteT = type;
		if(type == -1){
			allStatsMultiplier = 1 * allStatsMultiplier;
			for(int i = 0; i < 10; i++)
				statMultipliers[i] = 1;
			
			return;
		}
		allStatsMultiplier = 1.3f * allStatsMultiplier;
		switch(eliteT){
		case DEF : statMultipliers[DEF] = 1.3f; break;
		case DMG : statMultipliers[DMG] = 1.3f; break;
		case SPD : statMultipliers[SPD] = 1.3f; break;
		case SLOW : statMultipliers[DEF] = 1.2f; statMultipliers[SPD] = 0.6f; statMultipliers[DMG] = 1.2f; break;
		}
	}
	
	public void randomElite(){
		allStatsMultiplier = 1;
		
		Random rand = new Random();
		if(1 > rand.nextInt(10)){
			generateElite(rand.nextInt(4));
		} else {
			generateElite(-1);
		}
	}
	
	public void jump(){
		vy = -1;
	}
	
	// change le point ou le monstre apparait
	public void setSpawn(Point Spawn) {
		spawnPoint = Spawn;
	}
	
	public int getExp() {
		return (int)(exp*allStatsMultiplier);
	}
	
	// retourne la vie du monstre
	public int getLife() {
		return (int)life;
	}
	
	public void setLifeMultiplier(float lifeMultiplier){
		this.maxLife = maxLife * lifeMultiplier/this.lifeMultiplier;
		this.life = life * lifeMultiplier/this.lifeMultiplier;
		this.lifeMultiplier = lifeMultiplier;
	}

	public int getMaxLife() {
		return (int)(maxLife * allStatsMultiplier * statMultipliers[DEF]);
	}

	public int getLevel() {
		if(eliteT != -1) return lvl + 2;
		return lvl;
	}

	// modifie la position et la vitesse du monstre, le fais réapparaitre
	public void update(long timePassed) {
		if (alive) {
			regen+= timePassed;
			if(regen >= 40000/getMaxLife()+200){
				if(life < getMaxLife())
				life++;
				regen=0;
			}
			
			if(summonType != -1 && isAggro != null){
				if(summons.size() < maxSummons){
					summonTimer -= timePassed;
					if(summonTimer <= 0){
						summons.add(new Monster(summonType, new Point(getX(),getY()-yCorrection), summonMultiplier, eliteT));
						if(isAggro.x +isAggro.getWidth()/2 > x+width/2) {
							Monster summon = summons.get(summons.size()-1);
							summon.vx = -summon.vx;
							summon.setFacingLeft(false);
						}
						summonTimer = summonTime;
					}
					
				}
			}
			if(summonType != -1){
				for(Monster summon:summons){
					if(!summon.isAlive())
						summons.remove(summon);
				}
			}
			
			if(canMove && vx < 0 && vx != getSpeed()) vx = getSpeed();
			if(canMove && vx > 0 && vx != -getSpeed()) vx = -getSpeed();
			
			
			if (vx == getSpeed() || vx == -getSpeed())
				if ((isFacingLeft() && vx > 0)
						|| (!(isFacingLeft()) && vx < 0))
					vx = -vx;
			
			x += vx * timePassed;
			y += vy * timePassed;
			
			if (cantMoveTime >= 0) {
				cantMoveTime -= timePassed;
			}
			if (cantMoveTime <= 0)
				canMove = true;
			
			if(isAggro!=null){
			if(aggroTimer>=0){
				aggroTimer-=timePassed;
			}
			if(aggroTimer<=0) isAggro = null;
			}
			
		} else {
			deathTimer -= timePassed;
			if (deathTimer <= 0) {
				init();
			}
		}

	}

	// retourne si le monstre est en vie
	public boolean isAlive() {
		return alive;
	}

	// retourne si le monstre peut bouger
	public boolean canMove() {
		return canMove;
	}


	// fais mourir le monstre
	void die() {
		alive = false; isAggro = null;
		deathTimer = timer;
	}
	
	int getdropamount(){
		if(eliteT != -1) return (int) ((dropamount*1.5)*lifeMultiplier);
		return (int)(dropamount*lifeMultiplier);
	}
	
	int getdropchance(){
		return (int)(dropchance*allStatsMultiplier);
	}
	
	int getrarechance(){
		return (int)(rarechance*allStatsMultiplier);
	}
	
	public int getX(){
		return (int)x;
	}
	
	public int getY(){
		return (int)y;
		}

	// retourne l'espace ou la prochaine platforme devrait être
	public Rectangle getNextFloor() {
		if (facingLeft)
			return new Rectangle(getX()- 25, getY() + height - 5, 20,
					15);
		return new Rectangle(getX() + width + 5, getY() + height
				- 5, 20, 15);
	}

	// retourne le coté du monstre
	public Rectangle getSide() {
		if (vx < 0)
			return new Rectangle(getX() - 10, getY() + 10, 20,
					height - 25);
		return new Rectangle(getX() + width - 10, getY() + 10, 20,
				height - 25);
	}

	// retourne si le monstre "regarde" a gauche
	public boolean isFacingLeft() {
		return facingLeft;
	}

	public void setFacingLeft(boolean facingLeft) {
		this.facingLeft = facingLeft;
	}

	// retourne toute la surface du monstre
	public Rectangle getArea() {
		return new Rectangle(getX(), getY(), width, height);
	}

	// retourne la défence du monstre
	public int getDefense() {
		return (int)(def * statMultipliers[DEF] * allStatsMultiplier);
	}

	int getAtk(){
		return (int)(atk * allStatsMultiplier * statMultipliers[DMG]);
	}
	
	int getMastery(){
		int mast = (int)(mastery * allStatsMultiplier * statMultipliers[DMG]);
		if(mast >= 100) return 99;
		return mast;
	}

	// fais tomber le monstre
	public void fall(long timePassed) {
		if (vy < 0.8f)
			vy += 0.005f * timePassed;
	}

	// retourne la vitesse de base du monstre
	public float getSpeed() {
		return spd*allStatsMultiplier*statMultipliers[SPD];
	}

	// retourne la base du monstre
	public Rectangle getBase() {
		return new Rectangle(getX() + 10, getY() + height - 15,
				width - 20, 20);
	}
}
