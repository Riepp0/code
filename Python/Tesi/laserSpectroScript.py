from spectroLib import *
from laserLib import * 

# Create laser object
laser = LaserBox()

# Power on the laser
laser.powerOn()

# Get laser states
print(laser.getCurrent())
print(laser.getTemp())

# Set laser states <-------------------------------------------------------------
laser.setCurrent(40)
laser.setTemp(20)

# Create spectrometer object
spectro = Spectro()

# Get active spectrometer
spectro.printDevices()

# Get the spectrometer integration time
print(spectro.getIntegrationTime())

# Set the spectrometer integration time <-----------------------------------------
spectro.setIntegrationTime(10000)

# Get the spectrometer spectrum
# But before check if it is saturated
if spectro.isSaturated():
    print("Saturated!")
else:
    print(spectro.getSpectrum())

# !Now plot the spectrum!
spectro.plotSpectrum()


# Power off the laser
laser.powerOff()