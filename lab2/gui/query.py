import values
import subprocess
import numpy as np
import json

# java_path = ""
java_path = "C:\\Program Files\\Java\\jdk-14.0.2\\bin\\"


def call(number, eps):
    inp = subprocess.check_output([f"{java_path}java", "-Xmx512M", "-jar",
                                   values.jar_path, str(number), str(eps)])
    return json.loads(inp)


def call_get_number(number, eps):
    return int(subprocess.check_output([f"{java_path}java", "-jar", values.jar_path, str(number), str(eps), "-t"]))
