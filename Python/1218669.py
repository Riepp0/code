import numpy as np
import pandas as pd

# Esercizio 1
def computeCost(X, y, theta):
  squared_error = np.power(((X * theta.T) - y), 2)
  return np.sum(squared_error) / (2 * len(X))

def learn(X, y, theta, alpha, iters):
  tmp = np.matrix(np.zeros(theta.shape))
  param = int(theta.ravel().shape[1])
  cost = np.zeros(iters)

  for i in range(iters):
    error = (X * theta.T) - y
    for j in range(param):
      term = np.multiply(error, X[:,j])
      tmp[0,j] = theta[0,j] - ((alpha / len(X)) * np.sum(term))
    theta = tmp
    cost[i] = computeCost(X, y, theta)

  return theta, cost

def linear_regr(dataset, value):
  alpha = 0.01
  iters = 1000
  try:
    data = pd.read_csv(dataset, header=None, names=["Col1", "Col2"])
  except FileNotFoundError:
    return None

  data.insert(0, "Ones", 1)

  cols = data.shape[1]
  X = data.iloc[:, 0:cols-1]
  y = data.iloc[:, cols-1:cols]

  X = np.matrix(X.values)
  y = np.matrix(y.values)
  theta = np.matrix(np.array([0, 0]))

  theta, _ = learn(X, y, theta, alpha, iters)
  output = np.matrix([1, value]) * theta.T
  
  return float(output)

# Esercizio 2
def vertical_stack(a,b):
    if len(a) != len(b):
        return None
    else:
        return np.vstack((a,b))

# Esercizio 3
def match(a,b):
    if len(a) != len(b):
        return None
    else:
        ret = []
        for i in range(len(a)):
            if a[i] == b[i]:
                ret.append(i)
        return ret

print(linear_regr("day_mod.csv",0.22))