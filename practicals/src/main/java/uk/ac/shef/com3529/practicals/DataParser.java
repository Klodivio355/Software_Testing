package uk.ac.shef.com3529.practicals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class DataParser {
    public static int counter;

    public static LogicStatementNode.StatementType parseType(String body){
        String if_string = "if ";
        String elif_string = "else if ";
        String else_string = "else ";

        if (body.contains(elif_string)){
            return LogicStatementNode.StatementType.ELSE_IF;
        } else if (body.contains(if_string)){
            return LogicStatementNode.StatementType.IF;
        } else if (body.contains(else_string)){
            return LogicStatementNode.StatementType.ELSE;
        }
        return null;
    }

    public static Statement.LogicType parseSign(String body){
        String less_than_string = "<";
        String less_or_equal_than_string = "<=";
        String greater_than_string = ">";
        String greater_or_equal_then = ">=";
        String equals = "==";

        if (body.contains(less_than_string)){
            return Statement.LogicType.LESS_THAN;
        } else if (body.contains(less_or_equal_than_string)){
            return Statement.LogicType.LESS_EQUAL_THAN;
        } else if (body.contains(greater_than_string)){
            return Statement.LogicType.GREATER_THAN;
        } else if (body.contains(greater_or_equal_then)){
            return Statement.LogicType.GREATER_EQUAL_THAN;
        } else if (body.contains(equals)){
            return Statement.LogicType.EQUALS;
        } else{
            return null;
        }
    }

    public static Relation.RelationType parseRelationType(String body){
        String and_string = "&&";
        String or_string = "||";

        if (body.contains(and_string)){
            return Relation.RelationType.AND;
        } else if (body.contains(or_string)){
            return Relation.RelationType.OR;
        } else {
            return Relation.RelationType.NONE;
        }
    }

    public static String parseParenthesis(String body){
        String open_par = "(";
        String closed_par = ")";

        if (body.contains(open_par) || body.contains(closed_par)) {
            String result = body.substring(body.indexOf("(") + 1, body.indexOf(")"));
            return result;
        } else{
            return null;
        }
    }

    public static boolean is_multiple(String body){
        Boolean is_multiple = false;
        String and_string = "&&";
        String or_string = "||";
        String[] split_string = body.split(" ");

        for (int i = 0 ; i < split_string.length ; i++) {
            if (Stream.of(and_string, or_string).anyMatch(split_string[i]::contains)) {
                is_multiple = true;
            }
        }

        return is_multiple;
    }


    public static List<Statement> parseStatements(String body){
        ArrayList<Statement> result = new ArrayList<Statement>();

        if (body == null){
            return result;
        }

        String and_string = "&&";
        String or_string = "||";
        Boolean is_multiple = false;
        String second_variable ;
        String first_variable ;
        String third_variable ;
        String forth_variable ;
        Statement.LogicType sign;
        Statement.LogicType sign2;
        Relation.RelationType relation_operator;
        String[] split_string = body.split(" ");

        for (int i = 0 ; i < split_string.length ; i++) {
            if (Stream.of(and_string, or_string).anyMatch(split_string[i]::contains)) {
                is_multiple = true;
            }
        }

        first_variable = split_string[0];
        sign = parseSign(split_string[1]);
        second_variable = split_string[2];

        Statement left_part = new Statement(counter, first_variable, second_variable, sign);
        counter ++;

        result.add(left_part);

        if(is_multiple){
            third_variable = split_string[4];
            sign2 = parseSign(split_string[5]);
            forth_variable = split_string[6];
            Statement right_part = new Statement(counter, third_variable, forth_variable, sign2);
            counter ++;
            result.add(right_part);
        }

        return result;

    }

    public static void main(String[] args) {

        // This path may need to be changed as IntelliJ seems to only work with absolute paths
        String path = "/Users/maximefontana/Desktop/Third_Year/Testing/com3529-code/practicals/src/main/java/uk/ac/shef/com3529/practicals/BMICalculator.java";
        List<String> logic_statements = Arrays.asList("if ", "else if ", "else ", "while ", "for ");

        try {
            String content = Files.readString(Paths.get(path));
            //System.out.println(content);
            String[] lines = content.split("\\n");
            List<Integer> indices = new ArrayList<>();
            List<LogicStatementNode> results = new ArrayList<>();
            List<List<Criterion>> pre_complete_truth_table = new ArrayList<>();
            List<Criterion> complete_truth_table = new ArrayList<>();

            // Return the indices that contain logical statements so they can analysed later on
            for (int i = 0 ; i<lines.length ; i++){
                if (Stream.of("if ", "else if ", "else ", "while ", "for ").anyMatch(lines[i]::contains)){
                    //System.out.println("line " + (i+1) +  " ");
                    indices.add(i);
                }
            }

            // Print the lines that we are going to be analysing
            for (Integer indice : indices){
                if (Stream.of("    ").anyMatch(lines[indice]::contains)) {
                    System.out.println(lines[indice]);
                }
            }

            int current_dep= 0;
            // Process
            for (Integer indice : indices){
                System.out.println("Processing node : " + (indice+1));
                LogicStatementNode.StatementType type = parseType(lines[indice]);
                String inside_parenthesis = parseParenthesis(lines[indice]);
                List<Statement> processed_parenthesis = parseStatements(inside_parenthesis);
                ArrayList<Relation> relations = new ArrayList<Relation>();

                if (processed_parenthesis.size() > 1) { // check if multiple
                    //System.out.println(processed_parenthesis.get(1));
                    int left_id = processed_parenthesis.get(0).id;
                    int right_id = processed_parenthesis.get(1).id;
                    //System.out.println(inside_parenthesis);
                    Relation.RelationType relation_type = parseRelationType(inside_parenthesis);
                    //System.out.println(relation_type);
                    Relation relation = new Relation(left_id, right_id, relation_type);
                    relations.add(relation);
                }

                // apply logic
                if (type == LogicStatementNode.StatementType.IF){
                    current_dep = indice;
                }

                LogicStatementNode current_node = new LogicStatementNode(indice+1, current_dep, type, processed_parenthesis, relations);
                results.add(current_node);

            }

            for (LogicStatementNode elem : results){
                System.out.println(elem);
                // this is where you can actually build the truth tables for each logic node.
                pre_complete_truth_table.add(Criterion.generate_complete_condition_table(elem));
            }


            for (List<Criterion> elem : pre_complete_truth_table){
                for (Criterion elem2 : elem){
                    complete_truth_table.add(elem2);
                }
            }

            System.out.println("-------------RESTRICTED MCDC TABLE-----------");
            for (Criterion elem : complete_truth_table){
                System.out.println(elem);
            }

            System.out.println("------HERE ARE THE NODES-------"); // you could apply Testing here
            for (LogicStatementNode node : results){
                System.out.println(node);
            }

            /*MethodTree main_tree = new MethodTree(results.get(0));
            for (int i = 1; i<results.size(); i++){
                main_tree.insert(results.get(i));
            }*/

            // At this point, the nodes have been stored in the data structure MethodTree. Now we need to process it and perform our
            // Software Testing Techniques
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
