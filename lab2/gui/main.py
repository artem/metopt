import os
import tkinter as tk
from tkinter import ttk
import numpy as np
from pylab import cm, imshow, contour, clabel, colorbar, title, show
import json
from function import Function

# from matplotlib.backends.backend_tkagg import FigureCanvasTkAgg

import values
import query
import matplotlib.pyplot as plt
import matplotlib


class SteppingController:
    def __init__(self, steps: np.ndarray):
        self.is_active = False
        self.steps = steps.reshape((steps.shape[0], 2))
        # self.steps = np.array([(x[0][0], x[1][0]) for x in steps])
        self.steps_number = self.steps.shape[0]
        self.current = 0

    def get_radius(self):
        if self.is_active:
            x1, y1 = self.steps[self.current]
            x2, y2 = self.steps[self.current + 1]
            center = (abs(x2 - x1), abs(y2 - y1))
            radius = max(abs(x2 - x1), (y2 - y1))
            return center, radius * 1.2
        else:
            x1, y1 = self.steps.min(axis=0)
            x2, y2 = self.steps.max(axis=0)
            radius = max(abs(self.steps[-1][0] - x1), abs(self.steps[-1][1] - y1),
                         abs(self.steps[-1][0] - x2), abs(self.steps[-1][1] - y2))
            center = self.steps[-1]
            return center, radius * 1.2

    def next(self):
        if self.is_active:
            if self.current < self.steps_number - 1:
                self.current += 1
            return self.current

    def prev(self):
        if self.is_active:
            if self.current > 0:
                self.current -= 1
            return self.current


class GUI:
    def __init__(self):
        self.center = [0, 0]
        self.radius = 100.
        self.size = 100.

        self.method = None
        self.functionId = None
        self.function = None
        self.steps = None
        self.steps_controller = None

        self.settings = {
            'eps': '1e-3',
            'start': '0 0',
            'alpha': '1',
            'ng': '0'
        }

        matplotlib.use('TkAgg')
        self.window = tk.Tk()
        self.window.title('Metopt laba 2')
        # window.geometry('640x640')

        tk.Label(self.window, text='Метод').grid(column=0, row=0, pady=3)
        self.selected_method = tk.StringVar()
        method_selector = ttk.Combobox(self.window, textvariable=self.selected_method, values=values.methods,
                                       state='readonly')
        method_selector.grid(column=1, row=0, pady=3)
        method_selector.current(0)

        tk.Label(self.window, text='Фун.').grid(column=2, row=0, pady=3)
        self.selected_function = tk.StringVar()
        func_selector = ttk.Combobox(self.window, textvariable=self.selected_function,
                                     values=values.functions, state='readonly')
        func_selector.grid(column=3, row=0, pady=3)
        func_selector.current(0)

        tk.Button(self.window, text='Parameters', command=self.func, height=2, width=15, background='black',
                  foreground='white').grid(column=0, row=1, pady=5)
        tk.Button(self.window, text='Launch', command=self.launch, height=2, width=15, background='green',
                  foreground='white').grid(column=1, row=1, pady=5)
        tk.Button(self.window, text='Refresh', command=self.paint_main_function, height=2, width=15, background='black',
                  foreground='white').grid(column=2, row=1, pady=5)

        self.current_action = tk.StringVar()
        action_selector = ttk.Combobox(self.window, textvariable=self.current_action, values=values.actions,
                                       state='readonly')
        action_selector.grid(column=3, row=1, pady=3)
        action_selector.current(0)

        tk.Label(self.window, text='Zoom').grid(column=0, row=2)
        self.zoom_entry = tk.Scale(self.window, from_=1, to=200, len=400, orient=tk.HORIZONTAL, resolution=1)
        self.zoom_entry.grid(column=1, row=2, columnspan=3, pady=3)

        # self.fig = plt.figure(1)
        # self.canvas = FigureCanvasTkAgg(self.fig, master=self.window)
        self.text_editor = tk.Text()
        self.text_editor.grid(column=0, row=4, columnspan=4)
        scroll = tk.Scrollbar(command=self.text_editor.yview)
        scroll.grid(column=5, row=4, sticky='nsew')
        self.text_editor['yscrollcommand'] = scroll.set

        tk.Button(self.window, text='Stepping', command=self.steps_activate, height=2, width=15) \
            .grid(column=0, row=5, pady=5)
        tk.Button(self.window, text='Prev', command=self.steps_prev, height=2, width=15).grid(column=1, row=5, pady=5)
        tk.Button(self.window, text='Next', command=self.steps_next, height=2, width=15).grid(column=2, row=5, pady=5)
        tk.Button(self.window, text='All', command=self.steps_deactivate, height=2, width=15) \
            .grid(column=3, row=5, pady=5)

        frame_gui_settings = tk.Frame()
        self.var_lines = tk.BooleanVar()
        self.var_arrows = tk.BooleanVar()
        self.var_axis = tk.BooleanVar()
        self.var_text = tk.BooleanVar()
        self.var_lines.set(True)
        self.var_arrows.set(True)
        self.var_axis.set(True)
        self.var_text.set(True)
        tk.Checkbutton(frame_gui_settings, text='Lines',
                       variable=self.var_lines,
                       onvalue=1, offvalue=0,
                       command=self.paint_main_function).pack(anchor=tk.W)
        tk.Checkbutton(frame_gui_settings, text='Arrows',
                       variable=self.var_arrows,
                       onvalue=1, offvalue=0,
                       command=self.paint_main_function).pack(anchor=tk.W)
        tk.Checkbutton(frame_gui_settings, text='Axis',
                       variable=self.var_axis,
                       onvalue=1, offvalue=0,
                       command=self.paint_main_function).pack(anchor=tk.W)
        tk.Checkbutton(frame_gui_settings, text='Text',
                       variable=self.var_text,
                       onvalue=1, offvalue=0,
                       command=self.paint_main_function).pack(anchor=tk.W)
        frame_gui_settings.grid(column=0, row=6)

        frame_views = tk.Frame()
        ttk.Label(frame_views, text='Min x:').pack(anchor=tk.W)
        ttk.Label(frame_views, text='Min value:').pack(anchor=tk.W)
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

    def steps_activate(self):
        if self.steps_controller:
            self.steps_controller.is_active = True
            self.paint_main_function()

    def steps_deactivate(self):
        if self.steps_controller:
            self.steps_controller.is_active = False
            self.paint_main_function()

    def steps_next(self):
        if self.steps_controller:
            self.steps_controller.next()
            self.paint_main_function()

    def steps_prev(self):
        if self.steps_controller:
            self.steps_controller.prev()
            self.paint_main_function()

    def launch(self):
        action = values.actions.index(self.current_action.get())
        if action == 0:
            self.method = values.methods.index(self.selected_method.get())
            for i, f in enumerate(values.functions):
                if f == self.selected_function.get():
                    self.functionId = i
                    break
            self.function, self.steps = query.call(self.method, self.functionId, eps=self.settings['eps'],
                                                   alpha=self.settings['alpha'], ng=self.settings['ng'])
        elif action == 1:
            text: str = self.text_editor.get("1.0", 'end-1c')
            self.function, self.steps = query.call_custom(self.method, text, eps=self.settings['eps'],
                                                          alpha=self.settings['alpha'], ng=self.settings['ng'])
        self.text_editor.delete('1.0', 'end')
        self.text_editor.insert(tk.END, str(self.function))
        self.text_editor.insert(tk.END, '\n[\n')
        for i in self.steps:
            self.text_editor.insert(tk.END, str(i) + '\n')
        self.text_editor.insert(tk.END, ']')
        self.function.description = values.functions[self.functionId]
        self.center = (self.steps[-1][0][0], self.steps[-1][1][0])
        self.min_x_label['text'] = self.steps[-1].T
        self.min_y_label['text'] = self.function.eval(self.steps[-1])
        self.steps_label['text'] = self.steps.shape[0]
        self.steps_controller = SteppingController(self.steps)
        self.center, self.size = self.steps_controller.get_radius()
        self.paint_main_function()

    def paint_main_function(self):
        if not self.function or self.function.b.shape[0] != 2:
            return
        plt.cla()
        plt.clf()
        zoom = (int(self.zoom_entry.get()) ** 3)
        self.radius = self.size / zoom
        x = np.arange(self.center[0] - self.radius, self.center[0] + self.radius, self.radius / 50)
        y = np.arange(self.center[1] - self.radius, self.center[1] + self.radius, self.radius / 50)
        z = np.array([[self.function.eval(np.array([j, i]).T)[0] for j in x] for i in y])
        fg = imshow(z, cmap=cm.RdBu, extent=(x[0], x[-1], y[-1], y[0]))
        mini = z.min()
        maxi = z.max()
        if self.var_lines.get():
            cset = contour(z, np.arange(mini, maxi, (maxi - mini) / 10), linewidths=2, cmap=cm.Set2,
                           extent=(x[0], x[-1], y[0], y[-1]))
            clabel(cset, inline=True, fmt='%1.1f', fontsize=10)
        if self.var_text.get():
            title(self.function.description)
        if not self.var_axis.get():
            plt.axis('off')
        else:
            colorbar(fg)
        start_ind = 0 if not self.steps_controller.is_active else self.steps_controller.current
        end_ind = self.steps.shape[0] - 1 if not self.steps_controller.is_active else start_ind + 1
        if self.var_arrows.get():
            for i in range(start_ind, end_ind):
                a1, b1 = self.steps[i].T[0]
                a2, b2 = self.steps[i + 1].T[0]
                if not self.center[0] - self.radius < a1 < self.center[0] + self.radius or \
                        not self.center[1] - self.radius < b1 < self.center[1] + self.radius or \
                        not self.center[0] - self.radius < a2 < self.center[0] + self.radius or \
                        not self.center[1] - self.radius < b2 < self.center[1] + self.radius:
                    continue
                plt.arrow(a1, b1, a2 - a1,
                          b2 - b1, width=0.002 * self.radius, head_width=0.01 * self.radius,
                          head_length=0.005 * self.radius, fc='yellow', ec='yellow')
        # self.canvas.draw()
        show()

    def func(self):
        def save():
            self.settings['eps'] = eps.get()
            self.settings['start'] = start.get().split()
            self.settings['alpha'] = alpha.get()
            self.settings['ng'] = norm.get()
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
        tk.Label(top, text='Normalized gradient').pack(anchor=tk.W)
        norm = tk.Entry(top)
        norm.insert(tk.END, self.settings['ng'])
        norm.pack(anchor=tk.W)
        tk.Button(top, text='save', command=save, height=2, width=20, background='grey',
                  foreground='white').pack(anchor=tk.W)
        top.transient(self.window)
        top.grab_set()
        top.focus_set()
        top.wait_window()


gui = GUI()
gui.window.mainloop()
