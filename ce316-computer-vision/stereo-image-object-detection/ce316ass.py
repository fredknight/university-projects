'''
--------------------------------------------------------------------------------
ce316ass.py
--------------------------------------------------------------------------------
PROGRAM PURPOSE:
The purpose of this program is to identify objects and their distance (in
meters) to the cameras in a 3-dimensional space from a set of stereo images.
This program should also be capable of identifying which objects are UFOs and
which objects are meteors by the way that they are travelling.

To calculate the distance from the cameras to an object, the following equation
will be used:

         f * B
    Z = -------
           D

where Z is the camera's focal length, B is the baseline (the distance between
the cameras), and D is the distance between the same object on the pair stereo
images. As we are getting the distance in real values using pixels, the
equation will have to be changed in order to use the right measurement:

               f * B
    Z = -------------------
         D * pixel spacing

The values for the focal length, baseline, and pixel spacing are provided in
the assignment brief:

    +-----------------------------------------------+
    | CAMERA DETAILS:                               |
    | ----------------------------------------------|
    | Focal Length of Cameras = 12m (meters)        |
    | Distance Between Cameras = 3.5km (kilometers)	|
    | Pixel Spacing = 10μm (microns/micrometers)    |
    +-----------------------------------------------+

The program identifies which objects are travelling in a straight line by
calculating the slope from one point in the objects path to another, and then
comparing that to a later slope. If the object's points are co-linear (on the
same straight line), then the ratios should not vary much. This uses the
equation:

     y2 - y1       y3 - y1
    ---------  =  ---------
     x2 - x1       x3 - x1

where (yn, xn) is the (y,x) point in an image for object 'n'. In this program,
the n values above refer to the following points:

    n = 1 = first recorded (y,x) point of object,
    n = 2 = selected (y,x) point of object,
    n = 3 = last recorded (y,x) point of object.

As we are measuring using pixels instead of real numbers, there must be a
slight leniency in declaring if the object does not fall in the straight line.

PROGRAM USAGE:
The template command line invocation of this program should be as follows:

    python3 ce316ass.py 50 frames/left-%3.3d.png frames/right-%3.3d.png

where the first argument (50) is the total number of pairs of frames (images)
taken by the cameras to process, and the last 2 argument are the file names for
the set of frames (left and right respectively). These last 2 arguments are
written in such a way that the current frame number being processed can be
substituted into the argument to get the correct filename (the 18th frame being
processed would be left-018.png).

PROGRAM RESTRICTIONS:
The program can only distinguish between a selected set of colours: black,
blue, cyan, green, orange, red, white, yellow. All other colours present will
be ignored.

The program identifies objects by iterating each image pixel-by-pixel. This is
not the most efficient way to do this, and results in the program taking
upwards of 2 minutes to finish. A more efficient way of implementing this would
have been to contour objects and save their positions in a list, before
either getting the average hue value of that contour, or passing the contours
through the pixel-by-pixel function to reduce the amount of pixels searched.

The program assumes that the filenames for the images follow that of the
template invocation above. An example filename for an image taken from the left
camera (specifically the 19th frame) must be named:

    left-018.png

This also assumes that the first frame is labeled as follows:

    left-000.png

AUTHOR:
Registration Number: 1804162
'''

#-------------------------------------------------------------------------------
#  program environment  :  indicating what interpreter to use for the program,
#                          importing required modules
#-------------------------------------------------------------------------------

#!/usr/bin/env python3
import cv2
import sys

#-------------------------------------------------------------------------------
#  detect_obj  :  Iterate through all image pixels, identify object colour and
#                 calculate midpoint.
#
#  Code Adapted From:
#  Email sent by Dr Adrian Clark on 07/03/2021, titled:
#  "Some hints for the Computer Vision assignment"
#-------------------------------------------------------------------------------

def detect_obj(frame_rgb):
    h, w, c = frame_rgb.shape
    low_x = w + 1
    high_x = -1
    low_y = h + 1
    high_y = -1
    points = {}

    # Iterate through every pixel in the image. However, this is not efficient
    # so is the cause for the slow process time.
    for y in range(h):
        for x in range(w):
            b, g, r = frame_rgb[y, x]

            # As the image background is black, iterate if black is found.
            if r == 0 and g == 0 and b == 0:
                continue

            elif r > 0 and g == 0 and b == 0:
                obj = "red"

            # As yellow and orange both match the below condition, so will need
            # to change the colour space from rgb to hsv to compare the hue
            # values.
            elif r > 0 and g > 0 and b == 0:
                frame_hsv = cv2.cvtColor(frame_rgb, cv2.COLOR_BGR2HSV)
                if frame_hsv[y, x][0] < 30:
                    obj = "orange"
                else:
                    obj = "yellow"

            elif r == 0 and g > 0 and b == 0:
                obj = "green"
            elif r == 0 and g > 0 and b > 0:
                obj = "cyan"
            elif r == 0 and g == 0 and b > 0:
                obj = "blue"
            elif r > 0 and g > 0 and b > 0:
                obj = "white"

            # If none of the above colours are identified, then iterate.
            else:
                continue

            check_xy(obj, points, x, low_x, high_x, y, low_y, high_y, frame_rgb)

    # Calculate the middle x and y values for the object stored within the
    # dictionary.
    for point in points:
        mid_x = (points[point][0] + points[point][1]) // 2
        points[point][2] = mid_x
        mid_y = (points[point][3] + points[point][4]) // 2
        points[point][5] = mid_y

    return points

#-------------------------------------------------------------------------------
#  check_xy  :  Adds object to a dictionary, finds furthest x and y points
#               of objects, deals with white/yellow center of objects
#
#  Code Adapted From:
#  Email sent by Dr Adrian Clark on 07/03/2021, titled:
#  "Some hints for the Computer Vision assignment"
#-------------------------------------------------------------------------------

def check_xy(obj, points, x, low_x, high_x, y, low_y, high_y, frame_rgb):

    # If the object has not previously been seen, add it to the dictionary with
    # placeholder values.
    if obj not in points:
        points[obj] = [low_x, high_x, 0, low_y, high_y, 0]

    # As objects contain a white dot as a center due to lighting (yellow dot
    # for orange object), check to make sure that these objects are on the edge.
    if obj == "white" or obj == "yellow":
        if x < points[obj][0]:
            b, g, r = frame_rgb[y, x - 1]
            if r == 0 and g == 0 and b == 0:
                points[obj][0] = x

        if x > points[obj][1]:
            b, g, r = frame_rgb[y, x + 1]
            if r == 0 and g == 0 and b == 0:
                points[obj][1] = x

        if y < points[obj][3]:
            b, g, r = frame_rgb[y - 1, x]
            if r == 0 and g == 0 and b == 0:
                points[obj][3] = y

        if y > points[obj][4]:
            b, g, r = frame_rgb[y + 1, x]
            if r == 0 and g == 0 and b == 0:
                points[obj][4] = y

    # For any other object, compare the x and y value of the pixel to that of
    # the current lowest/highest value.
    else:
        if x < points[obj][0]:
            points[obj][0] = x

        if x > points[obj][1]:
            points[obj][1] = x

        if y < points[obj][3]:
            points[obj][3] = y

        if y > points[obj][4]:
            points[obj][4] = y

#-------------------------------------------------------------------------------
#  calc_z  :  Calculates object distance
#-------------------------------------------------------------------------------

def calc_z(lx, rx):
    d = lx - rx

    # Convert the values provided into meters.
    f = 12
    b = 3500
    p_spacing = 0.00001

    z = (f * b) / (d * p_spacing)
    return z

#-------------------------------------------------------------------------------
#  find_ufo  :  compares the ratio of the change in y and x for the first half
#               of the points in the dictionary for that object, with the last
#               half (AB = AC (= BC)).
#
#  Math Adapted From:
#  https://www.urbanpro.com/gre/how-to-determine-if-points-are-collinear
#-------------------------------------------------------------------------------

def find_ufo(obj_pos, obj):
    # Selecting the first and third (y,x) points to compare.
    first_pos = obj_pos[obj][0]
    last_pos = obj_pos[obj][-1]

    # Selects the middle index value as the second (y,x) point to compare.
    middle_index = len(obj_pos[obj]) // 2
    middle_pos = obj_pos[obj][middle_index]

    # Calculate and store y2-y1, and x2-x1.
    first_mid_change_y = middle_pos[0] - first_pos[0]
    first_mid_change_x = middle_pos[1] - first_pos[1]

    # Calculate and store y3-y1, and x3-x1.
    first_last_change_y = last_pos[0] - first_pos[0]
    first_last_change_x = last_pos[1] - first_pos[1]

    # Make sure that no 0 divisions occur.
    if first_mid_change_x != 0 and first_last_change_x != 0:

        # (y2 - y1) / (x2 - x1)
        first_slope = first_mid_change_y / first_mid_change_x

        # (y3 - y1) / (x3 - x1)
        last_slope = first_last_change_y / first_last_change_x

        # If the absolute difference between the slopes is more than 0.5
        # (selected leniency), then the object is not travelling straight.
        return abs(first_slope - last_slope) > 0.5

    else:
        return False

#-------------------------------------------------------------------------------
#   main program  :   Reads and assigns variables using system arguments from
#                     the command line, calls functions to process images
#                     for the current frame, formats program output, assigns
#                     object positions to dictionary for 'find_ufo' function.
#-------------------------------------------------------------------------------

# Print the header of the output with formatting.
print('{:5} {:8} {:8}'.format('frame', 'identity', 'distance'))

pos_set = {}
ufo_list = []

# Reading the number of frames to read from the first system argument.
n_frames = int(sys.argv[1])

for frame in range(n_frames):
    # Read the images into OpenCV from the second and third system arguments.
    fn_left = cv2.imread(sys.argv[2] % frame)
    fn_right = cv2.imread(sys.argv[3] % frame)

    # Look through each image to get dictionaries of objects in each frame.
    left = detect_obj(fn_left)
    right = detect_obj(fn_right)

    # Combine all unique objects from both images into a single set to
    # reference.
    both = {**left, **right}
    both = sorted(both)

    # Iterate through each unique identified object that is within both left
    # and right frames.
    for key in both:
        if key in left and key in right:
            # Calculating lX and rX for distance calculation.
            left_x = left[key][2] - (fn_left.shape[0] // 2)
            right_x = right[key][2] - (fn_right.shape[0] // 2)

            # Calculate distance of that object, and print using the same
            # formatting as the headers.
            distance = calc_z(left_x, right_x)
            print("{:5} {:8} {:8.2e}".format(str(frame), key, distance))

            # If that object hasn't been stored in the dictionary of object
            # positions, create an array entry with middle y and x values.
            # Otherwise, append a new array to the dictionary key.
            if key not in pos_set:
                pos_set[key] = [[left[key][5], left[key][2]]]
            else:
                pos_set[key].append([left[key][5], left[key][2]])

# For every object in the set of positions, pass the dictionary and the object
# into the 'find_ufo' function. If true, the object is added to the list of
# UFOs.
for key in pos_set:
    if find_ufo(pos_set, key):
        ufo_list.append(key)
print("UFO: " + ' '.join(ufo_list))
