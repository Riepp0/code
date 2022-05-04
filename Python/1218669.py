import networkx as nx 

# Ok
def diam_graph(grafo):
    """    Funzione che calcola il diametro del grafo    """
    if isinstance(grafo, nx.Graph) and nx.is_empty(grafo):
        return nx.diameter(grafo)
    else:
        return "Tipo di dato non valido"
# OK
def max_degree(grafo):
    """    Funzione che calcola il massimo grado del grafo    """
    if isinstance(grafo, nx.Graph) and nx.is_empty(grafo):
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
    if isinstance(grafo, nx.Graph) and nx.is_empty(grafo):
        # Check if start and end are valid nodes
        if start in grafo.nodes() and end in grafo.nodes():
            #Returns the list of visited nodes from start to end
            if start == end:
                return [start]
            else:
                visited = []
                queue = [start]
                while queue:
                    nodo = queue.pop()
                    visited.append(nodo)
                    for neighbors in grafo.neighbors(nodo):
                        if neighbors not in visited and neighbors not in queue:
                            queue[neighbors]
                return visited
            
        return "Nodo non presente nel grafo"
    return "Tipo di dato non valido"

G = nx.Graph()
G.add_nodes_from(["A","B","C"])
G.add_edges_from([("A","B"),("A","C")])



