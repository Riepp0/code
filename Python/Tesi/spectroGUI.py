from spectroLib import *
from tkinter import *
from matplotlib import figure
import numpy as np

spectro = Spectro()

### Create root object
root = Tk()

### Add title
root.title("Spectro GUI")

### Dimension
root.geometry("800x600")

### Create serial label
my_spectrometer = Label(root, font=("Helvetica", 14))
def getSerial():
    serial = spectro.printDevices()
    my_spectrometer.config(text="Spectrometer: " + serial)

get_my_spectrometer = Button(root, text="Get spectrometer", command=getSerial)
get_my_spectrometer.grid(row=0, column=0)
my_spectrometer.grid(row=0, column=1)

### Create integration time label
my_integration_time = Label(root, text="Set integration time", font=("Helvetica", 14))
my_integration_time.grid(row=1, column=0)

### Create integration time entry
my_integration_entry = Entry(root, width=10)
my_integration_entry.grid(row=1, column=1)

### Create integration time button
my_integration_button = Button(root, text="Set integration time", command=lambda: spectro.setIntegrationTime(int(my_integration_entry.get())))
my_integration_button.grid(row=1, column=2)

### Define plotting function
def plotSpectrum():
    """Plot spectrum"""
    x = spectro.getWaveLength()
    y = spectro.getIntensities()
    plt.ion()
    fig = plt.figure()

### Create spectrum button
my_spectrum_button = Button(root, text="Get spectrum", command=lambda: plotSpectrum())
my_spectrum_button.grid(row=2, column=0)



