package matfisplayer1;

import battlecode.common.*;
import scala.Int;

import java.util.Map;

public class Launcher {
    static RobotController rc;
    static void newLauncher(RobotController robc) throws GameActionException{
        rc = robc;
        Pathing.set(rc, RobotPlayer.rng.nextBoolean());
    }
    static void runLauncher() throws GameActionException {
        Team opponent = rc.getTeam().opponent();
        RobotInfo[] enemies = rc.senseNearbyRobots(-1, opponent);
        MapLocation toAttack = getTarget(enemies);
        if (rc.canAttack(toAttack)) {
            rc.attack(toAttack);
        }
        Direction dir = moveDirection(enemies);
        rc.setIndicatorString("Moviendo a " + dir);
        System.out.println("I'm a " + rc.getType() + " and I just got created! I have health " + rc.getHealth());
        if (rc.canMove(dir)) {
            rc.move(dir);
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
    static Direction moveDirection(RobotInfo[] enemies) throws GameActionException{
        Direction dir = RobotPlayer.directions[RobotPlayer.rng.nextInt(8)];
        if(enemies.length == 0) return dir;
        MapLocation HQloc = null;
        int minLife = Integer.MAX_VALUE;
        int maxID = 0;
        for(RobotInfo r : enemies) if(r.type == RobotType.HEADQUARTERS) HQloc = r.location;
        for(RobotInfo r : enemies){
            if(r.health < minLife || (r.health == minLife && r.ID < maxID)) {
                Direction posDir = rc.getLocation().directionTo(r.location);
                if (HQloc == null || HQloc.isWithinDistanceSquared(rc.getLocation().add(posDir),RobotType.LAUNCHER.actionRadiusSquared)) {
                    maxID = r.ID;
                    minLife = r.health;
                    dir = posDir;
                }
            }
        }
        return dir;
    }
}
