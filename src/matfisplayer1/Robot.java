package matfisplayer1;

import battlecode.common.Direction;
import battlecode.common.GameConstants;
import battlecode.common.MapLocation;
import battlecode.common.RobotController;

import java.util.Random;

public class Robot{
    static protected RobotController rc;
    static protected MapLocation[] HQs;
    static protected IslandInfo[] Islands = new IslandInfo[GameConstants.MAX_NUMBER_ISLANDS];
    static protected int turnCount = 0;
    protected static final Direction[] directions = {
            Direction.NORTH,
            Direction.NORTHEAST,
            Direction.EAST,
            Direction.SOUTHEAST,
            Direction.SOUTH,
            Direction.SOUTHWEST,
            Direction.WEST,
            Direction.NORTHWEST,
    };
    protected static final Random rng = new Random(3141592);
}
