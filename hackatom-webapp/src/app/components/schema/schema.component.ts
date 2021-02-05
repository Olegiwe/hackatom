import {AfterViewInit, Component, ElementRef, EventEmitter, OnInit, ViewChild} from '@angular/core';
import {GlobalService} from '../../services/global.service';
import {DomSanitizer, EventManager, SafeResourceUrl} from '@angular/platform-browser';


@Component({
  selector: 'app-schema',
  templateUrl: './schema.component.html',
  styleUrls: ['./schema.component.css']
})
export class SchemaComponent implements OnInit, AfterViewInit {

  @ViewChild('schema') schemaCanvas: ElementRef;

  private canvasContext: CanvasRenderingContext2D;

  image: string;
  imagePath: string;
  src: any;
  isDialogVisible = false;
  isPending = false;
  idArray = [500, 600];
  activePoints = [];
  selectedRegion = [];

  constructor(
    private globalService: GlobalService,
    private domSanitizer: DomSanitizer
  ) {
  }

  ngOnInit(): void {
  }

  ngAfterViewInit(): void {
    this.canvasContext = (this.schemaCanvas?.nativeElement as HTMLCanvasElement).getContext('2d');

  }

  async onRequest(id: number): Promise<void> {
    this.isPending = true;
    try {
      setTimeout(() => this.isDialogVisible = true, 4000);
      const respBody = await this.globalService.getSchemaFromPdf(id);
      console.log(this.imagePath);
      const imgBlob = respBody.body;
      const reader = new FileReader();
      reader.readAsDataURL(imgBlob);
      reader.onloadend = () => {
        this.imagePath = reader.result.toString();
        console.log(this.imagePath);
        this.imagePath = this.imagePath.replace('data:text/plain;base64,', '');
        // this.src = this.domSanitizer.bypassSecurityTrustUrl('data:image/png;base64,' + this.imagePath);
        this.src = 'data:image/png;base64,' + this.imagePath;
        console.log(this.src);
        this.resetVariables();
        this.drawSchemaImage();
      };
    } catch (e) {
      console.error(e.toString());
    } finally {
      this.isPending = false;
    }
  }

  drawSchemaImage() {
    this.canvasContext.clearRect(0, 0, this.schemaCanvas.nativeElement.width, this.schemaCanvas.nativeElement.height);
    const background = new Image();
    background.src = this.src;
    background.onload = () => {
      this.schemaCanvas.nativeElement.width = background.width;
      this.schemaCanvas.nativeElement.height = background.height;
      this.canvasContext.drawImage(background, 0, 0);
    };
  }

  recordCoordinates(event) {
    const x = event.offsetX;
    const y = event.offsetY;
    this.activePoints.push({x, y});
    this.canvasContext.fillRect(x, y, 5, 5);
    if (this.activePoints.length > 1) {
      console.log('rect');
      this.drawRectangle(this.activePoints[0].x, this.activePoints[0].y,
        this.activePoints[1].x - this.activePoints[0].x, this.activePoints[1].y - this.activePoints[0].y);
      Object.assign(this.selectedRegion, this.activePoints);
      this.activePoints = [];
      console.log(this.selectedRegion);
    }
  }

  drawRectangle(x, y, width, height) {
    this.canvasContext.strokeStyle = '#4682b4';
    this.canvasContext.strokeRect(x, y, width, height);
  }

  onSelectedRegionChange() {

  }

  addComponentFromScheme() {

  }

  resetVariables() {
    this.selectedRegion = [];
    this.activePoints = [];
  }

}
