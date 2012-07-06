"""
Ported from Sean McCullough's Processing code:
http://www.cricketschirping.com/processing/CirclePacking1/

See also: http://en.wiki.mcneel.com/default.aspx/McNeel/2DCirclePacking
http://www.cricketschirping.com/weblog/?p=1047
See also:
http://www.infovis-wiki.net/index.php/Circle_Packing

Original NodeBox code by Tom De Smedt:
http://nodebox.net/code/index.php/shared_2008-08-07-12-55-33
Later ported to Python + Psyco + Pygame by leonardo maffi, V.1.1, Apr 15 2009

V.1.1: removed a bug in the sort, found by Mark Dufour.
"""

from Config import config
from Objects import Specimen, Circle, Seat, get_random_specimen, generate_circles
from Genetic import Breeder, GenerationEvaluator, Generation, process_specimen

import sys, os
import time
from random import randrange, random
from Util import time_dec

import pygame
from pygame.locals import QUIT, K_ESCAPE, K_RETURN
import eztext

import Util

try:
    import psyco
    psyco.full()
except:
    print "Psyco is able to speed up this program a lot"
    print "http://psyco.sourceforge.net/"

# to center the window in the screen
if sys.platform == 'win32' or sys.platform == 'win64':
    os.environ['SDL_VIDEO_CENTERED'] = '1'

class ResetException(Exception):
    pass

def setup():
    global  surface, drawsurf, screen
    global overlay

    pygame.init()
    from Overlay import overlay
    screen = (config.SCREEN_WIDTH, config.SCREEN_HEIGHT)


    pygame.display.set_caption("Genetic algorithm evaluator and viewer.")
    surface = pygame.display.set_mode(screen)

    drawsurf = pygame.Surface(screen).convert()
    drawsurf.set_colorkey((0, 0, 0))

def get_input():
    key = pygame.key.get_pressed()

    events = pygame.event.get()
    for event in events:
        if event.type == QUIT or key[K_ESCAPE]:
            pygame.quit()
            sys.exit()
        elif key[K_RETURN]:
            text = overlay.txtbx.value.strip().split()
            try:
                if text[0] in ('quit', 'exit'):
                    pygame.quit()
                    sys.exit()
                elif text[0] in ('start', 'run'):
                    config.setControl('running', True)
                elif text[0] == 'stop':
                    config.setControl('running', False)
                elif text[0] in ('r', 'reset'):
                    overlay.txtbx.value = ""
                    raise ResetException
                elif text[0] in ('s' 'show'):
                    try:
                        config.setControl('showing specimen', int(text[1]))
                    except IndexError:
                        pass
                else:
                    try:
                        v = int(text[0])
                        config.setControl('showing specimen', int(text[0]))
                    except ValueError:
                        try:

                            if text[0][0] == 'I':
                                config.set(text[0], int(text[1]))
                            elif text[0][0] == 'F':
                                config.set(text[0], float(text[1]))
                            else:
                                config.set(text[0], text[1])
                        except IndexError:
                            pass
                        except ValueError:
                            pass
            except IndexError:
                pass
            overlay.txtbx.value = ""
    overlay.txtbx.update(events)

def run_evaluator(drawsurf, circles, gen_evaluator, generation):
    one_seat_space = config.get('Fseat-size') ** 2
    for spec_c, specimen in enumerate(generation):
        for i in xrange(1, config.get('Ipacking-iter')):
            Util.pack(circles, specimen, damping=0.1/i )

        if config.getControl('showing specimen') == 0:
            ds = drawsurf
        else:
            ds = None
        standing, sitting, seats_space, floor_space, stat, cost, comfort, space =\
                process_specimen(ds, specimen, circles, one_seat_space)
        gen_evaluator.addStat(spec_c, stat,( standing, sitting, seats_space, floor_space, stat, cost, comfort, space) )
        yield spec_c, specimen, standing, sitting, seats_space, floor_space, stat, cost, comfort, space

def run():
    gen_count = 0
    time_now = time_last_tick = time_start = time.clock()
    time_delta = 0
    one_seat_space = config.get('Fseat-size') ** 2
    circles = []
    circles = generate_circles()

    generation = Generation()
    for i in range(0,config.get('Ipopulation')):
        generation.add(get_random_specimen())



    gen_count = 0
    generator = None
    gen_evaluator = None
    gen_count = spec_c = specimen = standing = sitting = seats_space = \
            floor_space = stat = cost = comfort = space = rows = \
            rows_spacing = seat_separator = seats_potential = 0
    bus_w = bus_h = 0
    while True:
        surface.fill((0, 0, 0))
        drawsurf.fill((255, 255, 255))
        if config.getControl('running'):
            try:
                spec_c, specimen, standing, sitting, seats_space, floor_space, stat, cost, comfort, space = \
                        generator.next()
                bus_w = specimen.rect.w
                bus_h = specimen.rect.h
                rows = specimen.rows
                rows_spacing = specimen.rows_spacing
                seat_separator = specimen.seat_separator
                seats_potential = specimen.seats_potential
            except (StopIteration, AttributeError):
                if gen_evaluator:
                    new_specimens = []
                    print('Generation: %d' % gen_count)
                    for s in gen_evaluator.get_elite(config.get('Felitism')):
                        new_specimens.append(s)
                    print 'Elite: ' + str(len(new_specimens))

                    roulettes=[]
                    for s in gen_evaluator.roulette(config.get('Ipopulation') \
                            * config.get('Froulette') - len(new_specimens)):
                        roulettes.append(s)
                    print('Roulette: '+ str(len(roulettes)))
                    breeder = Breeder(roulettes + new_specimens)
                    bred_specimens = \
                            breeder.breed( config.get('Ipopulation') - len(new_specimens) )
                    print 'Bred: ' + str(len(bred_specimens))

                    new_specimens = new_specimens + bred_specimens
                    print 'Next generation: '+str(len(new_specimens))
                    new_generation = Generation()
                    for s in new_specimens:
                        new_generation.add(s)
                    generation = new_generation


                gen_evaluator = GenerationEvaluator(generation)
                generator = run_evaluator(drawsurf, circles, gen_evaluator, generation)
                max_gen = config.get('Imax-generations')
                if max_gen > 0 and gen_count >= max_gen:
                    config.setControl('running', False)
                else:
                    gen_count += 1
            time_now = time.clock()
        else:
            time_now = time.clock()
            time_delta += time_now - time_last_tick
        time_last_tick = time_now

        show_mode = config.getControl('showing specimen')
        if show_mode > 0:
            try:
                if gen_evaluator:
                    best = list(gen_evaluator.get_best(show_mode))
                    spec = best[show_mode-1][1]
                    try:
                        standing, sitting, seats_space, floor_space, stat, cost, comfort, space = best[show_mode-1][2]
                    except:
                        pass
                    #print standing, sitting, seats_space, floor_space, stat, cost, comfort, space
                    spec.update2(drawsurf, None)
                    #standing, sitting, seats_space, floor_space, stat, cost, comfort, space = \
                    #        process_specimen(drawsurf, spec, None, one_seat_space)

                    max_adapt = config.get('Imax-adaptation')
                    if max_adapt > 0 and stat >= max_adapt:
                        config.setControl('running', False)

                    bus_w = spec.rect.w
                    bus_h = spec.rect.h
                    rows = spec.rows
                    rows_spacing = spec.rows_spacing
                    seat_separator = spec.seat_separator
                    seats_potential = spec.seats_potential
                    sitting = len(spec.seats)
            except (IndexError, TypeError):
                config.setControl('showing specimen', 'ERROR')



        overlay.setStat('stat cost effect = ', cost)
        overlay.setStat('stat comfort = ', comfort)
        overlay.setStat('stat passangers = ', space)
        overlay.setStat('stat = ', stat)

        overlay.setTop('time = ', time_now - time_start - time_delta)
        overlay.setTop('generation = ', gen_count)
        overlay.setTop('specimen = ', spec_c)
        overlay.set('standing = ',standing)
        overlay.set('seats = ', sitting)
        overlay.set('seats (potential) = ', seats_potential)
        overlay.set('bus width = ', bus_w)
        overlay.set('bus height = ', bus_h)
        overlay.set('rows spacing = ', rows_spacing)
        overlay.set('seat separator = ', seat_separator)
        overlay.set('seats space = ', seats_space)
        overlay.set('floor space = ', floor_space)


        get_input()
        overlay.draw(drawsurf, config)
        surface.blit(drawsurf, (0, 0))
        pygame.display.flip()

if __name__=='__main__':
    while True:
        try:
            setup()
            run()
        except ResetException:
            continue
        break

