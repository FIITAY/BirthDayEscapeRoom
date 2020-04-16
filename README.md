# BIRTH DAY PROJECT

## made an escape room at home using some CS

1. the first stage is the website:  
    built using html,css, js:
    * htmls - made the stractur of the each page.
    * css - using `bootsrap` made most of the styling to make everythin centered, and made option to make text blink using the `.blink` class.
    * js - made the answer checks.

2. the second stage is the box:  
    built using java, python and c:
    * java - made http server that make a site that writes ***"good job"***, and lunches the python code.
    * python -  connected to arduino via serial port using `pyserial` library and sent 1 to open the box and 0 to close the box.
    * c - made the arduino listen to the serial connection over `9600` and using the servo and the side of the box as the latch locked and open the box.
        1. if the arduino got `0` in the serial connection it would lock the box.
        2. if the arduino got `1` in the serial connection it would open the box.
