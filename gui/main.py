import tkinter as tk
from tkinter import ttk
import numpy as np

from matplotlib.backends.backend_tkagg import FigureCanvasTkAgg

import values
import query
import matplotlib.pyplot as plt
import matplotlib

left_side = -1
right_side = 1


def launch():
    global min_x_label, min_y_label
    eps = float(entry.get())
    ind = values.methods.index(current_method.get())
    x, y, steps = query.call(ind, eps)
    paint(steps)
    min_x_label['text'] = x
    min_y_label['text'] = y
    steps_label['text'] = len(steps)


def paint_main_function():
    global ax
    fig.clf()
    xs = np.linspace(left_side, right_side, 100)
    ys = values.function(xs)
    ax = fig.add_subplot(111)
    ax.plot(xs, ys)
    fig.canvas.draw()


def paint_parabola(a, b, c):
    xs = np.linspace(left_side, right_side, 100)
    ys = a * np.power(xs, 2) + b * xs + c
    ax.plot(xs, ys)


def paint(steps):
    global left_side, right_side
    left_side = float(scale_left.get())
    right_side = float(scale_right.get())
    paint_main_function()
    for i in steps:
        if len(i) == 4:
            arr = zip(i[::2], i[1::2])
            arr = [i for i in arr if left_side < i[0] < right_side]
            x = [i[0] for i in arr]
            y = [i[1] for i in arr]
            ax.scatter(x, y)
            ax.plot(x, y)
        else:
            paint_parabola(*i)
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


tk.Label(window, text="Left side").grid(column=0, row=2)
tk.Label(window, text="Right side").grid(column=0, row=3)
scale_left = tk.Scale(window, orient=tk.HORIZONTAL, length=300, from_=-1, to=1, tickinterval=1, resolution=0.001)
scale_right = tk.Scale(window, orient=tk.HORIZONTAL, length=300, from_=-1, to=1, tickinterval=1, resolution=0.001)
scale_left.grid(column=1, row=2, columnspan=2)
scale_right.grid(column=1, row=3, columnspan=2)
scale_left.set(left_side)
scale_right.set(right_side)

fig = plt.figure(1)
canvas = FigureCanvasTkAgg(fig, master=window)
plot_widget = canvas.get_tk_widget()
plot_widget.grid(column=0, row=4, columnspan=3)
ax = fig.add_subplot(111)
paint_main_function()

ttk.Label(window, text="Min x:").grid(column=0, row=5, pady=5)
ttk.Label(window, text="Min y:").grid(column=1, row=5, pady=5)
ttk.Label(window, text="Steps:").grid(column=2, row=5, pady=5)
min_x_label = ttk.Label(window, text='not calculated')
min_y_label = ttk.Label(window, text='not calculated')
steps_label = ttk.Label(window, text='not calculated')
min_x_label.grid(column=0, row=6, pady=5)
min_y_label.grid(column=1, row=6, pady=5)
steps_label.grid(column=2, row=6, pady=5)

launch_btn = tk.Button(window, text="Launch", command=launch, height=2, width=20, background='black',
                       foreground="white")
launch_btn.grid(column=0, row=7, columnspan=3, pady=5)

window.mainloop()
