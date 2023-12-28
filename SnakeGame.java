import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList; //Stores the snake components
import java.util.Random; // Used to generate random x-y coordinates for apple
import javax.swing.*;


public class SnakeGame extends JPanel implements ActionListener, KeyListener{
    private class Tile{
        int x;
        int y;

        Tile(int x, int y){
            this.x = x;
            this.y = y;
        }
    }

    
    private int boardWidth;
    private int boardHeight;
    int tileSize = 25;

    //Snake
    Tile snakeHead;
    ArrayList<Tile> snakeBody;
    //Food
    Tile food;
    Random random;

    //Game Logic
    Timer gameLoop;
    int velocityX;
    int velocityY;
    boolean gameOver = false;


    //Constructor
    SnakeGame(int boardWidth, int boardHeight){
        this.boardHeight = boardHeight;
        this.boardWidth = boardWidth;
        setPreferredSize(new Dimension(this.boardWidth, this.boardHeight));
        setBackground(Color.black);
        addKeyListener(this);
        setFocusable(true);

        snakeHead = new Tile(5, 5);
        snakeBody = new ArrayList<Tile>();

        food = new Tile(10, 10);
        random = new Random();
        placeFood();

        velocityX = 0;
        velocityY = 0;

        gameLoop = new Timer(100, this);
        gameLoop.start();
       
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g){
        // //Grid - Remove Later
        // for(int i = 0; i < boardWidth/tileSize; i++){
        //     g.drawLine(i*tileSize, 0, i*tileSize, boardHeight);
        //     g.drawLine(0, i*tileSize, boardWidth, i*tileSize);
        // }

        //Food
        g.setColor(Color.red);
        g.fill3DRect(food.x*tileSize, food.y*tileSize, tileSize, tileSize, true);

        //Snake
        g.setColor(Color.green);
        g.fill3DRect(snakeHead.x*tileSize, snakeHead.y*tileSize, tileSize, tileSize, true);
        g.setColor(Color.yellow);

        //Snake Body
        for (int i = 0; i < snakeBody.size(); i++){
            Tile snakepart = snakeBody.get(i);
            g.fillRect(snakepart.x*tileSize, snakepart.y*tileSize, tileSize, tileSize);
        }

        //Score
        g.setFont(new Font("Arial", Font.PLAIN, 15));
        g.setColor(Color.white);
        if (gameOver){
            g.setColor(Color.red);
            g.drawString("Game Over: " + snakeBody.size(), tileSize -16, tileSize);
        } else {
            g.drawString("Score: " + snakeBody.size(), tileSize -16, tileSize);
        }
    }

        public void placeFood(){
            food.x = random.nextInt(boardWidth / tileSize); // 600/25 = 24
            food.y = random.nextInt(boardHeight / tileSize); // 600/25 = 24
        }

        public boolean collision(Tile tile1, Tile tile2){
            return tile1.x == tile2.x && tile1.y == tile2.y;
        }

        public void move(){
            //eat food
            if (collision(snakeHead, food)){
                snakeBody.add(new Tile(food.x, food.y));
                placeFood();
            }

            //Snake Body
            for(int i = snakeBody.size()-1; i >=0; i--){
                Tile snakePart = snakeBody.get(i);
                if (i==0){
                    snakePart.x = snakeHead.x;
                    snakePart.y = snakeHead.y;
                } else {
                    snakePart.x = snakeBody.get(i-1).x;
                    snakePart.y = snakeBody.get(i-1).y;
                }
            }

            //Snake Head
            snakeHead.x += velocityX;
            snakeHead.y += velocityY;
            
            int numOfTiles = boardHeight / tileSize;

            if (snakeHead.y > numOfTiles){
                snakeHead.y = 0;
            } 
            else if (snakeHead.y < 0){
                snakeHead.y = numOfTiles;
            }
            else if (snakeHead.x > numOfTiles){
                snakeHead.x = 0;
            } 
            else if (snakeHead.x < 0){
                snakeHead.x = numOfTiles;
            }

            //Game over Conditios
            for(int i = 0; i < snakeBody.size(); i++){
                Tile snakePart = snakeBody.get(i);
                //Collide with the snake head
                if (collision(snakeHead, snakePart)){
                    gameOver = true;
                }
            }

        }

        @Override
        public void actionPerformed(ActionEvent e) {
            move();
            repaint();
            if (gameOver){
                gameLoop.stop();
            }
        }

        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_UP ){
                velocityX = 0;
                velocityY = -1;
            }
            else if (e.getKeyCode() == KeyEvent.VK_DOWN ){
                velocityX = 0;
                velocityY = 1;
            }           
            else if (e.getKeyCode() == KeyEvent.VK_RIGHT ){
                velocityX = 1;
                velocityY = 0;
            }
            else if (e.getKeyCode() == KeyEvent.VK_LEFT ){
                velocityX = -1;
                velocityY = 0;
            }
        }



        //
        @Override
        public void keyReleased(KeyEvent e) {
         }

        @Override
        public void keyTyped(KeyEvent e) {
        }

}
