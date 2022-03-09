#include <stdio.h>

int main(){
	int a = 1;
	char* p = (char*)&a;
	printf("%x", *p);
	if(*p == 0)
        printf("Il sistema e' big endian");
    else
        printf("Il sistema e' little endian");
}