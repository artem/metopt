import values
import subprocess
import numpy as np
import json

# java_path = ""
java_path = "C:\\Program Files\\Java\\jdk-14.0.2\\bin\\"


def call(method, function, eps='0.001', ng=0, alpha=1):
    inp = subprocess.check_output([f"{java_path}java", "-Xmx512M", "-jar",
                                   values.jar_path, '-m', method, '-f', function,
                                   '-e', eps, '-ng', ng, '-a', alpha])
    return json.loads(inp)
