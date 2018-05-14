package EstadosJuego;

import EstadosJuego.CoreGame.drylands.Escena_Castillo;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import EstadosJuego.CoreGame.drylands.CoreGame;
import EstadosJuego.CoreGame.drylands.Escena_EntradaMina;
import EstadosJuego.CoreGame.drylands.Tutorial;

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
                addState(new Escena_Castillo());
                addState(new Estado_gameover());
                addState(new UnasHorasAntes());
                addState(new Tutorial());
                addState(new Escena_EntradaMina());
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
