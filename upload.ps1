$token = 'ee776b0a-ee95-4850-b554-06be02a8657f'
$projectId = 1608286
$jarPath = 'build/libs/carry_mechanics-26.1.2-neoforge-0.0.0-beta.27.jar'
$changelogPath = 'docs/curseforge/versions/0.0.0-beta.27.md'
$changelog = [System.IO.File]::ReadAllText((Resolve-Path $changelogPath))

$metadata = @{
    displayName = 'Carry Mechanics (0.0.0-beta.27)'
    gameVersions = @(16082, 9638, 9639, 10150)
    releaseType = 'beta'
    changelogType = 'html'
    changelog = $changelog
}

$metadataJson = $metadata | ConvertTo-Json -Compress

# Build multipart form manually
$boundary = [System.Guid]::NewGuid().ToString()
$fileItem = Get-Item -Path (Resolve-Path $jarPath)
$fileBytes = [System.IO.File]::ReadAllBytes($fileItem.FullName)

$bodyLines = New-Object System.Collections.ArrayList
[void]$bodyLines.Add("--$boundary")
[void]$bodyLines.Add("Content-Disposition: form-data; name=`"metadata`"")
[void]$bodyLines.Add("Content-Type: application/json")
[void]$bodyLines.Add("")
[void]$bodyLines.Add($metadataJson)
[void]$bodyLines.Add("--$boundary")
[void]$bodyLines.Add("Content-Disposition: form-data; name=`"file`"; filename=`"$($fileItem.Name)`"")
[void]$bodyLines.Add("Content-Type: application/java-archive")
[void]$bodyLines.Add("")

$bodyText = $bodyLines -join "`r`n"
$textBytes = [System.Text.Encoding]::UTF8.GetBytes($bodyText)
$endBytes = [System.Text.Encoding]::UTF8.GetBytes("`r`n--$boundary--`r`n")

$allBytes = New-Object System.IO.MemoryStream
$allBytes.Write($textBytes, 0, $textBytes.Length)
$allBytes.Write($fileBytes, 0, $fileBytes.Length)
$allBytes.Write($endBytes, 0, $endBytes.Length)
$allBytes.Position = 0

$headers = @{
    'X-Api-Token' = $token
    'Content-Type' = "multipart/form-data; boundary=$boundary"
}

try {
    $response = Invoke-WebRequest -Uri "https://minecraft.curseforge.com/api/projects/$projectId/upload-file" -Method Post -Headers $headers -Body $allBytes
    Write-Host "Status: $($response.StatusCode)"
    Write-Host "Response: $($response.Content)"
} catch {
    Write-Host "Status: $($_.Exception.Response.StatusCode.value__)"
    Write-Host "Error: $($_.Exception.Message)"
    if ($_.Exception.Response) {
        $reader = New-Object System.IO.StreamReader($_.Exception.Response.GetResponseStream())
        $reader.BaseStream.Position = 0
        $reader.DiscardBufferedData()
        Write-Host "Body: $($reader.ReadToEnd())"
    }
}