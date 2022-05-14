from spectroLib import *
from tkinter import *


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
    serial = spectro.getSpectrometer()
    my_spectrometer.config(text=str(serial))

get_my_spectrometer = Button(root, text="Get spectrometer", command=getSerial)
get_my_spectrometer.grid(row=0, column=0)
my_spectrometer.grid(row=0, column=1)

### Create integration time entry
my_integration_entry = Entry(root, width=10)
my_integration_entry.grid(row=1, column=0)

### Create confimation label time set
my_time_set = Label(root, font=("Helvetica", 14))
my_time_set.grid(row=1, column=2)

### Create integration time button
my_integration_button = Button(root, text="Set integration time", command=lambda: spectro.setIntegrationTime(my_integration_entry.get(), my_time_set))
my_integration_button.grid(row=1, column=1)

def myPlot():
    app = QtWidgets.QApplication(sys.argv)
    w = MainWindow()
    app.exec_()
    

### Create spectrum button
my_spectrum_button = Button(root, text="Get spectrum", command=myPlot)
my_spectrum_button.grid(row=2, column=0)

root.mainloop()


