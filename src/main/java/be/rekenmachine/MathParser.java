package be.rekenmachine;

import java.util.ArrayList;

import be.rekenmachine.token.operatorType;
import be.rekenmachine.token.tokenType;

public class MathParser {
    private int curTokenIndex;
    private token curToken;
    private ArrayList<token> tokens;

    private MathParser(){
        curTokenIndex = 0;
    }

    private mathTree negate(mathTree tree){
        return new mathTree(new token(null, tokenType.OPERATOR, operatorType.MUL), 
                            new mathTree(new token("-1", tokenType.DIGIT,null)),
                            tree);
    }

    private void AdvanceToken(){
        curTokenIndex++;
        if(curTokenIndex < tokens.size())
            curToken = tokens.get(curTokenIndex);
        else 
            curToken = new token("EOX", tokenType.EOX, operatorType.NULL);
    }

    private mathTree parseFactor(){
        if(curToken.tType == tokenType.DIGIT){
            mathTree a = new mathTree(curToken);
            AdvanceToken();
            return a;
        } else if(curToken.tType == tokenType.LPAR){
            AdvanceToken();
            mathTree next = parseExpression();
            if(next.self.tType == tokenType.NULL){
                return next;
            }
            else if(curToken.tType == tokenType.RPAR){
                AdvanceToken();
                return next;
            }
            else if(curToken.tType == tokenType.EOX){
                return new mathTree(new token(null, tokenType.NULL, operatorType.NULL));
            }
            else {
                return new mathTree(new token(null, tokenType.NULL, operatorType.NULL));
            }
        } else if(curToken.oType == operatorType.MIN){
            AdvanceToken();
            return negate(parseFactor());
        }
        return new mathTree(new token(null, tokenType.NULL, operatorType.NULL));
    }
      
    private mathTree parseTerm(){
        mathTree a = parseFactor();
        while(true){
            if(curToken.oType == operatorType.MUL){
                AdvanceToken();
                mathTree b = parseFactor();
                a = new mathTree(new token("*", tokenType.OPERATOR, operatorType.MUL), 
                                 a, 
                                 b);
            } else if(curToken.oType == operatorType.DIV){
                AdvanceToken();
                mathTree b = parseFactor();
                a = new mathTree(new token("/", tokenType.OPERATOR, operatorType.DIV),
                                 a,
                                 b);
            } else if(curToken.tType == tokenType.NULL){
                return new mathTree(new token(null, tokenType.NULL, operatorType.NULL));
            } else if(curToken.tType == tokenType.EOX){ 
                return a;
            } else {
                return a;
            }
        }
    }

    private mathTree parseExpression(){
        mathTree a = parseTerm();
        while(true){
            if(curToken.oType == operatorType.ADD){
                AdvanceToken();
                mathTree b = parseTerm();
                a = new mathTree(new token("+", tokenType.OPERATOR, operatorType.ADD),
                                 a, 
                                 b);
            } else if(curToken.oType == operatorType.MIN){
                AdvanceToken();
                mathTree b = parseTerm();
                a = new mathTree(new token("-", tokenType.OPERATOR, operatorType.MIN), 
                                 a, 
                                 b);
            } else if(curToken.tType == tokenType.NULL){
                return new mathTree(new token(null, tokenType.NULL, operatorType.NULL)); 
            } else if(curToken.tType == tokenType.EOX){
                return a;
            } else {
                return a;
            }
        }
    }

    private Double Parse(String s){
        tokens = new Lexer().lex(s);
        curToken = tokens.get(curTokenIndex);
        mathTree root = parseExpression();
        return root.resolve();
    }

    public static Double parse(String s){
        return new MathParser().Parse(s);
    }

    // shitty tests because i couldnt get junit working
    public static void main(String[] args) {
        System.out.println(parse("8 * 8 + 7 * 7")); //  == 113
        System.out.println(parse("8 *( 8 + 7 * 7)")); //  == 456
        System.out.println(parse("8 *( 8 + 7) * 7")); //  == 840
        System.out.println(parse("7 - 5 + 1")); //  == 3
        System.out.println(parse("88/8")); //  == 11
        System.out.println(parse("198 - -66")); // == 264
        System.out.println(parse("066")); // == 66
        System.out.println(parse("066+ 0099")); // == 165
        System.out.println(parse("066 / 2 * 3 + 1")); // == 100
    }
}
