This folder contains all the files required to train and run the AI agent 'neural_fredbot.py'.
Below is a list of the folder contents and what their purpose is:

fredbot_classifier:
	This directory contains the saved model from the classifier. This is used by 'neural_fredbot.py'
	in order to calculate the probabilities of the players in the game, provided a set of feature
	vectors.

classifier_fredbot.ipynb:
	This is the classifier that trains the model to be used by 'neural_fredbot.py'. It imports data
	from the log file 'FredBot.log' as a csv in order to format the data into columns and remove any
	stray print() statements. The model it outputs is saved within 'fredbot_classifier'.

FredBot.log:
	This is the log file created by 'fredbot.py', which contains the feature vectors of each player at
	each phase in the game. This is used by 'classifier_fredbot.ipynb' in order to train the model.

fredbot.py:
	This is a bot that has to simply observe the game; collecting data on the habits of the other players
	and storing them as a set of feature vectors within 'FredBot.log'

neural_fredbot.py:
	This is the main agent for this submission. This agent inherits the behaviour from 'fredbot.py' in order
	to store feature vectors on features about other players' behaviour. At each selection and voting phase, 
	these feature vectors are then passed into the model in order to calculate each bot's probability of being
	a spy. From here, the bot has its own behaviour in the selection, voting, and sabotaging functions that
	make use of the probabilities calculated.
================================================================
The classifier model has already been trained, so to run the competition with neural_fredbot.py, enter the following
code into the terminal window (assuming that the contents of this folder are stored in the 'bots' directory):

python competition.py 1000 bots/intermediates.py bots/neural_fredbot.py
================================================================
In order to train the bot, first run 'fredbot.py' in the following way:

python competition.py 10000 bots/intermediates.py bots/fredbot.py

This will then create a log file in the logs folder of the framework. Replace the log file within this folder with
the new one, and then run the 'classifier_fredbot.ipynb' notebook to train the model. Afterwards, run 'neural_fredbot.py'
as described above.