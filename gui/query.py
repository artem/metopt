import values
import subprocess


def call(number):
    # inp = subprocess.check_output(["java", "-jar", values.jar_path, number])
    inp = values.input_mosk
    data = inp.split(sep='\n')
    return float(data[0]), float(data[1]), [list(map(float, i.split())) for i in data[2:]]
