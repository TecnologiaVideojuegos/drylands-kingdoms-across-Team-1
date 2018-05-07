/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MapasProc;
import java.util.Random;
import java.util.ArrayList;
import org.mapeditor.core.*;

/**
 *
 * @author marino
 */

public class MetodosProcedural {

    /**
     * @param args the command line arguments
     */
    
    
    public static int encontrarGrupo(ArrayList<ArrayList<Punto>> subgrupos,int x, int y){
        for(ArrayList<Punto> subgrupo:subgrupos){
                            for(Punto punto:subgrupo){
                                if(punto.esIgual(x,y)){
                                    return subgrupos.indexOf(subgrupo);
                                    
                                }
                            }
                                
                                
                            
                        }
        return -1;
    }
    public static void mostrarMapa(Celula[][] matriz,int ancho,int alto){
        
        for(int i=0;i<alto;i++){
            for(int j=0;j<ancho;j++){
                if("sala".equals(matriz[j][i].getTipo())){
                    System.out.print("█");
                }
                else if("inicio".equals(matriz[j][i].getTipo())){
                    System.out.print("x");
                }
                else if("final".equals(matriz[j][i].getTipo())){
                    System.out.print("O");
                }
                else if("pasillo".equals(matriz[j][i].getTipo())){
                    if(matriz[j][i].getUp()){
                        if(matriz[j][i].getDown()){
                            if(matriz[j][i].getLeft()){
                                if(matriz[j][i].getRight()){
                                    System.out.print("╬");
                                }
                                else{
                                    System.out.print("╣");
                                }
                            }
                            else{
                                if(matriz[j][i].getRight()){
                                    System.out.print("╠");
                                }
                                else{
                                    System.out.print("║");
                                }
                            }
                            
                        }
                        else{
                            if(matriz[j][i].getLeft()){
                                if(matriz[j][i].getRight()){
                                    System.out.print("╩");
                                }
                                else{
                                    System.out.print("╝");
                                }
                            }
                            else{
                                if(matriz[j][i].getRight()){
                                    System.out.print("╚");
                                }
                                else{
                                    System.out.print("Algo ha ido mal, conector solo hacia arriba");
                                }
                            }
                        }
                    }
                    else{
                        if(matriz[j][i].getDown()){
                            if(matriz[j][i].getLeft()){
                                if(matriz[j][i].getRight()){
                                    System.out.print("╦");
                                }
                                else{
                                    System.out.print("╗");
                                }
                            }
                            else{
                                if(matriz[j][i].getRight()){
                                    System.out.print("╔");
                                }
                                else{
                                    System.out.print("Algo ha ido mal, conector solo hacia abajo");
                                }
                            }
                        }
                        else{
                            if(matriz[j][i].getLeft()){
                                if(matriz[j][i].getRight()){
                                    System.out.print("═");
                                }
                                else{
                                    System.out.print("Algo ha ido mal, conector solo hacia izquierda");
                                }
                            }
                            else{
                                if(matriz[j][i].getRight()){
                                    System.out.print("Algo ha ido mal, conector solo hacia derecha");
                                }
                                else{
                                    System.out.print("·");
                                }
                            }
                        }                        
                    }
                }
                else{
                    System.out.print("·");
                }
            }
            System.out.print("\n");
        }
    }
    public static void trazarVertical(Celula[][] matriz, int xpos,int y1,int y2){
        if(y1>y2){
                int aux = y1;
                y1=y2;
                y2=aux;
            }
            for (int i = y1; i <= y2; i++) {
                if(!("sala".equals(matriz[xpos][i].getTipo())||"final".equals(matriz[xpos][i].getTipo())||"inicio".equals(matriz[xpos][i].getTipo()))){
                    matriz[xpos][i].setTipo("pasillo");
                    matriz[xpos][i].conUp();
                    matriz[xpos][i].conDown();
                }
            }
            //Compruebo terminales
            try{
                if("vacio".equals(matriz[xpos][y1-1].getTipo())){
                        matriz[xpos][y1].dconUp();
                }
            }
            catch(java.lang.ArrayIndexOutOfBoundsException error){//si da una excepcion no puede haber nada, cortamos los terminales
                matriz[xpos][y1].dconUp();
            }
            try{
                if("vacio".equals(matriz[xpos][y2+1].getTipo())){
                        matriz[xpos][y2].dconDown();
                }
            }
            catch(java.lang.ArrayIndexOutOfBoundsException error){
                 matriz[xpos][y2].dconDown();
            }
    }
        public static void trazarHorizontal(Celula[][] matriz, int ypos,int x1,int x2){
        if(x1>x2){
                int aux = x1;
                x1=x2;
                x2=aux;
            }
            for (int i = x1; i <= x2; i++) {
                if(!("sala".equals(matriz[i][ypos].getTipo())||"final".equals(matriz[i][ypos].getTipo())||"inicio".equals(matriz[i][ypos].getTipo()))){
                    matriz[i][ypos].setTipo("pasillo");
                    matriz[i][ypos].conLeft();
                    matriz[i][ypos].conRight();
                }
            }
            //Compruebo terminales
            try{
                if("vacio".equals(matriz[x1-1][ypos].getTipo())){
                        matriz[x1][ypos].dconLeft();
                }
            }
            catch(java.lang.ArrayIndexOutOfBoundsException error){//si da una excepcion no puede haber nada, cortamos los terminales
                matriz[x1][ypos].dconLeft();
            }
            try{
                if("vacio".equals(matriz[x2+1][ypos].getTipo())){
                        matriz[x2][ypos].dconRight();
                }
            }
            catch(java.lang.ArrayIndexOutOfBoundsException error){
                 matriz[x2][ypos].dconRight();
            }
            
    }
    public static Celula[][] GenerarMapaLogico(int ancho,int alto,int salas) {
        //Parametros
        ArrayList<ArrayList<Punto>> subgrupos;//necesario para cerrar del todo el mapa
        subgrupos = new ArrayList<>(salas);
        Celula[][] matriz  = new Celula[ancho][alto];
        
        
        //Declaramos el mapa y lo inicializamos
        
        
        for(int i=0;i<ancho;i++){
            for(int j=0;j<alto;j++){
                matriz[i][j] = new Celula();
            }
           
        }
        //Generamos las salas
        Random random = new Random();
        
        for (int i = 0; i < salas; i++) {
            
            int tempx = random.nextInt(ancho);
            int tempy = random.nextInt(alto);
            boolean periferiaocupada=false;
            //Buscamos salas alrededor
            try{
                periferiaocupada|="sala".equals(matriz[tempx+1][tempy].getTipo());
                periferiaocupada|="inicio".equals(matriz[tempx+1][tempy].getTipo());
                periferiaocupada|="final".equals(matriz[tempx+1][tempy].getTipo());
            }
            catch(java.lang.ArrayIndexOutOfBoundsException error){
                
            }
            try{
                periferiaocupada|="sala".equals(matriz[tempx-1][tempy].getTipo());
                periferiaocupada|="inicio".equals(matriz[tempx-1][tempy].getTipo());
                periferiaocupada|="final".equals(matriz[tempx-1][tempy].getTipo());
            }
            catch(java.lang.ArrayIndexOutOfBoundsException error){
                
            }
            try{
                periferiaocupada|="sala".equals(matriz[tempx][tempy+1].getTipo());
                periferiaocupada|="inicio".equals(matriz[tempx][tempy+1].getTipo());
                periferiaocupada|="final".equals(matriz[tempx][tempy+1].getTipo());
            }
            catch(java.lang.ArrayIndexOutOfBoundsException error){
                
            }
            try{
                periferiaocupada|="sala".equals(matriz[tempx][tempy-1].getTipo());
                periferiaocupada|="inicio".equals(matriz[tempx][tempy-1].getTipo());
                periferiaocupada|="final".equals(matriz[tempx][tempy-1].getTipo());
            }
            catch(java.lang.ArrayIndexOutOfBoundsException error){
                
            }
            try{
                periferiaocupada|="sala".equals(matriz[tempx][tempy].getTipo());
                periferiaocupada|="inicio".equals(matriz[tempx][tempy].getTipo());
                periferiaocupada|="final".equals(matriz[tempx][tempy].getTipo());
            }
            catch(java.lang.ArrayIndexOutOfBoundsException error){
                
            }
            
            if(periferiaocupada){
                i--;
                
            }
            else{
                if(i==0)
                    matriz[tempx][tempy].setTipo("final");
                else if(i==1)
                    matriz[tempx][tempy].setTipo("inicio");
                else
                    matriz[tempx][tempy].setTipo("sala");
                
                ArrayList<Punto> grupoinicial= new ArrayList<>(1);
                grupoinicial.add(new Punto(tempx,tempy));
                subgrupos.add(grupoinicial);
            }
        }
        //System.out.print("El subgrupos vale"+subgrupos.toString());
        
        while(subgrupos.size()!=1) {
            
        
            //Unimos las salas
            int xpos = 0, ypos = 0, dist=999999999 , xhacia=0 , yhacia=0;
            boolean encontrado=false;

            for(int i=0;i<alto;i++){
                for(int j=0;j<ancho;j++){

                    if("sala".equals(matriz[j][i].getTipo())||"inicio".equals(matriz[j][i].getTipo())||"final".equals(matriz[j][i].getTipo())){
                        //
                        int indicegrupo=encontrarGrupo(subgrupos,j,i);
                        
                   
                        
                        //buscamos el nodo mas cercano
                        int distancia=1;
                        encontrado=false;
                        while((!encontrado)&&(distancia<dist)){
                            
                            int cursorx=j,cursory=i-distancia;
                            for (int k = 0; k < distancia; k++) {
                                try{
                                    if(("sala".equals(matriz[cursorx][cursory].getTipo())||"inicio".equals(matriz[cursorx][cursory].getTipo())||"final".equals(matriz[cursorx][cursory].getTipo())) && encontrarGrupo(subgrupos,cursorx,cursory)!=indicegrupo ){
                                        encontrado=true;
                                        xpos=j;
                                        ypos=i;
                                        dist=distancia;
                                        xhacia=cursorx;
                                        yhacia=cursory;
                                        break;
                                    }
                                }
                                catch(java.lang.ArrayIndexOutOfBoundsException error){

                                }
                                cursorx++;
                                cursory++;
                            }
                            for (int k = 0; k < distancia; k++) {
                                try{
                                    if(("sala".equals(matriz[cursorx][cursory].getTipo())||"inicio".equals(matriz[cursorx][cursory].getTipo())||"final".equals(matriz[cursorx][cursory].getTipo())) && encontrarGrupo(subgrupos,cursorx,cursory)!=indicegrupo ){
                                        encontrado=true;
                                        xpos=j;
                                        ypos=i;
                                        dist=distancia;
                                        xhacia=cursorx;
                                        yhacia=cursory;
                                        break;
                                    }
                                }
                                catch(java.lang.ArrayIndexOutOfBoundsException error){

                                }
                                cursorx--;
                                cursory++;
                            }
                            for (int k = 0; k < distancia; k++) {
                                try{
                                    if(("sala".equals(matriz[cursorx][cursory].getTipo())||"inicio".equals(matriz[cursorx][cursory].getTipo())||"final".equals(matriz[cursorx][cursory].getTipo())) && encontrarGrupo(subgrupos,cursorx,cursory)!=indicegrupo ){
                                        encontrado=true;
                                        xpos=j;
                                        ypos=i;
                                        dist=distancia;
                                        xhacia=cursorx;
                                        yhacia=cursory;
                                        break;
                                    }
                                }
                                catch(java.lang.ArrayIndexOutOfBoundsException error){

                                } 
                                cursorx--;
                                cursory--;
                            }
                            for (int k = 0; k < distancia; k++) {
                                try{
                                    if(("sala".equals(matriz[cursorx][cursory].getTipo())||"inicio".equals(matriz[cursorx][cursory].getTipo())||"final".equals(matriz[cursorx][cursory].getTipo())) && encontrarGrupo(subgrupos,cursorx,cursory)!=indicegrupo ){
                                        encontrado=true;
                                        xpos=j;
                                        ypos=i;
                                        dist=distancia;
                                        xhacia=cursorx;
                                        yhacia=cursory;
                                        break;
                                    }
                                }
                                catch(java.lang.ArrayIndexOutOfBoundsException error){

                                } 
                                cursorx++;
                                cursory--;
                            }
                            distancia++;
                        }
                    }

                }
            }
            int grupo_nodo_A=encontrarGrupo(subgrupos,xpos,ypos);
            int grupo_nodo_B=encontrarGrupo(subgrupos,xhacia,yhacia);
            //System.out.print("Nodo a encontrado en "+xpos+","+ypos+"\nNodo b encontrado en"+xhacia+","+yhacia+"\n A una distancia de"+dist+"\n");
            //System.out.print("Extra info, el grupo de A ="+encontrarGrupo(subgrupos,xpos,ypos)+" y grupo de B="+encontrarGrupo(subgrupos,xhacia,yhacia));
            //System.out.print("EL segundo grupo es "+subgrupos.get(encontrarGrupo(subgrupos,xhacia,yhacia)).toString());//Uno en el mismo grupo los dos nodos encontrados
            subgrupos.get(grupo_nodo_A).addAll(subgrupos.get(grupo_nodo_B));
            //System.out.print("El subgrupos vale"+subgrupos.toString());
            subgrupos.remove(grupo_nodo_B);
            //System.out.print("El subgrupos vale"+subgrupos.toString());
            
            //Unimos el enlace encontrado
            if(xpos==xhacia){//estan en linea vertical
                trazarVertical(matriz,xpos,ypos,yhacia);
            }
            else if(ypos==yhacia){//estan en linea horizontal
                trazarHorizontal(matriz,ypos,xpos,xhacia);
            }
            else{//no estan en linea
                int verticex,verticey;
                //aleatorizamos el vertice y trazamos la linea
                if(random.nextBoolean()){
                    trazarVertical(matriz,xpos,ypos,yhacia);
                    trazarHorizontal(matriz,yhacia,xpos,xhacia);

                }
                else{
                    trazarVertical(matriz,xhacia,ypos,yhacia);
                    trazarHorizontal(matriz,ypos,xpos,xhacia);
                }
            }
        }
        //Hacemos una pasada buscando errores de conexión y conectando salas
         for(int i=0;i<alto;i++){
            for(int j=0;j<ancho;j++){
            
                if("sala".equals(matriz[j][i].getTipo())||"inicio".equals(matriz[j][i].getTipo())||"final".equals(matriz[j][i].getTipo())){
                    try{
                        if(matriz[j-1][i].getRight())
                            matriz[j][i].conLeft();
                    }
                    catch(java.lang.ArrayIndexOutOfBoundsException error){}
                    try{
                        if(matriz[j+1][i].getLeft())
                            matriz[j][i].conRight();
                    }
                    catch(java.lang.ArrayIndexOutOfBoundsException error){}
                    try{
                        if(matriz[j][i-1].getDown())
                            matriz[j][i].conUp();
                    }
                    catch(java.lang.ArrayIndexOutOfBoundsException error){}
                    try{
                        if(matriz[j][i+1].getUp())
                            matriz[j][i].conDown();
                    }
                    catch(java.lang.ArrayIndexOutOfBoundsException error){}
                    
                }
                if("pasillo".equals(matriz[j][i].getTipo())){   //Fixeo un bug de pasillos no conectados con otros
                    if(matriz[j][i].getDown()){
                        try{
                            if(("pasillo".equals(matriz[j][i+1].getTipo()))&&(!matriz[j][i+1].getUp()))
                                matriz[j][i].dconDown();
                        }
                        catch(java.lang.ArrayIndexOutOfBoundsException error){}
                    }
                    if(matriz[j][i].getUp()){
                        try{
                            if(("pasillo".equals(matriz[j][i-1].getTipo()))&&(!matriz[j][i-1].getDown()))
                                matriz[j][i].dconUp();
                        }
                        catch(java.lang.ArrayIndexOutOfBoundsException error){}
                    }
                    if(matriz[j][i].getLeft()){
                        try{
                            if(("pasillo".equals(matriz[j-1][i].getTipo()))&&(!matriz[j-1][i].getRight()))
                                matriz[j][i].dconLeft();
                        }
                        catch(java.lang.ArrayIndexOutOfBoundsException error){}
                    }
                    if(matriz[j][i].getRight()){
                        try{
                            if(("pasillo".equals(matriz[j+1][i].getTipo()))&&(!matriz[j+1][i].getLeft()))
                                matriz[j][i].dconRight();
                        }
                        catch(java.lang.ArrayIndexOutOfBoundsException error){}
                    }
                }
                
            }
        }
        
        //Mostramos la composicion        
        //mostrarMapa(matriz,ancho,alto);
        return matriz;
    }
    public static void copiarRect(TileLayer desde,int xdesde,int ydesde,TileLayer hasta,int xhasta,int yhasta){
        for (int i = 0; i < (yhasta-ydesde); i++) {
            for (int j = 0; j < (xhasta-xdesde); j++) {
                hasta.setTileAt(xdesde+j, ydesde+i, desde.getTileAt(j, i));
            }
        }
    }
    
}
    