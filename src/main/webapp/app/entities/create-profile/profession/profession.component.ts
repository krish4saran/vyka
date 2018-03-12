import { Component, OnInit } from '@angular/core';
import { Profile } from '../../profile';
import { ProfileService } from '../../../layouts';
import { CreateProfileService } from '../create-profile.service';
import { ProfileSubject } from '../../profile-subject/profile-subject.model';
import { Observable } from 'rxjs/Rx';
import { Router, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'jhi-profession',
  templateUrl: './profession.component.html',
  styles: []
})
export class ProfessionComponent implements OnInit {
  ngOnInit(): void {
    throw new Error('');
  }
}
