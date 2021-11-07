
![Logo](https://upload.wikimedia.org/wikipedia/commons/f/f4/Logo_EPFL.svg)

# EPFL IC BA1 MP1
This project permits the comparison of fingerprints

## Notes
- During the development of the project, we found out we should use m2 as a rotation center in the match() function instead of m1.
Therefore we used m2 as a rotation center in our match() function.
<br>
- We improved the Main file by adding convenient debugging functions and tests to make sure that all our functions work properly.

## Bonus

We tried to implement preprocessing of the images in resources/original_fingerprint.
Therefore we wrote the method preProcessImages():

- input: path to the original_fingerprints 
- job : analyses the colour pixel by pixel and transforms it into a black or white pixel using an [Otsu's method](https://en.wikipedia.org/wiki/Otsu%27s_method) - like algorithm.

We also set up a Java CI Github action to automatically build and run our project after each commit to ensure that we kept our results consistent as we went along.

## Authors

- [@Arthur Wolf](https://www.github.com/arthur-wolf)
- [@Arthur Bigot](https://www.github.com/MisteFr)


## Resources

The resources provided for the project are available here:
- [Project's Description](https://proginsc.epfl.ch/wwwhiver/mini-projet1/descriptif.html)
- [Project's Guidelines](https://proginsc.epfl.ch/wwwhiver/mini-projet1/fingerprint.pdf)

## Github repository
- [Project's repository](https://github.com/arthur-wolf/ba1-mp1)