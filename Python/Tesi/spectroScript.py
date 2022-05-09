from spectroLib import *

spectro = Spectro()

spectro.printDevices()

spectro.setIntegrationTime(100000)
print(spectro.getSpectrum())
print(spectro.isSaturated())