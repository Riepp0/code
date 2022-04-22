from tkinter import *
from serial import *
from PIL import *
from laserLib import *


laser = LaserBox()

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

def switch():
    """ Switch laser ON/OFF """

    if is_on:
        on_button.config(image=off)
        my_label.config(text="The laser is OFF")
        # TURN LASER OFF THRUGH COMMAND
        print(laser.powerOff())
        is_on = False
    else:
        on_button.config(image=on)
        my_label.config(text="The laser is ON")
        # TURN LASER ON THRUGH COMMAND
        print(laser.powerOn())
        is_on = True

### Images
on = PhotoImage(file=r"C:\Users\miche\source\vscode\code\Python\Tesi\on.png")
off = PhotoImage(file=r"C:\Users\miche\source\vscode\code\Python\Tesi\off.png")

### Create A Button (SWitch)
on_button = Button(root, image=off)
on_button.config(command=switch())
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

# Create error label
current_err = Label(root, font=("Helvetica", 14))
current_err.grid(row=2, column=4)

def checkCurrent():
    """ Check current """

    if is_on == False:
        current_err.config(text="The laser is OFF")
        return
    try:
        current = float(current_text.get())
        if current < 40 or current > 50:
            raise ValueError
    except ValueError:
        current_err.config(text="Insert a valid number!")
    else:
        current_err.config(text="Current set!")
        print(laser.setCurrent(current))

### Create set button
set_current = Button(root, text="Set", command=checkCurrent())
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

# Create error label
temp_err = Label(root, font=("Helvetica", 14))
temp_err.grid(row=3, column=4)

def checkTemp():
    """ Check temperature """

    if is_on == False:
        temp_err.config(text="The laser is OFF")
        return
    try:
        temp = float(temp_text.get())
        if temp < 20 or temp > 25:
            raise ValueError
    except ValueError:
        temp_err.config(text="Insert a valid number!")
    else:
        temp_err.config(text="Temperature set!")
        print(laser.setTemp(temp))

# Create set button
set_temp = Button(root, text="Set", command=checkTemp())
set_temp.grid(row=3, column=3)
##########################################################################################################

# Get current
get_current = Button(root, text="Get current", command=laser.getCurrent())
my_actual_temp = Label(root, text="Actual current: " + print(laser.readLine()), font=("Helvetica", 14))
my_actual_temp.grid(row=4, column=1)
get_current.grid(row=4, column=0)

# Get temperature

get_temp = Button(root, text="Actual temperature", command=laser.getTemp())
my_current_temp = Label(root, text="Actual temperature: " + print(laser.readLine()), font=("Helvetica", 14))
my_current_temp.grid(row=5, column=1)
get_temp.grid(row=5, column=0)

# Get system power
get_power = Button(root, text="System power", command=laser.getPower())
my_power = Label(root, text="System power: " + print(laser.readLine()), font=("Helvetica", 14))
my_power.grid(row=6, column=1)
get_power.grid(row=6, column=0)

# Get system firmware version
get_firmware = Button(root, text="Firmware version", command=laser.getSystemFirmware())
my_firmware = Label(root, text="Firmware version: " + print(laser.readLine()), font=("Helvetica", 14))
my_firmware.grid(row=7, column=1)
get_firmware.grid(row=7, column=0)

# Get system serial number
get_serial = Button(root, text="Serial number", command=laser.getSerialNumber())
my_serial = Label(root, text="Serial number: " + print(laser.readLine()), font=("Helvetica", 14))
my_serial.grid(row=8, column=1)
get_serial.grid(row=8, column=0)

### Execute
root.mainloop()
