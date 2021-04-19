package uk.ac.shef.com3529.practicals;

import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

public class Coverage {

    static final int ITERATIONS = 1000;

    //static final int MIN_INT = Integer.MIN_VALUE;
    //static final int MAX_INT = Integer.MAX_VALUE;
    static final int MIN_INT = 0;
    static final int MAX_INT = 100;

    static int randomInt(Random r) {
        if (MIN_INT == Integer.MIN_VALUE && MAX_INT == Integer.MAX_VALUE) {
            return r.nextInt();
        } else {
            return r.nextInt((MAX_INT - MIN_INT + 1)) + MIN_INT;
        }
    }

    static void randomlyTestClassify(List<LogicStatementNode> nodes, List<Criterion> mcdc_requirements) {
        Random r = new Random();
        Set<Integer> coveredBranches = new TreeSet<>();

        for (int i=0; i < ITERATIONS; i ++) {
            int ran_int = randomInt(r);
            for (LogicStatementNode elem : nodes){
                //instrumentedClassify(ran_int, coveredBranches, elem);
            }
            System.out.println((i+1) + ": [" + ran_int + "]");

        }

        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("Branch Coverage: " + coveredBranches.size() + " /" + mcdc_requirements.size());
        System.out.println("Covered Branch IDs: " + coveredBranches);
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
    }


    static void instrumentedClassify2(int r, List<Criterion> requirements, LogicStatementNode node) {
        //  this function's got to be operated over the set of nodes
        // This function's got to  work out the logic, by that I mean, it must check the statements, the relations of a
        // specific node

        // This function needs to stop if a branch predicate evaluates to false.

        boolean[] truth_array = new boolean[node.statements.size()];
        int node_id = node.id;
        Statement left_statement = null;
        Statement right_statement = null;

        if (node.relations.size() == 1){
            if (node.statements.get(0).type == Statement.LogicType.GREATER_THAN) {
                truth_array[0] = logGreaterThan(node.statements.get(0), r);
                satisfiedRequirement(node, requirements, truth_array, truth_array[0]);
                if (!truth_array[0]){
                    System.out.println("This node has evaluated to false");
                    return;
                }
            } else if (node.statements.get(0).type == Statement.LogicType.GREATER_EQUAL_THAN){
                truth_array[0] = logGreaterEqual(node.statements.get(0), r);
                satisfiedRequirement(node, requirements, truth_array, truth_array[0]);
                if (!truth_array[0]){
                    System.out.println("This node has evaluated to false");
                    return;
                }
            } else if (node.statements.get(0).type == Statement.LogicType.LESS_THAN){
                truth_array[0] = logLessThan(node.statements.get(0),  r);
                satisfiedRequirement(node, requirements, truth_array, truth_array[0]);
                if (!truth_array[0]){
                    System.out.println("This node has evaluated to false");
                    return;
                }
            } else if (node.statements.get(0).type == Statement.LogicType.LESS_EQUAL_THAN){
                truth_array[0] = logLessEqual(node.statements.get(0),  r);
                satisfiedRequirement(node, requirements, truth_array, truth_array[0]);
                if (!truth_array[0]){
                    System.out.println("This node has evaluated to false");
                    return;
                }
            }
        }

        for (Relation rel : node.relations){
            int left_part = rel.left_id;
            int right_part =  rel.right_id;
            Relation.RelationType rel_type = rel.type;

            for (Statement stat : node.statements){
                if (stat.id == left_part){
                    left_statement = stat;
                }
                if (stat.id ==  right_part){
                    right_statement = stat;
                }
                // run function to process these 2 statements to get the actual logic type and therefore
                // grab the right function that will return a true/false value based on whether the condition was fulfilled or not
                //truth_array = process_statements(left_statement, right_statement, truth_array, rel_type); // this function is going to call which
                // this new truth_array should be sent to a function along with some other information in order to check whether any requirement has been
                // satisfied
            }
        }
    }

    static void satisfiedRequirement(LogicStatementNode node, List<Criterion> requirements, boolean[] truth_values, boolean pred) {
        for (Criterion crit : requirements){
            if (node.id == crit.id && truth_values == crit.values && pred == crit.predicate){
                if (!crit.satisfied){
                    System.out.println("New  Branch has been covered");
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
                System.out.println("First Argument is user-defined variable");

            }
        }
        for (int i = 0; i < stat.second_variable.length() ;i++){
            if (Character.isLetter(stat.second_variable.charAt(i))) {
                System.out.println("Second Argument is user-defined variable");
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
        } else if (is_first_letter && !is_snd_letter){
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
                System.out.println("First Argument is user-defined variable");

            }
        }
        for (int i = 0; i < stat.second_variable.length() ;i++){
            if (Character.isLetter(stat.second_variable.charAt(i))) {
                System.out.println("Second Argument is user-defined variable");
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
        } else if (is_first_letter && !is_snd_letter){
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

    public static boolean logGreaterEqual(Statement stat, int r){
        boolean is_first_letter = false;
        boolean is_snd_letter = false;

        for (int i = 0; i < stat.first_variable.length() ;i++){
            if (Character.isLetter(stat.first_variable.charAt(i))) {
                is_first_letter = true;
                System.out.println("First Argument is user-defined variable");

            }
        }
        for (int i = 0; i < stat.second_variable.length() ;i++){
            if (Character.isLetter(stat.second_variable.charAt(i))) {
                System.out.println("Second Argument is user-defined variable");
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
        } else if (is_first_letter && !is_snd_letter){
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
                System.out.println("First Argument is user-defined variable");

            }
        }
        for (int i = 0; i < stat.second_variable.length() ;i++){
            if (Character.isLetter(stat.second_variable.charAt(i))) {
                System.out.println("Second Argument is user-defined variable");
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
        } else if (is_first_letter && !is_snd_letter){
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
