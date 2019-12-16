/**
 * This AlphanumericPuzzle assigns the each distinct value of letter to numbers, calculates
 * by permutation mentality and give the results in recursively.
 * @author Mustafa KAPLAN---20160808050---Akdeniz CSE
 * @version 0.1
 * @since Oct-2019
 */
import java.util.*;

public class AlphanumericPuzzle{
    Scanner input;
    int numOfSolution;
    String first,second,last,totalWord;
    List<Character> glossary,charsOfTotalWord,U,S;

    public AlphanumericPuzzle(){
        /**
         * This is AlphanumericPuzzle class that includes constructor & methods.
         * @param input Scanner object that takes I/O from user.
         * @param numOfSolution Counter of the exact solutions.
         * @param first,second,last,totalWord These are the input vocabulary by respectively.
         * @param glossary Contains only distinct letter(no repetition) and created by using HashSet.
         * @see createGlossary
         * @param charsOfTotalWord Transferring all characters from
         * @param totalWord.
         * @param U,S Default lists for recursion.
         */
        numOfSolution=0;

        input=new Scanner(System.in);
        glossary=new ArrayList<>();

        U=new ArrayList<>();
        S=new ArrayList<>();

        totalWord=new String();
        charsOfTotalWord =new ArrayList<>();

        getUserInput(); //Getting user input
        createGlossary(totalWord);  //Create Glossary(as a list) by using totalWord
        createTotalCharList(totalWord); //Transferring all characters from totalWord
        convert10_toChar(); //Creating [0-9] number in a list for assigning the letters


        /**
         * On the below, 1st checking totalWord's size by the conditions on the sheet, that provided
         * on Google Classroom. 2nd of the condition supplies to checking dictionary size for starting
         * or terminating to the Puzzle.
         */

        if(!(totalWord.length()<6)&&!(totalWord.length()>18)) {

            if ((glossary.size() < 1 || glossary.size() > 10)) {
                System.out.println("There is No Possible Solution!");
            } else
                PuzzleSolver(glossary.size(), S, U);
        }else
            System.out.println("Please Check Your Words!\n"+"Total Size Cannot be "+totalWord.length()+"!");


        //At the end of the execution seeing the results.
        if (numOfSolution==0)
            System.out.println("There is No Solution!");
        else
            System.out.println("\nThere Exists: "+numOfSolution+" Solution!");
    }

    private void PuzzleSolver(int k, List<Character> S,List<Character> U) {

        //This solver directly inspired by the algorithm which located in the course textbook.
        for(int i=0;i<U.size();i++){
            S.add(U.get(0));
            U.remove(0);
            if(k==1){
                mainSolution(S);
            }else
                PuzzleSolver(k-1,S,U);

            U.add(S.get(S.size()-1));
            S.remove(S.size()-1);
        }

    }

    private void mainSolution(List<Character> sol){
        /**
         * @param cloneOfTotalChar This is clone of the our totalChars. Although replaceAll method
         *                         strictly changes all element in the object, we have to another copy
         *                         for processing.
         */
        List<Character> cloneOfTotalChar=new ArrayList<>();
        cloneOfTotalChar.addAll(charsOfTotalWord);

        /**
         * In this loop all characters stands for the numeric values are changed by using Collections.
         * Reason of the using Collections is ArrayList does only provide replaceAll in Unary!
         */
        for (int i=0;i<glossary.size();i++){
            Collections.replaceAll(cloneOfTotalChar,glossary.get(i),sol.get(i));
        }

        /**
         * For using length of the words we have our words' numeric summation.
         * Thought is simple, like splitting an array, just using indexes in here.
         */

        int firstW_total=listToNumber(cloneOfTotalChar,0,first.length());
        int secondW_total=listToNumber(cloneOfTotalChar,first.length(),first.length()+second.length());
        int lastW_total= listToNumber(cloneOfTotalChar,first.length()+second.length(),totalWord.length());

        int entry_sum=firstW_total+secondW_total;

        //Validation criterion If summation of left is equal right(first+second=last), solution is approved.
        if(entry_sum==lastW_total){
            System.out.print("Solution Found!\n");

            //Replace to numbers with words
            for(int i = 0; i < sol.size(); i++) {
                Collections.replaceAll(cloneOfTotalChar,sol.get(i),glossary.get(i));
            }
            System.out.println(glossary+"\n"+sol+"\n"+cloneOfTotalChar);
            System.out.println(first+" = "+firstW_total+"\n"+second+" = "+secondW_total+"\n"
                    +last+" = "+lastW_total);
            numOfSolution++;
        }

    }

    private int listToNumber(List my_List,int begin,int end){
        /**
         * There is no implementation in Java for my aim, so this method was created.
         * This just merges all elements in the List in stated interval.(begin to end)
         */

        String str="";
        for(int l=begin;l<end;l++){
            str+= String.valueOf(my_List.get(l));
        }
        int total=Integer.parseInt(str);
        return total;
    }

    private void convert10_toChar() {
        //Just converts [0-9] numbers to characters
        for(int i=0;i<10;i++)
            U.add((char)(i+'0'));
    }

    private void createTotalCharList(String totalWord){
        //Just adds String to ArrayList
        for(int j=0;j<totalWord.length();j++){
            charsOfTotalWord.add(totalWord.charAt(j));
        }
    }

    private void createGlossary(String word) {
        /**
         * Creating the glossary(syn. Dictionary) for comparision.
         * In here firstly HashSet used because of the feature for keeping non-repetition objects.
         * However lack of the get() method, just switched to the ArrayList.
         */

        HashSet mySet = new HashSet();
        for (int i = 0; i < word.length(); i++) {
            mySet.add(word.charAt(i));
        }
        glossary.addAll(mySet);
    }

    private void getUserInput(){

        for (int i=1;i<4;i++){
            switch (i){
                case 1:
                    System.out.println("Type Your Input "+i+" : ");
                    first=input.next(); break;
                case 2:
                    System.out.println("Type Your Input "+i+" : ");
                    second=input.next(); break;
                case 3:
                    System.out.println("Type Your Output : ");
                    last =input.next(); break;
            }

        }
        totalWord=first+second+last;
    }

    public static void main(String[] args){
            new AlphanumericPuzzle();
    }
}

