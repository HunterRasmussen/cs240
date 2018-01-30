package hangman;

import java.util.*;
import java.io.*;
import hangman.IEvilHangmanGame.GuessAlreadyMadeException;

public class EvilHangmanGame implements IEvilHangmanGame{

  public EvilHangmanGame(){
    currentPartition = new HashSet<String>();
    guessesMade = new TreeSet<Character>();
    previousPattern = "-1";
  }

  Set<String> currentPartition;
  Set<Character> guessesMade;
  String previousPattern;
  /**
   * Starts a new game of evil hangman using words from <code>dictionary</code>
   * with length <code>wordLength</code>.
   *	<p>
   *	This method should set up everything required to play the game,
   *	but should not actually play the game. (ie. There should not be
   *	a loop to prompt for input from the user.)
   *
   * @param dictionary Dictionary of words to use for the game
   * @param wordLength Number of characters in the word to guess
   */
  public void startGame(File dictionary, int wordLength){
    Scanner in;
    try{
      in = new Scanner(dictionary);
      while (in.hasNext()){
        String word = in.next();
        if (word.length() == wordLength){
          word.toLowerCase();
          currentPartition.add(word);
        }
      }
      in.close();
    }
    catch(FileNotFoundException e){
      System.out.println("Invalid File. Scanner couldn't read");
    }

  }



  /**
   * Make a guess in the current game.
   *
   * @param guess The character being guessed
   *
   * @return The set of strings that satisfy all the guesses made so far
   * in the game, including the guess made in this call. The game could claim
   * that any of these words had been the secret word for the whole game.
   *
   * @throws GuessAlreadyMadeException If the character <code>guess</code>
   * has already been guessed in this game.
   */
  public Set<String> makeGuess(char guess) throws GuessAlreadyMadeException{
    if(guessesMade.size() ==0){
      guessesMade.add(guess);
    }
    else{
      if(guessesMade.contains(guess)){
        throw new GuessAlreadyMadeException();
      }
      guessesMade.add(guess);
    }

    Map<String, TreeSet<String>> partitions = new HashMap<String, TreeSet<String>>();
    for (String word : currentPartition){
      String currentPattern = createPattern(word, guess, this.previousPattern);
      if (partitions.containsKey(currentPattern)){
        partitions.get(currentPattern).add(word);
      }
      else{
        partitions.put(currentPattern,new TreeSet<String>());
        partitions.get(currentPattern).add(word);
      }
    }

    List<String> possiblePatterns = check1(partitions);
    if (possiblePatterns.size() == 1){
      return partitions.get(possiblePatterns.get(0));
    }
    return partitions.get(possiblePatterns.get(0));
  }

    /*
   1. Choose the group in which the letter does not appear at all.
   2. If each group has the guessed letter, choose the one with the
   fewest letters.
   3. If this still has not resolved the issue, choose the one with the
   rightmost guessed letter.
   4. If there is still  more than one group, choose the one with the next
   rightmost letter.  Repeat this step (step 4) until a group is
   chosen.
  */



  // Chooses the largest group
  private List<String> check1(Map<String, TreeSet<String>> partitions){
    List<String> largestPartitions = new ArrayList<String>();
    int biggestSize =0;
    for(String pattern : partitions.keySet()){
      if(partitions.get(pattern).size() > biggestSize){
        largestPartitions.clear();
        largestPartitions.add(pattern);
        biggestSize = partitions.get(pattern).size();
      }
      else if(partitions.get(pattern).size() == biggestSize){
        largestPartitions.add(pattern);
      }
    }
    return largestPartitions;
  }





  public Character getGuess() {
    Scanner s = new Scanner(System.in);
    String guessString= s.next();
    if (guessString.length() >  1){
      return Character.MIN_VALUE;
    }
    guessString = guessString.toLowerCase();
    char guess = guessString.charAt(0);
    if (guess < 'a' || guess > 'z'){
      return Character.MIN_VALUE;
    }
    return guess;
  }

  public Boolean checkWin(Set<String> words){
      if (words.size() > 1){
        return false;
      }
      String toCheck = "";
      for(String word : words){
        toCheck = word;
        break;
      }
      for(int i = 0; i < toCheck.length(); i++){
        if(!guessesMade.contains(toCheck.charAt(i))){
          return false;
        }
      }
      return true;
  }

  private String createPattern(String word, char guess, String previousPattern){
    StringBuilder patternToReturn = new StringBuilder();
    if (previousPattern != "-1"){
      for(int i = 0; i < word.length(); i++){
        if(previousPattern.charAt(i) != '_'){
          patternToReturn.append(previousPattern.charAt(i));
        }
        else if(word.charAt(i) == guess){
          patternToReturn.append(guess);
        }
        else{
          patternToReturn.append('_');
        }
      }
    }
    else{
      for(int i = 0; i < word.length(); i++){
        if(word.charAt(i) == guess){
          patternToReturn.append(guess);
        }
        else{
          patternToReturn.append('_');
        }
      }
    }
    return patternToReturn.toString();
  }
}
