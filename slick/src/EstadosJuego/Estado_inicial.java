package EstadosJuego;

import EstadosJuego.CoreGame.drylands.CoreGame;
import EstadosJuego.CoreGame.drylands.Guardado;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

public class Estado_inicial extends BasicGameState {

    /**
     * The ID given to this state
     */
    //Sonidos del menu
    private Sound snd;
    //Imagenes del menu
    private Image fondo;
    private Image btn_jugar;
    private Image btn_salir;
    private Image btn_cargar;
    private Image btn_opciones;
    private Image btn_cargar_hover;
    private Image btn_jugar_hover;
    private Image btn_salir_hover;
    private Image btn_opciones_hover;
    //Cursor personalizado
    private Image cursor;
    //Seleccion de menu y hover
    private int btn=-1;
    public static final int ID = 1;
    @Override
    public int getID() {
        return ID;
    }
    
    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        //Inicializacion de imagenes
        fondo = new Image("res/fondo_menu2.png");
        btn_jugar = new Image("res/btn_jugar.png");
        btn_salir = new Image("res/btn_salir.png");
        btn_cargar = new Image("res/btn_cargar.png");
        btn_opciones = new Image("res/btn_opciones.png");
        btn_cargar_hover = new Image("res/btn_cargar_hover.png");
        btn_jugar_hover = new Image("res/btn_jugar_hover.png");
        btn_salir_hover = new Image("res/btn_salir_hover.png");
        btn_opciones_hover = new Image("res/btn_opciones_hover.png");
        cursor = new Image ("res/puntero.png");
        //Inicializacion de sonidos
        snd = new Sound("res/sonido1.wav");
        //Uso de logo en la esquina superior izquierda
        container.setIcon("res/icono1.png");
        //Inicializacion de cursor
        container.setMouseCursor(cursor, 3, 3);
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        //Configuramos display
        AppGameContainer gc = (AppGameContainer) container;
        gc.setDisplayMode(1366,768, false);
        //Dibujamos fondo
        fondo.draw(0, 0);
        //Seleccionamos hover o no en los botones
        if (btn==0) {
            btn_jugar_hover.draw(450, 160);
        } else {
            btn_jugar.draw(450, 160);
        }
        if (btn==1) {
            
            btn_cargar_hover.draw(450, 300);
        } else {
            btn_cargar.draw(450, 300);
        }
        if (btn==2) {
            btn_opciones_hover.draw(450, 440);
        } else {
            btn_opciones.draw(450, 440);
        }
        if (btn==3) {
            btn_salir_hover.draw(450, 580);
        } else {
            btn_salir.draw(450, 580);
        }
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) {

        int pos_x = Mouse.getX();
        int pos_y = Mouse.getY();
        //No va a haber ninguna señalada
        btn=-1;
        if (pos_x > 450 && pos_x < 898) {
            if (pos_y > 490 && pos_y < 608) {
                //Se activa el hover
                btn=0;
                //Se selecciona la opcion y se pasa al estado, aqui se juega una nueva partida
                if (Mouse.isButtonDown(0)) {

                    snd.play();
                    try {
                        Guardado partida = new Guardado("partida");
                        //partida.resetPartida();

                    }catch(SlickException e){}

                    game.enterState(9, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));

                }
            } else if (pos_y > 350 && pos_y < 468) {
                 //Se activa el hover
                btn=1;
                //Se selecciona la opcion y se pasa al estado, aqui se carga solo
                if (Mouse.isButtonDown(0)) {
                    snd.play();
                    game.enterState(3, new FadeOutTransition(Color.black), new FadeInTransition(Color.green));
                }
            } else if (pos_y > 210 && pos_y < 328) {
                 //Se activa el hover
                btn=2;
                //Se selecciona la opcion y se pasa al estado
                if (Mouse.isButtonDown(0)) {
                    snd.play();
                    game.enterState(Estado_opciones.ID, new FadeOutTransition(Color.decode("#1a0121")), new FadeInTransition(Color.decode("#1a0121")));
                }
            } else if (pos_y > 70 && pos_y < 188) {
                 //Se activa el hover
                btn=3;
                //Se selecciona la opcion y se pasa al estado
                if (Mouse.isButtonDown(0)) {
                    snd.play();
                    System.exit(0);
                }
            }
        }
    }

}
