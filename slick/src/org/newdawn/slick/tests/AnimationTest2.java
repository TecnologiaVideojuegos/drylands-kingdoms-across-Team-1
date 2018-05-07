package org.newdawn.slick.tests;

import org.newdawn.slick.Animation;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

/**
 * A test for basic animation rendering
 *
 * @author kevin
 */
public class AnimationTest2 extends BasicGame {
	/** The animation loaded */
	private Animation correr;
	/** The limited animation loaded */
	private Animation idle;
	/** The manual update animation loaded */
	private Animation aux;
	/** The animation loaded */
	
	/** The container */
	private GameContainer container;
	/** Start limited counter */
	private int start = 5000;
	public boolean idlef = true;
	/**
	 * Create a new image rendering test
	 */
	public AnimationTest2() {
		super("Ne");
	}
	
	/**
	 * @see org.newdawn.slick.BasicGame#init(org.newdawn.slick.GameContainer)
	 */
	public void init(GameContainer container) throws SlickException {
		this.container = container;
		
                SpriteSheet sheetidle = new SpriteSheet("testdata/idle.png", 48, 72);
                SpriteSheet sheetrun = new SpriteSheet("testdata/run.png", 48, 72);
		
		correr = new Animation();
		for (int i=0;i<4;i++) {
			correr.addFrame(sheetrun.getSprite(i,0), 150);
		}
		idle = new Animation();
		for (int i=0;i<4;i++) {
			idle.addFrame(sheetidle.getSprite(i,0), 150);
		}
		aux = idle;
		container.getGraphics().setBackground(new Color(0.4f,0.6f,0.6f));
	}

	/**
	 * @see org.newdawn.slick.BasicGame#render(org.newdawn.slick.GameContainer, org.newdawn.slick.Graphics)
	 */
	public void render(GameContainer container, Graphics g) {
		g.drawString("Space to restart() animation", 100, 50);
		g.drawString("Til Limited animation: "+start, 100, 500);
		g.drawString("Hold 4531 to move the manually animated", 100, 70);
		g.drawString("iddlef:"+idlef, 600, 70);
		
		g.scale(-1,1);
                if(idlef){
                    idle.draw(-100,100);
                }
                else{
                  correr.draw(-100,100);  
                }
                
		//correr.draw(-400,100,36*4,65*4);
		//if (start < 0) {
		//	limited.draw(-400,100,36*4,65*4);
		//}
		//manual.draw(-600,100,36*4,65*4);
		//pingPong.draw(-700,100,36*2,65*2);
	}

	/**
	 * @see org.newdawn.slick.BasicGame#update(org.newdawn.slick.GameContainer, int)
	 */
	public void update(GameContainer container, int delta) {
		
		if (start >= 0) {
			start -= delta;
		}
	}

	/**
	 * Entry point to our test
	 * 
	 * @param argv The arguments to pass into the test
	 */
	public static void main(String[] argv) {
		try {
			AppGameContainer container = new AppGameContainer(new AnimationTest2());
			container.setDisplayMode(800,600,false);
			container.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see org.newdawn.slick.BasicGame#keyPressed(int, char)
	 */
	public void keyPressed(int key, char c) {
		if (key == Input.KEY_ESCAPE) {
			container.exit();
		}
		if (key == Input.KEY_SPACE) {
			//limited.restart();
		}
	}
        public void mousePressed(int button, int x, int y){
            idlef = !idlef;
        }
        public void mouseReleased(int button, int x, int y){
            idlef = !idlef;
        }
        
}
