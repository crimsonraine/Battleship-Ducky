import java.util.ArrayList;

public class Tape {
    
    private ArrayList<Integer> positive = new ArrayList<Integer>(); // includes 0
    private ArrayList<Integer> negative = new ArrayList<Integer>();
    public int pointer;

    public Tape(String input, int start) {
        for (int i = 0; i < input.length(); i++){
            char c = input.charAt(i);        
            positive.add(Integer.valueOf(c) - Integer.valueOf('0'));
        }
        if (positive.size() == 0) {
            positive.add(0);
        }
        setPointer(start);
    }

    public int getValue() {
        if (pointer >= 0) {
            return positive.get(pointer);
        } else {
            return negative.get(pointer * -1 - 1);
        }
    }

    public void moveLeft() {
        pointer--;
        if (pointer < 0 && pointer * -1 - 1 >= negative.size()) {
            negative.add(0);
        }
    }

    public void moveRight() {
        pointer++;
        if (pointer >= 0 && pointer >= positive.size()) {
            positive.add(0);
        }
    }

    public void writeValue(int value) {
        if (pointer >= 0) {
            positive.set(pointer, value);
        } else {
            negative.set(pointer * -1 - 1, value);
        }
    }

    public void setPointer(int value) {
        pointer = value;
    }

    public String toString() {
        String ret = "";
        for (int i = negative.size() - 1; i >= 0; i--) {
            ret += negative.get(i) + " ";
        }
        ret += "|";
        for (int i = 0; i < positive.size(); i++) {
            ret += positive.get(i) + " ";
        }
        return ret;
    }

    public int getSum() {
        return positive.stream().mapToInt(a -> a).sum() + negative.stream().mapToInt(a -> a).sum();
    }

}
