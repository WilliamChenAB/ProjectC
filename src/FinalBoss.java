import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import javax.imageio.ImageIO;

public class FinalBoss extends Sprite{
	protected Image image_straight;
	
	protected double velocityX = 160;        	//PIXELS PER SECOND
	protected double velocityY = 160;          	//PIXELS PER SECOND
	private final double VELOCITY = 160;
	
	private double health = 150;
	private double damage = 3;
	private double range = 15000;
	private double shootingAngle;
	private double reloadTime;
	private int changeMovementTime = 0;
	
	private long phaseChangeTime = 0;
	private int phase = 0;
	
	public FinalBoss(double currentX, double currentY) {

		this.currentX = currentX;
		this.currentY = currentY;

		try {
			this.defaultImage = ImageIO.read(new File("res/Baron.png"));
			this.IMAGE_HEIGHT = this.defaultImage.getHeight(null) + 64;
			this.IMAGE_WIDTH = this.defaultImage.getWidth(null) + 64;
		}
		catch (IOException e) {
			System.out.println(e.toString());
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
		}
		checkCollisionWithSprites(currentX, currentY);
		if (phaseChangeTime <= 0) {
			Random random = new Random();
			phase = random.nextInt(3) + 1;
			phaseChangeTime = 8000;
		}
		if (phase == 1) {
			for (Sprite other : sprites) {
				if (other instanceof Player) {
					Player player = (Player)other;

					double newX = currentX;
					double newY = currentY;


					//calculate the attraction vector
					int deltaX = (int)(player.currentX - this.currentX - 90);
					int deltaY = (int)(player.currentY - this.currentY - 82);


					//implement logic for moving towards sprite
					if (deltaX > 0) {
						this.velocityX = VELOCITY;
					}
					else if (deltaX < 0) {
						this.velocityX = -VELOCITY;
					}
					else {
						this.velocityX = 0;
					}

					if (deltaY > 0) {
						this.velocityY = VELOCITY;
					}
					else if (deltaY < 0) {
						this.velocityY = -VELOCITY;
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
		else if (phase == 2) {
			shootingAngle = 0;
			for (Sprite other : sprites) {
				if (other instanceof Player) {
					Player player = (Player)other;
					
					double deltaX = Math.abs(player.currentX - this.currentX);
					double deltaY = Math.abs(player.currentY - this.currentY);
					
					shootingAngle += Math.atan(deltaY/deltaX);
					if (player.currentX < this.currentX && player.currentY > this.currentY){
						shootingAngle = Math.PI - shootingAngle ;
					}
					else if(player.currentX < this.currentX && player.currentY < this.currentY){
						shootingAngle += Math.PI;
					}
					else if(player.currentX > this.currentX && player.currentY < this.currentY){
						shootingAngle = Math.PI *2 - shootingAngle;
					}
					else if(player.currentX < this.currentX && shootingAngle == 0){
						shootingAngle = Math.PI;
					}
					
					
				}
			}
			reloadTime -= actual_delta_time;
			if (reloadTime < 0) {
				shoot();
			}
			
		}
		else if (phase == 3) {
			shootingAngle = 0;
			changeMovementTime -= actual_delta_time;
			double newX = currentX;
			double newY = currentY;
			Random randomTime = new Random();
			
			if (health <= 0)
			{
				this.setDispose();
			}
			
			if (changeMovementTime <= 100){
				changeMovementTime = randomTime.nextInt(551) + 1000;
				Random randomDirection = new Random();
				int direction = randomDirection.nextInt(2);
				if (changeMovementTime % 2 == 0){
					if (direction == 0){
						velocityY = -VELOCITY;
						velocityX = 0;
					}
					else if(direction == 1){
						velocityX = -VELOCITY;
						velocityY = 0;
					}
				}
				else if(changeMovementTime % 2 == 1){
					if (direction == 0){
						velocityY = VELOCITY;
						velocityX = 0;
					}
					else if(direction == 1){
						velocityX = VELOCITY;
						velocityY = 0;
					}
				}
				
			}
			newX += actual_delta_time * 0.001 * velocityX;
			newY += actual_delta_time * 0.001 * velocityY;
			if (checkCollisionWithBarrier(newX, newY) == false) {
				

				this.currentX = newX;
				this.currentY = newY;
			}
			for (Sprite other : sprites) {
				if (other instanceof Player) {
					Player player = (Player)other;
					
					double deltaX = Math.abs(player.currentX - this.currentX);
					double deltaY = Math.abs(player.currentY - this.currentY);
					
					shootingAngle += Math.atan(deltaY/deltaX);
					if (player.currentX < this.currentX && player.currentY > this.currentY){
						shootingAngle = Math.PI - shootingAngle ;
					}
					else if(player.currentX < this.currentX && player.currentY < this.currentY){
						shootingAngle += Math.PI;
					}
					else if(player.currentX > this.currentX && player.currentY < this.currentY){
						shootingAngle = Math.PI *2 - shootingAngle;
					}
					
					
				}
			}
			reloadTime -= actual_delta_time;
			if (reloadTime < 0) {
				shoot();
			}
		}
		else if (phase == 4) {
			System.out.println("phase 4");
		}
		phaseChangeTime -= actual_delta_time;
		System.out.println(phase);
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

	public void shoot() {

		double bulletVelocity = 250; // + currentVelocity;
//		double angleInRadians = Math.toRadians(shootingAngle);
		double bulletVelocityX = Math.cos(shootingAngle) * bulletVelocity;
		double bulletVelocityY = Math.sin(shootingAngle) * bulletVelocity;
		
		double bulletCurrentX = (this.currentX + (this.IMAGE_WIDTH / 2));
		double bulletCurrentY = (this.currentY + (this.IMAGE_HEIGHT / 2));

		EnemyBulletSprite bullet = new EnemyBulletSprite(bulletCurrentX, bulletCurrentY, bulletVelocityX, bulletVelocityY, "baron");
		bullet.setLifeTime((long)range);
		bullet.setSprites(sprites);
		bullet.setEnemySprites(enemySprites);
		bullet.setDamage(damage);
		enemySprites.add(bullet);
			
		reloadTime = 250;			
	}
	
	@Override
	public void takeDamage(double damage) {
		health -= damage;
		System.out.println(health);
	}
}
