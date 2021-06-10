import numpy as np
import json
from math import *


class FunctionEval:
    def __init__(self, string: str):
        self.description = string
        self.fun = string

    def eval(self, x: np.ndarray):
        return [eval(self.fun.replace('{x}', '(' + str(x[0]) + ')').replace('{y}', '(' + str(x[1]) + ')'))]

    def __str__(self):
        return self.fun
