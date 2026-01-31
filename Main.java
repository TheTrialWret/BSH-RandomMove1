
public class Main {
    public static void main(String[] args) {
        RandomMove moduless = new RandomMove(100);
        try {
            moduless.StartDelay(3000);
            moduless.pressRandomKeys(100);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}