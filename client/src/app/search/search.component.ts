import {Component, OnInit, ViewChild} from '@angular/core';
import {SearchService} from '../services/search.service';
import {FormControl} from '@angular/forms';
import {Flight} from '../model/flight';
import {MatPaginator, MatTableDataSource} from '@angular/material';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.scss']
})
export class SearchComponent implements OnInit {
  queryForm = new FormControl('');

  dataSource = new MatTableDataSource<Flight>();
  displayedColumns = ['flightNumber', 'carrier', 'origin', 'destination', 'arrival', 'departure', 'aircraft', 'distance', 'travelTime', 'status'];

  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;

  constructor(private searchService: SearchService) {
  }

  ngOnInit() {
    this.init();
  }

  init() {
    this.dataSource.data = [];
    this.dataSource.paginator = this.paginator;
    // Mock data source
    // this.dataSource.data = this.searchService.mockSearchFlights(null);
  }

  /**
   * Calls the http service to trigger a query
   * @param $event -  search button click
   */
  onSearchFlight($event: MouseEvent) {
    this.searchService.searchFlights(this.queryForm.value).subscribe(flights => {
      this.dataSource = new MatTableDataSource(flights);
      this.dataSource.paginator = this.paginator;
    });
    // this.searchService.mockSearchFlights(this.queryForm.value);
  }

  /**
   * Clear button event trigger for clearing the search box
   * @param $event
   */
  clearForm($event: MouseEvent) {
    this.dataSource.data = [];
    this.queryForm.setValue('');
  }

  /**
   * Pretty output of a date
   * @param date
   */
  formatDate(date: string): string {
    return date.replace('T', ' ');
  }
}
