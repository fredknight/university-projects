# Multi-Layer Perceptron (Individual Assignment)

The aim of this project was to develop a feed-forward neural network with backwards-propagation within Python.

In order to demonstrate a full understanding of the topic, this neural network was to be developed completely from Scratch. This project was given a restriction on using any external packages or modules, only allowing those that are present within The Python Standard Library. This restriction also extended to the preprocessing of the collected data.

This required understanding of the underlying mathematics and algorithms present within the neural network in order to be successful, as well as a good understanding of suitable preprocessing techniques.

Once the model was trained, it was integrated into a game (which was also used to collect the training data), acting as the test harness. The weights from the trained model were used on the inputs from the game state in order to produce consistently good outputs.

The purpose of the files within this project are explained briefly below:

- '**preprocessing.py**'

  - This file contains the various preprocessing steps on the original exported data within '**ce889_dataCollection.csv**'.
  
  - The generated file from this is '**ce889_dataCollection_preprocessed.csv**'.

- '**neuron.py**'

  - This file contains the '**Neuron**' class, which is used to instantiate new neurons for the neural network. It contains some neuron-specific variables, and performs some simple methods to calculate correct values for these variables.

- '**neural_network.py**'

  - This file contains the majority of the algorithms and mathematics for the network, and contains the main loop used to train the network. For training data, this file reads from the preprocessed data within '**ce889_dataCollection_preprocessed.csv**'.

  - Once the model is trained, this file has the following outputs:
  
    - '**weights.csv**' - The exported neuron weights. These should be copied into the '**NeuralNetHolder**' as part of the game integration.

    - '**rmse.csv**' - The RMSE performance for the training and validation of each epoch. These were used to create graphs for the project's demonstration.

    - '**test.csv**' - The generated log from testing the neural network.

- '**NeuralNetHolder.py**'

  - This file contains the code to integrate the trained neural network weights with the provided game (instructions on integrating this are provided).
  
- '**group_presentation.pptx**'

  - This file contains the slides used during my presentation that accompanied my demonstration. It provides an overview of the development process, an explanation of the main functions, and the key algorithms and components that form the neural network.

## The Game

The provided game is a simple copy of a Lunar Lander style game, where the objective to land on the goal with points being awarded based on the quality of the landing.

The '**ce889assignment.zip**' file contains all the files necessary to run the game in Windows (a program such as **dos2unix** will need to be used to convert line endings for this to work within a Unix system).

The following are the provided steps required to get the game up and running:

1. Extract the contents of the '**ce889assignment.zip**' file.
2. Change to the '**Scripts**' directory as shown below.
`..\ce889assignment\venv\Scripts`.
3. Activate the virtual environment using the command `.\activate`.
4. Return to the main directory using the command `cd ..\..\`.
5. Install the requirements for the game using the command `pip install -r .\requirements.txt`.
6. Run the game using the command `python .\Main.py`.

Once in the game, you will be prompted with multiple options in which to play:

- Play Game

  - This is the basic game mode. No scores are kept and data is not saved; this mode is simply for practicing before data is collected.

- Data Collection
  
  - This is the game mode where data is collected based on how the player performs. This data will be exported to a CSV file to be used to train the neural network model, so a consistently good game performance is required. 
  - The exported data can be found in the file '**ce889_dataCollection.csv**'.

- Neural Network

  - This is the game mode that uses the neural network model as the controller, based on the trained weights of the network.
  - In order to use this mode, the '**NeuralNetHolder.py**' must be altered to integrate the trained neural network's weights.

## Project Feedback

Below is the project feedback as it was presented to me. As this project contributed to **50%** of the overall module's coursework, the total marks should be doubled to give the project's relative grade (being **67%**).

---

|Comments|Marks|
|-|-|
|Good data split.|2/2&nbsp;Data Partition|
|Good data processing, but you could have done more by looking at the data trends.|3/4&nbsp;Data Processing|
||4/4&nbsp;Architecture Design|
|Good size comparison but you needed to do more than just the even numbers.|3/4&nbsp;Size Comparison|
|You need to do more a complex parameter search, the numbers seem a little low.|3/5&nbsp;Parameter Justification|
||6/6&nbsp;Feed Forward|
||1/1&nbsp;Error Calculation|
||5/6&nbsp;Back propagation|
|Your RMSE is not implemented correctly|0/1&nbsp;RMSE Training|
||0/1&nbsp;RMSE Validation|
|No early stopping criteria|0/2&nbsp;Stopping Criteria|
|Good game integration.|2/2&nbsp;Game Integration|
||1/6&nbsp;Game Performance|
|Very good comments in your code.<br>Shame about that error in the for loop in weight_update().|3.5/4&nbsp;OOP/Comments|
||0/2&nbsp;Innovation|
||**Total Marks**<br>33.5/50|
