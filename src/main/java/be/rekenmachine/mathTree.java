package be.rekenmachine;

import be.rekenmachine.token.tokenType;

public class mathTree {
    public token self;
    public mathTree lChild;
    public mathTree rChild;

    public mathTree(token s, mathTree l, mathTree r){
        assert s.tType == tokenType.OPERATOR;
        self = s;
        lChild = l;
        rChild = r;
    }

    public mathTree(token s){
        assert s.tType == tokenType.DIGIT;
        self = s;
        lChild = null;
        rChild = null;
    }

    public Double resolve(){
        if(self.tType == tokenType.DIGIT){
            return Double.valueOf(self.literal);
        }
        switch (self.oType) {
            case ADD:
                return lChild.resolve() + rChild.resolve();
            case DIV:
                return lChild.resolve() / rChild.resolve();
            case MIN:
                return lChild.resolve() - rChild.resolve();
            case MUL:
                return lChild.resolve() * rChild.resolve();
            case NULL:
                return null;
        }
        // UNREACHABLE
        return null;
    }
}
