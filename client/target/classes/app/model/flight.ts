/**
 * Flight data object
 */
export class Flight {
  constructor(
    public flightNumber?: string,
    public carrier?: string,
    public origin?: string,
    public departure?: Date,
    public destination?: string,
    public arrival?: Date,
    public aircraft?: string,
    public distance?: number,
    public travelTime?: string,
    public status?: string
  ) {
  }
}
