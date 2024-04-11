public class DumbMachine2 {

    private int[][][] states; // first layer maps indices to states, second layer maps states to their transitions, third layer is the transition (next state, what's being written, left/right/terminate)
    private Tape tape;
    private int current_state = 0;
    private boolean has_terminated = false;
    private static int input_length;
    private static int num_of_states;
    private static int alphabet_length;

    public DumbMachine2(String input, int[][][] state, int start) {
        tape = new Tape(input, start);
        states = state;
    }

    public void nextState() { // finds next state from states and writes to tape
        int[] instruction = states[current_state][tape.getValue()];
        tape.writeValue(instruction[1]);
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

    public int getSum() {
        return tape.getSum();
    }

    public long getScore() {
        return tape.getScore() / (input_length + num_of_states + alphabet_length);
    }

    public static void main(String[] args) {
        int[][][] shift_dec_states = {
            {{1, 0, 1}, {2, 0, 1}}, // s0
            {{1, 0, 1}, {2, 0, 1}, {3, 0, 1}}, // read a 0
            {{1, 1, 1}, {2, 1, 1}, {3, 1, 1}}, // read a 1
            {{4, 2, 0}},
            {{4, 1, 0}, {5, 0, 0}, {4, 1, 2}}, // s4
            {{5, 0, 0}, {5, 1, 0}, {6, 1, 1}}, // read a 1
            {{0, 2, 1}, {0, 2, 1}} // read a 1 // termination
        };
        String alphabet = "012Σ";
        String input = "2111111111111111111111112";

        input_length = input.length();
        num_of_states = shift_dec_states.length;
        alphabet_length = alphabet.length();

        DumbMachine2 shift_dec = new DumbMachine2(input, shift_dec_states, 1);
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