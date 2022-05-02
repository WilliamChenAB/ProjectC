import java.util.ArrayList;
import java.util.Random;

public class Waves {
	
	Sprite enemy = null;
	
	
	public ArrayList<Sprite> Wave(int floor, int wave){
		
		Random random = new Random();
		int number = random.nextInt(16);
		
		ArrayList<Sprite> sprites = new ArrayList<Sprite>();	
		if (floor == 5) {
			if (wave == 1) {
				enemy = new Melee(600,600,5);
				sprites.add(enemy);
				enemy = new Melee(1100,600,5);
				sprites.add(enemy);
				enemy = new Melee(1600,600,5);
				sprites.add(enemy);
				enemy = new Melee(800,1100,5);
				sprites.add(enemy);
				enemy = new Melee(1100,1100,5);
				sprites.add(enemy);
				enemy = new Melee(1600,1100,5);
				sprites.add(enemy);
				number = -1;
			}
			else if (wave == 2) {
				enemy = new Archer(600,900,5);
				sprites.add(enemy);
				enemy = new Archer(1700,900,5);
				sprites.add(enemy);
				number = -1;
			}
			else if (wave == 3) {
				enemy = new Turret(600,900,5);
				sprites.add(enemy);
				enemy = new Turret(1700,900,5);
				sprites.add(enemy);
				number = -1;
			}
			else if (wave == 4) {
				enemy = new Bouncing(1100,900,5);
				sprites.add(enemy);
				number = -1;
			}
			else if (wave == 5) {
				enemy = new FinalBoss(1100,900);
				sprites.add(enemy);
				number = -1;
			}
		}
		else if (wave != 5){
			if (floor == 1){
				number = new Random().nextInt(9);
			}
			if (floor == 2){
				number = new Random().nextInt(11);
			}
			if(floor == 3){
				number = new Random().nextInt(21);
			}
			if(floor == 4){
				number = new Random().nextInt(21);
			}
		}
		else if (floor == 1 && wave == 5){
			enemy = new Melee(1400,900,5);
			sprites.add(enemy);
			number = -1;
		}
		else if (floor == 2 && wave == 5){
			enemy = new Archer(1400,900,5);
			sprites.add(enemy);
			number = -1;
		}
		else if (floor == 3 && wave == 5){
			enemy = new Turret(1400,900,5);
			sprites.add(enemy);
			number = -1;
		}
		else if (floor == 4 && wave == 5){
			enemy = new Bouncing(1400,900,5);
			sprites.add(enemy);
			number = -1;
		}

		if(number == 0){
			enemy = new Archer(13*50, 800,floor);
			sprites.add(enemy);
			enemy = new Archer(29*50, 800,floor);
			sprites.add(enemy);
			enemy = new Archer(17*50, 1100,floor);
			sprites.add(enemy);
			enemy = new Archer(16*50, 1100,floor);
			sprites.add(enemy);
			enemy = new Archer(29*50, 800,floor);
			sprites.add(enemy);
			enemy = new Archer(18*50,1000,floor);
			sprites.add(enemy);
		}
		if(number == 1){
			enemy = new Melee(13*50, 800,floor);
			sprites.add(enemy);
			enemy = new Melee(26*50, 800,floor);
			sprites.add(enemy);
			enemy = new Melee(29*50, 1000,floor);
			sprites.add(enemy);
			enemy = new Melee(18*50, 1000,floor);
			sprites.add(enemy);
			enemy = new Melee(22*50, 800,floor);
			sprites.add(enemy);
			enemy = new Melee(18*50,1000,floor);
			sprites.add(enemy);
		}
		if(number == 2){
			enemy = new Archer(13*50, 800,floor);
			sprites.add(enemy);
			enemy = new Melee(26*50, 800,floor);
			sprites.add(enemy);
			enemy = new Archer(29*50, 1000,floor);
			sprites.add(enemy);
			enemy = new Melee(18*50, 1000,floor);
			sprites.add(enemy);
			enemy = new Archer(22*50, 800,floor);
			sprites.add(enemy);
			enemy = new Melee(18*50,1000,floor);
			sprites.add(enemy);

		}
		if(number == 3){
			enemy = new Melee(26*50, 800,floor);
			sprites.add(enemy);
			enemy = new Archer(29*50, 1000,floor);
			sprites.add(enemy);
			enemy = new Archer(22*50, 800,floor);
			sprites.add(enemy);
			enemy = new Melee(18*50,1000,floor);
			sprites.add(enemy);

		}
		if(number == 4){
			enemy = new Melee(29*50, 1000,floor);
			sprites.add(enemy);
			enemy = new Melee(18*50, 1000,floor);
			sprites.add(enemy);
			enemy = new Melee(22*50, 800,floor);
			sprites.add(enemy);
			enemy = new Melee(18*50,1000,floor);
			sprites.add(enemy);
			enemy = new Melee( 500, 600, floor);
			sprites.add(enemy);
			enemy = new Melee( 1000, 600, floor);
			sprites.add(enemy);
		}
		if(number == 5){
			enemy = new Archer(13*50, 800,floor);
			sprites.add(enemy);
			enemy = new Archer(29*50, 800,floor);
			sprites.add(enemy);
			enemy = new Archer(17*50, 1100,floor);
			sprites.add(enemy);
			enemy = new Archer(16*50, 1100,floor);
			sprites.add(enemy);
			enemy = new Archer(18*50,1000,floor);
			sprites.add(enemy);
			enemy = new Archer( 1000, 700,floor);
			sprites.add(enemy);
		}
		if(number == 6){
			enemy = new Melee(14*50, 1000,floor);
			sprites.add(enemy);
			enemy = new Melee(18*50, 1000,floor);
			sprites.add(enemy);
			enemy = new Melee(19*50, 800,floor);
			sprites.add(enemy);
			enemy = new Melee(18*50,1000,floor);
			sprites.add(enemy);
			enemy = new Archer(900, 700,floor);
			sprites.add(enemy);
			enemy = new Archer( 900, 900,floor);
			sprites.add(enemy);

		}
		if(number == 7){
			enemy = new Melee(14*50, 1000,floor);
			sprites.add(enemy);
			enemy = new Melee(18*50, 1000,floor);
			sprites.add(enemy);
			enemy = new Melee(19*50, 800,floor);
			sprites.add(enemy);
			enemy = new Melee(18*50,1000,floor);
			sprites.add(enemy);
			enemy = new Archer(16*50,900,floor);
			sprites.add(enemy);
		}
		if(number == 8){
			enemy = new Melee(14*50, 1000,floor);
			sprites.add(enemy);
			enemy = new Archer(18*50, 1000,floor);
			sprites.add(enemy);
			enemy = new Melee(19*50, 800,floor);
			sprites.add(enemy);
			enemy = new Archer(18*50,1000,floor);
			sprites.add(enemy);
			enemy = new Archer(16*50,900,floor);
			sprites.add(enemy);



		}if(number == 9){
			enemy = new Turret(14*50, 1000,floor);
			sprites.add(enemy);
			enemy = new Turret(20*50, 1000,floor);
			sprites.add(enemy);	
			enemy = new Turret(600, 600,floor);
			sprites.add(enemy);
			enemy = new Turret(600, 750,floor);
			sprites.add(enemy);
			enemy = new Turret(600, 900,floor);
			sprites.add(enemy);

		}
		if(number == 10){
			enemy = new Turret(12*50, 800,floor);
			sprites.add(enemy);
			enemy = new Archer(29*50, 800,floor);
			sprites.add(enemy);
			enemy = new Turret(17*50, 1100,floor);
			sprites.add(enemy);
			enemy = new Archer(16*50, 1100,floor);
			sprites.add(enemy);
			enemy = new Turret(18*50,1000,floor);
			sprites.add(enemy);


		}
		if(number == 11){
			enemy = new Archer(12*50, 800,floor);
			sprites.add(enemy);
			enemy = new Turret(29*50, 800,floor);
			sprites.add(enemy);
			enemy = new Archer(17*50, 1100,floor);
			sprites.add(enemy);
			enemy = new Archer(16*50, 1100,floor);
			sprites.add(enemy);
			enemy = new Turret(29*50, 800,floor);
			sprites.add(enemy);
			enemy = new Archer(18*50,1000,floor);
			sprites.add(enemy);


		}
		if(number == 12){
			enemy = new Archer(12*50, 800,floor);
			sprites.add(enemy);
			enemy = new Turret(26*50, 800,floor);
			sprites.add(enemy);
			enemy = new Turret(29*50, 1000,floor);
			sprites.add(enemy);
			enemy = new Melee(18*50, 1000,floor);
			sprites.add(enemy);
			enemy = new Archer(22*50, 800,floor);
			sprites.add(enemy);
			enemy = new Melee(18*50,1000,floor);
			sprites.add(enemy);
		}
		if(number == 13){
			enemy = new Melee(14*50, 1000,floor);
			sprites.add(enemy);
			enemy = new Bouncing(18*50, 1000,floor);
			sprites.add(enemy);
			enemy = new Melee(19*50, 800,floor);
			sprites.add(enemy);
			enemy = new Bouncing(18*50,1000,floor);
			sprites.add(enemy);
			enemy = new Turret(16*50,900,floor);
			sprites.add(enemy);
		}
		if(number == 14 && floor > 2){
			enemy = new Bouncing(12*50, 800,floor);
			sprites.add(enemy);
			enemy = new Bouncing(26*50, 800,floor);
			sprites.add(enemy);
			enemy = new Bouncing(29*50, 1000,floor);
			sprites.add(enemy);
			enemy = new Bouncing(18*50, 1000,floor);
			sprites.add(enemy);
			enemy = new Bouncing(22*50, 800,floor);
			sprites.add(enemy);
			enemy = new Bouncing(18*50,1000,floor);
			sprites.add(enemy);
		}
		if(number == 15 && floor > 2){
			enemy = new Bouncing(15*50, 800,floor);
			sprites.add(enemy);
			enemy = new Bouncing(17*50, 800,floor);
			sprites.add(enemy);
			enemy = new Bouncing(19*50, 800,floor);
			sprites.add(enemy);
			enemy = new Turret( 750, 700,floor);
			sprites.add(enemy);
			enemy = new Turret( 750, 600,floor);
			sprites.add(enemy);
			enemy = new Turret( 750, 1000,floor);
			sprites.add(enemy);
		}
		else if (number == 15) {
			enemy = new Archer( 600, 700, floor);
			sprites.add(enemy);
			enemy = new Archer( 600, 600, floor);
			sprites.add(enemy);
			enemy = new Archer( 600, 800, floor);
			sprites.add(enemy);
			enemy = new Archer( 600, 600, floor);
			sprites.add(enemy);
			enemy = new Melee( 1000, 700, floor);
			sprites.add(enemy);
			enemy = new Melee( 1000, 800, floor);
			sprites.add(enemy);
		}
		if(number == 16){
			enemy = new Archer(12*50, 800,floor);
			sprites.add(enemy);
			enemy = new Bouncing(26*50, 800,floor);
			sprites.add(enemy);
			enemy = new Turret(29*50, 1000,floor);
			sprites.add(enemy);
			enemy = new Bouncing(18*50, 1000,floor);
			sprites.add(enemy);
			enemy = new Archer(22*50, 800,floor);
			sprites.add(enemy);
			enemy = new Melee(18*50,1000,floor);
			sprites.add(enemy);
		}
		if(number == 17) {
			enemy = new Archer( 600, 700, floor);
			sprites.add(enemy);
			enemy = new Archer( 700, 600, floor);
			sprites.add(enemy);
			enemy = new Archer( 800, 800, floor);
			sprites.add(enemy);
			enemy = new Archer( 600, 600, floor);
			sprites.add(enemy);
			enemy = new Melee( 1000, 700, floor);
			sprites.add(enemy);
			enemy = new Melee( 1000, 800, floor);
			sprites.add(enemy);
		}
		if(number == 18) {
			enemy = new Archer( 1200, 700, floor);
			sprites.add(enemy);
			enemy = new Archer( 1200, 900, floor);
			sprites.add(enemy);
			enemy = new Archer( 1200, 1100, floor);
			sprites.add(enemy);
			enemy = new Turret( 800, 700, floor);
			sprites.add(enemy);
			enemy = new Turret( 800, 900, floor);
			sprites.add(enemy);
			enemy = new Turret( 800, 1100, floor);
			sprites.add(enemy);
		}
		if (number == 19) {
			enemy = new Melee( 600, 400, floor);
			sprites.add(enemy);
			enemy = new Melee( 600, 600, floor);
			sprites.add(enemy);
			enemy = new Melee( 600, 800, floor);
			sprites.add(enemy);
			enemy = new Melee( 1000, 600, floor);
			sprites.add(enemy);
			enemy = new Melee( 1000, 700, floor);
			sprites.add(enemy);
			enemy = new Melee( 1000, 800, floor);
			sprites.add(enemy);
		}
		if (number == 20) {
			enemy = new Melee( 1000, 700, floor);
			sprites.add(enemy);
			enemy = new Melee( 1000, 800, floor);
			sprites.add(enemy);
			enemy = new Archer( 500, 800, floor);
			sprites.add(enemy);
			enemy = new Archer( 500, 600, floor);
			sprites.add(enemy);
			enemy = new Turret( 700, 600, floor);
			sprites.add(enemy);
			enemy = new Turret( 700, 850, floor);
			sprites.add(enemy);
		}
		System.out.println(number);
		return sprites;

	}
}



