public class Main {

    public static void main(String[] args) {
        if (args.length == 1) {
            Akari puzzle2 = new Akari(args[0]);
            new AkariViewer(puzzle2);
        } else {
            Akari puzzle = new Akari();
            new AkariViewer(puzzle); 
        } 
    }
}