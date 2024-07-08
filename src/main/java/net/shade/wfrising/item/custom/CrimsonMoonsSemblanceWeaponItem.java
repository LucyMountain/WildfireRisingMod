package net.shade.wfrising.item.custom;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ClickType;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.shade.wfrising.WildfireRisingMod;
import net.shade.wfrising.effects.ModEffects;
import net.shade.wfrising.event.PlayerTickHandler;

public class CrimsonMoonsSemblanceWeaponItem extends Item {
    private float spearAttackDamage = 10;
    private double spearAttackSpeed = -2.2;
    private float scytheAttackDamage = 15;
    private double scytheAttackSpeed = -2.8;
    private int weaponForm = 0;
    private int maxUseTicks = 100;
    private int baseCooldown = 160;
    private int maxUseTime = 840;
    private Multimap<EntityAttribute, EntityAttributeModifier> attributeModifiers;

    public CrimsonMoonsSemblanceWeaponItem(Settings settings) {
        super(settings);
        ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier(ATTACK_DAMAGE_MODIFIER_ID, "Weapon modifier", (this.weaponForm == 0)? (double)this.spearAttackDamage : (double)this.scytheAttackDamage, EntityAttributeModifier.Operation.ADDITION));
        builder.put(EntityAttributes.GENERIC_ATTACK_SPEED, new EntityAttributeModifier(ATTACK_SPEED_MODIFIER_ID, "Weapon modifier", (this.weaponForm == 0)? this.spearAttackSpeed : this.scytheAttackSpeed, EntityAttributeModifier.Operation.ADDITION));
        this.attributeModifiers = builder.build();
    }

    public boolean canMine(BlockState state, World world, BlockPos pos, PlayerEntity miner) {
        return !miner.isCreative();
    }

    public float getMiningSpeedMultiplier(ItemStack stack, BlockState state) {
        return state.isIn(BlockTags.SWORD_EFFICIENT) ||
                state.isOf(Blocks.COBWEB) ? 1.5F : 1.0F;
    }

    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.damage(1, attacker, (e) -> {
            e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND);
        });
        target.addStatusEffect(new StatusEffectInstance(ModEffects.BLOOD_DEBT, 600, 0));
        int cooldown = stack.getOrCreateNbt().getInt("Cooldown");
        stack.getOrCreateNbt().putInt("Cooldown", cooldown - 16);
        if (stack.getOrCreateNbt().getInt("Form") == 1){
            if (target.hasStatusEffect(ModEffects.BLOOD_DEBT)) {
                target.damage(target.getDamageSources().magic(), 0.7F);
            }
            if (attacker.hasStatusEffect(ModEffects.BOND_OF_LIFE)) {
                int bond_amplifier = attacker.getActiveStatusEffects().get(ModEffects.BOND_OF_LIFE).getAmplifier();
                if (bond_amplifier < 19) {
                    attacker.removeStatusEffect(ModEffects.BOND_OF_LIFE);
                    attacker.addStatusEffect(new StatusEffectInstance(ModEffects.BOND_OF_LIFE, -1, bond_amplifier + 1));
                }
            }else{
                attacker.addStatusEffect(new StatusEffectInstance(ModEffects.BOND_OF_LIFE, -1,0));
            }
        }
        return true;
    }

    public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
        if (state.getHardness(world, pos) != 0.0F) {
            stack.damage(2, miner, (e) -> {
                e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND);
            });
        }

        return true;
    }

    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        return ItemUsage.consumeHeldItem(world, user, hand);
    }

    public int getMaxUseTime(ItemStack stack) {
        return maxUseTicks;
    }

    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        int ticks = maxUseTicks - remainingUseTicks;
        int currentForm = stack.getOrCreateNbt().getInt("Form");
        if (ticks > 10){
            if (currentForm == 1) {
                if (user.hasStatusEffect(ModEffects.BOND_OF_LIFE)) {
                    int bond_strength = user.getActiveStatusEffects().get(ModEffects.BOND_OF_LIFE).getAmplifier() + 1;
                    user.removeStatusEffect(ModEffects.BOND_OF_LIFE);
                    user.heal(bond_strength);
                    for (PlayerEntity player : world.getPlayers()) {
                        if (player.hasStatusEffect(ModEffects.BLOOD_DEBT)) {
                            player.removeStatusEffect(ModEffects.BLOOD_DEBT);
                            player.damage(player.getDamageSources().magic(), (float) (0.75 * bond_strength));
                        }
                    }
                }
            }
        }else{
            if (currentForm == 1){
                int totalTime = (int) world.getTime() - stack.getOrCreateNbt().getInt("ScytheFormStart");
                int cooldown = this.baseCooldown;
                if (totalTime < this.maxUseTime){
                    cooldown = cooldown + totalTime;
                }else{
                    cooldown = cooldown + this.maxUseTime;
                }
                stack.getOrCreateNbt().putInt("CooldownStart", (int) world.getTime());
                stack.getOrCreateNbt().putInt("Cooldown", cooldown);
                stack.getOrCreateNbt().putInt("Form", 0);
                ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> builder = ImmutableMultimap.builder();
                builder.put(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier(ATTACK_DAMAGE_MODIFIER_ID, "Weapon modifier", (double)this.spearAttackDamage, EntityAttributeModifier.Operation.ADDITION));
                builder.put(EntityAttributes.GENERIC_ATTACK_SPEED, new EntityAttributeModifier(ATTACK_SPEED_MODIFIER_ID, "Weapon modifier", (double)this.spearAttackSpeed
                        , EntityAttributeModifier.Operation.ADDITION));
                this.attributeModifiers = builder.build();
            }else{
                int cooldownLeft = stack.getOrCreateNbt().getInt("Cooldown") - (int) world.getTime() + stack.getOrCreateNbt().getInt("CooldownStart");
                if (cooldownLeft > stack.getOrCreateNbt().getInt("Cooldown") ||  cooldownLeft < 1) {
                    stack.getOrCreateNbt().putInt("Form", 1);
                    ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> builder = ImmutableMultimap.builder();
                    builder.put(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier(ATTACK_DAMAGE_MODIFIER_ID, "Weapon modifier", (double)this.scytheAttackDamage, EntityAttributeModifier.Operation.ADDITION));
                    builder.put(EntityAttributes.GENERIC_ATTACK_SPEED, new EntityAttributeModifier(ATTACK_SPEED_MODIFIER_ID, "Weapon modifier", (double)this.scytheAttackSpeed, EntityAttributeModifier.Operation.ADDITION));
                    this.attributeModifiers = builder.build();
                    stack.getOrCreateNbt().putInt("ScytheFormStart", (int) world.getTime());
                }
            }
        }
    }

    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (stack.getOrCreateNbt().getInt("Form") == 1) {
            int totalTime = (int) world.getTime() - stack.getOrCreateNbt().getInt("ScytheFormStart");
            if (totalTime >= this.maxUseTime) {
                this.onStoppedUsing(stack, world, (LivingEntity) entity, 99);
            }
        }
    }

    public boolean isSuitableFor(BlockState state) {
        return state.isOf(Blocks.COBWEB);
    }

    public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(EquipmentSlot slot) {
        return slot == EquipmentSlot.MAINHAND ? this.attributeModifiers : super.getAttributeModifiers(slot);
    }
}
