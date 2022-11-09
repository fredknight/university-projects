# Deep Learning (Group Project)

The aim of this project was to develop a deep learning neural network for the ['**Rossman Store Sales**'](https://www.kaggle.com/c/rossmann-store-sales) Kaggle competition. The objective is to train a model capable of forecasting the '**Sales**' data in the '**test.csv**' file.

This project was completed as a team, where we would all aim to contribute to each part of the development process, ensuring that every member understood the full workings of the neural network.

The final model was based on a **recurrent neural network** (specifically a **long short-term memory neural network**), due to the data being of time-series. This allowed us to properly process sequencial data in order to forecast future data.

The purpose of the files within this project are explained briefly below:

- '**train.csv**'

  - This file contains historical store data that is used in order to train the neural network. This file contains the '**Sales**' column.

- '**test.csv**'

  - This file contains historical store data that is used in order to test the neural network. This file does not contain the '**Sales**' column.

- '**store.csv**'

  - This file contains supplimentary data on the different stores within the training and testing data.

- '**sample_submission.csv**'

  - This file contains a sample layout for how the submission should be submitted.

- '**lstm.ipynb**'

  - This file contains neural network architecture, and the data preprocessing steps. A Jupyter Notebook is used as this allows for outputs to be seen in blocks, allowing for easier visualisation and debugging.

- '**submission.csv**'

  - This file contains the competition submission. It contains the predicted '**Sales**' column values that are missing from the '**test.csv**' file.

- '**group_presentation.pptx**'

  - This file contains the slides used during our presentation of our deep neural network. It provides an overview of the model, including key preprocessing steps, architecture design choices, and experimentation. It also contains the main contributions for each team member.

A detailed explanation of data fields within the provided files can be found on the ['**Rossman Store Sales**'](https://www.kaggle.com/c/rossmann-store-sales) Kaggle competition's page

## Project Feedback

Below is the project feedback as it was presented to me. As this project contributed to **50%** of the overall module's coursework, the total marks should be doubled to give the project's relative grade (being **80%**).

Whilst this project was a group project, each member recieved their own individual feedback. To my knowledge, the only difference between members' feedback was in the '**Individual Contribution**' marks.

---

|Comments|Marks|
|-|-|
|One hot encoding for categorical data. Sorted by dates but then removed. Day of the week is also one hot encoded. Good preprocessing.|7/10&nbsp;Preprocessing|
||5/5&nbsp;Preprocessing Implementation|
|LSTM architecture used, because they are the state of the art for time series. Data is visualised as time series, trying to use past data to predict sales. 3 LSTM layers. Great deep learning model implementation.|8/10&nbsp;Architecture|
||5/5&nbsp;Architecture Implementation|
|Multiple Kaggle scores for different architectures. This is great!<br><br>Best score is 0.396|5/10&nbsp;Score|
||10/10&nbsp;Individual Contribution|
||**Total Marks**<br>40/50|
