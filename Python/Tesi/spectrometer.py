from GeneralDevice import GeneralDevice
from tkinter import Label

import functools
import seabreeze
seabreeze.use('pyseabreeze')
from seabreeze.spectrometers import list_devices, Spectrometer as Spectro

import pyqtgraph as pg
from PyQt5.QtWidgets import QApplication

class Spectrometer(GeneralDevice):
    """Creating a Spectrometer class"""

    # Overriding abstractmethod
    def __init__(self):
        """Initialize spectrometer"""
        self.spectrometer = Spectro.from_first_available()

    # Overriding abstractmethod
    def powerOff(self):
        self.spectrometer.close()

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

    def setIntegrationTimeScript(self,time):
        """Set integration time in microseconds"""
        return self.spectrometer.integration_time_micros(time)        

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
        return max(self.getIntensities()) > 0.9 * self.spectrometer.max_intensity

    def upgrade(self, params):
        curve = params[0]
        line = params[1]
        x = self.getWaveLength()
        y = self.getIntensities()
        curve.setXRange(min(x), max(x))
        curve.setYRange(min(y), max(y))
        line.setData(x=x, y=y)
        
        
        

    def plotSpectrum(self):
        app = QApplication([])

        curve = pg.plot()
        curve.showGrid(x = True, y = True)

        x = self.getWaveLength()
        y = self.getIntensities()
        curve.setXRange(min(x), max(x))
        curve.setYRange(min(y), max(y))
        line = curve.plot(x,y, pen ='g', symbol ='x', symbolPen ='g',
                           symbolBrush = 0.2, name ='green')

        callBack = functools.partial(self.upgrade, params = [curve,line])

        timer = pg.QtCore.QTimer()
        timer.timeout.connect(callBack)
        timer.start(1000)
        
        app.exec()

        


    
