package uk.ac.shef.com3529.practicals;

public class Statement {


    public enum LogicType {
        GREATER_THAN,
        GREATER_EQUAL_THAN,
        LESS_THAN,
        LESS_EQUAL_THAN,
        EQUALS,
        NOT,
        NONE,
    }

    public String first_variable;
    public String second_variable;
    public LogicType type;
    public int id;

    public Statement(int id, String first_variable, String second_variable, LogicType type) {
        this.id = id;
        this.first_variable = first_variable;
        this.second_variable = second_variable;
        this.type = type;
    }

    public String toString(){
        //return "Type :" + this.type + " Left : (" + this.left.toString() + ") Right : " + this.right.toString();
        return "ID : " + this.id + " Type :" + this.type +  " first_variable : " + this.first_variable + " second_variable " + this.second_variable;
    }
}
