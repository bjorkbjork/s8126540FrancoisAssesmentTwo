# s8126540FrancoisAssessmentTwo
An Android Mobile App developed for assessment at Victoria University

The API was provided by Victoria University. If you are experiencing timeout errors, or internal server errors, take it up with VU administration. Not me.

The API returns different key/value pairs based on which user has logged-in, however I have implemented dynamic JSON mapping with Moshi.

There were some edge cases that my adapter didn't handle very well, including one set of data accessed with the 'investments' keypass, which returned two floating point numbers, and would always throw a JSON Data exception.
I've simply implemented a check in the login to detect if this keypass is returned, display a warning message, and not allow the user to log-in.

UI formatting was taken either from my previous assignment (log-in screen), or from the recyclerview example provided by VU (dashboard and details screen) with minor modifications made.
All credit to original authors of the VU examples.

Feel free to do whatever you want with this codebase.
I will leave it up as apart of my portfolio, as I am proud of this project.

# API will likely be shut down once this class has completed

The code is still a good example of networking and UI design.

- Working First name / StudentID pairs can be access by any NIT3213 users through VU Collaborate.

# Setup Instructions:

- There isn't much setup work needed, if you download the repo and run it in Android Studio, it should run out of the box. 
- My student ID and name can be found in the title of this project, so feel free to test it with my name/id combo.
- If you have access to VU Collaborate, you can iterate through each name/id pair found.


# Enjoy
