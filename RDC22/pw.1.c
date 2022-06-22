#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/types.h> /* See NOTES */
#include <sys/socket.h>
#include <errno.h>
#include <arpa/inet.h>
#include <stdint.h>
#include <unistd.h>
#include <netdb.h>
#include <netinet/in.h>

//Proxy web tunnelled (il proxy non vede i messaggi)

struct sockaddr_in local, remote, server; //Strutture per indirizzi
char request[10000]; //Buffer per la richiesta
char response[1000]; //Buffer per la risposta

struct header
{
	char *n;
	char *v;
} h[100];

struct hostent *he; //Struttura hostent

int main()
{
	char hbuffer[10000]; //Buffer per i token
	char buffer[2000]; //Buffer per la richiesta
	char *reqline; //Puntatore alla linea di richiesta
	char *method, *url, *ver, *scheme, *hostname; //Puntatori alle parti della linea di richiesta
	char *filename; //Puntatore al nome del file
	FILE *fin; //File in input
	int c;
	int n;
	int i, j, t, s, s2, s3;
	int yes = 1; //Permette la re-utilizzazione del socket
	int len;
	if ((s = socket(AF_INET, SOCK_STREAM, 0)) == -1) //Creo il socket, AF_INET per IPv4, SOCK_STREAM per TCP, 0 per default
	{
		printf("errno = %d\n", errno);
		perror("Socket Fallita");
		return -1;
	}
	local.sin_family = AF_INET; //Tipo di indirizzo
	local.sin_port = htons(17999); //Porta
	local.sin_addr.s_addr = 0; //Indirizzo IP

	t = setsockopt(s, SOL_SOCKET, SO_REUSEADDR, &yes, sizeof(int)); //Permette la re-utilizzazione del socket
	if (t == -1)
	{
		perror("setsockopt fallita"); 
		return 1;
	}

	if (-1 == bind(s, (struct sockaddr *)&local, sizeof(struct sockaddr_in))) //Assegno l'indirizzo al socket
	{
		perror("Bind Fallita");
		return -1;
	}

	if (-1 == listen(s, 10)) //Inizia a ricevere connessioni
	{
		perror("Listen Fallita");
		return -1;
	}
	remote.sin_family = AF_INET; //Tipo di indirizzo
	remote.sin_port = htons(0); //Porta
	remote.sin_addr.s_addr = 0; //Indirizzo IP
	len = sizeof(struct sockaddr_in); //Dimensione della struttura
	while (1)
	{
		s2 = accept(s, (struct sockaddr *)&remote, &len); //Accetto la connessione
		printf("Remote address: %.8X\n", remote.sin_addr.s_addr); //Stampo l'indirizzo remoto
		if (fork()) //Se il processo è figlio
			continue;
		if (s2 == -1) 
		{
			perror("Accept fallita");
			exit(1);
		}
		bzero(hbuffer, 10000); //Pulisco il buffer
		bzero(h, 100 * sizeof(struct header)); //Pulisco la struttura
		reqline = h[0].n = hbuffer;
		for (i = 0, j = 0; read(s2, hbuffer + i, 1); i++) //Leggo la richiesta 
		{
			printf("%c", hbuffer[i]); //Stampa la richiesta
			if (hbuffer[i] == '\n' && hbuffer[i - 1] == '\r') //Se il carattere è \r\n, la linea di richiesta è finita
			{
				hbuffer[i - 1] = 0; // Termino il token attuale
				if (!h[j].n[0]) //Se il token è vuoto, la linea di richiesta è finita
					break; //esco
				h[++j].n = hbuffer + i + 1; //Puntatore al prossimo token
			}
			if (hbuffer[i] == ':' && !h[j].v && j > 0) //Se il carattere è : e il token precedente è vuoto e non è il primo token, il token è il valore del token precedente
			{
				hbuffer[i] = 0; //Termino il token attuale 
				h[j].v = hbuffer + i + 1; //Puntatore al valore del token precedente
			}
		}

		printf("Request line: %s\n", reqline);
		method = reqline; //Puntatore alla linea di richiesta
		for (i = 0; i < 100 && reqline[i] != ' '; i++) //Scorro la linea di richiesta
			;
		reqline[i++] = 0; //Termino il token attuale
		url = reqline + i; //Puntatore al token url
		for (; i < 100 && reqline[i] != ' '; i++)
			;
		reqline[i++] = 0; //Termino il token attuale
		ver = reqline + i; //Puntatore al token ver
		for (; i < 100 && reqline[i] != '\r'; i++)
			;
		reqline[i++] = 0; //Termino il token attuale
		if (!strcmp(method, "GET")) //Se il metodo è GET
		{
			scheme = url; //Puntatore alla parte del url
			// GET http://www.aaa.com/file/file
			printf("url=%s\n", url); //Stampa l'URL 
			for (i = 0; url[i] != ':' && url[i]; i++) //Scorre l'URL fino al primo :
				;
			if (url[i] == ':') //Se il carattere è :
				url[i++] = 0; //Termino l'URL
			else //altrimenti
			{
				printf("Parse error, expected ':'"); //Stampa un errore di parsing
				exit(1);
			}
			if (url[i] != '/' || url[i + 1] != '/') //Se il carattere non è / o il successivo non è /
			{
				printf("Parse error, expected '//'"); //Stampa un errore di parsing
				exit(1);
			}
			i = i + 2;
			hostname = url + i; //Puntatore all'hostname
			for (; url[i] != '/' && url[i]; i++) //Scorre l'URL fino al primo /
				;
			if (url[i] == '/') //Se il carattere è /
				url[i++] = 0; //Termino l'URL
			else //Altrimenti
			{
				printf("Parse error, expected '/'"); //Stampa un errore di parsing
				exit(1);
			}
			filename = url + i; //Puntatore al filename
			printf("Schema: %s, hostname: %s, filename: %s\n", scheme, hostname, filename); //Stampa lo schema, l'hostname e il filename

			he = gethostbyname(hostname); //Ritorna il puntatore alla struttura hostname
			printf("%d.%d.%d.%d\n", (unsigned char)he->h_addr[0], (unsigned char)he->h_addr[1], (unsigned char)he->h_addr[2], (unsigned char)he->h_addr[3]); //Stampa l'indirizzo IP dell'hostname
			if ((s3 = socket(AF_INET, SOCK_STREAM, 0)) == -1) //Crea un socket s3
			{
				printf("errno = %d\n", errno);
				perror("Socket Fallita");
				exit(-1);
			}

			server.sin_family = AF_INET; //Imposta il tipo di socket
			server.sin_port = htons(80); //Imposta la porta
			server.sin_addr.s_addr = *(unsigned int *)(he->h_addr); //Imposta l'indirizzo IP

			if (-1 == connect(s3, (struct sockaddr *)&server, sizeof(struct sockaddr_in))) //Connette il socket s3 al server
			{
				perror("Connect Fallita");
				exit(1);
			}
			sprintf(request, "GET /%s HTTP/1.1\r\nHost:%s\r\nConnection:close\r\n\r\n", filename, hostname); //Crea la richiesta
			printf("%s\n", request); //Stampa la richiesta
			write(s3, request, strlen(request)); //Scrive la richiesta sul socket s3
			while (t = read(s3, buffer, 2000)) //Legge dal socket s3
				write(s2, buffer, t); //Scrive sul socket s2
			close(s3); //Chiude il socket s3
		}
		else //Se il metodo non è GET 
		{
			sprintf(response, "HTTP/1.1 501 Not Implemented\r\n\r\n"); //Crea la risposta
			write(s2, response, strlen(response)); //Scrive la risposta sul socket s2
		}
		close(s2);
		exit(1);
	}
	close(s);
}

