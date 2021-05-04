import numpy as np
import json


class Function:
    def __init__(self, a, b, c, d="", x0=None):
        self.description = d
        self.a = np.array(a)
        self.b = np.array(b)
        self.c: float = c
        self.x0 = np.array(x0)

    def eval(self, x: np.ndarray):
        return self.a.dot(x).T.dot(x) / 2 + self.b.T.dot(x) + self.c

    def __str__(self):
        # return json.dumps({"A": {"n": self.a.shape[0],
        #                          "m": self.a.shape[1],
        #                          "data": self.a},
        #                    "b": {"n": self.b.shape[0],
        #                          "m": self.b.shape[1],
        #                          "data": self.b},
        #                    "c": self.c,
        #                    "x0": {"n": self.x0.shape[0],
        #                           "m": self.x0.shape[1],
        #                           "data": self.x0},
        #                    }, indent=4)
        # return '{"A":{"n":' + str(self.a.shape[0]) + ',"m":' + str(self.a.shape[1]) + ',"data":' + str(self.a) + \
        #        '},"b":{"n":' + str(self.b.shape[0]) + ',"m":' + str(self.b.shape[1]) + ',"data":' + str(self.b) + \
        #        ',"c":' + str(self.c) + ',"x0":{"n":2,"m":1,"data":[[4.0],[4.0]]}}'
        return '{"A":\n' + str(self.a) + ',\n "b":\n' + str(self.b) + ',\n "c":\n' + str(self.c) + '\n}'
