/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EstadosJuego.CoreGame.drylands;

import org.newdawn.slick.Animation;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Vector2f;

/**
 * @author FairLight
 */
public class Enemigo extends Personaje {

    private final int dmg;
    public Dash dash;
    public int contadorMsReflejos;



    public Enemigo(int posx, int posy, int vida, double vel, SpriteSheet sprites,int dmg,int rango) {

        super(48, 60, 40, (float) vel, sprites, posx, posy, vida);
        this.dmg=dmg;
        dash = new Dash((float) 1.2, 1000, sprites,new Combo(),rango);

        contadorMsReflejos=0;
    }

    public Animation getAnim() {

            if (corriendo) {
                if (mirandoD) return run;
                else return runi;
            } else {
                if (mirandoD) return idle;
                else return idlei;
            }


    }

    public void update(Player player,int delta){
        if (retroceso) {


            contadorRetroceso-=delta;
            if(contadorRetroceso<=0){
                retroceso=false;
            }
            else{
                Vector2f despl = new Vector2f((double)anguloRetroceso);
                Vector2f posicion = new Vector2f(new float[]{posx,posy});

                this.setNewPosVector(posicion.x+1000*despl.x,posicion.y+1000*despl.y,(float)(getMaxstep(delta)*1.5*contadorRetroceso/msRetroceso));
            }


        } else if (dash.activa) {
            dash.calcNuevaPos(this, delta);
            System.out.println("holi2");
        }
        else {
            System.out.println(contadorMsReflejos);
            //primero calculo la distancia
            float difx = player.getX() - (this.posx + (TAMX / 2));
            float dify = player.getY() - (this.posy + (TAMY / 2));
            float difcuadrados = (difx * difx) + (dify * dify);
            float dist = (float) Math.sqrt(difcuadrados);

            if(dist>(dash.getRango()*0.66))
                setNewPosVector(player.getX(),player.getY(),this.getMaxstep(delta));
            else{
                contadorMsReflejos+=delta;
                if(contadorMsReflejos>=600){
                    contadorMsReflejos=0;
                    dash = new Dash((float) 1.2, 1000, sprites,new Combo(),200);
                    this.dash.cast(this,player.getX(),player.getY());
                }

            }
        }

    }

    @Override
    public boolean estaAtacando() {
        return dash.estaActiva();
    }

    @Override
    public boolean estaBloqueando() {
        return false;
    }
    public int getDmg(){
        return dmg;
    }
    @Override
    public void retroceder(float angulo) {
        System.out.println("retrocediendo en un angulo de :"+angulo);
        retroceso = true;
        anguloRetroceso=angulo;
        contadorRetroceso=msRetroceso;
        if(dash.estaActiva()){
            dash.contarCD();
            dash.terminar();
        }

    }
}
