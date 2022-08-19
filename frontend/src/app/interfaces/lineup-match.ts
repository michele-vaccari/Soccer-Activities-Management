export interface LineupMatch {
    id: number,
    tournamentName: string,
    matchName: string,
    matchDate: string,
    teamId: number,
    otherTeamId: number,
    teamName: string,
    otherTeamName: string,
    reportId: number,
    teamLineupSubmitted: boolean,
    otherTeamLineupSubmitted: boolean
}
