# Graph Report - 26.2  (2026-08-02)

## Corpus Check
- 66 files · ~85,501 words
- Verdict: corpus is large enough that graph structure adds value.

## Summary
- 449 nodes · 935 edges · 32 communities (31 shown, 1 thin omitted)
- Extraction: 94% EXTRACTED · 6% INFERRED · 0% AMBIGUOUS · INFERRED: 54 edges (avg confidence: 0.8)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `56d1777a`
- Run `git rev-parse HEAD` and compare to check if the graph is stale.
- Run `graphify update .` after code changes (no API cost).

## Community Hubs (Navigation)
- .getCarryData
- ClientboundSyncCarryDataPacket
- CarryData
- .tryPickUpBlock
- CarryRenderHelper
- ICarryOnRenderState
- CarryDataManager.java
- CarryConfig
- CarryingItemRenderLayer.java
- PickupCondition
- LivingEntityRendererMixin.java
- Flujo de trabajo — Carry Mechanics (Fabric)
- ModelOverrideHandler.java
- Carry Mechanics
- ListHandler
- PlayerMixin.java
- CurseForge — Variables del proyecto
- EntityRendererMixin.java
- InventoryMixin.java
- LivingEntityRendererAccessor.java
- EntityMixin.java
- [0.0.0-beta.1] - 2026-08-02
- CLAUDE.md — carry_mechanics (26.2)
- gradlew
- GamestageCompat
- .getCarryData
- .broadcast

## God Nodes (most connected - your core abstractions)
1. `CarryData` - 56 edges
2. `CarryScript` - 16 edges
3. `ClientboundSyncCarryDataPacket` - 14 edges
4. `ClientboundSyncScriptsPacket` - 14 edges
5. `Flujo de trabajo — Carry Mechanics (Fabric)` - 12 edges
6. `CarryRenderHelper` - 11 edges
7. `PlacementHandler` - 10 edges
8. `ClientboundStartRidingOtherPlayerPacket` - 10 edges
9. `ClientboundStartRidingPacket` - 10 edges
10. `Carry Mechanics` - 10 edges

## Surprising Connections (you probably didn't know these)
- `CarryData` --references--> `CarryScript`  [EXTRACTED]
  src/main/java/com/skd/carrymechanics/carry/CarryData.java → src/main/java/com/skd/carrymechanics/scripting/CarryScript.java
- `CarryDataManager` --references--> `CarryData`  [EXTRACTED]
  src/main/java/com/skd/carrymechanics/carry/CarryDataManager.java → src/main/java/com/skd/carrymechanics/carry/CarryData.java
- `ClientboundSyncCarryDataPacket` --references--> `CarryData`  [EXTRACTED]
  src/main/java/com/skd/carrymechanics/networking/ClientboundSyncCarryDataPacket.java → src/main/java/com/skd/carrymechanics/carry/CarryData.java
- `PlayerRenderStateMixin` --implements--> `ICarryOnRenderState`  [EXTRACTED]
  src/client/java/com/skd/carrymechanics/mixin/PlayerRenderStateMixin.java → src/client/java/com/skd/carrymechanics/client/render/ICarryOnRenderState.java
- `PlayerRenderStateMixin` --references--> `CarryData`  [EXTRACTED]
  src/client/java/com/skd/carrymechanics/mixin/PlayerRenderStateMixin.java → src/main/java/com/skd/carrymechanics/carry/CarryData.java

## Import Cycles
- None detected.

## Communities (32 total, 1 thin omitted)

### Community 0 - ".getCarryData"
Cohesion: 0.16
Nodes (13): BlockPlaceContext, CommandDispatcher, CommandSourceStack, Direction, BlockPos, BlockState, Entity, Property (+5 more)

### Community 1 - "ClientboundSyncCarryDataPacket"
Cohesion: 0.07
Nodes (36): ClientModInitializer, CustomPacketPayload, KeyMapping, ModInitializer, ResourceManager, CarryMechanicsClient, Override, CarryKeybinds (+28 more)

### Community 2 - "CarryData"
Cohesion: 0.07
Nodes (25): MapCodec, Provider, ICarryOnRenderState, Player, Mixin, Override, Player, PlayerRenderStateMixin (+17 more)

### Community 3 - ".tryPickUpBlock"
Cohesion: 0.18
Nodes (16): CarryScript, Codec, ServerPlayer, ObjectType, BLOCK, ENTITY, ScriptConditions, ScriptEffects (+8 more)

### Community 4 - "CarryRenderHelper"
Cohesion: 0.19
Nodes (12): HumanoidRenderState, ModelPart, CarryRenderHelper, BlockState, Entity, Player, PoseStack, HumanoidModelMixin (+4 more)

### Community 5 - "ICarryOnRenderState"
Cohesion: 0.43
Nodes (6): Avatar, AvatarExtractorMixin, AvatarRenderState, CallbackInfo, Inject, Mixin

### Community 6 - "CarryDataManager.java"
Cohesion: 0.18
Nodes (12): LocalPlayer, CarriedObjectRender, Player, PoseStack, SubmitNodeCollector, ItemStack, ItemInHandRendererMixin, CallbackInfo (+4 more)

### Community 7 - "CarryConfig"
Cohesion: 0.19
Nodes (6): Gson, CarryMechanicsAccess, Logger, ConfigAccess, CarryConfig, ConfigData

### Community 8 - "CarryingItemRenderLayer.java"
Cohesion: 0.19
Nodes (13): Context, PlayerModel, RenderLayer, RenderLayerParent, CarryingItemRenderLayer, AvatarRenderState, Override, PoseStack (+5 more)

### Community 9 - "PickupCondition"
Cohesion: 0.19
Nodes (8): BlockState, EntityType, ServerPlayer, PickupCondition, BlockState, Entity, EntityType, PickupConditionHandler

### Community 10 - "LivingEntityRendererMixin.java"
Cohesion: 0.23
Nodes (10): LivingEntity, LivingEntityRenderState, CallbackInfo, Inject, Mixin, LivingEntityRendererMixin, CallbackInfo, Inject (+2 more)

### Community 11 - "Flujo de trabajo — Carry Mechanics (Fabric)"
Cohesion: 0.15
Nodes (12): Buenas prácticas, Commits (Conventional Commits), Convenciones de nomenclatura, Específico del mod, Estructura del proyecto, Flujo de trabajo — Carry Mechanics (Fabric), Flujo por tarea, Idioma (+4 more)

### Community 12 - "ModelOverrideHandler.java"
Cohesion: 0.31
Nodes (6): Item, Block, ItemStack, ModelOverride, ModelOverrideHandler, ModCompat

### Community 13 - "Carry Mechanics"
Cohesion: 0.18
Nodes (10): Carry Mechanics, Commands, Configuration, Controls, Features, Installation, License, Overview (+2 more)

### Community 14 - "ListHandler"
Cohesion: 0.42
Nodes (5): Block, Entity, EntityType, ListHandler, TagKey

### Community 15 - "PlayerMixin.java"
Cohesion: 0.43
Nodes (6): ItemEntity, CallbackInfoReturnable, Inject, ItemStack, Mixin, PlayerMixin

### Community 16 - "CurseForge — Variables del proyecto"
Cohesion: 0.29
Nodes (6): CurseForge — Variables del proyecto, Game Versions (IDs), Proyecto, Rama, Tokens, Versión actual

### Community 17 - "EntityRendererMixin.java"
Cohesion: 0.48
Nodes (5): EntityRendererMixin, CallbackInfoReturnable, Entity, Inject, Mixin

### Community 18 - "InventoryMixin.java"
Cohesion: 0.48
Nodes (5): InventoryMixin, CallbackInfo, Inject, Mixin, Player

### Community 19 - "LivingEntityRendererAccessor.java"
Cohesion: 0.53
Nodes (4): Invoker, Mixin, RenderLayer, LivingEntityRendererAccessor

### Community 20 - "EntityMixin.java"
Cohesion: 0.53
Nodes (4): EntityMixin, CallbackInfoReturnable, Inject, Mixin

### Community 21 - "[0.0.0-beta.1] - 2026-08-02"
Cohesion: 0.33
Nodes (5): [0.0.0-beta.1] - 2026-08-02, [1.0.0] - 2026-08-02, Changelog, Initial Fabric Port, Stable Release

### Community 22 - "CLAUDE.md — carry_mechanics (26.2)"
Cohesion: 0.50
Nodes (3): CLAUDE.md — carry_mechanics (26.2), Prioridad de instrucciones, Workflow del mod

### Community 23 - "gradlew"
Cohesion: 0.83
Nodes (3): gradlew script, die(), warn()

### Community 29 - ".getCarryData"
Cohesion: 0.10
Nodes (25): BlockHitResult, EntityHitResult, InteractionHand, InteractionResult, MinecraftServer, ServerLevel, CarryDataManager, Player (+17 more)

### Community 30 - ".broadcast"
Cohesion: 0.32
Nodes (3): Entry, CarryDataSyncHandler, ServerPlayer

## Knowledge Gaps
- **36 isolated node(s):** `INVALID`, `BLOCK`, `ENTITY`, `PLAYER`, `ConfigAccess` (+31 more)
  These have ≤1 connection - possible missing edges or undocumented components.
- **1 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `CarryData` connect `CarryData` to `.getCarryData`, `ClientboundSyncCarryDataPacket`, `.tryPickUpBlock`, `CarryRenderHelper`, `CarryDataManager.java`, `.getCarryData`, `.broadcast`?**
  _High betweenness centrality (0.257) - this node is a cross-community bridge._
- **Why does `ClientboundSyncCarryDataPacket` connect `ClientboundSyncCarryDataPacket` to `CarryData`, `.broadcast`?**
  _High betweenness centrality (0.065) - this node is a cross-community bridge._
- **Why does `CarryScript` connect `.tryPickUpBlock` to `.getCarryData`, `ClientboundSyncCarryDataPacket`, `CarryData`, `.getCarryData`?**
  _High betweenness centrality (0.051) - this node is a cross-community bridge._
- **What connects `INVALID`, `BLOCK`, `ENTITY` to the rest of the system?**
  _36 weakly-connected nodes found - possible documentation gaps or missing edges._
- **Should `ClientboundSyncCarryDataPacket` be split into smaller, more focused modules?**
  _Cohesion score 0.06829573934837092 - nodes in this community are weakly interconnected._
- **Should `CarryData` be split into smaller, more focused modules?**
  _Cohesion score 0.06980392156862746 - nodes in this community are weakly interconnected._
- **Should `.getCarryData` be split into smaller, more focused modules?**
  _Cohesion score 0.10034013605442177 - nodes in this community are weakly interconnected._