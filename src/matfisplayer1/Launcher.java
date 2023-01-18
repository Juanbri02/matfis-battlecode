package matfisplayer1;

import battlecode.common.*;

public class Launcher {
    static RobotController rc;
    static void newLauncher(RobotController robc) throws GameActionException{
        rc = robc;
        Pathing.set(rc, RobotPlayer.rng.nextBoolean());
    }
    static void runLauncher() throws GameActionException {
        int radius = rc.getType().actionRadiusSquared;
        Team opponent = rc.getTeam().opponent();
        RobotInfo[] enemies = rc.senseNearbyRobots(radius, opponent);

        Direction dir = null;
        for(RobotInfo enemy : enemies) if(enemy.getType() != RobotType.HEADQUARTERS){
            dir = rc.getLocation().directionTo(enemy.location);
            MapLocation toAttack = enemies[0].location;
            if (rc.canAttack(toAttack)) {
                rc.attack(toAttack);
            }
            break;
        }
        if(dir == null) Pathing.moveRandom();
        if (rc.canMove(dir)) {
            rc.move(dir);
        }
    }
}
