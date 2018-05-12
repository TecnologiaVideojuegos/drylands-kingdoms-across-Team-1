package EstadosJuego.CoreGame.drylands;

import EstadosJuego.Narrativa.Pensamientos;
import MapasProc.ProceduralesMain;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.*;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import java.io.IOException;
import java.util.ArrayList;


/**
 * A test for basic animation rendering
 *
 * @author kevin
 */
public class CoreGame extends BasicGameState {

    /** The ID given to this state */
    public static final int ID = 3;
    private Combo combo;
    private static final int SCREENRESX = 1366, SCREENRESY = 768, maxFPS = 60;
    private static final boolean FULLSCREEN = false, VSYNC = true;
    private final int TAMX = 48, TAMY = 60;
    private GameContainer container;
    private Player player;
    private ArrayList<Enemigo> enemigos, enemigosMuertos, limpiaEnemigos;
    //private double avance;
    private Mapa mapa;
    private SpriteSheet spritesplayer,spritescursor;
    private boolean combate;
    private Guardado partida;
    private Animation cursor;
    private Image uidash,uiblock,uibar,uihp,uicd;
    private StateBasedGame game;
    private Pensamientos comentarios;
    private java.awt.Font UIFont1;
    private org.newdawn.slick.UnicodeFont uniFont;

    /*public CoreGame() {
        super("Dryland: Kingdoms Across");
    }*/

    public int getID() {
        return ID;
    }

    /*public static void main(String[] argv) {
        try {
            AppGameContainer container = new AppGameContainer(new CoreGame());
            container.setDisplayMode(SCREENRESX, SCREENRESY, FULLSCREEN);
            container.start();
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }/*

    /**
     * @see org.newdawn.slick.BasicGame#init(org.newdawn.slick.GameContainer)
     */
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        this.game = game;
        partida = new Guardado("partida");
        combo=new Combo();
        container.setTargetFrameRate(maxFPS);
        container.setVSync(VSYNC);
        container.setSmoothDeltas(true);
        try {
            spritesplayer = new SpriteSheet("ficheros/sprites/testsprites.png", TAMX, TAMY);
            spritescursor = new SpriteSheet("ficheros/sprites/spritecursor.png",15,15);
        } catch (SlickException e) {

        }

        cursor=new Animation();
        cursor.addFrame(spritescursor.getSprite(0,0),500);
        cursor.addFrame(spritescursor.getSprite(1,0),500);

        combate = false;


        player = new Player(spritesplayer,10,combo);

        container.getGraphics().setBackground(new Color(0.15f, 0.05f, 0.1f));
        if (partida.esNueva()) {
            Punto inicio;

            ProceduralesMain.generarMapa(8, 10, 14,"acto1","acto1info");

            mapa = new Mapa("ficheros/acto1.tmx","ficheros/acto1info.tmx", "ficheros", SCREENRESX, SCREENRESY);
            inicio = mapa.getInicio();
            player.setX(inicio.getX());
            player.setY(inicio.getY());
            mapa.actCamara(1,player);
            mapa.forzarCentro(player);
            partida.setCargada();
        } else {
            try{
                mapa = new Mapa("ficheros/acto1.tmx","ficheros/acto1info.tmx", "ficheros", SCREENRESX, SCREENRESY);
                partida.cargarMapa(mapa);
                partida.cargarPlayer(player);
            }
            catch (Exception e){
                System.out.println("Error al cargar el mapa, se va a     reiniciar la partida");
                e.printStackTrace();
                partida.resetPartida();
                System.exit(1);
            }


        }


        enemigos = new ArrayList<Enemigo>();
        enemigosMuertos = new ArrayList<Enemigo>();
        limpiaEnemigos = new ArrayList<Enemigo>();


        uihp = new Image("ficheros/GUI/vida.png");
        uidash = new Image("ficheros/GUI/dash.png");
        uiblock = new Image("ficheros/GUI/block.png");
        uibar = new Image("ficheros/GUI/barra.png");
        uicd = new Image("ficheros/GUI/sombra.png");

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

        comentarios=new Pensamientos(player,uniFont,new Sound("res/sonido1.wav"));
        comentarios.meterFrase("A ver si encuentro algo interesante");
        comentarios.meterFrase("Demasiados enemigos por aquí");
        comentarios.meterFrase("Estas ruinas cada vez son diferentes");
        comentarios.meterFrase("No escucho a mis compañeros...");
        comentarios.meterFrase("Oigo viento a lo lejos");
        comentarios.meterFrase("Fallar ataques me entorpece");
        comentarios.meterFrase("¿Habrá más reinos en algún lugar?");
        comentarios.meterFrase("Qué miedo dan las Drylands");
        comentarios.meterFraseHP4("¡A tope de energía!");
        comentarios.meterFraseHP4("¡Vamos!");
        comentarios.meterFraseHP3("He sufrido peores heridas");
        comentarios.meterFraseHP3("Solo unos rasguños");
        comentarios.meterFraseHP2("Voy a necesitar uan cura");
        comentarios.meterFraseHP2("Debería descansar");
        comentarios.meterFraseHP1("Ayuda, por favor...");
        comentarios.meterFraseHP1("Estoy muy débil...");

    }

    /**
     * @see org.newdawn.slick.BasicGame#render(org.newdawn.slick.GameContainer, org.newdawn.slick.Graphics)
     */
    public void render(GameContainer container,StateBasedGame game, Graphics g) {

        if (Keyboard.isKeyDown(Input.KEY_TAB)) {
            g.scale(mapa.getEscalaTab(), mapa.getEscalaTab());
            mapa.renderTab();
            g.resetTransform();
            cursor.draw(player.getX()*mapa.getEscalaTab(),player.getY()*mapa.getEscalaTab());
        } else {

            mapa.render();

            if (player.dash.estaActiva()) {
                g.drawGradientLine(player.dash.getStartX() + mapa.getOffX(), player.dash.getStartY() + mapa.getOffY(), (float) 1.0, (float) 1.0, (float) 1.0, (float) 0.0, player.getX() + mapa.getOffX() + player.TAMX / 2, player.getY() + mapa.getOffY() + player.TAMY / 2, (float) 1.0, (float) 1.0, (float) 1.0, (float) 1.0);
                g.drawGradientLine(player.dash.getStartX() + mapa.getOffX() + 1, player.dash.getStartY() + mapa.getOffY(), (float) 1.0, (float) 1.0, (float) 1.0, (float) 0.0, player.getX() + mapa.getOffX() + player.TAMX / 2, player.getY() + mapa.getOffY() + player.TAMY / 2, (float) 1.0, (float) 1.0, (float) 1.0, (float) 1.0);
                g.drawGradientLine(player.dash.getStartX() + mapa.getOffX() - 1, player.dash.getStartY() + mapa.getOffY(), (float) 1.0, (float) 1.0, (float) 1.0, (float) 0.0, player.getX() + mapa.getOffX() + player.TAMX / 2, player.getY() + mapa.getOffY() + player.TAMY / 2, (float) 1.0, (float) 1.0, (float) 1.0, (float) 1.0);
                g.drawGradientLine(player.dash.getStartX() + mapa.getOffX(), player.dash.getStartY() + mapa.getOffY() + 1, (float) 1.0, (float) 1.0, (float) 1.0, (float) 0.0, player.getX() + mapa.getOffX() + player.TAMX / 2, player.getY() + mapa.getOffY() + player.TAMY / 2, (float) 1.0, (float) 1.0, (float) 1.0, (float) 1.0);
                g.drawGradientLine(player.dash.getStartX() + mapa.getOffX(), player.dash.getStartY() + mapa.getOffY() - 1, (float) 1.0, (float) 1.0, (float) 1.0, (float) 0.0, player.getX() + mapa.getOffX() + player.TAMX / 2, player.getY() + mapa.getOffY() + player.TAMY / 2, (float) 1.0, (float) 1.0, (float) 1.0, (float) 1.0);
                g.drawGradientLine(player.dash.getStartX() + mapa.getOffX() + 1, player.dash.getStartY() + mapa.getOffY() + 1, (float) 1.0, (float) 1.0, (float) 1.0, (float) 0.0, player.getX() + mapa.getOffX() + player.TAMX / 2, player.getY() + mapa.getOffY() + player.TAMY / 2, (float) 1.0, (float) 1.0, (float) 1.0, (float) 1.0);


            }
            ArrayList<Personaje> listaentidadesrender = new ArrayList<>();
            listaentidadesrender.addAll(enemigos);

            listaentidadesrender.add(player);

            Personaje.renderOrdenados(mapa,listaentidadesrender);
            for (Enemigo enemigomuerto : enemigosMuertos) {
                enemigomuerto.getAnim("muerto").draw(enemigomuerto.getX() + mapa.getOffX(), enemigomuerto.getY() + mapa.getOffY());
            }
            g.drawString("JugX:" + player.getX(), 100, 100);
            g.drawString("JugY:" + player.getY(), 100, 120);

            g.drawString("MapaX:" + mapa.getOffX(), 100, 140);
            g.drawString("MapaY:" + mapa.getOffY(), 100, 160);

            g.drawString("JugAngulo:" + player.getAngulo(), 100, 180);
            g.drawString("Combo:" + combo.getCombo(), 100, 200);

            if(!combate){
                comentarios.render(g,mapa);
            }





            uibar.draw(894,674);
            uihp.getScaledCopy((int)(382*(float)player.getVida()/player.getVidamax()),42).draw(927,684);
            if(player.dash.estaActiva()){
                uidash.draw(33,674,new Color(1f, 0.8f, 0.8f));
            }
            else{
                uidash.draw(33,674);
                uicd.getScaledCopy(45,(int)(42*(float)player.dash.getCDRestante()/player.dash.getCdmax())).draw(43,683+42-(int)(42*(float)player.dash.getCDRestante()/player.dash.getCdmax()));
            }
            if(player.block.estaActiva()){
                uiblock.draw(109,674,new Color(1f, 0.8f, 0.8f));

            }
            else{
                uiblock.draw(109,674);
                uicd.getScaledCopy(45,(int)(42*(float)player.block.getCDRestante()/player.block.getCdmax())).draw(119,683+42-(int)(42*(float)player.block.getCDRestante()/player.block.getCdmax()));
            }


        }
    }

    /**
     * @see org.newdawn.slick.BasicGame#update(org.newdawn.slick.GameContainer, int)
     */
    public void update(GameContainer container, StateBasedGame game,int delta) {

        if(!combate){
            comentarios.update(delta);
        }



        mapa.actCamara(delta, player);
        //Actualizo las hitbox
        player.actHitbox();
        for (Enemigo enemigo : enemigos) {
            enemigo.actHitbox();
        }


        player.lowerCDs(delta);
        combo.contarMs(delta);

        if (Keyboard.isKeyDown(Input.KEY_W) && player.block.getCDRestante() == 0) {
            player.block.block(delta);
        }

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
        player.updAngulo();

        //Compruebo colisiones con enemigos
        if (!player.retrocediendo())
            for (Enemigo enemigo : enemigos) {
                if (enemigo.collidesCon(player.getHitbox())) {//ha habido colision, determino el contexto
                    if (!player.estaAtacando()) {//si el jugador no ataca, sufredaño
                        player.rcvDmg(enemigo.getDmg());
                        player.retroceder(player.getAngulo()+180);
                    } else { //si ataca compruebo si el enemigo no esta atacando
                        if (!enemigo.estaAtacando()) {
                            player.dash.landed();
                            combo.moreCombo();
                            enemigo.rcvDmg(1);
                        } else {
                        }


                    }


                    break;
                }
            }

        player.updPosX();
        player.updPosY();

        //Genero enemigos en una nueva sala
        if (mapa.playerEnSala() && mapa.playerEnSalaNueva() && mapa.camaraFijada()) {

            if (mapa.playerEnInicio(player)) {

            } else if (mapa.playerEnFinal(player)) {

            } else {
                Circle circulo = new Circle(player.posx + player.TAMX / 2, player.posy + player.TAMY / 2, player.TAMY * 3);

                for (int i = 0; i < 8; i++) {
                    Punto punto = mapa.getRandinSala();
                    if (!circulo.contains(punto.getX(), punto.getY()))
                        enemigos.add(new Enemigo((int) punto.getX(), (int) punto.getY(), 1, (double) 0.3, spritesplayer, 1));
                    else i--;
                }
            }


            mapa.actListaCentros();
        }
        if (enemigos.size() > 0) {
            if (!combate) {
                combate = true;
            }
        } else {
            if (combate && enemigosMuertos.size() == 0) {

                System.out.println("Sala limpiada, aqui deberia guardar");


                combate = false;
                try {
                    partida.guardarMapa(mapa);
                    partida.guardarPlayer(player);
                } catch (IOException error) {
                    System.out.println("Error al guardar");
                }

            }
        }


        //Compruebo vida de los personajes

        for (Enemigo enemigo : enemigos) {

            if (enemigo.getVida() <= 0) {

                enemigosMuertos.add(enemigo);
            }

        }
        if (enemigosMuertos.size() > 0)
            enemigos.removeAll(enemigosMuertos);//Lo quito aqui porque en el bucle da error


        //Limpio ahora el array de enemigos muertos
        for (Enemigo enemigomuerto : enemigosMuertos) {
            if (enemigomuerto.getAnim("muerto").getFrame() == 6) {
                limpiaEnemigos.add(enemigomuerto);
            }

        }
        if (limpiaEnemigos.size() > 0) {
            enemigosMuertos.removeAll(limpiaEnemigos);//Lo quito aqui porque en el bucle da error
            limpiaEnemigos.clear();
        }
    }

    /**
     * @see org.newdawn.slick.BasicGame#keyPressed(int, char)
     */
    public void keyPressed(int key, char c) {

        if (key == Input.KEY_ESCAPE) {


            game.enterState(1, new FadeOutTransition(Color.black), new FadeInTransition(Color.blue));

        } else if (key == Input.KEY_Q && player.dash.getCDRestante() == 0 && !player.dash.estaActiva()) {


            float difx = mapa.getAbsMouseX() - (player.getX() + player.TAMX / 2);
            float dify = mapa.getAbsMouseY() - (player.getY() + player.TAMY / 2);
            float difcuadrados = (difx * difx) + (dify * dify);
            float dist = (float) Math.sqrt(difcuadrados);
            int maxstep = player.dash.getRango();
            if (dist > (maxstep)) {
                float incx = (difx * maxstep / dist);

                int targetx = (int) Math.round(incx);


                float incy = (dify * maxstep / dist);
                int targety = (int) Math.round(incy);

                player.dash.cast(player, player.getX() + player.TAMX / 2 + targetx, player.getY() + player.TAMY / 2 + targety);

            } else {

                player.dash.cast(player, player.getX() + player.TAMX / 2 + (int) difx, player.getY() + player.TAMY / 2 + (int) dify);
                //System.out.println("Casteado movimiento a ",difx);
            }
        } else if (key == Input.KEY_P) {
            enemigos.add(new Enemigo((int) mapa.getAbsMouseX(), (int) mapa.getAbsMouseY(), 1, (double) 0.3, spritesplayer, 1));
        }

    }

    public void keyReleased(int key, char c) {
        if (key == Input.KEY_W && player.block.estaActiva()) {
            player.block.terminar();
            player.block.contarCD();

        }
    }

}
