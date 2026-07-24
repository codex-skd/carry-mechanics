# Graph Report - .  (2026-07-24)

## Corpus Check
- cluster-only mode — file stats not available

## Summary
- 327 nodes · 692 edges · 21 communities (20 shown, 1 thin omitted)
- Extraction: 93% EXTRACTED · 7% INFERRED · 0% AMBIGUOUS · INFERRED: 50 edges (avg confidence: 0.8)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `e05b4717`
- Run `git rev-parse HEAD` and compare to check if the graph is stale.
- Run `graphify update .` after code changes (no API cost).

## Community Hubs (Navigation)
- Community 0
- Community 1
- Community 2
- Community 3
- Community 4
- Community 5
- Community 6
- Community 7
- Community 8
- Community 9
- Community 10
- Community 11
- Community 12
- Community 13
- Community 14
- Community 15
- Community 16
- Community 17
- Community 18

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

## Communities (21 total, 1 thin omitted)

### Community 0 - "Community 0"
Cohesion: 0.09
Nodes (25): CustomPacketPayload, ResourceManager, ClientboundStartRidingOtherPlayerPacket, IPayloadContext, Override, RegistryFriendlyByteBuf, StreamCodec, Type (+17 more)

### Community 1 - "Community 1"
Cohesion: 0.09
Nodes (22): BlockEntity, FriendlyByteBuf, MapCodec, Provider, CarryData, CarryType, BLOCK, ENTITY (+14 more)

### Community 2 - "Community 2"
Cohesion: 0.10
Nodes (21): Builder, DeferredRegister, IEventBus, ModConfigSpec, RegisterPayloadHandlersEvent, ServerStartingEvent, ConfigAccess, BooleanValue (+13 more)

### Community 3 - "Community 3"
Cohesion: 0.17
Nodes (12): EntityInteract, ServerLevel, BlockPos, BlockState, Entity, Level, Property, ServerPlayer (+4 more)

### Community 4 - "Community 4"
Cohesion: 0.12
Nodes (15): CarryDataManager, Player, CarryMechanicsAccess, AttachmentType, Logger, EntityRendererMixin, CallbackInfoReturnable, Entity (+7 more)

### Community 5 - "Community 5"
Cohesion: 0.23
Nodes (11): BlockPlaceContext, Direction, RightClickBlock, BlockPos, BlockState, Entity, Property, ServerPlayer (+3 more)

### Community 6 - "Community 6"
Cohesion: 0.18
Nodes (16): CarryScript, Codec, ServerPlayer, ObjectType, BLOCK, ENTITY, ScriptConditions, ScriptEffects (+8 more)

### Community 7 - "Community 7"
Cohesion: 0.16
Nodes (11): AttackEntityEvent, BreakSpeed, CommandDispatcher, CommandSourceStack, EntityLeaveLevelEvent, Post, RegisterCommandsEvent, CommandCarryMechanics (+3 more)

### Community 8 - "Community 8"
Cohesion: 0.19
Nodes (8): BlockState, EntityType, ServerPlayer, PickupCondition, BlockState, Entity, EntityType, PickupConditionHandler

### Community 9 - "Community 9"
Cohesion: 0.31
Nodes (10): CameraRenderState, LivingEntity, LivingEntityRenderState, PoseStack, CallbackInfo, Entity, Inject, Mixin (+2 more)

### Community 10 - "Community 10"
Cohesion: 0.31
Nodes (6): Item, Block, ItemStack, ModelOverride, ModelOverrideHandler, ModCompat

### Community 11 - "Community 11"
Cohesion: 0.42
Nodes (5): Block, Entity, EntityType, ListHandler, TagKey

### Community 12 - "Community 12"
Cohesion: 0.36
Nodes (6): FMLClientSetupEvent, CarryMechanicsClient, EventBusSubscriber, Mod, ModContainer, SubscribeEvent

### Community 13 - "Community 13"
Cohesion: 0.43
Nodes (6): ItemEntity, CallbackInfoReturnable, Inject, ItemStack, Mixin, PlayerMixin

### Community 14 - "Community 14"
Cohesion: 0.48
Nodes (5): KeyMapping, RegisterKeyMappingsEvent, CarryKeybinds, EventBusSubscriber, SubscribeEvent

### Community 15 - "Community 15"
Cohesion: 0.38
Nodes (4): Pre, ClientEvents, EventBusSubscriber, SubscribeEvent

### Community 16 - "Community 16"
Cohesion: 0.53
Nodes (4): EntityMixin, CallbackInfoReturnable, Inject, Mixin

### Community 17 - "Community 17"
Cohesion: 0.83
Nodes (3): gradlew script, die(), warn()

## Knowledge Gaps
- **6 isolated node(s):** `INVALID`, `BLOCK`, `ENTITY`, `PLAYER`, `BLOCK` (+1 more)
  These have ≤1 connection - possible missing edges or undocumented components.
- **1 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `CarryData` connect `Community 1` to `Community 2`, `Community 3`, `Community 4`, `Community 5`, `Community 6`, `Community 7`, `Community 15`?**
  _High betweenness centrality (0.201) - this node is a cross-community bridge._
- **Why does `ClientboundStartRidingOtherPlayerPacket` connect `Community 0` to `Community 2`, `Community 3`, `Community 5`?**
  _High betweenness centrality (0.098) - this node is a cross-community bridge._
- **Why does `CarryScript` connect `Community 6` to `Community 0`, `Community 1`, `Community 3`, `Community 7`?**
  _High betweenness centrality (0.065) - this node is a cross-community bridge._
- **What connects `INVALID`, `BLOCK`, `ENTITY` to the rest of the system?**
  _6 weakly-connected nodes found - possible documentation gaps or missing edges._
- **Should `Community 0` be split into smaller, more focused modules?**
  _Cohesion score 0.09146341463414634 - nodes in this community are weakly interconnected._
- **Should `Community 1` be split into smaller, more focused modules?**
  _Cohesion score 0.09102564102564102 - nodes in this community are weakly interconnected._
- **Should `Community 2` be split into smaller, more focused modules?**
  _Cohesion score 0.0967741935483871 - nodes in this community are weakly interconnected._