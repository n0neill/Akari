/**
 * Space enumerates the possible states of a square on an Akari board. 
 *
 * @author Nathan O'Neill
 * @version 2021
 */
public enum Space
{
    BLACK,                       // black no number 
    ZERO, ONE, TWO, THREE, FOUR, // black with number (will always be in this order) 
    EMPTY,                       // empty 
    BULB;                        // bulb 
    
    /**
     * Returns true iff x represents a space on the board 
     * that can be changed during play. 
     */
    public static boolean isMutable(Space x)
    {
        return x == EMPTY || x == BULB;
    }
}
