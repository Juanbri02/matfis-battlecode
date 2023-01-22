package matfisplayer1;

import battlecode.common.*;

import java.util.Queue;

public class Comms {
    private static RobotController rc;
    private static Queue<CommsInfo> commsQueue;
    final static int MAP_LOC_BITS = 12;
    final static int OWNER_BITS = 1;
    final static int ISLAND_LIFE_BITS = 3;
    static void setRC(RobotController robc){
        rc = robc;
    }
    static void dumpQueue() throws GameActionException{
        while (Clock.getBytecodesLeft() > 350 && !commsQueue.isEmpty() && rc.canWriteSharedArray(0,0)){
            switch (commsQueue.element().getType()){
                case ENEMY:
                    addEnemy(commsQueue.element().getLoc());
                    break;
                case ISLAND:
                    addIsland(commsQueue.element().getLoc(), commsQueue.element().getLife());
                    break;
                case OBSTACLE:
                    addObstacle(commsQueue.element().getLoc());
                    break;
            }
            commsQueue.remove();
        }
    }
    static void addHeadquarter() throws GameActionException {
        MapLocation me = rc.getLocation();
        for (int i = 0; i<GameConstants.MAX_STARTING_HEADQUARTERS; ++i){
            if(rc.readSharedArray(i)==0){
                rc.writeSharedArray(i, locationToInt(me));
                break;
            }
        }
    }
    static void addEnemy(MapLocation loc){

    }
    static void addIsland(MapLocation loc, int life){
    }
    static void addObstacle(MapLocation loc){

    }
    static MapLocation[] updateHeadquarterInfo() throws GameActionException{
        MapLocation[] HQs = new MapLocation[GameConstants.MAX_STARTING_HEADQUARTERS];
        for (int i = 0; i<GameConstants.MAX_STARTING_HEADQUARTERS; ++i){
            HQs[i]=(intToLocation(rc.readSharedArray(i)));
            if(rc.readSharedArray(i)==0){
                return HQs;
            }
        }
        return HQs;
    }
    private static MapLocation intToLocation(int m){
        if (m==0){
            return null;
        }
        m=m-1;
        return new MapLocation(m%rc.getMapWidth(), m/rc.getMapWidth());
    }
    private static int locationToInt(MapLocation m){
        if (m==null){
            return 0;
        }
        return 1+m.x+rc.getMapWidth()*m.y;
    }
}
