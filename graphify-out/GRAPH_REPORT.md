# Graph Report - 26.2  (2026-08-01)

## Corpus Check
- 64 files · ~123,110 words
- Verdict: corpus is large enough that graph structure adds value.

## Summary
- 460 nodes · 809 edges · 73 communities (30 shown, 43 thin omitted)
- Extraction: 93% EXTRACTED · 7% INFERRED · 0% AMBIGUOUS · INFERRED: 55 edges (avg confidence: 0.8)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `d2bf122f`
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
- .tryPickUpBlock
- [0.0.0-beta.17] - 2026-07-13
- AvatarExtractorMixin.java
- CarryKeybinds.java
- CarryDataSyncHandler
- [0.0.0-beta.34] - 2026-07-25
- Player
- Block
- Entity
- EntityType
- Block
- ItemStack
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
1. `CarryData` - 49 edges
2. `CarryScript` - 15 edges
3. `ClientboundSyncScriptsPacket` - 13 edges
4. `CarryRenderHelper` - 11 edges
5. `Flujo de trabajo — Carry Mechanics (NeoForge)` - 11 edges
6. `PlacementHandler` - 10 edges
7. `ClientboundStartRidingOtherPlayerPacket` - 10 edges
8. `ClientboundStartRidingPacket` - 10 edges
9. `Carry Mechanics` - 10 edges
10. `CarryMechanics` - 9 edges

## Surprising Connections (you probably didn't know these)
- `CarryMechanics` --references--> `CarryData`  [EXTRACTED]
  src/main/java/com/skd/carrymechanics/CarryMechanics.java → src/main/java/com/skd/carrymechanics/carry/CarryData.java
- `CarryData` --references--> `CarryScript`  [EXTRACTED]
  src/main/java/com/skd/carrymechanics/carry/CarryData.java → src/main/java/com/skd/carrymechanics/scripting/CarryScript.java
- `CarryDataSyncHandler` --references--> `CarryData`  [EXTRACTED]
  src/main/java/com/skd/carrymechanics/carry/CarryDataSyncHandler.java → src/main/java/com/skd/carrymechanics/carry/CarryData.java
- `PlayerRenderStateMixin` --references--> `CarryData`  [EXTRACTED]
  src/main/java/com/skd/carrymechanics/mixin/PlayerRenderStateMixin.java → src/main/java/com/skd/carrymechanics/carry/CarryData.java
- `PacketIds` --references--> `ClientboundStartRidingOtherPlayerPacket`  [EXTRACTED]
  src/main/java/com/skd/carrymechanics/PacketIds.java → src/main/java/com/skd/carrymechanics/networking/ClientboundStartRidingOtherPlayerPacket.java

## Import Cycles
- None detected.

## Communities (73 total, 43 thin omitted)

### Community 0 - ".getCarryData"
Cohesion: 0.10
Nodes (18): AttackEntityEvent, BlockPlaceContext, BreakSpeed, CommandDispatcher, CommandSourceStack, Direction, EntityInteract, EntityLeaveLevelEvent (+10 more)

### Community 1 - "ClientboundStartRidingOtherPlayerPacket"
Cohesion: 0.17
Nodes (11): Buenas prácticas, Commits (Conventional Commits), Convenciones de nomenclatura, Específico del mod, Estructura del proyecto, Flujo de trabajo — Carry Mechanics (NeoForge), Flujo por tarea, Idioma (+3 more)

### Community 3 - ".tryPickUpBlock"
Cohesion: 0.18
Nodes (10): Carry Mechanics, Commands, Configuration, Controls, Features, Installation, License, Overview (+2 more)

### Community 4 - "CarryScript"
Cohesion: 0.14
Nodes (13): HumanoidRenderState, ModelPart, ICarryOnRenderState, Player, HumanoidModelMixin, CallbackInfo, Inject, Mixin (+5 more)

### Community 5 - "AvatarExtractorMixin.java"
Cohesion: 0.43
Nodes (6): ItemEntity, CallbackInfoReturnable, Inject, ItemStack, Mixin, PlayerMixin

### Community 6 - ".tryPlaceEntity"
Cohesion: 0.20
Nodes (9): [0.0.0-beta.1] - 2026-07-31, [0.0.0-beta.2] - 2026-07-31, [1.0.0] - 2026-08-01, [1.0.1] - 2026-08-01, Changelog, Fix, Fix, Initial Port (+1 more)

### Community 7 - "CarryMechanics.java"
Cohesion: 0.08
Nodes (27): CustomPacketPayload, NetworkHelper, ResourceManager, ServerLevel, ClientboundStartRidingOtherPlayerPacket, IPayloadContext, Override, RegistryFriendlyByteBuf (+19 more)

### Community 8 - "CarryingItemRenderLayer.java"
Cohesion: 0.19
Nodes (13): Context, PlayerModel, RenderLayer, RenderLayerParent, CarryingItemRenderLayer, AvatarRenderState, Override, PoseStack (+5 more)

### Community 9 - "PickupCondition"
Cohesion: 0.18
Nodes (14): CarryScript, ObjectType, BLOCK, ENTITY, ScriptConditions, ScriptEffects, ScriptObject, ScriptRender (+6 more)

### Community 10 - "CarryConfig"
Cohesion: 0.31
Nodes (5): Builder, ConfigAccess, CarryConfig, ConfigData, ModConfigSpec

### Community 11 - "LivingEntityRendererMixin.java"
Cohesion: 0.23
Nodes (10): LivingEntity, LivingEntityRenderState, CallbackInfo, Inject, Mixin, LivingEntityMixin, CallbackInfo, Inject (+2 more)

### Community 12 - "ModelOverrideHandler.java"
Cohesion: 0.28
Nodes (6): CarryRenderHelper, BlockState, Entity, ItemStack, Player, PoseStack

### Community 15 - "PlayerMixin.java"
Cohesion: 0.83
Nodes (3): gradlew script, die(), warn()

### Community 16 - "CarryKeybinds.java"
Cohesion: 0.12
Nodes (18): DeferredRegister, IEventBus, KeyMapping, RegisterKeyMappingsEvent, RegisterPayloadHandlersEvent, ServerStartingEvent, CarryMechanicsAccess, AttachmentType (+10 more)

### Community 17 - "InventoryMixin.java"
Cohesion: 0.22
Nodes (8): CurseForge, Game Versions (IDs), Mod, Project Variables — Carry Mechanics, Rama, Token de subida (Python), Variables para el script de subida (curseforge-upload.ps1), Versiones

### Community 19 - "CarryDataSyncHandler"
Cohesion: 0.09
Nodes (20): BlockEntity, InventoryMixin, MapCodec, Provider, CarryData, CarryType, BLOCK, ENTITY (+12 more)

### Community 23 - "settings.gradle"
Cohesion: 0.22
Nodes (9): Pre, RenderHandEvent, CarriedObjectRender, Player, PoseStack, SubmitNodeCollector, ClientEvents, EventBusSubscriber (+1 more)

### Community 37 - ".tryPickUpBlock"
Cohesion: 0.48
Nodes (5): EntityRendererMixin, CallbackInfoReturnable, Entity, Inject, Mixin

### Community 38 - "[0.0.0-beta.17] - 2026-07-13"
Cohesion: 0.50
Nodes (3): CLAUDE.md — carry_mechanics (26.2), Prioridad de instrucciones, Workflow del mod

### Community 39 - "AvatarExtractorMixin.java"
Cohesion: 0.43
Nodes (6): Avatar, AvatarExtractorMixin, AvatarRenderState, CallbackInfo, Inject, Mixin

### Community 40 - "CarryKeybinds.java"
Cohesion: 0.25
Nodes (8): BlockPos, BlockState, Entity, Level, Property, ServerPlayer, Vec3, PickupHandler

### Community 42 - "CarryDataSyncHandler"
Cohesion: 0.83
Nodes (3): FriendlyByteBuf, CarryDataSyncHandler, StreamCodec

### Community 53 - "[0.0.0-beta.34] - 2026-07-25"
Cohesion: 0.36
Nodes (4): ModelOverride, ModelOverrideHandler, ModCompat, Item

## Knowledge Gaps
- **39 isolated node(s):** `INVALID`, `BLOCK`, `ENTITY`, `PLAYER`, `ConfigAccess` (+34 more)
  These have ≤1 connection - possible missing edges or undocumented components.
- **43 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `CarryData` connect `CarryDataSyncHandler` to `.getCarryData`, `CarryScript`, `CarryKeybinds.java`, `PickupCondition`, `CarryDataSyncHandler`, `ModelOverrideHandler.java`, `CarryKeybinds.java`, `settings.gradle`?**
  _High betweenness centrality (0.251) - this node is a cross-community bridge._
- **Why does `CarryScript` connect `PickupCondition` to `CarryKeybinds.java`, `.getCarryData`, `CarryDataSyncHandler`, `CarryMechanics.java`?**
  _High betweenness centrality (0.052) - this node is a cross-community bridge._
- **Why does `CarryMechanics` connect `CarryKeybinds.java` to `CarryDataSyncHandler`?**
  _High betweenness centrality (0.042) - this node is a cross-community bridge._
- **What connects `INVALID`, `BLOCK`, `ENTITY` to the rest of the system?**
  _39 weakly-connected nodes found - possible documentation gaps or missing edges._
- **Should `.getCarryData` be split into smaller, more focused modules?**
  _Cohesion score 0.0971322849213691 - nodes in this community are weakly interconnected._
- **Should `CarryScript` be split into smaller, more focused modules?**
  _Cohesion score 0.13538461538461538 - nodes in this community are weakly interconnected._
- **Should `CarryMechanics.java` be split into smaller, more focused modules?**
  _Cohesion score 0.08282828282828283 - nodes in this community are weakly interconnected._