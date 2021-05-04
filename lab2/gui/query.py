import values
import subprocess
import numpy as np
import json
from function import Function
import os.path


def call(method, function, eps=0.001, ng=0, alpha=1):
    inp = subprocess.check_output([f'{values.java_path}java', '-Xmx512M', '-jar',
                                   values.jar_path, '-m', str(method), '-f', str(function),
                                   '-e', str(eps), '-ng', str(ng), '-a', str(alpha)])
    return load(inp)


def call_custom(method, f, eps=0.001, ng=0, alpha=1):
    with open(values.matrix_file_name, 'w') as r:
        r.write(f)
    file = os.path.abspath(values.matrix_file_name)
    inp = subprocess.check_output([f'{values.java_path}java', '-Xmx512M', '-jar',
                                   values.jar_path, '-c', str(file), '-m', str(method),
                                   '-e', str(eps), '-ng', str(ng), '-a', str(alpha)])
    return load(inp)


def load(inp):
    js = json.loads(inp)
    jsf = js['function']
    func = Function(jsf['A']['data'], jsf['b']['data'], jsf['c'], x0=jsf['x0']['data'])
    steps = np.array([x['data'] for x in js['steps']])
    load.json_func = json.dumps(jsf, indent=4)
    return func, steps
