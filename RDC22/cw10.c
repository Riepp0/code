#include <stdio.h>
#include <string.h>
#include <sys/types.h> /* See NOTES */
#include <sys/socket.h>
#include <errno.h>
#include <arpa/inet.h>
#include <stdint.h>
#include <unistd.h>

//HTTP 1.0

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
	//Creo il socket, AF_INET per IPv4, SOCK_STREAM per TCP, 0 per default
	{
		printf("errno = %d\n", errno);
		perror("Socket Fallita");
		return -1;
	}
	remote.sin_family = AF_INET; //Tipo di indirizzo
	remote.sin_port = htons(80); //Porta
	remote.sin_addr.s_addr = *((uint32_t *)ipserver); //Indirizzo IP
	if (-1 == connect(s, (struct sockaddr *)&remote, sizeof(struct sockaddr_in)))
	//Connessione al server (socket, indirizzo, dimensione indirizzo)
	{
		perror("Connect Fallita");
		return -1;
	}
	write(s, request, strlen(request)); //Scrivo sul socket
	statusline = h[0].n = hbuffer; //Salvo la statusline
	for (i = 0, j = 0; read(s, hbuffer + i, 1); i++) //Leggo dal socket
	{
		if (hbuffer[i] == '\n' && hbuffer[i - 1] == '\r') //Se il carattere letto è \n e il precedente \r (terminazione)
		{
			hbuffer[i - 1] = 0; // Termino il token attuale
			if (!h[j].n[0]) //Se il token è vuoto (arrivati alla fine del messaggio)
				break; //Esco
			h[++j].n = hbuffer + i + 1; //Salvo il nuovo token
		}
		if (hbuffer[i] == ':' && !h[j].v) //Se il carattere letto è : e il token non ha value
		{
			hbuffer[i] = 0; //Termino il token attuale
			h[j].v = hbuffer + i + 1; //Salvo il nuovo token
		}
	}
	for (i = 1; i < j; i++) //Scorro gli header
		printf("%s ---> %s\n", h[i].n, h[i].v); //Stampo i header

	for (len = 0; (n = read(s, response + len, 1000000 - len)) > 0; len += n) //Leggo dal socket
		;
	if (n == -1)
	{
		perror("Read fallita");
		return -1;
	}
	response[len] = 0; //Termino la stringa
	printf("%s\n", response); //Stampo la stringa
}
