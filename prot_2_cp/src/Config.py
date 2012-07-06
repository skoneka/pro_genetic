from math import sqrt
class Config(object):
    __slots__ = ["entries", "control"]

    SCREEN_WIDTH = 800
    SCREEN_HEIGHT = 800
    SCREEN_WIDTH_2 = SCREEN_WIDTH / 2
    SCREEN_HEIGHT_2 = SCREEN_HEIGHT / 2

    def __init__(self):
        self.entries = {}
        self.control = {}
        self.set('Ipopulation', 10)
        self.set('Igenome-bits', 32)

        self.set('Imin-seat-separator', 1)
        self.set('Imax-seat-separator', 15)
        self.set('Imin-rows', 1)
        self.set('Imax-rows', 10)
        self.set('Imin-rows-spacing', 32)
        self.set('Imax-rows-spacing', 100)
        self.set('Imin-seats', 0)
        self.set('Imax-seats', 200)
        self.set('Imax-bus-w', 400)
        self.set('Imax-bus-h', 100)
        self.set('Imin-bus-w', 10)
        self.set('Imin-bus-h', 10)

        self.set('Fmax-cost', 100000.0)
        self.set('Fmin-space', 1.0)
        self.set('Fmin-comfort', 1.0)

        self.set('Fcost-seat', 4.0)
        self.set('Fcost-space', 1.0)

        self.set('Imax-generations', 100)

        self.set('Fseat-size', 15.0)
        self.set('Fseat-radius', (sqrt((self.get('Fseat-size') / 2 )**2 * 2)))#sqrt(7.5**2 * 2))
        self.set('Fseat-offset', 3.0)

        self.set('Icircles', 100)
        self.set('Fcircle-radius', 10.5)
        self.set('Ipacking-iter', 20)

        self.set('Ffactor-capacity', 10.0)
        self.set('Ffactor-cost', 0.001)
        self.set('Ffactor-comfort', 20.0)

        self.set('Froulette', 0.5)
        self.set('Felitism', 0.01)
        self.set('Fmutation-rate', 0.01)

        self.set('Imax-adaptation', 0)

        self.setControl('running', True)
        self.setControl('showing specimen', 0)

    def set(self,name,value):
        self.entries[name] = value

    def get(self,name):
        return self.entries[name]

    def setControl(self,name,value):
        self.control[name] = value

    def getControl(self,name):
        return self.control[name]

config = Config()

