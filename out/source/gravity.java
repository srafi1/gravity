import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class gravity extends PApplet {

Planet p, mouseP;

public void setup() {
    
    noStroke();
    p = new Planet(width/2, height/2, 20);
    mouseP = new Planet(0, 0, 40);
}

public void draw() {
    background(255);
    if (mousePressed) {
	p.gravitate(mouseP);
	fill(200, 0, 0);
	mouseP.draw();
    }
    p.move();

    fill(0);
    p.draw();
}

public void mouseDragged() {
    mouseP.reposition(mouseX, mouseY);
}

class Planet {
    float x, y, dx, dy, size, mass;

    Planet(float nx, float ny, float nsize) {
	x = nx;
	y = ny;
	size = nsize;
	dx = 0;
	dy = 0;
	mass = (float) (size*size*Math.PI);
    }

    public void move() {
	x += dx;
	y += dy;

	if (x - size < 0 || x + size > width) {
	    x -= dx;
	    dx *= -0.1f;
	}
	if (y - size < 0 || y + size > height) {
	    y -= dy;
	    dy *= -0.1f;
	}
    }

    public float getX() {
	return x;
    }

    public float getY() {
	return y;
    }

    public float getSize() {
	return size;
    }

    public float getMass() {
	return mass;
    }

    public void draw() {
	ellipse(x, y, size*2, size*2);
    }

    public void gravitate(Planet p) {
	float m = mass*p.getMass();
	float d = dist(x, y, p.getX(), p.getY());
	float g = m/(d*d);
	float deltax = x - p.getX();
	float deltay = y - p.getY();
	dx -= g*deltax/d/10000;
	dy -= g*deltay/d/10000;

	if (dist(x, y, p.getX(), p.getY()) <= size + p.getSize()) {
	    x -= dx;
	    y -= dy;
	    dx *= -.4f;
	    dy *= -.4f;
	}
    }

    public void reposition(float nx, float ny) {
	x = nx;
	y = ny;
    }
    
}
  public void settings() {  size(600, 600); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "gravity" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
