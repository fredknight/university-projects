#  Design and Application of a Machine Learning System for a Practical Problem

The aim of this project is to be able to properly identify suitable machine learning techniques for a particular practical problem, and to be able to undertake a comparative evaluation of several machine learning procedures when apply to the aforementioned problem. The project is split into four main parts:

The first part of the project was to perform a brief pilot study on a given problem. This pilot study consisted of identifying the type of predictive task that had to be performed, providing examples of possible informative features that could be provided, learning procedures that I would deem suitable for the problem (with justifications), and how the system would be evaluated before deployment.

The second part of the project was to perform a comparative study of multiple machine learning proceadures, using a provided training dataset. For this, a Decision Tree classifier was required, along with at least 2 other procedures. After completion of the comparative study, the most suitable (and best performing) model was to be chosen in order to make predictions on a provided testing dataset.

The third part of the project was to perform a second comparative study of multiple machine learning proceadures, using another provided training dataset. For this, a Linear Regression procedure was required, along with at least 2 other procedures. After completion of the comparative study, the most suitable (and best performing) model was to be chosen in order to make predictions on a different provided testing dataset.

The fourth and final part of the project was to write a repor covering the comparative studies performed throughout the project. This was to include a brief summary of the experiments performed, followed by tables and figures to present any important numerical data. The rest of the report was to consist of an interpretation of the results, and how they show relative strengths and weaknesses of the used methods.

The purpose of the files within this project are explained briefly below:

- '**CE802_Pilot.pdf**'

  - This file contains the initial pilot study and analysis of the proposed problem in part one of the project.

- '**CE802_P2_Data.csv**'

  - This file contains the historical data for use with training in part two of the project.

- '**CE802_P2_Test.csv**'

  - This file contains the historical data for use with testing in part two of the project.

- '**CE802_P2_Notebook.csv**'

  - This file contains the models associated with the comparative study for part two of the project. This also contains the predictor code using the best discovered model architecture.

- '**CE802_P2_Test_Predictions.csv**'

  - This file contains the machine learning model predictions from the '**CE802_P2_Test.csv**' dataset.

- '**CE802_P3_Data.csv**'

  - This file contains the historical data for use with training in part three of the project.

- '**CE802_P3_Test.csv**'

  - This file contains the historical data for use with testing in part three of the project.

- '**CE802_P3_Notebook.csv**'

  - This file contains the models associated with the comparative study for part three of the project. This also contains the predictor code using the best discovered model architecture.

- '**CE802_P3_Test_Predictions.csv**'

  - This file contains the machine learning model predictions from the '**CE802_P3_Test.csv**' dataset.

- '**CE802_Report.pdf**'

  - This file contains the final report on the investigation for part four of the project. This investigation should consist of a brief summary of the results from both comparative studies, as well as a detailed interpretation of the results gained.

For this project, each student was provided a unique set of training and testing data for the comparative studies in parts two and three.

## Project Feedback

Below is the project feedback as it was presented to me. I was awarded a grade of **70%** on this project.

---

|General Comments|
|-|
|Overall, the report and the investigation are of very good quality.<br><br>Pilot study: Main comments / key action points to improve future work.<br>Consider that regression could also be used to predict profitability as revenue value. No mention of performance metrics.<br>Focus on n-fold cross-validation.<br><br>Comparative study: Main comments / key action points to improve future work.<br>Good job exploring different classifiers. Missing values well dealt with. Well use of grid search. Good use of pipelines. Only train/test split done, this is not enough. Cross validation was needed. For the final predictor, retraining with all the data would have been a better idea. More explanation between cells would have been good. No comments in the code. The code could have use some modularity. The test set submission achieved 0.902% accuracy (top submission: 0.91%; median 0.83%).<br><br>Additional comparative study: Main comments / key action points to improve future work.<br>Good job exploring different regression models. Missing values well dealt with and good imputation method. Well use of grid search. Good use of pipelines. Only train/test split done, this is not enough. Cross validation was needed. For the final predictor, retraining with all the data would have been a better idea. Nice personal additions to the process. There is no text between cells explaining what each one does. No comments in the code. The code could have use some modularity.<br>The test set submission achieved R²=0.723 (top submission: 0.909; median 0.5575).<br><br>Final report: Main comments / key action points to improve future work.<br>Quite good methods presentation and results.|

|Evalaution Breakdown|Quality|Weight|
|-|-|-|
|**Pilot Study**|||
|Correctness of identified type of predictive task|v|2|
|Validity of examples of possibly informative features|o|3|
|Appropriateness of learning procedure(s) suggested|o|3|
|Correctness of evaluation methods suggested|p|4|
|Overall clarity of presentation|e|4|
|**Comparative Study - Task A**|||
|Correctness and completeness of investigation|f|11|
|Quality of the code and comments|b|5|
|**Comparative Study - Task B**|||
|Accuracy of predictions|o|15|
|**Additional Comparative Study - Task A**|||
|Correctness and completeness of investigation|f|11|
|Quality of the code and comments|u|5|
|**Additional Comparative Study - Task B**|||
|Accuracy of predictions|e|15|
|**Report on the Investigation**|||
|Quality of presentation of the methods followed in part 2|v|4|
|Quality of presentation and discussion of results of part 2|v|4|
|Quality of presentation of the methods followed in part 3|e|4|
|Quality of presentation and discussion of results of part 3|v|4|
|Justification of conclusions drawn|v|4|
|**Others**|||
|Compliance with submission instructions|Yes|2|
|***Overall Mark***|**Very Good**||

*Key to quality assessment: poor, unsatisfactory, adequate, fair, good, very good, excellent, outstanding.
