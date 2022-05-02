import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Melee extends Sprite {

	protected Image image_straight;

	protected double velocityX = 50;        	//PIXELS PER SECOND
	protected double velocityY = 50;          	//PIXELS PER SECOND
	private double velocity = 50;

	private double health = 5;
	private double damage = 1;
	private final double MONEY = 1;

	public Melee(double currentX, double currentY, double floor) {

		super();
		this.currentX = currentX;
		this.currentY = currentY;

		try {
			this.defaultImage = ImageIO.read(new File("res/Melee.png"));
			this.IMAGE_HEIGHT = this.defaultImage.getHeight(null);
			this.IMAGE_WIDTH = this.defaultImage.getWidth(null);
		}
		catch (IOException e) {
			System.out.println(e.toString());
		}		
		
		if (floor == 1){
			damage = 1;
			health = 1.5;
			velocity = 100;
		}
		else if (floor == 2){
			damage = 1;
			health = 4;
			velocity = 110;
		}
		else if (floor == 3){
			damage = 1.5;
			health = 6;
			velocity = 120;
		}
		else if (floor == 4){
			damage = 1.5;
			health = 9;
			velocity = 130;
		}
		else if (floor == 5) {
			damage = 1;
			health = 12;
			velocity = 120;
			this.IMAGE_HEIGHT *= 2;
			this.IMAGE_WIDTH *= 2;
		}


	}

	public double getVelocityX() {
		return velocityX;
	}

	public void setVelocityX(double velocityX) {
		this.velocityX = velocityX;
	}

	public double getVelocityY() {
		return velocityY;
	}

	public void setVelocityY(double velocityY) {
		this.velocityY = velocityY;
	}

	@Override
	public void setSprites(ArrayList<Sprite> staticSprites) {
		this.sprites = staticSprites;

	}

	@Override
	public void setKeyboard(KeyboardInput keyboard) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean checkCollisionWithSprites(double x, double y) {
		// Checks collision with player only
		boolean colliding = false;

		for (Sprite sprite : sprites) {
			if (sprite instanceof Player) {
				boolean toLeft = (x + this.IMAGE_WIDTH) < sprite.getMinX();
				boolean toRight = x > sprite.getMaxX();
				boolean collidingX = !( toLeft || toRight);
				boolean above = (y + this.IMAGE_HEIGHT) < sprite.getMinY();
				boolean below = y > sprite.getMaxY();
				boolean collidingY = !( above || below);

				if (collidingX && collidingY) {
					colliding = true;
					sprite.takeDamage(damage);
					break;
				}
			}
		}

		return colliding;
	}

	public void setBarriers(ArrayList<Rectangle> barriers) {
		this.barriers = barriers;
	}

	public void setPlayers(ArrayList<Sprite> players) {
		this.sprites = players;
	}


	@Override
	public long getHeight() {
		return IMAGE_HEIGHT;
	}

	@Override
	public long getWidth() {
		return IMAGE_WIDTH;
	}	

	public double getMinX() {
		return currentX;
	}

	@Override
	public double getMaxX() { 
		// TODO Auto-generated method stub
		return currentX + IMAGE_WIDTH;
	}

	public void setMinX(double currentX) {
		this.currentX = currentX;
	}

	public void setMinY(double currentY) {
		this.currentY = currentY;
	}

	public double getMinY() {
		return currentY;
	}

	@Override
	public double getMaxY() {
		// TODO Auto-generated method stub
		return currentY + IMAGE_HEIGHT;
	}


	@Override
	public Image getImage() {
		return defaultImage;
	}

	@Override
	public void update(KeyboardInput keyboard, long actual_delta_time) {

		//calculate new position assuming there are no changes in direction
		if (health <= 0)
		{
			this.setDispose();
			for (Sprite other : sprites) {
				if (other instanceof Player) {
					((Player) other).addMoney(MONEY);
				}
			}
		}
		checkCollisionWithSprites(currentX, currentY);
		for (Sprite other : sprites) {
			if (other instanceof Player) {
				Player player = (Player)other;

				double newX = currentX;
				double newY = currentY;


				//calculate the attraction vector
				int deltaX = (int)(player.currentX - this.currentX);
				int deltaY = (int)(player.currentY - this.currentY);


				//implement logic for moving towards sprite
				if (deltaX > 0) {
					this.velocityX = velocity;
				}
				else if (deltaX < 0) {
					this.velocityX = -velocity;
				}
				else {
					this.velocityX = 0;
				}

				if (deltaY > 0) {
					this.velocityY = velocity;
				}
				else if (deltaY < 0) {
					this.velocityY = -velocity;
				}
				else {
					this.velocityY = 0;
				}
				newX +=  actual_delta_time * 0.001 * velocityX;
				newY +=  actual_delta_time * 0.001 * velocityY;
				if (checkCollisionWithBarrier(newX, newY) == false) {
					this.currentX = newX;
					this.currentY = newY;
				}
			}
		}

	}

	@Override
	public boolean checkCollisionWithBarrier(double x, double y) {
		boolean isColliding = false;

		for (int barrier = 0; barrier < barriers.size(); barrier++) {			
			//colliding in x dimension?	
			if ( !( (x + this.IMAGE_WIDTH) < barriers.get(barrier).getMinX() || x > barriers.get(barrier).getMaxX())) {			
				//colliding in y dimension?	
				if ( !( (y + this.IMAGE_HEIGHT) < barriers.get(barrier).getMinY() || y > barriers.get(barrier).getMaxY())) {								
					isColliding = true;
					break;
				}
			}
		}

		return isColliding;		
	}

	@Override
	public void setEnemySprites(ArrayList<Sprite> staticSprites) {
		this.enemySprites = staticSprites;
	}

	@Override
	public void takeDamage(double damage) {
		health -= damage;
	}
}



