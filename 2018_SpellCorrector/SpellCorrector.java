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
      boolean nonLetterFlag = false;
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
    return null;
  }

}
