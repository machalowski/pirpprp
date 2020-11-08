import java.util.Scanner;

public class zad2 {

    public static void main (String[] args) {

        Scanner s = new Scanner(System.in);

        System.out.print("Podaj liczbe filozofow od 2 do 100: ");
        int ilosc = s.nextInt();


        System.out.println("1. Semafory");
        System.out.println("2. Niesymetryczne");
        System.out.println("3. Moneta");
        System.out.print("Wybierz metode: ");
        int wybor = s.nextInt();

        switch (wybor) {
            case 1:
                Semafor semafor = new Semafor(ilosc);
                break;

            case 2:
                Niesymetryczni niesymetryczni = new Niesymetryczni(ilosc);
                break;

            case 3:
                Moneta moneta = new Moneta(ilosc);
                break;

            default:
                System.out.println("Nieprawidlowy numer");
        }
    }
}
