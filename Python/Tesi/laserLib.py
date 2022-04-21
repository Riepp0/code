from socket import timeout
from serial import *

class LaserBox:
    """ Creating a laser class """

    global laser


    def __init__() -> None:
        """ Initialize laser """
        laser = Serial("COM5", timeout=3)  
    
    def powerOn():
        laser.write("lon\r\n".encode())

    def powerOff():
        laser.write("loff\r\n".encode())

