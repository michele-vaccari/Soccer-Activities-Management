export interface TournamentMatch {
    id?: number;
    tournamentName: string;
    matchName: string;
    teamId?: number;
    otherTeamId?: number;
    teamName: string;
    otherTeamName: string;
    reportId?: number;
    teamLineupSubmitted?: boolean,
    otherTeamLineupSubmitted?: boolean;
}
