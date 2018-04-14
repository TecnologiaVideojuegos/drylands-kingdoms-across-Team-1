/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proceduralTiled;

/**
 *
 * @author marino
 */
public class Celula {
    private String tipo;
    private boolean up,down,left,right;
    Celula(){
        this.tipo="vacio";
        up=down=left=right=false;
    }
    public String getTipo(){
        return this.tipo;
    }
    public void setTipo(String tipo){
        this.tipo=tipo;
    }
    public boolean getUp(){
        return this.up;
    }
    public boolean getDown(){
        return this.down;
    }
    public boolean getLeft(){
        return this.left;
    }
    public boolean getRight(){
        return this.right;
    }
    public void conUp(){
        this.up=true;
    }
    public void dconUp(){
        this.up=false;
    }
    public void conDown(){
        this.down=true;
    }
    public void dconDown(){
        this.down=false;
    }
    public void conLeft(){
        this.left=true;
    }
    public void dconLeft(){
        this.left=false;
    }
    public void conRight(){
        this.right=true;
    }
    public void dconRight(){
        this.right=false;
    }
}
