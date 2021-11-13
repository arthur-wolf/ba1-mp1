
![Logo](https://upload.wikimedia.org/wikipedia/commons/f/f4/Logo_EPFL.svg)

# EPFL IC BA1 MP1
This project permits the comparison of fingerprints

## Notes
- During the development of the project, we found out we should use m2 as a rotation center in the match() function instead of m1.
Therefore we used m2 as a rotation center in our match() function.
- We improved the Main file by adding convenient debugging functions and tests to make sure that all our functions work properly.

## Bonus

### Multithreading
We have used multithreading in order to improve how fast the program was running. This has been done in Main.java.
We used the method "IntStream.range(1, 16).parallel().forEach(y -> testAllCombinationsOneFinger(y))" to test all combinations for
one finger using one thread per finger to compare with.

This has greatly improved the run time. Indeed, we have been able to test 1/16th of all possible combinations
in only 10 minutes (approximately).

### Outputting data and parsing for statistics
We thought it would be great to know what percentage of our results were accurate (or not). In order to do this, we created the writeOutput()
method that outputs the results that we can usually find in the terminal into an output.txt file.

This file serves as an input for a python script we have written, that parses the output.txt file and outputs
how many comparisons have the good result, and how many false positives / false negatives have been found among all the comparisons.
you can find this script under the name main.py.

### Image Pre-Processing
We were also interested in pre-processing images. We first tried a dumb approach by changing the colour of each pixel to black or white depending on whether it was closer to black or white.
The code written to achieve that can be found in the method preProcessImages() in our Main file.  
We realised that this approach was not suitable here, as there are holes in the lines, the image may be deteriorated in places. Therefore we went for more general research and came across the following research paper: [Fingerprint Image Enhancement: Algorithm and Performance Evaluation](https://ieeexplore.ieee.org/document/709565).  
Using this paper we were able to break down the image preprocessing into 5 steps:
- Normalization
- Orientation Image Estimation
- Frequency Image Estimation
- Region Mask Generation
- Filtering    
      
We had a try at implementing image normalization but we didn't had satisfactory results.

### Github Actions

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