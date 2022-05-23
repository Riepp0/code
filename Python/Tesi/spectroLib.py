import sys
from tkinter import Label


import seabreeze
seabreeze.use('pyseabreeze')
from seabreeze.spectrometers import list_devices, Spectrometer

import pyqtgraph as pg
from PyQt5.QtWidgets import QApplication
from pyqtgraph.ptime import time

import numpy as np

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

    def upgrade(self):

        x = self.getWaveLength()
        print(x)
        y = self.getIntensities()
        print(y)
        curve.setData(x,y)

    def plotSpectrum(self):
        global x,y,curve
        app = QApplication([])
        plot = pg.GraphicsWindow()

        p1 = plot.addPlot()
        x = np.zeros(len(self.getWaveLength()))
        y = np.zeros(len(self.getIntensities()))
        print(x)
        curve = p1.plot(x, y, pen = None, symbol = 'o')

        app.exec()

        timer = pg.QtCore.QTimer()
        timer.timeout.connect(self.upgrade)
        timer.start(1000)


    
