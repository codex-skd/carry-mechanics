# Carry Mechanics (1.20.1 Forge) — Changelog

Rama `minecraft/1.20.1/forge-47.4.23/production` (MinecraftForge, no NeoForge). Derivada de la rama 1.21.1 (v1.0.1); el historial de 1.21.1 se conserva más abajo como referencia.

## [0.0.0-beta.1] - 2026-09-24

### Port

- **Port completo a Minecraft 1.20.1 / MinecraftForge 47.4.23 (Java 17)** desde la rama 1.21.1 v1.0.1, con paridad total de funcionalidad (recoger/colocar bloques y entidades, apilado, peso/lentitud, tags de whitelist/blacklist, scripts, overrides de modelo, comando `/carrymechanics`, render en primera y tercera persona, mixins).
- **Datos del jugador**: el `AttachmentType` de NeoForge pasa a una capability de Forge (`carry_mechanics:carry_data`, `CarryDataCapability` + `CarryDataHolder`). Se copia en clones que no son por muerte (vuelta del End), igual que el attachment sin `copyOnDeath`.
- **Sincronización manual** (sustituye al `.sync()` automático del attachment): nuevo paquete `ClientboundSyncCarryDataPacket`, enviado al jugador y a quien lo rastrea en cada `setCarryData`, al entrar, al reaparecer, al cambiar de dimensión y en `StartTracking`.
- **Red**: payloads de NeoForge pasan a un `SimpleChannel` de Forge (`NetworkHelper`). La lógica cliente de los paquetes vive en `ClientPacketHandlers`, invocada vía `DistExecutor`, para no cargar clases de cliente en servidor dedicado.
- **Config**: `ModConfigSpec` pasa a `ForgeConfigSpec`, mismas claves y valores por defecto. Nueva pantalla `CarryConfigScreen`, porque Forge 1.20.1 no genera pantalla de config automática como NeoForge.
- **Eventos**: `@Mod.EventBusSubscriber` con bus explícito; ticks vía `TickEvent` con filtro de fase; `Event.Result.DENY` en lugar de `TriState.FALSE`.
- **APIs vanilla 1.20.1**: `new ResourceLocation(...)`, `saveWithId()`/`loadStatic()` sin registros, `DataResult.getOrThrow(false, ...)`, `SoundEvents.ARMOR_EQUIP_GENERIC` sin `Holder`, `List.get(0)`.
- **Build**: ModDevGradle `legacyforge` 2.0.91 con MinecraftForge 1.20.1-47.4.23, Parchment 2023.09.03, Gradle 8.14.5, Mixin AP + refmap (`carry_mechanics.refmap.json`), `mods.toml`, `pack_format` 15, tags en `tags/blocks/` y `tags/entity_types/`.

---

# Historial heredado — Carry Mechanics (1.21.1)

## [1.0.1] - 2026-09-15

### Fixed

- **Server crash when picking up an Ars Nouveau familiar entity** (e.g. Ars Elemental's Siren
  Familiar). `CarryData.getEntity()` reconstructed a detached, orphan copy of the carried entity
  from NBT via `EntityType.create(...)` just so `PickupHandler.potionLevel()` could read its
  bounding box for the weight/slowness calculation. `EntityType.create(...)` runs the entity's
  `readAdditionalSaveData()`, and Ars Nouveau's `FamiliarEntity` calls `setCustomName()` ->
  `syncTag()` there, which assumes the entity is already registered as a familiar tied to its
  owner's capability data — the orphan copy never was, so the capability lookup returned `null`
  and Ars Nouveau threw an `NPE`, crashing the dedicated server. Fix, entirely inside
  `carry_mechanics`: `setEntity()` now caches the entity's width/height from the live entity
  before removing it, so `potionLevel()` never needs to deserialize the entity; `getEntity()`
  also wraps `EntityType.create(...)` in a `try/catch` so any third-party crash during
  deserialization falls back to the existing placeholder-entity recovery path instead of
  propagating. No Ars Nouveau code touched.

## [1.0.0] - 2026-09-09

First stable release for **Minecraft 1.21.1 / NeoForge 21.1.249** (Java 21). Consolidates the
`0.0.0-beta.1` → `0.0.0-beta.3` line with no further code changes. The 1.21.1 build has been
running server-side in the *(Develop) Mystical Realms* modded-server pack.

### Summary of the beta line

- **beta.1** — initial port from the 26.2 fork tree (`com.skd.carrymechanics` identity), all
  26.2-only Minecraft/NeoForge API reverted to 1.21.1 using upstream Carry On 1.21.1 (v2.2.6,
  Tschipp / PurpliciousCow, LGPL-3.0) as the API reference. The fork's own gameplay dynamics
  (crouch-to-pick-up, grab rules, carry conditions, entity stacking, scripting, model overrides,
  movement penalties, inventory locking) are preserved unchanged.
- **beta.2** — fixed a client crash on world load: `AvatarExtractorMixin` `@Inject` targeted
  `PlayerRenderer.render(Player, …)`, but in 1.21.1 that method takes `AbstractClientPlayer`;
  the descriptor and handler parameter were corrected.
- **beta.3** — bundled the Spanish (`es_es`) locale: all 32 keys (creative-tab name, «Cargar»
  keybind, every config-screen label).

### Notes

- No gameplay change relative to `0.0.0-beta.3`. Verified: `./gradlew clean build` is green.
- Same CurseForge project as the 26.2 line (`1608286`); pick the file that matches your Minecraft
  version.

## [0.0.0-beta.3] - 2026-09-08

### Added

- **Spanish (`es_es`) locale**: full translation of all 32 keys (creative-tab name, "Cargar"
  keybind, every config-screen label). Taken from the Mystical Realms Translation & Fixes
  resource-pack QA pass so it ships with the mod. No code change.

## [0.0.0-beta.2] - 2026-09-01

### Fixed

- **Client crash on world load** (`InvalidInjectionException` → hard crash during the resource
  reload of the loading screen). `AvatarExtractorMixin` `@Inject` targeted
  `PlayerRenderer.render(Player, …)`, but in 1.21.1 that method takes `AbstractClientPlayer`
  (`PlayerRenderer extends LivingEntityRenderer<AbstractClientPlayer, …>`). Zero matching targets
  + `defaultRequire: 1` aborted mixin application and crashed the client. Target descriptor and
  handler parameter corrected to `net.minecraft.client.player.AbstractClientPlayer`. This was a
  leftover from reverting the 26.2 `AvatarRenderer#extractRenderState` render-state code to the
  1.21.1 immediate renderer; the descriptor was never compile-validated (no mixin AP refmap).

## [0.0.0-beta.1] - 2026-09-01

### Added

- **Initial port to Minecraft 1.21.1 / NeoForge 21.1.249** (Java 21). Strategy: the 26.2 fork tree
  (43 files, `com.skd.carrymechanics` identity) with all 26.2-only Minecraft/NeoForge API reverted
  to 1.21.1, using upstream Carry On 1.21.1 (v2.2.6, Tschipp / PurpliciousCow, LGPL-3.0) as the
  API reference. The fork's own gameplay dynamics (crouch-to-pick-up, grab rules, carry
  conditions, entity stacking, scripting, model overrides, movement penalties, inventory locking)
  are preserved unchanged.

### Technical

- API reversion (~200 compile errors) delegated to `opencode-go/mimo-v2.5`, operator finish:
  `Identifier` → `ResourceLocation`; the 26.2 **render-state architecture**
  (`renderer.entity.state.*`, `AvatarRenderState`, `PlayerRenderState`) has no 1.21.1 equivalent —
  the carried-object render layer was reworked to the 1.21.1 `RenderLayer<AbstractClientPlayer,
  PlayerModel<AbstractClientPlayer>>` / direct-entity renderer API; `PlayerRenderStateMixin` and
  `CarryMechanicsRenderState` (26.2 render-state carriers) removed.
- `Entity#startRiding(Entity, boolean, boolean)` → `startRiding(Entity, boolean)`;
  `animal.equine.*` → `animal.horse.*`; `MapCodec` → `Codec` for `CarryData`;
  `MobEffects.SLOWNESS` → `MOVEMENT_SLOWDOWN`; `Inventory#getSelectedSlot()/setSelectedSlot()` →
  the `selected` field; `TamableAnimal#getOwnerReference()` → `getOwnerUUID()`;
  `Mob#dropLeash(boolean)` → `dropLeash(boolean, boolean)`; `net.minecraft.util.TriState` →
  `net.neoforged.neoforge.common.util.TriState`; `KeyMapping.Category.MISC` →
  `"key.categories.misc"`; `net.minecraft.client.model.player.PlayerModel` →
  `net.minecraft.client.model.PlayerModel`.
- `carry_mechanics.mixins.json`: `compatibilityLevel` `JAVA_21`; `PlayerRenderStateMixin` dropped.
- Licensing corrected: `mod_license` → `LGPL-3.0-or-later`, `LICENSE` added (attribution to
  Tschipp / PurpliciousCow + full LGPL v3 text). Carry On is LGPL v3; the fork must stay LGPL.
- Build: `net.neoforged.moddev` template retargeted to NeoForge 21.1.249 / Java 21;
  `modLoader`/`loaderVersion` in `neoforge.mods.toml`; mixin/MixinExtras support from moddev.
- Verified: `./gradlew build` OK; `./gradlew runServer` → `Done`, `carry_mechanics` loads, the
  four server-side mixins apply, 0 FATAL. Client render mixins are compile-checked only.
- Port detail: `docs/PORT_REPORT_1.21.1.md`.
