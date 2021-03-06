package main.java;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.awt.event.KeyEvent;
import controlP5.ControlP5;
import ddf.minim.AudioPlayer;
import ddf.minim.Minim;
import processing.core.PApplet;
import processing.data.JSONArray;
import processing.data.JSONObject;
import java.util.Random;

/**
* This class is for sketching outcome using Processing
* You can do major UI control and some visualization in this class.  
*/
@SuppressWarnings("serial")
public class MainApplet extends PApplet{
	private String path = "main/resources/";
	Minim minim;
	AudioPlayer song;
	
	JSONObject data;
	JSONArray nodes,links;
	
	Character dragCh;
	int netX, netY, netRadius, netWeight;
	int circleNum = 0;
	int episode;
	int sub = 10;
	boolean changeVersion;
	
	private ArrayList<Character> characters;
	private ArrayList<Character> circleList;
	
	private ControlP5 cp5;
	
	private final static int width = 1200, height = 650;
	
	public void setup() {
		
		size(width, height);
		smooth();
		//
		characters = new ArrayList<Character>();
		circleList = new ArrayList<Character>();
		this.episode = 1;
		loadData();
		//music
		minim = new Minim(this);
		song = minim.loadFile("main/resources/Shake_It_Off.mp3");
		song.play();
		//buttons
		cp5 = new ControlP5(this);
		cp5.addButton("buttonA").setLabel("ADD ALL").setPosition(900,30).setSize(100,50);
		cp5.addButton("buttonB").setLabel("CLEAR").setPosition(1050,30).setSize(100,50);
		//
		this.netRadius = 290;
		this.netX = 630;
		this.netY = 340;
		this.netWeight = 5;
		this.changeVersion = false;
	}
	
	public void buttonA(){
		for(Character c:characters){
			c.setInCircle(true);
			circleList.add(c);
			if(circleNum!=0) circleNum += 1;
			setLittleCirclePosition();
		}
	}
	
	public void buttonB(){
		for (Character ch: characters){
			ch.setDrag(false);
			ch.setInCircle(false);
		}
	}

	public void draw() {
		background(250);
		//draw big circle
		this.stroke(180, 238, 180);
		this.strokeWeight(netWeight);
		this.fill(250);
		this.ellipse(this.netX, this.netY, this.netRadius*2, this.netRadius*2);
		//draw characters
		for(Character ch: characters){
			//draw network
			if (ch.getInCircle()){
				for(int i = 0; i < ch.getTargets().size(); i++){
					if(ch.getValue(i)>0){
						//draw line if target is also in circle
						if (ch.getTargets().get(i).getInCircle()){
							this.stroke(127, 0, 0, 50);
							this.strokeWeight(ch.getValue(i));	//set line thickness
							this.line(ch.getCircleX(), ch.getCircleY(), ch.getTargets().get(i).getCircleX(), ch.getTargets().get(i).getCircleY());
						}
						else {
							//do nothing
						}
					}
				}
			}
			//draw circles
			ch.display();
			//show character name
			if ((mouseX>ch.getNowX()-20 && mouseX<ch.getNowX()+20) && (mouseY>ch.getNowY()-20 && mouseY<ch.getNowY()+20)){
				ch.setRadius(50);			//bigger circle size
				this.fill(180, 238, 180);	//color of name
				text(ch.name, ch.getNowX()+10, ch.getNowY());	//name text
			} else {
				ch.setRadius(40);			//original circle size
			}
		}
		
	}
	public void mousePressed(){
		for (Character ch : characters){
			//a character is either in circle or at initial position
			if (ch.getInCircle()){
				if ((mouseX>ch.getCircleX()-20 && mouseX<ch.getCircleX()+20) && (mouseY>ch.getCircleY()-20 && mouseY<ch.getCircleY()+20)){
					dragCh = ch;
				}
			}
			else {
				if ((mouseX>ch.initX-20 && mouseX<ch.initX+20) && (mouseY>ch.initY-20 && mouseY<ch.initY+20)){
					dragCh = ch;
				}
			}
		}
	}
	public void mouseDragged(){
		dragCh.setDrag(true);
		dragCh.setDragX(pmouseX);
		dragCh.setDragY(pmouseY);
		if ((mouseX>this.netX-this.netRadius)&&(mouseX<this.netX+this.netRadius)&&(mouseY>this.netY-this.netRadius)&&(mouseY<this.netY+this.netRadius)){
			netWeight = 10;
		}
		else {
			netWeight = 5;
		}
	}
	public void mouseReleased(){
		netWeight = 5;
		if (dragCh==null){
			
		}
		else if (dragCh.getDrag()){
			dragCh.setDrag(false);
			//judge to 1. join the circle 2. return to initial position
			if (dragCh.getInCircle()){
				if ((mouseX>this.netX-this.netRadius)&&(mouseX<this.netX+this.netRadius)&&(mouseY>this.netY-this.netRadius)&&(mouseY<this.netY+this.netRadius)){
					//do nothing since it is already in circle
				}
				else {
					dragCh.setInCircle(false);
				}
			}
			else {
				if ((mouseX>this.netX-this.netRadius)&&(mouseX<this.netX+this.netRadius)&&(mouseY>this.netY-this.netRadius)&&(mouseY<this.netY+this.netRadius)){
					//join the circle
					dragCh.setInCircle(true);
					circleList.add(dragCh);
					circleNum += 1;
					setLittleCirclePosition();
				}
				else {
					//do nothing since it originally at initial position
					dragCh.setInCircle(false);
				}
			}
		}
		
	}
	public void setLittleCirclePosition(){
		
		if(circleNum == 1){
			float addX = (float)Math.cos(Math.toRadians(30));
			float addY = (float)Math.sin(Math.toRadians(30));
			dragCh.setCircleX(netX+netRadius*addX);
			dragCh.setCircleY(netY+netRadius*addY);
		}else{
			int num = 1;
			int degree = 360;
			for(Character n: circleList){
				degree -= sub;/**change**/
				float addX = (float)Math.cos(Math.toRadians(degree));
				float addY = (float)Math.sin(Math.toRadians(degree));
				n.setCircleX(netX+netRadius*addX);
				n.setCircleY(netY+netRadius*addY);
			}
		}
	}
	public void keyPressed() {
		if(key == KeyEvent.VK_1){
			episode = 1;
			sub = 10;
			this.changeVersion = true;
		}
		else if(key == KeyEvent.VK_2) {
			episode = 2;
			sub = 11;
			this.changeVersion = true;
		}
		else if(key == KeyEvent.VK_3) {
			episode = 3;
			sub = 15;
			this.changeVersion = true;
		}
		else if(key == KeyEvent.VK_4) {
			episode = 4;
			sub = 18;
			this.changeVersion = true;
		}
		else if(key == KeyEvent.VK_5) {
			episode = 5;
			sub = 19;
			this.changeVersion = true;
		}
		else if(key == KeyEvent.VK_6) {
			episode = 6;
			sub = 20;
			this.changeVersion = true;
		}
		else if(key == KeyEvent.VK_7) {
			episode = 7;
			sub = 15;
			this.changeVersion = true;
		}
		//
		if (this.changeVersion){
			this.clear();
			this.characters.clear();
			this.changeVersion = false;
		}
		
		loadData();
		draw();
	}
	private void loadData(){
		
		data = loadJSONObject(path+"starwars-episode-"+episode+"-interactions.json");
		nodes = data.getJSONArray("nodes");
		links = data.getJSONArray("links");
		
		int x=50,y=0,n=0;
		
		for(int i=0;i<nodes.size();i++){
			JSONObject node = nodes.getJSONObject(i);
			String name = node.getString("name");
			int value = node.getInt("value");
			String colour = node.getString("colour");
			if(n<11){
				y += 55;
				n++;
			}else{
				n = 1;
				x += 70;
				y = 55;
			}
			Character ch = new Character(this,name,colour,x,y);
			characters.add(ch);
		}
		
		for(int i=0;i<links.size();i++){
			JSONObject link = links.getJSONObject(i);
			int source = link.getInt("source");
			int target = link.getInt("target");
			int value = link.getInt("value");
			characters.get(source).addTarget(characters.get(target));
			characters.get(source).setValue(characters.get(source).getTargets().size()-1, value);
		}
	}

}
