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

method = None
steps = None
x_min = None
y_min = None
l = -1
r = 1


def calc_method(met):
    result = []
    for pre_eps in range(1, 13):
        eps = float(f"1e-{pre_eps}")
        result.append(query.call_get_number(met, eps))
    return result


def calc_nums():
    global ax
    result = {name: calc_method(i) for i, name in enumerate(values.methods)}
    fig.clf()
    ax = fig.add_subplot(111)
    ind = 0
    for name, vls in result.items():
        print(name, vls)
        color = values.colors[values.methods.index(name)]
        ax.plot([i+1 for i in range(len(vls))], vls, color)
        ax.text(ind*2+1, vls[2*ind], name, fontsize=6, bbox={'facecolor': color, 'alpha': 0.2})
        ind += 1
    ax.set_xlabel('Eps = 1e-x')
    ax.set_ylabel('Число вычислений функции')
    fig.canvas.draw()


def launch():
    global min_x_label, min_y_label, steps, x_min, y_min
    action = values.actions.index(current_action.get())
    if action == 0:
        eps = float(entry.get())
        ind = values.methods.index(current_method.get())
        x_min, y_min, steps = query.call(ind, eps)
        refresh()
    elif action == 1:
        calc_nums()
        x_min, y_min, steps = "Not available", "Not available", []
    min_x_label['text'] = x_min
    min_y_label['text'] = y_min
    steps_label['text'] = len(steps)


def paint_main_function():
    global ax
    fig.clf()
    xs = np.linspace(left_side, right_side, 100)
    # ys = values.function(xs)
    # ax = fig.add_subplot(111)
    # diff = max(ys) - min(ys)
    # ax.axis([left_side, right_side, min(ys) - diff * 0.1, max(ys)])
    # ax.plot(xs, ys)
    # fig.canvas.draw()


def refresh():
    global left_side, right_side
    if not steps:
        return
    zoom = (int(zoom_entry.get()) ** 3)

    left_side = x_min - (x_min - l) / zoom
    right_side = x_min + (r - x_min) / zoom
    if zoom != 1:
        perf = min(right_side - x_min, x_min - left_side)
        left_side = (left_side + x_min - perf) / 2
        right_side = (right_side + x_min + perf) / 2


def func():
    top = tk.Toplevel(window)
    button_top_level = tk.Button(top, text='Нажми').pack()
    top.transient(window)
    top.grab_set()
    top.focus_set()
    top.wait_window()


matplotlib.use('TkAgg')
window = tk.Tk()
window.title("Metopt")
# window.geometry("640x640")

lblMethod = tk.Label(window, text="Метод")
lblMethod.grid(column=0, row=0, pady=3)

current_method = tk.StringVar()
comboMethod = ttk.Combobox(window, textvariable=current_method, values=values.methods, state="readonly")
comboMethod.grid(column=1, row=0, pady=3)
comboMethod.current(0)

tk.Label(window, text="Фун.").grid(column=2, row=0, pady=3)

current_func = tk.StringVar()
combo = ttk.Combobox(window, textvariable=current_func,
                     values=[x.description for x in values.functions], state="readonly")
combo.grid(column=3, row=0, pady=3)
combo.current(0)

tk.Label(window, text="Eps:").grid(column=0, row=1, pady=3)
entry = tk.Entry(window)
entry.insert(tk.END, '1e-3')
entry.grid(column=1, row=1, pady=3)

tk.Label(window, text="Start").grid(column=2, row=1, pady=3)
entry = tk.Entry(window)
entry.insert(tk.END, '0, 0')
entry.grid(column=3, row=1, pady=3)

tk.Label(window, text="Zoom").grid(column=0, row=2)
zoom_entry = tk.Scale(window, from_=1, to=200, len=400, orient=tk.HORIZONTAL, resolution=1)
zoom_entry.grid(column=1, row=2, columnspan=3, pady=3)

fig = plt.figure(1)
canvas = FigureCanvasTkAgg(fig, master=window)
plot_widget = canvas.get_tk_widget()
plot_widget.grid(column=0, row=4, columnspan=4)
ax = fig.add_subplot(111)
paint_main_function()

ttk.Label(window, text="Min x:").grid(column=0, row=5, pady=3)
ttk.Label(window, text="Min y:").grid(column=1, row=5, pady=3)
ttk.Label(window, text="Steps:").grid(column=2, row=5, pady=3)
min_x_label = ttk.Label(window, text='not calculated')
min_y_label = ttk.Label(window, text='not calculated')
steps_label = ttk.Label(window, text='not calculated')
min_x_label.grid(column=0, row=6, pady=3)
min_y_label.grid(column=1, row=6, pady=3)
steps_label.grid(column=2, row=6, pady=3)

current_action = tk.StringVar()
comboMethod = ttk.Combobox(window, textvariable=current_action, values=values.actions, state="readonly")
comboMethod.grid(column=0, row=7, pady=3)
comboMethod.current(0)

launch_btn = tk.Button(window, text="Launch", command=launch, height=2, width=20, background='black',
                       foreground="white")
launch_btn.grid(column=1, row=7, pady=3)

launch_btn = tk.Button(window, text="Refresh", command=refresh, height=2, width=15, background='black',
                       foreground="white")
launch_btn.grid(column=2, row=7, pady=3)

window.mainloop()
