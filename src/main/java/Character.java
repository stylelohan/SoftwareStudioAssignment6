package main.java;

import java.util.ArrayList;

import processing.core.PApplet;

/**
* This class is used to store states of the characters in the program.
* You will need to declare other variables depending on your implementation.
*/
public class Character {
	
	private MainApplet parent;
	int initX,initY,circleX,circleY;
	int colour;
	String name;
	private ArrayList<Character> targets;
	private int value[];

	public Character(MainApplet parent,String name,String colour,int x,int y){
		targets = new ArrayList<Character>();
		this.parent = parent;
		this.name = name;
		this.colour = this.parent.unhex(colour.substring(1));
		this.initX = x;
		this.initY = y;
		value = new int[100];
	}

	public void addTarget(Character target){
		targets.add(target);
	}
	
	public ArrayList<Character> getTargets(){
		return this.targets;
	}
	
	public void setValue(int index,int v){
		value[index] = v;
	}
	
	public void display(){
		this.parent.noStroke();
		this.parent.fill(this.colour);
		this.parent.ellipse(this.initX, this.initY, 50, 50);
		
	}
	
}
