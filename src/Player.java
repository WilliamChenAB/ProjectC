import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Player extends Sprite {

	protected double velocityX = 50;        	//PIXELS PER SECOND
	protected double velocityY = 50;          	//PIXELS PER SECOND

	private double damage = 0.75;
	private double speed = 0.5;
	private double range = 500;
	private double rateOfFire = 500;
	private double reloadTime;
	private double maxHealth = 5;
	private double health = 5;
	private double armour = 0;
	private double money = 30;
	private double damageBuffer = 0;
	private double itemPickUpBuffer = 0;
	private String shootDirection = "";
	private boolean itemObtained = false;
	private boolean cullObtained = false;
	private boolean runaansHurricaneObtained = false;
	private boolean gargoyleStoneplateObtained = false;
	private boolean ludensEchoObtained = false;
	private String whichItem = "";

	public Player(double currentX, double currentY) {
		super();

		this.currentX = currentX;
		this.currentY = currentY;		

		try {
			this.defaultImage = ImageIO.read(new File("res/lucian.png"));
			this.IMAGE_HEIGHT = this.defaultImage.getHeight(null);
			this.IMAGE_WIDTH = this.defaultImage.getWidth(null);
		}
		catch (IOException e) {
			System.out.println(e.toString());
		}		
	}


	@Override
	public void update(KeyboardInput keyboard, long actual_delta_time) {

		double newX = currentX;
		double newY = currentY;

		if (health <= 0) {
			this.setDispose();
		}
		//LEFT	
		if (keyboard.keyDown(65)) {
			newX -= actual_delta_time * speed;
		}
		//UP
		if (keyboard.keyDown(87)) {
			newY -= actual_delta_time * speed;

		}
		// RIGHT
		if (keyboard.keyDown(68)) {
			newX +=  actual_delta_time * speed;
		}
		// DOWN
		if (keyboard.keyDown(83)) {
			newY +=  actual_delta_time * speed;
		}
		// SHOOT UP
		if (keyboard.keyDown(38)) {
			shootDirection = "up";
			shoot();
		}
		// SHOOT LEFT
		if (keyboard.keyDown(37)) {
			shootDirection = "left";
			shoot();
		}
		// SHOOT DOWN
		if (keyboard.keyDown(40)) {
			shootDirection = "down";
			shoot();
		}
		// SHOOT RIGHT
		if (keyboard.keyDown(39)) {
			shootDirection = "right";
			shoot();
		}

		if (checkCollisionWithBarrier(newX, newY) == false) {
			this.currentX = newX;
			this.currentY = newY;
		}
		reloadTime -= actual_delta_time;
		damageBuffer -= actual_delta_time;
		itemPickUpBuffer -= actual_delta_time;

	}

	public void shoot() {

		if (reloadTime <= 0) {
			double bulletVelocityX = 0;
			double bulletVelocityY = 0;
			double bulletCurrentX = (this.currentX + (this.IMAGE_WIDTH / 2));
			double bulletCurrentY = (this.currentY + (this.IMAGE_HEIGHT / 2));
			if (shootDirection.equals("left")) {
				bulletVelocityX = -800; 
				bulletVelocityY = 0;
			}
			else if (shootDirection.equals("right")) {
				bulletVelocityX = 800; 
				bulletVelocityY = 0;
			}
			else if (shootDirection.equals("up")) {
				bulletVelocityX = 0; 
				bulletVelocityY = -800;
			}
			else if (shootDirection.equals("down")) {
				bulletVelocityX = 0; 
				bulletVelocityY = 800;
			}
			else
			{
				return;
			}
			Bullet bullet = new Bullet(bulletCurrentX-8, bulletCurrentY-8, bulletVelocityX, bulletVelocityY, shootDirection);
			bullet.setLifeTime((long)range);
			bullet.setEnemySprites(enemySprites);
			bullet.setDamage(damage);
			if (runaansHurricaneObtained) {
				if (shootDirection.equals("left") || shootDirection.equals("right")) {
					Bullet bullet2 = new Bullet(bulletCurrentX-8, bulletCurrentY-24, bulletVelocityX, bulletVelocityY, shootDirection);
					Bullet bullet3 = new Bullet(bulletCurrentX-8, bulletCurrentY+8, bulletVelocityX, bulletVelocityY, shootDirection);
					bullet2.setLifeTime((long)range);
					bullet2.setEnemySprites(enemySprites);
					bullet2.setDamage(damage);
					sprites.add(bullet2);
					bullet3.setLifeTime((long)range);
					bullet3.setEnemySprites(enemySprites);
					bullet3.setDamage(damage);
					sprites.add(bullet3);
					if (ludensEchoObtained) {
						bullet2.IMAGE_HEIGHT *= 2;
						bullet2.IMAGE_HEIGHT *= 2;
						bullet3.IMAGE_WIDTH *= 2;
						bullet3.IMAGE_WIDTH *= 2;
					}
				}
				else {
					Bullet bullet2 = new Bullet(bulletCurrentX-24, bulletCurrentY-8, bulletVelocityX, bulletVelocityY, shootDirection);
					Bullet bullet3 = new Bullet(bulletCurrentX+8, bulletCurrentY-8, bulletVelocityX, bulletVelocityY, shootDirection);
					bullet2.setLifeTime((long)range);
					bullet2.setEnemySprites(enemySprites);
					bullet2.setDamage(damage);
					sprites.add(bullet2);
					bullet3.setLifeTime((long)range);
					bullet3.setEnemySprites(enemySprites);
					bullet3.setDamage(damage);
					sprites.add(bullet3);
					if (ludensEchoObtained) {
						bullet2.IMAGE_HEIGHT *= 2;
						bullet2.IMAGE_HEIGHT *= 2;
						bullet3.IMAGE_WIDTH *= 2;
						bullet3.IMAGE_WIDTH *= 2;
					}
				}
			}
			if (ludensEchoObtained) {
				bullet.IMAGE_HEIGHT *= 2;
				bullet.IMAGE_WIDTH *= 2;
			}
			sprites.add(bullet);
			reloadTime = rateOfFire;
		}
	}
	public double getHealth() {
		return health;
	}
	@Override
	public double getMinX() {
		return currentX;
	}

	@Override
	public double getMaxX() {
		return currentX + IMAGE_WIDTH;
	}

	@Override
	public double getMinY() {
		return currentY;
	}

	@Override
	public double getMaxY() {
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
		boolean colliding = false;

		for (Sprite sprite : sprites) {
			if (sprite != this) {
				boolean toLeft = (x + this.IMAGE_WIDTH) < sprite.getMinX();
				boolean toRight = x > sprite.getMaxX();
				boolean collidingX = !( toLeft || toRight);
				boolean above = (y + this.IMAGE_HEIGHT) < sprite.getMinY();
				boolean below = y > sprite.getMaxY();
				boolean collidingY = !( above || below);

				if (collidingX && collidingY) {
					colliding = true;
					break;

				}	
			}
		}

		return colliding;
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


	@Override
	public void setEnemySprites(ArrayList<Sprite> staticSprites) {
		this.enemySprites = staticSprites;
	}


	@Override
	public void takeDamage(double damage) {
		// TODO Auto-generated method stub
		if (damageBuffer <= 0) {
			damageBuffer = 1000;
			if (gargoyleStoneplateObtained) {
				if (armour > 0) {
					armour -= damage/2;
					if (armour < 0) {
						health += armour;
						armour = 0;
					}
				}
				else {
					health -= damage/2;
				}
			}
			else {
				if (armour > 0) {
					armour -= damage;
					if (armour < 0) {
						health += armour;
						armour = 0;
					}
				}
				else {
					health -= damage;
				}
			}
		}
	}

	public void addItem(Items item) {
		if (itemPickUpBuffer <= 0) {
			itemPickUpBuffer = 1000;
			if ((money - item.getMoney()) < 0) {
				return;
			}
			if (item.getFileName().equals("cull")) {
				cullObtained = true;
			}
			if (item.getFileName().equals("runaansHurricane")) {
				runaansHurricaneObtained = true;
			}
			if (item.getFileName().equals("gargoyleStoneplate")) {
				gargoyleStoneplateObtained = true;
			}
			if (item.getFileName().equals("rabadonsDeathcap")) {
				this.damage *= 2;
			}
			if (item.getFileName().equals("ludensEcho")) {
				ludensEchoObtained = true;
			}
			damage += item.getDamage();
			speed += item.getSpeed();
			range += item.getRange();
			rateOfFire -= item.getRateOfFire();
			if (rateOfFire < 100) {
				rateOfFire = 100;
			}
			maxHealth += item.getHealth();
			health += item.getHealth() + item.getMissingHealth();
			armour += item.getArmour();
			money -= item.getMoney();
			if (health > maxHealth) {
				health = maxHealth;
			}
			itemObtained = true;
		}
	}

	public void addMoney(double money) {
		if (cullObtained) {
			this.money += 2*money;
		}
		else {
			this.money += money;
		}
	}

	public double getMoney() {
		return money;
	}

	public double getArmour() {
		return armour;
	}

	public void setDamageBuffer(double i) {
		damageBuffer = i;
	}

	public double getItemPickUpBuffer() {
		return itemPickUpBuffer;
	}

	public boolean isItemObtained() {
		return itemObtained;
	}

	public void setItemObtained(boolean itemObtained) {
		this.itemObtained = itemObtained;
	}

	public String getWhichItem() {
		return whichItem;
	}

	public void setWhichItem(String whichItem) {
		this.whichItem = whichItem;
	}

	public boolean isLudensEchoObtained() {
		return ludensEchoObtained;
	}
}
