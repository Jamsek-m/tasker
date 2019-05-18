export interface DockerPartialInfo {
    Id: string;
    Names: string[];
    Image: string;
    ImageID: string;
    Command: string;
    Created: number;
    Ports: DockerPartialInfo.Port[];
    Labels: any;
    State: string;
    Status: string;
    HostConfig: DockerPartialInfo.HostConfig;
    NetworkSettings: DockerPartialInfo.NetworkSettings;
    Mounts: any[];
}

export namespace DockerPartialInfo {
    export interface Port {
        IP: string;
        PrivatePort: number;
        PublicPort: number;
        Type: string;
    }

    export interface HostConfig {
        NetworkMode: string;
    }

    export interface NetworkSettings {
        Networks: { [key: string]: DockerPartialInfo.Network };
    }

    export interface Network {
        IPAMConfig: any;
        Links: any;
        Aliases: any;
        NetworkID: string;
        EndpointID: string;
        Gateway: string;
        IPAddress: string;
        MacAddress: string;
        IPPrefixLen: number;
        IPv6Gateway: string;
        GlobalIPv6Address: string;
        GlobalIPv6PrefixLen: number;
        DriverOpts: any;
    }
}
