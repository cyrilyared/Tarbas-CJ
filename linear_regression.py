
import numpy as np
import sys
from sklearn.linear_model import LinearRegression

if __name__ == "__main__":
    if sys.argv[1] is not None:
        training_data = np.genfromtxt(sys.argv[1], delimiter=',').astype(int)
        X, y = training_data[:, :-1], training_data[:, -1].astype(int)
        reg = LinearRegression().fit(X, y)
        print(reg.score(X, y))