from constraint import * 
import networkx as nx

def sistema3():
    problem = Problem()

    problem.addVariable('x', [13,12,34])
    problem.addVariable('y', [40,57,76])
    problem.addVariable('z', [6,8,20])
    
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

def nqueens(n):
    problem = Problem()
    cols = range(0,n)	# variables
    rows = range(0,n)	# domains
    problem.addVariables(cols, rows)

    # queen loops and constraints
    for col1 in cols:
        for col2 in cols:
            if col1 < col2:
                problem.addConstraint(lambda row1, row2, col1=col1, col2=col2:
                    # diagonal check
                    abs(row1-row2) != abs(col1-col2) and
                    # horizontal check	
                    row1 != row2, (col1, col2))				

    return problem.getSolution()

#print(nqueens(5))
