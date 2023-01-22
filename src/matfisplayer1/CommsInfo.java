package matfisplayer1;

import battlecode.common.MapLocation;

public class CommsInfo {
    final private MapLocation loc;
    final private CommsType type;
    private int life;
    public CommsType getType() {
        return type;
    }

    public int getLife() {
        return life;
    }

    public MapLocation getLoc() {
        return loc;
    }
    public CommsInfo(MapLocation loc, CommsType type, int life){
        this.loc = loc;
        this.type = type;
        this.life = life;
    }
    public CommsInfo(MapLocation loc, CommsType type){
        this.loc = loc;
        this.type = type;
    }
}
