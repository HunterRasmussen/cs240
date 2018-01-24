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
		SpellCorrector corrector2 = new SpellCorrector();
		corrector2.useDictionary(dictionaryFileName);
		//corrector2.ourDictionaryTrie.add("birx");
		corrector.ourDictionaryTrie.add("birzz");
		boolean checkEquals = corrector.ourDictionaryTrie.equals(corrector2.ourDictionaryTrie);
		if(checkEquals){
		System.out.println("Those two tries are equal");
		}
		else {
			System.out.println("Those two tries are not equal");
		}
		System.out.println(corrector2.ourDictionaryTrie.toString());
		/*Trie.Node foundWord = corrector.ourDictionaryTrie.find("fred");
		if (foundWord != null){
			System.out.println("Found that word! Whee.");
		}
		else{
			System.out.println("Didnt find that word booo.");
		}*/
		String suggestion = corrector.suggestSimilarWord(inputWord);
		if (suggestion == null) {
		    suggestion = "No similar word found";
		}

		System.out.println("Suggestion is: " + suggestion);
	}

}
