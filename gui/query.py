import values
import subprocess
import numpy as np


def call(number, eps):
    # C:\\Program Files\\Java\\jdk-14.0.2\\bin\\
    inp = subprocess.check_output(["java", "-Xmx512M", "-jar",
                                   values.jar_path, str(number), str(eps)])
    data = inp.splitlines()
    return float(data[0]), float(data[1]), [list(map(float, i.split())) for i in data[2:]]
