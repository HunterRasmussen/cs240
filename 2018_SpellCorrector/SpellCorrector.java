package spell;

import java.io.IOException;
import java.util.*;
import java.io.*;

public class SpellCorrector implements ISpellCorrector {


  public Trie ourDictionaryTrie;
	/**
	 * Tells this <code>SpellCorrector</code> to use the given file as its dictionary
	 * for generating suggestions.
	 * @param dictionaryFileName File containing the words to be used
	 * @throws IOException If the file cannot be read
	 */
	public void useDictionary(String dictionaryFileName) throws IOException{
    File dictionary = new File(dictionaryFileName);
    ourDictionaryTrie = new Trie();
    if(!dictionary.canRead() || !dictionary.exists()){
      System.out.println("Invalid dictionary file name.");
      throw new IOException();
    }
    Scanner in = new Scanner(dictionary);
    while (in.hasNext()){
      String word = in.next();
      word.toLowerCase();
      boolean nonLetterFlag =checkForNonLetter(word);
      for (int i = 0; i < word.length(); i++){
        char c = word.charAt(i);
        if (!((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z'))){
          nonLetterFlag = true;
        }
      }
      if (nonLetterFlag == false){
        ourDictionaryTrie.add(word);
      }
    }
  }

	/**
	 * Suggest a word from the dictionary that most closely matches
	 * <code>inputWord</code>
	 * @param inputWord
	 * @return The suggestion or null if there is no similar word in the dictionary
	 */
	public String suggestSimilarWord(String inputWord){
    inputWord =  inputWord.toLowerCase();
    boolean nonLetterFlag = checkForNonLetter(inputWord);
    if (nonLetterFlag == true){
      System.out.println("Invalid characters in the word. Type a word that consists of only letters");
      return null;
    }
    if(ourDictionaryTrie.find(inputWord) != null){
      return inputWord;
    }
    Set<String> possibleWords = editDistance1(inputWord); //go get all words with edit distance of one
    Set<String> possibleWordsinTrie = findWordsinTrie(possibleWords); //find which of those words are in the Trie Dictionary
    // are there any words with edit distance one in our dictinary?
    if(possibleWordsinTrie.size()>0){
      return findMostSimilar(possibleWordsinTrie);
    }
    //We didn't find any words with edit distance one.  Time to edit distance 2
    Set<String> possibleWordsDistance2 = new TreeSet<String>();
    System.out.println("We went through edits once and found no similar word. Running through all the edits again");
    for(String word : possibleWords){
      Set<String> temp = editDistance1(word);
      if(temp != null){
        possibleWordsDistance2.addAll(temp);
      }
    }
    possibleWordsinTrie = findWordsinTrie(possibleWordsDistance2);
    if(possibleWordsinTrie.size() <1){
      return null;
    }
    return findMostSimilar(possibleWordsinTrie);
  }

  /**
  * Checks a string for any character that isn't a letter.
  * Returns true if it finds any characters that aren't letters
  * Returns false if the String is only letters
  * @param String
  */
  private boolean checkForNonLetter(String word){
    boolean nonLetterFlag = false;
    for (int i = 0; i < word.length(); i++){
      char c = word.charAt(i);
      if (!((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z'))){
        nonLetterFlag = true;
      }
    }
    return nonLetterFlag;
  }

  private Set<String> findWordsinTrie(Set<String> possibleWords){
    Set<String> wordsInTrie = new TreeSet<String>();
    for(String word : possibleWords){
      if(ourDictionaryTrie.find(word) != null){
        //System.out.println("Found the following word in our trie and adding it: " + word);
        wordsInTrie.add(word);
      }
    }
    //System.out.println("Leaving findWordsinTrie.  The size of the set leaving is " + wordsInTrie.size());
    return wordsInTrie;
  }


  private Set<String> editDistance1(String word){
    Set<String> possibleWords = new TreeSet<String>();
    Set<String> temp = deleteEdit(word);
    possibleWords.addAll(temp);
    temp = transposeEdit(word);
    if(temp != null){
      possibleWords.addAll(temp);
    }
    temp = alterationEdit(word);
    if(temp != null){
      possibleWords.addAll(temp);
    }
    temp = insertionEdit(word);
    if(temp != null){
      possibleWords.addAll(temp);
    }
    return possibleWords;
  }

  private String editDistance2(String word){
    //Set<String> temp = deleteTwoEdit(word);


    return null;

  }


  private Set<String> deleteEdit(String word){
    Set<String> possibleWords = new TreeSet<String>();
    for (int i = 0; i < word.length(); i++){
      StringBuilder potentialWord = new StringBuilder(word);
      potentialWord.deleteCharAt(i);
      //System.out.println("Adding the following potential word from Delete to our Set: " + potentialWord.toString());
      possibleWords.add(potentialWord.toString());
    }
    return possibleWords;
  }

  private Set<String> alterationEdit(String word){
    Set<String> toReturn = new TreeSet<String>();
    for(int i = 0; i < word.length(); i++){
      StringBuilder editedWord = new StringBuilder(word);
      for (int j = 0; j < 26; j++){
        char toInsert = numberToChar(j);
        editedWord.setCharAt(i,toInsert);
        //System.out.println("Adding the following from alteration to our Set " + editedWord.toString());
        toReturn.add(editedWord.toString());
      }
    }
    return toReturn;
  }

  private Set<String> insertionEdit(String word){
    Set<String> toReturn = new TreeSet<String>();
    for (int i = 0; i < word.length()+1; i++){
      for( int j = 0; j < 26; j ++){
        char toInsert = numberToChar(j);
        StringBuilder wordToEdit = new StringBuilder(word);
        wordToEdit.insert(i,toInsert);
        //System.out.println("Word To Edit = " + wordToEdit );
        //System.out.println("Adding the following from insertion to our Set " + wordToEdit.toString());
        toReturn.add(wordToEdit.toString());
      }
    }
    return toReturn;
  }

  private Set<String> transposeEdit(String word){
    Set<String> toReturn = new TreeSet<String>();
    for(int i = 0; i < word.length()-1; i++){
      StringBuilder wordToEdit = new StringBuilder(word);
      String editedWord = swapChars(wordToEdit, i, i+1);
      //System.out.println("Adding the following transposed word: " + editedWord);
      toReturn.add(editedWord);
    }
    return toReturn;
  }






  private String findMostSimilar(Set<String> possibleWords){
    int currentHighCount = 0;
    List<String> closestWords = new ArrayList<String>();
    for(String word: possibleWords){
      Trie.Node temp = ourDictionaryTrie.find(word);
      if(temp.getCount() > currentHighCount){
        currentHighCount = temp.getCount();
        closestWords = new ArrayList<String>();
        closestWords.add(word);
      }
      if(temp.getCount() == currentHighCount){
        closestWords.add(word);
      }
    }
    Collections.sort(closestWords);
    return closestWords.get(0); //return the firstMost alphabetically or the only one in the set.
  }

  private String swapChars(StringBuilder wordToEdit, int position1, int position2){
    char temp = wordToEdit.charAt(position1);
    wordToEdit.setCharAt(position1, wordToEdit.charAt(position2));
    wordToEdit.setCharAt(position2, temp);
    return wordToEdit.toString();
  }

    private char numberToChar(int number){
      if (number > -1 && number < 26){
        return (char)(number+'a');
      }
      else{
        return Character.MIN_VALUE;
      }
    }





}
