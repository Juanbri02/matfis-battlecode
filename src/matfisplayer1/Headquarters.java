package matfisplayer1;

import battlecode.common.*;
/**
 * Run a single turn for a Headquarters.
 * This code is wrapped inside the infinite loop in run(), so it is called once per turn.
 */
public class Headquarters extends Robot{
    static void newHeadquarters(RobotController robc) throws GameActionException {
        rc = robc;
        Comms.setRC(rc);
        Comms.addHeadquarter();
        Comms.dumpQueue();
    }
    static void runHeadquarters() throws GameActionException {
        // Pick a direction to build in.
        Direction dir = RobotPlayer.directions[RobotPlayer.rng.nextInt(RobotPlayer.directions.length)];
        RobotInfo[] allies = rc.senseNearbyRobots(-1, rc.getTeam());
        MapLocation newLoc = rc.getLocation().add(dir);
        if (RobotPlayer.turnCount % 2 == 0) {
            rc.setIndicatorString("Trying to build a carrier");
            if (rc.canBuildRobot(RobotType.CARRIER, newLoc)) {
                rc.buildRobot(RobotType.CARRIER, newLoc);
            }
        } else {
            // Let's try to build a launcher.
            rc.setIndicatorString("Trying to build a launcher");
            if (rc.canBuildRobot(RobotType.LAUNCHER, newLoc)) {
                rc.buildRobot(RobotType.LAUNCHER, newLoc);
            }
        }
        if (rc.canBuildAnchor(Anchor.STANDARD) && rc.getResourceAmount(ResourceType.ADAMANTIUM) > 100 ) {
            // If we can build an anchor do it!
            rc.buildAnchor(Anchor.STANDARD);
            rc.setIndicatorString("Building anchor! " + rc.getAnchor());
        }
    }
}
