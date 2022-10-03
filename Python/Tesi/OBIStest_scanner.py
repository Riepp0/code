import os
import numpy as np
import time
#"""
for filename in os.listdir(os.getcwd()+'\Python\Tesi\CSV\datiOBIS'):

    data = np.load(os.getcwd()+'\Python\Tesi\CSV\datiOBIS\\'+filename)
    x = data['x'] # Wavelength
    y = data['y'] # Intensity
    power = data['power'] # Power
    temp = data['temp'] # Temperature
    max_intensity = np.amax(y) # Max intensity
    max_index = np.argmax(y) # Index of max intensity
    max_wavelength = x[max_index] # Wavelength of max intensity
    counter = 0 # Counter for the number of points
    sumIntensity = 0 # Sum of the intensity
    for j in range(10):
        sumIntensity += y[j]
    averageIntensity = sumIntensity/10 # Average intensity
    peakHeight = max_intensity - averageIntensity # Peak height
    for i in range(len(y)):
        if y[i] > 0.2*peakHeight + averageIntensity:
            counter += 1
    np.savez('Python\Tesi\CSV\OBISmax_dati\\'+filename, max_intensity=max_intensity, max_wavelength=max_wavelength, max_index=max_index, counter=counter, power=power, temp=temp)
#"""
"""
# test lettura
for filename in os.listdir(os.getcwd()+ '\Python\Tesi\CSV\OBISmax_dati'):
    data = np.load(os.getcwd()+'\Python\Tesi\CSV\OBISmax_dati\\'+filename)
    print(data['max_intensity'])
    print(data['max_wavelength'])
    print(data['max_index'])
    print(data['counter'])
    #time.sleep(0.5)
    #"""