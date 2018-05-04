import java.util.Random;

/**
 * Represents a board configuration in the game of FlippingBits.
 * 
 * @author E. Stark
 * @version 20160411
 */
public class FlippingBitsBoard
{
    /** 2D array that represents the current configuration. */
    private int[][] board;

    /** Dimension of the board. */
    private final int size;

    /** Source of random numbers. */
    private static final Random random = new Random();
    
    /** Row labels. */
    private static final String rowLabels =
      "abcdefghijklmnopqrstuvwxyz";
      
    /** Column labels. */
    private static final String colLabels =
      "123456789ABCDEFGHIJKLMNOPQ";
      
    /** Maximum board size. */
    public static final int MAX_SIZE = rowLabels.length();

    /**
     * Initialize a board with a specified size and possibly
     * a random configuration.
     * 
     * @param size  The dimension of the board.
     * @param rand  If true, then initialize the board to a random
     * configuration, otherwise the board will be all zeros.
     */
    public FlippingBitsBoard(int size, boolean rand) {
        this.size = size;
        this.board = new int[size][size];
        if(rand) {
            for(int i = 0; i < size; i++) {
                for(int j = 0; j < size; j++) {
                    board[i][j] = random.nextInt(2);
                }
            }
        }
    }

    /**
     * Copy constructor.
     * Initializes a FlippingBitsBoard with the same size and
     * configuration as a specified board.
     * 
     * @param toCopy  The board to copy.
     */
    public FlippingBitsBoard (FlippingBitsBoard toCopy) {
        this(toCopy.size, false);
        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                board[i][j] = toCopy.board[i][j];
            }
        }
    }
    
    /**
     * Get the size of this board.
     * 
     * @return the size of this board.
     */
    public int getSize() {
        return size;
    }
    
    /**
     * Get the 0/1 value at a specified position in this board.
     * 
     * @param row  The row coordinate.
     * @param col  The column coordinate.
     * @return the value at the specified row and column.
     */
    public int getValue(int row, int col) {
        return board[row][col];
    }
    
    /**
     * Shuffle this board by performing a random sequence of row and column flips.
     * 
     * @param n  The number of flips to perform.
     */
    public void shuffle(int n) {
        for(int k = 0; k < n; k++) {
            int i = random.nextInt(size);
            if(random.nextBoolean())
                flipRow(i);
            else
                flipCol(i);
        }
    }

    /**
     * Flip a row of this board.
     * 
     * @param i  The row to flip.
     */
    public void flipRow(int i) {
        for(int j = 0; j < size; j++)
            board[i][j] = 1 - board[i][j];
    }
    
    /**
     * Flip a column of this board.
     * 
     * @param j  The column to flip.
     */
    public void flipCol(int j) {
        for(int i = 0; i < size; i++)
            board[i][j] = 1 - board[i][j];
    }
    
    /**
     * Determine if the configuration of this board matches
     * the configuration of a specified other board.
     * 
     * @param other  The other board.
     * @return true if this board and the other board have
     * the same size and configuration.
     */
    public boolean matches(FlippingBitsBoard other) {
        if(size != other.size)
          return false;
        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                if(board[i][j] != other.board[i][j])                    return false;
            }
        }
        return true;
    }
    
    /**
     * Determine if this board equals a specified other object.
     * 
     * @param other  The other object.
     * @return true if this board is equal to the other object,
     * otherwise false.
     */
    @Override
    public boolean equals(Object other) {
        if(this == other)
          return true;
        else if(other == null)
          return false;
        else if(getClass() != other.getClass())
          return false;
        else
          return matches((FlippingBitsBoard)other);
    }
    
    /**
     * Compute a hashcode for this board.
     * 
     * @return a hashcode for this board.
     */
    @Override
    public int hashCode() {
        int h = size;
        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                h = h*37 + board[i][j];
            }
        }
        return h;
    }
    
    /**
     * Create a string representation of this board,
     * suitable for printing.
     * 
     * @return a string representation of this board.
     */
    @Override
    public String toString() {
        String s = " " + colLabels.substring(0, size) + "\n";
        for(int i = 0; i < size; i++) {
            s += rowLabels.charAt(i);
            for(int j = 0; j < size; j++) {
                s += board[i][j];
            }
            s += "\n";
        }
        return s;
    }
    
    /**
     * Given a row label, determine the corresponding row number.
     * 
     * @param label  The row label.
     * @return the corresponding row number, or -1 if the given
     * label is not a valid row.
     */
    public int labelToRow(char label) {
        int i = rowLabels.indexOf(label);
        if(i < 0 || i >= size)
          return -1;
        else
          return i;
    }
    
    /**
     * Given a row number, determine the corresponding row label.
     * 
     * @param row  The row number.
     * @return  the corresponding row label, or 0 if the row
     * number is invalid.
     */
    public char rowToLabel(int row) {
        if(row >= 0 && row < size)
          return rowLabels.charAt(row);
        else
          return 0;
    }

    /**
     * Given a column label, determine the corresponding column number.
     * 
     * @param label  The column label.
     * @return the corresponding column number, or -1 if the given
     * label is not a valid column.
     */
    public int labelToCol(char label) {
        int j = colLabels.indexOf(label);
        if(j < 0 || j >= size)
          return -1;
        else
          return j;
    }
    
    /**
     * Given a column number, determine the corresponding column label.
     * 
     * @param col The column number.
     * @return  the corresponding column label, or 0 if the column
     * number is invalid.
     */
    public char colToLabel(int col) {
        if(col >= 0 && col < size)
          return colLabels.charAt(col);
        else
          return 0;
    }

}
