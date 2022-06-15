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
mincurrent = 40
mintemp = 20
maxcurrent = 60
maxtemp = 25
for current in range(mincurrent,maxcurrent):
    for temp in range (mintemp,maxtemp):
        laser.setCurrent(current)
        laser.setTemp(temp)
        if spectro.isSaturated():
            print("Saturated")
        else:
            waveTmp = spectro.getWaveLength()
            inteTmp = spectro.getIntensities()
            total = (maxcurrent-mincurrent)*(maxtemp-mintemp)
            df = []
            for i in range(1, total + 1):
                df[i] = pd.DataFrame(waveTmp, inteTmp, columns=['Wavelength', 'Intensity'])
            for i in range(1, len(df) + 1):
                alldata = alldata + df[i]
            print(alldata)
            alldata.to_csv('laserSpectroScript.csv', index=False) 
            



# !Now plot the spectrum!
#spectro.plotSpectrum()


# Power off the laser
#laser.powerOff()