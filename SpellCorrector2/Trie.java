package spell;




class Trie implements ITrie{

  public Trie(){
    root = new Node();
    wordCount = 0;
    nodeCount = 1;
  }

  Node root;
  int wordCount;
  int nodeCount;

  public void add(String word){
    //System.out.println("Adding the following word to dictionary: " + word);
    Node p = root;
    for (int i = 0; i < word.length(); i++){
      int index = word.charAt(i) - 'a';
      if(p.nodes[index] == null){
        p.nodes[index] = new Node();
        p = p.nodes[index];
        nodeCount++;
        //System.out.println("Total node count = " + nodeCount);
      }
      else{
        p = p.nodes[index];
      }
    }
    if(p.count == 0){
      wordCount++;
    }
    p.count++;
    //System.out.println("Total word count = " + wordCount);
  }


  public Node find(String word){
    Node p = root;
    for(int i = 0; i < word.length(); i++){
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
  StringBuilder finalString = new StringBuilder();
  StringBuilder currentWord = new StringBuilder();
  toString_r(root, currentWord, finalString);
  return finalString.toString();
}


private void toString_r(Node p, StringBuilder currentWord, StringBuilder finalString){
  if(p.count > 0){
    finalString.append(currentWord.toString());
    finalString.append("\n"); //add a new line after each word
  }
  for(int i = 0; i < 26; i ++ ){
    if(p.nodes[i] != null){
      currentWord.append(numberToChar(i));
      toString_r(p.nodes[i],currentWord, finalString);
    }
  }
  if(currentWord.length() > 0){
    currentWord.setLength(currentWord.length() -1);
  }
}



  @Override
  public int hashCode(){
    int toReturn = (nodeCount * wordCount * 53) %19;
    return toReturn;
  }

/*
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

*/

  //---------------------------------------------------------------------------------------------------------------------------------

  @Override
  public boolean equals(Object o){
    if(this == o){
      return true;
    }
    if(o==null){
      return false;
    }
    if(this.getClass() != o.getClass()){
      return false;
    }
    Trie toCompare = (Trie)o;
    if((this.nodeCount != toCompare.nodeCount) || (this.wordCount != o.wordCount)){}
      return false;
    }
    return equals_r(this.root, o.root);
  }


  boolean equals_r(Node a, Node b){
    if(a == b){
      return true;
    }
    if(a.count != b.count){
      return false;
    }
    for(int i = 0; i < 26; i++){
      if((a.nodes[i] != null && b.nodes[i] == null) || (a.nodes[i] == null && b.nodes[i] != null)){
        return false;
      }
      else if(a.nodes[i] != null && b.nodes[i] != null){
        if(!equals_r(a.nodes[i], b.nodes[i])){
          return false;
        }
      }
    }
    return true;
  }



  public String numberToChar(int number){
    if (number >-1 && number < 26){
      return String.valueOf((char)(number+ 'a'));
    }
    return null;
  }


  public class Node implements INode {

    Node(){
      count = 0;
      nodes = new Node[26];
    }

    int count;
    Node[] nodes;

    public int getValue(){
      return 0;
    }
  }


}
