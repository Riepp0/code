import numpy as np
import pandas as pd

def compute_cost(X, y, theta):
  squared_error = np.power(((X * theta.T) - y), 2)
  return np.sum(squared_error) / (2 * len(X))

def train(X, y, theta, alpha, iters):
  temp = np.matrix(np.zeros(theta.shape))
  params = int(theta.ravel().shape[1])
  cost = np.zeros(iters)

  for i in range(iters):
    error = (X * theta.T) - y
    for j in range(params):
      term = np.multiply(error, X[:,j])
      temp[0,j] = theta[0,j] - ((alpha / len(X)) * np.sum(term))
    theta = temp
    cost[i] = compute_cost(X, y, theta)

  return theta, cost

def linear_regr(dataset, value):
  alpha = 0.01
  iters = 1000
  try:
    data = pd.read_csv(dataset, header=None, names=["Population", "Profit"])
  except FileNotFoundError:
    return None

  data.insert(0, "Ones", 1)

  cols = data.shape[1]
  X = data.iloc[:, 0:cols-1]
  y = data.iloc[:, cols-1:cols]

  X = np.matrix(X.values)
  y = np.matrix(y.values)
  theta = np.matrix(np.array([0, 0]))

  theta, _ = train(X, y, theta, alpha, iters)
  output = np.matrix([1, value]) * theta.T
  
  return float(output)

def vertical_stack(a,b):
    if len(a) != len(b):
        return None
    else:
        return np.vstack((a,b))

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