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
import java.io.IOException;
import static java.lang.Boolean.parseBoolean;
import static java.lang.Thread.sleep;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SavedState;
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
public class Estado_opciones extends BasicGameState {

    //Elementos de Slick
    /**
     * The ID given to this state
     */
    public static final int ID = 4;
    /**
     * The font to write the message with
     */

    /**
     * The index of the selected option
     */
    private int select = 0;
    /**
     * The game holding this state
     */
    private StateBasedGame game;
    private SavedState save;
    private java.awt.Font UIFont1;
    private org.newdawn.slick.UnicodeFont uniFont;
    private int atras = 0;

    //Elementos de seleccionador de menu
    private String[] titulo = {"Controles", "Sonido", "Juego"};
    private Image fondo, btn_iz, btn_der, cabecera, cuerpo, cuerpo_2, btn_der_hover, btn_iz_hover;
    private Image btn_izq_lat, btn_der_lat, btn_izq_lat_hover, btn_der_lat_hover;
    private int btn = -1;
    private Image btn_guardar, btn_guardar_hover;

    //Elementos menu de controles
    private String[] letras = {"w", "a", "s", "d", "q", "e"};
    private String letras_guardado = "";
    private String[] controles = {"Atacar", "Bloquear", "Habilidad 1", "Habilidad 2", "Habilidad 3", "Usar"};
    private Image[] tecla = new Image[7];
    private int seleccion = -1;
    private boolean cambio = false;
    private boolean seleccionada = false;
    private long tiempo = 0;

    //Elementos menu de sonido
    private String[] sonidos = {"Volumen", "Música", "Efectos"};
    private int volumen = 100;
    private Image checkbox, checkbox_marcado;
    private Sound snd;
    private boolean musica = true, sonido = true;
    private int btn_spinner_vol = -1;

    //Elementos de menu de juego
    private String[] juego = {"Dificultad", "Zoom", "Brillo"};
    private String[] dificultades = {"Fácil", "Media", "Difícil"};
    private int dificultad = 2;
    private int brillo = 50;
    private int zoom = 0;
    private int btn_spinner_dif = -1;
    private int btn_spinner_zoom = -1;
    private int btn_spinner_brillo = -1;

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
        save = new SavedState("Guardado");
        cargarDatos();
        //Carga de todas las imagenes
        fondo = new Image("res/fondo_opciones.png");
        btn_iz = new Image("res/btn_despl_izq.png");
        btn_der = new Image("res/btn_despl_der.png");
        cabecera = new Image("res/tt.png");
        cuerpo = new Image("res/cuerpo.png");
        cuerpo_2 = new Image("res/cuerpo_2.png");
        btn_guardar = new Image("res/btn_guardar_volver.png");
        btn_guardar_hover = new Image("res/btn_guardar_volver_hover.png");
        btn_iz_hover = new Image("res/btn_despl_izq_hover.png");
        btn_der_hover = new Image("res/btn_despl_der_hover.png");
        btn_izq_lat = new Image("res/btn_lateral_izq.png");
        btn_der_lat = new Image("res/btn_lateral_der.png");
        btn_izq_lat_hover = new Image("res/btn_lateral_izq_hover.png");
        btn_der_lat_hover = new Image("res/btn_lateral_der_hover.png");
        checkbox = new Image("res/checkbox.png");
        checkbox_marcado = new Image("res/checkbox_marcado.png");
        //Carga de todas las teclas
        for (int i = 0; i < controles.length; i++) {
            tecla[i] = new Image("res/teclas/" + letras[i] + ".png");
        }
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
        cabecera.draw(450, 60);

        g.drawString(titulo[select], 542, 70);
        //Botones seleccionadores laterales de menu
        if (btn == 0) {
            btn_iz_hover.draw(350, 60);
        } else {
            btn_iz.draw(350, 60);
        }
        if (btn == 1) {
            btn_der_hover.draw(960, 60);
        } else {
            btn_der.draw(960, 60);
        }
        //Boton seleccionador de guardar y volver
        if (btn == 2) {
            btn_guardar_hover.draw(450, 670);
        } else {
            btn_guardar.draw(450, 670);
        }
        //Mostrar contenido de cada opcion del menu
        switch (select) {
            //Opciones de controles
            case 0:
                cuerpo.draw(450, 160);
                //Bucle para mostrar todos los controles
                for (int i = 0; i < controles.length; i++) {
                    //En caso de seleccionar una tecla hace que parpadee
                    if (seleccionada && seleccion == i) {
                        //Al estar a 0 no se muestra
                        tecla[i].draw(830, 80 * i + 250, 0);
                        //Si se presiona una tecla entonces esta cambia
                        if (cambio) {
                            setImageTeclas(i);
                            //Reseteamos el cambio
                            
                            cambio = false;
                        }
                        //Si no le afecta entonces la muestra de forma normal
                    } else {
                        tecla[i].draw(830, 80 * i + 165, 1.5f);
                    }
                    //Muestra los String que tenemos guardados
                    g.drawString(controles[i], 480, 80 * i + 175);
                }
                break;
            //Opciones de sonido
            case 1:
                cuerpo_2.draw(450, 160);
                //Muestra los String relativos a las opciones de sonido
                for (int i = 0; i < sonidos.length; i++) {
                    g.drawString(sonidos[i], 500, 120 * i + 200);
                }

                //Muestra los botones de los spinner
                //Si esta seleccionado el boton se activa el hover
                //Boton izquierdo
                if (btn_spinner_vol == 0) {
                    btn_izq_lat_hover.draw(760, 200);
                } else {
                    btn_izq_lat.draw(760, 200);
                }
                //Boton derecho
                if (btn_spinner_vol == 1) {
                    btn_der_lat_hover.draw(865, 200);
                } else {
                    btn_der_lat.draw(865, 200);
                }
                //Muestra el volumen que hay    
                g.drawString(volumen + "", 795, 200);

                //Muestra el checkbox de musica
                if (musica) {
                    checkbox_marcado.draw(800, 320);
                } else {
                    checkbox.draw(800, 320);
                }
                //Muestra el checkbox de sonido
                if (sonido) {
                    checkbox_marcado.draw(800, 440);
                } else {
                    checkbox.draw(800, 440);
                }
                break;
            //Opciones de juego
            default:
                cuerpo_2.draw(450, 160);
                //Muestra el texto del menu
                g.drawString(juego[0], 550, 200);
                g.drawString(dificultades[dificultad], 600, 275);
                g.drawString(juego[1], 500, 350);
                g.drawString(juego[2], 500, 450);
                g.drawString(zoom + "", 740, 350);
                g.drawString(brillo + "", 740, 450);

                //Spinner de la seleccion de dificultad
                //Boton izquierdo del spinner
                if (btn_spinner_dif == 0) {
                    btn_izq_lat_hover.draw(560, 275);
                } else {
                    btn_izq_lat.draw(560, 275);
                }
                //Boton derecho del spinner
                if (btn_spinner_dif == 1) {
                    btn_der_lat_hover.draw(780, 275);
                } else {
                    btn_der_lat.draw(780, 275);
                }
                //Spinner de la seleccion de zoom
                //Boton izquierdo del spinner
                if (btn_spinner_zoom == 0) {
                    btn_izq_lat_hover.draw(700, 350);
                } else {
                    btn_izq_lat.draw(700, 350);
                }
                //Boton derecho del spinner
                if (btn_spinner_zoom == 1) {
                    btn_der_lat_hover.draw(820, 350);
                } else {
                    btn_der_lat.draw(820, 350);
                }

                //Spinner de la seleccion de brillo
                //Boton izquierdo del spinner
                if (btn_spinner_brillo == 0) {
                    btn_izq_lat_hover.draw(700, 450);
                } else {
                    btn_izq_lat.draw(700, 450);
                }
                //Boton derecho del spinner
                if (btn_spinner_brillo == 1) {
                    btn_der_lat_hover.draw(820, 450);
                } else {
                    btn_der_lat.draw(820, 450);
                }
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
        //Calculamos el tiempo con delta
        tiempo += delta;
        //Si no detecta que esta sobre ningun boton entonces no muestra su hover
        btn = -1;
        //Seleccionador de menus
        //Pasar a menu de la izquierda
        if (pos_x > 350 && pos_x < 436) {
            if (pos_y > 622 && pos_y < 708) {
                //Selecciona mostrar el hover del boton izquierdo
                btn = 0;
                //Si detecta un click del raton
                if (Mouse.isButtonDown(0)) {
                    try {
                        //Reproduce un sonido
                        snd.play(0.5f, 0.5f);
                        //Espera un tiempo para que no sea inmediato
                        sleep(150);
                        //Cambia al menu adecuado de los fijados
                        if (select <= 0) {
                            select = 2;
                        } else {
                            --select;
                        }
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Estado_opciones.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        } //Pasar a menu a la derecha
        else if (pos_x > 960 && pos_x < 1046) {
            if (pos_y > 622 && pos_y < 708) {
                //Selecciona mostrar el hover del boton derecho
                btn = 1;
                //Si detecta un click del raton
                if (Mouse.isButtonDown(0)) {
                    try {
                        //Reproduce un sonido
                        snd.play(0.5f, 0.5f);
                        //Espera un tiempo para que no sea inmediato
                        sleep(150);
                        //Cambia al menu adecuado de los fijados
                        if (select >= 2) {
                            select = 0;
                        } else {
                            ++select;
                        }
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Estado_opciones.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            //Boton guardar y salir
        } else if (pos_x > 450 && pos_x < 950) {
            if (pos_y > 12 && pos_y < 98) {
                //Muestra su hover
                btn = 2;
                if (Mouse.isButtonDown(0)) {
                    //Hace que atras valga caracter escape
                    atras = 27;
                }
            }
        }
        //Menu de controles
        //Detecta si esta seleccionado este menu y si se hace click
        if (select == 0 && Mouse.isButtonDown(0)) {
            //Si esta entre las coordenadas selecciona una tecla u otra
            if (pos_x > 830 && pos_x < 894) {
                //Primera tecla
                if (pos_y > 539 && pos_y < 603) {
                    seleccion = 0;
                    //Segunda tecla
                } else if (pos_y > 459 && pos_y < 523) {
                    seleccion = 1;
                    //Tercera tecla
                } else if (pos_y > 379 && pos_y < 443) {
                    seleccion = 2;
                    //Cuarta tecla
                } else if (pos_y > 299 && pos_y < 363) {
                    seleccion = 3;
                    //Quinta tecla
                } else if (pos_y > 219 && pos_y < 283) {
                    seleccion = 4;
                    //Sexta tecla
                } else if (pos_y > 139 && pos_y < 203) {
                    seleccion = 5;
                }
            }
        }
        //Menu de sonido
        //Detecta si esta seleccionado este menu
        if (select == 1) {

            //Se selecciona checkbox de musica si se hace click
            if (Mouse.isButtonDown(0) && pos_x > 800 && pos_x < 864 && pos_y > 384 && pos_y < 448) {
                try {
                    //Emite sonido
                    snd.play(0.5f, 0.5f);
                    //Espera un tiempo
                    sleep(150);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Estado_opciones.class.getName()).log(Level.SEVERE, null, ex);
                }
                //Marca o desmarca el checkbox de musica
                musica = !musica;

                //Se selecciona checkbox de sonido si se hace click
            } else if (Mouse.isButtonDown(0) && pos_x > 800 && pos_x < 864 && pos_y > 264 && pos_y < 328) {
                try {
                    //Emite sonido
                    snd.play(0.5f, 0.5f);
                    //Espera un tiempo
                    sleep(150);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Estado_opciones.class.getName()).log(Level.SEVERE, null, ex);
                }
                //Marca o desmarca el checkbox de musica
                sonido = !sonido;

                //Spinners de sonido
                //Spinner izquierdo
            } else if (pos_x > 760 && pos_x < 792 && pos_y > 504 && pos_y < 568) {
                //Muestra el hover sobre el spinner de volumen
                btn_spinner_vol = 0;
                if (Mouse.isButtonDown(0)) {
                    //Emite sonido
                    snd.play(0.5f, 0.5f);
                    try {
                        //Espera un tiempo
                        sleep(100);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Estado_opciones.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    //Cambia la variable sonido decrementandola hasta 0
                    if (volumen > 0) {
                        --volumen;
                    }
                }
                //Spinner derecho
            } else if (pos_x > 865 && pos_x < 897 && pos_y > 504 && pos_y < 568) {
                //Muestra el hover sobre el spinner de volumen
                btn_spinner_vol = 1;
                if (Mouse.isButtonDown(0)) {
                    //Emite sonido    
                    snd.play(0.5f, 0.5f);
                    try {
                        //Espera un tiempo
                        sleep(100);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Estado_opciones.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    //Cambia la variable sonido incrementandola hasta 100
                    if (volumen < 100) {
                        ++volumen;
                    }
                }
            } else {
                //No muestra ningun hover sobre los spinner
                btn_spinner_vol = -1;
            }

            //Menu de juego
        } else if (select == 2) {

            //Spinner de dificultad de juego
            //Boton izquierdo del spinner
            if (pos_x > 560 && pos_x < 592 && pos_y > 429 && pos_y < 493) {
                //Muestra el hover del spinner de dificultad
                btn_spinner_dif = 0;
                //Si se hace click
                if (Mouse.isButtonDown(0)) {
                    //Emite sonido  
                    snd.play(0.5f, 0.5f);
                    //Espera un tiempo
                    try {
                        sleep(200);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Estado_opciones.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    //Cambia la variable dificultad decrementandola hasta 0
                    if (dificultad > 0) {
                        --dificultad;
                    }
                }
            } else if (pos_x > 780 && pos_x < 812 && pos_y > 429 && pos_y < 493) {
                //Muestra el hover del spinner de dificultad
                btn_spinner_dif = 1;
                //Si se hace click
                if (Mouse.isButtonDown(0)) {
                    //Emite un sonido
                    snd.play(0.5f, 0.5f);
                    //Espera un tiempo
                    try {
                        sleep(200);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Estado_opciones.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    //Cambia la variable dificultad incrementandola hasta 2
                    if (dificultad < 2) {
                        ++dificultad;
                    }
                }

                //Spinner de zoom del juego    
                //Boton izquierdo del spinner    
            } else if (pos_x > 700 && pos_x < 732 && pos_y > 354 && pos_y < 418) {
                //Muestra el hover del spinner de zoom
                btn_spinner_zoom = 0;
                //Si se hace click
                if (Mouse.isButtonDown(0)) {
                    //Emite un sonido
                    snd.play(0.5f, 0.5f);
                    //Espera un tiempo
                    try {
                        sleep(100);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Estado_opciones.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    //Cambia la variable zoom decrementandola hasta 0
                    if (zoom > 0) {
                        --zoom;
                    }

                }
                //Spinner derecho
            } else if (pos_x > 820 && pos_x < 852 && pos_y > 354 && pos_y < 418) {
                //Muestra el hover del spinner de zoom
                btn_spinner_zoom = 1;
                //Si se hace click
                if (Mouse.isButtonDown(0)) {
                    //Emite un sonido
                    snd.play(0.5f, 0.5f);
                    //Espera un tiempo
                    try {
                        sleep(100);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Estado_opciones.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    //Cambia la variable zoom incrementandola hasta 100
                    if (zoom < 100) {
                        ++zoom;
                    }
                }

                //Spinner de brillo de juego
                //Boton izquierdo del spinner
            } else if (pos_x > 700 && pos_x < 732 && pos_y > 254 && pos_y < 318) {
                //Muestra el brillo del spinner de zoom
                btn_spinner_brillo = 0;
                //Si se hace click
                if (Mouse.isButtonDown(0)) {
                    //Emite un sonido
                    snd.play(0.5f, 0.5f);
                    //Espera un tiempo
                    try {
                        sleep(100);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Estado_opciones.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    if (brillo > 0) {
                        --brillo;
                    }

                }
                //Spinner derecho
            } else if (pos_x > 820 && pos_x < 852 && pos_y > 254 && pos_y < 318) {
                //Muestra el brillo del spinner de zoom
                btn_spinner_brillo = 1;
                //Si se hace click
                if (Mouse.isButtonDown(0)) {
                    //Emite un sonido
                    snd.play(0.5f, 0.5f);
                    //Espera un tiempo
                    try {
                        sleep(100);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Estado_opciones.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    //Cambia la variable brillo incrementandola hasta 100
                    if (brillo < 100) {
                        ++brillo;
                    }
                }
            } else {
                //Muestra los hovers por defecto de los spinners
                btn_spinner_vol = -1;
                btn_spinner_dif = -1;
                btn_spinner_zoom = -1;
                btn_spinner_brillo = -1;
            }
        }
        //Crea el efecto de parpadeo cuando se selecciona una tecla
        if (seleccion >= 0 && (tiempo % 500) == 0) {
            seleccionada = !seleccionada;
        }
        //Sale del estado al menu inicial
        if (atras == 27) {
            try {
                guardarDatos();
                sleep(200);
            } catch (IOException ex) {
                Logger.getLogger(Estado_opciones.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                Logger.getLogger(Estado_opciones.class.getName()).log(Level.SEVERE, null, ex);
            }
            //Reseteamos atras
            atras = 0;
            //Entra en el estado
            game.enterState(Estado_inicial.ID, new FadeOutTransition(Color.decode("#f0dc69")), new FadeInTransition(Color.decode("#f0dc69")));

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
        //Primero se comprueba que este en modo de seleccion
        atras = c;
        if (seleccion >= 0) {
            //Se comprueba que sea uno de los caracteres que tenemos preparados
            if ((c > 96 && c < 122) || c == 241 || c > 47 && c < 57) {
                //Cambiamos la letra que se haya elegido por la pulsada
                letras[seleccion] = c + "";
                //Ahora hay que mostrar el cambio por lo que se usa este booleano
                cambio = true;
                snd.play();
            }
        }
    }

    /**
     * Cambia la imagen del array que se le pasa
     *
     * @param i (Int) posicion de la tecla a modificar
     */
    public void setImageTeclas(int i) {
        try {
            tecla[i] = new Image("res/teclas/" + letras[i] + ".png");
        } catch (SlickException se) {
            se.printStackTrace(System.err);
        }
    }

    /**
     * Guarda los datos de las opciones
     *
     * @throws java.io.IOException
     */

    public void guardarDatos() throws IOException {

        save.setNumber("brillo", brillo);
        save.setNumber("zoom", zoom);
        save.setNumber("dificultad", dificultad);
        save.setNumber("volumen", volumen);
        save.setString("musica", musica + "");
        save.setString("sonido", sonido + "");
        for (int i = 0; i < controles.length; i++) {
            letras_guardado += letras[i] + "-";
        }
        System.out.println("letras" + letras_guardado);
        save.setString("letras", letras_guardado);
        save.save();
    }

    /**
     * Carga los datos de las opciones
     *
     */
    public void cargarDatos() {
        brillo = (int) save.getNumber("brillo");
        zoom = (int) save.getNumber("zoom");
        dificultad = (int) save.getNumber("dificultad");
        volumen = (int) save.getNumber("volumen");
        musica = parseBoolean(save.getString("musica"));
        sonido = parseBoolean(save.getString("sonido"));
        //letras = save.getString("letras").split("-");

    }
}
