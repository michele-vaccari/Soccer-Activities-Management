export interface PlayerReport {
    id: number,
	jerseyNumber: number,
	name?: string,
	surname?: string,
	goal: number,
	admonitions: number,
	ejection: boolean,
    autogoals?: number
}
