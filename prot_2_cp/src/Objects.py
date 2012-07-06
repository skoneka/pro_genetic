from Config import config
import Util
import pygame
from random import randrange

class Specimen(object):
    __slots__ = ["color", "rect", "seats", "seats_potential", "rows", "rows_spacing", "seat_separator"]
    def __init__(self, width, height, seats_n, rows, rows_spacing, seat_separator):

        def validate(param, min, max):
            if param < min:
                return min 
            elif param > max:
                return max
            else:
                return param

        width = validate(width, config.get('Imin-bus-w'), config.get('Imax-bus-w'))
        height = validate(height, config.get('Imin-bus-h'), config.get('Imax-bus-h'))
        seats_n = validate(seats_n, config.get('Imin-seats'), config.get('Imax-seats'))
        rows = validate(rows, config.get('Imin-rows'), config.get('Imax-rows'))
        rows_spacing = validate(rows_spacing, config.get('Imin-rows-spacing'), config.get('Imax-rows-spacing'))
        seat_separator = validate(seat_separator, config.get('Imin-seat-separator'), config.get('Imax-seat-separator'))

        self.rows = rows
        self.rows_spacing = rows_spacing
        self.seat_separator = seat_separator
        self.color = pygame.Color('0x0000FF')
        self.rect = pygame.Rect((config.SCREEN_WIDTH_2-width/2 ,config.SCREEN_HEIGHT_2 - height/2 ,width ,height))
        self.seats_potential = seats_n
        self.seats = []
        seat_size = config.get('Fseat-size')
        seat_offset = config.get('Fseat-offset')
        seat_c_tmp = [0] * (self.rows + 1)
        for i in range(0, seats_n):
            y = randrange(0, 1+self.rows)
            if seat_c_tmp[y] % seat_separator:
                x_pos = seat_c_tmp[y] * (seat_size + seat_offset)
                y_pos = 1 + y * rows_spacing
                if x_pos < self.rect.w and y_pos < self.rect.h:
                    s = Seat(self.rect,
                            x_pos,
                            y_pos,
                            pygame.Color('0xAAAAAA'))
                    if self.rect.contains(s) and s.rect.collidelist(self.seats) == -1:
                        self.seats.append(s)
            seat_c_tmp[y] +=1

    def __str__(self):
        return "Specimen seats: %d" % (len(self.seats))

    def update2(self,drawsurf, circles ):
        seats = self.seats
        vc = 0
        if drawsurf != None:
            pygame.draw.rect(drawsurf, self.color, self.rect, 0)
        if circles:
            for circle in circles:
                if self.rect.contains(circle.rect):# and circle.rect.collidelist(self.seats) == -1:
                    vc+=1
                    if drawsurf != None:
                        circle.draw(drawsurf)
                #else:
                #    if drawsurf != None:
                #        circle.draw(drawsurf)
        if drawsurf != None:
            for seat in seats:
                seat.draw(drawsurf)
        return vc

class Seat(object):
    __slots__ = ["color", "rect", "circle"]
    def __init__(self, rect, w, h, color):
        self.color = color
        seat_size = config.get('Fseat-size')
        self.rect = pygame.Rect(rect.left + w, rect.top + h, seat_size, seat_size)
        self.circle = Circle(
                   self.rect.centerx , self.rect.centery,
                   config.get('Fseat-radius'),
                   pygame.Color('0x222222'), False)
    def draw(self, drawsurf):
        #self.circle.draw(drawsurf)
        pygame.draw.rect(drawsurf, self.color, self.rect, 0)


class Circle(object):
    __slots__ = ["x", "y", "radius", "color", "rect", "moveable"]

    def __init__(self, x, y, radius, color, moveable):
        self.x = x
        self.y = y
        self.radius = radius
        self.color = color
        self.moveable = moveable
        self.update_bounding_box()

    def update_bounding_box(self):
        self.rect = pygame.Rect((self.x-self.radius, self.y-self.radius, 2*self.radius, 2*self.radius))

    def offset(self):
        return Util.sqr_distance(self.x, self.y, config.SCREEN_WIDTH_2, config.SCREEN_HEIGHT_2)

    def contains(self, x, y):
        return Util.sqr_distance(self.x, self.y, x, y) <= (self.radius * self.radius)

    def intersects(self, other):
        d = Util.sqr_distance(self.x, self.y, other.x, other.y)
        radii = self.radius + other.radius
        return d < (radii * radii)

    def draw(self, drawsurf):
        pygame.draw.circle(drawsurf, self.color, (int(self.x), int(self.y)), self.radius, 0)

def get_random_specimen():
    return Specimen(
            randrange(config.get('Imin-bus-w'), config.get('Imax-bus-w')+1),\
            randrange(config.get('Imin-bus-h'), config.get('Imax-bus-h')+1),
            randrange(config.get('Imin-seats'),config.get('Imax-seats')+1),
            randrange(config.get('Imin-rows'),config.get('Imax-rows')+1),
            randrange(config.get('Imin-seat-separator'),config.get('Imax-seat-separator')+1),
            randrange(config.get('Imin-rows-spacing'),config.get('Imax-rows-spacing')+1))

def generate_circles():
    radius = config.get('Fcircle-radius')
    bus_max_w = config.get('Imax-bus-w')
    bus_max_h = config.get('Imax-bus-h')
    begin_x = config.SCREEN_WIDTH_2 - bus_max_w/2
    begin_y = config.SCREEN_HEIGHT_2 - bus_max_h/2
    circles = []
    for i in xrange(config.get('Icircles')):
        c = Circle(begin_x + randrange(1,bus_max_w),
                   begin_y + randrange(1,bus_max_h),
                   radius,
                   pygame.Color('0x555555'), True)
        circles.append(c)
    return circles
