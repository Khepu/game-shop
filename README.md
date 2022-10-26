# Game Shop

## Architecture

```mermaid
graph LR
Gateway -- https --- Keycloak

Keycloak --- KeycloakDB

CartManager -- rpc --- Gateway
BackOffice -- rpc --- Gateway

BackOffice --- BackOfficeDB
CartManager --- CartManagerDB
Gateway --- GatewayDB

subgraph API
   Gateway
   BackOffice
end

subgraph External
    Client((Client)) --- Internet(Internet)
    Admin((Admin)) --- Intranet(Intranet)
end

subgraph Storage
   CartManagerDB
   GatewayDB
   BackOfficeDB
end

Intranet(Intranet) --- BackOffice
Internet(Internet) --- Gateway
```