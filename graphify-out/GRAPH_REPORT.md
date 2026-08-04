# Graph Report - 26.2  (2026-08-04)

## Corpus Check
- 70 files · ~124,418 words
- Verdict: corpus is large enough that graph structure adds value.

## Summary
- 476 nodes · 707 edges · 125 communities (33 shown, 92 thin omitted)
- Extraction: 92% EXTRACTED · 8% INFERRED · 0% AMBIGUOUS · INFERRED: 57 edges (avg confidence: 0.8)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `bfda233d`
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
- build.gradle
- settings.gradle
- Entity
- Inject
- Logger
- Mixin
- [0.0.0-beta.10] - 2026-07-13
- AvatarExtractorMixin.java
- 0.0.0-beta.2.md
- CarryDataSyncHandler
- 1.0.0.md
- 1.0.1.md
- AttachmentType
- Logger
- Mod
- [0.0.0-beta.34] - 2026-07-25
- ModContainer
- SubscribeEvent
- EventBusSubscriber
- SubscribeEvent
- BlockState
- Entity
- ItemStack
- Player
- PoseStack
- project_description.md
- SubscribeEvent
- EventBusSubscriber
- SubscribeEvent
- CallbackInfo
- Inject
- Mixin
- CallbackInfoReturnable
- Entity
- Inject
- Mixin
- CallbackInfo
- Inject
- Mixin
- CallbackInfo
- Inject
- Mixin
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
- Player
- Type
- IPayloadContext
- Override
- RegistryFriendlyByteBuf
- StreamCodec
- Type
- Block
- ItemStack
- Type
- BlockPos
- BlockState
- CompoundTag
- Entity
- Level
- ServerPlayer
- EventBusSubscriber
- Mod
- ModContainer
- SubscribeEvent
- CallbackInfoReturnable
- Inject
- Mixin
- CallbackInfo
- Inject
- Mixin
- Player
- CustomPacketPayload
- BlockState
- EntityType
- ServerPlayer
- BlockState
- Entity
- EntityType
- Codec
- ServerPlayer

## God Nodes (most connected - your core abstractions)
1. `CarryData` - 45 edges
2. `CarryRenderHelper` - 11 edges
3. `Flujo de trabajo — Carry Mechanics (NeoForge)` - 11 edges
4. `ClientboundSyncScriptsPacket` - 10 edges
5. `CarryScript` - 10 edges
6. `Changelog` - 10 edges
7. `Carry Mechanics` - 10 edges
8. `ListHandler` - 9 edges
9. `PickupHandler` - 9 edges
10. `PlacementHandler` - 9 edges

## Surprising Connections (you probably didn't know these)
- `CarryData` --references--> `CarryScript`  [EXTRACTED]
  src/main/java/com/skd/carrymechanics/carry/CarryData.java → src/main/java/com/skd/carrymechanics/scripting/CarryScript.java
- `PlayerRenderStateMixin` --references--> `CarryData`  [EXTRACTED]
  src/main/java/com/skd/carrymechanics/mixin/PlayerRenderStateMixin.java → src/main/java/com/skd/carrymechanics/carry/CarryData.java
- `PacketIds` --references--> `ClientboundStartRidingOtherPlayerPacket`  [EXTRACTED]
  src/main/java/com/skd/carrymechanics/PacketIds.java → src/main/java/com/skd/carrymechanics/networking/ClientboundStartRidingOtherPlayerPacket.java
- `PacketIds` --references--> `ClientboundStartRidingPacket`  [EXTRACTED]
  src/main/java/com/skd/carrymechanics/PacketIds.java → src/main/java/com/skd/carrymechanics/networking/ClientboundStartRidingPacket.java
- `PacketIds` --references--> `ClientboundSyncScriptsPacket`  [EXTRACTED]
  src/main/java/com/skd/carrymechanics/PacketIds.java → src/main/java/com/skd/carrymechanics/networking/ClientboundSyncScriptsPacket.java

## Import Cycles
- None detected.

## Communities (125 total, 92 thin omitted)

### Community 0 - ".getCarryData"
Cohesion: 0.10
Nodes (14): EntityInteract, CarryMechanicsAccess, LivingEntityMixin, ScriptManager, CarryDataManager, Post, Property, BlockPos (+6 more)

### Community 1 - "ClientboundStartRidingOtherPlayerPacket"
Cohesion: 0.17
Nodes (11): Buenas prácticas, Commits (Conventional Commits), Convenciones de nomenclatura, Específico del mod, Estructura del proyecto, Flujo de trabajo — Carry Mechanics (NeoForge), Flujo por tarea, Idioma (+3 more)

### Community 3 - ".tryPickUpBlock"
Cohesion: 0.17
Nodes (11): BlockPlaceContext, Direction, EntityRendererMixin, InventoryMixin, RightClickBlock, BlockPos, BlockState, Entity (+3 more)

### Community 4 - "CarryScript"
Cohesion: 0.21
Nodes (10): LivingEntityRendererMixin, HumanoidRenderState, LivingEntity, LivingEntityRenderState, ModelPart, HumanoidModelMixin, CallbackInfo, Inject (+2 more)

### Community 5 - "AvatarExtractorMixin.java"
Cohesion: 0.21
Nodes (6): CarryMechanicsRenderState, Player, Mixin, Override, Player, PlayerRenderStateMixin

### Community 6 - ".tryPlaceEntity"
Cohesion: 0.10
Nodes (19): [0.0.0-beta.1] - 2026-07-31, [0.0.0-beta.2] - 2026-07-31, [1.0.0] - 2026-08-01, [1.0.1] - 2026-08-01, [1.0.2] - 2026-08-02, [1.0.3] - 2026-08-02, [1.0.4] - 2026-08-02, [1.0.5] - 2026-08-04 (+11 more)

### Community 7 - "CarryMechanics.java"
Cohesion: 0.09
Nodes (15): CustomPacketPayload, DeferredRegister, CarryMechanics, ClientboundStartRidingOtherPlayerPacket, ClientboundStartRidingPacket, ClientboundSyncScriptsPacket, PacketIds, ScriptReloadListener (+7 more)

### Community 8 - "CarryingItemRenderLayer.java"
Cohesion: 0.22
Nodes (10): Context, AvatarRendererMixin, PlayerModel, RenderLayer, RenderLayerParent, CarryingItemRenderLayer, AvatarRenderState, Override (+2 more)

### Community 9 - "PickupCondition"
Cohesion: 0.31
Nodes (8): CarryScript, ObjectType, BLOCK, ENTITY, ScriptConditions, ScriptEffects, ScriptObject, ScriptRender

### Community 10 - "CarryConfig"
Cohesion: 0.31
Nodes (5): Builder, ConfigAccess, CarryConfig, ConfigData, ModConfigSpec

### Community 11 - "LivingEntityRendererMixin.java"
Cohesion: 0.18
Nodes (10): Carry Mechanics, Commands, Configuration, Controls, Features, Installation, License, Overview (+2 more)

### Community 12 - "ModelOverrideHandler.java"
Cohesion: 0.22
Nodes (8): CurseForge, Game Versions (IDs), Mod, Project Variables — Carry Mechanics, Rama, Token de subida (Python), Variables para el script de subida (curseforge-upload.ps1), Versiones

### Community 13 - "ListHandler"
Cohesion: 0.38
Nodes (5): Block, Entity, EntityType, ListHandler, TagKey

### Community 15 - "PlayerMixin.java"
Cohesion: 0.15
Nodes (8): AttackEntityEvent, BreakSpeed, CommandDispatcher, CommandSourceStack, EntityLeaveLevelEvent, CommandCarryMechanics, CommonEvents, RegisterCommandsEvent

### Community 16 - "CarryKeybinds.java"
Cohesion: 0.60
Nodes (3): CarryKeybinds, KeyMapping, RegisterKeyMappingsEvent

### Community 17 - "InventoryMixin.java"
Cohesion: 0.50
Nodes (3): CLAUDE.md — carry_mechanics (26.2), Prioridad de instrucciones, Workflow del mod

### Community 19 - "CarryDataSyncHandler"
Cohesion: 0.09
Nodes (21): BlockEntity, FriendlyByteBuf, CarryDataSyncHandler, MapCodec, Provider, CarryData, CarryType, BLOCK (+13 more)

### Community 22 - "build.gradle"
Cohesion: 0.83
Nodes (3): gradlew script, die(), warn()

### Community 23 - "settings.gradle"
Cohesion: 0.13
Nodes (8): CarryRenderHelper, ClientEvents, Pre, RenderHandEvent, CarriedObjectRender, Player, PoseStack, SubmitNodeCollector

### Community 39 - "AvatarExtractorMixin.java"
Cohesion: 0.43
Nodes (6): Avatar, AvatarExtractorMixin, AvatarRenderState, CallbackInfo, Inject, Mixin

### Community 53 - "[0.0.0-beta.34] - 2026-07-25"
Cohesion: 0.31
Nodes (4): ModelOverride, ModelOverrideHandler, ModCompat, Item

## Knowledge Gaps
- **44 isolated node(s):** `INVALID`, `BLOCK`, `ENTITY`, `PLAYER`, `ConfigAccess` (+39 more)
  These have ≤1 connection - possible missing edges or undocumented components.
- **92 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `CarryData` connect `CarryDataSyncHandler` to `.getCarryData`, `.tryPickUpBlock`, `AvatarExtractorMixin.java`, `PickupCondition`, `PlayerMixin.java`, `settings.gradle`?**
  _High betweenness centrality (0.082) - this node is a cross-community bridge._
- **Why does `CarryMechanics` connect `CarryMechanics.java` to `CarryDataSyncHandler`?**
  _High betweenness centrality (0.035) - this node is a cross-community bridge._
- **Why does `CarryScript` connect `PickupCondition` to `.getCarryData`, `CarryDataSyncHandler`, `PlayerMixin.java`?**
  _High betweenness centrality (0.024) - this node is a cross-community bridge._
- **What connects `INVALID`, `BLOCK`, `ENTITY` to the rest of the system?**
  _44 weakly-connected nodes found - possible documentation gaps or missing edges._
- **Should `.getCarryData` be split into smaller, more focused modules?**
  _Cohesion score 0.10384068278805121 - nodes in this community are weakly interconnected._
- **Should `.tryPlaceEntity` be split into smaller, more focused modules?**
  _Cohesion score 0.1 - nodes in this community are weakly interconnected._
- **Should `CarryMechanics.java` be split into smaller, more focused modules?**
  _Cohesion score 0.09103840682788052 - nodes in this community are weakly interconnected._