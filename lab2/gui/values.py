import numpy as np


class Function:
    def __init__(self, a, b, c, d=""):
        self.description = d
        self.a: np.ndarray = a
        self.b: np.ndarray = b
        self.c: float = c

    def eval(self, x: np.ndarray):
        return self.a.dot(x).dot(x)/2 + self.b.dot(x) + self.c


a1 = np.array([[128, 126], [126, 128]], dtype=float)
a2 = np.array([[512, 506], [506, 512]], dtype=float)
b1 = np.array([-10, 30], dtype=float)
b2 = np.array([50, 130], dtype=float)

functions = [
    Function(a1, b1, 13, "64x^2+126xy+64y^2-10x+30y+13"),
    Function(a2, b2, 111, "254x^2+506xy+254y^2+50x+130y-111")
]

methods = ["Градиентного спуска",
           "Наискорейшего спуска",
           "Сопряженных градиентов"]

colors = ["b",
          "g",
          "y",
          "r",
          "orange"]

actions = ["Execute method",
           "Measure the iterations"]

jar_path = "../build/distributions/lib/metopt-0.1-all.jar"
