import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.*;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.util.Random;
public class RandomMove {

    private int duration;

    public RandomMove(int duration) {
        this.duration = duration;
    }

    public void StartDelay(int ms) throws AWTException {
        Robot robot = new Robot();
        robot.delay(ms);
    }

    public void pressRandomKeys(int times) throws Exception {
        Random r = new Random();
        for (int i = 0; i < times; i++) {
            doTheKeyPress(r.nextInt(4));
            checkFailSafe();
        }
    }

    public void doTheKeyPress(int index) throws Exception {
        Robot robot = new Robot();
        int a = -1;
        //System.out.println(index);
        switch (index) {
            case 0:
                a = KeyEvent.VK_W;
                break;
            case 1:
                a = KeyEvent.VK_A;
                break;
            case 2: 
                a = KeyEvent.VK_S;
                break;
            case 3: 
                a = KeyEvent.VK_D;
                break;
            default:
                a = -1;
                break;
        } 
        if (a == -1) 
            throw new Exception("KeyEvent is NOT valid!");
        //System.out.println("yes I run");
        robot.keyPress(a);
        robot.delay(duration);
        robot.keyRelease(a);
    }

    private void checkFailSafe() throws Exception {
        PointerInfo mouse = MouseInfo.getPointerInfo();
        if ((mouse.getLocation().getX()) < 10 && (mouse.getLocation().getY()) < 10) {
            throw new Exception("Triggered FAILSAFE!!!");
        }
    }
}
