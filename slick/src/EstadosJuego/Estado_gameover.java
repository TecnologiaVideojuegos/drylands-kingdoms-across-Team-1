/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EstadosJuego;

/**
 *
 * @author Alvarohf
 */

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.Sound;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

/**
 * A simple test state to display an image and rotate it
 *
 * @author kevin
 */
public class Estado_gameover extends BasicGameState {

    //Elementos de Slick
    /**
     * The ID given to this state
     */
    public static final int ID = 6;
    /**
     * The game holding this state
     */
    private StateBasedGame game;
    private java.awt.Font UIFont1;
    private org.newdawn.slick.UnicodeFont uniFont;
    private int atras = 0;
    //Elementos de seleccionador de menu
    private Image fondo, flecha;
    private Sound snd;
    private boolean primera = true;

    /**
     * @see org.newdawn.slick.state.BasicGameState#getID()
     */
    @Override
    public int getID() {
        return ID;
    }

    /**
     * @see
     * org.newdawn.slick.state.BasicGameState#init(org.newdawn.slick.GameContainer,
     * org.newdawn.slick.state.StateBasedGame)
     */
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        this.game=game;
        //Carga de todas las imagenes
        fondo = new Image("res/fondo_gameover.png");
        flecha = new Image("res/flecha.png");

        //Carga de sonidos del menu
        snd = new Sound("res/sonido1.wav");
        //Carga de fuente ttf pasandola a unifont
        try {
            UIFont1 = java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT,
                    org.newdawn.slick.util.ResourceLoader.getResourceAsStream("res/Sangoku4.ttf"));
            UIFont1 = UIFont1.deriveFont(java.awt.Font.PLAIN, 55.f); //You can change "PLAIN" to "BOLD" or "ITALIC"... and 16.f is the size of your font
            uniFont = new org.newdawn.slick.UnicodeFont(UIFont1);
            uniFont.addAsciiGlyphs();
            uniFont.getEffects().add(new ColorEffect(java.awt.Color.white)); //You can change your color here, but you can also change it in the render{ ... }
            uniFont.addAsciiGlyphs();
            uniFont.loadGlyphs();
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }

    /**
     * @see
     * org.newdawn.slick.state.BasicGameState#render(org.newdawn.slick.GameContainer,
     * org.newdawn.slick.state.StateBasedGame, org.newdawn.slick.Graphics)
     */
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        //Seleccionamos la fuente
        g.setFont(uniFont);
        //Dibujamos el fondo y elementos basicos
        fondo.draw(0, 0);
        switch (atras) {
            case 0:
                flecha.draw(440, 280);
                break;
            case 1:
                flecha.draw(410, 390);
                break;
            default:
                flecha.draw(550, 515);
                break;
        }
    }

    /**
     * @see
     * org.newdawn.slick.state.BasicGameState#update(org.newdawn.slick.GameContainer,
     * org.newdawn.slick.state.StateBasedGame, int)
     */
    public void update(GameContainer container, StateBasedGame game, int delta) {
        int pos_x = Mouse.getX();
        int pos_y = Mouse.getY();
       //Detectamos que el raton haga click sobre los textos para elegir la opcion
        if (pos_x > 480 && pos_x < 880 && pos_y > 418 && pos_y < 493 && Mouse.isButtonDown(0)) {
            if (primera) {
                snd.play();
                primera = false;
            }
            atras = 0;
        } else if (pos_x > 450 && pos_x < 950 && pos_y > 318 && pos_y < 378 && Mouse.isButtonDown(0)) {
            if (primera) {
                snd.play();
                primera = false;
            }
            atras = 1;
        } else if (pos_x > 590 && pos_x < 770 && pos_y > 193 && pos_y < 268 && Mouse.isButtonDown(0)) {
            if (primera) {
                snd.play();
                primera = false;
            }
            atras = 2;
        } else {
            primera = true;
        }

    }

    /**
     * Obtiene la ultima letra pulsada
     *
     * @param key (int) numero de la letra pulsada
     * @param c (char) Caracter de la letra pulsada
     */
    @Override
    public void keyReleased(int key, char c) {
        //Tambien se permite que elija con el teclado
        //Letra w y flecha hacia arriba
        if (c == 115 || key == 208) {
            if (atras < 2) {
                atras++;
            } else {
                atras = 0;
            }
            snd.play();
        //Letra s y flecha hacia abajo
        } else if (c == 119 || key == 200) {
            snd.play();
            if (atras > 0) {
                atras--;
            } else {
                atras = 2;
            }
        //Detecta si se pulsa enter para hacer una accion
        } else if (key == 28) {
            snd.play();
            if (atras == 0) {
                //Codigo a modificar
                game.enterState(Estado_cargar.ID, new FadeOutTransition(Color.decode("#f0dc69")), new FadeInTransition(Color.decode("#f0dc69")));
            } else if (atras == 1) {
                //Codigo a modificar
                game.enterState(Intro.ID, new FadeOutTransition(Color.decode("#f0dc69")), new FadeInTransition(Color.decode("#f0dc69")));
            } else if (atras == 2) {
                System.exit(0);
            }
        }
    }
    /**
     * Sirve para registrar a el ratón
     * @param button
     * @param x
     * @param y
     * @param clickCount*/
    @Override
    public void mouseClicked(int button, int x, int y, int clickCount) {
        //Miramos si se hace doble click
        if (clickCount == 2) {
            if (atras == 0) {
                //Codigo a modificar
                game.enterState(Estado_cargar.ID, new FadeOutTransition(Color.decode("#f0dc69")), new FadeInTransition(Color.decode("#f0dc69")));
            } else if (atras == 1) {
                //Codigo a modificar
                game.enterState(Intro.ID, new FadeOutTransition(Color.decode("#f0dc69")), new FadeInTransition(Color.decode("#f0dc69")));
            } else {
                System.exit(0);
            }
        }
    }
}
