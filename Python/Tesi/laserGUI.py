from distutils.command.config import config
import glob, os
import tkinter as tk
from PIL import Image, ImageTk
from tkinter import *
from tkinter import font

### Create object
root = tk.Tk()

### Add title
root.title("Laser GUI")

### Dimension
root.geometry("800x600")


### Create label 
is_on = False
my_label = Label(root, text = "Laser is off", fg="red", font = ("Helvetica", 14))
my_label.pack(pady = 10)

### Switch function
def switch():
    global is_on

    if is_on:
        on_button:config(image = off)
        my_label.config(text = "Laser is off", fg="grey")
        is_on = True
    else:
        on_button:config(image = on)
        my_label.config(text = "Laser is on", fg="green")
        is_on = False
### Define images
imon = Image.open("off.png")
on = ImageTk.PhotoImage(imon)
imoff = Image.open("./img/on.png")
off = ImageTk.PhotoImage(imoff)

### Button
on_button = Button(root, image = on, bd = 0, command = switch)
on_button.pack(pady = 10)

### Execute
root.mainloop()