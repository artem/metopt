import tkinter as tk
from tkinter import ttk
import numpy as np
from pylab import cm, imshow, contour, clabel, colorbar, axis, title, show

from matplotlib.backends.backend_tkagg import FigureCanvasTkAgg

import values
import query
import matplotlib.pyplot as plt
import matplotlib


class GUI:
    def __init__(self):
        self.left_side = -1
        self.right_side = 1

        self.method = None
        self.steps = None
        self.x_min = None
        self.y_min = None
        self.l = -1
        self.r = 1

        self.settings = {
            'eps': '1e-3',
            'start': '0 0',
            'alpha': '1'
        }

        self.cur_data = None

        matplotlib.use('TkAgg')
        self.window = tk.Tk()
        self.window.title('Metopt laba 2')
        # window.geometry('640x640')

        tk.Label(self.window, text='Метод').grid(column=0, row=0, pady=3)
        self.current_method = tk.StringVar()
        method_selector = ttk.Combobox(self.window, textvariable=self.current_method, values=values.methods,
                                       state='readonly')
        method_selector.grid(column=1, row=0, pady=3)
        method_selector.current(0)

        tk.Label(self.window, text='Фун.').grid(column=2, row=0, pady=3)
        self.current_func = tk.StringVar()
        func_selector = ttk.Combobox(self.window, textvariable=self.current_func,
                                     values=[x.description for x in values.functions], state='readonly')
        func_selector.grid(column=3, row=0, pady=3)
        func_selector.current(0)

        tk.Button(self.window, text='Settings', command=self.func, height=2, width=15, background='black',
                  foreground='white').grid(column=0, row=1, pady=5)
        tk.Button(self.window, text='Launch', command=self.launch, height=2, width=15, background='green',
                  foreground='white').grid(column=1, row=1, pady=5)
        tk.Button(self.window, text='Refresh', command=self.refresh, height=2, width=15, background='black',
                  foreground='white').grid(column=2, row=1, pady=5)

        self.current_action = tk.StringVar()
        action_selector = ttk.Combobox(self.window, textvariable=self.current_action, values=values.actions,
                                       state='readonly')
        action_selector.grid(column=3, row=1, pady=3)
        action_selector.current(0)

        tk.Label(self.window, text='Zoom').grid(column=0, row=2)
        self.zoom_entry = tk.Scale(self.window, from_=1, to=200, len=400, orient=tk.HORIZONTAL, resolution=1)
        self.zoom_entry.grid(column=1, row=2, columnspan=3, pady=3)

        self.fig = plt.figure(1)
        canvas = FigureCanvasTkAgg(self.fig, master=self.window)
        plot_widget = canvas.get_tk_widget()
        plot_widget.grid(column=0, row=4, columnspan=4)
        self.ax = self.fig.add_subplot(111)
        self.paint_main_function()

        tk.Button(self.window, text='Stepping', command=self.func, height=2, width=15).grid(column=0, row=5, pady=5)
        tk.Button(self.window, text='Prev', command=self.func, height=2, width=15).grid(column=1, row=5, pady=5)
        tk.Button(self.window, text='Next', command=self.launch, height=2, width=15).grid(column=2, row=5, pady=5)
        tk.Button(self.window, text='All', command=self.refresh, height=2, width=15).grid(column=3, row=5, pady=5)

        frame_gui_settings = tk.Frame()
        self.var_lines = tk.BooleanVar()
        self.var_arrows = tk.BooleanVar()
        self.var_axis = tk.BooleanVar()
        self.var_text = tk.BooleanVar()
        tk.Checkbutton(frame_gui_settings, text='Lines',
                       variable=self.var_lines,
                       onvalue=1, offvalue=0,
                       command=self.refresh).pack(anchor=tk.W)
        tk.Checkbutton(frame_gui_settings, text='Arrows',
                       variable=self.var_arrows,
                       onvalue=1, offvalue=0,
                       command=self.refresh).pack(anchor=tk.W)
        tk.Checkbutton(frame_gui_settings, text='Axis',
                       variable=self.var_axis,
                       onvalue=1, offvalue=0,
                       command=self.refresh).pack(anchor=tk.W)
        tk.Checkbutton(frame_gui_settings, text='Text',
                       variable=self.var_text,
                       onvalue=1, offvalue=0,
                       command=self.refresh).pack(anchor=tk.W)
        frame_gui_settings.grid(column=0, row=6)

        frame_views = tk.Frame()
        ttk.Label(frame_views, text='Min x:').pack(anchor=tk.W)
        ttk.Label(frame_views, text='Min y:').pack(anchor=tk.W)
        ttk.Label(frame_views, text='Steps:').pack(anchor=tk.W)
        frame_views.grid(column=1, row=6)

        frame_res = tk.Frame()
        self.min_x_label = ttk.Label(frame_res, text='not calculated')
        self.min_y_label = ttk.Label(frame_res, text='not calculated')
        self.steps_label = ttk.Label(frame_res, text='not calculated')
        self.min_x_label.pack(anchor=tk.W)
        self.min_y_label.pack(anchor=tk.W)
        self.steps_label.pack(anchor=tk.W)
        frame_res.grid(column=2, row=6, columnspan=2)

    def launch(self):
        action = values.actions.index(self.current_action.get())

    def paint_main_function(self):
        self.fig.clf()
        xs = np.linspace(self.left_side, self.right_side, 100)
        # ys = values.function(xs)
        # ax = fig.add_subplot(111)
        # diff = max(ys) - min(ys)
        # ax.axis([left_side, right_side, min(ys) - diff * 0.1, max(ys)])
        # ax.plot(xs, ys)
        # fig.canvas.draw()

    def refresh(self):
        if not self.steps:
            return
        zoom = (int(self.zoom_entry.get()) ** 3)

        self.left_side = self.x_min - (self.x_min - self.l) / zoom
        self.right_side = self.x_min + (self.r - self.x_min) / zoom
        if zoom != 1:
            perf = min(self.right_side - self.x_min, self.x_min - self.left_side)
            self.left_side = (self.left_side + self.x_min - perf) / 2
            self.right_side = (self.right_side + self.x_min + perf) / 2

    def func(self):
        def save():
            self.settings['eps'] = eps.get()
            self.settings['start'] = start.get().split()
            self.settings['alpha'] = alpha.get()
            top.destroy()

        top = tk.Toplevel(self.window)
        tk.Label(top, text='Eps:').pack(anchor=tk.W)
        eps = tk.Entry(top)
        eps.insert(tk.END, self.settings['eps'])
        eps.pack(anchor=tk.W)
        tk.Label(top, text='Start').pack(anchor=tk.W)
        start = tk.Entry(top)
        start.insert(tk.END, self.settings['start'])
        start.pack(anchor=tk.W)
        tk.Label(top, text='Alpha').pack(anchor=tk.W)
        alpha = tk.Entry(top)
        alpha.insert(tk.END, self.settings['alpha'])
        alpha.pack(anchor=tk.W)
        tk.Button(top, text='save', command=save, height=2, width=20, background='grey',
                  foreground='white').pack(anchor=tk.W)
        top.transient(self.window)
        top.grab_set()
        top.focus_set()
        top.wait_window()


gui = GUI()
gui.window.mainloop()
