package spell;

import java.util.*;
import java.lang.*;
import java.io.*;



public class SpellCorrector implements ISpellCorrector{


  Trie dictionary;

  public void useDictionary(String dictionaryFileName) throws IOException{

    try{
      Scanner in = new Scanner(new File(dictionaryFileName));
      dictionary = new Trie();
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

    if(dictionary.find(inputWord != null)){
      return inputWord;
    }
    Set<String> suggestions= editDistanceOfOne(inputWord);




    return null;
  }


  Set<String> editDistanceOfOne(String word){

    Set<String> suggestions = new TreeSet<String>();
    Set<String> temp = new TreeSet<String>();
    temp = deleteCharacter(word);
    if(temp.size() > 0){
      suggestions.addAll(temp);
    }
    temp = transposeCharacter(word);


  }




  public Set<String> deleteCharacter(String word){
    Set<String> toReturn = new TreeSet<String>();
    System.out.println("Why?");
    for(int i =0; i<word.length(); i++){
      StringBuilder editedWord = new StringBuilder(word);
      editedWord.deleteCharAt(i);
      System.out.println("Adding the following to suggested words " + editedWord.toString());
      toReturn.add(editedWord.toString());
    }
    return toReturn;
  }


  Set<String> transposeCharacter(String word){

    StringBuilder editedWord = new StringBuilder(word);
    for(int i = 0; i < word.length(); i++){
      editedWord.swapChars()
    }




  }
















}
