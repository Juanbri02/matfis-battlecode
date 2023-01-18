package matfisplayer1;

import battlecode.common.GameActionException;
import battlecode.common.RobotController;

public class Destabilizer {
    static RobotController rc;
    static void newDestabilizer(RobotController robc) throws GameActionException {
        rc = robc;
        Pathing.set(rc, RobotPlayer.rng.nextBoolean());
    }
    static void runDestabilizer() throws GameActionException {
    }
}
