package dk.ninjabear.astroids;

import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

public class Ship extends Polygon {
	private Translate position;
	private double r;
	private Rotate noseAngle;
	private int rotating;
	private boolean boosting;
	
	private double velX, velY, acceleration;
	
	public Ship(double x, double y, double r) {
		super(new double[]{0.0, r, -r/2, -r/2, r/2, -r/2});
		position = new Translate(x, y);
		this.r = r;
		noseAngle = new Rotate();
		this.getTransforms().add(position);
		this.getTransforms().add(noseAngle);
		this.setStroke(Color.WHITE);
		this.setFill(Color.BLACK);
		rotating = 0;
		
		velX = 0;
		velY = 0;
		acceleration = 0.2;
		
		boosting = false;
		
	}
	
	public void rotateLeft() {rotating = -5;}
	public void rotateRight() {rotating = 5;}
	public void stopRotating() {rotating = 0;}

	public void stopBoosting() {boosting = false;}
	public void boost() {
		if (!boosting) boosting = true;
		velX += Math.cos(Math.toRadians(noseAngle.getAngle()+90))*acceleration;
		velY += Math.sin(Math.toRadians(noseAngle.getAngle()+90))*acceleration;
	}

	public double getX() {return position.getX();}
	public double getY() {return position.getY();}
	public double getRadius() {return r;}
	
	public Missile attack() {
		return new Missile(position.getX(), position.getY(), noseAngle.getAngle()+90);
	}
	
	public void update() {
		// rotate
		if (rotating != 0)
			noseAngle.setAngle(noseAngle.getAngle() + rotating);
		
		// boosting
		if (boosting) // if the boost-key is pressed, accelerate more
			boost();
		else { // else decelerate
			velX *= 0.99;
			velY *= 0.99;
		}
		position.setX(position.getX() + velX);
		position.setY(position.getY() + velY);
		
		// going outside of the scene
		Scene scene =  this.getParent().getScene();
		if (position.getX() > scene.getWidth()) position.setX(0);
		if (position.getX() < 0) position.setX(scene.getWidth());
		if (position.getY() > scene.getHeight()) position.setY(0);
		if (position.getY() < 0) position.setY(scene.getHeight());
	}
}
