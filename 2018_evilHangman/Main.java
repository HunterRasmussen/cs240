package hangman;

import java.io.*;
import java.util.*;

import hangman.IEvilHangmanGame.GuessAlreadyMadeException;

public class Main{


  public static void main(String[] args){

    File dictionary = new File(args[0]);
    if (!dictionary.exists() || !dictionary.canRead()){
      System.out.println("Invalid file.  Input a vaild file path");
      return;
    }
    int wordLength = Integer.parseInt(args[1]);
    int guessesLeft = Integer.parseInt(args[2]);

    EvilHangmanGame theGame = new EvilHangmanGame();
    theGame.startGame(dictionary, wordLength);

    System.out.println("Welcome to Hangman");
    Set<Character> guessesMade = new TreeSet<Character>();
    while (guessesLeft > 0){
      System.out.println("Guesses left: " + guessesLeft);
      System.out.print("Guesses Made: ");
      for(char c : guessesMade){
        System.out.print(c + ", ");
      }
      System.out.println();
      System.out.println("--------------------------------");
      System.out.println();
      System.out.println("Please make a guess");
      char guess = theGame.getGuess(); //interacts with system.in and gets a char.

      if (guess == Character.MIN_VALUE){
        System.out.println("Invalid guess.  Please enter a character from a to z");
      }
      else{
        guessesMade.add(guess);
        Set<String> possibleWords = new TreeSet<String>();
        try{
          possibleWords = theGame.makeGuess(guess);
        }
        catch(GuessAlreadyMadeException e){
          System.out.println("You already made that guess.  Pick a letter you haven't guessed");
        }
        if(possibleWords.size() == 1){
          boolean winFlag = theGame.checkWin(possibleWords);
          if(winFlag){
            for(String word: possibleWords){
              System.out.println("Congratulations.  You won.  The word was: " + word);
              return;
            }
          }
        }
      }
    }
    System.out.println();
    System.out.println();
    System.out.println();
    System.out.println("YOU LOSE");

  }



}
