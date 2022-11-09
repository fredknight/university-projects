# 'Stereo Image Object Detection'

This project was not given an official title, so I have decided to identify the project using the title '**Stereo Image Object Detection**'.

This project involves developing a Python program to detect objects and monitor their movement using the ['**OpenCV**'](https://opencv.org/) computer vision library. The project involved using frames of the same scene taken from two separate cameras, and using the provided camera specifications in order to properly monitor the three-dimensional position of objects within the scene.

The project was given a 'fun' context, with the purpose being to distinguish meteors from UFOs in order to prevent an alien invasion, based on nothing but the way that they move between frames. If an object's trajectory was collinear (travelling in a straight line), then they were identified as a meteor, otherwise they would be identified as a UFO. The objects within the frame are of different colours, as this allows the program to easily distinguish objects from one another based on their colour values.

The frames provided are comprised of images from two different cameras, pointing at the same scene. The details on the position of the cameras from one another, as well as the specifications of each camera have also been provided to allow us to accurately record the positions of each object within a three-dimensional space.

The purpose of the files and directories within this project are explained briefly below:

- '**ce316ass.py**'

  - This file contains the full Python program, as well as an introductory comment including the required mathematics and algorithms used, and how to run the program from the command line. This file also contains the specifications and information provided on the cameras that would be necessary for calculating each objects' distance from each camera.

- '**frames**'

  - This directory contains the provided scene frames from the left and right cameras.

## Project Feedback

Below is the project feedback as it was presented to me. I was awarded a grade of **72%** on this project.

---

### Presentation: 20/25

The #! line must be the very first line of the file. The appearance of your code was generally fine. Some of your lines were longer than the 80-character limit. The introductory comments at the top of your program were good. The comments interspersed with your code made a good job of explaining how the code works and what individual sections of it are meant to do.

### Algorithms: 15/25

You identify the various targets by colour, which is the most sensible way. However, you convert the entire image from RGB to HSV for each yellow or orange pixel encountered in each image, which is wasteful of time. Why didn’t you just do that conversion outside the loop over the pixels? Also, you don’t produce a warning message if none of the known colours are found (line 156), so how do you know important pixels are not being missed? Your code for determining the distance to the meteors looks sensible. You were able to track the motion of objects across the frames, the trickiest part of the assignment. You compare gradients of line segments of the trajectory to determine whether or not motion is collinear and hence which objects are UFOs. This is fine.

### Coding style: 15/25

You have nicely separated some of the code out into separate routines, which makes the program as a whole easier to read. There are a few special cases to handle specific object colours, which reduces the generality of your software. With a little more effort, you would have been able to program around them.

### Results: 22/25

Your program works, correctly distinguishing the alien spacecraft from the meteors, and your missiles have saved the human race from what seemed like certain destruction. The entire 10 billion people on Earth breathe a collective sigh of relief and you are awarded the Galactic Order of Merit for your achievements ;-).<br><br>Your submission works well with the test harness. Your program also runs with my unseen test sequence.
