import java.util.concurrent.Semaphore;

class Niesymetryczny extends Thread {

    static Semaphore[] widelec;
    int mojNum;

    public Niesymetryczny(int nr)
    {
        mojNum = nr;
    }

    public void run()
    {
        while (true)
        {
            System.out.println("Mysle ¦ " + mojNum);
            try
            {
                Thread.sleep((long) (7000 * Math.random()));
            }
            catch (InterruptedException e)
            {
            }

            if (mojNum == 0)
            {
                widelec[(mojNum + 1) % widelec.length].acquireUninterruptibly();
                widelec[mojNum].acquireUninterruptibly();
            }
            else
            {
                widelec[mojNum].acquireUninterruptibly();
                widelec[(mojNum + 1) % widelec.length].acquireUninterruptibly();
            }

            System.out.println("Zaczyna jesc " + mojNum);
            try
            {
                Thread.sleep((long) (5000 * Math.random()));
            }
            catch (InterruptedException e)
            {
            }

            System.out.println("Konczy jesc " + mojNum);

            widelec[mojNum].release();
            widelec[(mojNum + 1) % widelec.length].release();
        }
    }
}

public class Niesymetryczni {

    public Niesymetryczni(int ilosc)
    {
        Niesymetryczny.widelec = new Semaphore [ilosc];

        for ( int i =0; i<ilosc; i++)
        {
            Niesymetryczny.widelec [ i ]=new Semaphore ( 1 ) ;
        }

        for ( int i =0; i<ilosc; i++)
        {
            new Niesymetryczny(i).start();
        }
    }
}
