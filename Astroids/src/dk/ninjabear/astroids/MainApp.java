package dk.ninjabear.astroids;

import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class MainApp extends Application {
	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Astroids");
		Group root = new Group();
		Scene scene = new Scene(root, 800, 600);
		initContent(scene, root);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	private void initContent(Scene scene, Group root) {
		Rectangle base = new Rectangle(800, 600, Color.BLACK);
		root.getChildren().add(base);
		
		Ship ship = new Ship(base.getWidth()/2, base.getHeight()/2, 25);
		root.getChildren().add(ship);

		ArrayList<Astroid> astroids = new ArrayList<>();
		for (int i = 0; i < 4; i++) {
			double x = Math.random() * scene.getWidth();
			double y = Math.random() * scene.getHeight();
			Astroid astroid = new Astroid(x, y, 50);
			astroids.add(astroid);
			root.getChildren().add(astroid);

		}
		
		ArrayList<Missile> missiles = new ArrayList<>();

		scene.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.LEFT) {
				ship.rotateLeft();
			} else if (e.getCode() == KeyCode.RIGHT) {
				ship.rotateRight();
			} else if (e.getCode() == KeyCode.UP) {
				ship.boost();
			} else if (e.getCode() == KeyCode.SPACE) {
				Missile missile = ship.attack();
				missiles.add(missile);
				root.getChildren().add(missile);
			}
		});
		
		scene.setOnKeyReleased(e -> {
			if (e.getCode() == KeyCode.RIGHT || 
				e.getCode() == KeyCode.LEFT)
				ship.stopRotating();
			else if( e.getCode() == KeyCode.UP)
				ship.stopBoosting();
		});
		
		new AnimationTimer() {
			@Override
			public void handle(long now) {
				// move all missiles
				for (int i = 0; i < missiles.size(); i++) {
					missiles.get(i).update();
					if (missiles.get(i).getX() <= 0 ||
							missiles.get(i).getX() > scene.getWidth() ||
							missiles.get(i).getY() <= 0 ||
							missiles.get(i).getY() > scene.getHeight()) {
						root.getChildren().remove(missiles.get(i));
						missiles.remove(i);
					}
				}
				
				// move ship
				ship.update();
				
				// move astroids in reversed direction so new smaller astroid can be added if one is hit
				for (int i = astroids.size()-1; i >= 0; i--) {
					astroids.get(i).update();
					
					// check if the ship hit an astroid
					if (astroids.get(i).isHit(ship.getX(), ship.getY(), ship.getRadius())) {
						System.out.println("GAME OVER");
						this.stop();
					}
					
					// check if a missile hit an astroid
					for (int j = 0; j < missiles.size(); j++) {
						Missile m = missiles.get(j);
						if (astroids.get(i).isHit(m.getX(), m.getY(), 0)) {
							double newX = astroids.get(i).getX();
							double newY = astroids.get(i).getY();
							double newR = astroids.get(i).getRadius() / 2;
							
							root.getChildren().remove(astroids.get(i));
							astroids.remove(i);
							root.getChildren().remove(missiles.get(j));
							missiles.remove(j);

							// add two new astroids if they are large enough
							if (newR > 10) {
								Astroid a1 = new Astroid(newX, newY, newR);
								Astroid a2 = new Astroid(newX, newY, newR);
								root.getChildren().addAll(a1, a2);
								astroids.add(a1);
								astroids.add(a2);
							}
							
							break; // exit the missile loop as no other missiles should hit this astroid
						}
					}
					
				}
				
				if (astroids.isEmpty()) {
					System.out.println("YOU WON!");
					this.stop();
				}
			}
		}.start();
	}
}
