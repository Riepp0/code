package main

import (
	"fmt"
	"math/rand"
	"sync"
	"time"
)

// Piatto ...
type Piatto struct {
	nome string
}

// Cameriere ...
type Cameriere struct {
	nome string
}

func ordina(p *Piatto, cook chan Piatto, wg *sync.WaitGroup) {
	cook <- *p
	fmt.Println(p.nome, "ordinato")
	wg.Done()
}

func cucina(cook chan Piatto, deliver chan Piatto, wg *sync.WaitGroup) {
	for len(cook) > 0 {
		a := <-cook
		time.Sleep(time.Duration(4+rand.Intn(3)) * time.Second)
		deliver <- a
		fmt.Println(a.nome, "cucinato")
	}

	wg.Done()
}

func consegna(c *Cameriere, cook chan Piatto, deliver chan Piatto, wg *sync.WaitGroup) {

	for len(cook) > 0 {
		for len(deliver) > 0 {
			b := <-deliver
			time.Sleep(3 * time.Second)
			fmt.Println(b.nome, "consegnato da", c.nome)
		}
		time.Sleep(time.Second)
	}
	wg.Done()
}

func main() {
	rand.Seed(time.Now().UTC().UnixNano())
	var wg sync.WaitGroup
	wg.Add(10)

	piatti := []Piatto{{"A"}, {"B"}, {"C"}, {"D"}, {"E"}, {"F"}, {"G"}, {"H"}, {"I"}, {"J"}}
	camerieri := []Cameriere{{"Alberto"}, {"Beppe"}}
	//Ordinazioni
	cook := make(chan Piatto, 10)
	for i := range piatti {
		go ordina(&piatti[i], cook, &wg)
	}

	wg.Wait()

	wg.Add(5)

	//Cucina
	deliver := make(chan Piatto, 10)
	for i := 0; i < 3; i++ {
		go cucina(cook, deliver, &wg)
	}

	//Consegna
	for i := range camerieri {
		go consegna(&camerieri[i], cook, deliver, &wg)
	}

	wg.Wait()
}
