package matfisplayer1;

import battlecode.common.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Carrier {
    static MapLocation hqLocation, wellLocation;
    static void runCarrier(RobotController rc) throws GameActionException {
        if(hqLocation == null) {
            findHqLocation(rc);
        }
        if(wellLocation == null){
            findWellLocation(rc);
        }
        if (rc.getAnchor() != null) {
            // If I have an anchor singularly focus on getting it to the first island I see
            int[] islands = rc.senseNearbyIslands();
            Set<MapLocation> islandLocs = new HashSet<>();
            for (int id : islands) {
                MapLocation[] thisIslandLocs = rc.senseNearbyIslandLocations(id);
                islandLocs.addAll(Arrays.asList(thisIslandLocs));
            }
            if (islandLocs.size() > 0) {
                MapLocation islandLocation = islandLocs.iterator().next();
                while (!rc.getLocation().equals(islandLocation)) {
                    Direction dir = rc.getLocation().directionTo(islandLocation);
                    if (rc.canMove(dir)) {
                        rc.move(dir);
                    }
                }
                if (rc.canPlaceAnchor()) {
                    rc.placeAnchor();
                }
            }
        }
        if(wellLocation != null && rc.canCollectResource(wellLocation, -1)) rc.collectResource(wellLocation,-1);

        // Occasionally try out the carriers attack
        if (RobotPlayer.rng.nextInt(20) == 1) {
            RobotInfo[] enemyRobots = rc.senseNearbyRobots(-1, rc.getTeam().opponent());
            if (enemyRobots.length > 0) {
                if (rc.canAttack(enemyRobots[0].location)) {
                    rc.attack(enemyRobots[0].location);
                }
            }
        }

        // If we can see a well, move towards it
        WellInfo[] wells = rc.senseNearbyWells();
        if (wells.length > 1 && rc.getResourceAmount(ResourceType.ADAMANTIUM) == 15) {
            WellInfo well_one = wells[1];
            Direction dir = rc.getLocation().directionTo(well_one.getMapLocation());
            if (rc.canMove(dir))
                rc.move(dir);
        }
        // Also try to move randomly.
        Direction dir = RobotPlayer.directions[RobotPlayer.rng.nextInt(RobotPlayer.directions.length)];
        if (rc.canMove(dir)) {
            rc.move(dir);
        }
    }
    static void findHqLocation(RobotController rc) throws GameActionException{
        RobotInfo[] units = rc.senseNearbyRobots(-1, rc.getTeam());
        for(RobotInfo u : units) if(u.getType() == RobotType.HEADQUARTERS) hqLocation = u.getLocation();
    }
    static void findWellLocation(RobotController rc) throws GameActionException{
        WellInfo[] wells = rc.senseNearbyWells();
        if(wells.length > 0) hqLocation = wells[0].getMapLocation();
    }
}
