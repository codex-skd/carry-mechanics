# Graph Report - 26.1.2  (2026-07-31)

## Corpus Check
- 97 files · ~128,069 words
- Verdict: corpus is large enough that graph structure adds value.

## Summary
- 606 nodes · 808 edges · 183 communities (63 shown, 120 thin omitted)
- Extraction: 93% EXTRACTED · 7% INFERRED · 0% AMBIGUOUS · INFERRED: 55 edges (avg confidence: 0.8)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `defe1919`
- Run `git rev-parse HEAD` and compare to check if the graph is stale.
- Run `graphify update .` after code changes (no API cost).

## Community Hubs (Navigation)
- .getCarryData
- ClientboundStartRidingOtherPlayerPacket
- CarryData
- .tryPickUpBlock
- CarryScript
- AvatarExtractorMixin.java
- .tryPlaceEntity
- CarryMechanics.java
- CarryingItemRenderLayer.java
- PickupCondition
- CarryConfig
- LivingEntityRendererMixin.java
- ModelOverrideHandler.java
- ListHandler
- CarryMechanicsClient.java
- PlayerMixin.java
- CarryKeybinds.java
- InventoryMixin.java
- EntityMixin.java
- CarryDataSyncHandler
- gradlew
- GamestageCompat
- settings.gradle
- AvatarRenderState
- Logger
- Override
- PoseStack
- SubmitNodeCollector
- CallbackInfo
- Entity
- Inject
- Logger
- Mixin
- [0.0.0-beta.10] - 2026-07-13
- [0.0.0-beta.12] - 2026-07-13
- [0.0.0-beta.13] - 2026-07-13
- [0.0.0-beta.14] - 2026-07-13
- [0.0.0-beta.17] - 2026-07-13
- [0.0.0-beta.18] - 2026-07-13
- [0.0.0-beta.19] - 2026-07-17
- [0.0.0-beta.1] - 2026-07-13
- [0.0.0-beta.20] - 2026-07-17
- [0.0.0-beta.21] - 2026-07-17
- [0.0.0-beta.23] - 2026-07-17
- [0.0.0-beta.27] - 2026-07-18
- [0.0.0-beta.28] - 2026-07-23
- [0.0.0-beta.29] - 2026-07-23
- [0.0.0-beta.2] - 2026-07-13
- [0.0.0-beta.30] - 2026-07-23
- [0.0.0-beta.31] - 2026-07-24
- [0.0.0-beta.32] - 2026-07-25
- [0.0.0-beta.33] - 2026-07-25
- [0.0.0-beta.34] - 2026-07-25
- [0.0.0-beta.37] - 2026-07-27
- [0.0.0-beta.38] - 2026-07-27
- [0.0.0-beta.3] - 2026-07-13
- [0.0.0-beta.4] - 2026-07-13
- [0.0.0-beta.5] - 2026-07-13
- [0.0.0-beta.6] - 2026-07-13
- [0.0.0-beta.7] - 2026-07-13
- [0.0.0-beta.8] - 2026-07-13
- [0.0.0-beta.9] - 2026-07-13
- BlockPos
- BlockState
- Codec
- CompoundTag
- Entity
- Level
- Player
- RegistryFriendlyByteBuf
- ServerPlayer
- StreamCodec
- Player
- StreamCodec
- AttachmentType
- Logger
- BooleanValue
- DoubleValue
- IntValue
- Block
- Entity
- EntityType
- Block
- ItemStack
- BlockPos
- BlockState
- AttachmentType
- Logger
- Mod
- ModContainer
- SubscribeEvent
- EventBusSubscriber
- Mod
- ModContainer
- SubscribeEvent
- EventBusSubscriber
- SubscribeEvent
- EventBusSubscriber
- SubscribeEvent
- AvatarRenderState
- CallbackInfo
- Inject
- Mixin
- CallbackInfo
- Inject
- Mixin
- CallbackInfoReturnable
- Inject
- Mixin
- CallbackInfoReturnable
- Entity
- Inject
- Mixin
- CallbackInfo
- Inject
- Mixin
- Player
- CallbackInfoReturnable
- Inject
- ItemStack
- Mixin
- IPayloadContext
- Override
- RegistryFriendlyByteBuf
- StreamCodec
- Type
- IPayloadContext
- Override
- RegistryFriendlyByteBuf
- StreamCodec
- Type
- IPayloadContext
- Override
- RegistryFriendlyByteBuf
- StreamCodec
- Type
- CustomPacketPayload
- Type
- BlockState
- EntityType
- ServerPlayer
- BlockState
- Entity
- EntityType
- Codec
- ServerPlayer
- BlockPos
- BlockState
- CompoundTag
- Entity
- Level
- ServerPlayer

## God Nodes (most connected - your core abstractions)
1. `Changelog` - 45 edges
2. `CarryData` - 40 edges
3. `CarryRenderHelper` - 11 edges
4. `Flujo de trabajo — Carry Mechanics (NeoForge)` - 11 edges
5. `PlacementHandler` - 10 edges
6. `ClientboundSyncScriptsPacket` - 10 edges
7. `Carry Mechanics` - 10 edges
8. `PickupHandler` - 9 edges
9. `ICarryOnRenderState` - 9 edges
10. `CommonEvents` - 9 edges

## Surprising Connections (you probably didn't know these)
- `PacketIds` --references--> `ClientboundStartRidingOtherPlayerPacket`  [EXTRACTED]
  src/main/java/com/skd/carrymechanics/PacketIds.java → src/main/java/com/skd/carrymechanics/networking/ClientboundStartRidingOtherPlayerPacket.java
- `PacketIds` --references--> `ClientboundStartRidingPacket`  [EXTRACTED]
  src/main/java/com/skd/carrymechanics/PacketIds.java → src/main/java/com/skd/carrymechanics/networking/ClientboundStartRidingPacket.java
- `PacketIds` --references--> `ClientboundSyncScriptsPacket`  [EXTRACTED]
  src/main/java/com/skd/carrymechanics/PacketIds.java → src/main/java/com/skd/carrymechanics/networking/ClientboundSyncScriptsPacket.java
- `PlayerRenderStateMixin` --references--> `CarryData`  [EXTRACTED]
  src/main/java/com/skd/carrymechanics/mixin/PlayerRenderStateMixin.java → src/main/java/com/skd/carrymechanics/carry/CarryData.java
- `PlayerRenderStateMixin` --implements--> `ICarryOnRenderState`  [EXTRACTED]
  src/main/java/com/skd/carrymechanics/mixin/PlayerRenderStateMixin.java → src/main/java/com/skd/carrymechanics/client/render/ICarryOnRenderState.java

## Import Cycles
- None detected.

## Communities (183 total, 120 thin omitted)

### Community 0 - ".getCarryData"
Cohesion: 0.07
Nodes (29): AttackEntityEvent, BlockPlaceContext, BreakSpeed, CommandDispatcher, CommandSourceStack, Direction, EntityInteract, EntityLeaveLevelEvent (+21 more)

### Community 1 - "ClientboundStartRidingOtherPlayerPacket"
Cohesion: 0.17
Nodes (11): Buenas prácticas, Commits (Conventional Commits), Convenciones de nomenclatura, Específico del mod, Estructura del proyecto, Flujo de trabajo — Carry Mechanics (NeoForge), Flujo por tarea, Idioma (+3 more)

### Community 3 - ".tryPickUpBlock"
Cohesion: 0.18
Nodes (10): Carry Mechanics, Commands, Configuration, Controls, Features, Installation, License, Overview (+2 more)

### Community 4 - "CarryScript"
Cohesion: 0.09
Nodes (15): BlockEntity, CarryData, CarryType, BLOCK, ENTITY, INVALID, PLAYER, MapCodec (+7 more)

### Community 5 - "AvatarExtractorMixin.java"
Cohesion: 0.43
Nodes (6): Avatar, AvatarExtractorMixin, AvatarRenderState, CallbackInfo, Inject, Mixin

### Community 6 - ".tryPlaceEntity"
Cohesion: 0.15
Nodes (12): [0.0.0-beta.16] - 2026-07-13, [0.0.0-beta.29] - 2026-07-23, [0.0.0-beta.2] - 2026-07-13, [0.0.0-beta.39] - 2026-07-28, [0.0.0-beta.5] - 2026-07-13, [0.0.0-beta.7] - 2026-07-13, Changelog, Docs (+4 more)

### Community 7 - "CarryMechanics.java"
Cohesion: 0.09
Nodes (15): CustomPacketPayload, DeferredRegister, CarryMechanics, ClientboundStartRidingOtherPlayerPacket, ClientboundStartRidingPacket, ClientboundSyncScriptsPacket, NetworkHelper, PacketIds (+7 more)

### Community 8 - "CarryingItemRenderLayer.java"
Cohesion: 0.22
Nodes (10): Context, AvatarRendererMixin, PlayerModel, RenderLayer, RenderLayerParent, CarryingItemRenderLayer, AvatarRenderState, Override (+2 more)

### Community 9 - "PickupCondition"
Cohesion: 0.31
Nodes (8): CarryScript, ObjectType, BLOCK, ENTITY, ScriptConditions, ScriptEffects, ScriptObject, ScriptRender

### Community 10 - "CarryConfig"
Cohesion: 0.17
Nodes (11): Builder, ModConfigSpec, ConfigAccess, BooleanValue, DoubleValue, IntValue, CarryConfig, ConfigData (+3 more)

### Community 11 - "LivingEntityRendererMixin.java"
Cohesion: 0.23
Nodes (10): LivingEntity, LivingEntityRenderState, CallbackInfo, Inject, Mixin, LivingEntityMixin, CallbackInfo, Inject (+2 more)

### Community 12 - "ModelOverrideHandler.java"
Cohesion: 0.28
Nodes (6): CarryRenderHelper, BlockState, Entity, ItemStack, Player, PoseStack

### Community 15 - "PlayerMixin.java"
Cohesion: 0.17
Nodes (9): ScriptManager, BlockPos, BlockState, Entity, Level, Property, ServerPlayer, Vec3 (+1 more)

### Community 16 - "CarryKeybinds.java"
Cohesion: 0.60
Nodes (3): CarryKeybinds, KeyMapping, RegisterKeyMappingsEvent

### Community 17 - "InventoryMixin.java"
Cohesion: 0.22
Nodes (8): CurseForge, Game Versions (IDs), Mod, Project Variables — Carry Mechanics, Rama, Token de subida (Python), Variables para el script de subida (curseforge-upload.ps1), Versiones

### Community 18 - "EntityMixin.java"
Cohesion: 0.33
Nodes (5): Changes, How to use, Known issues, Requirements, v0.0.0-beta.8

### Community 20 - "gradlew"
Cohesion: 0.40
Nodes (4): Changes, Known issues, Requirements, v0.0.0-beta.10

### Community 21 - "GamestageCompat"
Cohesion: 0.40
Nodes (4): Changes, Known issues, Requirements, v0.0.0-beta.9

### Community 23 - "settings.gradle"
Cohesion: 0.22
Nodes (9): Pre, RenderHandEvent, CarriedObjectRender, Player, PoseStack, SubmitNodeCollector, ClientEvents, EventBusSubscriber (+1 more)

### Community 24 - "AvatarRenderState"
Cohesion: 0.50
Nodes (3): Changes, Requirements, v0.0.0-beta.11

### Community 25 - "Logger"
Cohesion: 0.50
Nodes (3): Changes, Requirements, v0.0.0-beta.12

### Community 26 - "Override"
Cohesion: 0.50
Nodes (3): Changes, Requirements, v0.0.0-beta.13

### Community 27 - "PoseStack"
Cohesion: 0.50
Nodes (3): Changes, Requirements, v0.0.0-beta.14

### Community 28 - "SubmitNodeCollector"
Cohesion: 0.50
Nodes (3): Changes, Requirements, v0.0.0-beta.15

### Community 29 - "CallbackInfo"
Cohesion: 0.50
Nodes (3): Changes, Requirements, v0.0.0-beta.16

### Community 30 - "Entity"
Cohesion: 0.83
Nodes (3): gradlew script, die(), warn()

### Community 32 - "Logger"
Cohesion: 0.67
Nodes (3): [0.0.0-beta.11] - 2026-07-13, Feat, Fix

### Community 38 - "[0.0.0-beta.17] - 2026-07-13"
Cohesion: 0.50
Nodes (3): CLAUDE.md — carry_mechanics (26.1.2), Prioridad de instrucciones, Workflow del mod

### Community 47 - "[0.0.0-beta.29] - 2026-07-23"
Cohesion: 0.38
Nodes (7): HumanoidRenderState, ModelPart, HumanoidModelMixin, CallbackInfo, Inject, Mixin, Unique

### Community 53 - "[0.0.0-beta.34] - 2026-07-25"
Cohesion: 0.36
Nodes (4): ModelOverride, ModelOverrideHandler, ModCompat, Item

### Community 55 - "[0.0.0-beta.38] - 2026-07-27"
Cohesion: 0.67
Nodes (3): [0.0.0-beta.40] - 2026-07-28, Feat, Fixed

### Community 58 - "[0.0.0-beta.5] - 2026-07-13"
Cohesion: 0.67
Nodes (3): [0.0.0-beta.46] - 2026-07-31, Feat, Removed

## Knowledge Gaps
- **103 isolated node(s):** `INVALID`, `BLOCK`, `ENTITY`, `PLAYER`, `BLOCK` (+98 more)
  These have ≤1 connection - possible missing edges or undocumented components.
- **120 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `CarryData` connect `CarryScript` to `.getCarryData`, `settings.gradle`, `ModelOverrideHandler.java`, `PlayerMixin.java`?**
  _High betweenness centrality (0.092) - this node is a cross-community bridge._
- **Why does `Changelog` connect `.tryPlaceEntity` to `EventBusSubscriber`, `SubscribeEvent`, `AvatarRenderState`, `CallbackInfo`, `Inject`, `Mixin`, `Logger`, `[0.0.0-beta.10] - 2026-07-13`, `[0.0.0-beta.12] - 2026-07-13`, `[0.0.0-beta.13] - 2026-07-13`, `[0.0.0-beta.14] - 2026-07-13`, `[0.0.0-beta.18] - 2026-07-13`, `[0.0.0-beta.19] - 2026-07-17`, `[0.0.0-beta.1] - 2026-07-13`, `[0.0.0-beta.20] - 2026-07-17`, `[0.0.0-beta.21] - 2026-07-17`, `[0.0.0-beta.23] - 2026-07-17`, `[0.0.0-beta.27] - 2026-07-18`, `[0.0.0-beta.28] - 2026-07-23`, `[0.0.0-beta.2] - 2026-07-13`, `[0.0.0-beta.30] - 2026-07-23`, `[0.0.0-beta.31] - 2026-07-24`, `[0.0.0-beta.32] - 2026-07-25`, `[0.0.0-beta.33] - 2026-07-25`, `[0.0.0-beta.37] - 2026-07-27`, `[0.0.0-beta.38] - 2026-07-27`, `[0.0.0-beta.3] - 2026-07-13`, `[0.0.0-beta.4] - 2026-07-13`, `[0.0.0-beta.5] - 2026-07-13`, `[0.0.0-beta.6] - 2026-07-13`, `[0.0.0-beta.7] - 2026-07-13`, `[0.0.0-beta.8] - 2026-07-13`, `[0.0.0-beta.9] - 2026-07-13`, `BooleanValue`, `DoubleValue`, `IntValue`, `BlockPos`, `BlockState`?**
  _High betweenness centrality (0.022) - this node is a cross-community bridge._
- **What connects `INVALID`, `BLOCK`, `ENTITY` to the rest of the system?**
  _103 weakly-connected nodes found - possible documentation gaps or missing edges._
- **Should `.getCarryData` be split into smaller, more focused modules?**
  _Cohesion score 0.06923076923076923 - nodes in this community are weakly interconnected._
- **Should `CarryScript` be split into smaller, more focused modules?**
  _Cohesion score 0.08502024291497975 - nodes in this community are weakly interconnected._
- **Should `CarryMechanics.java` be split into smaller, more focused modules?**
  _Cohesion score 0.09103840682788052 - nodes in this community are weakly interconnected._