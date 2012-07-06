import platform, time, os
from math import sqrt, ceil
from Config import config
#from operator import methodcaller
def methodcaller(name, *args, **kwargs):
    def caller(obj):
        return getattr(obj, name)(*args, **kwargs)
    return caller

if platform.system() == 'Windows':
    timing = time.clock
else:
    timing = time.time

def time_dec(func):

  def wrapper(*a, **arg):
      global last_time
      t = timing()
      res = func(*a, **arg)
      last_time = timing()-t
      os.write(2, 'TIME %s : %f\n' % (func.__name__, last_time))
      return res

  return wrapper
def sqr_distance(x0, y0, x1, y1):
    dx = x1 - x0
    dy = y1 - y0
    return dx*dx + dy*dy

#def int2bin(n, count=24):
#    """returns the binary of integer n, using count number of digits"""
#    return "".join([str((n >> y) & 1) for y in range(count-1, -1, -1)])

# from http://www.radlogic.com/releases/rad_util.py
def int2bin(i, n):
    """Convert decimal integer i to n-bit binary number (string).

    >>> int2bin(0, 8)
    '00000000'

    >>> int2bin(123, 8)
    '01111011'

    >>> int2bin(123L, 8)
    '01111011'

    >>> int2bin(15, 2)
    Traceback (most recent call last):
    ValueError: Value too large for given number of bits.

    """
    hex2bin = {'0': '0000', '1': '0001', '2': '0010', '3': '0011',
               '4': '0100', '5': '0101', '6': '0110', '7': '0111',
               '8': '1000', '9': '1001', 'a': '1010', 'b': '1011',
               'c': '1100', 'd': '1101', 'e': '1110', 'f': '1111'}
    # Convert to hex then map each hex digit to binary equivalent.
    result = ''.join([hex2bin[x] for x in hex(i).lower().replace('l','')[2:]])

    # Shrink result to appropriate length.
    # Raise an error if the value is changed by the truncation.
    if '1' in result[:-n]:
        raise ValueError("Value too large for given number of bits.")
    result = result[-n:]
    # Zero-pad if length longer than mapped result.
    result = '0'*(n-len(result)) + result
    return result



# from http://www.radlogic.com/releases/rad_util.py
def bin2int(bin_string):
    """Convert binary number string to decimal integer.

    Note: Python > v2 has int(bin_string, 2)

    >>> bin2int('1111')
    15

    >>> bin2int('0101')
    5

##    """
##    result = 0
##    bin_list = list(bin_string)
##    if len(filter(lambda x: x in ('1','0'), bin_list)) < len(bin_list):
##        raise Exception ("bin2int: Error - not a binary number: %s"
##                         % bin_string)
##    bit_list = map(int, bin_list)
##    bit_list.reverse()  # Make most significant bit have highest index.
##    for bit_place in range(len(bit_list)):
##        result = result + ((2**bit_place) * bit_list[bit_place])
    return int(bin_string, 2)

def distance(x0, y0, x1, y1): # slow ***
    dx = x1 - x0
    dy = y1 - y0
    return sqrt(dx*dx + dy*dy)

# http://leonardo-m.livejournal.com/80721.html
#@time_dec
def pack(circles_passangers, specimen, damping=0.1, padding=2):
    #specimen seat circles must be not modified!

    circles = circles_passangers[:]
    for s in specimen.seats:
        circles.append(s.circle)
    circles.sort(key=methodcaller("offset"))

    len_circles = len(circles)
    # repulsive force: move away from intersecting circles.
    for i in xrange(len_circles):
        circle1 = circles[i]
        circle1_x = circle1.x
        circle1_y = circle1.y
        circle1_radius_padded = circle1.radius + padding
        for j in xrange(i+1, len_circles):
            circle2 = circles[j]

            #inlined for speed
            #d_d = Util.sqr_distance(circle1_x, circle1_y, circle2.x, circle2.y)
            dx = circle2.x - circle1_x
            dy = circle2.y - circle1_y
            d_d = dx*dx + dy*dy

            r = circle1_radius_padded + circle2.radius
            if d_d < (r * r - 0.01):
                dx = circle2.x - circle1_x
                dy = circle2.y - circle1_y
                d = sqrt(d_d) # slow
                aux = (r - d) * 0.5
                if d!=0:
                    vx = (dx / d) * aux
                    vy = (dy / d) * aux
                else:
                    vx = 1
                    vy = 1
                if circle1.moveable:
                    #if not circle2.moveable:
                    #    circle1.x -= vx
                    #    circle1.y -= vy
                    circle1.x -= vx
                    circle1.y -= vy
                if circle2.moveable:
                    #if not circle1.moveable:
                    #    circle2.x += vx
                    #    circle2.y += vy
                    circle2.x += vx
                    circle2.y += vy

    # attractive force: all circles move to center
    for circle in circles:
        if circle.moveable:
            vx = (circle.x - config.SCREEN_WIDTH_2) * damping/4
            vy = (circle.y - config.SCREEN_HEIGHT_2) * damping
            circle.x -= vx
            circle.y -= vy
            circle.update_bounding_box()

