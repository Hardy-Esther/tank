package com.complex.tank.net;

import com.complex.tank.Dir;
import com.complex.tank.Tank;
import com.complex.tank.TankFrame;

import java.io.*;
import java.util.UUID;

public class TankStopMsg extends Msg {

    UUID id;
    int x, y;

    public TankStopMsg() {
    }

    public TankStopMsg(UUID id, int x, int y){
        this.id = id;
        this.x = x;
        this.y = y;
    }

    public TankStopMsg(Tank tank){
        this.id = tank.getId();
        this.x = tank.getX();
        this.y = tank.getY();
    }

    @Override
    public void handle() {
        if (this.id.equals(TankFrame.INSTANCE.getMainTank().getId())) return;
        Tank t = TankFrame.INSTANCE.findTankByUUID(this.id);
        if (t != null) {
            t.setMoving(false);
            t.setX(this.x);
            t.setY(this.y);
        }
    }

    @Override
    public byte[] toBytes() {

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
    public void parse(byte[] bytes) {
        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(bytes));
        try{
            this.id = new UUID(dis.readLong(),dis.readLong());
            this.x = dis.readInt();
            this.y = dis.readInt();
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
    public MsgType getMsgType() {
        return MsgType.TankStop;
    }

    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder();
        builder.append(this.getClass().getName())
                .append("[")
                .append("uuid="+id+" | ")
                .append("x="+x+" | ")
                .append("y="+y+" | ")
                .append("]");
        return builder.toString();
    }
}
