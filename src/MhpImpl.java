import java.text.DecimalFormat;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class MhpImpl {

    public static void main(String[] args){

        final int DEFAULT_LOOP = 10000;
        final Random r = new Random();
        final DecimalFormat df = new DecimalFormat("0.00");
        long st = System.nanoTime();

        int v;
        int c;
        int o;
        int nc;

        int ifYes = 0;
        int ifNo = 0;
        long loopArgs;
        try{
            loopArgs = Long.parseLong(args[0]);
        }catch(NumberFormatException | ArrayIndexOutOfBoundsException e){
            loopArgs = -1;
        }

        final long nbLoop = (loopArgs == -1) ? DEFAULT_LOOP : loopArgs;

        for(long i = 0; i<nbLoop ; i++) {

            v = r.nextInt(3) + 1;
            c = r.nextInt(3) + 1;

            if (v == c) {
                boolean randomDoor = r.nextBoolean();
                if (v == 1)
                    o = randomDoor ? 2 : 3;
                else if (v == 2)
                    o = randomDoor ? 1 : 3;
                else
                    o = randomDoor ? 1 : 2;
            } else {
                if ((v + c) == 3)
                    o = 3;
                else {
                    o = Math.abs(v - c);
                }
            }

            nc = 6 - o - c;

            if (o == v)
                System.out.println("o==v");

            if (nc == v)
                ifYes++;
            else if (c == v)
                ifNo++;
        }

        System.out.println("");
        System.out.println(" --- Little MONTY HALL PROBLEM implementation ---");
        System.out.println("");
        System.out.println("Number of victories depending on whether you decide to change your choice or not (based on " + nbLoop + " tries) : ");
        System.out.println("If you changed your choice : " + ifYes + " (" + (df.format((long) (ifYes*100.0)/nbLoop)) + "%)");
        System.out.println("If you kept your choice : " + ifNo + " (" + (df.format((long) (ifNo*100.0)/nbLoop)) + "%)");
        System.out.println("Execution time : " + TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - st) + "ms");

    }
}
