
from tkinter import Label
from matplotlib import pyplot as plt

import seabreeze
seabreeze.use('pyseabreeze')
from seabreeze.spectrometers import list_devices, Spectrometer

import sys
import numpy as np
import matplotlib
matplotlib.use('Qt5Agg')

from PyQt5 import QtCore, QtWidgets, QtGui

from matplotlib.backends.backend_qt5agg import FigureCanvasQTAgg as FigureCanvas
from matplotlib.figure import Figure

class Spectro():
    """Creating a Spectrometer class"""

    def __init__(self):
        """Initialize spectrometer"""
        self.spectrometer = Spectrometer.from_first_available()

    def printDevices(self):
        """Print devices"""
        print(list_devices())

    def getSpectrometer(self):
        """Get spectrometer"""
        return self.spectrometer

    def setIntegrationTime(self, time, label : Label):
        """Set integration time
            @param time: int """
        if (not(isinstance(time, int))):
            try:
                time = int(time)
                if not(isinstance(time, int)):
                    raise ValueError
            except ValueError:
                label.config(text="Insert a valid number!")
                return
        if time < 1000 or time > 20000:
            label.config(text="Insert a valid value!")
        else:
            self.spectrometer.integration_time_micros(time)
            label.config(text="Time set!")
        

    def getWaveLength(self):
        """Get wavelength
            @return: list """
        return self.spectrometer.wavelengths()

    def getIntensities(self):
        """Get intensities
            @return: list """
        return self.spectrometer.intensities()

    def getSpectrum(self):
        """Get spectrum
            @return: list """
        return self.spectrometer.spectrum()

    def isSaturated(self):
        """Check if spectrum is saturated"""
        if max(self.getIntensities()) > 0.9 * self.spectrometer.max_intensity:
            print(max(self.getIntensities()))
            return True
        else:
            return False

class MplCanvas(FigureCanvas):

    def __init__(self, parent=None, width=5, height=4, dpi=100):
        fig = Figure(figsize=(width, height), dpi=dpi)
        self.axes = fig.add_sublot(111)
        super(MplCanvas, self).__init__(fig)

class MainWindow(QtWidgets.QMainWindow):

    def __init__(self, *args, **kwargs):
        super(MainWindow, self).__init__(*args, **kwargs)

        # Create the maptlotlib FigureCanvas object,
        # which defines a single set of axes as self.axes.
        self.canvas = MplCanvas(self, width=5, height=4, dpi=100)
        self.setCentralWidget(self.canvas)
        
        n_data = max(Spectro.getWaveLength)
        self.xdata = list(range(n_data))
        self.ydata = Spectro.getIntensities()
        self.update()

        self.show()

        # Setup a timer to trigger the redraw of the plot
        self.timer = QtCore.QTimer()
        self.timer.setInterval(100)
        self.timer.timeout(self.update)
        self.timer.start()

    def update_plot(self):
        None