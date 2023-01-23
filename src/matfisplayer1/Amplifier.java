package matfisplayer1;

import battlecode.common.*;

public class Amplifier extends Robot{
    static void newAmplifier(RobotController robc) throws GameActionException {
        rc = robc;
        Pathing.set(rc, rng.nextBoolean());
    }
    static void runAmplifier() throws GameActionException {
    }
}
