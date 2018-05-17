package EstadosJuego;

import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import EstadosJuego.CoreGame.drylands.Guardado;

public class PerdidoDesierto extends BasicGameState {

    public static final int ID = 51;
    private Image img;

    private int msFinal;

    @Override
    public int getID() {
        return ID;
    }

    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        

        img = new Image("ficheros/perdidoDesierto.png");

        msFinal = 0;
    }

    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        img.draw(0, 0);
    }

    public void update(GameContainer container, StateBasedGame game, int delta) {

        msFinal += delta;
        if (msFinal >= 1000) {
            game.enterState(52, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
        }

    }

}
