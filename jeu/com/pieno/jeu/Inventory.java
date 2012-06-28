package com.pieno.jeu;
import java.awt.Rectangle;
import java.io.Serializable;


public class Inventory implements Serializable{
		
		private static final long serialVersionUID = 9172706841883239806L;
		private boolean open;
		public Item[][] items = new Item[8][8];
		transient ItemSlot[][] itemSlot = new ItemSlot[8][8];
		public Item[] equip = new Item[8];
		transient ItemSlot[] equipSlot = new ItemSlot[8];
		public static final int x = 550,y = 38,width = 700,height = 550;
		
		public Inventory(){
			
			equip[Item.WEAPON] = new Item();
		}
		
		public void generateSlots(){
			for(int i = 0; i < 8; i++){
				equipSlot[i] = new ItemSlot(i, this);
				for(int j = 0; j < 8 ; j++){
					itemSlot[i][j] = new ItemSlot(i,j, this);
				}
			}
		}
		
		public void equip(int i, int j){
			//TODO item reqs check
			if(items[i][j]!=null){
				
				int slot = items[i][j].getSlot();
				
				Item holder = equip[slot];
				
				equip[slot] = items[i][j];
				delete(i,j);
				if(holder != null){
					add(holder);
				}
			}
		}
		
		public Rectangle getArea(){return new Rectangle(x,y,width,height);}
		
		public void unequip(int slot){
			Item holder = equip[slot];
			equip[slot] = null;
			add(holder);
		}
		
		public int getDamage(){
			if(equip[Item.WEAPON] == null) return 2;
			return equip[Item.WEAPON].getD();
		}
		
		public int getDefense(){
			int defense = 0;
			for(Item item : equip)if(item!=null){
				if(item.getSlot()!=Item.WEAPON) defense+=item.getD();
			}
			return defense;
		}
		
		public void setItem(Item item, int i, int j){ items[i][j] = item;}
		public Item getItem(int i, int j){ return items[i][j];}
		public void setEquip(Item item, int i){equip[i] = item;}
		public Item getEquip(int i){return equip[i];}
		
		public int getStat(int i){
			int stat = 0;
			for(Item item : equip){if(item!=null)stat+=item.getStat(i);}
			return stat;
		}
		
		public void delete(int i){
			equip[i] = null;
		}
		public void delete(int i, int j){
			items[i][j] = null;
		}
		
		public boolean add(Item item){
			
			for(int i = 0; i < 8; i++){
				for(int j = 0; j < 8; j++){
					
					if(items[j][i]==null){items[j][i]=item;return true;}
				}
			}
	
			return false;
		}
		
		public boolean isOpen() {return open;}
		public void toggle() {
			generateSlots();
			open = !open;
		}
		
	}
	
