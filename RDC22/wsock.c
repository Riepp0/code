#include <openssl/sha.h>
#include <stdlib.h>
#include <unistd.h>
#include <errno.h>
#include <stdio.h>
#include <string.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <netinet/ip.h> /* superset of previous */
#include <arpa/inet.h>

unsigned char line[501], wsk[50], key[50], token[50];
char encode(unsigned char c)
{
	if (c < 26)
		return c + 'A';
	if (c < 52)
		return c - 26 + 'a';
	if (c < 62)
		return c - 52 + '0';
	if (c == 62)
		return '+';
	if (c == 63)
		return '/';
	else
		return '?';
}

int base64(unsigned char *in, char *out, int lungh)
{
	long int *a;
	long int par = 0;
	char reb[4] = "";
	int i = 0, cont = 0;

	reb[3] = in[0];
	reb[2] = in[1];
	reb[1] = in[2];

	a = (long int *)reb;
	par = *a;

	out[3] = encode((char)(par >> 8) & 63);
	out[2] = encode((char)(par >> 14) & 63);
	out[1] = encode((char)(par >> 20) & 63);
	out[0] = encode((char)(par >> 26) & 63);
	return 4;
}

void mask(unsigned char *buf, int buflen, unsigned char *msk, int msklen)
{
	while (buflen--)
		buf[buflen] = buf[buflen] ^ msk[buflen % msklen];
}

int b64(unsigned char *inbuf, char *output)
{
	int in_len = 0, out_len = 0;
	int i, x;
	int quanti_per_riga = 0;
	char c;
	unsigned char input[3];
	for (; *inbuf; inbuf++)
	{ // wait for terminator
		input[in_len] = *inbuf;
		if (in_len == 2)
		{
			x = base64(input, output, 3);
			in_len = 0;
			output += x;
		}
		else
			in_len = in_len + 1;
	} // wait for terminator

	if (in_len == 2)
	{
		input[2] = 0;
		x = base64(input, output, 2);
		output += 3;
		*(output++) = '=';
		*(output++) = 0;
	}
	if (in_len == 1)
	{
		input[1] = input[2] = 0;
		x = base64(input, output, 2);
		output += 2;
		*(output++) = '=';
		*(output++) = '=';
		*(output++) = 0;
	}
}

char *p;
int lunghezza;
int yes = 1;
struct header
{
	char *n;
	char *v;
};
struct header h[100];
struct sockaddr_in indirizzo;
struct sockaddr_in indirizzo_remoto;
int primiduepunti;
char request[10000];
char response[10000];
char *request_line;
char *method, *uri, *http_ver;
int c;
FILE *fin;
int idazien;
int main()
{
	int s, s2, s3, t, j, i;
	char command[1000];
	s = socket(AF_INET, SOCK_STREAM, 0);
	if (s == -1)
	{
		perror("Socket Fallita");
		return 1;
	}
	if (setsockopt(s, SOL_SOCKET, SO_REUSEADDR, &yes, sizeof(int)) == -1)
	{
		perror("setsockopt");
		return 1;
	}
	indirizzo.sin_family = AF_INET;
	indirizzo.sin_port = htons(8181);
	indirizzo.sin_addr.s_addr = 0;

	t = bind(s, (struct sockaddr *)&indirizzo, sizeof(struct sockaddr_in));
	if (t == -1)
	{
		perror("Bind fallita");
		return 1;
	}
	t = listen(s, 10);
	if (t == -1)
	{
		perror("Listen Fallita");
		return 1;
	}
	while (1)
	{
		lunghezza = sizeof(struct sockaddr_in);
		s2 = accept(s, (struct sockaddr *)&indirizzo_remoto, &lunghezza);
		if (s2 == -1)
		{
			perror("Accept Fallita");
			return 1;
		}
		if (fork())
			continue; // if parent go to next accept....

		// if son  manage the connection

		h[0].n = request;
		request_line = h[0].n;
		h[0].v = h[0].n;
		for (i = 0, j = 0; (t = read(s2, request + i, 1)) > 0; i++)
		{
			printf("%c", request[i]);
			if ((i > 1) && (request[i] == '\n') && (request[i - 1] == '\r'))
			{
				primiduepunti = 1;
				request[i - 1] = 0;
				if (h[j].n[0] == 0)
					break;
				h[++j].n = request + i + 1;
			}
			if (primiduepunti && (request[i] == ':'))
			{
				h[j].v = request + i + 1;
				request[i] = 0;
				primiduepunti = 0;
			}
		}
		if (t == -1)
		{
			perror("Read Fallita");
			return 1;
		}
		wsk[0] = 0;
		for (i = 1; i < j; i++)
		{
			if (!strcmp("Sec-WebSocket-Key", h[i].n))
			{
				printf("*");
				strcpy(wsk, h[i].v + 1);
			}
			printf("%s ===> %s\n", h[i].n, h[i].v);
		}
		if (wsk[0])
		{ // E' un web socket!
			strcat(wsk, "258EAFA5-E914-47DA-95CA-C5AB0DC85B11");
			SHA1(wsk, strlen(wsk), key);
			b64(key, token);
			sprintf(response, "HTTP/1.1 101 Switching Protocols\r\nUpgrade: WebSocket\r\nConnection: Upgrade\r\nSec-WebSocket-Accept:%s\r\n\r\n", token);
			printf("%s", response);
			t = write(s2, response, strlen(response));
			t = read(s2, response, strlen(response));
			printf("Response:");
			for (int u = 0; u < t; u++)
				printf("%.2X ", (unsigned char)response[u]);
			mask(response + 6, response[1] & 0x7F, response + 2, 4);
			printf(" (");
			for (int u = 6; u < t; u++)
				printf("%c", (unsigned char)response[u]);
			printf(")\n");
			struct sockaddr_in wsa;
			for (int ch = 1; ch < 12; ch++)
			{
				printf("\nChat>");
				fgets(request + 2, 500, stdin);
				t = strlen(request + 2);
				request[0] = 0x81;
				request[1] = (t & 0x7F);
				if (write(s2, request, t + 2) == -1)
				{
					perror("write a web fallita");
					close(s3);
					close(s2);
					exit(1);
				}
				printf(">>%s\n", request + 2);
				for (int u = 0; u < t + 2; u++)
					printf("%.2X ", (unsigned char)request[u]);
				printf("\n");
			}
			close(s2);
			exit(1);
		}
		method = request_line;
		for (i = 0; request_line[i] != ' ' && request_line[i]; i++)
		{
		};
		if (request_line[i] != 0)
		{
			request_line[i] = 0;
			i++;
		}
		uri = request_line + i;
		for (; request_line[i] != ' ' && request_line[i]; i++)
		{
		};
		if (request_line[i] != 0)
		{
			request_line[i] = 0;
			i++;
		}
		http_ver = request_line + i;

		printf("Method = %s, URI = %s, Http-Version = %s\n", method, uri, http_ver);

		if (!strncmp(uri, "/exec/", 6))
		{
			sprintf(command, "%s > filetemp\n", uri + 6);
			printf("Eseguo il comando %s", command);
			if ((t = system(command)) == 0)
			{
				uri = "/filetemp";
			}
			else
				printf("system ha restituito %d\n", t);
		}

		if ((fin = fopen(uri + 1, "rt")) == NULL)
		{
			printf("File %s non aperto\n", uri + 1);
			sprintf(response, "HTTP/1.1 404 File not found\r\n\r\n<html>File non trovato</html>");
			t = write(s2, response, strlen(response));
			if (t == -1)
			{
				perror("write fallita");
				return -1;
			}
		}
		else
		{
			sprintf(response, "HTTP/1.1 200 OK\r\n\r\n");
			t = write(s2, response, strlen(response));
			while ((c = fgetc(fin)) != EOF)
			{
				if (write(s2, (unsigned char *)&c, 1) != 1)
				{
					perror("Write fallita");
				}
			}
			fclose(fin);
		}
		close(s2);
		exit(1);
	}
}
