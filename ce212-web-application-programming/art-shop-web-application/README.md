# 'Art Shop Web Application'

This project was not given an official title, so I have decided to identify the project using the title '**Art Shop Web Application**'.

This project involves developing a web application for an online art shop. The web application utilizes a HSQLDB relational database, and a local Tomcat server in order to use JavaServer Pages to form the backend of the web application.

This is one of if not the most enjoyable projects I had completed. It made use of several languages (**Java**, **JSP**, **HTML**, **JS**, **CSS**, **SQL**), allowing me a fair amount of freedom in which to express myself in terms of the base functionality and appearance of the web application. This was however the most frustrating project to set up and get working, as at the time I had not previously attempted anything such as this.

The project was given five main tasks for completion, as well as the general task of developing a good web application with the provided material.

The first task was to implement the basic store. This consisted of providing functionality for the user to browse products, view them, and then add them to a basket, before being able to check out and have the order recorded in the database.

The second task was to improve the appearance and functionality of the basket, changing the display to a tabular format, and adding more complex information to the items. This consisted of displaying a thumbnail of the product, as well as correctly adjusting the quanity and price of the item without just creating duplicate entries.

The third task was to ensure that the basket page refreshes correctly, with products remaining in the basket and stopping new items being added each time the page is refreshed.

The fourth task was to improve the appearance and usability of the shop. This consisted of providing a consistent page layout using a common header, and providing better navigation.

The fifth and final task was to add a way for the user to search for products, by either the artist's name or by a specified price range (implementation of two separate filters).

The project files within this repository are contained within an IntelliJ IDEA project (using '**IntelliJ IDEA Ultimate**'), as this was used at the time in order to properly set up the web application environment. I have not yet got a version of this working as a standalone web application within Tomcat, so cannot properly separate the files and have the project easily work on a different system. Once I have managed to have this project run outside of IntelliJ, I will update this README.md to provide a clearer explanation of each of the directories and files  within the project. However, for now the project is laid out in such a way that provides some level of self-explanation to the roles of the files, without this README.md becoming too cluttered.

The only external dependency that this project uses is a copy of '**hsqldb.jar**' This dependency is a driver that provides HSQLDB connectivity to a database. This particular version of the HSQLDB library is version '**2.7.1**'.
	
NOTE: This project has been edited slightly, however this is only to change the path of the database to what is stored within my PC (this is the **full path** to the '**shopdb**' files at the root of the project). The provided database requires a username of '**sa**' and a blank password. This project uses **Java 11** as I was running into some issues using **Java 8**. Currently this project supports HSQLDB version '**2.7.1**' and Tomcat version '**9.0.31**', however if compatibility issues are found with other versions then changing the HSQLDB library version within the '**lib**' directory, or changing the run configuration to a different Tomcat version may resolve this. As of now, the Tomcat server should be run from inside of IntelliJ.

## Project Feedback

Below is the project feedback as it was presented to me. I was awarded a grade of **82%** on this project.

As a note on the final comment about the CSS and JS file structure, the submission for this project involved submitting the separate files in their own directories, so a README.txt file was included to clarify where the directories should be placed. This is not necessary for the project currently within this repository, so this text file has not been included.

---

|Comments|
|-|
|**Part 1**<br><br>Fine; all requirements met.|
|**Part 2**<br><br>Error in addItem; when the item being added is a copy of one that already present increaseQuant is made to refer to the last item in the keyset, not the item being added.<br>Otherwise basket is fine.<br>Should add equals and hasCode methods to the Product class to allow the full search functionality of HashMap to be used; the complex code in addItem would then not be needed.|
|**Part 3**<br><br>Fixed.|
|**Part 4**<br><br>Good use of JSP header, good navigation facilities.<br>Should add some larger images, not just poor quality enlargements of the thumbnails.<br>Need to add some product descriptions.|
|**Part 5**<br><br>Artist search should find only exact matches, not substrings.<br>Need to tell user what to enter for price-range filter, i.e. pounds and pence.<br>Should output a message if a search produces no results.<br>Have to use a prepared statement to query the database for artist search (it doesn’t matter for price range search since the use of parseDouble ensures any SQL injection attempts will be rejected).|

(Your CSS and JS file structure was what I was expecting so the readme file wasn’t needed; due to the unusual circumstances I’ve been flexible with the submission structure and not been applying penalties this year for files in wrong folders anyway)