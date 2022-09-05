from datetime import datetime
from spectrometer import *
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

spectro.setIntegrationTimeScript(10000)

# Get active spectrometer
spectro.printDevices()

# Set laser states
# 10-60 decimal
# 0 to 90mA
mincurrent = 40
mintemp = 20
maxcurrent = 60
maxtemp = 30
for current in range(mincurrent,maxcurrent):
    for temp in np.arange(mintemp,maxtemp,0.1):
        print("Now setting current", current, "and temperature", temp)
        laser.setCurrent(current,mincurrent,maxcurrent)
        laser.setTemp(temp,mintemp,maxtemp)
        counter = 0
        while(((laser.getFloatCurrent() < current +1.2 -0.5) or (laser.getFloatCurrent() > current+1.2+0.5)) or (laser.getFloatTemp() < temp-0.03) or (laser.getFloatTemp() > temp+0.03)):
            if (spectro.isSaturated() == True):
                mincurrent = 20
                laser.setCurrent(20,mincurrent,maxcurrent)
                laser.powerOff()
                print("The spectrometer has been saturated and the laser current has been lowered")
                break
            time.sleep(1)
            laser.setTemp(temp,mintemp,maxtemp)
            counter += 1
            if counter == 20:
                print("I could not set these options")
                break
        else:
            if(spectro.isSaturated() == True):
                laser.powerOff()
            waveTmp = spectro.getWaveLength()
            index_low = np.searchsorted(waveTmp, 390)
            index_high = np.searchsorted(waveTmp, 420)
            inteTmp = spectro.getIntensities()
            waveTmp = waveTmp[index_low:index_high]
            inteTmp = inteTmp[index_low:index_high]
            laserCurrent = laser.getCurrent()[:-2]
            laserTemp = laser.getTemp()[:-2]
            date_time = datetime.now().strftime('%Y-%m-%d_%H-%M-%S')
            np.savez('data//dati_'+str(laserCurrent)+'mA-'+str(laserTemp)+'C_'+date_time, x=waveTmp, y=inteTmp, current=laserCurrent, temp=laserTemp)

spectro.powerOff()
            



# !Now plot the spectrum!
#spectro.plotSpectrum()


# Power off the laser
mincurrent = 20
laser.setCurrent(20,mincurrent,maxcurrent)
laser.powerOff()