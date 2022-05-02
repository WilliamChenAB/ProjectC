import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;

public class ItemSprite extends Sprite{
	
	private Items item = new Items(0, 0, 0, 0, 0, 0, 0, 0, "", "");
	private ArrayList<Items> normalItems = new ArrayList<Items>();
	private ArrayList<Items> specialItems = new ArrayList<Items>();
	private ArrayList<Items> consumables = new ArrayList<Items>();
	private GenerateItems generatedItems = new GenerateItems();
	
	public ItemSprite(double currentX, double currentY, String type, String location) {
		this.currentX = currentX;
		this.currentY = currentY;
		
		normalItems = generatedItems.getNormalItems();
		specialItems = generatedItems.getSpecialItems();
		consumables = generatedItems.getConsumables();
		
		if (type.equals("healthPotion")) {
			item = consumables.get(0);
		}
		else if (type.equals("doransShield")) {
			item = consumables.get(1);
		}
		else if (type.equals("normal")) {
			Random random = new Random();
			int index = (random.nextInt(normalItems.size()) - 1);
			if (index < 0) {
				index = 0;
			}
			item = normalItems.get(index);
		}
		else if (type.equals("special")) {
			Random random = new Random();
			int index = (random.nextInt(specialItems.size()) - 1);
			if (index < 0) {
				index = 0;
			}
			item = specialItems.get(index);
		}
		if (location.equals("cursed")) {
			item.setMoney(0);
		}
		try {
			this.defaultImage = ImageIO.read(new File("res/Items/"+item.getFileName()+".png"));
			this.IMAGE_HEIGHT = 64;
			this.IMAGE_WIDTH = 64;
		}
		catch (IOException e) {
			System.out.println(e.toString());
		}		
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
		return;
		
	}

	@Override
	public void setMinY(double currentY) {
		return;
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
			if (sprite instanceof Player) {
				boolean toLeft = (x + this.IMAGE_WIDTH) < sprite.getMinX();
				boolean toRight = x > sprite.getMaxX();
				boolean collidingX = !( toLeft || toRight);
				boolean above = (y + this.IMAGE_HEIGHT) < sprite.getMinY();
				boolean below = y > sprite.getMaxY();
				boolean collidingY = !( above || below);

				if (collidingX && collidingY && ((Player)sprite).getItemPickUpBuffer() <= 0) {
					colliding = true;
					if (((Player) sprite).getMoney() < item.getMoney()) {
						System.out.println("not enough money");
						break;
					}
					if (currentX == 1000) {
						((Player) sprite).setWhichItem("shopItem1");
					}
					else if (currentX == 1100) {
						((Player) sprite).setWhichItem("shopItem2");
					}
					else if (currentX == 1200) {
						((Player) sprite).setWhichItem("shopItem3");
					}
					else if (currentX == 1300) {
						((Player) sprite).setWhichItem("shopItem4");
					}
					((Player) sprite).addItem(item);
					this.setDispose();
					break;
				}
			}
		}
		
		return colliding;
	}

	@Override
	public boolean checkCollisionWithBarrier(double x, double y) {
		return false;
	}

	@Override
	public void takeDamage(double damage) {
		return;
	}

	@Override
	public void update(KeyboardInput keyboard, long actual_delta_time) {
		checkCollisionWithSprites(currentX, currentY);
	}

	@Override
	public void setEnemySprites(ArrayList<Sprite> staticSprites) {
		this.enemySprites = staticSprites;
	}

}
