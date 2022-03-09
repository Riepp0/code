#include <stdio.h>

int fattoriale(int x){
    if(x == 1) 
        return 1;
    else
        return x * fattoriale(x - 1);
}

int main(int argc, char **argv){
    if(argc != 2){
        printf("Usage: %s <num>\n", argv[0]);
        return 0;
    }
    printf("Il fattoriale di %d e' %d", (atoi(argv[1])), fattoriale(atoi(argv[1])));
}