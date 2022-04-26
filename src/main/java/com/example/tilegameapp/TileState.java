package com.example.tilegameapp;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Represents the state of a 3x3 tile game.
 *
 * Internally the state is represented as a flat array with 9 slots
 * storing the numbers 0 through 8. 0 is considered to be the blank. 
 * 
 * So, for example, the game state:
 * 
 * 0 3 2
 * 1 6 5
 * 4 7 8
 *s
 * is internally stored as {0, 3, 2, 1, 6, 5, 4, 7, 8}. 
 * 
 * @author Samuel H Page
 */
public class TileState {
    static int count = 0;

    // The flattened tile stored in row major order.
    private final int[] state;

    /**
     * Constructor initiates int array of length 9.
     * @param state int[]
     */
    public TileState(int[] state) {
        this.state = state;
    }

    public TileState() {
        state = null; // TODO change
    }

    /**
     * Array swap helper.
     * @param array
     * @param i
     * @param j
     */
    public static void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    /**
     *
     * @return a valid State
     */
    public static TileState randomValidState() {
        Random random = new Random();
        boolean isValid = false;
        TileState newTileState = null;
        int[] newState = {0, 1, 2, 3, 4, 5, 6, 7, 8};

        while (!isValid) {
            for (int i = 0; i < newState.length; i++) {
                int ranIndex = random.nextInt(newState.length);
                int temp = newState[ranIndex];
                newState[ranIndex] = newState[i];
                newState[i] = temp;
                newTileState = new TileState(newState);
            }
            if (isSolvable(newTileState)) {
                isValid = true;
            }
        }

        return newTileState;
    }

    /**
     *
     * @param tileState - the TileState to check
     * @return true if the given TileState is solvable. A TileState is solvable
     *         if the number of inversions is even.
     */
    public static boolean isSolvable(TileState tileState) {
        return getInversionCount(tileState) % 2 == 0;
    }

    /**
     * Count number of inversions
     *
     * @param tileState - the TileState to check
     * @return the inversion count
     */
    public static int getInversionCount(TileState tileState) {
        int count = 0;

        for (int i = 0; i < 3 - 1; i++) {
            for (int j = i + 1; j < 3; j++) {
                if (tileState.tileAt(j, i) > 0 &&
                        tileState.tileAt(j, i) > tileState.tileAt(i, j)) {
                    count++;
                }
            }
        }

        return count;
    }

    /**
     * Recursive permutation helper.
     *
     * Swaps every index of the initial array then swaps 1 index at a time down until idx = 0,
     * then back up untill idx = 8;
     *
     * @param set
     * @param curState
     * @param idx
     */
    public static void permutationHelper(ArrayList<TileState> set, int[] curState, int idx) {
        count++;
        if (idx == 8) {
            set.add(new TileState(curState));
        } else {
            for (int i = idx; i < 9; i++) {
                swap(curState, i, idx);
                permutationHelper(set, curState, idx + 1);
                swap(curState, i, idx);
            }
        }
    }

    /**
     * @return a list of all possible tile states. 
     */
    public static ArrayList<TileState> allStates() {
        int[] startSet = {0, 1, 2, 3, 4, 5, 6, 7, 8};
        ArrayList<TileState> permutations = new ArrayList<>();
        permutationHelper(permutations, startSet, 0);
        return permutations;
    }

    /** 
     * Returns the tile at row i and column j in the tile game.
     * @param i The row index. 
     * @param j The column index.
     * @return The tile number between 1 and 8, or 0 for blank at row i column j. 
     */
    public int tileAt(int i, int j) {
        return state[3*i + j];
    }

    // helper for neighborByMove, returns the index of the current empty slot
    public int findZero() {
        int idx = 0;
        int[] result = state;
        for (int i = 0; i < state.length; i++) {
            if (state[i] == 0) {
                idx = i;
            }
        }
        return idx;
    }

    /**
     * Find the new TileState from this TileState (if any) given a move.
     * @param c The move to make, either 'A', 'B', 'L', or 'R'. 
     * @return The TileState for the move that slides the appropriate tile into the blank,
     * or null if the move is not valid.
     */
    public TileState neighborByMove(char c) {
        int idx = findZero();
        int[] result = state;
        switch(c) {
            case 'A':
                if (idx < 3) {
                    return null;
                } else {
                    swap(result, idx, idx - 3);
                    return new TileState(result);
                }
            case 'B':
                if (idx > 5) {
                    return null;
                } else {
                    swap(result, idx, idx + 3);
                    return new TileState(result);
                }
            case 'L':
                if (idx % 3 == 0) {
                    return null;
                } else {
                    swap(result, idx, idx - 1);
                    return new TileState(result);
                }
            case 'R':
                if (idx % 3 == 2) {
                    return null;
                } else {
                    swap(result, idx, idx + 1);
                    return new TileState(result);
                }
        }
        return new TileState(result);
    }

    /**
     * GetValidMoves helper for move graph.
     * @return movelist, an arraylist of the characters representing valid moves
     */
    public ArrayList<String> getValidMoves() {
        ArrayList<String> moveList = new ArrayList<>();
        int idx = findZero();

        if (idx >= 3) {
            moveList.add("A");
        }
        if (idx <= 5) {
            moveList.add("B");
        }
        if (idx % 3 != 0) {
            moveList.add("L");
        }
        if (idx % 3 != 2) {
            moveList.add("R");
        }
        return moveList;
    }

    /**
     * Generates all states reachable by a single move. 
     * @return a List object containing all the states reachable by one move from this one.
     */
    public List<TileState> neighboringStates() {
        ArrayList<TileState> stateSet = new ArrayList<>();
        if (neighborByMove('A') != null) {
            stateSet.add(neighborByMove('A'));
        }
        if (neighborByMove('B') != null) {
            stateSet.add(neighborByMove('B'));
        }
        if (neighborByMove('L') != null) {
            stateSet.add(neighborByMove('L'));
        }
        if (neighborByMove('R') != null) {
            stateSet.add(neighborByMove('R'));
        }
        return stateSet;
    }

    /**
     * Returns a hashCode so if two state objects represent the same state, 
     * they will hash to the same value. 
     * @return A hash cde for this state. 
     */
    public int hashCode() {
        return Arrays.toString(state).hashCode();
    }

    /**
     * Determines whether two state objects represent the same state. 
     * @param o The other object to test. 
     * @return true if the other object represents the same state. 
     */
    public boolean equals(Object o) {
        if (o == null) { 
            return false;
        } else if (!(o instanceof TileState)) {
            return false;
        } else {
            TileState other = (TileState) o;
            return other.hashCode() == this.hashCode();
        }
    }

    /**
     * @return the state of the game as a String that prints each board position on a line.
     */
    public String toString() {
        String acc = "";
        for (int i = 0; i < 9; i++) {
            acc += state[i];
            acc += i % 3 == 2 ? "\n" : " ";
        }
        return acc;
    }

}
