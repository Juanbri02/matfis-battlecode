package matfisplayer1;

import battlecode.common.*;

public class Launcher{
    static RobotController rc;
    static MapLocation hqLocation;
    static int allyLaunchers;
    static boolean waiting = true;
    static void newLauncher(RobotController robc) throws GameActionException{
        rc = robc;
        Pathing.set(rc, RobotPlayer.rng.nextBoolean());
        hqLocation = Pathing.findHqLocation();
        if(rc.getMapWidth() == rc.getMapHeight()){
            Pathing.setObjective(new MapLocation(rc.getMapHeight() - hqLocation.x,rc.getMapWidth() - hqLocation.y));
        } else if (rc.getMapWidth() < rc.getMapHeight()) {
            Pathing.setObjective(new MapLocation(rc.getMapHeight() - hqLocation.x,hqLocation.y));
        } else{
            Pathing.setObjective(new MapLocation(rc.getMapHeight(),rc.getMapWidth() - hqLocation.y));
        }
    }
    static void runLauncher() throws GameActionException {
        Team opponent = rc.getTeam().opponent();
        RobotInfo[] enemies = rc.senseNearbyRobots(-1, opponent);
        RobotInfo[] allies = rc.senseNearbyRobots(-1, rc.getTeam());
        MapLocation toAttack = getTarget(enemies);
        if (toAttack != null && rc.canAttack(toAttack)) {
            rc.attack(toAttack);
        }
        if(waiting) allyLaunchers = 0;
        MapLocation toMove = moveGroup(allies);
        if(waiting && allyLaunchers < 3){
            Pathing.moveRandom();
            return;
        }
        waiting = false;
        if(toMove == null) {
            toMove = moveEnemy(enemies);
            rc.setIndicatorString("move enemy " + toMove);
        } else{
            rc.setIndicatorString("Move towards ally" + toMove);
        }
        if(toMove == null) {
            Pathing.move();
        } else {
            Pathing.moveTowards(toMove);
        }
    }
    static MapLocation getTarget(RobotInfo[] enemies){
        if(enemies.length == 0)
            return null;
        MapLocation loc = null;
        int minLife = Integer.MAX_VALUE;
        int maxID = 0;
        for(RobotInfo r : enemies){
            if(r.type != RobotType.HEADQUARTERS && (r.health < minLife || (r.health == minLife && r.ID < maxID))){
                maxID = r.ID;
                minLife = r.health;
                loc = r.location;
            }
        }
        return loc;
    }
    static MapLocation moveGroup(RobotInfo[] allies){
        int minID = rc.getID();
        MapLocation loc = null;
        for(RobotInfo r : allies) if(r.type == RobotType.LAUNCHER) {

            if(r.ID < minID-1){
                minID = r.ID;
                loc = r.getLocation();
            }
        }
        return loc;
    }
    static MapLocation moveEnemy(RobotInfo[] enemies){
        if(enemies.length == 0) return null;
        MapLocation loc = null;
        MapLocation HQloc = null;
        int minLife = Integer.MAX_VALUE;
        int maxID = 0;
        for(RobotInfo r : enemies) if(r.type == RobotType.HEADQUARTERS) HQloc = r.location;
        for(RobotInfo r : enemies){
            if(r.health < minLife || (r.health == minLife && r.ID > maxID)) {
                MapLocation posObj = rc.getLocation().add(rc.getLocation().directionTo(r.location));
                if (HQloc == null || HQloc.isWithinDistanceSquared(posObj,RobotType.LAUNCHER.actionRadiusSquared))
                    if(HQloc == null || !HQloc.isWithinDistanceSquared(posObj, RobotType.HEADQUARTERS.actionRadiusSquared)){
                        maxID = r.ID;
                        minLife = r.health;
                        loc = posObj;
                    }
            }
        }
        return loc;
    }
}
