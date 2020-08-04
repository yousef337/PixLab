package pixLab;
import java.awt.*;
import java.awt.font.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.text.*;
import java.util.*;
import java.util.List; // resolves problem with java.awt.List and java.util.List

/**
 * A class that represents a picture.  This class inherits from 
 * SimplePicture and allows the student to add functionality to
 * the Picture class.  
 * 
 * @author Barbara Ericson ericson@cc.gatech.edu
 */
public class Picture extends SimplePicture 
{
  ///////////////////// constructors //////////////////////////////////
  
  /**
   * Constructor that takes no arguments 
   */
  public Picture ()
  {
    /* not needed but use it to show students the implicit call to super()
     * child constructors always call a parent constructor 
     */
    super();  
  }
  
  /**
   * Constructor that takes a file name and creates the picture 
   * @param fileName the name of the file to create the picture from
   */
  public Picture(String fileName)
  {
    // let the parent class handle this fileName
    super(fileName);
  }
  
  /**
   * Constructor that takes the width and height
   * @param height the height of the desired picture
   * @param width the width of the desired picture
   */
  public Picture(int height, int width)
  {
    // let the parent class handle this width and height
    super(width,height);
  }
  
  /**
   * Constructor that takes a picture and creates a 
   * copy of that picture
   * @param copyPicture the picture to copy
   */
  public Picture(Picture copyPicture)
  {
    // let the parent class do the copy
    super(copyPicture);
  }
  
  /**
   * Constructor that takes a buffered image
   * @param image the buffered image to use
   */
  public Picture(BufferedImage image)
  {
    super(image);
  }
  
  ////////////////////// methods ///////////////////////////////////////
  
  /**
   * Method to return a string with information about this picture.
   * @return a string with information about the picture such as fileName,
   * height and width.
   */
  public String toString()
  {
    String output = "Picture, filename " + getFileName() + 
      " height " + getHeight() 
      + " width " + getWidth();
    return output;
    
  }
  
  /** Method to set the blue to 0 */
  public void zeroBlue()
  {
    Pixel[][] pixels = this.getPixels2D();
    for (Pixel[] rowArray : pixels)
    {
      for (Pixel pixelObj : rowArray)
      {
        pixelObj.setBlue(0);
      }
    }
  }

  public void zeroRed()
  {
    Pixel[][] pixels = this.getPixels2D();
    for (Pixel[] rowArray : pixels)
    {
      for (Pixel pixelObj : rowArray)
      {
        pixelObj.setRed(0);
      }
    }
  }

  public void zeroGreen()
  {
    Pixel[][] pixels = this.getPixels2D();
    for (Pixel[] rowArray : pixels)
    {
      for (Pixel pixelObj : rowArray)
      {
        pixelObj.setGreen(0);
      }
    }
  }
  
  /** Method that mirrors the picture around a 
    * vertical mirror in the center of the picture
    * from left to right */
  public void mirrorVertical()
  {
    Pixel[][] pixels = this.getPixels2D();
    Pixel leftPixel = null;
    Pixel rightPixel = null;
    int width = pixels[0].length;
    for (int row = 0; row < pixels.length; row++)
    {
      for (int col = 0; col < width / 2; col++)
      {
        leftPixel = pixels[row][col];
        rightPixel = pixels[row][width - 1 - col];
        rightPixel.setColor(leftPixel.getColor());
      }
    } 
  }
  
  /** Mirror just part of a picture of a temple */
  public void mirrorTemple()
  {
    int mirrorPoint = 276;
    Pixel leftPixel = null;
    Pixel rightPixel = null;
    int count = 0;
    Pixel[][] pixels = this.getPixels2D();
    
    // loop through the rows
    for (int row = 27; row < 97; row++)
    {
      // loop from 13 to just before the mirror point
      for (int col = 13; col < mirrorPoint; col++)
      {
        
        leftPixel = pixels[row][col];      
        rightPixel = pixels[row]                       
                         [mirrorPoint - col + mirrorPoint];
        rightPixel.setColor(leftPixel.getColor());
      }
    }
  }
  
  /** copy from the passed fromPic to the
    * specified startRow and startCol in the
    * current picture
    * @param fromPic the picture to copy from
    * @param startRow the start row to copy to
    * @param startCol the start col to copy to
    */
  public void copy(Picture fromPic, 
                 int startRow, int startCol)
  {
    Pixel fromPixel = null;
    Pixel toPixel = null;
    Pixel[][] toPixels = this.getPixels2D();
    Pixel[][] fromPixels = fromPic.getPixels2D();
    for (int fromRow = 0, toRow = startRow; 
         fromRow < fromPixels.length &&
         toRow < toPixels.length; 
         fromRow++, toRow++)
    {
      for (int fromCol = 0, toCol = startCol; 
           fromCol < fromPixels[0].length &&
           toCol < toPixels[0].length;  
           fromCol++, toCol++)
      {
        fromPixel = fromPixels[fromRow][fromCol];
        toPixel = toPixels[toRow][toCol];
        toPixel.setColor(fromPixel.getColor());
      }
    }   
  }

  /** Method to create a collage of several pictures */
  public void createCollage()
  {
    Picture flower1 = new Picture("flower1.jpg");
    Picture flower2 = new Picture("flower2.jpg");
    this.copy(flower1,0,0);
    this.copy(flower2,100,0);
    this.copy(flower1,200,0);
    Picture flowerNoBlue = new Picture(flower2);
    flowerNoBlue.zeroBlue();
    this.copy(flowerNoBlue,300,0);
    this.copy(flower1,400,0);
    this.copy(flower2,500,0);
    this.mirrorVertical();
    this.write("collage.jpg");
  }
  
  
  /** Method to show large changes in color 
    * @param edgeDist the distance for finding edges
    */
  public void edgeDetection(int edgeDist)
  {
    Pixel leftPixel = null;
    Pixel rightPixel = null;
    Pixel[][] pixels = this.getPixels2D();
    Color rightColor = null;
    for (int row = 0; row < pixels.length; row++)
    {
      for (int col = 0; 
           col < pixels[0].length-1; col++)
      {
        leftPixel = pixels[row][col];
        rightPixel = pixels[row][col+1];
        rightColor = rightPixel.getColor();
        if (leftPixel.colorDistance(rightColor) > 
            edgeDist)
          leftPixel.setColor(Color.BLACK);
        else
          leftPixel.setColor(Color.WHITE);
      }
    }
  }
  
  
  /* Main method for testing - each class in Java can have a main 
   * method 
   */



  public static void main(String[] args)
  {
    Picture beach = new Picture("images/black_rock_shooter_diamond_eyes.jpg");
    //beach.explore();
    beach.quarter(beach);
    beach.explore();

    //Diagonal(beach);
    //beach.explore();
  }

  public void quarter(Picture p){

    Pixel[][] pixels = p.getPixels2D();
    for (int i = 0; i <pixels.length/2;i++){

      for (int ii = 0; ii <pixels[0].length/2;ii++) {
        pixels[i][ii].setBlue(0);
      }

    }

    for (int i = pixels.length-1; i > pixels.length/2;i--){

      for (int ii = 0; ii <pixels[0].length/2;ii++) {
        pixels[i][ii].setRed(0);
      }

    }

    for (int i = 0; i <pixels.length/2;i++){

      for (int ii = pixels[0].length-1; ii > pixels[0].length/2;ii--) {
        pixels[i][ii].setGreen(80);
      }

    }

    for (int i = pixels.length-1; i > pixels.length/2;i--){

      for (int ii = pixels[0].length-1; ii > pixels[0].length/2;ii--) {
        pixels[i][ii].setGreen(0);
      }

    }


  }

  public void sumBlue(Picture p){

    Pixel[][] pixels = p.getPixels2D();
    // get the highest
    int r = 0;
    for (int i = 0; i <pixels.length;i++){

      for (int ii = 0; ii <pixels[0].length;ii++) {

        pixels[i][ii].setBlue(r);

      }
      r++;
    }

  }

  public void sumGreen(Picture p){

    Pixel[][] pixels = p.getPixels2D();
    // get the highest
    int r = 0;
    for (int i = 0; i <pixels.length;i++){

      for (int ii = 0; ii <pixels[0].length;ii++) {

        pixels[i][ii].setGreen(r);

      }
      r++;
    }

  }

  public void sumRed(Picture p){

    Pixel[][] pixels = p.getPixels2D();
    // get the highest
    int r = 0;
    for (int i = 0; i <pixels.length;i++){

      for (int ii = 0; ii <pixels[0].length;ii++) {

        pixels[i][ii].setRed(r);

      }
      r++;
    }

  }

  public static void sharpner(Picture p){

    Pixel[][] pixels = p.getPixels2D();
    // get the highest
    for (int i = 0; i <pixels.length;i++){

      for (int ii = 0; ii <pixels[0].length;ii++) {

        pixels[i][ii].setBlue(pixels[i][ii].getBlue()/50);
        pixels[i][ii].setRed(pixels[i][ii].getRed()/50);
        pixels[i][ii].setGreen(pixels[i][ii].getGreen()/50);


        pixels[i][ii].setBlue(pixels[i][ii].getBlue()*50);
        pixels[i][ii].setRed(pixels[i][ii].getRed()*50);
        pixels[i][ii].setGreen(pixels[i][ii].getGreen()*50);

      }

    }

  }

  public static void grayScale2(Picture p){

  Pixel[][] pixels = p.getPixels2D();
  // get the highest
  for (int i = 0; i <pixels.length;i++){

    for (int ii = 0; ii <pixels[0].length;ii++) {
      int ave = (int) pixels[i][ii].getAverage();
      pixels[i][ii].setBlue(ave);
      pixels[i][ii].setRed(ave);
      pixels[i][ii].setGreen(ave);

    }

  }

}

  public static void grayScale3(Picture p){

    Pixel[][] pixels = p.getPixels2D();
    // get the highest
    for (int i = 0; i <pixels.length;i++){

      for (int ii = 0; ii <pixels[0].length;ii++) {

        int ave = (int) pixels[i][ii].getAverage();
        pixels[i][ii].setBlue(ave);
        pixels[i][ii].setRed(ave);
        pixels[i][ii].setGreen(ave);

      }

    }

    for(int a = 0; a < pixels.length-1;a++) {

      Random random = new Random();
      boolean true20 = (random.nextInt(10) == 0) ? true : false;
      if (true20) {
        for (int i = 0; i < pixels[0].length - 1; i++) {
          int ave = (int) pixels[a][i].getAverage();
          int g = getRandomNumberInRange(30,100);
          pixels[a][i].setBlue(g);
          pixels[a][i].setRed(g);
          pixels[a][i].setGreen(g);
          pixels[a+1][i].setBlue(g);
          pixels[a+1][i].setRed(g);
          pixels[a+1][i].setGreen(g);
        }
      }


    }

  }

  private static int getRandomNumberInRange(int min, int max) {

    if (min >= max) {
      throw new IllegalArgumentException("max must be greater than min");
    }

    Random r = new Random();
    return r.nextInt((max - min) + 1) + min;
  }

  public static void filterUp(Picture p){

    Pixel[][] pixels = p.getPixels2D();
    for (int i = 0; i <pixels.length/2;i++){

      for (int ii = 0; ii <pixels[0].length;ii++) {
        pixels[i][ii].setRed(0);
      }

    }

  }

  public static void filterSide(Picture p){

    Pixel[][] pixels = p.getPixels2D();
    for (int i = 0; i <pixels.length;i++){

      for (int ii = 0; ii <pixels[0].length/2;ii++) {
        pixels[i][ii].setBlue(0);
      }

    }

  }

  public static void negate(Picture p){

    Pixel[][] pixels = p.getPixels2D();
    for (Pixel[] rowArray : pixels){
      for (Pixel pixelObj : rowArray){
        pixelObj.setBlue(255-pixelObj.getBlue());
        pixelObj.setRed(255-pixelObj.getRed());
        pixelObj.setGreen(255-pixelObj.getGreen());
      }
    }

  }

  public static void grayScale(Picture p) {

    Pixel[][] pixels = p.getPixels2D();
    for (Pixel[] rowArray : pixels) {
      for (Pixel pixelObj : rowArray) {
        pixelObj.setBlue(pixelObj.getBlue() / 3);
        pixelObj.setRed(pixelObj.getRed() / 3);
        pixelObj.setGreen(pixelObj.getGreen() / 3);
      }
    }
  }

    public static void fixBobbyFishes(Picture p){

      Pixel[][] pixels = p.getPixels2D();
      for (Pixel[] rowArray : pixels){
        for (Pixel pixelObj : rowArray){
          pixelObj.setBlue(pixelObj.getBlue()+10);
          pixelObj.setRed(pixelObj.getRed()+140);
          pixelObj.setGreen(pixelObj.getGreen()+10);
        }
      }

  }

  public static void bottomToUp(Picture p){

    Pixel[][] pixels = p.getPixels2D();
    for (int i = 0; i <pixels.length/2;i++){

        for (int ii = 0; ii <pixels[0].length;ii++) {
          pixels[i][ii].setBlue(pixels[599-i][ii].getBlue());
          pixels[i][ii].setRed(pixels[599-i][ii].getRed());
          pixels[i][ii].setGreen(pixels[599-i][ii].getGreen());
        }

      }


  }

  public static void topToBottom(Picture p){

    Pixel[][] pixels = p.getPixels2D();
    for (int i = 0; i <pixels.length/2;i++){

      for (int ii = 0; ii <pixels[0].length;ii++) {
        pixels[599-i][ii].setBlue(pixels[i][ii].getBlue());
        pixels[599-i][ii].setRed(pixels[i][ii].getRed());
        pixels[599-i][ii].setGreen(pixels[i][ii].getGreen());
      }

    }

  }

  public static void leftToRight(Picture p){

    Pixel[][] pixels = p.getPixels2D();
    for (int i = 0; i <pixels.length;i++){

      for (int ii = 0; ii <pixels[0].length/2;ii++) {
        pixels[i][ii].setBlue(pixels[i][799-ii].getBlue());
        pixels[i][ii].setRed(pixels[i][799-ii].getRed());
        pixels[i][ii].setGreen(pixels[i][799-ii].getGreen());
      }

    }

  }

  public static void Diagonal(Picture p){

    Pixel[][] pixels = p.getPixels2D();

    for (int i = 0; i <pixels.length;i++){

      for (int ii = 0; ii <pixels[0].length;ii++) {

        pixels[ii][i].setBlue(pixels[i][ii].getBlue());
        pixels[ii][i].setRed(pixels[i][ii].getRed());
        pixels[ii][i].setGreen(pixels[i][ii].getGreen());
      }


    }

  }


  /*public static void autoSelectedPaste(Picture p){

    Pixel[][] pixels = p.getPixels2D();
    int t = -3;
    for (int i = 0; i < pixels.length-5;i++){

      for (int ii = 0; ii < pixels[0].length-5;ii++) {

          if( (pixels[i][ii].getBlue()+t <= pixels[i+1][ii].getBlue() && pixels[i][ii].getBlue()+t <= pixels[i+2][ii].getBlue() && pixels[i][ii].getBlue()+t <= pixels[i+3][ii].getBlue()  && pixels[i][ii].getBlue()+t <= pixels[i+4][ii].getBlue() && pixels[i][ii].getBlue()+t <= pixels[i+5][ii].getBlue())
                  && (pixels[i][ii].getRed()+t <= pixels[i+1][ii].getRed() && pixels[i][ii].getRed()+t <= pixels[i+2][ii].getRed() && pixels[i][ii].getRed()+t <= pixels[i+3][ii].getRed() && pixels[i][ii].getRed()+t <= pixels[i+4][ii].getRed() && pixels[i][ii].getRed()+t <= pixels[i+5][ii].getRed())
                  && (pixels[i][ii].getGreen()+t <= pixels[i+1][ii].getGreen() && pixels[i][ii].getGreen()+t <= pixels[i+2][ii].getGreen() && pixels[i][ii].getGreen()+t <= pixels[i+3][ii].getGreen()) && pixels[i][ii].getGreen()+t <= pixels[i+4][ii].getGreen() && pixels[i][ii].getGreen()+t <= pixels[i+5][ii].getGreen()  ) {
            pixels[i][ii].setGreen(0);
            pixels[i][ii].setRed(0);
            pixels[i][ii].setBlue(0);
          }

      }


    }

  }
  */


  public static void println(Object o){
    System.out.println(o);
  }
  public static void print(Object o){
    System.out.print(o);
  }
  
} // this } is the end of class Picture, put all new methods before this
