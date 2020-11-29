#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <time.h>
#include "mpi.h"

#define REZERWA 500
#define ZAJEZDNIA 1
#define START 2
#define TRASA 3
#define KONIEC_TRASY 4
#define WYPADEK 5
#define TANKUJ 5000

int paliwo = 5000;
int POSTOJ=1, NIE_STOJ=0;
int liczba_procesow;
int nr_procesu;
int ilosc_autobusow;
int ilosc_tras=4;
int ilosc_zajetych_tras=0;
int tag=1;
int wyslij[2];
int odbierz[2];
MPI_Status mpi_status;

void Wyslij(int nr_autobusu, int stan) 
{
    wyslij[0]=nr_autobusu;
    wyslij[1]=stan;
    MPI_Send(&wyslij, 2, MPI_INT, 0, tag, MPI_COMM_WORLD);
    sleep(1);
}

void Zajezdnia(int liczba_procesow)
{
    int nr_autobusu,status;
    ilosc_autobusow = liczba_procesow - 1;

    printf("Zyczymy Panstwu, przyjemnej podrozy \n");
    printf("Dysponujemy %d trasami\n", ilosc_tras);
    sleep(2);

    while(ilosc_tras<=ilosc_autobusow)
        {
            MPI_Recv(&odbierz,2,MPI_INT,MPI_ANY_SOURCE,tag,MPI_COMM_WORLD, &mpi_status);
            nr_autobusu=odbierz[0];
            status=odbierz[1];
            if(status==1)
            {
                printf("Autobus %d stoi w zajezdni\n", nr_autobusu);
            }
            if(status==2)
            {
                printf("Autobus %d wyjezdza na trase nr %d\n", nr_autobusu, ilosc_zajetych_tras);
                ilosc_zajetych_tras--;
            }
            if(status==3)
            {
                printf("Autobus %d w trasie\n", nr_autobusu);
            }
            if(status==4)
            {
                if(ilosc_zajetych_tras<ilosc_tras)
                {
                    ilosc_zajetych_tras++;
                    MPI_Send(&POSTOJ, 1, MPI_INT, nr_autobusu, tag, MPI_COMM_WORLD);
                }
                else
                {
                    MPI_Send(&NIE_STOJ, 1, MPI_INT, nr_autobusu, tag, MPI_COMM_WORLD);
                }
            }
            if(status==5)
            {
                ilosc_autobusow--;
                printf("Ilosc autobusow %d\n", ilosc_autobusow);
            }
        }
    printf("Program zakonczyl dzialanie:)\n");
}

void Autobus()
{
    int stan,suma,i;
    stan=TRASA; 
    while(1)
    {
        if(stan==1)
        {
            if(rand()%2==1)
            {
                stan=START;
                paliwo=TANKUJ;
                printf("Prosze o pozwolenie na wyjazd w trase, autobus %d",nr_procesu);
                printf("\n");
                Wyslij(nr_procesu,stan);
            }
            else
            {
                Wyslij(nr_procesu,stan);
            }
        }
        else if(stan==2)
        {
            printf("Wyjezdzam na trase, autobus %d\n",nr_procesu);
            stan=TRASA;
            Wyslij(nr_procesu,stan);
        }
        else if(stan==3)
        {
            paliwo-=rand()%500; 
            if(paliwo<=REZERWA)
            {
                stan=KONIEC_TRASY;
                printf("Prosze o pozwolenie na zjazd do zajezdzni\n");
                Wyslij(nr_procesu,stan);
            }

            else
            {
                for(i=0; rand()%10000;i++);
            }
        }
        else if(stan==4)
        {
            int temp;
            MPI_Recv(&temp, 1, MPI_INT, 0, tag, MPI_COMM_WORLD, &mpi_status);
            if(temp==POSTOJ)
            {
                stan=ZAJEZDNIA;
                printf("Zakonczylem trase, autobus %d\n", nr_procesu);
            }
            else
            {
                paliwo-=rand()%500;
                if(paliwo>0)
                {
                    Wyslij(nr_procesu,stan);
                }
                else
                {
                    stan=WYPADEK;
                    printf("Mialem wypadek\n");
                    Wyslij(nr_procesu,stan);
                return;
                }
            }
        }
    }
}


int main(int argc, char *argv[])
{
    MPI_Init(&argc, &argv);
    MPI_Comm_rank(MPI_COMM_WORLD,&nr_procesu);
    MPI_Comm_size(MPI_COMM_WORLD,&liczba_procesow);
    srand(time(NULL));
    if(nr_procesu == 0) 
    Zajezdnia(liczba_procesow);
    else 
    Autobus();
    MPI_Finalize();
    return 0;
}
