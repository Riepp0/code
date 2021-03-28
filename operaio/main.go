package main

import (
	"fmt"
	"sync"
	"time"
)

type Operaio struct {
	nome      string
	trapanato int
}

type Martello struct {
}

type Cacciavite struct {
}

type Trapano struct {
}

func pick_martello(o *Operaio, ch1 chan Martello) {
	tool := <-ch1
	fmt.Println("Operaio", o.nome, "prende il martello")
	time.Sleep(time.Second)
	o.trapanato += 1
	ch1 <- tool
	fmt.Println("Operaio", o.nome, "lascia il martello")
}

func pick_cacciavite(o *Operaio, ch2 chan Cacciavite) {
	tool := <-ch2
	fmt.Println("Operaio", o.nome, "prende il cacciavite")
	time.Sleep(time.Second)
	o.trapanato += 2
	ch2 <- tool
	fmt.Println("Operaio", o.nome, "lascia il cacciavite")
}

func pick_trapano(o *Operaio, ch3 chan Trapano) {
	tool := <-ch3
	fmt.Println("Operaio", o.nome, "prende il trapano")
	time.Sleep(time.Second)
	o.trapanato += 4
	ch3 <- tool
	fmt.Println("Operaio", o.nome, "lascia il trapano")
}

func use(o *Operaio, ch1 chan Martello, ch2 chan Cacciavite, ch3 chan Trapano) {
	switch o.trapanato {
	case 0: //Martello o Trapano
		if len(ch1) > 0 {
			pick_martello(o, ch1)
			break
		}
		pick_trapano(o, ch3)
		break

	case 1: //Trapano
		pick_trapano(o, ch3)
		break
	case 4: //Martello o Cacciavite
		if len(ch1) > 0 {
			pick_martello(o, ch1)
			break
		}
		pick_cacciavite(o, ch2)
		break
	case 5: //Cacciavite
		pick_cacciavite(o, ch2)
		break

	case 6: //Martello
		pick_martello(o, ch1)
		break
	default:
		fmt.Println("XD")

	}
}

func main() {
	var wg sync.WaitGroup
	wg.Add(3)

	operai := []Operaio{{"Albero", 0}, {"Boris", 0}, {"Cavallo", 0}}
	var m Martello
	var c Cacciavite
	var t Trapano
	var t1 Trapano

	ch1 := make(chan Martello, 1)
	ch1 <- m

	ch2 := make(chan Cacciavite, 1)
	ch2 <- c

	ch3 := make(chan Trapano, 2)
	ch3 <- t
	ch3 <- t1

	for i := range operai {
		go func(_x int) {
			use(&operai[_x], ch1, ch2, ch3)
			use(&operai[_x], ch1, ch2, ch3)
			use(&operai[_x], ch1, ch2, ch3)
			wg.Done()
		}(i)
	}

	wg.Wait()
}
