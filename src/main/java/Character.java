package main.java;

import java.util.ArrayList;

import processing.core.PApplet;

/**
* This class is used to store states of the characters in the program.
* You will need to declare other variables depending on your implementation.
*/
public class Character {
	
	private MainApplet parent;
	int initX, initY, circleX, circleY, dragX, dragY, radius;
	int colour;
	String name;
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
	public void setValue(int index,int v){
		value[index] = v;
	}
	public void setCircleX(int x){
		this.circleX = x;
	}
	public void setCircleY(int y){
		this.circleY = y;
	}
	public void setDragX(int x){
		this.dragX = x;
	}
	public void setDragY(int y){
		this.dragY = y;
	}
	public void display(){
		this.parent.noStroke();
		this.parent.fill(this.colour);
		if (drag){
			this.parent.ellipse(dragX, dragY, radius, radius);
		}
		else if (inCircle){
			this.parent.ellipse(this.circleX, this.circleY, radius, radius);
		}
		else {
			this.parent.ellipse(this.initX, this.initY, radius, radius);
		}
		
		
	}
	
}
