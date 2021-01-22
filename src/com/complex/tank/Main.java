package com.complex.tank;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        TankFrame tf = new TankFrame();
        //初始化敌方tank
        int initTankCount = PropertyMgr.getInt("initTankCount");
        for (int i = 0; i < initTankCount; i++) {
            tf.tanks.add(new Tank(50 + i * 150, 200, Dir.DOWN, tf));
        }

        while (true) {
            Thread.sleep(50);
            tf.repaint();
        }

    }
}
