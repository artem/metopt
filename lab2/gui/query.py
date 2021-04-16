import values
import subprocess
import numpy as np
import json
from function import Function

# java_path = ''
java_path = 'C:\\Program Files\\Java\\jdk-14.0.2\\bin\\'


def call(method, function, eps=0.001, ng=0, alpha=1):
    inp = subprocess.check_output([f'{java_path}java', '-Xmx512M', '-jar',
                                   values.jar_path, '-m', str(method), '-f', str(function),
                                   '-e', str(eps), '-ng', str(ng), '-a', str(alpha)])
    js = json.loads(inp)
    jsf = js['function']
    func = Function(jsf['A']['data'], jsf['b']['data'], jsf['c'])
    steps = np.array([x['data'] for x in js['steps']])
    return func, steps
