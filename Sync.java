import java.awt.Color;

/** Klasa synchronizująca całą plansze */
public class Sync {
    
    /**
     * Synchroniczna metoda, tworzy nowy kolor uśredniając kolory aktywnych sąsiadów
     * @param pole instancja klasy {@link Field Field}, dla której obliczamy nowy kolor
     * @return obiekt klasy {@link Color}, zdefiniowany za pomocą rgb aktywnych sąsiadów pola
     */
    synchronized public Color getColors(Field pole){
        int r = 0;
        int g = 0;
        int b = 0;
        int i = 0;
        if(pole.lewySasiad().isActive){
            r += pole.lewySasiad().getBackground().getRed();
            b += pole.lewySasiad().getBackground().getBlue();
            g += pole.lewySasiad().getBackground().getGreen();
            i++;
        }
        if(pole.prawySasiad().isActive){
            r += pole.prawySasiad().getBackground().getRed();
            b += pole.prawySasiad().getBackground().getBlue();
            g += pole.prawySasiad().getBackground().getGreen();
            i++;
        }
        if(pole.gornySasiad().isActive){
            r += pole.gornySasiad().getBackground().getRed();
            b += pole.gornySasiad().getBackground().getBlue();
            g += pole.gornySasiad().getBackground().getGreen();
            i++;
        }
        if(pole.dolnySasiad().isActive){
            r += pole.dolnySasiad().getBackground().getRed();
            b += pole.dolnySasiad().getBackground().getBlue();
            g += pole.dolnySasiad().getBackground().getGreen();
            i++;    
        }
        if(i!=0) return new Color(r/i,g/i,b/i); else return pole.getBackground();
    }

    /**
     * Synchroniczna metoda, która zmienia kolor pola
     * @param pole pole, dla którego zmieniamy kolor
     */
    synchronized public void changeColor(Field pole){
        pole.setBackground(getColors(pole));
    }
}
