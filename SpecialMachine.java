public class SpecialMachine {

    private int[][][] states; // first layer maps indices to states, second layer maps states to their transitions, third layer is the transition (next state, what's being written, left/right/terminate)
    private NewTape tape;
    private int current_state = 0;
    private boolean has_terminated = false;

    public SpecialMachine(long[] input, int[][][] state, long start) {
        tape = new NewTape(input, start);
        states = state;
        // System.err.println(tape);
        // System.err.println(tape.pointer);
    }

    public void nextState() { // finds next state from states and writes to tape
        int[] instruction = states[current_state][(int)tape.getValue()];
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
        // System.err.println(tape);
        // System.err.println(tape.pointer);
    }

    public boolean has_terminated() {
        return has_terminated;
    }

    public long getScore() {
        return tape.getScore();
    }

    public static void main(String[] args) {
        // int[][][] shift_dec_states = {
        //     {{1, 0, 1}, {2, 0, 1}}, // s0
        //     {{1, 0, 1}, {2, 0, 1}, {3, 0, 1}}, // read a 0
        //     {{1, 1, 1}, {2, 1, 1}, {3, 1, 1}}, // read a 1
        //     {{4, 2, 0}},
        //     {{4, 1, 0}, {5, 0, 0}, {4, 1, 2}}, // s4
        //     {{5, 0, 0}, {5, 1, 0}, {6, 1, 1}}, // read a 1
        //     {{0, 2, 1}, {0, 2, 1}} // read a 1 // termination
        // };
        // SpecialMachine shift_dec = new SpecialMachine(new long[]{2, 1, 1, 1, 1, 1, 2}, shift_dec_states, 1);
        // long start = System.currentTimeMillis();   
        // while (shift_dec.has_terminated() != true) {
        //     shift_dec.nextState();
        // }
        // long elapsedTimeMillis = System.currentTimeMillis()-start;
        // System.err.println("Test shift and decrement: ");
        // System.err.println(shift_dec.getScore());
        // System.err.println(elapsedTimeMillis / 1000.0);

        // int[][][] base6_states = {
        //     // state 0
        //     {{1, 0, 1}, {2, 0, 1}, {3, 0, 1}, {4, 0, 1}, {5, 0, 1}, {6, 0, 1}}, // s0 -> read a [num]
        //     // states 1-6: shift forward
        //     {{1, 0, 1}, {2, 0, 1}, {3, 0, 1}, {4, 0, 1}, {5, 0, 1}, {6, 0, 1}, {7, 0, 1}}, // read a 0
        //     {{1, 1, 1}, {2, 1, 1}, {3, 1, 1}, {4, 1, 1}, {5, 1, 1}, {6, 1, 1}, {7, 1, 1}}, // read a 1
        //     {{1, 2, 1}, {2, 2, 1}, {3, 2, 1}, {4, 2, 1}, {5, 2, 1}, {6, 2, 1}, {7, 2, 1}}, // read a 2
        //     {{1, 3, 1}, {2, 3, 1}, {3, 3, 1}, {4, 3, 1}, {5, 3, 1}, {6, 3, 1}, {7, 3, 1}}, // read a 3
        //     {{1, 4, 1}, {2, 4, 1}, {3, 4, 1}, {4, 4, 1}, {5, 4, 1}, {6, 4, 1}, {7, 4, 1}}, // read a 4
        //     {{1, 5, 1}, {2, 5, 1}, {3, 5, 1}, {4, 5, 1}, {5, 5, 1}, {6, 5, 1}, {7, 5, 1}}, // read a 5
        //     // state 7: put the end marker back
        //     {{8, 6, 0}},
        //     // state 8: if 0 set to 5 and repeat itself, otherwise decrement and go on to state 9
        //     {{8, 5, 0}, {9, 0, 0}, {9, 1, 0}, {9, 2, 0}, {9, 3, 0}, {9, 4, 0}, {7, 1, 2}},
        //     // state 9: already decremented, so just ignore until marker.
        //     {{9, 0, 0}, {9, 1, 0}, {9, 2, 0}, {9, 3, 0}, {9, 4, 0}, {9, 5, 0}, {10, 1, 1}},
        //     // state 10: 
        //     {{0, 6, 1}, {0, 6, 1}} // read a 1 // termination
        // };
        // SpecialMachine base6 = new SpecialMachine(new long[]{6, 5, 5, 5, 5, 5, 5, 6}, base6_states, 1);
        // long start1 = System.currentTimeMillis();   
        // while (base6.has_terminated() != true) {
        //     base6.nextState();
        // }
        // long elapsedTimeMillis1 = System.currentTimeMillis()-start1;
        // System.err.println("Test base6: ");
        // System.err.println(base6.getScore());
        // System.err.println(elapsedTimeMillis1 / 1000.0);

        // int[][][] base12_states = {
        //     // state 0
        //     {{1, 0, 1}, {2, 0, 1}, {3, 0, 1}, {4, 0, 1}, {5, 0, 1}, {6, 0, 1}, {7, 0, 1}, {8, 0, 1}, {9, 0, 1}, {10, 0, 1}, {11, 0, 1}, {12, 0, 1}}, // s0 -> read a [num]
        //     // states 1-12: shift forward
        //     {{1, 0, 1}, {2, 0, 1}, {3, 0, 1}, {4, 0, 1}, {5, 0, 1}, {6, 0, 1}, {7, 0, 1}, {8, 0, 1}, {9, 0, 1}, {10, 0, 1}, {11, 0, 1}, {12, 0, 1}, {13, 0, 1}}, // read a 0
        //     {{1, 1, 1}, {2, 1, 1}, {3, 1, 1}, {4, 1, 1}, {5, 1, 1}, {6, 1, 1}, {7, 1, 1}, {8, 1, 1}, {9, 1, 1}, {10, 1, 1}, {11, 1, 1}, {12, 1, 1}, {13, 1, 1}}, // read a 1
        //     {{1, 2, 1}, {2, 2, 1}, {3, 2, 1}, {4, 2, 1}, {5, 2, 1}, {6, 2, 1}, {7, 2, 1}, {8, 2, 1}, {9, 2, 1}, {10, 2, 1}, {11, 2, 1}, {12, 2, 1}, {13, 2, 1}}, // read a 2
        //     {{1, 3, 1}, {2, 3, 1}, {3, 3, 1}, {4, 3, 1}, {5, 3, 1}, {6, 3, 1}, {7, 3, 1}, {8, 3, 1}, {9, 3, 1}, {10, 3, 1}, {11, 3, 1}, {12, 3, 1}, {13, 3, 1}}, // read a 3
        //     {{1, 4, 1}, {2, 4, 1}, {3, 4, 1}, {4, 4, 1}, {5, 4, 1}, {6, 4, 1}, {7, 4, 1}, {8, 4, 1}, {9, 4, 1}, {10, 4, 1}, {11, 4, 1}, {12, 4, 1}, {13, 4, 1}}, // read a 4
        //     {{1, 5, 1}, {2, 5, 1}, {3, 5, 1}, {4, 5, 1}, {5, 5, 1}, {6, 5, 1}, {7, 5, 1}, {8, 5, 1}, {9, 5, 1}, {10, 5, 1}, {11, 5, 1}, {12, 5, 1}, {13, 5, 1}}, // read a 5
        //     {{1, 6, 1}, {2, 6, 1}, {3, 6, 1}, {4, 6, 1}, {5, 6, 1}, {6, 6, 1}, {7, 6, 1}, {8, 6, 1}, {9, 6, 1}, {10, 6, 1}, {11, 6, 1}, {12, 6, 1}, {13, 6, 1}}, // read a 6
        //     {{1, 7, 1}, {2, 7, 1}, {3, 7, 1}, {4, 7, 1}, {5, 7, 1}, {6, 7, 1}, {7, 7, 1}, {8, 7, 1}, {9, 7, 1}, {10, 7, 1}, {11, 7, 1}, {12, 7, 1}, {13, 7, 1}}, // read a 7
        //     {{1, 8, 1}, {2, 8, 1}, {3, 8, 1}, {4, 8, 1}, {5, 8, 1}, {6, 8, 1}, {7, 8, 1}, {8, 8, 1}, {9, 8, 1}, {10, 8, 1}, {11, 8, 1}, {12, 8, 1}, {13, 8, 1}}, // read a 8
        //     {{1, 9, 1}, {2, 9, 1}, {3, 9, 1}, {4, 9, 1}, {5, 9, 1}, {6, 9, 1}, {7, 9, 1}, {8, 9, 1}, {9, 9, 1}, {10, 9, 1}, {11, 9, 1}, {12, 9, 1}, {13, 9, 1}}, // read a 9
        //     {{1,10, 1}, {2,10, 1}, {3,10, 1}, {4,10, 1}, {5,10, 1}, {6,10, 1}, {7,10, 1}, {8,10, 1}, {9,10, 1}, {10,10, 1}, {11,10, 1}, {12,10, 1}, {13,10, 1}}, // read a 10
        //     {{1,11, 1}, {2,11, 1}, {3,11, 1}, {4,11, 1}, {5,11, 1}, {6,11, 1}, {7,11, 1}, {8,11, 1}, {9,11, 1}, {10,11, 1}, {11,11, 1}, {12,11, 1}, {13,11, 1}}, // read a 11
        //     // state 13: put the end marker back
        //     {{14, 12, 0}},
        //     // state 14: if 0 set to 11 and repeat itself, otherwise decrement and go on to state 15
        //     {{14, 11, 0}, {15, 0, 0}, {15, 1, 0}, {15, 2, 0}, {15, 3, 0}, {15, 4, 0}, {15, 5, 0}, {15, 6, 0}, {15, 7, 0}, {15, 8, 0}, {15, 9, 0}, {15,10, 0}, {13, 1, 2}},
        //     // state 15: already decremented, so just ignore until marker.
        //     {{15, 0, 0}, {15, 1, 0}, {15, 2, 0}, {15, 3, 0}, {15, 4, 0}, {15, 5, 0}, {15, 6, 0}, {15, 7, 0}, {15, 8, 0}, {15, 9, 0}, {15,10, 0}, {15,11, 0}, {16, 1, 1}},
        //     // state 16: 
        //     {{0, 12, 1}, {0, 12, 1}} // read a 1 // termination
        // };
        // long[] input12 = new long[]{12, 11, 11, 11, 11, 11, 11, 12};
        // SpecialMachine base12 = new SpecialMachine(input12, base12_states, 1);
        // long start2 = System.currentTimeMillis();   
        // while (base12.has_terminated() != true) {
        //     base12.nextState();
        // }
        // long elapsedTimeMillis2 = System.currentTimeMillis()-start2;
        // System.err.println("Test base12: ");
        // System.err.println(base12.getScore() / (base12_states.length + input12.length + 13));
        // System.err.println(elapsedTimeMillis2 / 1000.0);

        int N = 8;
        int[][][] baseN_states = new int[N + 5][N + 1][3];
        for (int i = 0; i < N; i++) {
            baseN_states[0][i][0] = i + 1;
            baseN_states[0][i][1] = 0;
            baseN_states[0][i][2] = 1;
        }
        for (int i = 0; i < N; i++) { // range of N
            for (int j = 1; j <= N + 1; j++) { // range of N + 1
                baseN_states[i + 1][j - 1][0] = j;
                baseN_states[i + 1][j - 1][1] = i;
                baseN_states[i + 1][j - 1][2] = 1;
            }
        }
        baseN_states[N + 1][0][0] = N + 2;
        baseN_states[N + 1][0][1] = N;
        baseN_states[N + 1][0][2] = 0;
        baseN_states[N + 2][0][0] = N + 2;
        baseN_states[N + 2][0][1] = N - 1;
        baseN_states[N + 2][0][2] = 0;
        baseN_states[N + 2][N][0] = N + 1;
        baseN_states[N + 2][N][1] = 1;
        baseN_states[N + 2][N][2] = 2;
        for (int i = 1; i < N; i++) {
            baseN_states[N + 2][i][0] = N + 3;
            baseN_states[N + 2][i][1] = i - 1;
            baseN_states[N + 2][i][2] = 0;
        }
        for (int i = 0; i < N; i++) {
            baseN_states[N + 3][i][0] = N + 3;
            baseN_states[N + 3][i][1] = i;
            baseN_states[N + 3][i][2] = 0;
        }
        baseN_states[N + 3][N][0] = N + 4;
        baseN_states[N + 3][N][1] = 1;
        baseN_states[N + 3][N][2] = 1;
        baseN_states[N + 4][0][0] = 0;
        baseN_states[N + 4][0][1] = N;
        baseN_states[N + 4][0][2] = 1;
        baseN_states[N + 4][1][0] = 0;
        baseN_states[N + 4][1][1] = N;
        baseN_states[N + 4][1][2] = 1;
        long[] inputN = new long[]{N, 3, N-1, N-1, N-1, N-1, N-1, N-1, N-1, N-1, N-1, N-1, N};
        SpecialMachine baseN = new SpecialMachine(inputN, baseN_states, 1);
        long startN = System.currentTimeMillis();   
        while (baseN.has_terminated() != true) {
            baseN.nextState();
        }
        long elapsedTimeMillisN = System.currentTimeMillis()-startN;
        System.err.println("Test baseN: ");
        System.err.println(baseN.getScore() / (baseN_states.length + inputN.length + N + 1));
        System.err.println(elapsedTimeMillisN / 1000.0 / 60.0);
        // System.err.println(baseN.getScore() / (baseN_states.length + inputN.length + N + 1) / (elapsedTimeMillisN / 1000.0) * 60);
    }
}