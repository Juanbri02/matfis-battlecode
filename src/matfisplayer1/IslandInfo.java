package matfisplayer1;

import battlecode.common.MapLocation;
import battlecode.common.Team;

public class IslandInfo {
    private MapLocation loc;
    private int life;
    private Team owner;
    public int lastAct;
    public IslandInfo(MapLocation loc, int life, int lastAct, Team team){
        this.loc = loc;
        this.life = life;
        this.lastAct = lastAct;
        this.owner = team;
    }
    public void set(Team owner, int life, int lastAct){
        this.life = life;
        this.lastAct = lastAct;
        this.owner = owner;
    }
    public void setLife(int life, int lastAct) {
        this.life = life;
        this.lastAct = lastAct;
    }
    public void setLoc(MapLocation loc) {
        this.loc = loc;
    }
    public MapLocation getLoc() {
        return loc;
    }

    public Team getOwner() {
        return owner;
    }

    public int getLastAct() {
        return lastAct;
    }

    public int getLife() {
        return life;
    }
}
