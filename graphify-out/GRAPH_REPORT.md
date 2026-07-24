# Graph Report - .  (2026-07-24)

## Corpus Check
- cluster-only mode — file stats not available

## Summary
- 370 nodes · 756 edges · 26 communities (22 shown, 4 thin omitted)
- Extraction: 94% EXTRACTED · 6% INFERRED · 0% AMBIGUOUS · INFERRED: 48 edges (avg confidence: 0.8)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `ab74996c`
- Run `git rev-parse HEAD` and compare to check if the graph is stale.
- Run `graphify update .` after code changes (no API cost).

## Community Hubs (Navigation)
- .getCarryData
- CarryData
- ClientboundStartRidingOtherPlayerPacket
- CarryMechanics.java
- .tryPlaceEntity
- CarryScript
- AvatarExtractorMixin.java
- CarryingItemRenderLayer.java
- Community 8
- .setCarryData
- LivingEntityRendererMixin.java
- ModelOverrideHandler.java
- ListHandler
- CarryMechanicsClient.java
- PlayerMixin.java
- CarryKeybinds.java
- ClientEvents.java
- InventoryMixin.java
- EntityMixin.java
- gradlew
- GamestageCompat
- PoseStack
- Entity
- SubmitNodeCollector

## God Nodes (most connected - your core abstractions)
1. `CarryData` - 38 edges
2. `CarryScript` - 16 edges
3. `ClientboundStartRidingOtherPlayerPacket` - 14 edges
4. `ClientboundSyncScriptsPacket` - 13 edges
5. `PlacementHandler` - 10 edges
6. `ClientboundStartRidingPacket` - 10 edges
7. `CarryMechanics` - 9 edges
8. `PickupHandler` - 9 edges
9. `CommonEvents` - 9 edges
10. `PickupCondition` - 9 edges

## Surprising Connections (you probably didn't know these)
- `CarryMechanics` --references--> `CarryData`  [EXTRACTED]
  src/main/java/com/skd/carrymechanics/CarryMechanics.java → src/main/java/com/skd/carrymechanics/carry/CarryData.java
- `CarryData` --references--> `CarryScript`  [EXTRACTED]
  src/main/java/com/skd/carrymechanics/carry/CarryData.java → src/main/java/com/skd/carrymechanics/scripting/CarryScript.java
- `PacketIds` --references--> `ClientboundStartRidingOtherPlayerPacket`  [EXTRACTED]
  src/main/java/com/skd/carrymechanics/PacketIds.java → src/main/java/com/skd/carrymechanics/networking/ClientboundStartRidingOtherPlayerPacket.java
- `PacketIds` --references--> `ClientboundStartRidingPacket`  [EXTRACTED]
  src/main/java/com/skd/carrymechanics/PacketIds.java → src/main/java/com/skd/carrymechanics/networking/ClientboundStartRidingPacket.java
- `PacketIds` --references--> `ClientboundSyncScriptsPacket`  [EXTRACTED]
  src/main/java/com/skd/carrymechanics/PacketIds.java → src/main/java/com/skd/carrymechanics/networking/ClientboundSyncScriptsPacket.java

## Import Cycles
- None detected.

## Communities (26 total, 4 thin omitted)

### Community 0 - ".getCarryData"
Cohesion: 0.11
Nodes (21): AttackEntityEvent, BreakSpeed, EntityInteract, EntityLeaveLevelEvent, Post, RegisterCommandsEvent, RightClickBlock, ServerLevel (+13 more)

### Community 1 - "CarryData"
Cohesion: 0.08
Nodes (25): BlockEntity, CommandDispatcher, CommandSourceStack, FriendlyByteBuf, MapCodec, Provider, CarryData, CarryType (+17 more)

### Community 2 - "ClientboundStartRidingOtherPlayerPacket"
Cohesion: 0.09
Nodes (25): CustomPacketPayload, ResourceManager, ClientboundStartRidingOtherPlayerPacket, IPayloadContext, Override, RegistryFriendlyByteBuf, StreamCodec, Type (+17 more)

### Community 3 - "CarryMechanics.java"
Cohesion: 0.10
Nodes (21): Builder, DeferredRegister, IEventBus, ModConfigSpec, RegisterPayloadHandlersEvent, ServerStartingEvent, ConfigAccess, BooleanValue (+13 more)

### Community 4 - ".tryPlaceEntity"
Cohesion: 0.25
Nodes (10): BlockPlaceContext, Direction, BlockPos, BlockState, Entity, Property, ServerPlayer, Vec3 (+2 more)

### Community 5 - "CarryScript"
Cohesion: 0.18
Nodes (16): CarryScript, Codec, ServerPlayer, ObjectType, BLOCK, ENTITY, ScriptConditions, ScriptEffects (+8 more)

### Community 6 - "AvatarExtractorMixin.java"
Cohesion: 0.17
Nodes (12): Avatar, ICarryOnRenderState, CarryData, AvatarExtractorMixin, AvatarRenderState, CallbackInfo, Inject, Mixin (+4 more)

### Community 7 - "CarryingItemRenderLayer.java"
Cohesion: 0.18
Nodes (14): Context, PlayerModel, RenderLayer, RenderLayerParent, CarryingItemRenderLayer, AvatarRenderState, Logger, Override (+6 more)

### Community 8 - "Community 8"
Cohesion: 0.19
Nodes (8): BlockState, EntityType, ServerPlayer, PickupCondition, BlockState, Entity, EntityType, PickupConditionHandler

### Community 9 - ".setCarryData"
Cohesion: 0.17
Nodes (10): CarryDataManager, Player, CarryMechanicsAccess, AttachmentType, Logger, EntityRendererMixin, CallbackInfoReturnable, Entity (+2 more)

### Community 10 - "LivingEntityRendererMixin.java"
Cohesion: 0.29
Nodes (11): CameraRenderState, Entity, LivingEntity, LivingEntityRenderState, CallbackInfo, Inject, Logger, Mixin (+3 more)

### Community 11 - "ModelOverrideHandler.java"
Cohesion: 0.31
Nodes (6): Item, Block, ItemStack, ModelOverride, ModelOverrideHandler, ModCompat

### Community 12 - "ListHandler"
Cohesion: 0.42
Nodes (5): Block, Entity, EntityType, ListHandler, TagKey

### Community 13 - "CarryMechanicsClient.java"
Cohesion: 0.36
Nodes (6): FMLClientSetupEvent, CarryMechanicsClient, EventBusSubscriber, Mod, ModContainer, SubscribeEvent

### Community 14 - "PlayerMixin.java"
Cohesion: 0.43
Nodes (6): ItemEntity, CallbackInfoReturnable, Inject, ItemStack, Mixin, PlayerMixin

### Community 15 - "CarryKeybinds.java"
Cohesion: 0.48
Nodes (5): KeyMapping, RegisterKeyMappingsEvent, CarryKeybinds, EventBusSubscriber, SubscribeEvent

### Community 16 - "ClientEvents.java"
Cohesion: 0.38
Nodes (4): Pre, ClientEvents, EventBusSubscriber, SubscribeEvent

### Community 17 - "InventoryMixin.java"
Cohesion: 0.48
Nodes (5): InventoryMixin, CallbackInfo, Inject, Mixin, Player

### Community 18 - "EntityMixin.java"
Cohesion: 0.53
Nodes (4): EntityMixin, CallbackInfoReturnable, Inject, Mixin

### Community 19 - "gradlew"
Cohesion: 0.83
Nodes (3): gradlew script, die(), warn()

## Knowledge Gaps
- **6 isolated node(s):** `INVALID`, `BLOCK`, `ENTITY`, `PLAYER`, `BLOCK` (+1 more)
  These have ≤1 connection - possible missing edges or undocumented components.
- **4 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `CarryData` connect `CarryData` to `.getCarryData`, `CarryMechanics.java`, `.tryPlaceEntity`, `CarryScript`, `.setCarryData`, `ClientEvents.java`?**
  _High betweenness centrality (0.191) - this node is a cross-community bridge._
- **Why does `ClientboundStartRidingOtherPlayerPacket` connect `ClientboundStartRidingOtherPlayerPacket` to `.getCarryData`, `CarryMechanics.java`, `.tryPlaceEntity`?**
  _High betweenness centrality (0.081) - this node is a cross-community bridge._
- **Why does `CarryScript` connect `CarryScript` to `.getCarryData`, `CarryData`, `ClientboundStartRidingOtherPlayerPacket`?**
  _High betweenness centrality (0.066) - this node is a cross-community bridge._
- **What connects `INVALID`, `BLOCK`, `ENTITY` to the rest of the system?**
  _6 weakly-connected nodes found - possible documentation gaps or missing edges._
- **Should `.getCarryData` be split into smaller, more focused modules?**
  _Cohesion score 0.10570824524312897 - nodes in this community are weakly interconnected._
- **Should `CarryData` be split into smaller, more focused modules?**
  _Cohesion score 0.07822410147991543 - nodes in this community are weakly interconnected._
- **Should `ClientboundStartRidingOtherPlayerPacket` be split into smaller, more focused modules?**
  _Cohesion score 0.09146341463414634 - nodes in this community are weakly interconnected._