import easy_scpi as scpi

from laserDevice import Laser

class OBISBox(Laser):
    """ Creating a laser class """


    def __init__(self):
        """ Initialize laser """

        self.laser = scpi.Instrument("COM5", timeout=3000,handshake='OK',write_termination='\r\n',read_termination='\r\n')
        self.laser.connect()
    
    # Overriding abstractmethod
    def printDevices(self):
        """ Print all devices """

        return self.laser.query("*IDN?")
    
    def checkHandshake(self):
        """ Check handshake (default ON)"""

        return self.laser.query("SYST:COMM:HAND?")

    # Overriding abstractmethod
    def powerOn(self):
        """ Power on laser """

        return self.laser.source.am.state('on')

    # Overriding abstractmethod
    def powerOff(self):
        """ Power off laser """

        return self.laser.source.am.state('off')

    def getWavelength(self):
        """ Get wavelength of the laser"""

        return self.laser.query("SYST:INF:WAV?")

    def setPower(self,power):
        """Set power"""
        if power < float(self.laser.source.power.limit.low()):
            raise ValueError("Power too low")
        elif power > float(self.laser.source.power.limit.high()):
            raise ValueError("Power too high")

        return self.laser.source.power.level.immediate.amplitude(power)#("SOUR:POW:LEV:IMM:AMPL: "+ str(power))
    
    def getPower(self):
        """ Get power status """

        return self.laser.query("SOUR:POW:LEV?")

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
        tmp = "SOUR:POW:CURR: "+current
        return self.laser.query(tmp)

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
        tmp = "SOUR:TEMP:DSET: "+temp
        return self.laser.query(tmp)
    
    # Overriding abstractmethod
    def getCurrent(self):
        """ Get current through serial command  """

        return self.laser.query("SOUR:POW:CURR?")

    # Overriding abstractmethod
    def getTemp(self):
        """ Get temperature through serial command (default in C°)"""

        return self.laser.query("SOUR:TEMP:DIOD?")

    # Overriding abstractmethod
    def getFloatCurrent(self):
        """ Get current in floating point through serial command  """

        return self.laser.query("SOUR:POW:CURR?")

    # Overriding abstractmethod
    def getFloatTemp(self):
        """ Get temperature in floating point through serial command  (default in C°)"""

        return self.laser.query("SOUR:TEMP:BAS?")
    
    def getState(self):
        """ Power on laser """

        return self.laser.query("SOUR:AM:STAT?")

    

    


