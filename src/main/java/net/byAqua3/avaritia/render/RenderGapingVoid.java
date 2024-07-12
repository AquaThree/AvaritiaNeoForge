package net.byAqua3.avaritia.render;

import java.awt.Color;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.byAqua3.avaritia.Avaritia;
import net.byAqua3.avaritia.entity.EntityGapingVoid;
import net.byAqua3.avaritia.model.ModelGapingVoid;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class RenderGapingVoid<T extends EntityGapingVoid> extends EntityRenderer<T> {

	public static final ResourceLocation VOID = new ResourceLocation(Avaritia.MODID, "textures/entity/void.png");

	private final ModelGapingVoid<EntityGapingVoid> model;

	public RenderGapingVoid(EntityRendererProvider.Context context) {
		super(context);
		this.model = new ModelGapingVoid<>();
	}

	private Color getColor(double age) {
		double life = age / 186.0D;
		double f = Math.max(0.0D, (life - 0.95D) / 0.050000000000000044D);
		f = Math.max(f, 1.0D - life * 30.0D);
		return new Color((float) f, (float) f, (float) f, 1.0F);
	}

	@Override
	public void render(T livingEntity, float entityYaw, float partialTicks, PoseStack poseStack,
			MultiBufferSource multiBufferSource, int packedLight) {
		poseStack.pushPose();
		VertexConsumer vertexConsumer = multiBufferSource
				.getBuffer(this.model.renderType(this.getTextureLocation(livingEntity)));
		Color color = this.getColor(livingEntity.getAge());
		float scale = (float) EntityGapingVoid.getVoidScale(livingEntity.getAge());
		poseStack.scale(scale, scale, scale);
		poseStack.translate(0.0F, -(scale * 0.05F) - 1.5F, 0.0F);
		this.model.renderToBuffer(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY, color.getRed(), color.getGreen(), color.getBlue(),
				color.getAlpha());
		poseStack.popPose();
	}

	@Override
	public ResourceLocation getTextureLocation(EntityGapingVoid pEntity) {
		return VOID;
	}

}
