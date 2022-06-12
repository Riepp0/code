#include <stdio.h>

int main()
{
	// 4 bytes var
	int a = 1;
	// 1 byte pointer
	char *p = (char *)&a;
	// test
	if (!*p)
		printf("Il sistema è big endian\n");
	else
		printf("Il sistema è little endian\n");
}
