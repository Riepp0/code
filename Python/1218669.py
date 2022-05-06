import networkx as nx 

def diam_graph(grafo):
    """    Funzione che calcola il diametro del grafo    """
    if isinstance(grafo, nx.Graph) and grafo != None:
        return nx.diameter(grafo)
    else:
        return "Tipo di dato non valido"

def max_degree(grafo):
    """    Funzione che calcola il massimo grado del grafo    """
    if isinstance(grafo, nx.Graph) and grafo != None:
        grado = 0
        for i in grafo.nodes():
            if grafo.degree(i) > grado:
                grado = grafo.degree(i)
        #Returns the list of nodes with max degree
        return [i for i in grafo.nodes() if grafo.degree(i) == grado]
          
    else:
        return "Tipo di dato non valido"


def ampiezza(grafo, start, end):
    """    Funzione che calcola l'ampiezza del grafo da start ad end"""
    if isinstance(grafo, nx.Graph) and grafo != None:
        # Check if start and end are valid nodes
        if start in grafo.nodes() and end in grafo.nodes():
            #Returns the list of visited nodes from start to end
            if start == end:
                return [start]
            else:
                list = []
                queue = [start]
                while queue:
                    nodo = queue.pop()
                    list.append(nodo)
                    if nodo == end:
                        break
                    for neighbors in grafo.neighbors(nodo):
                        if neighbors not in list and neighbors not in queue:
                            queue.append(neighbors)
                return list
            
        return "Nodo non presente nel grafo"
    return "Tipo di dato non valido"

#G = nx.Graph()
#G.add_nodes_from(["A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"])
#G.add_edges_from([("A","B"),("B", "C"),("C", "D"), ("D", "E"), ("E", "F"), ("F", "G")])
#print(ampiezza(G, "A", "E"))


