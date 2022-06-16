import wave
from spectroLib import *
from laserLib import *
import pandas as pd
import time

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


# Set laser states
# 10-60 decimal
# 0 to 90mA
mincurrent = 40
mintemp = 20
maxcurrent = 60
maxtemp = 25
for current in range(mincurrent,maxcurrent):
    for temp in range (mintemp,maxtemp):
        laser.setCurrent(current)
        laser.setTemp(temp)
        while(laser.getCurrent() in range(current-0.1,current+0.1) and (laser.getTemp() in range(temp-0.01,temp+0.01))):
            if spectro.isSaturated():
                print("Saturated")
            else:
                waveTmp = spectro.getWaveLength()
                inteTmp = spectro.getIntensities()
                df = pd.DataFrame(columns=['WaveLength', 'Intensity', 'Current', 'Temperature'])
                df.to_csv('Python\Tesi\CSV\laserSpectroScriptTest.csv',mode='a', index=False)
                for i in range (0, len(waveTmp)):
                    df = pd.DataFrame([[waveTmp[i], inteTmp[i], current, temp]])
                    df.to_csv('Python\Tesi\CSV\laserSpectroScript.csv',mode='a', index=False, header=False)
            



# !Now plot the spectrum!
#spectro.plotSpectrum()


# Power off the laser
#laser.powerOff()