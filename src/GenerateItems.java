import java.util.ArrayList;

public class GenerateItems {
	
	private ArrayList<Items> normalItems = new ArrayList<Items>();
	private ArrayList<Items> specialItems = new ArrayList<Items>();
	private ArrayList<Items> consumables = new ArrayList<Items>();
   
	public GenerateItems() {
        
	Items rubyCrystal = new Items(0, 0, 0, 0, 1, 0, 0, 15, "normal", "rubyCrystal");
	Items longSword = new Items(0.5, 0, 0, 0, 0, 0, 0, 15, "normal", "longSword");
	Items dagger = new Items(0, 0, 0, 100, 0, 0, 0, 15, "normal", "dagger");
	Items pickaxe = new Items(1, 0, 0, 0, 0, 0, 0, 20, "normal", "pickaxe");
	Items amplifyingTome = new Items(0, 0, 100, 0, 0, 0, 0, 15, "normal", "amplifyingTome");
	Items doransBlade = new Items(0.25, 0, 0, 0, 0, 1, 0, 15, "normal", "doransBlade");
	Items corruptingPotion = new Items(0, 0, 0, 0, 0, 100, 0, 15, "normal", "corruptingPotion");
	Items giantsBelt = new Items(0, 0, 0, 0, 2, 0, 0, 20, "normal", "giantsBelt");
	Items chainVest = new Items(0, 0, 0, 0, 0, 0, 2, 20, "normal", "chainVest");
	Items recurveBow = new Items(0, 0, 0, 200, 0, 0, 0, 20, "normal", "recurveBow");
	Items aegisOfTheLegion = new Items(0, 0, 0, 0, 1, 0, 1, 20, "normal", "aegisOfTheLegion");
	Items blastingWand = new Items(0, 0, 250, 0, 0, 0, 0, 20, "normal", "blastingWand");
	Items jaurimsFist = new Items(0.5, 0, 0, 0, 1, 0, 0, 20, "normal", "jaurimsFist");
	Items seekersArmguard = new Items(0, 0, 100, 0, 0, 0, 1, 20, "normal", "seekersArmguard");
	Items bootsOfSpeed = new Items(0, 0.15, 0, 0, 0, 0, 0, 15, "normal", "bootsOfSpeed");
	Items berserkersGreaves = new Items(0, 0.1, 0, 100, 0, 0, 0, 20, "normal", "berserkersGreaves");
	Items ninjaTabi = new Items(0, 0.1, 0, 0, 0, 0, 1, 20, "normal", "ninjaTabi");
	Items sorcerersShoes = new Items(0, 0.1, 100, 0, 0, 0, 0, 20, "normal", "sorcerersShoes");
	Items bootsOfSwiftness = new Items(0, 0.1, 0, 0, 0, 0, 0, 20, "normal", "bootsOfSwiftness");
	Items ancientCoin = new Items(0, 0, 0, 0, 0, 0, 0, -20, "normal", "ancientCoin");
	Items cull = new Items(0, 0, 0, 0, 0, 0, 0, 20, "normal", "cull");
	Items runaansHurricane = new Items(0, 0, 0, 0, 0, 0, 0, 20, "normal", "runaansHurricane");
	Items ludensEcho = new Items(0, 0, 0, 0, 0, 0, 0, 20, "normal", "ludensEcho");
	normalItems.add(rubyCrystal);
	normalItems.add(longSword);
	normalItems.add(dagger);
	normalItems.add(pickaxe);
	normalItems.add(amplifyingTome);
	normalItems.add(doransBlade);
	normalItems.add(corruptingPotion);
	normalItems.add(giantsBelt);
	normalItems.add(chainVest);
	normalItems.add(recurveBow);
	normalItems.add(aegisOfTheLegion);
	normalItems.add(blastingWand);
	normalItems.add(jaurimsFist);
	normalItems.add(seekersArmguard);
	normalItems.add(bootsOfSpeed);
	normalItems.add(berserkersGreaves);
	normalItems.add(ninjaTabi);
	normalItems.add(sorcerersShoes);
	normalItems.add(bootsOfSwiftness);
	normalItems.add(ancientCoin);
	normalItems.add(cull);
	normalItems.add(runaansHurricane);
	normalItems.add(ludensEcho);
	
	Items warmogsArmor = new Items(0, 0, 0, 0, 3, 0, 0, 0, "special", "warmogsArmor");
	Items thornmail = new Items(0, 0, 0, 0, 0, 0, 4, 0, "special", "thornmail");
	Items infinityEdge = new Items(2.5, 0, 0, 0, 0, 0, 0, 0, "special", "infinityEdge");
	Items rabadonsDeathcap = new Items(0, 0, 0, 0, 0, 0, 0, 0, "special", "rabadonsDeathcap");
	Items triForce = new Items(0.5, 0.1, 100, 100, 1, 0, 0, 0, "special", "triForce");
	Items gargoyleStoneplate = new Items(0, 0, 0, 0, 0, 0, 1, 0, "special", "gargoyleStoneplate");
	specialItems.add(warmogsArmor);
	specialItems.add(thornmail);
	specialItems.add(infinityEdge);
	specialItems.add(rabadonsDeathcap);
	specialItems.add(triForce);
	specialItems.add(gargoyleStoneplate);

	Items healthPotion = new Items(0, 0, 0, 0, 0, 1, 0, 6, "healthPotion", "healthPotion");
	Items doransShield = new Items(0, 0, 0, 0, 0, 0, 1, 10, "doransShield", "doransShield");
	consumables.add(healthPotion);
	consumables.add(doransShield);
    }

	public ArrayList<Items> getNormalItems() {
		return normalItems;
	}

	public ArrayList<Items> getSpecialItems() {
		return specialItems;
	}
	
	public ArrayList<Items> getConsumables() {
		return consumables;
	}

}
