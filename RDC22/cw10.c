#include <stdio.h>
#include <string.h>
#include <sys/types.h> /* See NOTES */
#include <sys/socket.h>
#include <errno.h>
#include <arpa/inet.h>
#include <stdint.h>
#include <unistd.h>

struct sockaddr_in remote;
char response[1000001];
struct header
{
	char *n;
	char *v;
} h[100];

int main()
{
	size_t len = 0;
	int i, j, n;
	char *request = "GET /errore HTTP/1.0\r\n\r\n";
	char *statusline;
	char hbuffer[10000];
	unsigned char ipserver[4] = {142, 250, 180, 3};
	int s;
	if ((s = socket(AF_INET, SOCK_STREAM, 0)) == -1)
	{
		printf("errno = %d\n", errno);
		perror("Socket Fallita");
		return -1;
	}
	remote.sin_family = AF_INET;
	remote.sin_port = htons(80);
	remote.sin_addr.s_addr = *((uint32_t *)ipserver);
	if (-1 == connect(s, (struct sockaddr *)&remote, sizeof(struct sockaddr_in)))
	{
		perror("Connect Fallita");
		return -1;
	}
	write(s, request, strlen(request));
	statusline = h[0].n = hbuffer;
	for (i = 0, j = 0; read(s, hbuffer + i, 1); i++)
	{
		if (hbuffer[i] == '\n' && hbuffer[i - 1] == '\r')
		{
			hbuffer[i - 1] = 0; // Termino il token attuale
			if (!h[j].n[0])
				break;
			h[++j].n = hbuffer + i + 1;
		}
		if (hbuffer[i] == ':' && !h[j].v)
		{
			hbuffer[i] = 0;
			h[j].v = hbuffer + i + 1;
		}
	}
	for (i = 1; i < j; i++)
		printf("%s ---> %s\n", h[i].n, h[i].v);

	for (len = 0; (n = read(s, response + len, 1000000 - len)) > 0; len += n)
		;
	if (n == -1)
	{
		perror("Read fallita");
		return -1;
	}
	response[len] = 0;
	printf("%s\n", response);
}
