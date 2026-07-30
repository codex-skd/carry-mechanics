# Carry Mechanics

Pick up, carry, and place blocks and entities with your bare hands!

![minecraft](https://img.shields.io/badge/Minecraft-26.1.2-blue)
![neoforge](https://img.shields.io/badge/NeoForge-26.1.2.78-orange)
![version](https://img.shields.io/badge/version-0.0.0--beta.44-green)

## Overview

Carry Mechanics is a lightweight, configurable mod that lets you pick up, carry, and place blocks (with their tile entities) and entities using only your empty hands. No items, no tools — just you and what you can carry.

Inspired by the classic Carry On mod, rewritten from scratch for modern NeoForge.

## Requirements

| Dependency | Version |
|---|---|
| Minecraft | 26.1.2 |
| NeoForge | 26.1.2.78 |

## Installation

1. Install NeoForge 26.1.2.78 for Minecraft 26.1.2
2. Download the latest JAR from [CurseForge](https://www.curseforge.com/minecraft/mc-mods/carry-mechanics)
3. Place the JAR in your `mods/` folder
4. Launch Minecraft

## Features

- **Pick up blocks** — Grab any block with a tile entity (chests, furnaces, spawners, etc.) by right-clicking while holding Shift
- **Pick up entities** — Carry mobs, animals, and even other players
- **Place anywhere** — Right-click on any surface to place what you're carrying
- **Entity stacking** — Stack carried entities on top of other entities
- **Fully configurable** — Whitelist/blacklist via datapack tags, 25+ config options
- **Scripting support** — Datapack-powered scripts with conditions and custom commands
- **Carry players** — Pick up and carry other players (with config option)
- **Slowness effects** — Heavier objects slow you down more, configurable per type

## Controls

| Action | Control |
|--------|---------|
| Pick up | Hold **Left Shift** + Right-click |
| Place | Right-click on surface |
| Stack entity | Right-click carried entity on target |

## Commands

- `/carrymechanics debug` — Shows current carry data
- `/carrymechanics clear [player]` — Clears carry data
- `/carrymechanics place [player]` — Places carried object

## Configuration

Available in the mod's config screen or directly in `config/carry_mechanics-common.toml`:

- `maxDistance` — Maximum pickup range (default: 2.5)
- `maxEntityHeight/Width` — Size limits for pickup
- `blockSlownessMultiplier/entitySlownessMultiplier` — Slowness intensity
- `pickupAllBlocks` — Allow picking up blocks without tile entities
- `pickupHostileMobs` — Allow picking up hostile mobs
- `pickupPlayers` — Allow picking up other players
- And many more...

## Tags

Use datapack tags to control what can be picked up:

- `carry_mechanics:block/block_whitelist`
- `carry_mechanics:block/block_blacklist`
- `carry_mechanics:entity_type/entity_whitelist`
- `carry_mechanics:entity_type/entity_blacklist`
- `carry_mechanics:entity_type/stacking_whitelist`
- `carry_mechanics:entity_type/stacking_blacklist`

## License

All Rights Reserved.
