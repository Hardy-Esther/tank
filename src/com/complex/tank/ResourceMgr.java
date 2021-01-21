package com.complex.tank;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ResourceMgr {
    public static BufferedImage redTankL,redTankR,redTankU,redTankD;
    public static BufferedImage blueTankL,blueTankR,blueTankU,blueTankD;
    public static BufferedImage bulletL,bulletR,bulletU,bulletD;
    public static BufferedImage[] explodes = new BufferedImage[16];
    static {
        try {
            redTankU = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/BadTank1.png"));
            redTankL = ImageUtil.rotateImage(redTankU,-90);
            redTankR = ImageUtil.rotateImage(redTankU,90);
            redTankD = ImageUtil.rotateImage(redTankU,180);

            blueTankU = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/GoodTank1.png"));
            blueTankL = ImageUtil.rotateImage(blueTankU,-90);
            blueTankR = ImageUtil.rotateImage(blueTankU,90);
            blueTankD = ImageUtil.rotateImage(blueTankU,180);

            bulletU = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/bulletU.png"));
            bulletL = ImageUtil.rotateImage(bulletU,-90);
            bulletR = ImageUtil.rotateImage(bulletU,90);
            bulletD = ImageUtil.rotateImage(bulletU,180);

            for (int i = 0; i < 16; i++) {
                explodes[i] = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/e"+(i+1)+".gif"));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
