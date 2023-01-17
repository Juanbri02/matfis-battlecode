package matfisplayer1;

import battlecode.common.*;

public class Launcher {
    static void runLauncher(RobotController rc) throws GameActionException {
        int radius = rc.getType().actionRadiusSquared;
        Team opponent = rc.getTeam().opponent();
        RobotInfo[] enemies = rc.senseNearbyRobots(radius, opponent);
        if (enemies.length > 0) {
            MapLocation toAttack = enemies[0].location;

            if (rc.canAttack(toAttack)) {
                rc.setIndicatorString("Attacking");
                rc.attack(toAttack);
            }
        }

        Direction dir = null;
        for(RobotInfo enemy : enemies) if(enemy.getType() != RobotType.HEADQUARTERS){
            dir = rc.getLocation().directionTo(enemy.location);
            break;
        }
        if(dir == null) dir = RobotPlayer.directions[RobotPlayer.rng.nextInt(8)];
        if (rc.canMove(dir)) {
            rc.move(dir);
        }
    }
}
