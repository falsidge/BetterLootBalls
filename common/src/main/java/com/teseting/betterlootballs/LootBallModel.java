package com.teseting.betterlootballs;

import com.cobblemon.mod.common.client.render.models.blockbench.PosableModel;
import com.cobblemon.mod.common.client.render.models.blockbench.frame.PokeBallFrame;
import net.minecraft.client.model.geom.ModelPart;
import org.jetbrains.annotations.NotNull;

public class LootBallModel extends PosableModel implements PokeBallFrame {
    public ModelPart rootPart;
    public ModelPart base;
    public ModelPart lid;
    public LootBallModel(ModelPart root ) {
        super(root);
        rootPart = this.registerChildWithAllChildren(root, "poke_ball");
        base = this.getPart("bottom");
        lid = this.getPart("top");
//        base = getPart("")
    }

    @Override
    public @NotNull ModelPart getBase() {
        return base;
    }

    @Override
    public @NotNull ModelPart getLid() {
        return lid;
    }
}
