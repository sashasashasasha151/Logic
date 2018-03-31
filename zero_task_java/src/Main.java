import include.Parser;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String str = in.nextLine();
        Parser parser = new Parser(str);
        parser.parse();
    }
}
