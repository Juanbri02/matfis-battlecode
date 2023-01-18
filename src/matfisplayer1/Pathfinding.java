package matfisplayer1;

import battlecode.common.*;

public class Pathfinding {
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
    static int objectiveDistance(RobotController rc, MapLocation objective){
        if(objective == null) return -1;
        return rc.getLocation().distanceSquaredTo(objective);
    }
    static void move(RobotController rc, MapLocation objective) throws GameActionException{
        if(objective == null) return;
        Direction dir = rc.getLocation().directionTo(objective);
        rc.setIndicatorString("Move towards" + dir);
        if(rc.canMove(dir)) rc.move(dir);
        else moveRandom(rc);
    }
    static void moveRandom(RobotController rc) throws GameActionException{
        Direction dir = directions[RobotPlayer.rng.nextInt(directions.length)];
        if(rc.canMove(dir)) rc.move(dir);
        rc.setIndicatorString("Random move");
    }
    static MapLocation findHqLocation(RobotController rc) throws GameActionException {
        RobotInfo[] units = rc.senseNearbyRobots(-1, rc.getTeam());
        for(RobotInfo u : units) if(u.getType() == RobotType.HEADQUARTERS) return u.getLocation();
        return null;
    }
    static MapLocation findWellLocation(RobotController rc) throws GameActionException{
        WellInfo[] wells = rc.senseNearbyWells();
        if(wells.length > 0) return wells[0].getMapLocation();
        return null;
    }
}
