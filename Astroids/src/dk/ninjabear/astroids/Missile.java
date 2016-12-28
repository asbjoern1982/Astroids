package dk.ninjabear.astroids;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class Missile extends Line {
	public double length;
	public double speed;
	
	public Missile(double x, double y, double angle) {
		length = 10;
		setStartX(x);
		setStartY(y);
		setEndX(x + Math.cos(Math.toRadians(angle))*length);
		setEndY(y + Math.sin(Math.toRadians(angle))*length);

		speed = 1.1;
		setStroke(Color.WHITE);
		setStrokeWidth(2);
	}
	
	public double getX() {return getStartX();}
	public double getY() {return getEndY();}
	
	public void update() {
		double dx = (getEndX() - getStartX())*speed;
		double dy = (getEndY() - getStartY())*speed;
		
		setStartX(this.getStartX()+dx);
		setStartY(this.getStartY()+dy);
		setEndX(this.getEndX()+dx);
		setEndY(this.getEndY()+dy);
	}
}
