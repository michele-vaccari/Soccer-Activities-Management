export interface Player {
    id?: number,
    teamId: number,
    active: string,
    jerseyNumber: number,
    role: string,
    name: string,
    surname: string,
    birthDate?: string,
    citizenship?: string,
    description?: string,
    goal?: number,
    admonitions?: number,
    ejections?: number
}
