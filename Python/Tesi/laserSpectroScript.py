from Spectrometer import *
from LaserBox import *
import numpy as np

# Create laser object
laser = LaserBox()

# Power on the laser
# laser.powerOn()

# Get laser states
print(laser.getCurrent())
print(laser.getTemp())

# Create spectrometer object
spectro = Spectrometer()

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
                break
            time.sleep(1)
        else:
            if(spectro.isSaturated() == True):
                laser.powerOff()
            waveTmp = spectro.getWaveLength()
            index_low = np.searchsorted(waveTmp, 390)
            index_high = np.searchsorted(waveTmp, 420)
            inteTmp = spectro.getIntensities()
            waveTmp = waveTmp[index_low:index_high]
            inteTmp = inteTmp[index_low:index_high]
            laserCurrent = laser.getCurrent()
            laserTemp = laser.getTemp()
            np.savez('Python\Tesi\CSV\LaserScript\_'+str(laserCurrent)+'--'+str(laserTemp), x=waveTmp, y=inteTmp, current=laserCurrent, temp=laserTemp)

spectro.powerOff()
            



# !Now plot the spectrum!
#spectro.plotSpectrum()


# Power off the laser
#laser.powerOff()