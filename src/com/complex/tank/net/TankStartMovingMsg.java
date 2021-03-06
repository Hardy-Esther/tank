package com.complex.tank.net;

import com.complex.tank.Dir;
import com.complex.tank.Tank;
import com.complex.tank.TankFrame;

import java.io.*;
import java.util.UUID;

public class TankStartMovingMsg extends Msg {
    UUID id;
    int x, y;
    Dir dir;

    public TankStartMovingMsg() {
    }

    public TankStartMovingMsg(UUID id, int x, int y, Dir dir) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.dir = dir;
    }

    public TankStartMovingMsg(Tank tank) {
        this.id = tank.getId();
        this.x = tank.getX();
        this.y = tank.getY();
        this.dir = tank.getDir();
    }

    public UUID getId() {
        return id;
    }

    public Dir getDir() {
        return dir;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public MsgType getMsgType() {
        return MsgType.TankStartMoving;
    }

    @Override
    public void handle() {
        if (this.id.equals(TankFrame.INSTANCE.getMainTank().getId())) return;
        Tank t = TankFrame.INSTANCE.findTankByUUID(this.id);
        if (t != null) {
            t.setMoving(true);
            t.setX(this.x);
            t.setY(this.y);
            t.setDir(dir);
        }
    }

    @Override
    public void parse(byte[] bytes) {
        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(bytes));
        try{
            this.id = new UUID(dis.readLong(),dis.readLong());
            this.x = dis.readInt();
            this.y = dis.readInt();
            this.dir = Dir.values()[dis.readInt()];
        }catch (IOException e){
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
    public byte[] toBytes(){
        ByteArrayOutputStream baos = null;
        DataOutputStream dos = null;
        byte[] bytes = null;
        try{
            baos = new ByteArrayOutputStream();
            dos = new DataOutputStream(baos);
            dos.writeLong(id.getMostSignificantBits());
            dos.writeLong(id.getLeastSignificantBits());
            dos.writeInt(x);
            dos.writeInt(y);
            dos.writeInt(dir.ordinal());
            dos.flush();
            bytes = baos.toByteArray();
        }catch (Exception e){
            e.printStackTrace();
        } finally {
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
    public String toString(){
        StringBuilder builder = new StringBuilder();
        builder.append(this.getClass().getName())
                .append("[")
                .append("uuid="+id+" | ")
                .append("x="+x+" | ")
                .append("y="+y+" | ")
                .append("dir="+dir+" | ")
                .append("]");
        return builder.toString();
    }

}
