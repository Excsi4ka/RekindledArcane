package excsi.rekindledarcane.common.data.player;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

import java.util.HashMap;
import java.util.UUID;

public class PlayerDataManager {

    private static final HashMap<UUID, PlayerData> playerData = new HashMap<>();

    public static PlayerData getPlayerData(EntityPlayer player) {
        return playerData.get(player.getUniqueID());
    }

    public static void readDataForPlayer(EntityPlayer player, NBTTagCompound tagCompound) {
        PlayerData data = new PlayerData();
        playerData.put(player.getUniqueID(), data);
        data.readData(tagCompound, player);
    }

    public static void writeDataForPlayer(EntityPlayer player, NBTTagCompound tagCompound) {
        PlayerData data = playerData.get(player.getUniqueID());
        data.writeData(tagCompound, player);
    }

    public static PlayerData setPlayerDataDefault(EntityPlayer player) {
        PlayerData data = new PlayerData();
        playerData.put(player.getUniqueID(), data);
        return data;
    }
}
