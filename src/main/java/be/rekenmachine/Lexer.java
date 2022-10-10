package be.rekenmachine;

import java.util.ArrayList;
import java.util.function.Predicate;
import java.util.regex.Pattern;

import be.rekenmachine.token.tokenType;
import be.rekenmachine.token.operatorType;

public class Lexer {
    private String toParse;
    private int startIndex;
    private int index;

    private static Predicate<String> operators = Pattern.compile("-|\\*|\\+|/").asMatchPredicate();
    private static Predicate<String> digits = Pattern.compile("\\d+|-\\d+").asMatchPredicate();
    private static Predicate<String> whiteSpace = Pattern.compile(" ").asMatchPredicate();
    private static Predicate<String> brackets = Pattern.compile("\\(|\\)").asMatchPredicate();

    private tokenType getTokenType(String token){
        if(digits.test(token)){
            return tokenType.DIGIT;
        }
        if(operators.test(token)){
            return tokenType.OPERATOR;
        }
        if(brackets.test(token)){
            if(Pattern.matches("\\(", token)){
                return tokenType.LPAR;
            } else {
                return tokenType.RPAR;
            }
        }
        return tokenType.NULL;
    }

    private operatorType getOperatorType(String operator){
        if(operator.charAt(0) == '*'){
            return operatorType.MUL;
        }
        if(operator.charAt(0) == '+'){
            return operatorType.ADD;
        }
        if(operator.charAt(0) == '-'){
            return operatorType.MIN;
        }
        if(operator.charAt(0) == '/'){
            return operatorType.DIV;
        }
        return operatorType.NULL;
    }

    private String at(int Index){
        return String.valueOf(toParse.charAt(Index));
    }

    private String getToken(){
        if(!hasNextToken()){
            startIndex = toParse.length() - 1;
            while(digits.test(at(startIndex)) || whiteSpace.test(at(startIndex))){
                if(--startIndex == 0){
                    break;
                }
            }
            startIndex++;
            return toParse.substring(startIndex).replaceAll(" ", "");
        }
        if(digits.test(at(index)) || whiteSpace.test(at(index))){
            index++;
            return getToken();
        }
        if(operators.test(at(index)) || brackets.test(at(index))){
            index = startIndex == index ? ++index : index;
            String s = toParse.substring(startIndex, index).replaceAll(" ", "");
            startIndex = index;
            return s;
        }
        return null;
    }

    private Boolean hasNextToken(){
        return index < toParse.length();
    }

    protected ArrayList<token> lex(String s){
        toParse = s;
        index = 0;
        startIndex = 0;

        ArrayList<token> rArrayList = new ArrayList<>();
        String tokenliteral = "";
        while(hasNextToken()){
            tokenliteral = getToken();
            if(tokenliteral.isEmpty()){
                continue;
            }
            rArrayList.add( new token(tokenliteral,
                            getTokenType(tokenliteral),
                            getOperatorType(tokenliteral)));
        }
        return rArrayList;
    }
}
