public class LinkedTape {

    LinkedCell head;
    LinkedCell tail;
    LinkedCell current_cell = head;
    long pointer;

    public LinkedTape(long[] input, long start) {
        LinkedCell everything = new LinkedCell(Long.MIN_VALUE, Long.MAX_VALUE, 0, head, tail);
        head = everything;
        tail = everything;

        for (long i: input){ // inputs starting sequence    
            writeValue(i);
            moveRight();
        }

        pointer = start;
        current_cell = head; // finds the starting cell
        while (pointer > current_cell.value) {
            current_cell = current_cell.next;
        }
    }

    public void remove() { // removes current_cell and sets it to the one on the right
        current_cell = current_cell.next;
        current_cell.prev.prev.next = current_cell;
        current_cell.prev = current_cell.prev.prev;
    }

    public void insert(LinkedCell cell) { // inserts to the right
        current_cell.next.prev = cell;
        current_cell.next = cell;
    }

    public long getValue() {
        return current_cell.value;
    }

    public void moveLeft() {
        if (current_cell.low == pointer) {
            current_cell = current_cell.prev;
        }
        pointer--;
    }

    public void moveRight() {
        if (current_cell.high == pointer) {
            current_cell = current_cell.next;
        }
        pointer++;
    }

    public void writeValue(long new_value) { // writes the current value and updates the pointer, cases which are "finitely" close to pinf and ninf are not considered
        long current_value = current_cell.value;
        long low = current_cell.low;
        long high = current_cell.high;
        if (new_value != current_value) { 
            if (pointer == high && pointer == low) { // singleton case: check if shares value with left/right/both/neither, in theory guarenteed to be not on pinf and ninf
                if (current_cell.prev.value == new_value && current_cell.next.value == new_value) { // same on both sides
                    current_cell.prev.high = current_cell.next.high; // edits left
                    remove(); // removes middle
                    remove(); // removes right
                    current_cell = current_cell.prev; // shift left
                } else if (current_cell.next.value == new_value) { // same on right edge
                    current_cell.next.low--;
                    remove();
                } else if (current_cell.prev.value == new_value) { // same on left edge
                    current_cell.prev.high++;
                    remove();
                    current_cell = current_cell.prev;
                } else { // different both sides
                    current_cell.value = new_value;
                }
            } else if (pointer == high) { // right edge
                if (current_cell.next.value == new_value) { // if the thing to the right is the same thing, then merge
                    current_cell.high--;
                    current_cell.next.low--;
                    current_cell = current_cell.next;
                } else { // make new range
                    current_cell.high--;
                    insert(new LinkedCell(pointer, pointer, new_value, current_cell, current_cell.next));
                    current_cell = current_cell.next;
                }
            } else if (pointer == low) { // left edge
                if (current_cell.prev.value == new_value) { // if the thing to the right is the same thing, then merge
                    current_cell.prev.high++;
                    current_cell.low++;
                    current_cell = current_cell.prev;
                } else { // make new range
                    current_cell.low++; // edits current range
                    current_cell = current_cell.prev; // moves to left range
                    insert(new LinkedCell(pointer, pointer, new_value, current_cell, current_cell.next)); // fills in gap
                    current_cell = current_cell.next; // moves into new cell
                }
            } else { // middle insert |1| --> |new new 1|
                long temp_high = current_cell.high;
                current_cell.high = pointer - 1; // overwrite current to be new left
                insert(new LinkedCell(pointer + 1, temp_high, current_cell.value, current_cell, current_cell.next)); // new right
                insert(new LinkedCell(current_cell.high + 1, pointer - 1, current_cell.next.next.value, current_cell, current_cell.next)); // new middle
                current_cell = current_cell.next; // move one to the right
            }
        }
    }

    public String toString() {
        String ret = "";
        LinkedCell counter = head;
        while (counter != tail) {
            ret += counter.value;
            counter = counter.next;
        }
        ret += counter.value;
        return ret;
    }

    public long getScore() {
        long total = 0;
        LinkedCell counter = head;
        while (counter != tail) {
            if (counter.value == 1) {
                total += counter.value;
            }
            counter = counter.next;
        }
        return total;
    }

}
