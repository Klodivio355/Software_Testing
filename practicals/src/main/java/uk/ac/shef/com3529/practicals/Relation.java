package uk.ac.shef.com3529.practicals;


public class Relation {


    public enum RelationType {
        AND,
        OR,
        NONE,
    }

    int left_id, right_id;
    RelationType type;

    public Relation(int left_id, int right_id, RelationType type){
        this.left_id = left_id;
        this.right_id = right_id;
        this.type = type;
    }

    public String toString(){
        return "Left part : " + this.left_id + " , Right Part : " + this.right_id + " , Type : " + this.type;
    }
}
