#include <stdio.h>
#include <string.h>
#include <sys/types.h> /* See NOTES */
#include <sys/socket.h>
#include <errno.h>
#include <arpa/inet.h>
#include <stdint.h>
#include <unistd.h>
#include <stdlib.h>

//Server web

struct sockaddr_in local, remote; //Strutture per indirizzi
char request[1000001]; //Buffer per la richiesta
char response[1000]; //Buffer per la risposta

struct header //Struttura header
{
	char *n;
	char *v;
} h[100];

int main()
{
	char hbuffer[10000]; //Buffer per i token
	char *reqline; //Puntatore alla linea di richiesta
	char *method, *url, *ver; //Puntatori alle parti della linea di richiesta
	char *filename; //Puntatore al nome del file
	FILE *fin; //File in input
	int c; 
	int n; 
	int i, j, t, s, s2; 
	int yes = 1; //Permette la re-utilizzazione del socket
	int len; //Lunghezza del buffer
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
	remote.sin_family = AF_INET; //Tipo di indirizzo
	remote.sin_port = htons(0); //Porta
	remote.sin_addr.s_addr = 0; //Indirizzo IP
	len = sizeof(struct sockaddr_in); //Lunghezza della struttura
	while (1) 
	{
		s2 = accept(s, (struct sockaddr *)&remote, &len); //Accetta una connessione
		bzero(hbuffer, 10000); //Pulisce il buffer
		bzero(h, sizeof(struct header) * 100); //Pulisce la struttura
		reqline = h[0].n = hbuffer; //Puntatore alla linea di richiesta
		for (i = 0, j = 0; read(s2, hbuffer + i, 1); i++) //Legge la richiesta
		{
			if (hbuffer[i] == '\n' && hbuffer[i - 1] == '\r') //Se il carattere è \n e il predcedente è \r (terminazione)
			{
				hbuffer[i - 1] = 0; // Termino il token attuale
				if (!h[j].n[0]) //Se il token è vuoto
					break; //Esco dal ciclo
				h[++j].n = hbuffer + i + 1; //Salvo il nuovo token
			}
			if (hbuffer[i] == ':' && !h[j].v) //Se il carattere è : e il token attuale è vuoto
			{
				hbuffer[i] = 0; //Termino il token attuale
				h[j].v = hbuffer + i + 1; //Salvo il nuovo token
			}
		}

		printf("%s\n", reqline);
		if (len == -1) 
		{
			perror("Read Fallita");
			return -1;
		}
		method = reqline; //Puntatore alla linea di richiesta
		for (i = 0; reqline[i] != ' '; i++) //Scorre la linea di richiesta
			;
		reqline[i++] = 0; //Termino il token
		url = reqline + i; //Puntatore alla linea di richiesta (URL)
		for (; reqline[i] != ' '; i++) //Scorre la linea di richiesta
			;
		reqline[i++] = 0; //Termino il token
		ver = reqline + i; //Puntatore alla linea di richiesta (ver)
		for (; reqline[i] != 0; i++) //Scorre la linea di richiesta
			;
		reqline[i++] = 0; //Termino il token
		if (!strcmp(method, "GET")) //Se il metodo è GET
		{
			filename = url + 1; //Puntatore al nome del file
			fin = fopen(filename, "rt"); //Apre il file
			if (fin == NULL) //Se il file non esiste
			{
				sprintf(response, "HTTP/1.1 404 Not Found\r\n\r\n"); //Risponde con un messaggio di errore
				write(s2, response, strlen(response)); //Scrive la risposta
			}
			else //Se il file esiste
			{
				sprintf(response, "HTTP/1.1 200 OK\r\n\r\n"); //Risponde con un messaggio OK 
				write(s2, response, strlen(response)); //Scrive la risposta
				while ((c = fgetc(fin)) != EOF) //Legge il file fino alla fine
					write(s2, &c, 1); //Scrive il file
				fclose(fin); //Chiude il file
			}
		}
		else //Se il metodo non è GET
		{
			sprintf(response, "HTTP/1.1 501 Not Implemented\r\n\r\n"); //Risponde con un messaggio di errore
			write(s2, response, strlen(response)); //Scrive la risposta
		}
		close(s2); //Chiude il socket
	}
	close(s);
}
