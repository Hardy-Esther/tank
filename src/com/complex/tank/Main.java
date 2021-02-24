package com.complex.tank;

import com.complex.tank.net.Client;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        TankFrame tf = TankFrame.INSTANCE;
        tf.setVisible(true);

        new Thread(()->new Audio("audio/war1.wav").loop()).start();
        new Thread(()-> {
            while(true) {
                try {
                    Thread.sleep(25);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                tf.repaint();
            }
        }).start();

        Client.INSTANCE.connect();


    }
}
