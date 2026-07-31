# Project Variables — Carry Mechanics

<!--
  Este archivo contiene las variables específicas del proyecto para CurseForge.
  Se usa como referencia al subir versiones. No contiene secrets directamente,
  solo referencias a los tokens que están en el gestor de contraseñas.
-->

## CurseForge

| Variable | Valor |
|----------|-------|
| Project ID | `1608286` |
| Upload Token | `ee776b0a-ee95-4850-b554-06be02a8657f` |
| Core API GET | `$2a$10$yGwryAfmRkS9ZJsJUDf5YOKZpOIsmHB8Fji2D8JVCKBSZEKYlwmaO` |

## Game Versions (IDs)

| Recurso | ID | gameVersionTypeId |
|---------|----|----|
| Minecraft 26.2 | `16498` | 86297 |
| Client | `9638` | 75208 |
| Server | `9639` | 75208 |
| NeoForge | `10150` | 68441 |

## Versiones

| Recurso | Versión |
|---------|---------|
| Minecraft | `26.2` |
| NeoForge | `26.2.0.37-beta` |
| Java | `25` |
| Mod loader | `neoforge` |

## Mod

| Variable | Valor |
|----------|-------|
| mod_id | `carry_mechanics` |
| display_name | `Carry Mechanics` |
| mod_group_id | `com.skd.carrymechanics` |
| package_path | `com/skd/carrymechanics` |

## Rama

| Variable | Valor |
|----------|-------|
| branch | `minecraft/26.2/neoforge-26.2.0.41-beta/production` |
| tag_prefix | `26.2-neoforge-` |

## Variables para el script de subida (curseforge-upload.ps1)

<!-- Formato clave = valor requerido por Get-VarFromMd en codex-docs/scripts/curseforge-upload.ps1 -->

project_id = 1608286
api_token = ee776b0a-ee95-4850-b554-06be02a8657f
game_versions = 16498,9638,9639,10150
release_type = release

## Token de subida (Python)

```python
# Ejemplo de uso con requests
import requests

UPLOAD_TOKEN = "ee776b0a-ee95-4850-b554-06be02a8657f"
PROJECT_ID = 1608286
headers = {
    "X-Api-Tokens": UPLOAD_TOKEN
}

# displayName debe seguir el formato: "Carry Mechanics (0.0.0-beta.X)"
# gameVersions: [16498=Minecraft 26.2, 9638=Client, 9639=Server, 10150=NeoForge]
```
