# 'Simple Stock Trading Platform'

This project was not given an official title, so I have decided to identify the project using the title '**Simple Stock Trading Platform**'.

This project involves developing a platform within Python and C# to trade a single 'stock'. The project uses a socket-based client-server architecture, where multiple instances of the client programs can connect and communicate within the server program. However, only the client that holds the 'stock' is able to perform actions within the program (for simplicity).

The files related to the client and server programs have been written in both **Java** and **C#**. This is to both demonstrate some slight differences between the way in which the languages work, as well as demonstrating that the project programs can work regardless of the language used.

A README.txt file is located in each of the language directories, which provides instructions on how to run each program.

A report was also required as part of this project. This report consisted of a 'checklist' to show what features had been implemented from the original guidelines, as well as a brief protocol investigation and project review (including a statement the usage of particular threads by each program).

The purpose of the files and directories within this project are explained briefly below:

- '**report.py**'

  - This file contains the full Python program, as well as an introductory comment including the required mathematics and algorithms used, and how to run the program from the command line. This file also contains the specifications and information provided on the cameras that would be necessary for calculating each objects' distance from each camera.

- '**Java**'

  - This directory contains the files associated with the **Java** implementation of the client and server programs:

    - '**README.txt**' - This file includes instructions on running the **Java** implementations of the programs.

    - '**Client**' - This directory includes the files associated with the client program.

    - '**Server**' - This directory includes the files associated with the server program.

- '**C#**'

  - This directory contains the files associated with the **C#** implementation of the client and server programs:

    - '**README.txt**' - This file includes instructions on running the **C#** implementations of the programs.

    - '**Client**' - This directory includes the files associated with the client program.

    - '**Server**' - This directory includes the files associated with the server program.

## Project Feedback

Below is the project feedback as it was presented to me. I was awarded a grade of **94%** on this project.

---

### Report

The central part of the architecture of the client and the server applications in this project is how threads are used. You did well explaining this aspect of the
project. Your project review is insightful.

### Client-Server Architecture

Your client-server architecture in Java/C# is as expected.

### Server Side

The core market logic in both C# and Java is correctly implemented.

Your server user interface in both C# and Java exceeds the expectations, although it does not meet some basic requirements.

### Client Side

The client user interface in both C# and Java satisfies all the main criteria.

The quality of the user interface in both C# and Java exceeds expectations.

### Other

You invested a lot of thought into the protocol – well done.

You have produced a quality code.

You have made your implementations compatible with each other.

Server doesnt appropriately show the stock moves. The clients do not refresh themsleves automatically.
