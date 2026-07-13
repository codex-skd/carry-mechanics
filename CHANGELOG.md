# Changelog

## [0.0.0-beta.1] - 2026-07-13

### Initial Beta Release

- Pick up blocks (with tile entities) by right-clicking with empty hands while holding Shift
- Pick up entities (mobs, animals) by right-clicking while holding Shift
- Pick up other players by right-clicking while holding Shift
- Place carried blocks and entities by right-clicking on a surface
- Stack entities by right-clicking carried entity on another entity
- Configurable whitelist/blacklist system via datapack tags for blocks, entities, and stacking
- Extensive configuration (25+ options): max distance, entity size limits, slowness, hostile mobs, babies, etc.
- Scripting system via datapack JSON scripts with conditions and custom commands
- Networking system for key press sync, script sync, and player riding
- Commands: `/carrymechanics debug`, `/carrymechanics clear`, `/carrymechanics place`
- Mixin-based integration: entity collision, inventory locking, model arm adjustments, name tag hiding
- Client-server synchronization via NeoForge attachments
- Compatible with Minecraft 26.1.2 / NeoForge 26.1.2.78
