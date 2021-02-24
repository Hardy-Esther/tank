package com.complex.tank.net;

import com.complex.tank.*;

import java.io.*;
import java.util.UUID;

public class BulletNewMsg extends Msg{

    public int x,y;
    public Dir dir;
    public Group group;
    public UUID id;
    public UUID playerID;

    public BulletNewMsg(){}

    public BulletNewMsg(Bullet bullet) {
        this.playerID = bullet.getPlayerId();
        this.id = bullet.getId();
        this.x = bullet.getX();
        this.y = bullet.getY();
        this.dir = bullet.getDir();
        this.group = bullet.getGroup();
    }


    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(this.getClass().getName())
                .append("[")
                .append("uuid="+id+" | ")
                .append("x="+x+" | ")
                .append("y="+y+" | ")
                .append("dir="+dir+" | ")
                .append("group="+group+" | ")
                .append("]");
        return builder.toString();
    }

    @Override
    public void handle() {
        if (this.playerID.equals(TankFrame.INSTANCE.getMainTank().getId()))
            return;

        Bullet bullet = new Bullet(this.playerID, x, y, dir, group, TankFrame.INSTANCE);
        bullet.setId(this.id);
        TankFrame.INSTANCE.addBullet(bullet);

    }

    @Override
    public byte[] toBytes() {
        ByteArrayOutputStream baos = null;
        DataOutputStream dos = null;
        byte[] bytes = null;
        try{
            baos = new ByteArrayOutputStream();
            dos = new DataOutputStream(baos);

            dos.writeInt(x);
            dos.writeInt(y);
            dos.writeInt(dir.ordinal());
            dos.writeInt(group.ordinal());
            dos.writeLong(id.getMostSignificantBits());
            dos.writeLong(id.getLeastSignificantBits());
            dos.writeLong(playerID.getMostSignificantBits());
            dos.writeLong(playerID.getLeastSignificantBits());
            dos.flush();
            bytes = baos.toByteArray();

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try{
                if (baos != null){
                    baos.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
            try{
                if (dos != null){
                    dos.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        return bytes;
    }

    @Override
    public void parse(byte[] bytes) {
        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(bytes));
        try{
            this.x = dis.readInt();
            this.y = dis.readInt();
            this.dir = Dir.values()[dis.readInt()];
            this.group = Group.values()[dis.readInt()];
            this.id = new UUID(dis.readLong(),dis.readLong());
            this.playerID = new UUID(dis.readLong(),dis.readLong());

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try{
                dis.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public MsgType getMsgType() {
        return MsgType.BulletNew;
    }
}
