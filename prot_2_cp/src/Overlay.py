import pygame
import eztext

class Overlay(object):
    __slots__ = ["font", "color", "entries", "entries_top", "entries_stat", "txtbx"]
    def __init__(self):
        self.font = pygame.font.Font(None,25)
        self.color = (0,0,0)
        self.entries = {}
        self.entries_top = {}
        self.entries_stat = {}
        self.txtbx = eztext.Input(maxlength=45, color=(255,0,0), prompt='prompt> ')

    def draw(self, drawsurf, config):
        text = self.font.render('Genetic algorithm evaluator and viewer.', 1, self.color)
        t_height = text.get_rect().h
        drawsurf.blit(text, (5, 40))
        pos_y = 60
        for key, val in sorted(self.entries_top.iteritems()):
            text = self.font.render(str(key)+str(val), 1, self.color)
            drawsurf.blit(text, (1, pos_y))
            pos_y += t_height
        pos_y += t_height
        for key, val in sorted(self.entries.items()):
            text = self.font.render(str(key)+str(val), 1, self.color)
            drawsurf.blit(text, (1, pos_y))
            pos_y += t_height
        pos_y += t_height
        for key, val in sorted(self.entries_stat.items()):
            text = self.font.render(str(key)+str(val), 1, self.color)
            drawsurf.blit(text, (1, pos_y))
            pos_y += t_height

        pos_y = 60
        for key, val in sorted(config.control.items()):
            s = str(key)+' : '+str(val)
            text = self.font.render(s, 1, self.color)
            text_rect = text.get_rect()
            drawsurf.blit(text, (config.SCREEN_WIDTH-text_rect.w, pos_y))
            pos_y += t_height

        pos_y += t_height
        for key, val in sorted(config.entries.items()):
            s = str(key)+' : '+str(val)
            text = self.font.render(s, 1, self.color)
            text_rect = text.get_rect()
            drawsurf.blit(text, (config.SCREEN_WIDTH-text_rect.w, pos_y))
            pos_y += t_height
        self.txtbx.draw(drawsurf)

    def set(self, name, value):
        self.entries[name] = value
    def setTop(self, name, value):
        self.entries_top[name] = value
    def setStat(self, name, value):
        self.entries_stat[name] = value


overlay = Overlay();
