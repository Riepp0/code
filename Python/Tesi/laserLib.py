from tkinter import *
from serial import *
from PIL import *

class LaserBox:
    """ Creating a laser class """

    global laser, on, off

    ### Images
    on = PhotoImage(file=r"C:\Users\miche\source\vscode\code\Python\Tesi\on.png")
    off = PhotoImage(file=r"C:\Users\miche\source\vscode\code\Python\Tesi\off.png")


    def __init__() -> None:
        """ Initialize laser """

        laser = Serial("COM5", timeout=3)  
    
    def powerOn():
        """ Power on laser """

        laser.write("lon\r\n".encode())
        laser.readline().decode("utf-8")

    def powerOff():
        """ Power off laser """

        laser.write("loff\r\n".encode())
        laser.readline().decode("utf-8")
    
    def switch(on_button, my_label):
        """ Switch laser ON/OFF """

        if is_on:
            on_button.config(image=off)
            my_label.config(text="The laser is OFF")
            # TURN LASER OFF THRUGH COMMAND
            laser.powerOff()
            is_on = False
        else:
            on_button.config(image=on)
            my_label.config(text="The laser is ON")
            # TURN LASER ON THRUGH COMMAND
            laser.powerOn()
            is_on = True

    def setCurrent(current):
        """ Set current through serial command """

        laser.write(("slc:"+current+"\r\n").encode())
        laser.readline().decode("utf-8")

    def setTemp(temp):
        """ Set temperature through serial command """

        laser.write(("stt:"+temp+"\r\n").encode())
        laser.readline().decode("utf-8")
    
    def getCurrent(root):
        """ Get current through serial command """

        laser.write("rli?\r\n".encode())
        my_actual_temp = Label(root, text="Actual current: " + laser.readline().decode("utf-8") , font=("Helvetica", 14))
        my_actual_temp.grid(row=4, column=1)

    def getTemp(root):
        """ Get temperature through serial command """

        laser.write("rtt?\r\n".encode())
        my_current_temp = Label(root, text="Actual temperature: " + laser.readline().decode("utf-8"), font=("Helvetica", 14))
        my_current_temp.grid(row=5, column=1)
    
    def checkCurrent(is_on, current_err, current_text):
        """ Check if current is a valid number """

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
            laser.setCurrent(current)

    def checkTemp(is_on, temp_err, temp_text):
        """ Check if temperature is a valid number """

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
            laser.setTemp(temp)

    


