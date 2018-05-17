package EstadosJuego;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.*;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

public class CasaSyngrid1 extends BasicGameState {
    public static final int ID = 78;
    private Image introimg;
    private float posy;
    private final float vel=(float)0.04;
    private boolean movimiento;
    private int msFinal;


    @Override
    public int getID() {
        return ID;
    }
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        posy=0;

        introimg=new Image("ficheros/CasaSyngrid1.png");
        movimiento=true;
        msFinal=0;
    }
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        introimg.draw(0,-posy);
    }
    public void update(GameContainer container, StateBasedGame game, int delta) {
        if(movimiento){
            if(Keyboard.isKeyDown(Input.KEY_RETURN)){
                posy+=(vel*2.5)*delta;
            }
            else{
                posy+=(vel)*delta;
            }
            if(posy>=340){
                posy=340;
                movimiento=false;
            }
        }
        else{

            if(Keyboard.isKeyDown(Input.KEY_RETURN)){
                msFinal+=delta;
            }
            else{
                msFinal+=delta*2;
            }
            if(posy>=365){
                movimiento=false;
            }
        }

        if(Keyboard.isKeyDown(Input.KEY_ESCAPE)||msFinal>=3000){
            game.enterState(79, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));

        }

    }
}
