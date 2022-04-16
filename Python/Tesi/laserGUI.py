from distutils.command.config import config
import glob, os
import tkinter as tk
from tkinter import *
from PIL import Image, ImageFont, ImageDraw, ImageTk


### Create object
root = tk.Tk()

### Add title
root.title("Laser GUI")

### Dimension
root.geometry("800x600")

is_on = True

### Create label
my_label = Label(root,
    text = "The Switch Is On!",
    fg = "green",
    font = ("Helvetica", 14))
 
my_label.pack(pady = 20)

### Switch function
def switch():
    global is_on
     
    # Determine is on or off
    if is_on:
        on_button.config(image = off)
        my_label.config(text = "The Switch is Off",
                        fg = "grey")
        is_on = False
    else:
       
        on_button.config(image = on)
        my_label.config(text = "The Switch is On", fg = "green")
        is_on = True

### Images
on = PhotoImage(file = "on.png")
off = PhotoImage(file = "off.png")

# Create A Button
on_button = Button(root, image = on, bd = 0,
                   command = switch)
on_button.pack(pady = 50)

### Execute
root.mainloop()