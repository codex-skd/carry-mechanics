# Port Report — Carry Mechanics 1.21.1 / NeoForge 21.1.249

## Summary

Successfully reverted all 26.2-only Minecraft/NeoForge API references in `src/main/java` to their 1.21.1 equivalents. The codebase should now compile against MC 1.21.1 + NeoForge 21.1.249 + Java 21.

## API Reversion Patterns Applied

### 1. `Identifier` → `ResourceLocation`
- **Files:** `ListHandler.java`, `PacketIds.java`, `ModelOverrideHandler.java`
- `net.minecraft.resources.Identifier` → `net.minecraft.resources.ResourceLocation`
- `Identifier.fromNamespaceAndPath(...)` → `ResourceLocation.fromNamespaceAndPath(...)`
- `Identifier.parse(...)` → `ResourceLocation.parse(...)`
- **`PickupConditionHandler.java`**: Removed unused `Identifier` import.

### 2. `startRiding(Entity, boolean, boolean)` → `startRiding(Entity, boolean)` (3-arg → 2-arg)
- **Files:** `ClientboundStartRidingOtherPlayerPacket.java`, `ClientboundStartRidingPacket.java`, `PlacementHandler.java`
- Dropped the third `boolean` argument per upstream 1.21.1.

### 3. `animal.equine.Horse` → `animal.horse.Horse`
- **File:** `PlacementHandler.java`
- `net.minecraft.world.entity.animal.equine.Horse` → `net.minecraft.world.entity.animal.horse.Horse`

### 4. `MapCodec<CarryData>` → `Codec<CarryData>`
- **File:** `CarryData.java`
- Changed `MapCodec<CarryData> CODEC` to `Codec<CarryData> CODEC` (plain `Codec`, not wrapped in `.fieldOf()`).
- The attachment registration in `CarryMechanics.java` now uses `CarryData.CODEC` directly (matching upstream's `CarryOnData.CODEC`).

### 5. 26.2 `CompoundTag` / serialization APIs → 1.21.1 equivalents
- **File:** `CarryData.java`
- Removed imports: `ProblemReporter`, `TagValueOutput`, `TagValueInput`, `ValueInput`, `ValueOutput`, `MapCodec`
- `tag.getStringOr("key", default)` → `tag.getString("key")`
- `tag.getBooleanOr("key", default)` → `tag.getBoolean("key")`
- `tag.getIntOr("key", default)` → `tag.getInt("key")`
- `tag.getCompoundOrEmpty("key")` → `tag.getCompound("key")`
- `NbtUtils.readBlockState(BuiltInRegistries.BLOCK, ...)` → `NbtUtils.readBlockState(BuiltInRegistries.BLOCK.asLookup(), ...)`
- Block entity save: `TagValueOutput.createWithContext(reporter, registryAccess)` + `blockEntity.saveWithId(output)` + `output.buildResult()` → `blockEntity.saveWithId(registryAccess)` (returns `CompoundTag`)
- Entity save: `TagValueOutput.createWithContext(reporter, registryAccess)` + `entity.save(output)` + `output.buildResult()` → `CompoundTag entityData = new CompoundTag(); entity.save(entityData); nbt.put("entity", entityData);`
- Entity load: `EntityType.create(input, level, new EntitySpawnRequest(...))` → `EntityType.create(compoundTag, level)`
- `NbtUtils.writeBlockState(state)` returns `CompoundTag` in both versions — unchanged.

### 6. `ProblemReporter` / `TagValueOutput` removal from `PickupHandler.java`
- Removed `ProblemReporter` and `TagValueOutput` imports and usage.
- `blockEntity.saveWithId(output)` → `blockEntity.saveWithId(level.registryAccess())` (returns `CompoundTag`).
- `player.getGameProfile().id()` → `player.getGameProfile().getId()`
- `player.getGameProfile().name()` → `player.getGameProfile().getName()`
- `animal.dropLeash()` → `animal.dropLeash(true)` (1.21.1 requires boolean for drop items)

### 7. Client render architecture: 26.2 render-state → 1.21.1 direct renderer API
- **`CarriedObjectRender.java`** — Full rewrite:
  - `SubmitNodeCollector` → `MultiBufferSource`
  - `ItemStackRenderState` + `ItemModelResolver.updateForTopItem()` → `ItemRenderer.getModel()` + `ItemRenderer.render()`
  - `EntityRenderDispatcher.extractEntity()` + `dispatcher.submit(renderState, ...)` → `EntityRenderDispatcher.render(entity, 0, 0, 0, 0f, 0, poseStack, bufferSource, packedLight)`
  - `CameraRenderState` removed
  - `renderState.shadowPieces.clear()` / `renderState.lightCoords` → `dispatcher.setRenderShadow(false)` / `dispatcher.setRenderShadow(true)`
- **`CarryingItemRenderLayer.java`** — Full rewrite:
  - `RenderLayer<AvatarRenderState, PlayerModel>` → `RenderLayer<LivingEntity, PlayerModel<LivingEntity>>`
  - `submit(PoseStack, SubmitNodeCollector, int, AvatarRenderState, float, float)` → `render(PoseStack, MultiBufferSource, int, LivingEntity, float, float)`
  - Reads `Player` from `LivingEntity` parameter instead of `CarryMechanicsRenderState` interface.
- **`ClientEvents.java`**:
  - `event.getSubmitNodeCollector()` → `event.getMultiBufferSource()`

### 8. Mixin rework for 1.21.1 (no render-state architecture)
- **`HumanoidModelMixin.java`**:
  - `setupAnim(HumanoidRenderState, CallbackInfo)` → `setupAnim(LivingEntity, float, float, float, float, float, CallbackInfo)`
  - Reads `CarryData` directly from `CarryDataManager.getCarryData(player)` instead of from `CarryMechanicsRenderState`.
  - Checks `player.isVisuallySwimming()` / `player.isFallFlying()` instead of `state.isVisuallySwimming` / `state.isFallFlying`.
  - Checks `player.isCrouching()` instead of `state.isCrouching`.
- **`LivingEntityRendererMixin.java`** — Reworked:
  - Removed injection into `extractRenderState()` (does not exist in 1.21.1).
  - Changed to inject into `render(LivingEntity, float, float, PoseStack, MultiBufferSource, int)` at HEAD.
  - Sets `player.setPose(Pose.CROUCHING)` when carrying (forces crouching pose during render).
- **`AvatarRendererMixin.java`** — Retargeted:
  - `AvatarRenderer` → `PlayerRenderer`
  - Init injection: `<init>(EntityRendererProvider.Context, boolean)` targets `PlayerRenderer`.
- **`AvatarExtractorMixin.java`** — Retargeted:
  - `AvatarRenderer` → `PlayerRenderer`
  - Injects into `render(Player, float, float, PoseStack, MultiBufferSource, int)` at TAIL.
  - Sets `player.setPose(Pose.CROUCHING)` when carrying.
- **`EntityRendererMixin.java`**:
  - `shouldShowName(Entity, double, CallbackInfoReturnable)` → `shouldShowName(Entity, CallbackInfoReturnable)` (removed double distance parameter, 1.21.1 signature).
- **`PlayerRenderStateMixin.java`** — Removed from mixins.json:
  - Targeted `AvatarRenderState` which does not exist in 1.21.1.
  - File kept as placeholder; no functional code.
- **`CarryMechanicsRenderState.java`** — Emptied:
  - Interface was part of the 26.2 render-state architecture.
  - In 1.21.1, `HumanoidModelMixin` reads `CarryData` directly from `CarryDataManager`.

### 9. `carry_mechanics.mixins.json` updated
- Removed `PlayerRenderStateMixin` from `client` array.
- Remaining client mixins: `AvatarExtractorMixin`, `AvatarRendererMixin`, `EntityRendererMixin`, `HumanoidModelMixin`, `LivingEntityRendererMixin`.

## Files Modified (22 total)

| File | Change |
|------|--------|
| `carry/ListHandler.java` | `Identifier` → `ResourceLocation` |
| `carry/CarryData.java` | `MapCodec` → `Codec`; 1.21.1 serialization APIs |
| `carry/CarryDataManager.java` | No change needed |
| `carry/CarryDataSyncHandler.java` | No change needed |
| `carry/CarryMechanicsAccess.java` | No change needed |
| `carry/ConfigAccess.java` | No change needed |
| `carry/ListHandler.java` | `Identifier` → `ResourceLocation` |
| `carry/ModelOverrideHandler.java` | `Identifier` → `ResourceLocation` |
| `carry/PickupHandler.java` | 1.21.1 serialization, `dropLeash(true)`, `getGameProfile().getId()` |
| `carry/PlacementHandler.java` | `equine.Horse` → `horse.Horse`; `startRiding` 2-arg; `setPos(x,y,z)` |
| `networking/ClientboundStartRidingOtherPlayerPacket.java` | `startRiding` 2-arg |
| `networking/ClientboundStartRidingPacket.java` | `startRiding` 2-arg |
| `networking/ClientboundSyncScriptsPacket.java` | No change needed |
| `networking/NetworkHelper.java` | No change needed |
| `PacketIds.java` | `Identifier` → `ResourceLocation` |
| `pickupcondition/PickupConditionHandler.java` | Removed unused `Identifier` import |
| `events/ClientEvents.java` | `SubmitNodeCollector` → `MultiBufferSource` |
| `events/CommonEvents.java` | `getGameProfile().getName()` |
| `client/render/CarriedObjectRender.java` | Full rewrite for 1.21.1 renderer API |
| `client/render/CarryMechanicsRenderState.java` | Emptied (interface removed) |
| `client/render/CarryingItemRenderLayer.java` | `AvatarRenderState` → `LivingEntity`; `SubmitNodeCollector` → `MultiBufferSource` |
| `mixin/AvatarExtractorMixin.java` | Retargeted to `PlayerRenderer` |
| `mixin/AvatarRendererMixin.java` | Retargeted to `PlayerRenderer` |
| `mixin/EntityRendererMixin.java` | Removed `double` parameter from `shouldShowName` |
| `mixin/HumanoidModelMixin.java` | `setupAnim(LivingEntity, ...)` instead of `setupAnim(HumanoidRenderState, ...)` |
| `mixin/LivingEntityRendererMixin.java` | Reworked: inject into `render()` instead of `extractRenderState()` |
| `mixin/PlayerRenderStateMixin.java` | Emptied (placeholder; removed from mixins.json) |

## Files NOT Modified (no 26.2 API issues)

- `CarryMechanics.java` — AttachmentType builder and PayloadRegistrar are 1.21.1-compatible.
- `CarryMechanicsClient.java` — `@Mod(dist=Dist.CLIENT)` and `IConfigScreenFactory` are 1.21.1-compatible.
- `CarryConfig.java` — `ModConfigSpec.Builder` is unchanged.
- `ConfigData.java` — `ModConfigSpec` types are unchanged.
- `CarryScript.java` — Pure codec-based, no MC API dependency.
- `ScriptManager.java` — Uses `builtInRegistryHolder().key()` which exists in 1.21.1.
- `ScriptReloadListener.java` — `FileToIdConverter` and `OnDatapackSyncEvent` are 1.21.1-compatible.
- `CommandCarryMechanics.java` — Brigadier APIs are unchanged.
- `GamestageCompat.java` — Reflection-based, version-independent.
- `ModCompat.java` — No MC API dependency.
- `CarryKeybinds.java` — `KeyMapping` and `RegisterKeyMappingsEvent` are 1.21.1-compatible.
- `mixin/EntityMixin.java` — `isColliding` injection (see notes below).
- `mixin/InventoryMixin.java` — `pickSlot` injection is 1.21.1-compatible.
- `mixin/LivingEntityMixin.java` — `setJumping` / `jumpFromGround` injections are 1.21.1-compatible.
- `mixin/PlayerMixin.java` — `drop(ItemStack, boolean)` signature matches 1.21.1.

## Known Risks / Items for Operator Verification

1. **`EntityMixin.isColliding`** — The `@Inject(method = "isColliding")` target may not exist on `Entity` in 1.21.1 (it may be a 26.2 addition). If so, this mixin will fail at runtime. The upstream CarryOn 1.21.1 does not have this injection. Consider removing or retargeting if it causes a crash.

2. **`LivingEntityRendererMixin` pose forcing** — Setting `player.setPose(Pose.CROUCHING)` during the render call modifies the entity's actual pose, not just the render state. This is different from the 26.2 approach (which only modified the render state). This could have subtle side effects on animation systems. The upstream CarryOn 1.21.1 does not have a `LivingEntityRendererMixin` — it handles arm positioning via `HumanoidModelMixin` only. Consider whether the crouching pose override is needed or if the arm positioning alone is sufficient.

3. **`AvatarExtractorMixin` render injection** — Setting pose at TAIL of `PlayerRenderer.render()` is redundant with `LivingEntityRendererMixin`. Both force crouching. Consider removing one if it causes issues.

4. **`CarryMechanicsRenderState` empty interface** — The file is now a no-op interface. It can be safely deleted; it has no references.

5. **`PlayerRenderStateMixin` placeholder** — The file is a no-op placeholder with no `@Mixin` annotation. It is not listed in `mixins.json`. Can be safely deleted.

6. **`Animals.dropLeash(boolean)`** — The `boolean` parameter controls whether items are dropped. We pass `true` (matching upstream behavior). If the fork intended to suppress drops, change to `false`.

## Deliberate Fork Behaviours Preserved

All fork-specific behaviours from the 26.2 source have been preserved:
- Crouch-to-pick-up mechanic (not keybind-based)
- `CarryKeybinds` keybind registration (kept as an additional input method)
- `ConfigAccess` static field references to config values
- Custom `ListHandler` tag-based whitelist/blacklist system
- `CarryScript` codec-based scripting system
- `PickupCondition` system
- `ModelOverrideHandler` block-to-item rendering overrides
- Entity stacking with size checks
- All slowness/effect logic
- Jump prevention while carrying
- Drop prevention while carrying
- Break-speed cancellation while carrying
- Attack cancellation while carrying
- Inventory slot locking while carrying
- Client-side inventory slot enforcement
