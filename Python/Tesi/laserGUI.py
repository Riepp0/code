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


### Create A Button (SWitch)
on_button = Button(root, image=off)
on_button.config(command=laser.switch(is_on, on_button, my_label))
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

### Create set button
set_current = Button(root, text="Set", command=laser.checkCurrent(is_on, current_err, current_text))
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

# Create set button
set_temp = Button(root, text="Set", command=laser.checkCurrent(is_on, temp_err, temp_text))
set_temp.grid(row=3, column=3)
##########################################################################################################

### GET LASER CURRENT CURRENT THRUGH COMMAND

get_current = Button(root, text="Get current", command=laser.getCurrent(root))
get_current.grid(row=4, column=0)

### GET LASER CURRENT TEMPERATURE THRUGH COMMAND

get_temp = Button(root, text="Actual temperature", command=laser.getTemp(root))
get_temp.grid(row=5, column=0)


### Execute
root.mainloop()
