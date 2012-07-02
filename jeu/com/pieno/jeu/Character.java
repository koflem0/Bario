
package com.pieno.jeu;

import com.pieno.jeu.Ladder;
import com.pieno.jeu.Server2D.PassiveSkill;
import com.pieno.jeu.Server2D.Skill;

import java.awt.Rectangle;
import java.util.Random;

public class Character {
	public double x = 0, y = 0, xspeed = 0;
	public String name, map = "map0.jpg";
	public static final int FIGHTER = 0, MAGE = 1, ARCHER = 2;
	public int id, connection, dir=0, life=100, maxLife =100, mana = 100, maxMana = 100, lvl = 1, exp = 0, currentSkill = -1, skillStats = 0, attStats = 0, MF, RF;
	public boolean facingLeft, canMove, alive = true, invincible;
	transient public int defense, critChance, critDamage, wATK, mastery, baseMastery = 20, saveSlot, lifeRegen, manaRegen, lastSave = 0, skillKey = -1;
	public int[] atts = new int[4], baseAtts = {4,4,4,4};
	transient public Skill[] skills = new Skill[8];
	transient public PassiveSkill[] passives = new PassiveSkill[8];
	public int classe;
	transient int respawnTimer = 5000;
	transient public double vx=0, vy=0;
	transient public boolean[] moveKeys = {false,false,false,false,false};
	transient public boolean changedMap = true;
	transient Ladder onLadder;
	transient int pressingClimb = 0, invincibleTime;
	transient public Inventory inventory = new Inventory();
	public int[] skillLvls = {1,0,0,0,0,0,0};
	public int[] passiveLvls = {0,0,0,0};
	
	public Character(){}
	
	public float getStat(int stat)
	{
		switch (stat)
		{
		case Network.SPIRIT:
		case Network.POW:
		case Network.AGI:
		case Network.VIT:
			return atts[stat];
		case Network.CRITDMG:
			return critDamage;
		case Network.CRIT:
			return critChance;
		case Network.MASTERY:
			return mastery;
		case Network.IF:
			return MF;
		case Network.RF:
			return RF;
		}
		return 0;
	}
	
	public int getMinDamage()
	{
		float dmg = ((wATK * (atts[0] + 100) / 100));
		dmg = dmg * mastery / 100;
		if (dmg < 1)
			dmg = 1;
		return (int) dmg;
	}

	public int getMaxDamage()
	{
		return ((wATK * (atts[0] + 100) / 100));
	}
	
	public int getDamage(Monster monster, float dmgMult)
	{
		Random rand = new Random();
		if (monster.avoid > rand.nextInt(100) + atts[Network.AGI])
			return 0;
		int dmast = rand.nextInt(100 - monster.getMastery())
				+ monster.getMastery();

		float dmg = (((wATK * (atts[0] + 100) / 100) * dmgMult));
		dmg = dmg * dmast / 100;
		dmg = (float) (dmg * (1 - monster.getDefense()
				/ (monster.getDefense() + 22 * Math.pow(1.1, lvl))));
		if (dmg < 1)
			dmg = 1;
		return (int) dmg;
	}
	
	public void setInvincible(int time){
		invincible = true;
		invincibleTime = time;
	}
	
	public double getDamageReduction()
	{
		return 100 * defense / (defense + 22 * Math.pow(1.1, lvl));
	}
	
	public void loadStats()
	{
		for (PassiveSkill passive : passives)
			if (passive != null)
				passive.skillStats();

		for (int i = 0; i < 4; i++)
		{
			atts[i] = baseAtts[i] + inventory.getStat(i)
					+ inventory.getStat(Network.ALLSTATS);
			for (PassiveSkill passive : passives)
				if (passive != null)
					atts[i] += passive.getSkillStat(i);
		}

		defense = lvl + inventory.getDefense();

		maxLife = inventory.getStat(Network.HP) + (5 * lvl + 95) * (atts[Network.VIT] + 100) / 100;
		if (life > maxLife)
			life = maxLife;

		if (classe == MAGE)
			maxMana = 5 * (lvl - 1);
		else
			maxMana = 0;
		maxMana +=  inventory.getStat(Network.MANA) + 100;
		maxMana = maxMana * (atts[Network.SPIRIT] + 100) / 100;
		if (mana > maxMana)
			mana = maxMana;

		MF = inventory.getStat(Network.IF);
		RF = inventory.getStat(Network.RF);
		
		critChance = 5 + inventory.getStat(Network.CRIT);

		critDamage = 50 + inventory.getStat(Network.CRITDMG);

		wATK = inventory.getDamage();
		if (wATK == 0)
			wATK = 2;

		mastery = baseMastery + inventory.getStat(Network.MASTERY);

		for (PassiveSkill passive : passives)
			if (passive != null)
			{
				defense += passive.getSkillStat(Network.DEFENSE);
				critChance += passive.getSkillStat(Network.CRIT);
				critDamage += passive.getSkillStat(Network.CRITDMG);
				wATK += passive.getSkillStat(Network.WATK);
				mastery += passive.getSkillStat(Network.MASTERY);
			}

	}
	

	public String getResourceName()
	{
		String info = "";
		switch (classe)
		{
		case FIGHTER:
			info += "fury";
			break;
		case MAGE:
			info += "mana";
			break;
		case ARCHER:
			info += "conc";
			break;
		}
		return info;
	}
	
	public synchronized void lvlup(){
		life = maxLife;
		mana = maxMana;
		skillStats += 2;
		attStats += 5;
		exp -= expToLvl(lvl);
		lvl++;
	}
	
	public void respawn(){
		currentSkill = -1;
		canMove = true;
		life = maxLife;
		mana = maxMana;
		vx = 0;
		vy = 0;
		alive = true;
		setInvincible(1000);
		respawnTimer = 5000;
	}
	
	public void damageChar(int dmg)
	{
		if (alive)
			if (!invincible)
			{
				if (dmg > 0)
				{
					life -= dmg;
					if (classe == FIGHTER)
						if ((mana += 3 * (100 + atts[Network.SPIRIT]) / 100) > maxMana)
							mana = maxMana;
				}
				if (life <= 0)
				{
					life = 0;
					alive = false;
					exp -= expToLvl(lvl) * 0.05;
					if (exp < 0)
						exp = 0;
				}
				onLadder = null;
			}
	}
	
	public void update(long timePassed){
		if(alive){
			
			lastSave+= timePassed;
			
			if (classe == MAGE)
			{
				if (mana < maxMana)
					manaRegen += timePassed;
				if (manaRegen >= (800 * 100 / (100 + atts[Network.SPIRIT])) * 80 / (80 + lvl-1))
				{
					mana++;
					manaRegen = 0;
				}
			} else if (classe == ARCHER)
			{
				if (mana < maxMana)
				{
					manaRegen += timePassed;
					if ((vx == 0 && vx == 0))
						manaRegen += 5 * timePassed;
				}
				if (manaRegen >= (1200 * 100 / (100 + atts[Network.SPIRIT])))
				{	
					mana++;
					manaRegen = 0;
				}
			}

			if (life < maxLife)
				lifeRegen += timePassed;
			if (lifeRegen >= (2000 * 100 / (100 + atts[Network.VIT])))
			{
				life++;
				lifeRegen = 0;
			}
			
			updateXspeed(timePassed);
			
			if(canMove)
				vx=0.37d*xspeed;
			if(currentSkill != -1 && vy == 0) vx = 0;
			x += vx*timePassed;
			y += vy*timePassed;
			if(canMove)
			if(dir > 0) facingLeft = false; else if(dir < 0) facingLeft = true;
			if(onLadder == null)
			updateSkill(timePassed);
			else currentSkill = -1;
		}  else {
			respawnTimer-=timePassed;
			if(respawnTimer <= 0) respawn();
		}
		
		if(invincible && !name.equals("cheat")){
			invincibleTime -= timePassed;
			if(invincibleTime <=0) invincible = false;
		}
	}
	
	private void updateXspeed(long timePassed){
		double inc = 0.016d;
		if(vy != 0) inc = 0.009d;
		if(onLadder != null) inc = 0.012d;
		if(currentSkill != -1) inc = 0.006d;
		
		switch(dir){
		case -1:if(xspeed > -1){
			xspeed -= inc*timePassed;
			if(xspeed < -1) xspeed = -1; 
		}
			break;
		case 0:if(xspeed > 0){
			xspeed -= inc*timePassed;
			if(xspeed < 0) xspeed = 0;
		} else if(xspeed < 0){
			xspeed += inc*timePassed;
			if(xspeed > 0) xspeed = 0;
		}
			break;
		case 1:if(xspeed < 1){
			xspeed += inc*timePassed;
			if(xspeed > 1) xspeed = 1;
		}
			
			break;
		}
	}
	
	public void setSkill(int skill){
		skillKey = skill;
	}
	
	public void updateSkill(long timePassed){
		if(currentSkill!=-1 && skills[currentSkill] != null && skills[currentSkill].isActive())skills[currentSkill].update(timePassed);
		
		if((currentSkill == -1 || !skills[currentSkill].isActive()) && skillKey != -1){
			if(skillLvls[skillKey] == 0 || skills[skillKey] == null) return;
			if(currentSkill == -1 || (currentSkill == skillKey && !skills[skillKey].isActive())){
				skills[skillKey].activate();
			}
		}
		if(skillKey == -1 || skillKey !=currentSkill)
		if(currentSkill!=-1 && !skills[currentSkill].isActive())
			currentSkill = -1;
		
	}
	
	public void fall(long timePassed){
		if(vy < 0.84d && onLadder == null)
		vy += timePassed*0.0046d;
	}
	
	public void jump(){
		if(currentSkill == -1)
		vy = -1.45d;
	}
	
	public void move(int dir)
	{
		this.dir = dir;
	}
	
	public int getX(){return (int)x;}
	public int getY(){return (int)y;}
	public int getWidth(){return 120;}
	public int getHeight(){return 200;}
	
	public Rectangle getBase()
	{
		return new Rectangle(getX() + 25, getY() + getHeight() - 15,
				getWidth() - 50, 20);
	}

	public Rectangle getTop()
	{
		return new Rectangle(getX() + 20, getY() - 9, getWidth() - 40, 18);
	}

	public Rectangle getArea()
	{
		return new Rectangle(getX() + 17, getY()+2, getWidth() - 34,
				getHeight()-2);
	}

	public Rectangle getLeftSide()
	{
		return new Rectangle(getX() + 12, getY() + 13, 10, getHeight() - 24);
	}

	public Rectangle getRightSide()
	{
		return new Rectangle(getX() + getWidth() - 22, getY() + 13, 10,
				getHeight() - 24);
	}
	
	public void setClimbing(int i)
	{
		if (canMove)
			if (onLadder != null)
				vy = -i * 0.36d;
	}
	
	public static int expToLvl(int lvl)
	{
		return (int) (10 * Math.pow(1.45, lvl));

	}

}
