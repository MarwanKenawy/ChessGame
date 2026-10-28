# ChessGame
A Java-based chess game with a graphical interface built using **Java**. The project separates the chess rules and game state from the
GUI and includes move history so the last move can be undone.

## Features

-   Standard chessboard and initial piece setup.
-   Legal move validation and turn management.
-   Check, checkmate, and stalemate detection.
-   Castling and en passant.
-   Pawn promotion with a choice of **Queen, Rook, Bishop, or Knight**.
-   Capture and move-event tracking.
-   Detection of insufficient material.
-   Undo the last move, including restoration of special-move state.
-   GUI highlights for the selected piece, legal destinations, and a
    king in check.
-   Board orientation flips after a successful move.
-   Design patterns:
    -   **Factory** --- creates the piece selected for pawn promotion.
    -   **Observer** --- notifies the GUI when the game state changes.
    -   **Memento** --- stores game-state snapshots to support undo.

## Technologies

-   Java
-   Java Swing
-   Object-oriented programming
-   Design patterns: Factory, Observer, and Memento

## Requirements

-   A Java Development Kit (JDK) installed.
-   A terminal or IDE that can compile and run Java source files.

The commands below use PowerShell and assume the project root contains
the `src` directory.

## Compile

Compile the project:

``` powershell
javac -encoding UTF-8 -d out src/ChessCore/enums/*.java src/ChessCore/board/*.java src/ChessCore/pieces/*.java src/ChessCore/ChessGameObserver.java src/ChessCore/ChessGame.java src/ChessGUI/ChessFrame.java src/Main.java src/ChessGUIMain.java
```

The `-encoding UTF-8` option supports the Unicode chess-piece symbols
used by the GUI.

## Run the GUI

From the project root, run:

``` powershell
java -cp out ChessGUIMain
```

## Run Moves from a `.txt` File

The project also supports running a sequence of chess moves from a text file through the console entry point, `Main.java`. The default input file is `ChessGame.txt`.

1. Open `ChessGame.txt` in a text editor.
2. Enter the moves using the format expected by the program. Use the existing file as a reference for the accepted format, including how promotion choices are written.
3. Save the file in the project root, next to the `src` directory.
4. Compile the project using the command in the [Compile](#compile) section.
5. Run the console version from the project root:

   ```powershell
   java -cp out Main
   ```

The console program reads the move sequence from `ChessGame.txt`, processes the moves, and prints the resulting move events and game status. This is separate from the graphical version, which you can launch with `java -cp out ChessGUIMain`.


## How to Play

1.  Launch the GUI.
2.  Select one of your pieces. Legal destination squares are
    highlighted.
3.  Select a highlighted destination to make the move.
4.  The board flips orientation after a successful move.
5.  When a pawn reaches the opposite end of the board, choose the piece
    for promotion.
6.  Use **Undo** to restore the position before the most recent move.

The game prevents illegal moves and reports terminal game states such as
checkmate, stalemate, and insufficient material.

## Design Notes

### Factory Pattern

`PieceFactory` centralizes the creation of promotion pieces. The game
requests a piece using the promotion choice rather than constructing
each promotion type directly at the call site.

### Observer Pattern

`ChessGameObserver` defines the game-state update callback. `ChessFrame`
registers as an observer so the GUI can refresh when the game state
changes.

### Memento Pattern

`ChessGame` stores snapshots of the game state before moves. Undo
restores the previous board, turn, status, special-move state, and
related move information.

## Testing

The project has been manually tested for normal moves, legal-move
highlighting, board flipping, pawn promotion, undo, and special-move
undo cases, including castling and en passant.
