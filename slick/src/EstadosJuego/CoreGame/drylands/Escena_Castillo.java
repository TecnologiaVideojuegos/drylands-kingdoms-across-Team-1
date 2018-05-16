package EstadosJuego.CoreGame.drylands;

import EstadosJuego.Narrativa.Dialogo;
import EstadosJuego.Narrativa.Frase;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.*;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import java.io.EOFException;
import java.io.IOException;
import java.util.ArrayList;

/**
 * A test for basic animation rendering
 *
 * @author FairLight
 */
public class Escena_Castillo extends BasicGameState {

    /**
     * The ID given to this state
     */
    public static final int ID = 10;

    private java.awt.Font UIFont1;
    private org.newdawn.slick.UnicodeFont uniFont;

    private static final int SCREENRESX = 1366, SCREENRESY = 768;
    //private static final boolean FULLSCREEN = false, VSYNC = true;
    private final int TAMX = 48, TAMY = 60;
    private GameContainer container;
    private Player player;
    private Enemigo rey;
    private Enemigo cillop;
    private ArrayList<Enemigo> enemigos;

    private Mapa mapa, mapa2;
    private SpriteSheet spritesplayer, spritescursor;
    
    private Guardado partida;
    private Animation cursor;

    private StateBasedGame game;
    
    private Dialogo dialogo;
    private boolean libre;
    
    private long tiempo;
    private Image intro,mouse;
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

        } catch (SlickException e) {

        }

        cursor = new Animation();
        cursor.addFrame(spritescursor.getSprite(0, 0), 500);
        cursor.addFrame(spritescursor.getSprite(1, 0), 500);

        player = new Player(spritesplayer, 10, new Combo());
        rey = new Enemigo(400, 400, 10000, 300, spritesplayer = new SpriteSheet("ficheros/sprites/reyx3.png", TAMX, TAMY), 100,0);
        cillop = new Enemigo(400, 400, 10000, 300, spritesplayer = new SpriteSheet("ficheros/sprites/cillopx3.png", TAMX, TAMY), 100,0);

        container.getGraphics().setBackground(new Color(0.15f, 0.05f, 0.1f));



        mapa = new Mapa("ficheros/castillo3Dfront.tmx", "ficheros/castilloinfo.tmx", "ficheros", SCREENRESX, SCREENRESY);
        mapa2 = new Mapa("ficheros/castillo3Dback.tmx", "ficheros/castilloinfo.tmx", "ficheros", SCREENRESX, SCREENRESY);

        player.setX(1600);
        player.setY(741);
        rey.setX(1812);
        rey.setY(710);
        rey.setMirandoD(false);
        cillop.setX(1920);
        cillop.setY(785);
        cillop.setMirandoD(false);

        mapa.actCamara(1, player);
        mapa.forzarCentro(player);
        mapa2.actCamara(1, player);
        mapa2.forzarCentro(player);
        partida.setCargada();

        enemigos = new ArrayList<Enemigo>();
        enemigos.add(rey);
        enemigos.add(cillop);

        ArrayList<Frase> listafrases = new ArrayList<Frase>();
        listafrases.add(new Frase("¡Es injusto!", player));
        listafrases.add(new Frase("Se me acusa de algo estúpido", player));
        listafrases.add(new Frase("¡Mientes!", cillop));
        listafrases.add(new Frase("Tu irresponsabilidad nos ha podido salir cara", cillop));
        listafrases.add(new Frase("¡BASTA!", rey));
        listafrases.add(new Frase("¿Por qué te propasaste Solace?", rey));
        listafrases.add(new Frase("Noté algo, tuve un presentimiento", player));
        listafrases.add(new Frase("Vi algo nuevo, parecía un portal", player));
        listafrases.add(new Frase("¿Un presentimiento?", cillop));
        listafrases.add(new Frase("Vamos , por favor...", cillop));
        listafrases.add(new Frase("Las pruebas no están de tu parte, Solace", rey));
        listafrases.add(new Frase("Lo siento", rey));
        listafrases.add(new Frase("¿Acaso no me oís?", player));
        listafrases.add(new Frase("Tenemos un portal ahí abajo", player));
        listafrases.add(new Frase("Mide tus palabras", rey));
        listafrases.add(new Frase("Pones en riesgo nuestra seguridad", rey));
        listafrases.add(new Frase("Eso es lo que realmente importa", rey));
        listafrases.add(new Frase("¿¿Nuestra seguridad??", player));
        listafrases.add(new Frase("¿O su conciencia?", player));
        listafrases.add(new Frase("Es un peligro, nadie sabe adónde lleva", player));
        listafrases.add(new Frase("Están asustados y no piensan claramente", player));
        listafrases.add(new Frase("¡Y usted es un rey horrible!", player));
        listafrases.add(new Frase("Hasta aquí chico", rey));
        listafrases.add(new Frase("Ya hemos tenido suficiente", rey));
        listafrases.add(new Frase("Estás desterrado, abandona el reino", rey));
        listafrases.add(new Frase("Je,je,je", cillop));
        listafrases.add(new Frase("¿QUÈ?", player));
        listafrases.add(new Frase("No me hagas ordenar matarte", rey));
        listafrases.add(new Frase("...", player));

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
        musica = new Music("res/EscenaCastillo.ogg");
    }

    /**
     * @see org.newdawn.slick.BasicGame#render(org.newdawn.slick.GameContainer,
     * org.newdawn.slick.Graphics)
     */
    public void render(GameContainer container, StateBasedGame game, Graphics g) {

        mapa.render();

        ArrayList<Personaje> listaentidadesrender = new ArrayList<>();
        listaentidadesrender.addAll(enemigos);

        listaentidadesrender.add(player);

        Personaje.renderOrdenados(mapa, listaentidadesrender);

        mapa2.renderAt(mapa);

        g.drawString("JugX:" + player.getX(), 100, 100);
        g.drawString("JugY:" + player.getY(), 100, 120);

        g.drawString("MapaX:" + mapa.getOffX(), 100, 140);
        g.drawString("MapaY:" + mapa.getOffY(), 100, 160);

        g.drawString("JugAngulo:" + player.getAngulo(), 100, 180);
        //Si no esta libre muestra el control del intro
        if (!libre) {
            g.setFont(uniFont);
            g.drawString("Pulse Intro para cambiar diálogo", 830, 710);
            intro.draw(1200, 700);
        } else {
            //Si ya esta libre y no han pasado 10 segundos muestra control movimiento
            if (tiempo<10000){
                g.setFont(uniFont);
                g.drawString("Use el ratón para desplazarse", 830, 710);
                mouse.draw(1200, 700);
            }
        
        }
        dialogo.render(g, mapa);

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
            mapa2.actCamara(delta, player);
            //Actualizo las hitbox
            if(mapa.playerEnFinal(player))
                game.enterState(99, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
            player.actHitbox();
            for (Enemigo enemigo : enemigos) {
                enemigo.actHitbox();
            }

            if (Mouse.isButtonDown(Input.MOUSE_LEFT_BUTTON)) {
                player.setCorriendo();
            } else {
                player.setIdle();
            }

            player.calcNuevaPos(delta, mapa);

            //Compruebo colisiones con NPC
            if (!player.retrocediendo()) {
                for (Enemigo enemigo : enemigos) {
                    if (enemigo.collidesCon(player.getHitbox())) {//ha habido colision, determino el contexto

                        break;
                    }
                }
            }

            if (mapa.checkColX(player)) {//comprobamos colisiones muros
                player.resetX();
            }

            if (mapa.checkColY(player)) {
                player.resetY();
            }
            player.updAngulo();

            //Compruebo colisiones con NPC
            if (!player.retrocediendo()) {
                for (Enemigo enemigo : enemigos) {
                    if (enemigo.collidesCon(player.getHitbox())) {//ha habido colision, determino el contexto
                        if (enemigo.checkColX(player)) {//comprobamos colisiones muros
                            player.resetX();
                        }
                        if (enemigo.checkColY(player)) {
                            player.resetY();
                        }

                        break;
                    }
                }
            }

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
