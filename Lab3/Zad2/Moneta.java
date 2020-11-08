import java.util.Random;
import java.util.concurrent.Semaphore;

    class Rzut extends Thread {

        static Semaphore[] widelec;
        int mojNum;
        Random losuj;

        public Rzut(int nr)
        {
            mojNum = nr;
            losuj = new Random(mojNum);
        }

        public void run() {
            while (true) {
                System.out.println("Mysle ¦ " + mojNum);

                try
                {
                    Thread.sleep((long) (7000 * Math.random()));
                }
                catch (InterruptedException e)
                {
                }
                int strona = losuj.nextInt(2);
                boolean podnioslDwaWidelce = false;

                do {
                    if (strona == 0)
                    {
                        widelec[mojNum].acquireUninterruptibly();
                        if (!(widelec[(mojNum + 1) % widelec.length].tryAcquire()))
                        {
                            widelec[mojNum].release();
                        }
                        else
                        {
                            podnioslDwaWidelce = true;
                        }
                    }
                    else
                    {
                        widelec[(mojNum + 1) % widelec.length].acquireUninterruptibly();
                        if (!(widelec[mojNum].tryAcquire()))
                        {
                            widelec[(mojNum + 1) % widelec.length].release();
                        }
                        else
                        {
                            podnioslDwaWidelce = true;
                        }
                    }
                }

                while (!podnioslDwaWidelce);
                System.out.println("Zaczyna jesc " + mojNum);
                try
                {
                    Thread.sleep((long)(5000 * Math.random()));
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

    public class Moneta  {
        public Moneta(int ilosc)
        {
            Rzut.widelec = new Semaphore [ilosc];

            for ( int i =0; i<ilosc; i++)
            {
                Rzut.widelec [ i ]=new Semaphore ( 1 ) ;
            }

            for ( int i =0; i<ilosc; i++)
            {
                new Rzut(i).start();
            }
        }

    }