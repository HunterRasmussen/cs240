package spell;

import java.util.*;
import java.lang.*;
import java.io.*;



public class SpellCorrector implements ISpellCorrector{

  public void useDictionary(String dictionaryFileName) throws IOException{

    try{
      Scanner in = new Scanner(new File(dictionaryFileName));
      Trie dictionary = new Trie();
      while(in.hasNext()){
        String toAdd  = in.next();
        toAdd = toAdd.toLowerCase();
        dictionary.add(toAdd);
      }
    }
    catch(FileNotFoundException e){
      System.out.println("Invalid file name or path");
      throw new IOException();
    }


  }


  public String suggestSimilarWord(String inputWord){



    return null;
  }

}
