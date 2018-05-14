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
public class Escena_EntradaMina extends BasicGameState {

    /**
     * The ID given to this state
     */
    public static final int ID = 50;

    private java.awt.Font UIFont1;
    private org.newdawn.slick.UnicodeFont uniFont;

    private static final int SCREENRESX = 1366, SCREENRESY = 768;
    //private static final boolean FULLSCREEN = false, VSYNC = true;
    private final int TAMX = 48, TAMY = 60;
    private GameContainer container;
    private Player player;
    private Enemigo soldado,soldado1,soldado2,soldado3;
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
        //Creamos todos los personajes de la escena
        player = new Player(spritesplayer, 10, new Combo());
        soldado = new Enemigo(400, 400, 10000, 300, spritesplayer = new SpriteSheet("ficheros/sprites/soldado3.png", TAMX, TAMY), 100,0);
        soldado1 = new Enemigo(400, 400, 10000, 300, spritesplayer = new SpriteSheet("ficheros/sprites/soldado3.png", TAMX, TAMY), 100,0);
        soldado2 = new Enemigo(400, 400, 10000, 300, spritesplayer = new SpriteSheet("ficheros/sprites/soldado3.png", TAMX, TAMY), 100,0);
        soldado3 = new Enemigo(400, 400, 10000, 300, spritesplayer = new SpriteSheet("ficheros/sprites/soldado3.png", TAMX, TAMY), 100,0);
        cillop = new Enemigo(400, 400, 10000, 300, spritesplayer = new SpriteSheet("ficheros/sprites/cillopx3.png", TAMX, TAMY), 100,0);

        container.getGraphics().setBackground(new Color(0.15f, 0.05f, 0.1f));



        mapa = new Mapa("ficheros/entradaMina.tmx","ficheros/entradaMina.tmx", "ficheros", SCREENRESX, SCREENRESY);
       
        //Colocamos todos los personajes en el mapa
        player.setX(1600);
        player.setY(741);
        soldado.setX(1860);
        soldado.setY(710);
        soldado1.setX(1860);
        soldado1.setY(770);
        soldado2.setX(1812);
        soldado2.setY(770);
        soldado3.setX(1812);
        soldado3.setY(710);
        //Que esten mirando a la izquierda
        soldado.setMirandoD(false);
        soldado1.setMirandoD(false);
        soldado2.setMirandoD(false);
        soldado3.setMirandoD(false);
        cillop.setX(1720);
        cillop.setY(770);
        cillop.setMirandoD(false);

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
        listafrases.add(new Frase("Así que esta es la famosa mina que nos han encomendado explorar.", player));
        listafrases.add(new Frase("Eso parece, debemos recorrerla entera en busca de la tecnología abandonada.", cillop));
        listafrases.add(new Frase("Hay que evitar que ese legado se pierda por culpa de las guerras.", cillop));
        listafrases.add(new Frase("Bien, comencemos entonces y recordad nadie debe abandonar la mina sin su compañero.", player)); 
        listafrases.add(new Frase("Como ya sabeís estas antiguas minas cambian su forma cada vez que alguien entra en ellas.", player));
        listafrases.add(new Frase("¡En marcha!", soldado2));
        listafrases.add(new Frase("¡Entendido!", soldado1));
   

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
        if (libre) {
            tiempo += delta;
            mapa.actCamara(delta, player);
            //Actualizo las hitbox
            if(mapa.playerEnFinal(player))
                game.enterState(3, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
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
