import java.text.DecimalFormat;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.stream.LongStream;

public class MhpImpl {

    public static void main(String[] args){

        final int DEFAULT_LOOP = 10000;
        final DecimalFormat df = new DecimalFormat("0.00");
        long st = System.nanoTime();

        long ifYes[] = { 0 }; // Number of victories if you changed your decision after option is discovered
        long ifNo[] = { 0 }; // Number of victories if you did not change your decision after option was discovered

        long loopArgs;

        try{
            loopArgs = Long.parseLong(args[0]);
        }catch(NumberFormatException | ArrayIndexOutOfBoundsException e){
            loopArgs = -1;
        }

        final long nbLoop = (loopArgs == -1) ? DEFAULT_LOOP : loopArgs;
        
        LongStream.range(0,nbLoop).parallel().forEach(i-> {

            int v; // The simulated choice that makes you win
            int c; // The simulated users's choice
            int o; // The simulated option that is discovered after your choice (o!=v && o!=c)
            int nc; // The option that is left after the first option is discovered
            v = ThreadLocalRandom.current().nextInt(3) + 1;
            c = ThreadLocalRandom.current().nextInt(3) + 1;

            if (v == c) {
                boolean randomDoor = ThreadLocalRandom.current().nextBoolean();
                if (v == 1)
                    o = randomDoor ? 2 : 3;
                else if (v == 2)
                    o = randomDoor ? 1 : 3;
                else
                    o = randomDoor ? 1 : 2;
            } else {
                if ((v + c) == 3)
                    o = 3;
                else
                    o = Math.abs(v - c);
            }

            nc = 6 - o - c;

            if (o == v)
                System.out.println("o==v");

            if (nc == v)
               ifYes[0]++;
            else if (c == v)
                ifNo[0]++;
        });

        System.out.println("");
        System.out.println(" --- Little MONTY HALL PROBLEM implementation ---");
        System.out.println("");
        System.out.println("Number of victories depending on whether you decide to change your choice or not (based on " + nbLoop + " tries) : ");
        System.out.println("If you changed your choice : " + ifYes[0] + " (" + (df.format((long) (ifYes[0]*100.0)/nbLoop)) + "%)");
        System.out.println("If you kept your choice : " + ifNo[0] + " (" + (df.format((long) (ifNo[0]*100.0)/nbLoop)) + "%)");
        System.out.println("Execution time : " + TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - st) + "ms");
        System.out.println("Available cores : " + Runtime.getRuntime().availableProcessors());

    }
}
