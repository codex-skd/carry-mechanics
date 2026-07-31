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

<!-- TODO: pendiente de consultar en la Core API de CurseForge antes de la primera subida a 26.2. Los IDs de 26.1.2 NO son válidos para 26.2 -->

| Recurso | ID |
|---------|----|
| Minecraft 26.2 | `TODO` |
| Client | `9638` |
| Server | `9639` |
| NeoForge | `TODO` |

## Versiones

| Recurso | Versión |
|---------|---------|
| Minecraft | `26.2` |
| NeoForge | `26.2.0.41-beta` |
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

<!-- Formato clave = valor requerido por Get-VarFromMd en codex-docs/scripts/curseforge-upload.ps1. game_versions pendiente de completar (ver TODO arriba) antes de la primera subida -->

project_id = 1608286
api_token = ee776b0a-ee95-4850-b554-06be02a8657f
game_versions = TODO,9638,9639,TODO
release_type = beta

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
# gameVersions: [16082=Minecraft 26.1.2, 9638=Client, 9639=Server, 10150=NeoForge]
```
