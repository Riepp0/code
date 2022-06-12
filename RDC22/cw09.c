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

int main()
{
	int n;
	char *request = "GET /eolomammolo: \r\n";
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
	size_t len = 0;
	for (len = 0; (n = read(s, response + len, 1000000 - len)) > 0; len += n)
		;
	if (n == -1)
	{
		perror("Read fallita");
		return -1;
	}
	while (n = read(s, response + len, 1000000 - len))
	{
		len += n;
		// printf("%d bytes estratti\n",n);
	}
	printf("%s\n", response);
}
