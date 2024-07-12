package net.byAqua3.avaritia.model;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.byAqua3.avaritia.entity.EntityGapingVoid;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class ModelGapingVoid<T extends EntityGapingVoid> extends EntityModel<T> {

	public ModelGapingVoid() {
		super();
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshDefinition = new MeshDefinition();
		PartDefinition partDefinition = meshDefinition.getRoot();
		PartDefinition gaping_void = partDefinition.addOrReplaceChild("gaping_void", CubeListBuilder.create(),
				PartPose.offset(0.0F, 16.0F, 0.0F));

		PartDefinition bone = gaping_void.addOrReplaceChild("bone",
				CubeListBuilder.create().texOffs(-2, 0)
						.addBox(-2.0F, -2.0F, 2.0F, 4.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(0, 0)
						.addBox(-2.0F, -2.0F, -4.0F, 4.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(-8, -2)
						.addBox(-4.0F, -2.0F, -2.0F, 8.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(-3, 0)
						.addBox(-4.0F, -4.0F, -6.0F, 8.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(0, 0)
						.addBox(2.0F, -4.0F, -4.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(-6, -6)
						.addBox(4.0F, -4.0F, -4.0F, 2.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)).texOffs(0, 0)
						.addBox(2.0F, -4.0F, 2.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(0, 0)
						.addBox(-4.0F, -4.0F, -4.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(0, 0)
						.addBox(-4.0F, -4.0F, 2.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(-6, -6)
						.addBox(-6.0F, -4.0F, -4.0F, 2.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)).texOffs(-3, 0)
						.addBox(-4.0F, -4.0F, 4.0F, 8.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(-1, 0)
						.addBox(-2.0F, -6.0F, 6.0F, 4.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(-1, 0)
						.addBox(-6.0F, -6.0F, 4.0F, 4.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(-1, 0)
						.addBox(2.0F, -6.0F, 4.0F, 4.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(-1, 0)
						.addBox(-2.0F, -6.0F, -8.0F, 4.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(-1, 0)
						.addBox(2.0F, -6.0F, -6.0F, 4.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(-1, 0)
						.addBox(-6.0F, -6.0F, -6.0F, 4.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(0, 0)
						.addBox(-6.0F, -6.0F, 2.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(0, 0)
						.addBox(-6.0F, -6.0F, -4.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(0, 0)
						.addBox(4.0F, -6.0F, 2.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(0, 0)
						.addBox(4.0F, -6.0F, -4.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(-2, -2)
						.addBox(6.0F, -6.0F, -2.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(-2, -2)
						.addBox(-8.0F, -6.0F, -2.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(-6, -6)
						.addBox(6.0F, -8.0F, -4.0F, 2.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)).texOffs(-6, -6)
						.addBox(-8.0F, -8.0F, -4.0F, 2.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)).texOffs(-3, 0)
						.addBox(-4.0F, -8.0F, -8.0F, 8.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(-3, 0)
						.addBox(-4.0F, -8.0F, 6.0F, 8.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(0, 0)
						.addBox(4.0F, -8.0F, 4.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(0, 0)
						.addBox(-6.0F, -8.0F, 4.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(0, 0)
						.addBox(4.0F, -8.0F, -6.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(0, 0)
						.addBox(-6.0F, -8.0F, -6.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
				PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition bone2 = bone.addOrReplaceChild("bone2",
				CubeListBuilder.create().texOffs(-2, 0)
						.addBox(-2.0F, -2.0F, 2.0F, 4.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(0, 0)
						.addBox(-2.0F, -2.0F, -4.0F, 4.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(-8, -2)
						.addBox(-4.0F, -2.0F, -2.0F, 8.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(-3, 0)
						.addBox(-4.0F, -4.0F, -6.0F, 8.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(0, 0)
						.addBox(2.0F, -4.0F, -4.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(-6, -6)
						.addBox(4.0F, -4.0F, -4.0F, 2.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)).texOffs(0, 0)
						.addBox(2.0F, -4.0F, 2.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(0, 0)
						.addBox(-4.0F, -4.0F, -4.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(0, 0)
						.addBox(-4.0F, -4.0F, 2.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(-6, -6)
						.addBox(-6.0F, -4.0F, -4.0F, 2.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)).texOffs(-3, 0)
						.addBox(-4.0F, -4.0F, 4.0F, 8.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(-1, 0)
						.addBox(-2.0F, -6.0F, 6.0F, 4.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(-1, 0)
						.addBox(-6.0F, -6.0F, 4.0F, 4.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(-1, 0)
						.addBox(2.0F, -6.0F, 4.0F, 4.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(-1, 0)
						.addBox(-2.0F, -6.0F, -8.0F, 4.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(-1, 0)
						.addBox(2.0F, -6.0F, -6.0F, 4.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(-1, 0)
						.addBox(-6.0F, -6.0F, -6.0F, 4.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(0, 0)
						.addBox(-6.0F, -6.0F, 2.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(0, 0)
						.addBox(-6.0F, -6.0F, -4.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(0, 0)
						.addBox(4.0F, -6.0F, 2.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(0, 0)
						.addBox(4.0F, -6.0F, -4.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(-2, -2)
						.addBox(6.0F, -6.0F, -2.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(-2, -2)
						.addBox(-8.0F, -6.0F, -2.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(-6, -6)
						.addBox(6.0F, -8.0F, -4.0F, 2.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)).texOffs(-6, -6)
						.addBox(-8.0F, -8.0F, -4.0F, 2.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)).texOffs(-3, 0)
						.addBox(-4.0F, -8.0F, -8.0F, 8.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(-3, 0)
						.addBox(-4.0F, -8.0F, 6.0F, 8.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(0, 0)
						.addBox(4.0F, -8.0F, 4.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(0, 0)
						.addBox(-6.0F, -8.0F, 4.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(0, 0)
						.addBox(4.0F, -8.0F, -6.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(0, 0)
						.addBox(-6.0F, -8.0F, -6.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, -16.0F, 0.0F, 3.1416F, 0.0F, 0.0F));
		return LayerDefinition.create(meshDefinition, 16, 16);
	}

	@Override
	public void setupAnim(EntityGapingVoid entity, float limbSwing, float limbSwingAmount, float ageInTicks,
			float netHeadYaw, float headPitch) {
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay,
			float red, float green, float blue, float alpha) {

		ModelPart root = createBodyLayer().bakeRoot();
		ModelPart gaping_void = root.getChild("gaping_void");

		RenderSystem.enableCull();

		gaping_void.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

}
