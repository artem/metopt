import tkinter as tk
from tkinter import ttk
import numpy as np

from matplotlib.backends.backend_tkagg import FigureCanvasTkAgg

import values
import query
import matplotlib.pyplot as plt
import matplotlib


def launch():
    global min_x_label, min_y_label
    eps = float(entry.get())
    ind = values.methods.index(current_method.get())
    x, y, steps = query.call(ind, eps)
    paint(steps)
    min_x_label['text'] = x
    min_y_label['text'] = y
    steps_label['text'] = len(steps)


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
        ax.scatter(i[::2], i[1::2])
    fig.canvas.draw()


matplotlib.use('TkAgg')
window = tk.Tk()
window.title("Metopt")
# window.geometry("640x640")

lbl = tk.Label(window, text="Выберите метод")
lbl.grid(column=0, row=0, pady=5)

current_method = tk.StringVar()
combo = ttk.Combobox(window, textvariable=current_method, values=values.methods, state="readonly")
combo.grid(column=1, row=0, pady=5)
combo.current(0)


tk.Label(window, text="Eps:").grid(column=0, row=1, pady=5)
entry = tk.Entry(window)
entry.insert(tk.END, '1e-10')
entry.grid(column=1, row=1, pady=5)

fig = plt.figure(1)
canvas = FigureCanvasTkAgg(fig, master=window)
plot_widget = canvas.get_tk_widget()
plot_widget.grid(column=0, row=2, columnspan=3)
ax = fig.add_subplot(111)
paint_function()

ttk.Label(window, text="Min x:").grid(column=0, row=3, pady=5)
ttk.Label(window, text="Min y:").grid(column=0, row=4, pady=5)
ttk.Label(window, text="Steps:").grid(column=0, row=5, pady=5)
min_x_label = ttk.Label(window, text='not calculated')
min_y_label = ttk.Label(window, text='not calculated')
steps_label = ttk.Label(window, text='not calculated')
min_x_label.grid(column=1, row=3, pady=5)
min_y_label.grid(column=1, row=4, pady=5)
steps_label.grid(column=1, row=5, pady=5)

launch_btn = tk.Button(window, text="Launch", command=launch, height=2, width=20, background='black',
                       foreground="white")
launch_btn.grid(column=0, row=6, columnspan=3, pady=5)

window.mainloop()
