# Carry Mechanics

Pick up, carry, and place blocks and entities with your bare hands! Inspired by the classic Carry On mod, rewritten from scratch for modern NeoForge.

## Features

- **Pick up blocks** — Grab blocks with tile entities (chests, furnaces, spawners, etc.) by holding **Shift** + right-clicking with empty hands
- **Pick up entities** — Carry mobs, animals, and even other players
- **Place anywhere** — Right-click on any surface to place what you're carrying
- **Entity stacking** — Stack carried entities on top of others like a totem pole
- **Fully configurable** — 25+ options including max distance, entity size limits, slowness multipliers, whitelist/blacklist toggles, hit prevention, player pickup toggle, and more
- **Slowness & weight** — Heavier objects slow you down more; you cannot jump while carrying
- **Whitelist/Blacklist** — Control what can be picked up via datapack tags for blocks, entities, and stacking (6 tag types)
- **Scripting system** — Datapack-powered JSON scripts with conditions (gamestage, advancement, XP, gamemode) and custom commands on init/loop/place
- **Commands** — `/carrymechanics debug`, `/carrymechanics clear [player]`, `/carrymechanics place [player]`
- **Multiplayer** — Full sync between client and server

## Controls

| Action | Control |
|--------|---------|
| Pick up | Hold **Shift** (sneak) + Right-click |
| Place | Right-click on surface |
| Stack entity | Right-click carried entity on another entity |

## Requirements

- Minecraft 26.1.2
- NeoForge 26.1.2.78

## Disclaimer

This is an early beta. The visual rendering of carried objects is not yet implemented (objects appear invisible while carried).
