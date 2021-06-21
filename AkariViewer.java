/**
 * AkariViewer represents an interface for a player of Akari.
 * NOTE: Lightbulb unicode characters only work on Windows OS
 *
 * @author Nathan O'Neill
 * @version 2021
 */
import java.awt.*;
import java.awt.event.*; 

public class AkariViewer implements MouseListener
{    
    private Akari puzzle;    // the internal representation of the puzzle
    private SimpleCanvas sc; // the display window
    private int size;
    

    private final int cellsize; // size of each square
    private final int windowsize;
    private final int offset;

    private final Color white = Color.white;
    private final Color yellow = Color.yellow;
    private final Color black = Color.black;
    private final Color blue = Color.blue;
    private final Color red = Color.red;
    /**
     * Constructor for objects of class AkariViewer.
     * Sets all fields and displays the initial puzzle.
     */
    public AkariViewer(Akari puzzle)
    {
        this.puzzle = puzzle;
        size = puzzle.getSize();
        windowsize = 700;
        cellsize = windowsize / (puzzle.getSize() + 2 * 2);
        offset = (windowsize - puzzle.getSize() * cellsize) / 2;
        sc = new SimpleCanvas("AKARI", windowsize, windowsize, white);
        sc.setFont(new Font("Arial Unicode MS", 1, cellsize*2/3));
        sc.addMouseListener(this);
        displayPuzzle();

        /** 
        if (size > 7) {
            cellsize = 35;
            sc = new SimpleCanvas("AKARI", 1000, 1000, white);
            sc.addMouseListener(this);
            sc.setFont(new Font("Arial Unicode MS", 9, cellsize/10*3));
        } else {
            sc = new SimpleCanvas("AKARI", 75 * 7 + 2, 75 * 7 + 100, white);
            sc.addMouseListener(this);
            sc.setFont(new Font("Arial Unicode MS", 20, cellsize/10*3));
        }*/
        
    }

    private void drawGrid()
    {
        for (int i = 0; i <= size; i++)
            sc.drawLine(i * cellsize, 0, i * cellsize, size * cellsize, black);
        for (int j = 0; j <= size; j++)
            sc.drawLine(0, j*cellsize, size*cellsize, j*cellsize, black);
    }
    
    private void drawCells()
    {
        /**size = puzzle.getSize();
        if (size > 7) {
            cellsize = 20; // = size - 100
            sc.setFont(new Font("Arial Unicode MS", 5, cellsize/10*3));
        }*/
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
            {
                if (puzzle.getBoard(j,i) == Space.EMPTY) {
                    sc.drawRectangle(i * cellsize, j * cellsize, (i+1) * cellsize,(j+1) * cellsize, white);
                }
                else if (puzzle.getBoard(j,i) == Space.BLACK) {
                    sc.drawRectangle(i * cellsize, j * cellsize, (i+1) * cellsize,(j+1) * cellsize, black);
                }
                else if (puzzle.getBoard(j,i) == Space.BULB) {
                    String lightbulb = "\u2609"; //"\u2609"; //"\uD83D\uDCA1"
                    sc.drawRectangle(i * cellsize, j * cellsize, (i+1) * cellsize,(j+1) * cellsize, yellow);
                    sc.drawString(lightbulb, cellsize * i + cellsize * 2 / 5, cellsize * j + cellsize * 3 / 5, blue);
                }
                else if (puzzle.getBoard(j,i) == Space.ZERO) {
                    sc.drawRectangle(i * cellsize, j * cellsize, (i+1) * cellsize,(j+1) * cellsize, black);
                    sc.drawString(0, cellsize * i + cellsize * 2 / 5, cellsize * j + cellsize * 3 / 5, white);
                }
                else if (puzzle.getBoard(j,i) == Space.ONE) {
                    sc.drawRectangle(i * cellsize, j * cellsize, (i+1) * cellsize,(j+1) * cellsize, black);
                    sc.drawString(1, cellsize * i + cellsize * 2 / 5, cellsize * j + cellsize * 3 / 5, white);
                }
                else if (puzzle.getBoard(j,i) == Space.TWO) {
                    sc.drawRectangle(i * cellsize, j * cellsize, (i+1) * cellsize,(j+1) * cellsize, black);
                    sc.drawString(2, cellsize * i + cellsize * 2 / 5, cellsize * j + cellsize * 3 / 5, white);
                }
                else if (puzzle.getBoard(j,i) == Space.THREE) {
                    sc.drawRectangle(i * cellsize, j * cellsize, (i+1) * cellsize,(j+1) * cellsize, black);
                    sc.drawString(3, cellsize * i + cellsize * 2 / 5, cellsize * j + cellsize * 3 / 5, white);
                }
                else if (puzzle.getBoard(j,i) == Space.FOUR) {
                    sc.drawRectangle(i * cellsize, j * cellsize, (i+1) * cellsize,(j+1) * cellsize, black);
                    sc.drawString(4, cellsize * i + cellsize * 2 / 5, cellsize * j + cellsize * 3 / 5, white);
                }
        }
        for (int r = 0; r < size; r++)
            for (int c = 0; c < size; c++) {
                bulbLight(r,c);
                if (puzzle.getBoard(r,c) == Space.BULB && puzzle.canSeeBulb(r,c)) {
                    String lightbulb = "\u2609"; //"\uD83D\uDCA1"; // Only shows lightbulb on Windows OS lightbulb unicode = "\uD83D\uDCA1"
                    sc.drawRectangle(c * cellsize, r * cellsize, (c+1) * cellsize,(r+1) * cellsize, yellow);
                    sc.drawString(lightbulb, cellsize * c + cellsize * 2 / 5, cellsize * r + cellsize * 3 / 5, red);
                }
            }
    }
    
    public void drawButtons()
    {
        sc.drawString("SOLVED?", 10, cellsize * 7 + 50, Color.black);
        sc.drawString("CLEAR", cellsize * 6, cellsize * 7 + 50, Color.black);
    }
    
    /**
     * Selects from among the provided files in folder Puzzles. 
     * The number xyz selects pxy-ez.txt. 
     */
    public AkariViewer(int n)
    {
        this(new Akari("Puzzles/p" + n / 10 + "-e" + n % 10 + ".txt"));
    }
    
    /**
     * Uses the provided example file on the LMS page.
     */
    public AkariViewer()
    {
        this(77);
    }
    
    /**
     * Returns the internal state of the puzzle.
     */
    public Akari getPuzzle()
    {
        return puzzle;
    }
    
    /**
     * Returns the canvas.
     */
    public SimpleCanvas getCanvas()
    {
        return sc;
    }
    
    /**
     * Displays the initial puzzle. 
     */
    private void displayPuzzle()
    {
        drawCells();
        drawGrid();
        drawButtons();
    }
    
    /**
     * Performs a left click on the square at r,c if the indices are legal, o/w does nothing. 
     * Updates both puzzle and the display. 
     */
    public void leftClick(int r, int c)
    {
        if (puzzle.isLegal(r,c)){
            puzzle.leftClick(r,c);
            displayPuzzle();
        }
    }
    
    public void mousePressed (MouseEvent e) {
        puzzle.leftClick(e.getY()/cellsize, e.getX()/cellsize);
        if ( e.getY() > 525 && e.getY() < 625 && e.getX() > -1 && e.getX() < 110) {
            sc.drawRectangle(10, cellsize * 7 + 100, 450,cellsize * 7 + 25, white);
            sc.drawString(puzzle.isSolution(), 10, cellsize * 7 + 75, Color.black);
        }
        if ( e.getY() > 525 && e.getY() < 625 && e.getX() > 435 && e.getX() < 526) {
            puzzle.clear();
        }
        displayPuzzle();
    }
    public void mouseClicked (MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered (MouseEvent e) {}
    public void mouseExited  (MouseEvent e) {}
    
    private void bulbLight(int r, int c) {
        if (puzzle.isLegal(r,c) && puzzle.getBoard(r,c) == Space.BULB){
            for (int i = r+1; i < size && puzzle.getBoard(i,c) != Space.BLACK && puzzle.getBoard(i,c) != Space.ZERO && puzzle.getBoard(i,c) != Space.ONE && puzzle.getBoard(i,c) != Space.TWO && puzzle.getBoard(i,c) != Space.THREE && puzzle.getBoard(i,c) != Space.FOUR; i++){
                if (puzzle.isLegal(i,c)){if (puzzle.getBoard(i,c) == Space.EMPTY)
                    sc.drawRectangle(c * cellsize, i * cellsize, (c+1) * cellsize,(i+1) * cellsize, yellow);
                }
            }
            for (int i = r-1; i > -1 && puzzle.getBoard(i,c) != Space.BLACK && puzzle.getBoard(i,c) != Space.ZERO && puzzle.getBoard(i,c) != Space.ONE && puzzle.getBoard(i,c) != Space.TWO && puzzle.getBoard(i,c) != Space.THREE && puzzle.getBoard(i,c) != Space.FOUR; i--){
                if (puzzle.isLegal(i,c)){if (puzzle.getBoard(i,c) == Space.EMPTY)
                    sc.drawRectangle(c * cellsize, i * cellsize, (c+1) * cellsize,(i+1) * cellsize, yellow);
                }
            }
            for (int i = c+1; i < size && puzzle.getBoard(r,i) != Space.BLACK && puzzle.getBoard(r,i) != Space.ZERO && puzzle.getBoard(r,i) != Space.ONE && puzzle.getBoard(r,i) != Space.TWO && puzzle.getBoard(r,i) != Space.THREE && puzzle.getBoard(r,i) != Space.FOUR; i++){
                if (puzzle.isLegal(r,i)){if (puzzle.getBoard(r,i) == Space.EMPTY)
                    sc.drawRectangle(i * cellsize, r * cellsize, (i+1) * cellsize,(r+1) * cellsize, yellow);
                }
            }
            for (int i = c-1; i > -1 && puzzle.getBoard(r,i) != Space.BLACK && puzzle.getBoard(r,i) != Space.ZERO && puzzle.getBoard(r,i) != Space.ONE && puzzle.getBoard(r,i) != Space.TWO && puzzle.getBoard(r,i) != Space.THREE && puzzle.getBoard(r,i) != Space.FOUR; i--){
                if (puzzle.isLegal(r,i)){if (puzzle.getBoard(r,i) == Space.EMPTY)
                    sc.drawRectangle(i * cellsize, r * cellsize, (i+1) * cellsize,(r+1) * cellsize, yellow);
                }
            }
        }
    }
}