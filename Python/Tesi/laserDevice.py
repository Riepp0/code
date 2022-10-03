from abc import ABC, abstractmethod
from generalDevice import GeneralDevice
from serial import *

class LaserDevice(GeneralDevice,ABC):
    """ Creating a laser class """

    # Overriding abstractmethod
    def __init__(self):
        '''
        Il costruttore crea un oggetto LaserDevice
        
        @param self: l'oggetto LaserDevice
        '''
        self.laser = Serial("/dev/ttyUSB2", timeout=3)

    @abstractmethod
    def powerOff(self):
        '''
        Il metodo spegne il laser
        
        @param self: l'oggetto LaserDevice'''
        pass
    
    @abstractmethod
    def powerOn(self):
        '''
        Il metodo accende il laser
        
        @param self: l'oggetto LaserDevice
        '''
        pass

    @abstractmethod
    def setTemp(self,temp):
        '''
        Il metodo imposta la temperatura del laser      

        @param self: l'oggetto LaserDevice
        @param temp: temperatura in gradi centigradi
        '''
        pass

    @abstractmethod
    def getTemp(self):
        '''
        Il metodo restituisce la temperatura del laser
        
        @param self: l'oggetto LaserDevice
        '''
        pass

    @abstractmethod
    def getCurrent(self):
        '''
        Il metodo restituisce la corrente del laser
        
        @param self: l'oggetto LaserDevice'''
        pass

    @abstractmethod
    def getFloatTemp(self):
        '''
        Il metodo restituisce la temperatura del laser in gradi centigradi
        
        @param self: l'oggetto LaserDevice'''
        pass

    def readLine(self):
        '''
        Il metodo restituisce la stringa letta dal laser
        
        @param self: l'oggetto LaserDevice
        '''
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

    


