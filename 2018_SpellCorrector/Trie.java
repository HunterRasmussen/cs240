package spell;

class Trie implements ITrie{

  public Trie(){
    rootNode = new Node();
    wordCount = 0;
    nodeCount= 0;
  }
  private int wordCount;
  private int nodeCount;
  Node rootNode;


  public void add(String word){
    Node p = rootNode;
    for (int i = 0; i < word.length(); i++){
      int index = word.charAt(i)-'a';
      if (p.nodes[index] == null){
        Node temp = new Node();
        nodeCount++;
        p.nodes[index] = temp;
        p = temp;
      }
      else {
        p = p.nodes[index];
      }
    }
    if (p.count == 0){
      wordCount++;
    }
    p.count += 1;
    //System.out.println("Current count for the word " + word + " is: " + p.count);
    //System.out.println("Total unique word count is: " + wordCount);
  }



  public Node find(String word){
    Node p = rootNode;
    for (int i = 0; i < word.length(); i++){
      int index = word.charAt(i) - 'a';
      if(p.nodes[index] == null){
        return null;
      }
      p = p.nodes[index];
    }
    if (p.count > 0){
      return p;
    }
    return null;
  }

  public int getWordCount(){
    return wordCount;
  }

	public int getNodeCount(){
    return nodeCount;
  }

  @Override
	public String toString(){
    StringBuilder toReturn = new StringBuilder();
    StringBuilder currentWord = new StringBuilder();
    Node p = this.rootNode;
    toString_r(toReturn,currentWord, p);
    return toReturn.toString();
  }

  private void toString_r(StringBuilder returnString, StringBuilder currentWord, Node p){
    System.out.println("Starting toString_r");
    for (int i = 0; i < 26; i++){
      if (p.nodes[i] != null){
        currentWord.append(numberToChar(i));
        if(p.nodes[i].count > 0){
          returnString.append(currentWord.toString());
          returnString.append("\n");
        }
        this.toString_r(returnString, currentWord, p.nodes[i]);
      }
    }
    if(currentWord.length() > 0){
      currentWord.setLength(currentWord.length()-1);
    }
    System.out.println("Leaving toString_r");

  }

	@Override
	public int hashCode(){

    int hash = wordCount * nodeCount * 197;
    hash = hash % 61;

    return hash;
  }

	@Override
	public boolean equals(Object o){
    if (this == o){
      System.out.println("Equals method.  These two objects have the same reference");
      return true;
    }
    if(o == null){
      System.out.println("Equals method.  o is null!");
      return false;
    }
    if(this.getClass() != o.getClass()){
      System.out.println("Equals method, these two are not the same class");
      return false;
    }
    Trie toTest = (Trie)o;
    if(this.wordCount != toTest.wordCount){
      System.out.println("Equals method.  The word counts of each trie are not equal");
      return false;
    }
    if(this.nodeCount != toTest.nodeCount){
      System.out.println("Equals method.  The node counts of each trie are not equal.");
      return false;
    }
    return equals_r(this.rootNode ,toTest.rootNode);
  }

  public boolean equals_r(Node toCompare1, Node toCompare2){
    System.out.println("Starting equals_r");
    if (toCompare1 == toCompare2){
      //System.out.println("Top of equals_r.  The two nodes have the same reference. Should never happen.  Returning true");
      return true;
    }
    boolean flag = false;
    for(int i = 0; i < 26; i++){
      flag = false;
      //System.out.println("Current index we are searching is: " + numberToChar(i));
      if ((toCompare1.nodes[i] != null) && (toCompare2.nodes[i] != null)){  //are they both not null?
        if (toCompare1.nodes[i].count != toCompare2.nodes[i].count){  //are the counts the same?
          return false;
        }
        //toCompare1 = toCompare1.nodes[i];
        //toCompare2 = toCompare2.nodes[i];
        boolean result = equals_r(toCompare1.nodes[i], toCompare2.nodes[i]);
        if(!result){
          return false;
        }
      }
      if ((toCompare1.nodes[i] == null) && (toCompare2.nodes[i] == null)){  // are they both null?
        flag = true;
      }
      if(((toCompare1.nodes[i] == null) && (toCompare2.nodes[i] != null)) ||    //do they share the same null status?
         ((toCompare1.nodes[i] != null) && (toCompare2.nodes[i] == null))){
          return false;
        }
    }
    if (flag){
      System.out.println("Leaving equals_r");
      return true;
    }
    System.out.println("End of equals_r.  Maybe shouldn' be here!  Returning false");
    return false;
  }

  public class Node implements ITrie.INode {
    public Node(){
      nodes = new Node[26];
      count = 0;
    }
    private int count;
    Node[] nodes;
    public int getValue(){
      return count;
    }
  }

  public String numberToChar(int number){
    if (number > -1 && number < 26){
      return String.valueOf((char)(number+'a'));
    }
    else{
      return null;
    }
  }

}
