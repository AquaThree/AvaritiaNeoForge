package net.byAqua3.avaritia.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;

import net.byAqua3.avaritia.Avaritia;
import net.byAqua3.avaritia.entity.EntityInfinityArrow;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

public class RenderInfinityArrow<T extends EntityInfinityArrow> extends EntityRenderer<T> {
	
	public static final ResourceLocation INFINITY_ARROW = new ResourceLocation(Avaritia.MODID, "textures/entity/infinity_arrow.png");
	
	public RenderInfinityArrow(EntityRendererProvider.Context pContext) {
		super(pContext);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void render(EntityInfinityArrow entity, float entityYaw, float partialTicks, PoseStack poseStack,
			MultiBufferSource multiBufferSource, int packedLight) {
		poseStack.pushPose();
		poseStack.mulPose(Axis.YP.rotationDegrees(Mth.lerp(partialTicks, entity.yRotO, entity.getYRot()) - 90.0F));
		poseStack.mulPose(Axis.ZP.rotationDegrees(Mth.lerp(partialTicks, entity.xRotO, entity.getXRot())));
		float i = (float) entity.shakeTime - partialTicks;
		if (i > 0.0F) {
			float f2 = -Mth.sin(i * 3.0F) * i;
			poseStack.mulPose(Axis.ZP.rotationDegrees(f2));
		}

		poseStack.mulPose(Axis.XP.rotationDegrees(45.0F));
		poseStack.scale(0.05625F, 0.05625F, 0.05625F);
		poseStack.translate(-4.0F, 0.0F, 0.0F);
		VertexConsumer vertexconsumer = multiBufferSource.getBuffer(RenderType.entityCutout(this.getTextureLocation(entity)));
		PoseStack.Pose posestack$pose = poseStack.last();
		Matrix4f matrix4f = posestack$pose.pose();
		Matrix3f matrix3f = posestack$pose.normal();
		this.vertex(matrix4f, matrix3f, vertexconsumer, -7, -2, -2, 0.0F, 0.15625F, -1, 0, 0, packedLight);
		this.vertex(matrix4f, matrix3f, vertexconsumer, -7, -2, 2, 0.15625F, 0.15625F, -1, 0, 0, packedLight);
		this.vertex(matrix4f, matrix3f, vertexconsumer, -7, 2, 2, 0.15625F, 0.3125F, -1, 0, 0, packedLight);
		this.vertex(matrix4f, matrix3f, vertexconsumer, -7, 2, -2, 0.0F, 0.3125F, -1, 0, 0, packedLight);
		this.vertex(matrix4f, matrix3f, vertexconsumer, -7, 2, -2, 0.0F, 0.15625F, 1, 0, 0, packedLight);
		this.vertex(matrix4f, matrix3f, vertexconsumer, -7, 2, 2, 0.15625F, 0.15625F, 1, 0, 0, packedLight);
		this.vertex(matrix4f, matrix3f, vertexconsumer, -7, -2, 2, 0.15625F, 0.3125F, 1, 0, 0, packedLight);
		this.vertex(matrix4f, matrix3f, vertexconsumer, -7, -2, -2, 0.0F, 0.3125F, 1, 0, 0, packedLight);

		for (int j = 0; j < 4; ++j) {
			poseStack.mulPose(Axis.XP.rotationDegrees(90.0F));
			this.vertex(matrix4f, matrix3f, vertexconsumer, -8, -2, 0, 0.0F, 0.0F, 0, 1, 0, packedLight);
			this.vertex(matrix4f, matrix3f, vertexconsumer, 8, -2, 0, 0.5F, 0.0F, 0, 1, 0, packedLight);
			this.vertex(matrix4f, matrix3f, vertexconsumer, 8, 2, 0, 0.5F, 0.15625F, 0, 1, 0, packedLight);
			this.vertex(matrix4f, matrix3f, vertexconsumer, -8, 2, 0, 0.0F, 0.15625F, 0, 1, 0, packedLight);
		}

		poseStack.popPose();
		super.render((T) entity, entityYaw, partialTicks, poseStack, multiBufferSource, packedLight);
	}

	public void vertex(Matrix4f pMatrix, Matrix3f pNormal, VertexConsumer pConsumer, int pX, int pY, int pZ, float pU,
			float pV, int pNormalX, int pNormalZ, int pNormalY, int pPackedLight) {
		pConsumer.vertex(pMatrix, (float) pX, (float) pY, (float) pZ).color(255, 255, 255, 255).uv(pU, pV)
				.overlayCoords(OverlayTexture.NO_OVERLAY).uv2(pPackedLight)
				.normal(pNormal, (float) pNormalX, (float) pNormalY, (float) pNormalZ).endVertex();
	}
	
	@Override
	public  ResourceLocation getTextureLocation(EntityInfinityArrow entity) {
		return INFINITY_ARROW;
	}
}
