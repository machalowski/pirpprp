public class zad3 {
    static int ilosc_pociagow = 5;
    static int ilosc_torow = 5;
    static Zajezdnia zajezdnia;

    public zad3(){ }

    public static void main(String[] args) {

        zajezdnia = new Zajezdnia(ilosc_torow, ilosc_pociagow);

        for(int i = 0; i < ilosc_pociagow; i++)
            new Pociag(i,2000,zajezdnia).start();
    }
}