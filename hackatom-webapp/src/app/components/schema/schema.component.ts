import { Component, OnInit } from '@angular/core';
import {GlobalService} from '../../services/global.service';
import {DomSanitizer, SafeResourceUrl} from "@angular/platform-browser";

@Component({
  selector: 'app-schema',
  templateUrl: './schema.component.html',
  styleUrls: ['./schema.component.css']
})
export class SchemaComponent implements OnInit {

  image: string;
  imagePath: SafeResourceUrl;

  constructor(
    private globalService: GlobalService,
    private domSanitizer: DomSanitizer
  ) { }

  async ngOnInit(): Promise<void> {
    const respBlob = await this.globalService.getSchemaFromPdf(778);
    const imgBlob = respBlob.body;
    const reader = new FileReader();

    reader.addEventListener('loadend', event => {
      this.image = event.target.result.toString();
      console.log(this.image);
    });

    reader.readAsText(imgBlob);
    this.imagePath = this.domSanitizer.bypassSecurityTrustResourceUrl('data:image/jpg;base64,' + this.image);
  }

}
