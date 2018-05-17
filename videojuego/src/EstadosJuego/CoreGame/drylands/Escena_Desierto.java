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
public class Escena_Desierto extends BasicGameState {

    /**
     * The ID given to this state
     */
    public static final int ID = 52;

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

    private Mapa mapa;
    private SpriteSheet spritesplayer, spritescursor, spritesTormenta;

    private Guardado partida;
    private Animation cursor;
    private Animation tormenta;

    private StateBasedGame game;

    private Dialogo dialogo;
    private boolean libre;

    private long tiempo;
    private Image intro, mouse;
    private Music musica;

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
            spritesTormenta = new SpriteSheet("ficheros/sprites/tormentafinal.png", 1365, 768);

        } catch (SlickException e) {

        }
        tormenta = new Animation();
        for (int j = 0; j < 8; j++) {
            for (int i = 0; i < 5; i++) {
                tormenta.addFrame(spritesTormenta.getSprite(i, j), 100);
            }
        }
        tormenta.setPingPong(true);

        cursor = new Animation();
        cursor.addFrame(spritescursor.getSprite(0, 0), 500);
        cursor.addFrame(spritescursor.getSprite(1, 0), 500);
        //Creamos todos los personajes de la escena
        player = new Player(spritesplayer, 10, new Combo());

        container.getGraphics().setBackground(new Color(0.15f, 0.05f, 0.1f));

        mapa = new Mapa("ficheros/piramide.tmx", "ficheros/piramide.tmx", "ficheros", SCREENRESX, SCREENRESY);

        //Colocamos todos los personajes en el mapa
        player.setX(1600);
        player.setY(2000);

        player.setVel(0.2f);

        mapa.actCamara(1, player);
        mapa.forzarCentro(player);
        partida.setCargada();

        ArrayList<Frase> listafrases = new ArrayList<Frase>();
        //Frases de los personajes
        listafrases.add(new Frase("No quiero que este desierto sea mi tumba", player));

        try {
            UIFont1 = java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT,
                    org.newdawn.slick.util.ResourceLoader.getResourceAsStream("res/Sangoku4.ttf"));
            UIFont1 = UIFont1.deriveFont(java.awt.Font.PLAIN, 24.f); //You can change "PLAIN" to "BOLD" or "ITALIC"... and 16.f is the size of your font
            uniFont = new org.newdawn.slick.UnicodeFont(UIFont1);
            uniFont.addAsciiGlyphs();
            uniFont.getEffects().add(new ColorEffect(java.awt.Color.black)); //You can change your color here, but you can also change it in the render{ ... }
            uniFont.addAsciiGlyphs();
            uniFont.loadGlyphs();
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
        dialogo = new Dialogo(listafrases, uniFont, new Sound("res/sonido1.wav"));
        libre = false;
        musica = new Music("res/Desierto.ogg");
    }

    /**
     * @see org.newdawn.slick.BasicGame#render(org.newdawn.slick.GameContainer,
     * org.newdawn.slick.Graphics)
     */
    public void render(GameContainer container, StateBasedGame game, Graphics g) {

        mapa.render();

        ArrayList<Personaje> listaentidadesrender = new ArrayList<>();

        listaentidadesrender.add(player);

        Personaje.renderOrdenados(mapa, listaentidadesrender);

        
        //Si no esta libre muestra el control del intro
        if (!libre) {
            g.setFont(uniFont);
            g.drawString("Pulse Intro para cambiar diálogo", 830, 710);
            intro.draw(1200, 700);
        } else {
            //Si ya esta libre y no han pasado 10 segundos muestra control movimiento
            if (tiempo < 10000) {
                g.setFont(uniFont);
                g.drawString("Use el ratón para desplazarse", 830, 710);
                mouse.draw(1200, 700);
            }

        }
        dialogo.render(g, mapa);
        tormenta.draw(0, 0);
    }

    /**
     * @see org.newdawn.slick.BasicGame#update(org.newdawn.slick.GameContainer,
     * int)
     */
    public void update(GameContainer container, StateBasedGame game, int delta) {
        if(!musica.playing())
            musica.loop();
        partida.guardarEstado(this.getID());
        if (libre) {
            tiempo += delta;
            mapa.actCamara(delta, player);
            //Actualizo las hitbox
            if (mapa.playerEnFinal(player)) {
                game.enterState(75, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
            }
            player.actHitbox();

            if (Mouse.isButtonDown(Input.MOUSE_LEFT_BUTTON)) {
                player.setCorriendo();
            } else {
                player.setIdle();
            }

            player.calcNuevaPos(delta, mapa);

            //Compruebo colisiones con NPC
            if (mapa.checkColX(player)) {//comprobamos colisiones muros
                player.resetX();
            }

            if (mapa.checkColY(player)) {
                player.resetY();
            }
            player.updAngulo();

            //Compruebo colisiones con NPC
            player.updPosX();
            player.updPosY();
        } else {
            dialogo.update(delta);
        }

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
