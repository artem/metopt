import tkinter as tk
from tkinter import ttk
import numpy as np

from matplotlib.backends.backend_tkagg import FigureCanvasTkAgg

import values
import query
import matplotlib.pyplot as plt
import matplotlib


def launch():
    print("launched")
    ind = values.methods.index(current_method.get())
    print(ind)
    data = query.call(ind)
    paint(data[2])


def paint_function():
    global ax
    fig.clf()
    xs = list(map(lambda x: x / 100, range(-100, 101)))
    ys = [values.function(x) for x in xs]
    ax = fig.add_subplot(111)
    ax.plot(xs, ys)
    fig.canvas.draw()


def paint(steps):
    paint_function()
    for i in steps:
        ax.scatter([i[0], i[2]], [i[1], i[3]])
    fig.canvas.draw()


matplotlib.use('TkAgg')
window = tk.Tk()
window.title = "Metopt"
window.geometry("600x600")

lbl = tk.Label(window, text="Выберите метод")
lbl.grid(column=0, row=0)

current_method = tk.StringVar()
combo = ttk.Combobox(window, textvariable=current_method, values=values.methods, state="readonly")
combo.grid(column=0, row=1)
combo.current(0)

launch = ttk.Button(window, text="Launch", command=launch)
launch.grid(column=0, row=10)

fig = plt.figure(1)
canvas = FigureCanvasTkAgg(fig, master=window)
plot_widget = canvas.get_tk_widget()
plot_widget.grid(column=0, row=2)
ax = fig.add_subplot(111)
paint_function()
window.mainloop()
