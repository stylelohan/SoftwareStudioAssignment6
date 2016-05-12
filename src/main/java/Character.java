package main.java;

import java.util.ArrayList;

import processing.core.PApplet;

/**
* This class is used to store states of the characters in the program.
* You will need to declare other variables depending on your implementation.
*/
public class Character {
	
	private MainApplet parent;
	int colour;
	String name;
	int radius;
	int initX, initY;
	int circleX, circleY;
	int dragX, dragY;
	int nowX, nowY;
	
	boolean drag, inCircle;
	private ArrayList<Character> targets;
	private int value[];

	public Character(MainApplet parent,String name,String colour,int x,int y){
		targets = new ArrayList<Character>();
		this.parent = parent;
		this.name = name;
		this.colour = this.parent.unhex(colour.substring(1));
		this.initX = x;
		this.initY = y;
		this.radius = 40;
		this.drag = false;
		value = new int[100];
	}
	public void addTarget(Character target){
		targets.add(target);
	}
	public ArrayList<Character> getTargets(){
		return this.targets;
	}
	public void setRadius(int r){
		this.radius = r;
	}
	public void setValue(int index,int v){
		value[index] = v;
	}
	public void setDrag(boolean b){
		this.drag = b;
	}
	public boolean getDrag(){
		return this.drag;
	}
	public void setInCircle(boolean b){
		this.inCircle = b;
	}
	public boolean getInCircle(){
		return this.inCircle;
	}
	
	public void setCircleX(int x){
		this.circleX = x;
	}
	public void setCircleY(int y){
		this.circleY = y;
	}
	public int getCircleX(){
		return this.circleX;
	}
	public int getCircleY(){
		return this.circleY;
	}
	public void setDragX(int x){
		this.dragX = x;
	}
	public void setDragY(int y){
		this.dragY = y;
	}
	public int getNowX(){
		return this.nowX;
	}
	public int getNowY(){
		return this.nowY;
	}
	public void display(){
		this.parent.noStroke();
		this.parent.fill(this.colour, 180);
		if (drag){
			this.parent.ellipse(dragX, dragY, radius, radius);
			nowX = dragX;
			nowY = dragY;
		}
		else if (inCircle){
			this.parent.ellipse(this.circleX, this.circleY, radius, radius);
			nowX = circleX;
			nowY = circleY;
		}
		else {
			this.parent.ellipse(this.initX, this.initY, radius, radius);
			nowX = initX;
			nowY = initY;
		}
		
		
	}
	
}
