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

private Planet p, blackhole;
private ArrayList<Planet> asteroids;
private int score;

public void setup() {
    
    noStroke();
    p = new Planet(width/2, height/2, 20, color(100, 100, 255));
    blackhole = new Planet(0, 0, 40, color(0, 0, 0));
    asteroids = new ArrayList<Planet>();
    score = 0;
}

public void draw() {
    background(255);
    if (frameCount % 120 == 0) spawnAsteroid();
    blackhole.reposition(mouseX, mouseY);

    //handle gravity
    if (mousePressed) 
	p.gravitate(blackhole);
    for (int i = asteroids.size() - 1; i >= 0; i--) {
	Planet a = asteroids.get(i);
	if (a.getX() + a.getRadius() < 0 || a.getX() - a.getRadius() > width || a.getY() + a.getRadius() < 0 || a.getY() - a.getRadius() > width) {
	    asteroids.remove(i);
	    score++;
	}
	asteroids.get(i).gravitate(p);
	if (mousePressed)
	    asteroids.get(i).gravitate(blackhole);
	p.gravitate(a);
    }

    //handle movement
    for (Planet a : asteroids) {
	a.move();
    }
    p.move();
    if (p.getX() - p.getRadius() < 0 || p.getX() + p.getRadius() > width)
	p.bounceX();
    if (p.getY() - p.getRadius() < 0 || p.getY() + p.getRadius() > height)
	p.bounceY();

    //handle collisions
    if (checkCollision(blackhole, p) && mousePressed) {
	reset();
	return;
    }
    for (Planet a : asteroids) {
	if (checkCollision(a, p)) {
	    reset();
	    return;
	}
    }
    
    //handle rendering
    drawPlanet(p);
    for (Planet a : asteroids)
	drawPlanet(a);
    if (mousePressed)
	drawPlanet(blackhole);
}

public void spawnAsteroid() {
    float ax, ay;
    do {
	ax = random(width - 50) + 25;
	ay = random(height - 50) + 25;
    } while (dist(ax, ay, p.getX(), p.getY()) < 100);
    Planet asteroid = new Planet(ax, ay, 20, color(180, 120, 50));
    asteroids.add(asteroid);
}

public void drawPlanet(Planet pl) {
    fill(pl.getColor());
    ellipse(pl.getX(), pl.getY(), pl.getRadius()*2, pl.getRadius()*2);
}

public boolean checkCollision(Planet p1, Planet p2) {
    return dist(p1.getX(), p1.getY(), p2.getX(), p2.getY()) <= p1.getRadius() + p2.getRadius();
}

public void reset() {
    println(score);
    score = 0;
    p = new Planet(width/2, height/2, 20, color(100, 100, 255));
    asteroids.clear();
}
public class Planet {
    private float x, y, dx, dy, radius, mass;
    private int c;

    public Planet(float nx, float ny, float nradius, int ncolor) {
	x = nx;
	y = ny;
	radius = nradius;
	dx = 0;
	dy = 0;
	mass = (float) (radius*radius*Math.PI);
	c = ncolor;
    }

    public void move() {
	x += dx;
	y += dy;
    }

    public float getX() {
	return x;
    }

    public float getY() {
	return y;
    }

    public float getRadius() {
	return radius;
    }

    public float getMass() {
	return mass;
    }

    public void gravitate(Planet p) {
	float m = mass*p.getMass();
	float d = dist(x, y, p.getX(), p.getY());
	float g = m/(d*d);
	float deltax = x - p.getX();
	float deltay = y - p.getY();
	dx -= g*deltax/d/8000;
	dy -= g*deltay/d/8000;

	if (dist(x, y, p.getX(), p.getY()) <= radius + p.getRadius()) {
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
    
    public void bounceX() {    
	x -= dx;
	dx *= -0.1f;
    }
    
    public void bounceY() {    
	y -= dy;
	dy *= -0.1f;
    }

    public int getColor() {
	return c;
    }
}
  public void settings() {  size(displayWidth, displayHeight); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "gravity" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
