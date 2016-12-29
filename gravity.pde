private Planet p, blackhole;
private ArrayList<Planet> asteroids;
private int score;

public void setup() {
    size(displayWidth, displayHeight);
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
