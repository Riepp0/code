from constraint import * 
import networkx as nx

def sistema3():
    problem = Problem()

    problem.addVariable('x', domain=[13,12,34])
    problem.addVariable('y', domain=[40,57,76])
    problem.addVariable('z', domain=[6,8,20])
    
    problem.addConstraint(lambda a,b: a < b, ('x', 'y'))
    problem.addConstraint(lambda a,b,c: a + b + c < 65, ('x', 'y', 'z'))

    return problem.getSolution()

def grafo_vincoli(grafo : nx.Graph):
    if grafo.number_of_nodes() == 0:
        return {}
    domain = {"rosso","verde","blu"}
    problem = Problem()
    list = []
    for i in grafo.nodes():
        if(not(i in list)):
            problem.addVariable(i, domain)
        neighbors = grafo.neighbors(i)
        for j in neighbors:
            if(not(j in list)) and not (neighbors in list):
                problem.addConstraint(lambda a,b: a != b, (i,j))
    return problem.getSolution()

### Write a function that is called nqueen(n) that takes an integer n and returns only the first solution of the problem.
#def nqueen(n):

