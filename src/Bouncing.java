import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Bouncing extends Sprite {

	private double velocityX = 300; //per second
	private double velocityY = 300; //per second
	private double health = 5;
	private double damage = 1;
	private final double MONEY = 1;
	
	public Bouncing(double currentX, double currentY, double floor) {
		super();
		
		this.currentX = currentX;
		this.currentY = currentY;

		try {
			this.defaultImage = ImageIO.read(new File("res/Bouncing.png"));
			this.IMAGE_HEIGHT = this.defaultImage.getHeight(null);
			this.IMAGE_WIDTH = this.defaultImage.getWidth(null);
		}
		catch (IOException e) {
			System.out.println(e.toString());
		}		
		if (floor == 3){
			damage = 1.5;
			health = 6;
			velocityX = 300;
			velocityY = 300;
		}
		else if (floor == 4){
			damage = 2;
			health = 10;
			velocityX = 400;
			velocityY = 400;
		}
		else if (floor == 5) {
			damage = 2.5;
			health = 40;
			velocityX = 400;
			velocityY = 400;
			this.IMAGE_HEIGHT *= 2;
			this.IMAGE_WIDTH *= 2;
		}
	}
	
	@Override
	public void update(KeyboardInput keyboard, long actual_delta_time) {
		double newX = currentX;
		double newY = currentY;
		if (health <= 0)
		{
			this.setDispose();
			for (Sprite other : sprites) {
				if (other instanceof Player) {
					((Player) other).addMoney(MONEY);
				}
			}
		}
		newX -= actual_delta_time * 0.001 * velocityX;
		newY -= actual_delta_time * 0.001 * velocityY; 
		if (checkCollisionWithBarrier(newX, newY)){
			if (checkCollisionWithBarrier(newX, 800))
			{
				velocityX = -velocityX;
			}
			else
			{
				velocityY = -velocityY;
			}
		//this.velocityY = this.velocityY + 500 * 0.001 * actual_delta_time;
		}
		currentX -= actual_delta_time * 0.001 * velocityX;
		currentY -= actual_delta_time * 0.001 * velocityY; 
	}
	

	@Override
	public double getMinX() {
		// TODO Auto-generated method stub
		return currentX;
	}

	@Override
	public double getMaxX() {
		// TODO Auto-generated method stub
		return currentX + IMAGE_WIDTH;
	}

	@Override
	public double getMinY() {
		// TODO Auto-generated method stub
		return currentY;
	}

	@Override
	public double getMaxY() {
		// TODO Auto-generated method stub
		return currentY + IMAGE_HEIGHT;
	}

	@Override
	public long getHeight() {
		return IMAGE_HEIGHT;
	}

	@Override
	public long getWidth() {
		return IMAGE_WIDTH;
	}

	@Override
	public Image getImage() {
		// TODO Auto-generated method stub
		return defaultImage;
	}

	@Override
	public void setMinX(double currentX) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setMinY(double currentY) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setBarriers(ArrayList<Rectangle> barriers) {
		this.barriers = barriers;
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

	@Override
	public boolean checkCollisionWithBarrier(double x, double y) {

		boolean isColliding = false;

		for (int i = 0; i < barriers.size(); i++) {
			Rectangle barrier = barriers.get(i);
			if ( !( (x + this.IMAGE_WIDTH) < barrier.getMinX() || x > barrier.getMaxX())) {
				//colliding in y dimension?	
				if ( !( (y + this.IMAGE_HEIGHT) < barrier.getMinY() || y > barrier.getMaxY())) {								
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


