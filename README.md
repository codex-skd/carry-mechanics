# Carry Mechanics

Pick up, carry, and place blocks and entities with your bare hands!

![minecraft](https://img.shields.io/badge/Minecraft-26.2-blue)
![fabric](https://img.shields.io/badge/Fabric-0.19.3-orange)
![version](https://img.shields.io/badge/version-1.0.2-green)

## Overview

Carry Mechanics is a lightweight, configurable mod that lets you pick up, carry, and place blocks (with their tile entities) and entities using only your empty hands. No items, no tools — just you and what you can carry.

A fork of the classic **Carry On** mod by Tschipp and PurpliciousCow, reworked for modern Fabric (Fabric API).
Gameplay dynamics differ — crouch-to-pick-up behaviour, block and entity grab rules,
carry conditions and rendering — so this is a modified derivative, not a drop-in copy.
Original copyright is retained and the complete corresponding source is published here,
as the LGPL requires. See `LICENSE`.

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
- **Tall blocks** — Two-block-tall blocks (doors, tall plants, modded machines) are carried whole; larger blocks are off by default
- **Per-block / per-mod rules** — Allowed and blocked block lists by id or `modid:*`, in the config or with commands
- **First Person Model compatible** — The carried object shows correctly in first person when the optional First Person Model mod is installed
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
- `/carrymechanics blacklist add|remove <block>` — edits `blockedBlocks` (operators, permission level 2)
- `/carrymechanics whitelist add|remove <block>` — edits `allowedBlocks` (operators, permission level 2)
- `/carrymechanics blacklist list` / `whitelist list` — show the entries

`<block>` accepts a block id (`modid:name`) or a whole mod (`modid:*`). Changes apply immediately and are saved to the config.

## Configuration

The config file is `config/carry_mechanics.json` (generated on first launch):

- `maxDistance` — Maximum pickup range (default: 2.5)
- `maxEntityHeight/Width` — Size limits for pickup
- `blockSlownessMultiplier/entitySlownessMultiplier` — Slowness intensity
- `pickupAllBlocks` — Allow picking up blocks without tile entities
- `pickupHostileMobs` — Allow picking up hostile mobs
- And many more options

### Block rules (top-level keys)

- `allowTwoTallBlocks` (boolean, default `true`) — allow carrying two-block-tall blocks (doors, tall plants, modded machines) whole.
- `allowLargeBlocks` (boolean, default `false`) — allow carrying larger blocks (wider than one block, such as beds, or taller than two). Double chests and fence/wall posts are not treated as large.
- `allowedBlocks` (list) — block ids (`modid:name`) or whole mods (`modid:*`) that are always permitted, even large ones; overrides the datapack blacklist. Other safety checks (unbreakable, locked) still apply.
- `blockedBlocks` (list) — same syntax; these are never carried and win over `allowedBlocks`.

Order: `blockedBlocks` → `allowedBlocks` → datapack tags / whitelist mode → two-tall rule → large rule.

## Tags

Use datapack tags to control what can be picked up:

- `carry_mechanics:block/block_whitelist` / `block_blacklist`
- `carry_mechanics:entity_type/entity_whitelist` / `entity_blacklist`
- `carry_mechanics:entity_type/stacking_whitelist` / `stacking_blacklist`

## License

GNU LGPL v3.0 (LGPL-3.0-or-later) — see [`LICENSE`](LICENSE). Fork of Carry On (Tschipp, PurpliciousCow); the original copyright is retained and the full corresponding source is published in this repository, as the LGPL requires.
