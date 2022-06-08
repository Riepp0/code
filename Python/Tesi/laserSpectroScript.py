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
laser.setCurrent(60)
laser.setTemp(20)

# Create spectrometer object
spectro = Spectro()

# Get active spectrometer
spectro.printDevices()

# Set the spectrometer integration time <-----------------------------------------
spectro.setIntegrationTimeScript(10000000)



# Get the spectrometer spectrum
# But before check if it is saturated
i = 0
while(i < 10):
    print(spectro.getSpectrum()[1,200])
    i += 1
spectro.terminate()


# !Now plot the spectrum!
#spectro.plotSpectrum()


# Power off the laser
#laser.powerOff()