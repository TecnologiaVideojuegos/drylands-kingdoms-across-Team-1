package proceduralTiled;


import proceduralTiled.Celula;
import proceduralTiled.PruebaProcedural;
import org.newdawn.slick.Animation;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.tiled.TiledMap;

/**
 * A test for basic animation rendering
 *
 * @author kevin
 */
public class TileMapTest extends BasicGame {

    /**
     * The tile map we're going to load and render
     */
    private TiledMap salaTile;
    private TiledMap pasCruz, pasHor, pasVer, pasT, pasTInv, pasTIz, pasTDer, pasSupDer, pasSupIz, pasInfIz, pasInfDer;
    /**
     * the name of the map, read from map properties, specified by TilED
     */
    private String mapName;

    /**
     * how hard are the monsters, read from layer properties, specified by TilED
     */
    private String monsterDifficulty;

    /**
     * we try to read a property from the map which doesn't exist so we expect
     * the default value
     */
    private String nonExistingMapProperty;

    /**
     * we try to read a property from the layer which doesn't exist so we expect
     * the default value
     */
    private String nonExistingLayerProperty;

    /**
     * how long did we wait already until next update
     */
    private int updateCounter = 0;

    /**
     * changing some tile of the map every UPDATE_TIME milliseconds
     */
    private static int UPDATE_TIME = 1000;

    /**
     * we want to store the originalTileID before we set a new one
     */
    private int originalTileID = 0;

    /**
     * Create our tile map test
     */
    private int[][] sala = new int[3][2];

    private TiledMap[] arrayPasillos = {pasCruz, pasHor, pasVer, pasT, pasTInv, pasTDer, pasTIz, pasSupIz, pasSupDer, pasInfDer, pasInfIz};
    private static int tamSala = 192;
    private int nSalas = 7;
    private static int alto = 6, ancho = 6;
    private int[][] pasillos = new int[alto * ancho - nSalas][3];

    public TileMapTest() {
        super("Tile Map Test");
    }

    /**
     * @see org.newdawn.slick.BasicGame#init(org.newdawn.slick.GameContainer)
     */
    public void init(GameContainer container) throws SlickException {
        
        salaTile = new TiledMap("sala.tmx", "src");
        pasCruz = new TiledMap("pasillocruz.tmx", "src");
        pasHor = new TiledMap("pasillohor.tmx", "src");
        pasVer = new TiledMap("pasillover.tmx", "src");
        pasT = new TiledMap("pasilloT.tmx", "src");
        pasTInv = new TiledMap("pasilloTreves.tmx", "src");
        pasTDer = new TiledMap("pasilloTder.tmx", "src");
        pasTIz = new TiledMap("pasilloTiz.tmx", "src");
        pasSupIz = new TiledMap("pasillosupiz.tmx", "src");
        pasSupDer = new TiledMap("pasillosupder.tmx", "src");
        pasInfDer = new TiledMap("pasilloinfder.tmx", "src");
        pasInfIz = new TiledMap("pasilloinfiz.tmx", "src");

        //map2=new TiledMap("testdata/prueba.tmx","testdata");
        PruebaProcedural.setAlto(alto);
        PruebaProcedural.setAncho(ancho);
        PruebaProcedural.setSalas(nSalas);
        PruebaProcedural.generarSalas();
        sala = PruebaProcedural.getSala();
        pasillos = PruebaProcedural.getPasillo();

        
    }

    /**
     * @see org.newdawn.slick.BasicGame#render(org.newdawn.slick.GameContainer,
     * org.newdawn.slick.Graphics)
     */
    public void render(GameContainer container, Graphics g) {
        for (int i = 0; i < nSalas; i++) {
            salaTile.render(tamSala * sala[i][0], tamSala * sala[i][1]);
        }
        for (int i = 0; i < pasillos.length; i++) {
            int tipo = pasillos[i][2];
            if (tipo == 1) {
                pasCruz.render(tamSala * pasillos[i][0], tamSala * pasillos[i][1]);
            }
            else if (tipo == 2) {
                pasTIz.render(tamSala * pasillos[i][0], tamSala * pasillos[i][1]);
            }
            else if (tipo == 3) {
                pasTDer.render(tamSala * pasillos[i][0], tamSala * pasillos[i][1]);
            }
            else if (tipo == 4) {
                pasVer.render(tamSala * pasillos[i][0], tamSala * pasillos[i][1]);
            }
            else if (tipo == 5) {
                pasTInv.render(tamSala * pasillos[i][0], tamSala * pasillos[i][1]);
            }
            else if (tipo == 6) {
                pasInfDer.render(tamSala * pasillos[i][0], tamSala * pasillos[i][1]);
            }
            else if (tipo == 7) {
                pasInfIz.render(tamSala * pasillos[i][0], tamSala * pasillos[i][1]);
            }
            else if (tipo == 8) {
                pasT.render(tamSala * pasillos[i][0], tamSala * pasillos[i][1]);
            }
            else if (tipo == 9) {
                pasSupDer.render(tamSala * pasillos[i][0], tamSala * pasillos[i][1]);
            }
            else if (tipo == 10) {
                pasSupIz.render(tamSala * pasillos[i][0], tamSala * pasillos[i][1]);
            }
            else if (tipo == 11){
                pasHor.render(tamSala * pasillos[i][0], tamSala * pasillos[i][1]);
            }
            //arrayPasillos[pasillos[0][2]].render(tamSala*pasillos[0][0],tamSala*pasillos[0][1]);
        }
        g.scale(0.35f, 0.35f);

        g.resetTransform();

        g.drawString("map name: " + mapName, 10, 500);
        g.drawString("monster difficulty: " + monsterDifficulty, 10, 550);

        g.drawString("non existing map property: " + nonExistingMapProperty, 10, 525);

        g.drawString("non existing layer property: " + salaTile.getTileSet(0), 10, 575);
    }

    /**
     * @see org.newdawn.slick.BasicGame#update(org.newdawn.slick.GameContainer,
     * int)
     */
    public void update(GameContainer container, int delta) {
        updateCounter += delta;
       
    }

    /**
     * @see org.newdawn.slick.BasicGame#keyPressed(int, char)
     */
    public void keyPressed(int key, char c) {
        if (key == Input.KEY_ESCAPE) {
            System.exit(0);
        }
    }

    /**
     * Entry point to our test
     *
     * @param argv The arguments passed to the test
     */
    public static void main(String[] argv) {
        try {
            AppGameContainer container = new AppGameContainer(new TileMapTest());
            container.setDisplayMode(tamSala*alto, tamSala*ancho, false);
            container.start();
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

}
