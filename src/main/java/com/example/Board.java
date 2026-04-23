package com.example;

//example
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
// import java.util.ArrayList;
// import java.util.LinkedList;
// import java.util.List;
import java.net.URL;
import java.util.ArrayList;
import java.awt.Toolkit;

import javax.swing.*;

//You will be implmenting a part of a function and a whole function in this document. Please follow the directions for the 
//suggested order of completion that should make testing easier.
@SuppressWarnings("serial")
public class Board extends JPanel implements MouseListener, MouseMotionListener {
    // Resource location constants for piece images
    private static final String path = "/src/main/java/com/example/Pictures/";
    private static final String RESOURCES_WBISHOP_PNG = path + "wbishop.png";
    private static final String RESOURCES_BBISHOP_PNG = path + "bbishop.png";
    private static final String RESOURCES_WKNIGHT_PNG = path + "wknight.png";
    private static final String RESOURCES_BKNIGHT_PNG = path + "bknight.png";
    private static final String RESOURCES_WROOK_PNG = path + "wrook.png";
    private static final String RESOURCES_BROOK_PNG = path + "brook.png";
    private static final String RESOURCES_WKING_PNG = path + "wking.png";
    private static final String RESOURCES_BKING_PNG = path + "bking.png";
    private static final String RESOURCES_BQUEEN_PNG = path + "bqueen.png";
    private static final String RESOURCES_WQUEEN_PNG = path + "wqueen.png";
    private static final String RESOURCES_WPAWN_PNG = path + "wpawn.png";
    private static final String RESOURCES_BPAWN_PNG = path + "bpawn.png";
    private static final String RESOURCES_WCAT_PNG = path + "whiteCat.png";
    private static final String RESOURCES_BCAT_PNG = path + "blackCat.png";
    private static final String RESOURCES_BAMA_PNG = path + "blackAmazon.png";
    private static final String RESOURCES_WAMA_PNG = path + "whiteAmazon.png";

    // Logical and graphical representations of board
    private final Square[][] board;
    private final GameWindow g;

    // contains true if it's white's turn.
    private boolean whiteTurn;

    // if the player is currently dragging a piece this variable contains it.
    Piece currPiece;
    private Square fromMoveSquare;

    // used to keep track of the x/y coordinates of the mouse.
    private int currX;
    private int currY;

    public Board(GameWindow g) {
        this.g = g;
        board = new Square[8][8];
        setLayout(new GridLayout(8, 8, 0, 0));

        this.addMouseListener(this);
        this.addMouseMotionListener(this);

        // TO BE IMPLEMENTED FIRST
        boolean cSwitch = true;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                board[i][j] = new Square(this, cSwitch, i, j);
                this.add(board[i][j]);
                cSwitch = !cSwitch;
            }
            cSwitch = !cSwitch;
        }
        // populate the board with squares here. Note that the board is composed of 64
        // squares alternating from
        // white to black.

        initializePieces();

        this.setPreferredSize(new Dimension(400, 400));
        this.setMaximumSize(new Dimension(400, 400));
        this.setMinimumSize(this.getPreferredSize());
        this.setSize(new Dimension(400, 400));

        whiteTurn = true;

    }

    // set up the board such that the black pieces are on one side and the white
    // pieces are on the other.
    // since we only have one kind of piece for now you need only set the same
    // number of pieces on either side.
    // it's up to you how you wish to arrange your pieces.
    void initializePieces() {
        //White back pieces
        board[0][0].put(new Cat(true, RESOURCES_WCAT_PNG));
        board[0][1].put(new Snake(true, RESOURCES_WKNIGHT_PNG));
        board[0][2].put(new Bishop(true, RESOURCES_WBISHOP_PNG));
        board[0][3].put(new Amazon(true, RESOURCES_WAMA_PNG));
        board[0][4].put(new King(true, RESOURCES_WKING_PNG));
        board[0][5].put(new Bishop(true, RESOURCES_WBISHOP_PNG));
        board[0][6].put(new Snake(true, RESOURCES_WKNIGHT_PNG));
        board[0][7].put(new Cat(true, RESOURCES_WCAT_PNG));
        //Black Back Pieces
        board[7][0].put(new Cat(false, RESOURCES_BCAT_PNG));
        board[7][1].put(new Snake(false, RESOURCES_BKNIGHT_PNG));
        board[7][2].put(new Bishop(false, RESOURCES_BBISHOP_PNG));
        board[7][3].put(new Amazon(false, RESOURCES_BAMA_PNG));
        board[7][4].put(new King(false, RESOURCES_BKING_PNG));
        board[7][5].put(new Bishop(false, RESOURCES_BBISHOP_PNG));
        board[7][6].put(new Snake(false, RESOURCES_BKNIGHT_PNG));
        board[7][7].put(new Cat(false, RESOURCES_BCAT_PNG));
        for(int row = 0; row < 8; row++){
            board[1][row].put(new Pawn(true, RESOURCES_WPAWN_PNG));
        }
        for(int row = 0; row < 8; row++){
            board[6][row].put(new Pawn(true, RESOURCES_BPAWN_PNG));
        }

    }

    public Square[][] getSquareArray() {
        return this.board;
    }

    public boolean getTurn() {
        return whiteTurn;
    }

    public void setCurrPiece(Piece p) {
        this.currPiece = p;
    }

    public Piece getCurrPiece() {
        return this.currPiece;
    }

    @Override
    public void paintComponent(Graphics g) {
        Image backgroundImage = null;
        URL imageUrl = null;
        if (currPiece != null) {
            imageUrl = getClass().getResource("/src/main/java/com/example/" + currPiece.getImage());
        }

        if (imageUrl != null) {
            // This is the cleanest way to get an AWT Image object from a URL
            backgroundImage = Toolkit.getDefaultToolkit().createImage(imageUrl);
        }

        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                Square sq = board[x][y];
                if (sq == fromMoveSquare)
                    sq.setBorder(BorderFactory.createLineBorder(Color.blue));
                sq.paintComponent(g);
                // System.out.println("Painting square at " + x + ", " + y);

            }
        }
        if (currPiece != null) {
            if ((currPiece.getColor() && whiteTurn)
                    || (!currPiece.getColor() && !whiteTurn)) {
                final Image img = currPiece.getImage();
                g.drawImage(img, currX, currY, null);
            }
        }
    }

    @Override
    // precondition: the user's mouse is held down over a piece
    // postcondition: the user's mouse is released
    public void mousePressed(MouseEvent e) {
        currX = e.getX();
        currY = e.getY();

        Square sq = (Square) this.getComponentAt(new Point(e.getX(), e.getY()));

        if (sq.isOccupied()) {
            currPiece = sq.getOccupyingPiece();
            fromMoveSquare = sq;
            for (Square s : currPiece.getLegalMoves(this, fromMoveSquare)) {
                s.setBorder(BorderFactory.createLineBorder(Color.MAGENTA));
            }
            for (Square s : currPiece.getControlledSquares(board, fromMoveSquare)) {
                s.setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, Color.RED));
            }
            if (currPiece.getColor() != whiteTurn)
                return;
            sq.setDisplay(false);
        }
        repaint();
    }

    /*
     * isInCheck Function:
     * 1) Find every Opponent piece
     * 2) Make an arrayList of your opponent's pieces by looping through all the
     * squares on the board
     * 3) Get ALL controlled squares from your opponent's pieces
     * 4) Find King (by looping through every piece)
     * [variable].instanceOf.{class name} (returns true or false)
     */
    public boolean isInCheck(boolean kingColor) {
        ArrayList<Square> opponentSpaces = new ArrayList<Square>();
        for(int row = 0; row < 8; row++){
            for(int col = 0; col < 8; col++){
                Square currSquare = this.getSquareArray()[row][col];
                if(currSquare.isOccupied()){
                    opponentSpaces.add(currSquare);
                }
            }
        }
        
        return false;
        
        // if(Piece.instanceOf(King)){
        //     return true;
        // }
        // return false;
    }

    // TO BE IMPLEMENTED!
    // should move the piece to the desired location only if this is a legal move.
    // use the pieces "legal move" function to determine if this move is legal, then
    // complete it by
    // moving the new piece to it's new board location.
    // precondition: the user must release the mouse
    // postcondition: the mouse is not held, the piece either moves a space, or
    // snaps back to where it started
    @Override
    public void mouseReleased(MouseEvent e) {
        Square endSquare = (Square) this.getComponentAt(new Point(e.getX(), e.getY()));

        // using currPiece
        if (fromMoveSquare != null) {
            if ((currPiece != null) && (currPiece.getLegalMoves(this, fromMoveSquare).contains(endSquare))) {
                Piece p = endSquare.getOccupyingPiece();
                endSquare.put(currPiece);
                fromMoveSquare.removePiece();
                if (isInCheck(whiteTurn)) {
                    fromMoveSquare.put(currPiece);
                    endSquare.put(p);
                } else {
                    whiteTurn = !whiteTurn;
                }
            }
            fromMoveSquare.setDisplay(true);
        }
        for (Square[] row : board) {
            for (Square s : row) {
                s.setBorder(null);
            }
        }

        currPiece = null;
        repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        currX = e.getX() - 24;
        currY = e.getY() - 24;

        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

}