package be.rekenmachine;

public class token {
    protected enum tokenType {
        NULL,
        LPAR,
        RPAR,
        OPERATOR,
        DIGIT,
        EOX;

        @Override
        public String toString() {
            switch (this) {
                case OPERATOR:
                    return "OPER";
                case DIGIT:
                    return "NUM";
                case LPAR:
                    return "LPAR";
                case RPAR:
                    return "RPAR";
                case NULL:
                    return "NULL";
                case EOX:
                    return "END OF EXPRESSION";
            }
            return "NOT IMPLEMENTED IN TOKENTYPE";
        }
    }

    protected enum operatorType {
        NULL,
        ADD,
        MIN,
        MUL,
        DIV;

        @Override
        public String toString() {
            switch (this) {
                case ADD:
                    return "ADD";
                case MIN:
                    return "MIN";
                case MUL:
                    return "MUL";
                case DIV:
                    return "DIV";
                default:
                    return "NULL";
            }
        }
    }

    public String literal;
    protected tokenType tType;
    protected operatorType oType;

    @Override
    public String toString() {
        return literal + " : " + tType.toString() + " : " + oType.toString();
    }

    protected token(String s, tokenType t, operatorType o){
        literal = s;
        tType = t;
        oType = o;
    }
}
