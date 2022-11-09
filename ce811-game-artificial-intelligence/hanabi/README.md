# Hanabi

The aim of this project was to create an AI agent that was capable of performing well in the game '**Hanabi**'. The agent was to be based on any algorithm or technique covered in the module.

There would then need to be a full report on the development of the agent, covering the entire development process. A full background and literature review of the game, as well as the chosen algorithm would have to be completed to justify why the chosen algorithm would be suitable.

A full experimental study and performance analysis would then need to be completed, demonstrating reasoning behind every decision made in the behaviour of the agent, and documenting any issues, remedies, and discoveries during development.

An accompanying README text file was also to be included, providing instructions on how to use the submitted files.

The final agent was developed stochastic hill-climbing genetic algorithm, where the chromosomes for each generation would consist of a series of predefined rules. The performance of the agent would both be decided on the chosen sequence of rules, as well as the quality of the defined rules themselves.

The purpose of the files and directories within this project are explained briefly below:

- '**chromosome_evaluator.py**'

  - This file contains the stochastic hill-climbing algorithm, which will produce the final chromosome based on a combination of behavioural rules by the agent. This chromosome is then ran multiple times to get an average score, recording the average score for each generation and plotting these scores on a graph to show improvements between generations.

- '**rule_based_agent.py**'

  - This file contains the rule-based agent. It contains a series of different rules, however only uses a collection of rules in sequence that are provided by the '**chromosome_evaluator.py**' file.

- '**readme.txt**'

  - This file contains the original README file submitted as part of this submission. It contains an overview of the different files, as well as how to use the submitted files within the game engine.

- '**report.pdf**'

  - This file contains the background research and experimental study in relation to the devlopment of this project.

## The Game Engine

The provided game engine is a copy of Hanabi written in Python.

The '**ce811-hanabi-main.zip**' file contains all the files necessary to run the Hanabi game engine within Python. The files contain extremely basic behaviour, which are designed to be improved on and further developed by the end user.

The following are the provided steps required to get the game engine up and running:

1. Extract the contents of the '**ce811-hanabi-main.zip**' file.
2. Change to the main directory as shown here `..\ce811-hanabi-main`.
3. Run a simple game using the command `python run_game_simple.py`.

The project requires installation of the ['**hanabi-learning-environment**'](https://github.com/deepmind/hanabi-learning-environment) in order to run. This can be installed to your system using the command `pip install hanabi-learning-environment`.

NOTE: I had to work on this project within WSL, as I had issues with installing and using the environment within Windows. This issue did not appear to be present on any Linux systems I tried.

## Project Feedback

Below is the project feedback as it was presented to me. I was awarded a grade of **80%** on this project.

---

Score in mirror mode (my trial; 4-player version; 100 games): 15.79.

Method used: Chromosome interpreter has 8 new rules hardcoded. A SHC algorithm is implemented. Best chromosome uses most of the new rules created in prominent chromosome positions – indicating that the new rules have been helpful.

Final score is good (15.8). Need to take protection against random fluctuations in score tricking the GA.

Approximate Number of extra agent-rules introduced: 8.

Comments on your report: Excellent range of new rules and logic implemented. A simple improvement to make would be to increase the number of games used in each chromosome evaluation from 25, as this would protect better against random fluctuations which confuse the GA.

Experimental Study: A thorough experimental study section, with good use of graphs, showing how adding new rules made a difference. When presenting multiple runs, you should include some statistic measuring dispersion, e.g., standard deviation.

GENERAL FEEDBACK (given to all students):
In this assignment, the average Hanabi score by a bot submitted by students in CE811 was 15.2 and the standard deviation was 2.6. Scoring over 16 is really tough, so if your agent managed that, then well done. (Only 5 students managed this). Scoring over 15.5 is impressive, too.

A common piece of feedback that could be given to most students is that the randomness of scores in Hanabi can confuse the genetic algorithm quite a lot. For example, if you just repeatedly evaluate the same chromosome many times (using 25 games in each evaluation), then eventually it will appear to do well just by pure chance. This chromosome could then accidentally become your GA's 'best' chromosome, but in this case it's down to pure chance as opposed to quality of that chromosome. So, by repeatedly evaluating chromosomes, your GA sees a bias in the scores it sees - artificially inflating them to higher than they really are. Hence a lot of students seemed surprised when their best chromosome went through the automarker, and ended up with a lower score than the GA rated it at. But the auto-marker is giving a less biased estimate (and hence more accurate) estimate of the agent's true capability.  That's why the number of trials on the automarker was limited to just 2. I also re-evaluated the agents using more games than just 40 when I did the marking.

Hence ideally, you need to put some code into your GA to try to prevent against this bias. For example, you could raise the number of games used from 25 to 1000. This would help. Or you could make the best chromosome get re-evaluated occasionally, using a scheme you devise yourself, to try to cancel out any bias it has received.

Another trick might be to treat the evaluation of a chromosome as like a multi-armed bandit problem. Evaluating chromosomes is like pulling a bandit arm. The 'best' chromosome is the bandit with the highest UCB value, which you constantly keep updating. You'd have to keep track of a set of several of the most promising chromosomes seen so far and track the UCB scores for them all. That's a potentially interesting line of research to follow up on?
