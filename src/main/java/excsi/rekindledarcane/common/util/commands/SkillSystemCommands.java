package excsi.rekindledarcane.common.util.commands;

import excsi.rekindledarcane.api.RekindledArcaneAPI;
import excsi.rekindledarcane.api.skill.ISkill;
import excsi.rekindledarcane.api.skill.ISkillCategory;
import excsi.rekindledarcane.common.data.player.PlayerData;
import excsi.rekindledarcane.common.data.player.PlayerDataManager;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.PlayerNotFoundException;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;

import java.util.List;

public class SkillSystemCommands extends CommandBase {

    @Override
    public String getCommandName() {
        return "rekindledarcane";
    }

    @Override
    public String getCommandUsage(ICommandSender p_71518_1_) {
        return "/rekindledarcane <unlockSkill|forgetSkill> <player> <category> <skill>";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        if (args == null || args.length < 4)
            throw new WrongUsageException(getCommandUsage(sender));
        EntityPlayer player = getPlayer(sender, args[1]);
        if (player == null)
            throw new PlayerNotFoundException();
        ISkillCategory category = RekindledArcaneAPI.getCategory(args[2]);
        if (category == null)
            throw new WrongUsageException("No valid category found");
        ISkill skill = category.getSkillFromID(args[3]);
        if (skill == null)
            throw new WrongUsageException("No valid skill found");
        PlayerData data = PlayerDataManager.getPlayerData(player);
        if (args[0].equals("unlockSkill")) {
            data.unlockSkill(player, skill, true);
        } else if (args[0].equals("forgetSkill")) {
            data.forgetSkill(player, skill, true);
        }
    }

    @Override
    public List addTabCompletionOptions(ICommandSender sender, String[] args) {
        switch (args.length) {
            case 1: return getListOfStringsMatchingLastWord(args, "unlockSkill","forgetSkill");
            case 2: return getListOfStringsMatchingLastWord(args, getListOfPlayerUsernames());
            case 3: return getListOfStringsMatchingLastWord(args, RekindledArcaneAPI.getCategoryNames().toArray(new String[0]));
            case 4: {
                ISkillCategory category = RekindledArcaneAPI.getCategory(args[2]);
                if(category == null)
                    return null;
                return getListOfStringsMatchingLastWord(args, category.getAllSkillIDs().toArray(new String[0]));
            }
            default: return null;
        }
    }

    public String[] getListOfPlayerUsernames() {
        return MinecraftServer.getServer().getAllUsernames();
    }
}
