package cs107;

import java.util.*;

/**
 * Provides tools to compare fingerprint.
 */
public class Fingerprint {

  /**
   * The number of pixels to consider in each direction when doing the linear
   * regression to compute the orientation.
   */
  public static final int ORIENTATION_DISTANCE = 16;

  /**
   * The maximum distance between two minutiae to be considered matching.
   */
  public static final int DISTANCE_THRESHOLD = 5;

  /**
   * The number of matching minutiae needed for two fingerprints to be considered
   * identical.
   */
  public static final int FOUND_THRESHOLD = 20;

  /**
   * The distance between two angle to be considered identical.
   */
  public static final int ORIENTATION_THRESHOLD = 20;

  /**
   * The offset in each direction for the rotation to test when doing the
   * matching.
   */
  public static final int MATCH_ANGLE_OFFSET = 2;

  /**
   * Returns an array containing the value of the 8 neighbours of the pixel at
   * coordinates <code>(row, col)</code>.
   * <p>
   * The pixels are returned such that their indices corresponds to the following
   * diagram:<br>
   * ------------- <br>
   * | 7 | 0 | 1 | <br>
   * ------------- <br>
   * | 6 | _ | 2 | <br>
   * ------------- <br>
   * | 5 | 4 | 3 | <br>
   * ------------- <br>
   * <p>
   * If a neighbours is out of bounds of the image, it is considered white.
   * <p>
   * If the <code>row</code> or the <code>col</code> is out of bounds of the
   * image, the returned value should be <code>null</code>.
   *
   * @param image array containing each pixel's boolean value.
   * @param row   the row of the pixel of interest, must be between
   *              <code>0</code>(included) and
   *              <code>image.length</code>(excluded).
   * @param col   the column of the pixel of interest, must be between
   *              <code>0</code>(included) and
   *              <code>image[row].length</code>(excluded).
   * @return An array containing each neighbours' value.
   */

  public static boolean[] getNeighbours(boolean[][] image, int row, int col) {
	  assert (image != null); // special case that is not expected (the image is supposed to have been checked
                              // earlier)

      //On retourne null si la ligne ou la colonne n'appartiennent pas au tableau.
      if (row >= image.length || row < 0 || col >= image[0].length || col < 0) {
          return null;
      }

       /*
        On met dans une liste ordonnée de type String toutes les positions à vérifier sous le format "ligne:colonne".
       */

      String [] coordsToCheck = new String[8];
      coordsToCheck[0] = (row - 1) + ":" + col;         //P0
      coordsToCheck[1] = (row - 1) + ":" + (col + 1);   //P1
      coordsToCheck[2] = row + ":" + (col + 1);         //P2
      coordsToCheck[3] = (row + 1) + ":" + (col + 1);   //P3
      coordsToCheck[4] = (row + 1) + ":" + col;         //P4
      coordsToCheck[5] = (row + 1) + ":" + (col - 1);   //P5
      coordsToCheck[6] = row + ":" + (col - 1);         //P6
      coordsToCheck[7] = (row - 1) + ":" + (col - 1);   //P7

      boolean [] result = new boolean[8];

      /*
        On récupère pour chaque élément du tableau la ligne et la colonne à tester (coordonnées du pixel en question)
       */
      for (int i = 0; i < coordsToCheck.length; ++i) {
          int rowToTest = Integer.parseInt(coordsToCheck[i].split(":")[0]);
          int columnToTest = Integer.parseInt(coordsToCheck[i].split(":")[1]);

          /*
            Ici on vérifie que rowToTest est compris entre 0 et le nombre de lignes
            Et que columnToTest est compris entre 0 et le nombre de colonnes
           */
          if(rowToTest >= 0 && rowToTest <= (image.length - 1) && columnToTest >= 0 && columnToTest <= (image[rowToTest].length - 1)){
              if(isPixelBlack(image[rowToTest][columnToTest])){
                  result[i] = true;
              }else{
                  result[i] = false;
              }
          }else{
              //Le pixel n'appartient pas à image (outOfBounds) mais on considère qu'il est blanc -> false;
              result[i] = false;
          }
      }

	  return result;
  }

  /**
   * Computes the number of black (<code>true</code>) pixels among the neighbours
   * of a pixel.
   *
   * @param neighbours array containing each pixel value. The array must respect
   *                   the convention described in
   *                   {@link #getNeighbours(boolean[][], int, int)}.
   * @return the number of black neighbours.
   */
  public static int blackNeighbours(boolean[] neighbours) {
      assert(neighbours != null);

	  int count = 0;
      for (int i = 0; i < neighbours.length; i++){
          if (isPixelBlack(neighbours[i])){
              count++;
          }
      }
	  return count;
  }
  
  /**
   * Computes the number of white to black transitions among the neighbours of
   * pixel.
   *
   * @param neighbours array containing each pixel value. The array must respect
   *                   the convention described in
   *                   {@link #getNeighbours(boolean[][], int, int)}.
   * @return the number of white to black transitions.
   */
  public static int transitions(boolean[] neighbours) {
      assert(neighbours != null);
      int count = 0;

       for(int i = 0; i < (neighbours.length-1); ++i){
           if ((isPixelBlack(neighbours[i]) && !isPixelBlack(neighbours[(i + 1)]))) {
               count++;
           }
       }

      if ((isPixelBlack(neighbours[(neighbours.length-1)]) && !isPixelBlack(neighbours[0]))) {
          count++;
      }
      return count;
  }

  /**
   * Internal method used by {@link #thin(boolean[][])}.
   *
   * @param image array containing each pixel's boolean value.
   * @param step  the step to apply, Step 0 or Step 1.
   * @return A new array containing each pixel's value after the step.
   */
  public static boolean[][] thinningStep(boolean[][] image, int step) {
      assert (image != null);
      boolean [][] newImage = copyList(image);


      for (int row = 0; row < newImage.length; ++row){
          for (int column = 0; column < newImage[0].length; ++column){
              if(checkConditions(newImage, row, column, step)){
                  newImage[row][column] = false;
              }
          }
      }
	  return newImage;
  }

  public static boolean checkConditions(boolean [][] image, int row, int column, int step){
      boolean [] neighbours = getNeighbours(image, row, column);
      assert neighbours != null;
      /*
        Pas besoin de vérifier si neighbours est null car si !areNeighboursNull, la lazy evaluation
        fera que la suite des conditions n'est pas évaluée.
      */
      if (isPixelBlack(image[row][column]) && !areNeighboursNull(image, row, column)) {
          if ((blackNeighbours(neighbours) >= 2 && blackNeighbours(neighbours) <= 6) && transitions(neighbours) == 1) {

              boolean P0 = neighbours[0];
              boolean P2 = neighbours[2];
              boolean P4 = neighbours[4];
              boolean P6 = neighbours[6];


              if (step == 0) {
                  if (!isPixelBlack(P0) || !isPixelBlack(P2) || !isPixelBlack(P4)) {
                      if (!isPixelBlack(P2) || !isPixelBlack(P4) || !isPixelBlack(P6)) {
                          return true;
                      }
                  }
              } else if (step == 1) {
                  if (!isPixelBlack(P0) || !isPixelBlack(P2) || !isPixelBlack(P6)) {
                      if (!isPixelBlack(P0) || !isPixelBlack(P4) || !isPixelBlack(P6)) {
                          return true;
                      }
                  }
              }
          }
      }
      return false;
  }

    /**
     * Returns <code>true</code> if the images are identical and false otherwise.
     *
     * @param image1 array containing each pixel's boolean value.
     * @param image2 array containing each pixel's boolean value.
     * @return <code>True</code> if they are identical, <code>false</code>
     *         otherwise.
     */
    public static boolean identical(boolean[][] image1, boolean[][] image2) {
        return Main.arrayEqual(image1, image2);
    }

  /**
   * Compute the skeleton of a boolean image.
   *
   * @param image array containing each pixel's boolean value.
   * @return array containing the boolean value of each pixel of the image after
   *         applying the thinning algorithm.
   */
  public static boolean[][] thin(boolean[][] image) {
      boolean [][] newImage = copyList(image);

      int i = 0;
      boolean hasChange = false;

      while(true){
          hasChange = false;
          boolean [][] resultStep1 = thinningStep(newImage, 0);
          if(!identical(resultStep1, newImage)){
              hasChange = true;
          }
          Helper.writeBinary("debug/debug_skeleton_1_" + i  + "_small.png", resultStep1);
          ++i;
          boolean [][] resultStep2 = thinningStep(resultStep1, 1);
          if(!identical(resultStep1, resultStep2)){
              hasChange = true;
          }
          Helper.writeBinary("debug/debug_skeleton_1_" + i  + "_small.png", resultStep2);
          ++i;
          newImage = resultStep2;

          if(!hasChange){
              return newImage;
          }
      }

  }

  /**
   * Computes all pixels that are connected to the pixel at coordinate
   * <code>(row, col)</code> and within the given distance of the pixel.
   *
   * @param image    array containing each pixel's boolean value.
   * @param row      the first coordinate of the pixel of interest.
   * @param col      the second coordinate of the pixel of interest.
   * @param distance the maximum distance at which a pixel is considered.
   * @return An array where <code>true</code> means that the pixel is within
   *         <code>distance</code> and connected to the pixel at
   *         <code>(row, col)</code>.
   */
  public static boolean[][] connectedPixels(boolean[][] image, int row, int col, int distance) {
	  //TODO implement
	  return null;
  }

  /**
   * Computes the slope of a minutia using linear regression.
   *
   * @param connectedPixels the result of
   *                        {@link #connectedPixels(boolean[][], int, int, int)}.
   * @param row             the row of the minutia.
   * @param col             the col of the minutia.
   * @return the slope.
   */
  public static double computeSlope(boolean[][] connectedPixels, int row, int col) {
	  //TODO implement
	  return 0;
  }

  /**
   * Computes the orientation of a minutia in radians.
   * 
   * @param connectedPixels the result of
   *                        {@link #connectedPixels(boolean[][], int, int, int)}.
   * @param row             the row of the minutia.
   * @param col             the col of the minutia.
   * @param slope           the slope as returned by
   *                        {@link #computeSlope(boolean[][], int, int)}.
   * @return the orientation of the minutia in radians.
   */
  public static double computeAngle(boolean[][] connectedPixels, int row, int col, double slope) {
	  //TODO implement
	  return 0;
  }

  /**
   * Computes the orientation of the minutia that the coordinate <code>(row,
   * col)</code>.
   *
   * @param image    array containing each pixel's boolean value.
   * @param row      the first coordinate of the pixel of interest.
   * @param col      the second coordinate of the pixel of interest.
   * @param distance the distance to be considered in each direction to compute
   *                 the orientation.
   * @return The orientation in degrees.
   */
  public static int computeOrientation(boolean[][] image, int row, int col, int distance) {
	  //TODO implement
	  return 0;
  }

  /**
   * Extracts the minutiae from a thinned image.
   *
   * @param image array containing each pixel's boolean value.
   * @return The list of all minutiae. A minutia is represented by an array where
   *         the first element is the row, the second is column, and the third is
   *         the angle in degrees.
   * @see #thin(boolean[][])
   */
  public static List<int[]> extract(boolean[][] image) {
	  //TODO implement
	  return null;
  }

  /**
   * Applies the specified rotation to the minutia.
   *
   * @param minutia   the original minutia.
   * @param centerRow the row of the center of rotation.
   * @param centerCol the col of the center of rotation.
   * @param rotation  the rotation in degrees.
   * @return the minutia rotated around the given center.
   */
  public static int[] applyRotation(int[] minutia, int centerRow, int centerCol, int rotation) {
	  //TODO implement
	  return null;
  }

  /**
   * Applies the specified translation to the minutia.
   *
   * @param minutia        the original minutia.
   * @param rowTranslation the translation along the rows.
   * @param colTranslation the translation along the columns.
   * @return the translated minutia.
   */
  public static int[] applyTranslation(int[] minutia, int rowTranslation, int colTranslation) {
	  //TODO implement
	  return null;
  } 
  
  /**
   * Computes the row, column, and angle after applying a transformation
   * (translation and rotation).
   *
   * @param minutia        the original minutia.
   * @param centerCol      the column around which the point is rotated.
   * @param centerRow      the row around which the point is rotated.
   * @param rowTranslation the vertical translation.
   * @param colTranslation the horizontal translation.
   * @param rotation       the rotation.
   * @return the transformed minutia.
   */
  public static int[] applyTransformation(int[] minutia, int centerRow, int centerCol, int rowTranslation,
      int colTranslation, int rotation) {
	  //TODO implement
	  return null;
  }

  /**
   * Computes the row, column, and angle after applying a transformation
   * (translation and rotation) for each minutia in the given list.
   *
   * @param minutiae       the list of minutiae.
   * @param centerCol      the column around which the point is rotated.
   * @param centerRow      the row around which the point is rotated.
   * @param rowTranslation the vertical translation.
   * @param colTranslation the horizontal translation.
   * @param rotation       the rotation.
   * @return the list of transformed minutiae.
   */
  public static List<int[]> applyTransformation(List<int[]> minutiae, int centerRow, int centerCol, int rowTranslation,
      int colTranslation, int rotation) {
	  //TODO implement
	  return null;
  }
  /**
   * Counts the number of overlapping minutiae.
   *
   * @param minutiae1      the first set of minutiae.
   * @param minutiae2      the second set of minutiae.
   * @param maxDistance    the maximum distance between two minutiae to consider
   *                       them as overlapping.
   * @param maxOrientation the maximum difference of orientation between two
   *                       minutiae to consider them as overlapping.
   * @return the number of overlapping minutiae.
   */
  public static int matchingMinutiaeCount(List<int[]> minutiae1, List<int[]> minutiae2, int maxDistance,
      int maxOrientation) {
	  //TODO implement
	  return 0;
  }

  /**
   * Compares the minutiae from two fingerprints.
   *
   * @param minutiae1 the list of minutiae of the first fingerprint.
   * @param minutiae2 the list of minutiae of the second fingerprint.
   * @return Returns <code>true</code> if they match and <code>false</code>
   *         otherwise.
   */
  public static boolean match(List<int[]> minutiae1, List<int[]> minutiae2) {
	  //TODO implement
	  return false;
  }

    /**
     * Checks if a pixel is black.
     *
     * @param value the boolean value associated to the pixel.
     * @return Returns <code>true</code> if the pixel is black and <code>false</code>
     *         otherwise.
     */
    public static boolean isPixelBlack(boolean value) {
        return (value);
    }

    /**
     * Checks if neighbours of a pixel exist.
     *
     * @param row the integer value associated to the row.
     * @param column the integer value associated to the column.
     * @return Returns <code>true</code> if the neighbours are null and <code>false</code>
     *         otherwise.
     */
    public static boolean areNeighboursNull(boolean[][] image, int row, int column){
        return getNeighbours(image, row, column) == null; //return true si neighbours[] est null
    }

    public static boolean[][] copyList(boolean[][] list){
        boolean [][] newList =  new boolean[list.length][list[0].length];
        for(int i = 0; i < list.length; ++i){
            for(int j = 0; j < list[0].length; ++j){
                newList[i][j] = list[i][j];
            }
        }
        return newList;
    }
}
