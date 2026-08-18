# Changelog


## [1.0.8] - 2026-08-18

### Change

- **Actualización de NeoForge**: actualizado de 26.2.0.37-beta a 26.2.0.45-beta.
- **Nombre de JAR con versión del cargador**: el artefacto ahora se compila como `carry_mechanics-26.2-neoforge-26.2.0.45-beta-1.0.8.jar`.
- **Documentación del workflow**: actualizada `docs/WORKFLOW_CARRY_MECHANICS_26-2.md` para reflejar la nueva rama de trabajo.


## [1.0.7] - 2026-08-12

### Change

- **Nombre de JAR con versión del cargador**: el artefacto ahora se compila como `carry_mechanics-26.2-neoforge-26.2.0.37-beta-1.0.7.jar` (se añade la versión de cargador/NeoForge al nombre del archivo). Empaquetado y documentación; sin cambios de funcionalidad.

## [1.0.6] - 2026-08-04

### Fix
- Los goblins/hobgoblins del mod Warlockery (`warlockery:goblin`, `warlockery:hobgoblin`) no se podían cargar aunque el jugador quisiera transportarlos, ya que el filtro de mobs hostiles (`Enemy`/`MobCategory.MONSTER`) los bloqueaba igual que a cualquier mob hostil cuando `pickup_hostile_mobs` está desactivado. Se añade el nuevo tag de datos `carry_mechanics:hostile_pickup_whitelist` (entity_type) como excepción a ese filtro; `warlockery:goblin` y `warlockery:hobgoblin` están incluidos por defecto (marcados como no-requeridos, así que no falla si Warlockery no está instalado). El resto de mobs hostiles siguen bloqueados salvo que se active `pickup_hostile_mobs` o se añadan a ese mismo tag

## [1.0.5] - 2026-08-04

### Fix
- Bloques transportados con propiedad de orientación (`FACING`/`HORIZONTAL_FACING`/`AXIS`: barriles, hornos, dispensadores, observadores, troncos…) se colocaban siempre con la orientación que tenían al ser recogidos, ignorando hacia dónde miraba el jugador al colocarlos. `PlacementHandler.getPlacementState` calculaba correctamente la nueva orientación vía `getStateForPlacement(ctx)`, pero un bucle posterior reimponía incondicionalmente el valor original guardado para cualquier propiedad `Direction`/`Direction.Axis`, anulando el recálculo. Eliminado ese bucle: ahora la orientación se recalcula igual que en una colocación vanilla normal

## [1.0.4] - 2026-08-02

### Refactor
- Interfaz `ICarryOnRenderState` → `CarryMechanicsRenderState` (residuo del fork "Carry On"). Sin cambios funcionales.

## [1.0.3] - 2026-08-02

### Fix
- Entidades transportadas invisibles: la entidad deserializada (oveja, vaca, aldeano…) es una copia desacoplada que nunca entra en el nivel y, por tanto, no tenía asignado el ID de entidad. Al extraer el render state, `ItemModelResolver.updateForLiving()` llama a `entity.getId()`, que lanzaba `IllegalStateException: Tried to access entity ID before ID assignment`; el mod la tragaba y la entidad nunca se dibujaba (ni en primera ni en tercera persona), con el consiguiente lag por excepción por frame. Ahora a la copia de render se le asigna un ID único no-cero, así el mob transportado se renderiza correctamente

## [1.0.2] - 2026-08-02

### Fix
- Crash/`Pose stack not empty` al alternar a tercera persona (F5) mientras se transporta un objeto: el render de lo que llevas (bloques y entidades) se ejecutaba sobre el pose stack compartido del `LevelRenderer`; si el render lanzaba una excepción (algo frecuente en beta con el render de mobs), el stack quedaba desbalanceado y el siguiente frame crasheaba el cliente. Ahora se renderiza sobre un `PoseStack` local desechable, de modo que un fallo de render ya no puede corromper el stack del juego
- El error de render ya no se traga en silencio: se registra una vez por sesión en `logs/latest.log` con el stack trace real, para diagnosticar por qué un mob transportado no se dibuja

## [1.0.1] - 2026-08-01

### Fix
- Rendimiento: `CarryData.getEntity()` re-deserializaba el NBT completo de la entidad en cada llamada. En el cliente se ejecutaba dos veces por frame (render) y en el servidor una vez por tick (cálculo de lentitud), de modo que las entidades complejas (aldeanos con brain/inventario/ofertas, mobs de mods con IA pesada) saturaban el garbage collector y congelaban el juego. Ahora la entidad deserializada se cachea y se reutiliza hasta que cambia el NBT cargado o el nivel

## [1.0.0] - 2026-08-01

### Stable Release

- Primera versión estable del mod para Minecraft 26.2 / NeoForge 26.2.0.37-beta
- Sin cambios funcionales respecto a `0.0.0-beta.2`; solo bump de versión (`0.0.0-beta.2` → `1.0.0`) tras dar por estable el set de funcionalidades
- Requisito de NeoForge: `26.2.0.37-beta`

## [0.0.0-beta.2] - 2026-07-31

### Fix
- Requisito de NeoForge bajado de `26.2.0.41-beta` a `26.2.0.37-beta` (`neo_version` en gradle.properties) para que el mod funcione con la build de NeoForge realmente disponible/instalada en 26.2 a día de hoy. Sin cambios de código: compila igual contra ambas builds

## [0.0.0-beta.1] - 2026-07-31

### Initial Port

- Port inicial del mod a Minecraft 26.2 / NeoForge 26.2.0.41-beta, partiendo del código de la versión 26.1.2 (`1.0.0`)
- `CarryData.getEntity()`: `EntityType.create(input, level, EntitySpawnReason.BUCKET)` reemplazado por `EntityType.create(input, level, new EntitySpawnRequest(EntitySpawnReason.BUCKET, false))` — en 26.2 la sobrecarga estática `create(ValueInput, Level, EntitySpawnReason)` se eliminó en favor de `create(ValueInput, Level, EntitySpawnRequest)`. Único cambio necesario para compilar; el resto del código (mixins, render, networking, attachments) compiló sin cambios contra la nueva API
