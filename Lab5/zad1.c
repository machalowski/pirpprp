#include <stdio.h>
#include <stdlib.h>

float Trapezy(float a, float b, int n)
{
    float x;
    float fx;
    float wynik = 0;
    float dx = (b-a)/n;
    float fa = 4 * a - 6 * a + 5;
    float fb = 4 * b - 6 * b + 5;

    for (int i=1; i<=n-1; i++)
    {
        x = a + i * dx;
        fx = 4 * x - 6 * x + 5;
        wynik = wynik + fx;
    }

    return dx * (wynik + (fa + fb) / 2);
}

int main ()
{
    printf("Ilosc procesow: "); 
    int procesy;
    scanf("%d", &procesy);

    for(int i=0; i<procesy; i++)
    {
        if(fork()==0)
        {
            srand(time(NULL) ^ (getpid()<<16));

            int a = rand()%50;
            int b = a + 1 + rand()%50;
            int n = 50 + rand()%50;
            float wynik = Trapezy(a, b, n);

            printf("Przedzial dolny: %d\n", a, b);
            printf("Przedzial gorny: %d\n", b);
            printf("Wynik metoda trapezow: %f\n", wynik);
            exit(0);          
        }
    }
}
