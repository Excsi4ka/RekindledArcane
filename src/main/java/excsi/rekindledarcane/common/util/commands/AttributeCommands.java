package excsi.rekindledarcane.common.util.commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;

public class AttributeCommands extends CommandBase {

    @Override
    public String getCommandName() {
        return "playerattribute";
    }

    @Override
    public String getCommandUsage(ICommandSender p_71518_1_) {
        return "/playerattribute <apply|remove>,<player>,<attribute name>,<>,(Persistent through death?)<true|false>";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        if(args == null || args.length < 3) {
            throw new CommandException(getCommandUsage(sender));
        }
        String firstArg = args[0];
        EntityPlayer player = getPlayer(sender,args[1]);
        if(player == null)
            throw new CommandException("Unknown player");
//        if("apply".equals(firstArg)) {
//
//        }
    }

    public AttributeModifier parseAttributeFromString(String str) {


        return null;
    }
}
