package matfisplayer1;

import battlecode.common.*;

public strictfp class RobotPlayer {
    public static void run(RobotController rc){
        try {
            switch (rc.getType()) {
                case HEADQUARTERS:  Headquarters.newHeadquarters(rc);   break;
                case CARRIER:       Carrier.newCarrier(rc);             break;
                case LAUNCHER:      Launcher.newLauncher(rc);           break;
                case BOOSTER:       Booster.newBooster(rc);             break;
                case DESTABILIZER:  Destabilizer.newDestabilizer(rc);   break;
                case AMPLIFIER:     Amplifier.newAmplifier(rc);         break;
            }
        } catch (GameActionException e) {
            System.out.println(rc.getType() + "Game Exception");
            e.printStackTrace();
        } catch (Exception e) {
            //Normal exception
            System.out.println(rc.getType() + "Java Exception");
            e.printStackTrace();
        }
        while (true) {
            try {
                switch (rc.getType()) {
                    case HEADQUARTERS:  Headquarters.runHeadquarters(); break;
                    case CARRIER:       Carrier.runCarrier();           break;
                    case LAUNCHER:      Launcher.runLauncher();         break;
                    case BOOSTER:       Booster.runBooster();           break;
                    case DESTABILIZER:  Destabilizer.runDestabilizer(); break;
                    case AMPLIFIER:     Amplifier.runAmplifier();       break;
                }
            } catch (GameActionException e) {
                System.out.println(rc.getType() + " Game Exception");
                e.printStackTrace();
                rc.setIndicatorString(rc.getType() + " Game Exception");
            } catch (Exception e) {
                System.out.println(rc.getType() + " Java Exception");
                e.printStackTrace();
                rc.setIndicatorString(rc.getType() + " Java Exception");
            } finally {
                Clock.yield();
            }
        }
    }
}
