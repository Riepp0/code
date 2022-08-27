from spectrometer import *
from laserLib import *
import pandas as pd
import numpy as np

# Create laser object
laser = LaserBox()

# Power on the laser
# laser.powerOn()

# Get laser states
print(laser.getCurrent())
print(laser.getTemp())

# Create spectrometer object
spectro = Spectro()

# Get active spectrometer
spectro.printDevices()

# Set laser states
# 10-60 decimal
# 0 to 90mA
mincurrent = 40
mintemp = 20
maxcurrent = 61
maxtemp = 26
for current in range(mincurrent,maxcurrent):
    for temp in np.arange(mintemp,maxtemp,0.1):
        laser.setCurrent(current,mincurrent,maxcurrent)
        laser.setTemp(temp,mintemp,maxtemp)
        while(((laser.getFloatCurrent() < current-2) or (laser.getFloatCurrent() > current+2)) or (laser.getFloatTemp() < temp-0.05) or (laser.getFloatTemp() > temp+0.05)):
            if (spectro.isSaturated() == True):
                laser.powerOff()
        else:
            if(spectro.isSaturated() == True):
                laser.powerOff()
            waveTmp = spectro.getWaveLength()
            inteTmp = spectro.getIntensities()
            laserCurrent = laser.getCurrent()
            laserTemp = laser.getTemp()
            #currentArray = [laserCurrent] * len(waveTmp)
            #tempArray = [laserTemp] * len(waveTmp)
            #df = pd.DataFrame(list(zip(waveTmp, inteTmp, currentArray, tempArray)),columns=['WaveLength', 'Intensity', 'Current', 'Temperature'])
            #df.to_csv('Python\Tesi\CSV\laserSpectroScript.csv',index=False, mode='a')
            np.savez('Python\Tesi\CSV\LaserScript\_'+str(laserCurrent)+'--'+str(laserTemp), x=waveTmp, y=inteTmp, current=laserCurrent, temp=laserTemp)

spectro.terminate()
            



# !Now plot the spectrum!
#spectro.plotSpectrum()


# Power off the laser
#laser.powerOff()