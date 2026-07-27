# Graph Report - .  (2026-07-25)

## Corpus Check
- cluster-only mode — file stats not available

## Summary
- 375 nodes · 752 edges · 34 communities (23 shown, 11 thin omitted)
- Extraction: 94% EXTRACTED · 6% INFERRED · 0% AMBIGUOUS · INFERRED: 48 edges (avg confidence: 0.8)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `f328394c`
- Run `git rev-parse HEAD` and compare to check if the graph is stale.
- Run `graphify update .` after code changes (no API cost).

## Community Hubs (Navigation)
- .getCarryData
- ClientboundStartRidingOtherPlayerPacket
- CarryData
- .tryPickUpBlock
- CarryScript
- AvatarExtractorMixin.java
- CarryMechanics.java
- CarryingItemRenderLayer.java
- Community 8
- CarryConfig
- LivingEntityRendererMixin.java
- ModelOverrideHandler.java
- ListHandler
- CarryMechanicsClient.java
- PlayerMixin.java
- CarryKeybinds.java
- ClientEvents.java
- EntityRendererMixin.java
- InventoryMixin.java
- EntityMixin.java
- gradlew
- GamestageCompat
- PoseStack
- AvatarRenderState
- Logger
- Override
- CallbackInfo
- Entity
- Inject
- Logger
- Mixin
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

## Communities (34 total, 11 thin omitted)

### Community 0 - ".getCarryData"
Cohesion: 0.09
Nodes (25): AttackEntityEvent, BlockPlaceContext, BreakSpeed, CommandDispatcher, CommandSourceStack, Direction, EntityInteract, EntityLeaveLevelEvent (+17 more)

### Community 1 - "ClientboundStartRidingOtherPlayerPacket"
Cohesion: 0.09
Nodes (25): CustomPacketPayload, ResourceManager, ClientboundStartRidingOtherPlayerPacket, IPayloadContext, Override, RegistryFriendlyByteBuf, StreamCodec, Type (+17 more)

### Community 2 - "CarryData"
Cohesion: 0.10
Nodes (22): BlockEntity, FriendlyByteBuf, MapCodec, Provider, CarryData, CarryType, BLOCK, ENTITY (+14 more)

### Community 3 - ".tryPickUpBlock"
Cohesion: 0.19
Nodes (11): ServerLevel, BlockPos, BlockState, Entity, Level, Property, ServerPlayer, Vec3 (+3 more)

### Community 4 - "CarryScript"
Cohesion: 0.18
Nodes (16): CarryScript, Codec, ServerPlayer, ObjectType, BLOCK, ENTITY, ScriptConditions, ScriptEffects (+8 more)

### Community 5 - "AvatarExtractorMixin.java"
Cohesion: 0.17
Nodes (12): Avatar, ICarryOnRenderState, CarryData, AvatarExtractorMixin, AvatarRenderState, CallbackInfo, Inject, Mixin (+4 more)

### Community 6 - "CarryMechanics.java"
Cohesion: 0.16
Nodes (13): DeferredRegister, IEventBus, RegisterPayloadHandlersEvent, ServerStartingEvent, CarryMechanicsAccess, AttachmentType, Logger, CarryMechanics (+5 more)

### Community 7 - "CarryingItemRenderLayer.java"
Cohesion: 0.19
Nodes (13): AvatarRenderState, Context, Override, PlayerModel, RenderLayer, RenderLayerParent, CarryingItemRenderLayer, PoseStack (+5 more)

### Community 8 - "Community 8"
Cohesion: 0.19
Nodes (8): BlockState, EntityType, ServerPlayer, PickupCondition, BlockState, Entity, EntityType, PickupConditionHandler

### Community 9 - "CarryConfig"
Cohesion: 0.17
Nodes (11): Builder, ModConfigSpec, ConfigAccess, BooleanValue, DoubleValue, IntValue, CarryConfig, ConfigData (+3 more)

### Community 10 - "LivingEntityRendererMixin.java"
Cohesion: 0.31
Nodes (10): CallbackInfo, CameraRenderState, Entity, Inject, LivingEntity, LivingEntityRenderState, Mixin, PoseStack (+2 more)

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

### Community 17 - "EntityRendererMixin.java"
Cohesion: 0.48
Nodes (5): EntityRendererMixin, CallbackInfoReturnable, Entity, Inject, Mixin

### Community 18 - "InventoryMixin.java"
Cohesion: 0.48
Nodes (5): InventoryMixin, CallbackInfo, Inject, Mixin, Player

### Community 19 - "EntityMixin.java"
Cohesion: 0.53
Nodes (4): EntityMixin, CallbackInfoReturnable, Inject, Mixin

### Community 20 - "gradlew"
Cohesion: 0.83
Nodes (3): gradlew script, die(), warn()

## Knowledge Gaps
- **6 isolated node(s):** `INVALID`, `BLOCK`, `ENTITY`, `PLAYER`, `BLOCK` (+1 more)
  These have ≤1 connection - possible missing edges or undocumented components.
- **11 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `CarryData` connect `CarryData` to `.getCarryData`, `.tryPickUpBlock`, `CarryScript`, `CarryMechanics.java`, `ClientEvents.java`?**
  _High betweenness centrality (0.183) - this node is a cross-community bridge._
- **Why does `ClientboundStartRidingOtherPlayerPacket` connect `ClientboundStartRidingOtherPlayerPacket` to `.getCarryData`, `.tryPickUpBlock`, `CarryMechanics.java`?**
  _High betweenness centrality (0.079) - this node is a cross-community bridge._
- **Why does `CarryScript` connect `CarryScript` to `.getCarryData`, `ClientboundStartRidingOtherPlayerPacket`, `CarryData`, `.tryPickUpBlock`?**
  _High betweenness centrality (0.063) - this node is a cross-community bridge._
- **What connects `INVALID`, `BLOCK`, `ENTITY` to the rest of the system?**
  _6 weakly-connected nodes found - possible documentation gaps or missing edges._
- **Should `.getCarryData` be split into smaller, more focused modules?**
  _Cohesion score 0.09158249158249158 - nodes in this community are weakly interconnected._
- **Should `ClientboundStartRidingOtherPlayerPacket` be split into smaller, more focused modules?**
  _Cohesion score 0.09146341463414634 - nodes in this community are weakly interconnected._
- **Should `CarryData` be split into smaller, more focused modules?**
  _Cohesion score 0.09523809523809523 - nodes in this community are weakly interconnected._