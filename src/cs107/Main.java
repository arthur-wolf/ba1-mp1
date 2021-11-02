package cs107;

import java.util.ArrayList;
import java.util.List;

/**
 * This class will not be graded. You can use it to test your program.
 */
public class Main {

    /**
     * Main entry point of the program.
     *
     * @param args the command lines arguments of the program.
     */
    public static void main(String[] args) {
        //---------------------------
        // Tests functions separately
        //---------------------------
        System.out.println("Uncomment the function calls in Main.main to test your implementation.");
        System.out.println("The provided tests are not complete. You have to write your own tests.");
        System.out.println();
        testGetNeighbours();
        testBlackNeighbours();
        testTransition();
        testIdentical();
        testConnectedPixels();
        testComputeSlope();
        testComputeAngle();
        testOrientation();
        testApplyRotation();
        testApplyTranslation();
        testApplyTransformation();
        testThin();
        testWithSkeleton();

        testDrawSkeleton("1_1"); //draw skeleton of fingerprint 1_1.png
        testDrawSkeleton("1_2"); //draw skeleton of fingerprint 1_2.png
        testDrawSkeleton("2_1"); //draw skeleton of fingerprint 2_1.png

        testDrawMinutiae("1_1"); //draw minutiae of fingerprint 1_1.png
        testDrawMinutiae("1_2"); //draw minutiae of fingerprint 1_2.png
        testDrawMinutiae("2_1"); //draw minutiae of fingerprint 2_1.png

        //---------------------------
        // Test overall functionality
        //---------------------------
        //compare 1_1.png with 1_2.png: they are supposed to match
        testCompareFingerprints("1_1", "1_2", true);  //expected match: true

        //compare 1_1.png with 2_1.png: they are not supposed to match
        testCompareFingerprints("1_1", "2_1", false); //expected match: false

        //compare 1_1 with all other images of the same finger
        //testCompareAllFingerprints("1_1", 1, true);

        //compare 1_1 with all images of finger 2
        //testCompareAllFingerprints("1_1", 2, false);

        //compare 1_1 with all images of finger 3 to 16
        //for (int f = 3; f <= 16; f++) {
            //testCompareAllFingerprints("1_1", f, false);
        //}
    }

    /**
     * This function is here to help you test the functionalities of
     * getNeighbours. You are free to modify and/or delete it.
     */
    public static void testGetNeighbours() {
        //TEST 1
        System.out.print("testGetNeighbours 1: ");
        boolean[][] image = {{true}};
        boolean[] neighbours1 = Fingerprint.getNeighbours(image, 0, 0);
        boolean[] expected1 = {false, false, false, false,
                false, false, false, false};
        printResultsGetNeighbours(neighbours1, expected1);

        //TEST 2
        System.out.print("testGetNeighbours 2: ");
        boolean[][] image2 = {{true, true}};
        boolean[] neighbours2 = Fingerprint.getNeighbours(image2, 0, 0);
        boolean[] expected2 = {false, false, true, false,
                false, false, false, false};
        printResultsGetNeighbours(neighbours2, expected2);

        //TEST 3
        System.out.print("testGetNeighbours 3: ");
        boolean[][] image3 = {{true, true, true, true},
                {true, false, false, true},
                {true, false, false, true},
                {true, true, true, true}};
        boolean[] neighbours3 = Fingerprint.getNeighbours(image3, 1, 1);
        boolean[] expected3 = {true, true, false, false,
                false, true, true, true};
        printResultsGetNeighbours(neighbours3, expected3);

        System.out.println();
    }

    /**
     * This functions allows to clarify the code of the test for getNeighbours() above
     * It compares the Array that's outputted by Fingerprint.getNeighbours() and the expected output
     *
     * @param neighbours Fingerprint.getNeighbours() output
     * @param expected   expected output
     */
    public static void printResultsGetNeighbours(boolean[] neighbours, boolean[] expected) {
        if (arrayEqual(neighbours, expected)) {
            System.out.println("OK");
        } else {
            System.out.println("ERROR");
            System.out.print("Expected: ");
            printArray(expected);
            System.out.print("Computed: ");
            printArray(neighbours);
        }
    }

    /**
     * This function tests the functionalities of blackNeighbours on two different datasets
     */
    public static void testBlackNeighbours() {
        //TEST 1
        System.out.print("testBlackNeighbours1: ");
        boolean[] neighbours1 = {false, true, true,
                false, false,
                false, true, false};
        int expected1 = 3;

        int result1 = Fingerprint.blackNeighbours(neighbours1);
        printResultsBlackNeighbours(result1, expected1);

        //TEST 2
        System.out.print("testBlackNeighbours2: ");
        boolean[] neighbours2 = {true, true, false,
                false, true,
                false, false, true};
        int expected2 = 4;

        int result2 = Fingerprint.blackNeighbours(neighbours2);
        printResultsBlackNeighbours(result2, expected2);

        System.out.println();
    }

    /**
     * This functions allows to clarify the code of the test for blackNeighbours() above
     * It compares the Array that's outputted by Fingerprint.blackNeighbours() and the expected output
     *
     * @param result   Fingerprint.blackNeighbours() output
     * @param expected expected output
     */
    public static void printResultsBlackNeighbours(int result, int expected) {
        if (result == expected) {
            System.out.println("OK");
        } else {
            System.out.println("ERROR");
            System.out.print("Expected: " + expected + " ");
            System.out.println("Computed: " + result);
        }
    }

    /**
     * This function tests the functionalities of transition on 4 different datasets
     */
    public static void testTransition() {
        //TEST 1
        System.out.print("testTransition1: ");
        boolean[] neighbours = {false, true, true, false, false, false, true, false, false};
        int expected1 = 2;

        int result1 = Fingerprint.transitions(neighbours);
        printResultsTransition(result1, expected1);

        //TEST 2
        System.out.print("testTransition2: ");
        boolean[] neighbours2 = {true, false, true, false, true, false, true, false, false};
        int expected2 = 4;

        int result2 = Fingerprint.transitions(neighbours2);
        printResultsTransition(result2, expected2);

        //TEST 3
        System.out.print("testTransition3: ");
        boolean[] neighbours3 = {false, false, true, false, true, false, true, true};
        int expected3 = 3;

        int result3 = Fingerprint.transitions(neighbours3);
        printResultsTransition(result3, expected3);

        //TEST 4
        System.out.print("testTransition4: ");
        boolean[] neighbours4 = {false, true, true, false, false, false, true, false, false};
        int expected4 = 2;

        int result4 = Fingerprint.transitions(neighbours4);
        printResultsTransition(result4, expected4);

        System.out.println();
    }

    /**
     * This functions allows to clarify the code of the test for transitions() above
     * It compares the Array that's outputted by Fingerprint.transitions() and the expected output
     *
     * @param result   Fingerprint.transitions() output
     * @param expected expected output
     */
    public static void printResultsTransition(int result, int expected) {
        if (result == expected) {
            System.out.println("OK");
        } else {
            System.out.println("ERROR");
            System.out.print("Expected: " + expected + " ");
            System.out.println("Computed: " + result);
        }
    }

    /**
     * This function tests the functionalities of testIdentical on 3 different datasets
     */
    public static void testIdentical() {
        System.out.print("testIdentical1: ");
        boolean[][] image11 = {{false, true, false, true},
                {true, false, true, false},
                {false, true, true, false},
                {true, false, false, true}};
        boolean[][] image21 = {{true, true, true, true},
                {false, false, false, false},
                {false, false, true, false},
                {false, false, false, false}};
        final boolean expected1 = false;
        boolean result1 = Fingerprint.identical(image11, image21);
        printResultsIdentical(result1, expected1);

        System.out.print("testIdentical2: ");
        boolean[][] image12 = {{true, false, false, true},
                {false, false, true, true},
                {false, true, true, false},
                {false, false, false, false}};
        boolean[][] image22 = {{true, false, false, true},
                {false, false, true, true},
                {false, true, true, false},
                {false, false, false, false}};
        final boolean expected2 = true;

        boolean result2 = Fingerprint.identical(image12, image22);
        printResultsIdentical(result2, expected2);

        System.out.print("testIdentical2: ");
        boolean[][] image13 = {{true, false, false, true},
                {false, false, true, true},
                {false, true, true, false},
                {false, false, false, false}};
        boolean[][] image23 = {{true, false, false, true},
                {false, false, true, true},
                {false, true, true, false},
                {false, false, false, true}};
        final boolean expected3 = false;

        boolean result3 = Fingerprint.identical(image13, image23);
        printResultsIdentical(result3, expected3);

        System.out.println();
    }

    /**
     * This functions allows to clarify the code of the test for identical() above
     * It compares the Array that's outputted by Fingerprint.identical() and the expected output
     *
     * @param result   Fingerprint.identical() output
     * @param expected expected output
     */
    public static void printResultsIdentical(boolean result, boolean expected) {
        if (result == expected) {
            System.out.println("OK");
        } else {
            System.out.println("ERROR");
            System.out.print("Expected: " + expected + " ");
            System.out.println("Computed: " + result);
        }
    }

    /**
     * This function is here to help you test the functionalities of
     * connectedPixels. You are free to modify and/or delete it.
     */
    public static void testConnectedPixels() {
        System.out.print("testConnectedPixels1: ");
        boolean[][] image1 = {{true, false, false, true},
                {false, false, true, true},
                {false, true, true, false},
                {false, false, false, false}};
        boolean[][] expected1 = {{false, false, false, true},
                {false, false, true, true},
                {false, true, true, false},
                {false, false, false, false}};
        boolean[][] connectedPixels1 = Fingerprint.connectedPixels(image1, 2, 1, 10);
        printResultConnectedPixels(connectedPixels1, expected1);

        System.out.print("testConnectedPixels2: ");
        boolean[][] image2 = {{true, false, false, true},
                {false, false, true, true},
                {false, true, true, false},
                {false, false, false, false}};
        boolean[][] expected2 = {{false, false, false, false},
                {false, false, true, false},
                {false, true, true, false},
                {false, false, false, false}};
        boolean[][] connectedPixels2 = Fingerprint.connectedPixels(image2, 2, 1, 1);
        printResultConnectedPixels(connectedPixels2, expected2);

        System.out.print("testConnectedPixels3: ");
        boolean[][] image3 = {{true, false, false, true, true},
                {true, false, true, true, false},
                {true, true, false, false, false},
                {false, true, false, true, false}};
        boolean[][] expected3 = {{true, false, false, true, false},
                {true, false, true, true, false},
                {true, true, false, false, false},
                {false, true, false, false, false}};
        boolean[][] connectedPixels3 = Fingerprint.connectedPixels(image3, 2, 1, 2);
        printResultConnectedPixels(connectedPixels3, expected3);

        System.out.print("testConnectedPixels4: ");
        boolean[][] image4 = {{true, true, false, false, false},
                {false, false, true, false, false},
                {true, false, true, true, false},
                {false, true, false, true, true}};
        boolean[][] expected4 = {{false, true, false, false, false},
                {false, false, true, false, false},
                {false, false, true, true, false},
                {false, true, false, true, true}};
        boolean[][] connectedPixels4 = Fingerprint.connectedPixels(image4, 2, 3, 2);
        printResultConnectedPixels(connectedPixels4, expected4);

        System.out.println();
    }

    /**
     * This functions allows to clarify the code of the test for connectedPixels above
     * It compares the Array that's outputted by the written function and the expected output
     *
     * @param connectedPixels Array outputted by the written function
     * @param expected        expected output
     */
    public static void printResultConnectedPixels(boolean[][] connectedPixels, boolean[][] expected) {
        if (arrayEqual(connectedPixels, expected)) {
            System.out.println("OK");
        } else {
            System.out.println("ERROR");
            System.out.print("Expected: ");
            printArray(expected);
            System.out.print("Computed: ");
            printArray(connectedPixels);
        }
    }

    /**
     * This function tests the functionalities of Fingerprint.computeAngle() with 4 different datasets
     */
    public static void testComputeAngle() {
        System.out.print("testComputeAngle1:");   //case decreasing linear curve --> angle is -pi/4 (-45°)
        boolean[][] connectedPixels1 = {{false, false, false, false, false},
                {false, true, false, false, false},
                {false, false, true, false, false},
                {false, false, false, true, false},
                {false, false, false, false, false}};
        int row1 = 1;
        int col1 = 1;
        double slope1 = Fingerprint.computeSlope(connectedPixels1, row1, col1);
        double result1 = Fingerprint.computeAngle(connectedPixels1, row1, col1, slope1);
        double expected1 = -Math.PI / 4;
        printResultComputeAngle(result1, expected1);

        System.out.print("testComputeAngle2:");   //Case vertical going up --> slope is infinity and angle pi/2 (90°)
        boolean[][] connectedPixels2 = {{false, false, false, false, false},
                {false, false, true, false, false},
                {false, false, true, false, false},
                {false, false, true, false, false},
                {false, false, false, false, false}};
        int row2 = 3;
        int col2 = 2;
        double slope2 = Fingerprint.computeSlope(connectedPixels2, row2, col2);
        double result2 = Fingerprint.computeAngle(connectedPixels2, row2, col2, slope2);
        double expected2 = Math.PI / 2;
        printResultComputeAngle(result2, expected2);

        System.out.print("testComputeAngle3:");   //Case vertical going down --> slope is -infinity and angle -pi/2 (-90°)
        boolean[][] connectedPixels3 = {{false, false, false, false, false},
                {false, false, true, false, false},
                {false, false, true, false, false},
                {false, false, true, false, false},
                {false, false, false, false, false}};
        int row3 = 1;
        int col3 = 2;
        double slope3 = Fingerprint.computeSlope(connectedPixels3, row3, col3);
        double result3 = Fingerprint.computeAngle(connectedPixels3, row3, col3, slope3);
        double expected3 = -Math.PI / 2;
        printResultComputeAngle(result3, expected3);

        System.out.print("testComputeAngle4:");   //Case decreasing linear curve --> slope is -1 and angle 3*pi/4 (135°)
        boolean[][] connectedPixels4 = {{false, false, false, false, false},
                {false, true, false, false, false},
                {false, false, true, false, false},
                {false, false, false, true, false},
                {false, false, false, false, false}};
        int row4 = 3;
        int col4 = 3;
        double slope4 = Fingerprint.computeSlope(connectedPixels4, row4, col4);
        double result4 = Fingerprint.computeAngle(connectedPixels4, row4, col4, slope4);
        double expected4 = 3 * Math.PI / 4;
        printResultComputeAngle(result4, expected4);

        System.out.println();
    }

    /**
     * This function allows to clarify the code above for testComputeAngle()
     *
     * @param result   output from Fingerprint.computeAngle()
     * @param expected expected output
     */
    public static void printResultComputeAngle(double result, double expected) {
        if (result == expected) {
            System.out.println("OK");
        } else {
            System.out.println("ERROR");
            System.out.print("Expected: ");
            System.out.println(expected);
            System.out.print("Computed: ");
            System.out.println(result);
        }
    }

    /**
     * This function is here to help you test the functionalities of
     * computeOrientation. You are free to modify and/or delete it.
     */
    public static void testOrientation() {
        System.out.print("testOrientation1: ");
        boolean[][] image1 = {{true, false, false, true},
                {false, false, true, true},
                {false, true, true, false},
                {false, false, false, false}};
        int angle1 = Fingerprint.computeOrientation(image1, 2, 1, 3);
        int expectedAngle1 = 35;
        printResultOrientation(angle1, expectedAngle1);

        System.out.print("testOrientation2: ");  //case decreasing linear curve --> angle is -pi/4 (-45°)
        boolean[][] image2 = {{false, false, false, false, false},
                {false, true, false, false, false},
                {false, false, true, false, false},
                {false, false, false, true, false},
                {false, false, false, false, false}};
        int angle2 = Fingerprint.computeOrientation(image2, 1, 1, 5);
        int expectedAngle2 = (-45 + 360);
        printResultOrientation(angle2, expectedAngle2);

        System.out.print("testOrientation3: ");  //Case vertical going up --> slope is infinity and angle pi/2 (90°)
        boolean[][] image3 = {{false, false, false, false, false},
                {false, false, true, false, false},
                {false, false, true, false, false},
                {false, false, true, false, false},
                {false, false, false, false, false}};
        int angle3 = Fingerprint.computeOrientation(image3, 3, 2, 5);
        int expectedAngle3 = 90;
        printResultOrientation(angle3, expectedAngle3);

        System.out.print("testOrientation4: ");  //Case vertical going down --> slope is -infinity and angle -pi/2 (-90°)
        boolean[][] image4 = {{false, false, false, false, false},
                {false, false, true, false, false},
                {false, false, true, false, false},
                {false, false, true, false, false},
                {false, false, false, false, false}};
        int angle4 = Fingerprint.computeOrientation(image4, 1, 2, 5);
        int expectedAngle4 = (-90 + 360);
        printResultOrientation(angle4, expectedAngle4);

        System.out.print("testOrientation5: ");  //Case decreasing linear curve --> slope is -1 and angle 3*pi/4 (135°)
        boolean[][] image5 = {{false, false, false, false, false},
                {false, true, false, false, false},
                {false, false, true, false, false},
                {false, false, false, true, false},
                {false, false, false, false, false}};
        int angle5 = Fingerprint.computeOrientation(image5, 3, 3, 3);
        int expectedAngle5 = 135;
        printResultOrientation(angle5, expectedAngle5);

        System.out.println();
    }

    /**
     * This function allows to clarify the code above for testOrientation()
     *
     * @param result   output from Fingerprint.computeOrientation()
     * @param expected expected output
     */
    public static void printResultOrientation(int result, int expected) {
        if (result == expected) {
            System.out.println("OK");
        } else {
            System.out.println("ERROR");
            System.out.print("Expected: ");
            System.out.println(expected);
            System.out.print("Computed: ");
            System.out.println(result);
        }
    }

    /**
     * This function tests Fingerprint.computeSlope() with 5 different datasets
     */
    public static void testComputeSlope() {
        System.out.print("testComputeSlope1: "); //Case increasing linear curve
        boolean[][] connectedPixels1 = {{false, false, false, false, false},
                {false, false, false, true, false},
                {false, false, true, false, false},
                {false, true, false, false, false},
                {false, false, false, false, false}};
        int rowm1 = 3;
        int colm1 = 1;
        double coeff1 = Fingerprint.computeSlope(connectedPixels1, rowm1, colm1);
        double expected1 = 1.0;
        printResultComputeSlope(coeff1, expected1);

        System.out.print("testComputeSlope2: "); // Case a = constant
        boolean[][] connectedPixels2 = {{false, false, false, false, false},
                {true, true, true, true, false},
                {false, false, false, false, false},
                {false, false, false, false, false},
                {false, false, false, false, false}};
        int rowm2 = 1;
        int colm2 = 1;
        double coeff2 = Fingerprint.computeSlope(connectedPixels2, rowm2, colm2);
        double expected2 = 0;
        printResultComputeSlope(coeff2, expected2);

        System.out.print("testComputeSlope3: "); // Case Infinity
        boolean[][] connectedPixels3 = {{false, false, false, false, false},
                {false, true, false, false, false},
                {false, true, false, false, false},
                {false, true, false, false, false},
                {false, false, false, false, false}};
        int rowm3 = 3;
        int colm3 = 1;
        double coeff3 = Fingerprint.computeSlope(connectedPixels3, rowm3, colm3);
        double expected3 = Double.POSITIVE_INFINITY;
        printResultComputeSlope(coeff3, expected3);

        System.out.print("testComputeSlope4: "); // Case decreasing linear curve
        boolean[][] connectedPixels4 = {{false, false, false, false, false},
                {true, false, false, false, false},
                {true, true, false, false, false},
                {false, true, false, false, false},
                {false, false, false, false, false}};
        int rowm4 = 1;
        int colm4 = 0;
        double coeff4 = Fingerprint.computeSlope(connectedPixels4, rowm4, colm4);
        double expected4 = -2.0;
        printResultComputeSlope(coeff4, expected4);

        System.out.print("testComputeSlope5: "); // Case bigger array
        boolean[][] connectedPixels5 = {{false, false, false, false, false, false, false, false, false},
                {false, false, false, false, false, false, false, false, true},
                {false, false, false, false, false, false, true, false, false,},
                {false, false, false, false, true, false, false, false, false},
                {false, false, true, false, false, false, false, false, false},
                {true, false, false, false, false, false, false, false, false},
                {false, false, false, false, false, false, false, false, false}};
        int rowm5 = 4;
        int colm5 = 0;
        double coeff5 = Fingerprint.computeSlope(connectedPixels5, rowm5, colm5);
        double expected5 = 1 / 3.0;
        printResultComputeSlope(coeff5, expected5);

        System.out.println();
    }

    /**
     * This functions allows to clarify the code of the test for computeSlope() above
     * It compares the Array that's outputted by Fingerprint.computeSlope() and the expected output
     *
     * @param coeff    Fingerprint.computeSlope() output
     * @param expected expected output
     */
    public static void printResultComputeSlope(double coeff, double expected) {
        if (coeff == expected) {
            System.out.println("OK");
        } else {
            System.out.println("ERROR");
            System.out.print("Expected: ");
            System.out.println(expected);
            System.out.print("Computed: ");
            System.out.println(coeff);
        }
    }

    /**
     * This function is here to help you test the functionalities of
     * applyRotation. You are free to modify and/or delete it.
     */
    public static void testApplyRotation() {
        // minutia, centerRow, centerCol, rotation)
        int[] minutia = new int[]{1, 3, 10};

        System.out.print("testApplyRotation1: ");
        int[] result = Fingerprint.applyRotation(minutia, 0, 0, 0);
        assert result != null;
        int[] expected = new int[]{1, 3, 10};
        printResultApplyRotation(result, expected);

        System.out.print("testApplyRotation2: ");
        int[] result2 = Fingerprint.applyRotation(minutia, 10, 5, 0);
        assert result2 != null;
        int[] expected2 = new int[]{1, 3, 10};
        printResultApplyRotation(result2, expected2);

        System.out.print("testApplyRotation3: ");
        int[] result3 = Fingerprint.applyRotation(minutia, 0, 0, 90);
        assert result3 != null;
        int[] expected3 = new int[]{-3, 1, 100};
        printResultApplyRotation(result3, expected3);

        System.out.print("testApplyRotation4: ");
        int[] result4 = Fingerprint.applyRotation(new int[]{0, 3, 10}, 0, 0, 90);
        assert result4 != null;
        int[] expected4 = new int[]{-3, 0, 100};
        printResultApplyRotation(result4, expected4);

        System.out.print("testApplyRotation5: ");
        int[] result5 = Fingerprint.applyRotation(new int[]{3, 0, 10}, 0, 0, 90);
        assert result5 != null;
        int[] expected5 = new int[]{0, 3, 100};
        printResultApplyRotation(result5, expected5);

        System.out.println();
    }

    /**
     * This functions allows to clarify the code of the test for applyRotation() above
     * It compares the Array that's outputted by Fingerprint.applyRotation() and the expected output
     *
     * @param result   Fingerprint.applyRotation() output
     * @param expected expected output
     */
    public static void printResultApplyRotation(int[] result, int[] expected) {
        if (arrayEqual(result, expected)) {
            System.out.println("OK");
        } else {
            System.out.println("ERROR");
            System.out.print("Expected: ");
            printArray(expected);
            System.out.print("Computed: ");
            printArray(result);
        }
    }

    /**
     * This function is here to help you test the functionalities of
     * applyTranslation. You are free to modify and/or delete it.
     */
    public static void testApplyTranslation() {
        // minutia, rowTranslation, colTranslation)
        System.out.print("testApplyTranslation1: ");
        int[] result1 = Fingerprint.applyTranslation(new int[]{1, 3, 10}, 0, 0);
        assert result1 != null;
        int[] expected1 = new int[]{1, 3, 10};
        printResultApplyTranslation(result1, expected1);

        System.out.print("testApplyTranslation2: ");
        int[] result2 = Fingerprint.applyTranslation(new int[]{1, 3, 10}, 10, 5);
        assert result2 != null;
        int[] expected2 = new int[]{-9, -2, 10};
        printResultApplyTranslation(result2, expected2);

        System.out.print("testApplyTranslation3: ");
        int[] result3 = Fingerprint.applyTranslation(new int[]{1, 3, 10}, -1, 1);
        assert result3 != null;
        int[] expected3 = new int[]{2, 2, 10};
        printResultApplyTranslation(result3, expected3);

        System.out.println();
    }

    /**
     * This functions allows to clarify the code of the test for applyTranslation() above
     * It compares the Array that's outputted by Fingerprint.applyTranslation() and the expected output
     *
     * @param result   Fingerprint.applyTranslation() output
     * @param expected expected output
     */
    public static void printResultApplyTranslation(int[] result, int[] expected) {
        if (arrayEqual(result, expected)) {
            System.out.println("OK");
        } else {
            System.out.println("ERROR");
            System.out.print("Expected: ");
            printArray(expected);
            System.out.print("Computed: ");
            printArray(result);
        }
    }

    /**
     * This function is here to help you test the functionalities of
     * applyTransformation. You are free to modify and/or delete it.
     */
    public static void testApplyTransformation() {
        // minutia, centerRow, centerCol, rotation)
        int[] minutia = new int[]{1, 3, 10};

        System.out.print("testApplyTransformation: ");
        int[] result = Fingerprint.applyTransformation(minutia, 0, 0, 10, 5, 0);
        assert result != null;
        int[] expected = new int[]{-9, -2, 10};
        printResultApplyTransformation(result, expected);

        System.out.print("testApplyTransformation2: ");
        int[] result2 = Fingerprint.applyTransformation(minutia, 0, 0, 10, 5, 90);
        assert result2 != null;
        int[] expected2 = new int[]{-13, -4, 100};
        printResultApplyTransformation(result2, expected2);

        System.out.print("testApplyTransformation3: ");
        int[] result3 = Fingerprint.applyTransformation(new int[]{0, 3, 10}, 0, 0, 12, 0, 90);
        assert result3 != null;
        int[] expected3 = new int[]{-15, 0, 100};
        printResultApplyTransformation(result3, expected3);

        System.out.print("testApplyTransformation4: ");
        int[] result4 = Fingerprint.applyTransformation(new int[]{3, 0, 10}, 0, 0, 0, 5, 90);
        assert result4 != null;
        int[] expected4 = new int[]{0, -2, 100};
        printResultApplyTransformation(result4, expected4);

        System.out.println();
    }

    /**
     * This functions allows to clarify the code of the test for applyTransformation() above
     * It compares the Array that's outputted by Fingerprint.applyTransformation() and the expected output
     *
     * @param result   Fingerprint.applyTransformation() output
     * @param expected expected output
     */
    public static void printResultApplyTransformation(int[] result, int[] expected) {
        if (arrayEqual(result, expected)) {
            System.out.println("OK");
        } else {
            System.out.println("ERROR");
            System.out.print("Expected: ");
            printArray(expected);
            System.out.print("Computed: ");
            printArray(result);
        }
    }

    /**
     * This function is here to help you test the functionalities of extract.
     * It will read the first fingerprint and extract the minutiae. It will save
     * the thinned version as skeleton_1_1.png and a version where the minutiae
     * are drawn on top as minutiae_1_1.png. You are free to modify and/or delete
     * it.
     */
    public static void testThin() {
        boolean[][] image1 = Helper.readBinary("resources/test_inputs/1_1_small.png");
        boolean[][] skeleton1 = Fingerprint.thin(image1);
        Helper.writeBinary("skeleton_1_1_small.png", skeleton1);
    }

    public static void testDrawSkeleton(String name) {
        boolean[][] image1 = Helper.readBinary("resources/fingerprints/" + name + ".png");
        boolean[][] skeleton1 = Fingerprint.thin(image1);
        Helper.writeBinary("skeleton_" + name + ".png", skeleton1);
    }

    public static void testDrawMinutiae(String name) {
        boolean[][] image1 = Helper.readBinary("resources/fingerprints/" + name + ".png");
        boolean[][] skeleton1 = Fingerprint.thin(image1);
        List<int[]> minutia1 = Fingerprint.extract(skeleton1);
        int[][] colorImageSkeleton1 = Helper.fromBinary(skeleton1);
        Helper.drawMinutia(colorImageSkeleton1, minutia1);
        Helper.writeARGB("minutiae_" + name + ".png", colorImageSkeleton1);
    }

    /**
     * This function is here to help you test the functionalities of extract
     * without using the function thin. It will read the first fingerprint and
     * extract the minutiae. It will save a version where the minutiae are drawn
     * on top as minutiae_skeletonTest.png. You are free to modify and/or delete
     * it.
     */
    public static void testWithSkeleton() {
        boolean[][] skeleton1 = Helper.readBinary("resources/test_inputs/skeletonTest.png");
        assert skeleton1 != null;
        List<int[]> minutiae1 = Fingerprint.extract(skeleton1);
        List<int[]> expected = new ArrayList<int[]>();
        expected.add(new int[]{39, 21, 264});
        expected.add(new int[]{53, 33, 270});

        System.out.print("Expected minutiae: ");
        printMinutiae(expected);
        System.out.print("Computed minutiae: ");
        printMinutiae(minutiae1);

        // Draw the minutiae on top of the thinned image
        int[][] colorImageSkeleton1 = Helper.fromBinary(skeleton1);
        Helper.drawMinutia(colorImageSkeleton1, minutiae1);
        Helper.writeARGB("minutiae_skeletonTest.png", colorImageSkeleton1);
    }

    public static void printMinutiae(List<int[]> minutiae) {
        for (int[] minutia : minutiae) {
            System.out.print("[");
            for (int j = 0; j < minutia.length; j++) {
                System.out.print(minutia[j]);
                if (j != minutia.length - 1)
                    System.out.print(", ");
            }
            System.out.println("],");
        }
    }

    /**
     * This function is here to help you test the overall functionalities. It will
     * compare the fingerprint in the file name1.png with the fingerprint in the
     * file name2.png. The third parameter indicates if we expected a match or not.
     */
    public static void testCompareFingerprints(String name1, String name2, boolean expectedResult) {
        boolean[][] image1 = Helper.readBinary("resources/fingerprints/" + name1 + ".png");
        assert image1 != null;
        //Helper.show(Helper.fromBinary(image1), "Image1");
        boolean[][] skeleton1 = Fingerprint.thin(image1);
        //Helper.writeBinary("skeleton_" + name1 + ".png", skeleton1);
        List<int[]> minutiae1 = Fingerprint.extract(skeleton1);
        //printMinutiae(minutiae1);

        //int[][] colorImageSkeleton1 = Helper.fromBinary(skeleton1);
        //Helper.drawMinutia(colorImageSkeleton1, minutiae1);
        //Helper.writeARGB("./minutiae_" + name1 + ".png", colorImageSkeleton1);

        boolean[][] image2 = Helper.readBinary("resources/fingerprints/" + name2 + ".png");
        boolean[][] skeleton2 = Fingerprint.thin(image2);
        List<int[]> minutiae2 = Fingerprint.extract(skeleton2);

        //int[][] colorImageSkeleton2 = Helper.fromBinary(skeleton2);
        //Helper.drawMinutia(colorImageSkeleton2, minutiae2);
        //Helper.writeARGB("./minutiae_" + name2 + ".png", colorImageSkeleton2);

        boolean isMatch = Fingerprint.match(minutiae1, minutiae2);
        System.out.print("Compare " + name1 + " with " + name2);
        System.out.print(". Expected match: " + expectedResult);
        System.out.println(" Computed match: " + isMatch);
    }

    /**
     * This function is here to help you test the overall functionalities. It will
     * compare the fingerprint in the file <code>name1.png</code> with all the eight
     * fingerprints of the given finger (second parameter).
     * The third parameter indicates if we expected a match or not.
     */
    public static void testCompareAllFingerprints(String name1, int finger, boolean expectedResult) {
        for (int i = 1; i <= 8; i++) {
            testCompareFingerprints(name1, finger + "_" + i, expectedResult);
        }
    }

    /*
     * Helper functions to print and compare arrays
     */
    public static boolean arrayEqual(boolean[] array1, boolean[] array2) {
        if (array1 == null && array2 == null)
            return true;
        if (array1 == null || array2 == null)
            return false;
        if (array1.length != array2.length)
            return false;

        for (int i = 0; i < array1.length; i++) {
            if (array1[i] != array2[i])
                return false;
        }
        return true;
    }

    /*
     * Helper functions to print and compare arrays
     */
    public static boolean arrayEqual(int[] array1, int[] array2) {
        if (array1 == null && array2 == null)
            return true;
        if (array1 == null || array2 == null)
            return false;
        if (array1.length != array2.length)
            return false;

        for (int i = 0; i < array1.length; i++) {
            if (array1[i] != array2[i])
                return false;
        }
        return true;
    }

    public static boolean arrayEqual(boolean[][] array1, boolean[][] array2) {
        if (array1 == null && array2 == null)
            return true;
        if (array1 == null || array2 == null)
            return false;
        if (array1.length != array2.length)
            return false;

        for (int i = 0; i < array1.length; i++) {
            if (!arrayEqual(array1[i], array2[i]))
                return false;
        }
        return true;
    }

    public static void printArray(boolean[][] array) {
        for (boolean[] row : array) {
            for (boolean pixel : row) {
                System.out.print(pixel + ",");
            }
            System.out.println();
        }
    }

    public static void printArray(boolean[] array) {
        for (boolean pixel : array) {
            System.out.print(pixel + ",");
        }
        System.out.println();
    }

    public static void printArray(int[] array) {
        for (int pixel : array) {
            System.out.print(pixel + ",");
        }
        System.out.println();
    }
}
