import { Component, OnInit } from '@angular/core';
import { HttpClientService } from './services/http-client.service';

import { Todo } from './todo';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})

export class AppComponent implements OnInit {

  todos: Todo[] = [];

  constructor(private httpClientService: HttpClientService) { }

  ngOnInit(): void {
    this.httpClientService.getTodos().subscribe(res => {
      this.todos = res;
    })
  }
}
