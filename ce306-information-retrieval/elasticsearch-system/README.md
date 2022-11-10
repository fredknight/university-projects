# 'Elasticsearch System'

This project was not given an official title, so I have decided to identify the project using the title '**Elasticsearch System**'.

This project involves developing a Python program to search from a collection of movie plots, using the ['**Elasticsearch Python Client**'](https://github.com/elastic/elasticsearch-py). The collection of movie plots used for this project can be found in the ['**Wikipedia Movie Plots**'](https://www.kaggle.com/jrobischon/wikipedia-movie-plots?select=wiki_movie_plots_deduped.csv) page on Kaggle.

A ['**Kibana**'](https://www.elastic.co/kibana/) client was also used in order to test that the system was performing correctly, and to compare the results with that from the standalone system. Instructions on getting Elasticsearch and Kibana up and running are also located within the file '**report.pdf**'.

Along with the system, a brief report on the separate parts of the system was also required. The report was also to include instructions to install and run the system, as well as any dependencies that are required (as previously mentioned).

The purpose of the files within this project are explained briefly below:

- '**wiki_movie_plots_deduped.csv**'

  - This file contains the collection of movie plots and information.

- '**elasticsearch-system.py**'

  - This file contains the full Elasticsearch system, with the command-line interface being disabled by default. If the user wishes to use this as a standalone system rather than with Kibana, then they just need to uncomment the function call.

- '**report.pdf**'

  - This file contains the accompanying report to the developed system. It also contains setup and usage instructions, as well as the required dependencies.

A detailed explanation of data fields within the movie plot database can be found on the ['**Wikipedia Movie Plots**'](https://www.kaggle.com/jrobischon/wikipedia-movie-plots?select=wiki_movie_plots_deduped.csv) page on Kaggle.

NOTE: This project had to be developed within a Linux system, due to recieving some errors on my Windows system.

## Project Feedback

Below is the project feedback as it was presented to me. I was awarded a grade of **77%** on this project.

The feedback within the '**Engineering a Complete System**' section is incorrect. This was a mistake made by the marker, which was resolved during a meeting over Zoom. The system uses its own command-line interface, allowing it to function as a standalone system.

---

|Section|Feedback|
|-|-|
|**Indexing**|Did the code work? How can a reader know whether the code is correct, e.g., screenshots could have helped.|
|**Sentence Splitting, Tokenization, and Normalization**|Your work is either partially presented or lacks correct results. Your code works with the indexed document but might miss the proof in screenshots or outputs, or output is partially correct such as there still being punctuation/capital letters or lack of explanation on the pre-processing process.|
|**Selecting Keywords**|You have used an ELK analyser (Standard tokenizer with stop word removal and scripted tf-idf). A better approach to do the assignment is a preprocessing pipeline using suitable programming language and indexing and searching through the ES analyzer. Moreover, by using an ES analyzer, you can not select key phrases. A fingerprint analyzer is one of the possible options. However, it is not a good approach to selecting key phrases. A better approach to select phrases and keywords are n-gram, skip-gram, rake, POS tagging along with parser (to identify phrases), and others (using Python or other programming languages). For an ES analyzer you can check this [link](https://www.elastic.co/blog/phrase-Queries-a-world-without-stopwords).|
|**Stemming or Morphology Analysis**|Your work is either partially presented or lacks correct results. Your code works with the indexed document but might miss the proof in screenshots or lacks explanation of the task.|
|**Searching**|Searching in multiple fields has been done properly.|
|**Engineering a Complete System**|Mostly done in Kibana with some code implemented in an external programming language, not a complete system.|
|**Clarity of the program and its commenting**|Some comments within code, more detail needed for a clearer understanding.|
|**Documentation**|Excellent report presentation.|
