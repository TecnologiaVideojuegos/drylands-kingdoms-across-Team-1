package CoreGame.drylands;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Circle;

import java.io.IOException;
import java.util.ArrayList;
import MapasProc.ProceduralesMain;


/**
 * A test for basic animation rendering
 *
 * @author kevin
 */
public class CoreGame extends BasicGame {

    private static final int SCREENRESX = 1366, SCREENRESY = 768, maxFPS = 60;
    private static final boolean FULLSCREEN = false, VSYNC = true;
    private final int TAMX = 48, TAMY = 60;
    private GameContainer container;
    private Player player;
    private ArrayList<Enemigo> enemigos, enemigosMuertos, limpiaEnemigos;
    //private double avance;
    private Mapa mapa;
    private SpriteSheet sprites;
    private boolean combate;
    private Guardado partida;
    public CoreGame() {
        super("Dryland: Kingdoms Across");
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
     * @see org.newdawn.slick.BasicGame#init(org.newdawn.slick.GameContainer)
     */
    public void init(GameContainer container) throws SlickException {
        this.container = container;
        partida = new Guardado("partidaprueba");
        container.setTargetFrameRate(maxFPS);
        container.setVSync(VSYNC);
        container.setSmoothDeltas(true);
        try {
            sprites = new SpriteSheet("ficheros/sprites/testsprites.png", TAMX, TAMY);
        } catch (SlickException e) {

        }

        combate = false;


        player = new Player(sprites);

        container.getGraphics().setBackground(new Color(0.15f, 0.05f, 0.1f));
        if(partida.esNueva()){
            Punto inicio;

            ProceduralesMain.generarMapa(8,10,14);

            mapa = new Mapa("ficheros/salida.tmx", "ficheros", SCREENRESX, SCREENRESY);
            inicio=mapa.getInicio();
            player.setX(inicio.getX());
            player.setY(inicio.getY());
            mapa.setOffX(-inicio.getX()+SCREENRESX/2);
            mapa.setOffY(-inicio.getY()+SCREENRESY/2);
        }
        else{
            mapa = new Mapa("ficheros/salida.tmx", "ficheros", SCREENRESX, SCREENRESY);
            partida.cargarMapa(mapa);
            partida.cargarPlayer(player);

        }


        enemigos = new ArrayList<Enemigo>();
        enemigosMuertos = new ArrayList<Enemigo>();
        limpiaEnemigos = new ArrayList<Enemigo>();


        //Despues de iniciar todo por defecto intento cargar la partida si existe



    }

    /**
     * @see org.newdawn.slick.BasicGame#render(org.newdawn.slick.GameContainer, org.newdawn.slick.Graphics)
     */
    public void render(GameContainer container, Graphics g) {

        mapa.render();

        if(player.dash.estaActiva()){
            g.drawGradientLine(player.dash.getStartX()+mapa.getOffX(),player.dash.getStartY()+mapa.getOffY(),(float)1.0,(float)1.0,(float)1.0,(float)0.0,player.getX()+mapa.getOffX()+player.TAMX/2,player.getY()+mapa.getOffY()+player.TAMY/2,(float)1.0,(float)1.0,(float)1.0,(float)1.0);
            g.drawGradientLine(player.dash.getStartX()+mapa.getOffX()+1,player.dash.getStartY()+mapa.getOffY(),(float)1.0,(float)1.0,(float)1.0,(float)0.0,player.getX()+mapa.getOffX()+player.TAMX/2,player.getY()+mapa.getOffY()+player.TAMY/2,(float)1.0,(float)1.0,(float)1.0,(float)1.0);
            g.drawGradientLine(player.dash.getStartX()+mapa.getOffX()-1,player.dash.getStartY()+mapa.getOffY(),(float)1.0,(float)1.0,(float)1.0,(float)0.0,player.getX()+mapa.getOffX()+player.TAMX/2,player.getY()+mapa.getOffY()+player.TAMY/2,(float)1.0,(float)1.0,(float)1.0,(float)1.0);
            g.drawGradientLine(player.dash.getStartX()+mapa.getOffX(),player.dash.getStartY()+mapa.getOffY()+1,(float)1.0,(float)1.0,(float)1.0,(float)0.0,player.getX()+mapa.getOffX()+player.TAMX/2,player.getY()+mapa.getOffY()+player.TAMY/2,(float)1.0,(float)1.0,(float)1.0,(float)1.0);
            g.drawGradientLine(player.dash.getStartX()+mapa.getOffX(),player.dash.getStartY()+mapa.getOffY()-1,(float)1.0,(float)1.0,(float)1.0,(float)0.0,player.getX()+mapa.getOffX()+player.TAMX/2,player.getY()+mapa.getOffY()+player.TAMY/2,(float)1.0,(float)1.0,(float)1.0,(float)1.0);
            g.drawGradientLine(player.dash.getStartX()+mapa.getOffX()+1,player.dash.getStartY()+mapa.getOffY()+1,(float)1.0,(float)1.0,(float)1.0,(float)0.0,player.getX()+mapa.getOffX()+player.TAMX/2,player.getY()+mapa.getOffY()+player.TAMY/2,(float)1.0,(float)1.0,(float)1.0,(float)1.0);




        }


        player.getAnim().draw(player.getX() + mapa.getOffX(), player.getY() + mapa.getOffY());
        for (Enemigo enemigo : enemigos) {
            enemigo.getAnim("run").draw(enemigo.getX() + mapa.getOffX(), enemigo.getY() + mapa.getOffY());
        }
        for (Enemigo enemigomuerto : enemigosMuertos) {
            enemigomuerto.getAnim("muerto").draw(enemigomuerto.getX() + mapa.getOffX(), enemigomuerto.getY() + mapa.getOffY());
        }
        g.drawString("JugX:" + player.getX(), 100, 100);
        g.drawString("JugY:" + player.getY(), 100, 120);
        g.drawString("MapaX:" + mapa.getOffX(), 100, 140);
        g.drawString("MapaY:" + mapa.getOffY(), 100, 160);

        if(Keyboard.isKeyDown(Input.KEY_TAB)){
            g.scale(mapa.getEscalaTab(),mapa.getEscalaTab());
            mapa.renderTab();
            g.resetTransform();
        }

    }

    /**
     * @see org.newdawn.slick.BasicGame#update(org.newdawn.slick.GameContainer, int)
     */
    public void update(GameContainer container, int delta) {

        mapa.actCamara(delta, player);
        //Actualizo las hitbox
        player.actHitbox();
        for (Enemigo enemigo : enemigos) {
            enemigo.actHitbox();
        }


        player.lowerCDs(delta);

        if(Keyboard.isKeyDown(Input.KEY_W)&&player.block.getCDRestante()==0){
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

        //Compruebo colisiones con enemigos
        if (!player.retrocediendo())
            for (Enemigo enemigo : enemigos) {
                if (enemigo.collidesCon(player.getHitbox())) {//ha habido colision, determino el contexto
                    if (!player.estaAtacando()) {//si el jugador no ataca, sufredaño
                        player.rcvDmg(enemigo.getDmg());
                        player.retroceder(80);
                    } else { //si ataca compruebo si el enemigo no esta atacando
                        if(!enemigo.estaAtacando()){
                            enemigo.rcvDmg(1);
                        }
                        else{}



                    }


                    break;
                }
            }

        player.updPosX();
        player.updPosY();

        //Genero enemigos en una nueva sala
        if (mapa.playerEnSala() && mapa.playerEnSalaNueva() && mapa.camaraFijada()) {

            if(mapa.playerEnInicio(player)){

            }
            else if(mapa.playerEnFinal(player)){

            }
            else{
                Circle circulo = new Circle(player.posx + player.TAMX / 2, player.posy + player.TAMY / 2, player.TAMY * 3);

                for (int i = 0; i < 8; i++) {
                    Punto punto = mapa.getRandinSala();
                    if (!circulo.contains(punto.getX(), punto.getY()))
                        enemigos.add(new Enemigo((int) punto.getX(), (int) punto.getY(), 1, (double) 0.3, sprites,1));
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
        System.out.println(player.dash.getCDRestante());
        if (key == Input.KEY_ESCAPE) {

            container.exit();
        } else if (key == Input.KEY_Q && player.dash.getCDRestante() == 0 && !player.dash.estaActiva()){


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
            enemigos.add(new Enemigo((int) mapa.getAbsMouseX(), (int) mapa.getAbsMouseY(), 1, (double) 0.3, sprites,1));
        }

    }
    public void keyReleased(int key, char c){
        if (key == Input.KEY_W&&player.block.estaActiva()){
            player.block.terminar();
            player.block.contarCD();

        }
    }

}
