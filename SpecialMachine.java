public class SpecialMachine {

    private int[][][] states; // first layer maps indices to states, second layer maps states to their transitions, third layer is the transition (next state, what's being written, left/right/terminate)
    private NewTape tape;
    private int current_state = 0;
    private boolean has_terminated = false;

    public SpecialMachine(String input, int[][][] state, long start) {
        tape = new NewTape(input, start);
        states = state;
    }

    public void nextState() { // finds next state from states and writes to tape
        System.err.println(tape);

        int[] instruction = states[current_state][(int)tape.getValue()];
        tape.writeValue(tape.pointer, instruction[1]);
        if (instruction[2] == 0) {
            tape.moveLeft();
            current_state = instruction[0];
        } else if (instruction[2] == 1) {
            tape.moveRight();
            current_state = instruction[0];
        } else {
            has_terminated = true;
        }
    }

    public boolean has_terminated() {
        return has_terminated;
    }

    public long getSum() {
        return tape.getSum();
    }

    public static void main(String[] args) {

        // System.err.println(tape);

        int[][][] shift_dec_states = {
            {{1, 0, 1}, {2, 0, 1}}, // s0
            {{1, 0, 1}, {2, 0, 1}, {3, 0, 1}}, // read a 0
            {{1, 1, 1}, {2, 1, 1}, {3, 1, 1}}, // read a 1
            {{4, 2, 0}},
            {{4, 1, 0}, {5, 0, 0}, {4, 1, 2}}, // s4
            {{5, 0, 0}, {5, 1, 0}, {6, 1, 1}}, // read a 1
            {{0, 2, 1}, {0, 2, 1}} // read a 1 // termination
        };
        SpecialMachine shift_dec = new SpecialMachine("212", shift_dec_states, 1);
        long start = System.currentTimeMillis();   
        while (shift_dec.has_terminated() != true) {
            shift_dec.nextState();
        }
        long elapsedTimeMillis = System.currentTimeMillis()-start;
        System.err.println("Test shift and decrement: ");
        System.err.println(shift_dec.getSum());
        System.err.println(elapsedTimeMillis / 1000.0);
    }
}