# Assignment 1 - Neural Network Bot
# USERNAME: fk18726
# REG NUMBER: 1804162
import random

import numpy as np
import tensorflow as tf
from tensorflow import keras

from fredbot import FredBot

model = keras.models.load_model('bots/fredbot_classifier')


class NeuralBot(FredBot):
    spies = []
    spy_probability = {}
    sorted_players_by_trustworthiness = []

    def calc_player_probabilities_of_being_spy(self):
        probabilities = {}
        input_vectors = []
        for p in self.game.players:
            input_vector = [self.game.turn, self.game.tries, p.index, p.name, self.missions_been_on[p],
                            self.failed_missions_been_on[p]] + self.num_missions_voted_up_with_total_suspect_count[p] + \
                           self.num_missions_voted_down_with_total_suspect_count[p]
            input_vector = input_vector[4:]
            input_vectors.append(input_vector)
        input_vectors = np.stack(input_vectors, axis=0)
        output = model(input_vectors)
        for i in range(len(self.game.players)):
            probabilities[self.game.players[i]] = output[i, 1]
        return probabilities

    def sort_player_spy_probabilities(self):
        self.spy_probability = self.calc_player_probabilities_of_being_spy()
        self.sorted_players_by_trustworthiness = [k for k, v in sorted(self.spy_probability.items(), key=lambda item: item[1])]

    def announce(self):
        probabilities = self.spy_probability
        for k, v in probabilities.items():
            probabilities[k] = float(v)
        return probabilities

    def select(self, players, count):
        self.sort_player_spy_probabilities()
        selection = []

        if not self.spy:
            if self in self.sorted_players_by_trustworthiness[:count]:
                selection = self.sorted_players_by_trustworthiness[:count]
            else:
                selection = [self] + self.sorted_players_by_trustworthiness[:count - 1]
        else:
            for player in self.spies:
                if player != self:
                    selection = [random.choice([self, player])]
                    break
            for player in self.sorted_players_by_trustworthiness:
                if player not in self.spies:
                    selection += [player]
                    if len(selection) == count:
                        break

        return selection

    def vote(self, team):
        self.sort_player_spy_probabilities()

        if not self.spy:
            if self.game.tries < 5:
                for player in team:
                    if player in self.sorted_players_by_trustworthiness[-2:]:
                        return False
            return True
        else:
            if self.game.tries < 5:
                for player in team:
                    if player in self.spies and not set(self.spies).issubset(team):
                        return True
            return False

    def sabotage(self):
        if self.game.turn == 5 or self.game.losses == 2:
            return True
        else:
            if set(self.spies).issubset(self.game.team):
                return False
            for player in self.game.team:
                if player in self.sorted_players_by_trustworthiness[:2]:
                    return True
            return False


    def onGameComplete(self, win, spies):
        pass
