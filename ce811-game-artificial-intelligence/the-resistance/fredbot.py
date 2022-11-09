# Assignment 1 - Training Data Collection Bot
# USERNAME: fk18726
# REG NUMBER: 1804162

import random

from player import Bot


class FredBot(Bot):
    def __init__(self, game, index, spy):
        super().__init__(game, index, spy)
        self.spies = []
        self.failed_missions_been_on = {}
        self.missions_been_on = {}
        self.num_missions_voted_up_with_total_suspect_count = {}
        self.num_missions_voted_down_with_total_suspect_count = {}
        self.training_feature_vectors = {}

    def select(self, players, count):
        return [self] + random.sample(self.others(), count - 1)

    def vote(self, team):
        return True

    def sabotage(self):
        return True

    def mission_total_suspect_count(self, team):
        total = 0
        for p in team:
            total += self.failed_missions_been_on.get(p)
        return total

    def onVoteComplete(self, votes):
        for i in range(len(self.game.players)):
            p = self.game.players[i]
            vote = votes[i]
            sus = self.mission_total_suspect_count(self.game.team)
            if sus > 5:
                sus = 5
            if vote:
                self.num_missions_voted_up_with_total_suspect_count[p][sus] += 1
            else:
                self.num_missions_voted_down_with_total_suspect_count[p][sus] += 1
            self.training_feature_vectors[p].append(
                [self.game.turn, self.game.tries, p.index, p.name, self.missions_been_on[p],
                 self.failed_missions_been_on[p]] + self.num_missions_voted_up_with_total_suspect_count[p] +
                self.num_missions_voted_down_with_total_suspect_count[p])

    def onGameRevealed(self, players, spies):
        for p in players:
            self.spies = spies
            self.failed_missions_been_on[p] = 0
            self.missions_been_on[p] = 0
            self.num_missions_voted_up_with_total_suspect_count[p] = [0, 0, 0, 0, 0, 0]
            self.num_missions_voted_down_with_total_suspect_count[p] = [0, 0, 0, 0, 0, 0]
            self.training_feature_vectors[p] = []

    def onMissionComplete(self, sabotaged):
        for p in self.game.team:
            self.missions_been_on[p] += 1
            if sabotaged:
                self.failed_missions_been_on[p] += 1

    def onGameComplete(self, win, spies):
        for player in self.game.players:
            spy = player in spies
            feature_vectors = self.training_feature_vectors[player]
            for v in feature_vectors:
                v.append(1 if spy else 0)
                self.log.debug(','.join(map(str, v)))
