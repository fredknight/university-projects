C# Client-Server README
=========================

This is the README file for the C# versions of the
Client and Server programs. These programs are submitted
as VSCode projects; however the individual C# files can be found within
the main project "Server" or "Client" folders.

Using netcoreapp3.1

How to compile and run: VSCode IDE
------------------------------------
1.	Load the Client and Server Projects in seperate windows.

2.	Run the server project by going to "Terminal|New Terminal" and
	entering the command:
	"dotnet run"

3.	Wait for the message "Waiting for incoming trader connections..."
	before repeating step 2 with the Client program(s). Repeating step
	2 in a single window allows you to select from a list of multiple
	active Client processes, instead of opening multiple Client
	program windows.

4.	Follow the On-Screen prompts given.


NOTE: Only the current stockholder will have prompts; other traders must
      wait until the stock is transferred to them by the stockholder.