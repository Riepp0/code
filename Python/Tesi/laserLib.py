from serial import *

class LaserBox:
    """ Creating a laser class """


    def __init__(self):
        """ Initialize laser """

        self.laser = Serial("COM5", timeout=3) 
    
    def powerOn(self):
        """ Power on laser """
        
        self.laser.write("lon\r\n".encode())
        return self.readLine()

    def powerOff(self):
        """ Power off laser """

        self.laser.write("loff\r\n".encode())
        return self.readLine()

    def setCurrent(self,current):
        """ Set current through serial command 
            @param current: float """
        print(isinstance(current, float) or isinstance(current, int))
        if isinstance(current, float) or isinstance(current, int):
            current = '{0:.4f}'.format(current)
        else:
            try:
                current = float(current)
                if current < 40 or current > 50:
                    raise ValueError
            except ValueError:
                print("Insert a valid number!")
        tmp = "slc:"+current+"\r\n"
        self.laser.write(tmp.encode())
        return self.readLine()

    def setTemp(self,temp):
        """ Set temperature through serial command 
            @param temp: float """

        print(isinstance(temp, float) or isinstance(temp, int))
        if isinstance(temp, float) or isinstance(temp, int):
            temp = '{0:.4f}'.format(temp)
        else:
            try:
                temp = float(temp)
                if temp < 20 or temp > 25:
                    raise ValueError
            except ValueError:
                print("Insert a valid number!")
        tmp = "stt:"+temp+"\r\n"
        self.laser.write(tmp.encode())
        return self.readLine()
    
    def getCurrent(self):
        """ Get current through serial command  """

        self.laser.write("rli?\r\n".encode())
        return self.readLine()

    def getTemp(self):
        """ Get temperature through serial command """

        self.laser.write("rtt?\r\n".encode())
        return self.readLine()

    def getFloatCurrent(self):
        """ Get current in floating point through serial command  """

        self.laser.write("rli?\r\n".encode())
        return float(self.readLine())

    def getFloatTemp(self):
        """ Get temperature in floating point through serial command  """

        self.laser.write("rtt?\r\n".encode())
        return float(self.readLine())


######################################################
    def getPower(self):
        """ Get power status """

        self.laser.write("rlp?\r\n".encode())
        return self.readLine()
    
    def getSystemFirmware(self):
        """ Get system firmware """

        self.laser.write("rsv?\r\n".encode())
        return self.readLine()

    def getSerialNumber(self):
        """ Get serial number """

        self.laser.write("rsn?\r\n".encode())
        return self.readLine()
    
    def readLine(self):
        """ Read line from serial port """

        return self.laser.readline().decode("utf-8")
    

    


