package main.java;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.awt.event.KeyEvent;
import controlP5.ControlP5;
import processing.core.PApplet;
import processing.data.JSONArray;
import processing.data.JSONObject;

/**
* This class is for sketching outcome using Processing
* You can do major UI control and some visualization in this class.  
*/
@SuppressWarnings("serial")
public class MainApplet extends PApplet{
	private String path = "main/resources/";
	private String file = "starwars-episode-1-interactions.json";
	
	JSONObject data;
	JSONArray nodes,links;
	
	Character dragCh;
	int netX, netY, netRadius;
	
	private ArrayList<Character> characters;
	
	private ControlP5 cp5;
	
	private final static int width = 1200, height = 650;
	
	public void setup() {

		size(width, height);
		smooth();
		loadData();
		//buttons
		cp5 = new ControlP5(this);
		cp5.addButton("ADD ALL").setLabel("ADD ALL").setPosition(900,30).setSize(100,50);
		cp5.addButton("CLEAR").setLabel("CLEAR").setPosition(1050,30).setSize(100,50);
		//
		this.netRadius = 290;
		this.netX = 630;
		this.netY = 340;
		
	}

	public void draw() {
		background(250);
		//draw big circle
		this.stroke(180, 238, 180);
		this.strokeWeight(5);
		this.fill(250);
		this.ellipse(this.netX, this.netY, this.netRadius*2, this.netRadius*2);
		//draw characters
		for(Character ch: characters){
			//draw circles
			ch.display();
			//show character name
			if ((mouseX>ch.initX-20 && mouseX<ch.initX+20) && (mouseY>ch.initY-20 && mouseY<ch.initY+20)){
				ch.setRadius(50);			//bigger circle size
				this.fill(180, 238, 180);	//color of name
				text(ch.name, ch.initX+10, ch.initY);	//name
			} else {
				ch.setRadius(40);			//original circle size
			}
		}
		
	}
	public void mousePressed(){
		for (Character ch : characters){
			if ((mouseX>ch.initX-20 && mouseX<ch.initX+20) && (mouseY>ch.initY-20 && mouseY<ch.initY+20)){
				dragCh = ch;
				//System.out.println("pressed");
			}
		}
	}
	public void mouseDragged(){
		//System.out.println("dragged");
		dragCh.setDrag(true);
		dragCh.setDragX(pmouseX);
		dragCh.setDragY(pmouseY);
	}
	public void mouseReleased(){
		if (dragCh.getDrag()){
			dragCh.setDrag(false);
			//judge join network circle or not
			if ((mouseX>this.netX-this.netRadius)&&(mouseX<this.netX+this.netRadius)&&(mouseY>this.netY-this.netRadius)&&(mouseY<this.netY+this.netRadius)){
				dragCh.setInCircle(true);
				dragCh.setCircleX(netX+netRadius);
				dragCh.setCircleY(netY);
			}
			else{
				dragCh.setInCircle(false);
			}
		}
	}
	private void loadData(){
		
		characters = new ArrayList<Character>();
		data = loadJSONObject(path+file);
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
			characters.get(source).setValue(target, value);
		}
	}

}
