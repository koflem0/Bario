
package com.pieno.jeu;


import java.util.Vector;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;
import com.pieno.jeu.Server2D.PassiveSkill;
import com.pieno.jeu.Server2D.Skill;

// This class is a convenient place to keep things common to both the client and server.
public class Network {
	static public final int port = 54555;
	static public final int UP = 0, DOWN = 1, LEFT = 2, RIGHT = 3, JUMP = 4;
	public static final int POW = 0, AGI = 1, SPIRIT = 2, VIT = 3, CRIT = 4,
			CRITDMG = 5, MASTERY = 6, ALLSTATS = 7, HP = 11, MANA = 10, IF = 8, RF = 9, DEFENSE = 19, WATK = 20;
	public static final int ARROW = 0, ENERGY = 2, FIRE = 3;
	public static final int EXPLOSION = 0, EXPLOARROW = 1, LVLUP = 2;
	public static final int walkL = 0, walkR = 1, standL = 2, standR = 3,
			jumpR = 4, jumpL = 5, climb = 6, onladder = 7, climbSide = 8, wizwalkL = 9, wizwalkR = 10, wizstandL = 11, wizstandR = 12,
					wizjumpL = 13, wizjumpR = 14, wizclimb = 15;

	// This registers objects that are going to be sent over the network.
	static public void register (EndPoint endPoint) {
		Kryo kryo = endPoint.getKryo();
		kryo.register(Login.class);
		kryo.register(RegistrationRequired.class);
		kryo.register(Register.class);
		kryo.register(AddCharacter.class);
		kryo.register(UpdateCharacter.class);
		kryo.register(RemoveCharacter.class);
		kryo.register(Character.class);
		kryo.register(PressedKeys.class);
		kryo.register(boolean[].class);
		kryo.register(UpdateMonster.class);
		kryo.register(int[].class);
		kryo.register(AddProjectile.class);
		kryo.register(RemoveProjectile.class);
		kryo.register(AddEffect.class);
		kryo.register(DamageText.class);
		kryo.register(Inventory.class);
		kryo.register(Item[][].class);
		kryo.register(Item[].class);
		kryo.register(Item.class);
		kryo.register(Lvlskill.class);
		kryo.register(Lvlstat.class);
		kryo.register(Skills.class);
		kryo.register(Stats.class);
		kryo.register(Pickup.class);
		kryo.register(Vector.class);
		kryo.register(AddDrop.class);
		kryo.register(RemoveDrop.class);
		kryo.register(Equips.class);
		kryo.register(Items.class);
		kryo.register(SwitchItems.class);
		kryo.register(RequestSaves.class);
		kryo.register(SaveInfo.class);
		kryo.register(SaveInfo[].class);
		kryo.register(SavesInfo.class);
		kryo.register(DeleteSave.class);
		kryo.register(AuthResponse.class);
		kryo.register(Authentication.class);
		kryo.register(CharStats.class);
		kryo.register(RequestStats.class);
		kryo.register(AddExp.class);
		kryo.register(ChangedMap.class);
		kryo.register(UpdateSkill.class);
	}
	
	static public class ChangedMap{
		int id;
		String map;
	}
	
	static public class RequestStats{}
	
	static public class CharStats{
		int def, watk, crit, critd, rarec, itemf, mastery;
		int[] atts;
	}

	static public class RequestSaves {
		public String name, pass;
	}
	
	static public class SavesInfo {
		public SaveInfo[] saves = new SaveInfo[4];
	}
	static public class SaveInfo {
		public int classe = -1, lvl;
	}
	static public class DeleteSave {
		public int slot;
		public String name;
	}
	
	static public class AuthResponse {
		boolean accepted;
	}
	
	static public class Authentication {
		String name, pass;
	}
	
	static public class Pickup {}
	
	static public class Items {
		public Item[][] items;
	}
	
	static public class Equips {
		public Item[] equips;
	}
	
	static public class SwitchItems {
		int previousI = -1, previousJ = -1, i = -1, j = -1;
		boolean add = false;
	}
	
	static public class Login {
		public String name, pass;
		public int saveSlot = 0;
	}

	static public class RegistrationRequired {
	}
	
	static public class Lvlskill {
		boolean passive = false, up = true;
		int skill;
	}
	
	static public class Lvlstat {
		boolean up = true;
		int stat;
	}
	
	static public class Stats {
		int[] atts;
		int attStats;
	}
	
	static public class Skills {
		int[] skillLvls, passiveLvls;
		int skillStats;
	}

	static public class Register {
		public String name;
		public int classe;
		public int saveSlot = 0;
	}
	
	static public class DamageText {
		public int dmg, x, y;
		public boolean crit = false;
	}
	
	static public class AddEffect {
		public int x,y,id,cid=-1,type;
	}
	
	static public class AddDrop {
		public int x,y,type,rarity,id;
	}
	
	static public class RemoveDrop {
		public int id;
	}
	
	static public class UpdateSkill {
		public int skill, id;
		public boolean facingLeft;
	}

	static public class AddProjectile {
		public int id,x,y,type;
		public float vx,vy;
	}
	
	static public class RemoveProjectile {
		public int id;
	}
	
	static public class AddExp {
	public int exp, lvl;
	}
	
	static public class UpdateCharacter {
		public int id, x, y, life, maxLife, mana, maxMana, currentAnim;
		public boolean invincible, usingSkill = true;
	}

	static public class AddCharacter {
		public Character character;
	}

	static public class RemoveCharacter {
		public int id;
	}

	static public class PressedKeys {
		public boolean[] moveKeys;
		public int skill = -1;
	}
	
	static public class UpdateMonster {
		public int id, x, y, type, eliteType, lvl;
		public int lifePercentage;
		public boolean facingLeft, canMove, alive = false;
	}
}
