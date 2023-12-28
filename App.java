import javax.swing.*;



public class App {
    public static void main(String[] args) throws Exception {
        

        int boardWidth = 400;
        int boardHeight = boardWidth;
        

        JFrame frame = new JFrame("Snake");
        frame.setVisible(true); 
        frame.setSize(boardWidth, boardHeight); // Window will be of the given size (600*600)
        frame.setLocationRelativeTo(null); // Opens the window at center of screen
        frame.setResizable(false); // User cannot resize the window
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Program will terminate when user closes the window

        SnakeGame snakeGame = new SnakeGame(boardWidth, boardHeight);
        frame.add(snakeGame);
        frame.pack(); //Packs our frame to adjust with the title bar
        snakeGame.requestFocus();
    
    }
}
