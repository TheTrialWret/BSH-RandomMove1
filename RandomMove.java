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

    //Assigns keys to variables
    private int assignKeys(int index) {
        switch (index) {
            case 0:
                return KeyEvent.VK_W;
            case 1:
                return KeyEvent.VK_A;
            case 2: 
                return KeyEvent.VK_S;
            case 3: 
                return KeyEvent.VK_D;
            case 4:
                return KeyEvent.VK_E;
            default:
                return -1;
        }
    }

    //Fires a projectile using Auto Aim
    public void attack() throws AWTException {
        Robot robot = new Robot();
        
        robot.keyPress(assignKeys(4));
        robot.delay(100);
        robot.keyRelease(assignKeys(4));

    }


    // MODULE 1
    // PRESSES ONE AT A TIME RANDOM KEYS

    public void pressRandomKeys(int times) throws Exception {
        Random r = new Random();
        for (int i = 0; i < times; i++) {
            doTheKeyPress(r.nextInt(4));
            checkFailSafe();
        }
    }

    public void doTheKeyPress(int index) throws Exception {
        Robot robot = new Robot();
        int a = assignKeys(index);
        if (a == -1) 
            throw new Exception("KeyEvent is NOT valid!");
        //System.out.println("yes I run");
        robot.keyPress(a);
        robot.delay(duration);
        robot.keyRelease(a);
    }

    // MODULE 2
    // PRESSES AND HOLDS RANDOM KEYS AND RANDOM DURATION

    public void pressHoldRandomKeys(int times, int maxDuration) throws Exception {
        Random r = new Random();
        for (int i = 0; i < times; i++) {
            doTheKeyHold(r.nextInt(4), r.nextInt(maxDuration + 1));
            checkFailSafe();
        }
    }

    //declaration of list of keys to presshold
    private boolean[] press = {false, false, false, false};
    
    public void doTheKeyHold(int index, int dur) throws Exception {
        Robot robot = new Robot();
        int a = assignKeys(index);
        if (a == -1) 
            throw new Exception("KeyEvent is NOT valid!");
        // toggles between pressed and not pressed
        if (press[index]) {
            robot.keyRelease(a);
            press[index] = false;
        } else {
            robot.keyPress(a);
            press[index] = true;
        }
        robot.delay(dur);
    }

    // MODULE 3 
    // RANDOMLY ATTACKS ONCE IN A WHILE

    public void pressHoldAttack(int times, int maxDuration) throws Exception {
        Random r = new Random();
        for (int i = 0; i < times; i++) {
            doTheKeyHold(r.nextInt(4), r.nextInt(maxDuration + 1));
            if (r.nextInt(50) < (maxDuration / 50) + 1) {
                attack();
            }
            checkFailSafe();
        }
    }

    // FAILSAFE MODULE [DO NOT TOUCH!!!!!!]
    // STOPS THE PROGRAM IF THE MOUSE IS IN UPPER LEFT CORNER
    // PAUSES PROGRAM IF THE MOUSE IS IN UPPER RIGHT CORNER
    // RESUMES PROGRAM IF THE MOUSE IS IN BOTTOM RIGHT CORNER

    private void checkFailSafe() throws Exception {
        PointerInfo mouse = MouseInfo.getPointerInfo();
        Robot robot = new Robot();


        //Thows failsafeException if mouse is TL corner


        if ((mouse.getLocation().getX()) < 10 && (mouse.getLocation().getY()) < 10) {
            for (int i = 0; i < 5; i++) {
                robot.keyRelease(assignKeys(i));
            }
            throw new Exception("Triggered FAILSAFE!!!");
        }


        //Pauses if mouse is TR corner

        if ((mouse.getLocation().getX()) > 1430 && (mouse.getLocation().getY()) < 10) {
            
            for (int i = 0; i < 4; i++) {
                robot.keyRelease(assignKeys(i));
                press[i] = false;
            }

            System.out.println("System PAUSED!");


            while (!((mouse.getLocation().getX()) > 1430 && (mouse.getLocation().getY()) > 800)) {
                
                robot.delay(100);

                mouse = MouseInfo.getPointerInfo();
                
                //Can throw failsafeException during pause if mouse is TL corner
                //printMousePosition();
                if ((mouse.getLocation().getX()) < 10 && (mouse.getLocation().getY()) < 10) {
                    for (int i = 0; i < 5; i++) {
                        robot.keyRelease(assignKeys(i));
                    }
                    throw new Exception("Triggered FAILSAFE!!!");
                }
            }

            // only continues when mouse is BR corner

            System.out.println("System RESUMED!");
        }
    }

    public void printMousePosition() {
        PointerInfo mouse = MouseInfo.getPointerInfo();
        System.out.println(mouse.getLocation().getX());
        System.out.println(mouse.getLocation().getY());
    }
}
