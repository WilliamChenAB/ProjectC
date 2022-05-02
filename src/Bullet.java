import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Bullet extends Sprite {

	protected static Image defaultImage;
	
	protected double velocityX = 000;        	//PIXELS PER SECOND
	protected double velocityY = 0;          	//PIXELS PER SECOND
	protected double accelerationX = 0;          			//PIXELS PER SECOND PER SECOND 
	protected double accelerationY = 0;          		//PIXELS PER SECOND PER SECOND 
	protected long lifeTime = 1000;
	private double damage = 0;
	
	public double getDamage() {
		return damage;
	}

	public void setDamage(double damage) {
		this.damage = damage;
	}

	public Bullet(double currentX, double currentY, double velocityX, double velocityY, String direction) {

		super();
		this.currentX = currentX;
		this.currentY = currentY;
		this.velocityX = velocityX;
		this.velocityY = velocityY;
		defaultImage = null;
		
		if (direction.equals("right")) {
			try {
				if (defaultImage == null) {
					defaultImage = ImageIO.read(new File("res/bulletRight.png"));
				}
				this.IMAGE_HEIGHT = defaultImage.getHeight(null);
				this.IMAGE_WIDTH = defaultImage.getWidth(null);
			}
			catch (IOException e) {
				System.out.println(e.toString());
			}
		}
		else if (direction.equals("left")) {
			try {
				if (defaultImage == null) {
					defaultImage = ImageIO.read(new File("res/bulletLeft.png"));
				}
				this.IMAGE_HEIGHT = defaultImage.getHeight(null);
				this.IMAGE_WIDTH = defaultImage.getWidth(null);
			}
			catch (IOException e) {
				System.out.println(e.toString());
			}
		}
		else if (direction.equals("up")) {
			try {
				if (defaultImage == null) {
					defaultImage = ImageIO.read(new File("res/bulletUp.png"));
				}
				this.IMAGE_HEIGHT = defaultImage.getHeight(null);
				this.IMAGE_WIDTH = defaultImage.getWidth(null);
			}
			catch (IOException e) {
				System.out.println(e.toString());
			}
		}
		else {
			try {
				if (defaultImage == null) {
					defaultImage = ImageIO.read(new File("res/bulletDown.png"));
				}
				this.IMAGE_HEIGHT = defaultImage.getHeight(null);
				this.IMAGE_WIDTH = defaultImage.getWidth(null);
			}
			catch (IOException e) {
				System.out.println(e.toString());
			}
		}
		
	}
	
	public long getLifeTime() {
		return lifeTime;
	}

	public void setLifeTime(long lifeTime) {
		this.lifeTime = lifeTime;
	}

	@Override
	public void setSprites(ArrayList<Sprite> staticSprites) {
		// TODO Auto-generated method stub
		this.sprites = staticSprites;
		
	}
	
	public void setEnemySprites(ArrayList<Sprite> staticSprites) {
		// TODO Auto-generated method stub
		this.enemySprites = staticSprites;
		
	}

	@Override
	public void setKeyboard(KeyboardInput keyboard) {
		// TODO Auto-generated method stub
		
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

	    double movement_x = (this.velocityX * actual_delta_time * 0.001);
	    double movement_y = (this.velocityY * actual_delta_time * 0.001);
	    double new_x = this.currentX + movement_x;
	    double new_y = this.currentY + movement_y;

	    lifeTime -= actual_delta_time;
	    if (lifeTime < 0) {
	    	this.setDispose();
	    }

	    this.currentX = new_x;
	    this.currentY = new_y;
	    checkCollisionWithSprites(currentX, currentY);
			
	}			
	

	@Override
	public boolean checkCollisionWithBarrier(double x, double y) {
		boolean colliding = false;

		for (Rectangle barrier : barriers) {
			boolean toLeft = (x + this.IMAGE_WIDTH) < barrier.getMinX();
			boolean toRight = x > barrier.getMaxX();
			boolean collidingX = !( toLeft || toRight);
			boolean above = (y + this.IMAGE_HEIGHT) < barrier.getMinY();
			boolean below = y > barrier.getMaxY();
			boolean collidingY = !( above || below);
			if (collidingX && collidingY) {
				colliding = true;
				System.out.println("Working");
				break;
			}			
		}		
		return colliding;		
	}
	
	@Override
	public boolean checkCollisionWithSprites(double x, double y) {
		boolean colliding = false;
		
		for (Sprite sprite : enemySprites) {
			if (!(sprite instanceof EnemyBulletSprite)) {
				boolean toLeft = (x + this.IMAGE_WIDTH) < sprite.getMinX();
				boolean toRight = x > sprite.getMaxX();
				boolean collidingX = !( toLeft || toRight);
				boolean above = (y + this.IMAGE_HEIGHT) < sprite.getMinY();
				boolean below = y > sprite.getMaxY();
				boolean collidingY = !( above || below);

				if (collidingX && collidingY) {
					colliding = true;
					sprite.takeDamage(damage);
					this.setDispose();
					break;
				}
			}
		}
		
		return colliding;
	}

	@Override
	public void takeDamage(double damage) {
		return;
	}

	public static void setDefaultImage(Image defaultImage) {
		Bullet.defaultImage = defaultImage;
	}
}
