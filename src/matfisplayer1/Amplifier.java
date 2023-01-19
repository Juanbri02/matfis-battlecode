package matfisplayer1;

import battlecode.common.*;

public class Amplifier {
    static RobotController rc;
    static void newAmplifier(RobotController robc) throws GameActionException {
        rc = robc;
        Pathing.set(rc, RobotPlayer.rng.nextBoolean());
    }
    static void runAmplifier() throws GameActionException {
    }
}
