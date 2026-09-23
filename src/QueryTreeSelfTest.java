import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

// Self-checking tests for QueryTree. No test library is needed:
//   javac src/*.java -d out && java -cp out QueryTreeSelfTest
// It prints one line per check and exits with a non-zero status if any check fails.
public class QueryTreeSelfTest {
    private static int failures = 0;

    // Records one check: prints PASS or FAIL
    private static void check(String name, boolean ok) {
        System.out.println((ok ? "PASS  " : "FAIL  ") + name);
        if (!ok) failures++;
    }

    // Writes lines to a new temporary file (one per line) and returns the file
    private static File fileWith(String... lines) throws IOException {
        File f = File.createTempFile("querytree", ".txt");
        f.deleteOnExit();
        try (PrintWriter w = new PrintWriter(f)) {
            for (String line : lines) w.println(line);
        }
        return f;
    }

    // Returns true if running the task throws an exception of the given type
    private static boolean throwsA(Class<? extends Exception> type, Runnable task) {
        try { task.run(); return false; } catch (Exception e) { return type.isInstance(e); }
    }

    public static void main(String[] args) throws IOException {
        // A new tree has one answer ("computer") and no questions
        QueryTree t = new QueryTree();
        check("new tree has no question", !t.hasNextQuestion());
        check("new tree guesses computer", t.finalGuess().equals("computer"));
        check("nextQuestion on a guess throws", throwsA(IllegalStateException.class, t::nextQuestion));

        // Learning a new object adds a question, and the tree asks it next time
        t.updateTree("Is it made of wood?", "door", 'y');
        check("after update the tree asks a question", t.hasNextQuestion() && t.nextQuestion().equals("Is it made of wood?"));
        t.userResponse('y');
        check("yes leads to the new object", t.finalGuess().equals("door"));
        t.reset();
        t.userResponse('N');
        check("no (capital N) leads to the old guess", t.finalGuess().equals("computer"));
        check("bad answer letter throws", throwsA(IllegalArgumentException.class, () -> { t.reset(); t.userResponse('x'); }));
        check("empty question is rejected", throwsA(IllegalArgumentException.class, () -> { t.reset(); t.userResponse('n'); t.updateTree("  ", "cat", 'y'); }));
        check("empty item is rejected", throwsA(IllegalArgumentException.class, () -> t.updateTree("Is it an animal?", "", 'y')));

        // Save and load again: the tree must behave the same
        File f = File.createTempFile("querytree", ".txt");
        f.deleteOnExit();
        t.reset();
        t.writeOut(f);
        QueryTree loaded = new QueryTree();
        loaded.readIn(f);
        check("loaded tree asks the same question", loaded.nextQuestion().equals("Is it made of wood?"));
        loaded.userResponse('y');
        check("loaded tree keeps the yes answer", loaded.finalGuess().equals("door"));

        // Bad files are rejected, and a rejected file leaves the current tree untouched
        QueryTree keep = new QueryTree();
        check("truncated file (question with one branch) is rejected",
              throwsA(IllegalArgumentException.class, () -> { try { keep.readIn(fileWith("Q:", "Is it big?", "A:", "elephant")); } catch (IOException e) { throw new RuntimeException(e); } }));
        check("tree unchanged after a rejected file", !keep.hasNextQuestion() && keep.finalGuess().equals("computer"));
        check("file with a wrong tag is rejected",
              throwsA(IllegalArgumentException.class, () -> { try { keep.readIn(fileWith("X:", "hello")); } catch (IOException e) { throw new RuntimeException(e); } }));
        check("empty file is rejected",
              throwsA(IllegalArgumentException.class, () -> { try { keep.readIn(fileWith()); } catch (IOException e) { throw new RuntimeException(e); } }));
        check("missing file throws IOException", throwsA(RuntimeException.class, () -> { try { keep.readIn(new File("/no/such/file.txt")); } catch (IOException e) { throw new RuntimeException(e); } }));

        System.out.println(failures == 0 ? "\nAll checks passed." : "\n" + failures + " check(s) FAILED.");
        System.exit(failures == 0 ? 0 : 1);
    }
}
