import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import * as flights from '../../assets/flight-sample.json';
import {Flight} from '../model/flight';
import {HttpClient} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class SearchService {

  baseUrl = '//localhost:8080/';

  constructor(private http: HttpClient) {
  }

  searchFlights(q: string): Observable<Flight[]> {
    console.log('calling search service with query ', q);
    return this.http.get<Flight[]>(this.baseUrl + 'search', {params: {query: q}});
  }

  mockSearchFlights(query: string): Flight[] {
    const regex = /([a-zA-Z][a-zA-Z])?(\d+)/gm;
    let m;

    // tslint:disable-next-line:no-conditional-assignment
    while ((m = regex.exec(query)) !== null) {
      // This is necessary to avoid infinite loops with zero-width matches
      if (m.index === regex.lastIndex) {
        regex.lastIndex++;
      }

      console.log(m);
      // The result can be accessed through the `m`-variable.
      m.forEach((match, groupIndex) => {
        console.log(`Found match, group ${groupIndex}: ${match}`);
      });
    }

    // https://basarat.gitbooks.io/typescript/docs/types/type-assertion.html
    return flights as any as Flight[];
  }
}
