/*
Controller for model to view
 */
package javafxcalculator;


import java.util.List;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;


/**
 *
 * @author charles
 */
public class MainController {
    
    @FXML
    private Label resultLableNode;

    @FXML
    private Button delButtonNode;

    @FXML
    private ListView<String> statementListView;

    @FXML
    private Label errorLabel;

    @FXML
    private Button resetButton;
    
    boolean start = true;   
    CalculationModel model;
    ObservableList<String> calculationItemsArray;
    
    @FXML
    public void buildStatement(ActionEvent event) {
        errorLabel.setText("");
        resultLableNode.setBorder(Border.EMPTY);
        if (start) {
            resultLableNode.setText("");
            start = false;
        }
        
        String value = ((Button)event.getSource()).getText();
        String currentValue = resultLableNode.getText();
        
        //first character is a +,-,*,/
        if((value.equals("+") || value.equals("-") 
           || value.equals("*") || value.equals("/"))
                && (currentValue.length() == 0)){
            errorLabel.setText("Can't begin with +,-,*,/");
            Border border = new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT));
            resultLableNode.setBorder(border);
            return;
        }
        //if empty text field
        if(currentValue.length() == 0){
            resultLableNode.setText(currentValue + value);
            return;
        }
        String lastCharString = currentValue.substring(currentValue.length() - 1); 
        //check for error input
        if((lastCharString.equals("+") || lastCharString.equals("-") 
                || lastCharString.equals("*") || lastCharString.equals("/"))
                && (value.equals("+") || value.equals("-") 
                || value.equals("*") || value.equals("/"))){
            errorLabel.setText("Must be number/operator/number");
            Border border = new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT));
            resultLableNode.setBorder(border);
        }else{

            resultLableNode.setText(currentValue + value);
        }
        
    }
    
    @FXML
    public void deleteLastDigit(ActionEvent event){
        resultLableNode.setBorder(Border.EMPTY);
        StringBuilder stringBuilder = new StringBuilder(resultLableNode.getText());
        stringBuilder.deleteCharAt(stringBuilder.length()-1);
        String newNumber = stringBuilder.toString();
        resultLableNode.setText(newNumber);
        errorLabel.setText("");
        
        
    }

    @FXML
    public void processStatement(ActionEvent event) {
        resultLableNode.setBorder(Border.EMPTY);
        String statement = resultLableNode.getText();
        
        //if the resultLableNode is empty
        if(resultLableNode.getText().length() == 0){
            errorLabel.setText("Can't calculate empty field!");
            Border border = new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT));
            resultLableNode.setBorder(border);
            return;
        } 
        
        //if the last chacter in statement is +,-,*,/
        String currentValue = resultLableNode.getText();
        String lastCharString = currentValue.substring(currentValue.length() - 1); 
        //check for error input
        if(lastCharString.equals("+") || lastCharString.equals("-") 
                || lastCharString.equals("*") || lastCharString.equals("/")){
            errorLabel.setText("Last operator missing number!");
            Border border = new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT));
            resultLableNode.setBorder(border);
        }else{

            CalculationModel calculationModel = new CalculationModel(statement);
            resultLableNode.setText(calculationModel.getAnswer());
            populateListView(calculationModel);

            
        }        
    
    }
    
    //reset calculation
    @FXML
    public void resetCalculation(ActionEvent event){
        resultLableNode.setBorder(Border.EMPTY);
        resultLableNode.setText("");
        errorLabel.setText("");
        statementListView.getItems().clear();
        
        
    }
    
    @FXML
    public void populateListView(CalculationModel calculationModel){
        List<String> list = calculationModel.getStepToSolveList();
        for(String itemString: list){
            statementListView.getItems().add(itemString);
        }
    }
}
