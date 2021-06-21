/**
 * Akari represents a single puzzle of the game Akari.
 *
 * @author Nathan O'Neill
 * @version 2021
 */
import java.util.ArrayList;

public class Akari
{
    private String filename; // the name of the puzzle file
    private int size;        // the board is size x size
    private Space[][] board; // the board is a square grid of spaces of various types

    /**
     * Constructor for objects of class Akari. 
     * Creates and initialises the fields with the contents of filename. 
     * The layout of a puzzle file is described on the LMS page; 
     * you may assume the layout is always correct. 
     */
    public Akari(String filename)
    {
        FileIO file = new FileIO(filename);
        this.filename = file.getName();
        ArrayList<String> lines = file.getLines();
        size = Integer.valueOf(lines.get(0));
        board = new Space[size][size];
        
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                board[i][j] = Space.EMPTY;
        for (int i = 1; i < lines.size(); i++){
            String[] split = lines.get(i).split("\\s+");
            int slength = split[0].length();
            if (slength > 0) {
                for (int j = 0; j < split.length; j++){
                    int a = parseIndex(split[j].charAt(0));
                    String sub = split[j].substring(1);
                    if (i == 1){ 
                        for (int k = 0; k < sub.length(); k++){
                            board[a][parseIndex(sub.charAt(k))] = Space.BLACK;
                        }
                    }
                    if (i == 2){ 
                        for (int k = 0; k < sub.length(); k++){
                            board[a][parseIndex(sub.charAt(k))] = Space.ZERO;
                        }
                    }
                    if (i == 3){ 
                        for (int k = 0; k < sub.length(); k++){
                            board[a][parseIndex(sub.charAt(k))] = Space.ONE;
                        }
                    }
                    if (i == 4){ 
                        for (int k = 0; k < sub.length(); k++){
                            board[a][parseIndex(sub.charAt(k))] = Space.TWO;
                        }
                    }
                    if (i == 5){ 
                        for (int k = 0; k < sub.length(); k++){
                            board[a][parseIndex(sub.charAt(k))] = Space.THREE;
                        }
                    }
                    if (i == 6){ 
                        for (int k = 0; k < sub.length(); k++){
                            board[a][parseIndex(sub.charAt(k))] = Space.FOUR;
                        }
                    }
                }
            }
        }
    }
    
    /**
     * Uses the example file from the LMS page.
     */
    public Akari()
    {
        this("Puzzles/p7-e7.txt");
    }
    
    /**
     * Returns the name of the puzzle file.
     */
    public String getFilename()
    {
        return filename;
    }
    
    /**
     * Returns the size of the puzzle.
     */
    public int getSize()
    {
        return size;
    }
    
    /**
     * Returns true iff k is a legal index into the board. 
     */
    public boolean isLegal(int k)
    {
        if ( k < size && k > -1 )
            return true;
        else 
            return false; 
    }
    
    /**
     * Returns true iff r and c are both legal indices into the board. 
     */
    public boolean isLegal(int r, int c)
    {
        if (isLegal(r) && isLegal(c))
            return true;
        else 
            return false; 
    }
    
    /**
     * Returns the contents of the square at r,c if the indices are legal, 
     * o/w throws an illegal argument exception. 
     */
    public Space getBoard(int r, int c)
    {
        if (isLegal(r,c)) 
            return board[r][c];
        else 
            throw new IllegalArgumentException("Position is invalid");
    }
    
    /**
     * Returns the int equivalent of x. 
     * If x is a digit, returns 0 -> 0, 1 -> 1, etc; 
     * if x is an upper-case letter, returns A -> 10, B -> 11, etc; 
     * o/w throws an illegal argument exception. 
     */
    public static int parseIndex(char x)
    {
        boolean b = Character.isDigit(x);
        if (b) {
            int a = x - '0';
            return a;
        }
        else if (x > 'A'-1 && x < 'Z' +1){
            int a = x - 'A' + 10;
            return a;
        }
        else 
            throw new IllegalArgumentException("Position is illegal");
    }
    
    /**
     * Performs a left click on the square at r,c if the indices are legal, o/w does nothing. 
     * If r,c is empty, a bulb is placed; if it has a bulb, that bulb is removed.
     */
    public void leftClick(int r, int c)
    {
        if (isLegal(r,c)){      
            if (board[r][c] == Space.EMPTY) {
                board[r][c] = Space.BULB;
            } 
            else if (board[r][c] == Space.BULB) {
                board[r][c] = Space.EMPTY;
            }
        }
    }
    
    /**
     * Sets all mutable squares on the board to empty.
     */
    public void clear()
    {
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                if (board[i][j] == Space.BULB) {
                   board[i][j] = Space.EMPTY;
                }
    }
    
    /**
     * Returns the number of bulbs adjacent to the square at r,c. 
     * Throws an illegal argument exception if r,c are illegal coordinates.
     */
    public int numberOfBulbs(int r, int c)
    {
        int nbulbs = 0;
        if (isLegal(r, c)){
            if (isLegal(r+1,c)){
                if (board[r + 1][c] == Space.BULB){
                    nbulbs += 1;
                }
            }
            if (isLegal(r-1,c)){
                if (board[r - 1][c] == Space.BULB){
                    nbulbs += 1;
                }
            }
            if (isLegal(r,c+1)){
                if (board[r][c + 1] == Space.BULB){
                    nbulbs += 1;
                }
            }
            if (isLegal(r,c-1)){
                if (board[r][c - 1] == Space.BULB){
                    nbulbs += 1;
                }
            }
        }
        else 
            throw new IllegalArgumentException("Illegal Position");
        return nbulbs;
    }

    /**
     * Returns true iff the square at r,c is lit by a bulb elsewhere. 
     * Throws an illegal argument exception if r,c are illegal coordinates.
     */
    public boolean canSeeBulb(int r, int c)
    {
        boolean canseebulb = false;
        if (isLegal(r, c)) {
            for (int i = r+1; i < size && board[i][c] != Space.BLACK && board[i][c] != Space.ZERO && board[i][c] != Space.ONE && board[i][c] != Space.TWO && board[i][c] != Space.THREE && board[i][c] != Space.FOUR; i++){
                if (isLegal(i,c)){if (board[i][c] == Space.BULB)
                    canseebulb = true;
                }
            }
            for (int i = r-1; i > -1 && board[i][c] != Space.BLACK && board[i][c] != Space.ZERO && board[i][c] != Space.ONE && board[i][c] != Space.TWO && board[i][c] != Space.THREE && board[i][c] != Space.FOUR; i--){
                if (isLegal(i,c)){if (board[i][c] == Space.BULB)
                    canseebulb = true;
                }
            }
            for (int i = c+1; i < size && board[r][i] != Space.BLACK && board[r][i] != Space.ZERO && board[r][i] != Space.ONE && board[r][i] != Space.TWO && board[r][i] != Space.THREE && board[r][i] != Space.FOUR; i++){
                if (isLegal(r,i)){if (board[r][i] == Space.BULB)
                    canseebulb = true;
                }
            }
            for (int i = c-1; i > -1 && board[r][i] != Space.BLACK && board[r][i] != Space.ZERO && board[r][i] != Space.ONE && board[r][i] != Space.TWO && board[r][i] != Space.THREE && board[r][i] != Space.FOUR; i--){
                if (isLegal(r,i)){if (board[r][i] == Space.BULB)
                    canseebulb = true;
                }
            }
        }
        else 
            throw new IllegalArgumentException("Position is illegal");
        return canseebulb;
    }
    
    /**
     * Returns an assessment of the state of the puzzle, either 
     * "Clashing bulb at r,c", 
     * "Unlit square at r,c", 
     * "Broken number at r,c", or
     * three ticks, as on the LMS page. 
     * r,c must be the coordinates of a square that has that kind of error. 
     * If there are multiple errors on the board, you may return any one of them. 
     */
    public String isSolution()
    {
        String summary = "\u2713\u2713\u2713";
        for(int r = 0; r < size; r++){  
            for(int c = 0; c < size; c++){
                if( board[r][c] == Space.BULB){
                    if(canSeeBulb(r, c))
                        summary = "Clashing bulb at " + r + "," + c;
                }
                if (board[r][c] == Space.EMPTY){
                    if( canSeeBulb(r, c) == false)
                        summary = "Unlit square at " + r + "," + c;
                }
                if (board[r][c] == Space.ZERO){
                    if( numberOfBulbs(r, c) != 0)
                        summary = "Broken number at " + r + "," + c;
                }
                if( board[r][c] == Space.ONE){
                    if( numberOfBulbs(r, c) != 1)
                        summary = "Broken number at " + r + "," + c;
                }
                if( board[r][c] == Space.TWO){
                    if( numberOfBulbs(r, c) != 2)
                        summary = "Broken number at " + r + "," + c;
                }
                if( board[r][c] == Space.THREE){
                    if( numberOfBulbs(r, c) != 3)
                        summary = "Broken number at " + r + "," + c;
                }
                if( board[r][c] == Space.FOUR){
                    if( numberOfBulbs(r, c) != 4)
                        summary = "Broken number at " + r + "," + c;
                }
            }
        }
        return summary;
    }
}
