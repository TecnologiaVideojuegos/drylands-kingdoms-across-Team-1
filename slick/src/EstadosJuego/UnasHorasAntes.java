package EstadosJuego;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

public class UnasHorasAntes extends BasicGameState {
    public static final int ID = 11;
    private Image img;



    private int msFinal;



    @Override
    public int getID() {
        return ID;
    }
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
            

        img=new Image("ficheros/horas_antes.png");

        msFinal=0;
    }
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        img.draw(0,0);
    }
    public void update(GameContainer container, StateBasedGame game, int delta) {

        msFinal += delta;
        if(msFinal>=4000)
            game.enterState(50, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
    }




}
