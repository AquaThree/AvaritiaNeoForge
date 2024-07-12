package net.byAqua3.avaritia.render.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.byAqua3.avaritia.model.ModelInfinityArmor;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RenderInfinityArmor<T extends LivingEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {
	private final ModelInfinityArmor model;

	public RenderInfinityArmor(RenderLayerParent<T, M> renderer, EntityModelSet modelSet) {
		super(renderer);
		this.model = new ModelInfinityArmor(modelSet.bakeLayer(ModelLayers.PLAYER));
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void render(PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLight, T livingEntity,
			float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw,
			float headPitch) {

		if(livingEntity instanceof Player) {
			poseStack.pushPose();
	        this.getParentModel().copyPropertiesTo((EntityModel<T>) this.model);
	        this.model.setupAnim((Player) livingEntity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
			VertexConsumer vertexconsumer = multiBufferSource.getBuffer(RenderType.armorCutoutNoCull(ModelInfinityArmor.WING));
			this.model.render((Player) livingEntity, poseStack, multiBufferSource, vertexconsumer, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F,
					1.0F, 1.0F);
			poseStack.popPose();
		}
	}
}
