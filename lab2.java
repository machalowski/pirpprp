class M_prostokatow extends Thread{
    double suma,a,b,n;

    private static double calka(double x) {
        return x*x*x;
    }
    public M_prostokatow(double a, double b,double n) {
        this.a = a;
        this.b = b;
        this.n = n;
    }

    public void run() {
        double h;
        n = 3;
        h = (b - a) / n;
        suma = 0;
        for (int i = 1; i <= n; i++) {
            suma += calka(a + i * h);
        }
        suma *= h;
        System.out.println("Metoda prostokątów: " + suma);
    }
}

class M_trapezow extends Thread {
    double suma,a,b,n;

    public  M_trapezow(double a, double b,double n) {
        this.a = a;
        this.b = b;
        this.n = n;
    }

    private static double calka(double x) {
        return x*x*x;
    }

    public void run() {
        double h;
        h = (b - a) / n;
        suma = 0;

        for(int i = 1; i < n ; i++){
            suma = suma + calka(a + i * h);
        }
        suma = h * (suma + ((calka(a) + calka(b))) / 2);
        System.out.println("Metoda trapezów: " + suma);

    }
}

class M_simpsona extends Thread {
    double suma,a,b,n;


    public  M_simpsona(double a, double b,double n) {
        this.a = a;
        this.b = b;
        this.n = n;
    }

    private static double calka(double x) {
        return x*x*x;
    }

    public void run() {
        double h,ti;
        h = (b - a) / n;
        suma = 0;
        ti = 0;

        for(int i = 1; i < n; i++){
            ti = ti + calka((a + i * h) - h / 2);
            suma = suma + calka(a + i * h);
        }
        ti = ti + calka(b - h / 2);
        suma = (calka(a) + calka(b) + 2 * suma + 4 * ti)*(h / 6);
        System.out.println("Metoda Simpsona: " + suma);

    }
}

public class lab2 {
    public static void main(String[] args) {

        M_prostokatow prostokat = new M_prostokatow(3,5,3);
        M_trapezow trapez = new M_trapezow(3,5,3);
        M_simpsona simpson = new M_simpsona(3,5,3);

        prostokat.start();
        trapez.start();
        simpson.start();

        try {
            prostokat.join();
            trapez.join();
            simpson.join();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Suma = " + (prostokat.suma + trapez.suma + simpson.suma));
    }

}