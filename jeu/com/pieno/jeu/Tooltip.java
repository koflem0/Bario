package com.pieno.jeu;

import java.awt.Point;
import java.awt.Rectangle;
import com.pieno.jeu.Network;

public class Tooltip
{

	private Item item;
	private Rectangle area;
	private String[] stats = new String[5];

	public Tooltip(Item item, Point position)
	{
		this.item = item;
		setArea(position);

		if (item.getRarity() != Item.COMMON) getStats();

	}

	public void setArea(Point position)
	{
		area = new Rectangle(position.x + 10, position.y, 160, 165);
	}

	public Rectangle getArea()
	{
		return area;
	}

	private void getStats()
	{
		int number = 0;

		if (item.getEnhancedD() != 0)
		{

			stats[0] = item.getEnhancedD() + "% enhanced ";

			if (item.getSlot() == Item.WEAPON) stats[0] += " damage";
			else stats[0] += " defense";
			number++;
		}

		for (int i = 0; i < 14; i++)
		{
			if (item.getStat(i) != 0)
			{
				stats[number] = "+";
				stats[number] += item.getStat(i);
				switch (i)
				{
				case Network.ALLSTATS:
					stats[number] += " to all stats";
					break;
				case Network.SPIRIT:
					stats[number] += " Spirit";
					break;
				case Network.POW:
					stats[number] += " Power";
					break;
				case Network.AGI:
					stats[number] += " Agility";
					break;
				case Network.VIT:
					stats[number] += " Vitality";
					break;
				case Network.CRITDMG:
					stats[number] += "% Crit damage";
					break;
				case Network.CRIT:
					stats[number] += "% Crit chance";
					break;
				case Network.MASTERY:
					stats[number] += " Mastery";
					break;
				case Network.IF:
					stats[number] += "% Item Find";
					break;
				case Network.RF:
					stats[number] += "% Rare Chance";
					break;
				case Network.HP:
					stats[number] += " Health";
					break;
				case Network.MANA:
					stats[number] += " Mana";
					break;
				case Network.WATK:
					stats[number] += " Damage";
					break;
				case Network.DEFENSE:
					stats[number] += " Defense";
					break;
				}
				number++;
			}
		}
	}

	public Item getItem()
	{
		return item;
	}

	public String getInfo(int line)
	{
		String info = "";
		if (item.getSlot() == Item.WEAPON)
		{
			switch (line)
			{
			case 1:
				info += "Weapon";
				break;
			case 2:
				info += item.getDamage() + " damage";
				break;
			case 8:
				info += "Required level : " + item.getLevel();
				break;
			default:
				if (stats[line - 3] != null) info = stats[line - 3];
			}

		} else if (item.getSlot() != Item.RING && item.getSlot() != Item.AMULET)
		{
			switch (line)
			{

			case 1:
				info += "Armor - ";
				switch (item.getSlot())
				{
				case Item.TORSO:
					info += "torso";
					break;
				case Item.BOOTS:
					info += "boots";
					break;
				case Item.HELM:
					info += "helm";
					break;
				case Item.GLOVES:
					info += "gloves";
					break;
				case Item.PANTS:
					info += "pants";
					break;
				}
				break;

			case 2:
				info += item.getDefense() + " defense";
				break;
			case 8:
				info += "Required level : " + item.getLevel();
				break;
			default:
				if (stats[line - 3] != null) info = stats[line - 3];
			}
		} else
		{

			switch (line)
			{

			case 1:
				switch (item.getSlot())
				{
				case Item.RING:
					info += "Ring";
					break;
				case Item.AMULET:
					info += "Amulet";
					break;
				}
				break;

			case 2:
				break;
			case 8:
				info += "Required level : " + item.getLevel();
				break;
			default:
				if (stats[line - 3] != null) info = stats[line - 3];
			}
		}
		return info;
	}
}
