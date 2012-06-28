package com.pieno.jeu;
import java.io.Serializable;
import java.util.Random;


public class Item implements Serializable{
		
	private static final long serialVersionUID = 289517685004127930L;
	public static final int WEAPON = 0, TORSO = 1, HELM = 2, BOOTS = 3, RING = 6, AMULET = 7, PANTS = 5, GLOVES = 4;
	public static final int ARMOR = 0, MOP = 1, WAND = 2, BOW = 3;
	public static final int COMMON = 0, MAGIC = 1, RARE = 2;
		
	private int slot, rarity, level, classReq = -1, enhancedD=0;
	private float damage, defense;
	private int[] stats = new int[14];
	
	public Item(){
		this.slot = 0;
		this.level = 1;
		
		generateD();
	}
	
	public Item(int level, int slot){
		this.slot = slot;
		this.level = level;
		
		generateD();
	}
	
	private void generateD(){
		switch(slot){
		case(RING):case(AMULET): break;
		case(WEAPON) : damage = (int)Math.floor(Math.pow(1.1, level-1)*4.5); break;
		case(TORSO) : defense = (int)Math.floor(Math.pow(1.1, level-1)*4); break;
		default : defense = (int)Math.floor(Math.pow(1.1, level-1)*3); break;
		}
		
		Random rand = new Random();
		
		defense = (defense*(100+enhancedD)/100);
		defense = (defense *(90+rand.nextInt(41))/100);
		damage = (damage*(100+enhancedD)/100);
		if(level > 1)
		damage = (damage *(90+rand.nextInt(41))/100);
		
	}
	
	public int getLvl(){return level;}
		
	public Item(int level, int slot, int rarity){
		this.slot = slot;
		this.level = level;
		this.rarity = rarity;
		
		generateStats();
		generateD();
	}
	
	private void generateStats(){
		Random rand = new Random();
		
		int stats = 0;
		switch(rarity){
		case MAGIC : stats = rand.nextInt(2)+2; break;
		case RARE : stats = rand.nextInt(2)+4; break;
		}
		
		for(int i = 0; i < stats; i++){
			generateNewStat(i);
		}
		
	}
	
	private void generateNewStat(int i){
		Random rand = new Random();
		int stat;
		int statMax = 12;
		if(i == 0 && slot!=RING && slot!=AMULET){
			stat = rand.nextInt(4);
			if(stat < 1) enhancedD = rand.nextInt(15) + 11;
			else {
				do{stat = rand.nextInt(statMax);}
				while(stats[stat] != 0 || (stat==Network.MASTERY && slot != WEAPON));
				generateStat(stat);
			}
		} else {
			do{stat = rand.nextInt(statMax);}
			while(stats[stat] != 0 || (stat==Network.MASTERY && slot != WEAPON));
			generateStat(stat);
		}
	}
	
	private void generateStat(int stat){
		Random rand = new Random();
		switch(stat){
		case Network.MASTERY: stats[stat] = rand.nextInt(11) + 5; break;
		case Network.CRIT: stats[stat] = (int)((rand.nextDouble()+1)*Math.pow(1.05, level)); break;
		case Network.CRITDMG: stats[stat] = (int)((rand.nextDouble()*5+5)*Math.pow(1.07, level)); break;
		
		case Network.SPIRIT:
		case Network.POW:
		case Network.AGI:
		case Network.VIT:
			stats[stat] =(int) ((rand.nextDouble()*2.5 + 1.5)*Math.pow(1.1,level)); break;
		case Network.ALLSTATS:
			stats[stat] = (int) ((rand.nextDouble()*2 + 1)*Math.pow(1.1,level)); break;
		case Network.HP:
		case Network.MANA:
			stats[stat] =(int) ((rand.nextDouble()*2 + 5)*Math.pow(1.1,level)); break;
		case Network.IF:
		case Network.RF:
			stats[stat] =(int) ((rand.nextDouble()*2 + 5)*Math.pow(1.05,level)); break;
		}
	}
	
	public String getName(){
		switch(slot){
		case WEAPON : return "Weapon";
		case TORSO : return "Torso";
		case BOOTS : return "Boots";
		case PANTS : return "Pants";
		case AMULET: return "Amulet";
		case RING : return "Ring";
		case GLOVES : return "Gloves";
		case HELM : return "Helm";
		default : return "";
		}
	}
		
	public int getSlot(){return slot;}
	public int getClassReq(){return classReq;}
	public int getLevel(){return level;}
	public int getStat(int stat){return stats[stat];}
	public int getEnhancedD(){return enhancedD;}
	public int getRarity(){return rarity;}
		
	public int getD(){
		switch(slot){
		case WEAPON: return (int)damage;
		case RING : case AMULET : return 0;
		default : return (int)defense;
		}
	}	
}
	
