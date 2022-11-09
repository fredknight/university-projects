This folder contains the rule-based agent, and the chromosome evaluator used to determine each
chromosome's performance. The files present are as follows:

rule_based_agent.py:
	This file contains the rule-based agent to use; stored within the class 'MyAgent()'. It uses a chromosome (set of rules) 
	that had been produced using the chromosome evaluator.

chromosome_evaluator.py:
	This file contains the stochastic hill climber algorithm, which produces the final chromosome using a stochastic hill climbing 
	algorithm. The resulting chromosome is then run multiple times to get an average expected score, and graphs of the generation 
	scores are plotted and saved.
================================================================
The agent should be run within an episode of the Hanabi game. This should be done by importing the file as an object, which agents can
then be created from. Running this file on its own has no function, and will not return an output.
================================================================
In order run the chromosome evaluator, run the file 'chromosome_evaluator.py' by entering:

python3 chromosome_evaluator.py

This is assuming that you are executing this python file from within its directory.