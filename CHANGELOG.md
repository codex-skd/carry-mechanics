# Changelog

## [0.0.0-beta.27] - 2026-07-17

### Fix
- Crash al iniciar por `LivingEntityRendererMixin`: firma del método corregida (`LivingEntityRenderState` + `CameraRenderState` en vez de `EntityRenderState` + `Object`)

## [0.0.0-beta.23] - 2026-07-17

### Fix
- Render del bloque: cambiado de `ItemStackRenderState` a `BlockModelRenderState.submitMultiLayer`

## [0.0.0-beta.22] - 2026-07-17

### Fix
- Render del bloque cargado: implementadas las transformaciones exactas del mod original (rotaciones, escala, altura, agachado, natación)
- Reactivada recogida de entidades

## [0.0.0-beta.21] - 2026-07-17

### Fix
- Crash al iniciar: `AvatarExtractorMixin` usaba `Object` como parámetro, corregido a `Avatar` (tipo base del genérico)

## [0.0.0-beta.20] - 2026-07-17

### Fix
- Crash al iniciar: `AvatarExtractorMixin` usaba tipo incorrecto (`AbstractClientPlayer` en vez del genérico `AvatarlikeEntity`)
- Render: corregido mixin extractor para inyectar datos de carry en el render state

## [0.0.0-beta.19] - 2026-07-17

### Fix
- Render en tercera persona vía `CarryingItemRenderLayer` (RenderLayer)
- Render en primera persona vía mixin en `renderHand` de AvatarRenderer
- Datos de carry inyectados en `AvatarRenderState` via `AvatarExtractorMixin`

## [0.0.0-beta.18] - 2026-07-13

### Fix
- Render del bloque cargado: implementado via `RenderLayer` + `ICarryOnRenderState` (misma técnica que el mod original)
- Eliminado mixin `ItemInHandRendererMixin` (no funcionaba)

## [0.0.0-beta.17] - 2026-07-13

### Fix
- Render del bloque cargado: mixin movido a `renderArmWithItem` (método que maneja la mano vacía en primera persona)
- Desactivada recogida de entidades (mobs, animales, npcs) — solo objetos inanimados

## [0.0.0-beta.16] - 2026-07-13

### Docs
- Actualizado WORKFLOW.md: nueva sección Ramas, nuevo formato de tags `<mc-version>-neoforge-beta.X`
- Creada rama `minecraft/26.1.2/neoforge-26.1.2.78/production` según workflow

## [0.0.0-beta.15] - 2026-07-13

### Fix
- Render del bloque cargado: cambiado a mixin en `ItemInHandRenderer.renderItem()` (método real que pinta items en la mano en primera persona)
- Eliminado `AvatarRendererMixin` (solo renderiza el brazo, no items)

## [0.0.0-beta.14] - 2026-07-13

### Fix
- Slowness refrescado en cada tick del servidor para asegurar que el efecto se mantiene activo
- Render: cambiada posición del bloque en la mano para mejor visibilidad

## [0.0.0-beta.13] - 2026-07-13

### Fix
- Renderizado del bloque cargado: cambiado a `ItemStackRenderState` + `ItemModelResolver` para renderizar el bloque como item en la mano

## [0.0.0-beta.12] - 2026-07-13

### Fix
- Prevención de salto: eliminado mixin ServerPlayerMixin (no funcionaba), reemplazado por cancelación de movimiento vertical en ServerTick

## [0.0.0-beta.11] - 2026-07-13

### Feat
- Renderizado del bloque cargado en primera persona (mano derecha) usando `BlockModelRenderState` + `SubmitNodeCollector`
- Prevención de salto reforzada con cancelación de movimiento vertical en ServerTick

### Fix
- Eliminado mixin `ServerPlayerMixin` (no lograba interceptar el salto correctamente)

## [0.0.0-beta.10] - 2026-07-13

### Fix
- Prevención de salto no funcionaba — mixin movido de `LivingEntity` a `ServerPlayer.jumpFromGround()`

## [0.0.0-beta.9] - 2026-07-13

### Feat
- Slowness mínima nivel 1 siempre aplicada al transportar
- Hook de render en `AvatarRenderer.renderRightHand()` con firma correcta
- Mixin de prevención de salto en `LivingEntity.jumpFromGround()`

## [0.0.0-beta.8] - 2026-07-13

### Fix
- Pickup no funcionaba por latencia — reemplazado paquete custom por `player.isCrouching()` vanilla
- Eliminado `ServerboundCarryKeyPressedPacket` (ya no necesario)
- Eventos de right-click ya no se cancelan si no se realiza acción

## [0.0.0-beta.7] - 2026-07-13

### Fix
- Carry key state tracking — enviar estado de tecla al servidor en cada tick

## [0.0.0-beta.6] - 2026-07-13

### Fix
- Eliminado `LivingJumpEvent` (no existe en esta versión)
- Prevención de salto mediante cancelación de movimiento vertical en tick

## [0.0.0-beta.5] - 2026-07-13

### Fix
- Right-click ya no cancela todas las interacciones (abrir cofres, puertas)
- Prevención de salto mientras se carga
- Slowness siempre aplicada al cargar

## [0.0.0-beta.4] - 2026-07-13

### Fix
- Eliminado `AvatarRendererMixin` (API de render diferente en esta versión)

## [0.0.0-beta.3] - 2026-07-13

### Fix
- Corregida firma de `PlayerMixin.drop()` — `drop(ItemStack, boolean)`
- Eliminado `HumanoidModelMixin` (API `setupAnim` diferente)

## [0.0.0-beta.2] - 2026-07-13

### Fix
- Eliminado `ICarryOnRenderState` del mixin JSON (causaba `InvalidMixinException`)
- Simplificado `AvatarRendererMixin` — acceso directo a `CarryDataManager`

## [0.0.0-beta.1] - 2026-07-13

### Initial Beta Release

- Pick up blocks (with tile entities), entities, and players
- Place carried objects on any surface
- Entity stacking
- Whitelist/blacklist via datapack tags
- 25+ config options
- Scripting system
- Commands
- Networking and multiplayer sync
