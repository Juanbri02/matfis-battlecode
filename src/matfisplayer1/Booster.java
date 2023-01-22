package matfisplayer1;

import battlecode.common.GameActionException;
import battlecode.common.RobotController;

public class Booster extends Robot{
    static void newBooster(RobotController robc) throws GameActionException {
        rc = robc;
        Pathing.set(rc, RobotPlayer.rng.nextBoolean());
    }
    static void runBooster() throws GameActionException {
    }
}
