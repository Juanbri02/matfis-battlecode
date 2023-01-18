package matfisplayer1;

import battlecode.common.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class Carrier {
    static MapLocation hqLocation, wellLocation;
    static boolean gettingRec = true;
    static void newCarrier(RobotController rc) throws GameActionException{
        hqLocation = Pathfinding.findHqLocation(rc);
        wellLocation = Pathfinding.findWellLocation(rc);
    }
    static void runCarrier(RobotController rc) throws GameActionException {
        if(wellLocation == null){
            wellLocation = Pathfinding.findWellLocation(rc);
            Pathfinding.setObjective(wellLocation);
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
            return;
        }

        if(gettingRec){
            if(wellLocation == null) {
                Pathfinding.moveRandom(rc);
            }else if (rc.canCollectResource(wellLocation, -1)) {
                rc.collectResource(wellLocation, -1);
                if(carrying(rc) == GameConstants.CARRIER_CAPACITY) {
                    Pathfinding.setObjective(hqLocation);
                    gettingRec = false;
                }
            } else{
                Pathfinding.move(rc);
            }
        }else{
            ResourceType res = nonEmptyResource(rc);
            if(rc.canTransferResource(hqLocation, res, rc.getResourceAmount(res))){
                rc.transferResource(hqLocation,res,rc.getResourceAmount(res));
                if(carrying(rc) == 0){
                    Pathfinding.setObjective(wellLocation);
                    if(rc.canTakeAnchor(hqLocation,Anchor.STANDARD))
                        rc.takeAnchor(hqLocation,Anchor.STANDARD);
                    gettingRec = true;
                }
            }else{
                Pathfinding.move(rc);
            }
        }
    }
    static int carrying(RobotController rc){
        return rc.getResourceAmount(ResourceType.ADAMANTIUM) + rc.getResourceAmount(ResourceType.ELIXIR) + rc.getResourceAmount(ResourceType.MANA);
    }
    static ResourceType nonEmptyResource(RobotController rc){
        if (rc.getResourceAmount(ResourceType.ADAMANTIUM) != 0) return ResourceType.ADAMANTIUM;
        if (rc.getResourceAmount(ResourceType.MANA) != 0) return ResourceType.MANA;
        return ResourceType.ELIXIR;
    }
}
