package excsi.rekindledarcane.common.skill.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import excsi.rekindledarcane.api.skill.IToggleSwitch;
import excsi.rekindledarcane.api.skill.templates.EventWithDataSkillBase;
import excsi.rekindledarcane.common.data.skill.ToggleData;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

public class AutoStrikeSkill extends EventWithDataSkillBase<ToggleData> implements IToggleSwitch {

    public AutoStrikeSkill(String nameID) {
        super(nameID);
    }

    @Override
    public ToggleData createDefaultDataInstance() {
        return new ToggleData(getRegistryName(), false);
    }

    @Override
    public void toggle(EntityPlayer player, boolean toggled) {
        getSkillData(player).setToggled(toggled);
    }

    @Override
    public boolean isToggled(EntityPlayer player) {
        return getSkillData(player).isToggled();
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.START || event.side == Side.SERVER)
            return;
        EntityPlayer player = event.player;
        Minecraft minecraft = Minecraft.getMinecraft();
        if (!hasThisSkill(player))
            return;
        if (player.ticksExisted % 5 != 0)
            return;
        if (!minecraft.gameSettings.keyBindAttack.getIsKeyPressed())
            return;
        if (isToggled(player)) {
            minecraft.func_147116_af();
        }
    }
}
