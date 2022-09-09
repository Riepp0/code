from abc import ABC, abstractmethod


class GeneralDevice(ABC):
    """ Creating a general device class"""

    @abstractmethod
    def __init__(self):
        pass
    '''
    @abstractmethod
    Costruttore della classe astratta
    '''

    @abstractmethod
    def powerOff(self):
        pass
    '''
    @abstractmethod
    Metodo astratto che viene ereditato da tutti i device sottostanti alla classe padre,
    questi avranno un metodo ereditato che gli permette di eseguire uno shutdown del dispositivo
    '''


