package uk.ac.shef.com3529.practicals;

import java.util.ArrayList;
import java.util.List;

public class Criterion {

    //public int id;
    public int node_id;
    public boolean[] values;
    public int condition_id;
    public boolean predicate;
    public boolean satisfied;
    public static int id = 0;

    public Criterion(int id, int node_id, boolean[] values, boolean predicate){
        this.id = id;
        this.node_id = node_id;
        this.values = values;
        this.predicate = predicate;
        this.satisfied = false;
    }

    public void setSatisfied(boolean value){
        this.satisfied = value;
    }

    public boolean getSatisfied(){
        return satisfied;
    }

    public String printValues(boolean[] arr){
        String result = "";
        for (int i = 0; i < values.length ; i++){
            result += values[i];
            result += " ";
        }
        return result;
    }

    public String toString(){
        return "Criterion ID : " + this.id + " , Node ID : " + this.node_id + " , Values : " + printValues(this.values) + " Branch : " + this.predicate  + " satisfied : " + satisfied;
    }

    public static List<Criterion> generate_complete_condition_table(LogicStatementNode node){
        ArrayList<Criterion> result = new ArrayList<Criterion>();
        ArrayList<Criterion> mcdc = new ArrayList<Criterion>();


        if(node.statements.size() == 1){ // if 1 statement
            for (int a = 0; a<=node.statements.size(); a++){
                boolean[] values = new boolean[node.statements.size()];
                if (a == 0) {
                    boolean truth_value = false;
                    values[0] = truth_value;
                } else{
                    boolean truth_value = true;
                    values[0] = truth_value;
                }
                Criterion criteria = new Criterion(id, node.id, values, values[0]);
                System.out.println(criteria);
                result.add(criteria);
                id++;
            }
        }
        else if(node.statements.size() == 2) { // if 2 statements
            for (int a = 0; a < node.statements.size(); a++) {
                for (int b = 0; b < node.statements.size(); b++) {
                    boolean left_part = false;
                    boolean right_part = false;
                    if (a==0){
                        left_part = false;
                    } else{
                        left_part = true;
                    }
                    if(b==0){
                        right_part = false;
                    } else{
                        right_part = true;
                    }

                    boolean[] values = new boolean[node.statements.size()];
                    values[0] = left_part;
                    values[1] = right_part;

                    boolean branch_predicate = false;
                    if (node.relations.get(0).type == Relation.RelationType.AND){
                        if (left_part && right_part){
                            branch_predicate = true;
                        }
                    } else if (node.relations.get(0).type == Relation.RelationType.OR){
                        if (left_part || right_part){
                            branch_predicate = true;
                        }
                    }

                    Criterion criteria = new Criterion(id, node.id, values, branch_predicate);
                    System.out.println(criteria);
                    result.add(criteria);
                    id++;
                }
            }
        }
        else{
            for (int a = 0; a < node.statements.size(); a++) {
                for (int b = 0; b < node.statements.size(); b++) {
                    for (int c = 0; c < node.statements.size(); c++) {
                        boolean left_part = false;
                        boolean middle_part = false;
                        boolean right_part = false;
                        if (a==0){
                            left_part = false;
                        } else{
                            left_part = true;
                        }
                        if(b==0){
                            middle_part = false;
                        } else{
                            middle_part = true;
                        }
                        if(c==0){
                            right_part = false;
                        } else{
                            right_part = true;
                        }

                        boolean[] values = new boolean[node.statements.size()];
                        values[0] = left_part;
                        values[1] = middle_part;
                        values[2] = right_part;

                        boolean left_branch_predicate = false;
                        boolean right_branch_predicate = false;
                        boolean branch_predicate = false;


                        if(node.relations.get(0).type == Relation.RelationType.AND){
                            if (left_part && middle_part){
                                left_branch_predicate = true;
                            }
                        } else if (node.relations.get(0).type == Relation.RelationType.OR){
                            if (left_part || right_part){
                                left_branch_predicate = true;
                            }
                        }


                        if(node.relations.get(1).type == Relation.RelationType.AND){
                            if (middle_part && right_part){
                                right_branch_predicate = true;
                            }
                        } else if (node.relations.get(1).type == Relation.RelationType.OR){
                            if (middle_part || right_part){
                                right_branch_predicate = true;
                            }
                        }

                        if (left_branch_predicate && right_branch_predicate){
                            branch_predicate = true;
                        }

                        Criterion criteria = new Criterion(id, node.id, values, branch_predicate);
                        System.out.println(criteria);
                        result.add(criteria);
                        id++;
                    }
                }
            }
        }

        if (result.size() == 2){
            for (Criterion elem : result){
                mcdc.add(elem);
            }
        } else if (result.size() == 4){
            if (node.relations.get(0).type == Relation.RelationType.AND){
                System.out.println("FOR MCDC " + result.get(2));
                System.out.println("FOR MCDC " + result.get(3));
                mcdc.add(result.get(2));
                mcdc.add(result.get(3));
            }
            else if (node.relations.get(0).type == Relation.RelationType.OR){
                System.out.println("FOR MCDC " + result.get(1));
                System.out.println("FOR MCDC " + result.get(2));
                System.out.println("FOR MCDC " + result.get(3));

                mcdc.add(result.get(0));
                mcdc.add(result.get(1));
                mcdc.add(result.get(2));
            }

        }

        return mcdc;
    }

}