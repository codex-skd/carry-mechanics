# Changelog

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
