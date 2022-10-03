from generalDevice import GeneralDevice
from tkinter import Label

import functools
import seabreeze
seabreeze.use('pyseabreeze')
from seabreeze.spectrometers import list_devices, Spectrometer as Spectro

import pyqtgraph as pg
from PyQt5.QtWidgets import QApplication

class Spectrometer(GeneralDevice):
    """Creating a Spectrometer class"""

    # Overriding abstractmethod
    def __init__(self):
        self.spectrometer = Spectro.from_first_available()
        '''
        Il costruttore crea un oggetto Spectrometer dal primo spettrometro rilevato connesso e disponibile per l'utilizzo
        @param self: l'oggetto Spectrometer
        '''

    # Overriding abstractmethod
    def powerOff(self):
        self.spectrometer.close()
        '''
        Chiude la connessione al dispositivo Seabreeze 
        @param self: l'oggetto Spectrometer
        '''

    def printDevices(self):
        """Print devices"""
        print(list_devices())
        '''
        Stampa la lista dei dispositivi connessi supportati da Ocean Optics

        @param self: l'oggetto Spectrometer
        '''

    def getSpectrometer(self):
        return self.spectrometer
        '''
        Restituisce l'oggetto Spectrometer
        
        @param self: l'oggetto Spectrometer
        '''

    def setIntegrationTime(self, time, label : Label):
        '''
        Il metodo imposta un tempo di integrazione dello spettrometro secondo il quale il dispositivo misura il fascio luminoso.
        
        @param self: l'oggetto Spectrometer
        @param time: tempo di integrazione in microsecondi
        @param label: l'oggetto Label che visualizza il tempo di integrazione
        '''
        if (not(isinstance(time, int))):
            try:
                time = int(time)
                if not(isinstance(time, int)):
                    raise ValueError
            except ValueError:
                label.config(text="Insert a valid number!")
                return
        if time < 1000 or time > 20000:
            label.config(text="Insert a valid value!")
        else:
            self.spectrometer.integration_time_micros(time)
            label.config(text="Time set!")


    def setIntegrationTimeScript(self,time):
        '''
        Il metodo imposta un tempo di integrazione dello spettrometro secondo il quale il dispositivo misura il fascio luminoso.
        
        @param self: l'oggetto Spectrometer
        @param time: tempo di integrazione in microsecondi

        @return: tempo di integrazione impostato in microsecondi
        '''
        if(time < 3800 or time > 10000000):
            raise ValueError("Time must be between 3800 and 100000 microseconds")
        return self.spectrometer.integration_time_micros(time)        

    def getWaveLength(self):
        '''
        Il metodo restituisce la lunghezza dell'onda in nanometri
        
        @param self: l'oggetto Spectrometer
        
        @return: la lunghezza dell'onda in nanometri'''
        return self.spectrometer.wavelengths()

    def getIntensities(self):
        '''
        Il metodo restituisce l'intensità del fascio luminoso misurato dallo spettrometro
        
        @param self: l'oggetto Spectrometer
        
        @return: l'intensità del fascio luminoso misurato dallo spettrometro'''
        return self.spectrometer.intensities()

    def getSpectrum(self):
        '''
        Il metodo restituisce l'intensità del fascio luminoso misurato dallo spettrometro e la lunghezza dell'onda in nanometri
        
        @param self: l'oggetto Spectrometer
        
        @return: l'intensità del fascio luminoso misurato dallo spettrometro e la lunghezza dell'onda in nanometri'''
        return self.spectrometer.spectrum()

    def isSaturated(self):
        '''
        Il metodo restituisce True se lo spettrometro è saturato, False altrimenti
        
        @param self: l'oggetto Spectrometer
        
        @return: True se lo spettrometro è saturato, False altrimenti'''
        return max(self.getIntensities()) > 0.9 * self.spectrometer.max_intensity

    def upgrade(self, params):
        '''
        Il metodo permette di aggiornare il grafico generato dalla funzione plotSpectrum (che la richiama ogni secondo)
        tramite la sovrascrittura dei dati impostati nel disegno.

        @param self: l'oggetto Spectrometer
        '''
        curve = params[0]
        line = params[1]
        x = self.getWaveLength()
        y = self.getIntensities()
        curve.setXRange(400, 420)
        curve.setYRange(min(y), max(y))
        line.setData(x=x, y=y)
        
        
        

    def plotSpectrum(self):
        '''
        Questo metodo permette di creare a schermo una nuova finestra sulla quale viene effettuato il plot dello spettro rilevato dello spettrometro. 
        Sugli assi x e y vengono rappresentati i rispettivi valori di lunghezze d'onda e intensità, questi vengono aggiornati tramite la chiamata 
        periodica della funzione upgrade(self,params), in cui i parametri passati sono il grafico e la linea che rappresenta le intensità
        dello spettro.

        @param self: l'oggetto Spectrometer
        '''
        app = QApplication([])

        curve = pg.plot()
        curve.showGrid(x = True, y = True)

        x = self.getWaveLength()
        y = self.getIntensities()
        curve.setXRange(min(x), max(x))
        curve.setYRange(min(y), max(y))
        line = curve.plot(x,y, pen ='g', symbol ='x', symbolPen ='g',
                           symbolBrush = 0.2, name ='green')

        callBack = functools.partial(self.upgrade, params = [curve,line])

        timer = pg.QtCore.QTimer()
        timer.timeout.connect(callBack)
        timer.start(1000)
        
        app.exec()

        


    
