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
    private static final int SCREENRESX = 1366, SCREENRESY = 768, maxFPS = 60;
    private static final boolean FULLSCREEN = false, VSYNC = true;
    private Player player;
    private ArrayList<Enemigo> enemigos,enemigosMuertos,limpiaEnemigos;
    //private double avance;
    private Mapa mapa;
    private SpriteSheet sprites;
    private final int TAMX = 48, TAMY = 72;


    /**
     * @see org.newdawn.slick.BasicGame#init(org.newdawn.slick.GameContainer)
     */
    public void init(GameContainer container) throws SlickException {
        this.container = container;
        container.setTargetFrameRate(maxFPS);
        container.setVSync(VSYNC);
        container.setSmoothDeltas(true);
        try {
            sprites = new SpriteSheet("testdata/testsprites.png", TAMX, TAMY);
        } catch (SlickException e) {
        }

        //container.setMouseCursor("C:\\Users\\FairLight\\slick\\testdata\\cursor2.tga", 0, 0);

        player = new Player(sprites);

        container.getGraphics().setBackground(new Color(0.15f, 0.05f, 0.1f));

        mapa = new Mapa("C:\\Users\\FairLight\\drylands-kingdoms-across-Team-1\\MapasProcedurales\\ficheros\\salida.tmx", "C:\\Users\\FairLight\\drylands-kingdoms-across-Team-1\\MapasProcedurales\\ficheros", SCREENRESX, SCREENRESY);

        enemigos = new ArrayList<Enemigo>();
        enemigosMuertos = new ArrayList<Enemigo>();
        limpiaEnemigos = new ArrayList<Enemigo>();


    }

    /**
     * @see org.newdawn.slick.BasicGame#render(org.newdawn.slick.GameContainer, org.newdawn.slick.Graphics)
     */
    public void render(GameContainer container, Graphics g) {
        mapa.render();

        player.getAnim().draw(player.getX() + mapa.getOffX(), player.getY() + mapa.getOffY());
        for (Enemigo enemigo : enemigos) {
            enemigo.getAnim("run").draw(enemigo.getX() + mapa.getOffX(), enemigo.getY() + mapa.getOffY());
        }
        for (Enemigo enemigomuerto : enemigosMuertos){
            enemigomuerto.getAnim("muerto").draw(enemigomuerto.getX() + mapa.getOffX(), enemigomuerto.getY() + mapa.getOffY());
        }
        g.drawString("JugX:" + player.getX(), 100, 100);
        g.drawString("JugY:" + player.getY(), 100, 120);
        g.drawString("MapaX:" + mapa.getOffX(), 100, 140);
        g.drawString("MapaY:" + mapa.getOffY(), 100, 160);
    }

    /**
     * @see org.newdawn.slick.BasicGame#update(org.newdawn.slick.GameContainer, int)
     */
    public void update(GameContainer container, int delta) {

        mapa.actCamara(delta, player);
        //Actualizo las hitbox
        player.actHitbox();
        for (Enemigo enemigo: enemigos) {
            enemigo.actHitbox();
        }



        player.lowerCDs(delta);


        if (Mouse.isButtonDown(Input.MOUSE_LEFT_BUTTON)) {
            player.setCorriendo();
        } else {
            player.setIdle();
        }

        player.calcNuevaPos(delta, mapa);

        if (mapa.checkColX(player)) {//comprobamos colisiones muros
            player.resetX();
        }

        if (mapa.checkColY(player)) {
            player.resetY();
        }
        
        //Compruebo colisiones con enemigos
        if(!player.retrocediendo())
            for (Enemigo enemigo: enemigos) {
                if(enemigo.collidesCon(player.getHitbox())){//ha habido colision, determino el contexto
                    if(!player.atacando()){//si el jugador no ataca, daña al jugador
                        player.dmg(1);
                        player.retroceder(80);
                    }
                    else{
                        enemigo.dmg(1);
                        System.out.println("Colisiono y quito un punto");

                    }


                    break;
                }
            }
        
        player.updPosX();
        player.updPosY();

        //Compruebo vida de los personajes

        for (Enemigo enemigo: enemigos) {

            if(enemigo.getVida()<=0){

               enemigosMuertos.add(enemigo);
            }

        }
        if(enemigosMuertos.size()>0)
            enemigos.removeAll(enemigosMuertos);//Lo quito aqui porque en el bucle da error


        //Limpio ahora el array de enemigos muertos
        for (Enemigo enemigomuerto: enemigosMuertos) {
            if(enemigomuerto.getAnim("muerto").getFrame()==6){
                limpiaEnemigos.add(enemigomuerto);
            }

        }
        if(limpiaEnemigos.size()>0) {
            enemigosMuertos.removeAll(limpiaEnemigos);//Lo quito aqui porque en el bucle da error
            limpiaEnemigos.clear();
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
            container.setDisplayMode(SCREENRESX, SCREENRESY, FULLSCREEN);
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
        } else if (key == Input.KEY_Q && player.dash.getCDRestante() == 0) {


            float difx = mapa.getAbsMouseX() - (player.getX() + player.TAMX / 2);
            float dify = mapa.getAbsMouseY() - (player.getY() + player.TAMY / 2);
            float difcuadrados = (difx * difx) + (dify * dify);
            float dist = (float)Math.sqrt(difcuadrados);
            int maxstep = player.dash.getRango();
            if (dist > (maxstep)) {
                float incx = (difx * maxstep / dist);

                int targetx = (int) Math.round(incx);


                float incy = (dify * maxstep / dist);
                int targety = (int) Math.round(incy);

                player.dash.cast(player, player.getX() + player.TAMX / 2 + targetx, player.getY() + player.TAMY / 2 + targety);

            } else {

                player.dash.cast(player, player.getX() + player.TAMX / 2 + (int)difx, player.getY() + player.TAMY / 2 + (int)dify);
                //System.out.println("Casteado movimiento a ",difx);
            }
        } else if (key == Input.KEY_P) {
            enemigos.add(new Enemigo((int)mapa.getAbsMouseX(), (int)mapa.getAbsMouseY(), 1, (double) 0.3, sprites));
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
