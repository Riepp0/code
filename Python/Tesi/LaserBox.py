from serial import *

from laserDevice import LaserDevice

class LaserBox(LaserDevice):
    """ Creating a laser class """


    def __init__(self):
        '''
        Il costruttore crea un oggetto LaserBox dal primo laser rilevato connesso e disponibile per l'utilizzo
        
        @param self: l'oggetto LaserBox
        '''
        super().__init__()
    
    # Overriding abstractmethod
    def powerOn(self):
        '''
        Il metodo accende il laser
        
        @param self: l'oggetto LaserBox
        '''
        self.laser.write("lon\r\n".encode())
        return self.readLine()

    # Overriding abstractmethod
    def powerOff(self):
        '''
        Il metodo spegne il laser
        
        @param self: l'oggetto LaserBox
        '''
        self.laser.write("loff\r\n".encode())
        return self.readLine()
    
    # Overriding abstractmethod
    def setCurrent(self,current,minCurrent,maxCurrent):
        '''
        Il metodo imposta la corrente del laser

        @param self: l'oggetto LaserBox
        @param current: corrente in mA
        @param minCurrent: corrente minima
        @param maxCurrent: corrente massima

        @return: stringa di conferma
        '''

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
        '''
        Il metodo imposta la temperatura del laser
        
        @param self: l'oggetto LaserBox
        @param temp: temperatura in gradi centigradi
        @param minTemp: temperatura minima
        @param maxTemp: temperatura massima

        @return: stringa di conferma
        '''
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
        ''''
        Il metodo restituisce la corrente del laser
        
        @param self: l'oggetto LaserBox
        
        @return: corrente in mA
        '''
        self.laser.write("rli?\r\n".encode())
        return self.readLine()
    
    # Overriding abstractmethod
    def getTemp(self):
        '''
        Il metodo restituisce la temperatura del laser
        
        @param self: l'oggetto LaserBox
        
        @return: temperatura in gradi centigradi
        '''
        self.laser.write("rtt?\r\n".encode())
        return self.readLine()

    # Overriding abstractmethod
    def getFloatCurrent(self):
        '''
        Il metodo restituisce la corrente del laser
        
        @param self: l'oggetto LaserBox
        
        @return: corrente in mA
        '''
        self.laser.write("rli?\r\n".encode())
        return float(self.readLine())

    # Overriding abstractmethod
    def getFloatTemp(self):
        '''
        Il metodo restituisce la temperatura del laser
        
        @param self: l'oggetto LaserBox
        
        @return: temperatura in gradi centigradi
        '''
        self.laser.write("rtt?\r\n".encode())
        return float(self.readLine())

    def readLine(self) -> str:
        '''
        Il metodo legge una riga dal laser
        
        @param self: l'oggetto LaserBox
        
        @return: stringa letta
        '''
        return self.laser.readline().decode("utf-8")
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

    


