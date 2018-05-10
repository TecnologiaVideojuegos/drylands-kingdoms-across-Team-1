package EstadosJuego;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class Intro extends BasicGameState {
    public static final int ID = 5;
    public TextField tf;
    private java.awt.Font UIFont1;
    private org.newdawn.slick.UnicodeFont uniFont;

    @Override
    public int getID() {
        return ID;
    }
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
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
        tf=new TextField(container,uniFont,100,100,300,300);
        tf.setText("Dale a tu cuerpot alegria amcarena que tu cuerpo es pa darle alegria y ocsa bueno dijo el no se que mas poner pero espera que esto se va ya de los margenwes");
    }
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        tf.render(container,g);
    }
    public void update(GameContainer container, StateBasedGame game, int delta) {

    }
}
