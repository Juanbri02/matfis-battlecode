package matfisplayer1;

import battlecode.common.*;

public class Carrier extends Robot{
    static MapLocation hqLocation, wellLocation, islandLocation;
    static boolean gettingRec = true;
    static void newCarrier(RobotController robc) throws GameActionException{
        rc = robc;
        Pathing.set(rc, rng.nextBoolean());
        Comms.setRC(rc);
        HQs = Comms.updateHeadquarterInfo();

        hqLocation = Pathing.findHqLocation();
        wellLocation = Pathing.findWellLocation();
        Pathing.setObjective(wellLocation);
        System.out.println(rc.getType() + " HE nadic");
    }
    static void runCarrier() throws GameActionException {
        turnCount++;
        int[] islands = rc.senseNearbyIslands();
        actIslands(islands);
        if(rc.canTakeAnchor(hqLocation,Anchor.STANDARD)) {
            rc.takeAnchor(hqLocation, Anchor.STANDARD);
//            rc.setIndicatorString("Getting an anchor");
        }
        if (rc.getAnchor() != null) {
            if(turnCount%3 == 0){
                rc.setIndicatorString("Anchor");
                int minDist = 10000;
                int priority = 2;
                MapLocation obj = null;
                for (IslandInfo info : Islands)
                    if (info.getLoc() != null) {
                        int actPrio;
                        if (info.getOwner() == rc.getTeam()) actPrio = 2;
                        else if (info.getOwner() == Team.NEUTRAL) actPrio = 0;
                        else actPrio = 1;
                        int dist = info.getLoc().distanceSquaredTo(rc.getLocation());
                        if (actPrio < priority || (actPrio == priority && dist < minDist)) {
                            minDist = dist;
                            priority = actPrio;
                            obj = info.getLoc();
                        }
                    }
                Pathing.setObjective(obj);
            }
            Pathing.move();
            if(rc.canPlaceAnchor()) {
                rc.placeAnchor();
            }
        } else if(gettingRec){
            if(wellLocation == null) {
                wellLocation = Pathing.findWellLocation();
                Pathing.setObjective(wellLocation);
                Pathing.move();
            }else if (rc.canCollectResource(wellLocation, -1)) {
                rc.collectResource(wellLocation, -1);
                if(carrying() == GameConstants.CARRIER_CAPACITY) {
                    gettingRec = false;
                    Pathing.setObjective(hqLocation);
                }
            } else{
                Pathing.move();
            }
        }else{
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
        Comms.addIslands(islands);
        //Comms.dumpQueue();
    }
    private static int carrying(){
        return rc.getResourceAmount(ResourceType.ADAMANTIUM) + rc.getResourceAmount(ResourceType.ELIXIR) + rc.getResourceAmount(ResourceType.MANA);
    }
    private static ResourceType nonEmptyResource(){
        if (rc.getResourceAmount(ResourceType.ADAMANTIUM) != 0) return ResourceType.ADAMANTIUM;
        if (rc.getResourceAmount(ResourceType.MANA) != 0) return ResourceType.MANA;
        return ResourceType.ELIXIR;
    }

    static void actIslands(int[] islands) throws GameActionException{
        for(int i : islands){
            if(Islands[i] == null)
                Islands[i] = new IslandInfo(rc.senseNearbyIslandLocations(i)[0], rc.senseAnchorPlantedHealth(i), turnCount, rc.senseTeamOccupyingIsland(i));
            else
                Islands[i].set(rc.senseTeamOccupyingIsland(i), rc.senseAnchorPlantedHealth(i), turnCount);
        }
    }
}
