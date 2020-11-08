import java.util.Random;

public class Pociag extends Thread {
    
    static int ZAJEZDNIA=1;
    static int START=2;
    static int TRASA =3;
    static int KONIEC_TRASY =4;
    static int KATASTROFA=5;
    static int ILOSC_WEGLA =1000;
    static int REZERWA=500;

    int numer;
    int paliwo;
    int stan;
    Zajezdnia l;
    Random rand;

    public Pociag(int numer, int paliwo, Zajezdnia l)
    {
        this.numer=numer;
        this.paliwo=paliwo;
        this.stan= TRASA;
        this.l=l;
        rand=new Random();
    }

    public void run()
    {
        while(true){
            if(stan==ZAJEZDNIA)
            {
                if(rand.nextInt(2)==1)
                {
                    stan=START;
                    paliwo= ILOSC_WEGLA;
                    System.out.println("Prosze o pozwolenie na wyjazd z zajezdni"+numer);
                    stan=l.start(numer);
                }
                else
                {
                    System.out.println("Odpoczne jeszcze");
                }
            }
            else if(stan==START)
            {
                System.out.println("Wyjezdzam "+numer);
                stan= TRASA;
            }
            else if(stan== TRASA)
            {
                paliwo-=rand.nextInt(500);
                System.out.println("Pociag "+numer+" w trasie");
                if(paliwo<=REZERWA)
                {
                    stan= KONIEC_TRASY;
                }
                else try
                {
                    sleep(rand.nextInt(1000));
                }
                catch (Exception e){}
            }
            else if(stan== KONIEC_TRASY)
            {
                System.out.println("Zjezdzam do zajezdnii "+numer+" ilosc wegla "+paliwo);
                stan=l.laduj();
                if(stan== KONIEC_TRASY)
                {
                    paliwo-=rand.nextInt(500);
                    System.out.println("REZERWA "+paliwo);
                    if(paliwo<=0) stan=KATASTROFA;
                }
            }
            else if(stan==KATASTROFA)
            {
                System.out.println("KATASTROFA samolot "+numer);
                l.zmniejsz();
            }
        }
    }
}