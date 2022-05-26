import { RankingLine } from "./ranking-line";
import { TournamentMatch } from "./tournament-match";

export interface Tournament {
    id?: number;
    name: string;
    type: string,
    description?: string,
    firstRoundMatches: Map<number, TournamentMatch[]>,
    secondRoundMatches: Map<number, TournamentMatch[]>,

    eighthFinalsMatches: Map<number, TournamentMatch[]>,
	quarterFinalsMatches: Map<number, TournamentMatch[]>,
	semifinalMatches: Map<number, TournamentMatch[]>,
	finalMatch: TournamentMatch,

    ranking: RankingLine[]
}
