package com.pieno.jeu;

import com.pieno.jeu.Server2D.PassiveSkill;

public class PassiveData {

	public int[] statBonus =
		{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
	
	PassiveData(int skill, int lvl){
	switch (skill)
	{
	case PassiveSkill.WandMastery:
	case PassiveSkill.MopMastery:
	case PassiveSkill.BowMastery:
		statBonus[Network.WATK] = (int) Math.floor(lvl / 2);
		statBonus[Network.MASTERY] = lvl * 4;
	}
	}
}
