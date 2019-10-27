# Tasker
> Service for managing docker services

## About
Tasker is service for monitoring and managing services deployed
in docker containers.

## Config

### Angular config
When starting container create bind from /ng-config to directory with keycloak.json

### API config
Set keycloak credentials via environment variables

## Volume

Map docker volume to `my-volume:/app/data`