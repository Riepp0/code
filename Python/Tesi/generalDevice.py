from abc import ABC, abstractmethod


class GeneralDevice(ABC):
    """ Creating a general device class"""

    @abstractmethod
    def __init__(self):
        pass

    @abstractmethod
    def powerOff(self):
        pass


