public class Planet {
    private float x, y, dx, dy, radius, mass;
    private color c;

    public Planet(float nx, float ny, float nradius, color ncolor) {
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
	    dx *= -.4;
	    dy *= -.4;
	}
    }

    public void reposition(float nx, float ny) {
	x = nx;
	y = ny;
    }
    
    public void bounceX() {    
	x -= dx;
	dx *= -0.1;
    }
    
    public void bounceY() {    
	y -= dy;
	dy *= -0.1;
    }

    public color getColor() {
	return c;
    }
}
