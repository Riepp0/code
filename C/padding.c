#include <stdio.h>
#include <stdint.h>

struct prova{
    int8_t c;
    int64_t x;
};

int main(){
    struct prova v;
    printf("&c = %lx, &x = %lx\n",(unsigned long) &v.c,(unsigned long) &v.x);
    printf("Sizeof(v) = %ld\n", sizeof(v));
}