# Changelog


## [Unreleased]

### Fixed

- **README: la descripcion del origen del mod era incoherente con su propio `LICENSE`.** Decia
  *"Inspired by the classic Carry On mod, rewritten from scratch"*, mientras que el `LICENSE` declara
  que es una **obra derivada modificada**, que **retiene el copyright original** de Tschipp y
  PurpliciousCow, y que publica la fuente correspondiente completa como exige la LGPL. Afirmar
  "reescrito desde cero" mientras el `LICENSE` dice "derivada" no es una posicion coherente.
  Ahora las tres ramas NeoForge/Fabric dicen lo mismo que el `LICENSE`: fork modificado, no copia
  directa.

## [Unreleased]

### Fixed

- **`mod_license` ausente en `gradle.properties`.** Las otras cuatro ramas de este mod declaran
  `LGPL-3.0-or-later` y el `LICENSE` de esta rama dice lo mismo, pero el metadato del mod no lo
  declaraba, asi que el JAR no indicaba licencia. Anadido para que coincida con el fichero.

---

## [Unreleased]

### Change

- **Workflow de GitHub**: eliminado `.github/workflows/build.yml` (el workflow de ejemplo de la plantilla de Fabric); ya no se publica en el snapshot público ni se ejecuta en GitHub.
- **Enlaces del mod**: `homepage` y `sources` de `fabric.mod.json` apuntan ahora al repositorio público de GitHub (`github.com/codex-skd/carry-mechanics`) en lugar de `fabricmc.net` y del GitLab privado.
- **Créditos**: `fabric.mod.json` lista a Tschipp y PurpliciousCow como autores originales de Carry On (`contributors`).
- **Licencia**: el enlace al código fuente completo de `LICENSE` apunta ahora al repositorio público de GitHub (`github.com/codex-skd/carry-mechanics`) en lugar del GitLab privado.

## [1.0.2] - 2026-08-12

### Change

- **Nombre de JAR con versión del cargador**: el artefacto ahora se compila como `carry_mechanics-26.2-fabric-0.19.3-1.0.2.jar` (se añade la versión de cargador/NeoForge al nombre del archivo). Empaquetado y documentación; sin cambios de funcionalidad.

## [1.0.1] - 2026-08-02

### Refactor
- Interfaz `ICarryOnRenderState` → `CarryMechanicsRenderState` (residuo del fork "Carry On"). Sin cambios funcionales.

## [1.0.0] - 2026-08-02

### Stable Release

- Primera versión estable del port de Fabric para Minecraft 26.2 / Fabric Loader 0.19.3 / Fabric API 0.156.0
- Sin cambios funcionales respecto a `0.0.0-beta.1`; solo bump de versión (`0.0.0-beta.1` → `1.0.0`) tras dar por estable el set de funcionalidades del port

## [0.0.0-beta.1] - 2026-08-02

### Initial Fabric Port

- Port del mod a **Fabric 26.2** (Fabric Loader 0.19.3 / Fabric API 0.156.0), partiendo del código NeoForge 26.2 (`1.0.3`)
- Funcionalidad equivalente a NeoForge: recoger/cargar/colocar bloques (con tile entities) y entidades, apilado de entidades, whitelist/blacklist por datapack tags, scripting por datapack, comandos y 25+ opciones de configuración
- Sustituciones de APIs de NeoForge a Fabric:
  - Attachments → mapa `CarryData` por UUID (`CarryDataManager`) + sincronización con `ClientboundSyncCarryDataPacket`
  - `ModConfigSpec` (TOML) → config JSON (`config/carry_mechanics.json`)
  - `RenderHandEvent` → mixin `ItemInHandRendererMixin`
  - Eventos NeoForge → callbacks de Fabric API (UseBlock/UseEntity/Attack/PlayerBlockBreak/ServerTick, etc.)
  - Red (payloads `CustomPacketPayload`) → `PayloadTypeRegistry.clientboundPlay()` + `ClientPlayNetworking`
  - Keybinds → `KeyMappingHelper.registerKeyMapping`
  - Datapack sync → `SimpleSynchronousResourceReloadListener` + `SYNC_DATA_PACK_CONTENTS`
- Incluye los fixes de render de NeoForge 1.0.2/1.0.3: render del objeto transportado en `PoseStack` local (sin crash de pose stack) y asignación de entity id a la copia deserializada (la entidad transportada se ve correctamente)
