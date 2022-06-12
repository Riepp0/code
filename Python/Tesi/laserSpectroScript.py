from spectroLib import *
from laserLib import *
import pandas as pd

# Create laser object
laser = LaserBox()

# Power on the laser
laser.powerOn()

# Get laser states
print(laser.getCurrent())
print(laser.getTemp())

# Create spectrometer object
spectro = Spectro()

# Get active spectrometer
spectro.printDevices()

# Set the spectrometer integration time <-----------------------------------------
spectro.setIntegrationTimeScript(10000000)

# Set laser states
# Check if spectrometer is saturated
# If not, set the laser power to the desired value
# If yes, do not save the file and print a warning
for current in range(40,60):
    for temp in range (20,30):
        laser.setCurrent(current)
        laser.setTemp(temp)
        if spectro.isSaturated():
            print("Saturated")
        else:
            tmp = spectro.getSpectrum()
            df = pd.DataFrame(tmp)
            total = str(current * temp)
            df.to_csv("laserSpectroScript"+"total.csv")




# !Now plot the spectrum!
#spectro.plotSpectrum()


# Power off the laser
#laser.powerOff()