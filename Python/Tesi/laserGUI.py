from distutils.command.config import config
from tkinter import *
from tkinter.ttk import *
from PIL import Image, ImageFont, ImageDraw, ImageTk
import glob,os


LOWPOWERLIMIT = 40
HIGHPOWERLIMIT = 70
LOWTEMPERATURELIMIT = 20
HIGHTEMPERATURELIMIT = 40
SLEEP = 5


### Create object
root = Tk()

### Add title
root.title("Laser GUI")

### Dimension
root.geometry("800x600")

is_on = False

### Create label + button
my_label = Label(root, text = "The laser is OFF", font = ("Helvetica", 14))
my_label.grid(row = 0, column = 0, columnspan = 1)

### Switch function
def switch():
    global is_on
     
    # Determine is on or off
    if is_on:
        on_button.config(image = off)
        my_label.config(text = "The laser is OFF")
        # TURN LASER OFF THRUGH COMMAND
        is_on = False
    else:
        on_button.config(image = on)
        my_label.config(text = "The laser is ON")
        # TURN LASER ON THRUGH COMMAND
        is_on = True

### Images
on = PhotoImage(file = r"C:\Users\miche\source\vscode\code\Python\Tesi\on.png")
off = PhotoImage(file = r"C:\Users\miche\source\vscode\code\Python\Tesi\off.png")

### Create A Button (SWitch)
on_button = Button(root, image = off, command = switch)
on_button.grid(row = 1, column = 0)
##########################################################################################################

### Create label
my_power = Label(root, text = "Insert power", font = ("Helvetica", 14))
my_power.grid(row = 2, column = 0)
my_mA = Label(root, text = "mA", font = ("Helvetica", 14))
my_mA.grid(row = 2, column = 2)

### Create edit text
power_text = Entry(root, width = 10)
power_text.grid(row = 2, column = 1)

### Check if power is a good number
# Create error label
power_err = Label(root, font = ("Helvetica", 14))
power_err.grid(row = 2, column = 4)
def check_power():
    if is_on == False:
        power_err.config(text = "The laser is OFF")
        return
    try:
        power = int(power_text.get())
        if power < LOWPOWERLIMIT or power > HIGHPOWERLIMIT:
            raise ValueError
    except ValueError:
        power_err.config(text = "Insert a valid number!")
    else:
        power_err.config(text = "Power set!")
        # SET LASER POWER THRUGH COMMAND
    

### Create set button
set_power = Button(root, text = "Set", command = check_power)
set_power.grid(row = 2, column = 3)
##########################################################################################################

### Create label 
my_temp = Label(root, text = "Insert temperature", font = ("Helvetica", 14))
my_temp.grid(row = 3, column = 0)
my_C = Label(root, text = "°C", font = ("Helvetica", 14))
my_C.grid(row = 3, column = 2)

### Create edit text
temp_text = Entry(root, width = 10)
temp_text.grid(row = 3, column = 1)

### Check if temperature is a good number
# Create error label
temp_err = Label(root, font = ("Helvetica", 14))
temp_err.grid(row = 3, column = 4)
def check_temp():
    if is_on == False:
        temp_err.config(text = "The laser is OFF")
        return
    try:
        temp = int(temp_text.get())
        if temp < LOWTEMPERATURELIMIT or temp > HIGHTEMPERATURELIMIT:
            raise ValueError
    except ValueError:
        temp_err.config(text = "Insert a valid number!")
    else:
        temp_err.config(text = "Temperature set!")
        # SET LASER TEMPERATURE THRUGH COMMAND

# Create set button
set_temp = Button(root, text = "Set", command = check_temp)
set_temp.grid(row = 3, column = 3)
##########################################################################################################


### Execute
root.mainloop()