package net.pedroricardo.headed.mixin;

import net.minecraft.src.Render;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Render.class)
public interface LoadTextureInvoker {
    @Invoker(value = "loadTexture", remap = false)
    public void setLoadTexture(String s);
}
