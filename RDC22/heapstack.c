#include <stdio.h>
#include <stdlib.h>

int a;

int fattoriale(int x)
{
	int *p;
	p = (int *)malloc(sizeof(int));
	printf("Static:%lx  Stack: %lx Heap:%lx\n",
				 (unsigned long)&a,
				 (unsigned long)&x,
				 (unsigned long)p);
	if (x <= 1)
		return 1;
	return x * fattoriale(x - 1);
}

int main(int argc, char **argv)
{
	if (argc != 2)
	{
		printf("Usage: %s <num>\n", argv[0]);
		return 0;
	}
	printf("Il fattoriale di %d e' %d\n", atoi(argv[1]), fattoriale(atoi(argv[1])));
}
