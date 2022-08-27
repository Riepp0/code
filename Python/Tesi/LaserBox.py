from serial import *

from LaserDevice import LaserDevice

class LaserBox(LaserDevice):
    """ Creating a laser class """


    def __init__(self):
        """ Initialize laser """
        super().__init__()
    
    # Overriding abstractmethod
    def powerOn(self):
        """ Power on laser """
        
        self.laser.write("lon\r\n".encode())
        return self.readLine()

    # Overriding abstractmethod
    def powerOff(self):
        """ Power off laser """

        self.laser.write("loff\r\n".encode())
        return self.readLine()
    
    # Overriding abstractmethod
    def setCurrent(self,current,minCurrent,maxCurrent):
        """ Set current through serial command 
            @param current: float """
        if isinstance(current, float) or isinstance(current, int):
            current = '{0:.4f}'.format(current)
        else:
            try:
                current = float(current)
                if current < minCurrent or current > maxCurrent:
                    raise ValueError
            except ValueError:
                print("Insert a valid number!")
        tmp = "slc:"+current+"\r\n"
        self.laser.write(tmp.encode())
        return self.readLine()

    # Overriding abstractmethod
    def setTemp(self,temp,minTemp,maxTemp):
        """ Set temperature through serial command 
            @param temp: float """
        if isinstance(temp, float) or isinstance(temp, int):
            temp = '{0:.4f}'.format(temp)
        else:
            try:
                temp = float(temp)
                if temp < minTemp or temp > maxTemp:
                    raise ValueError
            except ValueError:
                print("Insert a valid number!")
        tmp = "stt:"+temp+"\r\n"
        self.laser.write(tmp.encode())
        return self.readLine()
    
    # Overriding abstractmethod
    def getCurrent(self):
        """ Get current through serial command  """

        self.laser.write("rli?\r\n".encode())
        return self.readLine()
    
    # Overriding abstractmethod
    def getTemp(self):
        """ Get temperature through serial command """

        self.laser.write("rtt?\r\n".encode())
        return self.readLine()

    # Overriding abstractmethod
    def getFloatCurrent(self):
        """ Get current in floating point through serial command  """

        self.laser.write("rli?\r\n".encode())
        return float(self.readLine())

    # Overriding abstractmethod
    def getFloatTemp(self):
        """ Get temperature in floating point through serial command  """

        self.laser.write("rtt?\r\n".encode())
        return float(self.readLine())

    def readLine(self):
        """ Read line from serial port """

        super().readLine()
"""
    def setPower(self,power):
        #Set power

        if isinstance(power, float) or isinstance(power, int):
            power = '{0:.4f}'.format(power)
        else:
            try:
                power = float(power)
                if power < 0 or power > 1:
                    raise ValueError
            except ValueError:
                print("Insert a valid number!")
        tmp = "slp:"+power+"\r\n"
        self.laser.write(tmp.encode())
        return self.readLine()

    def getPower(self):
        # Get power status 

        self.laser.write("rlp?\r\n".encode())
        return self.readLine()
    
    def getSystemFirmware(self):
        # Get system firmware 

        self.laser.write("rsv?\r\n".encode())
        return self.readLine()

    def getSerialNumber(self):
        # Get serial number 

        self.laser.write("rsn?\r\n".encode())
        return self.readLine()
"""

    


