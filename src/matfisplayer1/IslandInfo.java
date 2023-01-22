package matfisplayer1;

import battlecode.common.MapLocation;

public class IslandInfo {
    final private MapLocation loc;
    private int life;
    public int lastAct;
    public IslandInfo(MapLocation loc, int life, int lastAct){
        this.loc = loc;
        this.life = life;
        this.lastAct = lastAct;
    }

    public void setLife(int life, int lastAct) {
        this.life = life;
        this.lastAct = lastAct;
    }

    public MapLocation getLoc() {
        return loc;
    }

    public int getLastAct() {
        return lastAct;
    }

    public int getLife() {
        return life;
    }
}
