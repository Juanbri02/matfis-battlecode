package matfisplayer1;

import battlecode.common.*;

import java.util.Random;

public strictfp class RobotPlayer {

    static int turnCount = 0;
    static final Random rng = new Random(6147);
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
    public static void run(RobotController rc) throws GameActionException {
        try {
            switch (rc.getType()) {
                case HEADQUARTERS:  Headquarters.newHeadquarters(rc);   break;
                case CARRIER:       Carrier.newCarrier(rc);             break;
                case LAUNCHER:      Launcher.newLauncher(rc);           break;
                case BOOSTER:       Booster.newBooster(rc);             break;
                case DESTABILIZER:  Destabilizer.newDestabilizer(rc);   break;
                case AMPLIFIER:     Amplifier.newAmplifier(rc);         break;
            }
        } catch (GameActionException e) {
            System.out.println(rc.getType() + "Game Exception");
            e.printStackTrace();
        } catch (Exception e) {
            //Normal exception
            System.out.println(rc.getType() + "Java Exception");
            e.printStackTrace();
        }
        while (true) {
            turnCount += 1;
            try {
                switch (rc.getType()) {
                    case HEADQUARTERS:  Headquarters.runHeadquarters(); break;
                    case CARRIER:       Carrier.runCarrier();           break;
                    case LAUNCHER:      Launcher.runLauncher();         break;
                    case BOOSTER:       Booster.runBooster();           break;
                    case DESTABILIZER:  Destabilizer.runDestabilizer(); break;
                    case AMPLIFIER:     Amplifier.runAmplifier();       break;
                }
            } catch (GameActionException e) {
                System.out.println(rc.getType() + " Game Exception");
                e.printStackTrace();
            } catch (Exception e) {
                System.out.println(rc.getType() + " Java Exception");
                e.printStackTrace();

            } finally {
                Clock.yield();
            }
        }
    }
}
