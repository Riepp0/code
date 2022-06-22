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
#include <sys/types.h>
#include <signal.h>

//Proxy web

int pid; //Pid del processo
struct sockaddr_in local, remote, server; //Strutture per indirizzi
char request[10000]; //Buffer per la richiesta
char request2[10000]; //Buffer per la richiesta2
char response[1000]; //Buffer per la risposta
char response2[10000]; //Buffer per la risposta2

struct header //Struttura header
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
	char *method, *url, *ver, *scheme, *hostname, *port; //Puntatori alle parti della linea di richiesta
	char *filename; //Puntatore al nome del file
	FILE *fin; //File in input
	int c; 
	int n;
	int i, j, t, s, s2, s3;
	int yes = 1; //Permette la re-utilizzazione del socket
	int len; //Lunghezza del buffer
	if ((s = socket(AF_INET, SOCK_STREAM, 0)) == -1) //Creo il socket, AF_INET per IPv4, SOCK_STREAM per TCP, 0 per default
	{
		printf("errno = %d\n", errno);
		perror("Socket Fallita");
		return -1;
	}
	// Proprietà socket locale
	local.sin_family = AF_INET; //Tipo di indirizzo
	local.sin_port = htons(17999); //Porta
	local.sin_addr.s_addr = 0; //Indirizzo IP

	t = setsockopt(s, SOL_SOCKET, SO_REUSEADDR, &yes, sizeof(int)); //Permette la re-utilizzazione del socket
	if (t == -1)
	{
		perror("setsockopt fallita");
		return 1;
	}

	if (-1 == bind(s, (struct sockaddr *)&local, sizeof(struct sockaddr_in))) //Associa il socket all'indirizzo IP
	{
		perror("Bind Fallita");
		return -1;
	}

	if (-1 == listen(s, 10)) //Inizia a ricevere connessioni
	{
		perror("Listen Fallita");
		return -1;
	}
	// Proprietà socket remoto (server)
	remote.sin_family = AF_INET; //Tipo di indirizzo
	remote.sin_port = htons(0); //Porta
	remote.sin_addr.s_addr = 0; //Indirizzo IP
	len = sizeof(struct sockaddr_in); //Lunghezza della struttura
	while (1)
	{
		s2 = accept(s, (struct sockaddr *)&remote, &len); //Accetta una connessione
		printf("Remote address: %.8X\n", remote.sin_addr.s_addr); //Stampa l'indirizzo IP del client
		if (fork()) //Se il processo è figlio, chiude il socket
			continue; 
		if (s2 == -1) 
		{
			perror("Accept fallita");
			exit(1);
		}
		bzero(hbuffer, 10000); //Pulisce il buffer
		bzero(h, 100 * sizeof(struct header)); //Pulisce la struttura
		reqline = h[0].n = hbuffer; //Puntatore alla linea di richiesta
		for (i = 0, j = 0; read(s2, hbuffer + i, 1); i++) //Legge la richiesta
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

		printf("Request line: %s\n", reqline); //Stampa la linea di richiesta
		method = reqline; //Puntatore al metodo
		for (i = 0; i < 100 && reqline[i] != ' '; i++) //Scorre la linea di richiesta fino al primo spazio
			;
		reqline[i++] = 0; //Termino il metodo
		url = reqline + i; //Puntatore all'URL
		for (; i < 100 && reqline[i] != ' '; i++) //Scorre la linea di richiesta fino al secondo spazio
			;
		reqline[i++] = 0; //Termino l'URL
		ver = reqline + i; //Puntatore alla versione
		for (; i < 100 && reqline[i] != '\r'; i++) //Scorre la linea di richiesta fino al carattere \r
			;
		reqline[i++] = 0; //Termino la versione
		if (!strcmp(method, "GET")) //Se il metodo è GET
		{
			scheme = url; //Puntatore allo schema
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
		else if (!strcmp("CONNECT", method)) //Se il metodo è CONNECT
		{ // it is a connect  host:port
			hostname = url; //Puntatore all'hostname
			for (i = 0; url[i] != ':'; i++) //Scorre l'URL fino al primo :
				;
			url[i] = 0; //Termino l'URL
			port = url + i + 1; //Puntatore alla porta
			printf("hostname:%s, port:%s\n", hostname, port); //Stampa l'hostname e la porta
			he = gethostbyname(hostname); //Ritorna il puntatore alla struttura hostname
			if (he == NULL) //Se l'hostname non è stato trovato
			{
				printf("Gethostbyname Fallita\n");
				return 1;
			}
			printf("Connecting to address = %u.%u.%u.%u\n", (unsigned char)he->h_addr[0], (unsigned char)he->h_addr[1], (unsigned char)he->h_addr[2], (unsigned char)he->h_addr[3]); //Stampo l'IP a cui mi sto connettendo
			s3 = socket(AF_INET, SOCK_STREAM, 0); //Crea un socket s3

			if (s3 == -1)
			{
				perror("Socket to server fallita");
				return 1;
			}
			server.sin_family = AF_INET; //Imposta il tipo di socket
			server.sin_port = htons((unsigned short)atoi(port)); //Imposta la porta
			server.sin_addr.s_addr = *(unsigned int *)he->h_addr; //Imposta l'indirizzo IP
			t = connect(s3, (struct sockaddr *)&server, sizeof(struct sockaddr_in)); //Connette il socket s3 al server
			if (t == -1)
			{
				perror("Connect to server fallita");
				exit(0);
			}
			sprintf(response, "HTTP/1.1 200 Established\r\n\r\n"); //Crea la risposta
			write(s2, response, strlen(response)); //Scrive la risposta sul socket s2
			// <==============
			if (!(pid = fork())) //Se il processo figlio è stato creato con successo
			{ // Child
				while (t = read(s2, request2, 2000)) //legge dal socket s2
				{
					write(s3, request2, t); //Scrive sul socket s3
					// printf("CL >>>(%d)%s \n",t,hostname); //SOLO PER CHECK
				}
				exit(0);
			}
			else
			{ // Parent
				while (t = read(s3, response2, 2000)) //legge dal socket s3
				{
					write(s2, response2, t); //Scrive sul socket s2
					// printf("CL <<<(%d)%s \n",t,hostname);
				}
				kill(pid, SIGTERM); //Manda un segnale di terminazione al processo figlio
				close(s3); //Chiude il socket s3
			}
		}
		else //Se il metodo non è GET o CONNECT 
		{
			sprintf(response, "HTTP/1.1 501 Not Implemented\r\n\r\n"); //Crea la risposta
			write(s2, response, strlen(response)); //Scrive la risposta sul socket s2
		}
		close(s2);
		exit(1);
	}
	close(s);
}
