package com.example;
import java.awt.Graphics;
import java.awt.Image;
// import java.awt.image.BufferedImage;
// import java.io.File;
// import java.io.IOException;
import java.util.ArrayList;
// import java.util.LinkedList;
// import java.util.List;

// import javax.imageio.ImageIO;

//you will need to implement two functions in this file.
public class OffRook extends Piece{
    
    public OffRook(boolean isWhite, String img_file) {
        super(isWhite, img_file);
    }
    
    public boolean getColor() {
        return super.getColor();
    }
    
    public Image getImage() {
        return super.getImage();
    }
    
    public void draw(Graphics g, Square currentSquare) {
        int x = currentSquare.getX();
        int y = currentSquare.getY();
        
        g.drawImage(super.getImage(), x, y, null);
    }
    
    
    // TO BE IMPLEMENTED!
    //return a list of every square that is "controlled" by this piece. A square is controlled
    //if the piece capture into it legally.

    //Precondition: board cannot be null, square cannot be null, start row must be between 0-7, start col must be between 0-7
    //Post condition: returns all captureable squares
    public ArrayList<Square> getControlledSquares(Square[][] board, Square start) {
        ArrayList<Square> captures = new ArrayList<Square>();
        if(start.getRow()+1 < 8){
            // add the space in front of us to the capture list
            captures.add(board[start.getRow()+1][start.getCol()]);
        }
        if(start.getRow()-1 >= 0){
            //add the space behind us to the capture list
            captures.add(board[start.getRow()-1][start.getCol()]);
        }
        if(start.getCol()+1 < 8){
            //add the space to the right of us to the capture list
            captures.add(board[start.getRow()][start.getCol()+1]);
        }
        if(start.getCol()-1 >= 0){
            //add the space to the left of us to the capture list
            captures.add(board[start.getRow()][start.getCol()-1]);
        }
     return captures;
    }
    

    //TO BE IMPLEMENTED!
    //implement the move function here
    //it's up to you how the piece moves, but at the very least the rules should be logical and it should never move off the board!
    //returns an arraylist of squares which are legal to move to
    //please note that your piece must have some sort of logic. Just being able to move to every square on the board is not
    //going to score any points.
    //Precondition: board cannot be null, start cannot be null, start row must be between 0-7, start col must be between 0-7
    //Post condition: returns all legal moves
    public ArrayList<Square> getLegalMoves(Board b, Square start){
        ArrayList<Square> moves = new ArrayList<Square>();
                //check for in bounds first
                if(start.getCol()+1 < 8){
                    for(int row = start.getRow()+1; row < 8; row++){
                        Square right = b.getSquareArray()[row][start.getCol()+1];
                         if(!right.isOccupied() || right.getOccupyingPiece().getColor()!= super.getColor()){
                            moves.add(right);
                        }
                        if(right.isOccupied()){
                            break;
                        }
                        
                    }
                    for(int row = start.getRow()-1;row >= 0; row--){
                       Square right = b.getSquareArray()[row][start.getCol()+1];
                         if(!right.isOccupied() || right.getOccupyingPiece().getColor()!= super.getColor()){
                            moves.add(right);
                        }
                        if(right.isOccupied()){
                            break;
                        }
                    }
                    
                }

                 if(start.getCol()-1 >= 0){
                    for(int row = start.getRow()+1; row < 8; row++){
                        Square right = b.getSquareArray()[row][start.getCol()-1];
                         if(!right.isOccupied() || right.getOccupyingPiece().getColor()!= super.getColor()){
                            moves.add(right);
                        }
                        if(right.isOccupied()){
                            break;
                        }
                        
                    }
                    for(int row = start.getRow()-1;row >= 0; row--){
                       Square right = b.getSquareArray()[row][start.getCol()-1];
                         if(!right.isOccupied() || right.getOccupyingPiece().getColor()!= super.getColor()){
                            moves.add(right);
                        }
                        if(right.isOccupied()){
                            break;
                        }
                    }
                    
                }
                
    	return moves;
}
// post condition: 
}