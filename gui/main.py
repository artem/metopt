import tkinter as tk
from tkinter import ttk

from matplotlib.backends.backend_tkagg import FigureCanvasTkAgg

import values
import binds
import matplotlib.pyplot as plt
import matplotlib

data = 0


def choose_method(event):
    global data
    data = 0


def launch():
    print("launched")
    paint([1, 2, 3], [0.2, 0.3, 0.25])


def paint(x, y):
    plt.clf()
    plt.plot(x, y)
    fig.canvas.draw()


matplotlib.use('TkAgg')
window = tk.Tk()
window.title = "Metopt"
window.geometry("600x600")

lbl = tk.Label(window, text="Выберите метод")
lbl.grid(column=0, row=0)

combo = ttk.Combobox(window, values=values.methods)
combo.grid(column=0, row=1)
combo.current(0)
combo.bind("<<ComboboxSelected>>", choose_method)

launch = ttk.Button(window, text="Launch", command=launch)
launch.grid(column=0, row=10)

fig = plt.figure(1)
canvas = FigureCanvasTkAgg(fig, master=window)
plot_widget = canvas.get_tk_widget()
plot_widget.grid(column=0, row=2)

window.mainloop()
