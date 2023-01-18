package matfisplayer1;

import battlecode.common.*;

public class Carrier {
    static MapLocation hqLocation, wellLocation, islandLocation;
    static boolean gettingRec = true;
    static RobotController rc;
    static void newCarrier(RobotController robc) throws GameActionException{
        rc = robc;
        Pathing.setRc(rc);
        hqLocation = Pathing.findHqLocation();
        wellLocation = Pathing.findWellLocation();
        islandLocation = Pathing.findIslandLocation();
    }
    static void runCarrier() throws GameActionException {
        if(rc.canTakeAnchor(hqLocation,Anchor.STANDARD)) {
            rc.takeAnchor(hqLocation, Anchor.STANDARD);
            rc.setIndicatorString("Getting an anchor");
        }
        if (rc.getAnchor() != null) {
            rc.setIndicatorString("Anchor");
            if(islandLocation == null)
                islandLocation = Pathing.findIslandLocation();
            Pathing.move(islandLocation);
            if(rc.canPlaceAnchor())
                rc.placeAnchor();
            return;
        }
        if(gettingRec){
            rc.setIndicatorString("Going");
            if(wellLocation == null) {
                wellLocation = Pathing.findWellLocation();
                rc.setIndicatorString("Going nowhere");
                Pathing.moveRandom();
            }else if (rc.canCollectResource(wellLocation, -1)) {
                rc.setIndicatorString("Getting things");
                rc.collectResource(wellLocation, -1);
                if(carrying(rc) == GameConstants.CARRIER_CAPACITY) {
                    gettingRec = false;
                }
            } else{
                rc.setIndicatorString("Going somewhere" + wellLocation);
                Pathing.move(wellLocation);
            }
        }else{
            rc.setIndicatorString("Returning");
            ResourceType res = nonEmptyResource(rc);
            if(rc.canTransferResource(hqLocation, res, rc.getResourceAmount(res))){
                rc.transferResource(hqLocation,res,rc.getResourceAmount(res));
                if(carrying(rc) == 0) {
                    gettingRec = true;
                }
            }else{
                Pathing.move(hqLocation);
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
