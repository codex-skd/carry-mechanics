# Carry Mechanics (1.21.1) — Changelog

Branch `minecraft/1.21.1/neoforge-21.1.249/production`. History independent of the 26.2 branch.

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
