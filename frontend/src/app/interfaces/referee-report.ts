import { PlayerReport } from "./player-report";

export interface RefereeReport {
    id?: number,
    tournamentName?: string,
    teamName?: string,
    otherTeamName?: string,
    result?: string,
    mainTeamPlayerReportsDto: PlayerReport[],
    reserveTeamPlayerReportsDto: PlayerReport[],
    mainOtherTeamPlayerReportsDto: PlayerReport[],
    reserveOtherTeamPlayerReportsDto: PlayerReport[],
    autogoalTeam: number,
    autogoalOtherTeam: number,
    matchPlace?: string,
    matchDate?: string,
    matchStartTime?: string,
    matchEndTime?: string,
    refereeId?: number,
    refereeName?: string,
    refereeSurname?: string
}
