public class Zajezdnia {

    static int ZAJEZDNIA = 1;
    static int START = 2;
    static int TRASA =3;
    static int KONIEC_TRASY = 4;
    static int KATASTROFA = 5;
    int ilosc_torow;
    int ilosc_zajetych;
    int ilosc_pociagow;

    Zajezdnia(int ilosc_torow,int ilosc_pociagow)
    {
        this.ilosc_torow =ilosc_torow;
        this.ilosc_pociagow =ilosc_pociagow;
        this.ilosc_zajetych=0;
    }

    synchronized int start(int numer)
    {
        ilosc_zajetych--;
        System.out.println("Pozwolenie na wyjazd z zajedni " + numer);
        return START;
    }

    synchronized int laduj()
    {
        try
        {
            Thread.currentThread().sleep(1000);
        }
        catch(Exception ie)
        {
        }

        if(ilosc_zajetych< ilosc_torow)
        {
            ilosc_zajetych++;
            System.out.println("Pozwolenie na zjazd na tor "+ilosc_zajetych);
            return ZAJEZDNIA;
        }
        else
        {
            return KONIEC_TRASY;
        }
    }

    synchronized void zmniejsz()
    {
        ilosc_pociagow--;
        System.out.println("Kraksa");
        if(ilosc_pociagow == ilosc_torow) System.out.println("Ilosc pociagow jaka sama jak torow");
    }
}