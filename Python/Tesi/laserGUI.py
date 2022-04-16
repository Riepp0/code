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

is_on = True

### Create label + button
my_label = Label(root, text = "The laser is OFF", font = ("Helvetica", 14))
my_label.grid(row = 0, column = 0, columnspan = 1)

### Switch function
def switch():
    global is_on
     
    # Determine is on or off
    if is_on:
        on_button.config(image = on)
        my_label.config(text = "The laser is ON")
        is_on = False
    else:
        on_button.config(image = off)
        my_label.config(text = "The laser is OFF")
        is_on = True

### Images
on = PhotoImage(file = r"D:\code\code\Python\Tesi\on.png")
off = PhotoImage(file = r"D:\code\code\Python\Tesi\off.png")

### Create A Button
on_button = Button(root, image = off, command = switch)
on_button.grid(row = 1, column = 0)

### Create label
my_power = Label(root, text = "Insert power (mA)", font = ("Helvetica", 14))
my_power.grid(row = 2, column = 0)

### Create edit text
power_text = Entry(root, width = 10)
power_text.grid(row = 2, column = 1)

### Create label 
my_temp = Label(root, text = "Insert temperature (°C)", font = ("Helvetica", 14))
my_temp.grid(row = 3, column = 0)

### Create edit text
temp_text = Entry(root, width = 10)
temp_text.grid(row = 3, column = 1)


### Execute
root.mainloop()