package net.byAqua3.avaritia.model;

import java.awt.Color;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;

import net.byAqua3.avaritia.Avaritia;
import net.byAqua3.avaritia.event.AvaritiaClientEvent;
import net.byAqua3.avaritia.loader.AvaritiaItems;
import net.byAqua3.avaritia.shader.AvaritiaCosmicShaders;
import net.byAqua3.avaritia.shader.AvaritiaRenderType;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;

public class ModelInfinityArmor extends HumanoidModel<Player> {

	public static ResourceLocation EYE = new ResourceLocation(Avaritia.MODID,
			"textures/models/armor/infinity_armor_eyes.png");
	public static ResourceLocation WING = new ResourceLocation(Avaritia.MODID,
			"textures/models/armor/infinity_armor_wing.png");
	public static ResourceLocation WING_GLOW = new ResourceLocation(Avaritia.MODID,
			"textures/models/armor/infinity_armor_wingglow.png");

	public ModelInfinityArmor(ModelPart root) {
		super(root);
	}

	public static LayerDefinition createLayer() {
		MeshDefinition meshDefinition = new MeshDefinition();
		PartDefinition partDefinition = meshDefinition.getRoot();
		CubeDeformation cubeDeformation = new CubeDeformation(0.0F);
		partDefinition.addOrReplaceChild("left_wing",
				CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, -11.6F, 0.0F, 0.0F, 32.0F, 32.0F, cubeDeformation),
				PartPose.offsetAndRotation(-1.5F, 0.0F, 2.0F, 0.0F, (float) (Math.PI * 0.4), 0.0F));
		partDefinition.addOrReplaceChild("right_wing",
				CubeListBuilder.create().texOffs(0, 0).mirror().addBox(0.0F, -11.6F, 0.0F, 0.0F, 32.0F, 32.0F,
						cubeDeformation),
				PartPose.offsetAndRotation(1.5F, 0.0F, 2.0F, 0.0F, (float) (-Math.PI * 0.4), 0.0F));
		partDefinition
				.addOrReplaceChild(
						"cosmicBody", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-1.5F, -2.0F, 2.0F, 2.5F,
								2.5F, 0.2F, cubeDeformation),
						PartPose.offsetAndRotation(0.25F, 5.5F, -5.1F, 0.0F, 0.0F, 0.0F));
		partDefinition
		.addOrReplaceChild(
				"cosmicBody1", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-1.5F, -2.0F, 2.0F, 1.5F,
						8.15F, 0.2F, cubeDeformation),
				PartPose.offsetAndRotation(2.7F, 3.33F, 0.9F, 0.0F, 0.0F, 0.0F));
		partDefinition
		.addOrReplaceChild(
				"cosmicBody2", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-1.5F, -2.0F, 2.0F, 1.5F,
						8.15F, 0.2F, cubeDeformation),
				PartPose.offsetAndRotation(-1.2F, 3.33F, 0.9F, 0.0F, 0.0F, 0.0F));
		return LayerDefinition.create(meshDefinition, 64, 64);
	}

	@Override
	public void setupAnim(Player player, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw,
			float headPitch) {
		super.setupAnim(player, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
	}

	public void render(Player player, PoseStack poseStack, MultiBufferSource multiBufferSource,
			VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue,
			float alpha) {
		Item headItem = player.getItemBySlot(EquipmentSlot.HEAD).getItem();
		Item chestItem = player.getItemBySlot(EquipmentSlot.CHEST).getItem();
		Item legsItem = player.getItemBySlot(EquipmentSlot.LEGS).getItem();
		Item feetItem = player.getItemBySlot(EquipmentSlot.FEET).getItem();

		if (chestItem == AvaritiaItems.INFINITY_CHESTPLATE.get() && player.getAbilities().flying) {
			poseStack.pushPose();
			ModelPart root = createLayer().bakeRoot();
			ModelPart leftWing = root.getChild("left_wing");
			ModelPart rightWing = root.getChild("right_wing");
			leftWing.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
			rightWing.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
			leftWing.render(poseStack, multiBufferSource.getBuffer(AvaritiaRenderType.Glow(WING_GLOW)), packedLight,
					packedOverlay, red, green, blue, alpha);
			rightWing.render(poseStack, multiBufferSource.getBuffer(AvaritiaRenderType.Glow(WING_GLOW)), packedLight,
					packedOverlay, red, green, blue, alpha);
			poseStack.popPose();
		}

		if (headItem == AvaritiaItems.INFINITY_HELMET.get() && chestItem == AvaritiaItems.INFINITY_CHESTPLATE.get()
				&& legsItem == AvaritiaItems.INFINITY_LEGGINGS.get()
				&& feetItem == AvaritiaItems.INFINITY_BOOTS.get()) {
			poseStack.pushPose();

			if (player.isCrouching()) {
				poseStack.translate(0.0F, 0.25F, 0.0F);
			}
			int rgb = Color.HSBtoRGB((System.currentTimeMillis() - AvaritiaClientEvent.lastTime) / 2000.0F, 1.0F, 1.0F);
			float r = ((rgb >> 16) & 0xFF) / 255.0F;
			float g = ((rgb >> 8) & 0xFF) / 255.0F;
			float b = ((rgb >> 0) & 0xFF) / 255.0F;

			this.hat.render(poseStack, multiBufferSource.getBuffer(RenderType.armorCutoutNoCull(EYE)), packedLight,
					packedOverlay, r, g, b, alpha);
			poseStack.popPose();

			RenderType COSMIC_ARMOR_RENDER_TYPE = AvaritiaRenderType.COSMIC_ARMOR_RENDER_TYPE;

			float yaw = 0.0F;
			float pitch = 0.0F;
			float scale = 1.0F;

			if (AvaritiaCosmicShaders.cosmicInventoryRender) {
				scale = 100.0F;
			} else {
				yaw = (float) ((player.getYRot() * 2.0F) * Math.PI / 360.0D);
				pitch = -((float) ((player.getXRot() * 2.0F) * Math.PI / 360.0D));
			}

			AvaritiaCosmicShaders.timeArmorUniform
					.set((System.currentTimeMillis() - AvaritiaClientEvent.lastTime) / 2000.0F);
			AvaritiaCosmicShaders.yawArmorUniform.set(yaw);
			AvaritiaCosmicShaders.pitchArmorUniform.set(pitch);
			AvaritiaCosmicShaders.externalScaleArmorUniform.set(scale);
			AvaritiaCosmicShaders.opacityArmorUniform.set(1.0F);

			for (int i = 0; i < AvaritiaCosmicShaders.COSMIC_SPRITES.length; i++) {
				TextureAtlasSprite sprite = AvaritiaCosmicShaders.COSMIC_SPRITES[i];
				AvaritiaCosmicShaders.COSMIC_UVS[i * 4 + 0] = sprite.getU0();
				AvaritiaCosmicShaders.COSMIC_UVS[i * 4 + 1] = sprite.getV0();
				AvaritiaCosmicShaders.COSMIC_UVS[i * 4 + 2] = sprite.getU1();
				AvaritiaCosmicShaders.COSMIC_UVS[i * 4 + 3] = sprite.getV1();
			}
			AvaritiaCosmicShaders.cosmicuvsArmorUniform.set(AvaritiaCosmicShaders.COSMIC_UVS);
			
			ModelPart root = createLayer().bakeRoot();
			
			poseStack.pushPose();

			ModelPart cosmicBody = root.getChild("cosmicBody");

			if (player.isCrouching()) {
				poseStack.translate(0.0F, 0.20F, 0.02F);
				poseStack.mulPose(Axis.XP.rotationDegrees(25.0F));
			}

			cosmicBody.render(poseStack, multiBufferSource.getBuffer(COSMIC_ARMOR_RENDER_TYPE), packedLight,
					packedOverlay, red, green, blue, alpha);

			poseStack.popPose();
			
			poseStack.pushPose();
			
			ModelPart cosmicBody1 = root.getChild("cosmicBody1");
			ModelPart cosmicBody2 = root.getChild("cosmicBody2");

			if (player.isCrouching()) {
				poseStack.translate(0.0F, 0.23F, 0.01F);
				poseStack.mulPose(Axis.XP.rotationDegrees(30.0F));
			}

			cosmicBody1.render(poseStack, multiBufferSource.getBuffer(COSMIC_ARMOR_RENDER_TYPE), packedLight,
					packedOverlay, red, green, blue, alpha);
			cosmicBody2.render(poseStack, multiBufferSource.getBuffer(COSMIC_ARMOR_RENDER_TYPE), packedLight,
					packedOverlay, red, green, blue, alpha);
			
			poseStack.popPose();
		}
	}
}
