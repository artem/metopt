import tkinter as tk
from tkinter import ttk
import numpy as np
from pylab import cm, imshow, contour, clabel, colorbar, axis, title, show

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

settings = {
    'eps': '1e-3',
    'start': '0 0',
    'alpha': '1'
}


def calc_method(met):
    result = []
    for pre_eps in range(1, 13):
        eps = float(f"1e-{pre_eps}")
        result.append(query.call_get_number(met, eps))
    return result


def launch():
    global min_x_label, min_y_label, steps, x_min, y_min
    action = values.actions.index(current_action.get())


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
    global settings

    def save():
        settings['eps'] = float(eps.get())
        settings['start'] = list(map(float, start.get().split()))
        settings['alpha'] = float(alpha.get())
        top.destroy()

    top = tk.Toplevel(window)
    tk.Label(top, text="Eps:").pack(anchor=tk.W)
    eps = tk.Entry(top)
    eps.insert(tk.END, settings['eps'])
    eps.pack(anchor=tk.W)
    tk.Label(top, text="Start").pack(anchor=tk.W)
    start = tk.Entry(top)
    start.insert(tk.END, settings['start'])
    start.pack(anchor=tk.W)
    tk.Label(top, text="Alpha").pack(anchor=tk.W)
    alpha = tk.Entry(top)
    alpha.insert(tk.END, settings['alpha'])
    alpha.pack(anchor=tk.W)
    tk.Button(top, text="save", command=save, height=2, width=15, background='black',
              foreground="white").pack(anchor=tk.W)
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

tk.Button(window, text="Settings", command=func, height=2, width=15, background='black',
          foreground="white").grid(column=0, row=1, pady=5)

tk.Button(window, text="Launch", command=launch, height=2, width=15, background='green',
          foreground="white").grid(column=1, row=1, pady=5)
tk.Button(window, text="Refresh", command=refresh, height=2, width=15, background='black',
          foreground="white").grid(column=2, row=1, pady=5)
current_action = tk.StringVar()
comboMethod = ttk.Combobox(window, textvariable=current_action, values=values.actions, state="readonly")
comboMethod.grid(column=3, row=1, pady=3)
comboMethod.current(0)

tk.Label(window, text="Zoom").grid(column=0, row=2)
zoom_entry = tk.Scale(window, from_=1, to=200, len=400, orient=tk.HORIZONTAL, resolution=1)
zoom_entry.grid(column=1, row=2, columnspan=3, pady=3)

fig = plt.figure(1)
canvas = FigureCanvasTkAgg(fig, master=window)
plot_widget = canvas.get_tk_widget()
plot_widget.grid(column=0, row=4, columnspan=4)
ax = fig.add_subplot(111)
paint_main_function()

tk.Button(window, text="Stepping", command=func, height=2, width=15).grid(column=0, row=5, pady=5)
tk.Button(window, text="Prev", command=func, height=2, width=15).grid(column=1, row=5, pady=5)
tk.Button(window, text="Next", command=launch, height=2, width=15).grid(column=2, row=5, pady=5)
tk.Button(window, text="All", command=refresh, height=2, width=15).grid(column=3, row=5, pady=5)

frame_gui_settings = tk.Frame()
var_lines = tk.BooleanVar
var_arrows = tk.BooleanVar
var_axis = tk.BooleanVar
var_text = tk.BooleanVar
tk.Checkbutton(frame_gui_settings, text="Lines",
               variable=var_lines,
               onvalue=1, offvalue=0,
               command=refresh).pack(anchor=tk.W)
tk.Checkbutton(frame_gui_settings, text="Arrows",
               variable=var_lines,
               onvalue=1, offvalue=0,
               command=refresh).pack(anchor=tk.W)
tk.Checkbutton(frame_gui_settings, text="Axis",
               variable=var_lines,
               onvalue=1, offvalue=0,
               command=refresh).pack(anchor=tk.W)
tk.Checkbutton(frame_gui_settings, text="Text",
               variable=var_lines,
               onvalue=1, offvalue=0,
               command=refresh).pack(anchor=tk.W)
frame_gui_settings.grid(column=0, row=6)

frame_views = tk.Frame()
ttk.Label(frame_views, text="Min x:").pack(anchor=tk.W)
ttk.Label(frame_views, text="Min y:").pack(anchor=tk.W)
ttk.Label(frame_views, text="Steps:").pack(anchor=tk.W)
frame_views.grid(column=1, row=6)

frame_res = tk.Frame()
min_x_label = ttk.Label(frame_res, text='not calculated')
min_y_label = ttk.Label(frame_res, text='not calculated')
steps_label = ttk.Label(frame_res, text='not calculated')
min_x_label.pack(anchor=tk.W)
min_y_label.pack(anchor=tk.W)
steps_label.pack(anchor=tk.W)
frame_res.grid(column=2, row=6, columnspan=2)

window.mainloop()
