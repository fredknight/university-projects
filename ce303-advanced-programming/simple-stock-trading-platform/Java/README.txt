Java Client-Server README
=========================

This is the README file for the Java versions of the
Client and Server programs. These programs are submitted
as IntellIJ projects; however the individual Java files can be found within
the "src" folder.

Using Java version 14.0.1.

How to compile and run: IntellIJ IDE
------------------------------------
1.	Load the Client and Server Projects in seperate windows.

2.	In the Client project go to "Run|Debug Configuration" and make sure
	“Allow running in parallel” is checked in the dialog box. This
	allows for multiple instances of the program to run in the same
	window instead of opening multiple Client projects

3.	Run the server project, and wait for the message "Waiting for
	incoming trader connections..." before running the Client 
	program(s).

4.	Follow the On-Screen prompts given.


How to compile and run: Command Prompt (Requires Java to be added to PATH)
------------------------------------
1.	Open up command prompt windows for the Server and Client program(s).

2.	Navigate to the directory containing the files:
	for the Server program: "..\Server".
	for the Server program: "..\Client".

3.	To run the Server program, use commands:
	"javac ServerProgram.java"
	"java ServerProgram"

3.	To run the Client program(s), use commands:
	"javac ClientProgram.java"
	"java ClientProgram"

4.	Follow the On-Screen prompts given.
	


NOTE: Only the current stockholder will have prompts; other traders must
      wait until the stock is transferred to them by the stockholder.