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

    if(dictionary.find(inputWord) != null){
      return inputWord;
    }
    Set<String> suggestions= editDistanceOfOne(inputWord);
    Set<String> temp = new TreeSet<String>();
    temp = findWordsinTrie(suggestions);
    if(temp.size() > 0){
      return findMostSimilar(temp);
    }
    suggestions = editDistanceOfTwo(suggestions);
    suggestions = findWordsinTrie(suggestions);
    if(suggestions.size() > 0){
      return findMostSimilar(suggestions);
    }
    else{
      return null;
    }
  }


  Set<String> editDistanceOfOne(String word){

    Set<String> suggestions = new TreeSet<String>();
    Set<String> temp = new TreeSet<String>();
    temp = deleteCharacter(word);
    if(temp.size() > 0){
      suggestions.addAll(temp);
    }
    temp = transposeCharacter(word);
    if(temp.size() > 0){
      suggestions.addAll(temp);
    }
    temp = alterationEdit(word);
    if(temp.size() > 0){
      suggestions.addAll(temp);
    }
    temp = insertionEdit(word);
    if(temp.size() > 0){
      suggestions.addAll(temp);
    }
    return suggestions;


  }

  Set<String> editDistanceOfTwo(Set<String> suggestions){
    Set<String> toReturn = new TreeSet<String>();
    for(String word: suggestions){
      Set<String> temp = editDistanceOfOne(word);
      if(temp.size() > 0){
        toReturn.addAll(temp);
      }
    }
    return toReturn;
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

    Set<String> toReturn = new TreeSet<String>();
    for(int i = 0; i < word.length()-1; i++){
      StringBuilder editedWord = new StringBuilder(word);
      String toAdd = swapChars(editedWord,i,i+1);
      System.out.println("In transpose, adding the following word to suggested words: " +  toAdd);
      toReturn.add(toAdd);
    }



    return toReturn;
  }

  Set<String> alterationEdit(String word){
    Set<String> toReturn = new TreeSet<String>();
    for(int i = 0; i < word.length(); i++){
      for(int j =0; j < 26; j++){
        String convertedNumber = dictionary.numberToChar(j);
        StringBuilder editedWord = new StringBuilder(word);
        editedWord.setCharAt(i, convertedNumber.charAt(0));
        toReturn.add(editedWord.toString());
        System.out.println("Adding the following word from alteration: " + editedWord.toString());
      }
    }
    return toReturn;
  }

  Set<String> insertionEdit(String word){
    Set<String> toReturn = new TreeSet<String>();
    for(int i = 0; i <= word.length(); i ++){
      for(int j = 0; j < 26; j++){
        StringBuilder editedWord = new StringBuilder(word);
        String convertedNumber = dictionary.numberToChar(j);
        editedWord.insert(i,convertedNumber.charAt(0));
        toReturn.add(editedWord.toString());
        System.out.println("In insertionEdit addint the following word: " + editedWord.toString());
      }
    }
    return toReturn;
  }


  String swapChars(StringBuilder word, int position1, int position2){
    StringBuilder temp = new StringBuilder(word);
    word.setCharAt(position1,word.charAt(position2));
    word.setCharAt(position2,temp.charAt(position1));
    return word.toString();
  }


  Set<String> findWordsinTrie(Set<String> possibleWords){
    Set<String> toReturn = new TreeSet<String>();
    for(String word:  possibleWords){
      if(dictionary.find(word) != null){
        toReturn.add(word);
      }
    }
    return toReturn;
  }

  String findMostSimilar(Set<String> possibleWords){
    int highestCount = 0;
    List<String> toReturn = new ArrayList<String>();
    for(String word: possibleWords){
      Trie.Node temp =dictionary.find(word);
      if (temp.count > highestCount){
        highestCount = temp.count;
        toReturn.clear();
        toReturn.add(word);
      }
      else if(temp.count == highestCount){
        toReturn.add(word);
      }
    }
    if(toReturn.size() == 1){
      return toReturn.get(0);
    }
    Collections.sort(toReturn);
    return toReturn.get(0);



  }







}
