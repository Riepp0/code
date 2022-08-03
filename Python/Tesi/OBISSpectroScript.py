import wave
from spectroLib import *
from OBISLib import *
import pandas as pd
import numpy as np

# Create laser object
laser = OBISBox()

# Power on the laser
# laser.powerOn()

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
maxcurrent = 61
maxtemp = 26
for current in range(mincurrent,maxcurrent):
    for temp in np.arange(mintemp,maxtemp,0.1):
        laser.setCurrent(current)
        laser.setTemp(temp)
        while(((laser.getFloatCurrent() < current-2) or (laser.getFloatCurrent() > current+2)) or (laser.getFloatTemp() < temp-0.05) or (laser.getFloatTemp() > temp+0.05)):
            time.sleep(1)
        if spectro.isSaturated():
            print("Saturated")
        else:
            waveTmp = spectro.getWaveLength()
            inteTmp = spectro.getIntensities()
            laserCurrent = laser.getCurrent()
            laserTemp = laser.getTemp()
            currentArray = [laserCurrent] * len(waveTmp)
            tempArray = [laserTemp] * len(waveTmp)
            df = pd.DataFrame(list(zip(waveTmp, inteTmp, currentArray, tempArray)),columns=['WaveLength', 'Intensity', 'Current', 'Temperature'])
            df.to_csv('Python\Tesi\CSV\laserSpectroScript.csv',index=False, mode='a')
spectro.terminate()
            



# !Now plot the spectrum!
#spectro.plotSpectrum()


# Power off the laser
#laser.powerOff()