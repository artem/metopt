import numpy as np


class Function:
    def __init__(self, a, b, c, d=""):
        self.description = d
        self.a = np.array(a)
        self.b = np.array(b)
        self.c: float = c

    def eval(self, x: np.ndarray):
        return self.a.dot(x).T.dot(x) / 2 + self.b.T.dot(x) + self.c

    def __str__(self):
        return '{"A":\n' + str(self.a) + ',\n "b":\n' + str(self.b) + ',\n "c":\n' + str(self.c) + '\n}'
