package uk.ac.shef.com3529.practicals;


import java.util.List;

public class LogicStatementNode {

    public enum StatementType {
        IF,
        ELSE_IF,
        ELSE,
        FOR,
        WHILE,
    }

    public StatementType type;
    public int id, dependent_on;
    public List<Statement> statements;
    public List<Relation> relations;
    public MethodTree left;
    public MethodTree right;
    public List<Criterion> criterions;

    public LogicStatementNode(int id, int dependent_on, StatementType type, List<Statement> statements, List<Relation> relations) {
        this.id = id;
        this.dependent_on = dependent_on;
        this.type = type;
        this.statements = statements;
        this.relations = relations;
        this.left = null;
        this.right = null;
    }

    public LogicStatementNode(int id, int dependent_on, StatementType type, List<Statement> statements, List<Relation> relations, List<Criterion> criterions){
        this.id = id;
        this.dependent_on = dependent_on;
        this.type = type;
        this.statements = statements;
        this.relations = relations;
        this.left = null;
        this.right = null;
        this.criterions = criterions;
    }

    public String toString(){
        //return "Type :" + this.type + " Left : (" + this.left.toString() + ") Right : " + this.right.toString();
        return "Node ID : " + this.id + " , Type :" + this.type + " , Dependency on " + dependent_on + " , Statements " + this.statements +
                " , Relations " + relations;
    }
}
