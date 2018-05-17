package EstadosJuego.CoreGame.drylands;

import EstadosJuego.Narrativa.Dialogo;
import EstadosJuego.Narrativa.Frase;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.*;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import java.io.EOFException;
import java.util.ArrayList;

/**
 * A test for basic animation rendering
 *
 * @author FairLight
 */
public class Escena_Portal extends BasicGameState {

    /**
     * The ID given to this state
     */
    public static final int ID = 60;

    private java.awt.Font UIFont1;
    private org.newdawn.slick.UnicodeFont uniFont;

    private static final int SCREENRESX = 1366, SCREENRESY = 768;
    //private static final boolean FULLSCREEN = false, VSYNC = true;
    private final int TAMX = 48, TAMY = 60;
    private GameContainer container;
    private Player player;
    private Enemigo soldado, soldado1, soldado2, soldado3;
    private Enemigo cillop;
    private ArrayList<Enemigo> enemigos;

    private Mapa mapa, mapa2;
    private SpriteSheet spritesplayer, spritescursor, spritesPortal;

    private Guardado partida;
    private Animation cursor;
    private Animation portal;
    private Animation cuchillo;
    private StateBasedGame game;
    
    private float ultX, ultY;
    
    private Dialogo dialogo;
    private boolean libre, movCillop = false, movSolace = false,mostrarCuchillo=false;

    private long tiempo;
    private Image intro, mouse;

    public int getID() {
        return ID;
    }

    /**
     * @see org.newdawn.slick.BasicGame#init(org.newdawn.slick.GameContainer)
     */
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        this.game = game;
        partida = new Guardado("partida");
        
        intro = new Image("res/teclas/intro.png");
        mouse = new Image("res/teclas/mouse1.png");

        try {
            spritesplayer = new SpriteSheet("ficheros/sprites/personaje.png", TAMX, TAMY);
            spritescursor = new SpriteSheet("ficheros/sprites/spritecursor.png", 15, 15);
            spritesPortal = new SpriteSheet("ficheros/sprites/portalAnimacion.png", 96, 96);

        } catch (SlickException e) {

        }
        portal = new Animation();
        cursor = new Animation();
        cursor.addFrame(spritescursor.getSprite(0, 0), 500);
        cursor.addFrame(spritescursor.getSprite(1, 0), 500);
        for (int i = 0; i < 8; i++) {
            portal.addFrame(spritesPortal.getSprite(i, 0), 150);
        }
        cuchillo = new Animation();
        for (int i = 0; i < 4; i++) {
            cuchillo.addFrame(new SpriteSheet("ficheros/sprites/cillopx3_v2.png", TAMX, TAMY).getSprite(i, 4).getFlippedCopy(true, false), 150);
        }
        //Creamos todos los personajes de la escena
        player = new Player(spritesplayer, 10, new Combo());
        soldado = new Enemigo(400, 400, 10000, 300, spritesplayer = new SpriteSheet("ficheros/sprites/soldado3.png", TAMX, TAMY), 100, 0);
        soldado1 = new Enemigo(400, 400, 10000, 300, spritesplayer = new SpriteSheet("ficheros/sprites/soldado3.png", TAMX, TAMY), 100, 0);
        soldado2 = new Enemigo(400, 400, 10000, 300, spritesplayer = new SpriteSheet("ficheros/sprites/soldado3.png", TAMX, TAMY), 100, 0);
        soldado3 = new Enemigo(400, 400, 10000, 300, spritesplayer = new SpriteSheet("ficheros/sprites/soldado3.png", TAMX, TAMY), 100, 0);
        cillop = new Enemigo(400, 400, 10000, 300, spritesplayer = new SpriteSheet("ficheros/sprites/cillopx3_v2.png", TAMX, TAMY), 100, 0);

        container.getGraphics().setBackground(new Color(0.15f, 0.05f, 0.1f));

        mapa = new Mapa("/ficheros/MapasX3/Salas/SalaBossCerrada.tmx", "/ficheros/MapasX3/Salas/SalaBossCerrada.tmx", "ficheros", SCREENRESX, SCREENRESY);

        //Colocamos todos los personajes en el mapa
        player.setX(700);
        player.setY(120);
        player.setMirandoD(false);
        soldado.setX(360);
        soldado.setY(400);
        soldado1.setX(960);
        soldado1.setY(150);
        soldado2.setX(812);
        soldado2.setY(600);
        soldado3.setX(1012);
        soldado3.setY(350);
        //Que esten mirando a la izquierda
        soldado.setMirandoD(false);

        soldado2.setMirandoD(false);
        cillop.setX(500);
        cillop.setY(300);

        mapa.actCamara(1, player);
        mapa.forzarCentro(player);
        partida.setCargada();

        enemigos = new ArrayList<Enemigo>();
        //Los añadimos al arrayList
        enemigos.add(soldado);
        enemigos.add(soldado1);
        enemigos.add(soldado2);
        enemigos.add(soldado3);
        enemigos.add(cillop);

        ArrayList<Frase> listafrases = new ArrayList<Frase>();
        //Frases de los personajes
        listafrases.add(new Frase("Vaya, mirad esto, parece una especie de portal.", player));
        listafrases.add(new Frase("Me pregunto a donde llevará si lo atravesamos.", player));
        listafrases.add(new Frase("Vámonos, eso no es un portal, es un simple espejismo.", cillop));
        listafrases.add(new Frase("Esto no es una ilusión, es un portal real y debemos averiguar a donde lleva.", player));
        listafrases.add(new Frase("No creo que introducir un cuchillo al portal sea una gran idea.", player));
        listafrases.add(new Frase("Vayámonos e informemos de los hallazgos encontrados hoy aquí.", player));
        try {
            UIFont1 = java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT,
                    org.newdawn.slick.util.ResourceLoader.getResourceAsStream("res/Sangoku4.ttf"));
            UIFont1 = UIFont1.deriveFont(java.awt.Font.PLAIN, 24.f); //You can change "PLAIN" to "BOLD" or "ITALIC"... and 16.f is the size of your font
            uniFont = new org.newdawn.slick.UnicodeFont(UIFont1);
            uniFont.addAsciiGlyphs();
            uniFont.getEffects().add(new ColorEffect(java.awt.Color.white)); //You can change your color here, but you can also change it in the render{ ... }
            uniFont.addAsciiGlyphs();
            uniFont.loadGlyphs();
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
        dialogo = new Dialogo(listafrases, uniFont, new Sound("res/sonido1.wav"));
        libre = false;
    }

    /**
     * @see org.newdawn.slick.BasicGame#render(org.newdawn.slick.GameContainer,
     * org.newdawn.slick.Graphics)
     */
    public void render(GameContainer container, StateBasedGame game, Graphics g) {
        
        mapa.render();
        portal.draw(637, 48);
        ArrayList<Personaje> listaentidadesrender = new ArrayList<>();
        listaentidadesrender.addAll(enemigos);

        listaentidadesrender.add(player);

        Personaje.renderOrdenados(mapa, listaentidadesrender);

       
        if (dialogo.getiFrase() > 3 || mostrarCuchillo){
            
            cuchillo.draw(ultX+34,ultY+9);
            cillop.setX(2000);
            cillop.updPosX();
        }
        dialogo.render(g, mapa);
        
    }

    /**
     * @see org.newdawn.slick.BasicGame#update(org.newdawn.slick.GameContainer,
     * int)
     */
    public void update(GameContainer container, StateBasedGame game, int delta) {
        partida.guardarEstado(this.getID());
        tiempo += delta;
        if (tiempo % 10 == 0 && tiempo < 2400 && movCillop) {
            cillop.setCorriendo(true);
            cillop.addX(8);

            cillop.updPosX();

        }
        if (tiempo % 10 == 0 && tiempo > 2400 && movCillop) {
            cillop.addY(-8);
            cillop.updPosY();
            cillop.setCorriendo(true);
            cillop.setMirandoD(false);
            if (tiempo > 4000) {
                cillop.setCorriendo(false);
                ultX=cillop.getX();
                ultY=cillop.getY();
                movCillop = false;
                movSolace = true;
                mostrarCuchillo=true;
                tiempo = 0;
            }
        }
        if (dialogo.getiFrase() == 2) {
            movCillop = true;
            tiempo = 0;
        }
        if (dialogo.getiFrase() == 4) {
            player.setMirandoD(true);
        }
        if (tiempo % 10 == 0 && movSolace) {
            player.setCorriendo(true);
            player.addX(-8);
            player.updPosX();
            if (tiempo > 500) {
                player.setCorriendo(false);
                movSolace = false;
                tiempo = 0;
            }
        }
        if (dialogo.isTerminado()) {
            game.enterState(51, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
        } 
       
            dialogo.update(delta);
        

    }

    /**
     * @see org.newdawn.slick.BasicGame#keyPressed(int, char)
     */
    public void keyPressed(int key, char c) {

        if (key == Input.KEY_ESCAPE) {

            game.enterState(1, new FadeOutTransition(Color.black), new FadeInTransition(Color.blue));

        } else if (key == Input.KEY_RETURN) {
            try {
                dialogo.go();
            } catch (EOFException e) {
                System.out.println("El dialogo ha terminado");
                libre = true;
            }

        } else if (key == Input.KEY_K) {
            try {
                dialogo.kill();
            } catch (EOFException e) {
                System.out.println("El dialogo ha terminado");
                libre = true;
            }

        }

    }

}
