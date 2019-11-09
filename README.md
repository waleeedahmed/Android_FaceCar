<h1><strong>FaceCar Android Application</strong></h1>

Hello and welcome to FaceCar! :heart:

This is an android application that I built while supervising a team of four at my university and the project that I'm super
proud of. 

This application was built to address the security concerns of vehicle unlocking using keys which can be unsafe. 
FaceCar is an attempt to solve the above problem using facial recognition to authenticate a vehicle owner's face. 
Upon successfull authentication, the app sends an unlock signal to a networked Raspberry PI (RPi) which simulates vehicle
access/denial in the form of green/red LEDs respectively. 

FaceCar uses a Machine Learning algorithm called EigenFaces. We have successfully performed these facial recognition tests
in a variety of environments and different image resolutions before shipping finalizing our product. 

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

This section provided an animated tour of FaceCar and its functionalities. All images are taken in landscape mode since portrait
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


![](https://github.com/waleeedahmed/Android_FaceCar/blob/master/app/fc4.gif)
<br><br>
Above shows the failure screen when you try using FaceCar to Scan an unknown face that has not been registered. 
<br><br><br><br><br><br>


![](https://github.com/waleeedahmed/Android_FaceCar/blob/master/app/fc5.gif)
<br><br>
Finally, we clear user data in the face dataset. As shown, face dataset gets emptied.


<h1><strong>Built With</strong></h1>

- <a href = "https://opencv.org/">OpenCV Library</a>


<h1><strong>Acknowledgements</strong></h1>

A huge thank you goes to <a href = "https://github.com/saudet">Samuel Audet</a> for creating OpenCv android libraries containing
facial detection and recognition algorithms that we were able to tweak and use in our android appliction. 






