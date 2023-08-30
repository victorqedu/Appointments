import { Component, Input, OnChanges } from '@angular/core';

@Component({
  selector: 'app-image-display',
  templateUrl: './image-display.component.html',
  styleUrls: ['./image-display.component.css']
})
export class ImageDisplayComponent implements OnChanges {
  @Input() base64Image!: string; // Input property to pass the base64 image
  @Input() width: number = 200;
  @Input() height: number = 200;

  imageDataUrl!: string;

  ngOnChanges() {
    if (this.base64Image) {
      this.imageDataUrl = 'data:image/jpeg;base64,' + this.base64Image;
    }
  }
}
