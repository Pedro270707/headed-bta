package net.pedroricardo.headed.block.model;

import net.minecraft.src.ModelBase;
import net.minecraft.src.ModelRenderer;

public class HeadedSkullModel extends ModelBase {
    public ModelRenderer skull = new ModelRenderer(0, 0);

    public HeadedSkullModel() {
        this.skull.addBox(-4.0F, 4.0F, -4.0F, 8, 8, 8, 0.0F);
    }

    public void render() {
        this.skull.render(0.0625F);
    }
}
