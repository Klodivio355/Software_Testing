package uk.ac.shef.com3529.practicals;

public class Classification {

    // Returns different type of congratulations based on given mark out of 20
    public enum Type {
        MEAN,
        TOP,
        REALLY,
        BAD;
    }

    public static Classification.Type classification() {
        int mark = 0;

        if (mark == 0 || mark == 1) {
            return Type.REALLY;
        } else if (10 > mark) {
            return Type.BAD;
        } else if (mark == 10 || mark <= 15) {
            return Type.MEAN;
        } else {
            return Type.TOP;
        }
    }
}
