package matfisplayer1;

import battlecode.common.GameActionException;
import battlecode.common.RobotController;

public class Booster {
    static RobotController rc;
    static void newBooster(RobotController robc) throws GameActionException {
        rc = robc;
    }
    static void runBooster() throws GameActionException {
    }
}
