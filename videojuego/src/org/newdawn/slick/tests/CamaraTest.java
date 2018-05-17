package org.newdawn.slick.tests;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

/**
 * A test of the tile map system based around the TilED (http://www.mapeditor.org) tool
 *
 * @author kevin
 */
public class CamaraTest extends BasicGame {
	/** The tile map we're going to load and render */
	private TiledMap mapa;
	
	/** the name of the map, read from map properties, specified by TilED */
	private String mapName;
	
	/** how hard are the monsters, read from layer properties, specified by TilED */
	private String monsterDifficulty;
	
	/** we try to read a property from the map which doesn't exist so we expect the default value */
	private String nonExistingMapProperty;
	
	/** we try to read a property from the layer which doesn't exist so we expect the default value */
	private String nonExistingLayerProperty;
	
	/** how long did we wait already until next update */
	private int updateCounter = 0;
	
	/** changing some tile of the map every UPDATE_TIME milliseconds */
	private static int UPDATE_TIME = 1000;
	
	/** we want to store the originalTileID before we set a new one */
	public int xtecla,xcamara = 0;
        public int ytecla,ycamara = 0;
        
        
        public static final int resx = 1920,resy=1080;
	
        public int xraton = resx/2;
        public int yraton = resy/2;
	/**
	 * Create our tile map test
	 */
	public CamaraTest() {
		super("Camera Test");
	}
	
	/**
	 * @see org.newdawn.slick.BasicGame#init(org.newdawn.slick.GameContainer)
	 */
	public void init(GameContainer container) throws SlickException {
            
		mapa = new TiledMap("testdata/salida.tmx","testdata");
                
		// read some properties from map and layer
		mapName = mapa.getMapProperty("name", "Unknown mapaa name");
		monsterDifficulty = mapa.getLayerProperty(0, "monsters", "easy peasy");
		nonExistingMapProperty = mapa.getMapProperty("zaphod", "Undefined mapaaa property");
		nonExistingLayerProperty = mapa.getLayerProperty(1, "beeblebrox", "Undefined layer property");
		
		// store the original tileid of layer 0 at 10, 10
		//originalTileID = mapa.getTileId(10, 10, 0);
	}

	/**
	 * @see org.newdawn.slick.BasicGame#render(org.newdawn.slick.GameContainer, org.newdawn.slick.Graphics)
	 */
	public void render(GameContainer container, Graphics g) {
		mapa.render(0, 0, xcamara,ycamara,150,150);
		
		//g.scale(0.35f,0.35f);
		mapa.render(1400, 0);
		g.resetTransform();
		
		g.drawString("map name: " + mapName, 10, 500);
		g.drawString("monster difficulty: " + monsterDifficulty, 10, 550);
		
		g.drawString("y: " + ycamara, 10, 525);
		g.drawString("x: " + xcamara, 10, 575);
	}

	/**
	 * @see org.newdawn.slick.BasicGame#update(org.newdawn.slick.GameContainer, int)
	 */
	public void update(GameContainer container, int delta) {
		updateCounter += delta;
                xcamara=xraton+xtecla;
                ycamara=yraton+ytecla;
		if (updateCounter > UPDATE_TIME) {
			// swap the tile every second
			updateCounter -= UPDATE_TIME;
			////if (currentTileID != originalTileID)
				//mapa.setTileId(10, 10, 0, originalTileID);
			//else
				//mapa.setTileId(10, 10, 0, 1);
		}
	}

	/**
	 * @see org.newdawn.slick.BasicGame#keyPressed(int, char)
	 */
	public void keyPressed(int key, char c) {
		if (key == Input.KEY_ESCAPE) {
			System.exit(0);
		}
                if (key == Input.KEY_DOWN) {
			ytecla++;
		}
                if (key == Input.KEY_UP) {
			xtecla++;
		}
	}
        public void mouseMoved(int oldx, int oldy, int newx, int newy) {
		yraton=(resy/10)-newy/5;
                xraton=(resx/10)-newx/5;
	}
	
	/**
	 * Entry point to our test
	 * 
	 * @param argv The arguments passed to the test
	 */
	public static void main(String[] argv) {
		try {
			AppGameContainer container = new AppGameContainer(new CamaraTest());
			container.setDisplayMode(resx,resy,true);
			container.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
}
