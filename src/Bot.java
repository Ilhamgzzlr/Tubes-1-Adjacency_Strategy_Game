// public class Bot {
//     public int[] move() {
//         // create random move
//         return new int[]{(int) (Math.random()*8), (int) (Math.random()*8)};
//     }
// }


public interface Bot {
    int[] move();
    void updateBot(int roundsLeft);
}