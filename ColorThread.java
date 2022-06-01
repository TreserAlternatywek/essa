
/** Klasa wątku odpowiedzialnego za pola, rozszerza {@link Thread Thread} */
public class ColorThread extends Thread{
    
    /** pole, za które odpowiedzialny jest wątek */
    Field pole;

    /**
     * 
     * @param pole pole, za które odpowiedzialny jest wątek
     */
    ColorThread(Field pole){
        this.pole = pole;
    }

    /** Metoda, która uruchamia się po starcie wątku. Oblicza prawdopodobieństwo oraz opóźnienie.
     * Usypia wątek gdy pole, za które jest odpowiedizalny jest nieaktywne.
     * Synchronicznie zmienia kolor pola, za pomocą klasy {@link Sync Sync} */
    @Override
	public void run()
	{
        while(true){
            synchronized(this){
                if(!pole.isActive){
                    try {
                        wait();
                    } catch (InterruptedException e) {}
                }
            }
            double randomValue = 1 + (100 - 1) * Symulacja.random.nextDouble();
            double opoznienie = 0.5*Symulacja.szybkosc + (1.5*Symulacja.szybkosc - 0.5*Symulacja.szybkosc) * Symulacja.random.nextDouble();
            if(randomValue<=Symulacja.prawdopodobienstwo){
                System.out.println("Zaczynam: "+pole.x+" "+pole.y);
                Symulacja.sync.changeColor(pole);
                System.out.println("Koncze: "+pole.x+" "+pole.y);
            }
            try {
                sleep((long)opoznienie);
            } catch (Exception e) {}
        }
	}
}
