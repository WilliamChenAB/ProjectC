
public class Items {
	
	private double damage = 0;
	private double speed = 0;
	private double range = 0;
	private double rateOfFire = 0;
	private double missingHealth = 0;
	private double health = 0;
	private double armour = 0;
	private double money = 0;
	private String type = "";
	private String fileName = "";
	
	public Items(double damage, double speed, double range, double rateOfFire, double health, double missingHealth, double armour, double money, String type, String fileName) {
		this.damage = damage;
		this.speed = speed;
		this.range = range;
		this.rateOfFire = rateOfFire;
		this.health = health;
		this.armour = armour;
		this.money = money;
		this.missingHealth = missingHealth;
		this.type = type;
		this.fileName = fileName;
	}
	
	public String getFileName() {
		return fileName;
	}

	public double getDamage() {
		return damage;
	}

	public double getSpeed() {
		return speed;
	}

	public double getRange() {
		return range;
	}

	public double getRateOfFire() {
		return rateOfFire;
	}

	public double getMissingHealth() {
		return missingHealth;
	}

	public double getHealth() {
		return health;
	}

	public double getArmour() {
		return armour;
	}

	public double getMoney() {
		return money;
	}
	
	public void setMoney(double money) {
		this.money = money;
	}

}
