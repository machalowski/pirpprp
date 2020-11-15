#include <stdio.h>
#include <stdlib.h>
#include <math.h>

float PI(int n)
{
    float suma = 0;
    float wynik;
    for (int i=1; i<=n-1; i++)
    {
        wynik = powf(-1, i-1) / (2 * i - 1);
        suma = suma + wynik;
    }
    return 4 * suma;
}

int main ()
{
    int procesy;
    printf("Ilosc procesorow: ");
    
    scanf("%d", &procesy);

    for(int i=0; i<procesy; i++)
    {
        if(fork()==0)
        {
            srand(time(NULL) ^ (getpid()<<16));
            int n = 100 + rand()%5000;
            printf("N = %d\n", n);
            float wynik = PI(n);
            printf("Pi = %f\n", wynik);
            exit(0);
        }
    }
}
