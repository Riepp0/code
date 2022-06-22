#include <stdio.h>
#include <string.h>
#include <sys/types.h> /* See NOTES */
#include <sys/socket.h>
#include <errno.h>
#include <arpa/inet.h>
#include <stdint.h>
#include <unistd.h>
#include <stdlib.h>

struct sockaddr_in remote; //Struttura per l'indirizzo del server
char response[1000001]; //Buffer per la risposta del server
struct header //Struttura header
{
	char *n;
	char *v;
} h[100]; 

int main()
{
	size_t len = 0; //Dimensione del buffer
	int i, j, k, n, bodylen; 
	char *request = "GET / HTTP/1.1\r\nHost:www.google.it\r\n\r\n"; //Richiesta HTTP
	char *statusline; 
	char hbuffer[10000]; 
	unsigned char ipserver[4] = {142, 250, 180, 3}; //Indirizzo IP del server a cui mi connetto
	int s; //Creo il socket
	if ((s = socket(AF_INET, SOCK_STREAM, 0)) == -1) //Creo il socket, AF_INET per IPv4, SOCK_STREAM per TCP, 0 per default
	{
		printf("errno = %d\n", errno);
		perror("Socket Fallita");
		return -1;
	}
	remote.sin_family = AF_INET; //Tipo di indirizzo
	remote.sin_port = htons(80); //Porta
	remote.sin_addr.s_addr = *((uint32_t *)ipserver); //Indirizzo IP
	if (-1 == connect(s, (struct sockaddr *)&remote, sizeof(struct sockaddr_in))) //Connessione al server (socket, indirizzo, dimensione indirizzo)
	{
		perror("Connect Fallita");
		return -1;
	}

	for (k = 0; k < 1; k++) //Scrivo sul socket
	{
		write(s, request, strlen(request)); //Scrivo sul socket
		bzero(hbuffer, 10000); //Pulisco il buffer per poter accettare nuove richieste senza problemi
		statusline = h[0].n = hbuffer; //Salvo la statusline
		for (i = 0, j = 0; read(s, hbuffer + i, 1); i++) 
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
		bodylen = 1000000; 
		for (i = 1; i < j; i++) //Scorro gli header
		{
			printf("%s ---> %s\n", h[i].n, h[i].v); //Stampo i header
			if (!strcmp("Content-Length", h[i].n)) //Se il header è Content-Length
				bodylen = atoi(h[i].v); //Salvo la lunghezza del body
		}
		for (len = 0; len < bodylen && (n = read(s, response + len, 1000000 - len)) > 0; len += n) //Scorro il body
			;
		if (n == -1) 
		{
			perror("Read fallita");
			return -1;
		}
		response[len] = 0; //Termino la stringa
		printf("%s\n", response); //Stampo la risposta
	}
}
