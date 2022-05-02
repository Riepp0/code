import networkx as nx 

#Ok
def diam_graph(grafo):
    """    Funzione che calcola il diametro del grafo    """
    if isinstance(grafo, nx.Graph) and (grafo != None):
        return nx.diameter(grafo)
    else:
        return "Tipo di dato non valido"

def max_degree(grafo):
    """    Funzione che calcola il massimo grado del grafo    """
    if isinstance(grafo, nx.Graph) and (grafo != None):
        #Returns the list of max degree nodes
        return max(nx.degree(grafo))
    else:
        return "Tipo di dato non valido"

def ampiezza(grafo, start, end):
    """    Funzione che calcola l'ampiezza del grafo da start ad end"""
    # Check if grafo is a valid graph
    if isinstance(grafo, nx.Graph) and (grafo != None):
        # Check if start and end are valid nodes
        if start in grafo.nodes() and end in grafo.nodes():
            #Returns the list of visited nodes from start to end
            return nx.shortest_path(grafo, start, end)
        return "Nodo non presente nel grafo"
    return "Tipo di dato non valido"




