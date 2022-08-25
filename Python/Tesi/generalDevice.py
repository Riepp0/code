from abc import ABC, abstractmethod


class Device(ABC):
    """ Creating a general device class"""

    @abstractmethod
    def __init__(self):
        pass

    @abstractmethod
    def powerOff(self):
        pass


