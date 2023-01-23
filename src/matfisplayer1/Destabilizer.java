package matfisplayer1;

import battlecode.common.GameActionException;
import battlecode.common.RobotController;

public class Destabilizer extends Robot {
    static void newDestabilizer(RobotController robc) throws GameActionException {
        rc = robc;
        Pathing.set(rc, rng.nextBoolean());
    }
    static void runDestabilizer() throws GameActionException {

    }
}
