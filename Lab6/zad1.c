#include <stdio.h>
#include <math.h>
#include "mpi.h"

int main(int argc, char **argv)
{
    int rank, size;
    MPI_Init( &argc, &argv );
    MPI_Comm_size( MPI_COMM_WORLD, &size );
    MPI_Comm_rank( MPI_COMM_WORLD, &rank );
    MPI_Status status;

    float wynik = 0;

    if(rank!=0)
    {
        MPI_Recv(&wynik, 1, MPI_FLOAT, rank-1, 25, MPI_COMM_WORLD, &status);
    }

    wynik = wynik + powf(-1, rank) / (2 * (rank+1) - 1);

    if(rank!=size-1)
    {
        MPI_Send(&wynik, 1, MPI_FLOAT, rank+1, 25, MPI_COMM_WORLD);
    }
    else
    {
        printf("%f\n",4 * wynik);
    }
    
    MPI_Finalize();
    return 0;
}
