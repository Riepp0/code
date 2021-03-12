package main

import (
	"fmt"
	"math/rand"
	"sync"
	"time"
)

// Cliente ...
type Cliente struct {
	nome         string
	prenotazione string
}

// Viaggio ...
type Viaggio struct {
	meta string
}

//Prenotazione casuale
func prenota(c *Cliente, mete []Viaggio, wg *sync.WaitGroup) {
	c.prenotazione = mete[rand.Intn(2)].meta
	defer wg.Done()

}

//Stampo dove vanno i clienti
func stampaPartecipanti(c []Cliente, v []Viaggio) {
	var spagna int = 0
	var francia int = 0
	for i := range c {
		if c[i].prenotazione == "Francia" {
			francia++
		} else {
			spagna++
		}
		fmt.Println("Cliente", c[i].nome, " va in:", c[i].prenotazione)
	}

	if francia >= 2 {
		fmt.Println(v[0].meta, ": confermata")
	} else {
		fmt.Println(v[0].meta, ": non confermata")
	}

	if spagna >= 4 {
		fmt.Println(v[1].meta, ": confermata")
	} else {
		fmt.Println(v[1].meta, ": non confermata")
	}

}

func main() {
	clienti := []Cliente{{"A", ""}, {"B", ""}, {"C", ""}, {"D", ""}, {"E", ""}, {"F", ""}, {"G", ""}}
	mete := []Viaggio{{"Francia"}, {"Spagna"}}

	rand.Seed(time.Now().UTC().UnixNano())

	var wg sync.WaitGroup
	wg.Add(len(clienti))
	for i := range clienti {
		go prenota(&clienti[i], mete, &wg)
	}
	wg.Wait()

	stampaPartecipanti(clienti, mete)
}
