# Assignment 2: Agent Chromosome Evaluator
# USERNAME: fk18726
# REG NUMBER: 1804162

# this file contains the genetic algorithm to evaluate different chromosomes for the rule-based agent.
# uses the 'chromosome_evaluator.py' file from the seventh lab as a starting point.

import contextlib, os, sys
from hanabi_learning_environment import rl_env
from rule_based_agent import MyAgent

def run(num_episodes, num_players, chromosome, verbose=False):
    environment=rl_env.make('Hanabi-Full', num_players=num_players)
    game_scores = []

    for episode in range(num_episodes):
        observations = environment.reset()
        agents = [MyAgent({'players': num_players},chromosome) for _ in range(num_players)]
        done = False
        episode_reward = 0

        while not done:
            for agent_id, agent in enumerate(agents):
                observation = observations['player_observations'][agent_id]
                action = agent.act(observation)

                if observation['current_player'] == agent_id:
                    assert action is not None   
                    current_player_action = action
                    if verbose:
                        print("Player",agent_id,"to play")
                        print("Player",agent_id,"View of cards",observation["observed_hands"])
                        print("Fireworks",observation["fireworks"])
                        print("Player",agent_id,"chose action",action)
                        print()
                else:
                    assert action is None

            observations, reward, done, unused_info = environment.step(current_player_action)

            if reward < 0:
                reward=0
            episode_reward += reward
            
        if verbose:
            print("Game over. Fireworks:", observation["fireworks"], "Score:", episode_reward)

        game_scores.append(episode_reward)
    return sum(game_scores) / len(game_scores)

#----------------------------------------------------------------#
#          Above is the code to run the games of Hanabi          #
#----------------------------------------------------------------#
#     Below is the genetic algorithm to evaluate chromosomes     #
#----------------------------------------------------------------#

from random import choice, uniform, randint, randrange

def swap_rules(chromosome):
    rule_1 = randrange(len(chromosome))
    rule_2 = randrange(len(chromosome))
    while rule_1 == rule_2:
        rule_2 = randrange(len(chromosome))
    chromosome[rule_1], chromosome[rule_2] = chromosome[rule_2], chromosome[rule_1]
    return chromosome

def add_rule(chromosome):
    rule = randint(0, max_rules)
    while rule in chromosome:
        rule = randint(0, max_rules)
    chromosome.insert(randrange(len(chromosome) + 1), rule)
    return chromosome

def remove_rule(chromosome):
    rule = randrange(len(chromosome))
    chromosome.remove(chromosome[rule])
    return chromosome

def change_rule(chromosome):
    index = randrange(len(chromosome))
    chromosome[index] = -1
    rule = randint(0, max_rules)
    while rule in chromosome:
        rule = randint(0, max_rules)
    chromosome[index] = rule
    return chromosome

def fitness_function(chromosome):
    with open(os.devnull, 'w') as devnull:
        with contextlib.redirect_stdout(devnull):
            result = run(25, num_players, chromosome)
    return result


if __name__=="__main__":
    num_players = 4
    max_rules = 14
    chromosome = [-1] * 4
    chromosome_fitness = 0

    scores = [[],[],[]]

    while True:
        for i in range(len(chromosome)):
            rule = randint(0, max_rules)
            while rule in chromosome:
                rule = randint(0, max_rules)
            chromosome[i] = rule
        if 6 in chromosome:
            break

    chromosome_fitness = fitness_function(chromosome)

    scores[0].append(0)
    scores[1].append(chromosome_fitness)
    scores[2].append(chromosome_fitness)

    print("{:<12} {:<8} {:<64} {:<8} {:<6} {:<8}".format('Generation:', 0, '[' + ", ".join(str(x) for x in chromosome) + ']',
                                            chromosome_fitness, 'Best:', chromosome_fitness))


    for generation in range(150):

        while True:

            chromosome_test = list(chromosome)

            mutation = choice([1, 2, 3, 4])

            if mutation == 1:
                if len(chromosome_test) == max_rules + 1:
                    continue
                chromosome_test = add_rule(chromosome_test)

            elif mutation == 2:
                if len(chromosome_test) <= 4:
                    continue
                chromosome_test = remove_rule(chromosome_test)

            elif mutation == 3:
                chromosome_test = swap_rules(chromosome_test)

            elif mutation == 4:
                if len(chromosome_test) == max_rules + 1:
                    continue
                chromosome_test = change_rule(chromosome_test)

            if 6 in chromosome_test:
                break

        chromosome_test_fitness = fitness_function(chromosome_test)

        if chromosome_test_fitness >= chromosome_fitness:
            chromosome_fitness = chromosome_test_fitness
            chromosome = list(chromosome_test)

        scores[0].append(generation + 1)
        scores[1].append(chromosome_test_fitness)
        scores[2].append(chromosome_fitness)

        print("{:<12} {:<8} {:<64} {:<8} {:<6} {:<8}".format('Generation:', generation + 1, '[' + ", ".join(str(x) for x in chromosome_test) + ']',
            chromosome_test_fitness, 'Best:', chromosome_fitness))
    print('Best:', chromosome, chromosome_fitness)
    print('\n')

    print('Re-Running Best Chromosome For Consistency:')
    reruns = []
    reruns.append(chromosome_fitness)
    for i in range(10):
        result = fitness_function(chromosome)
        print('Run ' + str(i + 1) + ':', result)
        reruns.append(result)
    average = sum(reruns) / len(reruns)
    print('Average:', round(average, 4))

    from matplotlib import pyplot as plt

    plt.plot(scores[0], scores[1])

    plt.xlabel('generations')
    plt.ylabel('score')

    plt.savefig('score_evolutions/score_evolution.png')
    plt.clf()

    plt.plot(scores[0], scores[2])

    plt.xlabel('generations')
    plt.ylabel('score')

    plt.savefig('score_evolutions/best_score_evolution.png')