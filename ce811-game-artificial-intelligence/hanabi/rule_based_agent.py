# Assignment 2: Rule-Based Hanabi Agent
# USERNAME: fk18726
# REG NUMBER: 1804162

# this file contains the agent for the assignment task.
# uses the 'rule_agent_chromosome.py' file from the seventh lab as a starting point.

from hanabi_learning_environment.rl_env import Agent

def argmax(llist):
    return llist.index(max(llist))

class MyAgent(Agent):

    def __init__(self, config, chromosome=[0, 10, 11, 9, 1, 7, 2, 8, 12, 5, 6], *args, **kwargs):
        self.config = config
        self.chromosome = chromosome
        assert isinstance(chromosome, list)
        
        self.max_information_tokens = config.get('information_tokens', 8)

    def calculate_all_unseen_cards(self, discard_pile, player_hands, fireworks):
        colors = ['Y', 'B', 'W', 'R', 'G']
        full_hanabi_deck = [{"color":c, "rank":r} for c in colors for r in [0,0,0,1,1,2,2,3,3,4]]
        assert len(full_hanabi_deck) == 50
        result = full_hanabi_deck

        for card in discard_pile:
            if card in result:
                result.remove(card)
        
        for hand in player_hands[1:]:
            for card in hand:
                if card in result:
                    result.remove(card)

        for (color, height) in fireworks.items():
            for rank in range(height):
                card = {"color":color, "rank":rank}
                if card in result:
                    result.remove(card)

        return result

    def filter_card_list_by_hint(self, card_list, hint):
        filtered_card_list = card_list

        if hint["color"] != None:
            filtered_card_list = [c for c in filtered_card_list if c["color"] == hint["color"]]

        if hint["rank"] != None:
            filtered_card_list = [c for c in filtered_card_list if c["rank"] == hint["rank"]]

        return filtered_card_list


    def filter_card_list_by_playability(self, card_list, fireworks):
        #return [c for c in card_list if self.is_card_playable(c,fireworks)]
        result = []
        for c in card_list:
            if self.is_card_playable(c, fireworks):
                result.append(c)
        return result

    # def filter_card_list_by_unplayable(self, card_list, fireworks):
    #     #return [c for c in card_list if c["rank"] < fireworks[c["color"]]]
    #     result = []
    #     for c in card_list:
    #         if c["rank"] < fireworks[c["color"]]:
    #             result.append(c)
    #     return result

    def filter_card_list_by_unplayable(self, card_list, fireworks, discard_pile):
        result = []
        for c in card_list:
            if c["rank"] < fireworks[c["color"]]:
                result.append(c)
                
            else:
                for d in discard_pile:
                    copies = {
                        '0':3,
                        '1':2,
                        '2':2,
                        '3':2,
                        '4':1
                    }
                    count = {
                        '0':0,
                        '1':0,
                        '2':0,
                        '3':0,
                        '4':0
                    }
                    if d["color"] == c["color"]:
                        count[str(d["rank"])] += 1

                    for i in range(c["rank"]):
                        if count[str(i)] == copies[str(i)]:
                            result.append(c)
        return result

    def is_card_playable(self, card, fireworks):
        return card['rank'] == fireworks[card['color']]

    def act(self, observation):
        if observation['current_player_offset'] != 0:
            return None
        
        fireworks = observation['fireworks']
        card_hints = observation['card_knowledge'][0]
        hand_size = len(card_hints)

        discard_pile = observation['discard_pile']

        all_unseen_cards = self.calculate_all_unseen_cards(observation['discard_pile'],observation['observed_hands'],observation['fireworks'])
        possible_cards_by_hand = [self.filter_card_list_by_hint(all_unseen_cards, h) for h in card_hints]
        playable_cards_by_hand = [self.filter_card_list_by_playability(posscards, fireworks) for posscards in possible_cards_by_hand]
        probability_cards_playable = [len(playable_cards_by_hand[index]) / len(possible_cards_by_hand[index]) for index in range(hand_size)]

        # useless_cards_by_hand = [self.filter_card_list_by_unplayable(posscards, fireworks) for posscards in possible_cards_by_hand]
        useless_cards_by_hand = [self.filter_card_list_by_unplayable(posscards, fireworks, discard_pile) for posscards in possible_cards_by_hand]

        probability_cards_useless = [len(useless_cards_by_hand[index]) / len(possible_cards_by_hand[index]) for index in range(hand_size)]

        for rule in self.chromosome:

            if rule in [0,1]: # LAB CODE RULE [0,1] # PLAY A CARD THAT HAS A HIGH PROBABILITY OF BEING PLAYABLE
                threshold=0.8 if rule==0 else 0.5
                # if the probability of the card being playable is higher than the threshold
                if max(probability_cards_playable)>threshold:
                    # play the card that is most likely to be useful
                    card_index=argmax(probability_cards_playable)
                    return {
                        'action_type': 'PLAY',
                        'card_index': card_index
                    }

            elif rule==2: # SPLIT FROM LAB CODE RULE 2 # GIVE A COLOUR HINT TO ANOTHER PLAYER
                # check to see any more hints can be given
                if observation['information_tokens'] > 0:
                    # check to see if opponents have playable cards
                    for player_offset in range(1, observation['num_players']):
                        player_hand = observation['observed_hands'][player_offset]
                        player_hints = observation['card_knowledge'][player_offset]
                        # check to see if each card is playable
                        for card, hint in zip(player_hand, player_hints):
                            if self.is_card_playable(card,fireworks):
                                # if the card's colour is not already hinted
                                if hint['color'] is None:
                                    return {
                                        'action_type': 'REVEAL_COLOR',
                                        'color': card['color'],
                                        'target_offset': player_offset
                                    }

            elif rule in [3,4]: # LAB CODE RULE [3,4] # DISCARD A CARD THAT HAS A HIGH PROBABILITY OF BEING USELESS
                threshold=0.8 if rule==3 else 0.5
                # checks to see if a discard action can be made
                if observation['information_tokens'] < self.max_information_tokens:
                    # if the probability of the card being useless is higher than the threshold
                    if max(probability_cards_useless)>threshold:
                        # discard the card that is most likely to be useless
                        card_index=argmax(probability_cards_useless)
                        return {
                            'action_type': 'DISCARD',
                            'card_index': card_index
                        }

            elif rule==5: # LAB CODE RULE 5 # DISCARD THE OLDEST CARD
                # checks to see if a discard action can be made
                if observation['information_tokens'] < self.max_information_tokens:
                    return {
                        'action_type': 'DISCARD',
                        'card_index': 0
                    }

            elif rule==6: # SLIGHTLY EDITED FROM LAB CODE RULE 6 # PLAY THE CARD THAT HAS THE HIGHEST PROBABILITY OF BEING PLAYABLE
                # play the card that is most likely to be useful
                card_index = argmax(probability_cards_playable)
                return {
                    'action_type': 'PLAY',
                    'card_index': card_index
                }

            elif rule==7: # SPLIT FROM LAB CODE RULE 2 # GIVE A RANK HINT TO ANOTHER PLAYER
                # check to see any more hints can be given
                if observation['information_tokens'] > 0:
                    # check to see if opponents have playable cards
                    for player_offset in range(1, observation['num_players']):
                        player_hand = observation['observed_hands'][player_offset]
                        player_hints = observation['card_knowledge'][player_offset]
                        # check to see if each card is playable
                        for card, hint in zip(player_hand, player_hints):
                            if self.is_card_playable(card,fireworks):
                                # if the card's rank is not already hinted
                                if hint['rank'] is None:
                                    return {
                                        'action_type': 'REVEAL_RANK',
                                        'rank': card['rank'],
                                        'target_offset': player_offset
                                    }

            elif rule == 8: # DISCARD A CARD IF A DUPLICATE IS HELD
                # checks to see if a discard action can be made
                if observation['information_tokens'] < self.max_information_tokens:
                    player_hand = observation['observed_hands'][0]
                    player_hints = observation['card_knowledge'][0]
                    # checks each card against each other card
                    for card, hint in zip(player_hand, player_hints):
                        for other_card, other_hint in zip(player_hand, player_hints):
                            # skip if the card is being compared to itself
                            if card == other_card:
                                continue
                            # if the cards are identical discard one
                            if hint['color'] is not None and hint['rank'] is not None:
                                if hint == other_hint:
                                    return {
                                        'action_type': 'DISCARD',
                                        'card_index': player_hand.index(card)
                                    }

            elif rule == 9: # DISCARD A CARD IF THAT COLOUR'S FIREWORK IS COMPLETE
                # checks to see if a discard action can be made
                if observation['information_tokens'] < self.max_information_tokens:
                    player_hand = observation['observed_hands'][0]
                    player_hints = observation['card_knowledge'][0]
                    fireworks = observation['fireworks']
                    # checks each card against the firework pile
                    for card, hint in zip(player_hand, player_hints):
                        if hint['color'] is not None:
                            # discard the card if the firework pile for that colour is complete
                            if fireworks[hint['color']] == 5:
                                return {
                                    'action_type': 'DISCARD',
                                    'card_index': player_hand.index(card)
                                }
            
            elif rule == 10: # DISCARD A CARD IF IT IS PRESENT IN THE FIREWORK
                # checks to see if a discard action can be made
                if observation['information_tokens'] < self.max_information_tokens:
                    player_hand = observation['observed_hands'][0]
                    player_hints = observation['card_knowledge'][0]
                    fireworks = observation['fireworks']
                    # checks each card against the firework pile
                    for card, hint in zip(player_hand, player_hints):
                        if hint['color'] is not None and hint['rank'] is not None:
                            # discard the card if it has already been successfully played
                            if fireworks[hint['color']] == hint['rank'] + 1:
                                return {
                                    'action_type': 'DISCARD',
                                    'card_index': player_hand.index(card)
                                }

            elif rule == 11: # PLAY A CARD IF IT IS A ONE
                player_hand = observation['observed_hands'][0]
                player_hints = observation['card_knowledge'][0]
                fireworks = observation['fireworks']
                # checks each card against the firework pile
                for card, hint in zip(player_hand, player_hints):
                    if hint['rank'] == 0:
                        # if no fireworks have started yet
                        if all(value == 0 for value in fireworks.values()):
                            return {
                                'action_type': 'PLAY',
                                'card_index': player_hand.index(card)
                            }

                        elif hint['color'] is not None:
                            # if the card has a rank of one and that colour firework has not started
                            if fireworks[hint['color']] == 0:
                                return {
                                    'action_type': 'PLAY',
                                    'card_index': player_hand.index(card)
                                }

            elif rule == 12: # PLAY A CARD IF IT IS A FIVE
                player_hand = observation['observed_hands'][0]
                player_hints = observation['card_knowledge'][0]
                fireworks = observation['fireworks']
                # checks each card against the firework pile
                for card, hint in zip(player_hand, player_hints):
                    if hint['rank'] == 4:
                        if all(value == 4 or value == 5 for value in fireworks.values()):
                            return {
                                'action_type': 'PLAY',
                                'card_index': player_hand.index(card)
                            }

                        elif hint['color'] is not None:
                            # if the card has a rank of five and that colour firework has a size of four
                            if fireworks[hint['color']] == 4:
                                return {
                                    'action_type': 'PLAY',
                                    'card_index': player_hand.index(card)
                                }

            elif rule == 13: # REVEAL ANOTHER PLAYER'S CARD IF IT IS A ONE
                if observation['information_tokens'] > 0:
                    for player_offset in range(1, observation['num_players']):
                        player_hand = observation['observed_hands'][player_offset]
                        player_hints = observation['card_knowledge'][player_offset]
                        for card, hint in zip(player_hand, player_hints):
                            # checks to see if there is a one that has not been hinted
                            if card['rank'] == 0 and hint['rank'] == None:
                                return {
                                    'action_type': 'REVEAL_RANK',
                                    'rank': card['rank'],
                                    'target_offset': player_offset
                                }

            elif rule == 14: # REVEAL ANOTHER PLAYER'S CARD IF IT IS A FIVE
                if observation['information_tokens'] > 0:
                    for player_offset in range(1, observation['num_players']):
                        player_hand = observation['observed_hands'][player_offset]
                        player_hints = observation['card_knowledge'][player_offset]
                        for card, hint in zip(player_hand, player_hints):
                            # checks to see if there is a five that has not been hinted
                            if card['rank'] == 4 and hint['rank'] == None:
                                    return {
                                        'action_type': 'REVEAL_RANK',
                                        'rank': card['rank'],
                                        'target_offset': player_offset
                                    }

            else:
                raise Exception("Rule not defined: " + str(rule))  
        raise Exception("No rule fired for game situation - faulty rule set")