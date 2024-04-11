import java.util.ArrayList;

public class NewTape {
    
    private Long pinf = Long.MAX_VALUE;
    private Long ninf = Long.MIN_VALUE;
    private ArrayList<Long[]> ranges = new ArrayList<Long[]>(); // {[ninf, -10, 0], [-9, pinf, 1]} [lower bound, higher bound, value] inclusive
    public long pointer;

    public NewTape(String input, long start) {
        Long[] everything = {ninf, pinf, 0L};
        ranges.add(everything);

        for (int i = 0; i < input.length(); i++){ // inputs starting sequence
            char c = input.charAt(i);        
            writeValue(start, Long.valueOf(c) - Long.valueOf('0'));
        }

        setPointer(start);
    }

    public long getValue() {
        if (pointer >= 0) {
            return 0L;
        } else {
            return 0L;
        }
    }

    public void moveLeft() {
        pointer--;
    }

    public void moveRight() {
        pointer++;
    }

    public void writeValue(long pointer, long value) {
        if (pointer >= 0) {
            
        } else {
            
        }
    }

    public void setPointer(long value) {
        pointer = value;
    }

    public String toString() {
        return ranges.toString();
    }

    public long getSum() {
        return 0L;
    }

}
