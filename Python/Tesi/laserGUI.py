from distutils.command.config import config
import glob, os
from tkinter import *
from tkinter.ttk import *
from PIL import Image, ImageFont, ImageDraw, ImageTk


### Create object
root = Tk()

### Add title
root.title("Laser GUI")

### Dimension
root.geometry("800x600")

is_on = False

### Create label
my_label = Label(root, text = "The Switch Is On", font = ("Helvetica", 14))
my_label.pack(pady = 20)

### Switch function
def switch():
    global is_on
     
    # Determine is on or off
    if is_on:
        on_button.config(image = on)
        my_label.config(text = "The Switch is On")
        is_on = False
    else:
        on_button.config(image = off)
        my_label.config(text = "The Switch is Off")
        is_on = True

### Images
on = PhotoImage(file = r"D:\code\code\Python\Tesi\on.png")
off = PhotoImage(file = r"D:\code\code\Python\Tesi\off.png")

### Create A Button
on_button = Button(root, image = on, command = switch)
on_button.pack(pady = 20)

### Create an edittext
#my_entry = Entry(root, font=("Helvetica", 14))
#my_entry.pack(pady = 20)

### Execute
root.mainloop()