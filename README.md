<h1><strong>FaceCar Android Application</strong></h1>

Hello and welcome to FaceCar! :heart:

This is an Android application that I built while supervising a team of four at my university and the project that I'm super
proud of. 

This application was built to address the security concerns of vehicle unlocking using keys which can be unsafe. 
FaceCar is an attempt to solve the above problem using facial recognition to authenticate a vehicle owner's face. 
Upon successfull authentication, the app sends an unlock signal to a networked Raspberry PI (RPi) which simulates vehicle
access/denial in the form of green/red LEDs respectively. 

FaceCar uses a Machine Learning algorithm called EigenFaces. We have successfully performed these facial recognition tests
in a variety of environments and different image resolutions before finalizing our product shipping. 

FaceCar uses Websockets to communicate with RPi. The concept is fit the RPi in an actual vehicle to perform remote unlocking or
locking. 

We used OpenCv (Open source computer vision library) to first detect a face in a mobile camera canvas and the detected face 
is passed onto Face Recognizer to for training or recognition purposes. According to our tests, FaceCar is achieving an 
80% accuracy in recognition. 


<h1><strong>Getting Started</strong></h1>

<h2><strong>Prerequisites</strong></h2>

- Requires Android Studio to compile and produce a .apk of the App
- An Android phone with minimum Android 4 version (api level 15)
- A Raspberry PI with a breadboard circuit connecting with LEDs and a buzzer


<h1><strong>Guided Tour</strong></h1>

This section provides an animated tour of FaceCar and its functionalities. All images are taken in landscape mode since portrait
mode requires rotating the images which affects face recognitiion capability. 
<br><br>

![](https://github.com/waleeedahmed/Android_FaceCar/blob/master/app/fc1.gif)
<br><br>
Animation above demonstrates the first point of initiating FaceCar app. As you can see, we also designed a proper logo 
for the app. Upon entering FaceCar for the first time, it is coded to ask you to provide access to your files, storage and 
photos. Click accept and the homescreen comes into view. 
The homescreen has three functionalities. Registering the user's face, performing a scan on a user to determine authenticity, and
finally clearing user data. 
<br><br><br><br><br><br> 


![](https://github.com/waleeedahmed/Android_FaceCar/blob/master/app/fc2.gif)
<br><br>
The process above shows the first of the three functions; "Register". User has to register their face and on the "Success" screen,
press the "New photo" button. The more photos you take, the better face recognition the Machine Learning algorithm will perform.
Once desired number of photos taken, press "Train Model". Button text will change to "Trained!" once Machine Learning model builds
a .yml config file of all faces in the face dataset.
<br><br><br><br><br><br>


![](https://github.com/waleeedahmed/Android_FaceCar/blob/master/app/fc3.gif)
<br><br>
Above shows the second function  of the app "Begin Scan". Since my face data is present in the dataset, it confirms a face found. 
At the confirmation screen, Raspberry PI blinks green LED signalling success. 
Note: At the bottom of the success screen, you can see an image label number. This number corresponds to a unique label id for each
user that is registered. Prediction value is a measure of how probable the algorithm thinks the image label to be true. Low values
signify high confidence in given image label and high values signify low confidence.    
<br><br><br><br><br><br>


![](https://github.com/waleeedahmed/Android_FaceCar/blob/master/app/fc4.1.gif)
<br><br>
Above shows the failure screen when you try using FaceCar to Scan an unknown face that has not been registered. 
<br><br><br><br><br><br>


![](https://github.com/waleeedahmed/Android_FaceCar/blob/master/app/fc5.gif)
<br><br>
Finally, we clear user data in the face dataset. As shown, face dataset gets emptied.
<br><br><br>


<h1><strong>Raspberry Pi</strong></h1>
  
  <h2><strong>Requirements for Raspberry Pi</strong></h2>
  
- x1 Raspberry Pi 3 Model B+
- x6 Male to Female Jumper Wires (5 Reds, 1 Black)
- x2 Male to Male Jumper Wires (2 Blacks)
- x3 LEDs (Red, Yellow & Green)
- x3 220 Ohms Resistors
- x2 Buzzers (Access and Denial)
- tornado_ws_wgpio.py

Setting up Wireless hotspot
-	https://www.raspberrypi.org/documentation/configuration/wireless/access-point.md

Setting up .py file to run on startup
-	https://www.raspberrypi.org/documentation/linux/usage/rc-local.md
<br><br><br>

![](https://github.com/mtamtran/Android_FaceCar/blob/master/app/RPI.png)

<br>
A design of the RPi and each components have been setup in the picture above.
If you are planning on changing the layout of the GPIOs on the RPi, be sure to change bits of the code in tornado_ws_wgpio.py. Numbers represent each pin connected on the RPi.

```
# Change these values to your requirements
# buzzerLow is Deny Access. buzzerHigh is Allow Access.
buzzerLow = 21
buzzerHigh = 26
red = 12
yellow = 16
green = 20
```

Once all above have been setup correctly, Turn on (or restart if it is already on) the RPi, connect the Android device to RPi hotspot and use FaceCar.




<br><br>

<h1><strong>Built With</strong></h1>

- <a href = "https://opencv.org/">OpenCV Library</a>

<h1><strong>Team Members</strong></h1>

- <a href = "https://github.com/mtamtran">Tam Tran</a>
- <a href = "https://github.com/ryansinnott1991">Ryan Sinnott</a>
- <a href = "https://www.linkedin.com/in/joseph-nguyen-tran-594a80174/">Joseph Nguyen</a>
- Giselle Calinga


<h1><strong>Acknowledgements</strong></h1>

A huge thank you goes to <a href = "https://github.com/saudet">Samuel Audet</a> for creating OpenCV Android libraries containing
facial detection and recognition algorithms that we were able to tweak and use in our Android application :heart: 






