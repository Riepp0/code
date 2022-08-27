import time
from spectrometer import *
from OBISLib import *
import pandas as pd
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
spectro = Spectro()

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
        while(((laser.getFloatPower() < power-0.001) or (laser.getFloatPower() > power+0.001)) or (laser.getFloatTemp() < temp-0.05) or (laser.getFloatTemp() > temp+0.05)):
            if(spectro.isSaturated() == True):
                laser.powerOff()
            time.sleep(1)
        else:
            if(spectro.isSaturated() == True):
                laser.powerOff()
            waveTmp = spectro.getWaveLength()
            inteTmp = spectro.getIntensities()
            laserPower = laser.getPower()
            laserTemp = laser.getTemp()
            #currentArray = [laserCurrent] * len(waveTmp)
            #tempArray = [laserTemp] * len(waveTmp)
            #df = pd.DataFrame(list(zip(waveTmp, inteTmp, currentArray, tempArray)),columns=['WaveLength', 'Intensity', 'Current', 'Temperature'])
            #df.to_csv('Python\Tesi\CSV\OBISSpectroScript.csv',index=False, mode='a')
            np.savez('Python\Tesi\CSV\OBISScript\_'+str(laserPower)+'--'+str(laserTemp), x=waveTmp, y=inteTmp, power=laserPower, temp=laserTemp)
spectro.terminate()
            



# !Now plot the spectrum!
#spectro.plotSpectrum()


# Power off the laser
laser.powerOff()