# Changelog

## [0.0.0-beta.2] - 2026-07-31

### Fix
- Requisito de NeoForge bajado de `26.2.0.41-beta` a `26.2.0.37-beta` (`neo_version` en gradle.properties) para que el mod funcione con la build de NeoForge realmente disponible/instalada en 26.2 a día de hoy. Sin cambios de código: compila igual contra ambas builds

## [0.0.0-beta.1] - 2026-07-31

### Initial Port

- Port inicial del mod a Minecraft 26.2 / NeoForge 26.2.0.41-beta, partiendo del código de la versión 26.1.2 (`1.0.0`)
- `CarryData.getEntity()`: `EntityType.create(input, level, EntitySpawnReason.BUCKET)` reemplazado por `EntityType.create(input, level, new EntitySpawnRequest(EntitySpawnReason.BUCKET, false))` — en 26.2 la sobrecarga estática `create(ValueInput, Level, EntitySpawnReason)` se eliminó en favor de `create(ValueInput, Level, EntitySpawnRequest)`. Único cambio necesario para compilar; el resto del código (mixins, render, networking, attachments) compiló sin cambios contra la nueva API
