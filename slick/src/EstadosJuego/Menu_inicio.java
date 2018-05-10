package EstadosJuego;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import EstadosJuego.CoreGame.drylands.CoreGame;

/**
 * 
 *
 * @author Alvarohf
 */
public class Menu_inicio extends StateBasedGame {

	public Menu_inicio() {
		super("Dryland: Kingdom across");
	}
	
        @Override
	public void initStatesList(GameContainer container) throws SlickException {
		//addState(new TestState1());
		
		addState(new Estado_inicial());
                addState(new CoreGame());
                addState(new Intro());
                addState(new Estado_opciones());
	}
	
	public static void main(String[] argv) {
		try {
			AppGameContainer container = new AppGameContainer(new Menu_inicio());
			container.setDisplayMode(1366,768,false);
			container.start();
		} catch (SlickException e) {
		}
	}
}
