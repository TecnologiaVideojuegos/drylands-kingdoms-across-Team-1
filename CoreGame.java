package org.newdawn.slick.tests;

import org.lwjgl.input.Mouse;
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
public class CoreGame extends BasicGame {
	
	public CoreGame() {
		super("Dryland: Kingdoms Across");
	}
	private GameContainer container;
        private static final int SCREENRESX=800, SCREENRESY=540, maxFPS=60;
        private static final boolean FULLSCREEN = false,VSYNC=true;
        private Player player;
        //private double avance;
        private Mapa mapa; 
        
        
        
        
	/**
	 * @see org.newdawn.slick.BasicGame#init(org.newdawn.slick.GameContainer)
	 */
	public void init(GameContainer container) throws SlickException {
		this.container = container;
                container.setTargetFrameRate(maxFPS);
                container.setVSync(VSYNC);
                container.setSmoothDeltas(true);
                
                //container.setMouseCursor("C:\\Users\\FairLight\\slick\\testdata\\cursor2.tga", 0, 0);
                
		player = new Player("testdata/testsprites.png",SCREENRESX,SCREENRESY);
                
                container.getGraphics().setBackground(new Color(0.4f,0.6f,0.6f));
                
                mapa = new Mapa("C:\\Users\\FairLight\\drylands-kingdoms-across-Team-1\\MapasProcedurales\\ficheros\\salaabiertax3.tmx","C:\\Users\\FairLight\\drylands-kingdoms-across-Team-1\\MapasProcedurales\\ficheros",SCREENRESX,SCREENRESY);
                
                
               
        }

	/**
	 * @see org.newdawn.slick.BasicGame#render(org.newdawn.slick.GameContainer, org.newdawn.slick.Graphics)
	 */
	public void render(GameContainer container, Graphics g) {
            mapa.render();
            if(!player.isDash()){
                if(player.isCorriendo()){
                    if(player.mirandoD()){
                        player.getAnim("run").draw(player.getX(),player.getY());
                    }
                    else{
                        player.getAnim("runi").draw(player.getX(),player.getY());
                    }

                }
                else{
                    if(player.mirandoD()){
                        player.getAnim("idle").draw(player.getX(),player.getY());
                    }
                    else{
                        player.getAnim("idlei").draw(player.getX(),player.getY());
                    }
                }
            }
            else{
                if(player.mirandoD()){
                        player.getAnim("jump").draw(player.getX(),player.getY());
                    }
                    else{
                        player.getAnim("jumpi").draw(player.getX(),player.getY());
                    }
            }
	}

	/**
	 * @see org.newdawn.slick.BasicGame#update(org.newdawn.slick.GameContainer, int)
	 */
	public void update(GameContainer container, int delta) {
                mapa.actCamara(delta,player);
                player.updPosX();
                player.updPosY();
                player.lowerCDs(delta);
		player.updHitbox();
                
                if(Mouse.isButtonDown(Input.MOUSE_LEFT_BUTTON)){
                    player.setCorriendo();
                }
                else{
                    player.setIdle();
                }
                
                
                player.calcNuevaPos(delta);
                if(!mapa.checkColX(player)){
                        player.updPosX();
                        
                }
                else{
                        player.resetX();
                }
                if(!mapa.checkColY(player)){
                        player.updPosY();
                        
                }
                else{
                    player.resetY();
                }
                
            
                             
            
	}

	/**
	 * Entry point to our test
	 * 
	 * @param argv The arguments to pass into the test
	 */
	public static void main(String[] argv) {
		try {
			AppGameContainer container = new AppGameContainer(new CoreGame());
			container.setDisplayMode(SCREENRESX,SCREENRESY,FULLSCREEN);
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
	}
        /*public  void mouseMove(int button, int x, int y){
            if(button==Input.MOUSE_LEFT_BUTTON){
                player.setCorriendo();
            }
        }
        public  void mouseReleased(int button, int x, int y){
            if(button==Input.MOUSE_LEFT_BUTTON){
                player.setIdle();
            }
        }*/
        /*public  void mouseClicked(int button, int x, int y, int clickCount){
            if(button==Input.MOUSE_RIGHT_BUTTON){
                player.setDash(x, y);
            }
        }*/
        
}