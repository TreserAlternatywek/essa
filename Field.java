import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/** Klasa reprezentująca pojedyncze pole na planszy, posiada MouseAdapter, który zmienia status pola po kliknięciu */
public class Field extends JPanel {
    
    /** Współrzędna x pola w gridzie */
    public int x;
    /** Współrzędna y pola w gridzie */
    public int y;
    /** Status pola (aktywny, nieaktywny) */
    public boolean isActive;
    /** Border, który posiada nieaktywne pole */
    Border blackline;
    /** Border, który posiada aktywne pole */
    Border empty;
    /** Wątek, który jest odpowiedzialny za zmiane koloru konkretnego pola */
    ColorThread thread;

    /**
     * Konstruktor klasy {@link Field Field}
     * @param cords współrzędne [y,x], które posiada pole w gridzie
     */
    public Field(int[] cords){
        x = cords[1];
        y = cords[0];
        isActive = true;
        blackline = BorderFactory.createLineBorder(Color.black);
        empty = BorderFactory.createEmptyBorder();
        addMouseListener(new MouseAdapter(){
            @Override
            public void mousePressed(MouseEvent event){
                synchronized(thread){
                    if(isActive){
                        isActive = false;
                        setBorder(blackline);
                    }  else {
                        thread.notify();
                        isActive = true;
                        setBorder(empty);
                    }
                }
            }
        });
        setBackground(new Color(Symulacja.random.nextInt(256),Symulacja.random.nextInt(256),Symulacja.random.nextInt(256)));
    }

    /**
     * Metoda, zwraca instancje klasy {@link Field Field} sąsiada po lewej stronie.
     * @return instancja klasy {@link Field Field} sąsiada po lewej.
     * @see prawySasiad
     * @see gornySasiad
     * @see dolnySasiad
     */
    public Field lewySasiad(){
        if(x-1>=0){
            return Symulacja.fields[y][x-1];
        }else return Symulacja.fields[y][Symulacja.cols-1];
    }

    /**
     * Metoda, zwraca instancje klasy {@link Field Field} sąsiada po prawej stronie.
     * @return instancja klasy {@link Field Field} sąsiada po prawej.
     * @see lewySasiad
     * @see gornySasiad
     * @see dolnySasiad
     */
    public Field prawySasiad(){
        if(x+1<Symulacja.cols){
            return Symulacja.fields[y][x+1];
        }else return Symulacja.fields[y][0];
    }

    /**
     * Metoda, zwraca instancje klasy {@link Field Field} sąsiada wyżej.
     * @return instancja klasy {@link Field Field} sąsiada wyżej.
     * @see prawySasiad
     * @see lewySasiad
     * @see dolnySasiad
     */
    public Field gornySasiad(){
        if(y-1>=0){
            return Symulacja.fields[y-1][x];
        }else return Symulacja.fields[Symulacja.rows-1][x];
    }

    /**
     * Metoda, zwraca instancje klasy {@link Field Field} sąsiada niżej.
     * @return instancja klasy {@link Field Field} sąsiada niżej.
     * @see lewySasiad
     * @see gornySasiad
     * @see prawySasiad
     */
    public Field dolnySasiad(){
        if(y+1<Symulacja.rows){
            return Symulacja.fields[y+1][x];
        }else return Symulacja.fields[0][x];
    }

}
