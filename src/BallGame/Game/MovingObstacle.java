package BallGame.Game;

import java.util.Random;

public class MovingObstacle extends Obstacle {

    private float normalizedMinX;

    private float normalizedMaxX;

    private float velocity;

    private boolean isMovingLeft;

    public MovingObstacle(int x, int y, int width, int height, float normalizedMinX, float normalizedMaxX, float velocity){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.normalizedMaxX = normalizedMaxX;
        this.normalizedMinX = normalizedMinX;
        this.velocity = velocity;
        isMovingLeft = false;
    }

    public void updatePosition(int screenWidth){
        if(isMovingLeft){
            normalizedX-=velocity;
            if(normalizedX<=normalizedMinX)
                isMovingLeft=false;
        }
        else{
            normalizedX+=velocity;
            if(normalizedX>=normalizedMaxX)
                isMovingLeft=true;
        }
        x=(int)(normalizedX*screenWidth);
    }
}
