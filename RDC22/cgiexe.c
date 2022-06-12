#include <unistd.h>
#include <string.h>
#include <stdlib.h>
#include <stdio.h>
int main(int argc, char *argv[], char *env[])
{
	int i, j, t;
	int length;
	char line[500], *key, *value, *buffer;
	// fgets(line,500,stdin);

	// printf("Io sono il cgiexe e ho letto questa riga da stdin: %s",line);
	printf("E ho questo environment:\n");
	for (i = 0; env[i]; i++)
	{
		key = env[i];
		for (j = 0; env[i][j] != '='; j++)
			;
		env[i][j] = 0;
		value = env[i] + j + 1;
		printf("key:%s, value:%s\n", key, value);
		if (!strcmp(key, "CONTENT_LENGTH"))
			length = atoi(value);
	}
	buffer = malloc(length);
	for (i = 0; i < length && (t = read(0, buffer + i, length - i)); i += t)
		;
	printf("Body received\n");
	for (i = 0; i < length && (t = write(1, buffer + i, length - i)); i += t)
		;

	printf("Ciao, muoio\n");
}
