package uk.ac.shef.com3529.practicals;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Coverage {

    static final int ITERATIONS = 100;
    static final int MIN_INT = 0;
    static final int MAX_INT = 20;

    static boolean cont = true;

    static int randomInt(Random r) {
        if (MIN_INT == Integer.MIN_VALUE && MAX_INT == Integer.MAX_VALUE) {
            return r.nextInt();
        } else {
            return r.nextInt((MAX_INT - MIN_INT + 1)) + MIN_INT;
        }
    }

    static void performTesting(int ran_int, List<LogicStatementNode> nodes, List<Criterion> mcdc_requirements){
        for (LogicStatementNode elem : nodes){
            System.out.println("Current node being tested : " + elem.id);
            if (cont) {
                if (elem.type != LogicStatementNode.StatementType.ELSE) {
                    instrumentedClassify(ran_int, mcdc_requirements, elem);
                    if (!cont){
                        cont = false;
                        return;
                    }
                }

            }
        }
    }

    static void randomlyTestClassify(List<LogicStatementNode> nodes, List<Criterion> mcdc_requirements) {
        Random r = new Random();
        for (int i=0; i < ITERATIONS; i ++) {

            int ran_int = randomInt(r);
            cont = true;
            System.out.println("Iteration number : " + (i+1));
            System.out.println("The random number generated is : " + ran_int);

            performTesting(ran_int, nodes,mcdc_requirements);
            System.out.println("-----------------------");

        }
    }

    static void instrumentedClassify(int r, List<Criterion> requirements, LogicStatementNode node) {

        boolean[] truth_array = new boolean[node.statements.size()];
        int node_id = node.id;
        Statement left_statement = null;
        Statement right_statement = null;

        if (node.relations.size() == 0){
            if (node.statements.get(0).type == Statement.LogicType.GREATER_THAN) {
                truth_array[0] = logGreaterThan(node.statements.get(0), r);
                satisfiedRequirement(node, requirements, truth_array, truth_array[0]);
                if (truth_array[0]){
                    System.out.println("Node "  + node.id + " has evaluated to true");
                    cont = false;
                    return;
                }
            } else if (node.statements.get(0).type == Statement.LogicType.GREATER_EQUAL_THAN){
                truth_array[0] = logGreaterEqual(node.statements.get(0), r);
                satisfiedRequirement(node, requirements, truth_array, truth_array[0]);
                if (truth_array[0]){
                    System.out.println("Node "  + node.id + " has evaluated to true");
                    cont = false;
                    return;
                }
            } else if (node.statements.get(0).type == Statement.LogicType.LESS_THAN){
                truth_array[0] = logLessThan(node.statements.get(0),  r);
                satisfiedRequirement(node, requirements, truth_array, truth_array[0]);
                if (truth_array[0]){
                    System.out.println("Node "  + node.id + " has evaluated to true");
                    cont = false;
                    return;
                }
            } else if (node.statements.get(0).type == Statement.LogicType.LESS_EQUAL_THAN){
                truth_array[0] = logLessEqual(node.statements.get(0),  r);
                satisfiedRequirement(node, requirements, truth_array, truth_array[0]);
                if (truth_array[0]){
                    System.out.println("Node "  + node.id + " has evaluated to true");
                    cont = false;
                    return;
                }
            } else if (node.statements.get(0).type == Statement.LogicType.EQUALS){
                truth_array[0] = logEqual(node.statements.get(0), r);
                if (truth_array[0]){
                    System.out.println("Node "  + node.id + " has evaluated to true");
                    cont = false;
                    return;
                }
            }
        } else {

            Relation.RelationType rel_type = null;

            for (Relation rel : node.relations) {
                int left_part = rel.left_id;
                int right_part = rel.right_id;
                rel_type = rel.type;

                for (Statement stat : node.statements) {
                    if (stat.id == left_part) {
                        left_statement = stat;
                        if (left_statement.type == Statement.LogicType.GREATER_THAN) {
                            truth_array[0] = logGreaterThan(left_statement, r);
                        } else if (left_statement.type == Statement.LogicType.GREATER_EQUAL_THAN){
                            truth_array[0] = logGreaterEqual(left_statement, r);
                        } else if (left_statement.type == Statement.LogicType.LESS_THAN){
                            truth_array[0] = logLessThan(left_statement,  r);
                        } else if (left_statement.type == Statement.LogicType.LESS_EQUAL_THAN){
                            truth_array[0] = logLessEqual(left_statement,  r);
                        } else if (left_statement.type == Statement.LogicType.EQUALS){
                            truth_array[0] = logEqual(left_statement, r);
                        }

                    }
                    if (stat.id == right_part) {
                        right_statement = stat;
                        if (right_statement.type == Statement.LogicType.GREATER_THAN) {
                            truth_array[1] = logGreaterThan(right_statement, r);
                        } else if (right_statement.type == Statement.LogicType.GREATER_EQUAL_THAN){
                            truth_array[1] = logGreaterEqual(right_statement, r);
                        } else if (right_statement.type == Statement.LogicType.LESS_THAN){
                            truth_array[1] = logLessThan(right_statement,  r);
                        } else if (right_statement.type == Statement.LogicType.LESS_EQUAL_THAN){
                            truth_array[1] = logLessEqual(right_statement,  r);
                        } else if (right_statement.type == Statement.LogicType.EQUALS){
                            truth_array[1] = logEqual(right_statement, r);
                        }
                    }
                }
            }

            satisfiedRequirement(node, requirements, truth_array, computeOutcome(rel_type, truth_array));
            if (computeOutcome(rel_type, truth_array)){
                System.out.println("Node "  + node.id + " has evaluated to true");
                cont = false;
                return;
            }
        }
    }

    static boolean computeOutcome(Relation.RelationType type, boolean[] truth_array){
        if (type == Relation.RelationType.AND){
            if (truth_array[0] && truth_array[1]) {
                return true;
            } else {
                return false;
            }
        } else if (type == Relation.RelationType.OR){
            if  (truth_array[0] || truth_array[1]) {
                return true;
            } else {
                return false;
            }
        } else{
            return false;
        }
    }

    static void satisfiedRequirement(LogicStatementNode node, List<Criterion> requirements, boolean[] truth_values, boolean pred) {
        for (Criterion crit : requirements){
            if (node.id == crit.node_id && Arrays.equals(crit.values, truth_values) && pred == crit.predicate){
                if (!crit.satisfied){
                    System.out.println(" *** New Branch has been covered *** ");
                    crit.setSatisfied(true);
                }
            }
        }
    }

    public static boolean logGreaterThan(Statement stat, int r){
        boolean is_first_letter = false;
        boolean is_snd_letter = false;

        for (int i = 0; i < stat.first_variable.length() ;i++){
            if (Character.isLetter(stat.first_variable.charAt(i))) {
                is_first_letter = true;
            }
        }
        for (int i = 0; i < stat.second_variable.length() ;i++){
            if (Character.isLetter(stat.second_variable.charAt(i))) {
                is_snd_letter = true;
            }
        }

        if (!is_first_letter && !is_snd_letter){
            if (Float.parseFloat(stat.first_variable) > Float.parseFloat(stat.second_variable)){
                return true;
            } else{
                return false;
            }
        } else if (is_first_letter && !is_snd_letter){
            if (r > Float.parseFloat(stat.second_variable)){
                return true;
            } else{
                return false;
            }
        } else if (!is_first_letter && is_snd_letter){
            if (Float.parseFloat(stat.first_variable) > r){
                return true;
            } else{
                return false;
            }
        } else {
            System.out.println("Does not fit into the system requirements");
            return false;
        }
    }

    public static boolean logLessThan(Statement stat, int r){
        boolean is_first_letter = false;
        boolean is_snd_letter = false;

        for (int i = 0; i < stat.first_variable.length() ;i++){
            if (Character.isLetter(stat.first_variable.charAt(i))) {
                is_first_letter = true;
            }
        }
        for (int i = 0; i < stat.second_variable.length() ;i++){
            if (Character.isLetter(stat.second_variable.charAt(i))) {
                is_snd_letter = true;
            }
        }

        if (!is_first_letter && !is_snd_letter){
            if (Float.parseFloat(stat.first_variable) < Float.parseFloat(stat.second_variable)){
                return true;
            } else{
                return false;
            }
        } else if (is_first_letter && !is_snd_letter){
            if (r < Float.parseFloat(stat.second_variable)){
                return true;
            } else{
                return false;
            }
        } else if (!is_first_letter && is_snd_letter){
            if (Float.parseFloat(stat.first_variable) < r){
                return true;
            } else{
                return false;
            }
        } else {
            System.out.println("Does not fit into the system requirements");
            return false;
        }
    }

    public static boolean logEqual(Statement stat, int r){
        boolean is_first_letter = false;
        boolean is_snd_letter = false;

        for (int i = 0; i < stat.first_variable.length() ;i++){
            if (Character.isLetter(stat.first_variable.charAt(i))) {
                is_first_letter = true;
            }
        }
        for (int i = 0; i < stat.second_variable.length() ;i++){
            if (Character.isLetter(stat.second_variable.charAt(i))) {
                is_snd_letter = true;
            }
        }

        if (!is_first_letter && !is_snd_letter){
            if (Float.parseFloat(stat.first_variable) == Float.parseFloat(stat.second_variable)){
                return true;
            } else{
                return false;
            }
        } else if (is_first_letter && !is_snd_letter){
            if (r == Float.parseFloat(stat.second_variable)){
                return true;
            } else{
                return false;
            }
        } else if (!is_first_letter && is_snd_letter){
            if (Float.parseFloat(stat.first_variable) == r){
                return true;
            } else{
                return false;
            }
        } else {
            System.out.println("Does not fit into the system requirements");
            return false;
        }
    }

    public static boolean logGreaterEqual(Statement stat, int r){
        boolean is_first_letter = false;
        boolean is_snd_letter = false;

        for (int i = 0; i < stat.first_variable.length() ;i++){
            if (Character.isLetter(stat.first_variable.charAt(i))) {
                is_first_letter = true;

            }
        }
        for (int i = 0; i < stat.second_variable.length() ;i++){
            if (Character.isLetter(stat.second_variable.charAt(i))) {
                is_snd_letter = true;
            }
        }

        if (!is_first_letter && !is_snd_letter){
            if (Float.parseFloat(stat.first_variable) >= Float.parseFloat(stat.second_variable)){
                return true;
            } else{
                return false;
            }
        } else if (is_first_letter && !is_snd_letter){
            if (r >= Float.parseFloat(stat.second_variable)){
                return true;
            } else{
                return false;
            }
        } else if (!is_first_letter && is_snd_letter){
            if (Float.parseFloat(stat.first_variable) >= r){
                return true;
            } else{
                return false;
            }
        } else {
            System.out.println("Does not fit into the system requirements");
            return false;
        }
    }

    public static boolean logLessEqual(Statement stat, int r){
        boolean is_first_letter = false;
        boolean is_snd_letter = false;

        for (int i = 0; i < stat.first_variable.length() ;i++){
            if (Character.isLetter(stat.first_variable.charAt(i))) {
                is_first_letter = true;
            }
        }
        for (int i = 0; i < stat.second_variable.length() ;i++){
            if (Character.isLetter(stat.second_variable.charAt(i))) {
                is_snd_letter = true;
            }
        }

        if (!is_first_letter && !is_snd_letter){
            if (Float.parseFloat(stat.first_variable) <= Float.parseFloat(stat.second_variable)){
                return true;
            } else{
                return false;
            }
        } else if (is_first_letter && !is_snd_letter){
            if (r <= Float.parseFloat(stat.second_variable)){
                return true;
            } else{
                return false;
            }
        } else if (!is_first_letter && is_snd_letter){
            if (Float.parseFloat(stat.first_variable) <= r){
                return true;
            } else{
                return false;
            }
        } else {
            System.out.println("Does not fit into the system requirements");
            return false;
        }
    }

}
