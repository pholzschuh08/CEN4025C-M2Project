import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { Tasks } from './components/tasks/tasks';
import { Users } from './components/users/users';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, Tasks, Users],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  protected readonly title = signal('TaskManagerFrontEnd');
}
