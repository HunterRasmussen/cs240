package image_editor2018;
import java.io.*;
import java.util.*;






//  compile command   javac -d . *.java

//  run command  java image_editor2018.Editor


public class ImageEditor {

  public static void main(String[] args){

    int width = -1;
    int height = -1;
    int max_value = 0;
    Scanner in;
    try{
      in = new Scanner(new File(args[0]));
    }
    //if args[1]
    catch(FileNotFoundException e){
      System.out.println("File not found.  Please input a valid file destination");
      return;
    }
    if (in.hasNext("P3")){
      in.next();
    }
    while (in.hasNext("#")){
      in.nextLine();
    }
    if (in.hasNextInt()){
      width = in.nextInt();
    }
    while (in.hasNext("#")){
      System.out.println(in.nextLine());
    }
    if(in.hasNextInt()){
      height = in.nextInt();
    }
    while (in.hasNext("#")){
      System.out.println(in.nextLine());
    }
    if(in.hasNextInt()){
      max_value = in.nextInt();
    }
    while(in.hasNext("#")){
      in.nextLine();
    }
    //System.out.println("About to create a new image.  Width value = " + width);
    //System.out.println("About to create a new image.  Height value = " + height);
    //System.out.println("About to create a new image.  Max value = " + max_value);

    Image image_to_edit = new Image(width, height);
    image_to_edit = image_to_edit.add_pixels(in, image_to_edit, width, height);
    String editType = args[2];
    System.out.println("Edit type is " + editType);
    if (editType.equals("grayscale")){
      image_to_edit = image_to_edit.greyscale();
    }
    else if (editType.equals("invert")){
      image_to_edit = image_to_edit.invert();
    }
    else if(editType.equals("emboss")){
      image_to_edit = image_to_edit.emboss();
    }
    else if(editType.equals("motionblur")){
    int blurLength = Integer.parseInt(args[3]);
    image_to_edit = image_to_edit.motionBlur(blurLength);
    }
    else{
      System.out.println("Invalid edit type.  Type in grayscale, invert, emboss, or motionblur along with blur length");
      return;
    }
    image_to_edit.printImage(args[1]);

  }

}
