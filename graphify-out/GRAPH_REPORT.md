# Graph Report - 1.21.1  (2026-09-01)

## Corpus Check
- 63 files · ~125,363 words
- Verdict: corpus is large enough that graph structure adds value.

## Summary
- 461 nodes · 902 edges · 37 communities (36 shown, 1 thin omitted)
- Extraction: 94% EXTRACTED · 6% INFERRED · 0% AMBIGUOUS · INFERRED: 58 edges (avg confidence: 0.8)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `973f96d2`
- Run `git rev-parse HEAD` and compare to check if the graph is stale.
- Run `graphify update .` after code changes (no API cost).

## Community Hubs (Navigation)
- CarryData
- ClientboundSyncScriptsPacket
- .getCarryData
- CarryMechanics.java
- CarryRenderHelper
- CarryScript
- .isCarrying
- .draw
- CarryingItemRenderLayer.java
- PickupCondition
- CommonEvents.java
- API Reversion Patterns Applied
- Flujo de trabajo — Carry Mechanics (NeoForge)
- ListHandler
- HumanoidModelMixin.java
- Delegation brief — Carry Mechanics: finish the 1.21.1 / NeoForge 21.1.249 port
- CarryMechanicsClient.java
- .onRenderTail
- .onRenderHead
- Project Variables — Carry Mechanics (1.21.1)
- PlayerMixin.java
- CarryKeybinds.java
- InventoryMixin.java
- LivingEntityMixin.java
- Carry Mechanics
- EntityMixin.java
- [0.0.0-beta.1] - 2026-09-01
- CLAUDE.md — carry_mechanics (26.2)
- CarryDataSyncHandler
- gradlew
- GamestageCompat
- EntityRendererMixin.java

## God Nodes (most connected - your core abstractions)
1. `CarryData` - 46 edges
2. `CarryScript` - 16 edges
3. `ClientboundSyncScriptsPacket` - 13 edges
4. `CarryRenderHelper` - 11 edges
5. `Flujo de trabajo — Carry Mechanics (NeoForge)` - 11 edges
6. `ClientboundStartRidingOtherPlayerPacket` - 10 edges
7. `ClientboundStartRidingPacket` - 10 edges
8. `API Reversion Patterns Applied` - 10 edges
9. `CarryMechanics` - 9 edges
10. `ListHandler` - 9 edges

## Surprising Connections (you probably didn't know these)
- `CarryMechanics` --references--> `CarryData`  [EXTRACTED]
  src/main/java/com/skd/carrymechanics/CarryMechanics.java → src/main/java/com/skd/carrymechanics/carry/CarryData.java
- `CarryData` --references--> `CarryScript`  [EXTRACTED]
  src/main/java/com/skd/carrymechanics/carry/CarryData.java → src/main/java/com/skd/carrymechanics/scripting/CarryScript.java
- `CarryDataSyncHandler` --references--> `CarryData`  [EXTRACTED]
  src/main/java/com/skd/carrymechanics/carry/CarryDataSyncHandler.java → src/main/java/com/skd/carrymechanics/carry/CarryData.java
- `PacketIds` --references--> `ClientboundStartRidingOtherPlayerPacket`  [EXTRACTED]
  src/main/java/com/skd/carrymechanics/PacketIds.java → src/main/java/com/skd/carrymechanics/networking/ClientboundStartRidingOtherPlayerPacket.java
- `PacketIds` --references--> `ClientboundStartRidingPacket`  [EXTRACTED]
  src/main/java/com/skd/carrymechanics/PacketIds.java → src/main/java/com/skd/carrymechanics/networking/ClientboundStartRidingPacket.java

## Import Cycles
- None detected.

## Communities (37 total, 1 thin omitted)

### Community 0 - "CarryData"
Cohesion: 0.09
Nodes (23): BlockEntity, Provider, CarryData, CarryType, BLOCK, ENTITY, INVALID, PLAYER (+15 more)

### Community 1 - "ClientboundSyncScriptsPacket"
Cohesion: 0.09
Nodes (25): CustomPacketPayload, ResourceManager, ClientboundStartRidingOtherPlayerPacket, IPayloadContext, Override, RegistryFriendlyByteBuf, StreamCodec, Type (+17 more)

### Community 2 - ".getCarryData"
Cohesion: 0.12
Nodes (16): EntityInteract, Post, Property, CarryDataManager, Player, BlockPos, BlockState, Entity (+8 more)

### Community 3 - "CarryMechanics.java"
Cohesion: 0.08
Nodes (24): Builder, DeferredRegister, IEventBus, ModConfigSpec, RegisterPayloadHandlersEvent, ServerStartingEvent, CarryMechanicsAccess, AttachmentType (+16 more)

### Community 4 - "CarryRenderHelper"
Cohesion: 0.37
Nodes (5): CarryRenderHelper, BlockState, Entity, Player, PoseStack

### Community 5 - "CarryScript"
Cohesion: 0.18
Nodes (16): CarryScript, Codec, ServerPlayer, ObjectType, BLOCK, ENTITY, ScriptConditions, ScriptEffects (+8 more)

### Community 6 - ".isCarrying"
Cohesion: 0.26
Nodes (9): BlockPlaceContext, Direction, RightClickBlock, BlockPos, BlockState, Entity, ServerPlayer, Vec3 (+1 more)

### Community 7 - ".draw"
Cohesion: 0.33
Nodes (5): Pre, RenderHandEvent, ClientEvents, EventBusSubscriber, SubscribeEvent

### Community 8 - "CarryingItemRenderLayer.java"
Cohesion: 0.19
Nodes (13): Context, PlayerModel, RenderLayer, RenderLayerParent, CarryingItemRenderLayer, AbstractClientPlayer, MultiBufferSource, Override (+5 more)

### Community 9 - "PickupCondition"
Cohesion: 0.19
Nodes (8): BlockState, EntityType, ServerPlayer, PickupCondition, BlockState, Entity, EntityType, PickupConditionHandler

### Community 10 - "CommonEvents.java"
Cohesion: 0.13
Nodes (13): AttackEntityEvent, BreakSpeed, CommandDispatcher, CommandSourceStack, EntityLeaveLevelEvent, RegisterCommandsEvent, ServerLevel, CommandCarryMechanics (+5 more)

### Community 11 - "API Reversion Patterns Applied"
Cohesion: 0.12
Nodes (16): 1. `Identifier` → `ResourceLocation`, 2. `startRiding(Entity, boolean, boolean)` → `startRiding(Entity, boolean)` (3-arg → 2-arg), 3. `animal.equine.Horse` → `animal.horse.Horse`, 4. `MapCodec<CarryData>` → `Codec<CarryData>`, 5. 26.2 `CompoundTag` / serialization APIs → 1.21.1 equivalents, 6. `ProblemReporter` / `TagValueOutput` removal from `PickupHandler.java`, 7. Client render architecture: 26.2 render-state → 1.21.1 direct renderer API, 8. Mixin rework for 1.21.1 (no render-state architecture) (+8 more)

### Community 12 - "Flujo de trabajo — Carry Mechanics (NeoForge)"
Cohesion: 0.17
Nodes (11): Buenas prácticas, Commits (Conventional Commits), Convenciones de nomenclatura, Específico del mod, Estructura del proyecto, Flujo de trabajo — Carry Mechanics (NeoForge), Flujo por tarea, Idioma (+3 more)

### Community 13 - "ListHandler"
Cohesion: 0.38
Nodes (5): Block, Entity, EntityType, ListHandler, TagKey

### Community 14 - "HumanoidModelMixin.java"
Cohesion: 0.38
Nodes (7): ModelPart, HumanoidModelMixin, CallbackInfo, Inject, LivingEntity, Mixin, Unique

### Community 15 - "Delegation brief — Carry Mechanics: finish the 1.21.1 / NeoForge 21.1.249 port"
Cohesion: 0.22
Nodes (8): &#9888;&#65039; CRITICAL: gameplay dynamics differ from Carry On, Delegation brief — Carry Mechanics: finish the 1.21.1 / NeoForge 21.1.249 port, Deliverable, HARD CONSTRAINTS, Mission, Paths (all inside the work dir — sandbox blocks reads outside `--dir`), Scaffold already done (do NOT redo), TASK — make `src/main/java` compile on 1.21.1 (~200+ errors)

### Community 16 - "CarryMechanicsClient.java"
Cohesion: 0.36
Nodes (6): FMLClientSetupEvent, CarryMechanicsClient, EventBusSubscriber, Mod, ModContainer, SubscribeEvent

### Community 17 - ".onRenderTail"
Cohesion: 0.33
Nodes (7): AvatarExtractorMixin, AbstractClientPlayer, CallbackInfo, Inject, Mixin, MultiBufferSource, PoseStack

### Community 18 - ".onRenderHead"
Cohesion: 0.33
Nodes (7): CallbackInfo, Inject, LivingEntity, Mixin, MultiBufferSource, PoseStack, LivingEntityRendererMixin

### Community 19 - "Project Variables — Carry Mechanics (1.21.1)"
Cohesion: 0.25
Nodes (7): CurseForge, Nota post-subida (manual), Project Variables — Carry Mechanics (1.21.1), Rama, Repo GitLab, Tag, Variables para script (lectura automática)

### Community 20 - "PlayerMixin.java"
Cohesion: 0.43
Nodes (6): ItemEntity, CallbackInfoReturnable, Inject, ItemStack, Mixin, PlayerMixin

### Community 21 - "CarryKeybinds.java"
Cohesion: 0.48
Nodes (5): KeyMapping, RegisterKeyMappingsEvent, CarryKeybinds, EventBusSubscriber, SubscribeEvent

### Community 22 - "InventoryMixin.java"
Cohesion: 0.48
Nodes (5): InventoryMixin, CallbackInfo, Inject, Mixin, Player

### Community 23 - "LivingEntityMixin.java"
Cohesion: 0.31
Nodes (6): Item, Block, ItemStack, ModelOverride, ModelOverrideHandler, ModCompat

### Community 24 - "Carry Mechanics"
Cohesion: 0.33
Nodes (5): Carry Mechanics, Installation, License, Requirements, Status

### Community 25 - "EntityMixin.java"
Cohesion: 0.53
Nodes (4): EntityMixin, CallbackInfoReturnable, Inject, Mixin

### Community 26 - "[0.0.0-beta.1] - 2026-09-01"
Cohesion: 0.29
Nodes (6): [0.0.0-beta.1] - 2026-09-01, [0.0.0-beta.2] - 2026-09-01, Added, Carry Mechanics (1.21.1) — Changelog, Fixed, Technical

### Community 27 - "CLAUDE.md — carry_mechanics (26.2)"
Cohesion: 0.50
Nodes (3): CLAUDE.md — carry_mechanics (26.2), Prioridad de instrucciones, Workflow del mod

### Community 28 - "CarryDataSyncHandler"
Cohesion: 0.83
Nodes (3): FriendlyByteBuf, CarryDataSyncHandler, StreamCodec

### Community 29 - "gradlew"
Cohesion: 0.83
Nodes (3): gradlew script, die(), warn()

### Community 35 - "EntityRendererMixin.java"
Cohesion: 0.48
Nodes (5): EntityRendererMixin, CallbackInfoReturnable, Entity, Inject, Mixin

## Knowledge Gaps
- **52 isolated node(s):** `INVALID`, `BLOCK`, `ENTITY`, `PLAYER`, `BLOCK` (+47 more)
  These have ≤1 connection - possible missing edges or undocumented components.
- **1 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `CarryData` connect `CarryData` to `.getCarryData`, `CarryMechanics.java`, `CarryRenderHelper`, `CarryScript`, `.isCarrying`, `.draw`, `CommonEvents.java`, `CarryDataSyncHandler`?**
  _High betweenness centrality (0.256) - this node is a cross-community bridge._
- **Why does `CarryScript` connect `CarryScript` to `CarryData`, `ClientboundSyncScriptsPacket`, `CommonEvents.java`, `.getCarryData`?**
  _High betweenness centrality (0.052) - this node is a cross-community bridge._
- **What connects `INVALID`, `BLOCK`, `ENTITY` to the rest of the system?**
  _52 weakly-connected nodes found - possible documentation gaps or missing edges._
- **Should `CarryData` be split into smaller, more focused modules?**
  _Cohesion score 0.09042553191489362 - nodes in this community are weakly interconnected._
- **Should `ClientboundSyncScriptsPacket` be split into smaller, more focused modules?**
  _Cohesion score 0.09146341463414634 - nodes in this community are weakly interconnected._
- **Should `.getCarryData` be split into smaller, more focused modules?**
  _Cohesion score 0.12436974789915967 - nodes in this community are weakly interconnected._
- **Should `CarryMechanics.java` be split into smaller, more focused modules?**
  _Cohesion score 0.08253968253968254 - nodes in this community are weakly interconnected._