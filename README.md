# Carry Mechanics

Pick up, carry, and place blocks and entities with your bare hands!

![minecraft](https://img.shields.io/badge/Minecraft-26.2-blue)
![fabric](https://img.shields.io/badge/Fabric-0.19.3-orange)
![version](https://img.shields.io/badge/version-1.0.0-green)

## Overview

Carry Mechanics is a lightweight, configurable mod that lets you pick up, carry, and place blocks (with their tile entities) and entities using only your empty hands. No items, no tools — just you and what you can carry.

Inspired by the classic Carry On mod, rewritten from scratch for modern Fabric (Fabric API).

This is the **Fabric** build. A NeoForge build is also available from the same CurseForge project.

## Requirements

| Dependency | Version |
|---|---|
| Minecraft | 26.2 |
| Fabric Loader | 0.19.3 |
| Fabric API | 0.156.0+26.2 |

## Installation

1. Install Fabric Loader 0.19.3 for Minecraft 26.2
2. Install [Fabric API](https://modrinth.com/mod/fabric-api) for 26.2
3. Download the latest JAR from [CurseForge](https://www.curseforge.com/minecraft/mc-mods/carry-mechanics)
4. Place the JAR in your `mods/` folder
5. Launch Minecraft

## Features

- **Pick up blocks** — Grab any block with a tile entity (chests, furnaces, spawners, etc.) by right-clicking while holding Shift
- **Pick up entities** — Carry animals, villagers, and other mobs
- **Place anywhere** — Right-click on any surface to place what you're carrying
- **Entity stacking** — Stack carried entities on top of other entities
- **Fully configurable** — Whitelist/blacklist via datapack tags, 25+ config options
- **Scripting support** — Datapack-powered scripts with conditions and custom commands
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

The config file is `config/carry_mechanics.json` (generated on first launch):

- `maxDistance` — Maximum pickup range (default: 2.5)
- `maxEntityHeight/Width` — Size limits for pickup
- `blockSlownessMultiplier/entitySlownessMultiplier` — Slowness intensity
- `pickupAllBlocks` — Allow picking up blocks without tile entities
- `pickupHostileMobs` — Allow picking up hostile mobs
- And many more options

## Tags

Use datapack tags to control what can be picked up:

- `carry_mechanics:block/block_whitelist` / `block_blacklist`
- `carry_mechanics:entity_type/entity_whitelist` / `entity_blacklist`
- `carry_mechanics:entity_type/stacking_whitelist` / `stacking_blacklist`

## License

All Rights Reserved.
