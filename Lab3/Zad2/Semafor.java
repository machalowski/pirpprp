import java.util.concurrent.Semaphore;

    class Semafory extends Thread {

        static Semaphore[] widelec;
        int mojNum;

        public Semafory(int nr)
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

                widelec[mojNum].acquireUninterruptibly();
                widelec[(mojNum + 1) % widelec.length].acquireUninterruptibly();

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

    public class Semafor {

        public Semafor(int ilosc)
        {
            Semafory.widelec = new Semaphore [ilosc];

            for ( int i =0; i<ilosc; i++)
            {
                Semafory.widelec [ i ]=new Semaphore ( 1 ) ;
            }

            for ( int i =0; i<ilosc; i++)
            {
                new Semafory(i).start();
            }
        }
    }
