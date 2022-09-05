from abc import ABC, abstractmethod
from generalDevice import GeneralDevice
from serial import *

class LaserDevice(GeneralDevice,ABC):
    """ Creating a laser class """

    # Overriding abstractmethod
    def __init__(self):
        """ Initialize laser """

        self.laser = Serial("/dev/ttyUSB2", timeout=3)

    @abstractmethod
    def powerOff(self):
        pass
    
    @abstractmethod
    def powerOn(self):
        pass

    @abstractmethod
    def setTemp(self,temp):
        pass

    @abstractmethod
    def getTemp(self):
        pass

    @abstractmethod
    def getCurrent(self):
        pass

    @abstractmethod
    def getFloatTemp(self):
        pass

    def readLine(self):
        pass


"""
    def getPower(self):

        self.laser.write("rlp?\r\n".encode())
        return self.readLine()
    
    def getSystemFirmware(self):

        self.laser.write("rsv?\r\n".encode())
        return self.readLine()

    def getSerialNumber(self):

        self.laser.write("rsn?\r\n".encode())
        return self.readLine()
"""    

    


