import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;


public class Point {
    private int type; // 0 - none, 1 - victim, 2 - predator

    //less is more
    int gravityPower = 2;
    int i = 0;

    int x;
    int y;
    int centerX;
    int centerY;

    public Point(int type, int x, int y, int centerX, int centerY) {
        this.type = type;
        this.x = x;
        this.y = y;
        this.centerX = centerX;
        this.centerY = centerY;
    }

    public void move(){
        Random random = new Random();
//        boolean upDown = random.nextBoolean();
//        if(upDown){
//            x = x + random.nextInt(3) - 1;
//        } else {
//            y = y + random.nextInt(3) - 1;
//        }
        x = x + random.nextInt(3) - 1;
        y = y + random.nextInt(3) - 1;

    }

    public void useGravity(){

        if(i == gravityPower){
            if(x - centerX > 0){
                x = x - 1;
            }
            else if (x - centerX < 0){
                x = x + 1;
            }

            if(y - centerY > 0){
                y = y - 1;
            }
            else if (y - centerY < 0){
                y = y + 1;
            }
            i = 0;
        }
        i++;
    }

    public int getType() {
        return type;
    }

    public void setState(int s) {
        type = s;
    }
}
