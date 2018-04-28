import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import include.*;

public class Main {
    private static List<Result> axioms = new ArrayList<>();

    private static boolean isEquals(Result left, Result right) {
        if (left == null && right == null) {
            return true;
        }
        if (left.expression.equals(right.expression)) {
            return isEquals(left.left, right.left) && isEquals(left.right, right.right);
        }
        return false;
    }

    private static String makeResult(Result currentExpression) {
        StringBuilder sb = new StringBuilder();
        switch (currentExpression.number) {
            case 2:
                sb.append("(")
                        .append(makeResult(currentExpression.left))
                        .append(currentExpression.expression)
                        .append(makeResult(currentExpression.right))
                        .append(")");
                break;
            case 1:
                sb.append("!").append(makeResult(currentExpression.left)).append(")");
                break;
            case 0:
                return currentExpression.expression;
        }
        return sb.toString();
    }

    public static void main(String[] args) throws IOException {
        Scanner input = new Scanner(new File("src/include/axioms.txt"));
        for (int i = 0; i < 10; ++i) {
            axioms.add(new Parser(input.nextLine()).parse());
        }

//        Path path = Paths.get("src/input.txt");
        Path path = Paths.get("input.txt");
        List<String> list = Files
                .lines(path)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());

        String[] inputString = list.get(0).split(Pattern.quote("|-"));
        List<Result> assumptions = new ArrayList<>();
        if (!inputString[0].equals("")) {
            assumptions = Arrays.stream(inputString[0].split(","))
                    .map((p) -> new Parser(p).parse())
                    .collect(Collectors.toList());
        }

        int i = 1;
        List<Result> printed = new ArrayList<>();
        List<String> answers = new ArrayList<>();
        Map<String, Integer> hashed = new HashMap<>();
        Map<String, Integer> hashedH = new HashMap<>();
        for (int h = 1; h < list.size(); ++h) {
            String s = list.get(h);
            Result newAssumption = new Parser(s).parse();
            boolean isAssum = false;

            for (int j = 0; j < assumptions.size(); ++j) {
                if (isEquals(newAssumption, assumptions.get(j))) {
                    answers.add("(" + i++ + ") " + s + " (Предп. " + (j + 1) + ")");
                    isAssum = true;
                    break;
                }
            }

            if (isAssum) {
                printed.add(newAssumption);
                hashed.put(makeResult(newAssumption), i - 1);
                if (newAssumption.expression.equals("->")) {
                    hashedH.put(makeResult(newAssumption.right), i - 1);
                }
                continue;
            }

            for (int j = 0; j < 10; ++j) {
                Checker checker = new Checker();
                if (checker.checker(axioms.get(j), newAssumption)) {
                    answers.add("(" + i++ + ") " + s + " (Сх. акс. " + (j + 1) + ")");
                    isAssum = true;
                    break;
                }
            }

            if (isAssum) {
                printed.add(newAssumption);
                hashed.put(makeResult(newAssumption), i - 1);
                if (newAssumption.expression.equals("->")) {
                    hashedH.put(makeResult(newAssumption.right), i - 1);
                }
                continue;
            }

            String del = makeResult(newAssumption);
            if (hashedH.containsKey(del)) {
                String dell = makeResult(printed.get(hashedH.get(del) - 1).left);
                if (hashed.containsKey(dell)) {
                    answers.add("(" + i++ + ") " + s + " (M.P. " + hashedH.get(del) + ", " + hashed.get(dell) + ")");
                    isAssum = true;
                }
            }

            if (isAssum) {
                printed.add(newAssumption);
                hashed.put(makeResult(newAssumption), i - 1);
                if (newAssumption.expression.equals("->")) {
                    hashedH.put(makeResult(newAssumption.right), i - 1);
                }
                continue;
            }

            answers.add("(" + i++ + ") " + s + " (Не доказано)");
            printed.add(newAssumption);
            hashed.put(makeResult(newAssumption), i - 1);
            if (newAssumption.expression.equals("->")) {
                hashedH.put(makeResult(newAssumption.right), i - 1);
            }
        }
        for (int t = 0; t < answers.size(); ++t) {
            System.out.println(answers.get(t));
        }
    }
}
