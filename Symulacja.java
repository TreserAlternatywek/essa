import javax.swing.JFrame;
import java.awt.GridLayout;
import java.util.Random;

/** Główna klasa programu */
public class Symulacja extends JFrame{
    
    /** instancja klasy {@link Random} używana w aplikacji do generowania losowych liczb*/
    public static Random random;
    /** liczba wierszy */
    public static int rows;
    /** liczba kolumn */
    public static int cols;
    /** szybkośc używana do obliczania opóźnienia zmiany koloru */
    public static double szybkosc;
    /** prawdopodobieństwo z jakim zmieniają się kolory [zakres=(0,100)]*/
    public static double prawdopodobienstwo;
    /** instancja klasy {@link Sync Sync},która synchronizuje plansze */
    public static Sync sync;
    /** dwuwymiarowa tablica zapisująca pola według współrzędnych [y,x] na gridzie */
    public static Field[][] fields;
    /** tablica zapisująca wątki */
    public ColorThread[] threads;

    /** Metoda konfigurująca aplikację
     * Ustawia wielkość,lokalizację,layout
     */
    public void configApp(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 		setSize(1600, 900);
 		setLocation(160,90);
        setLayout(new GridLayout(rows,cols));        
    }
    
    /**
     * Metoda inicjuje tworzenie planszy i pól, oraz wątków
     */
    public void initFields(){
        int i = 0;
        for(int y=0;y<rows;y++){
            for(int x=0;x<cols;x++){
                Field pole = new Field(new int[]{y,x});
                fields[y][x] = pole;

                ColorThread colorThread = new ColorThread(pole);
                threads[i] = colorThread;
                i++;

                pole.thread = colorThread;
                add(pole);
            }
        }
    }

    /**
     * Metoda uruchamia wszystkie wątki
     */
    public void startThreads(){
        for(ColorThread thread: threads){
            thread.start();
        }
    }

    /**
     * Główny konstruktor aplikacji
     * @param rows liczba wierszy
     * @param cols liczba kolumn
     * @param szybkosc szybkość używana do obliczania opóźnienia zmiany koloru
     * @param prawdopodobienstwo prawdopodobieństwo zmiany koloru
     */
    public Symulacja(int rows,int cols, double szybkosc, double prawdopodobienstwo){
        super("symulacja");
        
        Symulacja.rows = rows;
        Symulacja.cols = cols;
        Symulacja.szybkosc = szybkosc;
        Symulacja.prawdopodobienstwo = prawdopodobienstwo;
        Symulacja.random = new Random();
        Symulacja.sync = new Sync();
        Symulacja.fields = new Field[rows][cols];
        threads = new ColorThread[rows*cols];

        configApp();
        initFields();
        startThreads();
        setVisible(true);
    }

    /**
     * Główna metoda pobierająca parametry, za pomocą których tworzony jest program
     * @param args parametry, za pomocą których tworzony jest program
     */
    public static void main(String[] args) {
        try {
            int row = Integer.parseInt(args[0]);
            int cols = Integer.parseInt(args[1]);
            double szybkosc = Double.parseDouble(args[2]);
            double prawdopodobienstwo = Double.parseDouble(args[3]);
            if(row>0&&cols>0&&szybkosc>0&&prawdopodobienstwo>0&&prawdopodobienstwo<100){
                Symulacja sym = new Symulacja(row, cols,szybkosc,prawdopodobienstwo);
            }else{
               System.out.println("proszę o podanie prawidłowych parametrów");
            }
        } catch (Exception e) {
            System.out.println("proszę o podanie prawidłowych parametrów");
        }
    }
}