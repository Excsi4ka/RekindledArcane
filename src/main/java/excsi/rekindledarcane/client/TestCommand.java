package excsi.rekindledarcane.client;

import excsi.rekindledarcane.api.RekindledArcaneAPI;
import excsi.rekindledarcane.common.registry.SkillSystemRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;

public class TestCommand extends CommandBase {

    public static boolean display = false;

    public static long time;

    @Override
    public String getCommandName() {
        return "ra";
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender p_71519_1_) {
        return true;
    }

    @Override
    public String getCommandUsage(ICommandSender p_71518_1_) {
        return "ra help";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 1;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        if(args.length > 0 && args[0].equals("display")) {
            display = true;
            time = Minecraft.getMinecraft().thePlayer.ticksExisted + 10;
        }
        if(args.length > 0 && args[0].equals("reg")) {
            RekindledArcaneAPI.getAllCategories().clear();
            SkillSystemRegistry.register();
        }
    }
}
