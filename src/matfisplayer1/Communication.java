package matfisplayer1;

import battlecode.common.GameActionException;
import battlecode.common.GameConstants;
import battlecode.common.MapLocation;
import battlecode.common.RobotController;

public class Communication {//códigos copiados, funciones que combierten localizaciones en enteros y viceversa.
    private static int locationToInt(RobotController rc, MapLocation m){
        if (m==null){
            return 0;
        }
        return 1+m.x+rc.getMapWidth()*m.y;
    }
    private static MapLocation intToLocation(RobotController rc, int m){
        if (m==0){
            return null;
        }
        m=m-1;
        return new MapLocation(m%rc.getMapWidth(), m/rc.getMapWidth());
    }
    //creo que esto es para guardar la localizacion de los headquarters, lo que no se es donde se guarda
    private static MapLocation[] headquartersloc = new MapLocation[GameConstants.MAX_STARTING_HEADQUARTERS];
    static void addHeadquarter(RobotController rc) throws GameActionException {
        MapLocation me = rc.getLocation();
        for (int i = 0; i<GameConstants.MAX_STARTING_HEADQUARTERS; ++i){
            if(rc.readSharedArray(i)==0){
                rc.writeSharedArray(i, locationToInt(rc, me));
                break;
            }
        }
    }
    static void updateheadquarterinfo(RobotController rc) throws GameActionException{
        if(RobotPlayer.turnCount==2){
            for (int i = 0; i<GameConstants.MAX_STARTING_HEADQUARTERS; ++i){
                headquartersloc[i]=(intToLocation(rc, rc.readSharedArray(i)));
                if(rc.readSharedArray(i)==0){
                    break;
                }
            }
        }
    }
}
