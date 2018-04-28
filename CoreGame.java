package org.newdawn.slick.drylands;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import java.util.ArrayList;
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
        private static final int SCREENRESX=1700, SCREENRESY=1000, maxFPS=60;
        private static final boolean FULLSCREEN = false,VSYNC=true;
        private Player player;
        private ArrayList<Enemigo> enemigos;
        //private double avance;
        private Mapa mapa; 
        private SpriteSheet sprites;
        private final int TAMX=48,TAMY=72;
        
        
        
	/**
	 * @see org.newdawn.slick.BasicGame#init(org.newdawn.slick.GameContainer)
	 */
	public void init(GameContainer container) throws SlickException {
		this.container = container;
                container.setTargetFrameRate(maxFPS);
                container.setVSync(VSYNC);
                container.setSmoothDeltas(true);
                try{
                    sprites=new SpriteSheet("testdata/testsprites.png",TAMX,TAMY);
                }
                catch(SlickException e){}
                
                //container.setMouseCursor("C:\\Users\\FairLight\\slick\\testdata\\cursor2.tga", 0, 0);
                
		player = new Player(sprites,SCREENRESX,SCREENRESY);
                
                container.getGraphics().setBackground(new Color(0.4f,0.6f,0.6f));
                
                mapa = new Mapa("C:\\Users\\FairLight\\drylands-kingdoms-across-Team-1\\MapasProcedurales\\ficheros\\salida.tmx","C:\\Users\\FairLight\\drylands-kingdoms-across-Team-1\\MapasProcedurales\\ficheros",SCREENRESX,SCREENRESY);
                
                enemigos = new ArrayList<Enemigo>();
                
               
        }

	/**
	 * @see org.newdawn.slick.BasicGame#render(org.newdawn.slick.GameContainer, org.newdawn.slick.Graphics)
	 */
	public void render(GameContainer container, Graphics g) {
            mapa.render();
            if(player.dash.estaActiva()){
               if(player.mirandoD()){
                   player.dash.getDAnim().draw(player.getX()+mapa.getOffX(),player.getY()+mapa.getOffY());
               }
               else{
                   player.dash.getIAnim().draw(player.getX()+mapa.getOffX(),player.getY()+mapa.getOffY());
               }
               
            }
            else{
                
                 if(player.isCorriendo()){
                    if(player.mirandoD()){
                        player.getAnim("run").draw(player.getX()+mapa.getOffX(),player.getY()+mapa.getOffY());
                    }
                    else{
                        player.getAnim("runi").draw(player.getX()+mapa.getOffX(),player.getY()+mapa.getOffY());
                    }

                }
                else{
                    if(player.mirandoD()){
                        player.getAnim("idle").draw(player.getX()+mapa.getOffX(),player.getY()+mapa.getOffY());
                    }
                    else{
                        player.getAnim("idlei").draw(player.getX()+mapa.getOffX(),player.getY()+mapa.getOffY());
                    }
                }
            }
            for(Enemigo enemigo : enemigos){
                enemigo.getAnim("run").draw(enemigo.getX()+mapa.getOffX(),enemigo.getY()+mapa.getOffY());
            }
            g.drawString("JugX:"+player.getX(), 100, 100);
            g.drawString("JugY:"+player.getY(), 100, 120);
            g.drawString("MapaX:"+mapa.getOffX(), 100, 140);
            g.drawString("MapaY:"+mapa.getOffY(), 100, 160);
	}

	/**
	 * @see org.newdawn.slick.BasicGame#update(org.newdawn.slick.GameContainer, int)
	 */
	public void update(GameContainer container, int delta) {
                mapa.actCamara(delta,player);
                
                
                player.lowerCDs(delta);
		
                
                if(Mouse.isButtonDown(Input.MOUSE_LEFT_BUTTON)){
                    player.setCorriendo();
                }
                else{
                    player.setIdle();
                }
                if(Keyboard.isKeyDown(Input.KEY_Q)){
                    
                }
                
                player.calcNuevaPos(delta,mapa);
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
            System.out.println(player.dash.getCDRestante());
            if (key == Input.KEY_ESCAPE) {
                container.exit();
            }
            else if (key == Input.KEY_Q&&player.dash.getCDRestante()==0) {

                
                
                int difx = mapa.getAbsMouseX() - (player.getX() );
                int dify = mapa.getAbsMouseY() - (player.getY());
                int difcuadrados = (difx * difx) + (dify * dify);
                double dist = Math.sqrt((double) difcuadrados);
                int maxstep=player.dash.getRango();
                if (dist > (maxstep)) {
                    double incx = (difx * maxstep / dist);

                    int targetx = (int) Math.round(incx);
                   
                    
                    double incy = (dify * maxstep / dist);
                    int targety = (int) Math.round(incy);
                    
                    player.dash.cast(player.getX()+targetx, player.getY()+targety);

                } else {

                    player.dash.cast(player.getX()+difx, player.getY()+dify);
                    //System.out.println("Casteado movimiento a ",difx);
                }
            }
            else if(key == Input.KEY_P){
                enemigos.add(new Enemigo(mapa.getAbsMouseX(),mapa.getAbsMouseY(),1,(double)0.3,sprites));
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
