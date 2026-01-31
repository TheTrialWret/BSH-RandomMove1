
public class Main {
    public static void main(String[] args) {
        RandomMove moduless = new RandomMove(300);
        try {
            moduless.StartDelay(3000);
            moduless.pressHoldAttack(Integer.MAX_VALUE, 600);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}