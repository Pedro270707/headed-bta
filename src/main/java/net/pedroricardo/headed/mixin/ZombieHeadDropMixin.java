package net.pedroricardo.headed.mixin;

import net.minecraft.src.*;
import net.pedroricardo.headed.Headed;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = EntityZombie.class, remap = false)
public abstract class ZombieHeadDropMixin extends EntityLiving {

    public ZombieHeadDropMixin(World world) {
        super(world);
    }

    @Override
    public void onDeath(Entity entity) {
        if (entity instanceof EntityCreeper) {
            if (((EntityCreeper)entity).getPowered()) {
                entity.dropItem(Item.itemsList[Headed.IDs.ZOMBIE_HEAD].itemID, 1);
            }
        }
    }
}
