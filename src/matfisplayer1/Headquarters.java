package matfisplayer1;

import battlecode.common.*;
/**
 * Run a single turn for a Headquarters.
 * This code is wrapped inside the infinite loop in run(), so it is called once per turn.
 */
public class Headquarters extends Robot{
    static int carrierProduced = 0;
    static int launcherProduced = 0;
    static boolean parity = true;
    static void newHeadquarters(RobotController robc) throws GameActionException {
        rc = robc;
        Comms.setRC(rc);
        Comms.addHeadquarter();
        Comms.dumpQueue();
    }
    static void runHeadquarters() throws GameActionException {
        turnCount++;
        parity = !parity;
        RobotInfo[] allies = rc.senseNearbyRobots(-1,rc.getTeam());
        rc.setIndicatorString(carrierProduced + "<carr - launc>" +launcherProduced);
        if(turnCount < 100){
            for(Direction dir : directions){
                if(parity && rc.canBuildRobot(RobotType.CARRIER, rc.getLocation().add(dir))) {
                    rc.buildRobot(RobotType.CARRIER, rc.getLocation().add(dir));
                    carrierProduced++;
                    parity = !parity;
                } else if(parity && rc.canBuildRobot(RobotType.LAUNCHER,rc.getLocation().add(dir))){
                    rc.buildRobot(RobotType.LAUNCHER,rc.getLocation().add(dir));
                    launcherProduced++;
                    parity = !parity;
                }
            }
        } else if(turnCount < 750){
            if(turnCount%8 == 0)
                rc.buildAnchor(Anchor.STANDARD);
            for(Direction dir : directions){
                if(parity && rc.canBuildRobot(RobotType.CARRIER, rc.getLocation().add(dir))) {
                    rc.buildRobot(RobotType.CARRIER, rc.getLocation().add(dir));
                    carrierProduced++;
                    parity = !parity;
                    return;
                }
                else if(parity && rc.canBuildRobot(RobotType.LAUNCHER,rc.getLocation().add(dir))){
                    rc.buildRobot(RobotType.LAUNCHER,rc.getLocation().add(dir));
                    launcherProduced++;
                    parity = !parity;
                    return;
                }
            }
        }else{
            if((turnCount%5 == 0 || allies.length > 20)&& rc.canBuildAnchor(Anchor.STANDARD)) {
                rc.buildAnchor(Anchor.STANDARD);
            }
            for(Direction dir : directions){
                if(parity && rc.getResourceAmount(ResourceType.ADAMANTIUM) > 150 && rc.canBuildRobot(RobotType.CARRIER, rc.getLocation().add(dir))) {
                    rc.buildRobot(RobotType.CARRIER, rc.getLocation().add(dir));
                    carrierProduced++;
                    return;
                }
                else if(parity && rc.getResourceAmount(ResourceType.MANA) > 160 && rc.canBuildRobot(RobotType.LAUNCHER,rc.getLocation().add(dir))){
                    rc.buildRobot(RobotType.LAUNCHER,rc.getLocation().add(dir));
                    launcherProduced++;
                    return;
                }
                parity = !parity;
            }
        }
    }
}
