package matfisplayer1;

import battlecode.common.*;

public class Pathing {
    static Direction currentDirection;
    static boolean rightHanded;
    static RobotController rc;
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
    static int objectiveDistance(MapLocation objective){
        if(objective == null) return -1;
        return rc.getLocation().distanceSquaredTo(objective);
    }
    static void moveBug(MapLocation objective) throws GameActionException{
        MapLocation actual = rc.getLocation();
        if(actual.equals(objective) || !rc.isMovementReady()) return;
        Direction dir = actual.directionTo(objective);
        if(rc.canMove(dir)) {
            rc.move(dir);
            currentDirection = null;
            return;
        }
        if(currentDirection == null){
            currentDirection = dir;
        }
        for(int i = 0; i < directions.length; ++i) {
            currentDirection = (rightHanded ? currentDirection.rotateRight() : currentDirection.rotateLeft());
            if (rc.canMove(currentDirection)) {
                rc.move(dir);
                currentDirection = (rightHanded ? currentDirection.rotateLeft() : currentDirection.rotateRight());
                return;
            }
        }
        currentDirection = null;
    }
    static void move(MapLocation objective) throws GameActionException{
        if(objective == null) {
            moveRandom();
            return;
        }
        Direction dir = rc.getLocation().directionTo(objective);
        rc.setIndicatorString("Move towards" + dir);
        if(rc.canMove(dir)) rc.move(dir);
        else moveRandom();
    }
    static void moveRandom() throws GameActionException{
        Direction dir = directions[RobotPlayer.rng.nextInt(directions.length)];
        if(rc.canMove(dir)) rc.move(dir);
        rc.setIndicatorString("Random move");
    }
    static MapLocation findHqLocation() throws GameActionException {
        RobotInfo[] units = rc.senseNearbyRobots(-1, rc.getTeam());
        for(RobotInfo u : units) if(u.getType() == RobotType.HEADQUARTERS) return u.getLocation();
        return null;
    }
    static MapLocation findWellLocation() throws GameActionException{
        WellInfo[] wells = rc.senseNearbyWells();
        if(wells.length > 0) return wells[0].getMapLocation();
        return null;
    }
    static MapLocation findIslandLocation() throws GameActionException{
        int[] islands = rc.senseNearbyIslands();
        if(islands.length > 0) return rc.senseNearbyIslandLocations(islands[0])[0];
        return null;
    }
    static void setRc(RobotController robc){
        rc = robc;
    }
}
