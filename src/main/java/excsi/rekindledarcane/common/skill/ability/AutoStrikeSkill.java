package excsi.rekindledarcane.common.skill.ability;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import excsi.rekindledarcane.common.data.skill.ToggleAndCooldownData;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

public class AutoStrikeSkill extends InstantAbilitySkillBase<ToggleAndCooldownData> {

    public AutoStrikeSkill(String nameID) {
        super(nameID);
        FMLCommonHandler.instance().bus().register(this);
    }

    @Override
    public boolean canUse(EntityPlayer player) {
        if(!hasThisSkill(player))
            return false;
        ToggleAndCooldownData data = getSkillData(player);
        return data.getSkillCooldown() == 0;
    }

    @Override
    public void resolveSkillCast(EntityPlayer player) {
        ToggleAndCooldownData data = getSkillData(player);
        data.setToggled(!data.isToggled());
    }

    @Override
    public ToggleAndCooldownData createDefaultDataInstance() {
        return new ToggleAndCooldownData(getRegistryName(), 1);
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onTick(TickEvent.PlayerTickEvent event) {
        if(event.phase == TickEvent.Phase.START)
            return;
        EntityPlayer player = event.player;
        Minecraft minecraft = Minecraft.getMinecraft();
        if(player.ticksExisted % 5 != 0 || !hasThisSkill(player))
            return;
        if(!minecraft.gameSettings.keyBindAttack.getIsKeyPressed())
            return;
        if(getSkillData(player).isToggled()) {
            minecraft.func_147116_af();
        }
    }
}
