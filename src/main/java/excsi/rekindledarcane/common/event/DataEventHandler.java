package excsi.rekindledarcane.common.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import excsi.rekindledarcane.RekindledArcane;
import excsi.rekindledarcane.common.data.player.PlayerDataManager;
import excsi.rekindledarcane.common.network.PacketManager;
import excsi.rekindledarcane.common.network.server.ServerPacketFullPlayerDataSync;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.entity.player.PlayerEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

//todo add data backup
public class DataEventHandler {

    @SubscribeEvent
    public void playerDataRead(PlayerEvent.LoadFromFile event) {
        File file = event.getPlayerFile("arcane");
        EntityPlayer player = event.entityPlayer;
        if (file.exists()) {
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                NBTTagCompound data = CompressedStreamTools.readCompressed(fileInputStream);
                fileInputStream.close();
                PlayerDataManager.readDataForPlayer(player, data);
            } catch (Exception e) {
                RekindledArcane.LOG.error(e.getMessage() + " Couldn't load " + player.getCommandSenderName() + "'s player data");
                e.printStackTrace();
                RekindledArcane.LOG.info("Set player data to default values");
                PlayerDataManager.setPlayerDataDefault(player);
            }
        } else {
            PlayerDataManager.setPlayerDataDefault(player);
        }
    }

    @SubscribeEvent
    public void playerDataWrite(PlayerEvent.SaveToFile event) {
        File file = event.getPlayerFile("arcane");
        EntityPlayer player = event.entityPlayer;
        try {
            if (file.exists() || file.createNewFile()) {
                NBTTagCompound tagCompound = new NBTTagCompound();
                PlayerDataManager.writeDataForPlayer(player, tagCompound);
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                CompressedStreamTools.writeCompressed(tagCompound, fileOutputStream);
                fileOutputStream.close();
            }
        } catch (Exception e) {
            RekindledArcane.LOG.error(e.getMessage() + " Couldn't save " + player.getCommandSenderName() + "'s player data");
            e.printStackTrace();
        }
    }

    @SubscribeEvent
    public void onPlayerLogin(PlayerLoggedInEvent event) {
        if (event.player.worldObj.isRemote)
            return;
        EntityPlayer player = event.player;
        NBTTagCompound data = new NBTTagCompound();
        PlayerDataManager.getPlayerData(player).writeData(data, player);
        ServerPacketFullPlayerDataSync packet = new ServerPacketFullPlayerDataSync(data);
        PacketManager.sendToPlayer(packet, player);
    }
}
