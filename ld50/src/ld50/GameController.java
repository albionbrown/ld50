package ld50;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

import com.albionbrown.rawge.Controller;
import com.albionbrown.rawge.GameContainer;
import com.albionbrown.rawge.Input;
import com.albionbrown.rawge.Renderer;
import com.albionbrown.rawge.gfx.*;

public class GameController implements Controller {

public static Input input;
public Player player;
public Drunk drunk;
public Meter drunkMeter;
public Table table1;
public Table table2;
public Table table3;
public Table table4;
public Table table5;
public ArrayList<Table> tables;

  public static void main(String[] args) {
	  GameController gController = new GameController();
	  GameContainer gContainer = new GameContainer("LD50", 1280, 960, gController);
	  gContainer.setScale(1f);
	  gContainer.initialise();
	  
	  input = gContainer.getInput();
	  
	  gController.init();
	  
	  gContainer.start();
  }
 
  
  public GameController() {
	  tables = new ArrayList<Table>();
  }
  
  public void init() {
	  
	  // Initialise player
	  Image playerImage = new Image(getClass().getResourceAsStream("/img/player.png"));
	  player = new Player("player", playerImage, 82, 60, 730, 840, input);

	  // Initialise the drunk
	  ImageTile drunkImage = new ImageTile(getClass().getResourceAsStream("/img/drunk_animation.png"), 80, 80);
	  drunk = new Drunk("drunk", drunkImage, 80, 80, 640, 840, input);

	  // Initialise scenery
	  initialiseTables();
	  drunk.setTables(tables);
	  
	  drunkMeter = new Meter(0, 100);
	  drunkMeter.setIncrement(1);
	  drunk.setDrunknessMeter(drunkMeter);
	  
	  player.setDrunk(drunk);
  }

  @Override
  public void update(GameContainer gc) {
	  
	 drunk.update();
	 player.update();
	 drunkMeter.update();
  }

  @Override
  public void render(Renderer r) {
	
	  // Draw grass
	  r.drawSquare(5668166, 0, 0, 1280, 960);
	  
	  // Draw tables
	  table1.render(r);
	  table2.render(r);
	  table3.render(r);
	  table4.render(r);
	  table5.render(r);
	  
	  drunk.render(r);
	  player.render(r);
	  
	  drunkMeter.render(r);
  }
  
  private void initialiseTables() {
	  table1 = new Table();
	  table1.setX(50);
	  table1.setY(750);
	  table1.setWidth(350);
	  table1.setHeight(180);
	  table1.setPointX(225);
	  table1.setPointY(750);

	  table2 = new Table();
	  table2.setX(50);
	  table2.setY(265);
	  table2.setWidth(180);
	  table2.setHeight(350);
	  table2.setPointX(230);
	  table2.setPointY(440);

	  table3 = new Table();
	  table3.setX(850);
	  table3.setY(50);
	  table3.setWidth(350);
	  table3.setHeight(180);
	  table3.setPointX(1025);
	  table3.setPointY(230);

	  table4 = new Table();
	  table4.setX(1050);
	  table4.setY(550);
	  table4.setWidth(180);
	  table4.setHeight(350);
	  table4.setPointX(1050);
	  table4.setPointY(725);

	  table5 = new Table();
	  table5.setX(315);
	  table5.setY(50);
	  table5.setWidth(350);
	  table5.setHeight(180);
	  table5.setPointX(490);
	  table5.setPointY(230);
	  
	  tables.add(table1);
	  tables.add(table2);
	  tables.add(table3);
	  tables.add(table4);
	  tables.add(table5);
  }
}