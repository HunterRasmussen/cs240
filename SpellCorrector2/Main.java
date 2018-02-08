package spell;

import java.io.IOException;

/**
 * A simple main class for running the spelling corrector. This class is not
 * used by the passoff program.
 */
public class Main {

	/**
	 * Give the dictionary file name as the first argument and the word to correct
	 * as the second argument.
	 */
	public static void main(String[] args) throws IOException {


		String dictionaryFileName = args[0];
		String inputWord = args[1];

		/**
		 * Create an instance of your corrector here
		 */
		SpellCorrector corrector = new SpellCorrector();
		corrector.useDictionary(dictionaryFileName);
		// corrector.dictionary.add("boot");
    // SpellCorrector corrector2 = new SpellCorrector();
    // corrector2.useDictionary(dictionaryFileName);
    // if(corrector.dictionary.equals(corrector2.dictionary)){
    //   System.out.println("Yes.  those two are eqaul");
    // }
    // else{
    //   System.out.println("No, those two are not equal");
    // }
		String suggestion = corrector.suggestSimilarWord(inputWord);
		if (suggestion == null) {
		    suggestion = "No similar word found";
		}

		System.out.println("You typed in: " + inputWord);
		System.out.println("Suggestion is: " + suggestion);
	}

}
