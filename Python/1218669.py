import networkx as nx 

def diam_graph(grafo : nx.Graph):
    """    Funzione che calcola il diametro del grafo    """
    if isinstance(grafo, nx.Graph) and (grafo != None):
        return nx.diameter(grafo)
    else:
        return "Tipo di dato non valido"

def max_degree(grafo : nx.Graph):
    """    Funzione che calcola il massimo grado del grafo    """
    if isinstance(grafo, nx.Graph) and (grafo != None):
        return max(grafo.degree())
    else:
        return "Tipo di dato non valido"

def ampiezza(grafo : nx.Graph, start, end):
    """    Funzione che calcola l'ampiezza del grafo da start ad end"""
    # Check if grafo is a valid graph
    if isinstance(grafo, nx.Graph) and (grafo != None):
        # Check if start and end are valid nodes
        if start in grafo.nodes() and end in grafo.nodes():
            return nx.shortest_path_length(grafo, start, end)
        return "Nodo non presente nel grafo"
    return "Tipo di dato non valido"

