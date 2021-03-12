package main

import (
	"sync"
)

// Piatto ...
type Piatto struct {
	nome string
}

// Cameriere ...
type Cameriere struct {
	nome string
}

func ordina(p *Piatto, ch chan Piatto) {
	ch <- *p
}

func cucina() {

}

func consegna(wg *sync.WaitGroup) {
	defer wg.Done()
}

func main() {
	var wg sync.WaitGroup
	wg.Add(10)
	ch := make(chan Piatto, 10)

	piatti := []Piatto{{"A"}, {"B"}, {"C"}, {"D"}, {"E"}, {"F"}, {"G"}, {"H"}, {"I"}, {"J"}}

	for i := 0; i < 10; i++ {
		func() {
			go ordina(&piatti[i], ch)
			go cucina()
			go consegna(&wg)
		}()
	}

	wg.Wait()
}
