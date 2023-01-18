package matfisplayer1;

import battlecode.common.*;

import java.nio.file.Path;
import java.util.Map;

public class Launcher {
    static void newLauncher(RobotController rc) throws GameActionException{

    }
    static void runLauncher(RobotController rc) throws GameActionException {
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
        if(dir == null) Pathfinding.moveRandom(rc);
        if (rc.canMove(dir)) {
            rc.move(dir);
        }
    }
}
