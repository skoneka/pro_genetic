from Config import config
import Util
from math import sqrt, ceil
from random import randrange, random

from Objects import Specimen


class Breeder(object):
    
    def __init__(self, specimens):
        self.specimens = specimens

    def breed(self, num):
        genome_bits = config.get('Igenome-bits')
        mutation_rate = 10000 * config.get('Fmutation-rate')
        new_specimens = []
        while True:
            for i_m, spec_m in enumerate(self.specimens):
                #print('spec_m: ' + str(spec_m))
                m = Util.int2bin(spec_m.seats_potential, genome_bits )
                m += Util.int2bin(spec_m.rect.w, genome_bits)
                m += Util.int2bin(spec_m.rect.h, genome_bits)
                m += Util.int2bin(spec_m.rows, genome_bits)
                m += Util.int2bin(spec_m.rows_spacing, genome_bits)
                m += Util.int2bin(spec_m.seat_separator, genome_bits)
                while True:
                    i_f = randrange(0,len(self.specimens))
                    if i_f == i_m:
                        continue;
                    else:
                        spec_f = self.specimens[i_f]
                        #print('spec_f: ' + str(spec_f))
                        f = Util.int2bin(spec_f.seats_potential, genome_bits )
                        f += Util.int2bin(spec_f.rect.w, genome_bits)
                        f += Util.int2bin(spec_f.rect.h, genome_bits)
                        f += Util.int2bin(spec_f.rows, genome_bits)
                        f += Util.int2bin(spec_f.rows_spacing, genome_bits)
                        f += Util.int2bin(spec_f.seat_separator, genome_bits)

                        n = self._crossover(m, f)

                        mut = randrange(0,10000)
                        if mut <= mutation_rate:
                            print 'mutate!'
                            n = self._mutate(n)

                        def get_chunks(n):
                            n = "".join(n)
                            for i in range(0, len(n), genome_bits):
                                yield n[i:i+genome_bits]

                        g = get_chunks(n)
                        seats_n = Util.bin2int(g.next())
                        width = Util.bin2int(g.next())
                        height = Util.bin2int(g.next())
                        rows = Util.bin2int(g.next())
                        rows_spacing = Util.bin2int(g.next())
                        seat_separator = Util.bin2int(g.next())
                        #print 'new seats: %d' % seats_n
                        spec_n = Specimen(width, height, seats_n, rows, rows_spacing, seat_separator)
                        #print('spec_n: ' + str(spec_n))
                        new_specimens.append(spec_n)
                        break
                if len(new_specimens) >= num:
                    return new_specimens
                    break

    def _crossover(self, s1, s2):
        #a = Util.bin2int("".join(s1))
        #b = Util.bin2int("".join(s2))
        #print a
        #print b
        #return Util.int2bin((a+b)/2, len(s1))
        s1_i = s2_i = 0
        #def
        n = []
        a = iter(s1)
        b = iter(s2)
        for i in range(0, len(s1)):
            try:
                n.append(a.next())
                a.next()
            except StopIteration:
                pass
            try:
                b.next()
                n.append(b.next())
            except StopIteration:
                break
        return n

    def _crossover_old(self, s1, s2):
        n = []
        a = iter(s1)
        b = iter(s2)
        for i in range(0, len(s1)):
            try:
                n.append(a.next())
                a.next()
            except StopIteration:
                pass
            try:
                b.next()
                n.append(b.next())
            except StopIteration:
                break
        return n

    def _mutate(self, ss):
        n = randrange(1, len(ss))
        for i in range(0,n):
            s_bit_select = randrange(1, len(ss))
            s_action = str(randrange(0,2))
            s_action_range = randrange(-1,2)
            if ss[s_bit_select] == '1':
                if s_bit_select + s_action_range < len(ss) and s_bit_select + s_action_range > -1:
                    s_bit_select += s_action_range
                #print '%d mutate! [%d] %s to %s' % (ss_select,s_bit_select, ss[ss_select][s_bit_select], s_action)
                ss[s_bit_select] = s_action

                n_s_bit_select = s_bit_select - 2 * s_action_range
                if s_action == 1 and n_s_bit_select < len(ss) and \
                        n_s_bit_select > -1:
                    ss[n_s_bit_select] = s_action
        return ss


class GenerationEvaluator(object):
    __slots__ = ["generation_stats", "generation_stats_others", "generation"]
    def __init__(self, generation):
        self.generation = generation
        self.generation_stats = [None] * len(generation)
        self.generation_stats_others = [None] * len(generation)

    def addStat(self,index, stat, other_stats):
        if stat < 0:
            stat = 0
        self.generation_stats[index] = stat
        self.generation_stats_others[index] = other_stats

    def get_best(self, num):
        # yield score and num best specimens
        elitism = []
        for i, v in enumerate(self.generation_stats):
            elitism.append((i,v))
        elitism.sort(key = lambda k : k[1], reverse =True)

        for i,v in elitism[:num]:
            yield v, self.generation[i],self.generation_stats_others[i]

    def get_elite(self, frac):
        for score, spec, other_stats in self.get_best(int(ceil(len(self.generation_stats) * frac))):
            yield spec


    def roulette(self, num):
        return self.roulette_select(self.generation, self.generation_stats, num)



    def roulette_select(self, population, fitnesses, num):
        """ Roulette selection, implemented according to:
            <http://stackoverflow.com/questions/177271/roulette
            -selection-in-genetic-algorithms/177278#177278>
            Return a list of selected indydiduals
        """
        total_fitness = float(sum(fitnesses))
        if total_fitness == 0:
            total_fitness = 0.00001
        rel_fitness = [f/total_fitness for f in fitnesses]
        # Generate probability intervals for each individual
        probs = [sum(rel_fitness[:i+1]) for i in range(len(rel_fitness))]
        # Draw new population
        new_population = []
        while num > 0:
            #for n in xrange(num):
            r = random()
            for (i, individual) in enumerate(population):
                if r <= probs[i]:
                    if True or individual not in new_population:
                        new_population.append(individual)
                        num -= 1
                        break
                    else:
                        pass
        return new_population




class Generation(object):
    __slots__ = ["specimens"]

    def __init__(self):
        self.specimens = []

    def evaluate(self):
        #return a list of survived specimens and  their fitness
        pass
    def add(self, specimen):
        self.specimens.append(specimen)

    def __iter__(self):
        for spec in self.specimens:
            yield spec
    def __len__(self):
        return len(self.specimens)
    def __getitem__(self, x):
        return self.specimens[x]
# http://leonardo-m.livejournal.com/80721.html
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


def process_specimen(drawsurf, specimen, circles, one_seat_space):
    standing = specimen.update2(drawsurf, circles)
    sitting = len(specimen.seats)
    seats_space = sitting * one_seat_space
    floor_space = specimen.rect.w * specimen.rect.w - seats_space

    cost = config.get('Ffactor-cost') * \
            (config.get('Fmax-cost') - \
            seats_space * config.get('Fcost-seat') - \
            seats_space * config.get('Fcost-space'))
    comfort = config.get('Ffactor-comfort') * \
            (sitting - config.get('Fmin-comfort') )
    space = config.get('Ffactor-capacity') * \
            (sitting + standing - \
            config.get('Fmin-space'))
    if cost < 0 or comfort < 0 or space < 0:
        stat = 0
    else:
        stat = cost + comfort + space

    return standing, sitting, seats_space, floor_space, stat, cost, comfort, space
