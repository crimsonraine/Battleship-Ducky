import java.util.ArrayList;

public class NewTape {
    
    private Long pinf = Long.MAX_VALUE;
    private Long ninf = Long.MIN_VALUE;
    private ArrayList<Long[]> ranges = new ArrayList<Long[]>(); // {[ninf, -10, 0], [-9, pinf, 1]} [lower bound, higher bound, value] inclusive
    public long pointer;
    public int current_range = 0;

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
        return ranges.get(current_range)[2];
    }

    public void moveLeft() {
        if (ranges.get(current_range)[0] == pointer) {
            current_range--;
        }
        pointer--;
    }

    public void moveRight() {
        if (ranges.get(current_range)[1] == pointer) {
            current_range++;
        }
        pointer++;
    }

    public void writeValue(long pointer, long value) { // writes the current value and updates the pointer
        long current_value = ranges.get(current_range)[2];
        long current_low = ranges.get(current_range)[0];
        long current_high = ranges.get(current_range)[1];
        if (value != current_value) { 
            if (pointer == current_high && pointer == current_low) {
                ranges.get(current_range)[2] = value;
            } else if (pointer == current_high) { // right edge
                // check for if it's the rightmost range, if it is then it's straightforward
                if (current_high == pinf) {
                    ranges.get(current_range)[1] = pointer - 1; // new left
                    ranges.add(new Long[]{pointer, pointer, value}); // new middle
                    ranges.add(new Long[]{pointer + 1, pinf, current_value}); // new right
                    current_range++;
                } else if (ranges.get(current_range + 1)[2] == value) { // if the thing to the right is the same thing, then merge
                    ranges.get(current_range)[1]--;
                    ranges.get(current_range + 1)[0]--;
                    current_range++;
                } else { // make new range
                    ranges.get(current_range)[1]--;
                    ranges.add(current_range + 1, new Long[]{pointer, pointer, value});
                    current_range++;
                }
            } else if (pointer == current_low) { // left edge
                // check for if it's the leftmost range, if it is then it's straightforward
                if (current_low == ninf) {
                    ranges.get(current_range)[0] = pointer + 1; // new right
                    ranges.add(new Long[]{pointer, pointer, value}); // new middle
                    ranges.add(new Long[]{ninf, pointer - 1, current_value}); // new left
                    current_range++;
                } else if (ranges.get(current_range - 1)[2] == value) { // if the thing to the right is the same thing, then merge
                    ranges.get(current_range - 1)[1]++;
                    ranges.get(current_range)[0]++;
                    current_range--;
                } else { // make new range
                    ranges.get(current_range)[0]++;
                    ranges.add(current_range, new Long[]{pointer, pointer, value});
                }
            } else { // middle insert |1| --> |new new 1|
                ranges.get(current_range)[0] = pointer + 1; // new right
                ranges.add(current_range, new Long[]{pointer, pointer, value}); // new middle
                ranges.add(current_range, new Long[]{current_low, pointer - 1, current_value}); // new left
                current_range++; // move one to the right
            }
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

    public long getScore() {
        long total = 0;
        for (int i = 0; i < ranges.size(); i++) {
            if (ranges.get(i)[2] == 1L) {
                total += (ranges.get(i)[1] - ranges.get(i)[0] + 1);
            }
        }
        return total;
    }

}
