package test;

import com.complex.tank.Dir;
import com.complex.tank.Group;
import com.complex.tank.net.MsgDecoder;
import com.complex.tank.net.MsgEncoder;
import com.complex.tank.net.MsgType;
import com.complex.tank.net.TankJoinMsg;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class TankJoinMsgCodecTest {
    @Test
    public void testEncoder(){
        EmbeddedChannel ch = new EmbeddedChannel();
        UUID id = UUID.randomUUID();
        TankJoinMsg msg = new TankJoinMsg(5,10, Dir.DOWN,true, Group.BLUE,id);
        ch.pipeline().addLast(new MsgEncoder());
        ch.writeOutbound(msg);

        ByteBuf buf = (ByteBuf) ch.readOutbound();
        MsgType msgType = MsgType.values()[buf.readInt()];
        assertEquals(MsgType.TankJoin,msgType);

        int length = buf.readInt();
        assertEquals(33,length);

        int x = buf.readInt();
        int y = buf.readInt();
        int dirOrdinal = buf.readInt();
        Dir dir = Dir.values()[dirOrdinal];
        boolean moving = buf.readBoolean();
        int groupOrdinal = buf.readInt();
        Group g = Group.values()[groupOrdinal];
        UUID uuid = new UUID(buf.readLong(),buf.readLong());

        assertEquals(5,x);
        assertEquals(10,y);
        assertEquals(Dir.DOWN,dir);
        assertEquals(true,moving);
        assertEquals(Group.BLUE,g);
        assertEquals(id,uuid);

    }

    @Test
    public void teatDecoder(){
        EmbeddedChannel ch = new EmbeddedChannel();
        UUID id = UUID.randomUUID();
        TankJoinMsg msg = new TankJoinMsg(5,10, Dir.DOWN,true, Group.BLUE,id);
        ch.pipeline().addLast(new MsgDecoder());

        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(MsgType.TankJoin.ordinal());
        byte[] bytes = msg.toBytes();
        buf.writeInt(bytes.length);
        buf.writeBytes(bytes);
        ch.writeInbound(buf.duplicate());
        TankJoinMsg msgR = (TankJoinMsg) ch.readInbound();

        assertEquals(5,msgR.x);
        assertEquals(10,msgR.y);
        assertEquals(Dir.DOWN,msgR.dir);
        assertEquals(true,msgR.moving);
        assertEquals(Group.BLUE,msgR.group);
        assertEquals(id,msgR.id);
    }

}
