package excsi.rekindledarcane.common.network.client;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;

public class ClientPacketKeyPress implements IMessage, IMessageHandler<ClientPacketKeyPress,IMessage> {

    public short keyType;

    public ClientPacketKeyPress() {}

    public ClientPacketKeyPress(int keyType) {
        this.keyType = (short) keyType;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        keyType = buf.readShort();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeShort(keyType);
    }

    @Override
    public IMessage onMessage(ClientPacketKeyPress message, MessageContext ctx) {
        return null;
    }
}
