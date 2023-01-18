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
        if(rc.canTakeAnchor(hqLocation,Anchor.STANDARD)) {
            rc.takeAnchor(hqLocation, Anchor.STANDARD);
            rc.setIndicatorString("Getting an anchor");
        }
        if (rc.getAnchor() != null) {
            rc.setIndicatorString("Anchor");
            Pathfinding.moveRandom(rc);
            return;
        }
        if(gettingRec){
            rc.setIndicatorString("Going");
            if(wellLocation == null) {
                wellLocation = Pathfinding.findWellLocation(rc);
                rc.setIndicatorString("Going nowhere");
                Pathfinding.moveRandom(rc);
            }else if (rc.canCollectResource(wellLocation, -1)) {
                rc.setIndicatorString("Getting things");
                rc.collectResource(wellLocation, -1);
                if(carrying(rc) == GameConstants.CARRIER_CAPACITY) {
                    gettingRec = false;
                }
            } else{
                rc.setIndicatorString("Going somewhere" + wellLocation);
                Pathfinding.move(rc, wellLocation);
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
                Pathfinding.move(rc, hqLocation);
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
