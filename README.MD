
![Logo](https://upload.wikimedia.org/wikipedia/commons/f/f4/Logo_EPFL.svg)

# EPFL IC BA1 MP1
This project permits the comparison of fingerprints

## Notes
- During the development of the project, we found out we should use m2 as a rotation center in the match() method instead of m1.
Therefore, we used m2 as a rotation center in our match() method.
- We improved the Main file by adding convenient debugging functions and unit tests to make sure that all our methods work properly.
- We tested all combinations of fingerprints between the fingers 1_1, 1_2 & 1_5. We obtained exactly the same results as expected whether we took m1 as the center of rotation or m2.
- You will later on read about a python script we have written. Please follow the link to the GitHub repository that can be found at the end of this README file in order to be able to read it,
as we did not include it in the /src folder. (also will it look nicer on GitHub)

## Bonus

### Multithreading
We have used multithreading in order to improve how fast the program was running. This has been done in Main.java.
We used the method "IntStream.range(1, 16).parallel().forEach(y -> testAllCombinationsOneFinger(y))" to test all combinations for
one finger using one thread per finger to compare with.

This has greatly improved the run time. Indeed, we have been able to test 1/16th of all possible combinations
in only 15 minutes (approximately).

### Outputting data and parsing for statistics
We thought it would be great to know what percentage of our results were accurate (or not). In order to do this, we created the writeOutput()
method that outputs the results that we can usually find in the terminal into an output.txt file.

This file serves as an input for a python script we have written, that parses the output.txt file and outputs
how many comparisons have the good result, and how many false positives / false negatives have been found among all the comparisons.
you can find this script under the name main.py.

### Image Pre-Processing
We were also interested in pre-processing images. We first tried a dumb approach by changing the colour of each pixel to black or white depending on whether it was closer to black or white.
The code written to achieve that can be found in the method preProcessImages() in our Main file. You can find the result of the image pre-processing of original_fingerprints/1_1.png on GitHub under the file output_preprocess.png.

We realised that this approach was not suitable here, as there are holes in the lines, the image may be deteriorated in places. Therefore, we went for more general research and came across the following research paper: [Fingerprint Image Enhancement: Algorithm and Performance Evaluation](https://ieeexplore.ieee.org/document/709565) written by Lin Hong, Yifei Wan and A. Jain.
Using this paper we were able to break down the image preprocessing into 5 steps:
- Normalization
- Orientation Image Estimation
- Frequency Image Estimation
- Region Mask Generation
- Filtering
      
We had a try at implementing image normalization, but we didn't have satisfactory results.

### GitHub Actions

We also set up a Java CI GitHub action to automatically build and run our project after each commit to ensure that we kept our results consistent as we went along.

## Authors

- [@Arthur Wolf](https://www.github.com/arthur-wolf)
- [@Arthur Bigot](https://www.github.com/MisteFr)


## Resources

The resources provided for the project are available here:
- [Project's Description](https://proginsc.epfl.ch/wwwhiver/mini-projet1/descriptif.html)
- [Project's Guidelines](https://proginsc.epfl.ch/wwwhiver/mini-projet1/fingerprint.pdf)

## GitHub repository
- [Project's repository](https://github.com/arthur-wolf/ba1-mp1)
