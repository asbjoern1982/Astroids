package dk.ninjabear.astroids;

import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.transform.Translate;

public class Astroid extends Polygon {
	private Translate position;
	private double angle;
	private double speed;
	private double r;
	
	public Astroid(double x, double y, double r) {
		this.r = r;
		int numberOfPoints = 8;
		Double[] points = new Double[numberOfPoints*2];
		for (int i = 0; i < numberOfPoints*2; i += 2) {
			double pointAngle = (i/2) * 360 / numberOfPoints;
			double inset = Math.random() * r * 0.7;
			points[i] = Math.cos(Math.toRadians(pointAngle)) * r + inset;
			points[i+1] = Math.sin(Math.toRadians(pointAngle)) * r + inset;
		}

		this.getPoints().addAll(points);
		
		position = new Translate(x, y);
		this.getTransforms().add(position);
		
		angle = Math.random() * 360;
		speed = Math.random() * 2;
		
		this.setStroke(Color.WHITE);
		this.setFill(null);
	}
	
	public boolean isHit(double x2, double y2, double r2) {
		double x1 = position.getX();
		double y1 = position.getY();
		double distrance = Math.sqrt((x2 - x1)*(x2 - x1) + (y2 - y1)*(y2 - y1));
		return r + r2 > distrance;

	}

	public double getX() {return position.getX();}
	public double getY() {return position.getY();}
	public double getRadius() {return r;}
	
	public void update() {
		position.setX(position.getX() + Math.cos(Math.toRadians(angle)) * speed);
		position.setY(position.getY() + Math.sin(Math.toRadians(angle)) * speed);

		// going outside of the scene
		Scene scene =  this.getParent().getScene();
		if (position.getX() > scene.getWidth()) position.setX(0);
		if (position.getX() < 0) position.setX(scene.getWidth());
		if (position.getY() > scene.getHeight()) position.setY(0);
		if (position.getY() < 0) position.setY(scene.getHeight());
	}
}
