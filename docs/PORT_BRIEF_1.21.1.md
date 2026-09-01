# Delegation brief — Carry Mechanics: finish the 1.21.1 / NeoForge 21.1.249 port

## Mission

`carry_mechanics` (our fork of **Carry On** by Tschipp / PurpliciousCow, LGPL-3.0)
currently exists only for Minecraft 26.2. We are creating a **Minecraft 1.21.1 /
NeoForge 21.1.249 / Java 21** version.

`src/main/java` currently holds the **26.2 fork's source, unchanged** (43 files,
`com.skd.carrymechanics` identity). It **does not compile on 1.21.1** —
`./gradlew compileJava` reports **~200+ errors**, essentially all from **26.2-only
Minecraft/NeoForge API** (the render-state architecture especially) that must be
reverted to its 1.21.1 form.

**Reference = upstream Carry On 1.21.1** (`temp/ref/carryon-common-java/` +
`temp/ref/carryon-neoforge-java/`, v2.2.6, MC 1.21.1, NeoForge 21.1.230). This is
the 1.21.1 version of the same mod family. Use it as the **API truth** — for each
broken symbol, find the equivalent in upstream's 1.21.1 code and use that form.

## &#9888;&#65039; CRITICAL: gameplay dynamics differ from Carry On

Our fork **changed Carry On's behaviour** — the crouch/sneak-to-pick-up mechanic,
which blocks/entities can be grabbed, the carry conditions, and the rendering. The
26.2 fork is the source of truth for **what the mod does**. Upstream Carry On 1.21.1
is the source of truth for **1.21.1 API only**. When a line in our tree differs
from upstream, assume it is a deliberate fork change and KEEP the behaviour —
only swap the 26.2 API tokens for their 1.21.1 equivalents. When in doubt, diff
`temp/ref/carry-mechanics-26.2-java` against `temp/ref/carryon-*-java` to see
which lines are fork changes (keep) vs 26.2-API (revert).

## Paths (all inside the work dir — sandbox blocks reads outside `--dir`)

| What | Path |
|---|---|
| **Work dir** (edit here) | `G:/Proyectos/Mods_Minecraft/carry_mechanics/neoforge/1.21.1` |
| Upstream Carry On 1.21.1 — Common module Java (**1.21.1 API truth**) | `temp/ref/carryon-common-java/tschipp/carryon/` |
| Upstream Carry On 1.21.1 — NeoForge module Java | `temp/ref/carryon-neoforge-java/tschipp/carryon/` |
| Upstream — resources | `temp/ref/carryon-common-resources/`, `temp/ref/carryon-neoforge-resources/` |
| Upstream — gradle.properties | `temp/ref/carryon-gradle.properties` |
| The **26.2 fork** — Java (behaviour + identity reference, NOT API) | `temp/ref/carry-mechanics-26.2-java/com/skd/carrymechanics/` |
| 26.2 fork — build.gradle | `temp/ref/carry-mechanics-26.2-build.gradle` |

`temp/` is gitignored. Upstream package root is `tschipp.carryon`; our identity is
`com.skd.carrymechanics`; upstream modid `carryon`, ours `carry_mechanics`; upstream
asset/data namespace `carryon:`, ours `carry_mechanics:`. Keep our identity.

## Scaffold already done (do NOT redo)

- `src/main/java` = 26.2 fork tree (renamed identity), 43 files.
- `src/main/resources` = 26.2 fork resources (already `carry_mechanics` namespace).
- `gradle.properties`: MC 1.21.1, range `[1.21.1,1.22)`, neo `21.1.249`,
  `loader_version_range=[1,)`, `mod_version=0.0.0-beta.1`, `mod_license=LGPL-3.0-or-later`.
- `build.gradle`: Java 21; `loader_version_range` in the `generateModMetadata` map.
- `src/main/resources/templates/META-INF/neoforge.mods.toml`: `modLoader="javafml"`
  kept; `loaderVersion` now `"${loader_version_range}"`.
- `src/main/resources/carry_mechanics.mixins.json`: `compatibilityLevel` now `JAVA_21`.
- `LICENSE` written (attribution + LGPL v3).

## HARD CONSTRAINTS

1. **Target API = Minecraft 1.21.1 + NeoForge 21.1.249 + Java 21.** Upstream Carry On
   1.21.1 is the API truth. Known 26.2 -> 1.21.1 reversions you WILL hit:
   - `net.minecraft.resources.Identifier` -> `net.minecraft.resources.ResourceLocation`.
   - **The 26.2 render-state architecture does not exist on 1.21.1.**
     `net.minecraft.client.renderer.entity.state.*` (e.g. `EntityRenderState`,
     `LivingEntityRenderState`, `PlayerRenderState`, `HumanoidRenderState`) and
     `net.minecraft.client.renderer.state.level.*` are 26.2. On 1.21.1 entity
     renderers/models take the entity directly (`render(T entity, float entityYaw,
     float partialTick, PoseStack, MultiBufferSource, int packedLight)` and
     `setupAnim(T entity, ...)`). Rework `CarryMechanicsRenderState`,
     `PlayerRenderStateMixin`, `AvatarExtractorMixin`, `AvatarRendererMixin`,
     `EntityRendererMixin`, `LivingEntityRendererMixin`, `HumanoidModelMixin`,
     `CarriedObjectRender`, `CarryRenderHelper`, `CarryingItemRenderLayer` to the
     1.21.1 renderer/model API exactly as upstream Carry On 1.21.1's client code
     does it (`temp/ref/carryon-common-java/tschipp/carryon/client/` +
     `carryon-neoforge-java/.../client/`). If our fork has an
     `Avatar*`/`PlayerRenderState*` mixin that has NO upstream 1.21.1 counterpart
     (because 26.2 renamed `Player*` -> `Avatar*`), retarget it to the 1.21.1
     `net.minecraft.client.model.PlayerModel` / `PlayerRenderer` /
     `AbstractClientPlayer` and rename accordingly; note it in the report.
   - `net.minecraft.client.model.player.*` (26.2) -> `net.minecraft.client.model.PlayerModel`
     / `HumanoidModel` (1.21.1).
   - `net.minecraft.world.entity.animal.equine.*` (26.2) ->
     `net.minecraft.world.entity.animal.horse.*` (1.21.1).
   - `Entity#startRiding(Entity, boolean, boolean)` (26.2, 3-arg) ->
     `startRiding(Entity, boolean)` (1.21.1, 2-arg). Drop the extra arg per upstream.
   - `Mob#dropLeash(...)` arg-list change -> match 1.21.1 signature.
   - `MapCodec` vs `Codec` for `CarryData` serialize/attachment registration ->
     the 1.21.1 form (`AttachmentType` builder, `Codec` not `MapCodec`).
   - `GuiGraphics`, tooltip, `Font`, key-mapping, payload/`StreamCodec`,
     `RegistryFriendlyByteBuf`, tag/`TagKey`, datapack-reload-listener APIs:
     1.21.1 signatures per upstream.
2. **Keep the 26.2 fork's behaviour and file set.** Do not re-add Carry On files
   the fork dropped; do not drop fork files. Match the mixin classes in
   `carry_mechanics.mixins.json` to the files actually present after the rework
   (update the json if a mixin is renamed/removed for a legit API reason — note it).
3. **Do NOT run git or gradle.** The operator builds and verifies.
4. **Do NOT bump the NeoForge version or add dependencies.** Mixin/MixinExtras
   support comes from the moddev plugin; do not add a raw
   `org.spongepowered:mixin` annotation processor.
5. License headers: preserve verbatim. Do not rename `Carry On` / `carryon` inside
   license headers or upstream-describing Javadoc.
6. All code / comments / your report: **English**.

## TASK — make `src/main/java` compile on 1.21.1 (~200+ errors)

Suggested order: `config/` -> `scripting/` -> `pickupcondition/` -> `carry/` ->
`networking/` -> `command/` -> `compat/` -> `events/` -> `mixin/` (non-client) ->
`client/render/` + `client/keybinds/` -> `mixin/` client render mixins -> the two
root classes (`CarryMechanics`, `CarryMechanicsClient`).

For each failing symbol: open the same-named / same-role file under
`temp/ref/carryon-common-java` or `carryon-neoforge-java`, apply the 1.21.1 API it
uses, keep our `com.skd.carrymechanics` identity and any deliberate fork behaviour.

## Deliverable

1. `src/main/java` correct for `./gradlew build` on NeoForge 21.1.249 by inspection.
2. `docs/PORT_REPORT_1.21.1.md` (English): every file modified / renamed / deleted
   with the reason; every 26.2 -> 1.21.1 API reversion pattern applied; anything
   where you guessed at the 1.21.1 API; any 26.2 behaviour you could not preserve.

Work only inside `G:/Proyectos/Mods_Minecraft/carry_mechanics/neoforge/1.21.1`.
