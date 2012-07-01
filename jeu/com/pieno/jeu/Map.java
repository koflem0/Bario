package com.pieno.jeu;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.swing.ImageIcon;

	public class Map {
		
		public String map;
		private ArrayList<Spot> spots = new ArrayList<Spot>();
		int Xlimit, Ylimit;
		private ArrayList<Wall> walls = new ArrayList<Wall>();
		private ArrayList<Platform> platforms = new ArrayList<Platform>();
		private ArrayList<Monster> monsters = new ArrayList<Monster>();
		private ArrayList<Ladder> ladders = new ArrayList<Ladder>();
		private Image background;
		private Rectangle[] water = new Rectangle[5];

		public Map(String map) {
			this.map = map;
			int x,y,width,height;
			File mapFile = new File("jeu/map", map.substring(0, map.indexOf("."))+".map");
			background = newImage("/"+map);
			Xlimit = background.getWidth(null);
			Ylimit = background.getHeight(null);
			String line;
			try {
				BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream(mapFile)));
				while((line = input.readLine())!=null){
					StringTokenizer bob = new StringTokenizer(line);
					switch(bob.nextToken().toLowerCase().charAt(0)){
					case 'p':
						x=Integer.parseInt(bob.nextToken());
						y=Integer.parseInt(bob.nextToken());
						width=Integer.parseInt(bob.nextToken());
						platforms.add(new Platform(x,y,width));
						break;
					case 'w':
						x=Integer.parseInt(bob.nextToken());
						y=Integer.parseInt(bob.nextToken());
						width=Integer.parseInt(bob.nextToken());
						height=Integer.parseInt(bob.nextToken());
						walls.add(new Wall(x,y,width,height));
						break;
					case 'm':
						int type = Integer.parseInt(bob.nextToken());
						x = Integer.parseInt(bob.nextToken());
						y = Integer.parseInt(bob.nextToken());
						monsters.add(new Monster(type, new Point(x,y)));
						break;
					case 's':
						x = Integer.parseInt(bob.nextToken());
						y = Integer.parseInt(bob.nextToken());
						int playerX = Integer.parseInt(bob.nextToken());
						int playerY = Integer.parseInt(bob.nextToken());
						String m = bob.nextToken().trim();
						boolean invisible = false;
						if(bob.hasMoreTokens()) invisible = Boolean.parseBoolean(bob.nextToken());
						spots.add(new Spot(new Point(x,y), new Point(playerX,playerY), m, invisible));
						break;
					case 'l':
						x = Integer.parseInt(bob.nextToken());
						y = Integer.parseInt(bob.nextToken());
						width=Integer.parseInt(bob.nextToken());
						if(width <= 2) width = 0;
						height=Integer.parseInt(bob.nextToken());
						ladders.add(new Ladder(x,y,width,height));
						break;
					}
				}
				limitWalls();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
		public void limitWalls(){
			walls.add(new Wall(-2,0,4,Ylimit));
			walls.add(new Wall(Xlimit-2,0,4,Ylimit));
			walls.add(new Wall(0,-2,Xlimit,4));
			walls.add(new Wall(0,Ylimit-2, Xlimit,4));
		}

		public ArrayList<Ladder> getLadders() {
			return ladders;
		}
		
		public Rectangle[] getWater() {
			return water;
		}
		
		public Image getBackground(){return background;}
		
		// retourne les murs et les platformes de la map
		public ArrayList<Wall> getWalls() {
			return walls;
		}

		public ArrayList<Platform> getPlatforms() {
			return platforms;
		}

		// retourne un spot de téléportation
		public Rectangle getSpot(int i) {
			return spots.get(i).getArea();
		}

		// retourne tous les spots de téléportation
		public ArrayList<Spot> getSpots() {
			return spots;
		}
		
		public Image newImage(String source) {
			return new ImageIcon(getClass().getResource("/map"+source)).getImage();
		}

		// retourne la prochaine map
		public String getNextMap(int i) {
			return spots.get(i).getNextMap();
		}

		// returns the character's starting spot on the map
		public Point getStart(int i) {
			return spots.get(i).getSpawn();
		}

		// retourne les limites de la map
		public int getXLimit() {
			return Xlimit;
		}

		public int getYLimit() {
			return Ylimit + 50;
		}

		// retourne les monstres de la map
		public ArrayList<Monster> getMonsters() {
			return monsters;
		}

	}
