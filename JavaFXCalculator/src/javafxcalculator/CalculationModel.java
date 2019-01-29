/*
 * organization and calculations for application
 * takes calcuation as string, parses string into inidividual elements
 * of statement and processes all of the * and /, then processes the - and +.
 * Stores each iteration of the array in a list to be viewed by the listview. 
 * 
 * 
 */
package javafxcalculator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author charles hammock
 */
public class CalculationModel {
    
    //list to keep elements of statement
    private final List<String> statementList;
    //list of steps to solve problem
    private final List<String> stepToSolveList;
    //final answer to return
    private String answer = "";
            
    public CalculationModel(String statementString){
        String[] statementStringArray = statementString.split("(?<=[-+*/])|(?=[-+*/])");
        this.statementList = new ArrayList<>(Arrays.asList(statementStringArray));
        this.stepToSolveList = new ArrayList<>();
        processOrderOfOperations();
               
    }
    
    //iterates over list and processes pushes parts of statement to 
    //doCalcuation.  purpose of fuction is to just grab priority calcuations 
    private void processOrderOfOperations(){
        addToStepsToSolveList(statementList);
        //iterate list and find calculations with top priority
            for(int i = 0;i < statementList.size();i++){
                if(statementList.get(i).equals("*") ||
                   statementList.get(i).equals("/")){
                    int number = doCalculation(statementList.get(i-1), statementList.get(i+1),
                                    statementList.get(i));
                    resizeArray(i, String.valueOf(number));
                    addToStepsToSolveList(statementList);
                    i = 0; //go back to beginning of statement if * or / found past n + 1
                }
            }
            //all other operations will be + or -, got from left to right
            for(int i = 0;i < statementList.size();i++){
                if(statementList.get(i).equals("+") ||
                   statementList.get(i).equals("-")){
                    int number = doCalculation(statementList.get(i-1), statementList.get(i+1),
                                    statementList.get(i));
                    resizeArray(i, String.valueOf(number));
                    addToStepsToSolveList(statementList);
                    i = 0;
                }
            }
        answer = statementList.get(0);
    }
    
    //takes format of number/operator/number, does calculation, and returns answer
    //in the operators index.  Due to shift every remove, kept deleting/ remove 
    // x in x + y.  middle index is "+" and every delete items shift to left
    private void resizeArray(int middleIndex, String answer){
        
        statementList.remove(middleIndex - 1);
        statementList.remove(middleIndex - 1);
        statementList.set(middleIndex - 1, answer);
        

        
    }
    
    //takes snapshot of current state of statementList and puts it into an list
    //this list is used to populate listview of GUI
    private <T> void addToStepsToSolveList(List<T> list){
        StringBuilder stringBuilder = new StringBuilder();
        list.forEach((item) -> {
            stringBuilder.append(item.toString());
            stringBuilder.append(" ");
        });
        String listString = stringBuilder.toString();
        stepToSolveList.add(listString);
    }
    
    //does calculations for each step
    private int doCalculation(String firstNumberString, String secondNumberString, String operator){
        int answerLocal = 0;
        int firstInt = Integer.parseInt(firstNumberString);
        int secondInt = Integer.parseInt(secondNumberString);
        switch(operator){
                case "+":
                    answerLocal = firstInt + secondInt;
                    break;
                case "-": 
                    answerLocal = firstInt - secondInt;
                    break;
                case "*":
                    answerLocal = firstInt * secondInt;
                    break;
                case "/":
                    if(secondInt == 0)
                        return 0;
                    answerLocal = firstInt / secondInt;
                    break;
                default:
                    answerLocal = 0;
        }
        
        return answerLocal;
    }
    
    public String getAnswer(){
        return answer;
    }
    
    public List<String> getStatementArray(){
        return statementList;
    }
    
    public List<String> getStepToSolveList(){
        return stepToSolveList;
    }
}
