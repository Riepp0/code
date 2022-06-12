#include<stdio.h>

void raddoppia(int* y) {
	*y = *y * 2;
}

int main()
{
	int x;
	x = 3;
	raddoppia(&x);
	printf("Vorrei che %d fosse 6\n",x);
}
