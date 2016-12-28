Planet p, mouseP;

void setup() {
    size(600, 600);
    noStroke();
    p = new Planet(width/2, height/2, 20);
    mouseP = new Planet(0, 0, 40);
}

void draw() {
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

void mouseDragged() {
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

    void move() {
	x += dx;
	y += dy;

	if (x - size < 0 || x + size > width) {
	    x -= dx;
	    dx *= -0.1;
	}
	if (y - size < 0 || y + size > height) {
	    y -= dy;
	    dy *= -0.1;
	}
    }

    float getX() {
	return x;
    }

    float getY() {
	return y;
    }

    float getSize() {
	return size;
    }

    float getMass() {
	return mass;
    }

    void draw() {
	ellipse(x, y, size*2, size*2);
    }

    void gravitate(Planet p) {
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
	    dx *= -.4;
	    dy *= -.4;
	}
    }

    void reposition(float nx, float ny) {
	x = nx;
	y = ny;
    }
    
}
