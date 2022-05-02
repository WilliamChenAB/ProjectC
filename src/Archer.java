import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;

public class Archer extends Sprite{
	protected double velocityX = 0.1;          	//PIXELS PER SECOND
	protected double velocityY = 0.1;			//PIXELS PER SECOND
	private double velocity = 0.1;					
	protected double reloadTime = 0;
	private double shootingAngle = 0;
	private int range;
	private double damage;
	private double rateOfFire;
	private double health;
	private int changeMovementTime = 0;
	private final double MONEY = 1;
	
	public Archer(double currentX, double currentY, int floor){
		this.currentX = currentX;
		this.currentY = currentY;
		try {
			this.defaultImage = ImageIO.read(new File("res/Archer.png"));
			this.IMAGE_HEIGHT = this.defaultImage.getHeight(null);
			this.IMAGE_WIDTH = this.defaultImage.getWidth(null);
		}
		catch (IOException e) {
			System.out.println(e.toString());
		}
		if (floor == 1){
			this.range = 3000;
			this.damage = 1;
			this.rateOfFire = 500;
			this.health = 1;
		}
		else if (floor == 2){
			this.range = 3000;
			this.damage = 1.5;
			this.rateOfFire = 500;
			this.health = 2;
		}
		else if (floor == 3){
			this.range = 3500;
			this.damage = 1.5;
			this.rateOfFire = 400;
			this.health = 3;
		}
		else if (floor == 4){
			this.range = 4000;
			this.damage = 2;
			this.rateOfFire = 350;
			this.health = 5;
		}
		else if (floor == 5) {
			this.range = 4000;
			this.damage = 1.5;
			this.rateOfFire = 300;
			this.health = 18;
			this.IMAGE_HEIGHT *= 2;
			this.IMAGE_WIDTH *= 2;
		}
		
	}
	public double getHealth() {
		return health;
	}
	@Override
	public void update(KeyboardInput keyboard, long actual_delta_time) {
		shootingAngle = 0;
		changeMovementTime -= actual_delta_time;
		double newX = currentX;
		double newY = currentY;
		Random randomTime = new Random();
		
		if (health <= 0)
		{
			this.setDispose();
			for (Sprite other : sprites) {
				if (other instanceof Player) {
					((Player) other).addMoney(MONEY);
				}
			}
		}
		
		if (changeMovementTime <= 100){
			changeMovementTime = randomTime.nextInt(551) + 1000;
			Random randomDirection = new Random();
			int direction = randomDirection.nextInt(2);
			if (changeMovementTime % 2 == 0){
				if (direction == 0){
					velocityY = -velocity;
					velocityX = 0;
				}
				else if(direction == 1){
					velocityX = -velocity;
					velocityY = 0;
				}
			}
			else if(changeMovementTime % 2 == 1){
				if (direction == 0){
					velocityY = velocity;
					velocityX = 0;
				}
				else if(direction == 1){
					velocityX = velocity;
					velocityY = 0;
				}
			}
			
		}
		newX += actual_delta_time * velocityX;
		newY += actual_delta_time * velocityY;
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
		// TODO Auto-generated method stub
		return IMAGE_HEIGHT;
	}

	@Override
	public long getWidth() {
		// TODO Auto-generated method stub
		return IMAGE_HEIGHT;
	}

	@Override
	public Image getImage() {
		// TODO Auto-generated method stub
		return this.defaultImage;
	}

	@Override
	public void setMinX(double currentX) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setMinY(double currentY) {
		// TODO Auto-generated method stub
		
	}
	public void setPlayers(ArrayList<Sprite> players) {
		this.sprites = players;
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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean checkCollisionWithBarrier(double x, double y) {
		
		boolean colliding = false;

		for (int barrier = 0; barrier < barriers.size(); barrier++) {
			boolean toLeft = (x + this.IMAGE_WIDTH) < barriers.get(barrier).getMinX();
			boolean toRight = x > barriers.get(barrier).getMaxX();
			boolean collidingX = !( toLeft || toRight);
			boolean above = (y + this.IMAGE_HEIGHT) < barriers.get(barrier).getMinY();
			boolean below = y > barriers.get(barrier).getMaxY();
			boolean collidingY = !( above || below);
			if (collidingX && collidingY) {
				colliding = true;
				break;
			}			
		}		
		return colliding;		
	}
	public void shoot() {

		double bulletVelocity = 200;
		double bulletVelocityX = Math.cos(shootingAngle) * bulletVelocity + velocityX;
		double bulletVelocityY = Math.sin(shootingAngle) * bulletVelocity + velocityY;
		
		double bulletCurrentX = (this.currentX + (this.IMAGE_WIDTH / 2));
		double bulletCurrentY = (this.currentY + (this.IMAGE_HEIGHT / 2));

		EnemyBulletSprite bullet = new EnemyBulletSprite(bulletCurrentX, bulletCurrentY, bulletVelocityX, bulletVelocityY, "archer");
		bullet.setLifeTime((long)range);
		bullet.setSprites(sprites);
		bullet.setEnemySprites(enemySprites);
		bullet.setDamage(damage);
		enemySprites.add(bullet);
			
		reloadTime = rateOfFire;			
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
