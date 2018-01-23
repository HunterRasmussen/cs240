
package image_editor2018;
import java.util.*;
import java.io.*;


public class Image{

  int height;
  int width;
  Pixel[][] raster;
  public Image (int width, int height){
    this.height = height;
    this.width = width;
    raster = new Pixel[width][height];
  }

  public void addPixel(int widthPosition, int heightPosition, Pixel toAdd){
    raster[widthPosition][heightPosition] = toAdd;
  }

  public Image add_pixels(Scanner in, Image image, int width, int height){

    int widthPosition = 0;
    int heightPosition = 0;
    while (in.hasNext()){
      int rValue = -1;
      int gValue = -1;
      int bValue = -1;
      if (in.hasNext("#")){
        System.out.println(in.nextLine());
      }

      else{
        rValue = in.nextInt();
        gValue = in.nextInt();
        bValue = in.nextInt();
      }
      Pixel toAdd = new Pixel(rValue, gValue, bValue);
      image.addPixel(widthPosition, heightPosition, toAdd);
      if (widthPosition < width-1){
        widthPosition++;
      }
      else if (widthPosition == width-1){
        widthPosition = 0;
        heightPosition++;
      }
      if(heightPosition > 748 && widthPosition > 990){
        //System.out.println("Height Position = " + heightPosition + " and width Position = " + widthPosition);
        //System.out.println("R value =" + rValue + "   gValue =" + gValue + "   bValue =" + bValue);
      }
    }
    return image;
  }


  public void printImage(String filename){
    PrintWriter outfile;
    try {
      outfile = new PrintWriter(filename);
    }
    catch (FileNotFoundException e) {
      System.out.println("File not found exception!!!");
      return;
    }
    outfile.println("P3");
    outfile.println(width + " " + height);
    outfile.println("255");
    for(int i = 0; i < height; i++){
      outfile.println("#Row number:" + i);
      for (int j= 0; j < width; j++){
        outfile.println(raster[j][i].redValue);
        outfile.println(raster[j][i].greenValue);
        outfile.println(raster[j][i].blueValue);
        if(i > 748 && (j > 995 || j < 5)){
        //System.out.println("Height position = " + i + "  and width position = " + j);
        //System.out.println("Rvalue = " + raster[j][i].redValue + "  Gvalue = " +
          //raster[j][i].greenValue + "  Blue value = " + raster[j][i].blueValue);
        }
      }
    }
    outfile.flush();
    outfile.close();

  }

  public Image invert(){
    Image edited = new Image(this.width, this.height);
    for(int i = 0; i < height; i++){
      for (int j= 0; j < width; j++){
        int rValue = 255 - this.raster[j][i].redValue;
        int gValue = 255 - this.raster[j][i].greenValue;
        int bValue = 255 - this.raster[j][i].blueValue;
        Pixel toAdd = new Pixel(rValue, gValue, bValue);
        edited.addPixel(j,i,toAdd);
      }
    }
    return edited;
  }

  public Image greyscale(){
    Image edited = new Image(this.width, this.height);
    for(int i = 0; i < height; i++){
      for (int j= 0; j < width; j++){
        int greyValue = this.raster[j][i].redValue + this.raster[j][i].greenValue + this.raster[j][i].blueValue;
        greyValue = greyValue/3;
        Pixel toAdd = new Pixel(greyValue, greyValue, greyValue);
        edited.addPixel(j,i,toAdd);
      }
    }

    return edited;

  }

  public Image emboss(){
    Image edited = new Image(this.width, this.height);
    for(int i = height-1; i > -1; i--){
      for (int j= width-1; j > -1; j--){
        int greatestDiff = -100000;
        if(j != 0 && i != 0){
          int redDiff = this.raster[j][i].redValue - this.raster[j-1][i-1].redValue;
          int greenDiff = this.raster[j][i].greenValue - this.raster[j-1][i-1].greenValue;
          int blueDiff = this.raster[j][i].blueValue - this.raster[j-1][i-1].blueValue;
          greatestDiff = redDiff;
          if (java.lang.Math.abs(greatestDiff) < java.lang.Math.abs(greenDiff)){
            greatestDiff = greenDiff;
          }
          if(java.lang.Math.abs(greatestDiff) < java.lang.Math.abs(blueDiff)){
            greatestDiff = blueDiff;
          }
          greatestDiff = greatestDiff + 128;
          if(greatestDiff < 0){
            greatestDiff = 0;
          }
          if(greatestDiff > 255){
            greatestDiff = 255;
          }
        }
        else{
          greatestDiff = 128;
        }
        Pixel toAdd = new Pixel(greatestDiff, greatestDiff, greatestDiff);
        edited.addPixel(j,i,toAdd);
      }
    }
    return edited;

  }

  public Image motionBlur(int blurLength){
    Image edited = new Image(this.width, this.height);
    for(int i = 0; i < height; i++){
      for (int j= 0; j < width; j++){
        int redAverage = this.raster[j][i].redValue;
        int greenAverage = this.raster[j][i].greenValue;
        int blueAverage = this.raster[j][i].blueValue;
        int count = 1;
        for(int k = 1; k < blurLength; k++){
          if(j+k > width-1){
            break;
          }
            count++;
            redAverage = redAverage + this.raster[j+k][i].redValue;
            greenAverage = greenAverage + this.raster[j+k][i].greenValue;
            blueAverage = blueAverage + this.raster[j+k][i].blueValue;


        }
      //  if(count > 0){
          redAverage   = redAverage/(count);
          greenAverage = greenAverage/(count);
          blueAverage  = blueAverage/(count);
        //}
        Pixel toAdd = new Pixel(redAverage, greenAverage, blueAverage);
        edited.addPixel(j,i,toAdd);
      }
    }
    return edited;

  }
/*
  (j + blurLength < width-1){
    redAverage   = redAverage/(count-1);
    greenAverage = greenAverage/(count-1);
    blueAverage  = blueAverage/(count-1);
  }
  if(count <2){
    redAverage   = redAverage/(count);
    greenAverage = greenAverage/(count);
    blueAverage  = blueAverage/(count);
  }
  else{
    redAverage   = redAverage/(count-1);
    greenAverage = greenAverage/(count-1);
    blueAverage  = blueAverage/(count-1);

*/

}
