import math
import numpy as np


def function(x):
    return np.power(x, 4) - 1.5 * np.arctan(x)


methods = ["Метод дихотомии",
           "Метод золотого сечения",
           "Метод парабол",
           "Метод Фибоначчи",
           "Метод Брента"]

colors = ["b",
          "g",
          "y",
          "r",
          "orange"]

actions = ["Execute method",
           "Measure the time"]

jar_path = "../build/distributions/lib/metopt-0.1-all.jar"
