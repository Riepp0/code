import easy_scpi as scpi

from laserDevice import LaserDevice

class OBISBox(LaserDevice):
    """ Creating a laser class """


    def __init__(self):
        '''
        Il costruttore crea un oggetto LaserBox dal primo laser rilevato connesso e disponibile per l'utilizzo
        
        @param self: l'oggetto LaserBox
        '''
        self.laser = scpi.Instrument("/dev/ttyACM0", timeout=3000,handshake='OK',write_termination='\r\n',read_termination='\r\n')
        self.laser.connect()
    
    # Overriding abstractmethod
    def printDevices(self):
        '''
        Il metodo stampa i dispositivi connessi
        
        @param self: l'oggetto LaserBox
        
        @return: stringa di conferma
        '''
        return self.laser.query("*IDN?")
    
    def checkHandshake(self):
        '''
        Il metodo controlla lo stato dell'handshake
        
        @param self: l'oggetto LaserBox
        
        @return: stringa di conferma
        '''
        return self.laser.query("SYST:COMM:HAND?")

    # Overriding abstractmethod
    def powerOn(self):
        '''
        Il metodo accende il laser
        
        @param self: l'oggetto LaserBox
        
        @return: stringa di conferma
        '''
        return self.laser.source.am.state('on')

    # Overriding abstractmethod
    def powerOff(self):
        '''
        Il metodo spegne il laser
        
        @param self: l'oggetto LaserBox
        
        @return: stringa di conferma
        '''
        return self.laser.source.am.state('off')

    def getWavelength(self):
        '''
        Il metodo restituisce la lunghezza d'onda del laser
        
        @param self: l'oggetto LaserBox
        
        @return: stringa di conferma
        '''
        return self.laser.query("SYST:INF:WAV?")

    def setPower(self,power,minPower,maxPower):
        '''
        Il metodo imposta la potenza del laser
        
        @param self: l'oggetto LaserBox
        @param power: potenza da impostare
        @param minPower: potenza minima
        @param maxPower: potenza massima
        
        @return: stringa di conferma
        '''
        if power < minPower or power > maxPower:
            raise ValueError("Wrong power value")

        return self.laser.source.power.level.immediate.amplitude(power)
    
    def getPower(self):
       '''
       Il metodo restituisce la potenza del laser
       
       @param self: l'oggetto OBISBox
       
       @return: stringa di conferma
       '''
       return self.laser.query("SOUR:POW:LEV?")

    # Overriding abstractmethod
    def setTemp(self,temp,minTemp,maxTemp):
        '''
        Il metodo imposta la temperatura del laser
        
        @param self: l'oggetto OBISBox
        @param temp: temperatura da impostare
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
        return self.laser.source.temperature.dsetpoint(temp)
    
    # Overriding abstractmethod
    def getCurrent(self):
        '''
        Il metodo restituisce la corrente del laser
        
        @param self: l'oggetto OBISBox
        
        @return: corrente del laser
        '''
        return self.laser.query("SOUR:POW:CURR?")

    # Overriding abstractmethod
    def getTemp(self):
        '''
        Il metodo restituisce la temperatura del laser
        
        @param self: l'oggetto OBISBox
        
        @return: temperatura del laser
        '''
        return self.laser.query("SOUR:TEMP:DIOD?")

    # Overriding abstractmethod
    def getFloatCurrent(self):
        '''
        Il metodo restituisce la corrente del laser in float
        
        @param self: l'oggetto OBISBox
        
        @return: corrente del laser in floating point
        '''
        return self.laser.query("SOUR:POW:CURR?")

    # Overriding abstractmethod
    def getFloatTemp(self):
        '''
        Il metodo restituisce la temperatura del laser in float
        
        @param self: l'oggetto OBISBox
        
        @return: temperatura del laser in floating point
        '''
        strTemp = self.laser.query("SOUR:TEMP:DIOD?")
        strTemp = strTemp[:-1]
        return float(strTemp)
    
    def getState(self):
        '''
        Il metodo restituisce lo stato del laser
        
        @param self: l'oggetto OBISBox
        
        @return: stringa di conferma dello stato del laser
        '''
        return self.laser.query("SOUR:AM:STAT?")
    
    def getFloatPower(self):
        '''
        Il metodo restituisce la potenza del laser in float
        
        @param self: l'oggetto OBIXBox
        
        @return: potenza del laser in floating point
        '''
        return float(self.laser.query("SOUR:POW:LEV?"))

    # Overriding abstractmethod
    """def setCurrent(self,current,minCurrent,maxCurrent):
         Set current through serial command 
            @param current: float
        if isinstance(current, float) or isinstance(current, int):
            current = '{0:.4f}'.format(current)
        else:
            try:
                current = float(current)
                if current < minCurrent or current > maxCurrent:
                    raise ValueError
            except ValueError:
                print("Insert a valid number!")
        #tmp = "SOUR:POW:CURR: "+current
        #return self.laser.source.power.current(current)"""

    

    


