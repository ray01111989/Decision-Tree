# Decision-Tree

A "20 Questions" game in Java. You think of an object and the program asks yes/no questions until it guesses. When it guesses wrong it asks you for a new question that tells your object apart from its guess, adds it to the tree, and saves the tree to a file so it gets smarter each time you play. It is not limited to 20 questions.

## Run it

You need a JDK (any recent version).

```bash
mkdir -p out
javac -d out src/*.java
java -cp out QueryTreeTest            # uses 20ss.txt in the current folder
java -cp out QueryTreeTest b.txt      # or pick another tree file
```

At the start it asks whether to read the previous tree. Answer `n` to start from scratch (the tree then holds a single guess, "computer"). The tree is saved to the same file after every round; if the file does not exist yet it is created.

## Run the tests

```bash
java -cp out QueryTreeSelfTest
```

`QueryTreeSelfTest` needs no test library. It checks the tree logic, saving and loading, and that bad files (empty, wrong tag, a question with only one branch) are rejected without changing the current tree.

## Tree file format

Each node is two lines: a tag (`Q:` for a question or `A:` for an answer) and its text. A question is followed by its YES subtree, then its NO subtree (in that order). Example (`b.txt`):

```
Q:
Is it made of wood?
A:
door
Q:
Can you drive it?
A:
car
Q:
Is it an animal?
A:
cat
A:
computer
```

Reading this: "made of wood?" YES gives `door`; NO gives "Can you drive it?", whose YES gives `car` and whose NO gives "Is it an animal?" with `cat` and `computer`.

## Files

| File | What it does |
| --- | --- |
| `src/QueryTree.java` | The tree: asking questions, learning new objects, reading and writing the file |
| `src/QueryTreeTest.java` | The interactive game (`main`) |
| `src/QueryTreeSelfTest.java` | Automatic checks for `QueryTree` |
| `20ss.txt`, `b.txt` | Sample trees |

Author: Rawad Bader (CSE 223, Programming Assignment 4).
