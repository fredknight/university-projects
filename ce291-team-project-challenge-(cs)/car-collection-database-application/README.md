# 'Car Collection Database Application'

This project was not given an official title, so I have decided to identify the project using the title '**Car Collection Database Application**'.

This project involves developing a Java  application which could properly communicate with a MySQL server. The interface for the project was created using Java Swing. This project was developed as part of the module's five-day '**Challenge Week**'.

The application had to be capable of searching through various ID's within the MySQL server's database, however did not require the functionality to further edit or search the database. As this was a self-guided project early on in the degree, the requirements were reasonably simple to achieve.

The aforementioned 'Challenge Week' consisted of completing the project, short daily update records, and a full demonstration of the work completed in the week. Because of this along with a lesser amount of experience in programming at this time, required the tasks to be completed to a good standard in a fast-paced environment. As the task was set late on the Monday, The first bit of progress in implementing the code for the project didn't occur until Wednesday (which is why the only videos that exist are from Wednesday, Thursday, and Friday).

Using Java Swing allowed this application to make use of a graphical user interface, which contributed to a higher grade at the end for the project. This also allows for easy expension, as elements can be easily reused when new functionality is introduced.

This project required good self-learning in a short amount of time, so although this isn't a necessarily impressive project from a code point of view, I am still proud of the work that went into implementing the required functionality in time to a good standard.

The purpose of the files and directories within this project are explained briefly below:

- '**lib**'

  - This directory contains the library dependencies associated with this project. As of now there is only one dependency for this project:

    - '**mysql-connector-j-8.0.31.jar**' - This dependency is a driver that provides MySQL connectivity for Java client programs. This particular version of the Java matches that of my current MySQL installation version ('**8.0.31**').

- '**src**'

  - This directory contains the source files associated with this project. Its contents are as follows:

    - '**data.sql**' - This file includes the SQL script, which can be used to instantiate a database using the command `source data.sql` within a MySQL shell.

    - '**TestConnect.java**' - This file includes the Java program to test the connection with the MySQL server. If a connection can properly be established, the program will simply print a list of '**Users**' stored within the database.

    - '**car_collection**' - This package contains the Java files that make up the full application. The main application method is found in '**CarCollectionApplication.java**.
	
- '**Wednesday.mp4**'

  - This video demonstrates that progress that I had made by Wednesday of the 'Challenge Week'.
  
- '**Thursday.mp4**'

  - This video demonstrates that progress that I had made by Thursday of the 'Challenge Week'.
  
- '**Friday.mp4**'

  - This video demonstrates that progress that I had made by Friday of the 'Challenge Week'.
	
NOTE: This project has been edited slightly, however this is only to change the connection address from that of the university's MySQL server to '**localhost**'. I have also redacted the user and password used. Currently this project supports MySQL version '**8.0.31**', however if compatibility issues are found with other versions then changing the library version within the '**lib**' directory should resolve this.

## Project Feedback

No feedback was provided. However, I was awarded a grade of **94%** on this project.
