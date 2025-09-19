import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.ArrayList;

public class Dgame extends JPanel implements ActionListener,KeyListener {
     int  Bheigth = 350;
     int Bwidth = 750;

     // adding image
    Image dinsourImg;
    Image dinasourDeadImg;
    Image dinasosarJumping;
    Image cactus1img;
    Image cactus2img;
    Image cactus3img;



    class parameterImage{
        int x,y,width,height;
        Image img;

        public parameterImage(int x, int y, int width, int height, Image img) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.img = img;
        }
    }


    // height, width , x and y cor dinate for dinasour
    int dinowidth=88;
    int dinoheight=94;
    int dinoX=50;
    int dinoY=Bheigth -dinoheight;

    // creating a objet of dinasour
     parameterImage dinasour;

     // adding cactus
    int cactus1Width=34;
    int cactus2Width=69;
    int cactus3Width=102;

    int cactusheigth=70;
    int cactusX=700;
    int cactusY=Bheigth-cactusheigth;
    ArrayList<parameterImage> CactusArray = new ArrayList<>();

     // chaning the position when jump;
    int VelocityX = -12;
     int VelocityY= 0;
     int gravity=1;// to stop dinasour to move high
    boolean gameOver = false;
    long score=0;

     // gme loop for udpating game in 60fps
    Timer gameLoop;
    // timer for cactusTimer;
    Timer Cactus;
     public Dgame() {
         setPreferredSize(new Dimension(Bwidth, Bheigth));  // Set game window size
         setBackground(Color.GRAY);// Set background color
         setFocusable(true);//setting that this jpanel is goig to listerning keypressed
         addKeyListener(this);// adding listerer to constructor
         // adding image to image variable
         dinsourImg = new ImageIcon(getClass().getResource("/image/dino-run.gif")).getImage();
         dinasourDeadImg = new ImageIcon(getClass().getResource("./image/dino-dead.png")).getImage();
         dinasosarJumping = new ImageIcon(getClass().getResource("./image/dino-jump.png")).getImage();
         cactus1img = new ImageIcon(getClass().getResource("./image/cactus1.png")).getImage();
         cactus2img = new ImageIcon(getClass().getResource("./image/cactus2.png")).getImage();
         cactus3img = new ImageIcon(getClass().getResource("./image/cactus3.png")).getImage();

         // now we have to specify image , x and y position ,width and height for dinasour
         dinasour = new parameterImage(dinoX, dinoY, dinowidth, dinoheight, dinsourImg);
         //cactusT
         gameLoop= new Timer(1000/60,this);
         gameLoop.start();

         // catus timmer
         Cactus = new Timer(1500, new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
                 placedCactus();
             }
         });
         Cactus.start();
     }

    public void  placedCactus(){
        double placeCactusChance = Math.random();
        if(gameOver)
        return;
        if(placeCactusChance> .70){
            parameterImage cactus = new parameterImage(cactusX,cactusY,cactus3Width,cactusheigth,cactus3img);
            CactusArray.add(cactus);
        }
        else if(placeCactusChance> .50){
            parameterImage cactus = new parameterImage(cactusX,cactusY,cactus2Width,cactusheigth,cactus2img);
            CactusArray.add(cactus);
        }else {
            parameterImage cactus = new parameterImage(cactusX,cactusY,cactus1Width,cactusheigth,cactus1img);
            CactusArray.add(cactus);
        }
    }

     public void paintComponent(Graphics g){
         super.paintComponent(g);
         draw(g);
     }
     public void draw(Graphics g){
         g.drawImage(dinasour.img,dinasour.x,dinasour.y,dinasour.width,dinasour.height,null);
     for(int i=0;i<CactusArray.size();i++){
         parameterImage cactus = CactusArray.get(i);
         g.drawImage(cactus.img,cactus.x,cactus.y,cactus.width,cactus.height,null);
     }
     // score
         g.setColor( Color.black);
     g.setFont(new Font("Courier",Font.PLAIN,32));
     // to display time in jframe
if(gameOver){
    g.drawString(" Game Over your score  : "+String.valueOf(score),10,35);
    g.drawString(" for replay press spacebar",10,75);
    g.drawString(" for exit press Enter",10,115);
}else{
    g.drawString("Score : "+String.valueOf(score),10,35);
}
     }

     public void move(){
         VelocityY+= gravity;// move downward
     // update movement of the dinasour
     dinasour.y+=VelocityY;
// for bringing back to ground
     if(dinasour.y> dinoY){
         dinasour.y=dinoY;
         VelocityY=0;
         dinasour.img=dinsourImg;
     }
         for(int i=0;i<CactusArray.size();i++) {
             parameterImage cactus = CactusArray.get(i);
             cactus.x += VelocityX;
             if(collision(dinasour,cactus)){
                 dinasour.img=dinasourDeadImg;
                 gameOver=true;
                 gameLoop.stop();
                 Cactus.stop();
             }
         }
score ++;
     }
     boolean collision(parameterImage a,parameterImage b){
         return  a.x <b.x +b.width && // dinasour top left not touch with cactus top right
                 a.x + a.width >b.x &&// dinasour bottom rigth not touch to cactus top left
                 // paralell wall build in which dinasour not be there . now limit the wall
                 a.y <b.y +b.height &&// Checks if top of dinasour is above bottom of cactus
                 a.y + a.height >b.y; //Checks if bottom of dinasour is below top of cactus
     }


    @Override
    public void actionPerformed(ActionEvent e) {
         move();// updating the position
        repaint();
    }

    @Override// which key is typed
    public void keyTyped(KeyEvent e) {}

    @Override// when key is pressed in key
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_SPACE){
           if(dinasour.y==dinoY)
               VelocityY=-17;
           dinasour.img=dinasosarJumping;
        }
        if(gameOver){
            if(e.getKeyCode()==KeyEvent.VK_ENTER){
                System.exit(0);
            }
        }
        if(gameOver){
            if(e.getKeyCode()==KeyEvent.VK_SPACE){
                dinasour.y=dinoY;
                dinasour.img=dinsourImg;
                VelocityY=0;
                CactusArray.clear();
                score=0;
                gameOver=false;
                gameLoop.start();
                Cactus.start();
            }
        }
    }

    @Override// when key is released in key5
    public void keyReleased(KeyEvent e) {

    }

}
