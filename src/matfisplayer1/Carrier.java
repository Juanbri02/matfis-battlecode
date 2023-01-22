package matfisplayer1;

import battlecode.common.*;

import java.util.Random;

public class Carrier extends Robot{
    static MapLocation hqLocation, wellLocation, islandLocation;
    static boolean gettingRec = true;
    static void newCarrier(RobotController robc) throws GameActionException{
        rc = robc;
        Pathing.set(rc, RobotPlayer.rng.nextBoolean());
        HQs = Comms.updateHeadquarterInfo();
        wellLocation = Pathing.findWellLocation();
        Pathing.setObjective(wellLocation);
        islandLocation = Pathing.findIslandLocation();
        System.out.println(rc.getType() + " HE nadic");
    }
    static void runCarrier() throws GameActionException {
        if(rc.canTakeAnchor(hqLocation,Anchor.STANDARD)) {
            rc.takeAnchor(hqLocation, Anchor.STANDARD);
            rc.setIndicatorString("Getting an anchor");
        }
        if (rc.getAnchor() != null) {
            rc.setIndicatorString("Anchor");
            if(islandLocation == null) {
                islandLocation = Pathing.findIslandLocation();
                Pathing.setObjective(islandLocation);
            }
            Pathing.move();
            if(rc.canPlaceAnchor()) {
                rc.placeAnchor();
            }
            return;
        }
        if(gettingRec){
            rc.setIndicatorString("Going");
            if(wellLocation == null) {
                wellLocation = Pathing.findWellLocation();
                Pathing.setObjective(wellLocation);
                rc.setIndicatorString("Going nowhere");
                Pathing.move();
            }else if (rc.canCollectResource(wellLocation, -1)) {
                rc.setIndicatorString("Getting things");
                rc.collectResource(wellLocation, -1);
                if(carrying() == GameConstants.CARRIER_CAPACITY) {
                    gettingRec = false;
                    Pathing.setObjective(hqLocation);
                }
            } else{
                rc.setIndicatorString("Going somewhere" + wellLocation);
                Pathing.move();
            }
        }else{
            rc.setIndicatorString("Returning");
            ResourceType res = nonEmptyResource();
            if(rc.canTransferResource(hqLocation, res, rc.getResourceAmount(res))){
                rc.transferResource(hqLocation,res,rc.getResourceAmount(res));
                if(carrying() == 0) {
                    gettingRec = true;
                    Pathing.setObjective(wellLocation);
                }
            }else{
                Pathing.move();
            }
        }
    }
    private static int carrying(){
        return rc.getResourceAmount(ResourceType.ADAMANTIUM) + rc.getResourceAmount(ResourceType.ELIXIR) + rc.getResourceAmount(ResourceType.MANA);
    }
    private static ResourceType nonEmptyResource(){
        if (rc.getResourceAmount(ResourceType.ADAMANTIUM) != 0) return ResourceType.ADAMANTIUM;
        if (rc.getResourceAmount(ResourceType.MANA) != 0) return ResourceType.MANA;
        return ResourceType.ELIXIR;
    }
}
