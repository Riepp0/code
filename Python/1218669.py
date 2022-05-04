import networkx as nx 

# Ok
def diam_graph(grafo):
    """    Funzione che calcola il diametro del grafo    """
    if isinstance(grafo, nx.Graph) and grafo != None:
        return nx.diameter(grafo)
    else:
        return "Tipo di dato non valido"
# OK
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
                visited = []
                queue = [start]
                while queue:
                    nodo = queue.pop()
                    visited.append(nodo)
                    for neighbors in grafo.neighbors(nodo):
                        if neighbors not in visited and neighbors not in queue:
                            queue.append(neighbors)
                return visited
            
        return "Nodo non presente nel grafo"
    return "Tipo di dato non valido"

G = nx.Graph()
G.add_nodes_from(["A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"])
G.add_edges_from([("A","B"),("A","C"),("A","D"),("A","E"),("A","F"),("A","G"),("A","H"),("A","I"),("A","J"),("A","K"),("A","L"),("A","M"),("A","N"),("A","O"),("A","P"),("A","Q"),("A","R"),("A","S"),("A","T"),("A","U"),("A","V"),("A","W"),("A","X"),("A","Y"),("A","Z"),("B","C"),("B","D"),("B","E"),("B","F"),("B","G"),("B","H"),("B","I"),("B","J"),("B","K"),("B","L"),("B","M"),("B","N"),("B","O"),("B","P"),("B","Q"),("B","R"),("B","S"),("B","T"),("B","U"),("B","V"),("B","W"),("B","X"),("B","Y"),("B","Z"),("C","D"),("C","E"),("C","F"),("C","G"),("C","H"),("C","I"),("C","J"),("C","K"),("C","L"),("C","M"),("C","N"),("C","O"),("C","P"),("C","Q"),("C","R"),("C","S"),("C","T"),("C","U"),("C","V"),("C","W"),("C","X"),("C","Y"),("C","Z"),("D","E"),("D","F"),("D","G"),("D","H"),("D","I"),("D","J"),("D","K"),("D","L"),("D","M"),("D","N"),("D","O"),("D","P"),("D","Q"),("D","R"),("D","S"),("D","T"),("D","U"),("D","V"),("D","W"),("D","X"),("D","Y"),("D","Z"),("E","F"),("E","G"),("E","H"),("E","I"),("E","J"),("E","K"),("E","L"),("E","M"),])
print(ampiezza(G, "A", "E"))


