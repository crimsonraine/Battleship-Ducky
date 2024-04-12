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
            writeValue(pointer, Long.valueOf(c) - Long.valueOf('0'));
            moveRight();
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

    public void writeValue(long pointer, long value) { // writes the current value and updates the pointer, cases which are "finitely" close to pinf and ninf are not considered
        long current_value = ranges.get(current_range)[2];
        long current_low = ranges.get(current_range)[0];
        long current_high = ranges.get(current_range)[1];
        if (value != current_value) { 
            if (pointer == current_high && pointer == current_low) { // singleton case: check if shares value with left/right/both/neither, in theory guarenteed to be not on pinf and ninf
                if (ranges.get(current_range - 1)[2] == value && ranges.get(current_range + 1)[2] == value) { // same on both sides
                    ranges.set(current_range - 1, new Long[]{ranges.get(current_range - 1)[0], ranges.get(current_range + 1)[1], value}); // edits left
                    ranges.remove(current_range); // removes middle
                    ranges.remove(current_range); // removes right
                    current_range--;
                } else if (ranges.get(current_range + 1)[2] == value) { // same on right edge
                    ranges.set(current_range + 1, new Long[]{pointer, ranges.get(current_range + 1)[1], value});
                    ranges.remove(current_range);
                } else if (ranges.get(current_range - 1)[2] == value) { // same on left edge
                    ranges.set(current_range - 1, new Long[]{ranges.get(current_range - 1)[0], pointer, value});
                    ranges.remove(current_range);
                    current_range--;
                } else { // different both sides
                    ranges.set(current_range, new Long[]{pointer, pointer, value});
                }
            } else if (pointer == current_high) { // right edge
                if (ranges.get(current_range + 1)[2] == value) { // if the thing to the right is the same thing, then merge
                    ranges.set(current_range, new Long[]{current_low, current_high - 1, current_value});
                    ranges.set(current_range + 1, new Long[]{current_high, ranges.get(current_range + 1)[1], value});
                    current_range++;
                } else { // make new range
                    ranges.set(current_range, new Long[]{current_low, current_high - 1, current_value});
                    ranges.add(current_range + 1, new Long[]{pointer, pointer, value});
                    current_range++;
                }
            } else if (pointer == current_low) { // left edge
                if (ranges.get(current_range - 1)[2] == value) { // if the thing to the right is the same thing, then merge
                    ranges.set(current_range - 1, new Long[]{ranges.get(current_range - 1)[0], current_low, value});
                    ranges.set(current_range, new Long[]{current_low + 1, current_high, current_value});
                    current_range--;
                } else { // make new range
                    ranges.set(current_range, new Long[]{current_low + 1, current_high, current_value})[0]++;
                    ranges.add(current_range, new Long[]{pointer, pointer, value});
                }
            } else { // middle insert |1| --> |new new 1|
                ranges.set(current_range, new Long[]{pointer + 1, current_high, current_value}); // new right
                ranges.add(current_range, new Long[]{pointer, pointer, value}); // new middle
                ranges.add(current_range, new Long[]{current_low, pointer - 1, current_value}); // new left
                current_range++; // move one to the right
            }
        }
    }

    public void setPointer(long value) {
        pointer = value;
        current_range = 0;
        while (pointer > ranges.get(current_range)[1]) {
            current_range++;
        }
    }

    public String toString() {
        String ret = "";
        for (int i = 0; i < ranges.size(); i++) {
            for (Long l: ranges.get(i)) {
                if (l.longValue() == ninf) {
                    ret += "ninf ";
                } else if (l.longValue() == pinf) {
                    ret += "pinf ";
                } else {
                    ret += l.toString() + " ";
                }
            }
            ret += " ";
        }
        return ret;
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
