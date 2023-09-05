export class ModalMessage {
  constructor(
    public title: string,
    public description: string,
    public show: boolean,
    public isFetching: boolean,
    public showCloseUpAndDown: boolean,
    public areYouSure: boolean,
    public componentName: string,
    public width: number | null,
  ) {}
}

