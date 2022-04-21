from asyncore import write
from distutils.command.config import config
from tkinter import *
from tkinter.ttk import *
from PIL import Image, ImageFont, ImageDraw, ImageTk
import glob, os, serial


LOWCURRENTLIMIT = 40
HIGHCURRENTLIMIT = 50
LOWTEMPERATURELIMIT = 20
HIGHTEMPERATURELIMIT = 25

laser = serial.Serial('COM5', timeout=3)

### Create object
root = Tk()

### Add title
root.title("Laser GUI")

### Dimension
root.geometry("800x600")

is_on = True

### Create label + button
my_label = Label(root, text="The laser is OFF", font=("Helvetica", 14))
my_label.grid(row=0, column=0, columnspan=1)

### Switch function
def switch():
    global is_on

    # Determine is on or off
    if is_on:
        on_button.config(image=off)
        my_label.config(text="The laser is OFF")
        # TURN LASER OFF THRUGH COMMAND
        laser.write("loff\r\n".encode())
        off_label = Label(root, text=laser.readline().decode("utf-8"), font=("Helvetica", 14))
        off_label.grid(row=6, column=0)
        is_on = False
    else:
        on_button.config(image=on)
        my_label.config(text="The laser is ON")
        # TURN LASER ON THRUGH COMMAND
        laser.write("lon\r\n".encode())
        on_label = Label(root, text=laser.readline().decode("utf-8"), font=("Helvetica", 14))
        on_label.grid(row=7, column=0)
        is_on = True


### Images
on = PhotoImage(file=r"C:\Users\miche\source\vscode\code\Python\Tesi\on.png")
off = PhotoImage(file=r"C:\Users\miche\source\vscode\code\Python\Tesi\off.png")

### Create A Button (SWitch)
on_button = Button(root, image=off, command=switch)
on_button.grid(row=1, column=0)
##########################################################################################################

### Create label
my_current = Label(root, text="Insert current", font=("Helvetica", 14))
my_current.grid(row=2, column=0)
my_mA = Label(root, text="mA", font=("Helvetica", 14))
my_mA.grid(row=2, column=2)

### Create edit text
current_text = Entry(root, width=10)
current_text.grid(row=2, column=1)

### Check if current is a good number
# Create error label
current_err = Label(root, font=("Helvetica", 14))
current_err.grid(row=2, column=4)


def check_current():
    if is_on == False:
        current_err.config(text="The laser is OFF")
        return
    try:
        current = float(current_text.get())
        print(current_text.get())
        if current < LOWCURRENTLIMIT or current > HIGHCURRENTLIMIT:
            raise ValueError
    except ValueError:
        current_err.config(text="Insert a valid number!")
    else:
        current_err.config(text="Current set!")
        # SET LASER current THRUGH COMMAND
        laser.write(("slc:"+current_text.get()+"\r\n").encode())
        laser.readline().decode("utf-8")


### Create set button
set_current = Button(root, text="Set", command=check_current)
set_current.grid(row=2, column=3)
##########################################################################################################

### Create label
my_temp = Label(root, text="Insert temperature", font=("Helvetica", 14))
my_temp.grid(row=3, column=0)
my_C = Label(root, text="°C", font=("Helvetica", 14))
my_C.grid(row=3, column=2)

### Create edit text
temp_text = Entry(root, width=10)
temp_text.grid(row=3, column=1)

### Check if temperature is a good number
# Create error label
temp_err = Label(root, font=("Helvetica", 14))
temp_err.grid(row=3, column=4)


def check_temp():
    if is_on == False:
        temp_err.config(text="The laser is OFF")
        return
    try:
        temp = float(temp_text.get())
        if temp < LOWTEMPERATURELIMIT or temp > HIGHTEMPERATURELIMIT:
            raise ValueError
    except ValueError:
        temp_err.config(text="Insert a valid number!")
    else:
        temp_err.config(text="Temperature set!")
        # SET LASER TEMPERATURE THRUGH COMMAND
        laser.write(("stt:"+temp_text.get()+"\r\n").encode())
        laser.readline().decode("utf-8")


# Create set button
set_temp = Button(root, text="Set", command=check_temp)
set_temp.grid(row=3, column=3)
##########################################################################################################

### GET LASER CURRENT CURRENT THRUGH COMMAND
def getCurrent():
    laser.write("rli?\r\n".encode())
    my_actual_temp = Label(root, text="Actual current: " + laser.readline().decode("utf-8") , font=("Helvetica", 14))
    my_actual_temp.grid(row=4, column=1)

get_current = Button(root, text="Get current", command=getCurrent)
get_current.grid(row=4, column=0)

### GET LASER CURRENT TEMPERATURE THRUGH COMMAND
def getTemp():
    laser.write("rtt?\r\n".encode())
    my_current_temp = Label(root, text="Actual temperature: " + laser.readline().decode("utf-8"), font=("Helvetica", 14))
    my_current_temp.grid(row=5, column=1)

get_temp = Button(root, text="Actual temperature", command=getTemp)
get_temp.grid(row=5, column=0)


### Execute
root.mainloop()
