import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class GameLoop extends JFrame {

	final public static int FRAMES_PER_SECOND = 60;
	final public static int SCREEN_HEIGHT = 690;
	final public static int SCREEN_WIDTH = 1340;
	final public static Color BARRIER_COLOR = Color.BLACK;
	final public static boolean CENTER_ON_PLAYER = false;

	private JPanel panel = null;
	private JButton btnPauseRun;
	private JLabel lblTimeLabel;
	private JLabel lblTime;
	private JLabel lblWave;
	private JLabel lblFloor;
	private JLabel lblHealth;
	private JLabel lblMoney;
	private JLabel lblArmour;
	static GameLoop gameFrame;
	private boolean alive = true;

	private static Thread loop;
	private GameMap background = new GameMap();    
	private GrassBackground grass = new GrassBackground();
	private KeyboardInput keyboard = new KeyboardInput();

	long current_time = 0;								//MILLISECONDS
	long next_refresh_time = 0;							//MILLISECONDS
	long last_refresh_time = 0;
	long minimum_delta_time = 1000 / FRAMES_PER_SECOND;	//MILLISECONDS
	long actual_delta_time = 0;							//MILLISECONDS
	long elapsed_time = 0;
	long elapsed_time_set = 0;
	long time;
	long timeChangePeriod;
	private boolean isPaused = false;
	String timeString;
	private boolean floorOver = false;
	private boolean cursedRoomDamageTaken = false;
	private int floor = 5;
	private int wave = 1;
	private final Rectangle SEALEDROOMBARRIER = new Rectangle(36*50,1,50,2000);
	int xOffset = 0;
	int yOffset = 0;
	boolean buttonPressed = false;

	Waves gameWave = new Waves();
	ArrayList<Rectangle> barriers = new ArrayList<Rectangle>();
	ArrayList<Sprite> sprites = new ArrayList<Sprite>();
	ArrayList<Sprite> enemySprites = new ArrayList<Sprite>();
	ArrayList<Sprite> spritesToDispose = new ArrayList<Sprite>();
	ArrayList<Rectangle> temporaryBarriers = new ArrayList<Rectangle>();
	ArrayList<Sprite> enemySoonSprites = new ArrayList<Sprite>();
	ArrayList<Sprite> soonItems = new ArrayList<Sprite>();
	Player me = null;
	Sprite shopItem1 = null;
	Sprite shopItem2 = null;
	Sprite shopItem3 = null;
	Sprite shopItem4 = null;
	Sprite cursedRoomItem = null;
	Sprite sealedRoomItem = null;

	public GameLoop()
	{
		super("");
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				System.exit(1);
			}
		});

		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				keyboard.keyPressed(arg0);
			}
			@Override
			public void keyReleased(KeyEvent arg0) {
				keyboard.keyReleased(arg0);
			}
		});
		this.setFocusable(true);

		getContentPane().setBackground(Color.BLACK);

		panel = new DrawPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		getContentPane().setLayout(null);

		btnPauseRun = new JButton("||");
		btnPauseRun.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				btnPauseRun_mouseClicked(arg0);
			}
		});

		btnPauseRun.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnPauseRun.setBounds(20, 20, 48, 32);
		getContentPane().add(btnPauseRun);

		lblTime = new JLabel("000");
		lblTime.setForeground(Color.WHITE);
		lblTime.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblTime.setBounds(171, 22, 302, 30);
		getContentPane().add(lblTime);

		panel.setLayout(null);
		panel.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
		setSize(SCREEN_WIDTH + 20, SCREEN_HEIGHT + 36);

		lblTimeLabel = new JLabel("Time: ");
		lblTimeLabel.setForeground(Color.WHITE);
		lblTimeLabel.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblTimeLabel.setBounds(78, 22, 239, 30);
		getContentPane().add(lblTimeLabel);

		lblWave = new JLabel("Wave : ");
		lblWave.setForeground(Color.WHITE);
		lblWave.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblWave.setBounds(78, 70, 239, 30);
		getContentPane().add(lblWave);

		lblFloor = new JLabel("Floor : ");
		lblFloor.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblFloor.setForeground(Color.WHITE);
		lblFloor.setBounds(78, 48, 239, 30);
		getContentPane().add(lblFloor);

		lblHealth = new JLabel("Health :");
		lblHealth.setForeground(Color.WHITE);
		lblHealth.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblHealth.setBounds(235, 26, 239, 30);
		getContentPane().add(lblHealth);

		lblArmour = new JLabel("Armour :");
		lblArmour.setForeground(Color.WHITE);
		lblArmour.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblArmour.setBounds(235, 48, 239, 30);
		getContentPane().add(lblArmour);

		lblMoney = new JLabel("Money :");
		lblMoney.setForeground(Color.WHITE);
		lblMoney.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblMoney.setBounds(235, 70, 239, 30);
		getContentPane().add(lblMoney);

		getContentPane().setComponentZOrder(lblTimeLabel, 0);
		getContentPane().setComponentZOrder(lblHealth, 0);
		getContentPane().setComponentZOrder(lblArmour, 0);
		getContentPane().setComponentZOrder(lblMoney, 0);
		getContentPane().setComponentZOrder(lblTime, 0);
		getContentPane().setComponentZOrder(btnPauseRun, 0);
		getContentPane().setComponentZOrder(lblFloor, 0);
		getContentPane().setComponentZOrder(lblWave, 0);
		getContentPane().setComponentZOrder(btnPauseRun, 0);

		createBarriers();
		createSprites();
		this.lblTime.setText("11");   			    	    
	}

	private void createBarriers() {
		this.barriers = background.getBarriers();
		this.barriers.add(SEALEDROOMBARRIER);
		temporaryBarriers.add(new Rectangle(1,11*50,2000,50));
		temporaryBarriers.add(new Rectangle(1,25*50,2000,50));
		temporaryBarriers.add(new Rectangle(9*50,1,50,2000));
	}

	private void createSprites() {

		Random random = new Random();

		me = new Player(1000, 800);
		sprites.add(me);
		shopItem1 = new ItemSprite(1000, 100, "normal", "shop");
		sprites.add(shopItem1);
		shopItem2 = new ItemSprite(1100, 100, "normal", "shop");
		sprites.add(shopItem2);
		shopItem3 = new ItemSprite(1200, 100, "healthPotion", "shop");
		sprites.add(shopItem3);
		shopItem4 = new ItemSprite(1300, 100, "doransShield", "shop");
		sprites.add(shopItem4);
		sealedRoomItem = new ItemSprite(2050, 700, "special", "special");
		sprites.add(sealedRoomItem);

		int i = random.nextInt(3);
		if (i == 0) {
			cursedRoomItem = new ItemSprite(225, 1000, "normal", "cursed");
			sprites.add(cursedRoomItem);
		}
		else if (i == 1) {
			cursedRoomItem = new ItemSprite(225, 1000, "special", "cursed");
			sprites.add(cursedRoomItem);
		}
		else {
			cursedRoomItem = null;
		}

		for (Sprite sprite : sprites) {
			sprite.setBarriers(barriers);
			sprite.setSprites(sprites);
			sprite.setEnemySprites(enemySprites);
		}
		for (Sprite sprite : enemySprites) {
			sprite.setBarriers(barriers);
			sprite.setSprites(sprites);
			sprite.setEnemySprites(enemySprites);
		}

	}

	private void resetItems() {
		Random random = new Random();
		int i = random.nextInt(3);
		if (me.getWhichItem().equals("shopItem1")) {
			shopItem1 = new ItemSprite(1000, 100, "normal", "shop");
			sprites.add(shopItem1);
			me.setWhichItem("");
		}
		else if (me.getWhichItem().equals("shopItem2")) {
			shopItem2 = new ItemSprite(1100, 100, "normal", "shop");
			sprites.add(shopItem2);
			me.setWhichItem("");
		}
		else if (me.getWhichItem().equals("shopItem3")) {
			shopItem3 = new ItemSprite(1200, 100, "healthPotion", "shop");
			sprites.add(shopItem3);
			me.setWhichItem("");
		}
		else if (me.getWhichItem().equals("shopItem4")) {
			shopItem4 = new ItemSprite(1300, 100, "doransShield", "shop");
			sprites.add(shopItem4);
			me.setWhichItem("");
		}
		if (floorOver) {
			if (cursedRoomItem != null) {
				sprites.remove(cursedRoomItem);
			}
			if (i == 0) {
				cursedRoomItem = new ItemSprite(225, 1000, "normal", "cursed");
				sprites.add(cursedRoomItem);
			}
			else if (i == 1) {
				cursedRoomItem = new ItemSprite(225, 1000, "special", "cursed");
				sprites.add(cursedRoomItem);
			}
			else {
				cursedRoomItem = null;
			}
			sprites.remove(sealedRoomItem);
			sealedRoomItem = new ItemSprite(2050, 700, "special", "special");
			sprites.add(sealedRoomItem);
		}
		for (Sprite sprite : sprites) {
			sprite.setBarriers(barriers);
			sprite.setSprites(sprites);
			sprite.setEnemySprites(enemySprites);
		}
	}

	public static void main(String[] args)
	{
		boolean playAgain = true;


		while (playAgain) {

			Intro greetings = new Intro();
			greetings.setVisible(true);
			if (greetings.play() == false) {
				//playAgain = false;
				break;
			} //else {
			//playAgain = true;
			//}

			gameFrame = new GameLoop();
			gameFrame.setVisible(true);
			loop = new Thread()
			{
				public void run()
				{
					gameFrame.gameLoop();
				}
			};
			loop.start();

			//do not proceed until game is over
			while (gameFrame.alive == true) {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			if (gameFrame.floor >= 6) {
				Victory win = new Victory();
				win.setLocationRelativeTo(gameFrame);
				win.setVisible(true);

				if(win.isOk()) {

				}
				else {
					playAgain = false;
				}
				gameFrame.setVisible(false);
				gameFrame.dispose();
			}
			else {

				GameOver end = new GameOver();
				end.setLocationRelativeTo(gameFrame);

				end.setVisible(true);


				if(end.isOk()){

				}
				else {
					playAgain = false;
				}

				gameFrame.setVisible(false);
				gameFrame.dispose();
			}


		}


	}

	private void gameLoop() {

		while (alive) { // main game loop

			//adapted from http://www.java-gaming.org/index.php?topic=24220.0
			last_refresh_time = System.currentTimeMillis();
			next_refresh_time = current_time + minimum_delta_time;

			while (current_time < next_refresh_time)
			{
				Thread.yield();

				try {Thread.sleep(1);}
				catch(Exception e) {} 

				current_time = System.currentTimeMillis();
			}

			//read input
			keyboard.poll();
			handleKeyboardInput();
			if (me.isItemObtained()) {
				resetItems();
				me.setItemObtained(false);
				System.out.println("working");
			}
			if (keyboard.keyDownOnce(78)){

				if (!buttonPressed) {
					barriers.add(new Rectangle(1,11*50,2000,50));
					barriers.add(new Rectangle(1,25*50,2000,50));
					barriers.add(new Rectangle(9*50,1,50,2000));
					buttonPressed = true;
					floorOver = false;
					barriers.add(SEALEDROOMBARRIER);
				}
			}
			//UPDATE STATE
			if (enemySprites.isEmpty() && wave == 1 && floorOver) {
				barriers.removeAll(temporaryBarriers);
				buttonPressed = false;
				barriers.remove(SEALEDROOMBARRIER);
				barriers.remove(SEALEDROOMBARRIER);
				cursedRoomDamageTaken = false;
				resetItems();
				floorOver = false;
			}
			if (buttonPressed && !floorOver){
				updateTimeC();
			}
			updateTime();
			updateSprites();
			disposeSprites();

			//REFRESH
			this.lblHealth.setText("Health : " + me.getHealth());
			this.repaint();
			this.lblMoney.setText("Money : " + me.getMoney());
			this.lblWave.setText("Wave : " + (wave-1));
			this.lblFloor.setText("Floor : " + floor);
			this.lblArmour.setText("Armour : " + me.getArmour());

			if(me.getHealth() <= 0){
				this.alive = false;
			}
			if(floor >= 6) {
				this.alive = false;
			}
		}
	}

	private void updateTime() {

		current_time = System.currentTimeMillis();
		actual_delta_time = (isPaused ? 0 : current_time - last_refresh_time);


	}

	private void updateTimeC() {

		time = 12000 - elapsed_time;
		timeChangePeriod = 1000 - elapsed_time_set;

		if(time < 0){
			this.lblTime.setText("11");
			elapsed_time = 0;
			elapsed_time_set = 0;
			levelRun();
		}
		else if(timeChangePeriod < 0) {
			timeString = Long.toString(time / 1000);
			this.lblTime.setText(timeString);
			elapsed_time_set = 0;
		}
		else{
			elapsed_time += actual_delta_time;
			elapsed_time_set += actual_delta_time;
		}

	}

	private void levelRun(){

		if(wave > -1){
			if (wave == 6){
				if (floor == 5) {
					System.exit(1);
				}
				else {
					wave = 1;
					floor++;
					floorOver = true;
				}
			}
			else {
				enemySoonSprites = gameWave.Wave(floor,wave);
				wave++;
				me.setDamageBuffer(1000);
				for (Sprite sprite : enemySoonSprites) {
					sprite.setBarriers(barriers);
					sprite.setSprites(sprites);
					sprite.setEnemySprites(enemySprites);
					enemySprites.add(sprite);
				}
			}
		}

	}

	private void updateSprites() {
		try {
			for (int i = 0; i < sprites.size(); i++) {
				Sprite sprite = sprites.get(i);
				sprite.update(keyboard, actual_delta_time);
			}
			for (int i = 0; i < enemySprites.size(); i++) {
				Sprite sprite = enemySprites.get(i);
				sprite.update(keyboard, actual_delta_time);
			}
		} catch (Exception e) {
			System.out.println(e);
		}    	
	}

	private void disposeSprites() {
		for (Sprite sprite : this.sprites) {
			if (sprite.getDispose() == true) {
				spritesToDispose.add(sprite);
			}
		}
		for (Sprite sprite : this.enemySprites) {
			if (sprite.getDispose() == true) {
				spritesToDispose.add(sprite);
			}
		}
		for (Sprite sprite : this.spritesToDispose) {
			sprites.remove(sprite);
			enemySprites.remove(sprite);
			if (sprite instanceof ItemSprite) {
				sprite = null;
			}
		}
		if (spritesToDispose.size() > 0) {
			spritesToDispose.clear();
		}
	}

	protected void btnPauseRun_mouseClicked(MouseEvent arg0) {
		if (isPaused) {
			isPaused = false;
			this.btnPauseRun.setText("||");
		}
		else {
			isPaused = true;
			this.btnPauseRun.setText(">");
		}
	}

	private void handleKeyboardInput() {
		//if the interface needs to respond to certain keyboard events
		if (keyboard.keyDown(80) && ! isPaused) {
			btnPauseRun_mouseClicked(null);	
		}
		if (keyboard.keyDown(79) && isPaused ) {
			btnPauseRun_mouseClicked(null);
		}
	}

	class DrawPanel extends JPanel {

		public void paintComponent(Graphics g) {
			xOffset = - 475;
			yOffset = - 582;
			// up
			if (me.getMinY() < 10 * 50 ) {
				xOffset = - 477;
				yOffset =  122;
			}
			// right
			if(me.getMinX() > 1755){
				xOffset = - 1820;
				yOffset =  -575;

			}
			// left
			if (me.getMinX() < 400){
				xOffset = 870;
				yOffset = -580;
				if (!cursedRoomDamageTaken) {
					me.takeDamage(1);
				}
				cursedRoomDamageTaken = true;
			}

			paintBackground(g, grass);
			paintBackground(g, background);

			g.setColor(BARRIER_COLOR);
			for (int barrier = 0; barrier < barriers.size(); barrier++) {
				g.fillRect((int)barriers.get(barrier).getX() + xOffset,(int) barriers.get(barrier).getY() + yOffset, (int)barriers.get(barrier).getWidth(), (int)barriers.get(barrier).getHeight());       	
			}

			for (Sprite staticSprite : sprites) {
				g.drawImage(staticSprite.getImage(), (int)staticSprite.getMinX() + xOffset, (int)staticSprite.getMinY() + yOffset, (int)staticSprite.getWidth(), (int)staticSprite.getHeight(), null);
			}
			for (int i = 0; i < enemySprites.size(); i++) {
				g.drawImage(enemySprites.get(i).getImage(), (int)enemySprites.get(i).getMinX() + xOffset, (int)enemySprites.get(i).getMinY() + yOffset, (int)enemySprites.get(i).getWidth(), (int)enemySprites.get(i).getHeight(), null);
			}

		}



		private void paintBackground(Graphics g, Background background) {
			//what tile covers the top-left corner?
			int xTopLeft = - xOffset;
			int yTopLeft = - yOffset;
			int row = background.getRow(yTopLeft);
			int col = background.getCol(xTopLeft);
			Tile tile = null;

			boolean rowDrawn = false;
			boolean colDrawn = false;
			while (colDrawn == false) {
				while (rowDrawn == false) {
					tile = background.getTile(col, row);
					g.drawImage(tile.getImage(), tile.getX() + xOffset, tile.getY() + yOffset, tile.getWidth(), tile.getHeight(), null);
					//does the RHE of this tile extend past the RHE of the visible area?
					int rheTile = tile.getX() + xOffset + tile.getWidth();
					if (rheTile > SCREEN_WIDTH || tile.isOutOfBounds()) {
						rowDrawn = true;
					}
					else {
						col++;
					}
				}
				//does the bottom edge of this tile extend past the bottom edge of the visible area?
				int bottomEdgeTile = tile.getY() + yOffset + tile.getHeight();
				if (bottomEdgeTile > SCREEN_HEIGHT || tile.isOutOfBounds()) {
					colDrawn = true;
				}
				else {
					col = background.getCol(xTopLeft);
					row++;
					rowDrawn = false;
				}
			}
		}				
	}
}
