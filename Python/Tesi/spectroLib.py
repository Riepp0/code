import seabreeze
seabreeze.use('pyseabreeze')
from seabreeze.spectrometers import list_devices, Spectrometer
from matplotlib import pyplot as plt

class Spectro():
    """Creating a Spectrometer class"""

    def __init__(self):
        """Initialize spectrometer"""
        self.spectrometer = Spectrometer.from_first_available()

    def printDevices(self):
        """Print devices"""
        print(list_devices())

    def setIntegrationTime(self, time):
        """Set integration time
            @param time: int """
        self.spectrometer.integration_time_micros(time)

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

    def plotSpectrum(self):
        """Plot spectrum"""
        plt.plot(self.getWaveLength(), self.getIntensities())
        plt.xlabel('Wavelength (nm)')
        plt.ylabel('Intensity')
        plt.show()

    def isSaturated(self):
        """Check if spectrum is saturated"""
        if max(self.getIntensities()) > 0.9 * self.spectrometer.max_intensity:
            print(max(self.getIntensities()))
            return True
        else:
            return False

