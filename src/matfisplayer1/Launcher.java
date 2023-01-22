package matfisplayer1;

import battlecode.common.*;
import scala.Int;

import java.util.Map;

public class Launcher{
    static RobotController rc;
    static MapLocation hqLocation;
    static int rRandom;
    static void newLauncher(RobotController robc) throws GameActionException{
        rc = robc;
        Pathing.set(rc, RobotPlayer.rng.nextBoolean());
        hqLocation = Pathing.findHqLocation();
    }
    static void runLauncher() throws GameActionException {
        Team opponent = rc.getTeam().opponent();
        RobotInfo[] enemies = rc.senseNearbyRobots(-1, opponent);
        RobotInfo[] allies = rc.senseNearbyRobots(-1, rc.getTeam());
        MapLocation toAttack = getTarget(enemies);
        if (toAttack != null && rc.canAttack(toAttack)) {
            rc.attack(toAttack);
        }
        MapLocation toMove = moveGroup(allies);
        if(toMove == null) {
            toMove = moveEnemy(enemies);
            rc.setIndicatorString("move enemy " + toMove);
        } else{
            rc.setIndicatorString("Move towards ally" + toMove);
        }
        if(toMove == null) {
            if(Pathing.objective == null || rRandom == 15) {
                Pathing.setRandomObjective();
                rRandom = 0;
            }
            Pathing.move();
            rRandom++;
        } else {
            Pathing.moveTowards(toMove);
            Pathing.setObjective(null);
        }
    }
    static MapLocation getTarget(RobotInfo[] enemies) throws GameActionException{
        if(enemies.length == 0) return null;
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
    static MapLocation moveGroup(RobotInfo[] allies) throws GameActionException{
        int minID = rc.getID();
        MapLocation loc = null;
        for(RobotInfo r : allies) if(r.type == RobotType.LAUNCHER && r.ID < minID-1){
            minID = r.ID;
            loc = r.getLocation();
        }
        return loc;
    }
    static MapLocation moveEnemy(RobotInfo[] enemies) throws GameActionException{
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
