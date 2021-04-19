package uk.ac.shef.com3529.practicals;

import java.util.Arrays;

public class MethodTree {

    public LogicStatementNode root;
    public MethodTree left_branch;
    public MethodTree right_branch;

    public MethodTree(LogicStatementNode root){
        this.root = root;
    }

    // Make sure that everything within a for or while loop is put to the right of the parent node
    // once this is sorted you can move on to text processing
    // Just remember it is as easy as to process the entire tree linearly and to check whether it has
    // right nodes that need to be taken into account as nodes that are dependent of the dependant one
    // i.e in the case of BMI, there is only one parent node, all others are dependant

    public MethodTree insert(LogicStatementNode object_to_insert){
        if (object_to_insert.type == LogicStatementNode.StatementType.IF || object_to_insert.type == LogicStatementNode.StatementType.WHILE
        || object_to_insert.type == LogicStatementNode.StatementType.FOR){
            if (left_branch == null){
                MethodTree node_to_add = new MethodTree(object_to_insert);
                left_branch = node_to_add;
                System.out.print("Left : " + left_branch);
            } else {
                left_branch.insert(object_to_insert);
            }
        }
        if (object_to_insert.type == LogicStatementNode.StatementType.ELSE_IF || object_to_insert.type == LogicStatementNode.StatementType.ELSE){
            if (right_branch == null){
                MethodTree node_to_add = new MethodTree(object_to_insert);
                right_branch = node_to_add;
                System.out.print("Right : " + right_branch);
            } else {
                right_branch.insert(object_to_insert);
            }
        }
        return this;
    }

    /*public MethodTree improved_insert(LogicStatementNode object_to_insert){
        if (object_to_insert.dependent_on == root.id){
            if (right_branch == null){
                MethodTree node_to_add = new MethodTree(object_to_insert);
                last_node_id = object_to_insert.id;
                System.out.println("Last node ID : " + last_node_id);
                right_branch = node_to_add;
                System.out.println("Right : " + right_branch);
            } else {
                right_branch.insert2(object_to_insert, last_node_id);
            }
        } else{
            if (left_branch == null){
                MethodTree node_to_add = new MethodTree(object_t*//**//*o_insert);
                last_node_id = object_to_insert.id;
                System.out.println("Last node ID : " + last_node_id);
                left_branch = node_to_add;
                System.out.println("Left : " + left_branch);
            } else {
                left_branch.insert2(object_to_insert);
            }
        }
        return this;
    }*/

    public String toString(){
        return "" + this.root.id + " ";
    }

    public static void main(String[] args) {
        // Node line 17
        Statement statement_node_17 = new Statement(1, "bmi", "18.5", Statement.LogicType.LESS_THAN);
        Relation relation_node_17 = new Relation(0, 0, Relation.RelationType.NONE);
        LogicStatementNode node_17 = new LogicStatementNode(17, 0, LogicStatementNode.StatementType.IF, Arrays.asList(statement_node_17), Arrays.asList(relation_node_17));
        //System.out.println(node_17);
        MethodTree main_tree = new MethodTree(node_17);

        // Node line 19
        Statement statement_node_19_a = new Statement(2, "bmi", "17.5", Statement.LogicType.LESS_EQUAL_THAN);
        Statement statement_node_19_b = new Statement(3, "bmi", "25", Statement.LogicType.LESS_THAN);
        Relation relation_node_19 = new Relation(2, 3, Relation.RelationType.AND);
        LogicStatementNode node_19 = new LogicStatementNode(19, 17, LogicStatementNode.StatementType.ELSE_IF, Arrays.asList(statement_node_19_a, statement_node_19_b), Arrays.asList(relation_node_19));
        main_tree.insert(node_19);

        // Node line 21
        Statement statement_node_21_a = new Statement(4, "bmi", "25", Statement.LogicType.GREATER_EQUAL_THAN);
        Statement statement_node_21_b = new Statement(5, "bmi", "30", Statement.LogicType.LESS_THAN);
        Relation relation_node_21 = new Relation(4, 5, Relation.RelationType.AND);
        LogicStatementNode node_21 = new LogicStatementNode(21, 19, LogicStatementNode.StatementType.ELSE_IF, Arrays.asList(statement_node_21_a, statement_node_21_b), Arrays.asList(relation_node_21));
        main_tree.insert(node_21);

        // Node line 23
        LogicStatementNode node_23 = new LogicStatementNode(23, 21, LogicStatementNode.StatementType.ELSE, Arrays.asList(), Arrays.asList());
        main_tree.insert(node_23);

    }
}
