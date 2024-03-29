from datetime import datetime
import time
from spectrometer import *
from OBISBox import *
import numpy as np

# Create laser object
laser = OBISBox()

# Power on the laser
laser.powerOn()
laser.checkHandshake()
time.sleep(3)

# Get laser states
print(laser.getPower()) # 0 in accensione
print(laser.getTemp())

# Create spectrometer object
spectro = Spectrometer()

spectro.setIntegrationTimeScript(20000)

# Get active spectrometer
spectro.printDevices()

# Set laser states
# 10-45° C baseplate range 
# 5 to 38 mW
minPower = 0.005
mintemp = 20
maxPower = 0.038
maxtemp = 30
laser.setPower(minPower,minPower,maxPower)
laser.setTemp(mintemp,mintemp,maxtemp)
time.sleep(5)
for power in np.arange(minPower,maxPower,0.001):
    for temp in np.arange(mintemp,maxtemp,0.1):
        print("Now setting power", power, "and temperature", temp)
        laser.setPower(power,minPower,maxPower)
        laser.setTemp(temp,mintemp,maxtemp)
        counter = 0
        while(((laser.getFloatPower() < power-0.0004) or (laser.getFloatPower() > power+0.0004)) or (laser.getFloatTemp() < temp-0.05) or (laser.getFloatTemp() > temp+0.05)):
            if (laser.getState() == 'OFF'):
                laser.powerOn()
            if(spectro.isSaturated() == True):
                laser.powerOff()
                print("The spectrometer has been saturated and the laser has been turned off")
                break
            time.sleep(1)
            laser.setTemp(temp,mintemp,maxtemp)
            counter += 1
            if counter == 50:
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
            laserPower = laser.getPower()
            laserTemp = laser.getTemp()
            date_time = datetime.now().strftime('%Y-%m-%d %H:%M:%S')
            np.savez('data//datiOBIS_'+str(laserPower)+'mW-'+str(laserTemp) + date_time, x=waveTmp, y=inteTmp, power=laserPower, temp=laserTemp)
spectro.powerOff()
            



# !Now plot the spectrum!
#spectro.plotSpectrum()


# Power off the laser
laser.powerOff()