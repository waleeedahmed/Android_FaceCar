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

<h1><strong>Built With</strong></h1>

- <a href = "https://opencv.org/">OpenCV Library</a>


<h1><strong>Acknowledgements</strong></h1>

A huge thank you goes to <a href = "https://github.com/saudet">Samuel Audet</a> for creating OpenCv android libraries containing
facial detection and recognition algorithms that we were able to tweak and use in our android appliction. 






