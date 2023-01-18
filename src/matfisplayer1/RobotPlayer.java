package matfisplayer1;

import battlecode.common.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * RobotPlayer is the class that describes your main robot strategy.
 * The run() method inside this class is like your main function: this is what we'll call once your robot
 * is created!
 */
public strictfp class RobotPlayer {
    /**
     * We will use this variable to count the number of turns this robot has been alive.
     * You can use static variables like this to save any information you want. Keep in mind that even though
     * Y ahora esto es mi rama Main, creo
     * Visca Fnatic
     * these variables are static, in Battlecode they aren't actually shared between your robots.
     */

    static int turnCount = 0;
    static final Random rng = new Random(6147);

    /** Array containing all the possible movement directions. */
    static final Direction[] directions = {
            Direction.NORTH,
            Direction.NORTHEAST,
            Direction.EAST,
            Direction.SOUTHEAST,
            Direction.SOUTH,
            Direction.SOUTHWEST,
            Direction.WEST,
            Direction.NORTHWEST,
    };

    /**
     * run() is the method that is called when a robot is instantiated in the Battlecode world.
     * It is like the main function for your robot. If this method returns, the robot dies!
     *
     * @param rc  The RobotController object. You use it to perform actions from this robot, and to get
     *            information on its current status. Essentially your portal to interacting with the world.
     **/
    public static void run(RobotController rc) throws GameActionException {

        // Hello world! Standard output is very useful for debugging.
        // Everything you say here will be directly viewable in your terminal when you run a match!
        //System.out.println("I'm a " + rc.getType() + " and I just got created! I have health " + rc.getHealth());

        try {
            switch (rc.getType()) {
                case HEADQUARTERS:  Headquarters.newHeadquarters(rc);  break;
                case CARRIER:       Carrier.newCarrier(rc);   break;
                case LAUNCHER:      Launcher.newLauncher(rc); break;
                case BOOSTER:       Booster.newBooster(rc); break;
                case DESTABILIZER:  Destabilizer.newDestabilizer(rc); break;
                case AMPLIFIER:     Amplifier.newAmplifier(rc); break;
            }
        } catch (GameActionException e) {
            System.out.println(rc.getType() + "Game Exception");
            e.printStackTrace();
        } catch (Exception e) {
            //Normal exception
            System.out.println(rc.getType() + "Java Exception");
            e.printStackTrace();
        }

        // You can also use indicators to save debug notes in replays.
        rc.setIndicatorString("Hello world!");
        while (true) {
            turnCount += 1;  // We have now been alive for one more turn!

            // Try/catch blocks stop unhandled exceptions, which cause your robot to explode.
            try {
                switch (rc.getType()) {
                    case HEADQUARTERS:  Headquarters.runHeadquarters();  break;
                    case CARRIER:       Carrier.runCarrier();   break;
                    case LAUNCHER:      Launcher.runLauncher(); break;
                    case BOOSTER:       Booster.runBooster(); break;
                    case DESTABILIZER:  Destabilizer.runDestabilizer(); break;
                    case AMPLIFIER:     Amplifier.runAmplifier(); break;
                }
            } catch (GameActionException e) {
                // Oh, no! It looks like we did something illegal in the Battlecode world. You should
                // handle GameActionExceptions judiciously, in case unexpected events occur in the game
                // world. Remember, uncaught exceptions cause your robot to explode!
                System.out.println(rc.getType() + " Exception");
                e.printStackTrace();
            } catch (Exception e) {
                // Oh, no! It looks like our code tried to do something bad. This isn't a
                // GameActionException, so it's more likely to be a bug in our code.
                System.out.println(rc.getType() + " Exception");
                e.printStackTrace();

            } finally {
                // Signify we've done everything we want to do, thereby ending our turn.
                // This will make our code wait until the next turn, and then perform this loop again.
                Clock.yield();
            }
            // End of loop: go back to the top. Clock.yield() has ended, so it's time for another turn!
        }

        // Your code should never reach here (unless it's intentional)! Self-destruction imminent...
    }


}
