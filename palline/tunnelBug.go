package main

import (
	"fmt"
	"math/rand"
	"time"
)

type Gruppo struct {
	nome     string
	nPalline int
}

type Tunnel struct {
	libero bool
}

func transumanza(g Gruppo, t chan Tunnel, c1 chan int) {
	for g.nPalline > 0 {
		time.Sleep(time.Duration(rand.Intn(2)) * time.Second)
		mandaPersona(&g, t, c1)
	}
}

func mandaPersona(g *Gruppo, t chan Tunnel, c1 chan int) {
	select { //Aggiungo una select per separare direttamente i casi tra caduta libera e scontri
	case tunnel := <-t:
		//tunnel.libero = false				//Questa variabile booleana non serve più
		//t <- tunnel
		fmt.Println("qui")
		select {
		case x := <-c1:
			fmt.Println("scontro ", g.nome)
			// GO si arrabbia se non usate una varabile...
			x = x - x
			t <- tunnel
			//tunnel.libero = true 			//Libero il tunnel dopo uno scontro (variabile booleana non serve)
		case <-time.After(time.Second):
			//tunnel := <-t
			//tunnel.libero = true			(variabile booleana non server)
			t <- tunnel
			fmt.Println("passato")
			g.nPalline--
			fmt.Println("rimangono ", g.nPalline, " nel gruppo ", g.nome)
		}
		//time.Sleep(time.Duration(4)*time.Second)
	default:
		//time.Sleep(time.Duration(4)*time.Second)
		c1 <- 1
		//tunnel.libero = true
		//t <- tunnel
		//time.Sleep(time.Duration(4)*time.Second)
	}

}

func main() {
	rand.Seed(time.Now().UnixNano())
	gruppo1 := Gruppo{"destra", 5}
	gruppo2 := Gruppo{"sinistra", 5}

	c1 := make(chan int, 1)

	tunnelChannel := make(chan Tunnel, 1) //Channel buffered (1)
	tunnel := Tunnel{true}
	tunnelChannel <- tunnel

	go transumanza(gruppo1, tunnelChannel, c1)
	go transumanza(gruppo2, tunnelChannel, c1)

	time.Sleep(time.Minute)
}
