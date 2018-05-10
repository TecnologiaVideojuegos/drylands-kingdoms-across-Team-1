package EstadosJuego.CoreGame.drylands;

import org.newdawn.slick.Animation;
import org.newdawn.slick.SpriteSheet;


public class Block extends Habilidad {

    private int msBlockMax,contadorMs;

    public Block(String nombre, int cdmax, int msBlockMax, SpriteSheet sprites,Combo combo) {
        super(nombre, cdmax,combo);
        this.msBlockMax = msBlockMax;
        contadorMs=0;

        der = new Animation();
        izq = new Animation();
        for (int i = 0; i < 6; i++) {
            der.addFrame(sprites.getSprite(i, 5), 150);
        }
        der.stopAt(6);
        for (int i = 0; i < 6; i++) {
            izq.addFrame(sprites.getSprite(i, 5).getFlippedCopy(true, false), 150);
        }
        izq.stopAt(6);
    }

    @Override
    public void calcNuevaPos(Personaje pj, int delta) {

    }

    public void block(int delta) {

        fail=true;
        System.out.println("Llamada a block, cdrestante="+cdrestante+" y msblock:"+contadorMs);
        if((contadorMs+delta)>msBlockMax){
            contarCD();
            contadorMs=0;
            terminar();
        }
        else{
            activa=true;
            contadorMs+=delta;
        }

    }
}
