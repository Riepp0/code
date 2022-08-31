import time
from Spectrometer import *
from OBISBox import *
import numpy as np

# Create laser object
laser = OBISBox()

# Power on the laser
laser.powerOn()
laser.checkHandshake()
time.sleep(3)

# Get laser states
print(laser.getPower())
print(laser.getTemp())

# Create spectrometer object
spectro = Spectrometer()

# Get active spectrometer
spectro.printDevices()

# Set the spectrometer integration time <-----------------------------------------


# Set laser states
# 10-45° C baseplate range 
# 5 to 38 mW
minPower = 0.005
mintemp = 20
maxPower = 0.008
maxtemp = 26
laser.setPower(minPower,minPower,maxPower)
laser.setTemp(mintemp,mintemp,maxtemp)
time.sleep(5)
for power in np.arange(minPower,maxPower,0.001):
    for temp in np.arange(mintemp,maxtemp,0.1):
        laser.setPower(power,minPower,maxPower)
        laser.setTemp(temp,mintemp,maxtemp)
        while(((laser.getFloatPower() < power-0.0005) or (laser.getFloatPower() > power+0.0005)) or (laser.getFloatTemp() < temp-0.05) or (laser.getFloatTemp() > temp+0.05)):
            if(spectro.isSaturated() == True):
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
            laserPower = laser.getPower()
            laserTemp = laser.getTemp()
            np.savez('Python\Tesi\CSV\OBISScript\_'+str(laserPower)+'--'+str(laserTemp), x=waveTmp, y=inteTmp, power=laserPower, temp=laserTemp)
spectro.powerOff()
            



# !Now plot the spectrum!
#spectro.plotSpectrum()


# Power off the laser
laser.powerOff()