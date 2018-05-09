/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EstadosJuego.CoreGame.drylands;

import org.newdawn.slick.SpriteSheet;

/**
 * @author FairLight
 */
public class Enemigo extends Personaje {

    private final int dmg;

    public Enemigo(int posx, int posy, int vida, double vel, SpriteSheet sprites,int dmg) {
        super(48, 60, 48, (float) 0.2, sprites, posx, posy, vida);
        this.dmg=dmg;


    }

    @Override
    public boolean estaAtacando() {
        return false;
    }

    @Override
    public boolean estaBloqueando() {
        return false;
    }
    public int getDmg(){
        return dmg;
    }
}
